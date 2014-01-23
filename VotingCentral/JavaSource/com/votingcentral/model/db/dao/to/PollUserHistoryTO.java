/*
 * Created on Aug 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.sql.Timestamp;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollUserHistoryTO {

	private long userId;

	private String pollId;

	private String questionId;

	private String answerId;

	private String answerText;

	private String userIpAddress;

	private String userLocationZip;

	private String userLocationCity;

	private int userLocationStateId;

	private int userLocationCountryId;
	
	private int yearOfBirth;
	
	private String gender;

	private Timestamp createTimestamp;

	private Timestamp modifyTimestamp;

	public PollUserHistoryTO() {

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
	public void setUserId(long loginEmailAddressId) {
		this.userId = loginEmailAddressId;
	}

	/**
	 * @return Returns the pollId.
	 */
	public String getPollId() {
		return pollId;
	}

	/**
	 * @param pollId
	 *            The pollId to set.
	 */
	public void setPollId(String pollId) {
		this.pollId = pollId;
	}

	/**
	 * @return Returns the userIpAddress.
	 */
	public String getUserIpAddress() {
		return userIpAddress;
	}

	/**
	 * @param userIpAddress
	 *            The userIpAddress to set.
	 */
	public void setUserIpAddress(String userIpAddress) {
		this.userIpAddress = userIpAddress;
	}

	/**
	 * @return Returns the userLocationCity.
	 */
	public String getUserLocationCity() {
		return userLocationCity;
	}

	/**
	 * @param userLocationCity
	 *            The userLocationCity to set.
	 */
	public void setUserLocationCity(String userLocationCity) {
		this.userLocationCity = userLocationCity;
	}

	/**
	 * @return Returns the userLocationCountryId.
	 */
	public int getUserLocationCountryId() {
		return userLocationCountryId;
	}

	/**
	 * @param userLocationCountryId
	 *            The userLocationCountryId to set.
	 */
	public void setUserLocationCountryId(int userLocationCountryId) {
		this.userLocationCountryId = userLocationCountryId;
	}

	/**
	 * @return Returns the userLocationStateId.
	 */
	public int getUserLocationStateId() {
		return userLocationStateId;
	}

	/**
	 * @param userLocationStateId
	 *            The userLocationStateId to set.
	 */
	public void setUserLocationStateId(int userLocationStateId) {
		this.userLocationStateId = userLocationStateId;
	}

	/**
	 * @return Returns the userLocationZip.
	 */
	public String getUserLocationZip() {
		return userLocationZip;
	}

	/**
	 * @param userLocationZip
	 *            The userLocationZip to set.
	 */
	public void setUserLocationZip(String userLocationZip) {
		this.userLocationZip = userLocationZip;
	}

	/**
	 * @return Returns the createTimestamp.
	 */
	public Timestamp getCreateTimestamp() {
		return createTimestamp;
	}

	/**
	 * @param createTimestamp
	 *            The createTimestamp to set.
	 */
	public void setCreateTimestamp(Timestamp voteCastedCreateTimestamp) {
		this.createTimestamp = voteCastedCreateTimestamp;
	}

	/**
	 * @return Returns the modifyTimestamp.
	 */
	public Timestamp getModifyTimestamp() {
		return modifyTimestamp;
	}

	/**
	 * @param modifyTimestamp
	 *            The modifyTimestamp to set.
	 */
	public void setModifyTimestamp(Timestamp voteCastedModifyTimestamp) {
		this.modifyTimestamp = voteCastedModifyTimestamp;
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
	 * @return Returns the answerText.
	 */
	public String getAnswerText() {
		return answerText;
	}

	/**
	 * @param answerText
	 *            The answerText to set.
	 */
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	/**
	 * @return Returns the questionId.
	 */
	public String getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId
	 *            The questionId to set.
	 */
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
	/**
	 * @return Returns the gender.
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender The gender to set.
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return Returns the yearOfBirth.
	 */
	public int getYearOfBirth() {
		return yearOfBirth;
	}
	/**
	 * @param yearOfBirth The yearOfBirth to set.
	 */
	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}
}