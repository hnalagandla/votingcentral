package com.votingcentral.actions.contests;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.votingcentral.forms.contests.ContestForm;
import com.votingcentral.model.bo.category.CategoryBO;
import com.votingcentral.model.bo.contests.ContestsBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.vcfile.VCFileBO;
import com.votingcentral.model.db.dao.to.CategoryTO;
import com.votingcentral.model.db.dao.to.ContestEntryTO;
import com.votingcentral.model.db.dao.to.TextLinkDescTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.ContestFileStatusEnum;
import com.votingcentral.model.enums.VCCategoryTypeEnum;
import com.votingcentral.model.enums.VCFileTypeEnum;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.guid.GUIDService;
import com.votingcentral.util.pictures.ImageUtils;
import com.votingcentral.util.request.RequestParameterObjects;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.FastURLEncoder;
import com.votingcentral.util.url.PresentationConstants;
import com.votingcentral.util.url.VCURLHelper;

public class ContestsUploadAction extends VCDispatchAction {

	private int NUM_POTENTIAL_COMPETEITOR_PICS = 5;

	private long MAX_VC_FILE_SIZE = 1048576;

	public ActionForward mainView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "uploadByType";
		String domContext = getDomainAndContext(request);
		ContestForm cForm = (ContestForm) form;
		String type = getValidatedContestType(cForm.getContestType());
		cForm.setContestType(type);

		String userName = VCRequestHelper.getUser(request);
		VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
		prepareForDisplay(cForm, request, domContext, vto);
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

	private void prepareForDisplay(ContestForm cForm,
			HttpServletRequest request, String domContext, VCUserTO vto) {
		// get the potential competeitors
		List cEntryTos = null;
		try {
			cEntryTos = ContestsBO.getInstance()
					.getContestEntriesNotInUserByType(vto.getUserId(),
							cForm.getContestType(),
							NUM_POTENTIAL_COMPETEITOR_PICS);
		} catch (IOException e) {
			// ignore
		} catch (SQLException e) {
			// ignore
		}
		if (cEntryTos != null) {
			for (Iterator itr = cEntryTos.iterator(); itr.hasNext();) {
				ContestEntryTO cto = (ContestEntryTO) itr.next();
				cto.setMaxImageUrl(VCURLHelper.getDisplayImageUrl(domContext,
						cto.getFileId(), ImageUtils.MAX_W, ImageUtils.MAX_H));
				cto.setMinImageUrl(VCURLHelper.getDisplayImageUrl(domContext,
						cto.getFileId(), ImageUtils.MIN_W, ImageUtils.MIN_H));
			}
			// if the size is zero set that to null so that it does not show up
			// in the UI
			cForm
					.setCompeteitorEntries(cEntryTos.size() > 0 ? cEntryTos
							: null);
		}
		// get any pictures already uploaded by this user that have not been
		// selected in contests yet.
		List userEntries = null;
		try {
			userEntries = ContestsBO.getInstance()
					.getContestEntriesByContestTypeFileStatusUserId(
							cForm.getContestType(),
							ContestFileStatusEnum.APPROVED, vto.getUserId());
		} catch (SQLException e) {
			// ignore
		} catch (IOException e) {
			// ignore
		}
		if (userEntries != null) {
			for (Iterator itr = userEntries.iterator(); itr.hasNext();) {
				ContestEntryTO cto = (ContestEntryTO) itr.next();
				cto.setMaxImageUrl(VCURLHelper.getDisplayImageUrl(domContext,
						cto.getFileId(), ImageUtils.MAX_W, ImageUtils.MAX_H));
				cto.setMinImageUrl(VCURLHelper.getDisplayImageUrl(domContext,
						cto.getFileId(), ImageUtils.MIN_W, ImageUtils.MIN_H));
			}
			// if the size is zero set that to null so that it does not show up
			// in the UI
			cForm.setUserEntries(userEntries.size() > 0 ? userEntries : null);
		}
		// generate upload URLs for other contest categories.
		cForm.setOtherUploadUrls(getOtherUploadUrls(domContext, cForm
				.getContestType()));

	}

	private List getOtherUploadUrls(String domContext, String currType) {
		List otherUrls = new ArrayList();
		List catTos = null;
		try {
			catTos = CategoryBO.getInstance().getListOfSuperCategories(
					VCCategoryTypeEnum.CONTEST);
		} catch (SQLException e) {
			// return a blank list on exception
			return otherUrls;
		}

		for (Iterator itr = catTos.iterator(); itr.hasNext();) {
			CategoryTO cto = (CategoryTO) itr.next();
			if (!cto.getSuperCategory().equalsIgnoreCase(currType)) {
				String url = VCURLHelper.getContestsUploadByTypeUrl(domContext,
						cto.getSuperCategory());
				TextLinkDescTO tldto = new TextLinkDescTO();
				tldto.setHref(url);
				tldto.setDesc(cto.getSuperCategory());
				tldto.setText(cto.getSuperCategory());
				otherUrls.add(tldto);
			}
		}
		return otherUrls;
	}

	private String getValidatedContestType(String currType) {
		boolean flag = false;
		try {
			flag = CategoryBO.getInstance().isValidCategory(
					VCCategoryTypeEnum.CONTEST, currType);
		} catch (SQLException e) {
			// ignore, assume it is invalid
		}
		if (!flag) {
			return "Hot Guy";
		} else {
			return currType;
		}
	}

	public ActionForward uploadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = null; // return value
		String next = "success";
		ContestForm cForm = (ContestForm) form;
		String fileId = "";
		String domContext = getDomainAndContext(request);
		try {
			String userName = VCRequestHelper.getUser(request);
			VCUserTO vto = UserBO.getInstance().getUserByUserName(userName);
			boolean isMultipart = FileUpload.isMultipartContent(request);
			validate(cForm, request, errors);
			if (errors.size() > 0) {
				saveErrors(request, errors);
				return mainView(mapping, form, request, response);
			}
			if (isMultipart) {
				FormFile file = cForm.getUploadFileName();
				fileId = GUIDService.getNextGUID();
				VCFileBO.getInstance().saveVCFile(file, fileId,
						vto.getUserId(), VCFileTypeEnum.CONTEST, false);
				ContestEntryTO cto = new ContestEntryTO();
				cto.setFileId(fileId);
				cto.setUserId(vto.getUserId());
				cto.setUserComments(cForm.getUserComments());
				cto.setKeywords(cForm.getKeywords());
				cto.setContestType(cForm.getContestType());
				cto.setFileStatus(ContestFileStatusEnum.APPROVED);
				ContestsBO.getInstance().addContestEntry(cto);
			}
		} catch (Exception e) {
			next = "mainView";
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
		UnSyncStringBuffer buffer = new UnSyncStringBuffer();
		buffer.append(forward.getPath())
				.append(PresentationConstants.AMPERSAND).append(
						RequestParameterObjects.FILE_ID).append(
						PresentationConstants.EQUALS).append(fileId);
		buffer.append(PresentationConstants.AMPERSAND).append("contestType")
				.append(PresentationConstants.EQUALS).append(
						FastURLEncoder.encode(cForm.getContestType()));
		ActionForward newForward = new ActionForward();
		newForward.setPath(buffer.toString());
		newForward.setName(forward.getName());
		newForward.setRedirect(true);
		return newForward;
	}

	public ActionForward uploadFileDone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = new ActionForward(); // return value
		String next = "showFileUpload";
		if (next.equalsIgnoreCase("showFileUpload")) {
			return mainView(mapping, form, request, response);
		}
		// Write logic determining how the user should be forwarded.
		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	public ActionForward showSuccess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = new ActionForward(); // return value
		ContestForm cForm = (ContestForm) form;
		String next = "success";
		String fileId = cForm.getFileId();
		String domContext = getDomainAndContext(request);
		// set URLs to show on the success page.
		cForm.setVcHomePageURL(VCURLHelper.getHomePageUrl(domContext));
		cForm.setContestsMainURL(VCURLHelper.getContestsMainUrl(domContext));
		cForm.setMaxImageUrl(VCURLHelper.getDisplayImageUrl(domContext, fileId,
				ImageUtils.MAX_W, ImageUtils.MAX_H));
		cForm.setMinImageUrl(VCURLHelper.getDisplayImageUrl(domContext, fileId,
				ImageUtils.MIN_W, ImageUtils.MIN_H));
		// generate upload URLs for other contest categories.
		cForm.setOtherUploadUrls(ContestsBO.getInstance()
				.getAllContestsUploadUrls(domContext));

		forward = mapping.findForward(next);
		log.debug("forward:" + forward.getPath());
		// Finish with
		return (forward);
	}

	private void validate(ContestForm form, HttpServletRequest request,
			ActionMessages errors) {
		if (form.getContestType()
				.equalsIgnoreCase(CategoryBO.SELECT_A_CATEGORY)) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.field.required", "Contest Type"));
		}
		if (form.getUserComments() == null
				|| form.getUserComments().length() == 0) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.field.required", "Comments"));
		}
		if (form.getKeywords() == null || form.getKeywords().length() == 0) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.field.required", "Tags/Keywords"));
		}
		if (form.getUploadFileName() == null
				|| form.getUploadFileName().getFileName().length() == 0) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.field.required", "File Name"));
		}
		// has the maximum length been exceeded?
		Boolean maxLengthExceeded = (Boolean) request
				.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);

		if ((maxLengthExceeded != null) && (maxLengthExceeded.booleanValue())
				|| form.getUploadFileName().getFileSize() > MAX_VC_FILE_SIZE) {
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"error.upload.file.size.too.large", "1024"));
		}
		if (FileUpload.isMultipartContent(request)
				&& form.getUploadFileName() != null
				&& form.getUploadFileName().getFileName().length() > 0) {
			if (!ImageUtils.getInstance().isVCSupportedMimeType(
					form.getUploadFileName().getContentType())) {
				errors.add("answer",
						new org.apache.struts.action.ActionMessage(
								"vc.file.upload.unsupported.image.format",
								ImageUtils.getInstance()
										.getVCSupportedFileFormats()));
			}
		}
	}

	protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mainView(mapping, form, request, response);
	}
}