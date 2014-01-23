/*
 * Created on Sep 10, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms;

import java.util.List;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class HomePageFormBean extends VCBaseFormBean {

	private List recEndedPolls;

	private String recEndedMoreUrl;

	private List recStartedPolls;

	private String recStartedMoreUrl;

	private List mostVotedPolls;

	private String mostVotedMoreUrl;

	private List mostEmailedPolls;

	private String mostEmailedMoreUrl;

	private String tafHomePageUrl;

	private List featuredPolls;

	// data coming in on submit from the featured poll.
	private String pollId;

	private String questionId;

	private String answerId;

	private List mostRecentUsers;

	private String mostRecentUsersMoreUrl;

	private List mostActiveUsers;

	private String mostActiveUsersMoreUrl;

	/**
	 * @return Returns the mostVotedPolls.
	 */
	public List getMostVotedPolls() {
		return mostVotedPolls;
	}

	/**
	 * @param mostVotedPolls
	 *            The mostVotedPolls to set.
	 */
	public void setMostVotedPolls(List mostPopPolls) {
		this.mostVotedPolls = mostPopPolls;
	}

	/**
	 * @return Returns the recEndedPolls.
	 */
	public List getRecEndedPolls() {
		return recEndedPolls;
	}

	/**
	 * @param recEndedPolls
	 *            The recEndedPolls to set.
	 */
	public void setRecEndedPolls(List recEndedPolls) {
		this.recEndedPolls = recEndedPolls;
	}

	/**
	 * @return the recStartedPolls
	 */
	public List getRecStartedPolls() {
		return recStartedPolls;
	}

	/**
	 * @param recStartedPolls
	 *            the recStartedPolls to set
	 */
	public void setRecStartedPolls(List recStartedPolls) {
		this.recStartedPolls = recStartedPolls;
	}

	/**
	 * @return Returns the tafHomePageUrl.
	 */
	public String getTafHomePageUrl() {
		return tafHomePageUrl;
	}

	/**
	 * @param tafHomePageUrl
	 *            The tafHomePageUrl to set.
	 */
	public void setTafHomePageUrl(String tafHomePageUrl) {
		this.tafHomePageUrl = tafHomePageUrl;
	}

	/**
	 * @return Returns the answerId.
	 */
	public String getAnswerId() {
		return answerId;
	}

	/**
	 * @param answerId
	 *            The answerId to set.
	 */
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
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
	 * @return Returns the questionId.
	 */
	public String getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId
	 *            The questionId to set.
	 */
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return Returns the mostEmailedPolls.
	 */
	public List getMostEmailedPolls() {
		return mostEmailedPolls;
	}

	/**
	 * @param mostEmailedPolls
	 *            The mostEmailedPolls to set.
	 */
	public void setMostEmailedPolls(List mostEmailedPolls) {
		this.mostEmailedPolls = mostEmailedPolls;
	}

	/**
	 * @return the featuredPolls
	 */
	public List getFeaturedPolls() {
		return featuredPolls;
	}

	/**
	 * @param featuredPolls
	 *            the featuredPolls to set
	 */
	public void setFeaturedPolls(List featuredPolls) {
		this.featuredPolls = featuredPolls;
	}

	/**
	 * @return Returns the mostEmailedMoreUrl.
	 */
	public String getMostEmailedMoreUrl() {
		return mostEmailedMoreUrl;
	}

	/**
	 * @param mostEmailedMoreUrl
	 *            The mostEmailedMoreUrl to set.
	 */
	public void setMostEmailedMoreUrl(String mostEmailedMoreUrl) {
		this.mostEmailedMoreUrl = mostEmailedMoreUrl;
	}

	/**
	 * @return Returns the mostVotedMoreUrl.
	 */
	public String getMostVotedMoreUrl() {
		return mostVotedMoreUrl;
	}

	/**
	 * @param mostVotedMoreUrl
	 *            The mostVotedMoreUrl to set.
	 */
	public void setMostVotedMoreUrl(String mostVotedMoreUrl) {
		this.mostVotedMoreUrl = mostVotedMoreUrl;
	}

	/**
	 * @return Returns the recEndedMoreUrl.
	 */
	public String getRecEndedMoreUrl() {
		return recEndedMoreUrl;
	}

	/**
	 * @param recEndedMoreUrl
	 *            The recEndedMoreUrl to set.
	 */
	public void setRecEndedMoreUrl(String recEndedMoreUrl) {
		this.recEndedMoreUrl = recEndedMoreUrl;
	}

	/**
	 * @return Returns the recStartedMoreUrl.
	 */
	public String getRecStartedMoreUrl() {
		return recStartedMoreUrl;
	}

	/**
	 * @param recStartedMoreUrl
	 *            The recStartedMoreUrl to set.
	 */
	public void setRecStartedMoreUrl(String recStartedMoreUrl) {
		this.recStartedMoreUrl = recStartedMoreUrl;
	}

	/**
	 * @return Returns the mostActiveUsers.
	 */
	public List getMostActiveUsers() {
		return mostActiveUsers;
	}

	/**
	 * @param mostActiveUsers
	 *            The mostActiveUsers to set.
	 */
	public void setMostActiveUsers(List mostActiveUsers) {
		this.mostActiveUsers = mostActiveUsers;
	}

	/**
	 * @return Returns the mostRecentUsers.
	 */
	public List getMostRecentUsers() {
		return mostRecentUsers;
	}

	/**
	 * @param mostRecentUsers
	 *            The mostRecentUsers to set.
	 */
	public void setMostRecentUsers(List mostRecentUsers) {
		this.mostRecentUsers = mostRecentUsers;
	}

	/**
	 * @return Returns the mostActiveUsersMoreUrl.
	 */
	public String getMostActiveUsersMoreUrl() {
		return mostActiveUsersMoreUrl;
	}

	/**
	 * @param mostActiveUsersMoreUrl
	 *            The mostActiveUsersMoreUrl to set.
	 */
	public void setMostActiveUsersMoreUrl(String mostActiveUsersMoreUrl) {
		this.mostActiveUsersMoreUrl = mostActiveUsersMoreUrl;
	}

	/**
	 * @return Returns the mostRecentUsersMoreUrl.
	 */
	public String getMostRecentUsersMoreUrl() {
		return mostRecentUsersMoreUrl;
	}

	/**
	 * @param mostRecentUsersMoreUrl
	 *            The mostRecentUsersMoreUrl to set.
	 */
	public void setMostRecentUsersMoreUrl(String mostRecentUsersMoreUrl) {
		this.mostRecentUsersMoreUrl = mostRecentUsersMoreUrl;
	}
}