/*
 * Created on Feb 20, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.account;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.user.UserInfoForm;
import com.votingcentral.model.bo.location.LocationBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.util.request.VCRequestHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MyVCAccountAction extends VCDispatchAction {

	public ActionForward showAccountInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "myaccount";
		UserInfoForm myAcctForm = (UserInfoForm) form;
		try {
			String userName = VCRequestHelper.getUser(request);
			VCUserTO vcUser = UserBO.getInstance().getUserByUserName(userName);

			myAcctForm.setEmailAddress(vcUser.getEmailAddress());
			myAcctForm.setUserName(userName);

			myAcctForm.setBirthDay(vcUser.getBirthDay());
			myAcctForm.setBirthMonth(vcUser.getBirthMonth());
			myAcctForm.setBirthYear(vcUser.getBirthYear());

			myAcctForm.setFirstName(vcUser.getFirstName());
			myAcctForm.setMiddleInitial(vcUser.getMiddleInitial());
			myAcctForm.setMiddleName(vcUser.getMiddleName());
			myAcctForm.setLastName(vcUser.getLastName());
			myAcctForm.setGender(vcUser.getGender());

			myAcctForm.setMailingAddress1(vcUser.getMailingAddress1());
			myAcctForm.setMailingAddress2(vcUser.getMailingAddress2());
			myAcctForm.setStateId(vcUser.getStateId());
			myAcctForm.setCity(vcUser.getCity());
			myAcctForm.setCountryId(vcUser.getCountryId());
			myAcctForm.setZipCode1(vcUser.getZipCode1());
			myAcctForm.setZipCode2(vcUser.getZipCode2());

			myAcctForm.setNickName(vcUser.getDisplayUserName());

			myAcctForm.setPhoneAreaCode(vcUser.getPhoneAreaCode());
			myAcctForm.setPhoneCountryCode(vcUser.getPhoneCountryCode());
			myAcctForm.setPhoneNum1(vcUser.getPhoneNum1());
			myAcctForm.setPhoneNum2(vcUser.getPhoneNum2());

			myAcctForm.setListOfCountries(LocationBO.getInstance()
					.getListOfCountries());
			myAcctForm.setListOfSecurityQuestions(UserBO.getInstance()
					.getListOfSecurityQuestionsForView());
			myAcctForm.setStatesForCountry(LocationBO.getInstance()
					.getStatesByCountry(vcUser.getCountryId()));

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
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	public ActionForward editAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward forward = new ActionForward(); // return value
		UserInfoForm myAcctForm = (UserInfoForm) form;
		ActionMessages errors = new ActionMessages();
		validate(myAcctForm, errors);
		if (errors.size() > 0) {
			saveErrors(request, errors);
			return showAccountInfo(mapping, form, request, response);
		}
		try {
			String userName = VCRequestHelper.getUser(request);
			VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
			fillUserInfoTOs(myAcctForm, vto);
			updateUser(vto);
			errors
					.add("name", new ActionMessage(
							"user.account.update.success"));
			myAcctForm.setPassword("");
			myAcctForm.setRenteredPassword("");
		} catch (Exception e) {
			log.error("Exception trying to update my account information", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("user.update.exception"));
		}
		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// take them back to the showaccount page, so taht they can see that
		// their
		// data is updated.
		return showAccountInfo(mapping, form, request, response);
	}

	private void fillUserInfoTOs(UserInfoForm form, VCUserTO vto)
			throws Exception {
		// set only information that has changed.
		if (form.getBirthDay() != null && form.getBirthDay().length() > 0) {
			vto.setBirthDay(form.getBirthDay());
		}
		if (form.getBirthMonth() != null && form.getBirthMonth().length() > 0) {
			vto.setBirthMonth(form.getBirthMonth());
		}
		if (form.getBirthYear() != null && form.getBirthYear().length() > 0) {
			vto.setBirthYear(form.getBirthYear());
		}
		if (form.getGender() != null && form.getGender().length() > 0) {
			vto.setGender(form.getGender());
		}
		if (form.getZipCode1() != null && form.getZipCode1().length() > 0) {
			vto.setZipCode1(form.getZipCode1());
		}
		if (form.getZipCode2() != null && form.getZipCode2().length() > 0) {
			vto.setZipCode2(form.getZipCode2());
		}
		if (form.getCity() != null && form.getCity().length() > 0) {
			vto.setCity(form.getCity());
		}
		if (form.getCountryId() > 0) {
			vto.setCountryId(form.getCountryId());
		}
		if (form.getStateId() > 0) {
			vto.setStateId(form.getStateId());
		}
		if (form.getFirstName() != null && form.getFirstName().length() > 0) {
			vto.setFirstName(form.getFirstName());
		}
		if (form.getLastName() != null && form.getLastName().length() > 0) {
			vto.setLastName(form.getLastName());
		}
		if (form.getMiddleInitial() != null
				&& form.getMiddleInitial().length() > 0) {
			vto.setMiddleInitial(form.getMiddleInitial());
		}
		if (form.getNickName() != null && form.getNickName().length() > 0) {
			vto.setUserName(form.getNickName());
		}
		if (form.getMailingAddress1() != null
				&& form.getMailingAddress1().length() > 0) {
			vto.setMailingAddress1(form.getMailingAddress1().trim());
		}
		if (form.getMailingAddress2() != null
				&& form.getMailingAddress2().length() > 0) {
			vto.setMailingAddress2(form.getMailingAddress2().trim());
		}
	}

	public ActionForward getStates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String next = "myaccount";
		ActionForward forward = new ActionForward(); // return value
		UserInfoForm regUserFormBean = (UserInfoForm) form;
		ActionMessages errors = new ActionMessages();

		regUserFormBean.setListOfCountries(LocationBO.getInstance()
				.getListOfCountries());
		regUserFormBean.setListOfSecurityQuestions(UserBO.getInstance()
				.getListOfSecurityQuestionsForView());
		// load state by country.
		regUserFormBean.setStatesForCountry(LocationBO.getInstance()
				.getStatesByCountry(regUserFormBean.getCountryId()));

		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	private void validate(UserInfoForm form, ActionMessages errors) {

		//
		// if ((form.getSecurityQuestion() == null)
		// || (form.getSecurityQuestion().length() == 0)) {
		// errors.add("securityQuestion",
		// new org.apache.struts.action.ActionMessage(
		// "error.field.required", "Security Question "));
		// }
		//
		// if ((form.getSecurityAnswer() == null)
		// || (form.getSecurityAnswer().length() == 0)) {
		// errors.add("securityAnswer",
		// new org.apache.struts.action.ActionMessage(
		// "error.field.required", "Security Answer "));
		// }
		//
		if ((form.getZipCode1() == null) || (form.getZipCode1().length() == 0)) {
			errors.add("zipCode1", new org.apache.struts.action.ActionMessage(
					"error.field.required", "Zip/PIN Code "));
		}

		if ((form.getCity() == null) || (form.getCity().length() == 0)) {
			errors.add("city", new org.apache.struts.action.ActionMessage(
					"error.field.required", "City "));
		}

		if (form.getCountryId() == 0) {
			errors.add("country", new org.apache.struts.action.ActionMessage(
					"error.field.required", "Country "));
		}

		if ((form.getStateId() == 0)) {
			errors.add("state", new org.apache.struts.action.ActionMessage(
					"error.field.required", "State "));
		}

		if ((form.getGender() == null) || (form.getGender().length() == 0)) {
			errors.add("gender", new org.apache.struts.action.ActionMessage(
					"error.field.required", "Gender "));
		}

		if ((form.getBirthYear() == null)
				|| (form.getBirthYear().length() == 0)) {
			errors.add("birthYear", new org.apache.struts.action.ActionMessage(
					"error.field.required", "Birth Year "));
		}
	}

	private boolean isValidEmail(String email) {
		String regex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)";
		return email.matches(regex);

	}

	private void updateUser(VCUserTO vto) throws SQLException {
		UserBO.getInstance().updateUser(vto);
	}

	public ActionForward done(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("done");
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
		return showAccountInfo(mapping, form, request, response);
	}
}