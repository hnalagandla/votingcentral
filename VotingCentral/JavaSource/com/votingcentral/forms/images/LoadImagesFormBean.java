/*
 * Created on Mar 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.images;

import org.apache.struts.action.ActionForm;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class LoadImagesFormBean extends ActionForm {

	private String imageId;

	private String w;

	private String h;

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
	 * @return Returns the h.
	 */
	public String getH() {
		return h;
	}

	/**
	 * @param h
	 *            The h to set.
	 */
	public void setH(String h) {
		this.h = h;
	}

	/**
	 * @return Returns the w.
	 */
	public String getW() {
		return w;
	}

	/**
	 * @param w
	 *            The w to set.
	 */
	public void setW(String w) {
		this.w = w;
	}
}