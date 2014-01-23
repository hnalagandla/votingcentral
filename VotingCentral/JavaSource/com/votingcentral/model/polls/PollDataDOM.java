/*
 * Created on Aug 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.polls;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.votingcentral.util.xml.ISaxDomBuilder;
import com.votingcentral.util.xml.IXmlParser;
import com.votingcentral.util.xml.XXMLException;
import com.votingcentral.util.xml.XmlUtil;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollDataDOM {
	private static final String POLL_ID = "PollId";

	private static final String QUESTIONNAIRE = "Questionnaire";

	private static final String QUESTION_DATA = "QuestionData";

	private static final String QUESTION = "Question";

	private static final String QUESTION_ID = "QuestionId";

	private static final String QUESTION_TOTAL_VOTES = "QuestionTotalVotes";

	private static final String QUESTION_TYPE = "QuestionType";

	private static final String QUESTION_IMAGE = "QuestionImage";

	private static final String QUESTION_IMAGE_ID = "QuestionImageId";

	private static final String QUESTION_IMAGE_H = "QuestionImageH";

	private static final String QUESTION_IMAGE_W = "QuestionImageW";

	private static final String QUESTION_IMAGE_COURTESY = "QuestionImageCourtesy";

	private static final String ANSWER_CHOICE = "AnswerChoice";

	private static final String ANSWER = "Answer";

	private static final String ANSWER_ID = "AnswerId";

	private static final String ANSWER_TOTAL_VOTES = "AnswerTotalVotes";

	private static final String ANSWER_IMAGE = "AnswerImage";

	private static final String ANSWER_IMAGE_ID = "AnswerImageId";

	private static final String ANSWER_IMAGE_H = "AnswerImageH";

	private static final String ANSWER_IMAGE_W = "AnswerImageW";

	private static final String ANSWER_IMAGE_COURTESY = "AnswerImageCourtesy";

	private static final String POLL_CATS = "PollCats";

	private static final String CATEGORY = "Category";

	private static final String SUPER_CATEGORY_NAME = "SuperCategoryName";

	private static final String CATEGORY_NAME = "CategoryName";

	private static final String SUB_CATEGORY_NAME = "SubCategoryName";

	private PollData pollData;

	private Node pollDataNode;

	private String pollDataAsXMLString;

	private static IXmlParser pf = XmlUtil.getXmlParserFactory();

	public PollDataDOM(PollData pollData) throws XXMLException, SAXException {
		this.pollData = pollData;
		pollDataNode = create();
		pollDataAsXMLString = getXMLAsString(pollDataNode);
	}

	/**
	 * @return Returns the pollDataNode.
	 */
	public Node getPollDataNode() {
		return pollDataNode;
	}

	/**
	 * @return Returns the pollDataAsXMLString.
	 */
	public String getPollDataAsXMLString() {
		return pollDataAsXMLString;
	}

	private String getXMLAsString(Node node) throws XXMLException {
		return pf.xmlToString(node);
	}

	private Node create() throws XXMLException, SAXException {
		Node n = null;
		try {
			Document doc = pf.createNewDocument();
			Node root = doc.createElement("PollData");
			doc.appendChild(root);
			ISaxDomBuilder dombuilder = pf.getDomBuilderForSaxEvents(root);
			AttributesImpl attrs = new AttributesImpl();

			dombuilder.startElement("", "", POLL_ID, (Attributes) attrs);
			dombuilder.characters(pollData.getPollId().toCharArray(), 0,
					pollData.getPollId().length());
			dombuilder.endElement("", "", POLL_ID);

			Questionnaire questionnaire = pollData.getQuestionnaire();
			int questionsCount = questionnaire.getQuestions().size();
			List questions = questionnaire.getQuestions();

			dombuilder.startElement("", "", QUESTIONNAIRE, (Attributes) attrs);
			//loop thru the questions an construct the Questionnaire node
			for (int i = 0; i < questionsCount; i++) {
				dombuilder.startElement("", "", QUESTION_DATA,
						(Attributes) attrs);
				QuestionData questionData = (QuestionData) questions.get(i);

				dombuilder.startElement("", "", QUESTION, (Attributes) attrs);
				dombuilder.characters(questionData.getQuestion().toCharArray(),
						0, questionData.getQuestion().length());
				dombuilder.endElement("", "", QUESTION);

				dombuilder
						.startElement("", "", QUESTION_ID, (Attributes) attrs);
				dombuilder.characters(questionData.getQuestionId()
						.toCharArray(), 0, questionData.getQuestionId()
						.length());
				dombuilder.endElement("", "", QUESTION_ID);

				dombuilder.startElement("", "", QUESTION_TOTAL_VOTES,
						(Attributes) attrs);
				dombuilder.characters((Long.toString(questionData
						.getQuestionTotalVotes()).toCharArray()), 0, Long
						.toString(questionData.getQuestionTotalVotes())
						.length());
				dombuilder.endElement("", "", QUESTION_TOTAL_VOTES);

				dombuilder.startElement("", "", QUESTION_TYPE,
						(Attributes) attrs);
				dombuilder.characters(questionData.getAnswerType()
						.toCharArray(), 0, questionData.getAnswerType()
						.length());
				dombuilder.endElement("", "", QUESTION_TYPE);

				if (questionData.getQuestionImageId() != null) {
					dombuilder.startElement("", "", QUESTION_IMAGE_ID,
							(Attributes) attrs);
					dombuilder.characters(questionData.getQuestionImageId()
							.toCharArray(), 0, questionData
							.getQuestionImageId().length());
					dombuilder.endElement("", "", QUESTION_IMAGE_ID);

					dombuilder.startElement("", "", QUESTION_IMAGE_H,
							(Attributes) attrs);
					dombuilder.characters(Integer.toString(
							questionData.getImageOriginalH()).toCharArray(), 0, Integer
							.toString(questionData.getImageOriginalH()).length());
					dombuilder.endElement("", "", QUESTION_IMAGE_H);

					dombuilder.startElement("", "", QUESTION_IMAGE_W,
							(Attributes) attrs);
					dombuilder.characters(Integer.toString(
							questionData.getImageOriginalW()).toCharArray(), 0, Integer
							.toString(questionData.getImageOriginalW()).length());
					dombuilder.endElement("", "", QUESTION_IMAGE_W);
				}

				if (questionData.getAnswerChoices() != null) {
					List answerChoices = questionData.getAnswerChoices();
					int answersCount = answerChoices.size();

					for (int j = 0; j < answersCount; j++) {
						AnswerChoice ac = (AnswerChoice) answerChoices.get(j);
						if (ac.getAnswer() == null
								|| ac.getAnswer().length() == 0) {
							continue;
						}
						dombuilder.startElement("", "", ANSWER_CHOICE,
								(Attributes) attrs);
						dombuilder.startElement("", "", ANSWER,
								(Attributes) attrs);
						dombuilder.characters(ac.getAnswer().toCharArray(), 0,
								ac.getAnswer().length());
						dombuilder.endElement("", "", ANSWER);

						dombuilder.startElement("", "", ANSWER_ID,
								(Attributes) attrs);
						dombuilder.characters(ac.getAnswerId().toCharArray(),
								0, ac.getAnswerId().length());
						dombuilder.endElement("", "", ANSWER_ID);

						dombuilder.startElement("", "", ANSWER_TOTAL_VOTES,
								(Attributes) attrs);
						dombuilder.characters(Long.toString(
								ac.getAnswerTotalVotes()).toCharArray(), 0,
								Long.toString(ac.getAnswerTotalVotes())
										.length());
						dombuilder.endElement("", "", ANSWER_TOTAL_VOTES);
						if (ac.getAnswerImageId() != null) {
							dombuilder.startElement("", "", ANSWER_IMAGE_ID,
									(Attributes) attrs);
							dombuilder.characters(ac.getAnswerImageId()
									.toCharArray(), 0, ac.getAnswerImageId()
									.length());
							dombuilder.endElement("", "", ANSWER_IMAGE_ID);

							dombuilder.startElement("", "", ANSWER_IMAGE_H,
									(Attributes) attrs);
							dombuilder.characters(Integer.toString(
									ac.getImageOriginalH()).toCharArray(), 0, Integer
									.toString(ac.getImageOriginalH()).length());
							dombuilder.endElement("", "", ANSWER_IMAGE_H);

							dombuilder.startElement("", "", ANSWER_IMAGE_W,
									(Attributes) attrs);
							dombuilder.characters(Integer.toString(
									ac.getImageOriginalW()).toCharArray(), 0, Integer
									.toString(ac.getImageOriginalW()).length());
							dombuilder.endElement("", "", ANSWER_IMAGE_W);
						}

						dombuilder.endElement("", "", ANSWER_CHOICE);
					}
				}
				dombuilder.endElement("", "", QUESTION_DATA);
			}
			dombuilder.endElement("", "", QUESTIONNAIRE);

			n = root;

		} catch (SAXException e) {

			throw new SAXException(e.getMessage(), e);

		} catch (XXMLException e) {

			throw new XXMLException(e.getMessage(), e);

		}
		return n;
	}

	public Node getPollCatsNode() throws XXMLException {
		Node pollIdNode = (Node) XmlUtil.getXPathApi().selectSingleNode(
				pollDataNode, "/PollData/PollCats");
		return pollIdNode;
	}

	public Node getQuestionnaireNode() throws XXMLException {
		Node pollQuestionnaireNode = (Node) XmlUtil.getXPathApi()
				.selectSingleNode(pollDataNode, "/PollData/Questionnaire");
		return pollQuestionnaireNode;
	}

	public String getQuestionnaireNodeAsString() throws XXMLException {
		Node pollQuestionnaireNode = getQuestionnaireNode();
		String parsedDoc = pf.xmlToString(pollQuestionnaireNode);
		return parsedDoc;
	}
}