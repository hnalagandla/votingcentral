package com.votingcentral.actions.msgboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.votingcentral.forms.msgboard.SubjectForm;
import com.votingcentral.model.db.dao.ISubjectDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;


public class ArchiveSubjectAction extends Action {
	private static Log log = LogFactory.getLog(ArchiveSubjectAction.class);
  	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		SubjectForm mbForm = (SubjectForm)form;
		String subjectId = mbForm.getSubjectId();
		deleteSubject(subjectId);
		return mapping.findForward("success");
	}
	
	/**
	 * 
	 * @param messageId
	 * @return
	 */
	private boolean deleteSubject(String subjectId)
	{
		VCDAOFactory factory = new VCDAOFactory();
		ISubjectDAO dao = factory.getSubjectDAO();
		boolean flag = dao.archiveSubject(subjectId);
		return flag;
	}
}