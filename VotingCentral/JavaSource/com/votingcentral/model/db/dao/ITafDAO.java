/*
 * Created on Aug 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface ITafDAO {
	public void saveTaf(List tafTOObjects) throws SQLException;

	public int getTafRequestsBySenderRecieverAndTime(String senderEmail,
			String receiverEmail, long inThePastNHours) throws SQLException;

	public List getMostEmailedPollIds(int count) throws SQLException;

	public int deleteTafEntriesByPollId(String pollId) throws SQLException;
}