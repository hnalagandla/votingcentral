/*
 * Created on Aug 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.votingcentral.model.db.dao.to.PollUserHistoryTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IPollUserHistoryDAO {

	public void createAHistoryRecord(PollUserHistoryTO puhto)
			throws SQLException;

	public int deleteVotesByPollId(String pollId) throws SQLException;

	public boolean hasUserVotedInPoll(long emailAddressId, String pollId)
			throws SQLException;

	public List getPollHistoryByUserAndPoll(long emailAddressId, String pollId)
			throws SQLException;

	public List getPollsVotedByUserLimitByCountTime(long emailAddressId,
			Date limitTimestamp, int limit) throws SQLException;

	public List getPollsVotedByUserLimitByTime(long emailAddressId,
			Date limitTimestamp) throws SQLException;

	public String getNextPoll(long emailAddrId) throws SQLException;

	public Map getVotesByPollIdGroupByYears(String pollId, String questionId)
			throws SQLException;

	public Map getVotesByPollIdGroupByGender(String pollId, String questionId)
			throws SQLException;

	public Map getVotesByPollIdGroupByTime(String pollId, String questionId)
			throws SQLException;

	public Map getVotesByPollIdGroupByLocation(String pollId, String questionId)
			throws SQLException;

	public Map getVotesByPollIdQuestionId(String pollId, String questionId)
			throws SQLException;

	public List getVotesByPollIdQuestionIdAllData(String pollId,
			String questionId) throws SQLException;
}