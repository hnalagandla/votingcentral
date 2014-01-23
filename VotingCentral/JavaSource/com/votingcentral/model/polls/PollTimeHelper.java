/*
 * Created on Mar 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.polls;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollTimeHelper {

	public static final long MILLI_SECS_PER_SEC = 1000L;

	public static final long MILLI_SECS_PER_MIN = 60 * MILLI_SECS_PER_SEC;

	public static final long MILLI_SECS_PER_HOUR = 60 * MILLI_SECS_PER_MIN;

	public static final long MILLI_SECS_PER_DAY = 24 * MILLI_SECS_PER_HOUR;

	public static final long MILLI_SECS_PER_WEEK = 7 * MILLI_SECS_PER_DAY;

	public static final long MILLI_SECS_PER_MONTH = 30 * MILLI_SECS_PER_DAY;

	public static final int MINS_PER_HOUR = 60;

	public static final int MINS_PER_DAY = 24 * MINS_PER_HOUR;

	public static final String SELECT_POLL_END_TIME = "Select Poll End Time";

	public static final String SELECT_POLL_START_TIME = "Select Poll Start Time";

	public static final String SELECT_POLL_BLOCKOUT_PERIOD = "Select Block Out Period";

	private static PollTimeHelper helper = null;

	private static final String datePattern = "MM/dd/yyyy 'at' hh:mm:ss a z";

	private static final String DATE_PATTERN_FOR_VIEW = "MM/dd/yyyy hh:mm a z";

	private static final String MEMBER_SINCE_PATTERN = "dd MMM, yyyy";

	private SimpleDateFormat DATE_FORMATTER = null;

	private SimpleDateFormat CAL_UI_DATE_FORMATTER = null;

	private SimpleDateFormat MEMBER_SINCE_FORMATTER = null;

	private static final String CAL_UI_DATE_PATTERN = "MM/dd/yyyy HH:mm";

	public static final TimeZone POLL_TIMES_TIME_ZONE = TimeZone
			.getTimeZone("America/Phoenix");

	public static final int PUBLIC_POLL_EXPIRE_NUM_DAYS_FROM_END = 60;

	public static PollTimeHelper getInstance() {
		if (helper == null)
			helper = new PollTimeHelper();

		return helper;
	}

	public SimpleDateFormat getDateFormatter() {
		if (DATE_FORMATTER == null) {
			DATE_FORMATTER = new SimpleDateFormat(DATE_PATTERN_FOR_VIEW);
			DATE_FORMATTER.setTimeZone(POLL_TIMES_TIME_ZONE);
		}
		return DATE_FORMATTER;
	}

	public SimpleDateFormat getCalendarUIDateFormatter() {
		if (CAL_UI_DATE_FORMATTER == null) {
			CAL_UI_DATE_FORMATTER = new SimpleDateFormat(CAL_UI_DATE_PATTERN);
			CAL_UI_DATE_FORMATTER.setTimeZone(POLL_TIMES_TIME_ZONE);
		}
		return CAL_UI_DATE_FORMATTER;
	}

	public SimpleDateFormat getMemberSinceDateFormatter() {
		if (MEMBER_SINCE_FORMATTER == null) {
			MEMBER_SINCE_FORMATTER = new SimpleDateFormat(MEMBER_SINCE_PATTERN);
			MEMBER_SINCE_FORMATTER.setTimeZone(POLL_TIMES_TIME_ZONE);
		}
		return MEMBER_SINCE_FORMATTER;
	}

	public String getTimeString(long millis) {
		String dateStr = "";
		dateStr = getDateFormatter().format(new Date(millis));
		return dateStr;
	}

	public Date getMonthsOldTimestamp(long daysBack) {
		Calendar gc = new GregorianCalendar(POLL_TIMES_TIME_ZONE);
		long currtime = System.currentTimeMillis();
		long dayMillis = daysBack * 24 * 60 * 60 * 1000;
		long past = currtime - dayMillis;
		Date f = new Date(past);
		gc.setTime(f);
		gc.set(Calendar.HOUR_OF_DAY, 1);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		return gc.getTime();
	}

	public Date getCurrentDate() {
		Calendar gc = new GregorianCalendar(POLL_TIMES_TIME_ZONE);
		return gc.getTime();
	}

	public Date getDate(long millis) {
		Calendar gc = new GregorianCalendar(POLL_TIMES_TIME_ZONE);
		gc.setTimeInMillis(millis);
		return gc.getTime();
	}

	public int getCurrentYear() {
		Calendar gc = new GregorianCalendar(POLL_TIMES_TIME_ZONE);
		return gc.get(Calendar.YEAR);
	}

	public int getCurrentMonth() {
		Calendar gc = new GregorianCalendar(POLL_TIMES_TIME_ZONE);
		return gc.get(Calendar.MONTH);
	}

	public int getMonthFromDate(Date date) {
		Calendar gc = new GregorianCalendar(POLL_TIMES_TIME_ZONE);
		gc.setTime(date);
		return gc.get(Calendar.MONTH);
	}

	public Date getDateFirstDayOfTheMonth() {
		GregorianCalendar from = new GregorianCalendar(POLL_TIMES_TIME_ZONE);
		from.set(Calendar.DAY_OF_MONTH, 1);
		from.set(Calendar.HOUR_OF_DAY, 1);
		from.set(Calendar.MINUTE, 0);
		from.set(Calendar.SECOND, 0);
		return from.getTime();
	}

	public Date getDateLastDayOfTheMonth() {
		GregorianCalendar to = new GregorianCalendar(POLL_TIMES_TIME_ZONE);
		to.set(Calendar.DAY_OF_MONTH, to
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		to.set(Calendar.HOUR_OF_DAY, 23);
		to.set(Calendar.MINUTE, 59);
		to.set(Calendar.SECOND, 59);
		return to.getTime();
	}

	public String getDiffTimeString(Date d1, Date d2) {
		if (d1 == null || d2 == null) {
			return "";
		}
		String output = "";
		//difference
		long difference = d1.getTime() - d2.getTime();
		if (difference < 1) {
			return "0sec";
		}
		//days
		long days = difference / MILLI_SECS_PER_DAY;
		if (days > 1) {
			output = output + " " + days + "d";
		}
		//hours
		long hours = (difference % MILLI_SECS_PER_DAY) / MILLI_SECS_PER_HOUR;
		if (hours > 1) {
			output = output + " " + hours + "h";
		}
		if (days == 0) {
			long mins = (difference % MILLI_SECS_PER_HOUR) / MILLI_SECS_PER_MIN;
			output = output + " " + mins + "m";

			if (hours == 0) {
				//only do secconds if no hours
				long secs = (difference % MILLI_SECS_PER_MIN)
						/ MILLI_SECS_PER_SEC;
				output = output + " " + secs + "s";
			}
		}
		return output;
	}

	public long getFutureDate(int numDays) {
		Calendar gc = new GregorianCalendar(POLL_TIMES_TIME_ZONE);
		long currMillis = getCurrentDate().getTime();
		long dayMillis = 24 * 60 * 60 * 1000;
		long future = currMillis + (numDays * dayMillis);
		Date f = new Date(future);
		gc.setTime(f);
		gc.set(Calendar.HOUR_OF_DAY, 1);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		return gc.getTimeInMillis();
	}

	public Date getFutureDateFromDate(Date cal, int numDays) {
		GregorianCalendar gc = new GregorianCalendar(POLL_TIMES_TIME_ZONE);
		long calMillis = cal.getTime();
		long future = calMillis + (numDays * MILLI_SECS_PER_DAY);
		Date f = new Date(future);
		gc.setTime(f);
		//		gc.set(Calendar.HOUR_OF_DAY, 1);
		//		gc.set(Calendar.MINUTE, 0);
		//		gc.set(Calendar.SECOND, 0);
		return gc.getTime();
	}

	public long getHoursBetweenDates(Date d1, Date d2) {
		long millisDiff = d1.getTime() - d2.getTime();
		//ensure it gets converted into a positive number.
		if (millisDiff < 0) {
			millisDiff = millisDiff * -1;
		}
		return millisDiff / (1000 * 60 * 60);
	}
}