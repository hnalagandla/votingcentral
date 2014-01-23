/*
 * Created on Aug 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;

import com.votingcentral.model.db.dao.to.VCParameterTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IVCParametersDAO {

	public VCParameterTO getParameterByName(String paramterName)
			throws SQLException;

	public void setParameter(String name, String value) throws SQLException;
}