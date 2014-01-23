/*
 * Created on Sep 13, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.home;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.HomePageFormBean;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.taf.TafBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.enums.TafTypeEnum;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.session.SessionObjects;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class HomePageAction extends VCDispatchAction {

	private final static int POLLS_SHORT_NAME_LENGTH = 30;

	private final static int FB_POLLS_SHORT_NAME_LENGTH = 20;

	private final static int MAX_POLLS_ON_HOME = 5;

	private final static int MAX_RECENT_USERS_ON_HOME = 5;

	private final static int MAX_ACTIVE_USERS_ON_HOME = 5;

	public ActionForward home(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		int shortNameLength = POLLS_SHORT_NAME_LENGTH;
		boolean isFacebook = VCRequestHelper.isFacebook(request);
		if (isFacebook) {
			shortNameLength = FB_POLLS_SHORT_NAME_LENGTH;
		}
		ActionForward forward = new ActionForward(); // return value
		String next = "home";
		HomePageFormBean homeForm = (HomePageFormBean) form;
		String domContext = VCRequestHelper.getDomainAndContext(request);
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();

		List fPolls = PollBO.getInstance().getFeaturedPolls();

		List dispFPolls = PollHelper.getDisplayablePolls(domContext, fPolls,
				shortNameLength, buffer).subList(0,
				getMaxIndexSize(fPolls));

		Collections.shuffle(dispFPolls);
		homeForm.setFeaturedPolls(dispFPolls);

		List rePolls = PollBO.getInstance().getRecentlyEndedPolls();
		List dispRePolls = PollHelper.getDisplayablePolls(domContext, rePolls,
				shortNameLength, buffer);
		PollHelper.setTimeAgoStr(dispRePolls, false);
		homeForm.setRecEndedPolls(dispRePolls.subList(0,
				getMaxIndexSize(rePolls)));
		homeForm
				.setRecEndedMoreUrl(VCURLHelper.getRecEndedPollsUrl(domContext));

		List rsPolls = PollBO.getInstance().getRecentlyStartedPolls();
		List dispRsPolls = PollHelper.getDisplayablePolls(domContext, rsPolls,
				shortNameLength, buffer);
		PollHelper.setTimeAgoStr(dispRsPolls, true);
		homeForm.setRecStartedPolls(dispRsPolls.subList(0,
				getMaxIndexSize(rsPolls)));
		homeForm.setRecStartedMoreUrl(VCURLHelper
				.getRecStartedPollsUrl(domContext));

		List mvPolls = PollBO.getInstance().getMostVotedPolls();
		homeForm.setMostVotedPolls(PollHelper.getDisplayablePolls(domContext,
				mvPolls, shortNameLength, buffer).subList(0,
				getMaxIndexSize(mvPolls)));
		homeForm.setMostVotedMoreUrl(VCURLHelper
				.getMostVotedPollsUrl(domContext));

		List mePolls = TafBO.getInstance().getMostEmailedPolls();
		homeForm.setMostEmailedPolls(PollHelper.getDisplayablePolls(domContext,
				mePolls, shortNameLength, buffer).subList(0,
				getMaxIndexSize(mePolls)));
		homeForm.setMostEmailedMoreUrl(VCURLHelper
				.getMostEmailedPollsUrl(domContext));

		String user = VCRequestHelper.getUser(request);
		if ((!request.getSession(false).isNew())
				&& (request.getSession(false).getAttribute(
						SessionObjects.USER_LOGIN_NAME) != null)) {
			String homePageURL = VCURLHelper.getHomePageUrl(domContext);
			homeForm.setTafHomePageUrl(VCURLHelper.getTAFUrl(domContext,
					homePageURL, "Your friend, " + user
							+ " thinks this is a great website.",
					TafTypeEnum.HOME));
		}

		incrementViewCounter(fPolls);

		List mostRecentUsers = UserBO.getInstance().getMostRecentlyJoinedUsers(
				MAX_RECENT_USERS_ON_HOME);
		List dispMostRecentUsers = UserBO.getInstance().getDisplayableUsers(
				mostRecentUsers, domContext);
		homeForm.setMostRecentUsers(dispMostRecentUsers);
		homeForm.setMostRecentUsersMoreUrl(VCURLHelper
				.getRecentUsersUrl(domContext));
		
		String keywords = PollHelper.getCleanedKeywords(buffer.toString() + ","
				+ homeForm.getCommaSeparatedKeywords());
		request.setAttribute(RequestParameterObjects.COMMA_SEP_KEYWORDS,
				keywords);
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	private int getMaxIndexSize(List polls) {
		if (polls == null || polls.size() == 0)
			return 0;

		return (polls.size() > MAX_POLLS_ON_HOME ? MAX_POLLS_ON_HOME : polls
				.size());
	}

	private void incrementViewCounter(List fPolls) throws SQLException {
		List local = new ArrayList();
		local.addAll(fPolls);
		for (Iterator itr = local.iterator(); itr.hasNext();) {
			String pollId = (String) itr.next();
			PollBO.getInstance().incrementPollViewsCount(pollId, 1);
		}
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
		return home(mapping, form, request, response);
	}
}