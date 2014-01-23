/*
 * Created on Apr 21, 2007
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
public class TShirtSizeEnum extends BaseEnum {

	public static final TShirtSizeEnum XS = new TShirtSizeEnum("XS", 1);

	public static final TShirtSizeEnum S = new TShirtSizeEnum("S", 2);

	public static final TShirtSizeEnum M = new TShirtSizeEnum("M", 3);

	public static final TShirtSizeEnum L = new TShirtSizeEnum("L", 4);

	public static final TShirtSizeEnum XL = new TShirtSizeEnum("XL", 5);

	public static final TShirtSizeEnum XXL = new TShirtSizeEnum("XXL", 6);

	public static final TShirtSizeEnum XXXL = new TShirtSizeEnum("XXXL", 7);

	//	Add new instances above this line
	public static TShirtSizeEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			TShirtSizeEnum status = (TShirtSizeEnum) iter.next();
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
	private TShirtSizeEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static TShirtSizeEnum get(int key) {
		return (TShirtSizeEnum) getEnum(TShirtSizeEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static TShirtSizeEnum getElseReturn(int key, TShirtSizeEnum elseEnum) {
		return (TShirtSizeEnum) getElseReturnEnum(TShirtSizeEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(TShirtSizeEnum.class);
	}
}