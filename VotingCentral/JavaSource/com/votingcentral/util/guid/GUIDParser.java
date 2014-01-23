/*
 * Created on Aug 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.guid;

import java.math.BigInteger;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * @author harishn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class GUIDParser {
	/**
	 * Guids can either be in an old, legacy format that uses 32 bitss
	 * of timestamp data, or a newer form that uses 44 bits of timestamp
	 * data.  The difference is detected by whether or not the sequence
	 * number is negative.
	 * @param paddedGUID
	 * @return true if the guid is in the new format
	 */
	private static boolean isNewFormat(String guid) {
		if (guid == null || guid.length()<8) {
			// guid has to be non null and a minimum length
			return false; 
		}
		int seqStart = 0;
		// the non-padded buid format will have a period as separator
		int period = guid.lastIndexOf(GUID.NON_PADDED_DELIMETER);
		if (period >= 0) {
			// non-padded format.  Pick off the sequence number and
			// test for less than zero
			seqStart = period + 1;
			if (seqStart >= guid.length()) {
				return false; // nothing to get here, assume old format
			}
		} else {
			// ISO or padded format.  In this case, we know that the 
			// sequence number comes from the last eight characters of
			// the guid.  Assume old format if guid is less than 8
			// characters long
			seqStart = guid.length()-8;
		}
		int seq = 0;
		try {
			seq = parseHexInt(guid.substring(seqStart));
		} catch (Exception e) {
			// ignore errors and treat as old format
			return false;
		}
		// got the seq, if less than zero, we're in new format
		return (seq < 0);
	}

	/**
	 * @return GuidData, given a padded GUID
	 */
	public static GUIDData parsePaddedGUID(String paddedGUID){
		if (isNewFormat(paddedGUID)) {
			return parseNewFormatPaddedGUID(paddedGUID);
		} else {
			return parseOldFormatPaddedGUID(paddedGUID);
		}
	}
	/**
	 * @return GuidData, given a padded GUID in the old format
	 */
	private static GUIDData parseOldFormatPaddedGUID(String paddedGUID){
		if(paddedGUID == null){
			throw new IllegalArgumentException("Padded GUID cannot be null.");
		}
		if(paddedGUID.length()!=32){
			throw new IllegalArgumentException("Padded GUID must be of length 32.");
		}
		
		long millis = Long.parseLong("f9"+paddedGUID.substring(0,8),16);
		BigInteger ipBigInteger = new BigInteger(paddedGUID.substring(8,16), 16);
		byte[] ipBytes = ipBigInteger.toByteArray();
		int hashInt = parseHexInt(paddedGUID.substring(16,24));
		int counterInt = parseHexInt(paddedGUID.substring(24,32));
		return new GUIDData(new Date(millis), ipBytes, hashInt, counterInt);
	}
	/**
	 * @return GuidData, given a padded GUID in the new format
	 */
	private static GUIDData parseNewFormatPaddedGUID(String paddedGUID){
		if(paddedGUID == null){
			throw new IllegalArgumentException("Padded GUID cannot be null.");
		}
		if(paddedGUID.length()!=32){
			throw new IllegalArgumentException("Padded GUID must be of length 32.");
		}
		String milliStr = paddedGUID.substring(8,11) + paddedGUID.substring(0,8);
		long millis = Long.parseLong(milliStr,16);
		BigInteger ipBigInteger = new BigInteger(paddedGUID.substring(11,19), 16);
		byte[] ipBytes = ipBigInteger.toByteArray();
		int hashInt = parseHexInt(paddedGUID.substring(19,24));
		int counterInt = parseHexInt(paddedGUID.substring(24,32));
		return new GUIDData(new Date(millis), ipBytes, hashInt, counterInt);
	}

	/**
	 * @return GuidData, given a non-padded GUID
	 */
	public static GUIDData parseNonPaddedGUID(String nonPaddedGUID){
		if (isNewFormat(nonPaddedGUID)) {
			return parseNewFormatNonPaddedGUID(nonPaddedGUID);
		} else {
			return parseOldFormatNonPaddedGUID(nonPaddedGUID);
		}
	}
	/**
	 * @return GuidData, given a non-padded GUID in the old format
	 */
	private static GUIDData parseOldFormatNonPaddedGUID(String nonPaddedGUID){
		if(nonPaddedGUID == null){
			throw new IllegalArgumentException("Non-Padded GUID cannot be null.");
		}
		StringTokenizer tokenizer = new StringTokenizer(nonPaddedGUID, GUID.NON_PADDED_DELIMETER);
		String millisHex = "f9"+tokenizer.nextToken();
		long millis = Long.parseLong(millisHex, 16);
		BigInteger ipBigInteger = new BigInteger(tokenizer.nextToken(), 16);
		byte[] ipBytes = ipBigInteger.toByteArray();
		int hashInt = parseHexInt(tokenizer.nextToken());
		int counterInt = parseHexInt(tokenizer.nextToken());
		return new GUIDData(new Date(millis), ipBytes, hashInt, counterInt);
	}
	/**
	 * @return GuidData, given a non-padded GUID in the new format
	 */
	private static GUIDData parseNewFormatNonPaddedGUID(String nonPaddedGUID){
		if(nonPaddedGUID == null){
			throw new IllegalArgumentException("Non-Padded GUID cannot be null.");
		}
		StringTokenizer tokenizer = new StringTokenizer(nonPaddedGUID, GUID.NON_PADDED_DELIMETER);
		String millisHex = tokenizer.nextToken();
		long millis = Long.parseLong(millisHex, 16);
		BigInteger ipBigInteger = new BigInteger(tokenizer.nextToken(), 16);
		byte[] ipBytes = ipBigInteger.toByteArray();
		int hashInt = parseHexInt(tokenizer.nextToken());
		int counterInt = parseHexInt(tokenizer.nextToken());
		return new GUIDData(new Date(millis), ipBytes, hashInt, counterInt);
	}

	/**
	 * Parse a GUID using the 36-digit iso standard form...
 	 */
	public static GUIDData parseIsoGUID(String isoGUID){
		if (isNewFormat(isoGUID)) {
			return parseNewIsoGUID(isoGUID);
		} else {
			return parseOldIsoGUID(isoGUID);
		}
	}
	/**
	 * Parse a GUID using the 36-digit iso standard form... old format
  	 * format:
	 * <pre> 
	 * 	xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
	 * </pre>
	 * 
	 * <ul>
	 *   <li>First 8 (hex): the low 32 bits of System.currentTimeMillis() represents uniqueness
	 * 		in time
	 * 	 <li>Next 8 (hex): We cannot generate a IEEE 802 address to represent uniqueness in space (in a cluster)
	 * 		without using native code, so the IP address of localhost is used instead.
	 *   <li>Next 8 (hex): System.identityHashCode is used in order to avoid conflicts with multiple objects
	 *   <li>Next 8 (hex): an internal counter is used to avoid several GUIDs being generated within the
	 * 		same millisecond or a clock reset... 
	 * </ul>
	 */
	private static GUIDData parseOldIsoGUID(String isoGUID){
		if(isoGUID == null){
			throw new IllegalArgumentException("ISO GUID cannot be null.");
		}
		if(isoGUID.length()!=36){
			throw new IllegalArgumentException("ISO GUID must be of length 36.");
		}
		
		long millis = Long.parseLong("f9"+isoGUID.substring(0,8),16);
		String ipString = isoGUID.substring(9,13) + isoGUID.substring(14,18);
		BigInteger ipBigInteger = new BigInteger(ipString, 16);
		byte[] ipBytes = ipBigInteger.toByteArray();
		String hashString = isoGUID.substring(19,23) + isoGUID.substring(24,28);
		int hashInt = parseHexInt(hashString);
		int counterInt = parseHexInt(isoGUID.substring(28,36));
		return new GUIDData(new Date(millis), ipBytes, hashInt, counterInt);
	}
	/**
	 * Parse a GUID using the 36-digit iso standard form... new format
  	 * format:
	 * <pre> 
	 *  0         1         2         3 
	 *  012345678901234567890123456789012345
	 * 	xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
	 * </pre>
	 * 
	 * <ul>
	 *   <li>First 11 (hex): the low 44 bits of System.currentTimeMillis() represents uniqueness
	 * 		in time
	 * 	 <li>Next 8 (hex): We cannot generate a IEEE 802 address to represent uniqueness in space (in a cluster)
	 * 		without using native code, so the IP address of localhost is used instead.
	 *   <li>Next 5 (hex): System.identityHashCode is used in order to avoid conflicts with multiple objects
	 *   <li>Next 8 (hex): an internal counter is used to avoid several GUIDs being generated within the
	 * 		same millisecond or a clock reset... 
	 * </ul>
	 */
	private static GUIDData parseNewIsoGUID(String isoGUID){
		if(isoGUID == null){
			throw new IllegalArgumentException("ISO GUID cannot be null.");
		}
		if(isoGUID.length()!=36){
			throw new IllegalArgumentException("ISO GUID must be of length 36.");
		}
		String milliStr = isoGUID.substring(0,8) + isoGUID.substring(9,12); 
		long millis = Long.parseLong(milliStr,16);
		String ipString = isoGUID.substring(12,13) + isoGUID.substring(14,18) +
			isoGUID.substring(19,22);
		BigInteger ipBigInteger = new BigInteger(ipString, 16);
		byte[] ipBytes = ipBigInteger.toByteArray();
		String hashString = isoGUID.substring(22,23) + isoGUID.substring(24,28);
		int hashInt = parseHexInt(hashString);
		int counterInt = parseHexInt(isoGUID.substring(28,36));
		return new GUIDData(new Date(millis), ipBytes, hashInt, counterInt);
	}
	
	/**
	 * Parse an integer from hex format.  Use this method instead of
	 * Integer.parseInt(string,16) because the Integer.parseInt method
	 * will throw NumberFormat exceptions for small negative numbers like
	 * "ffffffff" (-1)
	 * @param s an integer in hex notation, two's complement, 32-bits max
	 * @return the 32-bit integer representation
	 */
	private static int parseHexInt(String s) {		
		BigInteger bigInt = new BigInteger(s,16);
		return bigInt.intValue();
	}
}
