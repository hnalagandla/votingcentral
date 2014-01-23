/*
 * Created on Aug 16, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.polls;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import com.votingcentral.forms.VCBaseFormBean;
import com.votingcentral.model.db.dao.ICategoryDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.CategoryTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ProcessPollFormBean extends VCBaseFormBean {

	private final static Log log = LogFactory.getLog(ProcessPollFormBean.class);

	private static final int MAX_CHARS_IN_POLL_NAME = 64;

	private static final int MAX_CHARS_IN_POLL_DESC = 128;

	private static final int MAX_CHARS_IN_KEYWORDS = 64;

	private final String datePattern = "MM/dd/yyyy 'at' hh:mm:ss a z";

	private String afterSubmitPollChoice = null;

	private String answerChoice = null;

	private boolean pollRepeated = false;

	private String repeatedPollId = null;

	private String currPollId = null;

	private String pollDesc = null;

	private String pollName = null;

	private String keywords = null;

	private String category1 = null;

	private String category2 = null;

	private String category3 = null;

	private String headline1 = null;

	private String headlineSrc1 = null;

	private String headline2 = null;

	private String headlineSrc2 = null;

	private String pollQuestionType = null;

	private String pollQuestion = null;

	private String currQuestionId = null;

	private boolean questionHasPics = false;

	private boolean answer1HasPics = false;

	private String pollAnswer1 = null;

	private boolean answer2HasPics = false;

	private String pollAnswer2 = null;

	private boolean answer3HasPics = false;

	private String pollAnswer3 = null;

	private boolean answer4HasPics = false;

	private String pollAnswer4 = null;

	private boolean answer5HasPics = false;

	private String pollAnswer5 = null;

	private boolean answer6HasPics = false;

	private String pollAnswer6 = null;

	private String pollStartTimestamp = null;

	private String pollEndTimestamp = null;

	private String pollExpireTimestamp = null;

	private String msgBrdStartTimestamp = null;

	private String msgBrdEndTimestamp = null;

	private List listOfQuestionTypes = null;

	private List listOfSuperCategories = null;

	private List listOfCategories = null;

	private List listOfSubCategories = null;

	private List listOfStartTimes = null;

	private List listOfEndTimes = null;

	private List listOfExpireTimes = null;

	private List listOfMsgBrdStartTimes = null;

	private List listOfMsgBrdEndTimes = null;

	private List unFinishedPolls = null;

	/**
	 * @return Returns the pollRepeated.
	 */
	public boolean isPollRepeated() {
		return pollRepeated;
	}

	/**
	 * @param pollRepeated
	 *            The pollRepeated to set.
	 */
	public void setPollRepeated(boolean pollRepeated) {
		this.pollRepeated = pollRepeated;
	}

	/**
	 * @return Returns the repeatedPollId.
	 */
	public String getRepeatedPollId() {
		return repeatedPollId;
	}

	/**
	 * @param repeatedPollId
	 *            The repeatedPollId to set.
	 */
	public void setRepeatedPollId(String repeatedPollId) {
		this.repeatedPollId = repeatedPollId;
	}

	/**
	 * @return Returns the headline1.
	 */
	public String getHeadline1() {
		return headline1;
	}

	/**
	 * @param headline1
	 *            The headline1 to set.
	 */
	public void setHeadline1(String headline1) {
		this.headline1 = headline1;
	}

	/**
	 * @return Returns the headlineSrc2.
	 */
	public String getHeadlineSrc2() {
		return headlineSrc2;
	}

	/**
	 * @param headlineSrc2
	 *            The headlineSrc2 to set.
	 */
	public void setHeadlineSrc2(String headlineSrc2) {
		this.headlineSrc2 = headlineSrc2;
	}

	/**
	 * @return Returns the headline2.
	 */
	public String getHeadline2() {
		return headline2;
	}

	/**
	 * @param headline2
	 *            The headline2 to set.
	 */
	public void setHeadline2(String headline2) {
		this.headline2 = headline2;
	}

	/**
	 * @return Returns the headlineSrc.
	 */
	public String getHeadlineSrc1() {
		return headlineSrc1;
	}

	/**
	 * @param headlineSrc
	 *            The headlineSrc to set.
	 */
	public void setHeadlineSrc1(String headlineSrc1) {
		this.headlineSrc1 = headlineSrc1;
	}

	/**
	 * @return Returns the msgBrdEndTimestamp.
	 */
	public String getMsgBrdEndTimestamp() {
		return msgBrdEndTimestamp;
	}

	/**
	 * @param msgBrdEndTimestamp
	 *            The msgBrdEndTimestamp to set.
	 */
	public void setMsgBrdEndTimestamp(String msgBrdEndTimestamp) {
		this.msgBrdEndTimestamp = msgBrdEndTimestamp;
	}

	/**
	 * @return Returns the msgBrdStartTimestamp.
	 */
	public String getMsgBrdStartTimestamp() {
		return msgBrdStartTimestamp;
	}

	/**
	 * @param msgBrdStartTimestamp
	 *            The msgBrdStartTimestamp to set.
	 */
	public void setMsgBrdStartTimestamp(String msgBrdStartTimestamp) {
		this.msgBrdStartTimestamp = msgBrdStartTimestamp;
	}

	/**
	 * @return Returns the pollAnswer1.
	 */
	public String getPollAnswer1() {
		return pollAnswer1;
	}

	/**
	 * @param pollAnswer1
	 *            The pollAnswer1 to set.
	 */
	public void setPollAnswer1(String pollAnswer1) {
		this.pollAnswer1 = pollAnswer1;
	}

	/**
	 * @return Returns the pollAnswer2.
	 */
	public String getPollAnswer2() {
		return pollAnswer2;
	}

	/**
	 * @param pollAnswer2
	 *            The pollAnswer2 to set.
	 */
	public void setPollAnswer2(String pollAnswer2) {
		this.pollAnswer2 = pollAnswer2;
	}

	/**
	 * @return Returns the pollAnswer3.
	 */
	public String getPollAnswer3() {
		return pollAnswer3;
	}

	/**
	 * @param pollAnswer3
	 *            The pollAnswer3 to set.
	 */
	public void setPollAnswer3(String pollAnswer3) {
		this.pollAnswer3 = pollAnswer3;
	}

	/**
	 * @return Returns the pollAnswer4.
	 */
	public String getPollAnswer4() {
		return pollAnswer4;
	}

	/**
	 * @param pollAnswer4
	 *            The pollAnswer4 to set.
	 */
	public void setPollAnswer4(String pollAnswer4) {
		this.pollAnswer4 = pollAnswer4;
	}

	/**
	 * @return Returns the pollAnswer5.
	 */
	public String getPollAnswer5() {
		return pollAnswer5;
	}

	/**
	 * @param pollAnswer5
	 *            The pollAnswer5 to set.
	 */
	public void setPollAnswer5(String pollAnswer5) {
		this.pollAnswer5 = pollAnswer5;
	}

	/**
	 * @return Returns the pollAnswer6.
	 */
	public String getPollAnswer6() {
		return pollAnswer6;
	}

	/**
	 * @param pollAnswer6
	 *            The pollAnswer6 to set.
	 */
	public void setPollAnswer6(String pollAnswer6) {
		this.pollAnswer6 = pollAnswer6;
	}

	/**
	 * @return Returns the pollDesc.
	 */
	public String getPollDesc() {
		return pollDesc;
	}

	/**
	 * @param pollDesc
	 *            The pollDesc to set.
	 */
	public void setPollDesc(String pollDesc) {
		this.pollDesc = pollDesc;
	}

	/**
	 * @return Returns the pollEndTimestamp.
	 */
	public String getPollEndTimestamp() {
		return pollEndTimestamp;
	}

	/**
	 * @param pollEndTimestamp
	 *            The pollEndTimestamp to set.
	 */
	public void setPollEndTimestamp(String pollEndTimestamp) {
		this.pollEndTimestamp = pollEndTimestamp;
	}

	/**
	 * @return Returns the pollExpireTimestamp.
	 */
	public String getPollExpireTimestamp() {
		return pollExpireTimestamp;
	}

	/**
	 * @param pollExpireTimestamp
	 *            The pollExpireTimestamp to set.
	 */
	public void setPollExpireTimestamp(String pollExpireTimestamp) {
		this.pollExpireTimestamp = pollExpireTimestamp;
	}

	/**
	 * @return Returns the pollName.
	 */
	public String getPollName() {
		return pollName;
	}

	/**
	 * @param pollName
	 *            The pollName to set.
	 */
	public void setPollName(String pollName) {
		this.pollName = pollName;
	}

	/**
	 * @return Returns the currPollId.
	 */
	public String getCurrPollId() {
		return currPollId;
	}

	/**
	 * @param currPollId
	 *            The currPollId to set.
	 */
	public void setCurrPollId(String currPollId) {
		this.currPollId = currPollId;
	}

	/**
	 * @return Returns the answerChoice.
	 */
	public String getAnswerChoice() {
		return answerChoice;
	}

	/**
	 * @param answerChoice
	 *            The answerChoice to set.
	 */
	public void setAnswerChoice(String answerChoice) {
		this.answerChoice = answerChoice;
	}

	/**
	 * @return Returns the keywords.
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords
	 *            The keywords to set.
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return Returns the category1.
	 */
	public String getCategory1() {
		return category1;
	}

	/**
	 * @param category1
	 *            The category1 to set.
	 */
	public void setCategory1(String category1) {
		this.category1 = category1;
	}

	/**
	 * @return Returns the category2.
	 */
	public String getCategory2() {
		return category2;
	}

	/**
	 * @param category2
	 *            The category2 to set.
	 */
	public void setCategory2(String category2) {
		this.category2 = category2;
	}

	/**
	 * @return Returns the category3.
	 */
	public String getCategory3() {
		return category3;
	}

	/**
	 * @param category3
	 *            The category3 to set.
	 */
	public void setCategory3(String category3) {
		this.category3 = category3;
	}

	/**
	 * @return Returns the pollQuestionType.
	 */
	public String getPollQuestionType() {
		return pollQuestionType;
	}

	/**
	 * @param pollQuestionType
	 *            The pollQuestionType to set.
	 */
	public void setPollQuestionType(String pollQuestionType) {
		this.pollQuestionType = pollQuestionType;
	}

	/**
	 * @return Returns the answer1HasPics.
	 */
	public boolean isAnswer1HasPics() {
		return answer1HasPics;
	}

	/**
	 * @param answerHasPics
	 *            The answerHasPics to set.
	 */
	public void setAnswer1HasPics(boolean answer1HasPics) {
		this.answer1HasPics = answer1HasPics;
	}

	/**
	 * @return Returns the answer2HasPics.
	 */
	public boolean isAnswer2HasPics() {
		return answer2HasPics;
	}

	/**
	 * @param answer2HasPics
	 *            The answer2HasPics to set.
	 */
	public void setAnswer2HasPics(boolean answer2HasPics) {
		this.answer2HasPics = answer2HasPics;
	}

	/**
	 * @return Returns the answer3HasPics.
	 */
	public boolean isAnswer3HasPics() {
		return answer3HasPics;
	}

	/**
	 * @param answer3HasPics
	 *            The answer3HasPics to set.
	 */
	public void setAnswer3HasPics(boolean answer3HasPics) {
		this.answer3HasPics = answer3HasPics;
	}

	/**
	 * @return Returns the answer4HasPics.
	 */
	public boolean isAnswer4HasPics() {
		return answer4HasPics;
	}

	/**
	 * @param answer4HasPics
	 *            The answer4HasPics to set.
	 */
	public void setAnswer4HasPics(boolean answer4HasPics) {
		this.answer4HasPics = answer4HasPics;
	}

	/**
	 * @return Returns the answer5HasPics.
	 */
	public boolean isAnswer5HasPics() {
		return answer5HasPics;
	}

	/**
	 * @param answer5HasPics
	 *            The answer5HasPics to set.
	 */
	public void setAnswer5HasPics(boolean answer5HasPics) {
		this.answer5HasPics = answer5HasPics;
	}

	/**
	 * @return Returns the answer6HasPics.
	 */
	public boolean isAnswer6HasPics() {
		return answer6HasPics;
	}

	/**
	 * @param answer6HasPics
	 *            The answer6HasPics to set.
	 */
	public void setAnswer6HasPics(boolean answer6HasPics) {
		this.answer6HasPics = answer6HasPics;
	}

	/**
	 * @return Returns the questionHasPics.
	 */
	public boolean doesQuestionHavePics() {
		return questionHasPics;
	}

	/**
	 * @param questionHasPics
	 *            The questionHasPics to set.
	 */
	public void setQuestionHasPics(boolean questionHasPics) {
		this.questionHasPics = questionHasPics;
	}

	/**
	 * @return Returns the pollQuestion.
	 */
	public String getPollQuestion() {
		return pollQuestion;
	}

	/**
	 * @param pollQuestion
	 *            The pollQuestion to set.
	 */
	public void setPollQuestion(String pollQuestion) {
		this.pollQuestion = pollQuestion;
	}

	/**
	 * @return Returns the currQuestionId.
	 */
	public String getCurrQuestionId() {
		return currQuestionId;
	}

	/**
	 * @param currQuestionId
	 *            The currQuestionId to set.
	 */
	public void setCurrQuestionId(String currQuestionId) {
		this.currQuestionId = currQuestionId;
	}

	/**
	 * @return Returns the pollStartTimestamp.
	 */
	public String getPollStartTimestamp() {
		return pollStartTimestamp;
	}

	/**
	 * @param pollStartTimestamp
	 *            The pollStartTimestamp to set.
	 */
	public void setPollStartTimestamp(String pollStartTimestamp) {
		this.pollStartTimestamp = pollStartTimestamp;
	}

	/**
	 * @return Returns the listOfCategories.
	 * @throws SQLException
	 */
	public List getListOfCategories() throws SQLException {
		listOfCategories = new ArrayList();
		DAOFactory dao = DAOFactory.getDAOFactory();
		ICategoryDAO catDao = dao.getCategoryDAO();
		Iterator itr = catDao.getDistinctCategories().iterator();
		listOfCategories.add(new LabelValueBean("--------", "---------"));
		while (itr.hasNext()) {
			CategoryTO cto = (CategoryTO) itr.next();
			listOfCategories.add(new LabelValueBean(cto.getCategory(), cto
					.getCategory()));
		}
		return listOfCategories;
	}

	/**
	 * @param listOfCategories
	 *            The listOfCategories to set.
	 */
	public void setListOfCategories(List listOfCategories) {
		this.listOfCategories = listOfCategories;
	}

	/**
	 * @return Returns the listOfEndTimes.
	 */
	public List getListOfEndTimes() {
		return listOfEndTimes;
	}

	/**
	 * @param listOfEndTimes
	 *            The listOfEndTimes to set.
	 */
	public void setListOfEndTimes(List listOfEndTimes) {
		this.listOfEndTimes = listOfEndTimes;
	}

	/**
	 * @return Returns the listOfExpireTimes.
	 */
	public List getListOfExpireTimes() {
		return listOfExpireTimes;
	}

	/**
	 * @param listOfExpireTimes
	 *            The listOfExpireTimes to set.
	 */
	public void setListOfExpireTimes(List listOfExpireTimes) {
		this.listOfExpireTimes = listOfExpireTimes;
	}

	/**
	 * @return Returns the listOfMsgBrdEndTimes.
	 */
	public List getListOfMsgBrdEndTimes() {
		return listOfMsgBrdEndTimes;
	}

	/**
	 * @param listOfMsgBrdEndTimes
	 *            The listOfMsgBrdEndTimes to set.
	 */
	public void setListOfMsgBrdEndTimes(List listOfMsgBrdEndTimes) {
		this.listOfMsgBrdEndTimes = listOfMsgBrdEndTimes;
	}

	/**
	 * @return Returns the listOfMsgBrdStartTimes.
	 */
	public List getListOfMsgBrdStartTimes() {
		return listOfMsgBrdStartTimes;
	}

	/**
	 * @param listOfMsgBrdStartTimes
	 *            The listOfMsgBrdStartTimes to set.
	 */
	public void setListOfMsgBrdStartTimes(List listOfMsgBrdStartTimes) {
		this.listOfMsgBrdStartTimes = listOfMsgBrdStartTimes;
	}

	/**
	 * @return Returns the listOfStartTimes.
	 */
	public List getListOfStartTimes() {
		return listOfStartTimes;
	}

	/**
	 * @param listOfStartTimes
	 *            The listOfStartTimes to set.
	 */
	public void setListOfStartTimes(List listOfStartTimes) {
		this.listOfStartTimes = listOfStartTimes;
	}

	/**
	 * @return Returns the listOfSubCategories.
	 * @throws SQLException
	 */
	public List getListOfSubCategories() throws SQLException {
		return listOfSubCategories;
	}

	/**
	 * @param listOfSubCategories
	 *            The listOfSubCategories to set.
	 */
	public void setListOfSubCategories(List listOfSubCategories) {
		this.listOfSubCategories = listOfSubCategories;
	}

	/**
	 * @return Returns the listOfSuperCategories.
	 * @throws SQLException
	 */
	public List getListOfSuperCategories() {
		return listOfSuperCategories;
	}

	/**
	 * @param listOfSuperCategories
	 *            The listOfSuperCategories to set.
	 */
	public void setListOfSuperCategories(List listOfSuperCategories) {
		this.listOfSuperCategories = listOfSuperCategories;
	}

	/**
	 * @return Returns the listOfQuestionTypes.
	 */
	public List getListOfQuestionTypes() {
		return listOfQuestionTypes;
	}

	/**
	 * @param listOfQuestionTypes
	 *            The listOfQuestionTypes to set.
	 */
	public void setListOfQuestionTypes(List listOfQuestionTypes) {
		this.listOfQuestionTypes = listOfQuestionTypes;
	}

	/**
	 * @return Returns the afterSubmitPollChoice.
	 */
	public String getAfterSubmitPollChoice() {
		return afterSubmitPollChoice;
	}

	/**
	 * @param afterSubmitPollChoice
	 *            The afterSubmitPollChoice to set.
	 */
	public void setAfterSubmitPollChoice(String afterSubmitPollChoice) {
		this.afterSubmitPollChoice = afterSubmitPollChoice;
	}

	/**
	 * @return Returns the unFinishedPolls.
	 */
	public List getUnFinishedPolls() {
		return unFinishedPolls;
	}

	/**
	 * @param unFinishedPolls
	 *            The unFinishedPolls to set.
	 */
	public void setUnFinishedPolls(List unFinishedPolls) {
		this.unFinishedPolls = unFinishedPolls;
	}

}