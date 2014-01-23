/*
 * Created on Apr 21, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.mail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.enums.VCEmailTypeEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EmailContentHelper {

	private static Log log = LogFactory.getLog(EmailContentHelper.class);

	private String emailContent;

	private MessageFormat formatter;

	private VCEmailTypeEnum emailType;

	//cache the email content, so that we don't have to reload
	//from file again.
	private static Map emailContentMap = new HashMap();

	/**
	 *  
	 */
	public EmailContentHelper(VCEmailTypeEnum emailType) {
		this.emailType = emailType;
		loadEmailContent();
	}

	private void loadEmailContent() {
		String content = getFileAsString();
		emailContent = content;
		formatter = new MessageFormat(content);
	}

	private String getFileAsString() {
		String content = (String) emailContentMap.get(emailType.getName());
		if (content != null && content.length() > 0) {
			return content;
		}
		InputStream fis;

		try {
			fis = EmailContentHelper.class.getResourceAsStream(EmailProperties
					.getEmailProperty(emailType.getName()));
			int x = fis.available();
			byte b[] = new byte[x];
			fis.read(b);
			content = new String(b);
			emailContentMap.put(emailType.getName(), content);
		} catch (FileNotFoundException e) {
			log.fatal("Cannot find email content for:" + emailType.getName());
		} catch (IOException e) {
			log.fatal("Problem loading file:"
					+ EmailProperties.getEmailProperty(emailType.getName()));
		}
		return content;
	}

	/**
	 * @return Returns the emailContent.
	 */
	public String getEmailContent() {
		return emailContent;
	}

	/**
	 * @param emailContent
	 *            The emailContent to set.
	 */
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	/**
	 * @return Returns the emailType.
	 */
	public VCEmailTypeEnum getEmailType() {
		return emailType;
	}

	/**
	 * @param emailType
	 *            The emailType to set.
	 */
	public void setEmailType(VCEmailTypeEnum emailType) {
		this.emailType = emailType;
	}

	/**
	 * @return Returns the formatter.
	 */
	public MessageFormat getFormatter() {
		return formatter;
	}

	/**
	 * @param formatter
	 *            The formatter to set.
	 */
	public void setFormatter(MessageFormat formatter) {
		this.formatter = formatter;
	}
}