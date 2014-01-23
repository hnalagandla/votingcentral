/*
 * Created on Aug 23, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import sun.misc.BASE64Encoder;

/**
 * @author harishn
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PasswordService {

	private static PasswordService ps = null;

	private static final char[] NUMBERS_AND_LETTERS_ALPHABET = { 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c',
			'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9', };

	private PasswordService() {

	}

	public static synchronized PasswordService getInstance() {
		if (ps == null) {
			ps = new PasswordService();
		}
		return ps;
	}

	//always returns a 24 char encrypted element.
	public synchronized String encryptApache(String plaintext) throws Exception {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
		try {
			md.update(plaintext.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw e;
		}
		byte raw[] = md.digest();
		String hash = (new BASE64Encoder()).encode(raw);
		return hash;
	}

	public synchronized String encrypt(String plaintext) throws Exception {
		String hash = org.apache.catalina.realm.RealmBase.Digest(plaintext,
				"MD5");
		return hash;
	}

	public synchronized String generateRandomPassword(int pswdSize) {
		SecureRandom sr = new SecureRandom();
		StringBuffer sb = new StringBuffer(pswdSize);
		for (int i = 0; i < pswdSize; i++) {
			int nextInt = sr.nextInt(NUMBERS_AND_LETTERS_ALPHABET.length);
			sb.append(NUMBERS_AND_LETTERS_ALPHABET[nextInt]);
		}
		return sb.toString().trim();
	}

	public static void main(String[] args) {
		PasswordService ps = PasswordService.getInstance();
		String pswd = "testing123";
		try {
			String encPass = ps.encrypt(pswd);
			System.out.println("Pass is :" + pswd + ", pswd length is: "
					+ pswd.length() + "  Enc pass is:" + encPass
					+ ", enc pass length is:" + encPass.length());
			System.out.println("Rand Pswd1 :" + ps.generateRandomPassword(8));
			System.out.println("Rand Pswd2 :" + ps.generateRandomPassword(8));
			System.out.println("Rand Pswd3 :" + ps.generateRandomPassword(8));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}