/*
 * Created on Jan 27, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.cookies;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.votingcentral.model.polls.PollTimeHelper;

/**
 * @author Gandhari
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

public class VCCookielet {

	private final static String EQUAL = "=";

	private String name;

	private String value;

	private Date expiryTime;

	/**
	 * 
	 *  
	 */
	public VCCookielet() {
	}

	/**
	 * 
	 *  
	 */
	public VCCookielet(String nameAndValueExpiry) {
		setNameValueTimeStampStr(nameAndValueExpiry);
	}

	/**
	 * 
	 * @param name
	 * @param value
	 */
	public VCCookielet(String name, String value, Date expiryTime) {
		this.name = name;
		this.value = value;
		this.expiryTime = expiryTime;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 
	 * @return
	 */
	public String toString() {
		return this.name
				+ VCCookielet.EQUAL
				+ this.value
				+ VCCookielet.EQUAL
				+ (this.expiryTime != null ? PollTimeHelper.getInstance()
						.getTimeString(this.expiryTime.getTime()) : null);
	}

	/**
	 * 
	 * @param val
	 */
	private void setNameValueTimeStampStr(String val) {
		String[] t = StringUtils.split(val, VCCookielet.EQUAL);
		this.name = t[0];
		this.value = t[1];
		try {
			this.expiryTime = PollTimeHelper.getInstance().getDateFormatter()
					.parse(t[2]);
		} catch (ParseException e) {
			//ignore and set that value to current time, to force a login.
			this.expiryTime = PollTimeHelper.getInstance().getCurrentDate();
		}
	}

	/**
	 * @return Returns the expiryTime.
	 */
	public Date getExpiryTime() {
		return expiryTime;
	}

	/**
	 * @param expiryTime
	 *            The expiryTime to set.
	 */
	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}
}