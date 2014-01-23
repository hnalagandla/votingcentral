/*
 * Created on May 22, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao;

import java.sql.SQLException;
import java.util.List;

import com.votingcentral.model.db.dao.to.VCUserLinksTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface IVCUserLinksDAO {
	public List getAllLinksByUserId(long userId) throws SQLException;

	public List getAcceptedLinksByUserId(long userIdA) throws SQLException;

	public List getAllLinksRequestedByUserId(long userId) throws SQLException;

	public List getPendingLinksRequestedByUserId(long userId)
			throws SQLException;

	public List getAllLinksRecievedByUserId(long userId) throws SQLException;

	public List getPendingLinksRecievedByUserId(long userId)
			throws SQLException;

	public void createLink(VCUserLinksTO vulto) throws SQLException;

	public void updateLink(VCUserLinksTO vulto) throws SQLException;

	public VCUserLinksTO getDirectLinkBetwenUsersByUserId(long userIdA,
			long userIdB) throws SQLException;

	public VCUserLinksTO getDirectLinkBetwenUsersByUserName(String userNameA,
			String userNameB) throws SQLException;

	public VCUserLinksTO getDirectLinkBetwenUsersByEmail(String emailA,
			String emailB) throws SQLException;

	public VCUserLinksTO getLinkByAcceptOrRejectCode(String code)
			throws SQLException;

}