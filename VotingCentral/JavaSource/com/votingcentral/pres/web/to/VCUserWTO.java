/*
 * Created on Jul 29, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.pres.web.to;

import java.util.List;

import com.votingcentral.model.db.dao.to.VCUserTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCUserWTO extends VCUserTO {
	private String minImageUrl;

	private String maxImageUrl;

	private String memberSince;

	private String connectToUrl;

	private List userInfoNameValues;

	private String userProfileUrl;

	private String connectAcceptUrl;

	private String connectRejectUrl;

	private String connectComments;

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
	public void setMinImageUrl(String imageUrl) {
		this.minImageUrl = imageUrl;
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
	 * @return Returns the memberSince.
	 */
	public String getMemberSince() {
		return memberSince;
	}

	/**
	 * @param memberSince
	 *            The memberSince to set.
	 */
	public void setMemberSince(String memberSince) {
		this.memberSince = memberSince;
	}

	/**
	 * @return Returns the connectToUrl.
	 */
	public String getConnectToUrl() {
		return connectToUrl;
	}

	/**
	 * @param connectToUrl
	 *            The connectToUrl to set.
	 */
	public void setConnectToUrl(String connectToUrl) {
		this.connectToUrl = connectToUrl;
	}

	/**
	 * @return Returns the userInfoNameValues.
	 */
	public List getUserInfoNameValues() {
		return userInfoNameValues;
	}

	/**
	 * @param userInfoNameValues
	 *            The userInfoNameValues to set.
	 */
	public void setUserInfoNameValues(List userInfoNameValues) {
		this.userInfoNameValues = userInfoNameValues;
	}

	/**
	 * @return Returns the userProfileUrl.
	 */
	public String getUserProfileUrl() {
		return userProfileUrl;
	}

	/**
	 * @param userProfileUrl
	 *            The userProfileUrl to set.
	 */
	public void setUserProfileUrl(String userProfileUrl) {
		this.userProfileUrl = userProfileUrl;
	}

	/**
	 * @return Returns the connectAcceptUrl.
	 */
	public String getConnectAcceptUrl() {
		return connectAcceptUrl;
	}

	/**
	 * @param connectAcceptUrl
	 *            The connectAcceptUrl to set.
	 */
	public void setConnectAcceptUrl(String connectAcceptUrl) {
		this.connectAcceptUrl = connectAcceptUrl;
	}

	/**
	 * @return Returns the connectRejectUrl.
	 */
	public String getConnectRejectUrl() {
		return connectRejectUrl;
	}

	/**
	 * @param connectRejectUrl
	 *            The connectRejectUrl to set.
	 */
	public void setConnectRejectUrl(String connectRejectUrl) {
		this.connectRejectUrl = connectRejectUrl;
	}

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
	public void setConnectComments(String connectComments) {
		this.connectComments = connectComments;
	}
}