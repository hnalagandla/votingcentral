/*
 * Created on Aug 12, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.taf;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.env.EnvProps;
import com.votingcentral.forms.taf.TafForm;
import com.votingcentral.model.bo.mail.EmailBO;
import com.votingcentral.model.db.dao.ITafDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.TafTO;
import com.votingcentral.model.enums.TafTypeEnum;
import com.votingcentral.model.enums.VCEmailTypeEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TafBO {
	private static List mostEmailedPolls = null;

	private static long mepCacheRefreshIntervalMillis = new Long(EnvProps
			.getProperty("most.emailed.polls.cache.refresh.interval.millis"))
			.longValue();

	private static int mepCount = new Integer(EnvProps
			.getProperty("most.emailed.polls.count.on.home.page")).intValue();

	private static Log log = LogFactory.getLog(TafBO.class);

	private static Timer mostEmailedPollsCacheRefresher = null;

	private static TafBO tafBO = null;

	private TafBO() {

	}

	public static TafBO getInstance() {
		if (tafBO == null) {
			tafBO = new TafBO();
		}
		return tafBO;
	}

	/**
	 * 
	 * @param tForm
	 * @throws Exception
	 */
	public void saveTaf(TafForm tForm, String fromAddress, String ipAddress)
			throws Exception {
		DAOFactory dao = DAOFactory.getDAOFactory();
		ITafDAO iTafDao = dao.getTafDAO();
		List tafToObjects = new ArrayList();
		fillTafTOObjects(tafToObjects, tForm, fromAddress, ipAddress);
		iTafDao.saveTaf(tafToObjects);
	}

	public int deleteTafByPollId(String pollId) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		ITafDAO iTafDao = dao.getTafDAO();
		return iTafDao.deleteTafEntriesByPollId(pollId);
	}

	private void fillTafTOObjects(List tafToObjects, TafForm tForm,
			String fromAddress, String ipAddress) {
		String[] toEmails = tForm.getToEmail();
		for (int i = 0; i < toEmails.length; i++) {
			String toEmailAddress = toEmails[i];
			if (toEmailAddress.length() > 0) {
				TafTO tto = new TafTO();
				tto.setSenderIpAddress(ipAddress);
				tto.setSenderEmailAddress(fromAddress);
				tto.setReceiverEmailAddress(toEmailAddress);
				tto.setTafUrl(tForm.getTafUrl());
				tto.setType(TafTypeEnum.get(Integer.parseInt(tForm.getType())));
				tafToObjects.add(tto);
			}
		}
	}

	/**
	 * 
	 * @param fromAddress
	 * @param toEmail
	 * @param vcEmail
	 * @param subject
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public void sendEmail(String fromAddress, List toEmail,
			VCEmailTypeEnum vcEmail, String subject, Object[] params)
			throws SQLException {
		EmailBO.getInstance().createMailRequest(fromAddress, toEmail, subject,
				vcEmail, params);
	}

	public int getTafRequestsBySenderRecieverAndTime(String senderEmail,
			String receiverEmail, long inThePastNHours) {
		DAOFactory dao = DAOFactory.getDAOFactory();
		ITafDAO iTafDao = dao.getTafDAO();
		int count = 0;
		try {
			count = iTafDao.getTafRequestsBySenderRecieverAndTime(senderEmail,
					receiverEmail, inThePastNHours);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	private void fillMostEmailedPolls(List mePolls) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		ITafDAO iTafDao = dao.getTafDAO();
		List local = null;
		local = iTafDao.getMostEmailedPollIds(mepCount);
		mePolls.clear();
		for (Iterator itr = local.iterator(); itr.hasNext();) {
			TafTO tto = (TafTO) itr.next();
			mePolls.add(tto.getTafUrl());
		}
		log.info("Refreshed most emailed polls");
	}

	public List getMostEmailedPolls() {
		if (mostEmailedPolls == null) {
			mostEmailedPolls = new ArrayList();
			mostEmailedPollsCacheRefresher = new Timer();
			TimerTask mePollsCacheRefreshTask = new TimerTask() {
				public void run() {
					try {
						fillMostEmailedPolls(mostEmailedPolls);
					} catch (SQLException e) {
						log.fatal("Problem retrieving most emailed polls", e);
						throw new RuntimeException(e);
					}
				}
			};
			mePollsCacheRefreshTask.run();
			mostEmailedPollsCacheRefresher.schedule(mePollsCacheRefreshTask,
					mepCacheRefreshIntervalMillis,
					mepCacheRefreshIntervalMillis);
		}
		return mostEmailedPolls;
	}
}