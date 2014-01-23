/*
 * Created on May 26, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.user.connect;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.user.connect.RequestConnectForm;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.user.links.VCUserLinksBO;
import com.votingcentral.model.db.dao.to.VCUserLinksTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.util.request.VCRequestHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RequestConnectAction extends VCDispatchAction {

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String next = "connectRequestShow";
		RequestConnectForm connectForm = (RequestConnectForm) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value

		boolean isFromShow = true;
		validate(request, connectForm, errors, isFromShow);
		if (errors.size() > 0) {
			//if there are errors, disable the send button.
			connectForm.setButtonDisabled(true);
		}
		connectForm
				.setConnectComments("I'd like to add you to my network on VotingCentral.");
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

	public ActionForward send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String next = "connectRequestSent";
		RequestConnectForm connectForm = (RequestConnectForm) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value

		String linkToUserName = connectForm.getConnectToUserName();
		String linkToEmail = connectForm.getConnectToEmail();
		String requesterUserName = VCRequestHelper.getUser(request);

		validate(request, connectForm, errors, false);
		if (errors == null || errors.size() == 0) {
			VCUserTO linkToUserNameTo = UserBO.getInstance().getUserByUserName(
					linkToUserName);
			VCUserTO requesterUserNameTo = UserBO.getInstance()
					.getUserByUserName(requesterUserName);
			String domContext = VCRequestHelper.getDomainAndContext(request);
			if (linkToUserNameTo != null) {
				VCUserLinksBO.getInstance().sendInviteToLinkVCUsers(
						requesterUserNameTo, linkToUserNameTo, domContext,
						connectForm.getConnectComments());
			} else {
				VCUserLinksBO.getInstance().sendInviteToLinkVCAndNewUsers(
						requesterUserNameTo, linkToEmail, domContext,
						connectForm.getConnectComments());
			}
		} else {
			//there are errors in the request.
			next = "connectRequestShow";
		}
		//		 If a message is required, save the specified key(s)
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

	private void validate(HttpServletRequest request,
			RequestConnectForm linkForm, ActionMessages errors,
			boolean isFromShow) throws SQLException {
		String linkToUserName = linkForm.getConnectToUserName();
		String linkToEmail = linkForm.getConnectToEmail();

		if (!isFromShow) {
			if ((linkToUserName == null || linkToUserName.length() == 0)
					&& ((linkToEmail == null) || linkToEmail.length() == 0)) {
				errors.add("linkToUserName",
						new org.apache.struts.action.ActionMessage(
								"vc.users.link.invalid.linkto"));
				return;
			}
		}
		VCUserTO vtoLinkToUser = UserBO.getInstance()
				.getUserByUserNameOrEmailAddress(linkToUserName, linkToEmail);
		if (vtoLinkToUser != null) {
			// the link to user has an account in VotingCentral
			//check if a link exists between these 2 users already.
			String requesterUserName = VCRequestHelper.getUser(request);
			if (requesterUserName.equalsIgnoreCase(vtoLinkToUser.getUserName())) {
				//the user is trying to link to self
				//show an error.
				errors.add("linkToUserName",
						new org.apache.struts.action.ActionMessage(
								"vc.users.link.invalid.self"));
				return;
			}
			VCUserTO vtoLinkRequester = UserBO.getInstance().getUserByUserName(
					requesterUserName);
			VCUserLinksTO vulto = VCUserLinksBO.getInstance()
					.getDirectLinkByUserNameOrEmail(requesterUserName,
							linkToUserName, vtoLinkRequester.getEmailAddress(),
							linkToEmail);
			if (vulto != null) {
				//some kind of link already exists between these 2 users.
				//check the type of link and decide whether to send
				//another email or not.
				errors.add("linkToUserName",
						new org.apache.struts.action.ActionMessage(
								"vc.users.link.requested.curr.state",
								linkToUserName, vulto.getLinkStateEnum()
										.getName()));
			}
		} else {
			// this is a new user, show a page, they entered a
			//invalid username, if email is not present.
			if (linkToUserName != null && linkToUserName.length() > 0) {
				errors.add("linkToUserName",
						new org.apache.struts.action.ActionMessage(
								"vc.users.link.invalid.linkto"));
			}
			if (!isFromShow) {
				if (linkToEmail == null
						|| !PollHelper.isValidEmail(linkToEmail)) {
					errors.add("linkToUserName",
							new org.apache.struts.action.ActionMessage(
									"vc.users.link.invalid.linkto"));
					return;
				}
			}
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