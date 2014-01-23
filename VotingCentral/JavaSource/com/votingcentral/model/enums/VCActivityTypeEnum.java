/*
 * Created on Apr 13, 2006
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
public class VCActivityTypeEnum extends BaseEnum {
	public static final VCActivityTypeEnum INVALID_ACTIVITY = new VCActivityTypeEnum(
			"INVALID_ACTIVITY", 1);

	//these descriptions must match that entries in Env.properties file
	public static VCActivityTypeEnum VC_POINTS_REGISTER = new VCActivityTypeEnum(
			"vc.points.register", 2);

	public static VCActivityTypeEnum VC_POINTS_VOTING = new VCActivityTypeEnum(
			"vc.points.voting", 3);

	public static VCActivityTypeEnum VC_POINTS_CREATE_POLL = new VCActivityTypeEnum(
			"vc.points.create.poll", 4);

	public static VCActivityTypeEnum VC_POINTS_COMMENTS = new VCActivityTypeEnum(
			"vc.points.comment", 5);

	public static VCActivityTypeEnum VC_POINTS_UPLOAD_PICTURE = new VCActivityTypeEnum(
			"vc.points.upload.picture", 6);

	public static VCActivityTypeEnum VC_POINTS_TELL_A_FRIEND = new VCActivityTypeEnum(
			"vc.points.tell.a.friend", 7);

	public static VCActivityTypeEnum VC_POINTS_VC_CONNECT = new VCActivityTypeEnum(
			"vc.points.vc.connect", 8);

	public static VCActivityTypeEnum VC_POINTS_CONNECT_ACCEPT_REJECT = new VCActivityTypeEnum(
			"vc.points.connect.accept.reject", 9);

	public static VCActivityTypeEnum get(String name) {
		Iterator iter = iterator();
		while (iter.hasNext()) {
			VCActivityTypeEnum roles = (VCActivityTypeEnum) iter.next();
			if (roles.getName().equalsIgnoreCase(name)) {
				return roles;
			}
		}
		return INVALID_ACTIVITY;
	}

	//	Add new instances above this line

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private VCActivityTypeEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static VCActivityTypeEnum get(int key) {
		return (VCActivityTypeEnum) getEnum(VCActivityTypeEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static VCActivityTypeEnum getElseReturn(int key,
			VCActivityTypeEnum elseEnum) {
		return (VCActivityTypeEnum) getElseReturnEnum(VCActivityTypeEnum.class,
				key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(VCActivityTypeEnum.class);
	}

}