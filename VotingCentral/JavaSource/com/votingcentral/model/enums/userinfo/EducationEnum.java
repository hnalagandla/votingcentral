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
public class EducationEnum extends BaseEnum {
	public static final EducationEnum NO_HS = new EducationEnum(
			"No High School", 1);

	public static final EducationEnum HS = new EducationEnum(
			"High School Graduate", 2);

	public static final EducationEnum SOME_COLLEGE = new EducationEnum(
			"Some College", 3);

	public static final EducationEnum COLLEGE_GRAD = new EducationEnum(
			"College Graduate", 4);

	public static final EducationEnum POST_GRAD = new EducationEnum(
			"Post Graduate", 5);

	//	Add new instances above this line
	public static EducationEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			EducationEnum status = (EducationEnum) iter.next();
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
	private EducationEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static EducationEnum get(int key) {
		return (EducationEnum) getEnum(EducationEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static EducationEnum getElseReturn(int key, EducationEnum elseEnum) {
		return (EducationEnum) getElseReturnEnum(EducationEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(EducationEnum.class);
	}

}