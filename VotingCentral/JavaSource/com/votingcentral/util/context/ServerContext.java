/*
 * Created on Aug 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.context;

import java.net.InetAddress;

/**
 * @author harishn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ServerContext {

	// InetAddress is inherently unsafe to use at runtime
	// as it does DNS lookups and reverse DNS lookups.
	// We therefore cache the result here and do the DNS hits
	// only during initialization.
	
	// default values
	private static String _toString = "169.254.2.1";
	private static String s_getHostName = "169.254.2.1";	
	private static String s_getFullHostName = "169.254.2.1";	
	private static String s_getHostAddress = "169.254.2.1";
	private static byte[] s_getAddress = new byte[4];
	private static boolean s_isMulticastAddress = false;
	public static String serverName = "169.254.2.1";

	public static String serverToString()
	{
		return _toString;
	}
	
	public static String getHostName()
	{
		return s_getHostName;
	}

	public static String getFullHostName()
	{
		return s_getFullHostName;
	}

	public static String getHostAddress()
	{
		return s_getHostAddress;
	}

	public static byte[] getAddress()
	{
		return s_getAddress;
	}

	public static boolean isMulticastAddress()
	{
		return s_isMulticastAddress;
	}
	
		
	static {
		try {
			InetAddress host = InetAddress.getLocalHost();

        	if (host != null) {
				serverName = host.toString();
				_toString = host.toString();
				s_getHostName = host.getHostName();	
				s_getHostAddress = host.getHostAddress();
				s_getAddress = host.getAddress();
				s_isMulticastAddress = host.isMulticastAddress();
				s_getFullHostName = InetAddress.getByName(s_getHostAddress).getHostName();
        	}
		} catch (Exception e) {
        	e.printStackTrace();
        }
	} 
}
