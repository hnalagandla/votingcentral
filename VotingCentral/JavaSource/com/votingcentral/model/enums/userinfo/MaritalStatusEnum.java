/*
 * Created on Apr 21, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.enums.userinfo;

import java.util.Iterator;
import java.util.ListIterator;

import com.votingcentral.util.enums.BaseEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MaritalStatusEnum extends BaseEnum {
	public static final MaritalStatusEnum SINGLE = new MaritalStatusEnum(
			"Single", 1);

	public static final MaritalStatusEnum MARRIED = new MaritalStatusEnum(
			"Married", 2);

	public static final MaritalStatusEnum COMMITED = new MaritalStatusEnum(
			"Committed", 3);

	public static final MaritalStatusEnum OPEN_MARRIAGE = new MaritalStatusEnum(
			"Open Marriage", 4);

	public static final MaritalStatusEnum OPEN_RELATIONSHIP = new MaritalStatusEnum(
			"Open Relationship", 5);

	//	Add new instances above this line
	public static MaritalStatusEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			MaritalStatusEnum status = (MaritalStatusEnum) iter.next();
			if (status.getName().equalsIgnoreCase(name)) {
				return status;
			}
		}
		return null;
	}

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private MaritalStatusEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static MaritalStatusEnum get(int key) {
		return (MaritalStatusEnum) getEnum(MaritalStatusEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static MaritalStatusEnum getElseReturn(int key,
			MaritalStatusEnum elseEnum) {
		return (MaritalStatusEnum) getElseReturnEnum(MaritalStatusEnum.class,
				key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(MaritalStatusEnum.class);
	}
}