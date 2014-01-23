/*
 * Created on May 22, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;

import com.votingcentral.model.db.dao.to.VCUserPrefsTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IVCUserPrefsDAO {
	public VCUserPrefsTO getUserById(long userId) throws SQLException;

	public void createUser(VCUserPrefsTO vto) throws SQLException;

	public boolean updateUser(VCUserPrefsTO vto) throws SQLException;
}