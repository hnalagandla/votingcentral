/*
 * Created on Apr 23, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.user.info;

import java.sql.SQLException;

import com.votingcentral.model.db.dao.IVCUserPrefsDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.VCUserPrefsTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCUserPrefsBO {
	private static VCUserPrefsBO infoBo = null;

	private VCUserPrefsBO() {
	}

	public static VCUserPrefsBO getInstance() {
		if (infoBo == null) {
			infoBo = new VCUserPrefsBO();
		}
		return infoBo;
	}

	public VCUserPrefsTO getVCUserPrefsByUserId(long userId)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserPrefsDAO vdao = dao.getVCUserPrefsDAO();
		return vdao.getUserById(userId);
	}

	public void createVCUserPrefs(VCUserPrefsTO vto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserPrefsDAO vdao = dao.getVCUserPrefsDAO();
		vdao.createUser(vto);
	}

	public void updateVCUserPrefs(VCUserPrefsTO vto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserPrefsDAO vdao = dao.getVCUserPrefsDAO();
		vdao.updateUser(vto);
	}

	public void upsertVCUserPrefs(VCUserPrefsTO vto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCUserPrefsDAO vdao = dao.getVCUserPrefsDAO();
		boolean rowsUpdated = vdao.updateUser(vto);
		if (!rowsUpdated) {
			createVCUserPrefs(vto);
		}
	}
}