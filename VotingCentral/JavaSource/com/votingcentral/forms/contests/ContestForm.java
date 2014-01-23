/*
 * Created on Jan 15, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.contests;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

import com.votingcentral.forms.VCBaseFormBean;
import com.votingcentral.model.bo.contests.ContestsBO;
import com.votingcentral.model.enums.ContestFileStatusEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ContestForm extends VCBaseFormBean {

	private String fileId;

	private String[] fileStatus;

	private List contestFileStatuses;

	protected String creatorLoginName;

	protected FormFile uploadFileName;

	private String contestType;

	private String userComments;

	private List contestTypes;

	private List approvedFileNames;

	private List allUploadedFiles = null;

	private Map currentLiveContests = null;

	private String keywords;

	private String vcHomePageURL;

	private String contestsMainURL;

	private List competeitorEntries;

	private List userEntries;

	// to show the uploaded Image on the
	// success page.
	private String maxImageUrl;

	private String minImageUrl;

	private List otherUploadUrls;

	//these fields are used when a a user votes.
	private String[] answerId;

	private String questionId;

	private String pollId;

	/**
	 * @return Returns the uploadFileName.
	 */
	public FormFile getUploadFileName() {
		return uploadFileName;
	}

	/**
	 * @param uploadFileName
	 *            The uploadFileName to set.
	 */
	public void setUploadFileName(FormFile uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	/**
	 * @return Returns the contestType.
	 */
	public String getContestType() {
		return contestType;
	}

	/**
	 * @param contestType
	 *            The contestType to set.
	 */
	public void setContestType(String contestType) {
		this.contestType = contestType;
	}

	/**
	 * @return Returns the creatorLoginName.
	 */
	public String getCreatorLoginName() {
		return creatorLoginName;
	}

	/**
	 * @param creatorLoginName
	 *            The creatorLoginName to set.
	 */
	public void setCreatorLoginName(String creatorLoginName) {
		this.creatorLoginName = creatorLoginName;
	}

	/**
	 * @param contestTypes
	 *            The contestTypes to set.
	 */
	public void setContestTypes(List contestTypes) {
		this.contestTypes = contestTypes;
	}

	/**
	 * @return Returns the userComments.
	 */
	public String getUserComments() {
		return userComments;
	}

	/**
	 * @param userComments
	 *            The userComments to set.
	 */
	public void setUserComments(String userComments) {
		this.userComments = userComments;
	}

	/**
	 * @return Returns the approvedFileNames.
	 */
	public List getApprovedFileNames() {
		return approvedFileNames;
	}

	/**
	 * @param approvedFileNames
	 *            The approvedFileNames to set.
	 */
	public void setApprovedFileNames(List approvedFileNames) {
		this.approvedFileNames = approvedFileNames;
	}

	/**
	 * @return Returns the allUploadedFiles.
	 */
	public List getAllUploadedFiles() {
		if (allUploadedFiles == null) {
			try {
				allUploadedFiles = ContestsBO.getInstance()
						.getFilesByFileStatus(ContestFileStatusEnum.UPLOADED);
			} catch (SQLException e) {
			} catch (IOException e) {
			}
		}
		return allUploadedFiles;
	}

	/**
	 * @param allUploadedFiles
	 *            The allUploadedFiles to set.
	 */
	public void setAllUploadedFiles(List allUploadedFiles) {
		this.allUploadedFiles = allUploadedFiles;
	}

	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId
	 *            the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return Returns the fileStatus.
	 */
	public String[] getFileStatus() {
		return fileStatus;
	}

	/**
	 * @param fileStatus
	 *            The fileStatus to set.
	 */
	public void setFileStatus(String[] fileStatus) {
		this.fileStatus = fileStatus;
	}

	/**
	 * @return Returns the currentLiveContests.
	 */
	public Map getCurrentLiveContests() {
		return currentLiveContests;
	}

	/**
	 * @param currentLiveContests
	 *            The currentLiveContests to set.
	 */
	public void setCurrentLiveContests(Map currentLiveContests) {
		this.currentLiveContests = currentLiveContests;
	}

	/**
	 * @return Returns the contestFileStatuses.
	 */
	public List getContestFileStatuses() {
		if (contestFileStatuses == null) {
			contestFileStatuses = new ArrayList();
			contestFileStatuses.add(new LabelValueBean(
					ContestFileStatusEnum.APPROVED,
					ContestFileStatusEnum.APPROVED));
			contestFileStatuses.add(new LabelValueBean(
					ContestFileStatusEnum.REJECTED,
					ContestFileStatusEnum.REJECTED));
		}
		return contestFileStatuses;
	}

	/**
	 * @param contestFileStatuses
	 *            The contestFileStatuses to set.
	 */
	public void setContestFileStatuses(List contestFileStatuses) {
		this.contestFileStatuses = contestFileStatuses;
	}

	/**
	 * @return Returns the contestTypes.
	 */
	public List getContestTypes() {
		return contestTypes;
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
	 * @return Returns the vcHomePageURL.
	 */
	public String getVcHomePageURL() {
		return vcHomePageURL;
	}

	/**
	 * @param vcHomePageURL
	 *            The vcHomePageURL to set.
	 */
	public void setVcHomePageURL(String vcHomePageURL) {
		this.vcHomePageURL = vcHomePageURL;
	}

	/**
	 * @return Returns the contestsMainURL.
	 */
	public String getContestsMainURL() {
		return contestsMainURL;
	}

	/**
	 * @param contestsMainURL
	 *            The contestsMainURL to set.
	 */
	public void setContestsMainURL(String contestsMainURL) {
		this.contestsMainURL = contestsMainURL;
	}

	/**
	 * @return the competeitorEntries
	 */
	public List getCompeteitorEntries() {
		return competeitorEntries;
	}

	/**
	 * @param competeitorEntries
	 *            the competeitorEntries to set
	 */
	public void setCompeteitorEntries(List competeitorEntries) {
		this.competeitorEntries = competeitorEntries;
	}

	/**
	 * @return the maxImageUrl
	 */
	public String getMaxImageUrl() {
		return maxImageUrl;
	}

	/**
	 * @param maxImageUrl
	 *            the maxImageUrl to set
	 */
	public void setMaxImageUrl(String maxImageUrl) {
		this.maxImageUrl = maxImageUrl;
	}

	/**
	 * @return the minImageUrl
	 */
	public String getMinImageUrl() {
		return minImageUrl;
	}

	/**
	 * @param minImageUrl
	 *            the minImageUrl to set
	 */
	public void setMinImageUrl(String minImageUrl) {
		this.minImageUrl = minImageUrl;
	}

	/**
	 * @return the otherUploadUrls
	 */
	public List getOtherUploadUrls() {
		return otherUploadUrls;
	}

	/**
	 * @param otherUploadUrls
	 *            the otherUploadUrls to set
	 */
	public void setOtherUploadUrls(List otherUploadUrls) {
		this.otherUploadUrls = otherUploadUrls;
	}

	/**
	 * @return the userEntries
	 */
	public List getUserEntries() {
		return userEntries;
	}

	/**
	 * @param userEntries
	 *            the userEntries to set
	 */
	public void setUserEntries(List userEntries) {
		this.userEntries = userEntries;
	}

	/**
	 * @return the answerId
	 */
	public String[] getAnswerId() {
		return answerId;
	}

	/**
	 * @param answerId
	 *            the answerId to set
	 */
	public void setAnswerId(String[] answerId) {
		this.answerId = answerId;
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
}