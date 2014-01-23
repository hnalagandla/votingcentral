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
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.bo.chart.AgeInterpreter;
import com.votingcentral.model.bo.chart.ChartDataGrouper;
import com.votingcentral.model.db.dao.IPollUserHistoryDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.PollUserHistoryTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollUserHistoryDAO extends RdbmsDAO implements IPollUserHistoryDAO {

	private static Log log = LogFactory.getLog(PollUserHistoryDAO.class);

	public boolean hasUserVotedInPoll(long emailAddressId, String pollId)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.poll.user.history.by.user.poll");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		boolean votedFlag = false;

		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, emailAddressId);
			pps.setString(2, pollId);
			rs = pps.executeQuery();
			int rows = 0;
			while (rs.next()) {
				rows++;
			}
			if (rows > 0) {
				votedFlag = true;
			}
		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
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
		return votedFlag;
	}

	public void createAHistoryRecord(PollUserHistoryTO puhto)
			throws SQLException {
		String sql = SQLResources.getSQLResource("create.vote.record");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		boolean votedFlag = false;

		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, puhto.getUserId());
			pps.setString(2, puhto.getPollId());
			pps.setString(3, puhto.getQuestionId());
			pps.setString(4, puhto.getAnswerId());
			pps.setString(5, puhto.getAnswerText());
			pps.setString(6, puhto.getUserIpAddress());
			pps.setString(7, puhto.getUserLocationCity());
			pps.setInt(8, puhto.getUserLocationStateId());
			pps.setString(9, puhto.getUserLocationZip());
			pps.setInt(10, puhto.getUserLocationCountryId());
			pps.setInt(11, puhto.getYearOfBirth());
			pps.setString(12, puhto.getGender());
			int rows = 0;
			rows = pps.executeUpdate();
		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollUserHistoryDAO#getPollsVotedByUserLimitByCountTime(long,
	 *      java.sql.Timestamp, int)
	 */
	public List getPollsVotedByUserLimitByCountTime(long emailAddressId,
			Date limitTimestamp, int limit) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.polls.voted.by.user.id.limit.count.time");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setLong(1, emailAddressId);
			pps1.setTimestamp(2, new Timestamp(limitTimestamp.getTime()));
			pps1.setInt(3, limit);
			rs = pps1.executeQuery();
			while (rs.next()) {
				PollUserHistoryTO pto = new PollUserHistoryTO();
				fillPollsUserHistoryTO(rs, pto);
				results.add(pto);
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
	 * @see com.votingcentral.model.db.dao.IPollUserHistoryDAO#getPollsVotedByUserLimitByTime(long,
	 *      java.sql.Timestamp)
	 */
	public List getPollsVotedByUserLimitByTime(long emailAddressId,
			Date limitTimestamp) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.polls.voted.by.user.id.limit.time");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setLong(1, emailAddressId);
			pps1.setTimestamp(2, new Timestamp(limitTimestamp.getTime()));
			rs = pps1.executeQuery();
			while (rs.next()) {
				PollUserHistoryTO pto = new PollUserHistoryTO();
				fillPollsUserHistoryTO(rs, pto);
				results.add(pto);
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

	private void fillPollsUserHistoryTO(ResultSet rs, PollUserHistoryTO puhto)
			throws SQLException {

		long emailAddressId = rs.getLong("USER_ID");
		puhto.setUserId(emailAddressId);

		String local = "";
		local = rs.getString("POLL_ID");
		puhto.setPollId(local);

		local = rs.getString("QUESTION_ID");
		puhto.setQuestionId(local);

		local = rs.getString("ANSWER_ID");
		puhto.setAnswerId(local);

		local = rs.getString("ANSWER_TEXT");
		puhto.setAnswerText(local);

		local = rs.getString("USER_IP_ADDRESS");
		puhto.setUserIpAddress(local);

		local = rs.getString("USER_LOCATION_CITY");
		puhto.setUserLocationCity(local);

		int sid = rs.getInt("USER_LOCATION_STATE");
		puhto.setUserLocationStateId(sid);

		local = rs.getString("USER_LOCATION_ZIP");
		puhto.setUserLocationZip(local);

		int cid = rs.getInt("USER_LOCATION_COUNTRY");
		puhto.setUserLocationCountryId(cid);

		int yob = rs.getInt("YOB");
		puhto.setYearOfBirth(yob);

		local = rs.getString("GENDER");
		puhto.setGender(local);

		Timestamp timestamp = rs.getTimestamp("CREATE_TIMESTAMP");
		puhto.setCreateTimestamp(timestamp);

		timestamp = rs.getTimestamp("MODIFY_TIMESTAMP");
		puhto.setModifyTimestamp(timestamp);

	}

	/**
	 *  
	 */
	public String getNextPoll(long emailAddrId) throws SQLException {
		String sql = SQLResources.getSQLResource("get.next.poll.id");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setLong(1, emailAddrId);
			rs = pps1.executeQuery();
			if (rs.next()) {
				return rs.getString("poll_id");
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
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollUserHistoryDAO#getPollHistoryByUserAndPoll(long,
	 *      java.lang.String)
	 */
	public List getPollHistoryByUserAndPoll(long emailAddressId, String pollId)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.poll.user.history.by.user.poll.time");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		boolean votedFlag = false;
		List results = new ArrayList();

		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, emailAddressId);
			pps.setString(2, pollId);
			rs = pps.executeQuery();
			int rows = 0;
			while (rs.next()) {
				PollUserHistoryTO pto = new PollUserHistoryTO();
				fillPollsUserHistoryTO(rs, pto);
				results.add(pto);
			}

		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
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
	 * @see com.votingcentral.model.db.dao.IPollUserHistoryDAO#getVotesByPollIdGroupByYears(java.lang.String)
	 */
	public Map getVotesByPollIdGroupByYears(String pollId, String questionId)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.poll.user.history.by.age");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		ChartDataGrouper group = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollId);
			pps.setString(2, questionId);
			rs = pps.executeQuery();
			int rows = 0;
			group = new ChartDataGrouper();
			while (rs.next()) {
				long total = rs.getLong("VOTES");
				String aId = rs.getString("ANSWER_ID");
				String qId = rs.getString("QUESTION_ID");
				//yob in this case
				int distType = rs.getInt("DISTRIBUTION_TYPE");
				group.groupData(qId, AgeInterpreter.getInstance()
						.getAgeRangeKeyForYear(distType), aId, total);
			}

		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
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
		return group.getGroupedData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollUserHistoryDAO#getVotesByPollIdQuestionId(java.lang.String,
	 *      java.lang.String)
	 */
	public Map getVotesByPollIdQuestionId(String pollId, String questionId)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.votes.by.poll.id.question.id");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		ChartDataGrouper group = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollId);
			pps.setString(2, questionId);
			rs = pps.executeQuery();
			int rows = 0;
			group = new ChartDataGrouper();
			while (rs.next()) {
				long total = rs.getLong("VOTES");
				String aId = rs.getString("ANSWER_ID");
				String qId = rs.getString("QUESTION_ID");
				//no distribution type since, only one dimension.
				//UnUsed
				group.groupData(qId, "Unused", aId, total);
			}

		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
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
		return group.getGroupedData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollUserHistoryDAO#getVotesByPollIdGroupByGender(java.lang.String,
	 *      java.lang.String)
	 */
	public Map getVotesByPollIdGroupByGender(String pollId, String questionId)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.poll.user.history.by.gender");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		ChartDataGrouper group = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollId);
			pps.setString(2, questionId);
			rs = pps.executeQuery();
			int rows = 0;
			group = new ChartDataGrouper();
			while (rs.next()) {
				long total = rs.getLong("VOTES");
				String aId = rs.getString("ANSWER_ID");
				String qId = rs.getString("QUESTION_ID");
				String distType = rs.getString("DISTRIBUTION_TYPE");
				group.groupData(qId, distType, aId, total);
			}
		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
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
		return group.getGroupedData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollUserHistoryDAO#getVotesByPollIdGroupByTime(java.lang.String,
	 *      java.lang.String)
	 */
	public Map getVotesByPollIdGroupByTime(String pollId, String questionId)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.poll.user.history.by.time");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		ChartDataGrouper group = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollId);
			pps.setString(2, questionId);
			rs = pps.executeQuery();
			int rows = 0;
			group = new ChartDataGrouper();
			while (rs.next()) {
				long total = rs.getLong("VOTES");
				String aId = rs.getString("ANSWER_ID");
				String qId = rs.getString("QUESTION_ID");
				String year = rs.getString("DT1");
				int month = rs.getInt("DT2");
				int day = rs.getInt("DT3"); //getting day
				String key = day + "~" + month + "~" + year;
				group.groupData(qId, key, aId, total);
			}
		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
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
		return group.getGroupedData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollUserHistoryDAO#getVotesByPollIdGroupByLocation(java.lang.String,
	 *      java.lang.String)
	 */
	public Map getVotesByPollIdGroupByLocation(String pollId, String questionId)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.poll.user.history.by.location.city");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		ChartDataGrouper group = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollId);
			pps.setString(2, questionId);
			rs = pps.executeQuery();
			int rows = 0;
			group = new ChartDataGrouper();
			while (rs.next()) {
				long total = rs.getLong("VOTES");
				String aId = rs.getString("ANSWER_ID");
				String qId = rs.getString("QUESTION_ID");
				String distType = rs.getString("DISTRIBUTION_TYPE");
				group.groupData(qId, distType, aId, total);
			}
		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
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
		return group.getGroupedData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollUserHistoryDAO#deleteVotesByPollId(java.lang.String)
	 */
	public int deleteVotesByPollId(String pollId) throws SQLException {
		String sql = SQLResources.getSQLResource("delete.votes.by.pollid");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		int rows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollId);
			rows = pps.executeUpdate();
		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
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
		return rows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollUserHistoryDAO#getVotesByPollIdQuestionIdAllData(java.lang.String,
	 *      java.lang.String)
	 */
	public List getVotesByPollIdQuestionIdAllData(String pollId,
			String questionId) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.votes.by.poll.id.question.id.all.data");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollId);
			pps.setString(2, questionId);
			rs = pps.executeQuery();
			int rows = 0;
			while (rs.next()) {
				PollUserHistoryTO pto = new PollUserHistoryTO();
				fillPollsUserHistoryTO(rs, pto);
				results.add(pto);
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
}