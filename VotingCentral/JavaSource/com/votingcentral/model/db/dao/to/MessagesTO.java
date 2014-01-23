/*
 * Created on Aug 13, 2005
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
public class MessagesTO implements DataTransferObject {

	private String subjectId;

	private String messageId;

	private String message;

	private String responseToId;

	private String creatorLoginName;
	
	private long creatorId;

	private String creatorIPAddress;

	private boolean isFiltered;

	private Date createTimestamp;

	private Date modifyTimestamp;

	private long messageCount;

	private String ipAddress;

	private Date joinDate;

	public MessagesTO() {
		super();
		this.subjectId = "";
		this.messageId = "";
		this.message = "";
		this.responseToId = "";
		this.creatorLoginName = "";
		this.creatorId = 0;
		this.creatorIPAddress = "";
		this.isFiltered = false;
		this.createTimestamp = null;
		this.modifyTimestamp = null;
	}
	
	/**
	 * @return Returns the createTimestamp.
	 */
	public Date getCreateTimestamp() {
		return createTimestamp;
	}
	/**
	 * @param createTimestamp The createTimestamp to set.
	 */
	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}
	/**
	 * @return Returns the creatorIPAddress.
	 */
	public String getCreatorIPAddress() {
		return creatorIPAddress;
	}
	/**
	 * @param creatorIPAddress The creatorIPAddress to set.
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
	 * @param creatorLoginName The creatorLoginName to set.
	 */
	public void setCreatorLoginName(String creatorLoginName) {
		this.creatorLoginName = creatorLoginName;
	}
	/**
	 * @return Returns the ipAddress.
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * @param ipAddress The ipAddress to set.
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * @return Returns the isFiltered.
	 */
	public boolean isFiltered() {
		return isFiltered;
	}
	/**
	 * @param isFiltered The isFiltered to set.
	 */
	public void setFiltered(boolean isFiltered) {
		this.isFiltered = isFiltered;
	}
	/**
	 * @return Returns the joinDate.
	 */
	public Date getJoinDate() {
		return joinDate;
	}
	/**
	 * @param joinDate The joinDate to set.
	 */
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return Returns the messageCount.
	 */
	public long getMessageCount() {
		return messageCount;
	}
	/**
	 * @param messageCount The messageCount to set.
	 */
	public void setMessageCount(long messageCount) {
		this.messageCount = messageCount;
	}
	/**
	 * @return Returns the messageId.
	 */
	public String getMessageId() {
		return messageId;
	}
	/**
	 * @param messageId The messageId to set.
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	/**
	 * @return Returns the modifyTimestamp.
	 */
	public Date getModifyTimestamp() {
		return modifyTimestamp;
	}
	/**
	 * @param modifyTimestamp The modifyTimestamp to set.
	 */
	public void setModifyTimestamp(Date modifyTimestamp) {
		this.modifyTimestamp = modifyTimestamp;
	}
	/**
	 * @return Returns the responseToId.
	 */
	public String getResponseToId() {
		return responseToId;
	}
	/**
	 * @param responseToId The responseToId to set.
	 */
	public void setResponseToId(String responseToId) {
		this.responseToId = responseToId;
	}
	/**
	 * @return Returns the subjectId.
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * @param subjectId The subjectId to set.
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	
	/**
	 * @return Returns the creatorId.
	 */
	public long getCreatorId() {
		return creatorId;
	}
	/**
	 * @param creatorId The creatorId to set.
	 */
	public void setCreatorId(long creatorEmailId) {
		this.creatorId = creatorEmailId;
	}
}