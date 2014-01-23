/*
 * Created on Jan 26, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.cookies;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.crypto.CryptoHelper;
import com.votingcentral.util.url.FastURLEncoder;

/**
 * @author Gandhari
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCCookie {

	public final static String VCCOOKIENAME = "vc";

	public final static String VCCOOKIEDOMAIN = ".votingcentral.com";

	private final static String COOKIELET_SEPARATOR = ";";

	private final static String COOKIELET_EQUALS = "=";

	//	One week. This should be moved to .properties file
	private final static int MAXAGE = 7 * 24 * 60 * 60;

	private static Log log = LogFactory.getLog(VCCookie.class);

	private HashMap cookieletMap = null;

	private Cookie cookie;

	public VCCookie() {
		Cookie cookie = new Cookie(VCCOOKIENAME, null);
		cookieletMap = new HashMap();
	}

	/**
	 * 
	 *  
	 */
	public VCCookie(Cookie cookie) {
		setCookie(cookie);
	}

	/**
	 *  
	 */
	public String getName() {
		return VCCOOKIENAME;
	}

	/**
	 * Simply Ignore the name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		;
	}

	/**
	 *  
	 */
	public void setValue(String value) {
		//Clear the Mappings first
		cookieletMap.clear();

		if (value == null || value.trim() == "")
			return;
		String[] ckValues = StringUtils.split(value, COOKIELET_SEPARATOR);
		if (ckValues == null)
			return;

		//Now add the cookielets to the cookie
		for (int i = 0; i < ckValues.length; i++) {
			VCCookielet cklet = new VCCookielet((String) ckValues[i]);
			cookieletMap.put(cklet.getName(), cklet);
		}
	}

	/**
	 * Returns the specific VCCookielet
	 * 
	 * @param cookieletName
	 * @return
	 */
	public VCCookielet getCookielet(String cookieletName) {
		Object obj = cookieletMap.get(cookieletName);
		if (obj == null)
			return null;
		return (VCCookielet) obj;
	}

	/**
	 * set the cookielet into the HashMap
	 * 
	 * @param cookielet
	 */
	public void setCookielet(VCCookielet cookielet) {
		if (cookielet != null) {
			cookieletMap.put(cookielet.getName(), cookielet);
		}
	}

	/**
	 * 
	 * @param cookieletName
	 */
	public void removeCookielet(String cookieletName) {
		cookieletMap.remove(cookieletName);
	}

	/**
	 * @return Returns the cookie.
	 */
	public Cookie getCookie() {
		Cookie cookie = null;
		if (cookieletMap.isEmpty()) {
			cookie = new Cookie(VCCOOKIENAME, "");
		} else {
			UnSyncStringBuffer sb = new UnSyncStringBuffer();
			for (Iterator i = cookieletMap.keySet().iterator(); i.hasNext();) {
				String key = (String) i.next();
				VCCookielet ck = (VCCookielet) cookieletMap.get(key);
				sb.append(ck.toString());
				sb.append(COOKIELET_SEPARATOR);
			}
			CryptoHelper c = new CryptoHelper();
			String value = c.encrypt(sb.toString());
			cookie = new Cookie(VCCOOKIENAME, FastURLEncoder.encode(value));
		}
		//make this cookie available for all the apps.
		cookie.setPath("/");
		return cookie;
	}

	/**
	 * @param cookie
	 *            The cookie to set.
	 */
	public void setCookie(Cookie cookie) {
		this.cookie = cookie;
		cookieletMap = new HashMap();
		String value = cookie.getValue();
		String decodedCookies = "";
		try {
			//The cookie value is encrypted and URL encoded, URL decode and
			// decrypt.
			decodedCookies = URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.fatal("error decoding the cookie", e);
		}
		CryptoHelper c = new CryptoHelper();
		String decryptedCookie = c.decrypt(decodedCookies);
		String[] cookielets = StringUtils.split(decryptedCookie,
				COOKIELET_SEPARATOR);
		for (int i = 0; i < cookielets.length; i++) {
			VCCookielet cookielet = new VCCookielet(cookielets[i]);
			cookieletMap.put(cookielet.getName(), cookielet);
		}
	}
}