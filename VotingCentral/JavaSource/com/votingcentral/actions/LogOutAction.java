/*
 * Created on Feb 7, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.votingcentral.cookies.VCCookie;
import com.votingcentral.cookies.VCCookieHandler;
import com.votingcentral.cookies.VCCookielet;
import com.votingcentral.cookies.VCCookieletEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class LogOutAction extends VCDispatchAction {

	private static Log log = LogFactory.getLog(LogOutAction.class);

	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String next = "home";
		VCCookie vcCookie = VCCookieHandler.getCookie(request);
		VCCookielet user = vcCookie.getCookielet(VCCookieletEnum.USERNAME
				.getName());
		vcCookie.removeCookielet(VCCookieletEnum.USERNAME.getName());
		vcCookie.removeCookielet(VCCookieletEnum.LOGIN_IP.getName());
		vcCookie.removeCookielet(VCCookieletEnum.LOGIN.getName());
		VCCookieHandler.addCookie(response, vcCookie);

		return mapping.findForward(next);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward unspecified(ActionMapping arg0, ActionForm arg1,
			HttpServletRequest arg2, HttpServletResponse arg3) throws Exception {
		return logout(arg0, arg1, arg2, arg3);
	}
}