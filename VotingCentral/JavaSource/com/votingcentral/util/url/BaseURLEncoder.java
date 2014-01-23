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
public abstract class BaseURLEncoder {
	protected String[] tokens = new String[256];

	protected int[] cTokens = new int[256];

	protected BaseURLEncoder() {
		initArrays();
	}

	// Must be implemented by the child to initialize the encoding transform
	// arrays.
	protected abstract void initArrays();

	/**
	 * Convenience method for API compatibility with java.net.URLEncoder. The
	 * form of the method taking UnsynchronizedStringBuffer as an argument is
	 * much faster.
	 */
	protected String _encode(String str) {
		return _encode(str, null);
	}

	/**
	 * Convenience method for API compatibility with java.net.URLEncoder. The
	 * form of the method taking UnsynchronizedStringBuffer as an argument is
	 * much faster.
	 */
	protected String _encode(String str, String encoding) {
		if (str == null) {
			return null;
		}

		UnSyncStringBuffer rb = new UnSyncStringBuffer(
				(int) ((str.length() + 1) * 2));
		_encode(rb, str, encoding);

		return rb.toString();
	}

	/**
	 * Encode a string into x-www-form-urlencoded format.
	 */
	protected void _encode(UnSyncStringBuffer rb, String str) {
		_encode(rb, str, null);
	}

	protected void _encode(UnSyncStringBuffer sb, String str, String encoding) {
		if (str == null) {
			return;
		}

		try {
			byte[] bytes;

			if (encoding != null) {
				bytes = str.getBytes(encoding);
			} else {
				bytes = str.getBytes();
			}

			int length = bytes.length;
			char[] word = new char[64];
			int pWord = 0;

			for (int i = 0; i < length; i++) {
				if (cTokens[0xff & bytes[i]] == -1) {
					if (pWord > 0) {
						sb.append(word, 0, pWord);
						pWord = 0;
					}

					sb.append(tokens[0xff & bytes[i]]);
				} else {
					word[pWord++] = (char) cTokens[0xff & bytes[i]];

					if (pWord > 63) {
						sb.append(word, 0, 64);
						pWord = 0;
					}
				}
			}

			if (pWord > 0) {
				sb.append(word, 0, pWord);
				pWord = 0;
			}
		} catch (java.io.UnsupportedEncodingException e) {
		} // TODO - NOPMD - EmptyCatchBlock - This line created by PMD fixer.
	}

	// SOME CODE DUPLICATION HERE.
	// IT APPEARS NECESSARY AS ROPEBUFFER AND UNSYNCHRONIZEDSTRINGBUFFER
	// DO NOT HAVE A COMMON INTERFACE

	/**
	 * Encode a string into x-www-form-urlencoded format.
	 */
	protected void _encode(RopeBuffer rb, String str) {
		_encode(rb, str, null);
	}

	protected void _encode(RopeBuffer sb, String str, String encoding) {
		if (str == null) {
			return;
		}

		try {
			byte[] bytes;

			if (encoding != null) {
				bytes = str.getBytes(encoding);
			} else {
				bytes = str.getBytes();
			}

			int length = bytes.length;
			char[] word = new char[64];
			int pWord = 0;

			for (int i = 0; i < length; i++) {
				if (cTokens[0xff & bytes[i]] == -1) {
					if (pWord > 0) {
						sb.append(word, 0, pWord);
						pWord = 0;
					}

					sb.append(tokens[0xff & bytes[i]]);
				} else {
					word[pWord++] = (char) cTokens[0xff & bytes[i]];

					if (pWord > 63) {
						sb.append(word, 0, 64);
						pWord = 0;
					}
				}
			}

			if (pWord > 0) {
				sb.append(word, 0, pWord);
				pWord = 0;
			}
		} catch (java.io.UnsupportedEncodingException e) {
		} // TODO - NOPMD - EmptyCatchBlock - This line created by PMD fixer.
	}

}