/*
 * Created on Aug 17, 2005
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.util.session.SessionObjects;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class AuthenticatedAction extends Action {
	private static Log log = LogFactory.getLog(AuthenticatedAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//get the datasource and store that in the session.
		//the DAO factory will use this to get a connection and perform
		//the operataions on the DB.
		HttpSession session = request.getSession(false);
		String internalAction = request.getParameter("internalAction");

		//force login when coming from any other page besides
		//the index page.
		if (!internalAction.equalsIgnoreCase("fromIndexHTML")
				&& !internalAction.equalsIgnoreCase("newUserReg")) {
			if ((!this.userIsLoggedIn(request))
					&& (!this.hasLoginCredentials(request))) {
				ActionMessages errors = new ActionMessages();

				errors.add("error", new ActionMessage("logon.session.ended"));
				this.saveErrors(request, errors);

				return mapping.findForward("sessionEnded");
			}
		}

		return executeAction(mapping, form, request, response);
	}

	public abstract ActionForward executeAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	private boolean userIsLoggedIn(HttpServletRequest request) {
		if (request.getSession().getAttribute(SessionObjects.USER_LOGIN_NAME) == null) {
			return false;
		}

		return true;
	}

	private boolean hasLoginCredentials(HttpServletRequest request) {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		if ((userName != null) && (password != null) && (userName.length() > 1)
				&& (password.length() > 0)) {
			return true;
		}
		return false;
	}

}