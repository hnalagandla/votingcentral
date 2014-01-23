/*
 * Created on Aug 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.admin;

import java.sql.SQLException;
import java.util.ArrayList;
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
import com.votingcentral.forms.admin.AdminFormBean;
import com.votingcentral.model.bo.mail.EmailBO;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.roles.VCUserRolesBO;
import com.votingcentral.model.bo.user.PersonalConfigBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.user.info.VCUserPrefsBO;
import com.votingcentral.model.db.dao.to.PersonalConfigTO;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.db.dao.to.VCUserPrefsTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.PollPriorityEnum;
import com.votingcentral.model.enums.VCEmailTypeEnum;
import com.votingcentral.model.enums.VCPrivacyLevelEnum;
import com.votingcentral.model.enums.VCUserAccountStatusEnum;
import com.votingcentral.model.enums.VCUserRolesEnum;
import com.votingcentral.model.polls.DeletePollResponse;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.services.twitter.TwitterService;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AdminAction extends VCDispatchAction {

	private static Log log = LogFactory.getLog(AdminAction.class);

	public ActionForward showMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "adminMain";

		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		// Finish with
		return (forward);

	}

	public ActionForward assignAdmin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		AdminFormBean adminFormBean = (AdminFormBean) form;
		String next = "adminMain";

		try {
			VCUserRolesBO.getInstance().addUserRole(
					adminFormBean.getUserName(), VCUserRolesEnum.ADMIN);
			String message = "Admin access granted to user :"
					+ adminFormBean.getUserName();
			errors.add("name", new ActionMessage("generic.message", message));
		} catch (SQLException e) {
			log.error("Exception assigning admin access", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		// Finish with
		return (forward);

	}

	public ActionForward confirmUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		AdminFormBean adminFormBean = (AdminFormBean) form;
		String next = "adminMain";

		try {
			VCUserTO vto = UserBO.getInstance().getUserByUserName(
					adminFormBean.getUserName());
			if (vto != null) {
				UserBO.getInstance().setAccountStatusByUserName(
						adminFormBean.getUserName(),
						VCUserAccountStatusEnum.CONFIRMED);
			}
			String message = "User " + adminFormBean.getUserName()
					+ " is now a confirmed user.";
			errors.add("name", new ActionMessage("generic.message", message));
		} catch (SQLException e) {
			log.error("Exception confirming a user", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		// Finish with
		return (forward);

	}

	public ActionForward reConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		AdminFormBean adminFormBean = (AdminFormBean) form;
		String next = "adminMain";
		String message = "";
		try {
			String userName = adminFormBean.getUserName();
			VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
			if (vto != null) {
				PersonalConfigTO pto = PersonalConfigBO.getInstance()
						.getUserByUserName(userName);
				String confirmURL = VCURLHelper.getUserRegConfCodeUrlInEmail(
						VCRequestHelper.getDomainAndContext(request), userName,
						pto.getEmailConfCode());
				// send email
				List toAddresses = new ArrayList();
				toAddresses.add(vto.getEmailAddress());
				Object[] values = { vto.getEmailAddress(),
						pto.getEmailConfCode(), confirmURL };

				EmailBO.getInstance().createMailRequest(toAddresses,
						VCEmailTypeEnum.REG_CONFIRM_CODE, values, 0);

				message = "For user " + adminFormBean.getUserName()
						+ " confirmation email was resent at "
						+ vto.getEmailAddress();
			} else {
				message = "User " + adminFormBean.getUserName() + " unknown.";
			}

			errors.add("name", new ActionMessage("generic.message", message));
		} catch (SQLException e) {
			log.error("sending email conf code to user", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		// Finish with
		return (forward);
	}

	public ActionForward revokeAdmin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		AdminFormBean adminFormBean = (AdminFormBean) form;
		String next = "adminMain";

		try {
			VCUserRolesBO.getInstance().deleteUserRole(
					adminFormBean.getUserName(), VCUserRolesEnum.ADMIN);
			String message = "Admin access revoked for user :"
					+ adminFormBean.getUserName();
			errors.add("name", new ActionMessage("generic.message", message));
		} catch (SQLException e) {
			log.error("Exception removing admin access", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		// Finish with
		return (forward);

	}

	public ActionForward stopDailyEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		AdminFormBean adminFormBean = (AdminFormBean) form;
		String next = "adminMain";

		try {
			VCUserTO vto = UserBO.getInstance().getUserByEmailAddress(
					adminFormBean.getUserName());
			if (vto != null) {
				VCUserPrefsTO vuto = new VCUserPrefsTO();
				vuto.setPollStartedLevelEnum(VCPrivacyLevelEnum.PRIVATE);
				vuto.setUserId(vto.getUserId());
				VCUserPrefsBO.getInstance().upsertVCUserPrefs(vuto);
				String message = "Daily email disabled for email:"
						+ adminFormBean.getUserName() + ", username: "
						+ vto.getUserName() + ", user Id:" + vto.getUserId();
				errors.add("name",
						new ActionMessage("generic.message", message));
			}
		} catch (SQLException e) {
			log.error("Exception removing admin access", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		// Finish with
		return (forward);

	}

	public ActionForward editPoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = null; // return value
		AdminFormBean adminFormBean = (AdminFormBean) form;
		String next = "editPoll";
		String pollId = adminFormBean.getPollId();
		if (pollId == null || pollId.length() == 0) {
			return showMain(mapping, form, request, response);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		ActionForward newForward = new ActionForward();
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath()).append("&").append(
				RequestParameterObjects.POLL_ID).append("=").append(pollId);
		newForward.setPath(buffer.toString());
		newForward.setName(forward.getName());
		newForward.setRedirect(true);
		// Finish with
		return (newForward);

	}

	public ActionForward makeFeatured(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = null; // return value
		AdminFormBean adminFormBean = (AdminFormBean) form;
		String next = "adminMain";
		String pollId = adminFormBean.getPollId();
		if (pollId == null || pollId.length() == 0) {
			return showMain(mapping, form, request, response);
		}
		try {
			PollBO.getInstance().setPollPriority(pollId, PollPriorityEnum.TEN);
			// If a message is required, save the specified key(s)
			errors.add("name", new ActionMessage("generic.message", "Poll "
					+ PollBO.getInstance().getPollByPollId(pollId)
							.getPollName() + " set as featured poll"));
		} catch (SQLException e) {
			log.error("Exception setting poll as featured.", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		// Finish with
		return (forward);
	}

	public ActionForward deletePoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = null; // return value
		AdminFormBean adminFormBean = (AdminFormBean) form;
		String next = "adminMain";
		String pollId = adminFormBean.getPollId();
		if (pollId == null || pollId.length() == 0) {
			return showMain(mapping, form, request, response);
		}
		try {
			DeletePollResponse dRes = PollBO.getInstance().deletePoll(pollId);
			// If a message is required, save the specified key(s)
			errors.add("name", new ActionMessage("generic.message", dRes));
		} catch (SQLException e) {
			log.error("Exception setting poll as featured.", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		// Finish with
		return (forward);
	}

	public ActionForward extendPoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = null; // return value
		AdminFormBean adminFormBean = (AdminFormBean) form;
		String next = "adminMain";
		String pollId = adminFormBean.getPollId();
		if (pollId == null || pollId.length() == 0) {
			return showMain(mapping, form, request, response);
		}
		//extend a poll by 8 more weeks.
		try {
			PollTO pto = PollBO.getInstance().extendPoll(pollId, 8);
			// If a message is required, save the specified key(s)
			errors.add("name", new ActionMessage("generic.message",
					"New Poll end time is: "
							+ PollTimeHelper.getInstance().getTimeString(
									pto.getEndTimestamp().getTime())));
		} catch (SQLException e) {
			log.error("Exception setting poll as featured.", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		// Finish with
		return (forward);
	}

	public ActionForward pollToTwitter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = null; // return value
		AdminFormBean adminFormBean = (AdminFormBean) form;
		String next = "adminMain";
		String pollId = adminFormBean.getPollId();
		if (pollId == null || pollId.length() == 0) {
			return showMain(mapping, form, request, response);
		}
		//extend a poll by 8 more weeks.
		try {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			TwitterService.getInstance().updateStatus(pto.getPollName(),
					pto.getPollId());
			TwitterService.getInstance().sendDirectMessageToFollowers(
					pto.getPollName(), pto.getPollId());
			// If a message is required, save the specified key(s)
			errors.add("name", new ActionMessage("generic.message", "Poll, "
					+ pto.getPollName() + " poll Id :" + pto.getPollId()
					+ " posted to twitter"));
		} catch (SQLException e) {
			log.error("Exception setting poll as featured.", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		// Finish with
		return (forward);
	}

	public ActionForward removeFeatured(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = null; // return value
		AdminFormBean adminFormBean = (AdminFormBean) form;
		String next = "adminMain";
		String pollId = adminFormBean.getPollId();
		if (pollId == null || pollId.length() == 0) {
			return showMain(mapping, form, request, response);
		}
		try {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			if (pto.getPollData().hasPollImages()) {
				PollBO.getInstance().setPollPriority(pollId,
						PollPriorityEnum.SIX);
			} else {
				PollBO.getInstance().setPollPriority(pollId,
						PollPriorityEnum.TWO);
			}
			errors.add("name", new ActionMessage("generic.message", "Poll "
					+ pto.getPollName()
					+ " removed as featured poll and set back to priority "
					+ pto.getPollPriority()));
		} catch (SQLException e) {
			log.error("Exception setting poll as featured.", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		// Finish with
		return (forward);
	}

	public ActionForward textToTwitter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = null; // return value
		AdminFormBean adminFormBean = (AdminFormBean) form;
		String next = "adminMain";
		String textToTwitter = adminFormBean.getTextToTwitter();
		if (textToTwitter == null || textToTwitter.length() == 0) {
			return showMain(mapping, form, request, response);
		}
		TwitterService.getInstance().updateStatus(textToTwitter);
		// If a message is required, save the specified key(s)
		errors.add("name", new ActionMessage("generic.message", "'"
				+ textToTwitter + "' sent to Twitter"));
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		// Finish with
		return (forward);
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
		return showMain(mapping, form, request, response);
	}
}