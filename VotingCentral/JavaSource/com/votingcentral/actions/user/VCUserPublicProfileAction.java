/*
 * Created on Oct 28, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.user;

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
import com.votingcentral.forms.user.VCUserPublicProfileForm;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.user.links.VCUserLinksBO;
import com.votingcentral.model.bo.vaco.VCVacoPointsBO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.db.dao.to.VCVacoPointsTO;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.pres.web.to.VCUserWTO;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCUserPublicProfileAction extends VCDispatchAction {
	private static int MAX_POLLS_PER_PAGE = 10;

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String domContext = getDomainAndContext(request);
		String next = "vcUserPublic";
		VCUserPublicProfileForm vcUserPPForm = (VCUserPublicProfileForm) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String userName = vcUserPPForm.getUserName();
		if (userName == null || userName.length() == 0) {
			errors.add("userName", new org.apache.struts.action.ActionMessage(
					"user.profile.not.found", userName));
		} else {
			VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
			if (vto == null
					|| !vto.getAccountStatus().equalsIgnoreCase("CONFIRMED")) {
				errors.add("userName",
						new org.apache.struts.action.ActionMessage(
								"user.profile.not.found", userName));
			} else {
				//Append the user name to the title of the page.
				ComponentDefinition cdef = TilesUtil.getDefinition(
						"vc.user.public.profile", request, request.getSession()
								.getServletContext());
				Map attrs = cdef.getAttributes();
				attrs.put("title", "Voting Central  user " + " - " + userName);
				setPolls(vcUserPPForm, domContext, vto.getUserId());
				setFriends(vcUserPPForm, domContext, vto.getUserId());
				setUserInfo(vcUserPPForm, domContext, vto.getUserId());
				setVacoPoints(vcUserPPForm, domContext, vto.getUserId());
				vcUserPPForm.setAllFriendsUrl(VCURLHelper
						.getAllConnectionsByUserUrl(domContext, vto
								.getDisplayUserName()));
				vcUserPPForm.setPollsMoreUrl(VCURLHelper
						.getAllPollsCreatedByUserUrl(domContext, userName));
				vcUserPPForm.setEditImageUrl(VCURLHelper
						.getMyImageUrl(domContext));
				vcUserPPForm.setEditProfileUrl(VCURLHelper
						.getMyProfileUrl(domContext));
				String loggedInUser = VCRequestHelper.getUser(request);
				if (loggedInUser.equalsIgnoreCase(userName)) {
					vcUserPPForm.setShowEditLinks(true);
				}
			}
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	private void setVacoPoints(VCUserPublicProfileForm vcUserPPForm,
			String domContext, long userId) throws SQLException {
		long currPoints = 0;
		long allTimePoints = 0;

		VCVacoPointsTO vto = VCVacoPointsBO.getInstance()
				.getCurrentPointsByUserId(userId);
		if (vto != null) {
			currPoints = vto.getPoints();
		}
		allTimePoints = VCVacoPointsBO.getInstance().getAllPointsByUserId(
				userId);
		log.error("Curr points:" + currPoints + " alltime:" + allTimePoints);
		vcUserPPForm.setAllTimeVacoPoints(Long.toString(allTimePoints));
		vcUserPPForm.setCurrVacoPoints(Long.toString(currPoints));

	}

	private void setPolls(VCUserPublicProfileForm vcUserPPForm,
			String domContext, long userId) throws SQLException {
		List pollsByUser = PollBO.getInstance().getPollIdsCreatedByUser(userId,
				false);
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		List pollwtos = PollHelper.getDisplayablePolls(domContext, pollsByUser,
				PollHelper.POLLS_MAX_NAME_LENGTH, buffer);
		vcUserPPForm.setPollsByUser(pollwtos);
		String keywords = buffer + ","
				+ vcUserPPForm.getCommaSeparatedKeywords();
		vcUserPPForm.setCommaSeparatedKeywords(keywords);
	}

	private void setFriends(VCUserPublicProfileForm vcUserPPForm,
			String domContext, long userId) throws SQLException {
		List friends = VCUserLinksBO.getInstance().getDirectFriendsByUserId(
				userId);
		List dispFriends = UserBO.getInstance().getDisplayableUsers(friends,
				domContext);
		vcUserPPForm.setFriends(dispFriends);
	}

	private void setUserInfo(VCUserPublicProfileForm vcUserPPForm,
			String domContext, long userId) throws SQLException {
		VCUserTO userInfo = UserBO.getInstance().getFullUserByUserId(userId);
		VCUserWTO dispUserInfo = UserBO.getInstance().getDisplayableUserInfo(
				userInfo, domContext);
		vcUserPPForm.setUserWTO(dispUserInfo);
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