package com.votingcentral.model.db.dao.to;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VCUserTO {

	private long userId;

	private String firstName;

	private String lastName;

	private String middleInitial;

	private String middleName;

	private String emailAddress;

	private String userName;

	private String displayUserName;

	private String gender;

	private String birthYear;

	private String birthMonth;

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

	private Date createTimestamp;

	private Date modifyTimestamp;

	private List userRoles;

	private VCUserInfoTO vcUserInfoTo;

	private VCVacoPointsTO vcVacoPointsTo;

	public VCUserTO() {

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
	 * @return Returns the createTimestamp.
	 */
	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	/**
	 * @param createTimestamp
	 *            The createTimestamp to set.
	 */
	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
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
	 * @return Returns the userId.
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            The userId to set.
	 */
	public void setUserId(long emailAddressId) {
		this.userId = emailAddressId;
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
	 * @return Returns the modifyTimestamp.
	 */
	public Date getModifyTimestamp() {
		return modifyTimestamp;
	}

	/**
	 * @param modifyTimestamp
	 *            The modifyTimestamp to set.
	 */
	public void setModifyTimestamp(Date modifyTimestamp) {
		this.modifyTimestamp = modifyTimestamp;
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
	public void setUserName(String nickName) {
		this.userName = nickName;
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
	 * @return Returns the userRoles.
	 */
	public List getUserRoles() {
		return userRoles;
	}

	/**
	 * @param userRoles
	 *            The userRoles to set.
	 */
	public void setUserRoles(List userRoles) {
		this.userRoles = userRoles;
	}

	public void addUserRoles(String userRole) {
		if (this.userRoles == null)
			this.userRoles = new ArrayList();

		this.userRoles.add(userRole);
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
	 * @return the displayUserName
	 */
	public String getDisplayUserName() {
		return displayUserName;
	}

	/**
	 * @param displayUserName
	 *            the displayUserName to set
	 */
	public void setDisplayUserName(String displayUserName) {
		this.displayUserName = displayUserName;
	}

	/**
	 * @return Returns the vcUserInfoTo.
	 */
	public VCUserInfoTO getVcUserInfoTo() {
		return vcUserInfoTo;
	}

	/**
	 * @param vcUserInfoTo
	 *            The vcUserInfoTo to set.
	 */
	public void setVcUserInfoTo(VCUserInfoTO vcUserInfoTo) {
		this.vcUserInfoTo = vcUserInfoTo;
	}

	/**
	 * @return Returns the vcVacoPointsTo.
	 */
	public VCVacoPointsTO getVcVacoPointsTo() {
		return vcVacoPointsTo;
	}

	/**
	 * @param vcVacoPointsTo
	 *            The vcVacoPointsTo to set.
	 */
	public void setVcVacoPointsTo(VCVacoPointsTO vcVacoPointsTo) {
		this.vcVacoPointsTo = vcVacoPointsTo;
	}
}