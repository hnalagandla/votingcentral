/*
 * Created on Aug 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.chart;

/**
 * @author Gandhari This class represents data to draw one bar in the bar graph.
 *  
 */
public class ChartDataHolder {
	//The data that shows up on the bars.
	private String descBars;

	//The data that shows up on X-Axis
	private String descXAxis;

	private long count;

	public ChartDataHolder() {
	}

	/**
	 * @return Returns the count.
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param count
	 *            The count to set.
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * @return Returns the descBars.
	 */
	public String getDescBars() {
		return descBars;
	}

	/**
	 * @param descBars
	 *            The descBars to set.
	 */
	public void setDescBars(String descBars) {
		this.descBars = descBars;
	}

	/**
	 * @return Returns the descXAxis.
	 */
	public String getDescXAxis() {
		return descXAxis;
	}

	/**
	 * @param descXAxis
	 *            The descXAxis to set.
	 */
	public void setDescXAxis(String descXAxis) {
		this.descXAxis = descXAxis;
	}
}