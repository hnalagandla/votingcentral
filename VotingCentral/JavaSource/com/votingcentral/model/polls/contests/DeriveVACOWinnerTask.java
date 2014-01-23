/*
 * Created on Jan 29, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.polls.contests;

import java.sql.SQLException;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.bo.vcwinners.VCWinnersBO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DeriveVACOWinnerTask extends TimerTask {

	private final static Log log = LogFactory
			.getLog(DeriveVACOWinnerTask.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			VCWinnersBO.getInstance().checkAndCreatePreviousPeriodWinner();
		} catch (SQLException e) {
			log.error("Error deriving the VACO Winner", e);
		}
	}
}