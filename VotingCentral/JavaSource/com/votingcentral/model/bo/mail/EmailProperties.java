/*
 * Created on Apr 23, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.mail;

import java.util.MissingResourceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EmailProperties {
	private static Log log = LogFactory.getLog(EmailProperties.class);

	private static MessageResources resources = null;

	static {
		try {
			resources = MessageResources
					.getMessageResources("com.votingcentral.resources.Email");
		} catch (MissingResourceException e) {
			log.fatal("MIssing resource", e);
		}
	}

	public static String getEmailProperty(String key) {
		return resources.getMessage(key);
	}
}