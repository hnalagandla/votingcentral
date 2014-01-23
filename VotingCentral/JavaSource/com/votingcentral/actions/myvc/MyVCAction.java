/*
 * Created on Feb 18, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.myvc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.env.EnvProps;
import com.votingcentral.forms.myvc.MyVCForm;
import com.votingcentral.model.bo.contests.ContestsBO;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.db.dao.IPollCommentsDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.ContestEntryTO;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.pictures.ImageUtils;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MyVCAction extends VCDispatchAction {

	private static Log log = LogFactory.getLog(MyVCAction.class);

	private static int contestsDisplayLimit = Integer.parseInt(EnvProps
			.getProperty("myvc.contests.display.limit.count"));

	private static int contestDisplayLimitTime = Integer.parseInt(EnvProps
			.getProperty("myvc.contests.display.limit.time.days"));

	private static int mbDisplayLimit = Integer.parseInt(EnvProps
			.getProperty("myvc.polls.mb.display.limit.count"));

	private static int mbDisplayLimitTime = Integer.parseInt(EnvProps
			.getProperty("myvc.polls.mb.display.limit.time.days"));

	public ActionForward mainView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "main";
		MyVCForm myVCForm = (MyVCForm) form;
		try {
			String userName = VCRequestHelper.getUser(request);
			long userId = UserBO.getInstance().getUserByUserName(userName)
					.getUserId();
			UnSyncStringBuffer buffer = new UnSyncStringBuffer();
			List pollIdsCreated = getPollsCreated(userId, false);
			List dispPollsCreated = PollHelper.getDisplayablePolls(
					VCRequestHelper.getDomainAndContext(request),
					pollIdsCreated, 60, buffer);
			myVCForm.setPollsCreated(dispPollsCreated);

			List pollIdsVoted = PollBO.getInstance().getPollIdsVotedByUser(
					userId, false);
			List dispPollsVoted = PollHelper.getDisplayablePolls(
					VCRequestHelper.getDomainAndContext(request), pollIdsVoted,
					60, buffer);
			myVCForm.setPollsVoted(dispPollsVoted);

			List contestFiles = getContestEntries(userId, false,
					getDomainAndContext(request));
			myVCForm.setContestFiles(contestFiles);

			List commentsPollIds = getPollCommentsPollIds(userId, false);
			List dispCommentsPolls = PollHelper.getDisplayablePolls(
					VCRequestHelper.getDomainAndContext(request),
					commentsPollIds, 60, buffer);
			myVCForm.setPollComments(dispCommentsPolls);

			myVCForm.setEditAccountInfoUrl(VCURLHelper
					.getMyRegInfoUrl(getDomainAndContext(request)));
		} catch (Exception e) {
			log.error("Exception trying to my voting central data", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("user.login.exception"));
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

	public ActionForward allPollsVoted(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "allvotes";
		MyVCForm myVCForm = (MyVCForm) form;
		try {
			String userName = VCRequestHelper.getUser(request);
			long userId = UserBO.getInstance().getUserByUserName(userName)
					.getUserId();
			UnSyncStringBuffer buffer = new UnSyncStringBuffer();
			List pollIdsVoted = PollBO.getInstance().getPollIdsVotedByUser(
					userId, true);
			List dispPolls = PollHelper.getDisplayablePolls(VCRequestHelper
					.getDomainAndContext(request), pollIdsVoted, 60, buffer);
			myVCForm.setPollsVoted(dispPolls);

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

	public ActionForward allPollsCreated(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "allpolls";
		MyVCForm myVCForm = (MyVCForm) form;
		try {
			String userName = VCRequestHelper.getUser(request);
			long userId = UserBO.getInstance().getUserByUserName(userName)
					.getUserId();
			UnSyncStringBuffer buffer = new UnSyncStringBuffer();
			List pollIdsCreated = getPollsCreated(userId, true);
			List dispPolls = PollHelper.getDisplayablePolls(VCRequestHelper
					.getDomainAndContext(request), pollIdsCreated, 60, buffer);
			myVCForm.setPollsCreated(dispPolls);

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

	public ActionForward allContestFiles(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "allcontests";
		MyVCForm myVCForm = (MyVCForm) form;
		try {
			String userName = VCRequestHelper.getUser(request);
			long userId = UserBO.getInstance().getUserByUserName(userName)
					.getUserId();

			List contestFiles = getContestEntries(userId, true,
					getDomainAndContext(request));
			myVCForm.setContestFiles(contestFiles);

		} catch (Exception e) {
			log.error("Exception trying to my voting central data", e);
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

	public ActionForward allPollComments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "allpollcomments";
		MyVCForm myVCForm = (MyVCForm) form;
		try {
			String userName = VCRequestHelper.getUser(request);
			long userId = UserBO.getInstance().getUserByUserName(userName)
					.getUserId();
			UnSyncStringBuffer buffer = new UnSyncStringBuffer(); 
			List commentsPolls = getPollCommentsPollIds(userId, true);
			List dispCommentsPolls = PollHelper.getDisplayablePolls(
					VCRequestHelper.getDomainAndContext(request),
					commentsPolls, 60, buffer);
			myVCForm.setPollComments(dispCommentsPolls);
		} catch (Exception e) {
			log.error("Exception trying to my voting central data", e);
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

	private List getPollsCreated(long emailAddressId, boolean getAll)
			throws SQLException {
		return PollBO.getInstance().getPollIdsCreatedByUser(emailAddressId,
				getAll);
	}

	private List getContestEntries(long emailAddressId, boolean getAll,
			String domContext) throws IOException, SQLException {
		List results = null;
		if (getAll) {
			results = ContestsBO.getInstance()
					.getContestEntriesByUserLimitByTime(emailAddressId,
							contestDisplayLimitTime);
		} else {
			results = ContestsBO.getInstance()
					.getContestEntriesByUserLimitByCountTime(emailAddressId,
							contestDisplayLimitTime, contestsDisplayLimit);
		}
		if (results != null) {
			for (Iterator itr = results.iterator(); itr.hasNext();) {
				ContestEntryTO cto = (ContestEntryTO) itr.next();
				cto.setMaxImageUrl(VCURLHelper.getDisplayImageUrl(domContext,
						cto.getFileId(), ImageUtils.MAX_W, ImageUtils.MAX_H));
				cto.setMinImageUrl(VCURLHelper.getDisplayImageUrl(domContext,
						cto.getFileId(), ImageUtils.MIN_W, ImageUtils.MIN_H));
			}
		}

		return results;
	}

	/**
	 * 
	 * @param emailAddressId
	 * @param getAll
	 * @return
	 * @throws SQLException
	 */
	private List getPollCommentsPollIds(long emailAddressId, boolean getAll)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IPollCommentsDAO pdao = dao.getPollCommentsDAO();
		List results = null;
		if (getAll) {
			results = pdao.getPollCommentsByUserLimitByTime(emailAddressId,
					PollTimeHelper.getInstance().getMonthsOldTimestamp(
							mbDisplayLimitTime));
		} else {
			results = pdao.getPollCommentsByUserLimitByCountTime(
					emailAddressId, PollTimeHelper.getInstance()
							.getMonthsOldTimestamp(mbDisplayLimitTime),
					mbDisplayLimit);
		}
		return results;
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