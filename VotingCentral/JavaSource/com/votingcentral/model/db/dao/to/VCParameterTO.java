/*
 * Created on Jul 6, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.db.dao.to;

import java.util.Date;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCParameterTO {
	private long parameterId;

	private String parameterName;

	private String parameterValue;

	private Date parameterLastModified;

	/**
	 * @return Returns the parameterId.
	 */
	public long getParameterId() {
		return parameterId;
	}

	/**
	 * @param parameterId
	 *            The parameterId to set.
	 */
	public void setParameterId(long parameterId) {
		this.parameterId = parameterId;
	}

	/**
	 * @return Returns the parameterLastModified.
	 */
	public Date getParameterLastModified() {
		return parameterLastModified;
	}

	/**
	 * @param parameterLastModified
	 *            The parameterLastModified to set.
	 */
	public void setParameterLastModified(Date parameterLastModified) {
		this.parameterLastModified = parameterLastModified;
	}

	/**
	 * @return Returns the parameterName.
	 */
	public String getParameterName() {
		return parameterName;
	}

	/**
	 * @param parameterName
	 *            The parameterName to set.
	 */
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	/**
	 * @return Returns the parameterValue.
	 */
	public String getParameterValue() {
		return parameterValue;
	}

	/**
	 * @param parameterValue
	 *            The parameterValue to set.
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
}