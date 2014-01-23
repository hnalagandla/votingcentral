package com.votingcentral.actions.msgboard;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.votingcentral.forms.msgboard.DiscussBoardForm;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.db.dao.IMessagesDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.db.dao.to.SubjectTO;
import com.votingcentral.util.request.VCRequestHelper;

public class DiscussBoardAction extends Action {

	private Log log = LogFactory.getLog(DiscussBoardAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DiscussBoardForm mForm = (DiscussBoardForm) form;
		String subjectId = mForm.getSubjectId();

		SubjectTO board = getMessages(subjectId);

		org.apache.commons.beanutils.BeanUtils.copyProperties(mForm, board);
		mForm.setCreatorLoginName(VCRequestHelper.getUser(request));

		PollTO pollTO = getPollData(board.getPollId());
		mForm.setPollsTO(pollTO);
		// Clear the messages in the form bean now.
		mForm.setMessage("");
		return mapping.findForward("success");
	}

	/**
	 * 
	 * @param subjectId
	 * @return
	 */
	private SubjectTO getMessages(String subjectId) {
		VCDAOFactory factory = new VCDAOFactory();
		IMessagesDAO dao = factory.getMessagesDAO();
		SubjectTO mboard = dao.getMessagesBySubject(subjectId);
		return mboard;
	}

	/**
	 * 
	 * @param pollId
	 * @return
	 */
	private PollTO getPollData(String pollId) {
		PollTO pto = null;

		try {
			pto = PollBO.getInstance().getPollByPollId(pollId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pto;
	}
}