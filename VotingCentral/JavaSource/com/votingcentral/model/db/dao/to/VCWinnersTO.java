/*
 * Created on Aug 1, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.util.Date;

import com.votingcentral.model.enums.VCWinTypeCodeEnum;
import com.votingcentral.util.UnSyncStringBuffer;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCWinnersTO {

	private long winId;

	private long userId;

	private VCWinTypeCodeEnum winTypeCode;

	private Date startTimeStamp;

	private Date endTimeStamp;

	private String pollId;

	private Date createTimestamp;

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
	 * @return Returns the endTimeStamp.
	 */
	public Date getEndTimeStamp() {
		return endTimeStamp;
	}

	/**
	 * @param endTimeStamp
	 *            The endTimeStamp to set.
	 */
	public void setEndTimeStamp(Date endTimeStamp) {
		this.endTimeStamp = endTimeStamp;
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
	 * @return Returns the startTimeStamp.
	 */
	public Date getStartTimeStamp() {
		return startTimeStamp;
	}

	/**
	 * @param startTimeStamp
	 *            The startTimeStamp to set.
	 */
	public void setStartTimeStamp(Date startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
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
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return Returns the winId.
	 */
	public long getWinId() {
		return winId;
	}

	/**
	 * @param winId
	 *            The winId to set.
	 */
	public void setWinId(long winnerId) {
		this.winId = winnerId;
	}

	/**
	 * @return Returns the winTypeCode.
	 */
	public VCWinTypeCodeEnum getWinTypeCode() {
		return winTypeCode;
	}

	/**
	 * @param winTypeCode
	 *            The winTypeCode to set.
	 */
	public void setWinTypeCode(VCWinTypeCodeEnum winTypeCode) {
		this.winTypeCode = winTypeCode;
	}
	
	public String toString() {
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append("winId:");
		buffer.append(winId);
		buffer.append("userId:");
		buffer.append(userId);
		buffer.append("winTypeCode:");
		buffer.append(winTypeCode.getName());
		buffer.append("pollId:");
		buffer.append(pollId);
		buffer.append("startDt:");		
		buffer.append(startTimeStamp);
		buffer.append("endDt:");
		buffer.append(endTimeStamp);
		buffer.append("createDt:");
		buffer.append(createTimestamp);		
		return buffer.toString();
	}
}