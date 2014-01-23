/*
 * Created on Aug 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;

import com.votingcentral.model.db.dao.to.PersonalConfigTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IPersonalConfigDAO {
	public PersonalConfigTO getUserByUserId(long userId) throws SQLException;

	public PersonalConfigTO getUserByUserName(String userName)
			throws SQLException;

	public void setPersonalConfig(PersonalConfigTO pto) throws Exception;
}