package com.votingcentral.actions.msgboard;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.votingcentral.forms.msgboard.SubjectForm;
import com.votingcentral.model.bo.messageboard.MessagesBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.vcfile.VCFileBO;
import com.votingcentral.model.db.dao.ISubjectDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.to.MessagesTO;
import com.votingcentral.model.db.dao.to.SubjectTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.VCFileTypeEnum;
import com.votingcentral.util.guid.GUIDService;
import com.votingcentral.util.pictures.ImageUtils;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;

public class NewSubjectAction extends DispatchAction {
	private Log log = LogFactory.getLog(NewSubjectAction.class);

	private int MB = 1024 * 1024;

	public static final String JPG_CONTENT_TYPE = "image/jpeg";

	private static final int IMAGE_MAX_WIDTH = 300;

	private static final int IMAGE_MAX_HEIGHT = 300;

	/**
	 *  
	 */
	public ActionForward createSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();

		try {
			SubjectForm mForm = (SubjectForm) form;
			MessagesTO message = new MessagesTO();
			SubjectTO subject = new SubjectTO();

			BeanUtils.copyProperties(message, mForm);
			BeanUtils.copyProperties(subject, mForm);

			String userName = VCRequestHelper.getUser(request);
			VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);

			//Populate SubjectTO
			subject.setCreatorLoginName(userName);
			subject.setUserId(vto.getUserId());
			subject.setSubjectId(GUIDService.getNextGUID());
			//Populate MessageTO
			message.setCreatorLoginName(userName);
			message.setCreatorId(vto.getUserId());
			message.setMessageId(GUIDService.getNextGUID());

			//Upload the file
			//Retrieve the file representation
			//Check that we have a file upload request
			boolean isMultipart = FileUpload.isMultipartContent(request);
			if (isMultipart && mForm.getUploadFile() != null
					&& mForm.getUploadFile().getFileName().length() > 0) {
				FormFile file = mForm.getUploadFile();
				String imageId = GUIDService.getNextGUID();
				subject.setImageId(imageId);
				VCFileBO.getInstance().saveVCFile(
						file,
						imageId,
						UserBO.getInstance().getUserByUserName(
								VCRequestHelper.getUser(request)).getUserId(),
						VCFileTypeEnum.INVALID, false);
			}

			//Create the subject NOW.
			createSubject(subject);
			//Save the Message
			MessagesBO.getInstance().saveMessage(subject, message);

			//FOR SOME REASON, THE NEXT ACTION CLASS ISN'T GETTING THE POLLID
			// FROM REQUEST PARAMS
			VCRequestHelper.setValueIntoRequest(request,
					RequestParameterObjects.POLL_ID, mForm.getPollId());

		} catch (Exception e) {
			log.error("Printing Stack Trace", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("unhandled.exception"));
		}
		return mapping.findForward("success");
	}

	/**
	 * 
	 * @param subject
	 * @return
	 */
	private boolean createSubject(SubjectTO subject) {
		VCDAOFactory factory = new VCDAOFactory();
		ISubjectDAO dao = factory.getSubjectDAO();
		boolean flag = dao.createSubject(subject);
		return flag;
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
	public ActionForward previewSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();

		try {
			SubjectForm mForm = (SubjectForm) form;
			boolean isMultipart = FileUpload.isMultipartContent(request);
			if (isMultipart && mForm.getUploadFile() != null
					&& mForm.getUploadFile().getFileName().length() > 0) {
				mForm.setImageLoaded(new Boolean(true));
			} else
				mForm.setImageLoaded(new Boolean(false));

			HttpSession session = request.getSession();
			session.setAttribute("FORM", mForm);
		} catch (Exception e) {
			log.error("Printing Stack Trace", e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("unhandled.exception"));
		}
		return mapping.findForward("preview");
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
	public ActionForward showImage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		SubjectForm mform = (SubjectForm) session.getAttribute("FORM");
		FormFile formFile = mform.getUploadFile();
		response.setContentType(formFile.getContentType());
		BufferedOutputStream out = new BufferedOutputStream(response
				.getOutputStream());
		BufferedImage img = ImageIO.read(formFile.getInputStream());
		ImageUtils.getInstance().resizeImage(img, IMAGE_MAX_HEIGHT,
				IMAGE_MAX_WIDTH, out);
		return null;
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
	public ActionForward submitSubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		SubjectForm mForm = (SubjectForm) session.getAttribute("FORM");

		MessagesTO message = new MessagesTO();
		SubjectTO subject = new SubjectTO();

		BeanUtils.copyProperties(message, mForm);
		BeanUtils.copyProperties(subject, mForm);

		String userName = VCRequestHelper.getUser(request);
		VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);

		//Populate SubjectTO
		subject.setCreatorLoginName(userName);
		subject.setUserId(vto.getUserId());
		subject.setSubjectId(GUIDService.getNextGUID());
		//Populate MessageTO
		message.setCreatorLoginName(userName);
		message.setCreatorId(vto.getUserId());
		message.setMessageId(GUIDService.getNextGUID());

		if (mForm.getUploadFile() != null) {
			FormFile file = mForm.getUploadFile();
			String imageId = GUIDService.getNextGUID();
			subject.setImageId(imageId);
			VCFileBO.getInstance().saveVCFile(
					file,
					imageId,
					UserBO.getInstance().getUserByUserName(
							VCRequestHelper.getUser(request)).getUserId(),
					VCFileTypeEnum.INVALID, false);
		}
		//Create the subject NOW.
		createSubject(subject);
		//Save the Message
		MessagesBO.getInstance().saveMessage(subject, message);

		//FOR SOME REASON, THE NEXT ACTION CLASS ISN'T GETTING THE POLLID
		// FROM REQUEST PARAMS
		VCRequestHelper.setValueIntoRequest(request,
				RequestParameterObjects.POLL_ID, mForm.getPollId());
		//Cleanup session
		session.removeAttribute("FORM");

		return mapping.findForward("success");
	}

}