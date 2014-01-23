/*
 * Created on Mar 27, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.cookies;

import java.util.MissingResourceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

import com.votingcentral.env.EnvProps;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CookieletProperties {

	private static Log log = LogFactory.getLog(CookieletProperties.class);

	public static String VC_COOKIELET_CREATE_POLL_POLL_ID = "vc.cookielet.create.poll.pollid";

	private static MessageResources resources = null;

	static {
		try {
			resources = MessageResources
					.getMessageResources("com.votingcentral.resources.VCCookie");
		} catch (MissingResourceException e) {
			log.fatal("Missing resource", e);
		}
	}

	public static String getProperty(String key) {
		return resources.getMessage(key);
	}

	public static void main(String[] args) {
		System.out
				.println("Message is:"
						+ EnvProps
								.getProperty("popular.categories.cache.refresh.interval.millis"));
	}

}