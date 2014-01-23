/*
 * Created on Apr 11, 2007
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
public class PartyEnum extends BaseEnum {

	public static final PartyEnum DEMOCRAT = new PartyEnum("Democrat", 1);

	public static final PartyEnum REPUBLICAN = new PartyEnum("Republican", 2);

	public static final PartyEnum INDEPENDENT = new PartyEnum("Independent", 3);

	//	Add new instances above this line
	public static PartyEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			PartyEnum status = (PartyEnum) iter.next();
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
	private PartyEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static PartyEnum get(int key) {
		return (PartyEnum) getEnum(PartyEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static PartyEnum getElseReturn(int key, PartyEnum elseEnum) {
		return (PartyEnum) getElseReturnEnum(PartyEnum.class, key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(PartyEnum.class);
	}
}