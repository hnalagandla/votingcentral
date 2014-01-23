/*
 * Created on Oct 3, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.user.UserInfoForm;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.vaco.VCVacoPointsBO;
import com.votingcentral.model.db.dao.to.TextLinkDescTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.VCActivityTypeEnum;
import com.votingcentral.model.enums.VCUserAccountStatusEnum;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.url.FastURLEncoder;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RegConfirmSuccessAction extends VCDispatchAction {

	public ActionForward showSuccess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward newForward = new ActionForward();
		String next = "showSuccess";
		UserInfoForm userform = (UserInfoForm) form;
		if (userform.getUserName() != null) {
			VCUserTO vto = UserBO.getInstance().getUserByUserName(
					userform.getUserName());
			if (vto.getAccountStatus()
					.equals(VCUserAccountStatusEnum.CONFIRMED)) {
				userform.setEmailAddress(vto.getEmailAddress());
				setSuccessPageUrls(userform, vto, getDomainAndContext(request));
				newForward = mapping.findForward(next);
				//increment vaco points
				VCVacoPointsBO.getInstance().incrementPoints(vto.getUserId(),
						VCActivityTypeEnum.VC_POINTS_REGISTER);

			} else {
				// redirect them back to the confirmation page
				next = "enterConfCode";
				// Write logic determining how the user should be forwarded.
				ActionForward forward = mapping.findForward(next);
				UnSyncStringBuffer buffer = new UnSyncStringBuffer();
				buffer.append(forward.getPath()).append("&").append(
						RequestParameterObjects.USER_NAME).append("=").append(
						FastURLEncoder.encode(vto.getDisplayUserName()));
				newForward.setPath(buffer.toString());
				newForward.setName(forward.getName());
				newForward.setRedirect(true);
			}
		} else {
			next = "register";
			// Write logic determining how the user should be forwarded.
			newForward = mapping.findForward(next);
		}

		// Finish with
		return (newForward);
	}

	private void setSuccessPageUrls(UserInfoForm form, VCUserTO vto,
			String domContext) {
		List successPageUrls = new ArrayList();

		TextLinkDescTO tld1 = new TextLinkDescTO();
		tld1.setDesc("Create a Poll");
		tld1.setText("Create a Poll");
		tld1.setHref(VCURLHelper.getCreatePollUrl(domContext));

		TextLinkDescTO tld2 = new TextLinkDescTO();
		tld2.setDesc("My VotingCentral");
		tld2.setText("My VotingCentral");
		tld2.setHref(VCURLHelper.getMyVCMainUrl(domContext));

		TextLinkDescTO tld3 = new TextLinkDescTO();
		tld3.setDesc("Home");
		tld3.setText("Home");
		tld3.setHref(VCURLHelper.getHomePageUrl(domContext));

		TextLinkDescTO tld4 = new TextLinkDescTO();
		tld4.setDesc("User Profile Page");
		tld4.setText("User Profile Page");
		tld4.setHref(VCURLHelper.getVCUserPublicProfileUrl(domContext, vto
				.getUserName()));

		successPageUrls.add(tld1);
		successPageUrls.add(tld2);
		successPageUrls.add(tld3);
		successPageUrls.add(tld4);

		form.setSuccessPageUrls(successPageUrls);
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
		// TODO Auto-generated method stub
		return showSuccess(mapping, form, request, response);
	}
}