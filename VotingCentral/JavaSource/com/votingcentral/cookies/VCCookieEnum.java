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
public class VCCookieEnum extends BaseEnum {
	private final int maxAgeSecs;

	private final boolean secure; //is cookie data coming over a https

	private final boolean httpOnly; ///is cookie acessible via JS

	private final boolean isAutoRefreshed;

	public static final VCCookieEnum POLL = new VCCookieEnum("saki", 864000,
			false, false, true, 1);

	public static final VCCookieEnum LOGIN = new VCCookieEnum("indy", 864000,
			false, false, true, 2);

	public static final VCCookieEnum USERNAME = new VCCookieEnum("user",
			864000, false, false, true, 3);

	//	Add new instances above this line
	public static VCCookieEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			VCCookieEnum status = (VCCookieEnum) iter.next();
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
	private VCCookieEnum(String name, int maxAgeSecs, boolean secure,
			boolean httpOnly, boolean isAutoRefreshed, int intValue) {
		super(intValue, name);
		this.maxAgeSecs = maxAgeSecs;
		this.secure = secure;
		this.httpOnly = httpOnly;
		this.isAutoRefreshed = isAutoRefreshed;
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static VCCookieEnum get(int key) {
		return (VCCookieEnum) getEnum(VCCookieEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static VCCookieEnum getElseReturn(int key, VCCookieEnum elseEnum) {
		return (VCCookieEnum) getElseReturnEnum(VCCookieEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(VCCookieEnum.class);
	}

	/**
	 * @return Returns the maxAgeSecs.
	 */
	public int getMaxAgeSecs() {
		return maxAgeSecs;
	}

	/**
	 * @return Returns the httpOnly.
	 */
	public boolean isHttpOnly() {
		return httpOnly;
	}

	/**
	 * @return Returns the secure.
	 */
	public boolean isSecure() {
		return secure;
	}

	/**
	 * @return Returns the isAutoRefreshed.
	 */
	public boolean isAutoRefreshed() {
		return isAutoRefreshed;
	}
}