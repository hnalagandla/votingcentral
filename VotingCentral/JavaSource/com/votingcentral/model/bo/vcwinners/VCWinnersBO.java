/*
 * Created on Aug 2, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.vcwinners;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.env.EnvProps;
import com.votingcentral.model.bo.mail.EmailBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.vaco.VCVacoPointsBO;
import com.votingcentral.model.db.dao.IVCWinnersDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.db.dao.to.VCVacoPointsTO;
import com.votingcentral.model.db.dao.to.VCWinnersTO;
import com.votingcentral.model.enums.VCEmailTypeEnum;
import com.votingcentral.model.enums.VCWinTypeCodeEnum;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCWinnersBO {
	private static VCWinnersBO winnersBo = null;

	private final static int MAX_PREV_WINNERS_COUNT = 25;

	private static Log log = LogFactory.getLog(VCWinnersBO.class);

	private static int PRIZES_WIN_PERIOD_DAYS = Integer.parseInt(EnvProps
			.getProperty("prizes.win.period.days"));

	private static boolean shouldCheckMinimumPoints = EnvProps.getProperty(
			"prizes.win.should.check.minimum.points").equalsIgnoreCase("true");

	private static int MINIMUM_POINTS_FOR_WINNING = Integer.parseInt(EnvProps
			.getProperty("prizes.win.minimum.points.for.winning"));

	public static VCWinnersBO getInstance() {
		if (winnersBo == null) {
			winnersBo = new VCWinnersBO();
		}
		return winnersBo;
	}

	private VCWinnersBO() {

	}

	public int getActivityPeriodDays() throws SQLException {
		return PRIZES_WIN_PERIOD_DAYS;
	}

	public void addWinner(VCWinnersTO wto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCWinnersDAO vdao = dao.getVCWinnersDAO();
		vdao.addWinner(wto);
	}

	private VCWinnersTO getWinnerRecordByTimeRange(
			VCWinTypeCodeEnum winTypeCode, Date start, Date end)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCWinnersDAO vdao = dao.getVCWinnersDAO();
		return vdao.getWinnerRecordByTimeRange(winTypeCode, start, end);
	}

	public VCUserTO getLatestWinner(VCWinTypeCodeEnum winTypeCode)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCWinnersDAO vdao = dao.getVCWinnersDAO();
		VCWinnersTO vto = vdao.getLastWinnerByWinType(winTypeCode);
		if (vto != null) {
			return UserBO.getInstance().getUserByUserId(vto.getUserId());
		} else {
			return null;
		}
	}

	private VCWinnersTO getLatestWinnerRecord(VCWinTypeCodeEnum winTypeCode)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCWinnersDAO vdao = dao.getVCWinnersDAO();
		return vdao.getLastWinnerByWinType(winTypeCode);
	}

	public List getPrevWinners() throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCWinnersDAO vdao = dao.getVCWinnersDAO();
		List users = new ArrayList();
		List winners = vdao.getAllWinners(MAX_PREV_WINNERS_COUNT);
		for (Iterator iter = winners.iterator(); iter.hasNext();) {
			VCWinnersTO element = (VCWinnersTO) iter.next();
			VCUserTO vto = UserBO.getInstance().getUserByUserId(
					element.getUserId());
			users.add(vto);
		}
		return users;
	}

	private void sendEmailToWinner(VCUserTO vto) throws SQLException {
		String domContext = VCURLHelper.getDomainContext();
		List toAddress = new ArrayList();
		toAddress.add(vto.getEmailAddress());
		Object[] params = { vto.getUserName(), "New iPod shuffle 4GB",
				VCURLHelper.getMyProfileUrl(domContext),
				VCURLHelper.getMyRegInfoUrl(domContext),
				VCURLHelper.getVacoWinnersUrl(domContext) };
		EmailBO.getInstance().createMailRequest(toAddress,
				VCEmailTypeEnum.VACO_T_SHIRT_WINNER, params, 0);

	}

	public String getPeriodStartDateAsString() throws SQLException {
		return PollTimeHelper.getInstance().getTimeString(
				getPeriodStartDate().getTime());
	}

	public String getPeriodEndDateAsString() throws SQLException {
		return PollTimeHelper.getInstance().getTimeString(
				getPeriodEndDate(getPeriodStartDate()).getTime());
	}

	public Date getPeriodStartDate() throws SQLException {
		Date start = null;
		VCWinnersTO wto = getLatestWinnerRecord(VCWinTypeCodeEnum.ACTIVITY);
		log.debug("Wto is: " + wto);
		//if there is no winner at all, then this is first record.
		if (wto == null) {
			GregorianCalendar startGC = new GregorianCalendar(
					PollTimeHelper.POLL_TIMES_TIME_ZONE);
			if (EnvProps.isProd()) {
				startGC.set(Calendar.MONTH, Calendar.DECEMBER);
				startGC.set(Calendar.DAY_OF_MONTH, 12);
			} else {
				startGC.set(Calendar.MONTH, Calendar.NOVEMBER);
				startGC.set(Calendar.DAY_OF_MONTH, 26);
			}
			startGC.set(Calendar.YEAR, 2007);
			startGC.set(Calendar.HOUR_OF_DAY, 1);
			startGC.set(Calendar.MINUTE, 0);
			startGC.set(Calendar.SECOND, 0);

			start = startGC.getTime();
		} else {
			start = wto.getEndTimeStamp();
		}
		log.debug("Returning start date: " + start);
		return start;
	}

	public Date getPeriodEndDate(Date startDate) {
		return PollTimeHelper.getInstance().getFutureDateFromDate(startDate,
				PRIZES_WIN_PERIOD_DAYS);
	}

	public synchronized void checkAndCreatePreviousPeriodWinner()
			throws SQLException {
		Date start = getPeriodStartDate();
		Date end = getPeriodEndDate(start);

		if (PollTimeHelper.getInstance().getCurrentDate().after(end)
				&& getWinnerRecordByTimeRange(VCWinTypeCodeEnum.ACTIVITY,
						start, end) == null) {
			VCVacoPointsTO pto = VCVacoPointsBO.getInstance()
					.getUserWithHighestPoints(start, end);
			if (pto != null) {
				if (shouldCheckMinimumPoints
						&& pto.getPoints() < MINIMUM_POINTS_FOR_WINNING) {
					//check minimum is enabled and we don't have the minimum
					// for deriving a winner.
					return;
				}
				VCWinnersTO wto = new VCWinnersTO();
				wto.setUserId(pto.getUserId());
				wto.setStartTimeStamp(start);
				wto.setEndTimeStamp(end);
				wto.setWinTypeCode(VCWinTypeCodeEnum.ACTIVITY);
				VCWinnersBO.getInstance().addWinner(wto);
				//send the email
				VCUserTO vto = UserBO.getInstance().getUserByUserId(
						wto.getUserId());
				sendEmailToWinner(vto);
			}
		}
	}
}