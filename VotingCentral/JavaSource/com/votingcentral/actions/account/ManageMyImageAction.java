/*
 * Created on Apr 24, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.user.ManageMyImageForm;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.user.info.VCUserInfoBO;
import com.votingcentral.model.bo.vcfile.VCFileBO;
import com.votingcentral.model.db.dao.to.VCUserInfoTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.VCFileTypeEnum;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.util.guid.GUIDService;
import com.votingcentral.util.pictures.ImageUtils;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ManageMyImageAction extends VCDispatchAction {

	public ActionForward showImage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "myimage";
		String domContext = getDomainAndContext(request);
		ManageMyImageForm myImageForm = (ManageMyImageForm) form;
		try {
			String userName = VCRequestHelper.getUser(request);
			VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
			VCUserInfoTO vuto = VCUserInfoBO.getInstance()
					.getVCUserInfoByUserId(vto.getUserId());
			if (vuto != null && vuto.getImageId() != null
					&& vuto.getImageId().length() > 0) {
				String maxImgUrl = VCURLHelper.getDisplayImageUrl(domContext,
						vuto.getImageId(), ImageUtils.MAX_W, ImageUtils.MAX_H);
				myImageForm.setMaxImageUrl(maxImgUrl);

				String minImgUrl = VCURLHelper.getDisplayImageUrl(domContext,
						vuto.getImageId(), ImageUtils.MIN_W, ImageUtils.MIN_H);
				myImageForm.setMinImageUrl(minImgUrl);

				String delImageUrl = VCURLHelper.getDeleteMyImageUrl(
						domContext, vuto.getImageId());
				myImageForm.setDeleteImageUrl(delImageUrl);

				myImageForm.setImageId(vuto.getImageId());

			} else {
				//set a default image URL.
				myImageForm.setMinImageUrl("ask prateek for a default image.");
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

	public ActionForward upload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages errors = new ActionMessages();
		ActionForward forward = null; // return value
		String next = "self";
		ManageMyImageForm mForm = (ManageMyImageForm) form;
		String fileId = mForm.getImageId();
		String domContext = getDomainAndContext(request);
		try {
			String userName = VCRequestHelper.getUser(request);
			VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
			boolean isMultipart = FileUpload.isMultipartContent(request);
			boolean isImagePublic = true;
			validate(mForm, request, errors);
			if (errors.size() > 0) {
				saveErrors(request, errors);
				return showImage(mapping, form, request, response);
			}
			if (isMultipart) {
				FormFile file = mForm.getUploadFileName();
				if (fileId != null && fileId.length() > 0) {
					VCFileBO.getInstance().saveVCFile(file, fileId,
							vto.getUserId(), VCFileTypeEnum.MY_IMAGE, true);
				} else {
					fileId = GUIDService.getNextGUID();
					VCFileBO.getInstance().saveVCFile(file, fileId,
							vto.getUserId(), VCFileTypeEnum.MY_IMAGE, false);
				}
				VCUserInfoBO.getInstance().setImageId(vto.getUserId(), fileId,
						isImagePublic);
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
		forward = mapping.findForward(next);
		ActionForward newFwd = new ActionForward();
		newFwd.setRedirect(true);
		newFwd.setPath(forward.getPath());
		// Write logic determining how the user should be forwarded.
		return newFwd;
	}

	private void validate(ManageMyImageForm form, HttpServletRequest request,
			ActionMessages errors) {

		if (form.getUploadFileName() == null
				|| form.getUploadFileName().getFileName().length() == 0) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.field.required", "File Name"));
		}
		if (FileUpload.isMultipartContent(request)
				&& form.getUploadFileName() != null
				&& form.getUploadFileName().getFileName().length() > 0) {
			if (!ImageUtils.getInstance().isVCSupportedMimeType(
					form.getUploadFileName().getContentType())) {
				errors.add("image", new org.apache.struts.action.ActionMessage(
						"vc.file.upload.unsupported.image.format", ImageUtils
								.getInstance().getVCSupportedFileFormats()));
			}
		}
		//		 has the maximum length been exceeded?
		Boolean maxLengthExceeded = (Boolean) request
				.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);

		if ((maxLengthExceeded != null)
				&& (maxLengthExceeded.booleanValue())
				|| form.getUploadFileName().getFileSize() > PollHelper.MAX_VC_FILE_SIZE) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.upload.file.size.too.large", "1024"));
		}
	}

	public ActionForward deleteImage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		ManageMyImageForm mForm = (ManageMyImageForm) form;
		String imageId = mForm.getImageId();
		String next = "self";
		try {
			long creatorId = UserBO.getInstance().getUserByUserName(
					VCRequestHelper.getUser(request)).getUserId();
			VCUserInfoTO vuto = VCUserInfoBO.getInstance()
					.getVCUserInfoByUserId(creatorId);
			if (vuto != null && vuto.getImageId() != null
					&& vuto.getImageId().equals(imageId)) {
				VCFileBO.getInstance().deleteVCFile(imageId);
				VCUserInfoBO.getInstance().setImageId(creatorId, null, false);
			}
		} catch (Exception e) {
			log.error(e);
			// Report the error using the appropriate name and ID.
			errors.add("name", new ActionMessage("generic.exception", e
					.getMessage()));
		}
		//If a message is required, save the specified key(s)
		// into the request for use by the <struts:errors> tag.
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		ActionForward newFwd = new ActionForward();
		newFwd.setRedirect(true);
		newFwd.setPath(forward.getPath());
		return newFwd;
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
		return showImage(mapping, form, request, response);
	}
}