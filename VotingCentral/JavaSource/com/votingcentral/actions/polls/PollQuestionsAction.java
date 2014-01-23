/*
 * Created on Mar 19, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.polls;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.polls.PollQuestionsFormBean;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.vcfile.VCFileBO;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.enums.AnswerTypeEnum;
import com.votingcentral.model.enums.VCFileTypeEnum;
import com.votingcentral.model.polls.AnswerChoice;
import com.votingcentral.model.polls.PollData;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.model.polls.QuestionData;
import com.votingcentral.model.polls.Questionnaire;
import com.votingcentral.util.guid.GUIDService;
import com.votingcentral.util.pictures.ImageUtils;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollQuestionsAction extends VCDispatchAction {

	private final static Log log = LogFactory.getLog(PollQuestionsAction.class);

	public ActionForward addQuestions(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String next = "addQuestions";
		PollQuestionsFormBean questionsFormBean = (PollQuestionsFormBean) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String pollId = "";
		String questionId = "";

		pollId = questionsFormBean.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		if (pollId == null || pollId.length() == 0) {
			return mapping.findForward("createPollHome");
		}
		questionId = questionsFormBean.getQuestionId();
		questionId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.QUESTION_ID, questionId);

		questionsFormBean.setPollId(pollId);

		PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
		PollData pollData = pto.getPollData();
		List answerChoices = null;
		int maxAnsPerQuestion = VCRequestHelper
				.getMaxAnswersPerQuestion(request);
		//if a question id exists
		if ((questionId != null) && (questionId.length() > 0)) {
			questionsFormBean.setQuestionId(questionId);
			//load data for that question id.
			QuestionData qData = pollData
					.getQuestionDataByQuestionId(questionId);
			fillQuestionInfoIntoForm(request, questionsFormBean, qData);
			//get corresponding answer data.
			answerChoices = qData.getAnswerChoices();
			fillDiffQuestions(answerChoices, maxAnsPerQuestion);
		} else {
			//no question id but poll data exists.
			if (pollData != null) {
				//get the first question
				QuestionData qData = pollData.getFirstQuestion();
				fillQuestionInfoIntoForm(request, questionsFormBean, qData);
				answerChoices = qData.getAnswerChoices();
				fillDiffQuestions(answerChoices, maxAnsPerQuestion);
			} else {
				//no question , no poll data, new
				answerChoices = new ArrayList(maxAnsPerQuestion);
				for (int i = 0; i < maxAnsPerQuestion; i++) {
					answerChoices.add(new AnswerChoice());
				}
			}
		}
		questionsFormBean.populateForm(getDomainAndContext(request),
				answerChoices);
		questionsFormBean.setAnswerTypes(PollHelper.getListOfAnswerTypes());

		VCRequestHelper.setValueIntoRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		VCRequestHelper.setValueIntoRequest(request,
				RequestParameterObjects.QUESTION_ID, questionId);

		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	private void fillDiffQuestions(List answerChoices, int maxAnsPerQuestion) {
		if (answerChoices.size() < maxAnsPerQuestion) {
			int diff = maxAnsPerQuestion - answerChoices.size();
			for (int i = 0; i < diff; i++) {
				answerChoices.add(new AnswerChoice());
			}
		}
	}

	private void fillQuestionInfoIntoForm(HttpServletRequest request,
			PollQuestionsFormBean form, QuestionData qData) {

		try {
			BeanUtils.copyProperties(form, qData);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (qData.getQuestionImageId() != null
				&& qData.getQuestionImageId().length() > 0) {
			form.setQuestionDelImageUrl(VCURLHelper.getDeleteQuestionImageUrl(
					getDomainAndContext(request), form.getPollId(), qData
							.getQuestionId(), qData.getQuestionImageId()));
		}
	}

	public ActionForward deleteQImage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		PollQuestionsFormBean qForm = (PollQuestionsFormBean) form;
		String pollId = qForm.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		if (pollId == null || pollId.length() == 0) {
			return mapping.findForward("createPollHome");
		}
		String questionId = qForm.getQuestionId();
		questionId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.QUESTION_ID, questionId);
		qForm.setPollId(pollId);
		qForm.setQuestionId(questionId);
		long creatorId = UserBO.getInstance().getUserByUserName(
				VCRequestHelper.getUser(request)).getUserId();
		try {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			if (pto.getUserId() == creatorId) {
				PollBO.getInstance().deleteImagePollQuestion(pollId,
						questionId, qForm.getQuestionImageId());
			}
		} catch (Exception e) {
			log.error(e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		//If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = addQuestions(mapping, form, request, response);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	public ActionForward deleteAImage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		PollQuestionsFormBean qForm = (PollQuestionsFormBean) form;
		String pollId = qForm.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		if (pollId == null || pollId.length() == 0) {
			return mapping.findForward("createPollHome");
		}
		String questionId = qForm.getQuestionId();
		questionId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.QUESTION_ID, questionId);
		qForm.setPollId(pollId);
		long creatorId = UserBO.getInstance().getUserByUserName(
				VCRequestHelper.getUser(request)).getUserId();
		try {
			PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
			if (pto.getUserId() == creatorId) {
				PollBO.getInstance().deleteImagePollAnswer(pollId, questionId,
						qForm.getAnswerIdDel(), qForm.getAnswerImageDel());
			}
		} catch (Exception e) {
			log.error("Exception trying to delete image", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("user.login.exception"));
		}
		//If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = addQuestions(mapping, form, request, response);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward upsertQuestion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String next = "pollTimes";
		PollQuestionsFormBean questionsFormBean = (PollQuestionsFormBean) form;
		log.debug("ACTION AnswerImageFiles size:"
				+ questionsFormBean.getAnswerImageFiles().length);
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value

		String pollId = "";
		String questionId = "";
		pollId = questionsFormBean.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		if (pollId == null || pollId.length() == 0) {
			return mapping.findForward("createPollHome");
		}
		questionsFormBean.setPollId(pollId);
		questionId = questionsFormBean.getQuestionId();
		questionId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.QUESTION_ID, questionId);
		questionsFormBean.setQuestionId(questionId);

		validate(questionsFormBean, request, errors);
		if (errors.size() > 0) {
			saveErrors(request, errors);
			return addQuestions(mapping, form, request, response);
		}
		PollTO pto = null;
		long userId = UserBO.getInstance().getUserByUserName(
				VCRequestHelper.getUser(request)).getUserId();

		if ((questionId != null) && (questionId.length() > 0)) {
			pto = PollBO.getInstance().getPollByPollId(pollId);
			PollData pollData = pto.getPollData();

			QuestionData questionData = pollData
					.getQuestionDataByQuestionId(questionId);

			questionData.setQuestion(questionsFormBean.getQuestion());
			questionData.setAnswerType(questionsFormBean.getAnswerType());
			boolean isMultipart = FileUpload.isMultipartContent(request);
			if (isMultipart
					&& questionsFormBean.getQuestionImage() != null
					&& questionsFormBean.getQuestionImage().getFileName()
							.length() > 0) {
				boolean isUpdate = false;
				String qImageId = questionsFormBean.getQuestionImageId();
				if (qImageId != null && qImageId.length() > 0) {
					isUpdate = true;
				} else {
					qImageId = GUIDService.getNextGUID();
					isUpdate = false;
				}
				questionData.setQuestionImageId(qImageId);
				VCFileBO.getInstance().saveVCFile(
						questionsFormBean.getQuestionImage(), qImageId, userId,
						VCFileTypeEnum.POLL, isUpdate);
				questionData.setImageOriginalH(ImageUtils.getInstance()
						.getImageHeight(
								questionsFormBean.getQuestionImage()
										.getFileData()));
				questionData.setImageOriginalW(ImageUtils.getInstance()
						.getImageWidth(
								questionsFormBean.getQuestionImage()
										.getFileData()));
			}
			fillAnswerChoices(questionsFormBean, userId, questionData
					.getAnswerChoices());

			pto.setPollData(pollData);
		} else {
			pto = PollBO.getInstance().getPollByPollId(pollId);

			PollData pollData = new PollData();

			pollData.setPollId(pto.getPollId());

			Questionnaire questionnaire = new Questionnaire();
			ArrayList questions = new ArrayList();
			QuestionData questionData = new QuestionData();
			questionData.setQuestion(questionsFormBean.getQuestion());
			questionData.setAnswerType(questionsFormBean.getAnswerType());
			questionId = GUIDService.getNextGUID();
			questionData.setQuestionId(questionId);
			questionData.setQuestionTotalVotes(0);
			boolean isMultipart = FileUpload.isMultipartContent(request);
			if (isMultipart
					&& questionsFormBean.getQuestionImage() != null
					&& questionsFormBean.getQuestionImage().getFileName() != null
					&& questionsFormBean.getQuestionImage().getFileName()
							.length() > 0) {
				String imageId = GUIDService.getNextGUID();
				questionData.setQuestionImageId(imageId);
				VCFileBO.getInstance().saveVCFile(
						questionsFormBean.getQuestionImage(), imageId, userId,
						VCFileTypeEnum.POLL, false);
			}
			List answerChoices = new ArrayList();
			fillAnswerChoices(questionsFormBean, userId, answerChoices);
			questionData.setAnswerChoices(answerChoices);
			questions.add(questionData);
			questionnaire.setQuestions(questions);
			pollData.setQuestionnaire(questionnaire);
			pto.setPollData(pollData);
		}

		PollBO.getInstance().updatePollByPollId(pto);

		VCRequestHelper.setValueIntoRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		VCRequestHelper.setValueIntoRequest(request,
				RequestParameterObjects.QUESTION_ID, questionId);

		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	private void fillAnswerChoices(PollQuestionsFormBean questionsFormBean,
			long emailAddressId, List answerChoices) throws SQLException,
			IOException {
		//if the voter is expected to enter the answer then no choices will
		//exist.
		if (questionsFormBean.getAnswerType().equalsIgnoreCase("TEXTAREA")) {
			return;
		}
		int size = getAnswersCount(questionsFormBean.getAnswer());
		String[] answer = questionsFormBean.getAnswer();
		String[] answerId = questionsFormBean.getAnswerId();
		String[] answerImageId = questionsFormBean.getAnswerImageId();
		String[] answerImageDel = questionsFormBean.getAnswerDelImageUrl();
		Object[] answerImageFile = questionsFormBean.getAnswerImageFiles();
		//this is to make sure we either have answer choices with data or
		//fill with empty answer choices, so that the get(i) does not thrown
		// exception.
		prefillAnswerChoices(answer, answerChoices);
		for (int i = 0; i < size; i++) {
			if (answer[i] != null && answer[i].length() > 0) {
				AnswerChoice answerChoice = (AnswerChoice) answerChoices.get(i);
				answerChoice.setAnswer(answer[i]);
				if (answerId[i] != null && answerId[i].length() > 0) {
					answerChoice.setAnswerId(answerId[i]);
				} else {
					answerChoice.setAnswerId(GUIDService.getNextGUID());
				}
				if (answerImageFile != null) {
					FormFile answerImageFormFile = (FormFile) answerImageFile[i];
					if (answerImageFormFile != null
							&& answerImageFormFile.getFileName() != null
							&& answerImageFormFile.getFileName().length() > 0) {
						String imageId = answerImageId[i];
						boolean isUpdate = false;
						if (imageId != null && imageId.length() > 0) {
							isUpdate = true;
						} else {
							imageId = GUIDService.getNextGUID();
							isUpdate = false;
						}
						answerChoice.setAnswerImageId(imageId);
						FormFile image = (FormFile) answerImageFile[i];
						VCFileBO.getInstance().saveVCFile(image, imageId,
								emailAddressId, VCFileTypeEnum.POLL, isUpdate);
						answerChoice.setImageOriginalH(ImageUtils.getInstance()
								.getImageHeight(image.getFileData()));
						answerChoice.setImageOriginalW(ImageUtils.getInstance()
								.getImageWidth(image.getFileData()));
					}
				}
			}
		}

	}

	private int getAnswersCount(String[] answers) {
		int count = 0;
		for (int i = 0; i < answers.length; i++) {
			if (answers[i].length() > 0) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	private void validate(PollQuestionsFormBean form,
			HttpServletRequest request, ActionMessages errors) {
		if (form.getPollId() == null || form.getPollId().length() == 0) {
			errors.add("pollId", new org.apache.struts.action.ActionMessage(
					"create.poll.error.missing.poll.id"));
		}
		if (form.getQuestion() == null || form.getQuestion().length() == 0) {
			errors.add("question", new org.apache.struts.action.ActionMessage(
					"error.field.required", "Question"));
		}
		if (AnswerTypeEnum.get(form.getAnswerType()) == AnswerTypeEnum.INVALID) {
			errors.add("answerType",
					new org.apache.struts.action.ActionMessage(
							"create.poll.error.invalid.answer.type"));
		}

		String[] answer = form.getAnswer();
		if ((answer == null || answer.length < 2 || answer[0].length() == 0 || answer[1]
				.length() == 0)
				&& !form.getAnswerType().equalsIgnoreCase("TEXTAREA")) {
			errors.add("answer", new org.apache.struts.action.ActionMessage(
					"create.poll.error.atleast.two.answer.choices"));
		}

		if (FileUpload.isMultipartContent(request)
				&& form.getQuestionImage() != null
				&& form.getQuestionImage().getFileName().length() > 0) {
			if (!ImageUtils.getInstance().isVCSupportedMimeType(
					form.getQuestionImage().getContentType())) {
				errors.add("answer",
						new org.apache.struts.action.ActionMessage(
								"vc.file.upload.unsupported.image.format",
								ImageUtils.getInstance()
										.getVCSupportedFileFormats()));
			}
		}
		//		 has the maximum length been exceeded?
		Boolean maxLengthExceeded = (Boolean) request
				.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);

		if ((maxLengthExceeded != null)
				&& (maxLengthExceeded.booleanValue())
				|| form.getQuestionImage().getFileSize() > PollHelper.MAX_VC_FILE_SIZE) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.upload.file.size.too.large", "1024"));
		}

	}

	private void prefillAnswerChoices(String[] answer, List answerChoices) {
		if (answerChoices.size() > 0) {
			return;
		}
		for (int i = 0; i < answer.length; i++) {
			if (answer[i].length() > 0) {
				answerChoices.add(new AnswerChoice());
			} else {
				return;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return addQuestions(mapping, form, request, response);
	}
}