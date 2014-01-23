/*
 * Created on Apr 21, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.util.Date;

import com.votingcentral.model.enums.VCPrivacyLevelEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCUserPrefsTO {
	private long userId;

	private VCPrivacyLevelEnum pollStartedLevelEnum;

	private Date createTimestamp;

	private Date modifyTimestamp;

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
	 * @return Returns the pollStartedLevelEnum.
	 */
	public VCPrivacyLevelEnum getPollStartedLevelEnum() {
		return pollStartedLevelEnum;
	}

	/**
	 * @param pollStartedLevelEnum
	 *            The pollStartedLevelEnum to set.
	 */
	public void setPollStartedLevelEnum(VCPrivacyLevelEnum pollStartedLevelEnum) {
		this.pollStartedLevelEnum = pollStartedLevelEnum;
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
}