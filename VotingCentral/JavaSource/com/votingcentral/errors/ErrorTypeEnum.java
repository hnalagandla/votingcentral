/*
 * Created on Apr 25, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.errors;

import java.util.ListIterator;

import com.votingcentral.util.enums.BaseEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ErrorTypeEnum extends BaseEnum {
	public static final ErrorTypeEnum WARNING = new ErrorTypeEnum("WARNING", 0);

	public static final ErrorTypeEnum FATAL = new ErrorTypeEnum("FATAL", 1);

	//	Add new instances above this line

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private ErrorTypeEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static ErrorTypeEnum get(int key) {
		return (ErrorTypeEnum) getEnum(ErrorTypeEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static ErrorTypeEnum getElseReturn(int key, ErrorTypeEnum elseEnum) {
		return (ErrorTypeEnum) getElseReturnEnum(ErrorTypeEnum.class, key,
				elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(ErrorTypeEnum.class);
	}

}