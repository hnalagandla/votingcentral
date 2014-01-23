/*
 * Created on Aug 17, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.env;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
public class EnvProps {

	private static Log log = LogFactory.getLog(EnvProps.class);

	private static String macIPAddress = "";

	private static String hostName = "";

	private static boolean isDev = false;

	private static boolean isQA = false;

	private static boolean isProd = false;

	private static String envStr = "";

	private static MessageResources resources = null;

	static {
		try {
			resources = MessageResources
					.getMessageResources("com.votingcentral.resources.Env");
			deriveEnv();
		} catch (MissingResourceException e) {
			log.fatal("MIssing resource", e);
		}
	}

	public static String getProperty(String key) {
		String localkey = envStr + key;
		String res = resources.getMessage(localkey);
		//if the key for the respective environment does not
		//exist use dev configuration
		if (res == null || res.length() == 0) {
			localkey = "dev." + key;
			res = resources.getMessage(localkey);
		}
		return res;
	}

	private static void deriveEnv() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			// Get IP Address
			byte[] ipAddr = addr.getAddress();
			//Convert to dot representation
			String ipAddrStr = "";
			for (int i = 0; i < ipAddr.length; i++) {
				if (i > 0) {
					ipAddrStr += ".";
				}
				ipAddrStr += ipAddr[i] & 0xFF;
			}
			macIPAddress = ipAddrStr;
			// Get hostname
			hostName = addr.getHostName();
		} catch (UnknownHostException e) {
		}
		String qaDnsAddress = resources.getMessage("qa." + "dns.address");
		String prodDnsAddress = resources.getMessage("prod." + "dns.address");
		if (macIPAddress.equalsIgnoreCase(prodDnsAddress)) {
			isProd = true;
			envStr = "prod.";
		} else if (macIPAddress.equalsIgnoreCase(qaDnsAddress)) {
			isQA = true;
			envStr = "qa.";
		} else {
			isDev = true;
			envStr = "dev.";
		}
	}

	/**
	 * @return Returns the hostName.
	 */
	public static String getHostName() {
		return hostName;
	}

	/**
	 * @return Returns the isProd.
	 */
	public static boolean isProd() {
		return isProd;
	}

	/**
	 * @return Returns the isQA.
	 */
	public static boolean isQA() {
		return isQA;
	}

	/**
	 * @return Returns the macIPAddress.
	 */
	public static String getMacIPAddress() {
		return macIPAddress;
	}
}