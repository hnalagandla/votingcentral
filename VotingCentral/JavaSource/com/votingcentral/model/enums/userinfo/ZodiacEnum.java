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
public class ZodiacEnum extends BaseEnum {

	public static final ZodiacEnum ARIES = new ZodiacEnum("Aries", 1);

	public static final ZodiacEnum TAURUS = new ZodiacEnum("Taurus", 2);

	public static final ZodiacEnum GEMINI = new ZodiacEnum("Gemini", 3);

	public static final ZodiacEnum CANCER = new ZodiacEnum("Cancer", 4);

	public static final ZodiacEnum LEO = new ZodiacEnum("Leo", 5);

	public static final ZodiacEnum VIRGO = new ZodiacEnum("Virgo", 6);

	public static final ZodiacEnum LIBRA = new ZodiacEnum("Libra", 7);

	public static final ZodiacEnum SCORPIO = new ZodiacEnum("Scorpio", 8);

	public static final ZodiacEnum SAGITTARIUS = new ZodiacEnum("Sagittarius",
			9);

	public static final ZodiacEnum CAPRICON = new ZodiacEnum("Capricon", 10);

	public static final ZodiacEnum AQUARIUS = new ZodiacEnum("Aquarius", 11);

	public static final ZodiacEnum PISCES = new ZodiacEnum("Pisces", 12);

	//	Add new instances above this line
	public static ZodiacEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			ZodiacEnum status = (ZodiacEnum) iter.next();
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
	private ZodiacEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static ZodiacEnum get(int key) {
		return (ZodiacEnum) getEnum(ZodiacEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static ZodiacEnum getElseReturn(int key, ZodiacEnum elseEnum) {
		return (ZodiacEnum) getElseReturnEnum(ZodiacEnum.class, key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(ZodiacEnum.class);
	}

}