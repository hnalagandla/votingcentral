/*
 * Created on Sep 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.user;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import com.votingcentral.env.EnvProps;
import com.votingcentral.model.bo.user.info.VCUserInfoBO;
import com.votingcentral.model.db.dao.IVCUserDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.PersonalConfigTO;
import com.votingcentral.model.db.dao.to.TextLinkDescTO;
import com.votingcentral.model.db.dao.to.VCUserInfoTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.VCPrivacyLevelEnum;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.pres.web.to.VCUserWTO;
import com.votingcentral.util.pictures.ImageUtils;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class UserBO {
	private static UserBO user = null;

	private static String resUserNames = EnvProps
			.getProperty("reserved.usernames");

	private static List resUserNamesList = null;

	private static int userByEmailCacheSize = new Integer(EnvProps
			.getProperty("user.by.email.cache.size")).intValue();

	private static Map userByEmail = Collections.synchronizedMap(new LRUMap(
			userByEmailCacheSize, 0.75F));

	private static Map userById = Collections.synchronizedMap(new LRUMap(
			userByEmailCacheSize, 0.75F));

	private static Map userByUserName = Collections.synchronizedMap(new LRUMap(
			userByEmailCacheSize, 0.75F));

	private static Log log = LogFactory.getLog(UserBO.class);

	private UserBO() {

	}

	public static UserBO getInstance() {
		if (user == null) {
			user = new UserBO();
		}
		return user;
	}

	public VCUserTO getUserByUserName(String userName) throws SQLException {
		if (userName == null || userName.length() == 0) {
			return null;
		}
		String cleanedUser = getCleanedUser(userName);
		Object to = userByUserName.get(cleanedUser);
		if (to != null) {
			log.debug("User by userName in cache.");
			return (VCUserTO) to;
		}
		return getUserByUserNameInternal(cleanedUser);
	}

	public VCUserTO getUserByUserNameOrEmailAddress(String userName,
			String emailAddress) throws SQLException {
		VCUserTO vuto = getUserByUserName(userName);
		if (vuto == null) {
			vuto = getUserByEmailAddress(emailAddress);
		}
		return vuto;
	}

	public boolean isUserNameReserved(String userName) throws SQLException {
		String cleanedUser = getCleanedUser(userName);
		if (resUserNamesList == null) {
			resUserNamesList = new ArrayList();
			String[] names = StringUtils.split(resUserNames, ",");
			for (int i = 0; i < names.length; i++) {
				resUserNamesList.add(names[i]);
			}
		}
		return resUserNamesList.contains(cleanedUser);
	}

	private VCUserTO getUserByUserNameInternal(String userName)
			throws SQLException {
		String cleanedUser = getCleanedUser(userName);
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserDAO vdao = dao.getVCUserDAO();
		VCUserTO vto = vdao.getUserByUserName(cleanedUser);
		if (vto != null) {
			log.debug("loading the user by userName from DB" + userName);
			String cleanedEmail = getCleanedUser(vto.getEmailAddress());
			vto.setEmailAddress(cleanedEmail);
			userByUserName.put(cleanedUser, vto);
			userByEmail.put(cleanedEmail, vto);
			userById.put(Long.toString(vto.getUserId()), vto);
		}
		return vto;
	}

	public VCUserTO getUserByEmailAddress(String email) throws SQLException {
		if (email == null || email.length() == 0) {
			return null;
		}
		String cleanedEmail = getCleanedUser(email);
		Object to = userByEmail.get(cleanedEmail);
		if (to != null) {
			log.debug("User by email address in cache.");
			return (VCUserTO) to;
		}
		return getUserByEmailAddressInternal(cleanedEmail);
	}

	public VCUserTO getUserByUserId(long userId) throws SQLException {
		Object to = userById.get(Long.toString(userId));
		if (to != null) {
			log.debug("User by user id in cache.");
			return (VCUserTO) to;
		}
		return getUserByUserIdInternal(userId);
	}

	public VCUserTO getFullUserByUserId(long userId) throws SQLException {
		VCUserTO to = getUserByUserId(userId);
		VCUserInfoTO vito = VCUserInfoBO.getInstance().getVCUserInfoByUserId(
				userId);
		if (vito != null) {
			to.setVcUserInfoTo(vito);
		}
		return to;
	}

	private VCUserTO getUserByEmailAddressInternal(String email)
			throws SQLException {
		String cleanedEmail = getCleanedUser(email);
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserDAO vdao = dao.getVCUserDAO();
		VCUserTO vto = vdao.getUserByEmailAddress(cleanedEmail);
		if (vto != null) {
			log.debug("loading the user by email from DB" + cleanedEmail);
			String cleanedUser = getCleanedUser(vto.getUserName());
			vto.setUserName(cleanedUser);
			userByEmail.put(cleanedEmail, vto);
			userById.put(Long.toString(vto.getUserId()), vto);
			userByUserName.put(cleanedUser, vto);
		}
		return vto;
	}

	private VCUserTO getUserByUserIdInternal(long userId) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserDAO vdao = dao.getVCUserDAO();
		VCUserTO vto = vdao.getUserById(userId);
		if (vto != null) {
			log.debug("loading the user by userid from DB" + userId);
			userByEmail.put(vto.getEmailAddress(), vto);
			userById.put(Long.toString(vto.getUserId()), vto);
			userByUserName.put(vto.getUserName(), vto);
		}
		return vto;
	}

	public synchronized boolean createUser(VCUserTO vto, PersonalConfigTO pto)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserDAO vdao = dao.getVCUserDAO();
		boolean status = vdao.createUser(vto, pto);
		if (vto != null) {
			String cleanedEmail = getCleanedUser(vto.getEmailAddress());
			String cleanedUser = getCleanedUser(vto.getUserName());
			vto.setUserName(cleanedUser);
			vto.setEmailAddress(cleanedEmail);
			userByEmail.put(cleanedEmail, vto);
			userById.put(Long.toString(vto.getUserId()), vto);
			userByUserName.put(cleanedUser, vto);
		}
		return status;
	}

	public boolean updateUser(VCUserTO vto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserDAO vdao = dao.getVCUserDAO();
		boolean status = vdao.updateUser(vto);
		if (vto != null) {
			String cleanedEmail = getCleanedUser(vto.getEmailAddress());
			String cleanedUser = getCleanedUser(vto.getUserName());
			vto.setUserName(cleanedUser);
			vto.setEmailAddress(cleanedEmail);
			userByEmail.put(cleanedEmail, vto);
			userById.put(Long.toString(vto.getUserId()), vto);
			userByUserName.put(cleanedUser, vto);
		}
		return status;
	}

	public void setAccountStatusByUserName(String userName, String status)
			throws SQLException {
		String cleanedUser = getCleanedUser(userName);
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserDAO vdao = dao.getVCUserDAO();
		vdao.setAccountStatusByLoginName(cleanedUser, status);
		getUserByUserNameInternal(cleanedUser);
	}

	public List getListOfSecurityQuestionsForView() {
		List listOfSecurityQuestions = new ArrayList();
		listOfSecurityQuestions.add(new LabelValueBean("Select from this list",
				"Select from this list"));
		listOfSecurityQuestions.add(new LabelValueBean(
				"Which street did you grow up on ?",
				"Which street did you grow up on ?"));
		listOfSecurityQuestions.add(new LabelValueBean(
				"What was the model of your first car ?",
				"What was the model of your first car ?"));
		listOfSecurityQuestions.add(new LabelValueBean(
				"What was your first pet's name ?",
				"What was your first pet's name ?"));
		return listOfSecurityQuestions;
	}

	public List getListOfDaysForView() {
		List days = new ArrayList();
		for (int i = 1; i <= 31; i++) {
			days.add(new LabelValueBean(String.valueOf(i), String.valueOf(i)));
		}
		return days;
	}

	public List getListOfMonthsForView() {
		List months = new ArrayList();
		months.add(new LabelValueBean("Jan", String.valueOf(1)));
		months.add(new LabelValueBean("Feb", String.valueOf(2)));
		months.add(new LabelValueBean("Mar", String.valueOf(3)));
		months.add(new LabelValueBean("Apr", String.valueOf(4)));
		months.add(new LabelValueBean("May", String.valueOf(5)));
		months.add(new LabelValueBean("Jun", String.valueOf(6)));
		months.add(new LabelValueBean("Jul", String.valueOf(7)));
		months.add(new LabelValueBean("Aug", String.valueOf(8)));
		months.add(new LabelValueBean("Sep", String.valueOf(9)));
		months.add(new LabelValueBean("Oct", String.valueOf(10)));
		months.add(new LabelValueBean("Nov", String.valueOf(11)));
		months.add(new LabelValueBean("Dec", String.valueOf(12)));
		return months;
	}

	private String getCleanedUser(String userName) {
		String newUsername = userName;
		// remove trailing new lines
		newUsername = StringUtils.chomp(newUsername);
		// remove trailing and leading spaces.
		newUsername = StringUtils.trim(newUsername);
		// lowercase it
		newUsername = StringUtils.lowerCase(newUsername);
		return newUsername;
	}

	public List getDisplayableUsers(List users, String domContext)
			throws SQLException {
		if (users == null) {
			return null;
		}
		List vcUserWtos = new ArrayList();
		int i = 1;
		int total = vcUserWtos.size();
		for (Iterator itr = users.iterator(); itr.hasNext(); i++) {
			VCUserTO userTo = (VCUserTO) itr.next();
			VCUserWTO wto = getDisplayableUserInfo(userTo, domContext);
			vcUserWtos.add(wto);
		}
		return vcUserWtos;
	}

	public VCUserWTO getDisplayableUserInfo(VCUserTO userTO, String domContext)
			throws SQLException {
		if (userTO == null) {
			return null;
		}
		VCUserWTO wto = new VCUserWTO();
		try {
			BeanUtils.copyProperties(wto, userTO);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String memberSince = PollTimeHelper.getInstance()
				.getMemberSinceDateFormatter().format(
						userTO.getCreateTimestamp());
		wto.setMemberSince(memberSince);
		wto.setConnectToUrl(VCURLHelper.getInviteToConnectVCUserUrl(domContext,
				userTO.getUserName()));
		wto.setUserProfileUrl(VCURLHelper.getVCUserPublicProfileUrl(domContext,
				wto.getDisplayUserName()));
		if (userTO.getVcUserInfoTo() != null
				&& userTO.getVcUserInfoTo().getImageId() != null
				&& userTO.getVcUserInfoTo().getImageId().length() > 0) {

			wto.setMinImageUrl(VCURLHelper.getDisplayImageUrl(domContext,
					userTO.getVcUserInfoTo().getImageId(), ImageUtils.THUMB_W,
					ImageUtils.THUMB_H));

			wto.setMaxImageUrl(VCURLHelper.getDisplayImageUrl(domContext,
					userTO.getVcUserInfoTo().getImageId(), ImageUtils.MAX_W,
					ImageUtils.MAX_H));

		}
		List nameValues = wto.getUserInfoNameValues();
		if (nameValues == null) {
			nameValues = new ArrayList();
		}
		VCUserInfoTO vuto = userTO.getVcUserInfoTo();
		if (vuto != null) {
			if (vuto.getDrinkTypeEnum() != null
					&& vuto.getDrinkPrivacyLevelEnum() != null
					&& vuto.getDrinkPrivacyLevelEnum() == VCPrivacyLevelEnum.PUBLIC) {
				TextLinkDescTO drink = new TextLinkDescTO("Drink", vuto
						.getDrinkTypeEnum().getName());
				nameValues.add(drink);
			}

			if (vuto.getEducation() != null
					&& vuto.getEducationPrivacyLevelEnum() != null
					&& vuto.getEducationPrivacyLevelEnum() == VCPrivacyLevelEnum.PUBLIC) {
				TextLinkDescTO education = new TextLinkDescTO("Education", vuto
						.getEducation().getName());
				nameValues.add(education);
			}

			if (vuto.getIdeologyEnum() != null
					&& vuto.getIdeologyPrivacyLevelEnum() != null
					&& vuto.getIdeologyPrivacyLevelEnum() == VCPrivacyLevelEnum.PUBLIC) {
				TextLinkDescTO ideology = new TextLinkDescTO("Ideology", vuto
						.getIdeologyEnum().getName());
				nameValues.add(ideology);
			}

			if (vuto.getMaritalStatusEnum() != null
					&& vuto.getMaritalPrivacyLevelEnum() != null
					&& vuto.getMaritalPrivacyLevelEnum() == VCPrivacyLevelEnum.PUBLIC) {
				TextLinkDescTO ms = new TextLinkDescTO("Marital Status", vuto
						.getMaritalStatusEnum().getName());
				nameValues.add(ms);
			}

			if (vuto.getNumChildren() != null
					&& vuto.getNumChildPrivacyLevelEnum() != null
					&& vuto.getNumChildPrivacyLevelEnum() == VCPrivacyLevelEnum.PUBLIC) {
				TextLinkDescTO nc = new TextLinkDescTO("Children", vuto
						.getNumChildren().getName());
				nameValues.add(nc);
			}

			if (vuto.getPartyEnum() != null
					&& vuto.getPartyPrivacyLevelEnum() != null
					&& vuto.getPartyPrivacyLevelEnum() == VCPrivacyLevelEnum.PUBLIC) {
				TextLinkDescTO party = new TextLinkDescTO("Party", vuto
						.getPartyEnum().getName());
				nameValues.add(party);
			}

			if (vuto.getRaceEnum() != null
					&& vuto.getRacePrivacyLevelEnum() != null
					&& vuto.getRacePrivacyLevelEnum() == VCPrivacyLevelEnum.PUBLIC) {
				TextLinkDescTO race = new TextLinkDescTO("Race", vuto
						.getRaceEnum().getName());
				nameValues.add(race);
			}

			if (vuto.getReligionEnum() != null
					&& vuto.getReligionPrivacyLevelEnum() != null
					&& vuto.getReligionPrivacyLevelEnum() == VCPrivacyLevelEnum.PUBLIC) {
				TextLinkDescTO rel = new TextLinkDescTO("Religion", vuto
						.getReligionEnum().getName());
				nameValues.add(rel);
			}

			if (vuto.getSalaryRange() != null
					&& vuto.getSalaryPrivacyLevelEnum() != null
					&& vuto.getSalaryPrivacyLevelEnum() == VCPrivacyLevelEnum.PUBLIC) {
				TextLinkDescTO salary = new TextLinkDescTO("Salary", vuto
						.getSalaryRange().getName());
				nameValues.add(salary);
			}

			if (vuto.getSexOrientationEnum() != null
					&& vuto.getSexOrientPrivacyLevelEnum() != null
					&& vuto.getSexOrientPrivacyLevelEnum() == VCPrivacyLevelEnum.PUBLIC) {
				TextLinkDescTO sex = new TextLinkDescTO("Sex.Orientation", vuto
						.getSexOrientationEnum().getName());
				nameValues.add(sex);
			}

			if (vuto.getSmokeTypeEnum() != null
					&& vuto.getSmokePrivacyLevelEnum() != null
					&& vuto.getSmokePrivacyLevelEnum() == VCPrivacyLevelEnum.PUBLIC) {
				TextLinkDescTO smoke = new TextLinkDescTO("Smoke", vuto
						.getSmokeTypeEnum().getName());
				nameValues.add(smoke);
			}

			if (vuto.getTShirtSizeEnum() != null
					&& vuto.getTShirtSizePrivacyLevelEnum() != null
					&& vuto.getTShirtSizePrivacyLevelEnum() == VCPrivacyLevelEnum.PUBLIC) {
				TextLinkDescTO ts = new TextLinkDescTO("T-Shirt Size", vuto
						.getTShirtSizeEnum().getName());
				nameValues.add(ts);
			}

			if (vuto.getUnionMember() != null
					&& vuto.getUnionPrivacyLevelEnum() != null
					&& vuto.getUnionPrivacyLevelEnum() == VCPrivacyLevelEnum.PUBLIC) {
				TextLinkDescTO union = new TextLinkDescTO("Union Member", vuto
						.getUnionMember().getName());
				nameValues.add(union);
			}

			if (vuto.getZodiacEnum() != null
					&& vuto.getZodiacPrivacyLevelEnum() != null
					&& vuto.getZodiacPrivacyLevelEnum() == VCPrivacyLevelEnum.PUBLIC) {
				TextLinkDescTO zo = new TextLinkDescTO("Zodiac", vuto
						.getZodiacEnum().getName());
				nameValues.add(zo);
			}

		}
		wto.setUserInfoNameValues(nameValues);

		return wto;
	}

	public List getAllPollStartedEmailableUsers() throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserDAO vdao = dao.getVCUserDAO();
		List users = vdao.getAllPollStartedEmailableUsers();
		return users;
	}

	public List getMostRecentlyJoinedUsers(int limit) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserDAO vdao = dao.getVCUserDAO();
		List users = vdao.getMostRecentlyJoinedUsers(limit);
		return users;
	}
}