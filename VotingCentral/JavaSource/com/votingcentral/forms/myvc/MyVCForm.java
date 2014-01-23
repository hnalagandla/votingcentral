/*
 * Created on Feb 18, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.myvc;

import java.util.List;

import com.votingcentral.forms.VCBaseFormBean;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MyVCForm extends VCBaseFormBean {

	private List pollsVoted;

	private List pollsCreated;

	private List mbPolls;

	private List pollComments;

	private List contestFiles;

	private String editAccountInfoUrl;

	/**
	 * @return Returns the contestFiles.
	 */
	public List getContestFiles() {
		return contestFiles;
	}

	/**
	 * @param contestFiles
	 *            The contestFiles to set.
	 */
	public void setContestFiles(List contestFiles) {
		this.contestFiles = contestFiles;
	}

	/**
	 * @return Returns the mbPolls.
	 */
	public List getMbPolls() {
		return mbPolls;
	}

	/**
	 * @param mbPolls
	 *            The mbPolls to set.
	 */
	public void setMbPolls(List mbPolls) {
		this.mbPolls = mbPolls;
	}

	/**
	 * @return Returns the pollsCreated.
	 */
	public List getPollsCreated() {
		return pollsCreated;
	}

	/**
	 * @param pollsCreated
	 *            The pollsCreated to set.
	 */
	public void setPollsCreated(List pollsCreated) {
		this.pollsCreated = pollsCreated;
	}

	/**
	 * @return Returns the pollsVoted.
	 */
	public List getPollsVoted() {
		return pollsVoted;
	}

	/**
	 * @param pollsVoted
	 *            The pollsVoted to set.
	 */
	public void setPollsVoted(List pollsVoted) {
		this.pollsVoted = pollsVoted;
	}

	/**
	 * @return Returns the pollComments.
	 */
	public List getPollComments() {
		return pollComments;
	}

	/**
	 * @param pollComments
	 *            The pollComments to set.
	 */
	public void setPollComments(List pollComments) {
		this.pollComments = pollComments;
	}

	/**
	 * @return Returns the editAccountInfoUrl.
	 */
	public String getEditAccountInfoUrl() {
		return editAccountInfoUrl;
	}

	/**
	 * @param editAccountInfoUrl
	 *            The editAccountInfoUrl to set.
	 */
	public void setEditAccountInfoUrl(String editAccountInfoUrl) {
		this.editAccountInfoUrl = editAccountInfoUrl;
	}
}