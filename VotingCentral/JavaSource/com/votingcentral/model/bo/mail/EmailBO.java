package com.votingcentral.model.bo.mail;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.env.EnvProps;
import com.votingcentral.model.db.dao.IMailDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.MailTO;
import com.votingcentral.model.enums.VCEmailTypeEnum;
import com.votingcentral.model.polls.PollTimeHelper;
import com.votingcentral.util.UnSyncStringBuffer;
import com.votingcentral.util.url.VCHelpPageURLHelper;
import com.votingcentral.util.url.VCImageURLHelper;
import com.votingcentral.util.url.VCURLHelper;

public class EmailBO {
	private static final Log log = LogFactory.getLog(EmailBO.class);

	private final static int MAX_EMAILS_TO_SEND_IN_ONE_SHOT = Integer
			.parseInt(EnvProps.getProperty("emails.max.emails.to.send.once"));

	private final static boolean shouldSendImageInMailFooter = EnvProps
			.getProperty("emails.footer.send.image").equalsIgnoreCase("true");

	private static EmailBO emailBo = null;

	private EmailBO() {

	}

	public static EmailBO getInstance() {
		if (emailBo == null) {
			emailBo = new EmailBO();
		}
		return emailBo;
	}

	public void createMailRequest(List toAddress, VCEmailTypeEnum emailType,
			Object[] values, long delayMins) throws SQLException {
		String fromAddress = EmailProperties.getEmailProperty(emailType
				.getName()
				+ "_FROM");
		String subject = EmailProperties.getEmailProperty(emailType.getName()
				+ "_SUBJECT");
		List mails = new ArrayList();
		getMailTOs(mails, fromAddress, toAddress, subject, emailType, values);
		createMailRequest(mails, delayMins);
	}

	public void createMailRequest(String fromAddress, List toAddress,
			String subject, VCEmailTypeEnum emailType, Object[] values)
			throws SQLException {
		List mails = new ArrayList();
		getMailTOs(mails, fromAddress, toAddress, subject, emailType, values);
		createMailRequest(mails, 0);
	}

	public void createMailRequest(String fromAddress, List toAddress,
			VCEmailTypeEnum emailType, Object[] values) throws SQLException {
		String subject = EmailProperties.getEmailProperty(emailType.getName()
				+ "_SUBJECT");
		List mails = new ArrayList();
		getMailTOs(mails, fromAddress, toAddress, subject, emailType, values);
		createMailRequest(mails, 0);
	}

	public void sendUnsentEmails(Date startFrom, int maxRetryCount) {

		try {
			DAOFactory dao = DAOFactory.getDAOFactory();
			IMailDAO mdao = dao.getMailDAO();
			log.info("Looking for unsent emails starting from :"
					+ PollTimeHelper.getInstance().getTimeString(
							startFrom.getTime()));
			List unsentEmails = mdao.getUnsentMails(startFrom, maxRetryCount);
			if (unsentEmails != null
					&& unsentEmails.size() > MAX_EMAILS_TO_SEND_IN_ONE_SHOT) {
				int i = 0;
				for (Iterator itr = unsentEmails.iterator(); itr.hasNext()
						&& i < MAX_EMAILS_TO_SEND_IN_ONE_SHOT;) {
					MailTO mto = (MailTO) itr.next();
					send(mto);
					i++;
				}
			} else {
				for (Iterator itr = unsentEmails.iterator(); itr.hasNext();) {
					MailTO mto = (MailTO) itr.next();
					send(mto);
				}
			}
		} catch (SQLException e) {
			log.error("Exception retriveing unsent emails", e);
		}
	}

	private void getMailTOs(List mtos, String fromAddress, List toAddress,
			String subject, VCEmailTypeEnum emailType, Object[] values) {
		for (Iterator itr = toAddress.iterator(); itr.hasNext();) {
			String ta = (String) itr.next();
			MailTO mto = new MailTO();
			mto.setFromAddress(fromAddress);
			mto.setToAddress(ta);
			mto.setSubject(subject);
			mto.setReplyToAddress(fromAddress);
			EmailContentHelper helper = new EmailContentHelper(emailType);
			MessageFormat formatter = helper.getFormatter();
			//add the footer which contains promotional stuff.
			Object[] withFooter = new Object[values.length + 1];
			System.arraycopy(values, 0, withFooter, 0, values.length);
			withFooter[values.length] = getMailFooter();
			mto.setContent(formatter.format(withFooter));
			mto.setType(emailType);
			if (mtos == null) {
				mtos = new ArrayList();
			}
			mtos.add(mto);
		}
	}

	private String getMailFooter() {
		UnSyncStringBuffer buff = new UnSyncStringBuffer();
		String domContext = VCURLHelper.getDomainContext();
		if (shouldSendImageInMailFooter) {
			buff
					.append("<p> <a href=\"")
					.append(
							VCHelpPageURLHelper.getInstance()
									.getEmailPromotionFAQUrl(domContext))
					.append("\"><img src=\"")
					.append(
							VCImageURLHelper.getInstance()
									.getEmailPromotionImageUrl(domContext))
					.append(
							"\" alt=\"Win a FREE iPod Shuffle on VotingCentral\"></a></p>");
		} else {
			buff.append("<p> <a href=\"").append(
					VCHelpPageURLHelper.getInstance().getEmailPromotionFAQUrl(
							domContext)).append("\">").append(
					"Win a FREE iPod Shuffle on VotingCentral").append(
					"</a></p>");
		}
		return buff.toString();
	}

	private void createMailRequest(List mtos, long delayMins)
			throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IMailDAO mdao = dao.getMailDAO();
		for (Iterator itr = mtos.iterator(); itr.hasNext();) {
			MailTO mto = (MailTO) itr.next();
			mdao.createMailRequest(mto, delayMins);
		}
	}

	/**
	 * 
	 * @param emailInfo
	 * @return
	 */
	private boolean send(MailTO mto) {
		log.debug("send::Inside the mail class send method");
		Session session = null;
		Message message = null;
		try {
			session = EmailSessionProvider.getInstance().getMailSession();
			// create a message
			message = new MimeMessage(session);
			// set the sent Date
			message.setSentDate(PollTimeHelper.getInstance().getCurrentDate());
			// set the email subject
			message.setSubject(mto.getSubject());
			// set the source address
			message.setFrom(new InternetAddress(mto.getFromAddress()));
			// set the destination address
			Address[] destAddress = convertToAddress(mto.getToAddress());
			message.setRecipients(Message.RecipientType.TO, destAddress);
			// set the CC address
			Address[] ccAddress = null;
			if (mto.getCcAddress() != null) {
				ccAddress = convertToAddress(mto.getCcAddress());
				message.setRecipients(Message.RecipientType.CC, ccAddress);
			}
			message.setReplyTo(convertToAddress(mto.getReplyToAddress()));
			// set the BCC address
			Address[] bccAddress = null;
			if (mto.getBccAddress() != null) {
				bccAddress = convertToAddress(mto.getBccAddress());
				message.setRecipients(Message.RecipientType.BCC, bccAddress);
			}
			message.setContent(mto.getContent(), "text/html");
			Transport.send(message);
			log.info("send:: MAIL HAS BEEN SUCCESSFULLY SENT TO :)"
					+ destAddress);
			try {
				updateWhenSendSucceed(mto);
			} catch (SQLException e) {
				//we might send dupes
				log.fatal("Error updating DB after successfully sending email",
						e);
			}
			return true;
		} catch (SendFailedException sfex) {
			log.error("Error sending email, sfex", sfex);
			return handleMessagingException(session, sfex, mto);
		} catch (MessagingException mex) {
			log.error("Error sending email, mex", mex);
			return handleMessagingException(session, mex, mto);
		}
	}

	/**
	 * 
	 * @param address
	 * @return
	 */
	private Address[] convertToAddress(String address) {
		Address[] addr = new InternetAddress[1];
		try {
			addr[0] = new InternetAddress(address);
		} catch (AddressException e) {
			log.error("Failed converting address :" + address);
		}
		return addr;
	}

	private void updateWhenSendSucceed(MailTO mto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IMailDAO mdao = dao.getMailDAO();
		mdao.updateWhenSendSucceed(mto);
	}

	private void updateWhenSendFailed(MailTO mto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IMailDAO mdao = dao.getMailDAO();
		mdao.updateWhenSendFailed(mto);
	}

	private void delete(MailTO mto) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IMailDAO mdao = dao.getMailDAO();
		mdao.delete(mto);
	}

	private boolean handleMessagingException(Session session,
			MessagingException mex, MailTO mto) {
		log.error("Failed Sending email", mex);
		Properties p = session.getProperties();
		log.error("Properties are :" + p.toString());
		try {
			if (mex instanceof SendFailedException) {
				SendFailedException sfex = (SendFailedException) mex;
				Address[] invalid = sfex.getInvalidAddresses();
				if (invalid != null) {
					log.info("send::Invalid Addresses");
					for (int i = 0; i < invalid.length; i++) {
						log.info("\t" + invalid[i]);
						//this is an invalid email address
						//no need to retry .delete the request.
						delete(mto);
					}
				}
				Address[] validUnsent = sfex.getValidUnsentAddresses();
				if (validUnsent != null) {
					log.info("send:: Valid Unsent Addresses.");
					for (int i = 0; i < validUnsent.length; i++) {
						log.info("\t" + validUnsent[i]);
					}
					//put it back in the queue for retry
					updateWhenSendFailed(mto);
				}
				Address[] validSent = sfex.getValidSentAddresses();
				if (validSent != null) {
					log.info("send::Valid Sent Addresses");
					for (int i = 0; i < validSent.length; i++)
						log.info("\t>" + validSent[i]);
					//email sent successfully
					//mark status a complete. set success_timestamp
					updateWhenSendSucceed(mto);
				}
			} else {
				//put it back in the retry loop.
				updateWhenSendFailed(mto);
			}
		} catch (SQLException sqle) {
			log.error("failure handling exception", sqle);
		}
		return false;
	}
}