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

import com.votingcentral.model.db.dao.IMessagesDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;


public class DeleteMsgAction extends Action {
	private static Log log = LogFactory.getLog(DeleteMsgAction.class);
  	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		DynaActionForm msgForm = (DynaActionForm)form;
		String messageId = msgForm.get("messageId").toString();
		
		deleteMessage(messageId);
		return mapping.findForward("success");
	}
	
	/**
	 * 
	 * @param messageId
	 * @return
	 */
	private boolean deleteMessage(String messageId)
	{
		VCDAOFactory factory = new VCDAOFactory();
		IMessagesDAO dao = factory.getMessagesDAO();
		boolean flag = dao.deleteMessage(messageId);
		return flag;
	}
}