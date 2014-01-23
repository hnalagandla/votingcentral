/*
 * Created on Apr 21, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.util.Date;

import com.votingcentral.model.enums.VCPrivacyLevelEnum;
import com.votingcentral.model.enums.userinfo.DrinkTypeEnum;
import com.votingcentral.model.enums.userinfo.EducationEnum;
import com.votingcentral.model.enums.userinfo.IdeologyEnum;
import com.votingcentral.model.enums.userinfo.MaritalStatusEnum;
import com.votingcentral.model.enums.userinfo.NumChildrenEnum;
import com.votingcentral.model.enums.userinfo.PartyEnum;
import com.votingcentral.model.enums.userinfo.RaceEnum;
import com.votingcentral.model.enums.userinfo.ReligionEnum;
import com.votingcentral.model.enums.userinfo.SalaryRangeEnum;
import com.votingcentral.model.enums.userinfo.SexualOrientationEnum;
import com.votingcentral.model.enums.userinfo.SmokeTypeEnum;
import com.votingcentral.model.enums.userinfo.TShirtSizeEnum;
import com.votingcentral.model.enums.userinfo.UnionMembershipEnum;
import com.votingcentral.model.enums.userinfo.ZodiacEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCUserInfoTO {
	private long userId;

	private VCPrivacyLevelEnum profilePrivacyLevelEnum;

	private RaceEnum raceEnum;

	private VCPrivacyLevelEnum racePrivacyLevelEnum;

	private SalaryRangeEnum salaryRange;

	private VCPrivacyLevelEnum salaryPrivacyLevelEnum;

	private UnionMembershipEnum unionMember;

	private VCPrivacyLevelEnum unionPrivacyLevelEnum;

	private EducationEnum education;

	private VCPrivacyLevelEnum educationPrivacyLevelEnum;

	private PartyEnum partyEnum;

	private VCPrivacyLevelEnum partyPrivacyLevelEnum;

	private IdeologyEnum ideologyEnum;

	private VCPrivacyLevelEnum ideologyPrivacyLevelEnum;

	private ReligionEnum religionEnum;

	private VCPrivacyLevelEnum religionPrivacyLevelEnum;

	private ZodiacEnum zodiacEnum;

	private VCPrivacyLevelEnum zodiacPrivacyLevelEnum;

	private MaritalStatusEnum maritalStatusEnum;

	private VCPrivacyLevelEnum maritalPrivacyLevelEnum;

	private String imageId;

	private VCPrivacyLevelEnum imagePrivacyLevelEnum;

	private NumChildrenEnum numChildren;

	private VCPrivacyLevelEnum numChildPrivacyLevelEnum;

	private SmokeTypeEnum smokeTypeEnum;

	private VCPrivacyLevelEnum smokePrivacyLevelEnum;

	private DrinkTypeEnum drinkTypeEnum;

	private VCPrivacyLevelEnum drinkPrivacyLevelEnum;

	private String favQuote;

	private String favUrl;

	private Date createTimestamp;

	private Date modifyTimestamp;

	private SexualOrientationEnum sexOrientationEnum;

	private VCPrivacyLevelEnum sexOrientPrivacyLevelEnum;

	private long profileViewsCount;

	private String aboutMe;

	private TShirtSizeEnum tShirtSizeEnum;

	private VCPrivacyLevelEnum tShirtSizePrivacyLevelEnum;

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
	 * @return Returns the drinkPrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getDrinkPrivacyLevelEnum() {
		return drinkPrivacyLevelEnum;
	}

	/**
	 * @param drinkPrivacyLevelEnum
	 *            The drinkPrivacyLevelEnum to set.
	 */
	public void setDrinkPrivacyLevelEnum(
			VCPrivacyLevelEnum drinkPrivacyLevelEnum) {
		this.drinkPrivacyLevelEnum = drinkPrivacyLevelEnum;
	}

	/**
	 * @return Returns the drinkTypeEnum.
	 */
	public DrinkTypeEnum getDrinkTypeEnum() {
		return drinkTypeEnum;
	}

	/**
	 * @param drinkTypeEnum
	 *            The drinkTypeEnum to set.
	 */
	public void setDrinkTypeEnum(DrinkTypeEnum drinkTypeEnum) {
		this.drinkTypeEnum = drinkTypeEnum;
	}

	/**
	 * @return Returns the education.
	 */
	public EducationEnum getEducation() {
		return education;
	}

	/**
	 * @param education
	 *            The education to set.
	 */
	public void setEducation(EducationEnum education) {
		this.education = education;
	}

	/**
	 * @return Returns the educationPrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getEducationPrivacyLevelEnum() {
		return educationPrivacyLevelEnum;
	}

	/**
	 * @param educationPrivacyLevelEnum
	 *            The educationPrivacyLevelEnum to set.
	 */
	public void setEducationPrivacyLevelEnum(
			VCPrivacyLevelEnum educationPrivacyLevelEnum) {
		this.educationPrivacyLevelEnum = educationPrivacyLevelEnum;
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
	 * @return Returns the ideologyEnum.
	 */
	public IdeologyEnum getIdeologyEnum() {
		return ideologyEnum;
	}

	/**
	 * @param ideologyEnum
	 *            The ideologyEnum to set.
	 */
	public void setIdeologyEnum(IdeologyEnum ideologyEnum) {
		this.ideologyEnum = ideologyEnum;
	}

	/**
	 * @return Returns the ideologyPrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getIdeologyPrivacyLevelEnum() {
		return ideologyPrivacyLevelEnum;
	}

	/**
	 * @param ideologyPrivacyLevelEnum
	 *            The ideologyPrivacyLevelEnum to set.
	 */
	public void setIdeologyPrivacyLevelEnum(
			VCPrivacyLevelEnum ideologyPrivacyLevelEnum) {
		this.ideologyPrivacyLevelEnum = ideologyPrivacyLevelEnum;
	}

	/**
	 * @return Returns the imageId.
	 */
	public String getImageId() {
		return imageId;
	}

	/**
	 * @param imageId
	 *            The imageId to set.
	 */
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	/**
	 * @return Returns the imagePrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getImagePrivacyLevelEnum() {
		return imagePrivacyLevelEnum;
	}

	/**
	 * @param imagePrivacyLevelEnum
	 *            The imagePrivacyLevelEnum to set.
	 */
	public void setImagePrivacyLevelEnum(
			VCPrivacyLevelEnum imagePrivacyLevelEnum) {
		this.imagePrivacyLevelEnum = imagePrivacyLevelEnum;
	}

	/**
	 * @return Returns the maritalPrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getMaritalPrivacyLevelEnum() {
		return maritalPrivacyLevelEnum;
	}

	/**
	 * @param maritalPrivacyLevelEnum
	 *            The maritalPrivacyLevelEnum to set.
	 */
	public void setMaritalPrivacyLevelEnum(
			VCPrivacyLevelEnum maritalPrivacyLevelEnum) {
		this.maritalPrivacyLevelEnum = maritalPrivacyLevelEnum;
	}

	/**
	 * @return Returns the maritalStatusEnum.
	 */
	public MaritalStatusEnum getMaritalStatusEnum() {
		return maritalStatusEnum;
	}

	/**
	 * @param maritalStatusEnum
	 *            The maritalStatusEnum to set.
	 */
	public void setMaritalStatusEnum(MaritalStatusEnum maritalStatusEnum) {
		this.maritalStatusEnum = maritalStatusEnum;
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
	 * @return Returns the numChildPrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getNumChildPrivacyLevelEnum() {
		return numChildPrivacyLevelEnum;
	}

	/**
	 * @param numChildPrivacyLevelEnum
	 *            The numChildPrivacyLevelEnum to set.
	 */
	public void setNumChildPrivacyLevelEnum(
			VCPrivacyLevelEnum numChildPrivacyLevelEnum) {
		this.numChildPrivacyLevelEnum = numChildPrivacyLevelEnum;
	}

	/**
	 * @return Returns the numChildren.
	 */
	public NumChildrenEnum getNumChildren() {
		return numChildren;
	}

	/**
	 * @param numChildren
	 *            The numChildren to set.
	 */
	public void setNumChildren(NumChildrenEnum numChildren) {
		this.numChildren = numChildren;
	}

	/**
	 * @return Returns the partyEnum.
	 */
	public PartyEnum getPartyEnum() {
		return partyEnum;
	}

	/**
	 * @param partyEnum
	 *            The partyEnum to set.
	 */
	public void setPartyEnum(PartyEnum partyEnum) {
		this.partyEnum = partyEnum;
	}

	/**
	 * @return Returns the partyPrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getPartyPrivacyLevelEnum() {
		return partyPrivacyLevelEnum;
	}

	/**
	 * @param partyPrivacyLevelEnum
	 *            The partyPrivacyLevelEnum to set.
	 */
	public void setPartyPrivacyLevelEnum(
			VCPrivacyLevelEnum partyPrivacyLevelEnum) {
		this.partyPrivacyLevelEnum = partyPrivacyLevelEnum;
	}

	/**
	 * @return Returns the profilePrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getProfilePrivacyLevelEnum() {
		return profilePrivacyLevelEnum;
	}

	/**
	 * @param profilePrivacyLevelEnum
	 *            The profilePrivacyLevelEnum to set.
	 */
	public void setProfilePrivacyLevelEnum(
			VCPrivacyLevelEnum profilePrivacyLevelEnum) {
		this.profilePrivacyLevelEnum = profilePrivacyLevelEnum;
	}

	/**
	 * @return Returns the profileViewsCount.
	 */
	public long getProfileViewsCount() {
		return profileViewsCount;
	}

	/**
	 * @param profileViewsCount
	 *            The profileViewsCount to set.
	 */
	public void setProfileViewsCount(long profileViewsCount) {
		this.profileViewsCount = profileViewsCount;
	}

	/**
	 * @return Returns the raceEnum.
	 */
	public RaceEnum getRaceEnum() {
		return raceEnum;
	}

	/**
	 * @param raceEnum
	 *            The raceEnum to set.
	 */
	public void setRaceEnum(RaceEnum raceEnum) {
		this.raceEnum = raceEnum;
	}

	/**
	 * @return Returns the racePrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getRacePrivacyLevelEnum() {
		return racePrivacyLevelEnum;
	}

	/**
	 * @param racePrivacyLevelEnum
	 *            The racePrivacyLevelEnum to set.
	 */
	public void setRacePrivacyLevelEnum(VCPrivacyLevelEnum racePrivacyLevelEnum) {
		this.racePrivacyLevelEnum = racePrivacyLevelEnum;
	}

	/**
	 * @return Returns the religionEnum.
	 */
	public ReligionEnum getReligionEnum() {
		return religionEnum;
	}

	/**
	 * @param religionEnum
	 *            The religionEnum to set.
	 */
	public void setReligionEnum(ReligionEnum religionEnum) {
		this.religionEnum = religionEnum;
	}

	/**
	 * @return Returns the religionPrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getReligionPrivacyLevelEnum() {
		return religionPrivacyLevelEnum;
	}

	/**
	 * @param religionPrivacyLevelEnum
	 *            The religionPrivacyLevelEnum to set.
	 */
	public void setReligionPrivacyLevelEnum(
			VCPrivacyLevelEnum religionPrivacyLevelEnum) {
		this.religionPrivacyLevelEnum = religionPrivacyLevelEnum;
	}

	/**
	 * @return Returns the salaryPrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getSalaryPrivacyLevelEnum() {
		return salaryPrivacyLevelEnum;
	}

	/**
	 * @param salaryPrivacyLevelEnum
	 *            The salaryPrivacyLevelEnum to set.
	 */
	public void setSalaryPrivacyLevelEnum(
			VCPrivacyLevelEnum salaryPrivacyLevelEnum) {
		this.salaryPrivacyLevelEnum = salaryPrivacyLevelEnum;
	}

	/**
	 * @return Returns the salaryRange.
	 */
	public SalaryRangeEnum getSalaryRange() {
		return salaryRange;
	}

	/**
	 * @param salaryRange
	 *            The salaryRange to set.
	 */
	public void setSalaryRange(SalaryRangeEnum salaryRange) {
		this.salaryRange = salaryRange;
	}

	/**
	 * @return Returns the sexOrientationEnum.
	 */
	public SexualOrientationEnum getSexOrientationEnum() {
		return sexOrientationEnum;
	}

	/**
	 * @param sexOrientationEnum
	 *            The sexOrientationEnum to set.
	 */
	public void setSexOrientationEnum(SexualOrientationEnum sexOrientationEnum) {
		this.sexOrientationEnum = sexOrientationEnum;
	}

	/**
	 * @return Returns the sexOrientPrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getSexOrientPrivacyLevelEnum() {
		return sexOrientPrivacyLevelEnum;
	}

	/**
	 * @param sexOrientPrivacyLevelEnum
	 *            The sexOrientPrivacyLevelEnum to set.
	 */
	public void setSexOrientPrivacyLevelEnum(
			VCPrivacyLevelEnum sexOrientPrivacyLevelEnum) {
		this.sexOrientPrivacyLevelEnum = sexOrientPrivacyLevelEnum;
	}

	/**
	 * @return Returns the smokePrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getSmokePrivacyLevelEnum() {
		return smokePrivacyLevelEnum;
	}

	/**
	 * @param smokePrivacyLevelEnum
	 *            The smokePrivacyLevelEnum to set.
	 */
	public void setSmokePrivacyLevelEnum(
			VCPrivacyLevelEnum smokePrivacyLevelEnum) {
		this.smokePrivacyLevelEnum = smokePrivacyLevelEnum;
	}

	/**
	 * @return Returns the smokeTypeEnum.
	 */
	public SmokeTypeEnum getSmokeTypeEnum() {
		return smokeTypeEnum;
	}

	/**
	 * @param smokeTypeEnum
	 *            The smokeTypeEnum to set.
	 */
	public void setSmokeTypeEnum(SmokeTypeEnum smokeTypeEnum) {
		this.smokeTypeEnum = smokeTypeEnum;
	}

	/**
	 * @return Returns the unionMember.
	 */
	public UnionMembershipEnum getUnionMember() {
		return unionMember;
	}

	/**
	 * @param unionMember
	 *            The unionMember to set.
	 */
	public void setUnionMember(UnionMembershipEnum unionMember) {
		this.unionMember = unionMember;
	}

	/**
	 * @return Returns the unionPrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getUnionPrivacyLevelEnum() {
		return unionPrivacyLevelEnum;
	}

	/**
	 * @param unionPrivacyLevelEnum
	 *            The unionPrivacyLevelEnum to set.
	 */
	public void setUnionPrivacyLevelEnum(
			VCPrivacyLevelEnum unionPrivacyLevelEnum) {
		this.unionPrivacyLevelEnum = unionPrivacyLevelEnum;
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
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return Returns the zodiacEnum.
	 */
	public ZodiacEnum getZodiacEnum() {
		return zodiacEnum;
	}

	/**
	 * @param zodiacEnum
	 *            The zodiacEnum to set.
	 */
	public void setZodiacEnum(ZodiacEnum zodiacEnum) {
		this.zodiacEnum = zodiacEnum;
	}

	/**
	 * @return Returns the zodiacPrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getZodiacPrivacyLevelEnum() {
		return zodiacPrivacyLevelEnum;
	}

	/**
	 * @param zodiacPrivacyLevelEnum
	 *            The zodiacPrivacyLevelEnum to set.
	 */
	public void setZodiacPrivacyLevelEnum(
			VCPrivacyLevelEnum zodiacPrivacyLevelEnum) {
		this.zodiacPrivacyLevelEnum = zodiacPrivacyLevelEnum;
	}

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
	 * @return Returns the tShirtSizeEnum.
	 */
	public TShirtSizeEnum getTShirtSizeEnum() {
		return tShirtSizeEnum;
	}

	/**
	 * @param shirtSizeEnum
	 *            The tShirtSizeEnum to set.
	 */
	public void setTShirtSizeEnum(TShirtSizeEnum shirtSizeEnum) {
		tShirtSizeEnum = shirtSizeEnum;
	}

	/**
	 * @return Returns the tShirtSizePrivacyLevelEnum.
	 */
	public VCPrivacyLevelEnum getTShirtSizePrivacyLevelEnum() {
		return tShirtSizePrivacyLevelEnum;
	}

	/**
	 * @param shirtSizePrivacyLevelEnum
	 *            The tShirtSizePrivacyLevelEnum to set.
	 */
	public void setTShirtSizePrivacyLevelEnum(
			VCPrivacyLevelEnum shirtSizePrivacyLevelEnum) {
		tShirtSizePrivacyLevelEnum = shirtSizePrivacyLevelEnum;
	}
}