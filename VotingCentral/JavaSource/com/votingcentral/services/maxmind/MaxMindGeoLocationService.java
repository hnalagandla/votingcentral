/*
 * Created on Jun 15, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.services.maxmind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.env.EnvProps;
import com.votingcentral.util.url.FastURLEncoder;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MaxMindGeoLocationService {

	private static Log log = LogFactory.getLog(MaxMindGeoLocationService.class);

	private static String serviceUrl = EnvProps
			.getProperty("maxmind.service.url");

	private static String protocol = EnvProps
			.getProperty("maxmind.service.protocol");

	private static String port = EnvProps.getProperty("maxmind.service.port");

	private static String licenseKey = EnvProps
			.getProperty("maxmind.service.license.key");

	private static String surl = protocol + "://" + serviceUrl
			+ (port.equalsIgnoreCase("80") ? "" : port);

	private static String data = "l="
			+ FastURLEncoder.encode(licenseKey, "UTF-8") + "&i=";

	private static MaxMindGeoLocationService service = new MaxMindGeoLocationService();

	private MaxMindGeoLocationService() {

	}

	public static MaxMindGeoLocationService getInstance() {
		return service;
	}

	public MaxMindLocationTO getLocation(String ipAddress) {
		MaxMindLocationTO mto = new MaxMindLocationTO();
		String specificData = data + FastURLEncoder.encode(ipAddress, "UTF-8");
		URL url;
		OutputStreamWriter wr = null;
		BufferedReader rd = null;
		try {
			url = new URL(surl);
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
				String[] values = StringUtils.split(line, ",");
				int maxIndex = values.length - 1;
				if (maxIndex >= 0) {
					mto.setISO3166TwoLetterCountryCode(values[0]);
				}
				if (maxIndex >= 1) {
					mto.setRegionCode(values[1]);
				}
				if (maxIndex >= 2) {
					mto.setCity(values[2]);
				}
				if (maxIndex >= 3) {
					mto.setPostalCode(values[3]);
				}
				if (maxIndex >= 4) {
					mto.setLatitude(values[4]);
				}
				if (maxIndex >= 5) {
					mto.setLongitude(values[5]);
				}
				if (maxIndex >= 6) {
					mto.setMetropolitanCode(values[6]);
				}
				if (maxIndex >= 7) {
					mto.setAreaCode(values[7]);
				}
				if (maxIndex >= 8) {
					mto.setIsp(values[8]);
				}
				if (maxIndex >= 9) {
					mto.setOranization(values[9]);
				}
				if (maxIndex >= 10) {
					mto.setError(MaxMindErrorsEnum.get(values[10]));
				}
			}
		} catch (MalformedURLException e) {
			log.fatal("Issue calling Maxmind", e);
			mto.setError(MaxMindErrorsEnum.VC_INTERNAL_ERROR);
		} catch (IOException e) {
			log.fatal("Issue reading Maxmind", e);
			mto.setError(MaxMindErrorsEnum.VC_INTERNAL_ERROR);
		} finally {
			if (wr != null) {
				try {
					wr.close();
				} catch (IOException e1) {
					log.fatal("Issue closing Maxmind", e1);
					mto.setError(MaxMindErrorsEnum.VC_INTERNAL_ERROR);
				}
			}
			if (rd != null) {
				try {
					rd.close();
				} catch (IOException e1) {
					log.fatal("Issue closing Maxmind", e1);
					mto.setError(MaxMindErrorsEnum.VC_INTERNAL_ERROR);
				}
			}
		}
		return mto;
	}
}