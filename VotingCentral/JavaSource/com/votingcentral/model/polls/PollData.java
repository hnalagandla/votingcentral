/*
 * Created on Aug 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.votingcentral.model.polls;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.votingcentral.util.xml.XXMLException;
import com.votingcentral.util.xml.XmlUtil;

/**
 * @author harishn
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PollData {

	private String pollId;

	private Questionnaire questionnaire;

	private String pollDataInXMLString;

	private static List S_CHOICES = new ArrayList();

	private static Log log = LogFactory.getLog(PollData.class);

	public PollData() {
		initChoices();
	}

	public PollData(String pollDataInXMLString) throws XXMLException {
		initChoices();
		this.pollDataInXMLString = pollDataInXMLString;
		create();
	}

	public Map getChoiceToAnswerMap(String questionId) {
		//	 map that contains
		//   a = answer1
		//   b = answer2
		//   c = answer3
		Map choiceToAnswerMap = new TreeMap();
		List answerChoices = getAnswersByQuestionId(questionId);
		for (int i = 0; i < answerChoices.size(); i++) {
			AnswerChoice ac = (AnswerChoice) answerChoices.get(i);
			choiceToAnswerMap.put(S_CHOICES.get(i), ac.getAnswer());
		}
		return choiceToAnswerMap;
	}

	public Map getAnswerToChoiceMap(String questionId) {
		//map that contains
		//  answer1 = a
		//  answer2 = b
		//  answer3 = c.
		Map answerToChoiceMap = new TreeMap();
		List answerChoices = getAnswersByQuestionId(questionId);
		for (int i = 0; i < answerChoices.size(); i++) {
			AnswerChoice ac = (AnswerChoice) answerChoices.get(i);
			answerToChoiceMap.put(ac.getAnswer(), S_CHOICES.get(i));
		}
		return answerToChoiceMap;
	}

	public Map getAnswerIdToChoiceMap(String questionId) {
		//map that contains
		//  answerId1 = a
		//  answerId2 = b
		//  answerId3 = c.
		Map answerIdToChoiceMap = new HashMap();
		List answerChoices = getAnswersByQuestionId(questionId);
		for (int i = 0; i < answerChoices.size(); i++) {
			AnswerChoice ac = (AnswerChoice) answerChoices.get(i);
			answerIdToChoiceMap.put(ac.getAnswerId(), S_CHOICES.get(i));
		}
		return answerIdToChoiceMap;
	}

	private void initChoices() {
		S_CHOICES.add("a");
		S_CHOICES.add("b");
		S_CHOICES.add("c");
		S_CHOICES.add("d");
		S_CHOICES.add("e");
		S_CHOICES.add("f");
		S_CHOICES.add("g");
		S_CHOICES.add("h");
		S_CHOICES.add("i");
		S_CHOICES.add("j");
		S_CHOICES.add("k");
		S_CHOICES.add("l");
		S_CHOICES.add("m");
		S_CHOICES.add("n");
		S_CHOICES.add("o");
	}

	private void create() throws XXMLException {
		Node nd = XmlUtil.getXmlParserFactory().parseXml(
				new StringReader(pollDataInXMLString));

		Node pollIdNode = (Node) XmlUtil.getXPathApi().selectSingleNode(
				((Document) nd).getDocumentElement(), "/PollData/PollId");
		String pollId = pollIdNode.getFirstChild().getNodeValue();
		this.setPollId(pollId);
		Questionnaire questionnaire = new Questionnaire();
		List questions = new ArrayList();

		int questionsCount = XmlUtil.getXPathApi().selectNumber(
				((Document) nd).getDocumentElement(),
				"count(/PollData/Questionnaire/QuestionData)").intValue();
		for (int i = 1; i <= questionsCount; i++) {
			QuestionData qData = new QuestionData();

			Node questionNode = (Node) XmlUtil.getXPathApi().selectSingleNode(
					((Document) nd).getDocumentElement(),
					"/PollData/Questionnaire/QuestionData[" + i + "]/Question");
			String quesStr = questionNode.getFirstChild().getNodeValue();
			qData.setQuestion(quesStr);
			Node questionIdNode = (Node) XmlUtil.getXPathApi()
					.selectSingleNode(
							((Document) nd).getDocumentElement(),
							"/PollData/Questionnaire/QuestionData[" + i
									+ "]/QuestionId");
			String quesIdStr = questionIdNode.getFirstChild().getNodeValue();
			qData.setQuestionId(quesIdStr);

			Node questionTypeNode = (Node) XmlUtil.getXPathApi()
					.selectSingleNode(
							((Document) nd).getDocumentElement(),
							"/PollData/Questionnaire/QuestionData[" + i
									+ "]/QuestionType");
			String quesTypeStr = questionTypeNode.getFirstChild()
					.getNodeValue();
			qData.setAnswerType(quesTypeStr);

			Node questionImageIdNode = (Node) XmlUtil.getXPathApi()
					.selectSingleNode(
							((Document) nd).getDocumentElement(),
							"/PollData/Questionnaire/QuestionData[" + i
									+ "]/QuestionImageId");
			if (questionImageIdNode != null
					&& questionImageIdNode.getFirstChild() != null) {
				String questionImageIdStr = questionImageIdNode.getFirstChild()
						.getNodeValue();
				qData.setQuestionImageId(questionImageIdStr);

				Node questionImageH = (Node) XmlUtil.getXPathApi()
						.selectSingleNode(
								((Document) nd).getDocumentElement(),
								"/PollData/Questionnaire/QuestionData[" + i
										+ "]/QuestionImageH");
				Node questionImageW = (Node) XmlUtil.getXPathApi()
						.selectSingleNode(
								((Document) nd).getDocumentElement(),
								"/PollData/Questionnaire/QuestionData[" + i
										+ "]/QuestionImageW");
				if (questionImageH != null && questionImageW != null) {
					int quesImageH = new Integer(questionImageH.getFirstChild()
							.getNodeValue()).intValue();
					int quesImageW = new Integer(questionImageW.getFirstChild()
							.getNodeValue()).intValue();
					if (quesImageH > 0 && quesImageW > 0) {
						qData.setImageOriginalH(quesImageH);
						qData.setImageOriginalW(quesImageW);
					}
				}
			}

			Node questionTotalVotes = (Node) XmlUtil.getXPathApi()
					.selectSingleNode(
							((Document) nd).getDocumentElement(),
							"/PollData/Questionnaire/QuestionData[" + i
									+ "]/QuestionTotalVotes");
			long quesTotalVotes = new Long(questionTotalVotes.getFirstChild()
					.getNodeValue()).longValue();
			qData.setQuestionTotalVotes(quesTotalVotes);

			int answerChoiceCount = XmlUtil.getXPathApi().selectNumber(
					((Document) nd).getDocumentElement(),
					"count (/PollData/Questionnaire/QuestionData[" + i
							+ "]/AnswerChoice)").intValue();

			List answers = new ArrayList();

			for (int j = 1; j <= answerChoiceCount; j++) {
				AnswerChoice ac = new AnswerChoice();

				Node answer = (Node) XmlUtil.getXPathApi().selectSingleNode(
						((Document) nd).getDocumentElement(),
						"/PollData/Questionnaire/QuestionData[" + i
								+ "]/AnswerChoice[" + j + "]/Answer");
				String answerStr = answer.getFirstChild().getNodeValue();
				ac.setAnswer(answerStr);

				Node answerId = (Node) XmlUtil.getXPathApi().selectSingleNode(
						((Document) nd).getDocumentElement(),
						"/PollData/Questionnaire/QuestionData[" + i
								+ "]/AnswerChoice[" + j + "]/AnswerId");
				String answerIdStr = answerId.getFirstChild().getNodeValue();
				ac.setAnswerId(answerIdStr);

				Node answerTotalVotes = (Node) XmlUtil.getXPathApi()
						.selectSingleNode(
								((Document) nd).getDocumentElement(),
								"/PollData/Questionnaire/QuestionData[" + i
										+ "]/AnswerChoice[" + j
										+ "]/AnswerTotalVotes");
				long ansTotalVotes = new Long(answerTotalVotes.getFirstChild()
						.getNodeValue()).longValue();
				ac.setAnswerTotalVotes(ansTotalVotes);

				Node answerImageId = (Node) XmlUtil.getXPathApi()
						.selectSingleNode(
								((Document) nd).getDocumentElement(),
								"/PollData/Questionnaire/QuestionData[" + i
										+ "]/AnswerChoice[" + j
										+ "]/AnswerImageId");
				if (answerImageId != null
						&& answerImageId.getFirstChild() != null) {
					String answerImageIdStr = answerImageId.getFirstChild()
							.getNodeValue();
					ac.setAnswerImageId(answerImageIdStr);

					Node answerImageH = (Node) XmlUtil.getXPathApi()
							.selectSingleNode(
									((Document) nd).getDocumentElement(),
									"/PollData/Questionnaire/QuestionData[" + i
											+ "]/AnswerImageH");
					Node answerImageW = (Node) XmlUtil.getXPathApi()
							.selectSingleNode(
									((Document) nd).getDocumentElement(),
									"/PollData/Questionnaire/QuestionData[" + i
											+ "]/AnswerImageW");
					if (answerImageH != null && answerImageW != null) {
						int ansImageH = new Integer(answerImageH
								.getFirstChild().getNodeValue()).intValue();
						int ansImageW = new Integer(answerImageW
								.getFirstChild().getNodeValue()).intValue();
						if (ansImageH > 0 && ansImageW > 0) {
							ac.setImageOriginalH(ansImageH);
							ac.setImageOriginalW(ansImageW);
						}
					}
				}
				answers.add(ac);
			}
			qData.setAnswerChoices(answers);
			questions.add(qData);
		}
		questionnaire.setQuestions(questions);
		this.setQuestionnaire(questionnaire);
	}

	/**
	 * @return
	 */
	public String getPollId() {
		return pollId;
	}

	/**
	 * @return
	 */
	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	/**
	 * @param string
	 */
	public void setPollId(String string) {
		pollId = string;
	}

	/**
	 * @param questionnaire
	 */
	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public String getQuestionByQuestionId(String questionId) {
		String question = "";
		int totalQuestions = this.getQuestionnaire()
				.getTotalNumberOfQuestions();
		List questions = this.getQuestionnaire().getQuestions();

		//iterate through the questions and see which one is being modified.
		//and change infor for that question
		for (int i = 0; i < totalQuestions; i++) {
			QuestionData questionData = (QuestionData) questions.get(i);
			if (questionData.getQuestionId().equals(questionId)) {
				question = questionData.getQuestion();
				break;
			}
		}
		return question;
	}

	public QuestionData getQuestionDataByQuestionId(String questionId) {
		String question = "";
		int totalQuestions = this.getQuestionnaire()
				.getTotalNumberOfQuestions();
		List questions = this.getQuestionnaire().getQuestions();
		QuestionData qData = null;

		for (int i = 0; i < totalQuestions; i++) {
			qData = (QuestionData) questions.get(i);
			if (qData.getQuestionId().equals(questionId)) {
				break;
			}
		}
		return qData;
	}

	public List getAnswersByQuestionId(String questionId) {
		List answerChoices = new ArrayList();
		List temp = null;
		int totalQuestions = this.getQuestionnaire()
				.getTotalNumberOfQuestions();
		List questions = this.getQuestionnaire().getQuestions();

		//iterate through the questions and see which one is being modified.
		//and change infor for that question
		for (int i = 0; i < totalQuestions; i++) {
			QuestionData questionData = (QuestionData) questions.get(i);
			if (questionData.getQuestionId().equals(questionId)) {
				temp = questionData.getAnswerChoices();
				break;
			}
		}
		if (temp != null) {
			for (int i = 0; i < temp.size(); i++) {
				answerChoices.add(temp.get(i));
			}
		}
		return answerChoices;
	}

	public void setAnswersByQuestionId(String questionId, List answerChoices) {
		List temp = null;
		int totalQuestions = this.getQuestionnaire()
				.getTotalNumberOfQuestions();
		List questions = this.getQuestionnaire().getQuestions();

		//iterate through the questions and see which one is being modified.
		//and change infor for that question
		for (int i = 0; i < totalQuestions; i++) {
			QuestionData questionData = (QuestionData) questions.get(i);
			if (questionData.getQuestionId().equals(questionId)) {
				questionData.setAnswerChoices(answerChoices);
				break;
			}
		}
	}

	public String getQuestionTypeByQuestionId(String questionId) {
		String questionType = "";
		int totalQuestions = this.getQuestionnaire()
				.getTotalNumberOfQuestions();
		List questions = this.getQuestionnaire().getQuestions();

		//iterate through the questions and see which one is being modified.
		//and change infor for that question
		for (int i = 0; i < totalQuestions; i++) {
			QuestionData questionData = (QuestionData) questions.get(i);
			if (questionData.getQuestionId().equals(questionId)) {
				questionType = questionData.getAnswerType();
				break;
			}
		}
		return questionType;
	}

	public int getTotalNumberOfQuestions() {
		return getQuestionnaire().getQuestions().size();
	}

	public QuestionData getFirstQuestion() {
		QuestionData questionData = null;
		int totalQuestions = this.getQuestionnaire()
				.getTotalNumberOfQuestions();
		List questions = this.getQuestionnaire().getQuestions();
		questionData = (QuestionData) questions.get(0);
		return questionData;

	}

	public boolean isQIdAndAidComboValid(String questionId, String[] answerId) {
		for (int i = 0; i < answerId.length; i++) {
			if (getAnswerByQuestionIdAnswerId(questionId, answerId[i]) == null) {
				return false;
			}
		}
		return true;
	}

	public String getAnswerByQuestionIdAnswerId(String questionId,
			String answerId) {
		List choices = getAnswersByQuestionId(questionId);
		for (Iterator itr = choices.iterator(); itr.hasNext();) {
			AnswerChoice ac = (AnswerChoice) itr.next();
			if (ac.getAnswerId().equals(answerId)) {
				return ac.getAnswer();
			}
		}
		return null;
	}

	public AnswerChoice getAnswerChoiceByQuestionIdAnswerId(String questionId,
			String answerId) {
		List choices = getAnswersByQuestionId(questionId);
		for (Iterator itr = choices.iterator(); itr.hasNext();) {
			AnswerChoice ac = (AnswerChoice) itr.next();
			if (ac.getAnswerId().equals(answerId)) {
				return ac;
			}
		}
		return null;
	}

	public synchronized void incrementCount(String questionId, String answerId) {
		QuestionData qData = getQuestionDataByQuestionId(questionId);
		List answerChoices = qData.getAnswerChoices();
		for (Iterator iter = answerChoices.iterator(); iter.hasNext();) {
			AnswerChoice answerChoice = (AnswerChoice) iter.next();
			if (answerId.equals(answerChoice.getAnswerId())) {
				long count = answerChoice.getAnswerTotalVotes();
				count++;
				answerChoice.setAnswerTotalVotes(count);
				break;
			}
		}
		long count = qData.getQuestionTotalVotes();
		count++;
		qData.setQuestionTotalVotes(count);
	}

	public boolean hasPollImages() {
		boolean flag = false;
		List temp = null;
		int totalQuestions = this.getQuestionnaire()
				.getTotalNumberOfQuestions();
		List questions = this.getQuestionnaire().getQuestions();
		for (int i = 0; i < totalQuestions; i++) {
			QuestionData questionData = (QuestionData) questions.get(i);
			if (questionData.getQuestionImageId() != null
					&& questionData.getQuestionImageId().length() > 0) {
				return true;
			}
			List choices = getAnswersByQuestionId(questionData.getQuestionId());
			for (Iterator itr = choices.iterator(); itr.hasNext();) {
				AnswerChoice ac = (AnswerChoice) itr.next();
				if (ac.getAnswerImageId() != null
						&& ac.getAnswerImageId().length() > 0) {
					return true;
				}
			}
		}
		return flag;
	}

	public List getAllPollImagIds() {
		List imageIds = new ArrayList();
		List temp = null;
		int totalQuestions = this.getQuestionnaire()
				.getTotalNumberOfQuestions();
		List questions = this.getQuestionnaire().getQuestions();
		for (int i = 0; i < totalQuestions; i++) {
			QuestionData questionData = (QuestionData) questions.get(i);
			if (questionData.getQuestionImageId() != null
					&& questionData.getQuestionImageId().length() > 0) {
				imageIds.add(questionData.getQuestionImageId());
			}
			List choices = getAnswersByQuestionId(questionData.getQuestionId());
			for (Iterator itr = choices.iterator(); itr.hasNext();) {
				AnswerChoice ac = (AnswerChoice) itr.next();
				if (ac.getAnswerImageId() != null
						&& ac.getAnswerImageId().length() > 0) {
					imageIds.add(ac.getAnswerImageId());
				}
			}
		}
		return imageIds;
	}

	public String getDefaultPollImage() {
		boolean flag = false;
		List temp = null;
		int totalQuestions = this.getQuestionnaire()
				.getTotalNumberOfQuestions();
		List questions = this.getQuestionnaire().getQuestions();
		for (int i = 0; i < totalQuestions; i++) {
			QuestionData questionData = (QuestionData) questions.get(i);
			if (questionData.getQuestionImageId() != null
					&& questionData.getQuestionImageId().length() > 0) {
				return questionData.getQuestionImageId();
			}
			List choices = getAnswersByQuestionId(questionData.getQuestionId());
			for (Iterator itr = choices.iterator(); itr.hasNext();) {
				AnswerChoice ac = (AnswerChoice) itr.next();
				if (ac.getAnswerImageId() != null
						&& ac.getAnswerImageId().length() > 0) {
					return ac.getAnswerImageId();
				}
			}
		}
		return "";
	}

	public static void main(String[] args) {
		String xmlAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<PollData> <PollId>1062f059-c650-afe8-ccc0-1f21ffffffff</PollId> "
				+ "<Questionnaire> "
				+ "<QuestionData> "
				+ "<Question>How is Bush as a President ?</Question> "
				+ "<QuestionId>1062f059-cb40-afe8-ccc0-1f21fffffffe</QuestionId>"
				+ "<QuestionTotalVotes>0</QuestionTotalVotes>"
				+ "<QuestionType>RADIO</QuestionType>" + "<AnswerChoice>"
				+ "<Answer>Great</Answer>"
				+ "<AnswerId>1062f059-d8e0-afe8-ccc0-1f21fffffffd</AnswerId>"
				+ "<AnswerTotalVotes>0</AnswerTotalVotes>" + "</AnswerChoice>"
				+ "<AnswerChoice>" + "<Answer>Sucks</Answer>"
				+ "<AnswerId>1062f059-d8e0-afe8-ccc0-1f21fffffffc</AnswerId>"
				+ "<AnswerTotalVotes>0</AnswerTotalVotes>" + "</AnswerChoice>"
				+ "<AnswerChoice>" + "<Answer>Ok</Answer>"
				+ "<AnswerId>1062f059-d8e0-afe8-ccc0-1f21fffffffb</AnswerId>"
				+ "<AnswerTotalVotes>0</AnswerTotalVotes>" + "</AnswerChoice>"
				+ "</QuestionData>" + "</Questionnaire>" + "<PollCats>"
				+ "<Category>"
				+ "<SuperCategoryName>Business</SuperCategoryName>"
				+ "<CategoryName>Politics</CategoryName>"
				+ "<SubCategoryName>USA</SubCategoryName>" + "</Category>"
				+ "</PollCats>" + "</PollData>";

		try {
			PollData pd = new PollData(xmlAsString);
			String pollId = pd.getPollId();
			System.out.println("PollId is : " + pollId);
		} catch (XXMLException e) {
			e.printStackTrace();
		}
	}
}