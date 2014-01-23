/*
 * Created on Apr 13, 2006
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
public class VCUserRolesEnum extends BaseEnum {
	public static final VCUserRolesEnum INVALID_ROLE = new VCUserRolesEnum(
			"INVALID_ROLE", 0);

	public static final VCUserRolesEnum USER = new VCUserRolesEnum("USER", 1);

	public static final VCUserRolesEnum ADMIN = new VCUserRolesEnum("ADMIN", 2);

	public static VCUserRolesEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			VCUserRolesEnum roles = (VCUserRolesEnum) iter.next();
			if (roles.getName().equalsIgnoreCase(name)) {
				return roles;
			}
		}
		return INVALID_ROLE;
	}

	//	Add new instances above this line

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private VCUserRolesEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static VCUserRolesEnum get(int key) {
		return (VCUserRolesEnum) getEnum(VCUserRolesEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static VCUserRolesEnum getElseReturn(int key,
			VCUserRolesEnum elseEnum) {
		return (VCUserRolesEnum) getElseReturnEnum(VCUserRolesEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(VCUserRolesEnum.class);
	}

}