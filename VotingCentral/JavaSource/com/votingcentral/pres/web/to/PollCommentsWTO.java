/*
 * Created on Apr 16, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.pres.web.to;

import com.votingcentral.model.db.dao.to.PollCommentsTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollCommentsWTO extends PollCommentsTO {
	private String userLink;

	/**
	 * @return Returns the userLink.
	 */
	public String getUserLink() {
		return userLink;
	}

	/**
	 * @param userLink
	 *            The userLink to set.
	 */
	public void setUserLink(String userLink) {
		this.userLink = userLink;
	}
}