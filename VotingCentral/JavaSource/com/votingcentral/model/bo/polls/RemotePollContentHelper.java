/*
 * Created on May 16, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.polls;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class RemotePollContentHelper {
	public static final String REMOTE_POLL_COMPONENT_BODY = "REMOTE_POLL_BODY";

	public static final String REMOTE_POLL_COMPONENT_ANSWER = "REMOTE_POLL_COMPONENT_ANSWER";

	private static Log log = LogFactory.getLog(RemotePollContentHelper.class);

	private static RemotePollContentHelper rpch = null;

	//cache the file, so that we don't have to reload
	//from disk again.
	private Map remotePollContentMap = new HashMap();

	/**
	 *  
	 */
	private RemotePollContentHelper() {
		loadContent();
	}

	public static RemotePollContentHelper getInstance() {
		if (rpch == null) {
			rpch = new RemotePollContentHelper();
		}
		return rpch;
	}

	private void loadContent() {

		String c1 = getFileAsString(REMOTE_POLL_COMPONENT_BODY);
		String c2 = getFileAsString(REMOTE_POLL_COMPONENT_ANSWER);
		MessageFormat f1 = new MessageFormat(c1);
		MessageFormat f2 = new MessageFormat(c2);
		remotePollContentMap.put(REMOTE_POLL_COMPONENT_BODY, f1);
		remotePollContentMap.put(REMOTE_POLL_COMPONENT_ANSWER, f2);

	}

	private String getFileAsString(String key) {

		InputStream fis;
		String content = null;
		try {
			fis = RemotePollContentHelper.class
					.getResourceAsStream(RemotePollProperties.getProperty(key));
			int x = fis.available();
			byte b[] = new byte[x];
			fis.read(b);
			content = new String(b);
		} catch (FileNotFoundException e) {
			log.fatal("Cannot find email content for:" + key);
		} catch (IOException e) {
			log.fatal("Problem loading file:"
					+ RemotePollProperties.getProperty(key));
		}
		return content;
	}

	/**
	 * @return Returns the remotePollContentMap.
	 */
	public Map getRemotePollContentMap() {
		return remotePollContentMap;
	}
}