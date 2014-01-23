/*
 * Created on Jan 28, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.polls;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import twitter4j.TwitterException;

import com.votingcentral.env.EnvProps;
import com.votingcentral.forms.polls.ProcessPollFormBean;
import com.votingcentral.model.bo.contests.ContestsBO;
import com.votingcentral.model.bo.mail.EmailBO;
import com.votingcentral.model.bo.parameters.VCParametersBO;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.votes.Votes;
import com.votingcentral.model.db.dao.to.ContestEntryTO;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.db.dao.to.TextLinkDescTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.AnswerTypeEnum;
import com.votingcentral.model.enums.PollPriorityEnum;
import com.votingcentral.model.enums.PollTypeEnum;
import com.votingcentral.model.enums.PollsStatusEnum;
import com.votingcentral.model.enums.TafTypeEnum;
import com.votingcentral.model.enums.VCChartPerspectiveEnum;
import com.votingcentral.model.enums.VCChartSizeEnum;
import com.votingcentral.model.enums.VCChartTypeEnum;
import com.votingcentral.model.enums.VCEmailTypeEnum;
import com.votingcentral.model.enums.VCParametersEnum;
import com.votingcentral.model.enums.VCPrivacyLevelEnum;
import com.votingcentral.pres.web.to.ContestWTO;
import com.votingcentral.pres.web.to.PollWTO;
import com.votingcentral.services.twitter.TwitterService;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.guid.GUIDService;
import com.votingcentral.util.pictures.ImageUtils;
import com.votingcentral.util.url.PresentationConstants;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollHelper {

	public static String SELECT_ANSWER_TYPE = "Select Answer Type";

	// this should match the action path in struts-contests.xml
	public static String CONTEST_FORM_NAME_PREFIX = "/p/c/castAVote";

	//this should match the action path in struts-config.xml
	public static String POLL_FORM_NAME_PREFIX = "/p/h/castAVote";

	//this should match the class name in tabcontent.css
	public static String TAB_CONTENT_ID_PREFIX = "tcontent";

	public static int TAB_HEADER_LENGTH = 25;

	public static final int POLLS_MAX_NAME_LENGTH = 100;

	public static final int MAX_VC_FILE_SIZE = 1048576;

	private static int POLL_STARTED_EMAIL_SEND_INTERVAL_DAYS = 3;

	private static final int MAX_EMAILS_TO_BE_SENT_PER_DAY = 1000;

	private final static Random s_generator = new Random();

	private static String pollEndInWeeks = EnvProps
			.getProperty("poll.end.date.options.weeks");

	private final static Log log = LogFactory.getLog(PollHelper.class);

	public static List getListOfAnswerTypes() {
		List listOfAnswerTypes = new ArrayList();
		listOfAnswerTypes.add(new LabelValueBean(SELECT_ANSWER_TYPE,
				SELECT_ANSWER_TYPE));
		listOfAnswerTypes.add(new LabelValueBean("Only ONE Answer Choice",
				"RADIO"));
		listOfAnswerTypes.add(new LabelValueBean("ONE OR MORE Answer Choices",
				"CHECKBOX"));
		if (!EnvProps.isProd()) {
			listOfAnswerTypes.add(new LabelValueBean("User Typed Answers",
					"TEXTAREA"));
		}

		return listOfAnswerTypes;
	}

	public static final String KEYWORD_SEPARATOR = ",";

	public static void fillPollData(ProcessPollFormBean pfb, String pollId,
			PollData pollData) throws Exception {

		pollData.setPollId(pollId);

		Questionnaire questionnaire = new Questionnaire();
		ArrayList questions = new ArrayList();
		QuestionData questionData = new QuestionData();
		questionData.setQuestion(pfb.getPollQuestion());
		questionData.setAnswerType(pfb.getPollQuestionType());
		String guid = GUIDService.getNextGUID();
		questionData.setQuestionId(guid);
		questionData.setQuestionTotalVotes(0);
		if (pfb.doesQuestionHavePics()) {
			questionData.setQuestionImageId(GUIDService.getNextGUID());
		}

		ArrayList answerChoices = new ArrayList();
		if ((pfb.getPollAnswer1() != null) && pfb.getPollAnswer1().length() > 1) {
			AnswerChoice ac = new AnswerChoice();
			ac.setAnswer(pfb.getPollAnswer1());
			ac.setAnswerId(GUIDService.getNextGUID());
			ac.setAnswerTotalVotes(0);
			if (pfb.isAnswer1HasPics()) {
				ac.setAnswerImageId(GUIDService.getNextGUID());
			}
			answerChoices.add(ac);
		}
		if ((pfb.getPollAnswer2() != null) && pfb.getPollAnswer2().length() > 1) {
			AnswerChoice ac = new AnswerChoice();
			ac.setAnswer(pfb.getPollAnswer2());
			ac.setAnswerId(GUIDService.getNextGUID());
			ac.setAnswerTotalVotes(0);
			if (pfb.isAnswer2HasPics()) {
				ac.setAnswerImageId(GUIDService.getNextGUID());
			}
			answerChoices.add(ac);
		}
		if ((pfb.getPollAnswer3() != null) && pfb.getPollAnswer3().length() > 1) {
			AnswerChoice ac = new AnswerChoice();
			ac.setAnswer(pfb.getPollAnswer3());
			ac.setAnswerId(GUIDService.getNextGUID());
			ac.setAnswerTotalVotes(0);
			if (pfb.isAnswer3HasPics()) {
				ac.setAnswerImageId(GUIDService.getNextGUID());
			}
			answerChoices.add(ac);
		}
		if ((pfb.getPollAnswer4() != null) && pfb.getPollAnswer4().length() > 1) {
			AnswerChoice ac = new AnswerChoice();
			ac.setAnswer(pfb.getPollAnswer4());
			ac.setAnswerId(GUIDService.getNextGUID());
			ac.setAnswerTotalVotes(0);
			if (pfb.isAnswer4HasPics()) {
				ac.setAnswerImageId(GUIDService.getNextGUID());
			}
			answerChoices.add(ac);
		}
		if ((pfb.getPollAnswer5() != null) && pfb.getPollAnswer5().length() > 1) {
			AnswerChoice ac = new AnswerChoice();
			ac.setAnswer(pfb.getPollAnswer5());
			ac.setAnswerId(GUIDService.getNextGUID());
			ac.setAnswerTotalVotes(0);
			if (pfb.isAnswer5HasPics()) {
				ac.setAnswerImageId(GUIDService.getNextGUID());
			}
			answerChoices.add(ac);
		}
		if ((pfb.getPollAnswer6() != null) && pfb.getPollAnswer6().length() > 1) {
			AnswerChoice ac = new AnswerChoice();
			ac.setAnswer(pfb.getPollAnswer6());
			ac.setAnswerId(GUIDService.getNextGUID());
			ac.setAnswerTotalVotes(0);
			if (pfb.isAnswer6HasPics()) {
				ac.setAnswerImageId(GUIDService.getNextGUID());
			}
			answerChoices.add(ac);
		}
		questionData.setAnswerChoices(answerChoices);
		questions.add(questionData);
		questionnaire.setQuestions(questions);
		pollData.setQuestionnaire(questionnaire);
	}

	public static void fillPollData(List contestFiles, String contestType,
			String pollId, PollData pollData) throws Exception {
		if ((contestFiles == null) || (contestFiles.size() == 0)) {
			return;
		}
		pollData.setPollId(pollId);

		Questionnaire questionnaire = new Questionnaire();
		ArrayList questions = new ArrayList();
		QuestionData questionData = new QuestionData();
		questionData.setQuestion(EnvProps.getProperty("contest.question"));
		questionData.setAnswerType(AnswerTypeEnum.RADIO.getName());
		String guid = GUIDService.getNextGUID();
		questionData.setQuestionId(guid);
		questionData.setQuestionTotalVotes(0);

		ArrayList answerChoices = new ArrayList();
		for (Iterator itr = contestFiles.iterator(); itr.hasNext();) {
			ContestEntryTO cto = (ContestEntryTO) itr.next();
			AnswerChoice ac = new AnswerChoice();
			ac.setAnswer(cto.getUserComments());
			ac.setAnswerId(GUIDService.getNextGUID());
			ac.setAnswerTotalVotes(0);
			ac.setAnswerImageId(cto.getFileId());
			answerChoices.add(ac);
		}
		questionData.setAnswerChoices(answerChoices);
		questions.add(questionData);
		questionnaire.setQuestions(questions);
		pollData.setQuestionnaire(questionnaire);
	}

	public static void fillContestPollTO(List contestFiles, String pollId,
			String pollData, String contestTypeEnum, PollTO pto)
			throws Exception {

		if ((contestFiles == null) || (contestFiles.size() == 0)) {
			return;
		}
		pto.setPollId(pollId);
		pto.setPollStatus(PollsStatusEnum.CONFIRMED.getName());

		pto.setPollType(PollTypeEnum.CONTEST);

		pto.setPollPrivacyLevel(VCPrivacyLevelEnum.PUBLIC.getName());

		int totalVotes = 0;
		pto.setPollTotalVotes(totalVotes);

		pto.setPollName(contestTypeEnum + " "
				+ EnvProps.getProperty("contest.name"));
		//'votingcentral' is the user id 19. set that as the poll creator for
		// now.
		pto.setUserId(19);
		pto.setPollDesc(contestTypeEnum + " "
				+ EnvProps.getProperty("contest.desc"));

		// get four letter words from the user entered keywords and store those
		// as
		// keywords.
		pto.setKeywords(getKeyWords(contestFiles));
		processKeywords(pto);
		pto.setPollCategory1(contestTypeEnum);
		PollData pd = new PollData(pollData);
		pto.setPollData(pd);
		pto.setPollPriority(pd.hasPollImages() ? PollPriorityEnum.SIX
				: PollPriorityEnum.TWO);
		Date pollCreateTimestamp = new Date(System.currentTimeMillis());
		int contestEndsInDays = Integer.parseInt(EnvProps
				.getProperty("contest.days.active"));
		Date pollEnd = new Date(PollTimeHelper.getInstance().getFutureDate(
				contestEndsInDays));
		pto.setCreateTimestamp(pollCreateTimestamp);
		pto.setModifyTimestamp(pollCreateTimestamp);
		pto.setStartTimestamp(pollCreateTimestamp);
		pto.setEndTimestamp(pollEnd);
		pto.setExpireTimestamp(pollEnd);
	}

	private static String getKeyWords(List contestFiles) {
		UnSyncStringBuffer buff = new UnSyncStringBuffer();
		int MIN_WORD_SIZE = Integer.parseInt(EnvProps
				.getProperty("contest.min.word.size"));
		for (Iterator itr = contestFiles.iterator(); itr.hasNext();) {
			ContestEntryTO cto = (ContestEntryTO) itr.next();
			String[] words = StringUtils.split(cto.getKeywords());
			for (int i = 0; i < words.length; i++) {
				if (words[i].length() > MIN_WORD_SIZE) {
					buff.append(words[i]).append(",");
				}
			}
		}
		return buff.toString();
	}

	public static void checkAndSendPollEndedEmails() {
		try {
			List unEmailedPolls = PollBO.getInstance()
					.getAllExpiredPollsEmailStatusUnsent();
			log.debug("unEmailed ended polls count:" + unEmailedPolls.size());
			for (Iterator i = unEmailedPolls.iterator(); i.hasNext();) {
				PollTO pto = (PollTO) i.next();
				if (pto.getPollId().length() == 0)
					continue;
				long creator = pto.getUserId();
				VCUserTO vto = UserBO.getInstance().getUserByUserId(creator);
				String emailAddress = vto.getEmailAddress();
				sendPollEndedEmail(pto, emailAddress);
				// set the status to email sent.
				log.debug("setting status to email sent for :"
						+ pto.getPollId());
				PollBO.getInstance().setPollStatus(pto.getPollId(),
						PollsStatusEnum.ENDEMAILSENT.getName());
			}
		} catch (SQLException e) {
			log.fatal("problem sending poll ended email", e);
		}
	}

	public static void checkAndSendPollStartedEmails() {
		try {
			String emailLastSentTime = VCParametersBO.getInstance()
					.getParameterByName(
							VCParametersEnum.NEW_POLLS_DIGEST_LAST_SENT);
			log.error("Emails were last sent:" + emailLastSentTime);
			log.error("POLL_STARTED_EMAIL_SEND_INTERVAL_DAYS "
					+ POLL_STARTED_EMAIL_SEND_INTERVAL_DAYS);
			SimpleDateFormat format = PollTimeHelper.getInstance()
					.getCalendarUIDateFormatter();
			Date currDate = PollTimeHelper.getInstance().getCurrentDate();
			try {
				Date emailLastSentDate = format.parse(emailLastSentTime);
				Date emailToBeSentNext = PollTimeHelper.getInstance()
						.getFutureDateFromDate(emailLastSentDate,
								POLL_STARTED_EMAIL_SEND_INTERVAL_DAYS);
				if (currDate.before(emailToBeSentNext)) {
					log.warn("not ready to send yet, emailLastSent :"
							+ emailLastSentDate + ", currDate: " + currDate
							+ ", emailToBeSentNext: " + emailToBeSentNext);
					return;
				}
			} catch (ParseException e1) {
				log.fatal("problem parsing date: " + emailLastSentTime, e1);
			}
			List emailableUsers = UserBO.getInstance()
					.getAllPollStartedEmailableUsers();
			List unEmailedPolls = PollBO.getInstance()
					.getAllStartedPollsEmailStatusUnsent();
			if (emailableUsers == null || emailableUsers.isEmpty()
					|| unEmailedPolls == null || unEmailedPolls.isEmpty()) {
				return;
			}
			VCParametersBO.getInstance().setParameterByName(
					VCParametersEnum.NEW_POLLS_DIGEST_LAST_SENT,
					format.format(currDate));
			log.debug("unEmailed started polls count:" + unEmailedPolls.size());
			String startedPollsHtml = getStartedPollsHtml(unEmailedPolls);
			int emailableUsersCount = emailableUsers.size();
			int ratio = emailableUsersCount / MAX_EMAILS_TO_BE_SENT_PER_DAY;
			log.error("emails will be sent across days:" + ratio);
			if (ratio > POLL_STARTED_EMAIL_SEND_INTERVAL_DAYS) {
				log
						.error("incrementing since the current days are not enough:");
				POLL_STARTED_EMAIL_SEND_INTERVAL_DAYS = ratio + 1;
			}
			for (Iterator i1 = emailableUsers.iterator(); i1.hasNext();) {
				VCUserTO vto = (VCUserTO) i1.next();
				sendPollStartedEmail(startedPollsHtml, vto, vto.getUserId()
						% ratio);
			}
			postToTwitter(unEmailedPolls);
		} catch (SQLException e) {
			log.fatal("problem sending poll started email", e);
		} catch (TwitterException e) {
			log.fatal("problem sending poll started email, twitter", e);
		}
	}

	public static void checkAndSendUnfinishedPollEmails() {
		try {
			List unFinishedPolls = PollBO.getInstance().getUnfinishedPolls();
			if (unFinishedPolls == null || unFinishedPolls.isEmpty()) {
				return;
			}
			log.debug("unFinishedPolls started polls count:"
					+ unFinishedPolls.size());
			for (Iterator i1 = unFinishedPolls.iterator(); i1.hasNext();) {
				PollTO pto = (PollTO) i1.next();
				if (pto.getUfpReminderLastSentTimestamp() != null) {
					Date currDate = PollTimeHelper.getInstance()
							.getFutureDateFromDate(
									pto.getUfpReminderLastSentTimestamp(), 1);
					if (currDate.before(PollTimeHelper.getInstance()
							.getCurrentDate())) {
						sendUnfinishedPollReminderEmail(pto);
					}
				} else {
					sendUnfinishedPollReminderEmail(pto);
				}
			}
		} catch (SQLException e) {
			log.fatal("problem sending unfinished poll reminder email", e);
		}
	}

	public static void sendUnfinishedPollReminderEmail(PollTO pto) {
		try {
			long creatorId = pto.getUserId();
			VCUserTO vto = UserBO.getInstance().getUserByUserId(creatorId);
			List toAddresses = new ArrayList();
			toAddresses.add(vto.getEmailAddress());
			String domContext = VCURLHelper.getDomainContext();
			Object[] values = { vto.getDisplayUserName(),
					VCURLHelper.getCreatePollUrl(domContext),
					pto.getPollName(),
					EnvProps.getProperty("full.app.server.url"),
					EnvProps.getProperty("full.app.server.url"),
					VCURLHelper.getCreatePollUrl(domContext) };
			EmailBO.getInstance().createMailRequest(toAddresses,
					VCEmailTypeEnum.UNFINISHED_POLL_REMINDER, values, 0);
			PollBO.getInstance().setUnfinishedPollReminderSentNow(
					pto.getPollId());
		} catch (SQLException e) {
			log.fatal("Problem creating unfinished poll reminder email", e);
		}
	}

	public static void sendPollEndedEmail(PollTO pto, String toEmailAddress) {
		List toAddresses = new ArrayList();
		toAddresses.add(toEmailAddress);
		String domContext = VCURLHelper.getDomainContext();
		TextLinkDescTO age = VCURLHelper.getUrlAndDescForBarGraphByPerspective(
				domContext, VCChartPerspectiveEnum.ACX, pto.getPollId(), pto
						.getPollData().getFirstQuestion().getQuestionId());

		TextLinkDescTO gender = VCURLHelper
				.getUrlAndDescForBarGraphByPerspective(domContext,
						VCChartPerspectiveEnum.GCX, pto.getPollId(), pto
								.getPollData().getFirstQuestion()
								.getQuestionId());

		TextLinkDescTO loc = VCURLHelper.getUrlAndDescForBarGraphByPerspective(
				domContext, VCChartPerspectiveEnum.LCX, pto.getPollId(), pto
						.getPollData().getFirstQuestion().getQuestionId());

		TextLinkDescTO pie = VCURLHelper.getUrlAndDescForPieChart(domContext,
				pto.getPollId(), pto.getPollData().getFirstQuestion()
						.getQuestionId());

		String bigChartUrl = VCURLHelper.getURLForGraphImage(domContext,
				VCChartTypeEnum.BAR, VCChartPerspectiveEnum.ACX,
				VCChartSizeEnum.BIG, pto.getPollId(), pto.getPollData()
						.getFirstQuestion().getQuestionId());

		String extendPollUrl = VCURLHelper.getExtendPollUrl(domContext, pto
				.getPollId());
		String[] weeks = StringUtils.split(pollEndInWeeks, ",");
		int len = weeks.length;
		String weeksNum = weeks[len - 1];
		Object[] values = {
				pto.getPollName(),
				VCURLHelper.getDisplayPollUrl(domContext, pto.getPollId()),
				VCURLHelper.getDisplayPollResultsUrl(domContext, pto
						.getPollId()), age.getHref(), age.getText(),
				gender.getHref(), gender.getText(), loc.getHref(),
				loc.getText(), pie.getHref(), pie.getText(), bigChartUrl,
				"Bigger Image", extendPollUrl, weeksNum,
				VCURLHelper.getCreatePollUrl(domContext) };
		try {
			EmailBO.getInstance().createMailRequest(toAddresses,
					VCEmailTypeEnum.POLL_ENDED_DETAILED, values, 0);
		} catch (SQLException e) {
			log.fatal("Problem creating poll ended email event", e);
		}
	}

	public static void sendPollStartedEmail(String startedPollsHtml,
			VCUserTO vto, long dayNumber) throws SQLException {
		List toAddresses = new ArrayList();
		toAddresses.add(vto.getEmailAddress());
		String domContext = VCURLHelper.getDomainContext();

		String voterUrl = VCURLHelper.getVCUserPublicProfileUrl(domContext, vto
				.getDisplayUserName());

		Object[] values = { voterUrl, vto.getDisplayUserName(),
				startedPollsHtml, VCURLHelper.getCreatePollUrl(domContext),
				VCURLHelper.getTurnOffDailyEmailUrl(domContext) };
		long delayMins = dayNumber * PollTimeHelper.MINS_PER_DAY
				+ s_generator.nextInt(PollTimeHelper.MINS_PER_DAY);
		try {
			EmailBO.getInstance().createMailRequest(toAddresses,
					VCEmailTypeEnum.POLL_STARTED, values, delayMins);
		} catch (SQLException e) {
			log.fatal("Problem creating poll started email event", e);
		}
	}

	/**
	 * Iterate through the list of polls and post them to twitter.
	 * 
	 * @param ptos
	 * @throws TwitterException
	 */
	private static void postToTwitter(List ptos) throws TwitterException {
		if (ptos != null && ptos.size() > 0) {
			for (Iterator itr = ptos.iterator(); itr.hasNext();) {
				PollTO pto = (PollTO) itr.next();
				//send the poll to twitter account of votingcentral
				TwitterService.getInstance().updateStatus(pto.getPollName(),
						pto.getPollId());
				//send polls to all twitter followers.
				TwitterService.getInstance().sendDirectMessageToFollowers(
						pto.getPollName(), pto.getPollId());
			}
		}

	}

	private static String getStartedPollsHtml(List ptos) throws SQLException {
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		String domContext = VCURLHelper.getDomainContext();

		buffer.append("<ul>");
		for (Iterator i = ptos.iterator(); i.hasNext();) {
			PollTO pto = (PollTO) i.next();
			VCUserTO pollCreator = UserBO.getInstance().getUserByUserId(
					pto.getUserId());
			buffer.append("<li>");
			//<a href="http://displayPollURL">Poll Name </a> - created by <a
			// href="http://displayUserURL">Display UserName</a>
			buffer.append("<a href=\"");
			buffer.append(VCURLHelper.getDisplayPollUrl(domContext, pto
					.getPollId()));
			buffer.append("\">");
			buffer.append(pto.getPollName());
			buffer.append("</a>");
			buffer.append("&nbsp; - &nbsp; created by &nbsp;");
			buffer.append("<a href=\"");
			buffer.append(VCURLHelper.getVCUserPublicProfileUrl(domContext,
					pollCreator.getDisplayUserName()));
			buffer.append("\">");
			buffer.append(pollCreator.getDisplayUserName());
			buffer.append("</a>");

			buffer.append("</li>");
			buffer.append("\n");
			//after we get the HTML mark each poll as started email sent.
			PollBO.getInstance().setPollStartedEmailStatus(pto.getPollId(), 1);
		}
		buffer.append("</ul>");
		return buffer.toString();
	}

	public static void sendContestEndedEmail(String contestType, PollTO pto,
			String toEmailAddress) {
		List toAddresses = new ArrayList();
		toAddresses.add(toEmailAddress);
		String domContext = VCURLHelper.getDomainContext();

		Object[] values = {
				contestType,
				VCURLHelper.getDisplayPollUrl(domContext, pto.getPollId()),
				VCURLHelper.getContestsUploadByTypeUrl(domContext, contestType),
				VCURLHelper.getContestsMainUrl(domContext),
				VCURLHelper.getCreatePollUrl(domContext) };
		try {
			EmailBO.getInstance().createMailRequest(toAddresses,
					VCEmailTypeEnum.CONTEST_ENDED, values, 0);
		} catch (SQLException e) {
			log.fatal("Problem creating contest ended email event", e);
		}
	}

	public static void sendContestStartedEmail(PollTO pto,
			String toEmailAddress, String pollId) {
		List toAddresses = new ArrayList();
		String domContext = VCURLHelper.getDomainContext();
		toAddresses.add(toEmailAddress);
		Object[] values = { pto.getPollName(),
				VCURLHelper.getDisplayPollUrl(domContext, pollId) };
		try {
			EmailBO.getInstance().createMailRequest(toAddresses,
					VCEmailTypeEnum.CONTESTS_PICTURE_SELECTED, values, 0);
		} catch (SQLException e) {
			log.fatal("Problem creating contest picture selected event", e);
		}
	}

	public static void setImageUrls(String domContext, List questionDatas) {
		for (Iterator itr = questionDatas.iterator(); itr.hasNext();) {
			QuestionData qData = (QuestionData) itr.next();
			qData.setMaxImageUrl(VCURLHelper.getDisplayImageUrl(domContext,
					qData.getQuestionImageId(), ImageUtils.MAX_W,
					ImageUtils.MAX_H));
			qData.setMinImageUrl(VCURLHelper.getDisplayImageUrl(domContext,
					qData.getQuestionImageId(), ImageUtils.MIN_W,
					ImageUtils.MIN_H));
			List answerChoices = qData.getAnswerChoices();
			for (Iterator itr1 = answerChoices.iterator(); itr1.hasNext();) {
				AnswerChoice ac = (AnswerChoice) itr1.next();
				ac.setMaxImageUrl(VCURLHelper
						.getDisplayImageUrl(domContext, ac.getAnswerImageId(),
								ImageUtils.MAX_W, ImageUtils.MAX_H));
				ac.setMinImageUrl(VCURLHelper
						.getDisplayImageUrl(domContext, ac.getAnswerImageId(),
								ImageUtils.MIN_W, ImageUtils.MIN_H));
			}
		}
	}

	public static List getKeyWordUrls(String keywords, String domainContext) {
		List keywordUrls = new ArrayList();
		String[] keywordArr = StringUtils.split(keywords,
				PollHelper.KEYWORD_SEPARATOR);
		for (int i = 0; i < keywordArr.length; i++) {
			String url = VCURLHelper.getSearchUrlForSearchString(domainContext,
					keywordArr[i]);
			TextLinkDescTO tldto = new TextLinkDescTO();
			tldto.setHref(url);
			tldto.setDesc(keywordArr[i]);
			tldto.setText(keywordArr[i]);
			keywordUrls.add(tldto);
		}
		return keywordUrls;
	}

	public static void fillPollWTO(String domainContext, String tabContentId,
			PollWTO wto, PollTO pto, int shortNameLen, int formNumber,
			int totalFpCount) {
		try {
			BeanUtils.copyProperties(wto, pto);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wto.setKwUrls(getKeyWordUrls(pto.getKeywords(), domainContext));
		wto.setStartTimestampStr(PollTimeHelper.getInstance().getTimeString(
				pto.getStartTimestamp().getTime()));
		wto.setEndTimestampStr(PollTimeHelper.getInstance().getTimeString(
				pto.getEndTimestamp().getTime()));
		wto.setTafUrl(VCURLHelper.getTAFUrl(domainContext, VCURLHelper
				.getDisplayPollUrl(domainContext, pto.getPollId()),
				"Great Poll at VotingCentral", TafTypeEnum.POLL));
		wto.setDisplayPollUrl(VCURLHelper.getDisplayPollUrl(domainContext, pto
				.getPollId()));
		wto.setDeletePollUrl(VCURLHelper.getDeletePollUrl(domainContext, pto
				.getPollId()));
		wto.setTabContentId(TAB_CONTENT_ID_PREFIX + tabContentId);
		if (pto.getPollName().length() > TAB_HEADER_LENGTH) {
			wto.setTabHeading(pto.getPollName().substring(0, TAB_HEADER_LENGTH)
					+ "...");
		} else {
			wto.setTabHeading(pto.getPollName());
		}
		if (wto.getPollName().length() > shortNameLen) {
			wto.setPollName(wto.getPollName().substring(0, shortNameLen)
					+ "...");
		}
		String displayName = getDisplayUserName(pto);
		wto.setCreatorUrl(VCURLHelper.getVCUserPublicProfileUrl(domainContext,
				displayName));
		wto.setCreatorUserName(displayName);
		String pollFormName = POLL_FORM_NAME_PREFIX + formNumber + "."
				+ PresentationConstants.WEB_EXTN + "?action=voteFromHome";
		wto.setFormName(pollFormName);
		wto.setBtnPauseNumber("btnPause" + formNumber);
		wto.setTotalFeaturedPollCount(String.valueOf(totalFpCount));
		wto.setFormId(String.valueOf(formNumber));
		if (pto.getPollData() != null && pto.getPollData().hasPollImages()) {
			String imageId = pto.getPollData().getDefaultPollImage();
			wto
					.setDefaultImageUrlMax(VCURLHelper.getDisplayImageUrl(
							domainContext, imageId, ImageUtils.MAX_W,
							ImageUtils.MAX_H));
			wto
					.setDefaultImageUrlMin(VCURLHelper.getDisplayImageUrl(
							domainContext, imageId, ImageUtils.MIN_W,
							ImageUtils.MIN_H));
			wto.setDefaultImageUrlThumb(VCURLHelper.getDisplayImageUrl(
					domainContext, imageId, ImageUtils.THUMB_W,
					ImageUtils.THUMB_H));
		} else {
			wto.setDefaultImageUrlMax(null);
			wto.setDefaultImageUrlMin(null);
			wto.setDefaultImageUrlThumb(null);
		}
		if (pto.getPollData() != null) {
			wto.setDefaultQuestion(pto.getPollData().getFirstQuestion()
					.getQuestion());
		}
	}

	public static String getDisplayUserName(PollTO pto) {
		String displayName = "votingcentral";
		if (pto == null) {
			return displayName;
		}
		try {
			if (pto.getPollType().equalsIgnoreCase(PollTypeEnum.CONTEST)) {
				displayName = ContestsBO.CONTEST_CREATOR_USER;
			} else {
				VCUserTO vto = UserBO.getInstance().getUserByUserId(
						pto.getUserId());
				if (vto != null) {
					displayName = vto.getDisplayUserName();
				}
			}
		} catch (SQLException e1) {
			log.error("error loading user", e1);
		}
		return displayName;
	}

	public static void fillContestWTO(String domainContext, ContestWTO wto,
			PollTO pto, String contestType, int formNumber, long userId) {
		try {
			BeanUtils.copyProperties(wto, pto);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wto.setKwUrls(getKeyWordUrls(pto.getKeywords(), domainContext));
		wto.setStartTimestampStr(PollTimeHelper.getInstance().getTimeString(
				pto.getStartTimestamp().getTime()));
		wto.setEndTimestampStr(PollTimeHelper.getInstance().getTimeString(
				pto.getEndTimestamp().getTime()));
		wto.setTafUrl(VCURLHelper.getTAFUrl(domainContext, VCURLHelper
				.getDisplayPollUrl(domainContext, pto.getPollId()),
				"Great Contest at VotingCentral", TafTypeEnum.CONTEST));
		wto.setDisplayPollUrl(VCURLHelper.getDisplayPollUrl(domainContext, pto
				.getPollId()));
		wto.setUploadPicturesUrl(VCURLHelper.getContestsUploadByTypeUrl(
				domainContext, contestType));
		String contestFormName = CONTEST_FORM_NAME_PREFIX + formNumber + "."
				+ PresentationConstants.WEB_EXTN + "?action=voteFromContest";
		wto.setFormName(contestFormName);
		wto.setFormId(String.valueOf(formNumber));
		if (userId == 0) {
			wto.setShowPollAsDisabled(false);
		} else {
			try {
				boolean canVote = Votes.getInstance().canUserVote(userId,
						pto.getPollId());
				wto.setShowPollAsDisabled(!canVote);
				if (!canVote) {
					wto.setMessage("You already Voted in this Contest");
				}
			} catch (SQLException e1) {
				wto.setShowPollAsDisabled(true);
			}
		}
	}

	public static void processKeywords(PollTO pto) {
		String[] keywords = StringUtils.split(pto.getKeywords(),
				PollHelper.KEYWORD_SEPARATOR);
		for (int i = 0; i < keywords.length; i++) {
			String cleaned = keywords[i].trim();
			cleaned = StringUtils.lowerCase(cleaned);
			cleaned = StringUtils.deleteSpaces(cleaned);
			keywords[i] = cleaned;
		}
		String keyword = StringUtils.join(keywords,
				PollHelper.KEYWORD_SEPARATOR);
		pto.setKeywords(keyword);
	}

	public static String getCleanedKeywords(String in) {
		if (in == null || in.length() == 0) {
			return in;
		}
		String[] res = StringUtils.split(in, PollHelper.KEYWORD_SEPARATOR);
		List newStr = new ArrayList(res.length);
		for (int i = 0; i < res.length; i++) {
			String one = res[i];
			if (one != null && one.length() > 0) {
				one = StringUtils.trim(one);
				one = StringUtils.strip(one);
				one = StringUtils.chomp(one);
				if (!newStr.contains(one)) {
					newStr.add(one);
				}
			}
		}
		String joined = StringUtils.join(newStr.iterator(),
				PollHelper.KEYWORD_SEPARATOR + " ");
		return joined;
	}

	public static List getDisplayablePolls(String domContext, List pollIds,
			int shortNameLen, UnSyncStringBuffer buffer) throws SQLException {
		List pollWtos = new ArrayList();
		if (pollIds == null || pollIds.size() == 0) {
			return pollWtos;
		}
		int i = 1;
		int total = pollIds.size();
		for (Iterator itr = pollIds.iterator(); itr.hasNext(); i++) {
			String pollId = (String) itr.next();
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			buffer.append(pto.getKeywords() + ",");
			PollWTO wto = new PollWTO();
			if (pto.getPollData() != null) {
				PollHelper.setImageUrls(domContext, pto.getPollData()
						.getQuestionnaire().getQuestions());
			}
			PollHelper.fillPollWTO(domContext, String.valueOf(i), wto, pto,
					shortNameLen, i, total);
			pollWtos.add(wto);
		}
		return pollWtos;
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email) {
		if (email == null || email.length() == 0) {
			return false;
		}
		email = StringUtils.trim(email);
		// lowercase it
		email = StringUtils.lowerCase(email);
		// remove trailing new lines
		email = StringUtils.chomp(email);
		String regex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)";
		return email.matches(regex);

	}

	public static void setTimeAgoStr(List pollWtos, boolean useStartTime) {
		if (pollWtos == null) {
			return;
		}
		for (Iterator itr = pollWtos.iterator(); itr.hasNext();) {
			PollWTO wto = (PollWTO) itr.next();
			Date diffTimestamp = null;

			if (useStartTime) {
				diffTimestamp = wto.getStartTimestamp();
			} else {
				diffTimestamp = wto.getEndTimestamp();
			}
			Date curr = Calendar.getInstance(
					PollTimeHelper.POLL_TIMES_TIME_ZONE).getTime();
			String diff = PollTimeHelper.getInstance().getDiffTimeString(curr,
					diffTimestamp);
			wto.setTimeAgoStr(diff);
		}
	}

}