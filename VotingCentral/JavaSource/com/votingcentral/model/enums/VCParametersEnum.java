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
public class VCParametersEnum extends BaseEnum {

	public static final VCParametersEnum NEW_POLLS_DIGEST_LAST_SENT = new VCParametersEnum(
			"NEW_POLLS_DIGEST_LAST_SENT", 1);

	//	Add new instances above this line

	public static VCParametersEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			VCParametersEnum roles = (VCParametersEnum) iter.next();
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
	private VCParametersEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static VCParametersEnum get(int key) {
		return (VCParametersEnum) getEnum(VCParametersEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static VCParametersEnum getElseReturn(int key,
			VCParametersEnum elseEnum) {
		return (VCParametersEnum) getElseReturnEnum(VCParametersEnum.class,
				key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(VCParametersEnum.class);
	}

}