/*
 * Created on May 16, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.polls;

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
public class RemotePollProperties {
	private static Log log = LogFactory.getLog(RemotePollProperties.class);

	private static MessageResources resources = null;

	static {
		try {
			resources = MessageResources
					.getMessageResources("com.votingcentral.resources.RemotePoll");
		} catch (MissingResourceException e) {
			log.fatal("MIssing resource", e);
		}
	}

	public static String getProperty(String key) {
		return resources.getMessage(key);
	}
}