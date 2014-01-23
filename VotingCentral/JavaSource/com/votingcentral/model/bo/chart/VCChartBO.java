/*
 * Created on Jul 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.chart;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.block.ColumnArrangement;
import org.jfree.chart.block.LabelBlock;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.db.dao.IPollUserHistoryDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.enums.VCChartPerspectiveEnum;
import com.votingcentral.model.polls.AnswerChoice;
import com.votingcentral.model.polls.PollData;
import com.votingcentral.model.polls.PollTimeHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCChartBO {
	//TODO move this to properties file.
	private static String Y_AXIS_DESC = "Total No. of Votes";

	private static int LEGEND_ANSWER_STUB_LENGTH = 32;

	private static String LINE_TERMINATOR = "...";

	private static int LOCATION_CHART_DISPLAY_CITY_COUNT = 5;

	private static VCChartBO chartBO = null;

	private VCChartBO() {

	}

	public static VCChartBO getInstance() {
		if (chartBO == null) {
			chartBO = new VCChartBO();
		}
		return chartBO;
	}

	public JFreeChart getPieChart(String pollId, String questionId) {
		JFreeChart chart = null;
		try {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			chart = ChartFactory.createPieChart(pto.getPollName(), // chart
					// title
					getSimplePieDataSet(pollId, questionId),// dataset
					false, // include legend
					true, false);

			postProcessPieChart(chart, pto.getPollData()
					.getAnswersByQuestionId(questionId), pto.getPollData()
					.getAnswerToChoiceMap(questionId));
		} catch (SQLException e) {
			return null;
		}
		return chart;
	}

	public JFreeChart getChartByPerspective(VCChartPerspectiveEnum persp,
			String pollId, String questionId) {
		JFreeChart chart = null;
		if (persp == VCChartPerspectiveEnum.DEFAULT
				|| persp == VCChartPerspectiveEnum.SIMPLE_TOTALS) {
			chart = getSimpleTotalsBarChart(pollId, questionId);
		} else if (persp == VCChartPerspectiveEnum.ACX) {
			chart = getAgeBarChart(pollId, questionId);
		} else if (persp == VCChartPerspectiveEnum.GCX) {
			chart = getGenderBarChart(pollId, questionId);
		} else if (persp == VCChartPerspectiveEnum.LCX) {
			chart = getLocationBarChart(pollId, questionId);
		} else if (persp == VCChartPerspectiveEnum.TTX) {
			chart = getTimeSeriesChart(pollId, questionId);
		}
		return chart;
	}

	private JFreeChart getSimpleTotalsBarChart(String pollId, String questionId) {
		JFreeChart chart = null;
		try {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			chart = ChartFactory.createBarChart(pto.getPollName(), // chart
					// title
					"Poll Results", // domain axis label
					Y_AXIS_DESC, // range axis label
					getSimpleTotalsDataSet(pollId, questionId), // data
					PlotOrientation.VERTICAL, // orientation
					false, // include legend
					false, // tooltips?
					false // URLs?
					);
			postProcessBarChart(chart, pto.getPollData()
					.getAnswersByQuestionId(questionId), pto.getPollData()
					.getAnswerToChoiceMap(questionId));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return chart;
	}

	private JFreeChart getGenderBarChart(String pollId, String questionId) {
		JFreeChart chart = null;
		try {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			chart = ChartFactory.createBarChart(pto.getPollName(), // chart
					// title
					"Gender", // domain axis label
					Y_AXIS_DESC, // range axis label
					getGenderDataSet(pollId, questionId), // data
					PlotOrientation.VERTICAL, // orientation
					false, // include legend
					false, // tooltips?
					false // URLs?
					);
			postProcessBarChart(chart, pto.getPollData()
					.getAnswersByQuestionId(questionId), pto.getPollData()
					.getAnswerToChoiceMap(questionId));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return chart;
	}

	private CategoryDataset getGenderDataSet(String pollId, String questionId) {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollUserHistoryDAO pdao = dao.getPollUserHistoryDAO();
		Map results = null;
		try {
			results = pdao.getVotesByPollIdGroupByGender(pollId, questionId);
		} catch (SQLException e) {
			return null;
		}
		return getBarCategoryDataSet(results, null, pollId, questionId,
				VCChartPerspectiveEnum.GCX);
	}

	private JFreeChart getLocationBarChart(String pollId, String questionId) {
		JFreeChart chart = null;
		try {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			chart = ChartFactory.createBarChart(pto.getPollName(), // chart
					// title
					"Answer Choices", // domain axis label
					Y_AXIS_DESC, // range axis label
					getLocationDataSet(pollId, questionId), // data
					PlotOrientation.VERTICAL, // orientation
					false, // include legend
					false, // tooltips?
					false // URLs?
					);
			postProcessBarChart(chart, pto.getPollData()
					.getAnswersByQuestionId(questionId), pto.getPollData()
					.getAnswerToChoiceMap(questionId));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return chart;
	}

	private CategoryDataset getLocationDataSet(String pollId, String questionId) {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollUserHistoryDAO pdao = dao.getPollUserHistoryDAO();
		Map results = null;
		try {
			results = pdao.getVotesByPollIdGroupByLocation(pollId, questionId);
		} catch (SQLException e) {
			return null;
		}
		//get n cities with the top number of votes.
		Map newResults = new TreeMap();
		List cities = new ArrayList();
		getResultsOfCitiesTopVotes(results, newResults, cities);

		return getBarCategoryDataSet(newResults, cities, pollId, questionId,
				VCChartPerspectiveEnum.LCX);
	}

	private void getResultsOfCitiesTopVotes(Map oldResults, Map newResults,
			List cities) {
		Map cityToTotalMap = new TreeMap();
		for (Iterator itr = oldResults.entrySet().iterator(); itr.hasNext();) {
			Map.Entry entry = (Map.Entry) itr.next();
			String key = (String) entry.getKey();
			String[] keys = StringUtils.split((String) key,
					ChartDataGrouper.KEY_SEPARATOR);
			Long value = (Long) entry.getValue();
			String qId = keys[0];
			String desc = keys[1];
			String aId = keys[2];
			Long total = (Long) cityToTotalMap.get(desc);
			if (total != null) {
				long newVal = total.longValue() + value.longValue();
				cityToTotalMap.put(desc, new Long(newVal));
			} else {
				cityToTotalMap.put(desc, new Long(value.longValue()));
			}
		}

		List sortedByTotal = sortMapByValue(cityToTotalMap);

		int size = sortedByTotal.size();
		//get the first n from the list.
		if (size > LOCATION_CHART_DISPLAY_CITY_COUNT) {
			for (int i = size - 1, j = 0; j < LOCATION_CHART_DISPLAY_CITY_COUNT; i--, j++) {
				Map.Entry entry = (Map.Entry) sortedByTotal.get(i);
				cities.add(entry.getKey());
			}
		} else {
			for (int i = size - 1, j = 0; j < size; i--, j++) {
				Map.Entry entry = (Map.Entry) sortedByTotal.get(i);
				cities.add(entry.getKey());
			}
		}

		//now iterate through our result set and eliminate cities
		// we don't want.
		for (Iterator itr = oldResults.entrySet().iterator(); itr.hasNext();) {
			Map.Entry entry = (Map.Entry) itr.next();
			String key = (String) entry.getKey();
			String[] keys = StringUtils.split((String) key,
					ChartDataGrouper.KEY_SEPARATOR);
			Long value = (Long) entry.getValue();
			String qId = keys[0];
			String desc = keys[1];
			String aId = keys[2];
			Long total = (Long) cityToTotalMap.get(desc);
			if (cities.contains(desc)) {
				newResults.put(key, value);
			}
		}
	}

	public ArrayList sortMapByValue(Map map) {
		ArrayList outputList = null;
		int count = 0;
		Set set = null;
		Map.Entry[] entries = null;
		//	 Logic:
		//	 get a set from Map
		//	 Build a Map.Entry[] from set
		//	 Sort the list using Arrays.sort
		//	 Add the sorted Map.Entries into arrayList and return

		set = (Set) map.entrySet();
		Iterator iterator = set.iterator();
		entries = new Map.Entry[set.size()];
		while (iterator.hasNext()) {
			entries[count++] = (Map.Entry) iterator.next();
		}

		//	 Sort the entries with your own comparator for the values:
		Arrays.sort(entries, new Comparator() {
			public int compareTo(Object lhs, Object rhs) {
				Map.Entry le = (Map.Entry) lhs;
				Map.Entry re = (Map.Entry) rhs;
				return ((Comparable) le.getValue()).compareTo((Comparable) re
						.getValue());
			}

			public int compare(Object lhs, Object rhs) {
				Map.Entry le = (Map.Entry) lhs;
				Map.Entry re = (Map.Entry) rhs;
				return ((Comparable) le.getValue()).compareTo((Comparable) re
						.getValue());
			}
		});
		outputList = new ArrayList();
		for (int i = 0; i < entries.length; i++) {
			outputList.add(entries[i]);
		}
		return outputList;
	}

	private JFreeChart getTimeSeriesChart(String pollId, String questionId,
			Date begin, Date end) {
		JFreeChart chart = null;
		try {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			chart = ChartFactory.createTimeSeriesChart(pto.getPollName(),
					"Timeline", Y_AXIS_DESC, getTimeSeriesDataSet(pollId,
							questionId), false, //legend
					false, //tool tips
					false //urls
					);
			Class timePeriodClass = getTimePeriodClass(pto
					.getPollBlockoutPeriodMS());
			postProcessTimeSeriesChart(chart, timePeriodClass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chart;
	}

	private JFreeChart getTimeSeriesChart(String pollId, String questionId) {
		return getTimeSeriesChart(pollId, questionId, null, null);
	}

	private XYDataset getTimeSeriesDataSet(String pollId, String questionId) {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollUserHistoryDAO pdao = dao.getPollUserHistoryDAO();
		Map results = null;
		try {
			results = pdao.getVotesByPollIdGroupByTime(pollId, questionId);
		} catch (SQLException e) {
			return null;
		}
		return getTimeSeriesXYDataset(results, pollId, questionId);
	}

	private XYDataset getTimeSeriesXYDataset(Map results, String pollId,
			String questionId) {
		PollData pollData = null;
		Map aIdToTimeSeriesMap = new HashMap();
		TimeSeriesCollection dataset = new TimeSeriesCollection();

		try {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			Class timePeriodClass = getTimePeriodClass(pto
					.getPollBlockoutPeriodMS());
			pollData = pto.getPollData();
			List answers = pollData.getAnswersByQuestionId(questionId);
			for (Iterator itr = answers.iterator(); itr.hasNext();) {
				AnswerChoice ac = (AnswerChoice) itr.next();
				TimeSeries ts = new TimeSeries(ac.getAnswer(), timePeriodClass);
				aIdToTimeSeriesMap.put(ac.getAnswerId(), ts);
			}
			//no iterate through DB results and start setting the
			//individual totals.
			Iterator keyValuePairs = results.entrySet().iterator();
			for (int i = 0; i < results.size(); i++) {
				Map.Entry entry = (Map.Entry) keyValuePairs.next();
				Object key = entry.getKey();
				String[] keys = StringUtils.split((String) key,
						ChartDataGrouper.KEY_SEPARATOR);
				Long value = (Long) entry.getValue();
				String qId = keys[0];
				String desc = keys[1];
				String aId = keys[2];
				String[] ddmmyy = StringUtils.split(desc, "~");
				TimeSeries ts = (TimeSeries) aIdToTimeSeriesMap.get(aId);
				processTimeSeries(ts, timePeriodClass, ddmmyy, value);
			}

			//now set the over all data into collection.
			Iterator keyValuePairs1 = aIdToTimeSeriesMap.entrySet().iterator();
			for (int i = 0; i < aIdToTimeSeriesMap.size(); i++) {
				Map.Entry entry = (Map.Entry) keyValuePairs1.next();
				String aId = (String) entry.getKey();
				TimeSeries value = (TimeSeries) entry.getValue();
				dataset.addSeries(value);
			}

		} catch (SQLException e) {
			return null;
		}
		return dataset;
	}
	
	/**
	 * This method gives the timeseries based on the timePeriodClass
	 * @param ts
	 * @param timePeriodClass
	 * @param ddmmyy
	 * @param value
	 */
	private void processTimeSeries(TimeSeries ts, Class timePeriodClass, 
			String[] ddmmyy, Long value){
		System.out.println("TimePeriod class >> " + timePeriodClass);
		if(timePeriodClass.equals(Day.class)) {
			ts.add(new Day(Integer.parseInt(ddmmyy[0]), Integer.parseInt(ddmmyy[1]), 
					Integer.parseInt(ddmmyy[2])), value);
		} else if(timePeriodClass.equals(Month.class)) {
			ts.add(new Month(Integer.parseInt(ddmmyy[1]), Integer.parseInt(ddmmyy[2])),
					value);
		} else if(timePeriodClass.equals(Year.class)) {
			ts.add(new Year(Integer.parseInt(ddmmyy[2])), value);
		}
	}
	
	//derive the appropriate time period class based on the block out period.
	//these should match the possible pollblockout periods outlined in
	//PollBO.getListOfBlockOutTimes()
	private Class getTimePeriodClass(long pollBlockoutPeriodMS) {
		Class timePeriod = null;
		if (pollBlockoutPeriodMS == PollTimeHelper.MILLI_SECS_PER_DAY) {
			System.out.println("Day class >> " + PollTimeHelper.MILLI_SECS_PER_DAY);
			timePeriod = Day.class;
		} else if (pollBlockoutPeriodMS <= PollTimeHelper.MILLI_SECS_PER_WEEK) {
			System.out.println("Week class >> " + PollTimeHelper.MILLI_SECS_PER_WEEK);
			timePeriod = Day.class;
		} else if (pollBlockoutPeriodMS >= PollTimeHelper.MILLI_SECS_PER_MONTH
				&& pollBlockoutPeriodMS < 12 * PollTimeHelper.MILLI_SECS_PER_MONTH) {
			System.out.println("Month class >> " + PollTimeHelper.MILLI_SECS_PER_MONTH);
			timePeriod = Month.class;
		} else if (pollBlockoutPeriodMS >= 12 * PollTimeHelper.MILLI_SECS_PER_MONTH) {
			System.out.println("Year class >> " + PollTimeHelper.MILLI_SECS_PER_MONTH);
			timePeriod = Year.class;
		}
		return timePeriod;
	}

	public JFreeChart getDefaultChart(String pollId, String questionId) {
		return getSimpleTotalsBarChart(pollId, questionId);
	}

	private JFreeChart getAgeBarChart(String pollId, String questionId) {
		JFreeChart chart = null;
		try {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			chart = ChartFactory.createBarChart(pto.getPollName(), // chart
					// title
					"Answer Choices", // domain axis label
					Y_AXIS_DESC, // range axis label
					getAgeDataSet(pollId, questionId), // data
					PlotOrientation.VERTICAL, // orientation
					false, // include legend
					false, // tooltips?
					false // URLs?
					);
			postProcessBarChart(chart, pto.getPollData()
					.getAnswersByQuestionId(questionId), pto.getPollData()
					.getAnswerToChoiceMap(questionId));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return chart;
	}

	private CategoryDataset getAgeDataSet(String pollId, String questionId) {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollUserHistoryDAO pdao = dao.getPollUserHistoryDAO();
		Map results = null;
		try {
			results = pdao.getVotesByPollIdGroupByYears(pollId, questionId);
		} catch (SQLException e) {
			return null;
		}
		return getBarCategoryDataSet(results, null, pollId, questionId,
				VCChartPerspectiveEnum.ACX);
	}

	private CategoryDataset getSimpleTotalsDataSet(String pollId,
			String questionId) {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollUserHistoryDAO pdao = dao.getPollUserHistoryDAO();
		Map results = null;
		try {
			results = pdao.getVotesByPollIdQuestionId(pollId, questionId);
		} catch (SQLException e) {
			return null;
		}
		return getBarCategoryDataSet(results, null, pollId, questionId,
				VCChartPerspectiveEnum.SIMPLE_TOTALS);
	}

	private CategoryDataset getBarCategoryDataSet(Map results,
			List derivedData, String pollId, String questionId,
			VCChartPerspectiveEnum persp) {
		PollTO pto;
		try {
			pto = PollBO.getInstance().getPollByPollId(pollId);
			if (pto == null) {
				return null;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
		PollData pollData = pto.getPollData();
		//create the dataset...
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		//initialize the dataset.
		if (persp == VCChartPerspectiveEnum.LCX) {
			initializeLocationDataset(dataset, results, derivedData, pollId,
					questionId, pollData, persp);
		} else {
			initializeBarDataset(dataset, pollId, questionId, pollData, persp);
		}
		//set actual values.
		fillDataSet(dataset, results, pollId, questionId, pollData, persp);

		return dataset;
	}

	private void fillDataSet(DefaultCategoryDataset dataset, Map results,
			String pollId, String questionId, PollData pollData,
			VCChartPerspectiveEnum persp) {
		Map answerToChoiceMap = pollData.getAnswerToChoiceMap(questionId);

		Iterator keyValuePairs = results.entrySet().iterator();
		for (int i = 0; i < results.size(); i++) {
			Map.Entry entry = (Map.Entry) keyValuePairs.next();
			Object key = entry.getKey();
			String[] keys = StringUtils.split((String) key,
					ChartDataGrouper.KEY_SEPARATOR);
			Long value = (Long) entry.getValue();
			String qId = keys[0];
			String desc = keys[1];
			String aId = keys[2];
			String choice = (String) answerToChoiceMap.get(pollData
					.getAnswerByQuestionIdAnswerId(qId, aId));
			if (persp == VCChartPerspectiveEnum.SIMPLE_TOTALS) {
				dataset.addValue(value.longValue(), "Poll Results", choice);
			} else {
				dataset.addValue(value.longValue(), desc, choice);
			}
		}
	}

	private void initializeBarDataset(DefaultCategoryDataset dataset,
			String pollId, String questionId, PollData pollData,
			VCChartPerspectiveEnum persp) {
		List answerChoices = pollData.getAnswersByQuestionId(questionId);
		Map answerToChoiceMap = pollData.getAnswerToChoiceMap(questionId);
		if (persp == VCChartPerspectiveEnum.SIMPLE_TOTALS) {
			int i = 0;
			for (Iterator itr = answerChoices.iterator(); itr.hasNext(); i++) {
				AnswerChoice answerChoice = (AnswerChoice) itr.next();
				dataset.addValue(0, "Poll Results", (String) answerToChoiceMap
						.get(answerChoice.getAnswer()));
			}
		} else if (persp == VCChartPerspectiveEnum.GCX) {
			int i = 0;
			for (Iterator itr = answerChoices.iterator(); itr.hasNext(); i++) {
				AnswerChoice answerChoice = (AnswerChoice) itr.next();
				String choice = (String) answerToChoiceMap.get(answerChoice
						.getAnswer());
				dataset.addValue(0, "M", choice);
				dataset.addValue(0, "F", choice);
			}
		} else if (persp == VCChartPerspectiveEnum.ACX) {
			for (Iterator itr = answerChoices.iterator(); itr.hasNext();) {
				AnswerChoice answerChoice = (AnswerChoice) itr.next();
				String choice = (String) answerToChoiceMap.get(answerChoice
						.getAnswer());
				for (Iterator itr2 = AgeInterpreter.getInstance()
						.getAgeRangeObjs().iterator(); itr2.hasNext();) {
					AgeInterpreter.AgeRange range = (AgeInterpreter.AgeRange) itr2
							.next();
					dataset.addValue(0, range.getRangeKey(), choice);
				}
			}
		}
	}

	private void initializeLocationDataset(DefaultCategoryDataset dataset,
			Map results, List derivedData, String pollId, String questionId,
			PollData pollData, VCChartPerspectiveEnum persp) {
		Map choiceToAnswerMap = pollData.getChoiceToAnswerMap(questionId);
		for (Iterator itr = derivedData.iterator(); itr.hasNext();) {
			String value = (String) itr.next();
			for (Iterator itr1 = choiceToAnswerMap.entrySet().iterator(); itr1
					.hasNext();) {
				Map.Entry entry = (Map.Entry) itr1.next();
				String key = (String) entry.getKey();
				dataset.setValue(0, value, key);
			}
		}
	}

	private void initializePieDataset(DefaultPieDataset dataset, String pollId,
			String questionId, PollData pollData) {
		Map choiceToAnswerMap = pollData.getChoiceToAnswerMap(questionId);
		for (Iterator itr = choiceToAnswerMap.entrySet().iterator(); itr
				.hasNext();) {
			Map.Entry entry = (Map.Entry) itr.next();
			String key = (String) entry.getKey();
			dataset.setValue(key, 0);
		}
	}

	private PieDataset getSimplePieDataSet(String pollId, String questionId) {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollUserHistoryDAO pdao = dao.getPollUserHistoryDAO();
		Map results = null;
		try {
			results = pdao.getVotesByPollIdQuestionId(pollId, questionId);
		} catch (SQLException e) {
			return null;
		}
		return getPieDataSet(results, pollId, questionId);
	}

	private PieDataset getPieDataSet(Map results, String pollId,
			String questionId) {
		PollTO pto;
		try {
			pto = PollBO.getInstance().getPollByPollId(pollId);
			if (pto == null) {
				return null;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
		PollData pollData = null;
		pollData = pto.getPollData();
		Iterator keyValuePairs = results.entrySet().iterator();
		//create the dataset...
		DefaultPieDataset dataset = new DefaultPieDataset();

		initializePieDataset(dataset, pollId, questionId, pollData);

		Map answerToChoiceMap = pollData.getAnswerToChoiceMap(questionId);

		for (int i = 0; i < results.size(); i++) {
			Map.Entry entry = (Map.Entry) keyValuePairs.next();
			Object key = entry.getKey();
			String[] keys = StringUtils.split((String) key,
					ChartDataGrouper.KEY_SEPARATOR);
			Long value = (Long) entry.getValue();
			String qId = keys[0];
			String desc = keys[1];
			String aId = keys[2];
			dataset.setValue((String) answerToChoiceMap.get(pollData
					.getAnswerByQuestionIdAnswerId(qId, aId)), value
					.longValue());
		}
		return dataset;
	}

	private void postProcessPieChart(JFreeChart chart, List answerChoices,
			Map answerToChoicesMap) {

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setNoDataMessage("No data available");
		//plot.setIgnoreNullValues(true);
		//plot.setIgnoreZeroValues(true);

		plot.setSectionOutlinesVisible(false);

		//set the background color for the chart...
		chart.setBackgroundPaint(Color.white);

		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}={1}({2})"));

		TextTitle title = chart.getTitle();
		title.setExpandToFitSpace(true);

		addLegendToChart(chart, answerChoices, answerToChoicesMap);
	}

	private void postProcessBarChart(JFreeChart chart, List answerChoices,
			Map answerToChoicesMap) {
		CategoryPlot plot = chart.getCategoryPlot();

		plot.setNoDataMessage("No data available");

		//set the background color for the chart...
		chart.setBackgroundPaint(Color.white);

		//set the range axis to display integers only...
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		//rangeAxis.setAutoRange(true);
		rangeAxis.setAutoRangeIncludesZero(true);

		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer
				.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setItemLabelsVisible(true);

		TextTitle title = chart.getTitle();
		title.setExpandToFitSpace(true);

		addLegendToChart(chart, answerChoices, answerToChoicesMap);
	}

	private void addLegendToChart(JFreeChart chart, List answerChoices,
			Map answerToChoicesMap) {

		BlockContainer wrapper = new BlockContainer(new BorderArrangement());
		wrapper.setBorder(new BlockBorder(0.25, 0.25, 0.25, 0.25));

		LabelBlock title = new LabelBlock("Legend:", new Font("SansSerif",
				Font.BOLD, 10));
		title.setPadding(5, 5, 5, 5);

		BlockContainer keys = new BlockContainer(new ColumnArrangement());
		keys.setPadding(15, 15, 15, 15);
		keys.add(title);

		int i = 0;
		int size = answerChoices.size();

		for (Iterator itr = answerChoices.iterator(); itr.hasNext(); i++) {
			String answerChoice = ((AnswerChoice) itr.next()).getAnswer();
			String alphabet = (String) answerToChoicesMap.get(answerChoice);
			LabelBlock lb = null;
			//if the length is greater than the minimum stub length,
			//break it up into smaller chunks and then add to the labels.
			if (answerChoice.length() > LEGEND_ANSWER_STUB_LENGTH) {
				lb = new LabelBlock(alphabet + " = "
						+ answerChoice.substring(0, LEGEND_ANSWER_STUB_LENGTH)
						+ LINE_TERMINATOR, new Font("Dialog", Font.PLAIN, 10));
			} else {
				lb = new LabelBlock(alphabet + " = " + answerChoice, new Font(
						"Dialog", Font.PLAIN, 10));
			}
			lb.setPadding(5, 5, 5, 5);
			keys.add(lb);

		}
		wrapper.add(keys, RectangleEdge.TOP);
		LabelBlock subtitle = new LabelBlock(
				"Source: http://www.votingcentral.com");
		subtitle.setPadding(5, 15, 5, 5);
		wrapper.add(subtitle, RectangleEdge.BOTTOM);
		// *** this is important - you need to add the item container to
		// the wrapper, otherwise the legend items won't be displayed when
		// the wrapper is drawn... ***
		LegendTitle legend = new LegendTitle(chart.getPlot());
		BlockContainer items = legend.getItemContainer();
		items.setPadding(2, 10, 5, 2);
		wrapper.add(items);
		legend.setWrapper(wrapper);

		legend.setPosition(RectangleEdge.BOTTOM);
		legend.setHorizontalAlignment(HorizontalAlignment.CENTER);
		legend.setBackgroundPaint(Color.WHITE);
		chart.addSubtitle(legend);
	}

	private void postProcessTimeSeriesChart(JFreeChart chart, Class timePeriodClass) {
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			//renderer.setDefaultShapesVisible(true);
			//renderer.setDefaultShapesFilled(true);
		}

		//set the range axis to display integers only...
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setRange(0, 10);

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		if(timePeriodClass.equals(Day.class)){
			axis.setDateFormatOverride(new SimpleDateFormat("MMM-DD-yyyy"));
		}
		if(timePeriodClass.equals(Month.class)){
			axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		}
		if(timePeriodClass.equals(Year.class)){
			axis.setDateFormatOverride(new SimpleDateFormat("yyyy"));
		}
		
	    plot.getRangeAxis().setLabelAngle(Math.PI / 2);
	    XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
	    renderer.setShapesVisible(true);

	}

	private List getSmallerChunks(String str, int eachSubStrSize) {
		List result = new ArrayList();
		int strLen = str.length();
		int chunkSize = eachSubStrSize;
		int bLength = 0;
		if (str.length() <= eachSubStrSize) {
			result.add(str);
			return result;
		}
		for (int i = 0, j = eachSubStrSize; bLength < strLen; i = j, j = j
				+ chunkSize) {
			String chunk = str.substring(i, j);
			result.add(chunk);
			bLength = bLength + chunkSize;
			if (j + chunkSize > strLen) {
				chunkSize = strLen - j;
			}
		}
		return result;
	}
}