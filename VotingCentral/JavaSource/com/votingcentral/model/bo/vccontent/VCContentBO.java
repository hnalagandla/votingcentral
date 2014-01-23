/*
 * Created on Jun 10, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.vccontent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

	/**
	 *  
	 */
	private VCContentBO() {
	}

	public static VCContentBO getInstance() {
		if (content == null) {
			content = new VCContentBO();
		}
		return content;
	}

	public List getSearchResults(String searchString, long afterMaxId)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IVCContentDAO vdao = dao.getVCContentDAO();
		return vdao.search(searchString, afterMaxId);
	}

	public Map getGroupedSearchResults(String searchString, long afterMaxId)
			throws SQLException {
		List results = getSearchResults(searchString, afterMaxId);
		Map newRes = new LinkedHashMap();
		for (Iterator itr = results.iterator(); itr.hasNext();) {
			VCContentTO vcto = (VCContentTO) itr.next();
			if (vcto.getTableName().equalsIgnoreCase(SearchTablesColumns.POLLS)
					&& (vcto.getWhereId1()
							.equalsIgnoreCase(SearchTablesColumns.POLL_ID))) {
				String pollId = vcto.getWhereValue1();
				List polls = (List) newRes.get(pollId);
				if (polls == null) {
					polls = new ArrayList();
				}
				polls.add(vcto);
				newRes.put(vcto.getWhereValue1(), polls);
			} else if (vcto.getTableName().equalsIgnoreCase(
					SearchTablesColumns.POLL_COMMENTS)
					&& (vcto.getWhereId1()
							.equalsIgnoreCase(SearchTablesColumns.POLL_ID))) {
				String pollId = vcto.getWhereValue1();
				List polls = (List) newRes.get(pollId);
				if (polls == null) {
					polls = new ArrayList();
				}
				polls.add(vcto);
				newRes.put(vcto.getWhereValue1(), polls);
			}
		}
		return newRes;
	}
}