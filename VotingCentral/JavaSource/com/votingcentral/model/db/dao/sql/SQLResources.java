/*
 * Created on Aug 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.sql;

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
public class SQLResources {

	private static Log log = LogFactory.getLog(SQLResources.class);

	private static MessageResources resources = null;

	static {
		try {
			resources = MessageResources
					.getMessageResources("com.votingcentral.resources.SQLResources");			
		} catch (MissingResourceException e) {
			log.fatal("MIssing resource", e);
		}
	}

	public static String getSQLResource(String key) {
		return resources.getMessage(key);
	}

	public static void main(String[] args) {
		System.out.println("Message is:" + SQLResources.getSQLResource("get.featured.polls"));
	}
}