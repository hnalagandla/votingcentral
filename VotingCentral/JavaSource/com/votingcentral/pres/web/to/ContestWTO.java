/**
 * 
 */
package com.votingcentral.pres.web.to;

/**
 * @author harishn
 *  
 */
public class ContestWTO extends PollWTO {

	private String uploadPicturesUrl;

	private String message;

	/**
	 * @return the uploadPicturesUrl
	 */
	public String getUploadPicturesUrl() {
		return uploadPicturesUrl;
	}

	/**
	 * @param uploadPicturesUrl
	 *            the uploadPicturesUrl to set
	 */
	public void setUploadPicturesUrl(String uploadPicturesUrl) {
		this.uploadPicturesUrl = uploadPicturesUrl;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}