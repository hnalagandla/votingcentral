/*
 * Created on Aug 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.votingcentral.model.polls;

import org.apache.struts.upload.FormFile;

/**
 * @author harishn
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AnswerChoice {

	private String answer;

	private String answerId;

	private String answerImageId;

	private String minImageUrl;

	private String maxImageUrl;

	private int imageOriginalH;

	private int imageOriginalW;

	private long answerTotalVotes;

	private FormFile answerImageFile;

	/**
	 *  
	 */
	public AnswerChoice() {
		answerTotalVotes = 0;
	}

	/**
	 * @return
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @return
	 */
	public String getAnswerId() {
		return answerId;
	}

	/**
	 * @return
	 */
	public long getAnswerTotalVotes() {
		return answerTotalVotes;
	}

	/**
	 * @param string
	 */
	public void setAnswer(String string) {
		answer = string;
	}

	/**
	 * @param string
	 */
	public void setAnswerId(String string) {
		answerId = string;
	}

	/**
	 * @param l
	 */
	public void setAnswerTotalVotes(long l) {
		answerTotalVotes = l;
	}

	/**
	 * @return Returns the answerImageId.
	 */
	public String getAnswerImageId() {
		return answerImageId;
	}

	/**
	 * @param answerImageId
	 *            The answerImageId to set.
	 */
	public void setAnswerImageId(String answerImageId) {
		this.answerImageId = answerImageId;
	}

	/**
	 * @return Returns the answerImageFile.
	 */
	public FormFile getAnswerImageFile() {
		return answerImageFile;
	}

	/**
	 * @param answerImageFile
	 *            The answerImageFile to set.
	 */
	public void setAnswerImageFile(FormFile answerImageFile) {
		this.answerImageFile = answerImageFile;
	}

	/**
	 * @return Returns the imageOriginalH.
	 */
	public int getImageOriginalH() {
		return imageOriginalH;
	}

	/**
	 * @param imageOriginalH
	 *            The imageOriginalH to set.
	 */
	public void setImageOriginalH(int imageH) {
		this.imageOriginalH = imageH;
	}

	/**
	 * @return Returns the imageOriginalW.
	 */
	public int getImageOriginalW() {
		return imageOriginalW;
	}

	/**
	 * @param imageOriginalW
	 *            The imageOriginalW to set.
	 */
	public void setImageOriginalW(int imageW) {
		this.imageOriginalW = imageW;
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
	public void setMaxImageUrl(String maxImageUrl) {
		this.maxImageUrl = maxImageUrl;
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
	public void setMinImageUrl(String minImageUrl) {
		this.minImageUrl = minImageUrl;
	}
}