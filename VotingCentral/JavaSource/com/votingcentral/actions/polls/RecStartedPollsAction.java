/*
 * Created on Oct 5, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.polls;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.polls.RecStartedPollsFormBean;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.util.UnSyncStringBuffer;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RecStartedPollsAction extends VCDispatchAction {

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		ActionForward forward = new ActionForward(); // return value
		String next = "recStartedPolls";
		RecStartedPollsFormBean recStartedForm = (RecStartedPollsFormBean) form;
		List mvp = PollBO.getInstance().getRecentlyStartedPolls();
		List pollwtos = PollHelper.getDisplayablePolls(
				getDomainAndContext(request), mvp,
				PollHelper.POLLS_MAX_NAME_LENGTH, buffer);
		PollHelper.setTimeAgoStr(pollwtos, true);
		recStartedForm.setRecStartedPolls(pollwtos);
		String keywords = buffer + ","
				+ recStartedForm.getCommaSeparatedKeywords();
		recStartedForm.setCommaSeparatedKeywords(keywords);
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
		return show(mapping, form, request, response);
	}
}