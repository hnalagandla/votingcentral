/*
 * Created on Oct 5, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.polls;

import java.util.List;

import com.votingcentral.forms.VCBaseFormBean;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollsCreatedByUserFormBean extends VCBaseFormBean {
	private List allPollsCreated;

	private String userName;

	/**
	 * @return Returns the allPollsCreated.
	 */
	public List getAllPollsCreated() {
		return allPollsCreated;
	}

	/**
	 * @param allPollsCreated
	 *            The allPollsCreated to set.
	 */
	public void setAllPollsCreated(List allPollsCreated) {
		this.allPollsCreated = allPollsCreated;
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
}