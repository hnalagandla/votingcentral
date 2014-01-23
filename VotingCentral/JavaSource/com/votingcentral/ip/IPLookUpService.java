/*
 * Created on Sep 10, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.ip;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.votingcentral.model.db.dao.IGeoLocationDAO;
import com.votingcentral.model.db.dao.impl.DAOFactory;
import com.votingcentral.model.db.dao.to.GeoLocationTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class IPLookUpService {

	private String IP_PATTERN = "(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)";

	public boolean isPrivate(String ip) {
		int a = 0, b = 0, c = 0, d = 0;
		Pattern p = Pattern.compile(IP_PATTERN);
		Matcher m = p.matcher(ip);
		if (m.find()) {
			a = new Integer(m.group(1)).intValue();
			b = new Integer(m.group(2)).intValue();
			c = new Integer(m.group(3)).intValue();
			d = new Integer(m.group(4)).intValue();
		}

		if (a == 0 || b == 0 || c == 0 || d == 0 || a == 10
				|| (a == 172 && b >= 16 && b <= 31) || (a == 192 && b == 168)
				|| a == 239 || a == 0 || a == 127) {
			return true;
		}

		return false;
	}

	public GeoLocationTO getIPLocation(String ip) throws SQLException {
		int a, b, c, d = 0;
		Pattern p = Pattern.compile(IP_PATTERN);
		Matcher m = p.matcher(ip);
		if (m.find()) {
			a = new Integer(m.group(1)).intValue();
			b = new Integer(m.group(2)).intValue();
			c = new Integer(m.group(3)).intValue();
			d = new Integer(m.group(4)).intValue();
		}

		if (isPrivate(ip)) {
			GeoLocationTO gl = new GeoLocationTO();
			gl.setCountryName("Private Address");
			gl.setCityCode("0");
			gl.setCityName("Private Address");
			gl.setCountryCode("XX");
			return gl;
		}
		DAOFactory dao = DAOFactory.getDAOFactory();
		IGeoLocationDAO gdao = dao.getGeoLocationDAO();
		GeoLocationTO gto = gdao.getLocation(ip);
		if (gto == null) {
			GeoLocationTO gl = new GeoLocationTO();
			gl.setCountryName("Unknown");
			gl.setCityCode("0");
			gl.setCityName("Unknown");
			gl.setCountryCode("XX");
			return gl;
		}
		return gto;
	}

	public GeoLocationTO getIPFastCoord(String ip) throws SQLException {
		if (isPrivate(ip)) {
			GeoLocationTO gl = new GeoLocationTO();
			gl.setCountryName("Private Address");
			gl.setCityCode("0");
			gl.setCityName("Private Address");
			gl.setCountryCode("XX");
			return gl;
		}
		DAOFactory dao = DAOFactory.getDAOFactory();
		IGeoLocationDAO gdao = dao.getGeoLocationDAO();
		GeoLocationTO gto = gdao.getLongAndLat(ip);
		if (gto == null) {
			GeoLocationTO gl = new GeoLocationTO();
			gl.setLatitude("Unknown");
			gl.setLongitude("Unknown");
			return gl;
		}
		return gto;
	}

	public String getCountry(String id) throws SQLException {
		DAOFactory dao = DAOFactory.getDAOFactory();
		IGeoLocationDAO gdao = dao.getGeoLocationDAO();
		String country = gdao.getCountry(id);
		return country;
	}

	public static void main(String[] args) {
		IPLookUpService service = new IPLookUpService();
		try {
			service.getIPLocation("69.107.99.20");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}