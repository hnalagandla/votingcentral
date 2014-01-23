/*
 * Created on Aug 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.bo.chart.ChartDataHolder;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.db.dao.IChartDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.sql.SQLResources;
import com.votingcentral.model.db.dao.to.ChartDataSetTO;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.polls.AnswerChoice;
import com.votingcentral.model.polls.PollData;
import com.votingcentral.model.polls.QuestionData;
import com.votingcentral.model.polls.Questionnaire;
import com.votingcentral.util.xml.XXMLException;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ChartDAO extends RdbmsDAO implements IChartDAO {
	private static Log log = LogFactory.getLog(ChartDAO.class);

	/**
	 * There can be n number of questions for a given poll.
	 * 
	 * @throws SQLException
	 * @throws XXMLException
	 *  
	 */
	public ChartDataSetTO getChartData(String pollId) throws SQLException,
			XXMLException {

		//load the poll from cache.
		PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
		ChartDataSetTO cDataSet = null;
		cDataSet = new ChartDataSetTO();
		//get the poll Data
		PollData pollData = pto.getPollData();

		Questionnaire questionnaire = pollData.getQuestionnaire();
		List questions = questionnaire.getQuestions();
		for (Iterator iter = questions.iterator(); iter.hasNext();) {
			QuestionData qData = (QuestionData) iter.next();
			List answerChoices = qData.getAnswerChoices();
			for (Iterator iterator = answerChoices.iterator(); iterator
					.hasNext();) {
				AnswerChoice aChoice = (AnswerChoice) iterator.next();
				ChartDataHolder cData = new ChartDataHolder();
				//cData.setChoice(aChoice.getAnswer());
				cData.setCount(aChoice.getAnswerTotalVotes());
				//cData.setRowKey(aChoice.getAnswerId());
				cDataSet.addGraphData(cData);
			}
		}

		// Load more info about the poll including the Graph Title, X index
		// title and Y Index title.
		cDataSet.setChartTitle(pto.getPollName());
		cDataSet.setCategoryTitle("Questions in the Poll");
		cDataSet.setNumberTitle("Number of Votes By Question");
		return cDataSet;
	}

	/**
	 * Given a pollId and a question Id, returns the corresponding dataset.
	 * 
	 * @throws SQLException
	 * @throws XXMLException
	 *  
	 */
	public ChartDataSetTO getChartData(String pollId, String questionId)
			throws SQLException, XXMLException {
		PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
		ChartDataSetTO cDataSet = null;
		cDataSet = new ChartDataSetTO();
		PollData pollData = pto.getPollData();
		ChartDataHolder cData = null;

		QuestionData qData = pollData.getQuestionDataByQuestionId(questionId);
		List answerChoices = qData.getAnswerChoices();
		for (Iterator iterator = answerChoices.iterator(); iterator.hasNext();) {
			AnswerChoice aChoice = (AnswerChoice) iterator.next();
			cData = new ChartDataHolder();
			//cData.setChoice(aChoice.getAnswer());
			cData.setCount(aChoice.getAnswerTotalVotes());
			//cData.setRowKey(aChoice.getAnswerId());
			cDataSet.addGraphData(cData);
		}

		// Load more info about the poll including the Graph Title, X index
		// title and Y Index title.
		cDataSet.setChartTitle(pto.getPollName());
		cDataSet.setCategoryTitle("Questions in the Poll");
		cDataSet.setNumberTitle("Number of Votes By Question");

		return cDataSet;
	}

	/**
	 *  
	 */
	/**
	 * 
	 * @param pollId
	 * @return
	 * @throws SQLException
	 */
	public Map getDistribution(String pollId, String distType) {
		TreeMap dataMap = new TreeMap();
		HashMap ansMap = new HashMap();

		try {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			PollData pollData = pto.getPollData();
			Questionnaire questionnaire = pollData.getQuestionnaire();
			List questions = questionnaire.getQuestions();

			for (Iterator iter = questions.iterator(); iter.hasNext();) {
				QuestionData qData = (QuestionData) iter.next();
				List answerChoices = qData.getAnswerChoices();

				for (Iterator iterator = answerChoices.iterator(); iterator
						.hasNext();) {
					AnswerChoice aChoice = (AnswerChoice) iterator.next();
					dataMap.put(aChoice.getAnswer(), new ChartDataSetTO());
					ansMap.put(aChoice.getAnswerId(), aChoice.getAnswer());
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "";
		if (distType != null && distType.equalsIgnoreCase("LOCATION")) {
			sql = SQLResources.getSQLResource("chart.data.by.location");
		} else if (distType != null && distType.equalsIgnoreCase("GENDER")) {
			sql = SQLResources.getSQLResource("chart.data.by.gender");
		} else if (distType != null && distType.equalsIgnoreCase("AGE")) {
			sql = SQLResources.getSQLResource("chart.data.by.age");
		}

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			pps1.setString(1, pollId);
			rs = pps1.executeQuery();
			while (rs.next()) {
				String answer = (String) ansMap.get(rs.getString("answer_id"));
				ChartDataSetTO dataSet = (ChartDataSetTO) dataMap.get(answer);

				ArrayList list = (ArrayList) dataSet.getChartData();
				String disttype = rs.getString("disttype");
				long votes = rs.getLong("votes");
				//list.add(new ChartDataHolder(disttype, votes));
			}
		} catch (SQLException e) {
			log.error("SQLException", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps1 != null) {
					pps1.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
			}
		}

		return dataMap;
	}

	/**
	 * return unique list of locations from poll_user_locations.
	 * 
	 * @param pollId
	 * @return
	 * @throws SQLException
	 */
	public List getDistinctDistTypes(String pollId, String distType) {

		String sql = "";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		List results = new ArrayList();
		try {
			if (distType != null && distType.equalsIgnoreCase("AGE")) {
				//return ChartDataGrouper.getAgeRanges();
			}
			conn = VCDAOFactory.getConnection();
			if (distType != null && distType.equalsIgnoreCase("LOCATION")) {
				sql = SQLResources.getSQLResource("distinct.voted.locations");
				pps1 = conn.prepareStatement(sql);
				pps1.setString(1, pollId);
			} else if (distType != null && distType.equalsIgnoreCase("GENDER")) {
				sql = SQLResources.getSQLResource("distinct.voted.gender");
				pps1 = conn.prepareStatement(sql);
			}
			rs = pps1.executeQuery();
			while (rs.next()) {
				results.add(rs.getString("disttype"));
			}
		} catch (SQLException e) {
			log.error("SQLException", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps1 != null) {
					pps1.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
			}
		}
		return results;
	}

}