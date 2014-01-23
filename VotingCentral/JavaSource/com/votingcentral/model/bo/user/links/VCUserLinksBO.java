/*
 * Created on May 26, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.user.links;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.votingcentral.model.bo.mail.EmailBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.bo.user.info.VCUserInfoBO;
import com.votingcentral.model.bo.vaco.VCVacoPointsBO;
import com.votingcentral.model.db.dao.IVCUserLinksDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.VCUserInfoTO;
import com.votingcentral.model.db.dao.to.VCUserLinksTO;
import com.votingcentral.model.db.dao.to.VCUserTO;
import com.votingcentral.model.enums.VCActivityTypeEnum;
import com.votingcentral.model.enums.VCEmailTypeEnum;
import com.votingcentral.model.enums.VCUserLinkStateEnum;
import com.votingcentral.util.guid.GUIDService;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCUserLinksBO {

	private static VCUserLinksBO linksBo = null;

	public static VCUserLinksBO getInstance() {
		if (linksBo == null) {
			linksBo = new VCUserLinksBO();
		}
		return linksBo;
	}

	private VCUserLinksBO() {

	}

	public List getDirectFriendsByUserId(long userId) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserLinksDAO vdao = dao.getVCUserLinksDAO();
		List results = vdao.getAcceptedLinksByUserId(userId);
		List friends = new ArrayList();
		fillFriends(userId, results, friends);
		return friends;
	}

	public VCUserLinksTO getDirectLinkByUserId(long userIdA, long userIdB)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserLinksDAO vdao = dao.getVCUserLinksDAO();
		VCUserLinksTO vto = vdao.getDirectLinkBetwenUsersByUserId(userIdA,
				userIdB);
		return vto;
	}

	public VCUserLinksTO getDirectLinkByEmail(String emailA, String emailB)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserLinksDAO vdao = dao.getVCUserLinksDAO();
		VCUserLinksTO vto = vdao
				.getDirectLinkBetwenUsersByEmail(emailA, emailB);
		return vto;
	}

	public VCUserLinksTO getDirectLinkByUserName(String userA, String userB)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserLinksDAO vdao = dao.getVCUserLinksDAO();
		VCUserLinksTO vto = vdao.getDirectLinkBetwenUsersByUserName(userA,
				userB);
		return vto;
	}

	public VCUserLinksTO getDirectLinkByUserNameOrEmail(String userA,
			String userB, String emailA, String emailB) throws SQLException {
		VCUserLinksTO vto = getDirectLinkByUserName(userA, userB);
		if (vto == null) {
			vto = getDirectLinkByEmail(emailA, emailB);
		}
		return vto;
	}

	public List getPendingRequestsUsersSentUserId(long userId)
			throws SQLException {
		List results = getPendingRequestsSentUserId(userId);
		List friends = new ArrayList();
		fillFriends(userId, results, friends);
		return friends;
	}

	public List getPendingRequestsSentUserId(long userId) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserLinksDAO vdao = dao.getVCUserLinksDAO();
		return vdao.getPendingLinksRequestedByUserId(userId);
	}

	public List getPendingRequestsUsersReceivedUserId(long userId)
			throws SQLException {
		List results = getPendingRequestsReceivedUserId(userId);
		List friends = new ArrayList();
		fillFriends(userId, results, friends);
		return friends;
	}

	private void fillFriends(long userId, List results, List friends)
			throws SQLException {
		if (results == null) {
			return;
		}
		if (friends == null) {
			friends = new ArrayList();
		}
		for (Iterator itr = results.iterator(); itr.hasNext();) {
			VCUserLinksTO vulto = (VCUserLinksTO) itr.next();
			VCUserInfoTO vito = null;
			VCUserTO vto = null;
			if (vulto.getSourceUserId() == userId) {
				vito = VCUserInfoBO.getInstance().getVCUserInfoByUserId(
						vulto.getDestUserId());
				vto = UserBO.getInstance().getUserByUserId(
						vulto.getDestUserId());
			} else if (vulto.getDestUserId() == userId) {
				vito = VCUserInfoBO.getInstance().getVCUserInfoByUserId(
						vulto.getSourceUserId());
				vto = UserBO.getInstance().getUserByUserId(
						vulto.getSourceUserId());
			}
			if (vto != null && vito != null) {
				vto.setVcUserInfoTo(vito);
			}
			if (vto != null) {
				friends.add(vto);
			}
		}
	}

	public List getPendingRequestsReceivedUserId(long userId)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserLinksDAO vdao = dao.getVCUserLinksDAO();
		return vdao.getPendingLinksRecievedByUserId(userId);
	}

	public void sendInviteToLinkVCUsers(VCUserTO from, VCUserTO to,
			String domContext, String linkComments) throws SQLException,
			UnknownHostException {

		List toAddress = new ArrayList();
		toAddress.add(to.getEmailAddress());

		String toUserName = to.getUserName();
		String acceptCode = GUIDService.getNextGUID();
		String rejectCode = GUIDService.getNextGUID();
		Object[] values = { toUserName, from.getUserName(), linkComments,
				VCURLHelper.getManageConnectionsUrl(domContext) };

		EmailBO.getInstance().createMailRequest(from.getEmailAddress(),
				toAddress, VCEmailTypeEnum.USER_REQUEST_TO_LINK_USER, values);

		VCUserLinksTO vulto = new VCUserLinksTO();
		vulto.setAcceptCode(acceptCode);
		vulto.setDestUserEmail(to.getEmailAddress());
		vulto.setDestUserId(to.getUserId());
		vulto.setDestUserName(to.getUserName());
		vulto.setLinkComments(linkComments);
		vulto.setLinkStateEnum(VCUserLinkStateEnum.INITIATED);
		vulto.setRejectCode(rejectCode);
		vulto.setSourceUserEmail(from.getEmailAddress());
		vulto.setSourceUserId(from.getUserId());
		vulto.setSourceUserName(from.getUserName());
		createLink(vulto);

		// increment vaco points
		VCVacoPointsBO.getInstance().incrementPoints(from.getUserId(),
				VCActivityTypeEnum.VC_POINTS_VC_CONNECT);

	}

	public void sendInviteToLinkVCAndNewUsers(VCUserTO from, String toEmail,
			String domContext, String linkComments) throws SQLException,
			UnknownHostException {

		List toAddress = new ArrayList();
		toAddress.add(toEmail);

		String acceptCode = GUIDService.getNextGUID();
		String rejectCode = GUIDService.getNextGUID();
		Object[] values = {
				toEmail,
				from.getUserName(),
				linkComments,
				VCURLHelper.getVCUserPublicProfileUrl(domContext, from
						.getUserName()),
				VCURLHelper.getRegistrationUrl(domContext),
				VCURLHelper.getManageConnectionsUrl(domContext) };

		EmailBO.getInstance().createMailRequest(from.getEmailAddress(),
				toAddress, VCEmailTypeEnum.USER_REQUEST_TO_LINK_NEW_USER,
				values);

		VCUserLinksTO vulto = new VCUserLinksTO();
		vulto.setAcceptCode(acceptCode);
		vulto.setDestUserEmail(toEmail);
		vulto.setDestUserId(0);
		vulto.setDestUserName(null);
		vulto.setLinkComments(linkComments);
		vulto.setLinkStateEnum(VCUserLinkStateEnum.INITIATED);
		vulto.setRejectCode(rejectCode);
		vulto.setSourceUserEmail(from.getEmailAddress());
		vulto.setSourceUserId(from.getUserId());
		vulto.setSourceUserName(from.getUserName());
		createLink(vulto);

		//increment vaco points
		VCVacoPointsBO.getInstance().incrementPoints(from.getUserId(),
				VCActivityTypeEnum.VC_POINTS_VC_CONNECT);
	}

	public void sendInviteToConnectAccepted(VCUserTO from, String toEmail,
			String domContext, String linkComments) throws SQLException,
			UnknownHostException {

		List toAddress = new ArrayList();
		toAddress.add(toEmail);

		String acceptCode = GUIDService.getNextGUID();
		String rejectCode = GUIDService.getNextGUID();
		Object[] values = {
				toEmail,
				from.getUserName(),
				linkComments,
				VCURLHelper.getVCUserPublicProfileUrl(domContext, from
						.getUserName()),
				VCURLHelper.getRegistrationUrl(domContext),
				VCURLHelper.getManageConnectionsUrl(domContext) };

		EmailBO.getInstance().createMailRequest(from.getEmailAddress(),
				toAddress, VCEmailTypeEnum.USER_REQUEST_TO_LINK_NEW_USER,
				values);

		VCUserLinksTO vulto = new VCUserLinksTO();
		vulto.setAcceptCode(acceptCode);
		vulto.setDestUserEmail(toEmail);
		vulto.setDestUserId(0);
		vulto.setDestUserName(null);
		vulto.setLinkComments(linkComments);
		vulto.setLinkStateEnum(VCUserLinkStateEnum.INITIATED);
		vulto.setRejectCode(rejectCode);
		vulto.setSourceUserEmail(from.getEmailAddress());
		vulto.setSourceUserId(from.getUserId());
		vulto.setSourceUserName(from.getUserName());
		createLink(vulto);

		//increment vaco points
		VCVacoPointsBO.getInstance().incrementPoints(from.getUserId(),
				VCActivityTypeEnum.VC_POINTS_VC_CONNECT);
	}

	public void createLink(VCUserLinksTO vulto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserLinksDAO vdao = dao.getVCUserLinksDAO();
		vdao.createLink(vulto);
	}

	public void updateLink(VCUserLinksTO vulto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserLinksDAO vdao = dao.getVCUserLinksDAO();
		vdao.updateLink(vulto);
	}

	public VCUserLinksTO findByRejectORAcceptCode(String code)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserLinksDAO vdao = dao.getVCUserLinksDAO();
		return vdao.getLinkByAcceptOrRejectCode(code);
	}
}