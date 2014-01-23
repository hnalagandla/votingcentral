/*
 * Created on Jan 24, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.cookies;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gandhari
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

public class VCCookieHandler {

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public static VCCookie getCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		VCCookie vcCookie = null;

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName()
						.equalsIgnoreCase(VCCookie.VCCOOKIENAME)) {
					vcCookie = new VCCookie(cookies[i]);
					break;
				}
			}
		}
		//If VCCookie is not found, create a new one.
		if (vcCookie == null) {
			vcCookie = new VCCookie();
		}
		//Always set it back into the response
		//setCookie(response, vcCookie);
		return vcCookie;
	}

	/**
	 * This simply replaces the VC Cookie if it already exists, else sets a new
	 * VC Cookie.
	 * 
	 * @param request
	 * @param vcCookie
	 */
	public static void setCookie(HttpServletResponse response, Cookie vcCookie) {
		response.addCookie(vcCookie);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param cookieletName
	 * @return
	 */
	public static VCCookielet getCookielet(HttpServletRequest request,
			String cookieletName) {
		VCCookie vcCookie = (VCCookie) getCookie(request);
		return vcCookie.getCookielet(cookieletName);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param cookielet
	 */
	public static void setCookielet(VCCookie vcCookie, VCCookielet cookielet) {
		vcCookie.setCookielet(cookielet);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param cookielet
	 */
	public static void addCookie(HttpServletResponse response, VCCookie vcCookie) {
		response.addCookie(vcCookie.getCookie());
	}
}