/*
 * Created on Jan 15, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.contests;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.env.EnvProps;
import com.votingcentral.model.bo.category.CategoryBO;
import com.votingcentral.model.bo.vaco.VCVacoPointsBO;
import com.votingcentral.model.db.dao.IContestEntriesDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.CategoryTO;
import com.votingcentral.model.db.dao.to.ContestEntryTO;
import com.votingcentral.model.db.dao.to.TextLinkDescTO;
import com.votingcentral.model.enums.VCActivityTypeEnum;
import com.votingcentral.model.enums.VCCategoryTypeEnum;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ContestsBO {
	public static final String CONTEST_CREATOR_USER = "Contest";

	private static int contestFilesCacheSize = Integer.parseInt(EnvProps
			.getProperty("contest.files.cache.size"));

	private static Map contestFileCache = Collections
			.synchronizedMap(new LRUMap(contestFilesCacheSize, 0.75F));

	private static Log log = LogFactory.getLog(ContestsBO.class);

	private static ContestsBO cbo = null;

	private ContestsBO() {

	}

	public static ContestsBO getInstance() {
		if (cbo == null) {
			cbo = new ContestsBO();
		}
		return cbo;
	}

	public List getAllContestsUploadUrls(String domContext) {
		List otherUrls = new ArrayList();
		List catTos = null;
		try {
			catTos = CategoryBO.getInstance().getListOfSuperCategories(
					VCCategoryTypeEnum.CONTEST);
		} catch (SQLException e) {
			// return a blank list on exception
			return otherUrls;
		}
		for (Iterator itr = catTos.iterator(); itr.hasNext();) {
			CategoryTO cto = (CategoryTO) itr.next();

			String url = VCURLHelper.getContestsUploadByTypeUrl(domContext, cto
					.getSuperCategory());
			TextLinkDescTO tldto = new TextLinkDescTO();
			tldto.setHref(url);
			tldto.setDesc(cto.getSuperCategory());
			tldto.setText(cto.getSuperCategory());
			otherUrls.add(tldto);
		}
		return otherUrls;
	}

	public ContestEntryTO getContestEntryByFileId(String fileId)
			throws SQLException, IOException {
		Object o = contestFileCache.get(fileId);
		if (o != null) {
			return (ContestEntryTO) o;
		}
		return loadContestEntryByFileId(fileId);
	}

	private ContestEntryTO loadContestEntryByFileId(String fileId)
			throws SQLException, IOException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IContestEntriesDAO cdao = dao.getContestFilesDAO();
		ContestEntryTO cto = cdao.getContestEntryByFileId(fileId);
		contestFileCache.put(fileId, cto);
		return cto;
	}

	public void addContestEntry(ContestEntryTO cto) throws SQLException,
			IOException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IContestEntriesDAO cdao = dao.getContestFilesDAO();
		cdao.addContestEntry(cto);
		loadContestEntryByFileId(cto.getFileId());
		//increment vaco points
		VCVacoPointsBO.getInstance().incrementPoints(cto.getUserId(),
				VCActivityTypeEnum.VC_POINTS_UPLOAD_PICTURE);
	}

	public int deleteContestEntriesByPollId(String pollId) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IContestEntriesDAO cdao = dao.getContestFilesDAO();
		ContestEntryTO cto = new ContestEntryTO();
		cto.setPollId(pollId);
		return cdao.deleteContestEntriesByPollId(cto);
	}

	public List getContestEntriesByContestTypeFileStatus(
			String contestTypeEnum, String fileStatusEnum) throws SQLException,
			IOException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IContestEntriesDAO cdao = dao.getContestFilesDAO();
		List files = cdao.getContestEntriesByTypeAndStatus(contestTypeEnum,
				fileStatusEnum);
		return files;
	}

	public List getContestEntriesByContestTypeFileStatusUserId(
			String contestTypeEnum, String fileStatusEnum, long userId)
			throws SQLException, IOException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IContestEntriesDAO cdao = dao.getContestFilesDAO();
		List files = cdao.getContestEntriesByTypeStatusUserId(contestTypeEnum,
				fileStatusEnum, userId);
		return files;
	}

	public void updateContestEntryByFileId(ContestEntryTO cto)
			throws IOException, SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IContestEntriesDAO cdao = dao.getContestFilesDAO();
		cdao.updateContestEntryForFileId(cto);
		loadContestEntryByFileId(cto.getFileId());
	}

	public void updateContestStatusByFileId(String fileId,
			String contestFileStatusEnum) throws IOException, SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IContestEntriesDAO cdao = dao.getContestFilesDAO();
		cdao.updateContestEntryStatusByFileId(fileId, contestFileStatusEnum);
		loadContestEntryByFileId(fileId);
	}

	public List getOldestByContestTypeFileStatus(String contestTypeEnum,
			String fileStatusEnum, int numFiles) throws SQLException,
			IOException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IContestEntriesDAO cdao = dao.getContestFilesDAO();
		return cdao.getOldestContestEntriesByTypeAndFileStatus(contestTypeEnum,
				fileStatusEnum, numFiles);
	}

	public List getFilesByFileStatus(String fileStatusEnum)
			throws SQLException, IOException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IContestEntriesDAO cdao = dao.getContestFilesDAO();
		return cdao.getContestEntriesByFileStatus(fileStatusEnum);
	}

	public List getContestEntriesNotInUserByType(long userId,
			String contestType, int maxEntries) throws IOException,
			SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IContestEntriesDAO cdao = dao.getContestFilesDAO();
		return cdao.getContestEntriesNotInUserByType(userId, contestType,
				maxEntries);
	}

	public List getContestEntriesByUserLimitByTime(long userId,
			long contestDisplayLimitTime) throws IOException, SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IContestEntriesDAO cdao = dao.getContestFilesDAO();
		return cdao.getContestEntriesByUserLimitByTime(userId, PollTimeHelper
				.getInstance().getMonthsOldTimestamp(contestDisplayLimitTime));
	}

	public List getContestEntriesByUserLimitByCountTime(long userId,
			long contestDisplayLimitTime, int contestsDisplayLimit)
			throws IOException, SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IContestEntriesDAO cdao = dao.getContestFilesDAO();
		return cdao.getContestEntriesByUserLimitByCountTime(userId,
				PollTimeHelper.getInstance().getMonthsOldTimestamp(
						contestDisplayLimitTime), contestsDisplayLimit);
	}
}