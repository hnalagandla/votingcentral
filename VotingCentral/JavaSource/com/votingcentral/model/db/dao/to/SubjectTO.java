/*
 * Created on Aug 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SubjectTO implements DataTransferObject {

	private String pollId;

	private String subjectId;

	private String subject;
	
	private String imageId;

	private String creatorLoginName;

	private long userId;
	
	private String creatorIPAddress;

	private Date createTimeStamp;

	private Collection messages;

	private long msgCount;

	private String lastPostLoginName;

	private Date lastPostTimeStamp;

	public SubjectTO() {
		super();
		this.pollId = "";
		this.subjectId = "";
		this.subject = "";
		this.creatorLoginName = "";
		this.creatorIPAddress = "";
		this.createTimeStamp = null;
		this.messages = null;
		this.msgCount = 0;
		lastPostLoginName = "";
		lastPostTimeStamp = null;
	}

	/**
	 * @return Returns the createTimeStamp.
	 */
	public Date getCreateTimeStamp() {
		return createTimeStamp;
	}

	/**
	 * @param createTimeStamp
	 *            The createTimeStamp to set.
	 */
	public void setCreateTimeStamp(Date createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
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
	 * @return Returns the creatorLoginName.
	 */
	public String getCreatorLoginName() {
		return creatorLoginName;
	}

	/**
	 * @param creatorLoginName
	 *            The creatorLoginName to set.
	 */
	public void setCreatorLoginName(String creatorLoginName) {
		this.creatorLoginName = creatorLoginName;
	}

	/**
	 * @return Returns the imageId.
	 */
	public String getImageId() {
		return imageId;
	}
	/**
	 * @param imageId The imageId to set.
	 */
	public void setImageId(String imageId) {
		this.imageId = imageId;
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
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return Returns the subjectId.
	 */
	public String getSubjectId() {
		return subjectId;
	}

	/**
	 * @param subjectId
	 *            The subjectId to set.
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * @return Returns the messages.
	 */
	public Collection getMessages() {
		return messages;
	}

	/**
	 * @param messages
	 *            The messages to set.
	 */
	public void setMessages(List messages) {
		this.messages = messages;
	}

	/**
	 * @param messages
	 *            The messages to set.
	 */
	public void addMessage(MessagesTO message) {
		if (this.messages == null)
			this.messages = new ArrayList();
		this.messages.add(message);
	}

	/**
	 * @return Returns the lastPostLoginName.
	 */
	public String getLastPostLoginName() {
		return lastPostLoginName;
	}

	/**
	 * @param lastPostLoginName
	 *            The lastPostLoginName to set.
	 */
	public void setLastPostLoginName(String lastPostLoginName) {
		this.lastPostLoginName = lastPostLoginName;
	}

	/**
	 * @return Returns the lastPostTimeStamp.
	 */
	public Date getLastPostTimeStamp() {
		return lastPostTimeStamp;
	}

	/**
	 * @param lastPostTimeStamp
	 *            The lastPostTimeStamp to set.
	 */
	public void setLastPostTimeStamp(Date lastPostTimeStamp) {
		this.lastPostTimeStamp = lastPostTimeStamp;
	}

	/**
	 * @return Returns the msgCount.
	 */
	public long getMsgCount() {
		return msgCount;
	}

	/**
	 * @param msgCount
	 *            The msgCount to set.
	 */
	public void setMsgCount(long msgCount) {
		this.msgCount = msgCount;
	}
	
	/**
	 * @return Returns the userId.
	 */
	public long getUserId() {
		return userId;
	}
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(long creatorEmailId) {
		this.userId = creatorEmailId;
	}
}