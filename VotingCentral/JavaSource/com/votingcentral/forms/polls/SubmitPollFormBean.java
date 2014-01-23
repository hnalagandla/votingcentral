/*
 * Created on Mar 28, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.polls;

import com.votingcentral.forms.VCBaseFormBean;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SubmitPollFormBean extends VCBaseFormBean {
	private String pollId;

	private String displayPollUrl;

	private String myVCUrl;

	private String homePageUrl;

	private String createMorePollsUrl;

	private String contestsMainUrl;

	private String contestsUploadUrl;

	private String twitterUpdateUrl;

	private String pollName;

	private String yahooBuzzUpUrl;
	
	private String facebookShareUrl;

	/**
	 * @return Returns the contestsMainUrl.
	 */
	public String getContestsMainUrl() {
		return contestsMainUrl;
	}

	/**
	 * @param contestsMainUrl
	 *            The contestsMainUrl to set.
	 */
	public void setContestsMainUrl(String contestsMainUrl) {
		this.contestsMainUrl = contestsMainUrl;
	}

	/**
	 * @return Returns the contestsUploadUrl.
	 */
	public String getContestsUploadUrl() {
		return contestsUploadUrl;
	}

	/**
	 * @param contestsUploadUrl
	 *            The contestsUploadUrl to set.
	 */
	public void setContestsUploadUrl(String contestsUploadUrl) {
		this.contestsUploadUrl = contestsUploadUrl;
	}

	/**
	 * @return Returns the createMorePollsUrl.
	 */
	public String getCreateMorePollsUrl() {
		return createMorePollsUrl;
	}

	/**
	 * @param createMorePollsUrl
	 *            The createMorePollsUrl to set.
	 */
	public void setCreateMorePollsUrl(String createMorePollsUrl) {
		this.createMorePollsUrl = createMorePollsUrl;
	}

	/**
	 * @return Returns the homePageUrl.
	 */
	public String getHomePageUrl() {
		return homePageUrl;
	}

	/**
	 * @param homePageUrl
	 *            The homePageUrl to set.
	 */
	public void setHomePageUrl(String homePageUrl) {
		this.homePageUrl = homePageUrl;
	}

	/**
	 * @return Returns the myVCUrl.
	 */
	public String getMyVCUrl() {
		return myVCUrl;
	}

	/**
	 * @param myVCUrl
	 *            The myVCUrl to set.
	 */
	public void setMyVCUrl(String myVCUrl) {
		this.myVCUrl = myVCUrl;
	}

	/**
	 * @return Returns the displayPollUrl.
	 */
	public String getDisplayPollUrl() {
		return displayPollUrl;
	}

	/**
	 * @param displayPollUrl
	 *            The displayPollUrl to set.
	 */
	public void setDisplayPollUrl(String displayPollUrl) {
		this.displayPollUrl = displayPollUrl;
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
	 * @return Returns the pollName.
	 */
	public String getPollName() {
		return pollName;
	}

	/**
	 * @param pollName
	 *            The pollName to set.
	 */
	public void setPollName(String pollName) {
		this.pollName = pollName;
	}

	/**
	 * @return Returns the twitterUpdateUrl.
	 */
	public String getTwitterUpdateUrl() {
		return twitterUpdateUrl;
	}

	/**
	 * @param twitterUpdateUrl
	 *            The twitterUpdateUrl to set.
	 */
	public void setTwitterUpdateUrl(String twitterUpdateUrl) {
		this.twitterUpdateUrl = twitterUpdateUrl;
	}

	/**
	 * @return Returns the yahooBuzzUpUrl.
	 */
	public String getYahooBuzzUpUrl() {
		return yahooBuzzUpUrl;
	}

	/**
	 * @param yahooBuzzUpUrl
	 *            The yahooBuzzUpUrl to set.
	 */
	public void setYahooBuzzUpUrl(String yahooBuzzUpUrl) {
		this.yahooBuzzUpUrl = yahooBuzzUpUrl;
	}
	
	
	/**
	 * @return Returns the facebookShareUrl.
	 */
	public String getFacebookShareUrl() {
		return facebookShareUrl;
	}
	/**
	 * @param facebookShareUrl The facebookShareUrl to set.
	 */
	public void setFacebookShareUrl(String facebookShareUrl) {
		this.facebookShareUrl = facebookShareUrl;
	}
}