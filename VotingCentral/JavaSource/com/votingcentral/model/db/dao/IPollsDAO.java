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

import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.enums.PollsStatusEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IPollsDAO {
	public List getFeaturedPolls(int count) throws SQLException;

	public List getRecentlyEndedPolls(int count) throws SQLException;

	public List getRecentlyStartedPolls(int count) throws SQLException;

	public List getMostVotedPolls(int count) throws SQLException;

	public PollTO getPollByPollId(String pollId) throws SQLException;

	public List getPollsByUserLimitByCountTime(long emailAddressId,
			Date limitTimestamp, int limit) throws SQLException;

	public List getPollsByUserLimitByTime(long emailAddressId,
			Date limitTimestamp) throws SQLException;

	public List getPollsByUserAndStatus(long emailAddressId,
			PollsStatusEnum status) throws SQLException;

	public List getPollsByStatus(PollsStatusEnum status) throws SQLException;

	public List getPollsByType(String pollType) throws SQLException;

	public List getUnfinishedPollsLessThanMaxRetry(int maxRetryCount)
			throws SQLException;

	public void createNewPoll(PollTO pto) throws SQLException;

	public void updatePollByPollId(PollTO pto) throws SQLException;

	public int deletePollByPollId(String pollId) throws SQLException;

	public void setPollStatus(String pollId, String status) throws SQLException;

	public void setPollPriority(String pollId, int pollPriority)
			throws SQLException;

	public List getAllEndedPollsEmailStatusUnsent() throws SQLException;

	public List getAllStartedPollsEmailStatusUnsent() throws SQLException;

	public long getPollsCountByCategory(String category) throws SQLException;

	public List getPollsByCategory(String cat, String lastPollEndTime, int count)
			throws SQLException;

	public void incrementViewedCountByPollId(String pollId, int count)
			throws SQLException;

	public void setPollStartedEmailStatus(String pollId, int status)
			throws SQLException;

	public void setUnfinishedPollReminderSentNow(String pollId)
			throws SQLException;

	public List getAllActivePolls() throws SQLException;
}