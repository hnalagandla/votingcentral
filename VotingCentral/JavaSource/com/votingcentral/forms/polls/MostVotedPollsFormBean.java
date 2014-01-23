/*
 * Created on Oct 5, 2006
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
public class MostVotedPollsFormBean extends VCBaseFormBean {
	private List mostVotedPolls;
	
	/**
	 * @return Returns the mostVotedPolls.
	 */
	public List getMostVotedPolls() {
		return mostVotedPolls;
	}
	/**
	 * @param mostVotedPolls The mostVotedPolls to set.
	 */
	public void setMostVotedPolls(List mostPopPolls) {
		this.mostVotedPolls = mostPopPolls;
	}
}