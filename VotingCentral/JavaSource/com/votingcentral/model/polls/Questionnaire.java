/*
 * Created on Aug 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.votingcentral.model.polls;

import java.util.List;

/**
 * @author harishn
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Questionnaire {
	private List questions;

	/**
	 * @return
	 */
	public List getQuestions() {
		return questions;
	}

	/**
	 * @param list
	 */
	public void setQuestions(List list) {
		questions = list;
	}

	/**
	 * @param int how many questions
	 */
	public int getTotalNumberOfQuestions() {
		return questions.size();
	}

}
