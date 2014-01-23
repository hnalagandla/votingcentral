/*
 * Created on Jun 22, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.messageboard;

import com.votingcentral.model.db.dao.IMessagesDAO;
import com.votingcentral.model.db.dao.IVCContentDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.MessagesTO;
import com.votingcentral.model.db.dao.to.SubjectTO;
import com.votingcentral.model.db.dao.to.VCContentTO;
import com.votingcentral.model.search.SearchTablesColumns;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MessagesBO {
	private static MessagesBO messagesBO = null;

	private MessagesBO() {

	}

	public static MessagesBO getInstance() {
		if (messagesBO == null) {
			messagesBO = new MessagesBO();
		}
		return messagesBO;
	}

	public boolean saveMessage(SubjectTO board, MessagesTO msg) {
		VCDAOFactory factory = new VCDAOFactory();
		IMessagesDAO dao = factory.getMessagesDAO();
		boolean flag = dao.saveMessage(board, msg);
		try {
			//add the same to search as well
			//all message board content is searchable.
			fillAndUpsert(msg, SearchTablesColumns.MESSAGE);
		} catch (Exception e) {
			//ignore the failure enter a search record.
		}
		return flag;
	}

	private void fillAndUpsert(MessagesTO msgTo, String columnName)
			throws Exception {
		VCContentTO vcto = new VCContentTO();
		fillVCContentTO(msgTo, vcto, columnName);
		if (vcto.getContent().length() > 0) {
			//make it searcheable
			DAOFactory dao = DAOFactory.getDAOFactory();
			IVCContentDAO vcdao = dao.getVCContentDAO();
			vcdao.insertContent(vcto);
		}
	}

	private void fillVCContentTO(MessagesTO msgTo, VCContentTO vcto,
			String columnName) {
		vcto.setTableName(SearchTablesColumns.MESSAGE);
		vcto.setColumnName(SearchTablesColumns.MESSAGE);
		vcto.setWhereId1("MESSAGE_ID");
		vcto.setWhereValue1(msgTo.getMessageId());
		vcto.setContent(msgTo.getMessage());
	}

	/**
	 * 
	 * @param messageId
	 * @return
	 */
	public MessagesTO getMessage(String messageId) {
		VCDAOFactory factory = new VCDAOFactory();
		IMessagesDAO dao = factory.getMessagesDAO();
		MessagesTO msg = dao.getMessage(messageId);
		return msg;
	}
}