/*
 * Created on Mar 28, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.polls;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.polls.ShowPollFormBean;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.PollsStatusEnum;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DeletePollAction extends VCDispatchAction {

	public ActionForward deletePoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ShowPollFormBean submitPollFormBean = (ShowPollFormBean) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "createAPoll";
		String pollId = submitPollFormBean.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
		if (pto != null) {
			String loggedInUser = VCRequestHelper.getUser(request);
			VCUserTO vto = UserBO.getInstance().getUserByUserName(loggedInUser);
			if (pto.getUserId() == vto.getUserId()
					&& pto.getPollStatus().equalsIgnoreCase(
							PollsStatusEnum.UNFINISHED.getName())) {
				PollBO.getInstance().deletePoll(pollId);
			}
		}

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

		return deletePoll(mapping, form, request, response);
	}
}