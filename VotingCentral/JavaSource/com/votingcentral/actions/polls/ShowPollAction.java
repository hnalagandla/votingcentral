/*
 * Created on Mar 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.polls;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.tiles.ComponentDefinition;
import org.apache.struts.tiles.TilesUtil;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.polls.ShowPollFormBean;
import com.votingcentral.model.bo.pollcomments.PollCommentsBO;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.vaco.VCVacoPointsBO;
import com.votingcentral.model.bo.votes.Votes;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.PollPriorityEnum;
import com.votingcentral.model.enums.PollsStatusEnum;
import com.votingcentral.model.enums.TafTypeEnum;
import com.votingcentral.model.enums.VCActivityTypeEnum;
import com.votingcentral.model.enums.VCPrivacyLevelEnum;
import com.votingcentral.model.polls.PollData;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.model.polls.Questionnaire;
import com.votingcentral.model.search.SearchTablesColumns;
import com.votingcentral.pres.web.to.PollWTO;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.enums.PresErrorCodesEnum;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.PresentationConstants;
import com.votingcentral.util.url.VCURLHelper;
import com.votingcentral.util.xml.XXMLException;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ShowPollAction extends VCDispatchAction {

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward previewPoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = new ActionForward(); // return value
		ActionMessages errors = new ActionMessages();
		errors.add("generic", new org.apache.struts.action.ActionMessage(
				"create.poll.preview.info"));
		String next = "previewPoll";
		ShowPollFormBean showPollFormBean = (ShowPollFormBean) form;
		String pollId = showPollFormBean.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		String loginName = VCRequestHelper.getUser(request);
		VCUserTO vto = UserBO.getInstance().getUserByUserName(loginName);
		String name = vto.getFirstName() + " " + vto.getLastName();
		prepareForDisplay(request, pollId, name, loginName, showPollFormBean);
		showPollFormBean.setShowPollAsPreview(true);
		showPollFormBean.setShowPollAsDisabled(true);
		showPollFormBean.setShowNextAsDisabled(true);
		VCRequestHelper.setValueIntoRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		// Write logic determining how the user should be forwarded.
		saveErrors(request, errors);
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showPoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String domContext = getDomainAndContext(request);
		String next = "showPoll";
		ShowPollFormBean showPollFormBean = (ShowPollFormBean) form;
		String pollId = "";
		pollId = VCRequestHelper.getValueFromRequestOrForm(request,
				RequestParameterObjects.POLL_ID, pollId);

		showPollFormBean.setPollId(pollId);
		validate(request, showPollFormBean, errors);
		if (errors.size() > 0) {
			saveErrors(request, errors);
			return mapping.findForward("home");
		}
		PollTO pto = PollBO.getInstance().getPollByPollId(
				showPollFormBean.getPollId());
		Date now = Calendar.getInstance(PollTimeHelper.POLL_TIMES_TIME_ZONE)
				.getTime();
		String loginName = VCRequestHelper.getUser(request);
		String name = "";
		// poll is currently live
		if (pto.getEndTimestamp() != null && pto.getStartTimestamp() != null
				&& pto.getEndTimestamp().after(now)
				&& pto.getStartTimestamp().before(now)) {
			showPollFormBean.setShowPollAsDisabled(false);
		} else {
			// poll either ended or has not started yet.

			// poll already ended
			if (pto.getEndTimestamp().before(now)) {
				showPollFormBean.setPollResultsUrl(VCURLHelper
						.getDisplayPollResultsUrl(domContext, showPollFormBean
								.getPollId()));
				errors.add("pollId",
						new org.apache.struts.action.ActionMessage(
								"show.poll.ended"));
				if (!VCRequestHelper.isGuest(request)) {
					VCUserTO vto = UserBO.getInstance().getUserByUserId(
							pto.getUserId());
					if (loginName.equalsIgnoreCase(vto.getUserName())) {
						showPollFormBean.setExtendPollUrl(VCURLHelper
								.getExtendPollUrl(domContext, pollId));
					}
				}
			}
			// poll not started.
			if (pto.getStartTimestamp().after(now)) {
				errors.add("pollId",
						new org.apache.struts.action.ActionMessage(
								"show.poll.not.started"));
				if (!VCRequestHelper.isGuest(request)) {
					VCUserTO vto = UserBO.getInstance().getUserByUserId(
							pto.getUserId());
					if (loginName.equalsIgnoreCase(vto.getUserName())) {
						showPollFormBean.setEditPollUrl(VCURLHelper
								.getEditPollUrl(domContext, pollId));
					}
				}
			}
			showPollFormBean.setShowPollAsDisabled(true);
		}

		if (loginName != null && loginName.length() > 0
				&& !loginName.equalsIgnoreCase("guest")) {
			VCUserTO vto = UserBO.getInstance().getUserByUserName(loginName);
			name = vto.getFirstName() + " " + vto.getLastName();
			// if the user has already voted in the poll, show a message and
			// emit the see results URL.
			if (isPollActive(pto)
					&& !Votes.getInstance().canUserVote(vto.getUserId(),
							showPollFormBean.getPollId())) {
				errors.add("pollId",
						new org.apache.struts.action.ActionMessage(
								"show.poll.already.voted.with.url"));
				showPollFormBean.setPollResultsUrl(VCURLHelper
						.getDisplayPollResultsUrl(getDomainAndContext(request),
								showPollFormBean.getPollId()));
			}
		} else {
			name = "Guest";
		}
		prepareForDisplay(request, pollId, name, loginName, showPollFormBean);
		VCRequestHelper.setValueIntoRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		if (showPollFormBean.getErrCode() != null
				&& showPollFormBean.getErrCode().length() > 0) {
			try {
				int errCode = Integer.parseInt(showPollFormBean.getErrCode());
				errors.add("error", new ActionMessage(PresErrorCodesEnum.get(
						errCode).getName()));
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}
		//Append the poll name to the title of the page.
		ComponentDefinition cdef = TilesUtil.getDefinition("display.poll",
				request, request.getSession().getServletContext());
		Map attrs = cdef.getAttributes();
		attrs.put("title", pto.getPollName());

		PollBO.getInstance().incrementPollViewsCount(pollId, 1);
		// If a message is required, save the specified key(s)
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
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitPoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String next = "submitPoll";
		ShowPollFormBean showPollFormBean = (ShowPollFormBean) form;
		ActionForward forward = null; // return value
		String pollId = showPollFormBean.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);

		if ((pollId != null) && (pollId.length() > 0)) {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			pto.setPollStatus(PollsStatusEnum.CONFIRMED.getName());
			pto
					.setPollPriority(pto.getPollData().hasPollImages() ? PollPriorityEnum.SIX
							: PollPriorityEnum.TWO);
			PollBO.getInstance().updatePollByPollId(pto);
			if (pto.getPollPrivacyLevel().equalsIgnoreCase(
					VCPrivacyLevelEnum.PUBLIC.getName())) {
				PollBO.getInstance().fillAndUpsertSearch(pto,
						SearchTablesColumns.POLL_DESC);
				PollBO.getInstance().fillAndUpsertSearch(pto,
						SearchTablesColumns.POLL_NAME);
				PollBO.getInstance().fillAndUpsertSearch(pto,
						SearchTablesColumns.POLL_KEYWORDS);
				PollBO.getInstance().fillAndUpsertSearch(pto,
						SearchTablesColumns.POLL_DATA);
			}
			//increment vaco points
			VCVacoPointsBO.getInstance().incrementPoints(pto.getUserId(),
					VCActivityTypeEnum.VC_POINTS_CREATE_POLL);
		}

		VCRequestHelper.setValueIntoRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath()).append("&").append(
				RequestParameterObjects.POLL_ID).append("=").append(pollId);
		ActionForward newForward = new ActionForward();
		newForward.setPath(buffer.toString());
		newForward.setName(forward.getName());
		newForward.setRedirect(true);
		log.debug("forward:" + newForward.getPath());
		// Finish with
		return newForward;
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editPoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = null; // return value
		ShowPollFormBean adminFormBean = (ShowPollFormBean) form;
		String next = "editPoll";
		String pollId = adminFormBean.getPollId();
		if (pollId == null || pollId.length() == 0) {
			return showPoll(mapping, form, request, response);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		ActionForward newForward = new ActionForward();
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath())
				.append(PresentationConstants.AMPERSAND).append(
						RequestParameterObjects.POLL_ID).append(
						PresentationConstants.EQUALS).append(pollId);
		newForward.setPath(buffer.toString());
		newForward.setName(forward.getName());
		newForward.setRedirect(true);
		// Finish with
		return (newForward);

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward extendPoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = null; // return value
		ShowPollFormBean adminFormBean = (ShowPollFormBean) form;
		String next = "showPoll";
		String pollId = adminFormBean.getPollId();
		if (pollId == null || pollId.length() == 0) {
			return showPoll(mapping, form, request, response);
		}
		//extend a poll by 8 more weeks.
		try {
			//make sure the it is only the owner of the poll that
			//can extend the poll.
			if (!VCRequestHelper.isGuest(request)) {
				PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
				VCUserTO vto = UserBO.getInstance().getUserByUserId(
						pto.getUserId());
				String loginName = VCRequestHelper.getUser(request);
				if (loginName.equalsIgnoreCase(vto.getUserName())
						&& pto.getEndTimestamp().before(
								PollTimeHelper.getInstance().getCurrentDate())) {
					pto = PollBO.getInstance().extendPoll(pollId, 8);
					forward = mapping.findForward(next);
					ActionForward newForward = new ActionForward();
					UnSyncStringBuffer buffer = new UnSyncStringBuffer();
					buffer.append(forward.getPath()).append("&").append(
							RequestParameterObjects.POLL_ID).append("=")
							.append(pollId);
					buffer.append(PresentationConstants.AMPERSAND);
					buffer.append(PresentationConstants.ERR_CODE);
					buffer.append(PresentationConstants.EQUALS);
					buffer
							.append(PresErrorCodesEnum.SHOW_POLL_EXTENDED
									.getId());
					newForward.setPath(buffer.toString());
					newForward.setName(forward.getName());
					newForward.setRedirect(true);
					forward = newForward;
				}
			}
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
		if (forward == null) {
			forward = mapping.findForward(next);
			ActionForward newForward = new ActionForward();
			UnSyncStringBuffer buffer = new UnSyncStringBuffer();
			buffer.append(forward.getPath()).append("&").append(
					RequestParameterObjects.POLL_ID).append("=").append(pollId);
			newForward.setPath(buffer.toString());
			newForward.setName(forward.getName());
			newForward.setRedirect(true);
			forward = newForward;
		}
		// Finish with
		return (forward);
	}

	/**
	 * 
	 * @param pollId
	 * @param showPollFormBean
	 * @throws SQLException
	 * @throws XXMLException
	 */
	private void prepareForDisplay(HttpServletRequest request, String pollId,
			String loginName, String loginEmail,
			ShowPollFormBean showPollFormBean) throws SQLException,
			XXMLException {

		String domContext = getDomainAndContext(request);

		if ((pollId != null) && (pollId.length() > 0)) {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			PollWTO wto = new PollWTO();
			PollHelper.fillPollWTO(getDomainAndContext(request), "1", wto, pto,
					100, 0, 1);
			showPollFormBean.setPwto(wto);
			showPollFormBean.setKeywordUrls(PollHelper.getKeyWordUrls(pto
					.getKeywords(), domContext));
			PollData pollData = pto.getPollData();
			Questionnaire questionnaire = pollData.getQuestionnaire();
			List questionDatas = questionnaire.getQuestions();
			PollHelper.setImageUrls(domContext, questionDatas);
			showPollFormBean.setQuestionDataObjects(questionDatas);

			showPollFormBean.setPollStartTime(PollTimeHelper.getInstance()
					.getTimeString(pto.getStartTimestamp().getTime()));

			showPollFormBean.setPollEndTime(PollTimeHelper.getInstance()
					.getTimeString(pto.getEndTimestamp().getTime()));

			showPollFormBean.setPollExpireTime(PollTimeHelper.getInstance()
					.getTimeString(pto.getExpireTimestamp().getTime()));

			showPollFormBean.setPollComments(PollCommentsBO.getInstance()
					.getCommentsByPollForDisplay(domContext, pollId));

			showPollFormBean
					.setTafUrl(VCURLHelper.getTAFUrl(domContext, VCURLHelper
							.getDisplayPollUrl(domContext, pollId),
							"Your friend, " + loginEmail
									+ " thinks this is a great Poll.",
							TafTypeEnum.POLL));
			String userName = PollHelper.getDisplayUserName(pto);
			showPollFormBean.setPollCreatorUserName(userName);

			showPollFormBean.setPollsByUserLink(VCURLHelper
					.getVCUserPublicProfileUrl(domContext, userName));

			String keywords = pto.getKeywords() + ","
					+ showPollFormBean.getCommaSeparatedKeywords();
			showPollFormBean.setCommaSeparatedKeywords(keywords);
		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward displayPollResults(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String next = "showResults";
		ShowPollFormBean showPollFormBean = (ShowPollFormBean) form;
		ActionForward forward = mapping.findForward(next);
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath()).append("&").append(
				RequestParameterObjects.POLL_ID).append("=").append(
				showPollFormBean.getPollId());
		ActionForward newForward = new ActionForward();
		newForward.setPath(buffer.toString());
		newForward.setName(forward.getName());
		newForward.setRedirect(true);
		return newForward;
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

		return showPoll(mapping, form, request, response);
	}

	private void validate(HttpServletRequest request,
			ShowPollFormBean showPollFormBean, ActionMessages errors) {
		String pollId = showPollFormBean.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		if (pollId == null || pollId.length() == 0) {
			errors.add("pollId", new org.apache.struts.action.ActionMessage(
					"show.poll.invalid.pollid"));
			return;
		}

		PollTO pto = null;
		try {
			pto = PollBO.getInstance().getPollByPollId(pollId);
		} catch (SQLException e) {
			// ignore
		}
		if (pto == null) {
			errors.add("pollId", new org.apache.struts.action.ActionMessage(
					"show.poll.invalid.pollid"));
			return;
		}
	}

	private boolean isPollActive(PollTO pto) {
		Date now = Calendar.getInstance(PollTimeHelper.POLL_TIMES_TIME_ZONE)
				.getTime();
		return pto.getStartTimestamp().before(now)
				&& pto.getEndTimestamp().after(now);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward nextPoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null; // return value
		ActionForward newForward = new ActionForward(); // return value

		ShowPollFormBean showPollFormBean = (ShowPollFormBean) form;
		String pollId = showPollFormBean.getPollId();

		pollId = VCRequestHelper.getValueFromRequestOrForm(request,
				RequestParameterObjects.POLL_ID, pollId);
		String nextPollId = PollBO.getInstance().getNextPollIdFromShowPoll(
				pollId);

		log.debug("nextPollId>" + nextPollId);
		log.debug("nextPollId null ??" + (nextPollId == null));

		if (nextPollId != null) {
			VCRequestHelper.setValueIntoRequest(request,
					RequestParameterObjects.POLL_ID, nextPollId);
			showPollFormBean.setPollId(nextPollId);
			forward = mapping.findForward("nextPoll");
			UnSyncStringBuffer buffer = new UnSyncStringBuffer();
			buffer.append(forward.getPath()).append("&").append(
					RequestParameterObjects.POLL_ID).append("=").append(
					nextPollId);
			newForward.setPath(buffer.toString());
			newForward.setName(forward.getName());
			newForward.setRedirect(true);
			forward = newForward;
		} else {
			forward = showPoll(mapping, form, request, response);
		}

		return forward;
	}

}