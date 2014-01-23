/*
 * Created on Jan 15, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.IContestEntriesDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.ContestEntryTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ContestEntriesDAO implements IContestEntriesDAO {

	private static Log log = LogFactory.getLog(ContestEntriesDAO.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IContestEntriesDAO#getFilesByMessageId(java.lang.String)
	 */
	public ContestEntryTO getContestEntryByFileId(String messageId)
			throws SQLException, IOException {
		String sql = SQLResources
				.getSQLResource("get.contest.entry.by.file.id");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		ContestEntryTO cto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setString(1, messageId);
			rs = pps1.executeQuery();
			while (rs.next()) {
				cto = new ContestEntryTO();
				fillContestEntryTO(rs, cto);
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
		return cto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IContestEntriesDAO#addFile(com.votingcentral.model.db.dao.to.MessageBoardFilesTO)
	 */
	public void addContestEntry(ContestEntryTO cto) throws IOException,
			SQLException {
		String sql = SQLResources.getSQLResource("add.contest.entry");
		Connection conn = null;
		PreparedStatement pps1 = null;
		ArrayList files = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setString(1, cto.getFileId());
			pps1.setLong(2, cto.getUserId());
			// poll id is always null, since a poll is created later.
			pps1.setNull(3, Types.VARCHAR);
			pps1.setString(4, cto.getUserComments());
			pps1.setString(5, cto.getKeywords());
			pps1.setString(6, cto.getContestType());
			pps1.setString(7, cto.getFileStatus());
			int rows = pps1.executeUpdate();

		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
			throw e;
		} finally {
			try {
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

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IContestEntriesDAO#updateFileForMessageId(com.votingcentral.model.db.dao.to.MessageBoardFilesTO)
	 */
	public void updateContestEntryForFileId(ContestEntryTO cto)
			throws IOException, SQLException {
		String sql = SQLResources
				.getSQLResource("update.contest.entry.by.file.id");
		Connection conn = null;
		PreparedStatement pps1 = null;
		ArrayList files = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setString(1, cto.getFileId());
			pps1.setLong(2, cto.getUserId());
			// poll id is always null, since a poll is created later.
			pps1.setString(3, cto.getPollId());
			pps1.setString(4, cto.getUserComments());
			pps1.setString(5, cto.getContestType());
			pps1.setString(6, cto.getFileStatus());
			pps1.setString(7, cto.getFileId());

			int rows = pps1.executeUpdate();

		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
			throw e;
		} finally {
			try {
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

	}

	private void fillContestEntryTO(ResultSet rs, ContestEntryTO cto)
			throws SQLException, IOException {

		String value = rs.getString("ENTRY_FILE_ID");
		cto.setFileId(value);

		long creatorEmailAddressId = rs.getLong("USER_ID");
		cto.setUserId(creatorEmailAddressId);

		value = rs.getString("POLL_ID");
		cto.setPollId(value);

		value = rs.getString("USER_COMMENTS");
		cto.setUserComments(value);

		value = rs.getString("KEYWORDS");
		cto.setKeywords(value);

		value = rs.getString("CONTEST_TYPE");
		cto.setContestType(value);

		value = rs.getString("ENTRY_FILE_STATUS");
		cto.setFileStatus(value);

		Timestamp create = rs.getTimestamp("CREATE_TIMESTAMP");
		cto.setCreateTimestamp(create);

		Timestamp modify = rs.getTimestamp("MODIFY_TIMESTAMP");
		cto.setModifyTimestamp(modify);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IContestEntriesDAO#getFilesByContestTypeAndFileStatus(com.votingcentral.model.enums.ContestTypeEnum,
	 *      com.votingcentral.model.enums.ContestFileStatusEnum)
	 */
	public List getContestEntriesByTypeAndStatus(String contestTypeEnum,
			String contestFileStatusEnum) throws IOException, SQLException {
		String sql = SQLResources
				.getSQLResource("get.contest.entries.by.contest.type.and.file.status");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		ContestEntryTO cto = null;
		List contestFiles = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setString(1, contestTypeEnum);
			pps1.setString(2, contestFileStatusEnum);
			rs = pps1.executeQuery();
			while (rs.next()) {
				cto = new ContestEntryTO();
				fillContestEntryTO(rs, cto);
				contestFiles.add(cto);
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
		return contestFiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IContestEntriesDAO#getFilesByContestTypeAndFileStatus(com.votingcentral.model.enums.ContestTypeEnum,
	 *      com.votingcentral.model.enums.ContestFileStatusEnum)
	 */
	public List getContestEntriesByTypeStatusUserId(String contestTypeEnum,
			String contestFileStatusEnum, long userId) throws IOException,
			SQLException {
		String sql = SQLResources
				.getSQLResource("get.contest.entries.by.contest.type.file.status.user.id");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		ContestEntryTO cto = null;
		List contestFiles = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setString(1, contestTypeEnum);
			pps1.setString(2, contestFileStatusEnum);
			pps1.setLong(3, userId);
			rs = pps1.executeQuery();
			while (rs.next()) {
				cto = new ContestEntryTO();
				fillContestEntryTO(rs, cto);
				contestFiles.add(cto);
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
		return contestFiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IContestEntriesDAO#getOldestByContestTypeFileStatus(java.lang.String,
	 *      java.lang.String, int)
	 */
	public List getOldestContestEntriesByTypeAndFileStatus(
			String contestTypeEnum, String contestFileStatusEnum, int numFiles)
			throws IOException, SQLException {
		String sql = SQLResources
				.getSQLResource("get.oldest.n.number.of.contest.entries.by.contest.type.file.status");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		ContestEntryTO cto = null;
		List contestFiles = new ArrayList();
		List emailAddressIds = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setString(1, contestFileStatusEnum);
			pps1.setString(2, contestTypeEnum);
			pps1.setString(3, contestFileStatusEnum);
			pps1.setString(4, contestTypeEnum);
			pps1.setInt(5, numFiles);
			rs = pps1.executeQuery();
			// The result set could potentially return more than one row for the
			// same
			// user in that case, take the picture that was first uploaded.
			while (rs.next()) {
				cto = new ContestEntryTO();
				if (!emailAddressIds.contains(Long.toString(cto.getUserId()))) {
					fillContestEntryTO(rs, cto);
					contestFiles.add(cto);
					emailAddressIds.add(Long.toString(cto.getUserId()));
				}
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
		return contestFiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IContestEntriesDAO#getFilesByFileStatus(java.lang.String,
	 *      java.lang.String)
	 */
	public List getContestEntriesByFileStatus(String contestFileStatusEnum)
			throws IOException, SQLException {
		String sql = SQLResources
				.getSQLResource("get.contest.entries.by.file.status");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		ContestEntryTO cto = null;
		List contestFiles = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setString(1, contestFileStatusEnum);
			rs = pps1.executeQuery();
			// The result set could potentially return more than one row for the
			// same
			// user in that case, take the picture that was first uploaded.
			while (rs.next()) {
				cto = new ContestEntryTO();
				fillContestEntryTO(rs, cto);
				contestFiles.add(cto);
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
		return contestFiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IContestEntriesDAO#updateFileStatusByFileId(java.lang.String,
	 *      java.lang.String)
	 */
	public void updateContestEntryStatusByFileId(String fileId,
			String contestsFileStatusEnum) throws IOException, SQLException {
		String sql = SQLResources
				.getSQLResource("update.contest.entry.status.by.file.id");
		Connection conn = null;
		PreparedStatement pps1 = null;
		ArrayList files = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setString(1, contestsFileStatusEnum);
			pps1.setString(2, fileId);
			int rows = pps1.executeUpdate();

		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
			throw e;
		} finally {
			try {
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

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IContestEntriesDAO#getFilesByUserLimitByCountTime(long,
	 *      int, java.sql.Timestamp)
	 */
	public List getContestEntriesByUserLimitByCountTime(long emailAddressId,
			Date limitTimestamp, int limit) throws SQLException, IOException {
		String sql = SQLResources
				.getSQLResource("get.contest.entries.uploaded.by.user.id.limit.count.time");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		ContestEntryTO cto = null;
		List contestFiles = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setLong(1, emailAddressId);
			pps1.setTimestamp(2, new Timestamp(limitTimestamp.getTime()));
			pps1.setInt(3, limit);
			rs = pps1.executeQuery();
			while (rs.next()) {
				cto = new ContestEntryTO();
				fillContestEntryTO(rs, cto);
				contestFiles.add(cto);
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
		return contestFiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IContestEntriesDAO#getFilesByUserLimitByTime(long,
	 *      java.sql.Timestamp)
	 */
	public List getContestEntriesByUserLimitByTime(long emailAddressId,
			Date limitTimestamp) throws IOException, SQLException {
		String sql = SQLResources
				.getSQLResource("get.contest.entries.uploaded.by.user.id.limit.time");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		ContestEntryTO cto = null;
		List contestFiles = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setLong(1, emailAddressId);
			pps1.setTimestamp(2, new Timestamp(limitTimestamp.getTime()));
			rs = pps1.executeQuery();
			while (rs.next()) {
				cto = new ContestEntryTO();
				fillContestEntryTO(rs, cto);
				contestFiles.add(cto);
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
		return contestFiles;
	}

	public List getContestEntriesNotInUserByType(long emailAddressId,
			String contestType, int limit) throws IOException, SQLException {
		String sql = SQLResources
				.getSQLResource("get.contest.entries.not.in.user.id.by.type");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		ContestEntryTO cto = null;
		List contestFiles = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setLong(1, emailAddressId);
			pps1.setString(2, contestType);
			pps1.setInt(3, limit);
			rs = pps1.executeQuery();
			while (rs.next()) {
				cto = new ContestEntryTO();
				fillContestEntryTO(rs, cto);
				contestFiles.add(cto);
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
		return contestFiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IContestEntriesDAO#deleteContestEntriesByPollId(com.votingcentral.model.db.dao.to.ContestEntryTO)
	 */
	public int deleteContestEntriesByPollId(ContestEntryTO cto)
			throws SQLException {
		String sql = SQLResources
				.getSQLResource("delete.contest.entries.by.pollid");
		Connection conn = null;
		PreparedStatement pps1 = null;
		int rows = 0;
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setString(1, cto.getPollId());
			rows = pps1.executeUpdate();
		} catch (SQLException e) {
			log.fatal("SQLException: " + e.getMessage());
			log.fatal("SQLState: " + e.getSQLState());
			log.fatal("VendorError: " + e.getErrorCode());
			throw e;
		} finally {
			try {
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
		return rows;
	}
}