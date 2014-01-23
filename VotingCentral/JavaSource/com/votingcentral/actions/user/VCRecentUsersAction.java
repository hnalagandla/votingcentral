/*
 * Created on Oct 28, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.HomePageFormBean;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.vaco.VCVacoPointsBO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCRecentUsersAction extends VCDispatchAction {
	private static int MAX_RECENT_USERS = 50;

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String domContext = getDomainAndContext(request);
		String next = "recentUsers";
		HomePageFormBean homeForm = (HomePageFormBean) form;
		ActionForward forward = new ActionForward(); // return value
		// Write logic determining how the user should be forwarded.
		List mostActiveUsers = VCVacoPointsBO.getInstance()
				.getUsersWithHighestPoints(MAX_RECENT_USERS);
		List dispMostActiveUsers = UserBO.getInstance().getDisplayableUsers(
				mostActiveUsers, domContext);
		homeForm.setMostActiveUsers(dispMostActiveUsers);
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