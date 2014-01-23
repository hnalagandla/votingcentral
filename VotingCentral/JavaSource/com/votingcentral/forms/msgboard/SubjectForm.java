package com.votingcentral.forms.msgboard;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

public class SubjectForm extends ActionForm {
	protected String pollId;

	protected String subject;

	protected String subjectId;

	protected String message;

	protected String creatorLoginName;

	protected FormFile uploadFile;
	
	protected Boolean imageLoaded;

	private int KB = 1024;

	private int MB = 1024 * 1024;

	private long MAX_FILE_UPLOAD_SIZE = MB;
	
	/**
	 * @return Returns the creatorLoginName.
	 */
	public String getCreatorLoginName() {
		return creatorLoginName;
	}
	/**
	 * @param creatorLoginName The creatorLoginName to set.
	 */
	public void setCreatorLoginName(String creatorLoginName) {
		this.creatorLoginName = creatorLoginName;
	}
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return Returns the pollId.
	 */
	public String getPollId() {
		return pollId;
	}
	/**
	 * @param pollId The pollId to set.
	 */
	public void setPollId(String pollId) {
		this.pollId = pollId;
	}
	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return Returns the subjectId.
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * @param subjectId The subjectId to set.
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * @return Returns the uploadFile.
	 */
	public FormFile getUploadFile() {
		return uploadFile;
	}
	/**
	 * @param uploadFile The uploadFile to set.
	 */
	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		ActionErrors errors = new ActionErrors();
		// Validate the fields in your form, adding
		// adding each error to this.errors as found, e.g.
		String internalAction = request.getParameter("internalAction");
		if (internalAction.equalsIgnoreCase("fileAttach")) {
			//			has the maximum length been exceeded?
			Boolean maxLengthExceeded = (Boolean) request
					.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);

			if ((maxLengthExceeded != null)
					&& (maxLengthExceeded.booleanValue())) {
				errors = new ActionErrors();
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"error.upload.file.size.too.large", "1024"));				
			}			
		}
		return errors;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getName());
		sb.append("\n Poll Id:");
		sb.append(this.getPollId());
		sb.append("\n Subject Id:");
		sb.append(this.getSubjectId());
		sb.append("\n Subject:");
		sb.append(this.getSubject());
		sb.append("\n Message:");
		sb.append(this.getMessage());
		return sb.toString();
	}
	/**
	 * @return Returns the imageId.
	 */
	public Boolean getImageLoaded() {
		return imageLoaded;
	}
	/**
	 * @param imageId The imageId to set.
	 */
	public void setImageLoaded(Boolean imageLoaded) {
		this.imageLoaded = imageLoaded;
	}
}