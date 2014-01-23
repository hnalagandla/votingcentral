/*
 * Created on Oct 3, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.votingcentral.forms.user.UserInfoForm;
import com.votingcentral.model.bo.mail.EmailBO;
import com.votingcentral.model.bo.user.PersonalConfigBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.db.dao.to.PersonalConfigTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.VCEmailTypeEnum;
import com.votingcentral.model.enums.VCUserAccountStatusEnum;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.enums.PresErrorCodesEnum;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.FastURLEncoder;
import com.votingcentral.util.url.PresentationConstants;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RegConfirmCodeAction extends DispatchAction {

	public ActionForward showEnterCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages errors = new ActionMessages();
		String next = "showEnterCode";
		UserInfoForm userform = (UserInfoForm) form;
		String userName = userform.getUserName().trim();
		VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
		if (vto != null) {
			userform.setEmailAddress(vto.getEmailAddress());
		} else {
			userform.setEmailAddress("Unknown User");
		}
		if (userform.getErrCode() != null && userform.getErrCode().length() > 0) {
			try {
				int errCode = Integer.parseInt(userform.getErrCode());
				errors.add("error", new ActionMessage(PresErrorCodesEnum.get(
						errCode).getName()));
				if (PresErrorCodesEnum.get(errCode) == PresErrorCodesEnum.USER_CONFIRM_RE_REQUEST_CONF_CODE) {
					userform.setResendConfCodeUrl(VCURLHelper
							.getResendConfCodeUrl(VCRequestHelper
									.getDomainAndContext(request), userName));
				}
			} catch (NumberFormatException nfe) {
				//ignore
			}
		}
		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		//Write logic determining how the user should be forwarded.
		log.error("Looking up forward for next :" + next);
		VCRequestHelper.dumpRequestData(request);
		ActionForward forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	public ActionForward checkCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String next = "success";
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		UserInfoForm regUserFormBean = (UserInfoForm) form;
		String userName = regUserFormBean.getUserName().trim();
		String confirmCode = regUserFormBean.getConfirmCode().trim();
		int errCode = 0;
		if ((userName == null) || (userName.length() == 0)) {
			//try to get the value from the URL
			userName = request.getParameter(RequestParameterObjects.USER_NAME);
		}

		if ((confirmCode == null) || (confirmCode.length() == 0)) {
			//try to get the value from the URL
			confirmCode = request
					.getParameter(RequestParameterObjects.CONFIRMATION_CODE);
		}

		if ((userName == null) || (userName.length() == 0)
				|| (confirmCode == null) || (confirmCode.length() == 0)) {
			//login info itself is missing, send them to password
			//recovery flow.
			userName = "Guest";
			next = "failed";
			errCode = PresErrorCodesEnum.USER_CONFIRM_INVALID_INFO.getId();
		} else {
			VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
			if (vto != null) {
				PersonalConfigTO pto = PersonalConfigBO.getInstance()
						.getUserByUserName(userName);
				String ccFromDB = pto.getEmailConfCode();
				if (ccFromDB.equals(confirmCode)) {
					UserBO.getInstance().setAccountStatusByUserName(userName,
							VCUserAccountStatusEnum.CONFIRMED);
					regUserFormBean.setEmailAddress(vto.getEmailAddress());
					next = "success";
				} else {
					next = "failed";
					errCode = PresErrorCodesEnum.USER_CONFIRM_INVALID_CONF_CODE
							.getId();
				}
			} else {
				next = "failed";
				errCode = PresErrorCodesEnum.USER_CONFIRM_INVALID_USERNAME
						.getId();
			}
		}
		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		ActionForward newForward = new ActionForward();
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath()).append("&").append(
				RequestParameterObjects.USER_NAME).append("=").append(
				FastURLEncoder.encode(userName));
		if (errCode > 0) {
			buffer.append(PresentationConstants.AMPERSAND);
			buffer.append(PresentationConstants.ERR_CODE);
			buffer.append(PresentationConstants.EQUALS);
			buffer.append(errCode);
		}
		newForward.setPath(buffer.toString());
		newForward.setName(forward.getName());
		newForward.setRedirect(true);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (newForward);
	}

	/**
	 * Resend confirmation email
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reConfirm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = null; // return value
		ActionForward newForward = new ActionForward(); // return value
		UserInfoForm regUserFormBean = (UserInfoForm) form;

		String next = "enterConfCode";
		String message = "";
		String userName = VCRequestHelper.getUser(request);
		if (userName.equalsIgnoreCase("guest")) {
			//the user is not logged in
			userName = regUserFormBean.getUserName();
		}
		try {
			VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
			if (vto != null
					&& vto.getAccountStatus().equalsIgnoreCase(
							VCUserAccountStatusEnum.CREATED)) {
				PersonalConfigTO pto = PersonalConfigBO.getInstance()
						.getUserByUserName(userName);
				String confirmURL = VCURLHelper.getUserRegConfCodeUrlInEmail(
						VCRequestHelper.getDomainAndContext(request), userName,
						pto.getEmailConfCode());
				// send email
				List toAddresses = new ArrayList();
				toAddresses.add(vto.getEmailAddress());
				Object[] values = { vto.getEmailAddress(),
						pto.getEmailConfCode(), confirmURL };
				EmailBO.getInstance().createMailRequest(toAddresses,
						VCEmailTypeEnum.REG_CONFIRM_CODE, values, 0);
				errors.add("userName", new ActionMessage(
						"reg.newuser.re.request.confirmation.code.email.sent",
						vto.getEmailAddress()));
				regUserFormBean.setUserName(userName);
			}
		} catch (SQLException e) {
			log.error("sending email conf code to user", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath()).append("&").append(
				RequestParameterObjects.USER_NAME).append("=").append(
				FastURLEncoder.encode(userName));
		buffer.append(PresentationConstants.AMPERSAND);
		buffer.append(PresentationConstants.ERR_CODE);
		buffer.append(PresentationConstants.EQUALS);
		buffer.append(PresErrorCodesEnum.USER_CONFIRM_RE_REQUEST_CONF_CODE_SENT
				.getId());
		newForward.setPath(buffer.toString());
		newForward.setName(forward.getName());
		newForward.setRedirect(true);
		// Finish with
		return newForward;
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
		return showEnterCode(mapping, form, request, response);
	}
}