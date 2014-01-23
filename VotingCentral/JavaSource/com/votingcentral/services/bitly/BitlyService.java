/*
 * Created on Jun 21, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.services.bitly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.env.EnvProps;
import com.votingcentral.util.UnSyncStringBuffer;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BitlyService {
	private static BitlyService bs = null;

	private static Log log = LogFactory.getLog(BitlyService.class);

	private static String serviceUrl = EnvProps
			.getProperty("bitly.service.url");

	private static String protocol = EnvProps
			.getProperty("bitly.service.protocol");

	private static String port = EnvProps.getProperty("bitly.service.port");

	private static String apiKey = EnvProps
			.getProperty("bitly.service.api.key");

	private static String version = EnvProps
			.getProperty("bitly.service.version");

	private static UnSyncStringBuffer surl = new UnSyncStringBuffer();

	private static String bitlyLogin = EnvProps
			.getProperty("bitly.login.username");

	private static String bitlyPassword = EnvProps
			.getProperty("bitly.login.password");

	private BitlyService() {

	}

	public static BitlyService getInstance() {
		if (bs == null) {
			bs = new BitlyService();
			surl.append(protocol).append("://").append(serviceUrl).append(
					(port.equalsIgnoreCase("80") ? "" : port)).append(
					"/shorten").append("?version=").append(version).append(
					"&apiKey=").append(apiKey).append("&login=").append(
					bitlyLogin).append("&format=text");
		}
		return bs;
	}

	public String shorten(String longUrl) {
		log.error("Long URL is:" + longUrl);
		URL url;
		String shortUrl = "";
		OutputStreamWriter wr = null;
		BufferedReader rd = null;
		String specificData = surl.toString() + "&longUrl=" + longUrl;
		log.error("Calling Bitly URL :" + specificData);
		try {
			url = new URL(specificData);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(specificData);
			wr.flush();
			// Get the response
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				shortUrl = line;
			}
		} catch (MalformedURLException e) {
			log.fatal("Issue calling Bit.ly", e);
		} catch (IOException e) {
			log.fatal("Issue reading Bit.ly", e);
		} finally {
			if (wr != null) {
				try {
					wr.close();
				} catch (IOException e1) {
					log.fatal("Issue closing Bit.ly", e1);
				}
			}
			if (rd != null) {
				try {
					rd.close();
				} catch (IOException e1) {
					log.fatal("Issue closing Bit.ly", e1);
				}
			}
		}
		log.error("Short URL is:" + shortUrl);
		return shortUrl;
	}
}