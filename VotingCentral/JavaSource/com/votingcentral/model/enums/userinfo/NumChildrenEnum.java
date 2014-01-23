/*
 * Created on Apr 30, 2007
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
public class NumChildrenEnum extends BaseEnum {

	public static final NumChildrenEnum ONE = new NumChildrenEnum("One", 1);

	public static final NumChildrenEnum TWO = new NumChildrenEnum("Two", 2);

	public static final NumChildrenEnum THREE = new NumChildrenEnum("Three", 3);

	public static final NumChildrenEnum FOUR = new NumChildrenEnum("Four", 4);

	public static final NumChildrenEnum FIVE = new NumChildrenEnum("Five", 5);

	public static final NumChildrenEnum SIX = new NumChildrenEnum("Six", 6);

	public static final NumChildrenEnum SEVEN = new NumChildrenEnum("Seven", 7);

	//	Add new instances above this line
	public static NumChildrenEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			NumChildrenEnum status = (NumChildrenEnum) iter.next();
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
	private NumChildrenEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static NumChildrenEnum get(int key) {
		return (NumChildrenEnum) getEnum(NumChildrenEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static NumChildrenEnum getElseReturn(int key,
			NumChildrenEnum elseEnum) {
		return (NumChildrenEnum) getElseReturnEnum(NumChildrenEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(NumChildrenEnum.class);
	}
}