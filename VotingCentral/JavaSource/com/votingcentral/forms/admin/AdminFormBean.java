/*
 * Created on Aug 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.admin;

import org.apache.struts.action.ActionForm;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AdminFormBean extends ActionForm {

	private String pollId;

	private String userName;

	private String textToTwitter;

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
	 * @return Returns the textToTwitter.
	 */
	public String getTextToTwitter() {
		return textToTwitter;
	}

	/**
	 * @param textToTwitter
	 *            The textToTwitter to set.
	 */
	public void setTextToTwitter(String textToTwitter) {
		this.textToTwitter = textToTwitter;
	}
}