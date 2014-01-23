/*
 * Created on Feb 12, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.chart;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultIntervalCategoryDataset;

import de.laures.cewolf.ChartPostProcessor;

/**
 * @author Gandhari
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DistChartPostProcessor implements ChartPostProcessor { 
	private static ArrayList colors = new ArrayList();

	static{
		colors.add(Color.YELLOW);		
		colors.add(Color.RED);
		colors.add(Color.BLUE);
		colors.add(Color.ORANGE);
		colors.add(Color.GRAY);
		colors.add(Color.GREEN);
	}	

	/** 
	*  
	*/
	public DistChartPostProcessor() { 
		super(); 
	} 
	 
	/* (non-Javadoc) 
	* @see de.laures.cewolf.ChartPostProcessor#processChart(java.lang.Object, java.util.Map) 
	*/ 
	public void processChart(Object arg0, Map arg1) { 
	 
	try { 
		//	 TODO Auto-generated method stub 
		JFreeChart chart = (JFreeChart) arg0;
		if (chart == null) return; 
		CategoryPlot plot = (CategoryPlot)chart.getPlot();
		String pollId = (String)arg1.get("pollId");
		String distType = (String)arg1.get("distributionType");
		
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);
		
		Chart c = new Chart(pollId);
		DefaultIntervalCategoryDataset dataset = (DefaultIntervalCategoryDataset)c.getDistributionDataSet(distType);
		//set colors
		BarRenderer3D renderer = new BarRenderer3D();

		for(int i=0; i<colors.size(); i++){
			renderer.setSeriesPaint(i, (Color) colors.get(i));
		}

		if (plot != null) {
			if (dataset != null){
				plot.setDataset(dataset);
				plot.setRenderer(renderer);
			}
		}
	} catch (Exception e) {
		e.printStackTrace(); 
	}
  }
}