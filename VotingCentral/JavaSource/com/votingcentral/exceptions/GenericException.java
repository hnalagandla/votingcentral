/*
 * Created on Dec 14, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.exceptions;

import java.util.List;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public interface GenericException {
	public String getMessage();

	// get the nested exception
	public Throwable getCause();

	// get the Error data
	public ErrorData getErrorData();

	// gets the Error stack by going through the chain.
	// returns a Collection of ErrorData objects.
	public List getErrorDataStack();

	// get the stack trace by going through the chain
	public String getStackTraceX(); // JDK1.4 has getStackTrace() with different
	// return type

}