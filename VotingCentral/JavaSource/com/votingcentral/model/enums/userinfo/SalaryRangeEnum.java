/*
 * Created on May 1, 2007
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
public class SalaryRangeEnum extends BaseEnum {

	public static final SalaryRangeEnum ZERO_TO_FORTY = new SalaryRangeEnum(
			"0-40000", 1);

	public static final SalaryRangeEnum FORTY_TO_FIFTY = new SalaryRangeEnum(
			"40001-50000", 2);

	public static final SalaryRangeEnum FIFTY_TO_SIXTY = new SalaryRangeEnum(
			"50001-60000", 3);

	public static final SalaryRangeEnum SIXTY_TO_SEVENTY = new SalaryRangeEnum(
			"60001-70000", 4);

	public static final SalaryRangeEnum SEVENTY_TO_EIGHTY = new SalaryRangeEnum(
			"70001-80000", 5);

	public static final SalaryRangeEnum EIGHTY_TO_NINTY = new SalaryRangeEnum(
			"80001-90000", 6);

	public static final SalaryRangeEnum NINTY_TO_HUNDRED = new SalaryRangeEnum(
			"90001-100000", 7);

	public static final SalaryRangeEnum HUNDRED_TO_ONETWENTY = new SalaryRangeEnum(
			"100001-120000", 8);

	public static final SalaryRangeEnum ONETWENTY_TO_ONEFORTY = new SalaryRangeEnum(
			"120001-140000", 9);

	public static final SalaryRangeEnum ONEFORTY_TO_ONESIXTY = new SalaryRangeEnum(
			"140001-160000", 10);

	public static final SalaryRangeEnum ONEFIFTY_PLUS = new SalaryRangeEnum(
			"160001+", 11);

	public static SalaryRangeEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			SalaryRangeEnum status = (SalaryRangeEnum) iter.next();
			if (status.getName().equalsIgnoreCase(name)) {
				return status;
			}
		}
		return null;
	}

	//	Add new instances above this line

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private SalaryRangeEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static SalaryRangeEnum get(int key) {
		return (SalaryRangeEnum) getEnum(SalaryRangeEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static SalaryRangeEnum getElseReturn(int key,
			SalaryRangeEnum elseEnum) {
		return (SalaryRangeEnum) getElseReturnEnum(SalaryRangeEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(SalaryRangeEnum.class);
	}

}