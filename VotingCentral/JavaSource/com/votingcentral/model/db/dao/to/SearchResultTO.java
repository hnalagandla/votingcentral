/*
 * Created on Jan 10, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.votingcentral.env.EnvProps;
import com.votingcentral.model.bo.messageboard.MessagesBO;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.polls.PollHelper;
import com.votingcentral.model.search.SearchTablesColumns;
import com.votingcentral.pres.web.to.PollWTO;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SearchResultTO {

	private List textsLinksDescs;

	private PollWTO pollWto;

	private static final String REG_EXP_SPACE = "\\s+";

	private final static String POLL_NAME = "Poll Name";

	private final static String POLL_DESC = "Poll Desc";

	private final static String POLL_KEYWORDS = "Keywords";

	private final static String POLL_QANDA = "Poll Q&A";

	private final static String POLL_COMMENTS = "Comments";

	private final static String POLL_MESSAGE_BOARD = "Message Board";

	/**
	 *  
	 */
	public SearchResultTO(String domainContext, VCContentTO vcto,
			String searchStr) {
		initialize(domainContext, searchStr, vcto);
	}

	public SearchResultTO(String domainContext, List vctos, String searchStr,
			String pollId) {
		PollTO pto = null;
		try {
			pto = PollBO.getInstance().getPollByPollId(pollId);
		} catch (SQLException e) {

		}
		pollWto = new PollWTO();
		PollHelper.fillPollWTO(domainContext, "1", pollWto, pto, 35, 1, 1);
		for (Iterator itr = vctos.iterator(); itr.hasNext();) {
			VCContentTO vcto = (VCContentTO) itr.next();
			initialize(domainContext, searchStr, vcto);
		}
	}

	private void initialize(String domainContext, String searchStr,
			VCContentTO vcto) {
		if (textsLinksDescs == null) {
			textsLinksDescs = new ArrayList();
		}
		TextLinkDescTO tld = getLinksAndDesc(searchStr, domainContext, vcto);
		textsLinksDescs.add(tld);
	}

	private TextLinkDescTO getLinksAndDesc(String searchStr,
			String domainContext, VCContentTO vcto) {
		PollTO pto = null;
		// remove leading and trailing spaces.
		searchStr = StringUtils.strip(searchStr);
		String[] searchStrWords = searchStr.split(REG_EXP_SPACE);

		TextLinkDescTO tld = new TextLinkDescTO();
		if (vcto.getTableName().equalsIgnoreCase(SearchTablesColumns.POLLS)
				|| vcto.getTableName().equalsIgnoreCase(
						SearchTablesColumns.POLL_COMMENTS)) {
			String pollId = vcto.getWhereValue1();
			try {
				pto = PollBO.getInstance().getPollByPollId(pollId);
				pollWto = new PollWTO();
				PollHelper.fillPollWTO(domainContext, "1", pollWto, pto, 35, 0, 1);
			} catch (SQLException e) {
				// ignore the search result
				// something not right
				return null;
			}
			if (vcto.getColumnName().equalsIgnoreCase(
					SearchTablesColumns.POLL_NAME)) {

				tld.setDesc(POLL_NAME);
				tld.setText(getHighLightedName(pto.getPollName(),
						searchStrWords));
				tld.setHref(VCURLHelper.getDisplayPollUrl(domainContext, pto
						.getPollId()));
			} else if (vcto.getColumnName().equalsIgnoreCase(
					SearchTablesColumns.POLL_DESC)) {

				tld.setDesc(POLL_DESC);
				tld.setText(getHighLightedName(pto.getPollDesc(),
						searchStrWords));
				tld.setHref(VCURLHelper.getDisplayPollUrl(domainContext, pto
						.getPollId()));
			} else if (vcto.getColumnName().equalsIgnoreCase(
					SearchTablesColumns.POLL_KEYWORDS)) {

				tld.setDesc(POLL_KEYWORDS);
				tld.setText(getHighLightedName(pto.getKeywords(),
						searchStrWords));
				tld.setHref(VCURLHelper.getDisplayPollUrl(domainContext, pto
						.getPollId()));
			} else if (vcto.getColumnName().equalsIgnoreCase(
					SearchTablesColumns.POLL_DATA)) {
				String cleanStr = cleanUpQAndAString(vcto.getContent());
				tld.setDesc(POLL_QANDA);
				tld.setText(getHighLightedName(cleanStr, searchStrWords));
				tld.setHref(VCURLHelper.getDisplayPollUrl(domainContext, pto
						.getPollId()));
			} else if (vcto.getColumnName().equalsIgnoreCase(
					SearchTablesColumns.COMMENT_TEXT)) {
				tld.setDesc(POLL_COMMENTS);
				tld.setText(getHighLightedName(vcto.getContent(),
						searchStrWords));
				tld.setHref(VCURLHelper.getDisplayPollUrl(domainContext, pto
						.getPollId()));
			}
		} else if (vcto.getTableName().equalsIgnoreCase(
				SearchTablesColumns.MESSAGE)) {
			if (vcto.getColumnName().equalsIgnoreCase(
					SearchTablesColumns.MESSAGE)) {
				MessagesTO mto = MessagesBO.getInstance().getMessage(
						vcto.getWhereValue1());
				tld.setDesc(POLL_MESSAGE_BOARD);
				tld.setText(getHighLightedName(vcto.getContent(),
						searchStrWords));
				// TODO move this BO.
				UnSyncStringBuffer displayMBURL = new UnSyncStringBuffer();
				displayMBURL.append(EnvProps.getProperty("app.server.url"))
						.append("/").append(
								EnvProps.getProperty("dev.web.context"))
						.append("/msgboard/showDiscussBoard.").append(
								EnvProps.getProperty("dev.web.extension"))
						.append("do?subjectId=").append(mto.getSubjectId());
				tld.setHref(displayMBURL.toString());

			}
		}
		return tld;
	}

	private String cleanUpQAndAString(String content) {
		String result = StringUtils.replace(content,
				PollBO.A_AND_A_SEPARATOR, " ");
		result = StringUtils.replace(result, PollBO.A_AND_Q_SEPARATOR,
				" ");
		result = StringUtils.replace(result, PollBO.Q_AND_A_SEPARATOR,
				" ");
		return result;
	}

	private String getHighLightedName(String str, String[] search) {
		String result = str;
		for (int i = 0; i < search.length; i++) {
			String buff = "<span style='background:#CCDEF1;'>" + search[i]
					+ "</span>";
			result = StringUtils.replace(result, search[i], buff);
		}
		return result;
	}

	/**
	 * @return Returns the textsLinksDescs.
	 */
	public List getTextsLinksDescs() {
		return textsLinksDescs;
	}

	/**
	 * @param textsLinksDescs
	 *            The textsLinksDescs to set.
	 */
	public void setTextsLinksDescs(List textsLinksDescs) {
		this.textsLinksDescs = textsLinksDescs;
	}

	/**
	 * @return Returns the pollWto.
	 */
	public PollWTO getPollWto() {
		return pollWto;
	}

	/**
	 * @param pollWto
	 *            The pollWto to set.
	 */
	public void setPollWto(PollWTO pollWto) {
		this.pollWto = pollWto;
	}
}