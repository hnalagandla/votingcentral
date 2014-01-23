/*
 * Created on Aug 23, 2006
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
public class VCCategoryTypeEnum extends BaseEnum {
	public static final VCCategoryTypeEnum INVALID = new VCCategoryTypeEnum(
			"INVALID", 0);

	public static final VCCategoryTypeEnum POLL = new VCCategoryTypeEnum("POLL",
			1);

	public static final VCCategoryTypeEnum CONTEST = new VCCategoryTypeEnum(
			"CONTEST", 2);

	public static VCCategoryTypeEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			VCCategoryTypeEnum type = (VCCategoryTypeEnum) iter.next();
			if (type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return INVALID;
	}

	//	Add new instances above this line

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private VCCategoryTypeEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static VCCategoryTypeEnum get(int key) {
		return (VCCategoryTypeEnum) getEnum(VCCategoryTypeEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static VCCategoryTypeEnum getElseReturn(int key,
			VCCategoryTypeEnum elseEnum) {
		return (VCCategoryTypeEnum) getElseReturnEnum(VCCategoryTypeEnum.class,
				key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(VCCategoryTypeEnum.class);
	}
}