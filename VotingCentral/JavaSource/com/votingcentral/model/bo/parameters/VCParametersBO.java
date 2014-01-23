/*
 * Created on Oct 7, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.parameters;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.IVCParametersDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.VCParameterTO;
import com.votingcentral.model.enums.VCParametersEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCParametersBO {
	private static Log log = LogFactory.getLog(VCParametersBO.class);

	private static VCParametersBO pBO = null;

	private VCParametersBO() {

	}

	public static VCParametersBO getInstance() {
		if (pBO == null) {
			pBO = new VCParametersBO();
		}
		return pBO;
	}

	public String getParameterByName(VCParametersEnum paramter)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCParametersDAO pdao = dao.getVCParametersDAO();
		try {
			VCParameterTO vto = pdao.getParameterByName(paramter.getName());
			if (vto != null) {
				return vto.getParameterValue();
			}
		} catch (SQLException e) {
			log.error("Exception retrieving parameter", e);
		}
		return "";
	}

	public void setParameterByName(VCParametersEnum paramterName,
			String paramValue) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCParametersDAO pdao = dao.getVCParametersDAO();
		pdao.setParameter(paramterName.getName(), paramValue);
	}
}