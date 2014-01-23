/*
 * Created on Dec 10, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.cookies.VCCookie;
import com.votingcentral.cookies.VCCookieConstants;
import com.votingcentral.cookies.VCCookieHandler;
import com.votingcentral.cookies.VCCookielet;
import com.votingcentral.cookies.VCCookieletEnum;
import com.votingcentral.forms.VCLoginForm;
import com.votingcentral.model.bo.roles.VCUserRolesBO;
import com.votingcentral.model.bo.user.PersonalConfigBO;
import com.votingcentral.model.db.dao.to.PersonalConfigTO;
import com.votingcentral.model.db.dao.to.VCUserRolesTO;
import com.votingcentral.model.enums.VCUserRolesEnum;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.model.user.PasswordService;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.enums.PresErrorCodesEnum;
import com.votingcentral.util.url.PresentationConstants;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCLoginAction extends VCDispatchAction {
	private static Log log = LogFactory.getLog(VCLoginAction.class);

	public ActionForward showLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages errors = new ActionMessages();
		String domContext = getDomainAndContext(request);
		VCLoginForm loginForm = (VCLoginForm) form;
		loginForm
				.setRegistrationUrl(VCURLHelper.getRegistrationUrl(domContext));
		loginForm
				.setForgotPswdUrl(VCURLHelper.getForgotPasswordUrl(domContext));
		if (loginForm.getErrCode() != null
				&& loginForm.getErrCode().length() > 0) {
			try {
				int errCode = Integer.parseInt(loginForm.getErrCode());
				errors.add("error", new ActionMessage(PresErrorCodesEnum.get(
						errCode).getName()));
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}
		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return mapping.findForward("login");
	}

	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String next = "failed";
		ActionMessages errors = new ActionMessages();
		String domContext = getDomainAndContext(request);
		VCLoginForm loginForm = (VCLoginForm) form;
		String loginSuccessRu = loginForm.getLsru();
		//if no URL set take them to My VotingCentral
		if (loginSuccessRu == null || loginSuccessRu.length() == 0) {
			loginSuccessRu = VCURLHelper.getMyVCMainUrl(domContext);
		}
		loginForm
				.setRegistrationUrl(VCURLHelper.getRegistrationUrl(domContext));
		loginForm
				.setForgotPswdUrl(VCURLHelper.getForgotPasswordUrl(domContext));

		String userName = loginForm.getUserName();
		String password = loginForm.getPassword();

		if (userName != null && userName.length() > 0 && password != null
				&& password.length() > 0) {
			String paswdHash = PasswordService.getInstance().encrypt(
					password.trim());
			PersonalConfigTO pto = PersonalConfigBO.getInstance()
					.getUserByUserName(userName.trim());
			if (pto != null) {
				log.error("Comparing :" + pto.getEncryptedPassword()
						+ ": with :" + paswdHash + ": for user:"
						+ userName.trim() + " password in clear is :"
						+ password + ": trimmed password is:" + password.trim()
						+ ": ");
			} else {
				log.error("pto is null for:" + userName + ":");
			}
			if (pto != null && pto.getEncryptedPassword().equals(paswdHash)) {
				next = "success";
				log.error("login sucess for :" + userName + ":");
				VCCookie vcCookie = VCCookieHandler.getCookie(request);
				VCCookielet login = new VCCookielet(VCCookieletEnum.LOGIN
						.getName(),
						VCCookieConstants.LOGIN_SUCCESS_COOKIELET_VALUE,
						PollTimeHelper.getInstance().getFutureDateFromDate(
								PollTimeHelper.getInstance().getCurrentDate(),
								5));
				VCCookielet user = new VCCookielet(VCCookieletEnum.USERNAME
						.getName(), userName, null);
				VCCookielet loginIp = new VCCookielet(VCCookieletEnum.LOGIN_IP
						.getName(), request.getRemoteAddr(), null);
				VCCookieHandler.setCookielet(vcCookie, login);
				VCCookieHandler.setCookielet(vcCookie, user);
				VCCookieHandler.setCookielet(vcCookie, loginIp);
				VCCookieHandler.addCookie(response, vcCookie);

				//now check if the user has access to this specific page.
				List roles = VCUserRolesBO.getInstance().getUserRoles(userName);
				List onlyRoles = new ArrayList();
				for (Iterator i = roles.iterator(); i.hasNext();) {
					VCUserRolesTO vto = (VCUserRolesTO) i.next();
					onlyRoles.add(vto.getRole());
				}
				ActionForward newForward = null;
				if (onlyRoles == null
						|| onlyRoles.size() == 0
						|| (VCURLHelper.isAdminUrl(loginSuccessRu) && !onlyRoles
								.contains(VCUserRolesEnum.ADMIN.getName()))) {
					String insufficientAccessUrl = VCURLHelper
							.getInsuffcientAccessUrl(domContext);
					log.error("redirecting to insuffcient access URL for :"
							+ userName + ": Url:" + insufficientAccessUrl);
					newForward = new ActionForward(insufficientAccessUrl, true);
				} else {
					log.error("redirecting to login success :" + userName
							+ ": Url:" + loginSuccessRu);
					newForward = new ActionForward(loginSuccessRu, true);
				}
				return newForward;
			}
		}
		log.error("redirecting to password doesn't match :" + userName + ":");
		ActionForward forward = mapping.findForward(next);
		ActionForward newFwd = new ActionForward();
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath());
		buffer.append(PresentationConstants.QUESTION_MARK);
		buffer.append(PresentationConstants.ERR_CODE);
		buffer.append(PresentationConstants.EQUALS);
		buffer.append(PresErrorCodesEnum.LOGIN_INVALID_LOGIN.getId());
		newFwd.setPath(buffer.toString());
		newFwd.setName(forward.getName());
		newFwd.setRedirect(true);

		if (errors.size() > 0) {
			saveErrors(request, errors);
		}
		return newFwd;
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
		return showLogin(mapping, form, request, response);
	}
}