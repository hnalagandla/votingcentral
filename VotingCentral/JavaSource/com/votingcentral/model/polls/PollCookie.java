/*
 * Created on Sep 17, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.polls;

import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PollCookie {

	Cookie ck = null;

	HttpServletRequest request;

	HttpServletResponse response;

	String cookieName = "VotingCentral";

	public PollCookie(HttpServletRequest req, HttpServletResponse res,
			String loginName) {
		request = req;
		response = res;
		String name = cookieName;
		String tmp, pId;
		String pollVotes = "$"; // starting $

		if (loginName != "") {
			name = name + loginName;
		}

		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(name)) {
					StringTokenizer st = new StringTokenizer(cookies[i]
							.getValue().substring(1), "$");
					while (st.hasMoreTokens()) {
						pId = st.nextToken();
						tmp = "$" + pId + "$";
						if (pollVotes.indexOf(tmp) == -1)
							pollVotes += pId + "$";
					}
				}
			}
			if (!pollVotes.equals("$")) { // at least 1 poll id was added
				ck = new Cookie(name, pollVotes);
			}
		}
	}

	public boolean getAlreadyVoted(String pollId) {

		if (ck == null) {
			return false;
		}
		return (ck.getValue().indexOf("$" + pollId + "$") != -1);
	}

	/*
	 * Add a new pollId to the votingcentral cookie. Pass in null for the domain
	 * is you wish to use the server host/port.
	 */
	public void addVoteRecord(String pollId, String domain, int age,
			String loginName) {

		String name = cookieName;

		if (ck == null) {
			if (loginName != "") {
				name = name + loginName;
			}
			ck = new Cookie(name, "$" + pollId + "$");
		} else if (!getAlreadyVoted(pollId)) {
			ck.setValue(ck.getValue() + pollId + "$");
		}
		if (domain != null) { //let the page override the default
			ck.setDomain(domain);
		}

		ck.setPath("/"); // I could use /poll, but that would
		// make debugging more difficult.
		ck.setMaxAge(age);
		response.addCookie(ck);
	}
}