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
public interface IStatesDAO {
	public List getStatesForCountry(int countryId) throws SQLException;

	public StateTO getStateByStateCode(String stateCode) throws SQLException;
}