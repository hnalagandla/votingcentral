/*
 * Created on Aug 30, 2005
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
public class PollsStatusEnum extends BaseEnum {

	public static final PollsStatusEnum INVALID_STATUS = new PollsStatusEnum(
			"INVALID_STATUS", 0);

	public static final PollsStatusEnum UNFINISHED = new PollsStatusEnum(
			"UNFINISHED", 1);

	public static final PollsStatusEnum PENDING = new PollsStatusEnum(
			"PENDING", 2);

	public static final PollsStatusEnum CONFIRMED = new PollsStatusEnum(
			"CONFIRMED", 3);

	public static final PollsStatusEnum ENDEMAILSENT = new PollsStatusEnum(
			"ENDEMAILSENT", 4);

	//	Add new instances above this line
	public static PollsStatusEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			PollsStatusEnum status = (PollsStatusEnum) iter.next();
			if (status.getName().equalsIgnoreCase(name)) {
				return status;
			}
		}
		return INVALID_STATUS;
	}

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private PollsStatusEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static PollsStatusEnum get(int key) {
		return (PollsStatusEnum) getEnum(PollsStatusEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static PollsStatusEnum getElseReturn(int key,
			PollsStatusEnum elseEnum) {
		return (PollsStatusEnum) getElseReturnEnum(PollsStatusEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(PollsStatusEnum.class);
	}
}