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
public class VCChartTypeEnum extends BaseEnum {
	public static final VCChartTypeEnum INVALID = new VCChartTypeEnum(
			"INVALID", 0);

	public static final VCChartTypeEnum PIE = new VCChartTypeEnum("PIE", 1);

	public static final VCChartTypeEnum BAR = new VCChartTypeEnum("BAR", 2);

	public static final VCChartTypeEnum DEFAULT = BAR;

	public static VCChartTypeEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			VCChartTypeEnum vcctEnum = (VCChartTypeEnum) iter.next();
			if (vcctEnum.getName().equalsIgnoreCase(name)) {
				return vcctEnum;
			}
		}
		return INVALID;
	}

	//	Add new instances above this line

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private VCChartTypeEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static VCChartTypeEnum get(int key) {
		return (VCChartTypeEnum) getEnum(VCChartTypeEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static VCChartTypeEnum getElseReturn(int key,
			VCChartTypeEnum elseEnum) {
		return (VCChartTypeEnum) getElseReturnEnum(VCChartTypeEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(VCChartTypeEnum.class);
	}
}