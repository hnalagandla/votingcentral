package com.votingcentral.actions.msgboard;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.votingcentral.forms.msgboard.SubjectBoardForm;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.db.dao.ISubjectBoardDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.db.dao.to.SubjectBoardTO;

public class SubjectBoardAction extends Action {

	private Log log = LogFactory.getLog(SubjectBoardAction.class);

	/**
	 *  
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionErrors errors = new ActionErrors();
		try {
			SubjectBoardForm mForm = (SubjectBoardForm) form;
			if (request.getAttribute("pollId") != null)
				mForm.setPollId(request.getAttribute("pollId").toString());
			SubjectBoardTO board = getSubjectBoard(mForm.getPollId());
			org.apache.commons.beanutils.BeanUtils.copyProperties(mForm, board);
			//get Poll Data
			PollTO pollTO = getPollData(mForm.getPollId());
			mForm.setPollsTO(pollTO);
		} catch (Exception e) {
			log.error("Printing Stack Trace", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("unhandled.exception"));
		}

		return mapping.findForward("showSubjectBoard");
	}

	/**
	 * 
	 * @param pollId
	 * @return
	 */
	private SubjectBoardTO getSubjectBoard(String pollId) {
		VCDAOFactory factory = new VCDAOFactory();
		ISubjectBoardDAO dao = factory.getSubjectBoardDAO();
		SubjectBoardTO mboard = dao.getSubjectBoard(pollId);
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