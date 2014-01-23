/*
 * Created on Oct 20, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.polls;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.env.EnvProps;
import com.votingcentral.forms.HomePageFormBean;
import com.votingcentral.forms.contests.ContestForm;
import com.votingcentral.forms.polls.ShowPollFormBean;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.votes.Votes;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.enums.PresErrorCodesEnum;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.PresentationConstants;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CastAVoteAction extends VCDispatchAction {
	private final static boolean checkForDupeVotes = new Boolean(EnvProps
			.getProperty("check.for.duplicate.voting")).booleanValue();

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward castAVote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return castAVoteInternal(mapping, form, request, response);
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
	public ActionForward voteFromHome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HomePageFormBean homeFormBean = (HomePageFormBean) form;
		ShowPollFormBean pollFormBean = new ShowPollFormBean();
		BeanUtils.copyProperties(pollFormBean, homeFormBean);
		return castAVoteInternal(mapping, pollFormBean, request, response);
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
	public ActionForward voteFromContest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ContestForm cForm = (ContestForm) form;
		ShowPollFormBean pollFormBean = new ShowPollFormBean();
		BeanUtils.copyProperties(pollFormBean, cForm);
		return castAVoteInternal(mapping, pollFormBean, request, response);
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
	private ActionForward castAVoteInternal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String next = "showPoll";
		ShowPollFormBean showPollFormBean = (ShowPollFormBean) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = null; // return value
		validate(request, showPollFormBean, errors);
		if (errors.size() > 0) {
			saveErrors(request, errors);
			return getNewForward(showPollFormBean.getPollId(), mapping);
		}
		String loginName = VCRequestHelper.getUser(request);
		String ip = request.getRemoteAddr();

		String pollId = showPollFormBean.getPollId();

		String[] answerId = showPollFormBean.getAnswerId();
		String answerText = showPollFormBean.getAnswer();
		String questionId = showPollFormBean.getQuestionId();

		long voterUserId = UserBO.getInstance().getUserByUserName(loginName)
				.getUserId();
		int errCode = 0;
		if (Votes.getInstance().canUserVote(voterUserId, pollId)) {
			log.debug("letting the user vote for: user:" + voterUserId
					+ " pollId:" + pollId);
			PollBO.getInstance().castAVote(loginName, pollId, questionId,
					answerId, answerText, ip);
		} else {
			errCode = PresErrorCodesEnum.USER_ALREADY_VOTED_IN_POLL.getId();
		}
		next = "showResults";
		VCRequestHelper.setValueIntoRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		VCRequestHelper.setValueIntoRequest(request,
				RequestParameterObjects.QUESTION_ID, questionId);
		// Write logic determining how the user should be forwarded.
		// Forward the user
		forward = mapping.findForward(next);
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath()).append("&").append(
				RequestParameterObjects.POLL_ID).append("=").append(
				showPollFormBean.getPollId());
		if (errCode > 0) {
			buffer.append(PresentationConstants.AMPERSAND);
			buffer.append(PresentationConstants.ERR_CODE);
			buffer.append(PresentationConstants.EQUALS);
			buffer.append(errCode);
		}
		ActionForward newForward = new ActionForward();
		newForward.setPath(buffer.toString());
		newForward.setName(forward.getName());
		newForward.setRedirect(true);
		return newForward;
	}

	private void validate(HttpServletRequest request,
			ShowPollFormBean showPollFormBean, ActionMessages errors) {
		if (showPollFormBean.getAnswerId() == null
				|| showPollFormBean.getAnswerId().length == 0
				|| (showPollFormBean.getAnswerId()[0]).length() == 0) {
			errors.add("answer", new org.apache.struts.action.ActionMessage(
					"error.field.required", "Atleast one answer choice"));
			return;
		}
		if (showPollFormBean.getQuestionId() == null
				|| (showPollFormBean.getQuestionId().length() == 0)) {
			errors.add("answer", new org.apache.struts.action.ActionMessage(
					"error.field.required", "Question Id"));
			return;
		}

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
		String questionId = showPollFormBean.getQuestionId();
		String answerId[] = showPollFormBean.getAnswerId();
		// verify if there is a question in this poll with the question id
		String question = pto.getPollData().getQuestionByQuestionId(questionId);
		if (question == null || question.length() == 0) {
			errors.add("pollId", new org.apache.struts.action.ActionMessage(
					"show.poll.invalid.poll.id.question.id.combo"));
			return;
		}
		// check if the question id and answer id is a valid combination
		if (!pto.getPollData().isQIdAndAidComboValid(questionId, answerId)) {
			errors.add("pollId", new org.apache.struts.action.ActionMessage(
					"show.poll.invalid.question.id.answer.id.combo"));
			return;
		}
	}

	private ActionForward getNewForward(String pollId, ActionMapping mapping) {
		ActionForward forward = mapping.findForward("showPoll");
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath()).append("&").append(
				RequestParameterObjects.POLL_ID).append("=").append(pollId);
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
		return castAVote(mapping, form, request, response);
	}
}