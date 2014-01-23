/*
 * Created on Jun 19, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.services.twitter;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.votingcentral.env.EnvProps;
import com.votingcentral.services.bitly.BitlyService;
import com.votingcentral.util.url.VCURLHelper;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TwitterService {
	private static TwitterService ts = null;

	private static String twitterLogin = EnvProps
			.getProperty("twitter.login.username");

	private static String twitterPassword = EnvProps
			.getProperty("twitter.login.password");

	private static Twitter twitter = new Twitter(twitterLogin, twitterPassword);

	private static int TWITTER_MAX_POST_LENGTH = 140;

	private static int SPACE_BETWEEN_URL_AND_TEXT = 2;

	private static int TWITTER_NET_MAX_POST_LENGTH = TWITTER_MAX_POST_LENGTH
			- SPACE_BETWEEN_URL_AND_TEXT;

	private final static Log log = LogFactory.getLog(TwitterService.class);

	private TwitterService() {

	}

	/**
	 * create the twitterservice instance.
	 * 
	 * @return
	 */
	public static TwitterService getInstance() {
		if (ts == null) {
			ts = new TwitterService();
		}
		return ts;
	}

	/**
	 * get the update message for each pollname and pollid
	 * 
	 * @param pollName
	 * @param pollId
	 * @return
	 */
	private String getMessage(String pollName, String pollId) {
		String domContext = VCURLHelper.getDomainContext();
		String bitlyUrl = BitlyService.getInstance().shorten(
				VCURLHelper.getDisplayPollUrl(domContext, pollId));
		String message = pollName + ", " + bitlyUrl;
		if (message.length() > TWITTER_NET_MAX_POST_LENGTH) {
			int pollURLLength = bitlyUrl.length();
			int textLength = TWITTER_NET_MAX_POST_LENGTH - pollURLLength;
			String subText = pollName.substring(0, textLength);
			message = subText + ", " + bitlyUrl;
			log.error("Message posting to Twitter" + message);
		}
		return message;
	}

	/**
	 * set the status for a given pollname and pollid
	 * 
	 * @param pollName
	 * @param pollId
	 * @throws TwitterException
	 */
	public void updateStatus(String pollName, String pollId)
			throws TwitterException {
		twitter.setSource("VotingCentral Twitter4j API");
		Status ts = null;
		ts = twitter.updateStatus(getMessage(pollName, pollId));
		if (ts != null) {
			log.error("Status Time: " + ts.getCreatedAt() + " Status Id: "
					+ ts.getId() + " Status source: " + ts.getSource()
					+ " Status Text: " + ts.getText() + ";"
					+ ts.getRateLimitLimit() + " " + ts.getRateLimitRemaining()
					+ " UserName" + ts.getUser().getName());
		}
	}

	/**
	 * update votingcentral status to "message"
	 * 
	 * @param message
	 * @throws TwitterException
	 */
	public void updateStatus(String message) throws TwitterException {
		twitter.setSource("VotingCentral Twitter4j API");
		Status ts = twitter.updateStatus(message);
		if (ts != null) {
			log.error("Status Time: " + ts.getCreatedAt() + " Status Id: "
					+ ts.getId() + " Status source: " + ts.getSource()
					+ " Status Text: " + ts.getText() + ";"
					+ ts.getRateLimitLimit() + " " + ts.getRateLimitRemaining()
					+ " UserName" + ts.getUser().getName());
		}
	}

	/**
	 * get all followers of votingcentral
	 * 
	 * @return
	 * @throws TwitterException
	 */
	public List getFollowers() throws TwitterException {
		List followers = twitter.getFollowers();
		return followers;
	}

	/**
	 * send a direct message to all followers
	 * 
	 * @param pollName
	 * @param pollId
	 * @throws TwitterException
	 */
	public void sendDirectMessageToFollowers(String pollName, String pollId)
			throws TwitterException {
		String message = getMessage(pollName, pollId);
		List followers = getFollowers();
		for (Iterator itr = followers.iterator(); itr.hasNext();) {
			User follower = (User) itr.next();
			DirectMessage ds = twitter.sendDirectMessage(follower
					.getScreenName(), message);
		}
	}
}