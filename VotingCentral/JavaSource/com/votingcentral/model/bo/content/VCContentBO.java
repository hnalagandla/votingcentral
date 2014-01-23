/*
 * Created on Jan 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.content;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.IVCContentDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.VCContentTO;
import com.votingcentral.model.search.SearchTablesColumns;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCContentBO {
	private static VCContentBO content = null;

	private VCContentBO() {
	}

	private static Log log = LogFactory.getLog(VCContentBO.class);

	public static VCContentBO getInstance() {
		if (content == null) {
			content = new VCContentBO();
		}
		return content;
	}

	public void upsertContent(VCContentTO vcto) {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCContentDAO vcdao = dao.getVCContentDAO();
		try {
			boolean updateSuccess = vcdao.updateContent(vcto);
			if (!updateSuccess) {
				vcdao.insertContent(vcto);
			}
		} catch (SQLException e) {
			log.error("SQL exception trying to upsert", e);
		}
	}

	public int deleteContentByPollId(String pollId) {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCContentDAO vcdao = dao.getVCContentDAO();
		VCContentTO vcto = new VCContentTO();
		try {
			vcto.setWhereId1(SearchTablesColumns.POLL_ID);
			vcto.setWhereValue1(pollId);
			return vcdao.deleteContent(vcto);
		} catch (SQLException e) {
			log.error("SQL exception trying to delete", e);
		}
		return 0;
	}
}