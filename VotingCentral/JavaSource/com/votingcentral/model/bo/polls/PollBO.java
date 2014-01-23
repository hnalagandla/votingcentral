/*
 * Created on Aug 10, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.polls;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import com.votingcentral.env.EnvProps;
import com.votingcentral.model.bo.category.CategoryBO;
import com.votingcentral.model.bo.content.VCContentBO;
import com.votingcentral.model.bo.contests.ContestsBO;
import com.votingcentral.model.bo.pollcomments.PollCommentsBO;
import com.votingcentral.model.bo.taf.TafBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.vaco.VCVacoPointsBO;
import com.votingcentral.model.bo.vcfile.VCFileBO;
import com.votingcentral.model.bo.votes.Votes;
import com.votingcentral.model.db.dao.ICategoryDAO;
import com.votingcentral.model.db.dao.IPollUserHistoryDAO;
import com.votingcentral.model.db.dao.IPollsDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.CategoryTO;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.db.dao.to.PollUserHistoryTO;
import com.votingcentral.model.db.dao.to.VCContentTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.PollTypeEnum;
import com.votingcentral.model.enums.PollsStatusEnum;
import com.votingcentral.model.enums.VCActivityTypeEnum;
import com.votingcentral.model.enums.VCCategoryTypeEnum;
import com.votingcentral.model.polls.AnswerChoice;
import com.votingcentral.model.polls.DeletePollResponse;
import com.votingcentral.model.polls.PollData;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.model.polls.QuestionData;
import com.votingcentral.model.polls.Questionnaire;
import com.votingcentral.model.search.SearchTablesColumns;
import com.votingcentral.util.UnSyncStringBuffer;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollBO {

	private static List featuredPolls = Collections
			.synchronizedList(new ArrayList());

	private static long fpCacheRefreshIntervalMillis = new Long(EnvProps
			.getProperty("featured.polls.cache.refresh.interval.millis"))
			.longValue();

	private static int fpCount = new Integer(EnvProps
			.getProperty("featured.polls.count.on.home.page")).intValue();

	private static Timer featuredPollsCacheRefresher = null;

	private static Map pollIdsByCat = null;

	private static Timer pollIdsByCatCacheRefresher = null;

	private static List recEndedPolls = null;

	private static int reCount = new Integer(EnvProps
			.getProperty("rec.ended.polls.count.on.home.page")).intValue();

	private static long recEndedCacheRefreshIntervalMillis = new Long(EnvProps
			.getProperty("recently.ended.polls.cache.refresh.interval.millis"))
			.longValue();

	private static Timer recEndedPollsCacheRefresher = null;

	private static Timer recStartedPollsCacheRefresher = null;

	private static List recStartedPolls = null;

	private static int rsCount = new Integer(EnvProps
			.getProperty("rec.started.polls.count.on.home.page")).intValue();

	private static long recStartedCacheRefreshIntervalMillis = new Long(
			EnvProps
					.getProperty("recently.started.polls.cache.refresh.interval.millis"))
			.longValue();

	private static List mostVotedPolls = null;

	private static long mostVotedCacheRefreshIntervalMillis = new Long(EnvProps
			.getProperty("most.voted.polls.cache.refresh.interval.millis"))
			.longValue();

	private static int mvCount = new Integer(EnvProps
			.getProperty("most.voted.polls.count.on.home.page")).intValue();

	private static Timer mostVotedPollsCacheRefresher = null;

	private static int pollByPollIdCacheSize = new Integer(EnvProps
			.getProperty("poll.by.pollid.cache.size")).intValue();

	private static Map pollByPollIdCache = Collections
			.synchronizedMap(new LRUMap(pollByPollIdCacheSize, 0.75F));

	private static String pollEndInWeeks = EnvProps
			.getProperty("poll.end.date.options.weeks");

	private static int pollsCreatedDisplayLimitTime = Integer.parseInt(EnvProps
			.getProperty("myvc.polls.created.display.limit.time.days"));

	private static int pollsCreatedDisplayLimit = Integer.parseInt(EnvProps
			.getProperty("myvc.polls.created.display.limit.count"));

	private static int pollsVotedDisplayLimitTime = Integer.parseInt(EnvProps
			.getProperty("myvc.polls.voted.display.limit.time.days"));

	private static int pollsVotedDisplayLimit = Integer.parseInt(EnvProps
			.getProperty("myvc.polls.voted.display.limit.count"));

	private static int unfinishedPollsMaxReminderCount = Integer
			.parseInt(EnvProps
					.getProperty("poll.unfinished.polls.max.reminder.count"));

	private static List pollEndInWeeksForView = null;

	private static PollBO poll = null;

	private PollBO() {

	}

	private static Log log = LogFactory.getLog(PollBO.class);

	public static final String Q_AND_A_SEPARATOR = "~~~~~";

	public static final String A_AND_A_SEPARATOR = "@@@@@";

	public static final String A_AND_Q_SEPARATOR = "^^^^^";

	public static PollBO getInstance() {
		if (poll == null) {
			poll = new PollBO();
		}
		return poll;
	}

	public void deleteImagePollQuestion(String pollId, String qId,
			String imageId) {
		if (pollId == null || pollId.length() == 0 || qId == null
				|| qId.length() == 0 || imageId == null
				|| imageId.length() == 0) {
			return;
		}
		PollTO pto = null;
		try {
			pto = getPollByPollId(pollId);
			if (pto == null) {
				return;
			}
		} catch (SQLException e) {
			return;
		}
		try {
			PollData pollData = pto.getPollData();
			QuestionData qData = pollData.getQuestionDataByQuestionId(qId);
			qData.setQuestionImageId(null);
			VCFileBO.getInstance().deleteVCFile(imageId);
			updatePollByPollId(pto);
		} catch (SQLException e) {
			return;
		}
	}

	public void deleteImagePollAnswer(String pollId, String qId, String aId,
			String imageId) {
		if (pollId == null || pollId.length() == 0 || qId == null
				|| qId.length() == 0 || aId == null || aId.length() == 0
				|| imageId == null || imageId.length() == 0) {
			return;
		}
		PollTO pto = null;
		try {
			pto = getPollByPollId(pollId);
			if (pto == null) {
				return;
			}
		} catch (SQLException e) {
			return;
		}
		try {
			PollData pollData = pto.getPollData();
			AnswerChoice aChoice = pollData
					.getAnswerChoiceByQuestionIdAnswerId(qId, aId);
			aChoice.setAnswerImageId(null);
			VCFileBO.getInstance().deleteVCFile(imageId);
			updatePollByPollId(pto);
		} catch (SQLException e) {
			return;
		}
	}

	public synchronized void castAVote(String loginName, String pollId,
			String questionId, String[] answerId, String answerText, String ip)
			throws Exception {
		// get the polldata
		PollTO pto = getPollByPollId(pollId);

		// write the poll user history record to DB
		PollUserHistoryTO puhto = new PollUserHistoryTO();
		VCUserTO vto = UserBO.getInstance().getUserByUserName(loginName);

		// increment the count of the poll.
		long count = pto.getPollTotalVotes();
		count = count + answerId.length;
		pto.setPollTotalVotes(count);

		for (int i = 0; i < answerId.length; i++) {
			fillPollUserHistoryTO(puhto, vto, pto.getPollId(), questionId,
					answerId[i], answerText, ip);
			DAOFactory dao = DAOFactory.getDAOFactory();
			IPollUserHistoryDAO pdao = dao.getPollUserHistoryDAO();
			pdao.createAHistoryRecord(puhto);

			// increment choice counts
			incrementPollCount(pto, questionId, answerId[i]);
		}

		// write the updated poll record to DB.
		updatePollByPollId(pto);

		//increment vaco points
		VCVacoPointsBO.getInstance().incrementPoints(vto.getUserId(),
				VCActivityTypeEnum.VC_POINTS_VOTING);
	}

	private void fillPollUserHistoryTO(PollUserHistoryTO puhto, VCUserTO vto,
			String pollId, String questionId, String answerId,
			String answerText, String ip) {
		puhto.setUserId(vto.getUserId());
		puhto.setPollId(pollId);
		puhto.setQuestionId(questionId);
		puhto.setAnswerId(answerId);
		puhto.setAnswerText(answerText);
		puhto.setUserIpAddress(ip);
		puhto.setUserLocationCity(vto.getCity());
		puhto.setUserLocationStateId(vto.getStateId());
		puhto.setUserLocationZip(vto.getZipCode1());
		puhto.setUserLocationCountryId(vto.getCountryId());
		puhto.setGender(vto.getGender());
		puhto.setYearOfBirth(Integer.parseInt(vto.getBirthYear()));
	}

	private void incrementPollCount(PollTO pto, String questionId,
			String answerId) throws Exception {

		PollData pollData = pto.getPollData();
		pollData.incrementCount(questionId, answerId);

	}

	private void fillFeaturedPolls(List featuredPolls) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		List local = null;
		local = pdao.getFeaturedPolls(fpCount);
		featuredPolls.clear();
		for (Iterator itr = local.iterator(); itr.hasNext();) {
			PollTO pto = (PollTO) itr.next();
			featuredPolls.add(pto.getPollId());
		}
		log.info("Refreshed featured polls");
	}

	private void fillRecentlyEndedPolls(List recEndedPolls) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		List local = pdao.getRecentlyEndedPolls(reCount);
		recEndedPolls.clear();
		for (Iterator itr = local.iterator(); itr.hasNext();) {
			PollTO pto = (PollTO) itr.next();
			recEndedPolls.add(pto.getPollId());
		}
		log.info("Refreshed recently ended polls");
	}

	private void fillRecentlyStartedPolls(List recStartedPolls)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		List local = pdao.getRecentlyStartedPolls(rsCount);
		recStartedPolls.clear();
		for (Iterator itr = local.iterator(); itr.hasNext();) {
			PollTO pto = (PollTO) itr.next();
			recStartedPolls.add(pto.getPollId());
		}
		log.info("Refreshed recently started polls");
	}

	private void fillMostPopularPolls(List mostPopPolls) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		List local = pdao.getMostVotedPolls(mvCount);
		mostPopPolls.clear();
		for (Iterator itr = local.iterator(); itr.hasNext();) {
			PollTO pto = (PollTO) itr.next();
			mostPopPolls.add(pto.getPollId());
		}
		log.info("Refreshed most popular polls");
	}

	public List getFeaturedPolls() {
		if (featuredPolls == null || featuredPolls.size() == 0) {
			featuredPollsCacheRefresher = new Timer();
			TimerTask featuredPollsCacheRefreshTask = new TimerTask() {
				public void run() {
					try {
						fillFeaturedPolls(featuredPolls);
					} catch (SQLException e) {
						log.fatal("Problem retrieving featured polls", e);
						throw new RuntimeException(e);
					}
				}
			};
			featuredPollsCacheRefreshTask.run();
			featuredPollsCacheRefresher.schedule(featuredPollsCacheRefreshTask,
					fpCacheRefreshIntervalMillis, fpCacheRefreshIntervalMillis);
		}
		return featuredPolls;
	}

	private PollTO loadPollByPollId(String pollId) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		PollTO pto = pdao.getPollByPollId(pollId);
		if (pto != null) {
			pollByPollIdCache.put(pollId, pto);
			if (pto.getPollPriority() == 10) {
				if (featuredPolls == null) {
					featuredPolls = new ArrayList();
				}
				// add the newly added featured poll to the top of the list
				//if it is not already present.
				if (!featuredPolls.contains(pto.getPollId())) {
					featuredPolls.add(0, pto.getPollId());
				}
			}
		}
		return pto;
	}

	public List getRecentlyEndedPolls() {
		if (recEndedPolls == null) {
			if (recEndedPolls == null) {
				recEndedPolls = new ArrayList();
			}
			recEndedPollsCacheRefresher = new Timer();
			TimerTask recEndedPollsCacheRefreshTask = new TimerTask() {
				public void run() {
					try {
						fillRecentlyEndedPolls(recEndedPolls);
					} catch (SQLException e) {
						log.fatal("Problem retrieving Rec ended polls.", e);
						throw new RuntimeException(e);
					}
				}
			};
			recEndedPollsCacheRefreshTask.run();
			recEndedPollsCacheRefresher.scheduleAtFixedRate(
					recEndedPollsCacheRefreshTask,
					recEndedCacheRefreshIntervalMillis,
					recEndedCacheRefreshIntervalMillis);
		}
		return recEndedPolls;
	}

	public List getRecentlyStartedPolls() {
		if (recStartedPolls == null) {
			if (recStartedPolls == null) {
				recStartedPolls = new ArrayList();
			}
			recStartedPollsCacheRefresher = new Timer();
			TimerTask recStartedPollsCacheRefreshTask = new TimerTask() {
				public void run() {
					try {
						fillRecentlyStartedPolls(recStartedPolls);
					} catch (SQLException e) {
						log.fatal("Problem retrieving Rec Started polls.", e);
						throw new RuntimeException(e);
					}
				}
			};
			recStartedPollsCacheRefreshTask.run();
			recStartedPollsCacheRefresher.scheduleAtFixedRate(
					recStartedPollsCacheRefreshTask,
					recStartedCacheRefreshIntervalMillis,
					recStartedCacheRefreshIntervalMillis);
		}
		return recStartedPolls;
	}

	public List getMostVotedPolls() {
		if (mostVotedPolls == null) {
			mostVotedPolls = new ArrayList();
			mostVotedPollsCacheRefresher = new Timer();
			TimerTask mostVotedPollsCacheRefreshTask = new TimerTask() {
				public void run() {
					try {
						fillMostPopularPolls(mostVotedPolls);
					} catch (SQLException e) {
						log.fatal("Problem retrieving most popular polls.", e);
						throw new RuntimeException(e);
					}
				}
			};
			mostVotedPollsCacheRefreshTask.run();
			mostVotedPollsCacheRefresher.scheduleAtFixedRate(
					mostVotedPollsCacheRefreshTask,
					mostVotedCacheRefreshIntervalMillis,
					mostVotedCacheRefreshIntervalMillis);
		}
		return mostVotedPolls;
	}

	public List getPollIdsByCategory(String category) {
		if (pollIdsByCat == null) {
			pollIdsByCat = new HashMap();
			pollIdsByCatCacheRefresher = new Timer();
			TimerTask pollIdsByCatCacheRefreshTask = new TimerTask() {
				public void run() {
					try {
						fillPollsByCategory(pollIdsByCat);
					} catch (SQLException e) {
						log.fatal("Problem filling poll ids by category.", e);
						throw new RuntimeException(e);
					}
				}
			};
			pollIdsByCatCacheRefreshTask.run();
			pollIdsByCatCacheRefresher.scheduleAtFixedRate(
					pollIdsByCatCacheRefreshTask,
					PollTimeHelper.MILLI_SECS_PER_HOUR,
					PollTimeHelper.MILLI_SECS_PER_HOUR);
		}
		return (List) pollIdsByCat.get(category);
	}

	public PollTO getPollByPollId(String pollId) throws SQLException {
		Object o = pollByPollIdCache.get(pollId);
		if (o != null) {
			return (PollTO) o;
		}
		return loadPollByPollId(pollId);
	}

	public String getRemotePollHtml(String pollId) throws SQLException {
		PollTO pto = getPollByPollId(pollId);
		Map remContentMap = RemotePollContentHelper.getInstance()
				.getRemotePollContentMap();
		MessageFormat pollBody = (MessageFormat) remContentMap
				.get(RemotePollContentHelper.REMOTE_POLL_COMPONENT_BODY);
		MessageFormat pollAnswer = (MessageFormat) remContentMap
				.get(RemotePollContentHelper.REMOTE_POLL_COMPONENT_ANSWER);
		Questionnaire questionnaire = pto.getPollData().getQuestionnaire();
		return "";
	}

	public void setPollStatus(String pollId, String status) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		pdao.setPollStatus(pollId, status);
		loadPollByPollId(pollId);
	}

	public void setPollStartedEmailStatus(String pollId, int status)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		pdao.setPollStartedEmailStatus(pollId, status);
		loadPollByPollId(pollId);
	}

	public void setUnfinishedPollReminderSentNow(String pollId)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		pdao.setUnfinishedPollReminderSentNow(pollId);
		loadPollByPollId(pollId);
	}

	public synchronized void setPollPriority(String pollId, int pollPriorityEnum)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		pdao.setPollPriority(pollId, pollPriorityEnum);
		loadPollByPollId(pollId);
		fillFeaturedPolls(featuredPolls);
	}

	public synchronized PollTO extendPoll(String pollId, int numWeeks)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		PollTO pto = getPollByPollId(pollId);
		long endMillis = pto.getEndTimestamp().getTime();
		long newEndmillis = endMillis + numWeeks
				* PollTimeHelper.MILLI_SECS_PER_WEEK;
		// expire time is sometime after the poll end time.
		long pollExpireTime = newEndmillis
				+ PollTimeHelper.PUBLIC_POLL_EXPIRE_NUM_DAYS_FROM_END
				* PollTimeHelper.MILLI_SECS_PER_DAY;
		Date pollExpire = new Date(pollExpireTime);
		pto.setExpireTimestamp(pollExpire);
		pto.setEndTimestamp(PollTimeHelper.getInstance().getDate(newEndmillis));
		pto.setPollStatus(PollsStatusEnum.CONFIRMED.getName());
		IPollsDAO pdao = dao.getPollsDAO();
		pdao.updatePollByPollId(pto);
		return loadPollByPollId(pollId);
	}

	public synchronized DeletePollResponse deletePoll(String pollId)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		PollTO pto = getPollByPollId(pollId);
		DeletePollResponse dRes = new DeletePollResponse();
		if (pto == null) {
			return dRes;
		}
		int totalImageFiles = 0;
		//delete files related to the poll vc_files
		if (pto.getPollData() != null) {
			List imageIds = pto.getPollData().getAllPollImagIds();
			for (Iterator itr = imageIds.iterator(); itr.hasNext();) {
				String imageId = (String) itr.next();
				int imageFilesDeleted = VCFileBO.getInstance().deleteVCFile(
						imageId);
				totalImageFiles = totalImageFiles + imageFilesDeleted;
			}
			dRes.setImageFilesDeleted(totalImageFiles);
		}
		//delete from poll comments
		int commentsDel = PollCommentsBO.getInstance().deleteCommentsForPoll(
				pollId);
		dRes.setCommentsDeleted(commentsDel);

		//delete from poll_user_history
		int votesDeleted = Votes.getInstance().deleteVotesForPoll(pollId);
		dRes.setVotesDeleted(votesDeleted);

		//delete from vc_content
		int searcRows = VCContentBO.getInstance().deleteContentByPollId(pollId);
		dRes.setSearchRowsDeleted(searcRows);

		//delete from taf_log
		int tafRecs = TafBO.getInstance().deleteTafByPollId(pollId);
		dRes.setTafRecsDeleted(tafRecs);

		//delete from contest entries
		int contestEntries = ContestsBO.getInstance()
				.deleteContestEntriesByPollId(pollId);
		dRes.setContestEntriesDeleted(contestEntries);

		//remove the poll from cache.
		pollByPollIdCache.remove(pollId);
		//delete from polls table.
		int poll = pdao.deletePollByPollId(pollId);
		dRes.setPollRecsDeleted(poll);

		return dRes;
	}

	public void createNewPoll(PollTO pto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();

		// Following 2 inserts should be part of a single trx
		// Create a Polls record
		IPollsDAO pdao = dao.getPollsDAO();
		pdao.createNewPoll(pto);
		loadPollByPollId(pto.getPollId());
	}

	public void updatePollByPollId(PollTO pto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		pdao.updatePollByPollId(pto);
		loadPollByPollId(pto.getPollId());
	}

	public List getAllExpiredPollsEmailStatusUnsent() throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		return pdao.getAllEndedPollsEmailStatusUnsent();
	}

	public List getAllStartedPollsEmailStatusUnsent() throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		return pdao.getAllStartedPollsEmailStatusUnsent();
	}

	public List getUnfinishedPolls() throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		return pdao
				.getUnfinishedPollsLessThanMaxRetry(unfinishedPollsMaxReminderCount);
	}

	public List getPollIdsByCategory(String categoryName,
			String lastPollEndTime, int count) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		List local = pdao.getPollsByCategory(categoryName, lastPollEndTime,
				count);
		List returnVal = new ArrayList();
		for (Iterator itr = local.iterator(); itr.hasNext();) {
			PollTO pto = (PollTO) itr.next();
			returnVal.add(pto.getPollId());
		}
		return returnVal;
	}

	private void fillPollsByCategory(Map pollIdsByCat) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		ICategoryDAO catDao = dao.getCategoryDAO();
		pollIdsByCat.clear();

		for (Iterator itr = catDao.getDistinctSuperCategoriesByType(
				VCCategoryTypeEnum.POLL).iterator(); itr.hasNext();) {
			CategoryTO cto = (CategoryTO) itr.next();
			String category = cto.getSuperCategory();
			IPollsDAO pollsDao = dao.getPollsDAO();
			List pollIds = new ArrayList();
			// get all polls, we will save only poll ids.
			List polls = pollsDao.getPollsByCategory(category, null, 10000);
			for (Iterator itr1 = polls.iterator(); itr1.hasNext();) {
				PollTO pto = (PollTO) itr1.next();
				if (!pollIds.contains(pto.getPollId())) {
					pollIds.add(pto.getPollId());
				}
			}
			pollIdsByCat.put(category, pollIds);
		}
		log.info("Refreshed Poll Ids by category.");
	}

	public synchronized void incrementPollViewsCount(String pollId, int count)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		PollTO pto = getPollByPollId(pollId);
		pdao.incrementViewedCountByPollId(pollId, count);
		pto.setViewsCount(pto.getViewsCount() + count);
	}

	/**
	 * 
	 * @param emailAddrId
	 * @return
	 * @throws Exception
	 */
	public String getNextPollIdFromShowResults(long emailAddrId)
			throws Exception {
		VCDAOFactory factory = new VCDAOFactory();
		IPollUserHistoryDAO dao = factory.getPollUserHistoryDAO();
		return dao.getNextPoll(emailAddrId);
	}

	/**
	 * 
	 * @param currPollId
	 * @return
	 * @throws Exception
	 */
	public String getNextPollIdFromShowPoll(String currPollId) throws Exception {
		PollTO pto = getPollByPollId(currPollId);
		List pollIds = null;
		int index = 0;
		if (pto.getPollType().equalsIgnoreCase("CONTEST")) {
			pollIds = getPollIdsForContests();
			index = pollIds.indexOf(currPollId);
			if (index == pollIds.size() - 1) {
				index = 0;
			} else {
				index++;
			}
		} else {
			pollIds = getPollIdsByCategory(pto.getPollCategory1());
			index = pollIds.indexOf(currPollId);
			if (index == pollIds.size() - 1) {
				index = 0;
				String nextCat = pto.getPollCategory1();
				do {
					nextCat = CategoryBO.getInstance().getNextSuperCategory(
							VCCategoryTypeEnum.POLL, nextCat);
					pollIds = getPollIdsByCategory(nextCat);
				} while (pollIds.size() == 0);
			} else {
				index++;
			}
		}
		return (String) pollIds.get(index);
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List getPollIdsForContests() throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		List pto = pdao.getPollsByType(PollTypeEnum.CONTEST);
		List result = new ArrayList();
		if (pto != null && pto.size() > 0) {
			for (Iterator itr = pto.iterator(); itr.hasNext();) {
				String id = ((PollTO) itr.next()).getPollId();
				result.add(id);
			}
		}
		return result;
	}

	public List getPollEndTimes() {
		if (pollEndInWeeksForView == null) {
			pollEndInWeeksForView = getPollEndTimesInternal();
		}
		return pollEndInWeeksForView;
	}

	private List getPollEndTimesInternal() {
		List local = new ArrayList();
		String[] weeks = StringUtils.split(pollEndInWeeks, ",");
		int len = weeks.length;
		for (int i = 0; i < len; i++) {
			local.add(weeks[i]);
		}
		return local;
	}

	public List getPollIdsCreatedByUser(long emailAddressId, boolean getAll)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		List results = null;
		List returnVal = null;
		if (getAll) {
			results = pdao.getPollsByUserLimitByTime(emailAddressId,
					PollTimeHelper.getInstance().getMonthsOldTimestamp(
							pollsCreatedDisplayLimitTime));
		} else {
			results = pdao.getPollsByUserLimitByCountTime(emailAddressId,
					PollTimeHelper.getInstance().getMonthsOldTimestamp(
							pollsCreatedDisplayLimitTime),
					pollsCreatedDisplayLimit);
		}
		if (results != null) {
			returnVal = new ArrayList();
			for (Iterator itr = results.iterator(); itr.hasNext();) {
				PollTO pto = (PollTO) itr.next();
				returnVal.add(pto.getPollId());
			}
		}
		return returnVal;
	}

	public List getPollIdsByUserAndStatus(long emailAddressId,
			PollsStatusEnum status) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		List returnVal = null;
		List results = pdao.getPollsByUserAndStatus(emailAddressId, status);
		if (results != null) {
			returnVal = new ArrayList();
			for (Iterator itr = results.iterator(); itr.hasNext();) {
				PollTO pto = (PollTO) itr.next();
				returnVal.add(pto.getPollId());
			}
		}
		return returnVal;
	}

	public List getAllActivePolls() throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollsDAO pdao = dao.getPollsDAO();
		List results = pdao.getAllActivePolls();
		return results;
	}

	public List getPollIdsVotedByUser(long emailAddressId, boolean getAll)
			throws SQLException {
		List polls = null;
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollUserHistoryDAO pdao = dao.getPollUserHistoryDAO();
		List results = null;
		if (getAll) {
			results = pdao.getPollsVotedByUserLimitByTime(emailAddressId,
					PollTimeHelper.getInstance().getMonthsOldTimestamp(
							pollsVotedDisplayLimitTime));
		} else {
			results = pdao
					.getPollsVotedByUserLimitByCountTime(emailAddressId,
							PollTimeHelper.getInstance().getMonthsOldTimestamp(
									pollsVotedDisplayLimitTime),
							pollsVotedDisplayLimit);
		}
		if (results.size() > 0) {
			polls = new ArrayList();
		}
		for (Iterator itr = results.iterator(); itr.hasNext();) {
			PollUserHistoryTO puhto = (PollUserHistoryTO) itr.next();
			PollTO pto = PollBO.getInstance()
					.getPollByPollId(puhto.getPollId());
			polls.add(pto.getPollId());
		}
		return polls;
	}

	public List fillPollEndWeeks(String selectStr) {
		List times = new ArrayList();
		times.add(new LabelValueBean(selectStr, selectStr));
		int i = 0;
		for (Iterator itr = getPollEndTimes().iterator(); itr.hasNext(); i++) {
			String week = (String) itr.next();
			if (i == 0) {
				times.add(new LabelValueBean(week + " week after poll starts",
						week));
			} else {
				times.add(new LabelValueBean(week + " weeks after poll starts",
						week));
			}
		}
		return times;
	}

	public List getListOfBlockOutTimes() {
		List listOfBlockOutTimes = new ArrayList();
		listOfBlockOutTimes.add(new LabelValueBean(
				PollTimeHelper.SELECT_POLL_BLOCKOUT_PERIOD,
				PollTimeHelper.SELECT_POLL_BLOCKOUT_PERIOD));
		if (!EnvProps.isProd()) {
			listOfBlockOutTimes.add(new LabelValueBean(" 1 DAY", Long
					.toString(PollTimeHelper.MILLI_SECS_PER_DAY)));
		}
		listOfBlockOutTimes.add(new LabelValueBean("1 WEEK", Long
				.toString(PollTimeHelper.MILLI_SECS_PER_WEEK)));
		listOfBlockOutTimes.add(new LabelValueBean("30 DAYS", Long
				.toString(PollTimeHelper.MILLI_SECS_PER_MONTH)));
		listOfBlockOutTimes.add(new LabelValueBean("60 DAYS", Long
				.toString(PollTimeHelper.MILLI_SECS_PER_MONTH * 2)));
		listOfBlockOutTimes.add(new LabelValueBean("90 DAYS", Long
				.toString(PollTimeHelper.MILLI_SECS_PER_MONTH * 3)));
		listOfBlockOutTimes.add(new LabelValueBean("180 DAYS", Long
				.toString(PollTimeHelper.MILLI_SECS_PER_MONTH * 6)));
		listOfBlockOutTimes.add(new LabelValueBean("365 DAYS", Long
				.toString(PollTimeHelper.MILLI_SECS_PER_MONTH * 12)));

		return listOfBlockOutTimes;
	}

	public void fillAndUpsertSearch(PollTO pto, String columnName)
			throws Exception {
		VCContentTO vcto = new VCContentTO();
		fillVCContentTO(pto, vcto, columnName);
		if (vcto.getContent().length() > 0) {
			VCContentBO.getInstance().upsertContent(vcto);
		}
	}

	private void fillVCContentTO(PollTO pto, VCContentTO vcto, String columnName) {
		vcto.setTableName(SearchTablesColumns.POLLS);
		vcto.setWhereId1(SearchTablesColumns.POLL_ID);
		vcto.setWhereValue1(pto.getPollId());
		if (columnName.equalsIgnoreCase(SearchTablesColumns.POLL_DESC)) {
			vcto.setContent(pto.getPollDesc());
			vcto.setColumnName(SearchTablesColumns.POLL_DESC);
		} else if (columnName.equalsIgnoreCase(SearchTablesColumns.POLL_NAME)) {
			vcto.setContent(pto.getPollName());
			vcto.setColumnName(SearchTablesColumns.POLL_NAME);
		} else if (columnName
				.equalsIgnoreCase(SearchTablesColumns.POLL_KEYWORDS)) {
			vcto.setContent(pto.getKeywords());
			vcto.setColumnName(SearchTablesColumns.POLL_KEYWORDS);
		} else if (columnName.equalsIgnoreCase(SearchTablesColumns.POLL_DATA)) {
			// poll data contains questions and answers, make them searchable
			// as well by creating one string out of them.
			UnSyncStringBuffer buff = new UnSyncStringBuffer();

			PollData pollData = pto.getPollData();
			Questionnaire questionnaire = pollData.getQuestionnaire();
			List questionDatas = questionnaire.getQuestions();
			for (Iterator itr = questionDatas.iterator(); itr.hasNext();) {
				QuestionData qData = (QuestionData) itr.next();
				buff.append(qData.getQuestion());
				buff.append(Q_AND_A_SEPARATOR);
				List aChoices = qData.getAnswerChoices();
				for (Iterator itr1 = aChoices.iterator(); itr1.hasNext();) {
					AnswerChoice aChoice = (AnswerChoice) itr1.next();
					buff.append(aChoice.getAnswer());
					buff.append(A_AND_A_SEPARATOR);
				}
				buff.append(A_AND_Q_SEPARATOR);
			}

			vcto.setContent(buff.toString());
			vcto.setColumnName(SearchTablesColumns.POLL_DATA);
		}
	}
}