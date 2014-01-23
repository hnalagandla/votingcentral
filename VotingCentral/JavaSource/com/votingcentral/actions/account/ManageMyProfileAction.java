/*
 * Created on Apr 26, 2007
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

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.user.ManageMyProfileForm;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.user.info.VCUserInfoBO;
import com.votingcentral.model.db.dao.to.VCUserInfoTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
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
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.enums.PresErrorCodesEnum;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.PresentationConstants;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ManageMyProfileAction extends VCDispatchAction {
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showProfile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "myprofile";
		String domContext = getDomainAndContext(request);
		ManageMyProfileForm myProfForm = (ManageMyProfileForm) form;
		try {
			String userName = VCRequestHelper.getUser(request);
			VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
			VCUserInfoTO vuto = VCUserInfoBO.getInstance()
					.getVCUserInfoByUserId(vto.getUserId());
			fillFormFromDB(myProfForm, vuto);
			fillFormDropDownValues(myProfForm);
			myProfForm.setViewProfileUrl(VCURLHelper.getVCUserPublicProfileUrl(
					domContext, userName));
			if (myProfForm.getErrCode() != null
					&& myProfForm.getErrCode().length() > 0) {
				try {
					int errCode = Integer.parseInt(myProfForm.getErrCode());
					String profileUrl = VCURLHelper.getVCUserPublicProfileUrl(
							domContext, userName);
					String link = "<a href=\"" + profileUrl
							+ "\">Click Here</a>";
					errors.add("name", new ActionMessage(
							"my.vc.manage.profile.update.success", link));
				} catch (NumberFormatException nfe) {
					// ignore
				}
			}
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

	/**
	 * 
	 * @param myProfForm
	 */
	private void fillFormDropDownValues(ManageMyProfileForm myProfForm) {
		myProfForm.setListOfRaces(VCUserInfoBO.getInstance().getRacesForView());
		myProfForm.setListOfSalaries(VCUserInfoBO.getInstance()
				.getSalariesForView());
		myProfForm.setListOfUnionMemberShip(VCUserInfoBO.getInstance()
				.getUnionMemberShipForView());
		myProfForm.setListOfEducation(VCUserInfoBO.getInstance()
				.getEducationForView());
		myProfForm.setListOfParties(VCUserInfoBO.getInstance()
				.getPartyForView());
		myProfForm.setListOfIdeologies(VCUserInfoBO.getInstance()
				.getIdeologyForView());
		myProfForm.setListOfReligions(VCUserInfoBO.getInstance()
				.getReligionForView());
		myProfForm.setListOfZodiacs(VCUserInfoBO.getInstance()
				.getZodiacForView());
		myProfForm.setListOfMaritalStatus(VCUserInfoBO.getInstance()
				.getMaritalStatusForView());
		myProfForm.setListOfNumChildren(VCUserInfoBO.getInstance()
				.getNumChildrenForView());
		myProfForm.setListOfSmokeTypes(VCUserInfoBO.getInstance()
				.getSmokeTypeForView());
		myProfForm.setListOfDrinkTypes(VCUserInfoBO.getInstance()
				.getDrinkTypeForView());
		myProfForm.setListOfSexOrient(VCUserInfoBO.getInstance()
				.getSexualOrientationForView());
		myProfForm.setListOfShirtSizes(VCUserInfoBO.getInstance()
				.getTShirtSizesForView());

	}

	/**
	 * 
	 * @param myProfForm
	 * @param vuto
	 */
	private void fillFormFromDB(ManageMyProfileForm myProfForm,
			VCUserInfoTO vuto) {
		if (vuto == null) {
			return;
		}
		if (vuto.getFavQuote() != null && vuto.getFavQuote().length() > 0) {
			myProfForm.setFavQuote(vuto.getFavQuote());
		}
		if (vuto.getFavUrl() != null && vuto.getFavUrl().length() > 0) {
			myProfForm.setFavUrl(vuto.getFavUrl());
		}
		if (vuto.getAboutMe() != null && vuto.getAboutMe().length() > 0) {
			myProfForm.setAboutMe(vuto.getAboutMe());
		}
		if (vuto.getRaceEnum() != null && vuto.getRaceEnum().getName() != null
				&& vuto.getRaceEnum().getName().length() > 0) {
			myProfForm.setRace(vuto.getRaceEnum().getName());
		}
		if (vuto.getRacePrivacyLevelEnum() != null
				&& vuto.getRacePrivacyLevelEnum().getName() != null
				&& vuto.getRacePrivacyLevelEnum().getName().length() > 0) {
			myProfForm.setRacePrivacyLevel(vuto.getRacePrivacyLevelEnum()
					.getName());
		}

		if (vuto.getSalaryRange() != null
				&& vuto.getSalaryRange().getName() != null
				&& vuto.getSalaryRange().getName().length() > 0) {
			myProfForm.setSalary(vuto.getSalaryRange().getName());
		}
		if (vuto.getSalaryPrivacyLevelEnum() != null
				&& vuto.getSalaryPrivacyLevelEnum().getName() != null
				&& vuto.getSalaryPrivacyLevelEnum().getName().length() > 0) {
			myProfForm.setSalaryPrivacyLevel(vuto.getSalaryPrivacyLevelEnum()
					.getName());
		}

		if (vuto.getUnionMember() != null
				&& vuto.getUnionMember().getName() != null
				&& vuto.getUnionMember().getName().length() > 0) {
			myProfForm.setUnionMember(vuto.getUnionMember().getName());
		}

		if (vuto.getUnionPrivacyLevelEnum() != null
				&& vuto.getUnionPrivacyLevelEnum().getName() != null
				&& vuto.getUnionPrivacyLevelEnum().getName().length() > 0) {
			myProfForm.setUnionPrivacyLevel(vuto.getUnionPrivacyLevelEnum()
					.getName());
		}

		if (vuto.getEducation() != null
				&& vuto.getEducation().getName() != null
				&& vuto.getEducation().getName().length() > 0) {
			myProfForm.setEducation(vuto.getEducation().getName());
		}
		if (vuto.getEducationPrivacyLevelEnum() != null
				&& vuto.getEducationPrivacyLevelEnum().getName() != null
				&& vuto.getEducationPrivacyLevelEnum().getName().length() > 0) {
			myProfForm.setEducationPrivacyLevel(vuto
					.getEducationPrivacyLevelEnum().getName());
		}

		if (vuto.getPartyEnum() != null
				&& vuto.getPartyEnum().getName() != null
				&& vuto.getPartyEnum().getName().length() > 0) {
			myProfForm.setParty(vuto.getPartyEnum().getName());
		}
		if (vuto.getPartyPrivacyLevelEnum() != null
				&& vuto.getPartyPrivacyLevelEnum().getName() != null
				&& vuto.getPartyPrivacyLevelEnum().getName().length() > 0) {
			myProfForm.setPartyPrivacyLevel(vuto.getPartyPrivacyLevelEnum()
					.getName());
		}

		if (vuto.getIdeologyEnum() != null
				&& vuto.getIdeologyEnum().getName() != null
				&& vuto.getIdeologyEnum().getName().length() > 0) {
			myProfForm.setIdeology(vuto.getIdeologyEnum().getName());
		}
		if (vuto.getIdeologyPrivacyLevelEnum() != null
				&& vuto.getIdeologyPrivacyLevelEnum().getName() != null
				&& vuto.getIdeologyPrivacyLevelEnum().getName().length() > 0) {
			myProfForm.setIdeologyPrivacyLevel(vuto
					.getIdeologyPrivacyLevelEnum().getName());
		}

		if (vuto.getReligionEnum() != null
				&& vuto.getReligionEnum().getName() != null
				&& vuto.getReligionEnum().getName().length() > 0) {
			myProfForm.setReligion(vuto.getReligionEnum().getName());
		}
		if (vuto.getReligionPrivacyLevelEnum() != null
				&& vuto.getReligionPrivacyLevelEnum().getName() != null
				&& vuto.getReligionPrivacyLevelEnum().getName().length() > 0) {
			myProfForm.setReligionPrivacyLevel(vuto
					.getReligionPrivacyLevelEnum().getName());
		}

		if (vuto.getZodiacEnum() != null
				&& vuto.getZodiacEnum().getName() != null
				&& vuto.getZodiacEnum().getName().length() > 0) {
			myProfForm.setZodiac(vuto.getZodiacEnum().getName());
		}
		if (vuto.getZodiacPrivacyLevelEnum() != null
				&& vuto.getZodiacPrivacyLevelEnum().getName() != null
				&& vuto.getZodiacPrivacyLevelEnum().getName().length() > 0) {
			myProfForm.setZodiacPrivacyLevel(vuto.getZodiacPrivacyLevelEnum()
					.getName());
		}

		if (vuto.getMaritalStatusEnum() != null
				&& vuto.getMaritalStatusEnum().getName() != null
				&& vuto.getMaritalStatusEnum().getName().length() > 0) {
			myProfForm.setMaritalStatus(vuto.getMaritalStatusEnum().getName());
		}
		if (vuto.getMaritalPrivacyLevelEnum() != null
				&& vuto.getMaritalPrivacyLevelEnum().getName() != null
				&& vuto.getMaritalPrivacyLevelEnum().getName().length() > 0) {
			myProfForm.setMaritalStatusPrivacyLevel(vuto
					.getMaritalPrivacyLevelEnum().getName());
		}

		if (vuto.getNumChildren() != null
				&& vuto.getNumChildren().getName() != null
				&& vuto.getNumChildren().getName().length() > 0) {
			myProfForm.setNumChildren(vuto.getNumChildren().getName());
		}
		if (vuto.getNumChildPrivacyLevelEnum() != null
				&& vuto.getNumChildPrivacyLevelEnum().getName() != null
				&& vuto.getNumChildPrivacyLevelEnum().getName().length() > 0) {
			myProfForm.setNumChildrenPrivacyLevel(vuto
					.getNumChildPrivacyLevelEnum().getName());
		}

		if (vuto.getSmokeTypeEnum() != null
				&& vuto.getSmokeTypeEnum().getName() != null
				&& vuto.getSmokeTypeEnum().getName().length() > 0) {
			myProfForm.setSmokeType(vuto.getSmokeTypeEnum().getName());
		}
		if (vuto.getSmokePrivacyLevelEnum() != null
				&& vuto.getSmokePrivacyLevelEnum().getName() != null
				&& vuto.getSmokePrivacyLevelEnum().getName().length() > 0) {
			myProfForm.setSmokeTypePrivacyLevel(vuto.getSmokePrivacyLevelEnum()
					.getName());
		}

		if (vuto.getDrinkTypeEnum() != null
				&& vuto.getDrinkTypeEnum().getName() != null
				&& vuto.getDrinkTypeEnum().getName().length() > 0) {
			myProfForm.setDrinkType(vuto.getDrinkTypeEnum().getName());
		}
		if (vuto.getDrinkPrivacyLevelEnum() != null
				&& vuto.getDrinkPrivacyLevelEnum().getName() != null
				&& vuto.getDrinkPrivacyLevelEnum().getName().length() > 0) {
			myProfForm.setDrinkTypePrivacyLevel(vuto.getDrinkPrivacyLevelEnum()
					.getName());
		}

		if (vuto.getSexOrientationEnum() != null
				&& vuto.getSexOrientationEnum().getName() != null
				&& vuto.getSexOrientationEnum().getName().length() > 0) {
			myProfForm.setSexOrient(vuto.getSexOrientationEnum().getName());
		}
		if (vuto.getSexOrientPrivacyLevelEnum() != null
				&& vuto.getSexOrientPrivacyLevelEnum().getName() != null
				&& vuto.getSexOrientPrivacyLevelEnum().getName().length() > 0) {
			myProfForm.setSexOrientPrivacyLevel(vuto
					.getSexOrientPrivacyLevelEnum().getName());
		}

		if (vuto.getTShirtSizeEnum() != null
				&& vuto.getTShirtSizeEnum().getName() != null
				&& vuto.getTShirtSizeEnum().getName().length() > 0) {
			myProfForm.setShirtSize(vuto.getTShirtSizeEnum().getName());
		}
		if (vuto.getTShirtSizePrivacyLevelEnum() != null
				&& vuto.getTShirtSizePrivacyLevelEnum().getName() != null
				&& vuto.getTShirtSizePrivacyLevelEnum().getName().length() > 0) {
			myProfForm.setShirtSizePrivacyLevel(vuto
					.getTShirtSizePrivacyLevelEnum().getName());
		}

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "self";
		String domContext = getDomainAndContext(request);
		ManageMyProfileForm myProfForm = (ManageMyProfileForm) form;
		try {
			String userName = VCRequestHelper.getUser(request);
			long userId = UserBO.getInstance().getUserByUserName(userName)
					.getUserId();
			VCUserInfoTO vuto = new VCUserInfoTO();
			vuto.setUserId(userId);
			fillVCUserInfoTO(vuto, myProfForm);
			VCUserInfoBO.getInstance().upsertVCUserInfo(vuto);
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
		ActionForward newFwd = new ActionForward();
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath());
		buffer.append(PresentationConstants.QUESTION_MARK);
		buffer.append(PresentationConstants.ERR_CODE);
		buffer.append(PresentationConstants.EQUALS);
		buffer.append(PresErrorCodesEnum.MY_VC_MANAGE_PROFILE_UPDATE_SUCCESS
				.getId());
		newFwd.setRedirect(true);
		newFwd.setPath(buffer.toString());
		return newFwd;
	}

	private void fillVCUserInfoTO(VCUserInfoTO vuto,
			ManageMyProfileForm myProfForm) {
		vuto.setAboutMe(myProfForm.getAboutMe());
		vuto.setFavQuote(myProfForm.getFavQuote());
		vuto.setFavUrl(myProfForm.getFavUrl());

		vuto.setRaceEnum(RaceEnum.get(myProfForm.getRace()));
		vuto.setRacePrivacyLevelEnum(VCPrivacyLevelEnum.get(myProfForm
				.getRacePrivacyLevel()));

		vuto.setSalaryRange(SalaryRangeEnum.get(myProfForm.getSalary()));
		vuto.setSalaryPrivacyLevelEnum(VCPrivacyLevelEnum.get(myProfForm
				.getSalaryPrivacyLevel()));

		vuto.setUnionMember(UnionMembershipEnum
				.get(myProfForm.getUnionMember()));
		vuto.setUnionPrivacyLevelEnum(VCPrivacyLevelEnum.get(myProfForm
				.getUnionPrivacyLevel()));

		vuto.setEducation(EducationEnum.get(myProfForm.getEducation()));
		vuto.setEducationPrivacyLevelEnum(VCPrivacyLevelEnum.get(myProfForm
				.getEducationPrivacyLevel()));

		vuto.setPartyEnum(PartyEnum.get(myProfForm.getParty()));
		vuto.setPartyPrivacyLevelEnum(VCPrivacyLevelEnum.get(myProfForm
				.getPartyPrivacyLevel()));

		vuto.setIdeologyEnum(IdeologyEnum.get(myProfForm.getIdeology()));
		vuto.setIdeologyPrivacyLevelEnum(VCPrivacyLevelEnum.get(myProfForm
				.getIdeologyPrivacyLevel()));

		vuto.setReligionEnum(ReligionEnum.get(myProfForm.getReligion()));
		vuto.setReligionPrivacyLevelEnum(VCPrivacyLevelEnum.get(myProfForm
				.getReligionPrivacyLevel()));

		vuto.setZodiacEnum(ZodiacEnum.get(myProfForm.getZodiac()));
		vuto.setZodiacPrivacyLevelEnum(VCPrivacyLevelEnum.get(myProfForm
				.getZodiacPrivacyLevel()));

		vuto.setMaritalStatusEnum(MaritalStatusEnum.get(myProfForm
				.getMaritalStatus()));
		vuto.setMaritalPrivacyLevelEnum(VCPrivacyLevelEnum.get(myProfForm
				.getMaritalStatusPrivacyLevel()));

		vuto.setNumChildren(NumChildrenEnum.get(myProfForm.getNumChildren()));
		vuto.setNumChildPrivacyLevelEnum(VCPrivacyLevelEnum.get(myProfForm
				.getNumChildrenPrivacyLevel()));

		vuto.setSmokeTypeEnum(SmokeTypeEnum.get(myProfForm.getSmokeType()));
		vuto.setSmokePrivacyLevelEnum(VCPrivacyLevelEnum.get(myProfForm
				.getSmokeTypePrivacyLevel()));

		vuto.setDrinkTypeEnum(DrinkTypeEnum.get(myProfForm.getDrinkType()));
		vuto.setDrinkPrivacyLevelEnum(VCPrivacyLevelEnum.get(myProfForm
				.getDrinkTypePrivacyLevel()));

		vuto.setSexOrientationEnum(SexualOrientationEnum.get(myProfForm
				.getSexOrient()));
		vuto.setSexOrientPrivacyLevelEnum(VCPrivacyLevelEnum.get(myProfForm
				.getSexOrientPrivacyLevel()));

		vuto.setTShirtSizeEnum(TShirtSizeEnum.get(myProfForm.getShirtSize()));
		vuto.setTShirtSizePrivacyLevelEnum(VCPrivacyLevelEnum.get(myProfForm
				.getShirtSizePrivacyLevel()));

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
		return showProfile(mapping, form, request, response);
	}
}