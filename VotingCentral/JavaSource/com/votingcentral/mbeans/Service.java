/*
 * Created on Jun 15, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.mbeans;

import java.util.ArrayList;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Service implements ServiceMBean {

	private String serviceURL;

	private String protocol;

	private String port;

	public Service() {
		MBeanServer server = getServer();
		ObjectName name = null;
		try {
			name = new ObjectName("Application:Name=Server,Type=Server");
			server.registerMBean(this, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MBeanServer getServer() {
		MBeanServer mbserver = null;
		String agentId = null;
		String domainName = null;
		ArrayList mbservers = MBeanServerFactory.findMBeanServer(agentId);

		if (mbservers.size() > 0) {
			mbserver = (MBeanServer) mbservers.get(0);
		}

		if (mbserver != null) {
			System.out.println("Found our MBean server");
		} else {
			mbserver = MBeanServerFactory.createMBeanServer();
		}
		return mbserver;
	}

	/**
	 * @return Returns the port.
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port
	 *            The port to set.
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return Returns the protocol.
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol
	 *            The protocol to set.
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return Returns the serviceURL.
	 */
	public String getServiceURL() {
		return serviceURL;
	}

	/**
	 * @param serviceURL
	 *            The serviceURL to set.
	 */
	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}
}