/*
 * Created on Jul 27, 2007
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
import com.votingcentral.model.bo.vaco.VCVacoPointsBO;
import com.votingcentral.model.db.dao.to.VCUserLinksTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.VCActivityTypeEnum;
import com.votingcentral.model.enums.VCUserLinkStateEnum;
import com.votingcentral.util.request.VCRequestHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ConnectAcceptRejectAction extends VCDispatchAction {

	public ActionForward acceptOrReject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String next = "connectAcceptRejectShow";
		RequestConnectForm connectForm = (RequestConnectForm) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		validate(request, connectForm, errors);
		if (errors.size() > 0) {
			//if there are errors, disable the send button.
			connectForm.setButtonDisabled(true);
		}
		String code = connectForm.getCode();
		VCUserLinksTO vulto = VCUserLinksBO.getInstance()
				.findByRejectORAcceptCode(code);
		VCUserLinkStateEnum currLinkState = vulto.getLinkStateEnum();
		if (vulto == null) {
			errors.add("code", new org.apache.struts.action.ActionMessage(
					"vc.users.link.code.invalid"));
		} else {
			if (code.equals(vulto.getAcceptCode())) {
				vulto.setLinkStateEnum(VCUserLinkStateEnum.ACCEPTED);
				errors
						.add("code",
								new org.apache.struts.action.ActionMessage(
										"vc.users.connect.accepted", vulto
												.getSourceUserName()));

			} else if (code.equals(vulto.getRejectCode())) {
				vulto.setLinkStateEnum(VCUserLinkStateEnum.REJECTED);
				errors
						.add("code",
								new org.apache.struts.action.ActionMessage(
										"vc.users.connect.rejected", vulto
												.getSourceUserName()));
			} else {
				//this should never happen.
			}
			String actingUser = VCRequestHelper.getUser(request);
			VCUserTO actingUserTo = UserBO.getInstance().getUserByUserName(
					actingUser);
			//check and make sure that the guy accepting this request
			//is indeed the destination user.
			if (actingUserTo.getEmailAddress().equalsIgnoreCase(
					vulto.getDestUserEmail())) {
				vulto.setDestUserName(actingUser);
				vulto.setDestUserId(actingUserTo.getUserId());
				VCUserLinksBO.getInstance().updateLink(vulto);
				//increment vaco points only if the current link state is
				//inititated, no points, for further actions.
				if (currLinkState == VCUserLinkStateEnum.INITIATED) {
					VCVacoPointsBO.getInstance().incrementPoints(
							vulto.getDestUserId(),
							VCActivityTypeEnum.VC_POINTS_CONNECT_ACCEPT_REJECT);
				}
			}

		}
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

	private void validate(HttpServletRequest request,
			RequestConnectForm linkForm, ActionMessages errors)
			throws SQLException {
		String code = linkForm.getCode();
		if ((code == null || code.length() == 0)) {
			errors.add("code", new org.apache.struts.action.ActionMessage(
					"vc.users.link.code.invalid"));
			return;
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
		return acceptOrReject(mapping, form, request, response);
	}
}