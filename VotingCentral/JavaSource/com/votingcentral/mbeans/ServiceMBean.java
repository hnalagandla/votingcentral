/*
 * Created on Jun 15, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.mbeans;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface ServiceMBean {

	public String getServiceURL();

	public String getPort();

	public String getProtocol();

	public void setServiceURL(String serviceURL);

	public void setPort(String port);

	public void setProtocol(String protocol);
}