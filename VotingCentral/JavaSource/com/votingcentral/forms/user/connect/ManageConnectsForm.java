/*
 * Created on Aug 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.user.connect;

import java.util.List;

import com.votingcentral.forms.VCBaseFormBean;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ManageConnectsForm extends VCBaseFormBean {
	private List requestsSentPending;

	private List requestsRecievedPending;

	private List friends;

	//used in allconfconnectionsaction
	private String userName;

	/**
	 * @return Returns the friends.
	 */
	public List getFriends() {
		return friends;
	}

	/**
	 * @param friends
	 *            The friends to set.
	 */
	public void setFriends(List friends) {
		this.friends = friends;
	}

	/**
	 * @return Returns the requestsRecievedPending.
	 */
	public List getRequestsRecievedPending() {
		return requestsRecievedPending;
	}

	/**
	 * @param requestsRecievedPending
	 *            The requestsRecievedPending to set.
	 */
	public void setRequestsRecievedPending(List requestsRecievedPending) {
		this.requestsRecievedPending = requestsRecievedPending;
	}

	/**
	 * @return Returns the requestsSentPending.
	 */
	public List getRequestsSentPending() {
		return requestsSentPending;
	}

	/**
	 * @param requestsSentPending
	 *            The requestsSentPending to set.
	 */
	public void setRequestsSentPending(List requestsSentPending) {
		this.requestsSentPending = requestsSentPending;
	}
	
	
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}