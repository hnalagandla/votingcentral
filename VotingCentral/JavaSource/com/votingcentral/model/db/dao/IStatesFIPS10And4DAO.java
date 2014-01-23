/*
 * Created on Jul 6, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;
import java.util.List;

import com.votingcentral.model.db.dao.to.StateTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IStatesFIPS10And4DAO {
	public StateTO getFIPS10And4StateByStateCodeAndCountryCode(
			String countryCode, String stateCode) throws SQLException;

	public StateTO getFIPS10And4StateByStateName(String stateName)
			throws SQLException;

	public List getStatesByCountryCode(String countryCode) throws SQLException;
}