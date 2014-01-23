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
public class RaceEnum extends BaseEnum {

	public static final RaceEnum AFRICAN_AMERICAN = new RaceEnum(
			"African American(black)", 1);

	public static final RaceEnum ASIAN = new RaceEnum("Asian", 2);

	public static final RaceEnum MONGOLIAN = new RaceEnum("Mongolian", 3);

	public static final RaceEnum MALAY_ARCHIPELAGO = new RaceEnum(
			"Malay Archipelago", 4);

	public static final RaceEnum JAPANESE = new RaceEnum("Japanese", 5);

	public static final RaceEnum SOUTH_EAST_ASIAN = new RaceEnum(
			"South East Asian", 6);

	public static final RaceEnum SINO_TIBETAN = new RaceEnum("Sino Tibetan", 7);

	public static final RaceEnum POLYNESIAN = new RaceEnum("Polynesian", 8);

	public static final RaceEnum SUB_SAHARAN_AFRICAN = new RaceEnum(
			"Sub Saharan African", 9);

	public static final RaceEnum CAUCASIAN = new RaceEnum("Caucasian(white)",
			10);

	public static final RaceEnum EAST_INDIAN = new RaceEnum("East Indian", 11);

	public static final RaceEnum HISPANIC_LATINO = new RaceEnum(
			"Hispanic/Latino", 12);

	public static final RaceEnum MIDDLE_EASTERN = new RaceEnum(
			"Middle Eastern", 13);

	public static final RaceEnum NATIVE_AMERICAN = new RaceEnum(
			"Native American", 14);

	public static final RaceEnum PACIFIC_ISLANDER = new RaceEnum(
			"Pacific Islander", 15);

	public static final RaceEnum MULTI_RACIAL = new RaceEnum("Multi Racial", 16);

	public static final RaceEnum INDIAN_SUB_CONTINENT = new RaceEnum(
			"Indian Sub Continent", 17);

	public static final RaceEnum ARABIAN = new RaceEnum("Arabian", 18);

	public static final RaceEnum NORTH_AFRICAN = new RaceEnum("North African",
			19);

	public static final RaceEnum MEDITERRANEAN = new RaceEnum("Mediterranean",
			20);

	//	Add new instances above this line
	public static RaceEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			RaceEnum status = (RaceEnum) iter.next();
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
	private RaceEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static RaceEnum get(int key) {
		return (RaceEnum) getEnum(RaceEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static RaceEnum getElseReturn(int key, RaceEnum elseEnum) {
		return (RaceEnum) getElseReturnEnum(RaceEnum.class, key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(RaceEnum.class);
	}
}