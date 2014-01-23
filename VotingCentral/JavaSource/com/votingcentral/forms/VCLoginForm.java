/*
 * Created on Dec 13, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCLoginForm extends VCBaseFormBean {

	private String userName;

	private String password;

	private String forgotPswdUrl;

	private String registrationUrl;

	private String loginUrl;

	//login sucess redirect URL.
	private String lsru;

	/**
	 * @return Returns the forgotPswdUrl.
	 */
	public String getForgotPswdUrl() {
		return forgotPswdUrl;
	}

	/**
	 * @param forgotPswdUrl
	 *            The forgotPswdUrl to set.
	 */
	public void setForgotPswdUrl(String forgotPswdUrl) {
		this.forgotPswdUrl = forgotPswdUrl;
	}

	/**
	 * @return Returns the registrationUrl.
	 */
	public String getRegistrationUrl() {
		return registrationUrl;
	}

	/**
	 * @param registrationUrl
	 *            The registrationUrl to set.
	 */
	public void setRegistrationUrl(String registrationUrl) {
		this.registrationUrl = registrationUrl;
	}

	/**
	 * @return Returns the loginUrl.
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * @param loginUrl
	 *            The loginUrl to set.
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
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
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return Returns the lsru.
	 */
	public String getLsru() {
		return lsru;
	}

	/**
	 * @param lsru
	 *            The lsru to set.
	 */
	public void setLsru(String lsru) {
		this.lsru = lsru;
	}
}