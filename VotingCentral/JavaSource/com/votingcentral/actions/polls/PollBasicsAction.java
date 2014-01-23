/*
 * Created on Mar 19, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.polls;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.polls.PollBasicsFormBean;
import com.votingcentral.model.bo.category.CategoryBO;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.VCCategoryTypeEnum;
import com.votingcentral.model.enums.VCUserRolesEnum;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.util.enums.PresErrorCodesEnum;
import com.votingcentral.util.guid.GUIDService;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollBasicsAction extends VCDispatchAction {

	public ActionForward pollBasics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String next = "pollBasics";
		PollBasicsFormBean pollBasicsFormBean = (PollBasicsFormBean) form;
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		validateShow(pollBasicsFormBean, request, errors);
		if (errors.size() == 0) {
			if (pollBasicsFormBean.getPollId() != null
					&& pollBasicsFormBean.getPollId().length() > 0) {
				PollTO pto = PollBO.getInstance().getPollByPollId(
						pollBasicsFormBean.getPollId());
				fillFormWithBasics(pto, pollBasicsFormBean);
			}
			pollBasicsFormBean.setCategories(CategoryBO.getInstance()
					.getListOfSuperCategoriesForView(VCCategoryTypeEnum.POLL));
		} else {
			next = "createNewPoll";
		}
		if (pollBasicsFormBean.getErrCode() != null
				&& pollBasicsFormBean.getErrCode().length() > 0) {
			try {
				int errCode = Integer.parseInt(pollBasicsFormBean.getErrCode());
				errors.add("error", new ActionMessage(PresErrorCodesEnum.get(
						errCode).getName()));
			} catch (NumberFormatException nfe) {
				// ignore
			}
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

	public ActionForward upsertPoll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String next = "addQuestions";
		String pollId = "";
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value

		PollBasicsFormBean pollBasicsFormBean = (PollBasicsFormBean) form;
		validate(pollBasicsFormBean, request, errors);
		if (errors.size() > 0) {
			pollBasicsFormBean.setCategories(CategoryBO.getInstance()
					.getListOfSuperCategoriesForView(VCCategoryTypeEnum.POLL));
			saveErrors(request, errors);
			return mapping.findForward("pollBasics");
		}
		String userName = VCRequestHelper.getUser(request);
		long userId = UserBO.getInstance().getUserByUserName(userName)
				.getUserId();
		pollId = pollBasicsFormBean.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		PollTO pto = null;
		if ((pollId != null) && (pollId.length() > 0)) {
			pto = PollBO.getInstance().getPollByPollId(pollId);
			fillPollTOWithBasics(pto, pollBasicsFormBean);
			PollBO.getInstance().updatePollByPollId(pto);
		} else {
			pto = new PollTO();
			pollId = GUIDService.getNextGUID();
			fillPollTOWithBasics(pto, pollBasicsFormBean);
			pto.setPollId(pollId);
			pto.setUserId(userId);
			PollBO.getInstance().createNewPoll(pto);
			pollBasicsFormBean.setPollId(pollId);
		}
		VCRequestHelper.setValueIntoRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	private void fillPollTOWithBasics(PollTO pto,
			PollBasicsFormBean pollBasicsFormBean) {
		try {
			BeanUtils.copyProperties(pto, pollBasicsFormBean);
		} catch (IllegalAccessException e) {
			// TODO write a safe copier method
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO write a safe copier method
			e.printStackTrace();
		}
		PollHelper.processKeywords(pto);
	}

	private void fillFormWithBasics(PollTO pto,
			PollBasicsFormBean pollBasicsFormBean) {
		try {
			BeanUtils.copyProperties(pollBasicsFormBean, pto);
		} catch (IllegalAccessException e) {
			// TODO write a safe copier method
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO write a safe copier method
			e.printStackTrace();
		}
	}

	private void validate(PollBasicsFormBean form, HttpServletRequest request,
			ActionMessages errors) {
		validateShow(form, request, errors);
		if (errors.size() > 0) {
			return;
		}
		if (form.getPollName() == null || form.getPollName().length() == 0) {
			errors.add("pollName", new org.apache.struts.action.ActionMessage(
					"error.field.required", "Poll Name"));
		}
		if (form.getPollDesc() == null || form.getPollDesc().length() == 0) {
			errors.add("pollDesc", new org.apache.struts.action.ActionMessage(
					"error.field.required", "Poll Description"));
		}
		if (form.getKeywords() == null || form.getKeywords().length() == 0) {
			errors.add("keywords", new org.apache.struts.action.ActionMessage(
					"error.field.required", "Keywords"));
		}
		if (form.getPollCategory1() == null
				|| form.getPollCategory1().length() == 0
				|| form.getPollCategory1().equalsIgnoreCase(
						CategoryBO.SELECT_A_CATEGORY)) {
			errors.add("pollCategory1",
					new org.apache.struts.action.ActionMessage(
							"error.field.required", "Poll Category 1"));
		}
	}

	private void validateShow(PollBasicsFormBean form,
			HttpServletRequest request, ActionMessages errors) {
		String pollId = form.getPollId();
		pollId = VCRequestHelper.getValueFromRequest(request,
				RequestParameterObjects.POLL_ID, pollId);
		if (pollId != null && pollId.length() > 0) {
			PollTO pto = null;
			try {
				pto = PollBO.getInstance().getPollByPollId(pollId);
			} catch (SQLException e) {
				// ignore
			}
			if (pto == null) {
				errors.add("pollId",
						new org.apache.struts.action.ActionMessage(
								"show.poll.invalid.pollid"));
				return;
			}
			VCUserTO vto = null;
			try {
				vto = UserBO.getInstance().getUserByUserName(
						VCRequestHelper.getUser(request));
			} catch (SQLException e1) {
				// ignore
			}
			if (vto == null) {
				errors.add("pollId",
						new org.apache.struts.action.ActionMessage(
								"error.field.invalid.user"));
				return;
			}
			// only poll creator can edit the poll, unless it is an admin
			if ((pto.getUserId() != vto.getUserId())
					&& !request.isUserInRole(VCUserRolesEnum.ADMIN.getName())) {
				errors.add("pollId",
						new org.apache.struts.action.ActionMessage(
								"create.poll.error.cant.edit.poll"));
				return;
			}
			try {
				// only poll that has no votes can be edited, unless it is an
				// admin.
				if (PollBO.getInstance().getPollByPollId(pollId)
						.getPollTotalVotes() != 0
						&& !request.isUserInRole(VCUserRolesEnum.ADMIN
								.getName())) {
					errors
							.add(
									"pollId",
									new org.apache.struts.action.ActionMessage(
											"create.poll.error.cant.edit.poll.started"));
					return;
				}
			} catch (SQLException e2) {
				log.error("Error checking if poll has votes", e2);
				errors.add("pollId",
						new org.apache.struts.action.ActionMessage(
								"create.poll.error.cant.edit.poll.started"));

			}
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

		return pollBasics(mapping, form, request, response);
	}
}