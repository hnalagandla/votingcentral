/*
 * Created on Mar 20, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.cookies;

import java.util.Iterator;
import java.util.ListIterator;

import com.votingcentral.util.enums.BaseEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class VCCookieletEnum extends BaseEnum {
	private final VCCookieEnum parent;

	public static final VCCookieletEnum LOGIN = new VCCookieletEnum("login",
			VCCookieEnum.LOGIN, 1);

	public static final VCCookieletEnum USERNAME = new VCCookieletEnum("user",
			VCCookieEnum.USERNAME, 2);

	public static final VCCookieletEnum LOGIN_IP = new VCCookieletEnum(
			"loginip", VCCookieEnum.LOGIN, 3);

	//	Add new instances above this line
	public static VCCookieletEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			VCCookieletEnum status = (VCCookieletEnum) iter.next();
			if (status.getName().equalsIgnoreCase(name)) {
				return status;
			}
		}
		return null;
	}

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private VCCookieletEnum(String name, VCCookieEnum parent, int intValue) {
		super(intValue, name);
		this.parent = parent;
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static VCCookieletEnum get(int key) {
		return (VCCookieletEnum) getEnum(VCCookieletEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static VCCookieletEnum getElseReturn(int key,
			VCCookieletEnum elseEnum) {
		return (VCCookieletEnum) getElseReturnEnum(VCCookieletEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(VCCookieletEnum.class);
	}

	/**
	 * @return Returns the parent.
	 */
	public VCCookieEnum getParent() {
		return parent;
	}
}