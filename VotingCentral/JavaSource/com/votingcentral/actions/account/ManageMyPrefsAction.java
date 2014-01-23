/*
 * Created on Apr 26, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.user.ManageMyPrefsForm;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.user.info.VCUserPrefsBO;
import com.votingcentral.model.db.dao.to.VCUserPrefsTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.VCPrivacyLevelEnum;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.enums.PresErrorCodesEnum;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.PresentationConstants;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ManageMyPrefsAction extends VCDispatchAction {
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showPrefs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "myprefs";
		String domContext = getDomainAndContext(request);
		ManageMyPrefsForm myProfForm = (ManageMyPrefsForm) form;
		try {
			String userName = VCRequestHelper.getUser(request);
			VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
			VCUserPrefsTO vuto = VCUserPrefsBO.getInstance()
					.getVCUserPrefsByUserId(vto.getUserId());
			fillFormFromDB(myProfForm, vuto);
			if (myProfForm.getErrCode() != null
					&& myProfForm.getErrCode().length() > 0) {
				try {
					int errCode = Integer.parseInt(myProfForm.getErrCode());
					errors.add("error", new ActionMessage(PresErrorCodesEnum
							.get(errCode).getName()));
				} catch (NumberFormatException nfe) {
					// ignore
				}
			}
		} catch (Exception e) {
			log.error(e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	/**
	 * 
	 * @param myProfForm
	 * @param vuto
	 */
	private void fillFormFromDB(ManageMyPrefsForm myProfForm, VCUserPrefsTO vuto) {
		if (vuto == null) {
			return;
		}
		if (vuto.getPollStartedLevelEnum() != null
				&& vuto.getPollStartedLevelEnum().getName() != null
				&& vuto.getPollStartedLevelEnum().getName().length() > 0) {
			myProfForm.setPollStartedEmail(vuto.getPollStartedLevelEnum()
					.getName());
		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "self";
		String domContext = getDomainAndContext(request);
		ManageMyPrefsForm myProfForm = (ManageMyPrefsForm) form;
		try {
			String userName = VCRequestHelper.getUser(request);
			long userId = UserBO.getInstance().getUserByUserName(userName)
					.getUserId();
			VCUserPrefsTO vuto = new VCUserPrefsTO();
			vuto.setUserId(userId);
			fillVCUserPrefsTO(vuto, myProfForm);
			VCUserPrefsBO.getInstance().upsertVCUserPrefs(vuto);
		} catch (Exception e) {
			log.error(e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		ActionForward newFwd = new ActionForward();
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath());
		buffer.append(PresentationConstants.QUESTION_MARK);
		buffer.append(PresentationConstants.ERR_CODE);
		buffer.append(PresentationConstants.EQUALS);
		buffer.append(PresErrorCodesEnum.MY_VC_MANAGE_PREFS_UPDATE_SUCCESS
				.getId());
		newFwd.setRedirect(true);
		newFwd.setPath(buffer.toString());
		return newFwd;
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward turnOffDailyEmail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "self";
		String domContext = getDomainAndContext(request);
		ManageMyPrefsForm myProfForm = (ManageMyPrefsForm) form;
		try {
			String userName = VCRequestHelper.getUser(request);
			long userId = UserBO.getInstance().getUserByUserName(userName)
					.getUserId();
			VCUserPrefsTO vuto = new VCUserPrefsTO();
			vuto.setUserId(userId);
			vuto.setPollStartedLevelEnum(VCPrivacyLevelEnum.PRIVATE);
			VCUserPrefsBO.getInstance().upsertVCUserPrefs(vuto);
		} catch (Exception e) {
			log.error(e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		// If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		ActionForward newFwd = new ActionForward();
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath());
		buffer.append(PresentationConstants.QUESTION_MARK);
		buffer.append(PresentationConstants.ERR_CODE);
		buffer.append(PresentationConstants.EQUALS);
		buffer.append(PresErrorCodesEnum.MY_VC_MANAGE_PREFS_UPDATE_SUCCESS
				.getId());
		newFwd.setRedirect(true);
		newFwd.setPath(buffer.toString());
		return newFwd;
	}

	private void fillVCUserPrefsTO(VCUserPrefsTO vuto,
			ManageMyPrefsForm myProfForm) {
		vuto.setPollStartedLevelEnum(VCPrivacyLevelEnum.get(myProfForm
				.getPollStartedEmail()));
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
		return showPrefs(mapping, form, request, response);
	}
}