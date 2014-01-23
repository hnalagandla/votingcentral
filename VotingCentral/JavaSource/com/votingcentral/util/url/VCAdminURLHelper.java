/*
 * Created on Aug 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.url;

import com.votingcentral.util.UnSyncStringBuffer;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCAdminURLHelper {

	public static String getAdminMainUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		url.append(domainContext).append("/admin/main.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public static String getAdminMainContestsUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		url.append(domainContext).append("/admin/contests.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public static String getAdminMainPollsUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		url.append(domainContext).append("/admin/polls.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

	public static String getAdminMainUsersUrl(String domainContext) {
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		url.append(domainContext).append("/admin/users.").append(
				PresentationConstants.WEB_EXTN);
		return url.toString();
	}

}