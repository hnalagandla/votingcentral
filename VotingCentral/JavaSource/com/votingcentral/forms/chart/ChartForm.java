/*
 * Created on Jul 9, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.chart;

import java.util.Date;

import com.votingcentral.forms.VCBaseFormBean;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ChartForm extends VCBaseFormBean {

	//chart type
	private String cct;

	private int width;

	private int height;

	private String chartSize;

	private String pollId;

	private String questionId;

	//chart perspective
	private String ccp;

	private Date startTime;

	private Date endTime;

	private int interval;

	/**
	 * @return Returns the endTime.
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            The endTime to set.
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return Returns the height.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            The height to set.
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return Returns the interval.
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * @param interval
	 *            The interval to set.
	 */
	public void setInterval(int interval) {
		this.interval = interval;
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
	 * @return Returns the startTime.
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            The startTime to set.
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return Returns the width.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            The width to set.
	 */
	public void setWidth(int width) {
		this.width = width;
	}

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
	 * @return Returns the chartSize.
	 */
	public String getChartSize() {
		return chartSize;
	}

	/**
	 * @param chartSize
	 *            The chartSize to set.
	 */
	public void setChartSize(String chartSize) {
		this.chartSize = chartSize;
	}
}