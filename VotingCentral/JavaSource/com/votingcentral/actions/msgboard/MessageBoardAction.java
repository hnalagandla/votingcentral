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

import com.votingcentral.forms.msgboard.MessageBoardForm;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.db.dao.to.PollTO;

/**
 * THIS CLASS IS NOT BEING USED...DELETE IT?
 * 
 * @author Gandhari
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MessageBoardAction extends Action {

	private Log log = LogFactory.getLog(MessageBoardAction.class);

	/**
	 *  
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionErrors errors = new ActionErrors();
		try {
			MessageBoardForm mForm = (MessageBoardForm) form;
			if (request.getAttribute("pollId") != null)
				mForm.setPollId(request.getAttribute("pollId").toString());
			//MessageBoardTO board = getMessageBoard(mForm.getPollId());
			//org.apache.commons.beanutils.BeanUtils.copyProperties(mForm,
			// board);
			//get Poll Data
			PollTO pollTO = getPollData(mForm.getPollId());
			mForm.setPollsTO(pollTO);
		} catch (Exception e) {
			log.error("Printing Stack Trace", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("unhandled.exception"));
		}

		String nextAction = "showMsgBoard";
		/* Other pages might want to have the msg board as an include. */
		if (request.getAttribute("nextAction") != null)
			nextAction = (String) request.getAttribute("nextAction");

		return mapping.findForward(nextAction);
	}

	/**
	 * 
	 * @param pollId
	 * @return
	 */
	/*
	 * private MessageBoardTO getMessageBoard(String pollId) { VCDAOFactory
	 * factory = new VCDAOFactory(); IMessageBoardDAO dao =
	 * factory.getMessageBoardDAO(); MessageBoardTO mboard =
	 * dao.getMessageBoard(pollId); return mboard; }
	 */

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