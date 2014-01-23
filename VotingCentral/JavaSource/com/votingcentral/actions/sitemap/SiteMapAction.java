/*
 * Created on Nov 22, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.sitemap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.sitemap.SiteMapForm;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SiteMapAction extends VCDispatchAction {

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SiteMapForm siteForm = (SiteMapForm) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "sitemap";
		String domContext = getDomainAndContext(request);

		siteForm.setContestsMainUrl(VCURLHelper.getContestsMainUrl(domContext));

		siteForm.setCreatePollUrl(VCURLHelper.getCreatePollUrl(domContext));

		siteForm.setHomePageUrl(VCURLHelper.getHomePageUrl(domContext));

		siteForm.setMyVCUrl(VCURLHelper.getMyVCMainUrl(domContext));
		siteForm.setEditAccountUrl(VCURLHelper.getMyRegInfoUrl(domContext));

		siteForm.setLoginUrl(VCURLHelper.getLoginUrl(domContext));
		siteForm.setForgotPswdUrl(VCURLHelper.getForgotPasswordUrl(domContext));
		siteForm.setRegUrl(VCURLHelper.getRegistrationUrl(domContext));

		siteForm.setSearchUrl(VCURLHelper.getSearchUrl(domContext));
		siteForm.setVacoWinnersUrl(VCURLHelper.getVacoWinnersUrl(domContext));

		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.actions.DispatchAction#unspecified(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return show(mapping, form, request, response);
	}
}