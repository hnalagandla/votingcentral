/*
 * Created on May 22, 2007
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
public class VCWinTypeCodeEnum extends BaseEnum {

	public static final VCWinTypeCodeEnum ACTIVITY = new VCWinTypeCodeEnum(
			"ACTIVITY", 1);

	//	Add new instances above this line

	public static VCWinTypeCodeEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			VCWinTypeCodeEnum roles = (VCWinTypeCodeEnum) iter.next();
			if (roles.getName().equalsIgnoreCase(name)) {
				return roles;
			}
		}
		return null;
	}

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private VCWinTypeCodeEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static VCWinTypeCodeEnum get(int key) {
		return (VCWinTypeCodeEnum) getEnum(VCWinTypeCodeEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static VCWinTypeCodeEnum getElseReturn(int key,
			VCWinTypeCodeEnum elseEnum) {
		return (VCWinTypeCodeEnum) getElseReturnEnum(VCWinTypeCodeEnum.class,
				key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(VCWinTypeCodeEnum.class);
	}

}