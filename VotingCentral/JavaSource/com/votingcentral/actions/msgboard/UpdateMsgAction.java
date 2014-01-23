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
import com.votingcentral.model.db.dao.to.MessagesTO;


public class UpdateMsgAction extends Action {
	private Log log = LogFactory.getLog(UpdateMsgAction.class);
  	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		DynaActionForm msgForm = (DynaActionForm)form;
		String messageId = msgForm.get("messageId").toString();
		String message = msgForm.get("message").toString(); 
		MessagesTO msg=new MessagesTO();
		msg.setMessageId(messageId);
		msg.setMessage(message);
		log.debug("MESSAGE MESSAGE ID:"+messageId);
		log.debug("MESSAGE MESSAGE:"+message);
		updateMessage(msg);
		return mapping.findForward("success");
	}
	
	/**
	 * 
	 * @param messageId
	 * @return
	 */
	private boolean updateMessage(MessagesTO msg)
	{
		VCDAOFactory factory = new VCDAOFactory();
		IMessagesDAO dao = factory.getMessagesDAO();
		boolean flag = dao.updateMessage(msg);
		return flag;
	}
}