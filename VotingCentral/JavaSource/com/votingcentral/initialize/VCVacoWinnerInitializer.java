/*
 * Created on Jan 29, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.initialize;

import java.util.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.env.EnvProps;
import com.votingcentral.model.polls.contests.DeriveVACOWinnerTask;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCVacoWinnerInitializer implements Initializer {

	private static final long deriveWinnerInterval = Long.parseLong(EnvProps
			.getProperty("derive.vaco.winner.interval.millis"));

	private Timer deriveWinner = null;

	private final static Log log = LogFactory
			.getLog(VCVacoWinnerInitializer.class);

	public VCVacoWinnerInitializer() {
	}

	public void init() {
		deriveWinner = new Timer();
		DeriveVACOWinnerTask deriveWinnerTask = new DeriveVACOWinnerTask();
		deriveWinnerTask.run();
		deriveWinner.schedule(deriveWinnerTask, deriveWinnerInterval,
				deriveWinnerInterval);

	}
}