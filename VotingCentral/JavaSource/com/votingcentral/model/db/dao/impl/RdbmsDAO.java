/*
 * Created on Nov 14, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.util.ResourceLocator;

/**
 * @author Gandhari
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RdbmsDAO {

	private Connection conn = null;

	private static Log log = LogFactory.getLog(RdbmsDAO.class);

	public RdbmsDAO() {
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException {

		if (conn == null || conn.isClosed()) {
			try {
				conn = ResourceLocator.getDataSource().getConnection();
			} catch (SQLException e) {
				throw e;
			}
		}
		return conn;
	}

	/**
	 * 
	 * @param autoCommit
	 * @return
	 * @throws SQLException
	 */
	protected Connection getConnection(boolean autoCommit) throws SQLException {
		getConnection();
		conn.setAutoCommit(autoCommit);
		return conn;
	}

	/**
	 * 
	 * @throws SQLException
	 */
	protected void commit() throws SQLException {
		conn.commit();
	}

	/**
	 * 
	 * @throws SQLException
	 */
	protected void rollback() throws SQLException {
		try {
			conn.rollback();
		} finally {
			closeConnection();
		}
	}

	/**
	 * 
	 *  
	 */
	protected void closeConnection() throws SQLException {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException sqle) {
			log.fatal("Connection.close", sqle);
			throw sqle;
		}
	}

	/**
	 *  
	 */
	protected void finalize() throws Throwable {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} finally {
			super.finalize();
		}
	}

	protected void handleSQLException(SQLException e, PreparedStatement stmt) {
		if (stmt != null) {
			log.fatal("Error executing SQL: " + stmt.toString());
		}
		log.fatal("SQLException: " + e.getMessage());
		log.fatal("SQLState: " + e.getSQLState());
		log.fatal("VendorError: " + e.getErrorCode());
	}
}