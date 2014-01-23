/*
 * Created on Dec 17, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.enums;

import java.util.ListIterator;

import com.votingcentral.util.enums.BaseEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TafTypeEnum extends BaseEnum {

	public static final TafTypeEnum POLL = new TafTypeEnum("POLL", 0);

	public static final TafTypeEnum HOME = new TafTypeEnum("HOME", 1);

	public static final TafTypeEnum CONTEST = new TafTypeEnum("CONTEST", 2);

	// Add new instances above this line

	// -----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	// -----------------------------------------------------------------//
	private TafTypeEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static TafTypeEnum get(int key) {
		return (TafTypeEnum) getEnum(TafTypeEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static TafTypeEnum getElseReturn(int key, TafTypeEnum elseEnum) {
		return (TafTypeEnum) getElseReturnEnum(TafTypeEnum.class, key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(TafTypeEnum.class);
	}
}