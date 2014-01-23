/*
 * Created on Mar 19, 2006
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
public class PollBasicsFormBean extends VCBaseFormBean {

	private String pollId;

	private String pollName;

	private String pollDesc;

	private String keywords;

	private String pollCategory1;

	private String pollCategory2;

	private String pollCategory3;

	private List categories;

	/**
	 * @return Returns the categories.
	 */
	public List getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            The categories to set.
	 */
	public void setCategories(List categories) {
		this.categories = categories;
	}

	/**
	 * @return Returns the pollCategory1.
	 */
	public String getPollCategory1() {
		return pollCategory1;
	}

	/**
	 * @param pollCategory1
	 *            The pollCategory1 to set.
	 */
	public void setPollCategory1(String pollCategory1) {
		this.pollCategory1 = pollCategory1;
	}

	/**
	 * @return Returns the pollCategory2.
	 */
	public String getPollCategory2() {
		return pollCategory2;
	}

	/**
	 * @param pollCategory2
	 *            The pollCategory2 to set.
	 */
	public void setPollCategory2(String pollCategory2) {
		this.pollCategory2 = pollCategory2;
	}

	/**
	 * @return Returns the pollCategory3.
	 */
	public String getPollCategory3() {
		return pollCategory3;
	}

	/**
	 * @param pollCategory3
	 *            The pollCategory3 to set.
	 */
	public void setPollCategory3(String pollCategory3) {
		this.pollCategory3 = pollCategory3;
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
	 * @return Returns the keywords.
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords
	 *            The keywords to set.
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return Returns the pollDesc.
	 */
	public String getPollDesc() {
		return pollDesc;
	}

	/**
	 * @param pollDesc
	 *            The pollDesc to set.
	 */
	public void setPollDesc(String pollDesc) {
		this.pollDesc = pollDesc;
	}

	/**
	 * @return Returns the pollName.
	 */
	public String getPollName() {
		return pollName;
	}

	/**
	 * @param pollName
	 *            The pollName to set.
	 */
	public void setPollName(String pollName) {
		this.pollName = pollName;
	}

}