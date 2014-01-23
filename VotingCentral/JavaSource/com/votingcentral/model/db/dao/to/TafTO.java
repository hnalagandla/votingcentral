package com.votingcentral.model.db.dao.to;

import java.util.Date;

import com.votingcentral.model.enums.TafTypeEnum;

public class TafTO {

	private long tafRequestId;

	private String senderIpAddress;

	private String senderEmailAddress;

	private String receiverEmailAddress;

	private String tafUrl;

	private TafTypeEnum type;

	private Date tafSentTimestamp;

	/**
	 * @return Returns the receiverEmailAddress.
	 */
	public String getReceiverEmailAddress() {
		return receiverEmailAddress;
	}

	/**
	 * @param receiverEmailAddress
	 *            The receiverEmailAddress to set.
	 */
	public void setReceiverEmailAddress(String receiverEmailAddress) {
		this.receiverEmailAddress = receiverEmailAddress;
	}

	/**
	 * @return Returns the senderEmailAddress.
	 */
	public String getSenderEmailAddress() {
		return senderEmailAddress;
	}

	/**
	 * @param senderEmailAddress
	 *            The senderEmailAddress to set.
	 */
	public void setSenderEmailAddress(String senderEmailAddress) {
		this.senderEmailAddress = senderEmailAddress;
	}

	/**
	 * @return Returns the senderIpAddress.
	 */
	public String getSenderIpAddress() {
		return senderIpAddress;
	}

	/**
	 * @param senderIpAddress
	 *            The senderIpAddress to set.
	 */
	public void setSenderIpAddress(String senderIpAddress) {
		this.senderIpAddress = senderIpAddress;
	}

	/**
	 * @return Returns the tafUrl.
	 */
	public String getTafUrl() {
		return tafUrl;
	}

	/**
	 * @param tafUrl
	 *            The tafUrl to set.
	 */
	public void setTafUrl(String tafUrl) {
		this.tafUrl = tafUrl;
	}

	/**
	 * @return Returns the tafSentTimestamp.
	 */
	public Date getTafSentTimestamp() {
		return tafSentTimestamp;
	}

	/**
	 * @param tafSentTimestamp
	 *            The tafSentTimestamp to set.
	 */
	public void setTafSentTimestamp(Date tafSentTimestamp) {
		this.tafSentTimestamp = tafSentTimestamp;
	}

	/**
	 * @return Returns the type.
	 */
	public TafTypeEnum getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(TafTypeEnum type) {
		this.type = type;
	}

	/**
	 * @return Returns the tafRequestId.
	 */
	public long getTafRequestId() {
		return tafRequestId;
	}

	/**
	 * @param tafRequestId
	 *            The tafRequestId to set.
	 */
	public void setTafRequestId(long tafRequestId) {
		this.tafRequestId = tafRequestId;
	}
}