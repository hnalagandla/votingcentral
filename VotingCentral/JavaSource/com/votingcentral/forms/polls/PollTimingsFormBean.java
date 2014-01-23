/*
 * Created on Mar 25, 2006
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
public class PollTimingsFormBean extends VCBaseFormBean {

	private String pollId;

	private String pollStartDate = null;

	private String pollStartTime = null;

	private List pollStartTimes = null;

	private List pollExpireTimes = null;

	private List pollEndTimes = null;

	private List pollBlockOutTimes = null;

	private String pollEndTimestamp = null;

	private String pollExpireTimestamp = null;

	private String msgBrdStartTimestamp = null;

	private String msgBrdEndTimestamp = null;

	private String pollBlockoutPeriodMS = null;

	/**
	 * @return Returns the msgBrdEndTimestamp.
	 */
	public String getMsgBrdEndTimestamp() {
		return msgBrdEndTimestamp;
	}

	/**
	 * @param msgBrdEndTimestamp
	 *            The msgBrdEndTimestamp to set.
	 */
	public void setMsgBrdEndTimestamp(String msgBrdEndTimestamp) {
		this.msgBrdEndTimestamp = msgBrdEndTimestamp;
	}

	/**
	 * @return Returns the msgBrdStartTimestamp.
	 */
	public String getMsgBrdStartTimestamp() {
		return msgBrdStartTimestamp;
	}

	/**
	 * @param msgBrdStartTimestamp
	 *            The msgBrdStartTimestamp to set.
	 */
	public void setMsgBrdStartTimestamp(String msgBrdStartTimestamp) {
		this.msgBrdStartTimestamp = msgBrdStartTimestamp;
	}

	/**
	 * @return Returns the pollEndTimestamp.
	 */
	public String getPollEndTimestamp() {
		return pollEndTimestamp;
	}

	/**
	 * @param pollEndTimestamp
	 *            The pollEndTimestamp to set.
	 */
	public void setPollEndTimestamp(String pollEndTimestamp) {
		this.pollEndTimestamp = pollEndTimestamp;
	}

	/**
	 * @return Returns the pollExpireTimestamp.
	 */
	public String getPollExpireTimestamp() {
		return pollExpireTimestamp;
	}

	/**
	 * @param pollExpireTimestamp
	 *            The pollExpireTimestamp to set.
	 */
	public void setPollExpireTimestamp(String pollExpireTimestamp) {
		this.pollExpireTimestamp = pollExpireTimestamp;
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
	 * @return Returns the pollStartDate.
	 */
	public String getPollStartDate() {
		return pollStartDate;
	}

	/**
	 * @param pollStartDate
	 *            The pollStartDate to set.
	 */
	public void setPollStartDate(String pollStartDate) {
		this.pollStartDate = pollStartDate;
	}

	/**
	 * @return Returns the pollStartTime.
	 */
	public String getPollStartTime() {
		return pollStartTime;
	}

	/**
	 * @param pollStartTime
	 *            The pollStartTime to set.
	 */
	public void setPollStartTime(String pollStartTime) {
		this.pollStartTime = pollStartTime;
	}

	/**
	 * @return Returns the pollStartTimes.
	 */
	public List getPollStartTimes() {
		return pollStartTimes;
	}

	/**
	 * @param pollStartTimes
	 *            The pollStartTimes to set.
	 */
	public void setPollStartTimes(List pollStartTimes) {
		this.pollStartTimes = pollStartTimes;
	}

	/**
	 * @return Returns the pollBlockoutPeriodMS.
	 */
	public String getPollBlockoutPeriodMS() {
		return pollBlockoutPeriodMS;
	}

	/**
	 * @param pollBlockoutPeriodMS
	 *            The pollBlockoutPeriodMS to set.
	 */
	public void setPollBlockoutPeriodMS(String pollBlockoutPeriodMS) {
		this.pollBlockoutPeriodMS = pollBlockoutPeriodMS;
	}

	/**
	 * @return Returns the pollExpireTimes.
	 */
	public List getPollExpireTimes() {
		return pollExpireTimes;
	}

	/**
	 * @param pollExpireTimes
	 *            The pollExpireTimes to set.
	 */
	public void setPollExpireTimes(List pollExpireTimes) {
		this.pollExpireTimes = pollExpireTimes;
	}

	/**
	 * @return Returns the pollEndTimes.
	 */
	public List getPollEndTimes() {
		return pollEndTimes;
	}

	/**
	 * @param pollEndTimes
	 *            The pollEndTimes to set.
	 */
	public void setPollEndTimes(List pollEndTimes) {
		this.pollEndTimes = pollEndTimes;
	}

	/**
	 * @return Returns the pollBlockOutTimes.
	 */
	public List getPollBlockOutTimes() {
		return pollBlockOutTimes;
	}

	/**
	 * @param pollBlockOutTimes
	 *            The pollBlockOutTimes to set.
	 */
	public void setPollBlockOutTimes(List pollBlockOutTimes) {
		this.pollBlockOutTimes = pollBlockOutTimes;
	}
}