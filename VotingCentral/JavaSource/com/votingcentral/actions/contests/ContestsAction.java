/*
 * Created on Jan 15, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.contests;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.contests.ContestForm;
import com.votingcentral.model.bo.category.CategoryBO;
import com.votingcentral.model.bo.contests.ContestsBO;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.db.dao.to.CategoryTO;
import com.votingcentral.model.db.dao.to.ContestEntryTO;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.enums.ContestFileStatusEnum;
import com.votingcentral.model.enums.VCCategoryTypeEnum;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.pres.web.to.ContestWTO;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.request.VCRequestHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ContestsAction extends VCDispatchAction {

	private static Log log = LogFactory.getLog(ContestsAction.class);

	public ActionForward mainView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = new ActionForward(); // return value
		ContestForm cForm = (ContestForm) form;
		String next = "mainView";
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		String domContext = getDomainAndContext(request);
		// create a dastructure like this.
		// map with a list of contest entries
		// CUTEBABY -> ContestWTO
		// COOLGUY -> ContestWTO
		String userName = VCRequestHelper.getUser(request);
		long userId = 0;
		if (!userName.equalsIgnoreCase("guest")) {
			userId = UserBO.getInstance().getUserByUserName(userName)
					.getUserId();
		}
		List cats = CategoryBO.getInstance().getListOfSuperCategories(
				VCCategoryTypeEnum.CONTEST);
		Map pollsByContests = null;
		int i = 0;
		for (Iterator itr = cats.iterator(); itr.hasNext(); i++) {
			String cat = ((CategoryTO) itr.next()).getSuperCategory();
			List currEntries = ContestsBO.getInstance()
					.getContestEntriesByContestTypeFileStatus(cat,
							ContestFileStatusEnum.INUSE);
			if (currEntries != null && currEntries.size() > 0) {
				if (pollsByContests == null) {
					pollsByContests = new HashMap();
				}
				ContestEntryTO cto = (ContestEntryTO) currEntries.get(0);
				String pollId = cto.getPollId();
				ContestWTO wto = new ContestWTO();
				PollTO pto = PollBO.getInstance().getPollByPollId(pollId);
				buffer.append(pto.getKeywords() + ",");
				PollHelper.setImageUrls(domContext, pto.getPollData()
						.getQuestionnaire().getQuestions());
				PollHelper.fillContestWTO(domContext, wto, pto, cto
						.getContestType(), i, userId);
				pollsByContests.put(cat, wto);
			}
		}

		cForm.setCurrentLiveContests(pollsByContests);
		cForm.setOtherUploadUrls(ContestsBO.getInstance()
				.getAllContestsUploadUrls(domContext));
		cForm.setCommaSeparatedKeywords(buffer.toString()
				+ cForm.getCommaSeparatedKeywords());
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	public ActionForward showFileUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = new ActionForward(); // return value
		ContestForm cForm = (ContestForm) form;
		cForm.setContestTypes(getContestTypes());
		String nextView = "showFileUpload";
		String nextAction = "uploadFile";
		forward = mapping.findForward(nextView);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	/**
	 * @return Returns the contestTypes.
	 */
	private List getContestTypes() {
		List contestTypes = null;
		try {
			contestTypes = CategoryBO
					.getInstance()
					.getListOfSuperCategoriesForView(VCCategoryTypeEnum.CONTEST);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contestTypes;
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
		return mainView(mapping, form, request, response);
	}
}