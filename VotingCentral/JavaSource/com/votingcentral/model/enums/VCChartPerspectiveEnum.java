/*
 * Created on Jul 9, 2006
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
public class VCChartPerspectiveEnum extends BaseEnum {
	public static final VCChartPerspectiveEnum INVALID = new VCChartPerspectiveEnum(
			"INVALID", 0);

	//SIMPLE TOTALS
	public static final VCChartPerspectiveEnum SIMPLE_TOTALS = new VCChartPerspectiveEnum(
			"SIMPLE_TOTALS", 1);

	//By Age, Choice on X
	public static final VCChartPerspectiveEnum ACX = new VCChartPerspectiveEnum(
			"ACX", 2);

	//By Gender, Choice on X
	public static final VCChartPerspectiveEnum GCX = new VCChartPerspectiveEnum(
			"GCX", 3);

	//By Location, Choice on X
	public static final VCChartPerspectiveEnum LCX = new VCChartPerspectiveEnum(
			"LCX", 4);

	//By Time, Time on X
	public static final VCChartPerspectiveEnum TTX = new VCChartPerspectiveEnum(
			"TTX", 5);

	public static final VCChartPerspectiveEnum DEFAULT = SIMPLE_TOTALS;

	public static VCChartPerspectiveEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			VCChartPerspectiveEnum vccpEnum = (VCChartPerspectiveEnum) iter
					.next();
			if (vccpEnum.getName().equalsIgnoreCase(name)) {
				return vccpEnum;
			}
		}
		return INVALID;
	}

	//	Add new instances above this line

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private VCChartPerspectiveEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static VCChartPerspectiveEnum get(int key) {
		return (VCChartPerspectiveEnum) getEnum(VCChartPerspectiveEnum.class,
				key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static VCChartPerspectiveEnum getElseReturn(int key,
			VCChartPerspectiveEnum elseEnum) {
		return (VCChartPerspectiveEnum) getElseReturnEnum(
				VCChartPerspectiveEnum.class, key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(VCChartPerspectiveEnum.class);
	}
}