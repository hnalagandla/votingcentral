package com.votingcentral.actions.user;

import java.sql.SQLException;
import java.util.ArrayList;
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
import org.apache.struts.action.DynaActionForm;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.model.bo.mail.EmailBO;
import com.votingcentral.model.bo.user.PersonalConfigBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.db.dao.to.PersonalConfigTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.VCEmailTypeEnum;
import com.votingcentral.util.url.VCURLHelper;

public class PasswdReminderAction extends VCDispatchAction {
	private static Log log = LogFactory.getLog(PasswdReminderAction.class);

	/**
	 *  
	 */
	public ActionForward getSecQuestion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages errors = new ActionMessages();
		try {
			DynaActionForm mForm = (DynaActionForm) form;
			String userName = (String) mForm.get("loginName");

			validateGetQues(mForm, errors);
			if (errors.size() > 0) {
				saveErrors(request, errors);
				return mapping.findForward("forgotPswd");
			}
			PersonalConfigTO pto = PersonalConfigBO.getInstance()
					.getUserByUserName(userName);
			mForm.set("securityQuestion", pto.getSecurityQuestion());
		} catch (Exception e) {
			log.error(e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		return mapping.findForward("forgotPswd");
	}

	private void validateGetQues(DynaActionForm mForm, ActionMessages errors) {
		String userName = (String) mForm.get("userName");
		if (userName == null || userName.length() == 0) {
			errors.add("loginName", new org.apache.struts.action.ActionMessage(
					"error.field.required", "Voting Central User Name"));
		}
		if (userName != null && userName.length() > 0) {
			try {
				VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
				if (vto == null) {
					errors.add("userName",
							new org.apache.struts.action.ActionMessage(
									"forgot.password.account.not.found"));
				}
			} catch (SQLException e) {
				errors.add("userName",
						new org.apache.struts.action.ActionMessage(
								"forgot.password.account.not.found"));
			}
		}
	}

	public ActionForward sendTempPswd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages errors = new ActionMessages();
		DynaActionForm mForm = (DynaActionForm) form;
		String userName = (String) mForm.get("userName");
		try {
			validateSendTemp(mForm, errors);
			if (errors.size() > 0) {
				saveErrors(request, errors);
				return mapping.findForward("forgotPswd");
			}
			VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
			String newPass = resetPassword(vto.getUserId());
			String myVCURL = VCURLHelper
					.getMyVCMainUrl(getDomainAndContext(request));
			sendEmail(vto.getEmailAddress(), vto.getDisplayUserName(), newPass,
					myVCURL);
		} catch (Exception e) {
			log.error(e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
			return mapping.findForward("forgotPswd");
		}
		return mapping.findForward("success");
	}

	private void validateSendTemp(DynaActionForm mForm, ActionMessages errors) {
		String userName = (String) mForm.get("userName");
		if (userName == null || userName.length() == 0) {
			errors.add("userName", new org.apache.struts.action.ActionMessage(
					"error.field.required", "User Name"));
			return;
		}
		String birthYear = (String) mForm.get("birthYear");
		if (birthYear == null || birthYear.length() == 0) {
			errors.add("birthYear", new org.apache.struts.action.ActionMessage(
					"error.field.required", "Birth Year"));
			return;
		}
		//		String zipCode = (String) mForm.get("zipCode");
		//		if (zipCode == null || zipCode.length() == 0) {
		//			errors.add("zipCode", new org.apache.struts.action.ActionMessage(
		//					"error.field.required", "Zip Code"));
		//			return;
		//		}
		String emailAddress = (String) mForm.get("emailAddress");
		if (emailAddress == null || emailAddress.length() == 0) {
			errors.add("emailAddress",
					new org.apache.struts.action.ActionMessage(
							"error.field.required", "Email Address"));
			return;
		}
		if (userName != null && userName.length() > 0) {
			try {
				VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
				if (vto == null) {
					errors.add("userName",
							new org.apache.struts.action.ActionMessage(
									"forgot.password.account.not.found"));
				} else {
					if ((!vto.getBirthYear().equals(birthYear))
							|| (!vto.getEmailAddress().equals(emailAddress))) {
						errors.add("userName",
								new org.apache.struts.action.ActionMessage(
										"forgot.password.info.no.match"));
					}
				}
			} catch (SQLException e) {
				errors.add("userName",
						new org.apache.struts.action.ActionMessage(
								"forgot.password.account.not.found"));
			}
		}
	}

	/**
	 * 
	 * @param emailAddress
	 * @return
	 * @throws Exception
	 */
	private String resetPassword(long userId) throws Exception {
		return PersonalConfigBO.getInstance().resetAndGetPasswordByUserName(
				userId);
	}

	/**
	 * 
	 * @param toEmail
	 * @param newPass
	 * @return
	 */
	private boolean sendEmail(String toEmail, String displayUserName,
			String newPass, String myVCURL) {
		List toAddresses = new ArrayList();
		toAddresses.add(toEmail);
		Object[] values = { toEmail, displayUserName, newPass, myVCURL };
		try {
			EmailBO.getInstance().createMailRequest(toAddresses,
					VCEmailTypeEnum.PASSWORD_REMINDER, values, 0);
		} catch (SQLException e) {
			log.fatal("failure creatng email for pswd reminder ", e);
		}
		return true;
	}

}