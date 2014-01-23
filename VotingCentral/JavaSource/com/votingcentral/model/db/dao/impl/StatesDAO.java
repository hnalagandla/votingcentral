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

import com.votingcentral.model.db.dao.IStatesDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.StateTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class StatesDAO extends RdbmsDAO implements IStatesDAO {
	private static Log log = LogFactory.getLog(StatesDAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IStatesDAO#getStatesForCountry(long)
	 */
	public List getStatesForCountry(int countryId) throws SQLException {
		String sql = SQLResources.getSQLResource("get.states.for.country");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		ArrayList results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, countryId);
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

	private void fillStateTO(ResultSet rs, StateTO sto) throws SQLException {
		int id = rs.getInt("ID");
		sto.setStateId(id);

		id = rs.getInt("COUNTRY_ID");
		sto.setCountryId(id);

		String local = rs.getString("CODE");
		sto.setCode(local);

		local = rs.getString("NAME");
		sto.setName(local);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IStatesDAO#getStateByStateCode(java.lang.String)
	 */
	public StateTO getStateByStateCode(String stateCode) throws SQLException {
		String sql = SQLResources.getSQLResource("get.state.by.state.code");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		StateTO sto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, stateCode);
			rs = pps.executeQuery();
			while (rs.next()) {
				sto = new StateTO();
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