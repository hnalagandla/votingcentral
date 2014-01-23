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

import com.votingcentral.model.db.dao.IVCWinnersDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.VCWinnersTO;
import com.votingcentral.model.enums.VCWinTypeCodeEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCWinnersDAO extends RdbmsDAO implements IVCWinnersDAO {

	private static Log log = LogFactory.getLog(VCWinnersDAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCWinnersDAO#addWinner(com.votingcentral.model.db.dao.to.VCWinnersTO)
	 */
	public void addWinner(VCWinnersTO wto) throws SQLException {
		String sql = SQLResources.getSQLResource("insert.into.vc.winners");
		Connection conn = null;
		PreparedStatement pps = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, wto.getUserId());
			pps.setInt(2, wto.getWinTypeCode().getId());
			pps.setTimestamp(3,
					new Timestamp(wto.getStartTimeStamp().getTime()));
			pps.setTimestamp(4, new Timestamp(wto.getEndTimeStamp().getTime()));
			pps.setString(5, wto.getPollId());
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
	 * @see com.votingcentral.model.db.dao.IVCWinnersDAO#getWinnerByPeriod(java.lang.String)
	 */
	public VCWinnersTO getLastWinnerByWinType(VCWinTypeCodeEnum winTypeCode)
			throws SQLException {
		String sql = SQLResources.getSQLResource("get.last.winner.by.type");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		VCWinnersTO vulto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setInt(1, winTypeCode.getId());
			rs = pps.executeQuery();
			while (rs.next()) {
				vulto = new VCWinnersTO();
				fillVCWinnersTO(rs, vulto);
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
		return vulto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCWinnersDAO#getAllWinners(int)
	 */
	public List getAllWinners(int limit) throws SQLException {
		String sql = SQLResources.getSQLResource("get.all.winners");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setInt(1, limit);
			rs = pps.executeQuery();
			while (rs.next()) {
				VCWinnersTO vulto = new VCWinnersTO();
				fillVCWinnersTO(rs, vulto);
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

	private void fillVCWinnersTO(ResultSet rs, VCWinnersTO vulto)
			throws SQLException {

		long winId = rs.getLong("WIN_ID");
		vulto.setWinId(winId);

		long userId = rs.getLong("USER_ID");
		vulto.setUserId(userId);

		int code = rs.getInt("WIN_TYPE_CODE");
		vulto.setWinTypeCode(VCWinTypeCodeEnum.get(code));

		Timestamp periodStartTimestamp = rs.getTimestamp("START_TIMESTAMP");
		vulto.setStartTimeStamp(periodStartTimestamp);

		Timestamp pollEndTimestamp = rs.getTimestamp("END_TIMESTAMP");
		vulto.setEndTimeStamp(pollEndTimestamp);

		String pollId = rs.getString("POLL_ID");
		vulto.setPollId(pollId);

		Timestamp createTimestamp = rs.getTimestamp("CREATE_TIMESTAMP");
		vulto.setCreateTimestamp(createTimestamp);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IVCWinnersDAO#getWinnerRecordByTimeRange(com.votingcentral.model.enums.VCWinTypeCodeEnum,
	 *      java.util.Date, java.util.Date)
	 */
	public VCWinnersTO getWinnerRecordByTimeRange(
			VCWinTypeCodeEnum winTypeCode, Date start, Date end)
			throws SQLException {
		String sql = SQLResources.getSQLResource("get.winner.by.time.range");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		VCWinnersTO vulto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setTimestamp(1, new Timestamp(start.getTime()));
			pps.setTimestamp(2, new Timestamp(end.getTime()));
			pps.setInt(3, winTypeCode.getId());
			rs = pps.executeQuery();
			while (rs.next()) {
				vulto = new VCWinnersTO();
				fillVCWinnersTO(rs, vulto);
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
		return vulto;
	}
}