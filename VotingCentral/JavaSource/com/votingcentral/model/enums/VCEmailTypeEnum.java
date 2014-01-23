/*
 * Created on Apr 21, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.enums;

import java.util.Iterator;
import java.util.ListIterator;

import com.votingcentral.util.enums.BaseEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCEmailTypeEnum extends BaseEnum {
	public static final VCEmailTypeEnum INVALID = new VCEmailTypeEnum(
			"INVALID", 0);

	public static final VCEmailTypeEnum REG_CONFIRM_CODE = new VCEmailTypeEnum(
			"REG_CONFIRM_CODE", 1);

	public static final VCEmailTypeEnum PASSWORD_REMINDER = new VCEmailTypeEnum(
			"PASSWORD_REMINDER", 2);

	public static final VCEmailTypeEnum TAF = new VCEmailTypeEnum("TAF", 3);

	//send an email to the user when their picture was selected in a poll with
	// the
	//poll id.
	public static final VCEmailTypeEnum CONTESTS_PICTURE_SELECTED = new VCEmailTypeEnum(
			"CONTESTS_PICTURE_SELECTED", 4);

	public static final VCEmailTypeEnum POLL_ENDED = new VCEmailTypeEnum(
			"POLL_ENDED", 5);

	public static final VCEmailTypeEnum CONTEST_ENDED = new VCEmailTypeEnum(
			"CONTEST_ENDED", 6);

	public static final VCEmailTypeEnum POLL_ENDED_DETAILED = new VCEmailTypeEnum(
			"POLL_ENDED_DETAILED", 7);

	public static final VCEmailTypeEnum USER_REQUEST_TO_LINK_USER = new VCEmailTypeEnum(
			"USER_REQUEST_TO_LINK_USER", 8);

	public static final VCEmailTypeEnum USER_REQUEST_TO_LINK_NEW_USER = new VCEmailTypeEnum(
			"USER_REQUEST_TO_LINK_NEW_USER", 9);

	public static final VCEmailTypeEnum VACO_T_SHIRT_WINNER = new VCEmailTypeEnum(
			"VACO_T_SHIRT_WINNER", 10);

	public static final VCEmailTypeEnum POLL_STARTED = new VCEmailTypeEnum(
			"POLL_STARTED", 11);

	public static final VCEmailTypeEnum UNFINISHED_POLL_REMINDER = new VCEmailTypeEnum(
			"UNFINISHED_POLL_REMINDER", 12);

	public static VCEmailTypeEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			VCEmailTypeEnum email = (VCEmailTypeEnum) iter.next();
			if (email.getName().equalsIgnoreCase(name)) {
				return email;
			}
		}
		return INVALID;
	}

	//	Add new instances above this line

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private VCEmailTypeEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static VCEmailTypeEnum get(int key) {
		return (VCEmailTypeEnum) getEnum(VCEmailTypeEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static VCEmailTypeEnum getElseReturn(int key,
			VCEmailTypeEnum elseEnum) {
		return (VCEmailTypeEnum) getElseReturnEnum(VCEmailTypeEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(VCEmailTypeEnum.class);
	}
}