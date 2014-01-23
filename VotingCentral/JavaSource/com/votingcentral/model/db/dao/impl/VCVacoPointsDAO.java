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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.IVCVacoPointsDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.VCVacoPointsTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCVacoPointsDAO extends RdbmsDAO implements IVCVacoPointsDAO {

	private static Log log = LogFactory.getLog(VCVacoPointsDAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCVacoPointsDAO#createPointsByUserId(com.votingcentral.model.db.dao.to.VCVacoPointsTO)
	 */
	public void createPointsByUserId(VCVacoPointsTO vpto) throws SQLException {
		String sql = SQLResources.getSQLResource("insert.into.vc.vaco.points");
		Connection conn = null;
		PreparedStatement pps = null;
		ArrayList list = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, vpto.getUserId());
			pps.setLong(2, vpto.getPoints());
			int numRows = pps.executeUpdate();
		} catch (SQLException e) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCVacoPointsDAO#updatePointsByUserId(com.votingcentral.model.db.dao.to.VCVacoPointsTO)
	 */
	public void updatePointsByUserId(VCVacoPointsTO vpto) throws SQLException {
		String sql = SQLResources.getSQLResource("update.vc.vaco.points");
		Connection conn = null;
		PreparedStatement pps = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, vpto.getPoints());
			//where clause
			pps.setLong(2, vpto.getPointsId());
			int numRows = pps.executeUpdate();
			log.debug("Rows updated is:" + numRows + " points id is:"
					+ vpto.getPointsId());
		} catch (SQLException e) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCVacoPointsDAO#getPointsByUserId(long)
	 */
	public List getPointsByUserId(long userId, Date from, Date to)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.vaco.points.by.user.id.from.and.to");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, userId);
			pps.setTimestamp(2, new Timestamp(from.getTime()));
			pps.setTimestamp(3, new Timestamp(to.getTime()));
			rs = pps.executeQuery();
			while (rs.next()) {
				VCVacoPointsTO vulto = new VCVacoPointsTO();
				fillVCVacoPointsTO(rs, vulto);
				results.add(vulto);
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
		return results;
	}

	private void fillVCVacoPointsTO(ResultSet rs, VCVacoPointsTO vulto)
			throws SQLException {
		long pointsId = rs.getLong("POINTS_ID");
		log.debug("Points Id from the DB is:" + pointsId);
		vulto.setPointsId(pointsId);

		long userId = rs.getLong("USER_ID");
		log.debug("User Id from the DB is:" + userId);
		vulto.setUserId(userId);

		long points = rs.getLong("POINTS");
		log.debug("points from the DB is:" + points);
		vulto.setPoints(points);

		Timestamp createTimestamp = rs.getTimestamp("CREATE_TIMESTAMP");
		vulto.setCreateTimestamp(createTimestamp);

		Timestamp modifyTimestamp = rs.getTimestamp("MODIFY_TIMESTAMP");
		vulto.setModifyTimestamp(modifyTimestamp);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCVacoPointsDAO#getPointsByUserId(long)
	 */
	public List getPointsByUserId(long userId) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.all.vaco.points.by.user.id");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, userId);
			rs = pps.executeQuery();
			while (rs.next()) {
				VCVacoPointsTO vulto = new VCVacoPointsTO();
				fillVCVacoPointsTO(rs, vulto);
				results.add(vulto);
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
		return results;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCVacoPointsDAO#getUsersWithHighestPointsDuringPeriod(java.util.Date,
	 *      java.util.Date, int)
	 */
	public List getUsersAndPointsDuringPeriod(Date from, Date to)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.users.and.points.during.period");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setTimestamp(1, new Timestamp(from.getTime()));
			pps.setTimestamp(2, new Timestamp(to.getTime()));
			rs = pps.executeQuery();
			while (rs.next()) {
				VCVacoPointsTO vulto = new VCVacoPointsTO();
				fillVCVacoPointsTO(rs, vulto);
				results.add(vulto);
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
		return results;
	}

}