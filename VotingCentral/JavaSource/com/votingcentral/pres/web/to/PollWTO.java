/**
 * 
 */
package com.votingcentral.pres.web.to;

import java.util.List;

import com.votingcentral.model.db.dao.to.PollTO;

/**
 * @author harishn
 *  
 */
public class PollWTO extends PollTO {
	private String startTimestampStr;

	private String endTimestampStr;

	private String tafUrl;

	private List kwUrls;

	private String displayPollUrl;

	private String deletePollUrl;

	private boolean showPollAsDisabled;

	private String tabContentId;

	private String tabHeading;

	private String shortName;

	private String creatorUrl;

	private String creatorUserName;

	private String formName;

	private String formId;

	private String defaultImageUrlMax;

	private String defaultImageUrlMin;

	private String defaultImageUrlThumb;

	private String defaultQuestion;

	private String timeAgoStr;

	private String btnPauseNumber;

	private String totalFeaturedPollCount;

	/**
	 * @return the endTimestampStr
	 */
	public String getEndTimestampStr() {
		return endTimestampStr;
	}

	/**
	 * @param endTimestampStr
	 *            the endTimestampStr to set
	 */
	public void setEndTimestampStr(String endTimestampStr) {
		this.endTimestampStr = endTimestampStr;
	}

	/**
	 * @return the kwUrls
	 */
	public List getKwUrls() {
		return kwUrls;
	}

	/**
	 * @param kwUrls
	 *            the kwUrls to set
	 */
	public void setKwUrls(List kwUrls) {
		this.kwUrls = kwUrls;
	}

	/**
	 * @return the startTimestampStr
	 */
	public String getStartTimestampStr() {
		return startTimestampStr;
	}

	/**
	 * @param startTimestampStr
	 *            the startTimestampStr to set
	 */
	public void setStartTimestampStr(String startTimestampStr) {
		this.startTimestampStr = startTimestampStr;
	}

	/**
	 * @return the tafUrl
	 */
	public String getTafUrl() {
		return tafUrl;
	}

	/**
	 * @param tafUrl
	 *            the tafUrl to set
	 */
	public void setTafUrl(String tafUrl) {
		this.tafUrl = tafUrl;
	}

	/**
	 * @return the displayPollUrl
	 */
	public String getDisplayPollUrl() {
		return displayPollUrl;
	}

	/**
	 * @param displayPollUrl
	 *            the displayPollUrl to set
	 */
	public void setDisplayPollUrl(String displayPollUrl) {
		this.displayPollUrl = displayPollUrl;
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
	 * @return the tabContentId
	 */
	public String getTabContentId() {
		return tabContentId;
	}

	/**
	 * @param tabContentId
	 *            the tabContentId to set
	 */
	public void setTabContentId(String tabContentId) {
		this.tabContentId = tabContentId;
	}

	/**
	 * @return Returns the tabHeading.
	 */
	public String getTabHeading() {
		return tabHeading;
	}

	/**
	 * @param tabHeading
	 *            The tabHeading to set.
	 */
	public void setTabHeading(String tabHeading) {
		this.tabHeading = tabHeading;
	}

	/**
	 * @return Returns the shortName.
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName
	 *            The shortName to set.
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return Returns the creatorUrl.
	 */
	public String getCreatorUrl() {
		return creatorUrl;
	}

	/**
	 * @param creatorUrl
	 *            The creatorUrl to set.
	 */
	public void setCreatorUrl(String creatorUrl) {
		this.creatorUrl = creatorUrl;
	}

	/**
	 * @return Returns the creatorUserName.
	 */
	public String getCreatorUserName() {
		return creatorUserName;
	}

	/**
	 * @param creatorUserName
	 *            The creatorUserName to set.
	 */
	public void setCreatorUserName(String creatorUserName) {
		this.creatorUserName = creatorUserName;
	}

	/**
	 * @return the formName
	 */
	public String getFormName() {
		return formName;
	}

	/**
	 * @param formName
	 *            the formName to set
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}

	/**
	 * @return the formId
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * @param formId
	 *            the formId to set
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}

	/**
	 * @return Returns the defaultImageUrlMax.
	 */
	public String getDefaultImageUrlMax() {
		return defaultImageUrlMax;
	}

	/**
	 * @param defaultImageUrlMax
	 *            The defaultImageUrlMax to set.
	 */
	public void setDefaultImageUrlMax(String defaultImageUrlMax) {
		this.defaultImageUrlMax = defaultImageUrlMax;
	}

	/**
	 * @return Returns the defaultImageUrlMin.
	 */
	public String getDefaultImageUrlMin() {
		return defaultImageUrlMin;
	}

	/**
	 * @param defaultImageUrlMin
	 *            The defaultImageUrlMin to set.
	 */
	public void setDefaultImageUrlMin(String defaultImageUrlMin) {
		this.defaultImageUrlMin = defaultImageUrlMin;
	}

	/**
	 * @return Returns the defaultQuestion.
	 */
	public String getDefaultQuestion() {
		return defaultQuestion;
	}

	/**
	 * @param defaultQuestion
	 *            The defaultQuestion to set.
	 */
	public void setDefaultQuestion(String defaultQuestion) {
		this.defaultQuestion = defaultQuestion;
	}

	/**
	 * @return Returns the defaultImageUrlThumb.
	 */
	public String getDefaultImageUrlThumb() {
		return defaultImageUrlThumb;
	}

	/**
	 * @param defaultImageUrlThumb
	 *            The defaultImageUrlThumb to set.
	 */
	public void setDefaultImageUrlThumb(String defaultImageUrlThumb) {
		this.defaultImageUrlThumb = defaultImageUrlThumb;
	}

	/**
	 * @return Returns the timeAgoStr.
	 */
	public String getTimeAgoStr() {
		return timeAgoStr;
	}

	/**
	 * @param timeAgoStr
	 *            The timeAgoStr to set.
	 */
	public void setTimeAgoStr(String timeAgoStr) {
		this.timeAgoStr = timeAgoStr;
	}

	/**
	 * @return Returns the btnPauseNumber.
	 */
	public String getBtnPauseNumber() {
		return btnPauseNumber;
	}

	/**
	 * @param btnPauseNumber
	 *            The btnPauseNumber to set.
	 */
	public void setBtnPauseNumber(String imagePauseNumber) {
		this.btnPauseNumber = imagePauseNumber;
	}

	/**
	 * @return Returns the totalFeaturedPollCount.
	 */
	public String getTotalFeaturedPollCount() {
		return totalFeaturedPollCount;
	}

	/**
	 * @param totalFeaturedPollCount
	 *            The totalFeaturedPollCount to set.
	 */
	public void setTotalFeaturedPollCount(String totalFeaturedPollCount) {
		this.totalFeaturedPollCount = totalFeaturedPollCount;
	}

	/**
	 * @return Returns the deletePollUrl.
	 */
	public String getDeletePollUrl() {
		return deletePollUrl;
	}

	/**
	 * @param deletePollUrl
	 *            The deletePollUrl to set.
	 */
	public void setDeletePollUrl(String deletePollUrl) {
		this.deletePollUrl = deletePollUrl;
	}
}