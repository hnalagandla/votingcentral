/*
 * Created on Aug 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.guid;

import java.net.UnknownHostException;

/**
 * @author harishn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GUIDService {

	private static GUID guid = null;
	
	private GUIDService() {
		
	}
	
	public static synchronized String getNextGUID() throws UnknownHostException {
		if (guid == null) {
			guid = new GUID();
		}
		return guid.nextISOGUID();		
	}
}
