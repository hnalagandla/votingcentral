package com.votingcentral.forms.msgboard;

import java.util.Collection;

import org.apache.struts.action.ActionForm;

import com.votingcentral.model.db.dao.to.PollTO;

public class MessageBoardForm extends ActionForm {

	protected String pollId;
	protected PollTO pollTO;
	protected String subject;	
	protected Collection subjects;
	protected String creatorLoginName;
	
	public MessageBoardForm() {
		super();
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPollId() {
		return pollId;
	}

	public void setPollId(String pollId) {
		this.pollId = pollId;
	}

	public String getCreatorLoginName() {
		return creatorLoginName;
	}

	public void setCreatorLoginName(String insertUserName) {
		this.creatorLoginName = insertUserName;
	}

	
	/**
	 * @return Returns the subjects.
	 */
	public Collection getSubjects() {
		return subjects;
	}
	/**
	 * @param subjects The subjects to set.
	 */
	public void setSubjects(Collection subjects) {
		this.subjects = subjects;
	}
	
	
	/**
	 * @return Returns the pollTO.
	 */
	public PollTO getPollsTO() {
		return pollTO;
	}
	/**
	 * @param pollTO The pollTO to set.
	 */
	public void setPollsTO(PollTO pollTO) {
		this.pollTO = pollTO;
	}
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getName());
		sb.append("\n Poll Id:"); sb.append(this.getPollId());
		sb.append("\n Subject:"); sb.append(this.getSubject());
		sb.append("\n Create Login Name:"); sb.append(this.getCreatorLoginName());
		return sb.toString();
	}
}