/*
 * Created on Apr 26, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.user;

import java.util.List;

import com.votingcentral.forms.VCBaseFormBean;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ManageMyProfileForm extends VCBaseFormBean {
	private List listOfRaces;

	private List listOfSalaries;

	private List listOfUnionMemberShip;

	private List listOfEducation;

	private List listOfParties;

	private List listOfIdeologies;

	private List listOfReligions;

	private List listOfZodiacs;

	private List listOfMaritalStatus;

	private List listOfNumChildren;

	private List listOfSmokeTypes;

	private List listOfDrinkTypes;

	private List listOfSexOrient;

	private List listOfShirtSizes;

	private String race;

	private String racePrivacyLevel;

	private String salary;

	private String salaryPrivacyLevel;

	private String unionMember;

	private String unionPrivacyLevel;

	private String education;

	private String educationPrivacyLevel;

	private String ideology;

	private String ideologyPrivacyLevel;

	private String religion;

	private String religionPrivacyLevel;

	private String zodiac;

	private String zodiacPrivacyLevel;

	private String maritalStatus;

	private String maritalStatusPrivacyLevel;

	private String party;

	private String partyPrivacyLevel;

	private String numChildren;

	private String numChildrenPrivacyLevel;

	private String smokeType;

	private String smokeTypePrivacyLevel;

	private String drinkType;

	private String drinkTypePrivacyLevel;

	private String sexOrient;

	private String sexOrientPrivacyLevel;

	private String aboutMe;

	private String favUrl;

	private String favQuote;

	private String shirtSize;

	private String shirtSizePrivacyLevel;

	private String viewProfileUrl;

	/**
	 * @return Returns the aboutMe.
	 */
	public String getAboutMe() {
		return aboutMe;
	}

	/**
	 * @param aboutMe
	 *            The aboutMe to set.
	 */
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	/**
	 * @return Returns the drinkType.
	 */
	public String getDrinkType() {
		return drinkType;
	}

	/**
	 * @param drinkType
	 *            The drinkType to set.
	 */
	public void setDrinkType(String drinkType) {
		this.drinkType = drinkType;
	}

	/**
	 * @return Returns the drinkTypePrivacyLevel.
	 */
	public String getDrinkTypePrivacyLevel() {
		return drinkTypePrivacyLevel;
	}

	/**
	 * @param drinkTypePrivacyLevel
	 *            The drinkTypePrivacyLevel to set.
	 */
	public void setDrinkTypePrivacyLevel(String drinkTypePrivacyLevel) {
		this.drinkTypePrivacyLevel = drinkTypePrivacyLevel;
	}

	/**
	 * @return Returns the education.
	 */
	public String getEducation() {
		return education;
	}

	/**
	 * @param education
	 *            The education to set.
	 */
	public void setEducation(String education) {
		this.education = education;
	}

	/**
	 * @return Returns the educationPrivacyLevel.
	 */
	public String getEducationPrivacyLevel() {
		return educationPrivacyLevel;
	}

	/**
	 * @param educationPrivacyLevel
	 *            The educationPrivacyLevel to set.
	 */
	public void setEducationPrivacyLevel(String educationPrivacyLevel) {
		this.educationPrivacyLevel = educationPrivacyLevel;
	}

	/**
	 * @return Returns the favQuote.
	 */
	public String getFavQuote() {
		return favQuote;
	}

	/**
	 * @param favQuote
	 *            The favQuote to set.
	 */
	public void setFavQuote(String favQuote) {
		this.favQuote = favQuote;
	}

	/**
	 * @return Returns the favUrl.
	 */
	public String getFavUrl() {
		return favUrl;
	}

	/**
	 * @param favUrl
	 *            The favUrl to set.
	 */
	public void setFavUrl(String favUrl) {
		this.favUrl = favUrl;
	}

	/**
	 * @return Returns the ideology.
	 */
	public String getIdeology() {
		return ideology;
	}

	/**
	 * @param ideology
	 *            The ideology to set.
	 */
	public void setIdeology(String ideology) {
		this.ideology = ideology;
	}

	/**
	 * @return Returns the ideologyPrivacyLevel.
	 */
	public String getIdeologyPrivacyLevel() {
		return ideologyPrivacyLevel;
	}

	/**
	 * @param ideologyPrivacyLevel
	 *            The ideologyPrivacyLevel to set.
	 */
	public void setIdeologyPrivacyLevel(String ideologyPrivacyLevel) {
		this.ideologyPrivacyLevel = ideologyPrivacyLevel;
	}

	/**
	 * @return Returns the listOfDrinkTypes.
	 */
	public List getListOfDrinkTypes() {
		return listOfDrinkTypes;
	}

	/**
	 * @param listOfDrinkTypes
	 *            The listOfDrinkTypes to set.
	 */
	public void setListOfDrinkTypes(List listOfDrinkTypes) {
		this.listOfDrinkTypes = listOfDrinkTypes;
	}

	/**
	 * @return Returns the listOfEducation.
	 */
	public List getListOfEducation() {
		return listOfEducation;
	}

	/**
	 * @param listOfEducation
	 *            The listOfEducation to set.
	 */
	public void setListOfEducation(List listOfEducation) {
		this.listOfEducation = listOfEducation;
	}

	/**
	 * @return Returns the listOfIdeologies.
	 */
	public List getListOfIdeologies() {
		return listOfIdeologies;
	}

	/**
	 * @param listOfIdeologies
	 *            The listOfIdeologies to set.
	 */
	public void setListOfIdeologies(List listOfIdeologies) {
		this.listOfIdeologies = listOfIdeologies;
	}

	/**
	 * @return Returns the listOfMaritalStatus.
	 */
	public List getListOfMaritalStatus() {
		return listOfMaritalStatus;
	}

	/**
	 * @param listOfMaritalStatus
	 *            The listOfMaritalStatus to set.
	 */
	public void setListOfMaritalStatus(List listOfMaritalStatus) {
		this.listOfMaritalStatus = listOfMaritalStatus;
	}

	/**
	 * @return Returns the listOfNumChildren.
	 */
	public List getListOfNumChildren() {
		return listOfNumChildren;
	}

	/**
	 * @param listOfNumChildren
	 *            The listOfNumChildren to set.
	 */
	public void setListOfNumChildren(List listOfNumChildren) {
		this.listOfNumChildren = listOfNumChildren;
	}

	/**
	 * @return Returns the listOfParties.
	 */
	public List getListOfParties() {
		return listOfParties;
	}

	/**
	 * @param listOfParties
	 *            The listOfParties to set.
	 */
	public void setListOfParties(List listOfParties) {
		this.listOfParties = listOfParties;
	}

	/**
	 * @return Returns the listOfRaces.
	 */
	public List getListOfRaces() {
		return listOfRaces;
	}

	/**
	 * @param listOfRaces
	 *            The listOfRaces to set.
	 */
	public void setListOfRaces(List listOfRaces) {
		this.listOfRaces = listOfRaces;
	}

	/**
	 * @return Returns the listOfReligions.
	 */
	public List getListOfReligions() {
		return listOfReligions;
	}

	/**
	 * @param listOfReligions
	 *            The listOfReligions to set.
	 */
	public void setListOfReligions(List listOfReligions) {
		this.listOfReligions = listOfReligions;
	}

	/**
	 * @return Returns the listOfSalaries.
	 */
	public List getListOfSalaries() {
		return listOfSalaries;
	}

	/**
	 * @param listOfSalaries
	 *            The listOfSalaries to set.
	 */
	public void setListOfSalaries(List listOfSalaries) {
		this.listOfSalaries = listOfSalaries;
	}

	/**
	 * @return Returns the listOfSexOrient.
	 */
	public List getListOfSexOrient() {
		return listOfSexOrient;
	}

	/**
	 * @param listOfSexOrient
	 *            The listOfSexOrient to set.
	 */
	public void setListOfSexOrient(List listOfSexOrient) {
		this.listOfSexOrient = listOfSexOrient;
	}

	/**
	 * @return Returns the listOfSmokeTypes.
	 */
	public List getListOfSmokeTypes() {
		return listOfSmokeTypes;
	}

	/**
	 * @param listOfSmokeTypes
	 *            The listOfSmokeTypes to set.
	 */
	public void setListOfSmokeTypes(List listOfSmokeTypes) {
		this.listOfSmokeTypes = listOfSmokeTypes;
	}

	/**
	 * @return Returns the listOfUnionMemberShip.
	 */
	public List getListOfUnionMemberShip() {
		return listOfUnionMemberShip;
	}

	/**
	 * @param listOfUnionMemberShip
	 *            The listOfUnionMemberShip to set.
	 */
	public void setListOfUnionMemberShip(List listOfUnionMemberShip) {
		this.listOfUnionMemberShip = listOfUnionMemberShip;
	}

	/**
	 * @return Returns the listOfZodiacs.
	 */
	public List getListOfZodiacs() {
		return listOfZodiacs;
	}

	/**
	 * @param listOfZodiacs
	 *            The listOfZodiacs to set.
	 */
	public void setListOfZodiacs(List listOfZodiacs) {
		this.listOfZodiacs = listOfZodiacs;
	}

	/**
	 * @return Returns the maritalStatus.
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}

	/**
	 * @param maritalStatus
	 *            The maritalStatus to set.
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	/**
	 * @return Returns the maritalStatusPrivacyLevel.
	 */
	public String getMaritalStatusPrivacyLevel() {
		return maritalStatusPrivacyLevel;
	}

	/**
	 * @param maritalStatusPrivacyLevel
	 *            The maritalStatusPrivacyLevel to set.
	 */
	public void setMaritalStatusPrivacyLevel(String maritalStatusPrivacyLevel) {
		this.maritalStatusPrivacyLevel = maritalStatusPrivacyLevel;
	}

	/**
	 * @return Returns the numChildren.
	 */
	public String getNumChildren() {
		return numChildren;
	}

	/**
	 * @param numChildren
	 *            The numChildren to set.
	 */
	public void setNumChildren(String numChildren) {
		this.numChildren = numChildren;
	}

	/**
	 * @return Returns the numChildrenPrivacyLevel.
	 */
	public String getNumChildrenPrivacyLevel() {
		return numChildrenPrivacyLevel;
	}

	/**
	 * @param numChildrenPrivacyLevel
	 *            The numChildrenPrivacyLevel to set.
	 */
	public void setNumChildrenPrivacyLevel(String numChildrenPrivacyLevel) {
		this.numChildrenPrivacyLevel = numChildrenPrivacyLevel;
	}

	/**
	 * @return Returns the party.
	 */
	public String getParty() {
		return party;
	}

	/**
	 * @param party
	 *            The party to set.
	 */
	public void setParty(String party) {
		this.party = party;
	}

	/**
	 * @return Returns the partyPrivacyLevel.
	 */
	public String getPartyPrivacyLevel() {
		return partyPrivacyLevel;
	}

	/**
	 * @param partyPrivacyLevel
	 *            The partyPrivacyLevel to set.
	 */
	public void setPartyPrivacyLevel(String partyPrivacyLevel) {
		this.partyPrivacyLevel = partyPrivacyLevel;
	}

	/**
	 * @return Returns the race.
	 */
	public String getRace() {
		return race;
	}

	/**
	 * @param race
	 *            The race to set.
	 */
	public void setRace(String race) {
		this.race = race;
	}

	/**
	 * @return Returns the racePrivacyLevel.
	 */
	public String getRacePrivacyLevel() {
		return racePrivacyLevel;
	}

	/**
	 * @param racePrivacyLevel
	 *            The racePrivacyLevel to set.
	 */
	public void setRacePrivacyLevel(String racePrivacyLevel) {
		this.racePrivacyLevel = racePrivacyLevel;
	}

	/**
	 * @return Returns the religion.
	 */
	public String getReligion() {
		return religion;
	}

	/**
	 * @param religion
	 *            The religion to set.
	 */
	public void setReligion(String religion) {
		this.religion = religion;
	}

	/**
	 * @return Returns the religionPrivacyLevel.
	 */
	public String getReligionPrivacyLevel() {
		return religionPrivacyLevel;
	}

	/**
	 * @param religionPrivacyLevel
	 *            The religionPrivacyLevel to set.
	 */
	public void setReligionPrivacyLevel(String religionPrivacyLevel) {
		this.religionPrivacyLevel = religionPrivacyLevel;
	}

	/**
	 * @return Returns the salary.
	 */
	public String getSalary() {
		return salary;
	}

	/**
	 * @param salary
	 *            The salary to set.
	 */
	public void setSalary(String salary) {
		this.salary = salary;
	}

	/**
	 * @return Returns the salaryPrivacyLevel.
	 */
	public String getSalaryPrivacyLevel() {
		return salaryPrivacyLevel;
	}

	/**
	 * @param salaryPrivacyLevel
	 *            The salaryPrivacyLevel to set.
	 */
	public void setSalaryPrivacyLevel(String salaryPrivacyLevel) {
		this.salaryPrivacyLevel = salaryPrivacyLevel;
	}

	/**
	 * @return Returns the sexOrient.
	 */
	public String getSexOrient() {
		return sexOrient;
	}

	/**
	 * @param sexOrient
	 *            The sexOrient to set.
	 */
	public void setSexOrient(String sexOrient) {
		this.sexOrient = sexOrient;
	}

	/**
	 * @return Returns the sexOrientPrivacyLevel.
	 */
	public String getSexOrientPrivacyLevel() {
		return sexOrientPrivacyLevel;
	}

	/**
	 * @param sexOrientPrivacyLevel
	 *            The sexOrientPrivacyLevel to set.
	 */
	public void setSexOrientPrivacyLevel(String sexOrientPrivacyLevel) {
		this.sexOrientPrivacyLevel = sexOrientPrivacyLevel;
	}

	/**
	 * @return Returns the smokeType.
	 */
	public String getSmokeType() {
		return smokeType;
	}

	/**
	 * @param smokeType
	 *            The smokeType to set.
	 */
	public void setSmokeType(String smokeType) {
		this.smokeType = smokeType;
	}

	/**
	 * @return Returns the smokeTypePrivacyLevel.
	 */
	public String getSmokeTypePrivacyLevel() {
		return smokeTypePrivacyLevel;
	}

	/**
	 * @param smokeTypePrivacyLevel
	 *            The smokeTypePrivacyLevel to set.
	 */
	public void setSmokeTypePrivacyLevel(String smokeTypePrivacyLevel) {
		this.smokeTypePrivacyLevel = smokeTypePrivacyLevel;
	}

	/**
	 * @return Returns the unionMember.
	 */
	public String getUnionMember() {
		return unionMember;
	}

	/**
	 * @param unionMember
	 *            The unionMember to set.
	 */
	public void setUnionMember(String unionMember) {
		this.unionMember = unionMember;
	}

	/**
	 * @return Returns the unionPrivacyLevel.
	 */
	public String getUnionPrivacyLevel() {
		return unionPrivacyLevel;
	}

	/**
	 * @param unionPrivacyLevel
	 *            The unionPrivacyLevel to set.
	 */
	public void setUnionPrivacyLevel(String unionPrivacyLevel) {
		this.unionPrivacyLevel = unionPrivacyLevel;
	}

	/**
	 * @return Returns the zodiac.
	 */
	public String getZodiac() {
		return zodiac;
	}

	/**
	 * @param zodiac
	 *            The zodiac to set.
	 */
	public void setZodiac(String zodiac) {
		this.zodiac = zodiac;
	}

	/**
	 * @return Returns the zodiacPrivacyLevel.
	 */
	public String getZodiacPrivacyLevel() {
		return zodiacPrivacyLevel;
	}

	/**
	 * @param zodiacPrivacyLevel
	 *            The zodiacPrivacyLevel to set.
	 */
	public void setZodiacPrivacyLevel(String zodiacPrivacyLevel) {
		this.zodiacPrivacyLevel = zodiacPrivacyLevel;
	}

	/**
	 * @return Returns the listOfShirtSizes.
	 */
	public List getListOfShirtSizes() {
		return listOfShirtSizes;
	}

	/**
	 * @param listOfShirtSizes
	 *            The listOfShirtSizes to set.
	 */
	public void setListOfShirtSizes(List listOfTShirtSizes) {
		this.listOfShirtSizes = listOfTShirtSizes;
	}

	/**
	 * @return Returns the shirtSizePrivacyLevel.
	 */
	public String getShirtSizePrivacyLevel() {
		return shirtSizePrivacyLevel;
	}

	/**
	 * @param shirtSizePrivacyLevel
	 *            The shirtSizePrivacyLevel to set.
	 */
	public void setShirtSizePrivacyLevel(String shirtSizePrivacyLevel) {
		this.shirtSizePrivacyLevel = shirtSizePrivacyLevel;
	}

	/**
	 * @return Returns the shirtSize.
	 */
	public String getShirtSize() {
		return shirtSize;
	}

	/**
	 * @param shirtSize
	 *            The shirtSize to set.
	 */
	public void setShirtSize(String shirtSize) {
		this.shirtSize = shirtSize;
	}

	/**
	 * @return Returns the viewProfileUrl.
	 */
	public String getViewProfileUrl() {
		return viewProfileUrl;
	}

	/**
	 * @param viewProfileUrl
	 *            The viewProfileUrl to set.
	 */
	public void setViewProfileUrl(String viewProfileUrl) {
		this.viewProfileUrl = viewProfileUrl;
	}
}