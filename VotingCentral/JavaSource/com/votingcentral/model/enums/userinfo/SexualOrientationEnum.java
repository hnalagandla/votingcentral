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
public class SexualOrientationEnum extends BaseEnum {
	public static final SexualOrientationEnum STRAIGHT = new SexualOrientationEnum(
			"Straight", 1);

	public static final SexualOrientationEnum GAY = new SexualOrientationEnum(
			"Gay", 2);

	public static final SexualOrientationEnum BI_SEXUAL = new SexualOrientationEnum(
			"Bisexual", 3);

	public static final SexualOrientationEnum BI_CURIOUS = new SexualOrientationEnum(
			"Bi-curious", 4);

	//	Add new instances above this line
	public static SexualOrientationEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			SexualOrientationEnum status = (SexualOrientationEnum) iter.next();
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
	private SexualOrientationEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static SexualOrientationEnum get(int key) {
		return (SexualOrientationEnum) getEnum(SexualOrientationEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static SexualOrientationEnum getElseReturn(int key,
			SexualOrientationEnum elseEnum) {
		return (SexualOrientationEnum) getElseReturnEnum(
				SexualOrientationEnum.class, key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(SexualOrientationEnum.class);
	}
}