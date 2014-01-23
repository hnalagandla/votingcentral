/*
 * Created on Jul 29, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.polls;

import java.util.List;

import com.votingcentral.forms.VCBaseFormBean;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ShowPollResultsFormBean extends VCBaseFormBean {
	//current chart type
	private String cct;

	//current chart perspective.
	private String ccp;

	private String size;

	private String defaultPieURL;

	private String defaultBarURL;

	private String chartImageUrl;

	private String bigSmallUrl;

	private String bigSmallUrlDesc;

	private List textDescAndUrls;

	private String pollId;

	private String questionId;

	private String pollName;

	private List pollComments;

	private String comment;

	private String tafUrl;

	//download fileType
	private String dfType;

	private String downloadAsExcelUrl;

	private String downloadAsCSVUrl;

	private String downloadAsTxtUrl;

	private String displayPollResultsUrl;

	/**
	 * @return Returns the ccp.
	 */
	public String getCcp() {
		return ccp;
	}

	/**
	 * @param ccp
	 *            The ccp to set.
	 */
	public void setCcp(String ccp) {
		this.ccp = ccp;
	}

	/**
	 * @return Returns the cct.
	 */
	public String getCct() {
		return cct;
	}

	/**
	 * @param cct
	 *            The cct to set.
	 */
	public void setCct(String cct) {
		this.cct = cct;
	}

	/**
	 * @return Returns the chartImageUrl.
	 */
	public String getChartImageUrl() {
		return chartImageUrl;
	}

	/**
	 * @param chartImageUrl
	 *            The chartImageUrl to set.
	 */
	public void setChartImageUrl(String chartImageUrl) {
		this.chartImageUrl = chartImageUrl;
	}

	/**
	 * @return Returns the textDescAndUrls.
	 */
	public List getTextDescAndUrls() {
		return textDescAndUrls;
	}

	/**
	 * @param textDescAndUrls
	 *            The textDescAndUrls to set.
	 */
	public void setTextDescAndUrls(List textDescAndUrls) {
		this.textDescAndUrls = textDescAndUrls;
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
	 * @return Returns the bigSmallUrl.
	 */
	public String getBigSmallUrl() {
		return bigSmallUrl;
	}

	/**
	 * @param bigSmallUrl
	 *            The bigSmallUrl to set.
	 */
	public void setBigSmallUrl(String bigSmallUrl) {
		this.bigSmallUrl = bigSmallUrl;
	}

	/**
	 * @return Returns the bigSmallUrlDesc.
	 */
	public String getBigSmallUrlDesc() {
		return bigSmallUrlDesc;
	}

	/**
	 * @param bigSmallUrlDesc
	 *            The bigSmallUrlDesc to set.
	 */
	public void setBigSmallUrlDesc(String bigSmallUrlDesc) {
		this.bigSmallUrlDesc = bigSmallUrlDesc;
	}

	/**
	 * @return Returns the defaultBarURL.
	 */
	public String getDefaultBarURL() {
		return defaultBarURL;
	}

	/**
	 * @param defaultBarURL
	 *            The defaultBarURL to set.
	 */
	public void setDefaultBarURL(String defaultBarURL) {
		this.defaultBarURL = defaultBarURL;
	}

	/**
	 * @return Returns the defaultPieURL.
	 */
	public String getDefaultPieURL() {
		return defaultPieURL;
	}

	/**
	 * @param defaultPieURL
	 *            The defaultPieURL to set.
	 */
	public void setDefaultPieURL(String defaultPieURL) {
		this.defaultPieURL = defaultPieURL;
	}

	/**
	 * @return Returns the size.
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size
	 *            The size to set.
	 */
	public void setSize(String size) {
		this.size = size;
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
	 * @return Returns the dfType.
	 */
	public String getDfType() {
		return dfType;
	}

	/**
	 * @param dfType
	 *            The dfType to set.
	 */
	public void setDfType(String dfType) {
		this.dfType = dfType;
	}

	/**
	 * @return Returns the downloadAsCSVUrl.
	 */
	public String getDownloadAsCSVUrl() {
		return downloadAsCSVUrl;
	}

	/**
	 * @param downloadAsCSVUrl
	 *            The downloadAsCSVUrl to set.
	 */
	public void setDownloadAsCSVUrl(String downloadAsCSVUrl) {
		this.downloadAsCSVUrl = downloadAsCSVUrl;
	}

	/**
	 * @return Returns the downloadAsExcelUrl.
	 */
	public String getDownloadAsExcelUrl() {
		return downloadAsExcelUrl;
	}

	/**
	 * @param downloadAsExcelUrl
	 *            The downloadAsExcelUrl to set.
	 */
	public void setDownloadAsExcelUrl(String downloadAsExcelUrl) {
		this.downloadAsExcelUrl = downloadAsExcelUrl;
	}

	/**
	 * @return Returns the downloadAsTxtUrl.
	 */
	public String getDownloadAsTxtUrl() {
		return downloadAsTxtUrl;
	}

	/**
	 * @param downloadAsTxtUrl
	 *            The downloadAsTxtUrl to set.
	 */
	public void setDownloadAsTxtUrl(String downloadAsTxtUrl) {
		this.downloadAsTxtUrl = downloadAsTxtUrl;
	}

	/**
	 * @return Returns the displayPollResultsUrl.
	 */
	public String getDisplayPollResultsUrl() {
		return displayPollResultsUrl;
	}

	/**
	 * @param displayPollResultsUrl
	 *            The displayPollResultsUrl to set.
	 */
	public void setDisplayPollResultsUrl(String displayPollResultsUrl) {
		this.displayPollResultsUrl = displayPollResultsUrl;
	}
}