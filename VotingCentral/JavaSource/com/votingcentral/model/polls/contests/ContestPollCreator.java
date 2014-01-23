/*
 * Created on Jan 16, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.polls.contests;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import twitter4j.TwitterException;

import com.votingcentral.env.EnvProps;
import com.votingcentral.model.bo.category.CategoryBO;
import com.votingcentral.model.bo.contests.ContestsBO;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.db.dao.to.CategoryTO;
import com.votingcentral.model.db.dao.to.ContestEntryTO;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.ContestFileStatusEnum;
import com.votingcentral.model.enums.PollsStatusEnum;
import com.votingcentral.model.enums.VCCategoryTypeEnum;
import com.votingcentral.model.enums.VCEmailTypeEnum;
import com.votingcentral.model.polls.PollData;
import com.votingcentral.model.polls.PollDataDOM;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.model.search.SearchTablesColumns;
import com.votingcentral.services.twitter.TwitterService;
import com.votingcentral.util.guid.GUIDService;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ContestPollCreator {

	private static int MIN_PICTURES_PER_POLL = Integer.parseInt(EnvProps
			.getProperty("contest.min.pictures.per.poll"));

	private static int MAX_PICTURES_PER_POLL = Integer.parseInt(EnvProps
			.getProperty("contest.max.pictures.per.poll"));

	private final static Log log = LogFactory.getLog(ContestPollCreator.class);

	public ContestPollCreator() {
	}

	public void refreshPoll() {
		List categories;
		try {
			categories = CategoryBO.getInstance().getListOfSuperCategories(
					VCCategoryTypeEnum.CONTEST);
			for (Iterator itr = categories.iterator(); itr.hasNext();) {
				CategoryTO cto = (CategoryTO) itr.next();
				refreshPollByContestType(cto.getSuperCategory());
			}
		} catch (SQLException e) {
			log.fatal("Failure Intializing Contests ", e);
		}
	}

	public void refreshPollByContestType(String contestType) {
		try {
			// get the current open contest files of this type.
			List curContestFiles = ContestsBO.getInstance()
					.getContestEntriesByContestTypeFileStatus(contestType,
							ContestFileStatusEnum.INUSE);
			//if there are files and they are not ready to expire then
			//just return, because the poll is currently ON.
			if (!isReadyToCreateNewPoll(curContestFiles, contestType)) {
				return;
			}
			//Mark all those files as expired.
			//send emails to all the users whose polls have ended.
			changeFileStatusContestFiles(curContestFiles,
					ContestFileStatusEnum.EXPIRED);
			//set poll id as null, will be used from the contest entries
			// themselves.
			sendEmailsToUsers(curContestFiles, VCEmailTypeEnum.POLL_ENDED
					.getName(), null, true);
			//end the poll, should we ? or will it automatically expire.

			//get a list of oldest n number of photos, that don't belong to
			//the same user.
			List contestFiles = ContestsBO.getInstance()
					.getOldestByContestTypeFileStatus(contestType,
							ContestFileStatusEnum.APPROVED,
							MAX_PICTURES_PER_POLL);
			//if we don't get any results back or get less than
			// MIN_PICTURES_PER_POLL files
			//return back, wait for more files to be uploaded.
			if (contestFiles == null
					|| contestFiles.size() < MIN_PICTURES_PER_POLL) {
				log.info("Minumim number of pcitures in poll not found");
				return;
			}
			String pollId = createNewPollUsingContestFiles(contestFiles,
					contestType);
			setPollIdOnContestFiles(contestFiles, pollId);
			changeFileStatusContestFiles(contestFiles,
					ContestFileStatusEnum.INUSE);
			// send emails to all the users whose polls have been selected
			sendEmailsToUsers(contestFiles,
					VCEmailTypeEnum.CONTESTS_PICTURE_SELECTED.getName(),
					pollId, false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setPollIdOnContestFiles(List contestFiles, String pollId) {
		for (Iterator itr = contestFiles.iterator(); itr.hasNext();) {
			ContestEntryTO cto = (ContestEntryTO) itr.next();
			cto.setPollId(pollId);
			log.info("assinging poll id for :" + cto.getContestType()
					+ " file id: " + cto.getFileId() + " poll Id:" + pollId);
		}
	}

	private boolean isReadyToCreateNewPoll(List curContestFiles,
			String contestType) {
		boolean readyToCreateNewPoll = true;
		//if we didnt get any results back, proceed to create a new poll.
		if ((curContestFiles == null) || (curContestFiles.size() == 0)) {
			return true;
		}
		log.info("checking if ready to create polls for contest type:"
				+ contestType);
		String pollId = "";
		pollId = ((ContestEntryTO) curContestFiles.get(0)).getPollId();
		try {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			Date pollEnd = pto.getEndTimestamp();
			Date current = PollTimeHelper.getInstance().getCurrentDate();
			//if poll end date is after current date then
			//the poll is still live, don't expire this.
			if (pollEnd.after(current)) {
				readyToCreateNewPoll = false;
			}
		} catch (SQLException e) {
			log.fatal("Exception loading a poll", e);
		}
		return readyToCreateNewPoll;
	}

	private String createNewPollUsingContestFiles(List contestFiles,
			String contestType) throws Exception {
		if ((contestFiles == null) || (contestFiles.size() == 0)) {
			//nothing to create.
			return "";
		}
		log.info("Creating new poll using contest files");
		//generate the PollTO.
		PollData pd = new PollData();
		String pollId = GUIDService.getNextGUID();
		PollHelper.fillPollData(contestFiles, contestType, pollId, pd);
		PollDataDOM pDom = new PollDataDOM(pd);

		PollTO pto = new PollTO();
		PollHelper.fillContestPollTO(contestFiles, pollId, pDom
				.getPollDataAsXMLString(), contestType, pto);
		//create the Poll.
		PollBO.getInstance().createNewPoll(pto);
		PollBO.getInstance().fillAndUpsertSearch(pto,
				SearchTablesColumns.POLL_KEYWORDS);

		try {
			// submit the contest to Twitter
			TwitterService.getInstance().updateStatus(pto.getPollName(),
					pto.getPollId());
			TwitterService.getInstance().sendDirectMessageToFollowers(
					pto.getPollName(), pto.getPollId());
		} catch (TwitterException e) {
			log.fatal("Exception sending to twitter, but moving on", e);
		}
		return pto.getPollId();
	}

	private void changeFileStatusContestFiles(List contestFiles,
			String fileStatusEnum) throws Exception {
		if ((contestFiles == null) || (contestFiles.size() == 0)) {
			//nothing to change status on
			return;
		}
		for (Iterator itr = contestFiles.iterator(); itr.hasNext();) {
			ContestEntryTO cto = (ContestEntryTO) itr.next();
			cto.setFileStatus(fileStatusEnum);
			log.info("changing file status for:" + cto.getContestType()
					+ " file id" + cto.getFileId());
			ContestsBO.getInstance().updateContestEntryByFileId(cto);
		}

	}

	private void sendEmailsToUsers(List contestFiles, String emailType,
			String pollId, boolean contestEnded) throws SQLException {
		if ((contestFiles == null) || (contestFiles.size() == 0)) {
			//no users to send emails.
			return;
		}
		List toAddresses = new ArrayList();
		for (Iterator itr = contestFiles.iterator(); itr.hasNext();) {
			ContestEntryTO cto = (ContestEntryTO) itr.next();
			log.info("Sending email for:" + cto.getContestType() + " file id"
					+ cto.getFileId());
			long emailAddressId = cto.getUserId();
			VCUserTO vto = UserBO.getInstance().getUserByUserId(emailAddressId);
			String emailAddress = vto.getEmailAddress();
			log.info("Sending email for:" + cto.getContestType() + " file id"
					+ cto.getFileId() + " email address" + emailAddress
					+ " poll Id" + cto.getPollId());
			if (pollId == null || pollId.length() == 0) {
				pollId = cto.getPollId();
			}
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			if (contestEnded) {
				PollHelper.sendContestEndedEmail(cto.getContestType(), pto,
						emailAddress);
				PollBO.getInstance().setPollStatus(pollId,
						PollsStatusEnum.ENDEMAILSENT.getName());
			} else {
				PollHelper.sendContestStartedEmail(pto, emailAddress, pollId);
			}
		}

	}

}