/*
 * Created on Sep 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public final class ResourceLocator {
	private static DataSource ds = null;

	private static final String lockObject = "LOCK";

	private static Log log = LogFactory.getLog(ResourceLocator.class);

	private ResourceLocator() {
	}

	public static DataSource getDataSource() {
		if (ds == null) {
			try {
				synchronized (lockObject) {
					if (ds == null) {
						Context initContext = new InitialContext();
						Context envContext = (Context) initContext
								.lookup("java:/comp/env");
						ds = (DataSource) envContext
								.lookup("jdbc/votingcentral");
					} else
						return ds;
				}
			} catch (NamingException e) {
				log.error("", e);
			}
		}
		return ds;
	}

	public static Object getEnvEntry(String jndiName) {
		Object envValue = null;
		try {
			Context ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env/");
			//Look up using the env entry name
			envValue = env.lookup(jndiName);
		} catch (NamingException ne) {
			log.error("", ne);
		}
		return envValue;
	}
}