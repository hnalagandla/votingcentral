/*
 * Created on May 26, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.user.connect;

import com.votingcentral.forms.VCBaseFormBean;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RequestConnectForm extends VCBaseFormBean {
	private String connectToUserName;

	private String connectToEmail;

	private String connectComments;

	private boolean buttonDisabled;

	private String code;

	/**
	 * @return Returns the connectComments.
	 */
	public String getConnectComments() {
		return connectComments;
	}

	/**
	 * @param connectComments
	 *            The connectComments to set.
	 */
	public void setConnectComments(String linkComments) {
		this.connectComments = linkComments;
	}

	/**
	 * @return Returns the connectToEmail.
	 */
	public String getConnectToEmail() {
		return connectToEmail;
	}

	/**
	 * @param connectToEmail
	 *            The connectToEmail to set.
	 */
	public void setConnectToEmail(String linkToEmail) {
		this.connectToEmail = linkToEmail;
	}

	/**
	 * @return Returns the connectToUserName.
	 */
	public String getConnectToUserName() {
		return connectToUserName;
	}

	/**
	 * @param connectToUserName
	 *            The connectToUserName to set.
	 */
	public void setConnectToUserName(String linkToUserName) {
		this.connectToUserName = linkToUserName;
	}

	/**
	 * @return Returns the buttonDisabled.
	 */
	public boolean isButtonDisabled() {
		return buttonDisabled;
	}

	/**
	 * @param buttonDisabled
	 *            The buttonDisabled to set.
	 */
	public void setButtonDisabled(boolean buttonDisabled) {
		this.buttonDisabled = buttonDisabled;
	}

	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}
}