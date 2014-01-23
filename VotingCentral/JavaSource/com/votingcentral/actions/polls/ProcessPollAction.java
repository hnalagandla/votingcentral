/*
 * Created on Aug 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.polls;

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
import com.votingcentral.forms.polls.ProcessPollFormBean;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.enums.PollsStatusEnum;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.enums.PresErrorCodesEnum;
import com.votingcentral.util.request.VCRequestHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ProcessPollAction extends VCDispatchAction {

	private final static Log log = LogFactory.getLog(ProcessPollAction.class);

	private final String datePattern = "MM/dd/yyyy 'at' hh:mm:ss a z";

	public ActionForward createPoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String next = "";
		ProcessPollFormBean pollFormBean = (ProcessPollFormBean) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String loginName = VCRequestHelper.getUser(request);
		long userId = UserBO.getInstance().getUserByUserName(loginName)
				.getUserId();

		List unFinishedPollIds = PollBO.getInstance()
				.getPollIdsByUserAndStatus(userId, PollsStatusEnum.UNFINISHED);
		if ((unFinishedPollIds == null) || unFinishedPollIds.size() == 0) {
			next = "createNewPoll";
		} else {
			next = "unFinished";
			List pollwtos = PollHelper.getDisplayablePolls(
					getDomainAndContext(request), unFinishedPollIds,
					PollHelper.POLLS_MAX_NAME_LENGTH, new UnSyncStringBuffer());
			pollFormBean.setUnFinishedPolls(pollwtos);
			if (pollFormBean.getErrCode() != null
					&& pollFormBean.getErrCode().length() > 0) {
				try {
					int errCode = Integer.parseInt(pollFormBean.getErrCode());
					errors.add("error", new ActionMessage(PresErrorCodesEnum
							.get(errCode).getName()));
				} catch (NumberFormatException nfe) {
					// ignore
				}
			}
		}
		saveErrors(request, errors);
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	public ActionForward createNewPoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String next = "createNewPoll";
		ActionForward forward = new ActionForward(); // return value
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
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

		return createPoll(mapping, form, request, response);
	}
}