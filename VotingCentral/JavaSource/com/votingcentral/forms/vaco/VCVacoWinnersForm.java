/*
 * Created on Aug 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.vaco;

import java.util.List;

import com.votingcentral.forms.VCBaseFormBean;
import com.votingcentral.pres.web.to.VCUserWTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCVacoWinnersForm extends VCBaseFormBean {
	private VCUserWTO currWinner;

	private List prevWinners;

	/**
	 * @return Returns the currWinner.
	 */
	public VCUserWTO getCurrWinner() {
		return currWinner;
	}

	/**
	 * @param currWinner
	 *            The currWinner to set.
	 */
	public void setCurrWinner(VCUserWTO currWinner) {
		this.currWinner = currWinner;
	}

	/**
	 * @return Returns the prevWinners.
	 */
	public List getPrevWinners() {
		return prevWinners;
	}

	/**
	 * @param prevWinners
	 *            The prevWinners to set.
	 */
	public void setPrevWinners(List prevWinners) {
		this.prevWinners = prevWinners;
	}
}