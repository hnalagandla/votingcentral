/*
 * Created on May 22, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.util.Date;

import com.votingcentral.model.enums.VCUserLinkStateEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCUserLinksTO {
	private long linkId;

	private long sourceUserId;

	private String sourceUserEmail;

	private String sourceUserName;

	private long destUserId;

	private String destUserEmail;

	private String destUserName;

	private VCUserLinkStateEnum linkStateEnum;

	private Date createTimestamp;

	private Date modifyTimestamp;

	private String linkComments;

	private String acceptCode;

	private String rejectCode;

	/**
	 * @return Returns the acceptCode.
	 */
	public String getAcceptCode() {
		return acceptCode;
	}

	/**
	 * @param acceptCode
	 *            The acceptCode to set.
	 */
	public void setAcceptCode(String acceptId) {
		this.acceptCode = acceptId;
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
	 * @return Returns the destUserEmail.
	 */
	public String getDestUserEmail() {
		return destUserEmail;
	}

	/**
	 * @param destUserEmail
	 *            The destUserEmail to set.
	 */
	public void setDestUserEmail(String destUserEmail) {
		this.destUserEmail = destUserEmail;
	}

	/**
	 * @return Returns the destUserId.
	 */
	public long getDestUserId() {
		return destUserId;
	}

	/**
	 * @param destUserId
	 *            The destUserId to set.
	 */
	public void setDestUserId(long destUserId) {
		this.destUserId = destUserId;
	}

	/**
	 * @return Returns the destUserName.
	 */
	public String getDestUserName() {
		return destUserName;
	}

	/**
	 * @param destUserName
	 *            The destUserName to set.
	 */
	public void setDestUserName(String destUserName) {
		this.destUserName = destUserName;
	}

	/**
	 * @return Returns the linkComments.
	 */
	public String getLinkComments() {
		return linkComments;
	}

	/**
	 * @param linkComments
	 *            The linkComments to set.
	 */
	public void setLinkComments(String linkComments) {
		this.linkComments = linkComments;
	}

	/**
	 * @return Returns the linkId.
	 */
	public long getLinkId() {
		return linkId;
	}

	/**
	 * @param linkId
	 *            The linkId to set.
	 */
	public void setLinkId(long linkId) {
		this.linkId = linkId;
	}

	/**
	 * @return Returns the linkStateEnum.
	 */
	public VCUserLinkStateEnum getLinkStateEnum() {
		return linkStateEnum;
	}

	/**
	 * @param linkStateEnum
	 *            The linkStateEnum to set.
	 */
	public void setLinkStateEnum(VCUserLinkStateEnum linkStateEnum) {
		this.linkStateEnum = linkStateEnum;
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
	 * @return Returns the rejectCode.
	 */
	public String getRejectCode() {
		return rejectCode;
	}

	/**
	 * @param rejectCode
	 *            The rejectCode to set.
	 */
	public void setRejectCode(String rejectId) {
		this.rejectCode = rejectId;
	}

	/**
	 * @return Returns the sourceUserEmail.
	 */
	public String getSourceUserEmail() {
		return sourceUserEmail;
	}

	/**
	 * @param sourceUserEmail
	 *            The sourceUserEmail to set.
	 */
	public void setSourceUserEmail(String sourceUserEmail) {
		this.sourceUserEmail = sourceUserEmail;
	}

	/**
	 * @return Returns the sourceUserId.
	 */
	public long getSourceUserId() {
		return sourceUserId;
	}

	/**
	 * @param sourceUserId
	 *            The sourceUserId to set.
	 */
	public void setSourceUserId(long sourceUserId) {
		this.sourceUserId = sourceUserId;
	}

	/**
	 * @return Returns the sourceUserName.
	 */
	public String getSourceUserName() {
		return sourceUserName;
	}

	/**
	 * @param sourceUserName
	 *            The sourceUserName to set.
	 */
	public void setSourceUserName(String sourceUserName) {
		this.sourceUserName = sourceUserName;
	}
}