/*
 * Created on Aug 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.util.Date;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PersonalConfigTO {

	private long userId;

	private String userName;

	private String securityQuestion;

	private String emailConfCode;

	private String securityAnswer;

	private String encryptedPassword;

	private String tempPassword;

	private Date createTimestamp;

	private Date modifyTimestamp;

	public PersonalConfigTO() {

	}

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
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String emailAddress) {
		this.userName = emailAddress;
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
	 * @return Returns the emailConfCode.
	 */
	public String getEmailConfCode() {
		return emailConfCode;
	}

	/**
	 * @param emailConfCode
	 *            The emailConfCode to set.
	 */
	public void setEmailConfCode(String emailConfCode) {
		this.emailConfCode = emailConfCode;
	}

	/**
	 * @return Returns the encryptedPassword.
	 */
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	/**
	 * @param encryptedPassword
	 *            The encryptedPassword to set.
	 */
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
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
	 * @return Returns the securityAnswer.
	 */
	public String getSecurityAnswer() {
		return securityAnswer;
	}

	/**
	 * @param securityAnswer
	 *            The securityAnswer to set.
	 */
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	/**
	 * @return Returns the securityQuestion.
	 */
	public String getSecurityQuestion() {
		return securityQuestion;
	}

	/**
	 * @param securityQuestion
	 *            The securityQuestion to set.
	 */
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	/**
	 * @return Returns the tempPassword.
	 */
	public String getTempPassword() {
		return tempPassword;
	}

	/**
	 * @param tempPassword
	 *            The tempPassword to set.
	 */
	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

}