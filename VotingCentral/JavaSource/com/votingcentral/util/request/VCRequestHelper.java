/*
 * Created on Mar 29, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.request;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.cookies.VCCookie;
import com.votingcentral.cookies.VCCookieHandler;
import com.votingcentral.cookies.VCCookielet;
import com.votingcentral.cookies.VCCookieletEnum;
import com.votingcentral.env.EnvProps;
import com.votingcentral.model.enums.VCUserRolesEnum;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.url.PresentationConstants;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCRequestHelper {
	private final static Log log = LogFactory.getLog(VCRequestHelper.class);

	private static String GUEST = "guest";

	private final static int MAX_ANSWERS_PER_QUESTION = Integer
			.parseInt(EnvProps.getProperty("polls.max.answers.per.question"));

	public static String getValueFromRequest(HttpServletRequest request,
			String literalKey, String currValue) {
		String newValue = "";
		if (currValue != null && currValue.length() > 0) {
			newValue = currValue;
		} else {
			newValue = (String) request.getAttribute(literalKey);
		}
		return newValue;
	}

	public static boolean isFacebook(HttpServletRequest request) {
		String value = request
				.getParameter(RequestParameterObjects.FACEBOOK_SIG_API_KEY);
		if (value == null) {
			return false;
		} else {
			return true;
		}
	}

	public static String getValueFromRequestOrForm(HttpServletRequest request,
			String literalKey, String currValue) {
		String newValue = "";
		//if we already have a value, leave it un touched.
		if (currValue != null && currValue.length() > 0) {
			newValue = currValue;
		} else {
			//first see we have an attribute.
			newValue = (String) request.getAttribute(literalKey);
			if (newValue == null || newValue.length() == 0) {
				//now see if it exists as a paramter.
				newValue = (String) request.getParameter(literalKey);
			}
		}
		return newValue;
	}

	public static void setValueIntoRequest(HttpServletRequest request,
			String literalKey, String newValue) {
		request.setAttribute(literalKey, newValue);
	}

	public static String getDomainAndContext(HttpServletRequest request) {
		//dumpRequestData(request);
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		//		url.append(request.getScheme());
		//		url.append(PresentationConstants.COLON);
		//		url.append(PresentationConstants.SLASH);
		//		url.append(PresentationConstants.SLASH);
		boolean isRequestFromFacebook = isFacebook(request);
		if (isRequestFromFacebook) {
			url.append("apps.facebook.com");
		} else {
			url.append(request.getServerName());
		}
		if (!EnvProps.isProd() && !isRequestFromFacebook) {
			url.append(PresentationConstants.COLON);
			url.append(request.getServerPort());
		}
		if (isRequestFromFacebook) {
			url.append(request.getContextPath().toLowerCase());
		} else {
			url.append(request.getContextPath());
		}
		return url.toString();
	}

	public static String getDomainAndContextForImg(HttpServletRequest request) {
		//dumpRequestData(request);
		UnSyncStringBuffer url = new UnSyncStringBuffer();
		//		url.append(request.getScheme());
		//		url.append(PresentationConstants.COLON);
		//		url.append(PresentationConstants.SLASH);
		//		url.append(PresentationConstants.SLASH);
		url
				.append(EnvProps
						.getProperty("full.app.server.url.without.protocol"));
		return url.toString();
	}

	public static String getDomainAndContextWithProtocol(
			HttpServletRequest request) {
		String url = getDomainAndContext(request);
		if (request.isSecure()) {
			return "https://" + url;
		} else {
			return "http://" + url;
		}
	}

	public static String getDomainAndContextWithProtocolForImg(
			HttpServletRequest request) {
		String url = getDomainAndContextForImg(request);
		if (request.isSecure()) {
			return "https://" + url;
		} else {
			return "http://" + url;
		}
	}

	public static void dumpRequestData(HttpServletRequest request) {
		String protocol = request.getProtocol();
		log.error("Protocol:" + protocol);

		String context = request.getContextPath();
		log.error("Context Path:" + context);

		String method = request.getMethod();
		log.error("Method:" + method);

		String pathInfo = request.getPathInfo();
		log.error("Pathinfo:" + pathInfo);

		String trxPath = request.getPathTranslated();
		log.error("Path translated:" + trxPath);

		String queryStr = request.getQueryString();
		log.error("Query String:" + queryStr);

		String remoteAddr = request.getRemoteAddr();
		log.error("Remote Address:" + remoteAddr);

		String remoteHost = request.getRemoteHost();
		log.error("Remote Host:" + remoteAddr);

		String remoteUser = request.getRemoteUser();
		log.error("Remote User:" + remoteUser);

		String requestURI = request.getRequestURI();
		log.error("Request URI:" + requestURI);

		String requestURL = "null";
		if (request.getRequestURL() != null) {
			requestURL = request.getRequestURL().toString();
		}
		log.error("Request URL:" + requestURL);

		String schema = request.getScheme();
		log.error("Schema:" + schema);

		String serverName = request.getServerName();
		log.error("Servername:" + serverName);

		int serverPort = request.getServerPort();
		log.error("Server Port:" + serverPort);

		String path = request.getServletPath();
		log.error("Servlet Path:" + path);
	}

	public static String getUser(HttpServletRequest request) {
		String loginName = null;
		VCCookie vcCookie = VCCookieHandler.getCookie(request);
		VCCookielet user = vcCookie.getCookielet(VCCookieletEnum.USERNAME
				.getName());
		if (user == null || user.getValue() == null
				|| user.getValue().length() == 0) {
			loginName = GUEST;
		} else {
			loginName = user.getValue();
		}
		return loginName.toLowerCase().trim();
	}

	public static boolean isGuest(HttpServletRequest request) {
		String loginName = getUser(request);
		return loginName.equalsIgnoreCase(GUEST);
	}

	public static int getMaxAnswersPerQuestion(HttpServletRequest request) {
		if (request.isUserInRole(VCUserRolesEnum.ADMIN.getName())) {
			return 2 * MAX_ANSWERS_PER_QUESTION;
		}
		return MAX_ANSWERS_PER_QUESTION;
	}

}