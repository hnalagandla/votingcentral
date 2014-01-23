/*
 * Created on Jan 27, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.votingcentral.model.db.dao.to.MailTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IMailDAO {
	public void createMailRequest(MailTO mto, long delayMins)
			throws SQLException;

	public List getUnsentMails(Date fromTimestamp, int maxRetryCount)
			throws SQLException;

	public void update(MailTO mto) throws SQLException;

	public void updateWhenSendFailed(MailTO mto) throws SQLException;

	public void updateWhenSendSucceed(MailTO mto) throws SQLException;

	public void delete(MailTO mto) throws SQLException;
}