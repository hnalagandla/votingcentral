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
public class VCUserLinkStateEnum extends BaseEnum {
	public static final VCUserLinkStateEnum INITIATED = new VCUserLinkStateEnum(
			"INITIATED", 0);

	public static final VCUserLinkStateEnum ACCEPTED = new VCUserLinkStateEnum(
			"ACCEPTED", 1);

	public static final VCUserLinkStateEnum REJECTED = new VCUserLinkStateEnum(
			"REJECTED", 2);

	public static final VCUserLinkStateEnum BROKEN = new VCUserLinkStateEnum(
			"BROKEN", 3);

	public static final VCUserLinkStateEnum RE_ESTABLISHED = new VCUserLinkStateEnum(
			"RE_ESTABLISHED", 4);
	
	public static final VCUserLinkStateEnum RECALL = new VCUserLinkStateEnum(
			"RECALL", 5);

	public static VCUserLinkStateEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			VCUserLinkStateEnum roles = (VCUserLinkStateEnum) iter.next();
			if (roles.getName().equalsIgnoreCase(name)) {
				return roles;
			}
		}
		return null;
	}

	//	Add new instances above this line

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private VCUserLinkStateEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static VCUserLinkStateEnum get(int key) {
		return (VCUserLinkStateEnum) getEnum(VCUserLinkStateEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static VCUserLinkStateEnum getElseReturn(int key,
			VCUserLinkStateEnum elseEnum) {
		return (VCUserLinkStateEnum) getElseReturnEnum(
				VCUserLinkStateEnum.class, key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(VCUserLinkStateEnum.class);
	}

}