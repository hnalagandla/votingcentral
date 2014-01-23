/*
 * Created on Oct 3, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.user;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.IPersonalConfigDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.PersonalConfigTO;
import com.votingcentral.model.user.PasswordService;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PersonalConfigBO {
	private static PersonalConfigBO pcbo = null;

	private static Log log = LogFactory.getLog(PersonalConfigBO.class);

	private PersonalConfigBO() {

	}

	public static PersonalConfigBO getInstance() {
		if (pcbo == null) {
			pcbo = new PersonalConfigBO();
		}
		return pcbo;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public PersonalConfigTO getUserByUserId(long userId) throws SQLException {

		DAOFactory dao = DAOFactory.getDAOFactory();
		IPersonalConfigDAO pdao = dao.getPersonalConfigDAO();
		PersonalConfigTO pto = pdao.getUserByUserId(userId);
		return pto;
	}

	/**
	 * 
	 * @param userName
	 * @return
	 * @throws SQLException
	 */
	public PersonalConfigTO getUserByUserName(String userName)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPersonalConfigDAO pdao = dao.getPersonalConfigDAO();
		PersonalConfigTO pto = pdao.getUserByUserName(userName);
		return pto;
	}

	public String resetAndGetPasswordByUserName(long userId) throws Exception {
		String newPass = "";
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPersonalConfigDAO pdao = dao.getPersonalConfigDAO();
		PersonalConfigTO pto = pdao.getUserByUserId(userId);
		if (pto.getTempPassword().equalsIgnoreCase("TEMP")) {
			newPass = PasswordService.getInstance().generateRandomPassword(6);
		} else {
			newPass = pto.getTempPassword();
		}
		String encPass = PasswordService.getInstance().encrypt(newPass);
		pto.setEncryptedPassword(encPass);
		pto.setTempPassword(newPass);

		pdao.setPersonalConfig(pto);

		return newPass;
	}

	public void resetPasswordByUserName(PersonalConfigTO pto) throws Exception {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPersonalConfigDAO pdao = dao.getPersonalConfigDAO();
		pdao.setPersonalConfig(pto);
	}
}