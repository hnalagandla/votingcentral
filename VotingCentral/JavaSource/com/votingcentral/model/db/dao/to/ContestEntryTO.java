/*
 * Created on Sep 25, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.util.Date;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ContestEntryTO {
	private String fileId;

	private long userId;

	private String pollId;

	private String userComments;

	private String keywords;

	private String contestType;

	private String fileStatus;

	private String minImageUrl;

	private String maxImageUrl;

	private int imageOriginalH;

	private int imageOriginalW;

	private Date createTimestamp;

	private Date modifyTimestamp;

	/**
	 * @return Returns the contestType.
	 */
	public String getContestType() {
		return contestType;
	}

	/**
	 * @param contestType
	 *            The contestType to set.
	 */
	public void setContestType(String contestType) {
		this.contestType = contestType;
	}

	/**
	 * @return Returns the createTimestamp.
	 */
	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	/**
	 * @param createTimestamp
	 *            The createTimestamp to set.
	 */
	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	/**
	 * @return Returns the userId.
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            The userId to set.
	 */
	public void setUserId(long emailAddressId) {
		this.userId = emailAddressId;
	}

	/**
	 * @return Returns the fileId.
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId
	 *            The fileId to set.
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return Returns the fileStatus.
	 */
	public String getFileStatus() {
		return fileStatus;
	}

	/**
	 * @param fileStatus
	 *            The fileStatus to set.
	 */
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	/**
	 * @return Returns the modifyTimestamp.
	 */
	public Date getModifyTimestamp() {
		return modifyTimestamp;
	}

	/**
	 * @param modifyTimestamp
	 *            The modifyTimestamp to set.
	 */
	public void setModifyTimestamp(Date modifyTimestamp) {
		this.modifyTimestamp = modifyTimestamp;
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
	 * @return Returns the userComments.
	 */
	public String getUserComments() {
		return userComments;
	}

	/**
	 * @param userComments
	 *            The userComments to set.
	 */
	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}

	/**
	 * @return Returns the keywords.
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords
	 *            The keywords to set.
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return the imageOriginalH
	 */
	public int getImageOriginalH() {
		return imageOriginalH;
	}

	/**
	 * @param imageOriginalH
	 *            the imageOriginalH to set
	 */
	public void setImageOriginalH(int imageOriginalH) {
		this.imageOriginalH = imageOriginalH;
	}

	/**
	 * @return the imageOriginalW
	 */
	public int getImageOriginalW() {
		return imageOriginalW;
	}

	/**
	 * @param imageOriginalW
	 *            the imageOriginalW to set
	 */
	public void setImageOriginalW(int imageOriginalW) {
		this.imageOriginalW = imageOriginalW;
	}

	/**
	 * @return the maxImageUrl
	 */
	public String getMaxImageUrl() {
		return maxImageUrl;
	}

	/**
	 * @param maxImageUrl
	 *            the maxImageUrl to set
	 */
	public void setMaxImageUrl(String maxImageUrl) {
		this.maxImageUrl = maxImageUrl;
	}

	/**
	 * @return the minImageUrl
	 */
	public String getMinImageUrl() {
		return minImageUrl;
	}

	/**
	 * @param minImageUrl
	 *            the minImageUrl to set
	 */
	public void setMinImageUrl(String minImageUrl) {
		this.minImageUrl = minImageUrl;
	}

}