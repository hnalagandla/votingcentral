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
import org.jfree.data.category.DefaultCategoryDataset;

import com.votingcentral.model.bo.chart.ChartDataHolder;

/**
 * @author Gandhari
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BarChart extends Chart {

	private static Log log = LogFactory.getLog(BarChart.class);

	public BarChart(String pollId) {
		super(pollId);
	}

	//Bharath deprecating this...
	public Object getDataSet() {
		ArrayList list = (ArrayList) getChartDataSet().getChartData();
		if (list == null)
			return null;
		long voteCount=0;
		//  Create and populate a CategoryDataset
		Iterator iter = list.listIterator();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		while (iter.hasNext()) {
			ChartDataHolder gd = (ChartDataHolder) iter.next();
			voteCount += gd.getCount();
			//dataset.addValue(new Long(gd.getCount()), gd.getChoice(), gd.getChoice());
		}

		if(voteCount==0)
			return null;
		else
			return dataset;
	}

}