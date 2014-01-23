/*
 * Created on Aug 1, 2007
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
public class VCVacoPointsTO {
	private long pointsId;

	private long userId;

	private long points;

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
	 * @return Returns the points.
	 */
	public long getPoints() {
		return points;
	}

	/**
	 * @param points
	 *            The points to set.
	 */
	public void setPoints(long points) {
		this.points = points;
	}

	/**
	 * @return Returns the pointsId.
	 */
	public long getPointsId() {
		return pointsId;
	}

	/**
	 * @param pointsId
	 *            The pointsId to set.
	 */
	public void setPointsId(long pointsId) {
		this.pointsId = pointsId;
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