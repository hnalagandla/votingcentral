package com.votingcentral.forms.msgboard;

import java.util.Collection;

import org.apache.struts.action.ActionForm;

import com.votingcentral.model.db.dao.to.PollTO;

public class DiscussBoardForm extends ActionForm {

	protected String subject;
	protected String subjectId;
	protected String imageId="";
	protected String pollId;
	protected PollTO pollTO;
	protected String message;	
	protected Collection messages;
	protected String creatorLoginName;
	
	public DiscussBoardForm() {
		super();
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * @return Returns the imageId.
	 */
	public String getImageId() {
		return imageId;
	}
	/**
	 * @param imageId The imageId to set.
	 */
	public void setImageId(String imageId) {
		if(imageId!=null)
			this.imageId = imageId;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Collection getMessages() {
		return messages;
	}

	public void setMessages(Collection messages) {
		this.messages = messages;
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
		sb.append("\n Subject Id:"); sb.append(this.getSubjectId());
		sb.append("\n Subject:"); sb.append(this.getSubject());
		sb.append("\n Message:"); sb.append(this.getMessage());
		return sb.toString();
	}
}