/*
 * Created on Aug 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.vaco;

import java.util.List;

import com.votingcentral.forms.VCBaseFormBean;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCVacoLeadersForm extends VCBaseFormBean {
	private List currLeaders;

	/**
	 * @return Returns the currLeaders.
	 */
	public List getCurrLeaders() {
		return currLeaders;
	}

	/**
	 * @param currLeaders
	 *            The currLeaders to set.
	 */
	public void setCurrLeaders(List prevWinners) {
		this.currLeaders = prevWinners;
	}
}