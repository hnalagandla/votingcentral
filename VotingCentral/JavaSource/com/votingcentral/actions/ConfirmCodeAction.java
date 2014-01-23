/*
 * Created on Sep 18, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.votingcentral.model.user.PasswordService;
import com.votingcentral.util.pictures.PictureGenerator;
import com.votingcentral.util.session.SessionObjects;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ConfirmCodeAction extends Action {

	public static final String JPG_CONTENT_TYPE = "image/jpeg";

	private static Log log = LogFactory.getLog(ConfirmCodeAction.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse res)
			throws Exception {
		HttpSession session = request.getSession();
		String confirmcode = PasswordService.getInstance()
				.generateRandomPassword(8);
		session.setAttribute(SessionObjects.REGISTRATION_CONFIRM_CODE,
				confirmcode);
		log.debug("Storing code: " + confirmcode);
		
		res.setContentType(JPG_CONTENT_TYPE);
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
		try {
			if (log.isDebugEnabled()) {
				log.debug("ConfirmCodeAction : image generator is started");
			}
			PictureGenerator.getInstance().generatePicture(confirmcode,
					res.getOutputStream());
			res.flushBuffer();
			if (log.isDebugEnabled()) {
				log.debug("ConfirmCodeAction : image generator is finished");
			}
		} catch (Exception e) {
			log.error(" Error generating ConfirmCodeAction :", e);
		}
		return null;
	}
}