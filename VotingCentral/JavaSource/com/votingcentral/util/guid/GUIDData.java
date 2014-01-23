/*
 * Created on Aug 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.guid;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import com.votingcentral.util.RopeBuffer;

/**
 * @author harishn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GUIDData {
	private Date m_date;
	private String m_ipString;
	private int m_objAddress;
	private int m_counter;
		
	public GUIDData(Date date, byte[] ipAddress, int objAddress, int counter){
		m_date = date;
		m_ipString = toIpString(ipAddress);
		m_objAddress = objAddress;
		m_counter = counter;
	}

	private String toIpString(byte rawIpBytes[]){
		RopeBuffer buff = new RopeBuffer();
		int length = rawIpBytes.length;
		for (int i = 0; i < length; i++) {
			if(i > 0){
				buff.append(".");
			}
			buff.append(rawIpBytes[i] & 0xff);
		}
		return buff.toString();
	}
		
	/**
	 * @return the Date component of this GUID
	 */
	public Date getDate() {
		return m_date;
	}

	/**
	 * @return the IP Address component of this GUID
	 */
	public String getIpString() {
		return m_ipString;
	}
	
	public String getHostName(){
		try{
			InetAddress addr = InetAddress.getByName(m_ipString);
			return addr.getHostName();
		}catch(UnknownHostException uhe){
			return "Unknown";
		}
	}

	/**
	 * @return the Object Address component of this GUID
	 */
	public int getObjAddress() {
		return m_objAddress;
	}

	/**
	 * @return the Counter component of this GUID
	 */
	public int getCounter() {
		return m_counter;
	}

	/**
	 * @return an XML String representation of this Object, which
	 * can be used with XSL to produce a view of the GUID Data...
	 */
	public String toString(){
		RopeBuffer buff = new RopeBuffer();
		buff.append("\n\n<GUID>");
		buff.append("\n\t<date>");
		buff.append("\n\t\t"+m_date);
		buff.append("\n\t</date>");
		buff.append("\n\t<ip>");
		buff.append("\n\t\t"+m_ipString);
		buff.append("\n\t</ip>");
		buff.append("\n\t<host-name>");
		buff.append("\n\t\t"+getHostName());
		buff.append("\n\t</host-name>");
		buff.append("\n\t<ObjAddr>");
		buff.append("\n\t\t"+m_objAddress);
		buff.append("\n\t</ObjAddr>");
		buff.append("\n\t<Counter>");
		buff.append("\n\t\t"+m_counter);
		buff.append("\n\t</Counter>");
		buff.append("\n</GUID>");
		return buff.toString();
	}
}
