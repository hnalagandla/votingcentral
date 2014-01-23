/*
 * Created on Nov 28, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.IVCUserRolesDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.VCUserRolesTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCUserRolesDAO extends RdbmsDAO implements IVCUserRolesDAO {

	private static Log log = LogFactory.getLog(VCUserRolesDAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserRolesDAO#getUserRoles(java.lang.String)
	 */
	public List getUserRoles(String userName) throws SQLException {
		String sql = SQLResources.getSQLResource("get.roles.by.user");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List roles = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, userName);
			rs = pps.executeQuery();
			while (rs.next()) {
				VCUserRolesTO vto = new VCUserRolesTO();
				fillUserRolesTO(rs, vto);
				roles.add(vto);
			}
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return roles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserRolesDAO#addUserRole(java.lang.String,
	 *      java.lang.String)
	 */
	public void addUserRole(String userName, String role) throws SQLException {
		String sql = SQLResources.getSQLResource("add.user.role");
		Connection conn = null;
		PreparedStatement pps = null;
		ArrayList list = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, userName);
			pps.setString(2, role);
			int numRows = pps.executeUpdate();
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserRolesDAO#deleteUserRole(java.lang.String,
	 *      java.lang.String)
	 */
	public void deleteUserRole(String userName, String role)
			throws SQLException {
		String sql = SQLResources.getSQLResource("delete.user.role");
		Connection conn = null;
		PreparedStatement pps = null;
		ArrayList files = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, userName);
			pps.setString(2, role);
			int rows = pps.executeUpdate();
		} catch (SQLException e) {
			handleSQLException(e, pps);
			throw e;
		} finally {
			try {
				if (pps != null) {
					pps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
	}

	private void fillUserRolesTO(ResultSet rs, VCUserRolesTO vto)
			throws SQLException {
		String local = rs.getString("USER_NAME");
		vto.setUserName(local);

		local = rs.getString("ROLE_NAME");
		vto.setRole(local);
	}
}