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
import com.votingcentral.forms.polls.SubmitPollFormBean;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.services.bitly.BitlyService;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SubmitPollAction extends VCDispatchAction {

	public ActionForward submitPoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		SubmitPollFormBean submitPollFormBean = (SubmitPollFormBean) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "submitPoll";
		String pollId = submitPollFormBean.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
		submitPollFormBean.setPollName(pto.getPollName());
		submitPollFormBean.setDisplayPollUrl(VCURLHelper.getDisplayPollUrl(
				getDomainAndContext(request), pollId));
		submitPollFormBean.setContestsMainUrl(VCURLHelper
				.getContestsMainUrl(getDomainAndContext(request)));
		submitPollFormBean.setCreateMorePollsUrl(VCURLHelper
				.getCreatePollUrl(getDomainAndContext(request)));
		submitPollFormBean.setHomePageUrl(VCURLHelper
				.getHomePageUrl(getDomainAndContext(request)));
		submitPollFormBean.setMyVCUrl(VCURLHelper
				.getMyVCMainUrl(getDomainAndContext(request)));
		submitPollFormBean.setTwitterUpdateUrl(VCURLHelper
				.getTwitterUpdateUrl(pto.getPollName(), BitlyService
						.getInstance().shorten(
								VCURLHelper.getDisplayPollUrl(
										getDomainAndContext(request), pto
												.getPollId()))));
		submitPollFormBean.setYahooBuzzUpUrl(VCURLHelper
				.getYahooBuzzUpUrl(VCURLHelper.getDisplayPollUrl(
						getDomainAndContext(request), pollId)));
		submitPollFormBean.setFacebookShareUrl(VCURLHelper.getFacebookShareUrl(
				VCURLHelper.getDisplayPollUrl(getDomainAndContext(request),
						pollId), pto.getPollName()));
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	} /*
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

		return submitPoll(mapping, form, request, response);
	}
}