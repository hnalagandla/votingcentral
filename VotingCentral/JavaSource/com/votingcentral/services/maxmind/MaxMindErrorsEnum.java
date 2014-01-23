/*
 * Created on Jan 31, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.services.maxmind;

import java.util.Iterator;
import java.util.ListIterator;

import com.votingcentral.errors.ErrorTypeEnum;
import com.votingcentral.util.enums.BaseEnum;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MaxMindErrorsEnum extends BaseEnum {

	private ErrorTypeEnum errorType;

	public static final MaxMindErrorsEnum VC_INTERNAL_ERROR = new MaxMindErrorsEnum(
			"VC_INTERNAL_ERROR", ErrorTypeEnum.FATAL, -9999);
	
	public static final MaxMindErrorsEnum IP_NOT_FOUND = new MaxMindErrorsEnum(
			"IP_NOT_FOUND", ErrorTypeEnum.WARNING, 1);

	public static final MaxMindErrorsEnum COUNTRY_NOT_FOUND = new MaxMindErrorsEnum(
			"COUNTRY_NOT_FOUND", ErrorTypeEnum.WARNING, 2);

	public static final MaxMindErrorsEnum CITY_NOT_FOUND = new MaxMindErrorsEnum(
			"CITY_NOT_FOUND", ErrorTypeEnum.WARNING, 3);

	public static final MaxMindErrorsEnum CITY_REQUIRED = new MaxMindErrorsEnum(
			"CITY_REQUIRED", ErrorTypeEnum.WARNING, 4);

	public static final MaxMindErrorsEnum POSTAL_CODE_REQUIRED = new MaxMindErrorsEnum(
			"POSTAL_CODE_REQUIRED", ErrorTypeEnum.WARNING, 5);

	public static final MaxMindErrorsEnum POSTAL_CODE_NOT_FOUND = new MaxMindErrorsEnum(
			"POSTAL_CODE_NOT_FOUND", ErrorTypeEnum.WARNING, 6);

	public static final MaxMindErrorsEnum INVALID_LICENSE_KEY = new MaxMindErrorsEnum(
			"INVALID_LICENSE_KEY", ErrorTypeEnum.FATAL, 7);

	public static final MaxMindErrorsEnum MAX_REQUESTS_PER_LICENSE = new MaxMindErrorsEnum(
			"MAX_REQUESTS_PER_LICENSE", ErrorTypeEnum.FATAL, 8);

	public static final MaxMindErrorsEnum IP_REQUIRED = new MaxMindErrorsEnum(
			"IP_REQUIRED", ErrorTypeEnum.FATAL, 9);

	public static final MaxMindErrorsEnum LICENSE_REQUIRED = new MaxMindErrorsEnum(
			"LICENSE_REQUIRED", ErrorTypeEnum.FATAL, 10);

	public static final MaxMindErrorsEnum MAX_REQUESTS_REACHED = new MaxMindErrorsEnum(
			"MAX_REQUESTS_REACHED", ErrorTypeEnum.FATAL, 11);

	public static MaxMindErrorsEnum get(String name) {
		if (name == null || name.length() == 0) {
			return null;
		}
		Iterator iter = iterator();
		while (iter.hasNext()) {
			MaxMindErrorsEnum type = (MaxMindErrorsEnum) iter.next();
			if (type.getName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return null;
	}

	//	Add new instances above this line

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private MaxMindErrorsEnum(String name, ErrorTypeEnum errorType, int intValue) {
		super(intValue, name);
		this.errorType = errorType;
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static MaxMindErrorsEnum get(int key) {
		return (MaxMindErrorsEnum) getEnum(MaxMindErrorsEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static MaxMindErrorsEnum getElseReturn(int key,
			MaxMindErrorsEnum elseEnum) {
		return (MaxMindErrorsEnum) getElseReturnEnum(MaxMindErrorsEnum.class,
				key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(MaxMindErrorsEnum.class);
	}
}