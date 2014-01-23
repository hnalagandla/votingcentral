/*
 * Created on Aug 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.votingcentral.env.EnvProps;
import com.votingcentral.forms.user.UserInfoForm;
import com.votingcentral.model.bo.location.LocationBO;
import com.votingcentral.model.bo.mail.EmailBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.db.dao.to.PersonalConfigTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.VCEmailTypeEnum;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.model.user.PasswordService;
import com.votingcentral.services.maxmind.MaxMindGeoLocationService;
import com.votingcentral.services.maxmind.MaxMindLocationTO;
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
public class RegEnterInfoAction extends DispatchAction {
	private static Log log = LogFactory.getLog(RegEnterInfoAction.class);

	private static boolean isMaxMindOn = EnvProps.getProperty(
			"maxmind.turned.on").equalsIgnoreCase("true");

	// we don't accept these characters in the user name.
	public final static String[] INVALID_CHARS = { "`", "~", "!", "@", "#",
			"$", "%", "^", "&", "*", "(", ")", "+", "=", ":", ";", "'", "[",
			"]", "{", "}", "|", "\\", "/", ",", "." };

	public static final int MAX_CHARS_IN_EMAIL_ADDRESS = 64;

	public static final int MIN_CHARS_IN_EMAIL_ADDRESS = 6;

	public static final int MAX_CHARS_IN_USER_NAME = 32;

	public static final int MIN_CHARS_IN_USER_NAME = 4;

	public static final int MAX_CHARS_IN_PASSWORD = 32;

	public static final int MIN_CHARS_IN_PASSWORD = 4;

	public static final int MAX_CHARS_IN_CITY = 64;

	public static final int MIN_CHARS_IN_CITY = 3;

	public static final int MIN_AGE_IN_YEARS_FOR_MEMBER_SHIP = 13;

	public static final int MAX_AGE_IN_YEARS_FOR_MEMBER_SHIP = 115;

	public static final int US_ZIP1_LENGTH = 5;

	public static final int US_ZIP2_LENGTH = 4;

	private int COUNTRY_ID_USA = 226;

	public ActionForward showReg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// if a secure URL is not being accessed securely redirect back
		// to a
		// secure version of that URL.
		if (!VCURLHelper.isSecureUrlSecure(request)) {
			String secUrl = VCURLHelper.prependHttpsPrefix(request
					.getRequestURL().toString(), request.getParameterMap());
			ActionForward newForward = new ActionForward(secUrl, true);
			// Finish with
			return (newForward);
		}
		String next = "register";
		ActionForward forward = new ActionForward(); // return value
		UserInfoForm regUserFormBean = (UserInfoForm) form;
		ActionMessages errors = new ActionMessages();

		if (shouldHideLocationInfoOnForm(request)) {
			regUserFormBean.setShowLocationInfoOnForm(false);
		} else {
			regUserFormBean.setShowLocationInfoOnForm(true);
		}

		if (regUserFormBean.getListOfCountries() == null) {
			regUserFormBean.setListOfCountries(LocationBO.getInstance()
					.getListOfCountries());
		}

		if (regUserFormBean.getListOfSecurityQuestions() == null) {
			regUserFormBean.setListOfSecurityQuestions(UserBO.getInstance()
					.getListOfSecurityQuestionsForView());
		}
		// initially set the states to be USA
		if (regUserFormBean.getStatesForCountry() == null) {
			if (regUserFormBean.getCountryId() == 0
					|| regUserFormBean.getCountryId() == COUNTRY_ID_USA) {
				regUserFormBean.setStatesForCountry(LocationBO.getInstance()
						.getStatesByCountry(COUNTRY_ID_USA));
			} else {
				regUserFormBean.setStatesForCountry(LocationBO.getInstance()
						.getStatesByCountry(regUserFormBean.getCountryId()));
			}
		}
		if (regUserFormBean.getCountryId() == 0) {
			regUserFormBean.setCountryId(COUNTRY_ID_USA);
		}
		regUserFormBean.setBirthDays(UserBO.getInstance()
				.getListOfDaysForView());
		regUserFormBean.setBirthMonths(UserBO.getInstance()
				.getListOfMonthsForView());
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	public ActionForward getStates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String next = "register";
		ActionForward forward = new ActionForward(); // return value
		UserInfoForm regUserFormBean = (UserInfoForm) form;
		ActionMessages errors = new ActionMessages();

		// set the new country specific data and redirect the user to
		// showReg.
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
		return showReg(mapping, form, request, response);
	}

	public ActionForward regNewUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String next = "enterConfCode";
		ActionForward forward = null; // return value
		ActionForward newForward = new ActionForward();
		UserInfoForm regUserFormBean = (UserInfoForm) form;
		ActionMessages errors = new ActionMessages();
		validate(regUserFormBean, request, errors);
		if (errors.size() > 0) {
			saveErrors(request, errors);
			return showReg(mapping, form, request, response);
		}
		VCUserTO vto = new VCUserTO();
		PersonalConfigTO pto = new PersonalConfigTO();
		String ip = "";
		if (!EnvProps.isProd()) {
			ip = "69.226.236.61";
		} else {
			ip = request.getRemoteAddr();
		}
		fillUserInfoTOs(regUserFormBean, vto, pto, ip, request);
		UserBO.getInstance().createUser(vto, pto);
		String confirmURL = VCURLHelper.getUserRegConfCodeUrlInEmail(
				VCRequestHelper.getDomainAndContext(request), regUserFormBean
						.getUserName(), pto.getEmailConfCode());
		// send email
		sendEmail(regUserFormBean.getEmailAddress(), pto.getEmailConfCode(),
				confirmURL);

		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath()).append("&").append(
				RequestParameterObjects.USER_NAME).append("=").append(
				FastURLEncoder.encode(regUserFormBean.getUserName()));
		buffer.append(PresentationConstants.AMPERSAND);
		buffer.append(PresentationConstants.ERR_CODE);
		buffer.append(PresentationConstants.EQUALS);
		buffer.append(PresErrorCodesEnum.USER_CONFIRM_RE_REQUEST_CONF_CODE
				.getId());
		newForward.setPath(buffer.toString());
		newForward.setName(forward.getName());
		newForward.setRedirect(true);
		// Finish with
		return (newForward);
	}

	/**
	 * The same code is in update MyVCAccountAction
	 * 
	 * @param regUserFormBean
	 * @param vto
	 * @param pto
	 * @throws Exception
	 */
	private void fillUserInfoTOs(UserInfoForm regUserFormBean, VCUserTO vto,
			PersonalConfigTO pto, String ipAddress, HttpServletRequest request)
			throws Exception {
		// first set the required stuff.
		org.apache.commons.beanutils.BeanUtils.copyProperties(vto,
				regUserFormBean);
		vto.setAccountStatus("CREATED");
		vto.addUserRoles("USER");

		vto.setUserName(regUserFormBean.getUserName().toLowerCase().trim());
		vto.setDisplayUserName(regUserFormBean.getUserName().trim());

		// Now populate the PersonalConfigTO
		org.apache.commons.beanutils.BeanUtils.copyProperties(pto,
				regUserFormBean);

		pto.setUserName(regUserFormBean.getUserName().toLowerCase().trim());
		// encrypt the password and store that.
		String encPass = regUserFormBean.getPassword().trim();
		encPass = PasswordService.getInstance().encrypt(encPass);
		String confCode = PasswordService.getInstance().generateRandomPassword(
				6);
		pto.setEmailConfCode(confCode);
		pto.setEncryptedPassword(encPass);
		pto.setTempPassword("NONE");

		if (shouldHideLocationInfoOnForm(request)) {
			MaxMindLocationTO mto = MaxMindGeoLocationService.getInstance()
					.getLocation(ipAddress);
			vto.setCountryId(LocationBO.getInstance()
					.getCountryIdByCountryCode(
							mto.getISO3166TwoLetterCountryCode()));
			String lc = mto.getCity().toLowerCase();
			vto.setCity(StringUtils.capitaliseAllWords(lc));
			vto.setStateId(LocationBO.getInstance()
					.getFipsStateIdByCountryCodeStateCode(
							mto.getISO3166TwoLetterCountryCode(),
							mto.getRegionCode()));
		} else {
			MaxMindLocationTO mto = MaxMindGeoLocationService.getInstance()
					.getLocation(ipAddress);
			printComparitiveInfo(mto, vto);
		}
		log.debug("fillUserInfoTOs: pto:" + pto.getEncryptedPassword());

	}

	private void printComparitiveInfo(MaxMindLocationTO mto, VCUserTO vto) {
		if (mto == null || vto == null) {
			log.error("mto or vto is null");
		}
		try {
			log.error("Country Code , max :"
					+ mto.getISO3166TwoLetterCountryCode()
					+ ", user:"
					+ LocationBO.getInstance().getCountryCodeByCountryId(
							vto.getCountryId()));
			log.error("State Code , max :"
					+ mto.getRegionCode()
					+ ", user:"
					+ LocationBO.getInstance().getStateCodeByStateIdCountryId(
							vto.getStateId(), vto.getCountryId()));
			log.error("City , max :" + mto.getCity() + ", user:"
					+ vto.getCity());
		} catch (SQLException e) {
			log.fatal("exception getting a location", e);
		}
	}

	private boolean sendEmail(String toEmail, String confirmCode,
			String confirmURL) {
		List toAddresses = new ArrayList();
		toAddresses.add(toEmail);
		Object[] values = { toEmail, confirmCode, confirmURL };
		try {
			EmailBO.getInstance().createMailRequest(toAddresses,
					VCEmailTypeEnum.REG_CONFIRM_CODE, values, 0);
		} catch (SQLException e) {
			log.fatal("failure inserting mail event for confirmation code", e);
			return false;
		}
		return true;
	}

	private void validate(UserInfoForm form, HttpServletRequest request,
			ActionMessages errors) {

		if ((form.getEmailAddress() == null)
				|| (form.getEmailAddress().length() == 0)) {
			errors.add("emailAddress",
					new org.apache.struts.action.ActionMessage(
							"error.field.required", "Email Address"));
		} else {
			if (form.getEmailAddress().length() > MAX_CHARS_IN_EMAIL_ADDRESS) {
				errors.add("loginName", new ActionMessage(
						"error.field.email.address.more.than.max.size"));
			}

			if (form.getEmailAddress().length() < MIN_CHARS_IN_EMAIL_ADDRESS) {
				errors.add("loginName", new ActionMessage(
						"error.field.email.address.less.than.min.size"));
			}

			try {
				if (UserBO.getInstance().getUserByEmailAddress(
						form.getEmailAddress().toLowerCase().trim()) != null) {
					errors
							.add(
									"emailAddress",
									new org.apache.struts.action.ActionMessage(
											"reg.newuser.error.email.address.not.unique"));

				}

			} catch (SQLException e) {
				log
						.fatal(
								"Fatal exception while checking for qunique user name and address",
								e);
				errors.add("emailAddress",
						new org.apache.struts.action.ActionMessage(
								"reg.newuser.error.email.address.not.unique"));
			}
		}

		if ((form.getUserName() == null) || (form.getUserName().length() == 0)) {
			errors.add("userName", new org.apache.struts.action.ActionMessage(
					"error.field.required", "User Name"));
		} else {
			if (StringUtils.lastIndexOfAny(form.getUserName(), INVALID_CHARS) != -1) {
				String invalidChars = StringUtils.join(INVALID_CHARS, ",");
				errors.add("userName", new ActionMessage(
						"error.field.contains.invalid.chars", "User Name",
						invalidChars));
				return;
			}
			if (form.getUserName().trim().indexOf(" ") != -1) {
				errors.add("userName", new ActionMessage(
						"error.field.username.spaces.not.allowed"));
				return;
			}
			if (form.getUserName().length() > MAX_CHARS_IN_USER_NAME) {
				errors.add("userName", new ActionMessage(
						"error.field.username.more.than.max.size"));
			}

			if (form.getUserName().length() < MIN_CHARS_IN_USER_NAME) {
				errors.add("userName", new ActionMessage(
						"error.field.username.less.than.min.size"));
			}
			try {
				if (UserBO.getInstance().getUserByUserName(
						form.getUserName().toLowerCase()) != null
						|| UserBO.getInstance().isUserNameReserved(
								form.getUserName())) {
					errors.add("userName",
							new org.apache.struts.action.ActionMessage(
									"reg.newuser.error.username.not.unique"));

				}
			} catch (SQLException e) {
				log
						.fatal(
								"Fatal exception while checking for qunique user name and address",
								e);
				errors.add("userName",
						new org.apache.struts.action.ActionMessage(
								"reg.newuser.error.username.not.unique"));
			}
		}

		if ((form.getPassword() == null) || (form.getPassword().length() == 0)) {
			errors.add("password", new ActionMessage("error.field.required",
					"Password"));
		}
		if ((form.getRenteredPassword() == null)
				|| (form.getRenteredPassword().length() == 0)) {
			errors.add("renteredPassword", new ActionMessage(
					"error.field.required", "Re-enter Password"));
		}

		if ((form.getPassword() != null)
				&& (form.getRenteredPassword() != null)) {
			if (!form.getRenteredPassword().equals(form.getPassword())) {
				errors.add("renteredPassword", new ActionMessage(
						"error.field.password.rentered.dont.match"));
			}
			if (form.getPassword().length() > MAX_CHARS_IN_PASSWORD) {
				errors.add("password", new ActionMessage(
						"error.field.password.more.than.max.size"));
			}
			if (form.getPassword().length() < MIN_CHARS_IN_PASSWORD) {
				errors.add("password", new ActionMessage(
						"error.field.password.less.than.min.size"));
			}
		}

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
		if (!PollHelper.isValidEmail(form.getEmailAddress())) {
			errors.add("emailAddress",
					new org.apache.struts.action.ActionMessage(
							"error.field.invalid.email.address"));
		}
		if (!shouldHideLocationInfoOnForm(request)) {
			if ((form.getZipCode1() == null)
					|| (form.getZipCode1().length() == 0)) {
				errors.add("zipCode1",
						new org.apache.struts.action.ActionMessage(
								"error.field.required", "Zip/PIN Code "));
			} else {
				if ((form.getCountryId() == COUNTRY_ID_USA)
						&& (form.getZipCode1().length() != US_ZIP1_LENGTH)) {
					errors.add("zipCode1",
							new org.apache.struts.action.ActionMessage(
									"error.field.zip.code1.not.5digits"));
				}
				try {
					int zip1 = Integer.parseInt(form.getZipCode1());
				} catch (NumberFormatException nfe) {
					if (form.getCountryId() == COUNTRY_ID_USA) {
						errors.add("zipCode1",
								new org.apache.struts.action.ActionMessage(
										"error.field.zip.code1.numeric"));
					}
				}
			}
			if (form.getZipCode2() != null && form.getZipCode2().length() > 0) {
				try {
					int zip2 = Integer.parseInt(form.getZipCode2());
					if ((form.getCountryId() == COUNTRY_ID_USA)
							&& (form.getZipCode2().length() != US_ZIP2_LENGTH)) {
						errors.add("zipCode2",
								new org.apache.struts.action.ActionMessage(
										"error.field.zip.code2.not.4digits"));
					}
				} catch (NumberFormatException nfe) {
					if (form.getCountryId() == COUNTRY_ID_USA) {
						errors.add("zipCode2",
								new org.apache.struts.action.ActionMessage(
										"error.field.zip.code2.numeric"));
					}
				}
			}
			if ((form.getCity() == null) || (form.getCity().length() == 0)) {
				errors.add("city", new org.apache.struts.action.ActionMessage(
						"error.field.required", "City "));
			} else {
				if (form.getCity().length() > MAX_CHARS_IN_CITY) {
					errors.add("city", new ActionMessage(
							"error.field.city.more.than.max.size"));
				}
				if (form.getCity().length() < MIN_CHARS_IN_CITY) {
					errors.add("city", new ActionMessage(
							"error.field.city.less.than.min.size"));
				}
				if (form.getCity().length() < MIN_CHARS_IN_CITY) {
					errors.add("city", new ActionMessage(
							"error.field.city.less.than.min.size"));
				}
				if (StringUtils.lastIndexOfAny(form.getCity(), INVALID_CHARS) != -1) {
					String invalidChars = StringUtils.join(INVALID_CHARS, ",");
					errors.add("city", new ActionMessage(
							"error.field.contains.invalid.chars", "City",
							invalidChars));
					return;
				}
			}

			if (form.getCountryId() == 0) {
				errors.add("country",
						new org.apache.struts.action.ActionMessage(
								"error.field.required", "Country "));
				return;
			}

			if (form.getStateId() == 0 && form.getCountryId() == COUNTRY_ID_USA) {
				errors.add("state", new org.apache.struts.action.ActionMessage(
						"error.field.required", "State "));
			}
		}
		if ((form.getGender() == null) || (form.getGender().length() == 0)) {
			errors.add("gender", new org.apache.struts.action.ActionMessage(
					"error.field.required", "Gender "));
		}

		if ((form.getBirthYear() == null)
				|| (form.getBirthYear().length() == 0)) {
			errors.add("birthYear", new org.apache.struts.action.ActionMessage(
					"error.field.required", "Birth Year "));
		} else {
			try {
				int by = Integer.parseInt(form.getBirthYear());
				int cy = Calendar.getInstance(
						PollTimeHelper.POLL_TIMES_TIME_ZONE).get(Calendar.YEAR);
				if (cy - by < MIN_AGE_IN_YEARS_FOR_MEMBER_SHIP) {
					errors
							.add(
									"birthYear",
									new org.apache.struts.action.ActionMessage(
											"error.field.birth.year.min.age",
											Integer
													.toString(MIN_AGE_IN_YEARS_FOR_MEMBER_SHIP)));
				}
				if (cy - by > MAX_AGE_IN_YEARS_FOR_MEMBER_SHIP) {
					errors
							.add(
									"birthYear",
									new org.apache.struts.action.ActionMessage(
											"error.field.birth.year.max.age",
											Integer
													.toString(MAX_AGE_IN_YEARS_FOR_MEMBER_SHIP)));
				}
			} catch (NumberFormatException ne) {
				errors.add("birthYear",
						new org.apache.struts.action.ActionMessage(
								"error.field.birth.year.numeric"));
			}
		}

		if (form.getTc() == null || !form.getTc().equalsIgnoreCase("ACCEPT")) {
			errors.add("tc", new org.apache.struts.action.ActionMessage(
					"error.field.terms.and.conditions"));
		}
		/*
		 * if (!preConfirmCode.equals(confirmCode)) { errors.add("confirmation
		 * code", new org.apache.struts.action.ActionMessage(
		 * "error.field.confirm.code.does.not.match")); }
		 */
	}

	private MaxMindLocationTO getMaxMindLocation(HttpServletRequest request) {
		String ip = "";
		if (!EnvProps.isProd()) {
			ip = "69.226.236.61";
		} else {
			ip = request.getRemoteAddr();
		}
		MaxMindLocationTO mto = MaxMindGeoLocationService.getInstance()
				.getLocation(ip);
		return mto;
	}

	private boolean shouldHideLocationInfoOnForm(HttpServletRequest request) {
		MaxMindLocationTO mto = getMaxMindLocation(request);
		return isMaxMindOn && mto.getError() == null;
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
		return showReg(mapping, form, request, response);
	}
}