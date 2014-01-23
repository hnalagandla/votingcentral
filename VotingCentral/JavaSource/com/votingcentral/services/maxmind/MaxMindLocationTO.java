/*
 * Created on Jun 15, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.services.maxmind;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MaxMindLocationTO {
	private String iSO3166TwoLetterCountryCode;

	private String regionCode;

	private String city;

	private String postalCode;

	private String latitude;

	private String longitude;

	private String metropolitanCode;

	private String areaCode;

	private String isp;

	private String oranization;

	private MaxMindErrorsEnum error;

	/**
	 * @return Returns the areaCode.
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode
	 *            The areaCode to set.
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return Returns the city.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return Returns the error.
	 */
	public MaxMindErrorsEnum getError() {
		return error;
	}

	/**
	 * @param error
	 *            The error to set.
	 */
	public void setError(MaxMindErrorsEnum error) {
		this.error = error;
	}

	/**
	 * @return Returns the iSO3166TwoLetterCountryCode.
	 */
	public String getISO3166TwoLetterCountryCode() {
		return iSO3166TwoLetterCountryCode;
	}

	/**
	 * @param twoLetterCountryCode
	 *            The iSO3166TwoLetterCountryCode to set.
	 */
	public void setISO3166TwoLetterCountryCode(String twoLetterCountryCode) {
		iSO3166TwoLetterCountryCode = twoLetterCountryCode;
	}

	/**
	 * @return Returns the isp.
	 */
	public String getIsp() {
		return isp;
	}

	/**
	 * @param isp
	 *            The isp to set.
	 */
	public void setIsp(String isp) {
		this.isp = isp;
	}

	/**
	 * @return Returns the latitude.
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            The latitude to set.
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
	 * @param longitude
	 *            The longitude to set.
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return Returns the metropolitanCode.
	 */
	public String getMetropolitanCode() {
		return metropolitanCode;
	}

	/**
	 * @param metropolitanCode
	 *            The metropolitanCode to set.
	 */
	public void setMetropolitanCode(String metropolitanCode) {
		this.metropolitanCode = metropolitanCode;
	}

	/**
	 * @return Returns the oranization.
	 */
	public String getOranization() {
		return oranization;
	}

	/**
	 * @param oranization
	 *            The oranization to set.
	 */
	public void setOranization(String oranization) {
		this.oranization = oranization;
	}

	/**
	 * @return Returns the postalCode.
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 *            The postalCode to set.
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return Returns the regionCode.
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode
	 *            The regionCode to set.
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
}