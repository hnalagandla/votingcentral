/*
 * Created on Aug 1, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.vaco;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.env.EnvProps;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.vcwinners.VCWinnersBO;
import com.votingcentral.model.db.dao.IVCVacoPointsDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.db.dao.to.VCVacoPointsTO;
import com.votingcentral.model.enums.VCActivityTypeEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCVacoPointsBO {
	private static VCVacoPointsBO vacoBo = null;

	private static String TIME_PERIOD = "MONTHLY";

	private static Log log = LogFactory.getLog(VCVacoPointsBO.class);

	public static VCVacoPointsBO getInstance() {
		if (vacoBo == null) {
			vacoBo = new VCVacoPointsBO();
		}
		return vacoBo;
	}

	private VCVacoPointsBO() {

	}

	public void incrementPoints(long userId, VCActivityTypeEnum actvity)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCVacoPointsDAO vdao = dao.getVCVacoPointsDAO();
		VCVacoPointsTO vto = getCurrentPointsByUserId(userId);
		int points = getPointsByActivity(actvity);

		if (vto == null) {
			vto = new VCVacoPointsTO();
			vto.setUserId(userId);
			vto.setPoints(points);
			vdao.createPointsByUserId(vto);
			log
					.error("Points didn't exist during this period, creating a new row");
		} else {
			vto.setPoints(vto.getPoints() + points);
			vdao.updatePointsByUserId(vto);
			log.debug("Points exist during this period, updating row");
		}
		log.debug("inceremented points are : " + vto.getPoints()
				+ " for points id:" + vto.getPointsId());
	}

	public VCVacoPointsTO getCurrentPointsByUserId(long userId)
			throws SQLException {
		VCVacoPointsTO vto = null;
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCVacoPointsDAO vdao = dao.getVCVacoPointsDAO();
		Date from = VCWinnersBO.getInstance().getPeriodStartDate();
		Date to = VCWinnersBO.getInstance().getPeriodEndDate(from);
		List results = vdao.getPointsByUserId(userId, from, to);
		log
				.debug("Search for Curr points UserId: "
						+ userId
						+ "From date : "
						+ from
						+ " to date:"
						+ to
						+ " results size is :"
						+ ((results == null) ? "null" : String.valueOf(results
								.size())));
		//always use the first one since there should only be one record
		// returned.
		if (results != null) {
			if (results.size() == 1) {
				vto = (VCVacoPointsTO) results.get(0);
			} else {
				log.error("Do we have to sum up the total?? ");

			}
		}
		if (vto != null) {
			log.debug("Curr points UserId : " + userId + " points : "
					+ vto.getPoints() + " points id is: " + vto.getPointsId());
		}
		return vto;
	}

	public List getAllPointsEntriesByUserId(long userId) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCVacoPointsDAO vdao = dao.getVCVacoPointsDAO();

		List vto = vdao.getPointsByUserId(userId);
		return vto;
	}

	public long getAllPointsByUserId(long userId) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCVacoPointsDAO vdao = dao.getVCVacoPointsDAO();
		long total = 0;
		List results = vdao.getPointsByUserId(userId);
		log
				.debug("All points UserId:"
						+ userId
						+ " results size is :"
						+ ((results == null) ? "null" : String.valueOf(results
								.size())));

		for (Iterator iter = results.iterator(); iter.hasNext();) {
			VCVacoPointsTO element = (VCVacoPointsTO) iter.next();
			total = total + element.getPoints();
		}
		return total;
	}

	public VCVacoPointsTO getUserWithHighestPoints(Date from, Date to)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCVacoPointsDAO vdao = dao.getVCVacoPointsDAO();
		VCVacoPointsTO max = null;
		long maxPoints = 0;
		List results = vdao.getUsersAndPointsDuringPeriod(from, to);
		if (results != null && results.size() > 0) {
			for (Iterator i = results.iterator(); i.hasNext();) {
				VCVacoPointsTO vvto = (VCVacoPointsTO) i.next();
				if (vvto.getPoints() > maxPoints) {
					maxPoints = vvto.getPoints();
					max = vvto;
				}
			}
		}
		return max;
	}

	public List getUsersWithHighestPoints(int limit) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCVacoPointsDAO vdao = dao.getVCVacoPointsDAO();
		Date from = VCWinnersBO.getInstance().getPeriodStartDate();
		Date to = VCWinnersBO.getInstance().getPeriodEndDate(from);
		List results = vdao.getUsersAndPointsDuringPeriod(from, to);
		List users = new ArrayList();
		if (results != null && results.size() > 0) {
			Collections.sort(results, new VCVacoPointsComparator());
			List subList = null;
			if (results.size() > limit) {
				subList = results.subList(0, limit);
			} else {
				subList = results;
			}
			for (Iterator iterator = subList.iterator(); iterator.hasNext();) {
				VCVacoPointsTO vto = (VCVacoPointsTO) iterator.next();
				VCUserTO uto = UserBO.getInstance().getUserByUserId(
						vto.getUserId());
				uto.setVcVacoPointsTo(vto);
				users.add(uto);
			}
		}
		return users;
	}

	public int getPointsByActivity(VCActivityTypeEnum activity) {
		return Integer.parseInt(EnvProps.getProperty(activity.getName()));
	}

	private static class VCVacoPointsComparator implements Comparator {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Object o1, Object o2) {
			VCVacoPointsTO vto1 = (VCVacoPointsTO) o1;
			VCVacoPointsTO vto2 = (VCVacoPointsTO) o2;
			if (vto1.getPoints() == vto2.getPoints()) {
				return 0;
			} else if (vto1.getPoints() > vto2.getPoints()) {
				return -1;
			}
			return 1;
		}
	}
}