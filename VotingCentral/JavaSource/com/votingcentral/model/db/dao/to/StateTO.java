/*
 * Created on Jul 6, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class StateTO {
	private int stateId;

	private int countryId;

	private String countryCode;

	private String stateNumber;

	private String code;

	private String name;

	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            The code to set.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return Returns the countryId.
	 */
	public int getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId
	 *            The countryId to set.
	 */
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the stateId.
	 */
	public int getStateId() {
		return stateId;
	}

	/**
	 * @param stateId
	 *            The stateId to set.
	 */
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return Returns the countryCode.
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode
	 *            The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return Returns the stateNumber.
	 */
	public String getStateNumber() {
		return stateNumber;
	}

	/**
	 * @param stateNumber
	 *            The stateNumber to set.
	 */
	public void setStateNumber(String stateNumber) {
		this.stateNumber = stateNumber;
	}
}