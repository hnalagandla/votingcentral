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
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RecStartedPollsFormBean extends VCBaseFormBean {
	private List recStartedPolls;
	
	
	/**
	 * @return Returns the recStartedPolls.
	 */
	public List getRecStartedPolls() {
		return recStartedPolls;
	}
	/**
	 * @param recStartedPolls The recStartedPolls to set.
	 */
	public void setRecStartedPolls(List recEndedPolls) {
		this.recStartedPolls = recEndedPolls;
	}
}
