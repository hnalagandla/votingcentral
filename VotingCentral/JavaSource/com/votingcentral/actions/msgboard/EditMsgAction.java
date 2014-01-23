package com.votingcentral.actions.msgboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.votingcentral.model.bo.messageboard.MessagesBO;
import com.votingcentral.model.db.dao.to.MessagesTO;

public class EditMsgAction extends Action {
	private Log log = LogFactory.getLog(EditMsgAction.class);

	/**
	 *  
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DynaActionForm msgForm = (DynaActionForm) form;
		String messageId = msgForm.get("messageId").toString();
		MessagesTO msg = MessagesBO.getInstance().getMessage(messageId);
		log.debug("message >" + msg.getMessage());
		msgForm.set("messageId", messageId);
		msgForm.set("message", msg.getMessage());
		log.debug("Forward >" + mapping.findForward("success").getPath());
		return mapping.findForward("success");
	}

}