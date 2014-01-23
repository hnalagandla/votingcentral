/*
 * Created on Feb 8, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.chart;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.data.category.CategoryDataset;

import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.CategoryItemLinkGenerator;

/** 
 * Data producer.
 *  
 */
public class VCChart implements DatasetProducer, CategoryToolTipGenerator, CategoryItemLinkGenerator, Serializable {
    private String title ="My Chart Titlessss";
    private String xaxisLabel="No of Votes";
    private String yaxisLabel="Answers";
    private String chartType="bar";
    private String distributionType="LOCATION";
    private String pollId;
    private Chart chart;
    private Object obj;

    public VCChart(){
    	System.out.println("VCChart<> ");
    }

	/**
	 *  Produces some random data.
	 */
    public Object produceDataset(Map params) throws DatasetProduceException {
    	if(this.chart==null)
    		this.chart = new Chart(this.pollId);
		if(chartType!=null && chartType.equalsIgnoreCase("bar"))
		{
			obj = chart.getCategoryDataSet();
		}
		else if(chartType!=null && chartType.equalsIgnoreCase("pie"))
		{
			obj = chart.getPieDataSet();
		}
		else if(chartType!=null && chartType.equalsIgnoreCase("distribution"))
		{
			obj = chart.getDistributionDataSet(distributionType);
		}		
		
		return obj;
    }

    public Object getDataSet(){
    	return obj;
    }
    /**
     * This producer's data is invalidated after 5 seconds. By this method the
     * producer can influence Cewolf's caching behaviour the way it wants to.
     */
	public boolean hasExpired(Map params, Date since) {
		return (System.currentTimeMillis() - since.getTime())  > 5000;
	}

	/**
	 * Returns a unique ID for this DatasetProducer
	 */
	public String getProducerId() {
		return "PageViewCountData DatasetProducer";
	}

    /**
     * Returns a link target for a special data item.
     */
    public String generateLink(Object data, int series, Object category) {
        /////return seriesNames[series];
    	return "Test Series Name";
    }
    
	/**
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * @see org.jfree.chart.tooltips.CategoryToolTipGenerator#generateToolTip(CategoryDataset, int, int)
	 */
	public String generateToolTip(CategoryDataset arg0, int series, int arg2) {
		//return seriesNames[series];
		return "Test Series Name Tool Tip";
	}

	
	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		if(this.chart!=null)
			this.title = chart.getTitle();
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return Returns the xaxisLabel.
	 */
	public String getXaxisLabel() {
		return xaxisLabel;
	}
	/**
	 * @param xaxisLabel The xaxisLabel to set.
	 */
	public void setXaxisLabel(String xaxisLabel) {
		this.xaxisLabel = xaxisLabel;
	}
	/**
	 * @return Returns the yaxisLabel.
	 */
	public String getYaxisLabel() {
		return yaxisLabel;
	}
	/**
	 * @param yaxisLabel The yaxisLabel to set.
	 */
	public void setYaxisLabel(String yaxisLabel) {
		this.yaxisLabel = yaxisLabel;
	}
	
	/**
	 * @return Returns the chartType.
	 */
	public String getChartType() {
		return chartType;
	}
	/**
	 * @param chartType The chartType to set.
	 */
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
	/**
	 * @return Returns the pollId.
	 */
	public String getPollId() {
		return pollId;
	}
	/**
	 * @param pollId The pollId to set.
	 */
	public void setPollId(String pollId) {
		this.pollId = pollId;		
		this.chart = new Chart(this.pollId);
	}
	
	/**
	 * @return Returns the distributionType.
	 */
	public String getDistributionType() {
		return distributionType;
	}
	/**
	 * @param distributionType The distributionType to set.
	 */
	public void setDistributionType(String distributionType) {
		this.distributionType = distributionType;
	}
}