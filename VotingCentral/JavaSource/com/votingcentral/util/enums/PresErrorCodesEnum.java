/*
 * Created on Dec 15, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.enums;

import java.util.ListIterator;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PresErrorCodesEnum extends BaseEnum {
	public static final PresErrorCodesEnum USER_CONFIRM_INVALID_USERNAME = new PresErrorCodesEnum(
			"reg.newuser.invalid.login.name", 10001);

	public static final PresErrorCodesEnum USER_CONFIRM_INVALID_INFO = new PresErrorCodesEnum(
			"reg.newuser.invalid.info", 10002);

	public static final PresErrorCodesEnum USER_CONFIRM_INVALID_CONF_CODE = new PresErrorCodesEnum(
			"reg.newuser.invalid.confirmation.code", 10003);

	public static final PresErrorCodesEnum USER_CONFIRM_RE_REQUEST_CONF_CODE = new PresErrorCodesEnum(
			"reg.newuser.re.request.confirmation.code.email", 10004);

	public static final PresErrorCodesEnum USER_CONFIRM_RE_REQUEST_CONF_CODE_SENT = new PresErrorCodesEnum(
			"reg.newuser.re.request.confirmation.code.email.sent", 10005);

	public static final PresErrorCodesEnum USER_ALREADY_VOTED_IN_POLL = new PresErrorCodesEnum(
			"show.poll.results.already.voted", 11000);

	public static final PresErrorCodesEnum USER_NOT_VOTED_IN_POLL = new PresErrorCodesEnum(
			"show.poll.results.not.voted", 11005);

	public static final PresErrorCodesEnum COMMENT_NOT_VALID_IN_POLL = new PresErrorCodesEnum(
			"show.poll.comments.not.valid", 11010);

	public static final PresErrorCodesEnum CREATE_A_POLL_NO_POLLS = new PresErrorCodesEnum(
			"create.a.poll.no.polls", 12000);

	public static final PresErrorCodesEnum MY_VC_MANAGE_PROFILE_UPDATE_SUCCESS = new PresErrorCodesEnum(
			"my.vc.manage.profile.update.success", 13000);

	public static final PresErrorCodesEnum MY_VC_MANAGE_PREFS_UPDATE_SUCCESS = new PresErrorCodesEnum(
			"my.vc.manage.prefs.update.success", 13001);

	public static final PresErrorCodesEnum MY_VC_MANAGE_PASSWORD_UPDATE_SUCCESS = new PresErrorCodesEnum(
			"my.vc.manage.password.update.success", 14000);

	public static final PresErrorCodesEnum SHOW_POLL_EXTENDED = new PresErrorCodesEnum(
			"show.poll.extended", 15000);

	public static final PresErrorCodesEnum LOGIN_INVALID_LOGIN = new PresErrorCodesEnum(
			"user.login.error.invalid.pswd", 16000);

	//	-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace //
	// enumeration class name with the name of this class. //
	//-----------------------------------------------------------------//
	private PresErrorCodesEnum(String name, int intValue) {
		super(intValue, name);
	}

	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static PresErrorCodesEnum get(int key) {
		return (PresErrorCodesEnum) getEnum(PresErrorCodesEnum.class, key);
	}

	/**
	 * Get the enumeration instance for a given value or return the elseEnum
	 * default.
	 */
	public static PresErrorCodesEnum getElseReturn(int key,
			PresErrorCodesEnum elseEnum) {
		return (PresErrorCodesEnum) getElseReturnEnum(PresErrorCodesEnum.class,
				key, elseEnum);
	}

	/**
	 * Return an bidirectional iterator that traverses the enumeration instances
	 * in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(PresErrorCodesEnum.class);
	}
}