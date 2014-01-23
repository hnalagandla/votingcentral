/*
 * Created on Sep 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.images;

import java.awt.image.RenderedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.votingcentral.forms.images.LoadImagesFormBean;
import com.votingcentral.model.bo.vcfile.VCFileBO;
import com.votingcentral.model.db.dao.to.VCFileTO;
import com.votingcentral.util.pictures.ImageUtils;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class LoadImageAction extends Action {

	private static Log log = LogFactory.getLog(LoadImageAction.class);

	public static final String JPG_CONTENT_TYPE = "image/jpeg";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm, javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		LoadImagesFormBean loadImageForm = (LoadImagesFormBean) form;

		long curr = System.currentTimeMillis();
		OutputStream oStream = null;
		BufferedOutputStream out = null;
		ByteArrayInputStream inStream = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug("LoadImageAction : image generator is started :"
						+ System.currentTimeMillis());
			}
			String imageId = "";
			String widthStr = "";
			String heightStr = "";
			if (loadImageForm != null) {
				imageId = loadImageForm.getImageId();
			} else {
				imageId = request.getParameter("imageId");
				widthStr = request.getParameter("w");
				heightStr = request.getParameter("h");
			}

			int width = 0;
			int height = 0;
			try {
				width = Integer.parseInt(widthStr);
				height = Integer.parseInt(heightStr);
			} catch (NumberFormatException nfe) {
				width = ImageUtils.MIN_W;
				height = ImageUtils.MIN_H;
			}
			VCFileTO fto = VCFileBO.getInstance().getImageFileByFileId(imageId,
					width, height);
			if (fto != null) {
				response.setContentType(fto.getFileMimeType());
				oStream = response.getOutputStream();
				out = new BufferedOutputStream(oStream);
				ImageIO
						.write((RenderedImage) fto.getScaledImage(), "jpeg",
								out);
			}
			if (log.isDebugEnabled()) {
				log.debug("LoadImageAction : image generator is finished : "
						+ System.currentTimeMillis());
			}
			long time = System.currentTimeMillis() - curr;
			log.info("Time taken to load the image is :" + time);

		} catch (Exception e) {
			log.error(" Error generating LoadImageAction :", e);
		} finally {
			if (inStream != null) {
				inStream.close();
			}
			if (out != null) {
				out.close();
			}
			if (oStream != null) {
				oStream.close();
			}
		}
		return null;
	}
}