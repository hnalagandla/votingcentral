/*
 * Created on Aug 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.votingcentral.model.polls;

import java.util.List;

/**
 * @author harishn
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QuestionData {

	private String question;

	private String questionId;

	private long questionTotalVotes;

	private String answerType;

	private String questionImageId;

	private String minImageUrl;

	private String maxImageUrl;

	private int imageOriginalH;

	private int imageOriginalW;

	private List answerChoices;

	/**
	 * @return
	 */
	public List getAnswerChoices() {
		return answerChoices;
	}

	/**
	 * @return
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @return
	 */
	public String getQuestionId() {
		return questionId;
	}

	/**
	 * @return
	 */
	public long getQuestionTotalVotes() {
		return questionTotalVotes;
	}

	/**
	 * @return Returns the answerType.
	 */
	public String getAnswerType() {
		return answerType;
	}

	/**
	 * @param answerType
	 *            The answerType to set.
	 */
	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}

	/**
	 * @param list
	 */
	public void setAnswerChoices(List list) {
		answerChoices = list;
	}

	/**
	 * @param string
	 */
	public void setQuestion(String string) {
		question = string;
	}

	/**
	 * @param string
	 */
	public void setQuestionId(String string) {
		questionId = string;
	}

	/**
	 * @param l
	 */
	public void setQuestionTotalVotes(long l) {
		questionTotalVotes = l;
	}

	/**
	 * @return Returns the questionImageId.
	 */
	public String getQuestionImageId() {
		return questionImageId;
	}

	/**
	 * @param questionImageId
	 *            The questionImageId to set.
	 */
	public void setQuestionImageId(String questionImageId) {
		this.questionImageId = questionImageId;
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