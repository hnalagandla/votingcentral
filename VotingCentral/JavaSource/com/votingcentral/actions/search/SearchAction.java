/*
 * Created on Aug 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.forms.search.AdvanceSearchFormBean;
import com.votingcentral.model.bo.vccontent.VCContentBO;
import com.votingcentral.model.db.dao.to.SearchResultTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SearchAction extends VCDispatchAction {

	private final static Log log = LogFactory.getLog(SearchAction.class);

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "search";
		AdvanceSearchFormBean srchFormBean = (AdvanceSearchFormBean) form;
		String searchStr = srchFormBean.getSearchString();
		List searchResults = new ArrayList();
		if (searchStr != null && searchStr.length() > 0) {
			try {
				Map results = VCContentBO.getInstance()
						.getGroupedSearchResults(
								srchFormBean.getSearchString(),
								srchFormBean.getAfterMaxId());
				for (Iterator itr = results.keySet().iterator(); itr.hasNext();) {
					String pollId = (String) itr.next();
					List vctos = (List) results.get(pollId);
					SearchResultTO sto = new SearchResultTO(
							getDomainAndContext(request), vctos, srchFormBean
									.getSearchString(), pollId);
					searchResults.add(sto);
				}
				srchFormBean.setResults(searchResults);
				srchFormBean.setResultsCount(String.valueOf(searchResults
						.size()));
			} catch (Exception e) {
				log.error(e);
				// Report the error using the appropriate name and ID.
				errors.add("name", new ActionMessage("generic.exception", e
						.getMessage()));
			}
		} else {
			//no search string
		}
		//If a message is required, save the specified key(s)
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
		return search(mapping, form, request, response);
	}
}