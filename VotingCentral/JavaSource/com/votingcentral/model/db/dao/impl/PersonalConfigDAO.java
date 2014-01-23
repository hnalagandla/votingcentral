/*
 * Created on Aug 13, 2005
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

import com.votingcentral.model.db.dao.IPersonalConfigDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.PersonalConfigTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PersonalConfigDAO extends RdbmsDAO implements IPersonalConfigDAO {

	private static Log log = LogFactory.getLog(PersonalConfigDAO.class);

	public PersonalConfigTO getUserByUserId(long userId) throws SQLException {
		String sql = SQLResources.getSQLResource("get.pc.by.user.id");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		PersonalConfigTO pto = null;

		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, userId);
			rs = pps.executeQuery();
			while (rs.next()) {
				pto = new PersonalConfigTO();
				fillPersonalConfigTO(rs, pto);
			}
		} catch (SQLException e) {
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
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return pto;

	}

	public PersonalConfigTO getUserByUserName(String userName)
			throws SQLException {
		String sql = SQLResources.getSQLResource("get.pc.by.username");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		PersonalConfigTO pto = null;

		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, userName);
			rs = pps.executeQuery();
			while (rs.next()) {
				pto = new PersonalConfigTO();
				fillPersonalConfigTO(rs, pto);
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
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return pto;

	}

	/**
	 *  
	 */
	public void setPersonalConfig(PersonalConfigTO pto) throws Exception {
		String sql = SQLResources
				.getSQLResource("set.personal.config.by.userid");
		Connection conn = null;
		PreparedStatement pps = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pto.getEncryptedPassword());
			pps.setString(2, pto.getTempPassword());
			pps.setLong(3, pto.getUserId());
			int rows = pps.executeUpdate();
			log.error("Rows updated for personal config is:" + rows
					+ " with Id:" + pto.getUserId());
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
				log.fatal("Connection.close", e);
				throw e;
			}
		}
	}

	private void fillPersonalConfigTO(ResultSet rs, PersonalConfigTO pto)
			throws SQLException {
		long id = rs.getLong("USER_ID");
		log.debug("Id from the DB is:" + id);
		pto.setUserId(id);

		String value = "";

		value = rs.getString("USER_NAME");
		if (value != null) {
			pto.setUserName(value);
		}

		value = rs.getString("EMAIL_CONFIRM_CODE");
		if (value != null) {
			pto.setEmailConfCode(value);
		}

		value = rs.getString("SECURITY_QUESTION");
		if (value != null) {
			pto.setSecurityQuestion(value);
		}

		value = rs.getString("SECURITY_ANSWER");
		if (value != null) {
			pto.setSecurityAnswer(value);
		}

		value = rs.getString("ENC_PASSWORD");
		if (value != null) {
			pto.setEncryptedPassword(value);
		}

		value = rs.getString("TEMP_PASSWORD");
		if (value != null) {
			pto.setTempPassword(value);
		}

		Timestamp d = rs.getTimestamp("CREATE_TIMESTAMP");
		if (value != null) {
			pto.setCreateTimestamp(d);
		}

		d = rs.getTimestamp("MODIFY_TIMESTAMP");
		if (value != null) {
			pto.setModifyTimestamp(d);
		}
	}
}