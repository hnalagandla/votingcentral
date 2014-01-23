/*
 * Created on Apr 26, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.user;

import com.votingcentral.forms.VCBaseFormBean;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ManageMyPrefsForm extends VCBaseFormBean {

	private String pollStartedEmail;

	/**
	 * @return Returns the pollStartedEmail.
	 */
	public String getPollStartedEmail() {
		return pollStartedEmail;
	}

	/**
	 * @param pollStartedEmail
	 *            The pollStartedEmail to set.
	 */
	public void setPollStartedEmail(String pollStartedEmail) {
		this.pollStartedEmail = pollStartedEmail;
	}
}