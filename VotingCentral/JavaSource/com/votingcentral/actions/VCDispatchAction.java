/*
 * Created on Apr 16, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.votingcentral.forms.VCBaseFormBean;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.TafTypeEnum;
import com.votingcentral.model.enums.VCUserAccountStatusEnum;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.enums.PresErrorCodesEnum;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.FastURLEncoder;
import com.votingcentral.util.url.PresentationConstants;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCDispatchAction extends DispatchAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.actions.DispatchAction#dispatchMethod(org.apache.struts.action.ActionMapping,
	 *      org.apache.struts.action.ActionForm,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.String)
	 */
	protected ActionForward dispatchMethod(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String name) throws Exception {

		try {
			// if a secure URL is not being accessed securely redirect back
			// to a
			// secure version of that URL.
			if (!VCURLHelper.isSecureUrlSecure(request)) {
				String secUrl = VCURLHelper.prependHttpsPrefix(request
						.getRequestURL().toString(), request.getParameterMap());
				ActionForward newForward = new ActionForward(secUrl, true);
				// Finish with
				return (newForward);
			}
			//check is a protected page is being accessed without logging in
			// or suffcient access.
			if (VCURLHelper.isRedirectToLoginRequired(request)) {
				String currUrl = null;
				if (request.getRequestURL() != null) {
					currUrl = request.getRequestURL().toString();
					//add the request parameters as
					String requestParamsAsString = VCURLHelper
							.getRequestParamsAsString(request.getParameterMap());
					currUrl = currUrl + requestParamsAsString;
				}
				String loginUrl = VCURLHelper.getLoginUrl(VCRequestHelper
						.getDomainAndContext(request));
				UnSyncStringBuffer buff = new UnSyncStringBuffer();
				buff.append(loginUrl).append(
						PresentationConstants.QUESTION_MARK).append(
						RequestParameterObjects.LOGIN_SUCCESS_RU).append(
						PresentationConstants.EQUALS).append(
						FastURLEncoder.encode(currUrl));
				ActionForward newForward = new ActionForward(buff.toString(),
						true);
				// Finish with
				return (newForward);
			}
			String user = VCRequestHelper.getUser(request);
			VCUserTO vto = null;
			if (user != null && user.length() > 0
					&& !user.equalsIgnoreCase("guest")) {
				vto = UserBO.getInstance().getUserByUserName(user);
			}
			ActionForward forward = null;
			// if the account is not confirmed, take them
			// to the account confirmation page.
			if (vto != null
					&& !vto.getAccountStatus().equalsIgnoreCase(
							VCUserAccountStatusEnum.CONFIRMED)) {
				// part of the global forward.
				forward = mapping.findForward("confirmUser");
				ActionForward newForward = new ActionForward();
				UnSyncStringBuffer buffer = new UnSyncStringBuffer();
				buffer.append(forward.getPath()).append(
						PresentationConstants.AMPERSAND).append(
						RequestParameterObjects.USER_NAME).append(
						PresentationConstants.EQUALS).append(
						FastURLEncoder.encode(vto.getDisplayUserName()));
				buffer.append(PresentationConstants.AMPERSAND);
				buffer.append(PresentationConstants.ERR_CODE);
				buffer.append(PresentationConstants.EQUALS);
				buffer
						.append(PresErrorCodesEnum.USER_CONFIRM_RE_REQUEST_CONF_CODE
								.getId());
				newForward.setPath(buffer.toString());
				newForward.setName(forward.getName());
				newForward.setRedirect(true);
				// Finish with
				return (newForward);
			} else {
				// setBaseFormData(form, request);
				// call the method that needs to be invoked.
				forward = super.dispatchMethod(mapping, form, request,
						response, name);
			}
			// not all forms need to extend from VCBaseFormBean
			if (form instanceof VCBaseFormBean) {
				VCBaseFormBean baseForm = (VCBaseFormBean) form;
				String kws = PollHelper.getCleanedKeywords(baseForm
						.getCommaSeparatedKeywords());
				request.setAttribute(
						RequestParameterObjects.COMMA_SEP_KEYWORDS, kws);
			}
			return forward;
		} catch (NoSuchMethodException e) {
			try {
				return unspecified(mapping, form, request, response);
			} catch (Exception e1) {
				log.error("Exception in base unspecified:", e1);
				throw e1;
			}
		} catch (Exception e) {
			log.error("Exception in base:", e);
			throw e;
		}
	}

	protected static String getDomainAndContext(HttpServletRequest request) {
		return VCRequestHelper.getDomainAndContext(request);
	}

	protected static String getDomainAndContextWithProtocol(
			HttpServletRequest request) {
		return VCRequestHelper.getDomainAndContextWithProtocol(request);
	}

	protected static String getDomainAndContextForImg(HttpServletRequest request) {
		return VCRequestHelper.getDomainAndContextForImg(request);
	}

	private void setBaseFormData(ActionForm form, HttpServletRequest request) {
		String domCtx = getDomainAndContext(request);
		VCBaseFormBean vcForm = (VCBaseFormBean) form;
		vcForm.setAboutUsUrl("");
		vcForm.setCommaSeparatedKeywords("");
		vcForm.setContactUsUrl("");
		vcForm.setContestsCategoriesUrls(null);
		vcForm.setCreatePollUrl(VCURLHelper.getCreatePollUrl(domCtx));
		request.setAttribute("createPollUrl", VCURLHelper
				.getCreatePollUrl(domCtx));
		vcForm.setFaqUrl("");
		vcForm.setFeaturedPollsUrl("");
		vcForm.setHomeUrl(VCURLHelper.getHomePageUrl(domCtx));
		vcForm.setLogOutUrl(VCURLHelper.getLogoutUrl(domCtx));
		vcForm.setMyVCUrl(VCURLHelper.getMyVCMainUrl(domCtx));
		request.setAttribute("myVCMainUrl", VCURLHelper.getMyVCMainUrl(domCtx));
		vcForm.setPollCategoriesUrls(VCURLHelper
				.getPollsByCategoriesURLs(domCtx));
		vcForm.setRecEndedPollsUrl(VCURLHelper.getRecEndedPollsUrl(domCtx));
		vcForm.setTafHomeUrl(VCURLHelper.getTAFUrl(domCtx, VCURLHelper
				.getHomePageUrl(domCtx), "Cool Site!!", TafTypeEnum.HOME));
	}

}