/*
 * Created on Aug 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.util.ArrayList;
import java.util.List;

import com.votingcentral.model.bo.chart.ChartDataHolder;

/**
 * @author Gandhari
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ChartDataSetTO implements DataTransferObject {

	List chartData;

	String chartTitle;

	String categoryTitle;

	String numberTitle;

	/**
	 * @return Returns the dataSet.
	 */
	public List getChartData() {
		if(chartData==null)
			chartData = new ArrayList();
		return chartData;
	}

	public ChartDataHolder getChartData(String choice){
		if(chartData==null || chartData.isEmpty())
			return null;
		for(int i=0; i<chartData.size(); i++){
			ChartDataHolder data = (ChartDataHolder) chartData.get(i);
			//if(data.getChoice().equalsIgnoreCase(choice))
				return data;
		}
		return null;
	}
	/**
	 * @param dataSet
	 *            The dataSet to set.
	 */
	public void setChartData(ArrayList data) {
		this.chartData = data;
	}

	/**
	 * @param dataSet
	 *            The dataSet to set.
	 */
	public void addGraphData(ChartDataHolder data) {
		if (this.chartData == null)
			this.chartData = new ArrayList();
		this.chartData.add(data);
	}

	/**
	 * @return Returns the categoryTitle.
	 */
	public String getCategoryTitle() {
		return categoryTitle;
	}

	/**
	 * @param categoryTitle
	 *            The categoryTitle to set.
	 */
	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	/**
	 * @return Returns the graphTitle.
	 */
	public String getChartTitle() {
		return chartTitle;
	}

	/**
	 * @param graphTitle
	 *            The graphTitle to set.
	 */
	public void setChartTitle(String title) {
		this.chartTitle = title;
	}

	/**
	 * @return Returns the numberTitle.
	 */
	public String getNumberTitle() {
		return numberTitle;
	}

	/**
	 * @param numberTitle
	 *            The numberTitle to set.
	 */
	public void setNumberTitle(String numberTitle) {
		this.numberTitle = numberTitle;
	}

}