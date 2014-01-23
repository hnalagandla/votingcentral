/*
 * Created on Jan 28, 2006
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
public class VCPrivacyLevelEnum extends BaseEnum {

	public static final VCPrivacyLevelEnum PUBLIC = new VCPrivacyLevelEnum(
			"PUBLIC", 1);

	public static final VCPrivacyLevelEnum PRIVATE = new VCPrivacyLevelEnum(
			"PRIVATE", 2);

	public static final VCPrivacyLevelEnum FRIENDS = new VCPrivacyLevelEnum(
			"FRIENDS", 3);

	public static VCPrivacyLevelEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			VCPrivacyLevelEnum status = (VCPrivacyLevelEnum) iter.next();
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
	private VCPrivacyLevelEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static VCPrivacyLevelEnum get(int key) {
		return (VCPrivacyLevelEnum) getEnum(VCPrivacyLevelEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static VCPrivacyLevelEnum getElseReturn(int key,
			VCPrivacyLevelEnum elseEnum) {
		return (VCPrivacyLevelEnum) getElseReturnEnum(VCPrivacyLevelEnum.class,
				key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(VCPrivacyLevelEnum.class);
	}
}