/*
 * Created on Jan 31, 2006
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
public class AnswerTypeEnum extends BaseEnum {

	public static final AnswerTypeEnum INVALID = new AnswerTypeEnum(
			"INVALID", 0);

	public static final AnswerTypeEnum RADIO = new AnswerTypeEnum("RADIO",
			1);

	public static final AnswerTypeEnum CHECKBOX = new AnswerTypeEnum(
			"CHECKBOX", 2);

	public static final AnswerTypeEnum TEXTAREA = new AnswerTypeEnum(
			"TEXTAREA", 3);

	public static AnswerTypeEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			AnswerTypeEnum type = (AnswerTypeEnum) iter.next();
			if (type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return INVALID;
	}

	//	Add new instances above this line

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private AnswerTypeEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static AnswerTypeEnum get(int key) {
		return (AnswerTypeEnum) getEnum(AnswerTypeEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static AnswerTypeEnum getElseReturn(int key,
			AnswerTypeEnum elseEnum) {
		return (AnswerTypeEnum) getElseReturnEnum(AnswerTypeEnum.class,
				key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(AnswerTypeEnum.class);
	}
}