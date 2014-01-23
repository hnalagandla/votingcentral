/*
 * Created on May 17, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.initialize;

import java.util.Timer;
import java.util.TimerTask;

import com.votingcentral.env.EnvProps;
import com.votingcentral.model.polls.PollHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class UnfinishedPollEmailInitializer implements Initializer {
	private final static long refreshInterval = Long.parseLong(EnvProps
			.getProperty("polls.unfinished.poll.email.sender.interval.millis"));

	private Timer emailSender = null;

	public UnfinishedPollEmailInitializer() {
	}

	public void init() {
		emailSender = new Timer();
		TimerTask emailSenderTask = new TimerTask() {
			public void run() {
				PollHelper.checkAndSendUnfinishedPollEmails();
			}
		};
		emailSenderTask.run();
		emailSender.schedule(emailSenderTask, refreshInterval, refreshInterval);
	}
}