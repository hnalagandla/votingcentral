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
public class DrinkTypeEnum extends BaseEnum {
	public static final DrinkTypeEnum NO = new DrinkTypeEnum("No", 1);

	public static final DrinkTypeEnum SOCIALLY = new DrinkTypeEnum("Socially",
			2);

	public static final DrinkTypeEnum OCCASSIONALLY = new DrinkTypeEnum(
			"Occasionally", 3);

	public static final DrinkTypeEnum REGULARLY = new DrinkTypeEnum(
			"Regularly", 4);

	public static final DrinkTypeEnum HEAVILY = new DrinkTypeEnum("Heavily", 5);

	//	Add new instances above this line
	public static DrinkTypeEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			DrinkTypeEnum status = (DrinkTypeEnum) iter.next();
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
	private DrinkTypeEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static DrinkTypeEnum get(int key) {
		return (DrinkTypeEnum) getEnum(DrinkTypeEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static DrinkTypeEnum getElseReturn(int key, DrinkTypeEnum elseEnum) {
		return (DrinkTypeEnum) getElseReturnEnum(DrinkTypeEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(DrinkTypeEnum.class);
	}
}