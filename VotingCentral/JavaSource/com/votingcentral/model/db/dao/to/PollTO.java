/*
 * Created on Aug 12, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.util.Date;

import com.votingcentral.model.enums.PollPriorityEnum;
import com.votingcentral.model.enums.VCPrivacyLevelEnum;
import com.votingcentral.model.enums.PollTypeEnum;
import com.votingcentral.model.enums.PollsStatusEnum;
import com.votingcentral.model.polls.PollData;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollTO {

	private String pollId;

	private String pollRepeatedId;

	private long userId;

	private String pollType;

	private String pollName;

	private String pollDesc;

	private String pollPrivacyLevel;

	private int pollPriority;

	private String keywords;

	private String pollCategory1;

	private String pollCategory2;

	private String pollCategory3;

	private long pollTotalVotes;

	private String pollStatus;

	private PollData pollData;

	private Date createTimestamp;

	private Date modifyTimestamp;

	private Date startTimestamp;

	private Date endTimestamp;

	private Date expireTimestamp;

	private long pollBlockoutPeriodMS;

	private long viewsCount;

	private int pollStartedEmailSentStatus;

	private int unfinishedPollReminderCount;

	private Date ufpReminderLastSentTimestamp;

	public PollTO() {
		pollId = "";

		pollRepeatedId = "";

		userId = 0;

		pollType = PollTypeEnum.POLL;

		pollName = "Default Name";

		pollDesc = "Default Desc";

		pollPrivacyLevel = VCPrivacyLevelEnum.PUBLIC.getName();

		pollPriority = PollPriorityEnum.ONE;

		keywords = "k1, k2";

		pollCategory1 = "cat1";

		pollCategory2 = "cat2";

		pollCategory3 = "cat3";

		pollTotalVotes = 0;

		pollStatus = PollsStatusEnum.UNFINISHED.getName();

		pollData = null;

		createTimestamp = new Date();

		modifyTimestamp = new Date();

		startTimestamp = new Date();

		endTimestamp = new Date();

		expireTimestamp = new Date();

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
	 * @return Returns the pollCategory1.
	 */
	public String getPollCategory1() {
		return pollCategory1;
	}

	/**
	 * @param pollCategory1
	 *            The pollCategory1 to set.
	 */
	public void setPollCategory1(String pollCategory1) {
		this.pollCategory1 = pollCategory1;
	}

	/**
	 * @return Returns the pollCategory2.
	 */
	public String getPollCategory2() {
		return pollCategory2;
	}

	/**
	 * @param pollCategory2
	 *            The pollCategory2 to set.
	 */
	public void setPollCategory2(String pollCategory2) {
		this.pollCategory2 = pollCategory2;
	}

	/**
	 * @return Returns the pollCategory3.
	 */
	public String getPollCategory3() {
		return pollCategory3;
	}

	/**
	 * @param pollCategory3
	 *            The pollCategory3 to set.
	 */
	public void setPollCategory3(String pollCategory3) {
		this.pollCategory3 = pollCategory3;
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
	public void setCreateTimestamp(Date pollCreateTimestamp) {
		this.createTimestamp = pollCreateTimestamp;
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
	public void setUserId(long pollCreatorEmailId) {
		this.userId = pollCreatorEmailId;
	}

	/**
	 * @return Returns the pollData.
	 */
	public PollData getPollData() {
		return pollData;
	}

	/**
	 * @param pollData
	 *            The pollData to set.
	 */
	public void setPollData(PollData pollDataObj) {
		this.pollData = pollDataObj;
	}

	/**
	 * @return Returns the pollDesc.
	 */
	public String getPollDesc() {
		return pollDesc;
	}

	/**
	 * @param pollDesc
	 *            The pollDesc to set.
	 */
	public void setPollDesc(String pollDesc) {
		this.pollDesc = pollDesc;
	}

	/**
	 * @return Returns the endTimestamp.
	 */
	public Date getEndTimestamp() {
		return endTimestamp;
	}

	/**
	 * @param endTimestamp
	 *            The endTimestamp to set.
	 */
	public void setEndTimestamp(Date pollEndTimestamp) {
		this.endTimestamp = pollEndTimestamp;
	}

	/**
	 * @return Returns the expireTimestamp.
	 */
	public Date getExpireTimestamp() {
		return expireTimestamp;
	}

	/**
	 * @param expireTimestamp
	 *            The expireTimestamp to set.
	 */
	public void setExpireTimestamp(Date pollExpireTimestamp) {
		this.expireTimestamp = pollExpireTimestamp;
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
	public void setModifyTimestamp(Date pollModifyTimestamp) {
		this.modifyTimestamp = pollModifyTimestamp;
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
	 * @return Returns the pollPriority.
	 */
	public int getPollPriority() {
		return pollPriority;
	}

	/**
	 * @param pollPriority
	 *            The pollPriority to set.
	 */
	public void setPollPriority(int pollPriority) {
		this.pollPriority = pollPriority;
	}

	/**
	 * @return Returns the pollPrivacyLevel.
	 */
	public String getPollPrivacyLevel() {
		return pollPrivacyLevel;
	}

	/**
	 * @param pollPrivacyLevel
	 *            The pollPrivacyLevel to set.
	 */
	public void setPollPrivacyLevel(String pollPrivacyLevel) {
		this.pollPrivacyLevel = pollPrivacyLevel;
	}

	/**
	 * @return Returns the pollRepeatedId.
	 */
	public String getPollRepeatedId() {
		return pollRepeatedId;
	}

	/**
	 * @param pollRepeatedId
	 *            The pollRepeatedId to set.
	 */
	public void setPollRepeatedId(String pollRepeatedId) {
		this.pollRepeatedId = pollRepeatedId;
	}

	/**
	 * @return Returns the startTimestamp.
	 */
	public Date getStartTimestamp() {
		return startTimestamp;
	}

	/**
	 * @param startTimestamp
	 *            The startTimestamp to set.
	 */
	public void setStartTimestamp(Date pollStartTimestamp) {
		this.startTimestamp = pollStartTimestamp;

	}

	/**
	 * @return Returns the pollStatus.
	 */
	public String getPollStatus() {
		return pollStatus;
	}

	/**
	 * @param pollStatus
	 *            The pollStatus to set.
	 */
	public void setPollStatus(String pollStatus) {
		this.pollStatus = pollStatus;
	}

	/**
	 * @return Returns the pollTotalVotes.
	 */
	public long getPollTotalVotes() {
		return pollTotalVotes;
	}

	/**
	 * @param pollTotalVotes
	 *            The pollTotalVotes to set.
	 */
	public void setPollTotalVotes(long pollTotalVotes) {
		this.pollTotalVotes = pollTotalVotes;
	}

	/**
	 * @return Returns the pollType.
	 */
	public String getPollType() {
		return pollType;
	}

	/**
	 * @param pollType
	 *            The pollType to set.
	 */
	public void setPollType(String pollType) {
		this.pollType = pollType;
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
	 * @return Returns the pollBlockoutPeriodMS.
	 */
	public long getPollBlockoutPeriodMS() {
		return pollBlockoutPeriodMS;
	}

	/**
	 * @param pollBlockoutPeriodMS
	 *            The pollBlockoutPeriodMS to set.
	 */
	public void setPollBlockoutPeriodMS(long pollBlockoutPeriodMS) {
		this.pollBlockoutPeriodMS = pollBlockoutPeriodMS;
	}

	/**
	 * @return Returns the viewsCount.
	 */
	public long getViewsCount() {
		return viewsCount;
	}

	/**
	 * @param viewsCount
	 *            The viewsCount to set.
	 */
	public void setViewsCount(long viewsCount) {
		this.viewsCount = viewsCount;
	}

	/**
	 * @return Returns the pollStartedEmailSentStatus.
	 */
	public int getPollStartedEmailSentStatus() {
		return pollStartedEmailSentStatus;
	}

	/**
	 * @param pollStartedEmailSentStatus
	 *            The pollStartedEmailSentStatus to set.
	 */
	public void setPollStartedEmailSentStatus(int pollStartedEmailSentStatus) {
		this.pollStartedEmailSentStatus = pollStartedEmailSentStatus;
	}

	/**
	 * @return Returns the ufpReminderLastSentTimestamp.
	 */
	public Date getUfpReminderLastSentTimestamp() {
		return ufpReminderLastSentTimestamp;
	}

	/**
	 * @param ufpReminderLastSentTimestamp
	 *            The ufpReminderLastSentTimestamp to set.
	 */
	public void setUfpReminderLastSentTimestamp(
			Date ufpReminderLastSentTimestamp) {
		this.ufpReminderLastSentTimestamp = ufpReminderLastSentTimestamp;
	}

	/**
	 * @return Returns the unfinishedPollReminderCount.
	 */
	public int getUnfinishedPollReminderCount() {
		return unfinishedPollReminderCount;
	}

	/**
	 * @param unfinishedPollReminderCount
	 *            The unfinishedPollReminderCount to set.
	 */
	public void setUnfinishedPollReminderCount(int unfinishedPollReminderCount) {
		this.unfinishedPollReminderCount = unfinishedPollReminderCount;
	}
}