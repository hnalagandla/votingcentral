/*
 * Created on Jan 29, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.polls.contests;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ContestRefreshTask extends TimerTask {

	private ContestPollCreator pollCreator;

	private final static Log log = LogFactory.getLog(ContestRefreshTask.class);

	public ContestRefreshTask() {
		pollCreator = new ContestPollCreator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		pollCreator.refreshPoll();
	}

}