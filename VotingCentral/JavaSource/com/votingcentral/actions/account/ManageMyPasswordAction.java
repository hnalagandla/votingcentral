/*
 * Created on May 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.model.bo.user.PersonalConfigBO;
import com.votingcentral.model.db.dao.to.PersonalConfigTO;
import com.votingcentral.model.user.PasswordService;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.enums.PresErrorCodesEnum;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.PresentationConstants;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ManageMyPasswordAction extends VCDispatchAction {

	public static final int MAX_CHARS_IN_PASSWORD = 10;

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		DynaActionForm mForm = (DynaActionForm) form;
		String errCode = (String) mForm.get("errCode");
		String next = "mypswd";

		if (errCode != null && errCode.length() > 0) {
			try {
				int errorCode = Integer.parseInt(errCode);
				errors.add("error", new ActionMessage(PresErrorCodesEnum.get(
						errorCode).getName()));
			} catch (NumberFormatException nfe) {
				// ignore
			}
		}
		//If a message is required, save the specified key(s)
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

	public ActionForward change(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages errors = new ActionMessages();
		ActionForward forward = null; // return value
		String next = "self";
		DynaActionForm mForm = (DynaActionForm) form;
		String password = (String) mForm.get("password");
		String renteredPassword = (String) mForm.get("renteredPassword");
		int errCode = 0;
		String domContext = getDomainAndContext(request);
		validate(password, renteredPassword, request, errors);
		if (errors.size() > 0) {
			saveErrors(request, errors);
			return show(mapping, form, request, response);
		}
		try {
			String userName = VCRequestHelper.getUser(request);
			PersonalConfigTO pto = PersonalConfigBO.getInstance()
					.getUserByUserName(userName);
			// encrypt the password and store that.
			String encPass = PasswordService.getInstance().encrypt(
					password.trim());
			pto.setEncryptedPassword(encPass);
			PersonalConfigBO.getInstance().resetPasswordByUserName(pto);
			errCode = PresErrorCodesEnum.MY_VC_MANAGE_PASSWORD_UPDATE_SUCCESS
					.getId();
		} catch (Exception e) {
			log.error(e);
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
		ActionForward newFwd = new ActionForward();
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath());
		if (errCode > 0) {
			buffer.append(PresentationConstants.QUESTION_MARK);
			buffer.append(PresentationConstants.ERR_CODE);
			buffer.append(PresentationConstants.EQUALS);
			buffer.append(errCode);
		}
		newFwd.setPath(buffer.toString());
		newFwd.setName(forward.getName());
		newFwd.setRedirect(true);
		// Write logic determining how the user should be forwarded.
		return newFwd;
	}

	private void validate(String pswd, String rPswd,
			HttpServletRequest request, ActionMessages errors) {

		boolean isPasswordPresent = true;
		boolean isRePasswordPresent = true;

		if ((pswd == null) || (pswd.length() == 0)) {
			isPasswordPresent = false;
		}

		if ((rPswd == null) || (rPswd.length() == 0)) {
			isRePasswordPresent = false;
		}

		if (isPasswordPresent && !isRePasswordPresent) {
			errors
					.add("renteredPassword", new ActionMessage(
							"error.field.required",
							"Password and Re-entered Password"));
		}

		if (!isPasswordPresent && isRePasswordPresent) {
			errors
					.add("renteredPassword", new ActionMessage(
							"error.field.required",
							"Password and Re-entered Password"));
		}

		if (isPasswordPresent && isRePasswordPresent) {
			if (!rPswd.equals(pswd)) {
				errors.add("renteredPassword", new ActionMessage(
						"error.field.password.rentered.dont.match"));
			}
			if (pswd.length() > MAX_CHARS_IN_PASSWORD) {
				errors.add("password", new ActionMessage(
						"error.field.password.more.than.max.size"));
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