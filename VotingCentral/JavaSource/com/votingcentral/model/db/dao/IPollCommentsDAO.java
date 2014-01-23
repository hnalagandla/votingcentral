/*
 * Created on Jul 16, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.votingcentral.model.db.dao.to.PollCommentsTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IPollCommentsDAO {
	public List getCommentsByPoll(String pollId) throws SQLException;

	public List getCommentsByPollAndUserId(String pollId, long userId)
			throws SQLException;

	public void addCommentsForPoll(String pollId, PollCommentsTO pto)
			throws SQLException;

	public void updateCommentsForPoll(String pollId, PollCommentsTO pto)
			throws SQLException;

	public List getCommentsByUser(String loginName) throws SQLException;

	public List getPollCommentsByUserLimitByTime(long emailAddressId,
			Date limitTimestamp) throws SQLException;

	public List getPollCommentsByUserLimitByCountTime(long emailAddressId,
			Date limitTimestamp, int limit) throws SQLException;

	public int deleteCommentsForPoll(String pollId) throws SQLException;
}