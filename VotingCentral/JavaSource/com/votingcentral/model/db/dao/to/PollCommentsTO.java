/*
 * Created on Jul 16, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.util.Date;

import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.util.UnSyncStringBuffer;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollCommentsTO {
	public static String PADDING = "XXXXXXXX";

	private long commentId;

	private String pollId;

	private String comment;

	private String userName;

	private long userId;

	private String creatorIPAddress;

	private boolean isFiltered;

	private Date createTimestamp;

	//only used in the UI
	private String createTimestampStr;

	private Date modifyTimestamp;

	/**
	 * @return Returns the comment.
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            The comment to set.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return Returns the commentId.
	 */
	public long getCommentId() {
		return commentId;
	}

	/**
	 * @param commentId
	 *            The commentId to set.
	 */
	public void setCommentId(long commentId) {
		this.commentId = commentId;
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
	public void setUserId(long creatorEmailId) {
		this.userId = creatorEmailId;
	}

	/**
	 * @return Returns the creatorIPAddress.
	 */
	public String getCreatorIPAddress() {
		return creatorIPAddress;
	}

	/**
	 * @param creatorIPAddress
	 *            The creatorIPAddress to set.
	 */
	public void setCreatorIPAddress(String creatorIPAddress) {
		this.creatorIPAddress = creatorIPAddress;
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String creatorLoginName) {
		this.userName = creatorLoginName;
	}

	/**
	 * @return Returns the isFiltered.
	 */
	public boolean isFiltered() {
		return isFiltered;
	}

	/**
	 * @param isFiltered
	 *            The isFiltered to set.
	 */
	public void setFiltered(boolean isFiltered) {
		this.isFiltered = isFiltered;
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
	 * @return Returns the createTimestampStr.
	 */
	public String getCreateTimestampStr() {
		if (createTimestampStr == null || createTimestampStr.length() == 0) {
			createTimestampStr = PollTimeHelper.getInstance()
					.getDateFormatter().format(createTimestamp);
		}
		return createTimestampStr;
	}

	/**
	 * @param createTimestampStr
	 *            The createTimestampStr to set.
	 */
	public void setCreateTimestampStr(String createTimestampStr) {
		this.createTimestampStr = createTimestampStr;
	}

	private String xOutLoginName(String loginName) {
		int atPos = loginName.indexOf("@");
		UnSyncStringBuffer part = new UnSyncStringBuffer();
		part.append(loginName.substring(0, atPos + 1));
		part.append(PADDING);
		return part.toString();
	}
}