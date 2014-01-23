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
import com.votingcentral.model.polls.contests.EmailSenderTask;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EmailSenderInitializer implements Initializer {

	private static final long sendEmailInterval = Long.parseLong(EnvProps
			.getProperty("send.email.interval.millis"));

	private Timer emailSender = null;

	private final static Log log = LogFactory
			.getLog(EmailSenderInitializer.class);

	public EmailSenderInitializer() {
	}

	public void init() {
		emailSender = new Timer();
		EmailSenderTask emailSenderTask = new EmailSenderTask();
		emailSenderTask.run();
		emailSender.schedule(emailSenderTask, sendEmailInterval,
				sendEmailInterval);

	}
}