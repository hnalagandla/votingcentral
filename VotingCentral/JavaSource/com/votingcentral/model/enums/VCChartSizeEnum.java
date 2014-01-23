/*
 * Created on Aug 6, 2006
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
public class VCChartSizeEnum extends BaseEnum {
	public static final VCChartSizeEnum INVALID = new VCChartSizeEnum(
			"INVALID", 0);

	//Big size
	public static final VCChartSizeEnum BIG = new VCChartSizeEnum("BIG", 1);

	//Small
	public static final VCChartSizeEnum SMALL = new VCChartSizeEnum("SMALL", 2);

	//Custom
	public static final VCChartSizeEnum CUSTOM = new VCChartSizeEnum("CUSTOM",
			3);

	public static final VCChartSizeEnum DEFAULT = SMALL;

	public static VCChartSizeEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			VCChartSizeEnum vccsEnum = (VCChartSizeEnum) iter.next();
			if (vccsEnum.getName().equalsIgnoreCase(name)) {
				return vccsEnum;
			}
		}
		return INVALID;
	}

	//	Add new instances above this line

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private VCChartSizeEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static VCChartSizeEnum get(int key) {
		return (VCChartSizeEnum) getEnum(VCChartSizeEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static VCChartSizeEnum getElseReturn(int key,
			VCChartSizeEnum elseEnum) {
		return (VCChartSizeEnum) getElseReturnEnum(VCChartSizeEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(VCChartSizeEnum.class);
	}
}