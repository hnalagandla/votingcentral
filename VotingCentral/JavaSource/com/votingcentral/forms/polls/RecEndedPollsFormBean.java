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
public class RecEndedPollsFormBean extends VCBaseFormBean {
	private List recEndedPolls;
	
	
	/**
	 * @return Returns the recEndedPolls.
	 */
	public List getRecEndedPolls() {
		return recEndedPolls;
	}
	/**
	 * @param recEndedPolls The recEndedPolls to set.
	 */
	public void setRecEndedPolls(List recEndedPolls) {
		this.recEndedPolls = recEndedPolls;
	}
}
