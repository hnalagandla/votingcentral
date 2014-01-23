/*
 * Created on Nov 22, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.url;

import com.votingcentral.env.EnvProps;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PresentationConstants {
	public static String EQUALS = "=";

	public static String QUESTION_MARK = "?";

	public static String COLON = ":";

	public static String SLASH = "/";

	public static String WEB_EXTN = EnvProps.getProperty("web.extension");

	public static String SECURE_SCHEME = "https://";

	public static String UN_SECURE_SCHEME = "http://";

	public static String AMPERSAND = "&";

	public static String WEB_CONTEXT = EnvProps.getProperty("web.context");

	public static String ERR_CODE = "errCode";

}