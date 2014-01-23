/*
 * Created on Sep 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.polls;

import java.util.List;

import com.votingcentral.forms.VCBaseFormBean;
import com.votingcentral.pres.web.to.PollWTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ShowPollFormBean extends VCBaseFormBean {

	//use in case of preview mode.
	private boolean showPollAsPreview;

	//used in case poll has not started yet.
	private boolean showPollAsDisabled;

	//used in case preview poll only.
	private boolean showNextAsDisabled;

	private String pollId;

	private PollWTO pwto;

	private String question;

	private String questionId;

	private String answer;

	private String[] answerId;

	private List questionDataObjects;

	private String pollStartTime;

	private String pollEndTime;

	private String pollExpireTime;

	private String pollMsgBoardStartTime;

	private String pollMsgBoardEndTime;

	private List pollComments;

	private String comment;

	private String tafUrl;

	private String pollResultsUrl;

	private List keywordUrls;

	private String pollCreatorUserName;

	private String pollsByUserLink;

	private String extendPollUrl;

	private String editPollUrl;

	/**
	 * @return Returns the answer.
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer
	 *            The answer to set.
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return Returns the answerId.
	 */
	public String[] getAnswerId() {
		return answerId;
	}

	/**
	 * @param answerId
	 *            The answerId to set.
	 */
	public void setAnswerId(String[] answerId) {
		this.answerId = answerId;
	}

	/**
	 * @return Returns the pollEndTime.
	 */
	public String getPollEndTime() {
		return pollEndTime;
	}

	/**
	 * @param pollEndTime
	 *            The pollEndTime to set.
	 */
	public void setPollEndTime(String pollEndTime) {
		this.pollEndTime = pollEndTime;
	}

	/**
	 * @return Returns the pollExpireTime.
	 */
	public String getPollExpireTime() {
		return pollExpireTime;
	}

	/**
	 * @param pollExpireTime
	 *            The pollExpireTime to set.
	 */
	public void setPollExpireTime(String pollExpireTime) {
		this.pollExpireTime = pollExpireTime;
	}

	/**
	 * @return Returns the pollId.
	 */
	public String getPollId() {
		return pollId;
	}

	/**
	 * @param pollId
	 *            The pollId to set.
	 */
	public void setPollId(String pollId) {
		this.pollId = pollId;
	}

	/**
	 * @return Returns the pollMsgBoardEndTime.
	 */
	public String getPollMsgBoardEndTime() {
		return pollMsgBoardEndTime;
	}

	/**
	 * @param pollMsgBoardEndTime
	 *            The pollMsgBoardEndTime to set.
	 */
	public void setPollMsgBoardEndTime(String pollMsgBoardEndTime) {
		this.pollMsgBoardEndTime = pollMsgBoardEndTime;
	}

	/**
	 * @return Returns the pollMsgBoardStartTime.
	 */
	public String getPollMsgBoardStartTime() {
		return pollMsgBoardStartTime;
	}

	/**
	 * @param pollMsgBoardStartTime
	 *            The pollMsgBoardStartTime to set.
	 */
	public void setPollMsgBoardStartTime(String pollMsgBoardStartTime) {
		this.pollMsgBoardStartTime = pollMsgBoardStartTime;
	}

	/**
	 * @return Returns the pollStartTime.
	 */
	public String getPollStartTime() {
		return pollStartTime;
	}

	/**
	 * @param pollStartTime
	 *            The pollStartTime to set.
	 */
	public void setPollStartTime(String pollStartTime) {
		this.pollStartTime = pollStartTime;
	}

	/**
	 * @return Returns the pwto.
	 */
	public PollWTO getPwto() {
		return pwto;
	}

	/**
	 * @param pwto
	 *            The pwto to set.
	 */
	public void setPwto(PollWTO pwto) {
		this.pwto = pwto;
	}

	/**
	 * @return Returns the question.
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            The question to set.
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return Returns the questionDataObjects.
	 */
	public List getQuestionDataObjects() {
		return questionDataObjects;
	}

	/**
	 * @param questionDataObjects
	 *            The questionDataObjects to set.
	 */
	public void setQuestionDataObjects(List questionDataObjects) {
		this.questionDataObjects = questionDataObjects;
	}

	/**
	 * @return Returns the questionId.
	 */
	public String getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId
	 *            The questionId to set.
	 */
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return Returns the showPollAsPreview.
	 */
	public boolean isShowPollAsPreview() {
		return showPollAsPreview;
	}

	/**
	 * @param showPollAsPreview
	 *            The showPollAsPreview to set.
	 */
	public void setShowPollAsPreview(boolean showPollAsPreview) {
		this.showPollAsPreview = showPollAsPreview;
	}

	/**
	 * @return Returns the pollComments.
	 */
	public List getPollComments() {
		return pollComments;
	}

	/**
	 * @param pollComments
	 *            The pollComments to set.
	 */
	public void setPollComments(List pollComments) {
		this.pollComments = pollComments;
	}

	/**
	 * @return Returns the comment.
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            The comment to set.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return Returns the tafUrl.
	 */
	public String getTafUrl() {
		return tafUrl;
	}

	/**
	 * @param tafUrl
	 *            The tafUrl to set.
	 */
	public void setTafUrl(String tafUrl) {
		this.tafUrl = tafUrl;
	}

	/**
	 * @return Returns the keywordUrls.
	 */
	public List getKeywordUrls() {
		return keywordUrls;
	}

	/**
	 * @param keywordUrls
	 *            The keywordUrls to set.
	 */
	public void setKeywordUrls(List keywordUrls) {
		this.keywordUrls = keywordUrls;
	}

	/**
	 * @return Returns the showPollAsDisabled.
	 */
	public boolean isShowPollAsDisabled() {
		return showPollAsDisabled;
	}

	/**
	 * @param showPollAsDisabled
	 *            The showPollAsDisabled to set.
	 */
	public void setShowPollAsDisabled(boolean showPollAsDisabled) {
		this.showPollAsDisabled = showPollAsDisabled;
	}

	/**
	 * @return Returns the pollCreatorUserName.
	 */
	public String getPollCreatorUserName() {
		return pollCreatorUserName;
	}

	/**
	 * @param pollCreatorUserName
	 *            The pollCreatorUserName to set.
	 */
	public void setPollCreatorUserName(String pollCreatorUserName) {
		this.pollCreatorUserName = pollCreatorUserName;
	}

	/**
	 * @return Returns the pollsByUserLink.
	 */
	public String getPollsByUserLink() {
		return pollsByUserLink;
	}

	/**
	 * @param pollsByUserLink
	 *            The pollsByUserLink to set.
	 */
	public void setPollsByUserLink(String pollsByUserLink) {
		this.pollsByUserLink = pollsByUserLink;
	}

	/**
	 * @return Returns the pollResultsUrl.
	 */
	public String getPollResultsUrl() {
		return pollResultsUrl;
	}

	/**
	 * @param pollResultsUrl
	 *            The pollResultsUrl to set.
	 */
	public void setPollResultsUrl(String pollResultsUrl) {
		this.pollResultsUrl = pollResultsUrl;
	}

	/**
	 * @return Returns the showNextAsDisabled.
	 */
	public boolean isShowNextAsDisabled() {
		return showNextAsDisabled;
	}

	/**
	 * @param showNextAsDisabled
	 *            The showNextAsDisabled to set.
	 */
	public void setShowNextAsDisabled(boolean showNextAsDisabled) {
		this.showNextAsDisabled = showNextAsDisabled;
	}

	/**
	 * @return Returns the extendPollUrl.
	 */
	public String getExtendPollUrl() {
		return extendPollUrl;
	}

	/**
	 * @param extendPollUrl
	 *            The extendPollUrl to set.
	 */
	public void setExtendPollUrl(String extendPollUrl) {
		this.extendPollUrl = extendPollUrl;
	}

	/**
	 * @return Returns the editPollUrl.
	 */
	public String getEditPollUrl() {
		return editPollUrl;
	}

	/**
	 * @param editPollUrl
	 *            The editPollUrl to set.
	 */
	public void setEditPollUrl(String editPollUrl) {
		this.editPollUrl = editPollUrl;
	}
}