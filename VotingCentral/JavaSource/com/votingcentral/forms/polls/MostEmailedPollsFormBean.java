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
public class MostEmailedPollsFormBean extends VCBaseFormBean {
	private List mostEmailedPolls;
	
	/**
	 * @return Returns the mostEmailedPolls.
	 */
	public List getMostEmailedPolls() {
		return mostEmailedPolls;
	}
	/**
	 * @param mostEmailedPolls The mostEmailedPolls to set.
	 */
	public void setMostEmailedPolls(List mostPopPolls) {
		this.mostEmailedPolls = mostPopPolls;
	}
}