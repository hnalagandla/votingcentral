/*
 * Created on Apr 25, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.user;

import org.apache.struts.upload.FormFile;

import com.votingcentral.forms.VCBaseFormBean;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ManageMyImageForm extends VCBaseFormBean {

	private FormFile uploadFileName;

	private String deleteImageUrl;

	private String minImageUrl;

	private String maxImageUrl;

	private String imageId;

	/**
	 * @return Returns the deleteImageUrl.
	 */
	public String getDeleteImageUrl() {
		return deleteImageUrl;
	}

	/**
	 * @param deleteImageUrl
	 *            The deleteImageUrl to set.
	 */
	public void setDeleteImageUrl(String deleteImageUrl) {
		this.deleteImageUrl = deleteImageUrl;
	}

	/**
	 * @return Returns the maxImageUrl.
	 */
	public String getMaxImageUrl() {
		return maxImageUrl;
	}

	/**
	 * @param maxImageUrl
	 *            The maxImageUrl to set.
	 */
	public void setMaxImageUrl(String displayImageUrlMax) {
		this.maxImageUrl = displayImageUrlMax;
	}

	/**
	 * @return Returns the minImageUrl.
	 */
	public String getMinImageUrl() {
		return minImageUrl;
	}

	/**
	 * @param minImageUrl
	 *            The minImageUrl to set.
	 */
	public void setMinImageUrl(String displayImageUrlMin) {
		this.minImageUrl = displayImageUrlMin;
	}

	/**
	 * @return Returns the imageId.
	 */
	public String getImageId() {
		return imageId;
	}

	/**
	 * @param imageId
	 *            The imageId to set.
	 */
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	/**
	 * @return Returns the uploadFileName.
	 */
	public FormFile getUploadFileName() {
		return uploadFileName;
	}

	/**
	 * @param uploadFileName
	 *            The uploadFileName to set.
	 */
	public void setUploadFileName(FormFile uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
}