/*
 * Created on Aug 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.db.dao.IMessagesDAO;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.MessagesTO;
import com.votingcentral.model.db.dao.to.SubjectTO;
import com.votingcentral.model.db.dao.to.VCUserTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MessagesDAO extends RdbmsDAO implements IMessagesDAO {

	private static Log log = LogFactory.getLog(MessagesDAO.class);

	/**
	 * 
	 * @param subjectId
	 * @return
	 */
	public SubjectTO getMessagesBySubject(String subjectId) {
		SubjectTO dboard = new SubjectTO();
		String sql = SQLResources.getSQLResource("get.messages.by.subject.id");
		try {
			PreparedStatement ps = getConnection().prepareStatement(sql);
			ps.setString(1, subjectId);
			ps.setQueryTimeout(60);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				MessagesTO msg = new MessagesTO();
				msg.setMessageId(rs.getString("MESSAGE_ID"));
				msg.setSubjectId(rs.getString("SUBJECT_ID"));
				msg.setMessage(rs.getString("MESSAGE"));
				msg.setResponseToId(rs.getString("RESPONSE_TO_ID"));
				msg.setCreatorId(Integer.parseInt(rs
						.getString("CREATOR_EMAIL_ID")));
				msg.setCreatorIPAddress(rs.getString("CREATOR_IP_ADDRESS"));
				msg.setFiltered(rs.getString("FILTER_IND")
						.equalsIgnoreCase("Y") ? true : false);
				msg.setCreateTimestamp(rs.getTimestamp("CREATE_TIMESTAMP"));
				msg.setCreateTimestamp(rs.getTimestamp("MODIFY_TIMESTAMP"));

				VCUserTO person = UserBO.getInstance().getUserByUserId(
						msg.getCreatorId());
				msg.setCreatorLoginName(person.getUserName());
				msg.setJoinDate(person.getCreateTimestamp());
				dboard.addMessage(msg);
			}

			//Do a second query to get the message board header information.
			ps = getConnection().prepareStatement(
					SQLResources.getSQLResource("get.subjects.by.subject.id"));
			ps.setString(1, subjectId);
			ps.setQueryTimeout(60);
			rs = ps.executeQuery();

			while (rs.next()) {
				dboard.setSubject(rs.getString("subject"));
				dboard.setPollId(rs.getString("message_board_id"));
				dboard.setImageId(rs.getString("file_id"));
			}
			dboard.setSubjectId(subjectId);
			rs.close();
			ps.close();
		} catch (SQLException sqle) {
			log.error("", sqle);
		} finally {
			try {
				closeConnection();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return dboard;
	}

	/**
	 * 
	 * @param messageId
	 * @return
	 */
	public MessagesTO getMessage(String messageId) {
		MessagesTO msg = new MessagesTO();
		String sql = SQLResources.getSQLResource("get.message.by.message.id");
		try {
			PreparedStatement ps = getConnection().prepareStatement(sql);
			log.info("getMessage: sql>" + sql);
			ps.setString(1, messageId);
			ps.setQueryTimeout(60);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				msg.setMessageId(rs.getString("MESSAGE_ID"));
				msg.setSubjectId(rs.getString("SUBJECT_ID"));
				msg.setMessage(rs.getString("MESSAGE"));
				msg.setResponseToId(rs.getString("RESPONSE_TO_ID"));
				msg.setCreatorId(rs.getLong("EMAIL_ADDRESS_ID"));
				msg.setCreatorIPAddress(rs.getString("CREATOR_IP_ADDRESS"));
				msg.setFiltered(rs.getString("FILTER_IND")
						.equalsIgnoreCase("Y") ? true : false);
				msg.setCreateTimestamp(rs.getTimestamp("CREATE_TIMESTAMP"));
				msg.setCreateTimestamp(rs.getTimestamp("MODIFY_TIMESTAMP"));
			}

			rs.close();
			ps.close();
		} catch (SQLException sqle) {
			log.error("", sqle);
		} finally {
			try {
				closeConnection();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return msg;
	}

	/**
	 * 
	 * @param board
	 * @param msg
	 * @return
	 */
	public boolean saveMessage(SubjectTO board, MessagesTO msg) {
		boolean updateFlag = false;

		try {
			String sql = SQLResources.getSQLResource("insert.new.message");
			PreparedStatement ps = getConnection().prepareStatement(sql);
			log.info("insert.new.message: SQl>" + sql);

			ps.setString(1, msg.getMessageId());
			ps.setString(2, board.getSubjectId());
			ps.setString(3, msg.getMessage());
			ps.setString(4, msg.getResponseToId());
			ps.setLong(5, msg.getCreatorId());
			ps.setString(6, msg.getCreatorIPAddress());
			ps.setString(7, msg.isFiltered() ? "Y" : "N");
			ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
			ps.setQueryTimeout(60);
			updateFlag = ps.execute();
			ps.close();
		} catch (SQLException sqle) {
			log.error("", sqle);
		} finally {
			try {
				closeConnection();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return updateFlag;
	}

	/**
	 * 
	 * @param messageId
	 * @return
	 */
	public boolean deleteMessage(String messageId) {
		boolean updateFlag = false;

		try {
			String sql = SQLResources.getSQLResource("delete.message");
			PreparedStatement ps = getConnection().prepareStatement(sql);
			log.info("deleteMessage: sql>" + sql);
			ps.setString(1, messageId);
			ps.setQueryTimeout(60);
			updateFlag = ps.execute();
			ps.close();
		} catch (SQLException sqle) {
			log.error("", sqle);
		} finally {
			try {
				closeConnection();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return updateFlag;
	}

	/**
	 * 
	 * @param msg
	 * @return
	 */
	public boolean updateMessage(MessagesTO msg) {
		boolean updateFlag = false;

		try {
			String sql = SQLResources.getSQLResource("update.message");
			PreparedStatement ps = getConnection().prepareStatement(sql);
			log.info("updateMessage: sql>" + sql);
			ps.setString(1, msg.getMessage());
			ps.setString(2, msg.getMessageId());
			ps.setQueryTimeout(60);
			updateFlag = ps.execute();
			ps.close();
		} catch (SQLException sqle) {
			log.error("", sqle);
		} finally {
			try {
				closeConnection();
			} catch (SQLException sqle) {
				log.error("", sqle);
			}
		}
		return updateFlag;
	}
}