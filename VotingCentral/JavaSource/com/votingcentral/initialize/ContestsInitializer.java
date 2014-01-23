/*
 * Created on Jan 16, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.initialize;

import java.util.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.env.EnvProps;
import com.votingcentral.model.polls.contests.ContestRefreshTask;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ContestsInitializer implements Initializer {

	private static final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;

	private static final long defaultContestRefreshInterval = Long
			.parseLong(EnvProps.getProperty("contest.refresh.interval.millis"));

	private Timer contestRefresher = null;

	private final static Log log = LogFactory.getLog(ContestsInitializer.class);

	public ContestsInitializer() {
	}

	public void init() {
		contestRefresher = new Timer();
		ContestRefreshTask contestRefresherTask = new ContestRefreshTask();
		contestRefresherTask.run();
		contestRefresher.schedule(contestRefresherTask,
				defaultContestRefreshInterval, defaultContestRefreshInterval);

	}
}