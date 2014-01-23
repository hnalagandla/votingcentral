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
public class IdeologyEnum extends BaseEnum {

	public static final IdeologyEnum LIBERAL = new IdeologyEnum("Liberal", 1);

	public static final IdeologyEnum MODERATE = new IdeologyEnum("Moderate", 2);

	public static final IdeologyEnum CONSERVATIVE = new IdeologyEnum(
			"Conservative", 3);

	//	Add new instances above this line
	public static IdeologyEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			IdeologyEnum status = (IdeologyEnum) iter.next();
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
	private IdeologyEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static IdeologyEnum get(int key) {
		return (IdeologyEnum) getEnum(IdeologyEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static IdeologyEnum getElseReturn(int key, IdeologyEnum elseEnum) {
		return (IdeologyEnum) getElseReturnEnum(IdeologyEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(IdeologyEnum.class);
	}

}