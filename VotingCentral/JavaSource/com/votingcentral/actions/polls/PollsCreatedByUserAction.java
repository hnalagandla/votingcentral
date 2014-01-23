/*
 * Created on Oct 5, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.polls;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.tiles.ComponentDefinition;
import org.apache.struts.tiles.TilesUtil;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.polls.PollsCreatedByUserFormBean;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.util.UnSyncStringBuffer;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollsCreatedByUserAction extends VCDispatchAction {

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		ActionForward forward = new ActionForward(); // return value
		String next = "allPollsCreated";
		PollsCreatedByUserFormBean mpopForm = (PollsCreatedByUserFormBean) form;
		VCUserTO vto = UserBO.getInstance().getUserByUserName(
				mpopForm.getUserName());
		if (vto != null) {
			List mvp = PollBO.getInstance().getPollIdsCreatedByUser(
					vto.getUserId(), true);
			mpopForm.setAllPollsCreated(PollHelper.getDisplayablePolls(
					getDomainAndContext(request), mvp,
					PollHelper.POLLS_MAX_NAME_LENGTH, buffer));
			String keywords = buffer + ","
					+ mpopForm.getCommaSeparatedKeywords();
			mpopForm.setCommaSeparatedKeywords(keywords);
			//			Append the user name to the title of the page.
			ComponentDefinition cdef = TilesUtil.getDefinition(
					"all.polls.created", request, request.getSession()
							.getServletContext());
			Map attrs = cdef.getAttributes();
			attrs.put("title", "All Polls created by " + " - "
					+ vto.getUserName());
		}
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