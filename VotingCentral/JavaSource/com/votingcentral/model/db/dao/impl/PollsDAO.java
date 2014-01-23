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
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import com.votingcentral.model.db.dao.IPollsDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.CategoryTO;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.enums.PollsStatusEnum;
import com.votingcentral.model.polls.PollData;
import com.votingcentral.model.polls.PollDataDOM;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.util.xml.XXMLException;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollsDAO extends RdbmsDAO implements IPollsDAO {

	private static Log log = LogFactory.getLog(PollsDAO.class);

	private static String DATE_PATTERN = "yyyy-MM-DD hh:mm:ss";

	private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
			DATE_PATTERN);

	public List getFeaturedPolls(int count) throws SQLException {
		String sql = SQLResources.getSQLResource("get.featured.polls");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List fp = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setInt(1, count);
			rs = pps.executeQuery();
			while (rs.next()) {
				PollTO pto = new PollTO();
				fillPollsTO(rs, pto);
				fp.add(pto);
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
		return fp;
	}

	public List getRecentlyEndedPolls(int count) throws SQLException {
		String sql = SQLResources.getSQLResource("get.recently.ended.polls");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List recEnded = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setInt(1, count);
			rs = pps.executeQuery();
			while (rs.next()) {
				PollTO pto = new PollTO();
				fillPollsTO(rs, pto);
				recEnded.add(pto);
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
		return recEnded;
	}

	public List getMostVotedPolls(int count) throws SQLException {
		String sql = SQLResources.getSQLResource("get.most.voted.polls");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List mostPop = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setInt(1, count);
			rs = pps.executeQuery();

			while (rs.next()) {
				PollTO pto = new PollTO();
				fillPollsTO(rs, pto);
				mostPop.add(pto);
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
		return mostPop;
	}

	public CategoryTO getCategoryByCategoryName(String category)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.category.by.category.name");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		CategoryTO cto = null;

		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, category);
			rs = pps.executeQuery();

			while (rs.next()) {
				cto = new CategoryTO();
				fillCatTO(rs, cto);
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
		return cto;
	}

	public PollTO getPollByPollId(String pollId) throws SQLException {
		String sql = SQLResources.getSQLResource("get.poll.by.pollid");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		PollTO pto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollId);
			rs = pps.executeQuery();
			while (rs.next()) {
				pto = new PollTO();
				fillPollsTO(rs, pto);
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
		return pto;
	}

	private void fillPollsTO(ResultSet rs, PollTO pto) throws SQLException {

		String pollId = rs.getString("POLL_ID");
		pto.setPollId(pollId);

		String pollRepeatedId = rs.getString("MASTER_POLL_ID");
		if (pollRepeatedId != null) {
			pto.setPollRepeatedId(pollRepeatedId);
		}

		long pollCreatorEmailId = rs.getLong("USER_ID");
		pto.setUserId(pollCreatorEmailId);

		String pollType = rs.getString("POLL_TYPE");
		pto.setPollType(pollType);

		String pollName = rs.getString("POLL_NAME");
		pto.setPollName(pollName);

		String pollDesc = rs.getString("POLL_DESC");
		pto.setPollDesc(pollDesc);

		String pollPrivacyLevel = rs.getString("POLL_PRIVACY_LEVEL");
		pto.setPollPrivacyLevel(pollPrivacyLevel);

		int pollPriority = rs.getInt("POLL_PRIORITY");
		pto.setPollPriority(pollPriority);

		String keywords = rs.getString("POLL_KEYWORDS");
		pto.setKeywords(keywords);

		String local = rs.getString("POLL_CATEGORY1");
		pto.setPollCategory1(local);

		local = rs.getString("POLL_CATEGORY2");
		pto.setPollCategory2(local);

		local = rs.getString("POLL_CATEGORY3");
		pto.setPollCategory3(local);

		long totalVotes = rs.getInt("POLL_TOTAL_VOTES");
		pto.setPollTotalVotes(totalVotes);

		String pollStatus = rs.getString("POLL_STATUS");
		pto.setPollStatus(pollStatus);

		String pollDataStr = rs.getString("POLL_DATA");
		if (pollDataStr != null && pollDataStr.length() > 0) {
			try {
				PollData pollData = new PollData(pollDataStr);
				pto.setPollData(pollData);
			} catch (XXMLException e) {
				// should we throw it up ??
				e.printStackTrace();
			}
		}

		Timestamp pollCreateTimestamp = rs.getTimestamp("CREATE_TIMESTAMP");
		pto.setCreateTimestamp(pollCreateTimestamp);

		Timestamp pollModifyTimestamp = rs.getTimestamp("MODIFY_TIMESTAMP");
		pto.setModifyTimestamp(pollModifyTimestamp);

		Timestamp pollStartTimestamp = rs.getTimestamp("START_TIMESTAMP");
		pto.setStartTimestamp(pollStartTimestamp);

		Timestamp pollEndTimestamp = rs.getTimestamp("END_TIMESTAMP");
		pto.setEndTimestamp(pollEndTimestamp);

		Timestamp pollExpireTimestamp = rs.getTimestamp("EXPIRE_TIMESTAMP");
		pto.setExpireTimestamp(pollExpireTimestamp);

		long pollBlockoutPeriod = rs.getLong("BLOCK_OUT_PERIOD_MS");
		pto.setPollBlockoutPeriodMS(pollBlockoutPeriod);

		long viewsCount = rs.getLong("VIEWS_COUNT");
		pto.setViewsCount(viewsCount);

		int status = rs.getInt("POLL_STARTED_EMAIL_SENT");
		pto.setPollStartedEmailSentStatus(status);

		int ufpCount = rs.getInt("UNFINISHED_POLL_REMINDER_COUNT");
		pto.setUnfinishedPollReminderCount(ufpCount);

		Timestamp ufpLastSentTimestamp = rs
				.getTimestamp("UFP_REMINDER_LAST_SENT_TIMESTAMP");
		pto.setUfpReminderLastSentTimestamp(ufpLastSentTimestamp);

	}

	public void createNewPoll(PollTO pto) throws SQLException {
		String sql = SQLResources.getSQLResource("create.new.poll");
		Connection conn = null;
		PreparedStatement pps = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pto.getPollId());
			pps.setString(2, pto.getPollRepeatedId());
			pps.setLong(3, pto.getUserId());
			pps.setString(4, pto.getPollType());
			pps.setString(5, pto.getPollName());
			pps.setString(6, pto.getPollDesc());
			pps.setString(7, pto.getPollPrivacyLevel());
			pps.setInt(8, pto.getPollPriority());
			pps.setString(9, pto.getKeywords());
			pps.setString(10, pto.getPollCategory1());
			pps.setString(11, pto.getPollCategory2());
			pps.setString(12, pto.getPollCategory3());
			pps.setLong(13, pto.getPollTotalVotes());
			pps.setString(14, pto.getPollStatus());
			if (pto.getPollData() != null) {
				try {
					PollDataDOM dom = new PollDataDOM(pto.getPollData());
					pps.setString(15, dom.getPollDataAsXMLString());
				} catch (XXMLException e1) {
					pps.setString(15, "");
				} catch (SAXException e1) {
					pps.setString(15, "");
				}
			} else {
				pps.setString(15, "");
			}

			pps.setTimestamp(16, new Timestamp(System.currentTimeMillis()));
			pps.setTimestamp(17, new Timestamp(System.currentTimeMillis()));
			pps.setTimestamp(18, new Timestamp(pto.getStartTimestamp()
					.getTime()));
			pps
					.setTimestamp(19, new Timestamp(pto.getEndTimestamp()
							.getTime()));
			pps.setTimestamp(20, new Timestamp(pto.getExpireTimestamp()
					.getTime()));
			if (pto.getPollBlockoutPeriodMS() > 0L) {
				pps.setLong(21, pto.getPollBlockoutPeriodMS());
			} else {
				pps.setNull(21, Types.BIGINT);
			}
			//Poll views
			pps.setLong(22, 0);
			//poll started email sent.
			pps.setInt(23, 0);
			//poll unfinished reminders sent.
			pps.setInt(24, 0);
			//poll unfinished reminder last sent.
			pps.setNull(25, Types.TIMESTAMP);

			int numRows = pps.executeUpdate();
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
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
	}

	public void updatePollByPollId(PollTO pto) throws SQLException {
		String sql = SQLResources.getSQLResource("update.poll.by.pollid");
		Connection conn = null;
		PreparedStatement pps = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pto.getPollId());
			pps.setString(2, pto.getPollRepeatedId());
			pps.setLong(3, pto.getUserId());
			pps.setString(4, pto.getPollType());
			pps.setString(5, pto.getPollName());
			pps.setString(6, pto.getPollDesc());
			pps.setString(7, pto.getPollPrivacyLevel());
			pps.setInt(8, pto.getPollPriority());
			pps.setString(9, pto.getKeywords());
			pps.setString(10, pto.getPollCategory1());
			pps.setString(11, pto.getPollCategory2());
			pps.setString(12, pto.getPollCategory3());
			pps.setLong(13, pto.getPollTotalVotes());
			pps.setString(14, pto.getPollStatus());

			if (pto.getPollData() != null) {
				try {
					PollDataDOM dom = new PollDataDOM(pto.getPollData());
					pps.setString(15, dom.getPollDataAsXMLString());
				} catch (XXMLException e1) {
					pps.setString(15, "");
				} catch (SAXException e1) {
					pps.setString(15, "");
				}
			} else {
				pps.setString(15, "");
			}

			pps.setTimestamp(16, new Timestamp(pto.getCreateTimestamp()
					.getTime()));
			pps.setTimestamp(17, new Timestamp(System.currentTimeMillis()));
			pps.setTimestamp(18, new Timestamp(pto.getStartTimestamp()
					.getTime()));
			pps
					.setTimestamp(19, new Timestamp(pto.getEndTimestamp()
							.getTime()));
			pps.setTimestamp(20, new Timestamp(pto.getExpireTimestamp()
					.getTime()));

			if (pto.getPollBlockoutPeriodMS() > 0L) {
				pps.setLong(21, pto.getPollBlockoutPeriodMS());
			} else {
				pps.setNull(21, Types.BIGINT);
			}
			pps.setLong(22, pto.getViewsCount());
			pps.setInt(23, pto.getPollStartedEmailSentStatus());
			pps.setInt(24, pto.getUnfinishedPollReminderCount());
			if (pto.getUfpReminderLastSentTimestamp() != null) {
				pps.setTimestamp(25, new Timestamp(pto
						.getUfpReminderLastSentTimestamp().getTime()));
			} else {
				pps.setNull(25, Types.TIMESTAMP);
			}
			// for the where clause
			pps.setString(26, pto.getPollId());

			int numRows = pps.executeUpdate();
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

	public void setPollStatus(String pollId, String status) throws SQLException {

		String sql = SQLResources.getSQLResource("set.poll.status");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		int numRows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, status);
			pps.setString(2, pollId);
			numRows = pps.executeUpdate();
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
	}

	private void fillCatTO(ResultSet rs, CategoryTO cto) throws SQLException {

		String superCat = rs.getString("SUPER_CATEGORY");
		if (superCat != null) {
			cto.setSuperCategory(superCat);
		}

		String cat = rs.getString("CATEGORY");
		if (cat != null) {
			cto.setCategory(cat);
		}

		String subCat = rs.getString("SUB_CATEGORY");
		if (subCat != null) {
			cto.setSubCategory(subCat);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollsDAO#getPollsByUserLimitByCountTime(long,
	 *      int, java.sql.Timestamp)
	 */
	public List getPollsByUserLimitByCountTime(long emailAddressId,
			Date limitTimestamp, int limit) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.polls.created.by.user.id.limit.count.time");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, emailAddressId);
			pps.setTimestamp(2, new Timestamp(limitTimestamp.getTime()));
			pps.setInt(3, limit);
			rs = pps.executeQuery();
			while (rs.next()) {
				PollTO pto = new PollTO();
				fillPollsTO(rs, pto);
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
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return results;
	}

	public List getPollsByUserLimitByTime(long emailAddressId,
			Date limitTimestamp) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.polls.created.by.user.id.limit.time");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, emailAddressId);
			pps.setTimestamp(2, new Timestamp(limitTimestamp.getTime()));
			rs = pps.executeQuery();
			while (rs.next()) {
				PollTO pto = new PollTO();
				fillPollsTO(rs, pto);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollsDAO#getPollsByUserAndStatus(long,
	 *      com.votingcentral.model.enums.PollsStatusEnum, int)
	 */
	public List getPollsByUserAndStatus(long emailAddressId,
			PollsStatusEnum status) throws SQLException {
		String sql = SQLResources.getSQLResource("get.polls.by.userid.status");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, emailAddressId);
			pps.setString(2, status.getName());

			rs = pps.executeQuery();
			while (rs.next()) {
				PollTO pto = new PollTO();
				fillPollsTO(rs, pto);
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
	 * @see com.votingcentral.model.db.dao.IPollsDAO#getAllExpiredPollsEmailStatusUnsent()
	 */
	public List getAllEndedPollsEmailStatusUnsent() throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.all.ended.polls.ended.email.not.sent");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			rs = pps.executeQuery();
			while (rs.next()) {
				PollTO pto = new PollTO();
				fillPollsTO(rs, pto);
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
	 * @see com.votingcentral.model.db.dao.IPollsDAO#getPollsCountByCategory(java.lang.String)
	 */
	public long getPollsCountByCategory(String category) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.polls.counts.by.category");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		long count = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, category);
			rs = pps.executeQuery();
			while (rs.next()) {
				count = rs.getLong("TOTAL");
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
		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollsDAO#getPollsByCategory(java.lang.String,
	 *      java.lang.String, int)
	 */
	public List getPollsByCategory(String cat, String lastPollEndTime, int count)
			throws SQLException {
		String sql = "";

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			if (lastPollEndTime != null && lastPollEndTime.length() > 0) {
				sql = SQLResources
						.getSQLResource("get.polls.by.category.before.last.poll.end.time");
				pps = conn.prepareStatement(sql);
				pps.setString(1, cat);
				DATE_FORMATTER.setTimeZone(PollTimeHelper.POLL_TIMES_TIME_ZONE);
				pps.setString(2, DATE_FORMATTER.format(new Date(Long
						.parseLong(lastPollEndTime))));
				pps.setInt(3, count);
			} else {
				sql = SQLResources.getSQLResource("get.polls.by.category");
				pps = conn.prepareStatement(sql);
				pps.setString(1, cat);
				pps.setInt(2, count);
			}
			rs = pps.executeQuery();
			while (rs.next()) {
				PollTO pto = new PollTO();
				fillPollsTO(rs, pto);
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
				handleSQLException(e, pps);
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return results;
	}

	public List getRecentlyStartedPolls(int count) throws SQLException {
		String sql = SQLResources.getSQLResource("get.recently.started.polls");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List recStarted = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setInt(1, count);
			rs = pps.executeQuery();
			while (rs.next()) {
				PollTO pto = new PollTO();
				fillPollsTO(rs, pto);
				recStarted.add(pto);
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
		return recStarted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollsDAO#incrementViewedCountByPollId(java.lang.String)
	 */
	public void incrementViewedCountByPollId(String pollId, int count)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("increment.viewed.count.by.poll.id");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		int numRows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setInt(1, count);
			pps.setString(2, pollId);
			numRows = pps.executeUpdate();
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
	}

	public void setPollPriority(String pollId, int pollPriority)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("set.poll.priority.by.poll.id");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		int numRows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setInt(1, pollPriority);
			pps.setString(2, pollId);
			numRows = pps.executeUpdate();
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollsDAO#deletePollByPollId(java.lang.String)
	 */
	public int deletePollByPollId(String pollId) throws SQLException {
		String sql = SQLResources.getSQLResource("delete.poll.by.poll.id");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		int numRows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollId);
			numRows = pps.executeUpdate();
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
		return numRows;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollsDAO#getAllStartedPollsEmailStatusUnsent()
	 */
	public List getAllStartedPollsEmailStatusUnsent() throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.all.started.polls.started.email.not.sent");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			rs = pps.executeQuery();
			while (rs.next()) {
				PollTO pto = new PollTO();
				fillPollsTO(rs, pto);
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
	 * @see com.votingcentral.model.db.dao.IPollsDAO#setPollStartedEmailStatus(java.lang.String,
	 *      int)
	 */
	public void setPollStartedEmailStatus(String pollId, int status)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("set.poll.started.status.by.poll.id");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		int numRows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setInt(1, status);
			pps.setString(2, pollId);
			numRows = pps.executeUpdate();
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollsDAO#getPollsByStatus(com.votingcentral.model.enums.PollsStatusEnum)
	 */
	public List getPollsByStatus(PollsStatusEnum status) throws SQLException {
		String sql = SQLResources.getSQLResource("get.polls.by.status");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, status.getName());
			rs = pps.executeQuery();
			while (rs.next()) {
				PollTO pto = new PollTO();
				fillPollsTO(rs, pto);
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
	 * @see com.votingcentral.model.db.dao.IPollsDAO#getUnfinishedPollsLessThanMaxRetry(int)
	 */
	public List getUnfinishedPollsLessThanMaxRetry(int maxRetryCount)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.polls.unfinished.less.than.max.retry.count");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setInt(1, maxRetryCount);
			rs = pps.executeQuery();
			while (rs.next()) {
				PollTO pto = new PollTO();
				fillPollsTO(rs, pto);
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
	 * @see com.votingcentral.model.db.dao.IPollsDAO#setUnfinishedPollSentNow(java.lang.String)
	 */
	public void setUnfinishedPollReminderSentNow(String pollId)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("set.poll.unfinished.poll.reminder.sent.now");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		int numRows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollId);
			numRows = pps.executeUpdate();
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

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IPollsDAO#getPollsByType(com.votingcentral.model.enums.PollTypeEnum)
	 */
	public List getPollsByType(String pollType) throws SQLException {
		String sql = SQLResources.getSQLResource("get.polls.by.type");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List results = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, pollType);
			rs = pps.executeQuery();
			while (rs.next()) {
				PollTO pto = new PollTO();
				fillPollsTO(rs, pto);
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
	 * @see com.votingcentral.model.db.dao.IPollsDAO#getAllActivePolls()
	 */
	public List getAllActivePolls() throws SQLException {
		String sql = SQLResources.getSQLResource("get.all.active.polls");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List fp = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			rs = pps.executeQuery();
			while (rs.next()) {
				PollTO pto = new PollTO();
				fillPollsTO(rs, pto);
				fp.add(pto);
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
		return fp;

	}

}