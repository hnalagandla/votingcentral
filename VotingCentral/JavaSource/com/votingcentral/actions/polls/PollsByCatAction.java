/*
 * Created on Sep 13, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.polls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.tiles.ComponentDefinition;
import org.apache.struts.tiles.TilesUtil;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.polls.PollsByCatFormBean;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.util.UnSyncStringBuffer;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollsByCatAction extends VCDispatchAction {

	private static int MAX_POLLS_PER_PAGE = 125;

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String next = "pollsByCat";
		PollsByCatFormBean pollByCatForm = (PollsByCatFormBean) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String category = pollByCatForm.getCategoryName();
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		List pollsByCat = PollBO.getInstance().getPollIdsByCategory(category,
				pollByCatForm.getLastPollEndTime(), MAX_POLLS_PER_PAGE);
		List pollwtos = PollHelper.getDisplayablePolls(
				getDomainAndContext(request), pollsByCat,
				PollHelper.POLLS_MAX_NAME_LENGTH, buffer);
		pollByCatForm.setPollsByCat(pollwtos);
		setFirstPollInResultEndTime(pollByCatForm, pollsByCat);
		setLastPollInResultEndTime(pollByCatForm, pollsByCat);

		String keywords = buffer + ","
				+ pollByCatForm.getCommaSeparatedKeywords();
		pollByCatForm.setCommaSeparatedKeywords(keywords);

		//Append the poll name to the title of the page.
		ComponentDefinition cdef = TilesUtil.getDefinition("polls.by.cat",
				request, request.getSession().getServletContext());
		Map attrs = cdef.getAttributes();
		attrs.put("title", category + " - "
				+ "VotingCentral.com Polls by Category.");

		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	public ActionForward showNext(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return show(mapping, form, request, response);
	}

	private void setLastPollInResultEndTime(PollsByCatFormBean pollByCatForm,
			List pollsByCat) throws SQLException {
		if (pollsByCat == null || pollsByCat.size() == 0) {
			return;
		}
		int size = pollsByCat.size();
		String pollId = (String) pollsByCat.get(size - 1);
		PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
		String endTime = String.valueOf(pto.getEndTimestamp().getTime());
		pollByCatForm.setLastPollEndTime(endTime);
	}

	private void setFirstPollInResultEndTime(PollsByCatFormBean pollByCatForm,
			List pollsByCat) throws SQLException {
		if (pollsByCat == null || pollsByCat.size() == 0) {
			return;
		}
		String pollId = (String) pollsByCat.get(0);
		PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
		String endTime = String.valueOf(pto.getEndTimestamp().getTime());
		pollByCatForm.setLastPollEndTime(endTime);
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