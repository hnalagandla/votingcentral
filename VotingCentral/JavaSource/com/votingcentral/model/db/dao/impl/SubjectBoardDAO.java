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
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.ISubjectBoardDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.SubjectBoardTO;
import com.votingcentral.model.db.dao.to.SubjectTO;
import com.votingcentral.util.DateComparator;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SubjectBoardDAO extends RdbmsDAO implements ISubjectBoardDAO {

	private static Log log = LogFactory.getLog(SubjectBoardDAO.class);

	/**
	 * 
	 * @param pollId
	 * @return
	 */
	public SubjectBoardTO getSubjectBoard(String msgBoardId) {
		Connection conn = null;
		SubjectBoardTO mboard = new SubjectBoardTO();
		TreeMap tempMap = new TreeMap();

		try {
			conn = VCDAOFactory.getConnection();
			//Do a second query to get the message board header information.
			String sql = SQLResources.getSQLResource("get.messageboard.summary.by.mb.id");

			log.debug("SUMMARY:" + sql);
			PreparedStatement ps2 = conn.prepareStatement(sql);
			ps2.setString(1, msgBoardId);
			ps2.setQueryTimeout(60);
			ResultSet rs2 = ps2.executeQuery();

			while (rs2.next()) {
				SubjectTO sub = new SubjectTO();
				sub.setSubjectId(rs2.getString("SUBJECT_ID"));
				sub.setMsgCount(rs2.getLong("MESSAGECOUNT"));
				sub.setLastPostLoginName(rs2.getString("LASTPOSTUSER"));
				sub.setLastPostTimeStamp(rs2.getTimestamp("LASTPOSTDATE"));
				java.util.Date d = sub.getLastPostTimeStamp();
				tempMap.put(sub.getSubjectId(), sub);
			}
			rs2.close();
			ps2.close();

			//Get the info from Message_board
			String sql1 = SQLResources
					.getSQLResource("get.messageboard.by.mb.id");
			log.debug("MBOARD:" + sql1);
			PreparedStatement ps = conn.prepareStatement(sql1);
			ps.setString(1, msgBoardId);
			ps.setQueryTimeout(60);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String subId = rs.getString("SUBJECT_ID");
				SubjectTO sub = (SubjectTO) tempMap.get(subId);
				if(sub!=null){
					sub.setSubject(rs.getString("SUBJECT"));
					sub.setCreatorLoginName(rs.getString("CREATOR_EMAIL_ID"));
					sub.setCreateTimeStamp(rs.getTimestamp("CREATE_TIMESTAMP"));
				}
			}
			rs.close();
			ps.close();
			ArrayList list = new ArrayList();
			
			if(!tempMap.isEmpty()){
				Iterator it = tempMap.values().iterator();
				while (it.hasNext()) {
					SubjectTO sub = (SubjectTO) it.next();
					list.add(sub);
				}
				Collections.sort(list, new DateComparator("LastPostTimeStamp"));
			}

			mboard.setSubjects(list);
			
		} catch (SQLException sqle) {
			log.error("", sqle);
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return mboard;
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
				String polldId = rs.getString("message_board_id");
				results.add(polldId);
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
				String polldId = rs.getString("message_board_id");
				results.add(polldId);
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