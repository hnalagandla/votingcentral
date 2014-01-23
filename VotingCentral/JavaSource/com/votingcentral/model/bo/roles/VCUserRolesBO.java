/*
 * Created on Nov 28, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.roles;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import com.votingcentral.model.db.dao.IVCUserRolesDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.to.VCUserRolesTO;
import com.votingcentral.model.enums.VCUserRolesEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCUserRolesBO {

	private static VCUserRolesBO userRoles = null;

	private VCUserRolesBO() {

	}

	public static VCUserRolesBO getInstance() {
		if (userRoles == null) {
			userRoles = new VCUserRolesBO();
		}
		return userRoles;
	}

	public List getUserRoles(String userName) throws SQLException {
		VCDAOFactory factory = new VCDAOFactory();
		IVCUserRolesDAO dao = factory.getVCUserRolesDAO();
		return dao.getUserRoles(userName);
	}

	public void addUserRole(String userName, VCUserRolesEnum role)
			throws SQLException {
		VCDAOFactory factory = new VCDAOFactory();
		IVCUserRolesDAO dao = factory.getVCUserRolesDAO();
		List userRoles = dao.getUserRoles(userName);
		//check if the roll already exists for the user and skip
		for (Iterator itr = userRoles.iterator(); itr.hasNext();) {
			VCUserRolesTO vto = (VCUserRolesTO) itr.next();
			if (vto.getRole().equalsIgnoreCase(role.getName())) {
				return;
			}
		}
		dao.addUserRole(userName, role.getName());
	}

	public void deleteUserRole(String userName, VCUserRolesEnum role)
			throws SQLException {
		VCDAOFactory factory = new VCDAOFactory();
		IVCUserRolesDAO dao = factory.getVCUserRolesDAO();
		dao.deleteUserRole(userName, role.getName());
	}
}