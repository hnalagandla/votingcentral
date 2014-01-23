/*
 * Created on Apr 30, 2007
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
public class UnionMembershipEnum extends BaseEnum {

	public static final UnionMembershipEnum NO = new UnionMembershipEnum("No",
			1);

	public static final UnionMembershipEnum YES = new UnionMembershipEnum(
			"Yes", 2);

	public static UnionMembershipEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			UnionMembershipEnum status = (UnionMembershipEnum) iter.next();
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
	private UnionMembershipEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static UnionMembershipEnum get(int key) {
		return (UnionMembershipEnum) getEnum(UnionMembershipEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static UnionMembershipEnum getElseReturn(int key,
			UnionMembershipEnum elseEnum) {
		return (UnionMembershipEnum) getElseReturnEnum(
				UnionMembershipEnum.class, key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(UnionMembershipEnum.class);
	}

}