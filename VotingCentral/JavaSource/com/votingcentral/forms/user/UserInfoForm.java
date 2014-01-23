/*
 * Created on Aug 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.user;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.forms.VCBaseFormBean;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class UserInfoForm extends VCBaseFormBean {

	private static Log log = LogFactory.getLog(UserInfoForm.class);

	private String confirmCode;

	private String preConfirmCode;

	private String firstName;

	private String lastName;

	private String middleInitial;

	private String middleName;

	private String emailAddress;

	private String nickName;

	private String gender;

	private String birthYear;

	private String birthMonth;

	private List birthMonths;

	private List birthDays;

	private String birthDay;

	private String mailingAddress1;

	private String mailingAddress2;

	private String city;

	private int stateId;

	private String zipCode1;

	private String zipCode2;

	private int countryId;

	private String phoneCountryCode;

	private String phoneAreaCode;

	private String phoneNum1;

	private String phoneNum2;

	private String accountStatus;

	private String securityQuestion;

	private String securityAnswer;

	private String password;

	private String newPassword;

	private String renteredPassword;

	private List listOfCountries = null;

	private List listOfSecurityQuestions = null;

	private List statesForCountry = null;

	private String afterConfChoice = null;

	private String userName;

	//terms and conditions
	private String tc;

	//show URLs on the confirmation success page.
	private List successPageUrls;

	private String resendConfCodeUrl;

	private boolean showLocationInfoOnForm;
	
	/**
	 * @return Returns the confirmCode.
	 */
	public String getConfirmCode() {
		return confirmCode;
	}

	/**
	 * @return Returns the listOfCountries.
	 */
	public List getListOfCountries() {
		return listOfCountries;
	}

	/**
	 * @return Returns the listOfSecurityQuestions.
	 */
	public List getListOfSecurityQuestions() {
		return listOfSecurityQuestions;
	}

	/**
	 * @param confirmCode
	 *            The confirmCode to set.
	 */
	public void setConfirmCode(String confirmCode) {
		this.confirmCode = confirmCode;
	}

	/**
	 * @return Returns the accountStatus.
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param accountStatus
	 *            The accountStatus to set.
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * @return Returns the city.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return Returns the countryId.
	 */
	public int getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId
	 *            The countryId to set.
	 */
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return Returns the emailAddress.
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            The emailAddress to set.
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return Returns the lastName.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            The lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return Returns the mailingAddress1.
	 */
	public String getMailingAddress1() {
		return mailingAddress1;
	}

	/**
	 * @param mailingAddress1
	 *            The mailingAddress1 to set.
	 */
	public void setMailingAddress1(String mailingAddress1) {
		this.mailingAddress1 = mailingAddress1;
	}

	/**
	 * @return Returns the mailingAddress2.
	 */
	public String getMailingAddress2() {
		return mailingAddress2;
	}

	/**
	 * @param mailingAddress2
	 *            The mailingAddress2 to set.
	 */
	public void setMailingAddress2(String mailingAddress2) {
		this.mailingAddress2 = mailingAddress2;
	}

	/**
	 * @return Returns the middleInitial.
	 */
	public String getMiddleInitial() {
		return middleInitial;
	}

	/**
	 * @param middleInitial
	 *            The middleInitial to set.
	 */
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	/**
	 * @return Returns the middleName.
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName
	 *            The middleName to set.
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return Returns the nickName.
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            The nickName to set.
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return Returns the birthDay.
	 */
	public String getBirthDay() {
		return birthDay;
	}

	/**
	 * @param birthDay
	 *            The birthDay to set.
	 */
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	/**
	 * @return Returns the birthMonth.
	 */
	public String getBirthMonth() {
		return birthMonth;
	}

	/**
	 * @param birthMonth
	 *            The birthMonth to set.
	 */
	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}

	/**
	 * @return Returns the birthYear.
	 */
	public String getBirthYear() {
		return birthYear;
	}

	/**
	 * @param birthYear
	 *            The birthYear to set.
	 */
	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	/**
	 * @return Returns the gender.
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            The gender to set.
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return Returns the phoneAreaCode.
	 */
	public String getPhoneAreaCode() {
		return phoneAreaCode;
	}

	/**
	 * @param phoneAreaCode
	 *            The phoneAreaCode to set.
	 */
	public void setPhoneAreaCode(String phoneAreaCode) {
		this.phoneAreaCode = phoneAreaCode;
	}

	/**
	 * @return Returns the phoneCountryCode.
	 */
	public String getPhoneCountryCode() {
		return phoneCountryCode;
	}

	/**
	 * @param phoneCountryCode
	 *            The phoneCountryCode to set.
	 */
	public void setPhoneCountryCode(String phoneCountryCode) {
		this.phoneCountryCode = phoneCountryCode;
	}

	/**
	 * @return Returns the phoneNum1.
	 */
	public String getPhoneNum1() {
		return phoneNum1;
	}

	/**
	 * @param phoneNum1
	 *            The phoneNum1 to set.
	 */
	public void setPhoneNum1(String phoneNum1) {
		this.phoneNum1 = phoneNum1;
	}

	/**
	 * @return Returns the phoneNum2.
	 */
	public String getPhoneNum2() {
		return phoneNum2;
	}

	/**
	 * @param phoneNum2
	 *            The phoneNum2 to set.
	 */
	public void setPhoneNum2(String phoneNum2) {
		this.phoneNum2 = phoneNum2;
	}

	/**
	 * @return Returns the securityAnswer.
	 */
	public String getSecurityAnswer() {
		return securityAnswer;
	}

	/**
	 * @param securityAnswer
	 *            The securityAnswer to set.
	 */
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	/**
	 * @return Returns the securityQuestion.
	 */
	public String getSecurityQuestion() {
		return securityQuestion;
	}

	/**
	 * @param securityQuestion
	 *            The securityQuestion to set.
	 */
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}

	/**
	 * @return Returns the stateId.
	 */
	public int getStateId() {
		return stateId;
	}

	/**
	 * @param stateId
	 *            The stateId to set.
	 */
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return Returns the zipCode1.
	 */
	public String getZipCode1() {
		return zipCode1;
	}

	/**
	 * @param zipCode1
	 *            The zipCode1 to set.
	 */
	public void setZipCode1(String zipCode1) {
		this.zipCode1 = zipCode1;
	}

	/**
	 * @return Returns the zipCode2.
	 */
	public String getZipCode2() {
		return zipCode2;
	}

	/**
	 * @param zipCode2
	 *            The zipCode2 to set.
	 */
	public void setZipCode2(String zipCode2) {
		this.zipCode2 = zipCode2;
	}

	/**
	 * @return Returns the renteredPassword.
	 */
	public String getRenteredPassword() {
		return renteredPassword;
	}

	/**
	 * @param renteredPassword
	 *            The renteredPassword to set.
	 */
	public void setRenteredPassword(String renteredPassword) {
		this.renteredPassword = renteredPassword;
	}

	/**
	 * @param listOfCountries
	 *            The listOfCountries to set.
	 */
	public void setListOfCountries(List listOfCountries) {
		this.listOfCountries = listOfCountries;
	}

	/**
	 * @param listOfSecurityQuestions
	 *            The listOfSecurityQuestions to set.
	 */
	public void setListOfSecurityQuestions(List listOfSecurityQuestions) {
		this.listOfSecurityQuestions = listOfSecurityQuestions;
	}

	/**
	 * @return Returns the preConfirmCode.
	 */
	public String getPreConfirmCode() {
		return preConfirmCode;
	}

	/**
	 * @param preConfirmCode
	 *            The preConfirmCode to set.
	 */
	public void setPreConfirmCode(String preConfirmCode) {
		this.preConfirmCode = preConfirmCode;
	}

	/**
	 * @return Returns the afterConfChoice.
	 */
	public String getAfterConfChoice() {
		return afterConfChoice;
	}

	/**
	 * @param afterConfChoice
	 *            The afterConfChoice to set.
	 */
	public void setAfterConfChoice(String afterConfChoice) {
		this.afterConfChoice = afterConfChoice;
	}

	/**
	 * @return Returns the newPassword.
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword
	 *            The newPassword to set.
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return Returns the statesForCountry.
	 */
	public List getStatesForCountry() {
		return statesForCountry;
	}

	/**
	 * @param statesForCountry
	 *            The statesForCountry to set.
	 */
	public void setStatesForCountry(List statesForCountry) {
		this.statesForCountry = statesForCountry;
	}

	/**
	 * @return Returns the birthDays.
	 */
	public List getBirthDays() {
		return birthDays;
	}

	/**
	 * @param birthDays
	 *            The birthDays to set.
	 */
	public void setBirthDays(List birthDays) {
		this.birthDays = birthDays;
	}

	/**
	 * @return Returns the birthMonths.
	 */
	public List getBirthMonths() {
		return birthMonths;
	}

	/**
	 * @param birthMonths
	 *            The birthMonths to set.
	 */
	public void setBirthMonths(List birthMonths) {
		this.birthMonths = birthMonths;
	}

	/**
	 * @return Returns the tc.
	 */
	public String getTc() {
		return tc;
	}

	/**
	 * @param tc
	 *            The tc to set.
	 */
	public void setTc(String tc) {
		this.tc = tc;
	}

	/**
	 * @return Returns the successPageUrls.
	 */
	public List getSuccessPageUrls() {
		return successPageUrls;
	}

	/**
	 * @param successPageUrls
	 *            The successPageUrls to set.
	 */
	public void setSuccessPageUrls(List successPageUrls) {
		this.successPageUrls = successPageUrls;
	}

	/**
	 * @return Returns the resendConfCodeUrl.
	 */
	public String getResendConfCodeUrl() {
		return resendConfCodeUrl;
	}

	/**
	 * @param resendConfCodeUrl
	 *            The resendConfCodeUrl to set.
	 */
	public void setResendConfCodeUrl(String resendConfCodeUrl) {
		this.resendConfCodeUrl = resendConfCodeUrl;
	}
	
	
	/**
	 * @return Returns the showLocationInfoOnForm.
	 */
	public boolean isShowLocationInfoOnForm() {
		return showLocationInfoOnForm;
	}
	/**
	 * @param showLocationInfoOnForm The showLocationInfoOnForm to set.
	 */
	public void setShowLocationInfoOnForm(boolean showLocationInfoOnForm) {
		this.showLocationInfoOnForm = showLocationInfoOnForm;
	}
}