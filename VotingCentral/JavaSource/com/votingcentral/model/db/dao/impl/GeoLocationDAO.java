/*
 * Created on Sep 10, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.impl;

import java.net.URLDecoder;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.votingcentral.model.db.dao.IGeoLocationDAO;
import com.votingcentral.model.db.dao.VCDAOFactory;
import com.votingcentral.model.db.dao.to.GeoLocationTO;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class GeoLocationDAO extends RdbmsDAO implements IGeoLocationDAO{

	private Log log = LogFactory.getLog(this.getClass().getName());

	private String IP_PATTERN = "(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)";

	public GeoLocationTO getLocation(String ip) throws SQLException {
		/***********************************************************************
		 * *******************************************************************\ |*
		 * Get the country and city (if available) for this /24 \
		 **********************************************************************/
		int a = 0, b = 0, c = 0, d = 0;
		Pattern p = Pattern.compile(IP_PATTERN);
		Matcher m = p.matcher(ip);
		if (m.find()) {
			a = new Integer(m.group(1)).intValue();
			b = new Integer(m.group(2)).intValue();
			c = new Integer(m.group(3)).intValue();
			d = new Integer(m.group(4)).intValue();
		}
		String sql = "SELECT * FROM ip4_" + a + " WHERE b=" + b + " AND c=" + c;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		GeoLocationTO gto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			rs = pps1.executeQuery();
			short bb = 0;
			short cc = 0;
			short countryCode = 0;
			short cityCode = 0;
			Date cron;
			int rowCount = 0;
			gto = new GeoLocationTO();
			while (rs.next()) {
				bb = rs.getShort("b");
				cc = rs.getShort("c");
				countryCode = rs.getShort("COUNTRY");
				gto.setCountryCode(String.valueOf(countryCode));
				cityCode = rs.getShort("CITY");
				gto.setCityCode(String.valueOf(cityCode));
				cron = rs.getDate("CRON");
				rowCount++;
			}
			if (rs != null) {
				rs.close();
			}
			if (pps1 != null) {
				pps1.close();
			}
			if (rowCount > 0) {
				int countryId = 0;
				sql = "SELECT name, lat, lng FROM cityByCountry WHERE city="
						+ cityCode;
				pps1 = conn.prepareStatement(sql);
				rs = pps1.executeQuery();
				while (rs.next()) {
					String cityName = URLDecoder.decode(rs.getString("NAME"));
					gto.setCityName(cityName);
					String lat = rs.getString("LAT");
					gto.setLatitude(lat);
					String lng = rs.getString("LNG");
					gto.setLongitude(lng);
					countryId = rs.getInt("COUNTRY");
				}
				if (rs != null) {
					rs.close();
				}
				if (pps1 != null) {
					pps1.close();
				}
				sql = "SELECT name, code FROM countries WHERE id=" + countryId;
				pps1 = conn.prepareStatement(sql);
				rs = pps1.executeQuery();
				while (rs.next()) {
					String countryName = rs.getString("NAME");
					gto.setCountryName(countryName);
					countryCode = rs.getShort("CODE");
					gto.setCountryCode(String.valueOf(countryCode));
				}
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps1 != null) {
					pps1.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return gto;
	}
	
	public GeoLocationTO getLongAndLat(String ip) throws SQLException {
		/***********************************************************************
		 * *******************************************************************\ |*
		 * Get the country and city (if available) for this /24 \
		 **********************************************************************/
		int a = 0, b = 0, c = 0, d = 0;
		Pattern p = Pattern.compile(IP_PATTERN);
		Matcher m = p.matcher(ip);
		if (m.find()) {
			a = new Integer(m.group(1)).intValue();
			b = new Integer(m.group(2)).intValue();
			c = new Integer(m.group(3)).intValue();
			d = new Integer(m.group(4)).intValue();
		}
		String sql = "SELECT * FROM ip4_" + a + " WHERE b=" + b + " AND c=" + c;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		GeoLocationTO gto = null;
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			rs = pps1.executeQuery();
			int bb = 0;
			int cc = 0;
			int countryCode = 0;
			int cityCode = 0;
			Date cron;
			int rowCount = 0;
			gto = new GeoLocationTO();
			while (rs.next()) {
				bb = rs.getInt("B");
				cc = rs.getInt("C");
				countryCode = rs.getInt("COUNTRY");
				gto.setCountryCode(String.valueOf(countryCode));
				cityCode = rs.getInt("CITY");
				gto.setCityCode(String.valueOf(cityCode));
				cron = rs.getDate("CRON");
				rowCount++;
			}
			if (rs != null) {
				rs.close();
			}
			if (pps1 != null) {
				pps1.close();
			}
			if (rowCount > 0) {
				int countryId = 0;
				sql = "SELECT name, lat, lng FROM cityByCountry WHERE city="
						+ cityCode;
				pps1 = conn.prepareStatement(sql);
				rs = pps1.executeQuery();
				while (rs.next()) {
					String cityName = URLDecoder.decode(rs.getString("NAME"));
					gto.setCityName(cityName);
					String lat = rs.getString("LAT");
					gto.setLatitude(lat);
					String lng = rs.getString("LNG");
					gto.setLongitude(lng);
					countryId = rs.getInt("COUNTRY");
				}
				if (rs != null) {
					rs.close();
				}
				if (pps1 != null) {
					pps1.close();
				}				
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps1 != null) {
					pps1.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return gto;
	}
	
	public String getCountry(String countryId) throws SQLException {
		
		
		String sql = "SELECT * FROM countries WHERE id=" + countryId;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pps1 = null;
		String country = "";
		try {
			conn = VCDAOFactory.getConnection();
			pps1 = conn.prepareStatement(sql);
			rs = pps1.executeQuery();		
		
			while (rs.next()) {
				country = rs.getString("NAME");				
			}			
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pps1 != null) {
					pps1.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				log.fatal("Connection.close", e);
				throw e;
			}
		}
		return country;
	}
}