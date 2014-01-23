/*
 * Created on Jan 16, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.initialize;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCInitializer {

	private final static VCInitializer initializer = new VCInitializer();

	private static Log log = LogFactory.getLog(VCInitializer.class);

	private VCInitializer() {

	}

	public static VCInitializer getInstance() {
		return initializer;
	}

	public void init() {
		initializeEmailSender();
		initializeContests();
		initializePollEndEmail();
		initializeVCVacoWinner();
		initializePollStartedEmail();
		initializeUnfinishedPollEmail();
	}

	private void initializeContests() {
		ContestsInitializer contestInit = new ContestsInitializer();
		contestInit.init();
	}

	private void initializePollEndEmail() {
		PollEndedEmailInitializer initializer = new PollEndedEmailInitializer();
		initializer.init();
	}

	private void initializePollStartedEmail() {
		PollStartedEmailInitializer initializer = new PollStartedEmailInitializer();
		initializer.init();
	}

	private void initializeEmailSender() {
		EmailSenderInitializer initializer = new EmailSenderInitializer();
		initializer.init();
	}

	private void initializeVCVacoWinner() {
		VCVacoWinnerInitializer initializer = new VCVacoWinnerInitializer();
		initializer.init();
	}

	private void initializeUnfinishedPollEmail() {
		UnfinishedPollEmailInitializer initializer = new UnfinishedPollEmailInitializer();
		initializer.init();
	}
}