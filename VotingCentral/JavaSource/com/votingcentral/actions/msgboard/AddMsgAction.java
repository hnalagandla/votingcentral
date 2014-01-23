package com.votingcentral.actions.msgboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.forms.msgboard.DiscussBoardForm;
import com.votingcentral.model.bo.messageboard.MessagesBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.db.dao.to.MessagesTO;
import com.votingcentral.model.db.dao.to.SubjectTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.util.UtilDateConverter;
import com.votingcentral.util.WordFilter;
import com.votingcentral.util.guid.GUIDService;
import com.votingcentral.util.request.VCRequestHelper;

public class AddMsgAction extends Action {
	private static Log log = LogFactory.getLog(AddMsgAction.class);
	//Register the converter used to convert the dates.
	static {
		org.apache.commons.beanutils.ConvertUtils.register(
				new UtilDateConverter(), java.sql.Date.class);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DiscussBoardForm mForm = (DiscussBoardForm) form;
		MessagesTO message = new MessagesTO();
		SubjectTO board = new SubjectTO();

		if (WordFilter.isObscene(mForm.getMessage())) {
			ActionMessages errors = new ActionMessages();
			ActionMessage msg = new ActionMessage(
					"msgboard.obscene.filter.message");
			errors.add(ActionMessages.GLOBAL_MESSAGE, msg);
			saveMessages(request, errors);
			return mapping.findForward("success");
		}

		org.apache.commons.beanutils.BeanUtils.copyProperties(message, mForm);
		org.apache.commons.beanutils.BeanUtils.copyProperties(board, mForm);

		String userName = VCRequestHelper.getUser(request);
		VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
		message.setCreatorId(vto.getUserId());

		String messageId = GUIDService.getNextGUID();
		message.setMessageId(messageId);
		message.setCreatorIPAddress(request.getRemoteAddr());
		board.setSubjectId(mForm.getSubjectId());
		board.setSubject(mForm.getSubject());
		board.addMessage(message);

		//Save the message now.
		MessagesBO.getInstance().saveMessage(board, message);

		return mapping.findForward("success");
	}

}