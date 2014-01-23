/*
 * Created on Aug 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.actions.rss;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.sun.syndication.feed.WireFeed;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.WireFeedOutput;
import com.votingcentral.actions.VCDispatchAction;
import com.votingcentral.env.EnvProps;
import com.votingcentral.model.bo.polls.PollBO;
import com.votingcentral.model.bo.user.UserBO;
import com.votingcentral.model.db.dao.to.PollTO;
import com.votingcentral.util.request.VCRequestHelper;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RSSFileGenaratorAction extends VCDispatchAction {

	private static Log log = LogFactory.getLog(RSSFileGenaratorAction.class);

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages errors = new ActionMessages();
		ActionForward forward = new ActionForward(); // return value
		String next = "rss";
		String domContext = VCRequestHelper.getDomainAndContext(request);

		SyndFeed feed = new SyndFeedImpl(); // create the feed
		Date publishDate = new Date(System.currentTimeMillis());
		feed.setLink(EnvProps.getProperty("full.app.server.url"));
		feed.setTitle("VotingCentral.com");
		feed
				.setDescription("Voting is a very powerful concept."
						+ "When everyone comes together to discuss and vote on various current issues"
						+ " it is not only educational but also has a potential to bring about a sea of change. "
						+ "It is a transparent polling and discussion platform which can facilitate democratic thinking.");
		feed.setLanguage("en-us");
		feed.setPublishedDate(publishDate);
		feed.setFeedType("rss_2.0"); // set the type of your feed
		feed
				.setCopyright("Copyright (c) VotingCentral.com Inc. All rights reserved.");

		List activePolls = PollBO.getInstance().getAllActivePolls();
		List entries = new ArrayList();

		if (activePolls != null && activePolls.size() > 0) {
			for (Iterator itr = activePolls.iterator(); itr.hasNext();) {
				PollTO pto = (PollTO) itr.next();
				SyndEntry entry = new SyndEntryImpl(); // create a feed entry
				entry.setTitle(pto.getPollName());
				entry.setPublishedDate(publishDate);
				entry.setAuthor(UserBO.getInstance().getUserByUserId(
						pto.getUserId()).getDisplayUserName());

				entry.setLink(VCURLHelper.getDisplayPollUrl(domContext, pto
						.getPollId()));
				entry.setPublishedDate(pto.getStartTimestamp());
				entry.setUri(VCURLHelper.getDisplayPollUrl(domContext, pto
						.getPollId()));

				//set categories
				List cats = new ArrayList();
				SyndCategory syndCat = new SyndCategoryImpl();
				syndCat.setName(pto.getPollCategory1());
				cats.add(syndCat);
				entry.setCategories(cats);

				//create the content of your entry
				SyndContent content = new SyndContentImpl();
				content.setType("text/html");
				content.setValue(pto.getPollDesc());
				entry.setDescription(content);
				entries.add(entry);
			}
		}
		PrintWriter wr = response.getWriter();

		feed.setEntries(entries); // you can add multiple entries in your feed
		WireFeed wf = feed.createWireFeed();
		WireFeedOutput wfo = new WireFeedOutput();
		wfo.output(wf, wr);
		// Finish with
		return null;
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
		return show(mapping, form, request, response);
	}
}