/*
 * Created on Jul 16, 2006
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

import com.votingcentral.model.db.dao.IPollCommentsDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.PollCommentsTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollCommentsDAO extends RdbmsDAO implements IPollCommentsDAO {
	private static Log log = LogFactory.getLog(PollCommentsDAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollCommentsDAO#getCommentsByPoll(java.lang.String)
	 */
	public List getCommentsByPoll(String pollId) throws SQLException {
		String sql = SQLResources.getSQLResource("get.comments.by.pollid");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List list = new ArrayList();
		PollCommentsTO pto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollId);
			rs = pps.executeQuery();
			while (rs.next()) {
				pto = new PollCommentsTO();
				fillPollCommentsTO(rs, pto);
				list.add(pto);
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
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollCommentsDAO#getCommentsByUser(java.lang.String)
	 */
	public List getCommentsByUser(String loginName) throws SQLException {
		String sql = SQLResources.getSQLResource("get.comments.by.user");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		ArrayList list = new ArrayList();
		PollCommentsTO pto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, loginName);
			rs = pps.executeQuery();
			while (rs.next()) {
				pto = new PollCommentsTO();
				fillPollCommentsTO(rs, pto);
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
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollCommentsDAO#addCommentsForPoll(java.lang.String,
	 *      com.votingcentral.model.db.dao.to.PollCommentsTO)
	 */
	public void addCommentsForPoll(String pollId, PollCommentsTO pto)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("insert.into.comment.for.poll");
		Connection conn = null;
		PreparedStatement pps = null;
		ArrayList list = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollId);
			pps.setString(2, pto.getComment());
			pps.setString(3, pto.getUserName());
			pps.setLong(4, pto.getUserId());
			pps.setString(5, pto.getCreatorIPAddress());
			pps.setString(6, pto.isFiltered() ? "Y" : "N");
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
	 * @see com.votingcentral.model.db.dao.IPollCommentsDAO#updateCommentsForPoll(java.lang.String,
	 *      com.votingcentral.model.db.dao.to.PollCommentsTO)
	 */
	public void updateCommentsForPoll(String pollId, PollCommentsTO pto)
			throws SQLException {
		String sql = SQLResources.getSQLResource("update.comment.for.poll");
		Connection conn = null;
		PreparedStatement pps = null;
		ArrayList list = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollId);
			pps.setString(2, pto.getComment());
			pps.setString(3, pto.getUserName());
			pps.setLong(4, pto.getUserId());
			pps.setString(5, pto.getCreatorIPAddress());
			pps.setString(6, pto.isFiltered() ? "Y" : "N");

			pps.setString(7, pollId);

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

	private void fillPollCommentsTO(ResultSet rs, PollCommentsTO pto)
			throws SQLException {

		long commentId = rs.getLong("COMMENT_ID");
		pto.setCommentId(commentId);

		String local = rs.getString("POLL_ID");
		pto.setPollId(local);

		local = rs.getString("COMMENT_TEXT");
		pto.setComment(local);

		local = rs.getString("USER_NAME");
		pto.setUserName(local);

		long creatorEmailId = rs.getLong("USER_ID");
		pto.setUserId(creatorEmailId);

		local = rs.getString("USER_IP_ADDRESS");
		pto.setCreatorIPAddress(local);

		local = rs.getString("FILTER_IND");
		pto.setFiltered(local.equalsIgnoreCase("Y"));

		Timestamp createTimestamp = rs.getTimestamp("CREATE_TIMESTAMP");
		pto.setCreateTimestamp(createTimestamp);

		Timestamp modifyTimestamp = rs.getTimestamp("MODIFY_TIMESTAMP");
		pto.setModifyTimestamp(modifyTimestamp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollCommentsDAO#getPollCommentsByUserLimitByTime(long,
	 *      java.util.Date)
	 */
	public List getPollCommentsByUserLimitByTime(long emailAddressId,
			Date limitTimestamp) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.poll.comments.by.user.id.limit.time");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List list = new ArrayList();
		PollCommentsTO pto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, emailAddressId);
			pps.setTimestamp(2, new Timestamp(limitTimestamp.getTime()));
			rs = pps.executeQuery();
			while (rs.next()) {
				String pollId = rs.getString("POLL_ID");
				list.add(pollId);
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
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollCommentsDAO#getPollCommentsByUserLimitByCountTime(long,
	 *      java.util.Date, int)
	 */
	public List getPollCommentsByUserLimitByCountTime(long emailAddressId,
			Date limitTimestamp, int limit) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.poll.comments.by.user.id.limit.count.time");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List list = new ArrayList();
		PollCommentsTO pto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, emailAddressId);
			pps.setTimestamp(2, new Timestamp(limitTimestamp.getTime()));
			pps.setInt(3, limit);
			rs = pps.executeQuery();
			while (rs.next()) {
				String pollId = rs.getString("POLL_ID");
				list.add(pollId);
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
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollCommentsDAO#deleteCommentsForPoll(java.lang.String)
	 */
	public int deleteCommentsForPoll(String pollId) throws SQLException {
		String sql = SQLResources.getSQLResource("delete.comments.by.pollid");
		Connection conn = null;
		PreparedStatement pps = null;
		int numRows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollId);
			numRows = pps.executeUpdate();
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
		return numRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollCommentsDAO#getCommentsByPollAndUserId(java.lang.String,
	 *      long)
	 */
	public List getCommentsByPollAndUserId(String pollId, long userId)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.comments.by.pollid.and.userid");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List list = new ArrayList();
		PollCommentsTO pto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollId);
			pps.setLong(2, userId);
			rs = pps.executeQuery();
			while (rs.next()) {
				pto = new PollCommentsTO();
				fillPollCommentsTO(rs, pto);
				list.add(pto);
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
		return list;
	}
}