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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.IMessageBoardDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.PollTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MessageBoardDAO extends RdbmsDAO implements IMessageBoardDAO {

	private static Log log = LogFactory.getLog(MessageBoardDAO.class);

	/**
	 * 
	 * @param pto
	 * @throws SQLException
	 */
	public void createMessageBoard(PollTO pto) throws SQLException {
		String sql = SQLResources.getSQLResource("insert.new.message.board");
		Connection conn = null;
		PreparedStatement pps = null;
		ArrayList list = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pto.getPollId());
			pps.setLong(2, pto.getUserId());
			//TODO change this to CREATED and CONFIRMED later.
			pps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			pps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			//pps.setTimestamp(5, new
			// Timestamp(pto.getMessageBoardStartTimestamp().getTime()));
			//pps.setTimestamp(6, new
			// Timestamp(pto.getMessageBoardEndTimestamp().getTime()));
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
	 * @see com.votingcentral.model.db.dao.IMessageBoardDAO#getMessageBoardPollsByUserLimitByCountTime(long,
	 *      java.util.Date, int)
	 */
	public List getMessageBoardPollsByUserLimitByCountTime(long emailAddressId,
			Date limitTimestamp, int limit) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.distinct.message.board.polls.by.user.id.limit.count.time");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setLong(1, emailAddressId);
			pps1.setLong(2, emailAddressId);
			pps1.setTimestamp(3, new Timestamp(limitTimestamp.getTime()));
			pps1.setInt(4, limit);
			rs = pps1.executeQuery();
			while (rs.next()) {
				String mbId = rs.getString("MESSAGE_BOARD_ID");
				results.add(mbId);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps1 != null) {
					pps1.close();
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
	 * @see com.votingcentral.model.db.dao.IMessageBoardDAO#getMessageBoardPollsByUserLimitByTime(long,
	 *      java.util.Date)
	 */
	public List getMessageBoardPollsByUserLimitByTime(long emailAddressId,
			Date limitTimestamp) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.distinct.message.board.polls.by.user.id.limit.time");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setLong(1, emailAddressId);
			pps1.setLong(2, emailAddressId);
			pps1.setTimestamp(3, new Timestamp(limitTimestamp.getTime()));
			rs = pps1.executeQuery();
			while (rs.next()) {
				String mbId = rs.getString("MESSAGE_BOARD_ID");
				results.add(mbId);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps1 != null) {
					pps1.close();
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

}