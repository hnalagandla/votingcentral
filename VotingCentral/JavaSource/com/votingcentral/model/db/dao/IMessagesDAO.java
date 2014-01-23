/*
 * Created on Aug 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import com.votingcentral.model.db.dao.to.MessagesTO;
import com.votingcentral.model.db.dao.to.SubjectTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IMessagesDAO {
	public SubjectTO getMessagesBySubject(String subjectId);

	public MessagesTO getMessage(String messageId);

	public boolean saveMessage(SubjectTO board, MessagesTO msg);

	public boolean deleteMessage(String messageId);

	public boolean updateMessage(MessagesTO msg);
}