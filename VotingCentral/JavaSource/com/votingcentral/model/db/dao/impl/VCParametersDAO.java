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
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.IVCParametersDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.VCParameterTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCParametersDAO extends RdbmsDAO implements IVCParametersDAO {
	private static Log log = LogFactory.getLog(VCParametersDAO.class);

	private void fillParameterTO(ResultSet rs, VCParameterTO sto)
			throws SQLException {
		long parameterId = rs.getLong("PARAMETER_ID");
		sto.setParameterId(parameterId);

		String name = rs.getString("PARAMETER_NAME");
		sto.setParameterName(name);

		String value = rs.getString("PARAMETER_VALUE");
		sto.setParameterValue(value);

		Timestamp lastModTimestamp = rs.getTimestamp("PARAMETER_LAST_MODIFIED");
		sto.setParameterLastModified(lastModTimestamp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCParametersDAO#getParameterByName(java.lang.String)
	 */
	public VCParameterTO getParameterByName(String paramterName)
			throws SQLException {
		String sql = SQLResources.getSQLResource("get.parameter.by.name");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		VCParameterTO sto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, paramterName);
			rs = pps.executeQuery();
			while (rs.next()) {
				sto = new VCParameterTO();
				fillParameterTO(rs, sto);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCParametersDAO#setParameter(com.votingcentral.model.db.dao.to.VCParameterTO)
	 */
	public void setParameter(String name, String value) throws SQLException {
		String sql1 = SQLResources.getSQLResource("update.parameter.by.name");
		String sql2 = SQLResources.getSQLResource("insert.into.parameter");
		Connection conn = null;
		PreparedStatement pps1 = null;
		PreparedStatement pps2 = null;
		int numRows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql1);
			pps1.setString(1, value);
			pps1.setString(2, name);
			numRows = pps1.executeUpdate();
			if (numRows == 0) {
				pps2 = conn.prepareStatement(sql2);
				pps2.setString(1, name);
				pps2.setString(2, value);
				numRows = pps2.executeUpdate();
			}
		} catch (SQLException e) {
			handleSQLException(e, pps1);
			handleSQLException(e, pps2);
			throw e;
		} finally {
			try {
				if (pps1 != null) {
					pps1.close();
				}
				if (pps2 != null) {
					pps2.close();
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

}