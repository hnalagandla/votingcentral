/*
 * Created on Jan 29, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.polls.contests;

import java.util.TimerTask;

import com.votingcentral.model.bo.mail.EmailBO;
import com.votingcentral.model.polls.PollTimeHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EmailSenderTask extends TimerTask {

	private static final int numHoursBack = 24 * 5;

	long lookBackMillis = PollTimeHelper.MILLI_SECS_PER_HOUR * numHoursBack;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		long currTime = PollTimeHelper.getInstance().getCurrentDate().getTime();
		long timePrev = currTime - lookBackMillis;
		EmailBO.getInstance().sendUnsentEmails(
				PollTimeHelper.getInstance().getDate(timePrev), 10);
	}
}