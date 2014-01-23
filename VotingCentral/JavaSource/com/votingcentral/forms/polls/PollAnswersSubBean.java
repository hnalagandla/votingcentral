/*
 * Created on Mar 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.polls;

import org.apache.struts.upload.FormFile;

import com.votingcentral.forms.VCBaseFormBean;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollAnswersSubBean extends VCBaseFormBean {
	private String answer;

	private String answerId;

	private String answerImageId;

	private String delImgURL;

	private long answerTotalVotes;

	private FormFile answerImageFile;

	/**
	 * @return Returns the answer.
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer
	 *            The answer to set.
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return Returns the answerId.
	 */
	public String getAnswerId() {
		return answerId;
	}

	/**
	 * @param answerId
	 *            The answerId to set.
	 */
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
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
	 * @return Returns the answerTotalVotes.
	 */
	public long getAnswerTotalVotes() {
		return answerTotalVotes;
	}

	/**
	 * @param answerTotalVotes
	 *            The answerTotalVotes to set.
	 */
	public void setAnswerTotalVotes(long answerTotalVotes) {
		this.answerTotalVotes = answerTotalVotes;
	}

	/**
	 * @return Returns the delImgURL.
	 */
	public String getDelImgURL() {
		return delImgURL;
	}

	/**
	 * @param delImgURL
	 *            The delImgURL to set.
	 */
	public void setDelImgURL(String delImgURL) {
		this.delImgURL = delImgURL;
	}
}