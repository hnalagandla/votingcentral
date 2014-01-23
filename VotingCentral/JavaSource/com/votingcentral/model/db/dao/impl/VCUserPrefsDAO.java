/*
 * Created on Aug 1, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.IVCUserPrefsDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.VCUserPrefsTO;
import com.votingcentral.model.enums.VCPrivacyLevelEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCUserPrefsDAO extends RdbmsDAO implements IVCUserPrefsDAO {

	private static Log log = LogFactory.getLog(VCUserPrefsDAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserPrefsDAO#getUserById(long)
	 */
	public VCUserPrefsTO getUserById(long userId) throws SQLException {
		String sql = SQLResources.getSQLResource("get.vcuser.prefs.by.user.id");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		VCUserPrefsTO vto = null;

		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, userId);
			rs = pps.executeQuery();
			while (rs.next()) {
				vto = new VCUserPrefsTO();
				fillVCUserInfoTO(rs, vto);
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
		return vto;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCUserPrefsDAO#createUser(com.votingcentral.model.db.dao.to.VCUserPrefsTO)
	 */
	public void createUser(VCUserPrefsTO vto) throws SQLException {
		String sql = SQLResources.getSQLResource("insert.new.vc.user.prefs");
		Connection conn = null;
		PreparedStatement pps = null;
		int rows = 0;

		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, vto.getUserId());
			if (vto.getPollStartedLevelEnum() != null) {
				pps.setInt(2, vto.getPollStartedLevelEnum().getId());
			} else {
				pps.setInt(2, VCPrivacyLevelEnum.PUBLIC.getId());
			}
			rows = pps.executeUpdate();
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
	 * @see com.votingcentral.model.db.dao.IVCUserPrefsDAO#updateUser(com.votingcentral.model.db.dao.to.VCUserPrefsTO)
	 */
	public boolean updateUser(VCUserPrefsTO vto) throws SQLException {
		String sql = SQLResources.getSQLResource("update.vc.user.prefs");
		Connection conn = null;
		PreparedStatement pps = null;
		int rows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);

			if (vto.getPollStartedLevelEnum() != null) {
				pps.setInt(1, vto.getPollStartedLevelEnum().getId());
			} else {
				pps.setInt(1, VCPrivacyLevelEnum.PUBLIC.getId());
			}

			pps.setLong(2, vto.getUserId());
			rows = pps.executeUpdate();

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
		return rows > 0;
	}

	private void fillVCUserInfoTO(ResultSet rs, VCUserPrefsTO vto)
			throws SQLException {
		vto.setUserId(rs.getLong("user_id"));

		int value = rs.getInt("POLL_STARTED_EMAIL");
		vto.setPollStartedLevelEnum(VCPrivacyLevelEnum.get(value));
		Timestamp d1 = rs.getTimestamp("CREATE_TIMESTAMP");
		if (d1 != null) {
			vto.setCreateTimestamp(d1);
		}
		Timestamp d2 = rs.getTimestamp("MODIFY_TIMESTAMP");
		if (d2 != null) {
			vto.setModifyTimestamp(d2);
		}

	}
}