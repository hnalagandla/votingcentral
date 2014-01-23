/*
 * Created on Aug 18, 2005
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
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.ITafDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.TafTO;
import com.votingcentral.model.enums.TafTypeEnum;
import com.votingcentral.model.polls.PollTimeHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TafDAO extends RdbmsDAO implements ITafDAO {

	private static Log log = LogFactory.getLog(TafDAO.class);

	public void saveTaf(List tafToObjects) throws SQLException {
		String sql = SQLResources.getSQLResource("save.taf.log");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		for (Iterator itr = tafToObjects.iterator(); itr.hasNext();) {
			TafTO tafTo = (TafTO) itr.next();
			try {
				conn = VCDAOFactory.getConnection();
				pps = conn.prepareStatement(sql);
				pps.setString(1, tafTo.getSenderIpAddress());
				pps.setString(2, tafTo.getSenderEmailAddress());
				pps.setString(3, tafTo.getReceiverEmailAddress());
				if (tafTo.getType() == TafTypeEnum.POLL) {
					String pollId = tafTo.getTafUrl().substring(
							tafTo.getTafUrl().indexOf("pollId=")
									+ "pollId=".length());
					pps.setString(4, pollId);
				} else {
					pps.setString(4, tafTo.getTafUrl());
				}
				pps.setInt(5, tafTo.getType().getId());
				pps.executeUpdate();
			} catch (SQLException e) {
				log.fatal("SQLException: " + e.getMessage());
				log.fatal("SQLState: " + e.getSQLState());
				log.fatal("VendorError: " + e.getErrorCode());
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.ITafDAO#getTafRequestsBySenderRecieverAndTime(java.lang.String,
	 *      java.lang.String, int)
	 */
	public int getTafRequestsBySenderRecieverAndTime(String senderEmail,
			String receiverEmail, long inThePastNHours) throws SQLException {
		String sql = SQLResources
				.getSQLResource("get.taf.requests.count.by.requestor.receiver.and.past.hours");
		int total = 0;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps = null;
		ArrayList list = new ArrayList();
		try {
			conn = VCDAOFactory.getConnection();
			pps = conn.prepareStatement(sql);
			pps.setString(1, senderEmail);
			pps.setString(2, receiverEmail);
			pps.setLong(3, inThePastNHours);
			rs = pps.executeQuery();
			while (rs.next()) {
				total = rs.getInt("TOTAL");
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
		return total;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.ITafDAO#mostEmailedPolIds(int)
	 */
	public List getMostEmailedPollIds(int count) throws SQLException {
		String sql = SQLResources.getSQLResource("get.most.emailed.polls");
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
				TafTO pto = new TafTO();
				fillTafTO(rs, pto);
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

	private void fillTafTO(ResultSet rs, TafTO tto) throws SQLException {

		try {
			long id = rs.getLong("TAF_REQUEST_ID");
			tto.setTafRequestId(id);
		} catch (SQLException sqle) {
			tto.setTafRequestId(0);
		}
		String local = null;
		try {
			local = rs.getString("SENDER_IP_ADDRESS");
			if (local != null) {
				tto.setSenderIpAddress(local);
			}
		} catch (SQLException sqle) {
			tto.setSenderIpAddress(null);
		}
		try {
			local = rs.getString("SENDER_EMAIL_ADDRESS");
			if (local != null) {
				tto.setSenderEmailAddress(local);
			}
		} catch (SQLException sqle) {
			tto.setSenderEmailAddress(null);
		}
		try {
			local = rs.getString("RECEIVER_EMAIL_ADDRESS");
			if (local != null) {
				tto.setReceiverEmailAddress(local);
			}
		} catch (SQLException sqle) {
			tto.setReceiverEmailAddress(null);
		}
		try {
			local = rs.getString("TAF_URL");
			if (local != null) {
				tto.setTafUrl(local);
			}
		} catch (SQLException sqle) {
			tto.setTafUrl(null);
		}
		try {
			int type = rs.getInt("TAF_TYPE");
			tto.setType(TafTypeEnum.get(type));
		} catch (SQLException sqle) {
			tto.setType(null);
		}
		try {
			Timestamp tst = rs.getTimestamp("TAF_SENT_TIMESTAMP", Calendar
					.getInstance(PollTimeHelper.POLL_TIMES_TIME_ZONE));
			tto.setTafSentTimestamp(tst);
		} catch (SQLException sqle) {
			tto.setTafSentTimestamp(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.model.db.dao.ITafDAO#deleteTafEntriesByPollId(java.lang.String)
	 */
	public int deleteTafEntriesByPollId(String pollId) throws SQLException {
		String sql = SQLResources
				.getSQLResource("delete.taf.entries.by.pollid");
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
		} catch (Exception e) {
			e.printStackTrace();
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

}