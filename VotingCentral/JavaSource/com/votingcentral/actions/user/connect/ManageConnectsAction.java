/*
 * Created on Aug 5, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.user.connect;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.user.connect.ManageConnectsForm;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.user.links.VCUserLinksBO;
import com.votingcentral.model.db.dao.to.VCUserLinksTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.pres.web.to.VCUserWTO;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ManageConnectsAction extends VCDispatchAction {

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String next = "manageConnects";
		ManageConnectsForm connectForm = (ManageConnectsForm) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value

		String userName = VCRequestHelper.getUser(request);
		String domContext = VCRequestHelper.getDomainAndContext(request);

		VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
		List pendRec = VCUserLinksBO.getInstance()
				.getPendingRequestsUsersReceivedUserId(vto.getUserId());
		List dispPendRec = UserBO.getInstance().getDisplayableUsers(pendRec,
				domContext);
		setAcceptRejectUrls(domContext, dispPendRec, vto.getUserName());
		connectForm.setRequestsRecievedPending(dispPendRec);

		List pendSent = VCUserLinksBO.getInstance()
				.getPendingRequestsUsersSentUserId(vto.getUserId());
		List dispPendSent = UserBO.getInstance().getDisplayableUsers(pendSent,
				domContext);
		connectForm.setRequestsSentPending(dispPendSent);

		List friends = VCUserLinksBO.getInstance().getDirectFriendsByUserId(
				vto.getUserId());
		List dispFriends = UserBO.getInstance().getDisplayableUsers(friends,
				domContext);
		connectForm.setFriends(dispFriends);

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

	private void setAcceptRejectUrls(String domContext, List vcUserWtos,
			String loggedInUser) throws SQLException {
		if (vcUserWtos == null) {
			return;
		}
		for (Iterator iter = vcUserWtos.iterator(); iter.hasNext();) {
			VCUserWTO element = (VCUserWTO) iter.next();
			VCUserLinksTO lto = VCUserLinksBO.getInstance()
					.getDirectLinkByUserName(element.getUserName(),
							loggedInUser);
			String acceptUrl = VCURLHelper.getVCConnectAcceptedOrRejectedUrl(
					domContext, lto.getAcceptCode());
			String rejectUrl = VCURLHelper.getVCConnectAcceptedOrRejectedUrl(
					domContext, lto.getRejectCode());

			element.setConnectAcceptUrl(acceptUrl);
			element.setConnectRejectUrl(rejectUrl);
			element.setConnectComments(lto.getLinkComments());
		}
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