/*
 * Created on Aug 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.taf;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.taf.TafForm;
import com.votingcentral.model.bo.taf.TafBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.vaco.VCVacoPointsBO;
import com.votingcentral.model.bo.vcwinners.VCWinnersBO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.TafTypeEnum;
import com.votingcentral.model.enums.VCActivityTypeEnum;
import com.votingcentral.model.enums.VCEmailTypeEnum;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.FastURLEncoder;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TafAction extends VCDispatchAction {

	private static Log log = LogFactory.getLog(TafAction.class);

	private static int TAF_CHECK_HOURS = 24;

	private static int MAX_TAF_REQUESTS_PER_USER_CHECKED_TIME = 5;

	private static int MAX_TAF_REQUESTS_PER_USER_IN_PRIZE_WINNING_PERIOD = 10;

	private static String TAF_DEFAULT_DISPLAY_BODY = "Type a message here...";

	/**
	 *  
	 */
	public ActionForward showTaf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "showTaf";
		TafForm tForm = (TafForm) form;
		try {
			String loginName = VCRequestHelper.getUser(request);
			VCUserTO vto = UserBO.getInstance().getUserByUserName(loginName);
			tForm.setFromEmail(vto.getEmailAddress());
			tForm.setSubject("Your friend " + vto.getEmailAddress()
					+ " thinks this is something you should check.");
			tForm.setBody(TAF_DEFAULT_DISPLAY_BODY);
			tForm.setToEmail(null);
		} catch (Exception e) {
			log.error(e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		//If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	/**
	 *  
	 */
	public ActionForward showThanks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = new ActionForward(); // return value
		String next = "showTafThanks";
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	public ActionForward sendEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "success";
		TafForm tForm = (TafForm) form;
		String userName = VCRequestHelper.getUser(request);
		VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
		String fromAddress = vto.getEmailAddress();
		validate(fromAddress, tForm, errors);
		if (errors.size() > 0) {
			saveErrors(request, errors);
			return showTaf(mapping, form, request, response);
		}
		try {
			String[] toAddresses = tForm.getToEmail();
			List toAddress = new ArrayList();
			for (int i = 0; i < toAddresses.length; i++) {
				if (toAddresses[i].length() > 0) {
					toAddress.add(toAddresses[i]);
				}
			}
			String ipAddress = request.getRemoteAddr();
			if (tForm.getCopy() != null
					&& tForm.getCopy().equalsIgnoreCase("SELF")) {
				toAddress.add(fromAddress);
			}
			String subject = tForm.getSubject();
			String url = tForm.getTafUrl();
			TafBO.getInstance().saveTaf(tForm, fromAddress, ipAddress);
			String body = tForm.getBody();
			if (body.equalsIgnoreCase(TAF_DEFAULT_DISPLAY_BODY)) {
				body = subject;
			}
			Object[] values = { body, url };
			TafBO.getInstance().sendEmail(fromAddress, toAddress,
					VCEmailTypeEnum.TAF, subject, values);
			//increment vaco points
			VCVacoPointsBO.getInstance().incrementPoints(vto.getUserId(),
					VCActivityTypeEnum.VC_POINTS_TELL_A_FRIEND);

		} catch (Exception e) {
			next = "showTaf";
			log.error("Exception trying to email to friend", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("user.login.exception"));
		}

		//If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		ActionForward newFwd = new ActionForward();
		UnSyncStringBuffer buff = new UnSyncStringBuffer();
		buff.append(forward.getPath());

		buff.append("&");
		buff.append("tafUrl");
		buff.append("=");
		buff.append(FastURLEncoder.encode(tForm.getTafUrl()));

		buff.append("&");
		buff.append("orignalPageUrl");
		buff.append("=");
		String tafUrl = VCURLHelper.getTAFUrl(getDomainAndContext(request),
				tForm.getTafUrl(), "Your friend " + vto.getEmailAddress()
						+ " thinks this is a great Poll.", TafTypeEnum.POLL);
		buff.append(FastURLEncoder.encode(tafUrl));

		newFwd.setPath(buff.toString());
		newFwd.setRedirect(true);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (newFwd);
	}

	private void validate(String fromEmail, TafForm tForm, ActionMessages errors) {
		String[] toEmail = tForm.getToEmail();
		if (toEmail == null || toEmail.length == 0 || toEmail[0].equals("")
				|| toEmail[0].length() == 0) {
			errors.add("toEmail", new org.apache.struts.action.ActionMessage(
					"error.field.required",
					"Atleast one Friend's email Address"));
		} else {
			for (int i = 0; i < toEmail.length; i++) {
				String to = toEmail[i];
				if (to == null || to.length() == 0) {
					continue;
				}
				if (!PollHelper.isValidEmail(to)) {
					errors.add("emailAddress",
							new org.apache.struts.action.ActionMessage(
									"error.field.invalid.email.address.taf",
									String.valueOf(i + 1)));
				} else {
					int count = TafBO.getInstance()
							.getTafRequestsBySenderRecieverAndTime(fromEmail,
									to, TAF_CHECK_HOURS);
					//enforce no more than 5 emails to a given user
					// in 24 hours.
					if (count > MAX_TAF_REQUESTS_PER_USER_CHECKED_TIME) {
						errors.add("emailAddress",
								new org.apache.struts.action.ActionMessage(
										"error.field.taf.limit.reached", to,
										String.valueOf(TAF_CHECK_HOURS)));
					}
					int count1 = 0;
					try {
						count1 = TafBO
								.getInstance()
								.getTafRequestsBySenderRecieverAndTime(
										fromEmail,
										to,
										getHoursFromCurrentTimeToPrizeWinningPeriodStart());
						// enforce no more than 10 emails to a given user
						// in a contest period.
						if (count1 > MAX_TAF_REQUESTS_PER_USER_IN_PRIZE_WINNING_PERIOD) {
							errors
									.add(
											"emailAddress",
											new org.apache.struts.action.ActionMessage(
													"error.field.taf.limit.reached.in.winning.period",
													to));
						}
					} catch (SQLException e) {
						log.error("");
					}
				}
			}
		}
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	private long getHoursFromCurrentTimeToPrizeWinningPeriodStart()
			throws SQLException {
		Date d1 = PollTimeHelper.getInstance().getCurrentDate();
		Date d2 = VCWinnersBO.getInstance().getPeriodStartDate();
		return PollTimeHelper.getInstance().getHoursBetweenDates(d1, d2);
	}

}