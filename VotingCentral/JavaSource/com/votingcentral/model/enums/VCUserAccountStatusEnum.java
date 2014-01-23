/*
 * Created on Aug 21, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.enums;

/**
 * @author harishn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VCUserAccountStatusEnum {

	public static final String CREATED = "CREATED";
	
	//After email is confirmed
	public static final String CONFIRMED = "CONFIRMED";
	
	//Pending email confirmation
	public static final String PENDING = "PENDING";
	public static final String SUSPENDED = "SUSPENDED";
	public static final String FORGOTPSWD = "FORGOTPSWD";
	//use this for a soft delete
	public static final String DELETED = "DELETED";
}
