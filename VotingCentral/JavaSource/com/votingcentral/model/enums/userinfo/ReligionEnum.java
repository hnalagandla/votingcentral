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
public class ReligionEnum extends BaseEnum {
	public static final ReligionEnum AGNOSTIC = new ReligionEnum("Agnostic", 1);

	public static final ReligionEnum ATHIEST = new ReligionEnum("Atheist", 2);

	public static final ReligionEnum BAHAI = new ReligionEnum("Baha'i", 3);

	public static final ReligionEnum BUDDHIST = new ReligionEnum("Buddhist", 4);

	public static final ReligionEnum CAO_DAI = new ReligionEnum("Cao Dai", 5);

	public static final ReligionEnum CHRISTIAN_ANGLICAN = new ReligionEnum(
			"Christian/Anglican", 6);

	public static final ReligionEnum CHRISTIAN_CATHOLIC = new ReligionEnum(
			"Christian/Catholic", 7);

	public static final ReligionEnum CHRISTIAN_LDS = new ReligionEnum(
			"Christian/LDS", 8);

	public static final ReligionEnum CHRISTIAN_ORTHODOX = new ReligionEnum(
			"Christian/Orthodox", 9);

	public static final ReligionEnum CHRISTIAN_OTHER = new ReligionEnum(
			"Christian/Other", 10);

	public static final ReligionEnum CHRISTIAN_PROTESTANT = new ReligionEnum(
			"Christian/Protestant", 11);

	public static final ReligionEnum HINDU = new ReligionEnum("Hindu", 12);

	public static final ReligionEnum JAIN = new ReligionEnum("Jain", 13);

	public static final ReligionEnum JEWISH = new ReligionEnum("Jewish", 14);

	public static final ReligionEnum MUSLIM = new ReligionEnum("Muslim", 15);

	public static final ReligionEnum NEO_PAGANIST = new ReligionEnum(
			"Neo Paganist", 16);

	public static final ReligionEnum RASTAFARIAN = new ReligionEnum(
			"Rastafarian", 17);

	public static final ReligionEnum RELIGIOUS_HUMANISM = new ReligionEnum(
			"Religious Humanism", 18);

	public static final ReligionEnum SCIENTOLOGIST = new ReligionEnum(
			"Scientologist", 19);

	public static final ReligionEnum SHINTO = new ReligionEnum("Shinto", 20);

	public static final ReligionEnum SIKH = new ReligionEnum("Sikh", 21);

	public static final ReligionEnum SPIRITUAL_NOT_RELIGIOUS = new ReligionEnum(
			"Spiritual But Not Religious", 22);

	public static final ReligionEnum TAOIST = new ReligionEnum("Taoist", 23);

	public static final ReligionEnum TENRIKYO = new ReligionEnum("Tenrikyo", 24);

	public static final ReligionEnum UNITARIAN_UNIVERSALIST = new ReligionEnum(
			"Unitarian Universalist", 25);

	public static final ReligionEnum ZOROASTRIAN = new ReligionEnum(
			"Zoroastrian", 26);

	public static final ReligionEnum OTHER = new ReligionEnum("Other", 27);

	//	Add new instances above this line
	public static ReligionEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			ReligionEnum status = (ReligionEnum) iter.next();
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
	private ReligionEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static ReligionEnum get(int key) {
		return (ReligionEnum) getEnum(ReligionEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static ReligionEnum getElseReturn(int key, ReligionEnum elseEnum) {
		return (ReligionEnum) getElseReturnEnum(ReligionEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(ReligionEnum.class);
	}

}