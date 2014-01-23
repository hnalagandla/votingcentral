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

import com.votingcentral.model.db.dao.to.SubjectBoardTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface ISubjectBoardDAO {

	public SubjectBoardTO getSubjectBoard(String pollId);

	public List getMessageBoardPollsByUserLimitByCountTime(long emailAddressId,
			Date limitTimestamp, int limit) throws SQLException;

	public List getMessageBoardPollsByUserLimitByTime(long emailAddressId,
			Date limitTimestamp) throws SQLException;
}