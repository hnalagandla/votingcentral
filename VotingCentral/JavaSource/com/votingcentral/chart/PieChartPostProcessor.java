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
import org.jfree.chart.plot.PiePlot;

import de.laures.cewolf.ChartPostProcessor;

/**
 * @author Gandhari
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PieChartPostProcessor implements ChartPostProcessor { 
	private static ArrayList colors = new ArrayList();

	static{
		colors.add(Color.MAGENTA);
		colors.add(Color.YELLOW);		
		colors.add(Color.RED);
		colors.add(Color.CYAN);		
		colors.add(Color.BLUE);
		colors.add(Color.ORANGE);
		colors.add(Color.GREEN);
	}	

	/** 
	*  
	*/
	public PieChartPostProcessor() { 
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
		PiePlot plot = (PiePlot)chart.getPlot(); 
		//set colors
		for(int i=0; i<colors.size(); i++){
			plot.setSectionPaint(i, (Color) colors.get(i));
		}
	} catch (Exception e) {
		e.printStackTrace(); 
	}
  }
}