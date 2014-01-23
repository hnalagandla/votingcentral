/*
 * Created on Aug 30, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms;

import java.util.List;

import org.apache.struts.action.ActionForm;

import com.votingcentral.env.EnvProps;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCBaseFormBean extends ActionForm {

	private static String keywords = EnvProps
			.getProperty("meta.default.keywords");

	private String homeUrl;

	private String faqUrl;

	private String contactUsUrl;

	private String aboutUsUrl;

	private String logOutUrl;

	private String createPollUrl;

	private String myVCUrl;

	private String featuredPollsUrl;

	private String recEndedPollsUrl;

	private String commaSeparatedKeywords = keywords;

	private String tafHomeUrl;

	private List pollCategoriesUrls;

	private List contestsCategoriesUrls;

	private String errCode;

	/**
	 * @return Returns the aboutUsUrl.
	 */
	public String getAboutUsUrl() {
		return aboutUsUrl;
	}

	/**
	 * @param aboutUsUrl
	 *            The aboutUsUrl to set.
	 */
	public void setAboutUsUrl(String aboutUsUrl) {
		this.aboutUsUrl = aboutUsUrl;
	}

	/**
	 * @return Returns the commaSeparatedKeywords.
	 */
	public String getCommaSeparatedKeywords() {
		return commaSeparatedKeywords;
	}

	/**
	 * @param commaSeparatedKeywords
	 *            The commaSeparatedKeywords to set.
	 */
	public void setCommaSeparatedKeywords(String commaSeparatedKeywords) {
		this.commaSeparatedKeywords = commaSeparatedKeywords;
	}

	/**
	 * @return Returns the contactUsUrl.
	 */
	public String getContactUsUrl() {
		return contactUsUrl;
	}

	/**
	 * @param contactUsUrl
	 *            The contactUsUrl to set.
	 */
	public void setContactUsUrl(String contactUsUrl) {
		this.contactUsUrl = contactUsUrl;
	}

	/**
	 * @return Returns the contestsCategoriesUrls.
	 */
	public List getContestsCategoriesUrls() {
		return contestsCategoriesUrls;
	}

	/**
	 * @param contestsCategoriesUrls
	 *            The contestsCategoriesUrls to set.
	 */
	public void setContestsCategoriesUrls(List contestsCategoriesUrls) {
		this.contestsCategoriesUrls = contestsCategoriesUrls;
	}

	/**
	 * @return Returns the createPollUrl.
	 */
	public String getCreatePollUrl() {
		return createPollUrl;
	}

	/**
	 * @param createPollUrl
	 *            The createPollUrl to set.
	 */
	public void setCreatePollUrl(String createPollUrl) {
		this.createPollUrl = createPollUrl;
	}

	/**
	 * @return Returns the faqUrl.
	 */
	public String getFaqUrl() {
		return faqUrl;
	}

	/**
	 * @param faqUrl
	 *            The faqUrl to set.
	 */
	public void setFaqUrl(String faqUrl) {
		this.faqUrl = faqUrl;
	}

	/**
	 * @return Returns the featuredPollsUrl.
	 */
	public String getFeaturedPollsUrl() {
		return featuredPollsUrl;
	}

	/**
	 * @param featuredPollsUrl
	 *            The featuredPollsUrl to set.
	 */
	public void setFeaturedPollsUrl(String featuredPollsUrl) {
		this.featuredPollsUrl = featuredPollsUrl;
	}

	/**
	 * @return Returns the homeUrl.
	 */
	public String getHomeUrl() {
		return homeUrl;
	}

	/**
	 * @param homeUrl
	 *            The homeUrl to set.
	 */
	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	/**
	 * @return Returns the logOutUrl.
	 */
	public String getLogOutUrl() {
		return logOutUrl;
	}

	/**
	 * @param logOutUrl
	 *            The logOutUrl to set.
	 */
	public void setLogOutUrl(String logOutUrl) {
		this.logOutUrl = logOutUrl;
	}

	/**
	 * @return Returns the myVCUrl.
	 */
	public String getMyVCUrl() {
		return myVCUrl;
	}

	/**
	 * @param myVCUrl
	 *            The myVCUrl to set.
	 */
	public void setMyVCUrl(String myVCUrl) {
		this.myVCUrl = myVCUrl;
	}

	/**
	 * @return Returns the pollCategoriesUrls.
	 */
	public List getPollCategoriesUrls() {
		return pollCategoriesUrls;
	}

	/**
	 * @param pollCategoriesUrls
	 *            The pollCategoriesUrls to set.
	 */
	public void setPollCategoriesUrls(List pollCategoriesUrls) {
		this.pollCategoriesUrls = pollCategoriesUrls;
	}

	/**
	 * @return Returns the recEndedPollsUrl.
	 */
	public String getRecEndedPollsUrl() {
		return recEndedPollsUrl;
	}

	/**
	 * @param recEndedPollsUrl
	 *            The recEndedPollsUrl to set.
	 */
	public void setRecEndedPollsUrl(String recEndedPollsUrl) {
		this.recEndedPollsUrl = recEndedPollsUrl;
	}

	/**
	 * @return Returns the tafHomeUrl.
	 */
	public String getTafHomeUrl() {
		return tafHomeUrl;
	}

	/**
	 * @param tafHomeUrl
	 *            The tafHomeUrl to set.
	 */
	public void setTafHomeUrl(String tafHomeUrl) {
		this.tafHomeUrl = tafHomeUrl;
	}

	/**
	 * @return Returns the errCode.
	 */
	public String getErrCode() {
		return errCode;
	}

	/**
	 * @param errCode
	 *            The errCode to set.
	 */
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
}