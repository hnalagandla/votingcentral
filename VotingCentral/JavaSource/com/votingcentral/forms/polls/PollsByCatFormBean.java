/*
 * Created on Sep 13, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.polls;

import java.util.List;

import com.votingcentral.forms.VCBaseFormBean;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollsByCatFormBean extends VCBaseFormBean {

	private String categoryName;

	private String nextNumber;

	private String nextUrl;

	private String prevUrl;

	private String lastPollEndTime;

	private String firstPollEndTime;

	private List pollsByCat;

	/**
	 * @return Returns the categoryName.
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *            The categoryName to set.
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return Returns the nextUrl.
	 */
	public String getNextUrl() {
		return nextUrl;
	}

	/**
	 * @param nextUrl
	 *            The nextUrl to set.
	 */
	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}

	/**
	 * @return Returns the nextNumber.
	 */
	public String getNextNumber() {
		return nextNumber;
	}

	/**
	 * @param nextNumber
	 *            The nextNumber to set.
	 */
	public void setNextNumber(String nextNumber) {
		this.nextNumber = nextNumber;
	}

	/**
	 * @return Returns the pollsByCat.
	 */
	public List getPollsByCat() {
		return pollsByCat;
	}

	/**
	 * @param pollsByCat
	 *            The pollsByCat to set.
	 */
	public void setPollsByCat(List pollsByCat) {
		this.pollsByCat = pollsByCat;
	}

	/**
	 * @return Returns the lastPollEndTime.
	 */
	public String getLastPollEndTime() {
		return lastPollEndTime;
	}

	/**
	 * @param lastPollEndTime
	 *            The lastPollEndTime to set.
	 */
	public void setLastPollEndTime(String lastPollEndTime) {
		this.lastPollEndTime = lastPollEndTime;
	}

	/**
	 * @return Returns the prevUrl.
	 */
	public String getPrevUrl() {
		return prevUrl;
	}

	/**
	 * @param prevUrl
	 *            The prevUrl to set.
	 */
	public void setPrevUrl(String prevUrl) {
		this.prevUrl = prevUrl;
	}

	/**
	 * @return Returns the firstPollEndTime.
	 */
	public String getFirstPollEndTime() {
		return firstPollEndTime;
	}

	/**
	 * @param firstPollEndTime
	 *            The firstPollEndTime to set.
	 */
	public void setFirstPollEndTime(String firstPollEndTime) {
		this.firstPollEndTime = firstPollEndTime;
	}
}