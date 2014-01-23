/*
 * Created on Mar 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCFileTO {
	private String fileId;

	private long userId;

	private String fileType;

	private String fileName;

	private String fileMimeType;

	private byte[] fileContent;

	private Date createTimestamp;

	private Date modifyTimestamp;

	private BufferedImage scaledImage;

	/**
	 * @return Returns the createTimestamp.
	 */
	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	/**
	 * @param createTimestamp
	 *            The createTimestamp to set.
	 */
	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	/**
	 * @return Returns the userId.
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            The userId to set.
	 */
	public void setUserId(long emailAddressId) {
		this.userId = emailAddressId;
	}

	/**
	 * @return Returns the fileContent.
	 */
	public byte[] getFileContent() {
		return fileContent;
	}

	/**
	 * @param fileContent
	 *            The fileContent to set.
	 */
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	/**
	 * @return Returns the fileId.
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId
	 *            The fileId to set.
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return Returns the fileMimeType.
	 */
	public String getFileMimeType() {
		return fileMimeType;
	}

	/**
	 * @param fileMimeType
	 *            The fileMimeType to set.
	 */
	public void setFileMimeType(String fileMimeType) {
		this.fileMimeType = fileMimeType;
	}

	/**
	 * @return Returns the fileName.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            The fileName to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return Returns the fileType.
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType
	 *            The fileType to set.
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return Returns the modifyTimestamp.
	 */
	public Date getModifyTimestamp() {
		return modifyTimestamp;
	}

	/**
	 * @param modifyTimestamp
	 *            The modifyTimestamp to set.
	 */
	public void setModifyTimestamp(Date modifyTimestamp) {
		this.modifyTimestamp = modifyTimestamp;
	}

	/**
	 * @return Returns the scaledImage.
	 */
	public BufferedImage getScaledImage() {
		return scaledImage;
	}

	/**
	 * @param scaledImage
	 *            The scaledImage to set.
	 */
	public void setScaledImage(BufferedImage scaledImage) {
		this.scaledImage = scaledImage;
	}
}