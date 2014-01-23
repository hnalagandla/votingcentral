/*
 * Created on Apr 25, 2007
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
public class VCDownloadFileTypeEnum extends BaseEnum {

	public static final VCDownloadFileTypeEnum EXCEL = new VCDownloadFileTypeEnum(
			"excel", 1);

	public static final VCDownloadFileTypeEnum CSV = new VCDownloadFileTypeEnum(
			"csv", 2);

	public static final VCDownloadFileTypeEnum TEXT = new VCDownloadFileTypeEnum(
			"txt", 3);

	public static final VCDownloadFileTypeEnum DEFAULT = TEXT;

	//	Add new instances above this line
	public static VCDownloadFileTypeEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			VCDownloadFileTypeEnum status = (VCDownloadFileTypeEnum) iter
					.next();
			if (status.getName().equalsIgnoreCase(name)) {
				return status;
			}
		}
		return VCDownloadFileTypeEnum.DEFAULT;
	}

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private VCDownloadFileTypeEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static VCDownloadFileTypeEnum get(int key) {
		VCDownloadFileTypeEnum dftype = (VCDownloadFileTypeEnum) getEnum(
				VCDownloadFileTypeEnum.class, key);
		if (dftype == null) {
			return VCDownloadFileTypeEnum.DEFAULT;
		} else {
			return dftype;
		}
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static VCDownloadFileTypeEnum getElseReturn(int key,
			VCDownloadFileTypeEnum elseEnum) {
		return (VCDownloadFileTypeEnum) getElseReturnEnum(
				VCDownloadFileTypeEnum.class, key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(VCDownloadFileTypeEnum.class);
	}

}