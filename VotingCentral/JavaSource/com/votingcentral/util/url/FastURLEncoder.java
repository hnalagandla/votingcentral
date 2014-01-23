/*
 * Created on Aug 12, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.url;

import com.votingcentral.util.RopeBuffer;
import com.votingcentral.util.UnSyncStringBuffer;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class FastURLEncoder extends BaseURLEncoder {
	//Internally the implementatoin is a singleton
	private static FastURLEncoder instance = new FastURLEncoder();

	private FastURLEncoder() {
		super();
	}

	public static FastURLEncoder getInstance() {
		return instance;
	}

	protected void initArrays() {

		for (int i = -128; i < 128; i++) {
			char c = (char) i;
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
					|| (c >= '0' && c <= '9') || c == '.' || c == '-'
					|| c == '_' || c == '*') {

				cTokens[0xff & i] = (int) c;
			} else if (c == ' ') {
				cTokens[0xff & i] = (int) '+';
			} else {
				cTokens[0xff & i] = -1;

				String encodedStr = Integer.toHexString(c);
				int size = encodedStr.length();

				if (size >= 2) {

					encodedStr = encodedStr.substring(size - 2, size);
					tokens[0xff & i] = "%" + encodedStr.toUpperCase();

				} else {
					tokens[0xff & i] = "%0" + encodedStr.toUpperCase();
				}
			}
		}
	}

	// Public interface - compatible with JDK1.4.1 api and augmented
	// with Kernel String types

	/**
	 * Convenience method for API compatibility with java.net.URLEncoder. The
	 * form of the method taking UnsynchronizedStringBuffer as an argument is
	 * much faster.
	 */
	public static String encode(String str) {
		return getInstance()._encode(str);

	}

	public static String encode(String str, String encoding) {
		return getInstance()._encode(str, encoding);

	}

	/**
	 * Encode a string into x-www-form-urlencoded format.
	 */
	public static void encode(UnSyncStringBuffer rb, String str) {
		getInstance()._encode(rb, str, null);
	}

	public static void encode(UnSyncStringBuffer sb, String str, String encoding) {
		getInstance()._encode(sb, str, encoding);
	}

	// SOME CODE DUPLICATION HERE.
	// IT APPEARS NECESSARY AS ROPEBUFFER AND UNSYNCHRONIZEDSTRINGBUFFER
	// DO NOT HAVE A COMMON INTERFACE

	/**
	 * Encode a string into x-www-form-urlencoded format.
	 */
	public static void encode(RopeBuffer rb, String str) {
		getInstance()._encode(rb, str, null);
	}

	public static void encode(RopeBuffer rb, String str, String encoding) {
		getInstance()._encode(rb, str, encoding);
	}

}