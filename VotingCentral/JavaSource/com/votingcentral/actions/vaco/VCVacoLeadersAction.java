/*
 * Created on Aug 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.vaco;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.vaco.VCVacoLeadersForm;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.vaco.VCVacoPointsBO;
import com.votingcentral.util.request.VCRequestHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCVacoLeadersAction extends VCDispatchAction {

	private static int MAX_ACTIVE_USERS = 10;

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String next = "vcLeaders";
		VCVacoLeadersForm leadersForm = (VCVacoLeadersForm) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String domContext = VCRequestHelper.getDomainAndContext(request);
		List mostActiveUsers = VCVacoPointsBO.getInstance()
				.getUsersWithHighestPoints(MAX_ACTIVE_USERS);
		List dispMostActiveUsers = UserBO.getInstance().getDisplayableUsers(
				mostActiveUsers, domContext);
		leadersForm.setCurrLeaders(dispMostActiveUsers);

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