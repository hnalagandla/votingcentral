/*
 * Created on Jan 27, 2007
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
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.IMailDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.MailTO;
import com.votingcentral.model.enums.VCEmailTypeEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MailDAO extends RdbmsDAO implements IMailDAO {

	private static Log log = LogFactory.getLog(MailDAO.class);

	private final static Random generator = new Random();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IMailDAO#createMailRequest(com.votingcentral.model.db.dao.to.MailTO)
	 */
	public void createMailRequest(MailTO mto, long delayMins)
			throws SQLException {
		String sql1 = SQLResources.getSQLResource("create.mail.record");
		String sql2 = SQLResources
				.getSQLResource("create.mail.record.with.delay");
		String sql = "";
		if (delayMins > 0) {
			sql = sql2;
		} else {
			sql = sql1;
		}
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		boolean votedFlag = false;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setInt(1, mto.getType().getId());
			pps.setString(2, mto.getFromAddress());
			pps.setString(3, mto.getToAddress());
			pps.setString(4, mto.getCcAddress());
			pps.setString(5, mto.getBccAddress());
			pps.setString(6, mto.getSubject());
			pps.setString(7, mto.getReplyToAddress());
			pps.setString(8, mto.getContent());
			if (delayMins > 0) {
				pps.setLong(9, delayMins);
			}
			int rows = 0;
			rows = pps.executeUpdate();
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IMailDAO#getUnsentMails(java.util.Date)
	 */
	public List getUnsentMails(Date fromTimestamp, int maxRetryCount)
			throws SQLException {
		String sql = SQLResources.getSQLResource("get.unsent.emails");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		List mails = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setInt(1, maxRetryCount);
			pps.setTimestamp(2, new Timestamp(fromTimestamp.getTime()));
			rs = pps.executeQuery();
			while (rs.next()) {
				MailTO mto = new MailTO();
				fillMailTO(rs, mto);
				mails.add(mto);
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
		return mails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IMailDAO#update(com.votingcentral.model.db.dao.to.MailTO)
	 */
	public void update(MailTO mto) throws SQLException {
		// TODO Auto-generated method stub

	}

	private void fillMailTO(ResultSet rs, MailTO mto) throws SQLException {
		long mailId = rs.getLong("MAIL_ID");
		mto.setMailId(mailId);

		int typeId = rs.getInt("TYPE_ID");
		mto.setType(VCEmailTypeEnum.get(typeId));

		String local = rs.getString("FROM_ADDRESS");
		if (local != null) {
			mto.setFromAddress(local);
		}
		local = rs.getString("TO_ADDRESS");
		if (local != null) {
			mto.setToAddress(local);
		}
		local = rs.getString("CC_ADDRESS");
		if (local != null) {
			mto.setCcAddress(local);
		}
		local = rs.getString("BCC_ADDRESS");
		if (local != null) {
			mto.setBccAddress(local);
		}
		local = rs.getString("SUBJECT");
		if (local != null) {
			mto.setSubject(local);
		}
		local = rs.getString("REPLY_ADDRESS");
		if (local != null) {
			mto.setReplyToAddress(local);
		}
		local = rs.getString("CONTENT");
		if (local != null) {
			mto.setContent(local);
		}
		int retryCount = rs.getInt("RETRY_COUNT");
		mto.setRetryCount(retryCount);

		Timestamp startTimestamp = rs.getTimestamp("START_TIMESTAMP");
		mto.setStartTimestamp(startTimestamp);

		Timestamp successTimestamp = rs.getTimestamp("SUCCESS_TIMESTAMP");
		mto.setSuccessTimestamp(successTimestamp);

		Timestamp lastRetryTimestamp = rs.getTimestamp("LAST_RETRY_TIMESTAMP");
		mto.setLastRetryTimestamp(lastRetryTimestamp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IMailDAO#delete(com.votingcentral.model.db.dao.to.MailTO)
	 */
	public void delete(MailTO mto) throws SQLException {
		String sql = SQLResources.getSQLResource("delete.when.send.failed");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		boolean votedFlag = false;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, mto.getMailId());
			int rows = 0;
			rows = pps.executeUpdate();
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
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IMailDAO#updateWhenSendFailed(com.votingcentral.model.db.dao.to.MailTO)
	 */
	public void updateWhenSendFailed(MailTO mto) throws SQLException {
		String sql = SQLResources.getSQLResource("update.when.send.failed");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		boolean votedFlag = false;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			int nextHours = 60 + generator.nextInt(60);
			log.error("Resetting the next retry to minutues from now "
					+ nextHours + " for mail id " + mto.getMailId());
			pps.setLong(1, nextHours);
			pps.setLong(2, mto.getMailId());
			int rows = 0;
			rows = pps.executeUpdate();
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

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.IMailDAO#updateWhenSendSucceed(com.votingcentral.model.db.dao.to.MailTO)
	 */
	public void updateWhenSendSucceed(MailTO mto) throws SQLException {
		String sql = SQLResources.getSQLResource("update.when.send.success");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		boolean votedFlag = false;
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setLong(1, mto.getMailId());
			int rows = 0;
			rows = pps.executeUpdate();
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
	}
}