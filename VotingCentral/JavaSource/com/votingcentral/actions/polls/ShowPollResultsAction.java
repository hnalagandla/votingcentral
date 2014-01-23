/*
 * Created on Jul 29, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.polls;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.polls.ShowPollFormBean;
import com.votingcentral.forms.polls.ShowPollResultsFormBean;
import com.votingcentral.model.bo.pollcomments.PollCommentsBO;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.votes.Votes;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.TafTypeEnum;
import com.votingcentral.model.enums.VCChartPerspectiveEnum;
import com.votingcentral.model.enums.VCChartSizeEnum;
import com.votingcentral.model.enums.VCChartTypeEnum;
import com.votingcentral.model.enums.VCDownloadFileTypeEnum;
import com.votingcentral.model.polls.PollData;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.enums.PresErrorCodesEnum;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.PresentationConstants;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ShowPollResultsAction extends VCDispatchAction {

	public ActionForward showResults(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String next = "showResults";
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		ShowPollResultsFormBean showResultsFormBean = (ShowPollResultsFormBean) form;
		String pollId = "";
		pollId = VCRequestHelper.getValueFromRequestOrForm(request,
				RequestParameterObjects.POLL_ID, pollId);
		String questionId = showResultsFormBean.getQuestionId();
		questionId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.QUESTION_ID, questionId);
		showResultsFormBean.setPollId(pollId);
		showResultsFormBean.setQuestionId(questionId);
		String domContext = getDomainAndContext(request);

		try {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			// if the user has not voted and the poll has not ended
			// redirect to show poll page.
			VCUserTO vto = UserBO.getInstance().getUserByUserName(
					VCRequestHelper.getUser(request));
			Date now = PollTimeHelper.getInstance().getCurrentDate();
			if (Votes.getInstance().canUserVote(vto.getUserId(), pollId)
					&& pto.getEndTimestamp().after(now)) {
				log.debug("User has not voted, sending them to display poll.");
				errors.add("pollId",
						new org.apache.struts.action.ActionMessage(
								"show.poll.participation.reqd"));
				return getNewForward(pollId, mapping,
						PresErrorCodesEnum.USER_NOT_VOTED_IN_POLL.getId());
			} else {
				// at this stage if the question Id is null,the user is
				// coming directly to the poll results page.
				// figure out the question.
				// Fixx this when we support multiple questions in a
				// poll.
				if (questionId == null || questionId.length() == 0) {
					PollData pollData = pto.getPollData();
					questionId = pollData.getFirstQuestion().getQuestionId();
					showResultsFormBean.setQuestionId(questionId);
				}

				VCChartTypeEnum currType = VCChartTypeEnum
						.get(showResultsFormBean.getCct());
				VCChartPerspectiveEnum currPers = VCChartPerspectiveEnum
						.get(showResultsFormBean.getCcp());
				VCChartSizeEnum size = VCChartSizeEnum.get(showResultsFormBean
						.getSize());
				if (currPers == VCChartPerspectiveEnum.INVALID) {
					currPers = VCChartPerspectiveEnum.DEFAULT;
					showResultsFormBean.setCcp(VCChartPerspectiveEnum.DEFAULT
							.getName());
				}
				if (currType == VCChartTypeEnum.INVALID) {
					currType = VCChartTypeEnum.DEFAULT;
					showResultsFormBean.setCct(VCChartTypeEnum.DEFAULT
							.getName());
				}
				if (size == VCChartSizeEnum.INVALID) {
					size = VCChartSizeEnum.DEFAULT;
					showResultsFormBean.setSize(VCChartSizeEnum.DEFAULT
							.getName());
				}
				setDownloadResultsUrls(showResultsFormBean, domContext);
				setPollResultsUrls(request, showResultsFormBean, currType,
						currPers);

				String imageUrl = VCURLHelper.getURLForGraphImage(domContext,
						currType, currPers, size, pollId, questionId);
				showResultsFormBean.setChartImageUrl(imageUrl);

				setBigSmallURL(request, showResultsFormBean, currType,
						currPers, size, pollId, questionId);

				showResultsFormBean.setPollComments(PollCommentsBO
						.getInstance().getCommentsByPollForDisplay(domContext,
								pollId));

				showResultsFormBean.setTafUrl(VCURLHelper.getTAFUrl(domContext,
						VCURLHelper.getDisplayPollUrl(domContext, pollId),
						"Your friend, " + vto.getDisplayUserName()
								+ " thinks this is a great Poll.",
						TafTypeEnum.POLL));
				showResultsFormBean.setPollName(pto.getPollName());
				showResultsFormBean.setDisplayPollResultsUrl(VCURLHelper
						.getDisplayPollResultsUrl(domContext, pollId));
			}

			VCRequestHelper.setValueIntoRequest(request,
					RequestParameterObjects.POLL_ID, pollId);
			VCRequestHelper.setValueIntoRequest(request,
					RequestParameterObjects.QUESTION_ID, questionId);

		} catch (Exception e) {
			log.error("error showing results:", e);
		}
		if (showResultsFormBean.getErrCode() != null
				&& showResultsFormBean.getErrCode().length() > 0) {
			try {
				int errCode = Integer
						.parseInt(showResultsFormBean.getErrCode());
				errors.add("error", new ActionMessage(PresErrorCodesEnum.get(
						errCode).getName()));
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}

		saveErrors(request, errors);

		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	private void setBigSmallURL(HttpServletRequest request,
			ShowPollResultsFormBean showResultsFormBean, VCChartTypeEnum type,
			VCChartPerspectiveEnum persp, VCChartSizeEnum size, String pollId,
			String questionId) {
		String imageUrl = "";

		if (VCChartSizeEnum.get(showResultsFormBean.getSize()) == VCChartSizeEnum.BIG) {
			showResultsFormBean.setBigSmallUrlDesc("Smaller Image");
			imageUrl = VCURLHelper.getURLForGraphImage(
					getDomainAndContext(request), type, persp,
					VCChartSizeEnum.SMALL, pollId, questionId);
		} else {
			showResultsFormBean.setBigSmallUrlDesc("Bigger Image");
			imageUrl = VCURLHelper.getURLForGraphImage(
					getDomainAndContext(request), type, persp,
					VCChartSizeEnum.BIG, pollId, questionId);
		}
		showResultsFormBean.setBigSmallUrl(imageUrl);
	}

	private void setDownloadResultsUrls(
			ShowPollResultsFormBean showResultsFormBean, String domContext) {
		showResultsFormBean.setDownloadAsCSVUrl(VCURLHelper
				.getDownloadPollResultsUrl(domContext, showResultsFormBean
						.getPollId(), showResultsFormBean.getQuestionId(),
						VCDownloadFileTypeEnum.CSV));
		showResultsFormBean.setDownloadAsExcelUrl(VCURLHelper
				.getDownloadPollResultsUrl(domContext, showResultsFormBean
						.getPollId(), showResultsFormBean.getQuestionId(),
						VCDownloadFileTypeEnum.EXCEL));
		showResultsFormBean.setDownloadAsTxtUrl(VCURLHelper
				.getDownloadPollResultsUrl(domContext, showResultsFormBean
						.getPollId(), showResultsFormBean.getQuestionId(),
						VCDownloadFileTypeEnum.TEXT));
	}

	private void setPollResultsUrls(HttpServletRequest request,
			ShowPollResultsFormBean showResultsFormBean,
			VCChartTypeEnum currType, VCChartPerspectiveEnum currPersp) {
		List urls = new ArrayList();

		// set both the default URLs first.

		// bar graphs have various data representations.
		// pie does not have any besides basic totals.
		if (currType == VCChartTypeEnum.BAR) {
			showResultsFormBean.setCct(VCChartTypeEnum.BAR.getName());
			setOtherPollResultsUrls(request, showResultsFormBean.getPollId(),
					showResultsFormBean.getQuestionId(), currPersp, urls);
		} else {
			showResultsFormBean.setCct(VCChartTypeEnum.PIE.getName());
		}
		String defBarURL = VCURLHelper.getUrlAndDescForBarGraphByPerspective(
				getDomainAndContextForImg(request),
				VCChartPerspectiveEnum.SIMPLE_TOTALS,
				showResultsFormBean.getPollId(),
				showResultsFormBean.getQuestionId()).getHref();

		showResultsFormBean.setDefaultBarURL(defBarURL);
		String defPieURL = VCURLHelper.getUrlAndDescForPieChart(
				getDomainAndContextForImg(request), showResultsFormBean.getPollId(),
				showResultsFormBean.getQuestionId()).getHref();

		showResultsFormBean.setDefaultPieURL(defPieURL);

		// add the time based results only if this is a trended poll and chart
		// type is not Pie.
		if (currType != VCChartTypeEnum.PIE) {
			try {
				PollTO pto = PollBO.getInstance().getPollByPollId(
						showResultsFormBean.getPollId());
				if (pto != null && pto.getPollBlockoutPeriodMS() > 0
						&& currPersp != VCChartPerspectiveEnum.TTX) {
					urls.add(VCURLHelper.getUrlAndDescForBarGraphByPerspective(
							getDomainAndContextForImg(request),
							VCChartPerspectiveEnum.TTX, showResultsFormBean
									.getPollId(), showResultsFormBean
									.getQuestionId()));
				}
			} catch (SQLException e) {
				// ignore
			}
		}
		showResultsFormBean.setTextDescAndUrls(urls);
	}

	// Set the other URLs execpt the current type and invalid and default type.
	private void setOtherPollResultsUrls(HttpServletRequest request,
			String pollId, String questionId, VCChartPerspectiveEnum curr,
			List urls) {
		for (Iterator itr = VCChartPerspectiveEnum
				.getIterator(VCChartPerspectiveEnum.class); itr.hasNext();) {
			VCChartPerspectiveEnum persp = (VCChartPerspectiveEnum) itr.next();
			if (persp != VCChartPerspectiveEnum.INVALID
					&& persp != VCChartPerspectiveEnum.TTX && persp != curr) {
				urls.add(VCURLHelper
						.getUrlAndDescForBarGraphByPerspective(
								getDomainAndContext(request), persp, pollId,
								questionId));
			}
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
	public ActionForward nextPoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null; // return value
		ActionForward newForward = new ActionForward(); // return value
		String loginName = VCRequestHelper.getUser(request);
		long userId = UserBO.getInstance().getUserByUserName(loginName)
				.getUserId();
		ShowPollResultsFormBean showResultsFormBean = (ShowPollResultsFormBean) form;
		String nextPollId = PollBO.getInstance().getNextPollIdFromShowResults(
				userId);
		log.debug("nextPollId>" + nextPollId);
		log.debug("nextPollId null ??" + (nextPollId == null));
		if (nextPollId != null) {
			VCRequestHelper.setValueIntoRequest(request,
					RequestParameterObjects.POLL_ID, nextPollId);
			String currPollIdInReqAttr = (String) request
					.getAttribute(RequestParameterObjects.POLL_ID);
			String currPollIdInReqParam = (String) request
					.getParameter(RequestParameterObjects.POLL_ID);
			showResultsFormBean.setPollId(nextPollId);
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
			forward = mapping.findForward("createAPoll");
		}

		return forward;
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
		ActionForward forward = null; // return value
		ActionForward newForward = new ActionForward(); // return value
		ShowPollResultsFormBean showResultsFormBean = (ShowPollResultsFormBean) form;
		String pollId = showResultsFormBean.getPollId();
		forward = getNewForward(pollId, mapping, 0);
		return forward;
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
		return showResults(mapping, form, request, response);
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

	private ActionForward getNewForward(String pollId, ActionMapping mapping,
			int errCode) {
		ActionForward forward = mapping.findForward("showPoll");
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath()).append("&").append(
				RequestParameterObjects.POLL_ID).append("=").append(pollId);
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
}