/*
 * Created on Mar 16, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.polls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.actions.DownloadAction;
import com.votingcentral.forms.polls.ShowPollResultsFormBean;
import com.votingcentral.model.bo.location.LocationBO;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.votes.Votes;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.db.dao.to.PollUserHistoryTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.VCDownloadFileTypeEnum;
import com.votingcentral.model.polls.PollData;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DownloadPollResultsAction extends DownloadAction {
	private static Log log = LogFactory.getLog(DownloadPollResultsAction.class);

	private static String COMMA = ",";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.votingcentral.actions.DownloadAction#getStreamInfo(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		long millis = System.currentTimeMillis();
		String fileName = "";
		String contentType = "";
		byte[] fileBytes = null;
		ShowPollResultsFormBean showResultsFormBean = (ShowPollResultsFormBean) form;
		String pollId = "";
		pollId = VCRequestHelper.getValueFromRequestOrForm(request,
				RequestParameterObjects.POLL_ID, pollId);
		String questionId = showResultsFormBean.getQuestionId();
		questionId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.QUESTION_ID, questionId);
		showResultsFormBean.setPollId(pollId);
		showResultsFormBean.setQuestionId(questionId);
		PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
		// if the user has not voted and the poll has not ended
		// redirect to show poll page.
		VCUserTO vto = UserBO.getInstance().getUserByUserName(
				VCRequestHelper.getUser(request));
		Date now = PollTimeHelper.getInstance().getCurrentDate();
		if (Votes.getInstance().canUserVote(vto.getUserId(), pollId)
				&& pto.getEndTimestamp().after(now)) {
			log.debug("User has not voted, sending them to display poll.");
			errors.add("pollId", new org.apache.struts.action.ActionMessage(
					"show.poll.participation.reqd"));
			return null;
		} else {
			VCDownloadFileTypeEnum dfType = (showResultsFormBean.getDfType() == null ? VCDownloadFileTypeEnum.DEFAULT
					: VCDownloadFileTypeEnum.get(showResultsFormBean
							.getDfType()));
			if (dfType == VCDownloadFileTypeEnum.EXCEL) {
				fileName = "VC" + ".xls";
				contentType = "application/vnd.ms-excel";
			} else if (dfType == VCDownloadFileTypeEnum.TEXT) {
				fileName = "VC" + ".txt";
				contentType = "text/plain";
				fileBytes = getTextFormatBytes(pollId, questionId);
			} else if (dfType == VCDownloadFileTypeEnum.CSV) {
				fileName = "VC" + ".csv";
				contentType = "application/vnd.ms-excel";
				fileBytes = getTextFormatBytes(pollId, questionId);
			}
		}
		//set content type
		response.setHeader("Content-Type", "application/download");
		// Set the content disposition
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName);
		response.setContentLength(fileBytes.length);		
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-control", "must-revalidate");
		return new ByteArrayStreamInfo(contentType, fileBytes);
	}

	private byte[] getTextFormatBytes(String pollId, String questionId)
			throws SQLException {
		PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
		PollData pd = pto.getPollData();
		Map answerIdToChoiceMap = pd.getAnswerIdToChoiceMap(questionId);
		List puhTos = Votes.getInstance().getVotesByPollIdQuestionId(pollId,
				questionId);

		UnSyncStringBuffer buffer = new UnSyncStringBuffer(1024);

		buffer.append("AnswerChoice");
		buffer.append(COMMA);
		buffer.append("City");
		buffer.append(COMMA);
		buffer.append("State");
		buffer.append(COMMA);
		buffer.append("Zip");
		buffer.append(COMMA);
		buffer.append("Country");
		buffer.append(COMMA);
		buffer.append("YOB");
		buffer.append(COMMA);
		buffer.append("Gender");
		buffer.append("\r\n");

		for (Iterator itr = puhTos.iterator(); itr.hasNext();) {
			PollUserHistoryTO puhto = (PollUserHistoryTO) itr.next();
			buffer.append(answerIdToChoiceMap.get(puhto.getAnswerId()));
			buffer.append(COMMA);
			buffer.append(puhto.getUserLocationCity());
			buffer.append(COMMA);
			buffer.append(LocationBO.getInstance().getStateByStateIdCountryId(
					puhto.getUserLocationStateId(),
					puhto.getUserLocationCountryId()));
			buffer.append(COMMA);
			buffer.append(puhto.getUserLocationZip());
			buffer.append(COMMA);
			buffer.append(LocationBO.getInstance().getCountryByCountryId(
					puhto.getUserLocationCountryId()));
			buffer.append(COMMA);
			buffer.append(puhto.getYearOfBirth());
			buffer.append(COMMA);
			buffer.append(puhto.getGender());
			buffer.append("\r\n");
		}
		return buffer.toString().getBytes();
	}

	protected class ByteArrayStreamInfo implements StreamInfo {

		protected String contentType;

		protected byte[] bytes;

		public ByteArrayStreamInfo(String contentType, byte[] bytes) {
			this.contentType = contentType;
			this.bytes = bytes;
		}

		public String getContentType() {
			return contentType;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(bytes);
		}
	}
}