/*
 * Created on Sep 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.chart;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.data.general.DefaultPieDataset;

import com.votingcentral.model.bo.chart.ChartDataHolder;

/**
 * @author Gandhari
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PieChart extends Chart {
	private static Log log = LogFactory.getLog(PieChart.class);
	
	public PieChart(String pollId)
	{
		super(pollId);
	}

	/**
	 * 
	 * @return
	 */
	public Object getDataSet() {
		ArrayList list = (ArrayList) getChartDataSet().getChartData();
		if(list == null)
			return null;

		// Create and populate a CategoryDataset
		DefaultPieDataset dataset = new DefaultPieDataset();	
		Iterator iter = list.listIterator();
		long voteCount = 0;
		
		while (iter.hasNext()) {
			ChartDataHolder gd = (ChartDataHolder) iter.next();
			voteCount += gd.getCount();
			//dataset.setValue(gd.getChoice(), new Long(gd.getCount()));
		}
		if(voteCount==0)
			return null;
		else
			return dataset;
	}
}