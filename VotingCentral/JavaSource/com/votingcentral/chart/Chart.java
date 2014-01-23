/*
 * Created on Sep 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.chart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.votingcentral.model.bo.chart.ChartDataHolder;
import com.votingcentral.model.db.dao.IChartDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.to.ChartDataSetTO;
import com.votingcentral.util.xml.XXMLException;

/**
 * @author Gandhari
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Chart {

	private ChartDataSetTO dataSet = null;
	private String pollId;

	public Chart(String pollId)
	{
		try {
			this.pollId = pollId;
			VCDAOFactory factory = new VCDAOFactory();
			IChartDAO dao = factory.getChartDAO();
			this.dataSet = dao.getChartData(pollId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XXMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public Object getCategoryDataSet() {
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

	/**
	 * 
	 * @return
	 */
	public Object getPieDataSet() {
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

	/**
	 * Return a HashMap of ChartDataSetTO objects.
	 * @return
	 */
	public Object getDistributionDataSet(String distType){
		//Get the data
		VCDAOFactory factory = new VCDAOFactory();
		IChartDAO dao = factory.getChartDAO();
		/*Get the TreeMap of ChartDataSetTO - each 
		 * ChartDataSetTO representing a user choice answer */
		TreeMap map = (TreeMap)dao.getDistribution(this.pollId, distType);
		Set keySet = map.keySet();
		Iterator choicesIter = keySet.iterator();

		List distList = dao.getDistinctDistTypes(this.pollId, distType);
		int distSize = distList.size();

		String[] choices = (String[]) keySet.toArray(new String[keySet.size()]);
		String[] distributions = new String[distSize];
		Long[][] startValues = new Long[choices.length][distributions.length];;
		Long[][] endValues = new Long[choices.length][distributions.length];

		int choice = 0;
		while(choicesIter.hasNext())
		{
			String key = (String)choicesIter.next();
			ChartDataSetTO cDataSet = (ChartDataSetTO) map.get(key);
			ArrayList list = (ArrayList) cDataSet.getChartData();

			for(int i=0; i<distList.size(); i++){
				startValues[choice][i] = new Long(0);				
				String location = (String)distList.get(i);
				ChartDataHolder data = (ChartDataHolder)cDataSet.getChartData(location);				
				
				if(data!=null)
					endValues[choice][i] = new Long(data.getCount());
				else
					endValues[choice][i] = new Long(0);
				distributions[i] = location;
			}
			choice++;
		}

		DefaultIntervalCategoryDataset ds =
			new DefaultIntervalCategoryDataset(choices, distributions, startValues, 
					endValues);
		return ds;	
	}
	
	/**
	 * This is just a test method to show how this works.
	 * @return
	 */
	public Object getDistributionDataSetTest(){
		/*Integer[] keys = {new Integer(1000),new Integer(2000),new Integer(3000),new Integer(4),
				new Integer(5),new Integer(6),new Integer(7),new Integer(8),new Integer(9),
				new Integer(10),new Integer(11),new Integer(12), new Integer(13)};
		*/
		Integer[] keys = {new Integer(0)};
		Integer[] values = {new Integer(20),new Integer(30),new Integer(40),
				new Integer(50),new Integer(60),new Integer(70),new Integer(80),new Integer(90),
				new Integer(100),new Integer(110),new Integer(120), new Integer(130),new Integer(140)};
		
		String[] choices = { "Yes", "No", "Don't Know" };
		String[] categories = { "Qtr1", "Qtr2", "Qtr3", "Qtr4" };
		Integer[][] startValues = new Integer[choices.length][categories.length];
		Integer[][] endValues = new Integer[choices.length][categories.length];

		int v=0;
		for(int series = 0; series < choices.length; series++) {
			for (int i = 0; i < categories.length; i++) {
				startValues[series][i] = new Integer(0);//keys[++v];
				endValues[series][i] = values[++v];
			}
		}		
		
		DefaultIntervalCategoryDataset ds =
			new DefaultIntervalCategoryDataset(choices, categories, startValues, endValues);
		return ds;
		
	}	
	
	protected ChartDataSetTO getChartDataSet()
	{ return this.dataSet; }

	public String getTitle(){
		return getChartDataSet().getChartTitle();
	}
}