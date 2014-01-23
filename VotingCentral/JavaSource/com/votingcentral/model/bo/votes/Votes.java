/*
 * Created on Oct 20, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.votes;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.db.dao.IPollUserHistoryDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.db.dao.to.PollUserHistoryTO;
import com.votingcentral.model.polls.PollTimeHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Votes {
	private static Votes votes = null;

	private static final Log log = LogFactory.getLog(Votes.class);

	private Votes() {

	}

	public static Votes getInstance() {
		if (votes == null) {
			votes = new Votes();
		}
		return votes;
	}

	public boolean hasUserVotedInPoll(long userId, String pollId) {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollUserHistoryDAO pdao = dao.getPollUserHistoryDAO();
		List votes = null;
		try {
			votes = pdao.getPollHistoryByUserAndPoll(userId, pollId);
		} catch (SQLException e) {
			log.error("SQLE checking if user has voted in poll", e);
		}
		return (votes != null && votes.size() > 0) ? true : false;
	}

	public int deleteVotesForPoll(String pollId) {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollUserHistoryDAO pdao = dao.getPollUserHistoryDAO();
		try {
			return pdao.deleteVotesByPollId(pollId);
		} catch (SQLException e) {
			log.error("SQLE deleting, votes in a poll", e);
		}
		return 0;
	}

	public boolean isBlockOut(long userId, String pollId) throws SQLException {
		PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
		//if there is no block out period set for this poll
		//then everytime is a blockout time.
		if (pto.getPollBlockoutPeriodMS() == 0) {
			return true;
		}
		Date lastBlockoutStartDate = getLastBlockoutStartDate(pto);
		log.debug("Last blockout period start date: "
				+ PollTimeHelper.getInstance().getTimeString(
						lastBlockoutStartDate.getTime()));
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollUserHistoryDAO pdao = dao.getPollUserHistoryDAO();
		List votes = null;
		try {
			votes = pdao.getPollHistoryByUserAndPoll(userId, pollId);
			if (votes != null && votes.size() > 0) {
				PollUserHistoryTO puto = (PollUserHistoryTO) votes.get(0);
				Date lastVotedDate = puto.getModifyTimestamp();
				log.debug("Last voted date is: "
						+ PollTimeHelper.getInstance().getTimeString(
								lastVotedDate.getTime()));
				if (lastVotedDate.after(lastBlockoutStartDate)) {
					return true;
				}
			}
		} catch (SQLException e) {
			log.error("SQLE checking if user has voted in poll", e);
		}
		return false;
	}

	public boolean canUserVote(long voterUserId, String pollId)
			throws SQLException {
		boolean hasUserVoted = hasUserVotedInPoll(voterUserId, pollId);
		log.debug("User has voted ?" + hasUserVoted);
		boolean isBlockOut = isBlockOut(voterUserId, pollId);
		log.debug("Is block out period ?" + isBlockOut);
		return (!hasUserVoted || !isBlockOut);
	}

	//get the start date of the last block out period.
	private Date getLastBlockoutStartDate(PollTO pto) {
		Date pollStartDt = pto.getStartTimestamp();
		log.debug("Poll start date String:"
				+ PollTimeHelper.getInstance().getTimeString(
						pollStartDt.getTime()) + " millis:"
				+ pollStartDt.getTime());
		long currTime = PollTimeHelper.getInstance().getCurrentDate().getTime();
		log.debug("Current time String:"
				+ PollTimeHelper.getInstance().getTimeString(currTime)
				+ " millis:" + currTime);
		long pollStart = pollStartDt.getTime();
		long blockoutPeriod = pto.getPollBlockoutPeriodMS();
		log.debug("Blockout millis is :" + blockoutPeriod);

		//the first blockout start date is
		// pollStart plus block period.
		long firstBlockStartTime = pollStart + blockoutPeriod;
		log
				.debug("First blockout time String:"
						+ PollTimeHelper.getInstance().getTimeString(
								firstBlockStartTime) + " millis:"
						+ firstBlockStartTime);
		//if added time is greater than currTime
		//then we are still in first blockout period.
		if (blockoutPeriod == 0 || firstBlockStartTime > currTime) {
			return pollStartDt;
		}
		//keep on incrementing the time in blocks until
		//we get the last one before curr time.
		while (firstBlockStartTime < currTime) {
			firstBlockStartTime = firstBlockStartTime + blockoutPeriod;
			if (firstBlockStartTime > currTime) {
				firstBlockStartTime = firstBlockStartTime - blockoutPeriod;
				break;
			}
		}
		return PollTimeHelper.getInstance().getDate(firstBlockStartTime);
	}

	public List getVotesByPollIdQuestionId(String pollId, String questionId) {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollUserHistoryDAO pdao = dao.getPollUserHistoryDAO();
		List results = null;
		try {
			results = pdao
					.getVotesByPollIdQuestionIdAllData(pollId, questionId);
		} catch (SQLException e) {
			log.fatal("Error getting votes for pollid:" + pollId
					+ " question id:" + questionId, e);
			return Collections.EMPTY_LIST;
		}
		return results;
	}
}