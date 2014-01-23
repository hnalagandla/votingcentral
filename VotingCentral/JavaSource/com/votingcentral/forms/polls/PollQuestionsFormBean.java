/*
 * Created on Mar 19, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.forms.polls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;

import com.votingcentral.forms.VCBaseFormBean;
import com.votingcentral.model.polls.AnswerChoice;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollQuestionsFormBean extends VCBaseFormBean {

	private final static Log log = LogFactory
			.getLog(PollQuestionsFormBean.class);

	private String pollId;

	private String answerType;

	private String question;

	private String questionId;

	private String questionImageId;

	private FormFile questionImage;

	private String questionDelImageUrl;

	private String[] answer;

	private String[] answerId;

	private String[] answerImageId;

	private String[] answerDelImageUrl;

	private FormFile[] answerImageFiles = new FormFile[12];

	private String answerIdDel;

	private String answerImageDel;

	private List answerTypes;

	/**
	 * @return Returns the answer.
	 */
	public String[] getAnswer() {
		return answer;
	}

	/**
	 * @param answer
	 *            The answer to set.
	 */
	public void setAnswer(String[] answer) {
		this.answer = answer;
	}

	/**
	 * @return Returns the answerId.
	 */
	public String[] getAnswerId() {
		return answerId;
	}

	/**
	 * @param answerId
	 *            The answerId to set.
	 */
	public void setAnswerId(String[] answerId) {
		this.answerId = answerId;
	}

	/**
	 * @return Returns the answerImageId.
	 */
	public String[] getAnswerImageId() {
		return answerImageId;
	}

	/**
	 * @param answerImageId
	 *            The answerImageId to set.
	 */
	public void setAnswerImageId(String[] answerImageId) {
		this.answerImageId = answerImageId;
	}

	/**
	 * @return Returns the answerImageFiles.
	 */
	public Object[] getAnswerImageFiles() {
		return answerImageFiles;
	}

	/**
	 * @param answerImageFiles
	 *            The answerImageFiles to set.
	 */
	public void setAnswerImageFiles(FormFile[] answerImageFile) {
		this.answerImageFiles = answerImageFile;
	}

	public void addAnswerImageFile(int id, FormFile answerImageFile) {
		this.answerImageFiles[id] = answerImageFile;
	}

	/**
	 * @return Returns the answerImageFiles.
	 */
	public FormFile getAnswerImageFile0() {
		if (this.answerImageFiles != null)
			return (FormFile) this.answerImageFiles[0];
		else
			return null;
	}

	/**
	 * @param answerImageFiles
	 *            The answerImageFiles to set.
	 */
	public void setAnswerImageFile0(FormFile answerImageFile) {
		addAnswerImageFile(0, answerImageFile);
	}

	/**
	 * @return Returns the answerImageFile2.
	 */
	public FormFile getAnswerImageFile1() {
		if (this.answerImageFiles != null)
			return (FormFile) this.answerImageFiles[1];
		else
			return null;
	}

	/**
	 * @param answerImageFile1
	 *            The answerImageFile1 to set.
	 */
	public void setAnswerImageFile1(FormFile answerImageFile) {
		addAnswerImageFile(1, answerImageFile);
	}

	/**
	 * @return Returns the answerImageFile2.
	 */
	public FormFile getAnswerImageFile2() {
		if (this.answerImageFiles != null)
			return (FormFile) this.answerImageFiles[2];
		else
			return null;
	}

	/**
	 * @param answerImageFile2
	 *            The answerImageFile2 to set.
	 */
	public void setAnswerImageFile2(FormFile answerImageFile) {
		addAnswerImageFile(2, answerImageFile);
	}

	/**
	 * @return Returns the answerImageFile3.
	 */
	public FormFile getAnswerImageFile3() {
		if (this.answerImageFiles != null)
			return (FormFile) this.answerImageFiles[3];
		else
			return null;
	}

	/**
	 * @param answerImageFile3
	 *            The answerImageFile3 to set.
	 */
	public void setAnswerImageFile3(FormFile answerImageFile) {
		addAnswerImageFile(3, answerImageFile);
	}

	/**
	 * @return Returns the answerImageFile4.
	 */
	public FormFile getAnswerImageFile4() {
		if (this.answerImageFiles != null)
			return (FormFile) this.answerImageFiles[4];
		else
			return null;
	}

	/**
	 * @param answerImageFile4
	 *            The answerImageFile4 to set.
	 */
	public void setAnswerImageFile4(FormFile answerImageFile) {
		addAnswerImageFile(4, answerImageFile);
	}

	/**
	 * @return Returns the answerImageFile5.
	 */
	public FormFile getAnswerImageFile5() {
		if (this.answerImageFiles != null)
			return (FormFile) this.answerImageFiles[5];
		else
			return null;
	}

	/**
	 * @param answerImageFile5
	 *            The answerImageFile5 to set.
	 */
	public void setAnswerImageFile5(FormFile answerImageFile) {
		addAnswerImageFile(5, answerImageFile);
	}

	/**
	 * @return Returns the answerImageFiles.
	 */
	public FormFile getAnswerImageFile6() {
		if (this.answerImageFiles != null)
			return (FormFile) this.answerImageFiles[6];
		else
			return null;
	}

	/**
	 * @param answerImageFiles
	 *            The answerImageFiles to set.
	 */
	public void setAnswerImageFile6(FormFile answerImageFile) {
		addAnswerImageFile(6, answerImageFile);
	}

	/**
	 * @return Returns the answerImageFile7.
	 */
	public FormFile getAnswerImageFile7() {
		if (this.answerImageFiles != null)
			return (FormFile) this.answerImageFiles[7];
		else
			return null;
	}

	/**
	 * @param answerImageFile7
	 *            The answerImageFile7 to set.
	 */
	public void setAnswerImageFile7(FormFile answerImageFile) {
		addAnswerImageFile(7, answerImageFile);
	}

	/**
	 * @return Returns the answerImageFile8.
	 */
	public FormFile getAnswerImageFile8() {
		if (this.answerImageFiles != null)
			return (FormFile) this.answerImageFiles[8];
		else
			return null;
	}

	/**
	 * @param answerImageFile8
	 *            The answerImageFile8 to set.
	 */
	public void setAnswerImageFile8(FormFile answerImageFile) {
		addAnswerImageFile(8, answerImageFile);
	}

	/**
	 * @return Returns the answerImageFile9.
	 */
	public FormFile getAnswerImageFile9() {
		if (this.answerImageFiles != null)
			return (FormFile) this.answerImageFiles[9];
		else
			return null;
	}

	/**
	 * @param answerImageFile9
	 *            The answerImageFile9 to set.
	 */
	public void setAnswerImageFile9(FormFile answerImageFile) {
		addAnswerImageFile(9, answerImageFile);
	}

	/**
	 * @return Returns the answerImageFile10.
	 */
	public FormFile getAnswerImageFile10() {
		if (this.answerImageFiles != null)
			return (FormFile) this.answerImageFiles[10];
		else
			return null;
	}

	/**
	 * @param answerImageFile11
	 *            The answerImageFile11 to set.
	 */
	public void setAnswerImageFile10(FormFile answerImageFile) {
		addAnswerImageFile(10, answerImageFile);
	}

	/**
	 * @return Returns the answerImageFile12.
	 */
	public FormFile getAnswerImageFile11() {
		if (this.answerImageFiles != null)
			return (FormFile) this.answerImageFiles[11];
		else
			return null;
	}

	/**
	 * @param answerImageFile11
	 *            The answerImageFile11 to set.
	 */
	public void setAnswerImageFile11(FormFile answerImageFile) {
		addAnswerImageFile(11, answerImageFile);
	}

	/**
	 * @param answerImageFiles
	 *            The answerImageFiles to set.
	 */
	/*
	 * public void setAnswerImageFiles(ArrayList answerImageFiles) {
	 * this.answerImageFiles = answerImageFiles; }
	 */
	/**
	 * @return Returns the pollId.
	 */
	public String getPollId() {
		return pollId;
	}

	/**
	 * @param pollId
	 *            The pollId to set.
	 */
	public void setPollId(String pollId) {
		this.pollId = pollId;
	}

	/**
	 * @return Returns the question.
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            The question to set.
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return Returns the questionId.
	 */
	public String getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId
	 *            The questionId to set.
	 */
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return Returns the questionImage.
	 */
	public FormFile getQuestionImage() {
		return questionImage;
	}

	/**
	 * @param questionImage
	 *            The questionImage to set.
	 */
	public void setQuestionImage(FormFile questionImage) {
		this.questionImage = questionImage;
	}

	/**
	 * @return Returns the questionImageId.
	 */
	public String getQuestionImageId() {
		return questionImageId;
	}

	/**
	 * @param questionImageId
	 *            The questionImageId to set.
	 */
	public void setQuestionImageId(String questionImageId) {
		this.questionImageId = questionImageId;
	}

	/**
	 * @return Returns the answerType.
	 */
	public String getAnswerType() {
		return answerType;
	}

	/**
	 * @param answerType
	 *            The answerType to set.
	 */
	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}

	/**
	 * @return Returns the answerTypes.
	 */
	public List getAnswerTypes() {
		return answerTypes;
	}

	/**
	 * @param answerTypes
	 *            The answerTypes to set.
	 */
	public void setAnswerTypes(List questionTypes) {
		this.answerTypes = questionTypes;
	}

	public List getAnswerObjects() {
		List answerList = new ArrayList();

		final int size = answer.length;
		for (int i = 0; i < size; i++) {
			PollAnswersSubBean answerChoice = new PollAnswersSubBean();
			answerChoice.setAnswer(answer[i]);
			answerChoice.setAnswerId(answerId[i]);
			answerChoice.setAnswerImageId(answerImageId[i]);
			answerChoice.setDelImgURL(answerDelImageUrl[i]);
			answerChoice.setAnswerImageFile((FormFile) answerImageFiles[i]);
			answerList.add(answerChoice);
		}
		return answerList;
	}

	public void populateForm(String domainContext, List answerChoices) {
		if ((answerChoices == null) || (answerChoices.size() == 0)) {
			return;
		}
		final int size = answerChoices.size();
		answer = new String[size];
		answerId = new String[size];
		answerImageId = new String[size];
		answerDelImageUrl = new String[size];
		answerImageFiles = new FormFile[size];
		int i = 0;
		for (Iterator itr = answerChoices.iterator(); itr.hasNext(); i++) {
			AnswerChoice answerChoice = (AnswerChoice) itr.next();
			answer[i] = answerChoice.getAnswer();
			answerId[i] = answerChoice.getAnswerId();
			answerImageId[i] = answerChoice.getAnswerImageId();
			answerDelImageUrl[i] = VCURLHelper.getDeleteAnswerImageUrl(
					domainContext, pollId, questionId, answerChoice
							.getAnswerId(), answerChoice.getAnswerImageId());
			answerImageFiles[i] = answerChoice.getAnswerImageFile();
		}
	}

	/**
	 * @return Returns the answerDelImageUrl.
	 */
	public String[] getAnswerDelImageUrl() {
		return answerDelImageUrl;
	}

	/**
	 * @param answerDelImageUrl
	 *            The answerDelImageUrl to set.
	 */
	public void setAnswerDelImageUrl(String[] answerDelImageUrl) {
		this.answerDelImageUrl = answerDelImageUrl;
	}

	/**
	 * @return Returns the answerIdDel.
	 */
	public String getAnswerIdDel() {
		return answerIdDel;
	}

	/**
	 * @param answerIdDel
	 *            The answerIdDel to set.
	 */
	public void setAnswerIdDel(String answerIdDel) {
		this.answerIdDel = answerIdDel;
	}

	/**
	 * @return Returns the answerImageDel.
	 */
	public String getAnswerImageDel() {
		return answerImageDel;
	}

	/**
	 * @param answerImageDel
	 *            The answerImageDel to set.
	 */
	public void setAnswerImageDel(String answerImageDel) {
		this.answerImageDel = answerImageDel;
	}

	/**
	 * @return Returns the questionDelImageUrl.
	 */
	public String getQuestionDelImageUrl() {
		return questionDelImageUrl;
	}

	/**
	 * @param questionDelImageUrl
	 *            The questionDelImageUrl to set.
	 */
	public void setQuestionDelImageUrl(String questionDelImageUrl) {
		this.questionDelImageUrl = questionDelImageUrl;
	}
}