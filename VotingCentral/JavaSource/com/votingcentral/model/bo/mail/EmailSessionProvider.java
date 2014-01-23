/*
 * Created on Jan 12, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.votingcentral.env.EnvProps;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EmailSessionProvider {
	private static EmailSessionProvider emailSession = null;

	private static final String SMTP_USER_NAME = EnvProps
			.getProperty("mail.smtp.username");

	private static final String SMTP_PASSWORD = EnvProps
			.getProperty("mail.smtp.password");

	private static Properties props = null;

	private static Session mailSession = null;

	private static class ForcedAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(SMTP_USER_NAME, SMTP_PASSWORD);
		}
	}

	private EmailSessionProvider() {
		props = new Properties();

		boolean debugOn = EnvProps.getProperty("mail.smtp.debug.on")
				.equalsIgnoreCase("true");
		boolean isLocalEmail = EnvProps.getProperty("mail.smtp.local.server")
				.equalsIgnoreCase("true");
		props.put("mail.transport.protocol", EnvProps
				.getProperty("mail.transport.protocol"));

		if (!isLocalEmail) {
			props.put("mail.smtp.host", EnvProps
					.getProperty("remote.mail.smtp.host"));
			props.put("mail.smtp.port", EnvProps
					.getProperty("remote.mail.smtp.port"));
			props.put("mail.smtp.starttls.enable", EnvProps
					.getProperty("remote.mail.smtp.starttls.enable"));
			props.put("mail.smtp.auth", EnvProps
					.getProperty("remote.mail.smtp.auth"));
			mailSession = Session.getInstance(props, new ForcedAuthenticator());
		} else {
			props.put("mail.smtp.host", EnvProps.getProperty("mail.smtp.host"));
			props.put("mail.smtp.port", EnvProps.getProperty("mail.smtp.port"));
			props.put("mail.smtp.starttls.enable", EnvProps
					.getProperty("mail.smtp.starttls.enable"));
			props.put("mail.smtp.auth", EnvProps.getProperty("mail.smtp.auth"));
			mailSession = Session.getInstance(props);
		}

		mailSession.setDebug(debugOn);
	}

	public static synchronized EmailSessionProvider getInstance() {
		if (emailSession == null) {
			emailSession = new EmailSessionProvider();
		}
		return emailSession;
	}

	/**
	 * @return Returns the mailSession.
	 */
	public Session getMailSession() {
		return mailSession;
	}
}