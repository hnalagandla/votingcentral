/*
 * Created on Oct 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.polls;

import java.sql.SQLException;
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
import com.votingcentral.model.db.dao.to.PollCommentsTO;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.WordFilter;
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
public class PollCommentsAction extends VCDispatchAction {
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward fromPoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "showPoll";
		ShowPollFormBean showPollFormBean = (ShowPollFormBean) form;
		String pollId = showPollFormBean.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		showPollFormBean.setPollId(pollId);
		String userName = VCRequestHelper.getUser(request);
		VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
		try {
			//show if invalid pollId.
			validate(pollId, showPollFormBean.getComment(), vto.getUserId(),
					errors);
			if (errors.size() == 0) {
				addComments(pollId, userName, showPollFormBean.getComment(),
						request.getRemoteAddr());
			}
			VCRequestHelper.setValueIntoRequest(request,
					RequestParameterObjects.POLL_ID, pollId);
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
		// Finish with
		return getNewActionForward(next, pollId, mapping, null, errors.size());
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
	public ActionForward fromResults(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "showResults";
		ShowPollResultsFormBean resultsFormBean = (ShowPollResultsFormBean) form;
		String pollId = resultsFormBean.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		resultsFormBean.setPollId(pollId);
		String userName = VCRequestHelper.getUser(request);
		VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);

		try {
			//show if invalid pollId.
			validate(pollId, resultsFormBean.getComment(), vto.getUserId(),
					errors);
			if (errors.size() == 0) {
				addComments(pollId, userName, resultsFormBean.getComment(),
						request.getRemoteAddr());
			}
			VCRequestHelper.setValueIntoRequest(request,
					RequestParameterObjects.POLL_ID, pollId);
		} catch (Exception e) {
			log.error("Exception adding comments to poll", e);
		}
		//If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}

		// Finish with
		return getNewActionForward(next, pollId, mapping, resultsFormBean,
				errors.size());
	}

	private ActionForward getNewActionForward(String next, String pollId,
			ActionMapping mapping, ShowPollResultsFormBean resultsFormBean,
			int errors) {
		ActionForward forward = mapping.findForward(next);
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath()).append("&").append(
				RequestParameterObjects.POLL_ID).append("=").append(pollId);
		if (resultsFormBean != null) {
			buffer.append("&").append("ccp").append("=").append(
					resultsFormBean.getCcp());
			buffer.append("&").append("cct").append("=").append(
					resultsFormBean.getCct());
		}
		if (errors > 0) {
			buffer.append(PresentationConstants.AMPERSAND).append(
					PresentationConstants.ERR_CODE).append(
					PresentationConstants.EQUALS);
			buffer.append(PresErrorCodesEnum.COMMENT_NOT_VALID_IN_POLL.getId());
		}
		ActionForward newForward = new ActionForward();
		newForward.setPath(buffer.toString());
		newForward.setName(forward.getName());
		newForward.setRedirect(true);
		log.debug("forward:" + newForward.getPath());
		// Finish with
		return newForward;
	}

	private void validate(String pollId, String comment, long userId,
			ActionMessages errors) {
		if (WordFilter.isObscene(comment)) {
			errors.add("pollId", new org.apache.struts.action.ActionMessage(
					"show.poll.comments.obscene"));
			return;
		}
		if (pollId == null || pollId.length() == 0) {
			errors.add("pollId", new org.apache.struts.action.ActionMessage(
					"show.poll.invalid.pollid"));
		} else {
			PollTO pto = null;
			try {
				pto = PollBO.getInstance().getPollByPollId(pollId);
			} catch (SQLException e) {
			}
			if (pto == null) {
				errors.add("pollId",
						new org.apache.struts.action.ActionMessage(
								"show.poll.invalid.pollid"));
			} else {
				if (comment == null || comment.length() < 5) {
					errors.add("comment",
							new org.apache.struts.action.ActionMessage(
									"show.poll.atleast.5chars.required"));
				}
			}

			//check if the user is putting in the same comment multiple times
			// for a given poll
			List pollComments = PollCommentsBO.getInstance()
					.getCommentsByPollAndUserId(pollId, userId);
			for (Iterator iter = pollComments.iterator(); iter.hasNext();) {
				PollCommentsTO element = (PollCommentsTO) iter.next();
				if (element.getComment().equalsIgnoreCase(comment)) {
					errors
							.add(
									"comment",
									new org.apache.struts.action.ActionMessage(
											"show.poll.duplicate.comments.not.allowed"));
					break;
				}
			}
		}
	}

	private void addComments(String pollId, String loginName, String comment,
			String remoteAddr) throws Exception {
		PollCommentsTO pcto = new PollCommentsTO();
		VCUserTO vto = null;
		vto = UserBO.getInstance().getUserByUserName(loginName);
		PollCommentsBO.getInstance().fillPollCommentsTO(pcto, vto, pollId,
				comment, remoteAddr);
		PollCommentsBO.getInstance().addCommentsForPoll(pollId, pcto);

	}
}