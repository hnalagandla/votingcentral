/*
 * Created on Nov 28, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IVCUserRolesDAO {
	public List getUserRoles(String userName) throws SQLException;

	public void addUserRole(String userName, String role) throws SQLException;

	public void deleteUserRole(String userName, String role)
			throws SQLException;
}