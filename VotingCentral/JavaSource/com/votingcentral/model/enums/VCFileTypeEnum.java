/*
 * Created on Apr 25, 2007
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
public class VCFileTypeEnum extends BaseEnum {
	public static final VCFileTypeEnum INVALID = new VCFileTypeEnum("INVALID",
			0);

	public static final VCFileTypeEnum POLL = new VCFileTypeEnum("POLL", 1);

	public static final VCFileTypeEnum CONTEST = new VCFileTypeEnum("CONTEST",
			2);

	public static final VCFileTypeEnum MY_IMAGE = new VCFileTypeEnum(
			"MY_IMAGE", 3);

	//	Add new instances above this line

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private VCFileTypeEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static VCFileTypeEnum get(int key) {
		return (VCFileTypeEnum) getEnum(VCFileTypeEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static VCFileTypeEnum getElseReturn(int key, VCFileTypeEnum elseEnum) {
		return (VCFileTypeEnum) getElseReturnEnum(VCFileTypeEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(VCFileTypeEnum.class);
	}

}