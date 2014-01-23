/*
 * Created on Jan 9, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.search;

import java.util.List;

import com.votingcentral.forms.VCBaseFormBean;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AdvanceSearchFormBean extends VCBaseFormBean {

	private String searchString;

	private long afterMaxId;

	private List results;

	private String resultsCount;

	/**
	 * @return Returns the searchString.
	 */
	public String getSearchString() {
		return searchString;
	}

	/**
	 * @param searchString
	 *            The searchString to set.
	 */
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	/**
	 * @return Returns the afterMaxId.
	 */
	public long getAfterMaxId() {
		return afterMaxId;
	}

	/**
	 * @param afterMaxId
	 *            The afterMaxId to set.
	 */
	public void setAfterMaxId(long afterMaxId) {
		this.afterMaxId = afterMaxId;
	}

	/**
	 * @return Returns the results.
	 */
	public List getResults() {
		return results;
	}

	/**
	 * @param results
	 *            The results to set.
	 */
	public void setResults(List results) {
		this.results = results;
	}

	/**
	 * @return Returns the resultsCount.
	 */
	public String getResultsCount() {
		return resultsCount;
	}

	/**
	 * @param resultsCount
	 *            The resultsCount to set.
	 */
	public void setResultsCount(String resultsCount) {
		this.resultsCount = resultsCount;
	}
}