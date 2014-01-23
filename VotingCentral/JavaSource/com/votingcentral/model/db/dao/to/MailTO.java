/*
 * Created on Jan 27, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.votingcentral.model.enums.VCEmailTypeEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MailTO {

	private long mailId;

	private VCEmailTypeEnum type;

	private String fromAddress;

	private String toAddress;

	private String ccAddress;

	private String bccAddress;

	private String subject;

	private String replyToAddress;

	private List files;

	private String content;

	private int retryCount;

	private Date startTimestamp;

	private Date successTimestamp;

	private Date lastRetryTimestamp;

	/**
	 * @return Returns the bccAddress.
	 */
	public String getBccAddress() {
		return bccAddress;
	}

	/**
	 * @param bccAddress
	 *            The bccAddress to set.
	 */
	public void setBccAddress(String bccAddress) {
		this.bccAddress = bccAddress;
	}

	/**
	 * @return Returns the ccAddress.
	 */
	public String getCcAddress() {
		return ccAddress;
	}

	/**
	 * @param ccAddress
	 *            The ccAddress to set.
	 */
	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}

	/**
	 * @return Returns the fromAddress.
	 */
	public String getFromAddress() {
		return fromAddress;
	}

	/**
	 * @param fromAddress
	 *            The fromAddress to set.
	 */
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	/**
	 * @return Returns the replyToAddress.
	 */
	public String getReplyToAddress() {
		return replyToAddress;
	}

	/**
	 * @param replyToAddress
	 *            The replyToAddress to set.
	 */
	public void setReplyToAddress(String replyToAddress) {
		this.replyToAddress = replyToAddress;
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
	 * @return Returns the toAddress.
	 */
	public String getToAddress() {
		return toAddress;
	}

	/**
	 * @param toAddress
	 *            The toAddress to set.
	 */
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	/**
	 * @return Returns the content.
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            The content to set.
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return Returns the files.
	 */
	public List getFiles() {
		return files;
	}

	/**
	 * @param files
	 *            The files to set.
	 */
	public void setFiles(List files) {
		this.files = files;
	}

	/**
	 * @return Returns the lastRetryTimestamp.
	 */
	public Date getLastRetryTimestamp() {
		return lastRetryTimestamp;
	}

	/**
	 * @param lastRetryTimestamp
	 *            The lastRetryTimestamp to set.
	 */
	public void setLastRetryTimestamp(Date lastRetryTimestamp) {
		this.lastRetryTimestamp = lastRetryTimestamp;
	}

	/**
	 * @return Returns the mailId.
	 */
	public long getMailId() {
		return mailId;
	}

	/**
	 * @param mailId
	 *            The mailId to set.
	 */
	public void setMailId(long mailId) {
		this.mailId = mailId;
	}

	/**
	 * @return Returns the retryCount.
	 */
	public int getRetryCount() {
		return retryCount;
	}

	/**
	 * @param retryCount
	 *            The retryCount to set.
	 */
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
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
	public void setStartTimestamp(Date startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	/**
	 * @return Returns the successTimestamp.
	 */
	public Date getSuccessTimestamp() {
		return successTimestamp;
	}

	/**
	 * @param successTimestamp
	 *            The successTimestamp to set.
	 */
	public void setSuccessTimestamp(Date successTimestamp) {
		this.successTimestamp = successTimestamp;
	}

	/**
	 * @return Returns the type.
	 */
	public VCEmailTypeEnum getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(VCEmailTypeEnum type) {
		this.type = type;
	}

	public void addAttachment(File file) {
		if (this.files == null)
			this.files = new ArrayList();
		this.files.add(file);
	}

}