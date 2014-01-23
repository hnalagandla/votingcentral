/*
 * Created on Sep 10, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

/**
 * @author harishn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GeoLocationTO {

	private String countryName;

	private String cityName;

	private String countryCode;
	
	private String cityCode;
	
	private String ipAddress;
	
	private String latitude;
	
	private String longitude;
	
	
	/**
	 * 
	 */
	public GeoLocationTO() {

		
	}
	
	public GeoLocationTO(String ipAddress) {
		this.ipAddress = ipAddress;		
	}
	
		
	/**
	 * @return Returns the cityCode.
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * @param cityCode The cityCode to set.
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * @return Returns the cityName.
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName The cityName to set.
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}	
	/**
	 * @return Returns the countryCode.
	 */
	public String getCountryCode() {
		return countryCode;
	}
	/**
	 * @param countryCode The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	/**
	 * @return Returns the countryName.
	 */
	public String getCountryName() {
		return countryName;
	}
	/**
	 * @param countryName The countryName to set.
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	/**
	 * @return Returns the ipAddress.
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * @param ipAddress The ipAddress to set.
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * @return Returns the latitude.
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude The latitude to set.
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return Returns the longitude.
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude The longitude to set.
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
