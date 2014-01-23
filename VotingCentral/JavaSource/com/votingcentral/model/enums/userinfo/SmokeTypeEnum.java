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
public class SmokeTypeEnum extends BaseEnum {
	public static final SmokeTypeEnum NO = new SmokeTypeEnum("No", 1);

	public static final SmokeTypeEnum SOCIALLY = new SmokeTypeEnum("Socially",
			2);

	public static final SmokeTypeEnum OCCASIONALLY = new SmokeTypeEnum(
			"Occasionally", 3);

	public static final SmokeTypeEnum REGULARLY = new SmokeTypeEnum(
			"Regularly", 4);

	public static final SmokeTypeEnum HEAVILY = new SmokeTypeEnum("Heavily", 5);

	public static final SmokeTypeEnum TRYING_TO_QUIT = new SmokeTypeEnum(
			"Trying To Quit", 6);

	public static final SmokeTypeEnum QUIT = new SmokeTypeEnum("Quit", 7);

	//	Add new instances above this line
	public static SmokeTypeEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			SmokeTypeEnum status = (SmokeTypeEnum) iter.next();
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
	private SmokeTypeEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static SmokeTypeEnum get(int key) {
		return (SmokeTypeEnum) getEnum(SmokeTypeEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static SmokeTypeEnum getElseReturn(int key, SmokeTypeEnum elseEnum) {
		return (SmokeTypeEnum) getElseReturnEnum(SmokeTypeEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(SmokeTypeEnum.class);
	}
}