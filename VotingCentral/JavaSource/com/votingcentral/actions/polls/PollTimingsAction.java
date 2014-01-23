/*
 * Created on Mar 25, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.polls;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.polls.PollTimingsFormBean;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollTimingsAction extends VCDispatchAction {

	public ActionForward pollTimes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String next = "pollTimes";
		PollTimingsFormBean timesFormBean = (PollTimingsFormBean) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value

		String pollId = timesFormBean.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		if (pollId == null || pollId.length() == 0) {
			return mapping.findForward("createPollHome");
		}
		PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
		timesFormBean.setPollId(pollId);
		timesFormBean.setPollEndTimes(PollBO.getInstance().fillPollEndWeeks(
				PollTimeHelper.SELECT_POLL_END_TIME));

		timesFormBean.setPollBlockOutTimes(PollBO.getInstance()
				.getListOfBlockOutTimes());
		timesFormBean.setPollBlockoutPeriodMS(Long.toString(pto
				.getPollBlockoutPeriodMS()));

		VCRequestHelper.setValueIntoRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	public ActionForward upsertPollTimes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String next = "previewPoll";
		PollTimingsFormBean timesFormBean = (PollTimingsFormBean) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String pollId = timesFormBean.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		if (pollId == null || pollId.length() == 0 || pollId.length() != 36) {
			return mapping.findForward("createPollHome");
		}
		validate(timesFormBean, errors);
		if (errors.size() > 0) {
			saveErrors(request, errors);
			return pollTimes(mapping, timesFormBean, request, response);
		}
		PollTO pto = PollBO.getInstance().getPollByPollId(pollId);

		long pollStartTime = PollTimeHelper.getInstance()
				.getCalendarUIDateFormatter().parse(
						timesFormBean.getPollStartDate() + " "
								+ timesFormBean.getPollStartTime()).getTime();
		Date pollStartdate = PollTimeHelper.getInstance()
				.getDate(pollStartTime);
		pto.setStartTimestamp(pollStartdate);

		int pollEndInWeeks = Integer.parseInt(timesFormBean
				.getPollEndTimestamp());
		long pollEndTime = 0;

		pollEndTime = pollStartTime
				+ (pollEndInWeeks * PollTimeHelper.MILLI_SECS_PER_WEEK);

		Date pollEndDate = PollTimeHelper.getInstance().getDate(pollEndTime);
		pto.setEndTimestamp(pollEndDate);

		// expire time is sometime after the poll end time.
		long pollExpireTime = pollEndTime
				+ PollTimeHelper.PUBLIC_POLL_EXPIRE_NUM_DAYS_FROM_END
				* PollTimeHelper.MILLI_SECS_PER_DAY;
		Date pollExpire = new Date(pollExpireTime);
		pto.setExpireTimestamp(pollExpire);

		if (timesFormBean.getPollBlockoutPeriodMS() != null
				&& !timesFormBean.getPollBlockoutPeriodMS().equalsIgnoreCase(
						PollTimeHelper.SELECT_POLL_BLOCKOUT_PERIOD)) {
			pto.setPollBlockoutPeriodMS(Long.parseLong(timesFormBean
					.getPollBlockoutPeriodMS()));
		}
		PollBO.getInstance().updatePollByPollId(pto);

		VCRequestHelper.setValueIntoRequest(request,
				RequestParameterObjects.POLL_ID, pollId);

		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	private void fillDateAndTimes(List times, String selectStr) {
		if (times == null) {
			times = new ArrayList();
		}

		long currMillis = System.currentTimeMillis();

		times.add(new LabelValueBean(selectStr, selectStr));

		String dateStr = "";
		dateStr = PollTimeHelper.getInstance().getTimeString(currMillis);
		times.add(new LabelValueBean(dateStr, dateStr));

		dateStr = PollTimeHelper.getInstance().getTimeString(
				currMillis + PollTimeHelper.MILLI_SECS_PER_HOUR);
		times.add(new LabelValueBean(dateStr, dateStr));

		dateStr = PollTimeHelper.getInstance().getTimeString(
				currMillis + 3 * PollTimeHelper.MILLI_SECS_PER_HOUR);
		times.add(new LabelValueBean(dateStr, dateStr));

		dateStr = PollTimeHelper.getInstance().getTimeString(
				currMillis + 5 * PollTimeHelper.MILLI_SECS_PER_HOUR);
		times.add(new LabelValueBean(dateStr, dateStr));

		dateStr = PollTimeHelper.getInstance().getTimeString(
				currMillis + 10 * PollTimeHelper.MILLI_SECS_PER_HOUR);
		times.add(new LabelValueBean(dateStr, dateStr));

		//"Midnight, Tonight"
		dateStr = PollTimeHelper.getInstance().getTimeString(
				getTimeAtMidnightFor(currMillis, 1));
		times.add(new LabelValueBean(dateStr, dateStr));

		//"Midnight, Tomorrow"
		dateStr = PollTimeHelper.getInstance().getTimeString(
				getTimeAtMidnightFor(currMillis, 2));
		times.add(new LabelValueBean(dateStr, dateStr));

		//"Midnight, Day After Tomorrow"
		dateStr = PollTimeHelper.getInstance().getTimeString(
				getTimeAtMidnightFor(currMillis, 3));
		times.add(new LabelValueBean(dateStr, dateStr));

		//"Midnight, Week From Now"
		dateStr = PollTimeHelper.getInstance().getTimeString(
				getTimeAtMidnightFor(currMillis, 7));

		times.add(new LabelValueBean(dateStr, dateStr));

		//"Midnight, 2 Weeks From Now"
		dateStr = PollTimeHelper.getInstance().getTimeString(
				getTimeAtMidnightFor(currMillis, 14));
		times.add(new LabelValueBean(dateStr, dateStr));
	}

	private List getListOfStartTimes() {
		List listOfStartTimes = new ArrayList();
		fillDateAndTimes(listOfStartTimes,
				PollTimeHelper.SELECT_POLL_START_TIME);
		return listOfStartTimes;
	}

	/**
	 * Given a time 'currMillis' and for a day 'n' days away, get the midnight
	 * millis.
	 * 
	 * @param currMillis
	 * @param daysFromToday
	 * @return
	 */
	private long getTimeAtMidnightFor(long currMillis, int daysFromToday) {
		Calendar gc = new GregorianCalendar(PollTimeHelper.POLL_TIMES_TIME_ZONE);
		long future = currMillis
				+ (daysFromToday * PollTimeHelper.MILLI_SECS_PER_DAY);
		Date f = new Date(future);
		gc.setTime(f);
		gc.set(Calendar.HOUR, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.AM_PM, Calendar.AM);
		return gc.getTime().getTime();
	}

	private void validate(PollTimingsFormBean form, ActionMessages errors) {
		if (form.getPollStartDate() == null
				|| form.getPollStartDate().length() < 10) {
			errors.add("pollStartDate",
					new org.apache.struts.action.ActionMessage(
							"error.field.required", "Valid Poll Start Date"));
		}
		if (form.getPollStartTime() == null
				|| form.getPollStartTime().length() < 4
				|| form.getPollStartTime().indexOf("Select") != -1) {
			errors.add("pollStartTime",
					new org.apache.struts.action.ActionMessage(
							"error.field.required", "Valid Poll Start Time"));
		}
		if (form.getPollEndTimestamp().equalsIgnoreCase(
				PollTimeHelper.SELECT_POLL_END_TIME)) {
			errors.add("pollEndTimeStamp",
					new org.apache.struts.action.ActionMessage(
							"error.field.required", "Poll End Time"));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */

	protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return pollTimes(mapping, form, request, response);
	}
}