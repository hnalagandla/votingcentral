package com.votingcentral.forms.taf;

import com.votingcentral.forms.VCBaseFormBean;

public class TafForm extends VCBaseFormBean {

	private String fromEmail;

	private String[] toEmail;

	private String subject;

	private String body;

	private String fromIPAddress;

	private String copy;

	private String tafUrl;

	private String orignalPageUrl;

	private String type;

	/**
	 * @return Returns the toEmail.
	 */
	public String[] getToEmail() {
		return toEmail;
	}

	/**
	 * @param toEmail
	 *            The toEmail to set.
	 */
	public void setToEmail(String[] toEmail) {
		this.toEmail = toEmail;
	}

	/**
	 * @return Returns the body.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            The body to set.
	 */
	public void setBody(String body) {
		this.body = body;
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
	 * @return Returns the fromIPAddress.
	 */
	public String getFromIPAddress() {
		return fromIPAddress;
	}

	/**
	 * @param fromIPAddress
	 *            The fromIPAddress to set.
	 */
	public void setFromIPAddress(String fromIPAddress) {
		this.fromIPAddress = fromIPAddress;
	}

	/**
	 * @return Returns the copy.
	 */
	public String getCopy() {
		return copy;
	}

	/**
	 * @param copy
	 *            The copy to set.
	 */
	public void setCopy(String copy) {
		this.copy = copy;
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
	 * @return Returns the fromEmail.
	 */
	public String getFromEmail() {
		return fromEmail;
	}

	/**
	 * @param fromEmail
	 *            The fromEmail to set.
	 */
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	/**
	 * @return Returns the orignalPageUrl.
	 */
	public String getOrignalPageUrl() {
		return orignalPageUrl;
	}

	/**
	 * @param orignalPageUrl
	 *            The orignalPageUrl to set.
	 */
	public void setOrignalPageUrl(String tafPageUrl) {
		this.orignalPageUrl = tafPageUrl;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String tafType) {
		this.type = tafType;
	}
}