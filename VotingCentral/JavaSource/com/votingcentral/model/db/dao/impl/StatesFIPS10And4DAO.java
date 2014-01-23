/*
 * Created on Jul 6, 2006
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

import com.votingcentral.model.db.dao.IStatesFIPS10And4DAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.StateTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class StatesFIPS10And4DAO extends RdbmsDAO implements
		IStatesFIPS10And4DAO {
	private static Log log = LogFactory.getLog(StatesFIPS10And4DAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IStatesFIPS10And4DAO#getFIPS10And4StateByStateCodeAndCountryCode(java.lang.String,
	 *      int)
	 */
	public StateTO getFIPS10And4StateByStateCodeAndCountryCode(
			String countryCode, String stateCode) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.fips.10_4.state.by.country.code.and.state.code");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		StateTO sto = new StateTO();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, countryCode);
			pps.setString(2, stateCode);
			rs = pps.executeQuery();
			while (rs.next()) {
				fillStateTO(rs, sto);
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
		return sto;
	}

	private void fillStateTO(ResultSet rs, StateTO sto) throws SQLException {
		int id = rs.getInt("STATE_ID");
		sto.setStateId(id);

		String cCode = rs.getString("COUNTRY_CODE");
		sto.setCountryCode(cCode);

		String stateNum = rs.getString("STATE_NUMBER");
		sto.setStateNumber(stateNum);

		String name = rs.getString("STATE_NAME");
		sto.setName(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IStatesFIPS10And4DAO#getStatesByCountryCode(java.lang.String)
	 */
	public List getStatesByCountryCode(String countryCode) throws SQLException {
		String sql = SQLResources.getSQLResource("get.fips.states.for.country");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		ArrayList results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, countryCode);
			rs = pps.executeQuery();
			while (rs.next()) {
				StateTO sto = new StateTO();
				fillStateTO(rs, sto);
				results.add(sto);

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
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IStatesFIPS10And4DAO#getFIPS10And4StateByStateName(java.lang.String)
	 */
	public StateTO getFIPS10And4StateByStateName(String stateName)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.fips.10_4.state.by.state.name");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		StateTO sto = new StateTO();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, stateName);
			rs = pps.executeQuery();
			while (rs.next()) {
				fillStateTO(rs, sto);
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
		return sto;
	}

}