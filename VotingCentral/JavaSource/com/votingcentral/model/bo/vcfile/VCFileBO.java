/*
 * Created on Mar 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.vcfile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;

import com.votingcentral.env.EnvProps;
import com.votingcentral.model.db.dao.IVCFilesDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.VCFileTO;
import com.votingcentral.model.enums.VCFileTypeEnum;
import com.votingcentral.util.pictures.VCImageScaler;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCFileBO {
	private static int vcFilesCacheSize = Integer.parseInt(EnvProps
			.getProperty("vc.files.in.cache"));

	private static Map vcFileCache = Collections.synchronizedMap(new LRUMap(
			vcFilesCacheSize, 0.75F));

	private static VCFileBO vcfile = null;

	private final static Log log = LogFactory.getLog(VCFileBO.class);

	private VCFileBO() {

	}

	public static VCFileBO getInstance() {
		if (vcfile == null) {
			vcfile = new VCFileBO();
		}
		return vcfile;
	}

	public void saveVCFile(FormFile formFile, String imageId,
			long creatorEmailId, VCFileTypeEnum vcFileTypeEnum, boolean isUpdate)
			throws SQLException, IOException {
		String data = null;

		VCFileTO vto = new VCFileTO();
		String mimeType = formFile.getContentType();
		String fileName = formFile.getFileName();
		byte[] buffer = formFile.getFileData();

		vto.setFileId(imageId);
		vto.setUserId(creatorEmailId);
		vto.setFileType(vcFileTypeEnum.getName());
		vto.setFileName(fileName);
		vto.setFileMimeType(mimeType);
		vto.setFileContent(buffer);
		if (isUpdate) {
			updateFileByFileId(vto);
		} else {
			addFile(vto);
		}
	}

	public int deleteVCFile(String imageId) throws SQLException {
		VCFileTO vto = new VCFileTO();
		vto.setFileId(imageId);
		return deleteFileByFileId(vto);
	}

	public VCFileTO getFileByFileId(String fileId) throws SQLException,
			IOException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCFilesDAO vdao = dao.getVCFilesDAO();
		VCFileTO vto = vdao.getFileByFileId(fileId);
		return vto;
	}

	public VCFileTO getImageFileByFileId(String fileId, int width, int height)
			throws SQLException, IOException {
		String key = fileId;
		Object o = vcFileCache.get(key);
		VCFileTO fto = null;
		if (o != null) {
			log.debug("image from memory:" + key);
			fto = (VCFileTO) o;
		} else {
			fto = getFileByFileId(fileId);
		}
		if (fto == null) {
			log.error("can't find file for :" + fileId);
			return null;
		}
		ByteArrayInputStream inStream = new ByteArrayInputStream(fto
				.getFileContent());
		BufferedImage img = ImageIO.read(inStream);
		VCImageScaler scaler = new VCImageScaler(img, width, height);
		BufferedImage scaledImage = scaler.getScaledImage();
		fto.setScaledImage(scaledImage);
		//save the scaled image
		vcFileCache.put(key, fto);
		return fto;
	}

	public void addFile(VCFileTO vto) throws SQLException, IOException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCFilesDAO vdao = dao.getVCFilesDAO();
		vdao.addFile(vto);
	}

	private void updateFileByFileId(VCFileTO vto) throws IOException,
			SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCFilesDAO vdao = dao.getVCFilesDAO();
		vdao.updateFileForFileId(vto);
		//remove it from the cache, so that the update image will be loaded
		vcFileCache.remove(vto.getFileId());
	}

	private int deleteFileByFileId(VCFileTO vto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCFilesDAO vdao = dao.getVCFilesDAO();
		vcFileCache.remove(vto.getFileId());
		return vdao.deleteFileForFileId(vto);
	}

}