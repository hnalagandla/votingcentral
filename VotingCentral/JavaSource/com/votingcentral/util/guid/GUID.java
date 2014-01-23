/*
 * Created on Aug 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util.guid;

import java.io.IOException;
import java.net.UnknownHostException;

import com.votingcentral.util.RopeBuffer;
import com.votingcentral.util.context.ServerContext;

/**
 * Guid is a Utility class that can be used to generate unique ids among a
 * cluster of machines. Instances of the GUID class should be cached and used as
 * approprate (note that the Constructor calls an init routine that caches
 * static parts of the GUID, so do not create a new instance of this class each
 * time you want to generate a GUID).
 * <p>
 * This GUID represents uniqueness in space and in time, using 4 key components:
 * 
 * <ol>
 * <li>bits from System.currentTimeMillis() represents uniqueness in time
 * <li>We cannot generate a IEEE 802 address to represent uniqueness in space
 * (in a cluster) without using native code, so the IP address of localhost is
 * used instead.
 * <li>System.identityHashCode is used in order to avoid conflicts with
 * multiple Guid instances within the same VM. Therefore, two Guid instances can
 * create unique IDs within the same millisecond because their identityHashCodes
 * will be different
 * <li>an internal counter is used to avoid several GUIDs being generated
 * within the same millisecond or problems resulting from a clock reset...
 * </ol>
 * 
 * <p>
 * Originally, the guids produced by this class used the low-order 32 bits of
 * the timestamp. However, further study showed that this range wrapped every
 * 49.7 days. Obviously, this is not suitable. So, this version of the code uses
 * 12 more bits (for a total of 44 bits) from the timestamp. This value will not
 * wrap for 557.7 years. Furthermore, as of about 4:00pm on 6/21/2005, the
 * current timestamp in hex was
 * 
 * <pre><code>
 * 
 *  		00 00 01 04 a1 2b a7 a6
 *  
 * </code></pre>
 * 
 * The low-order bits are the non-zero hex digits. They will not wrap for
 * another 522.3 years. So, when decoding the time value with this scheme, we
 * can safely assume that the high order bits are zero. [It should be safe to
 * assume that in 500 years, if eBay is still here, we'll be using a different
 * technology for this.]
 * 
 * <p>
 * In order to maintain backward compatibility with the existing guids, we'll
 * use a negative sequence number. Inspection of the sequence numbers used in
 * production confirms that the sequence numbers in use are all positive. We
 * will modify the sequence number management to always have a negative number
 * so that that in combination with the extended timestamp will keep the guids
 * unique.
 * 
 * <p>
 * In order to accomdate the increased timestamp bits, we'll steal 12 bits from
 * the hash value. Assuming that most straighforward JVM implementation of
 * System.identityHashCode would use the virtual memory address of the object,
 * we'll discard the low order 12 bit positions of this value to make room for
 * the additional bits added by the expanded timestamp.
 * 
 * There are three methods, which can be used to generate GUIDs, based on
 * preference:
 * 
 * <ol>
 * <li>nextGUID: produces the shortest and fastest representaiton of a GUID and
 * delimits each of the components mentioned above using a dot "." notation. For
 * example:
 * 
 * <pre>
 * 
 *  			104a665f655.afe2157.62a72.fffb6c20
 *  		
 * </pre>
 * 
 * The disadvantage is that the size of the GUID will change with time.
 * <li>nextPaddedGUID: produces a padded representation, without using the dot
 * "." notation. This GUID is almost as fast as the GUID produced by the
 * nextGUID, but is larger in size (32 digit alphanumeric). The advantage is
 * that the size of the GUID is always the same, where zeroes "0" are used to
 * pad where needed. A large, early user of this GUID format is the Sojourner
 * tracking system. It has a feature that uses bytes 7 and 8 as a hash value,
 * these correspond to the least significant timestamp bits. In order to
 * preserve that property, the timestamp for this form is written wiht the least
 * significant 32 bits first, followed by the most significant 12 bits (out of
 * the 44 bits we use).
 * <li>nextISOGUID: Generates a GUID that resembles the Internet standards
 * draft for the Network Working Group by Paul J. Leach, Microsoft, and Rich
 * Salz, Certco. The specification uses a 36-digit alphanumeric (including
 * hyphens) of the following format:
 * 
 * <pre>
 * xxxxxxxx - xxxx - xxxx - xxxx - xxxxxxxxxxxx
 * </pre>
 * 
 * where, in this implementation:
 * <ul>
 * <li>First 11 Hex Chars: the low 44 bits of System.currentTimeMillis()
 * represents uniqueness in time
 * <li>Next 8 Hex Chars: We cannot generate a IEEE 802 address to represent
 * uniqueness in space (in a cluster) without using native code, so the IP
 * address of localhost is used instead.
 * <li>Next 5 Hex Chars: low 20 bits of System.identityHashCode is used in
 * order to avoid conflicts with multiple objects
 * <li>Next 8 Hex Chars: an internal counter is used to avoid several GUIDs
 * being generated within the same millisecond or a clock reset...
 * </ul>
 * </ol>
 * <p>
 * Note: there is an RMI Uid as well, however, it is not used because it is used
 * for a single machine only and therefore, we'd have to add the IP Address
 * component to it. In addition, the RMI version does not conform to internet
 * standards. Furthermore, we'd have to import RMI specific classes, when we
 * really aren't using RMI...
 * <p>
 */
public final class GUID {

	/**
	 * this is the static part of the GUID used for non-padded GUID generation.
	 * This will not change for the lifetime instance of this object.
	 * 
	 * It consists of the Hex representation of the IP Address concatinated with
	 * the Hex representation of the GUID Object Hash
	 */
	private String m_staticGUID;

	/**
	 * this is the padded version of m_staticGUID that will not change for the
	 * lifetime instance of this object.
	 */
	private String m_paddedStaticGUID;

	/**
	 * this represeents the middle 20 characters (-xxxx-xxxx-xxxx-xxxx) of the
	 * ISO GUID. This will not change for the lifetime instance of this object.
	 */
	private String m_isoPaddedStaticGUID;

	/**
	 * synchronized counter used to differentiate between method calls
	 */
	private int m_counter = 0;

	/**
	 * delimeter used for separating fields in the ISO representation of the
	 * GUID
	 */
	public static final String ISO_DELIMETER = "-";

	/**
	 * delimeter used for separating fields in the non-padded GUID
	 * representation
	 */
	public static final String NON_PADDED_DELIMETER = ".";

	/**
	 * Constructor for this Guid class.
	 * 
	 * Note: constructing an instance of this class will perform an
	 * initialization routine that will take approximately 70ms on a P4 2.66 GHZ
	 * machine. So, you should construct and cache an instance of this GUID
	 * Generator for your use.
	 * 
	 * @exception UnknownHostException
	 *                if no IP address for localhost could be found (should not
	 *                happen).
	 */
	public GUID() throws UnknownHostException {
		init();
	}

	/**
	 * initialize the static parts of the GUID, which are:
	 * 
	 * <ul>
	 * <li>the IP Address
	 * <li>the unique Object Hash
	 * </ul>
	 */
	private void init() throws UnknownHostException {

		/**
		 * we could make the size smaller by:
		 * 
		 * (1) Taking the 2 less significant bytes of the IP address (i.e.
		 * ip[2], ip[3] in byte[] ip = InetAddress.getLocalHost().getAddress();)
		 * 
		 * (2) Taking the 4 less significant bytes of the object address
		 */
		byte[] inetAddrBytes = ServerContext.getAddress();
		int addrInt = ((inetAddrBytes[0] & 0x000000FF) << 24)
				| ((inetAddrBytes[1] & 0x000000FF) << 16)
				| ((inetAddrBytes[2] & 0x000000FF) << 8)
				| (inetAddrBytes[3] & 0x000000FF);
		String inetAddrHexString = Integer.toHexString(addrInt);

		int objAddr = System.identityHashCode(this);
		// discard least significant 12 bits of object hash
		String objAddrHexString = Integer.toHexString(objAddr >>> 12);

		initStaticGuid(inetAddrHexString, objAddrHexString);
		initPaddedStaticGuid(inetAddrHexString, objAddrHexString);
		initISOStaticUUID(m_paddedStaticGUID);
	}

	/**
	 * initialize the small notation of the GUID, where each part is separated
	 * by a ".". These do not change for the lifetime of this object.
	 * 
	 * @param inetAddrHexString
	 *            the inet address in hex format
	 * @param objAddrHexString
	 *            the unique object address in hex format
	 */
	private void initStaticGuid(String inetAddrHexString,
			String objAddrHexString) {
		m_staticGUID = NON_PADDED_DELIMETER + inetAddrHexString
				+ NON_PADDED_DELIMETER + objAddrHexString
				+ NON_PADDED_DELIMETER;
	}

	/**
	 * initialize the padded notation of the GUID. These do not change for the
	 * lifetime of this object. No separaters exist because the size of each
	 * field is well known:
	 * 
	 * <ul>
	 * <li>inet address: 8 characters
	 * <li>object address: 8 characters
	 * </ul>
	 * 
	 * @param inetAddrHexString
	 *            the inet address in hex format
	 * @param objAddrHexString
	 *            the unique object address in hex format
	 */
	private void initPaddedStaticGuid(String inetAddrHexString,
			String objAddrHexString) {
		m_paddedStaticGUID = pad(inetAddrHexString, 8)
				+ pad(objAddrHexString, 5);
	}

	/**
	 * initialize the middle 16 characters (x-xxxx-xxxx-xxxx) of the ISO GUID.
	 * These do not change for the lifetime of this object.
	 * 
	 * The first x-xxxx-xxx sequence represents the IP Address The second x-xxxx
	 * sequence represents the unique Object Hash
	 */
	private void initISOStaticUUID(String paddedGuid) {
		RopeBuffer buff = new RopeBuffer();
		buff.append(paddedGuid.substring(0, 1));
		buff.append(ISO_DELIMETER);
		buff.append(paddedGuid.substring(1, 5));
		buff.append(ISO_DELIMETER);
		buff.append(paddedGuid.substring(5, 9));
		buff.append(ISO_DELIMETER);
		buff.append(paddedGuid.substring(9));
		m_isoPaddedStaticGUID = buff.toString();
	}

	/**
	 * @return the next GUID in hex form, which represents a GUID that is unique
	 *         across a cluster of boxes. The GUID consists of the following 4
	 *         components:
	 * 
	 * <ol>
	 * <li>the low 44 bits of System.currentTimeMillis(); in hex form
	 * <li>the localhost IP Address in hex form
	 * <li>the unique Object Hash in hex form
	 * <li>the unique counter in hex form
	 * </ol>
	 * 
	 * each component is delimeted using dot "." notation, for example:
	 * 
	 * <pre>
	 * 
	 *  	ec618664.afe220a.653ed70.493e0
	 *  
	 * </pre>
	 */
	public String nextGUID() {
		RopeBuffer buff = new RopeBuffer();
		long millis = System.currentTimeMillis();
		//use the low 44 bits
		millis = millis & 0x00000FFFFFFFFFFFL;
		buff.append(pad(Long.toHexString(millis), 11));

		buff.append(m_staticGUID);
		buff.append(Integer.toHexString(nextCounter()));
		return buff.toString();
	}

	/**
	 * @returns the next GUID in hex form (padded where necessary), which
	 *          represents a GUID that is unique across a cluster of boxes...
	 *          <p>
	 *          This does **not** generate a GUID that follows the Internet
	 *          standards draft. (see nextISOGUID for one that does).
	 *          <p>
	 *          The GUID returned will always contain 32 characters, where:
	 * 
	 * <ul>
	 * <li>First 11 ::= the low 44 bits of System.currentTimeMillis(); in hex
	 * form
	 * <li>Next 8 ::= the localhost IP Address in hex form
	 * <li>Next 5 ::= the unique Object Hash in hex form
	 * <li>Next 8 ::= the unique counter in hex form
	 * </ul>
	 * 
	 * Each component listed above is padded with a zero where necessary. Here's
	 * a sample:
	 * 
	 * <pre>
	 * 
	 *  	104ec6186640afe220a3ed70fff493e1
	 *  
	 * </pre>
	 */
	public String nextPaddedGUID() {
		RopeBuffer buff = new RopeBuffer();
		long millis = System.currentTimeMillis();
		//use the low 44 bits
		millis = millis & 0x00000FFFFFFFFFFFL;
		String milliStr = pad(Long.toHexString(millis), 11);
		buff.append(milliStr.substring(3));
		buff.append(milliStr.substring(0, 3));
		buff.append(m_paddedStaticGUID);
		// next counter will always be negative with means it will always be
		// 8 hex bytes and not require padding
		buff.append(Integer.toHexString(nextCounter()));
		return buff.toString();
	}

	/**
	 * Generates a GUID that resembles the Internet standards draft for the
	 * Network Working Group by Paul J. Leach, Microsoft, and Rich Salz, Certco.
	 * <p>
	 * This GUID is not an exact implementation of the specification.
	 * <p>
	 * The specification uses a 36-digit alphanumeric (including hyphens) of the
	 * following format:
	 * 
	 * <pre>
	 * xxxxxxxx - xxxx - xxxx - xxxx - xxxxxxxxxxxx
	 * </pre>
	 * 
	 * where, in this implementation:
	 * <ul>
	 * <li>First 11 (hex): the low 44 bits of System.currentTimeMillis()
	 * represents uniqueness in time
	 * <li>Next 8 (hex): We cannot generate a IEEE 802 address to represent
	 * uniqueness in space (in a cluster) without using native code, so the IP
	 * address of localhost is used instead.
	 * <li>Next 5 (hex): System.identityHashCode is used in order to avoid
	 * conflicts with multiple objects
	 * <li>Next 8 (hex): an internal counter is used to avoid several GUIDs
	 * being generated within the same millisecond or a clock reset...
	 * </ul>
	 */
	public String nextISOGUID() {
		RopeBuffer buff = new RopeBuffer();
		long millis = System.currentTimeMillis();
		//use the low 44 bits
		millis = millis & 0x00000FFFFFFFFFFFL;
		String timeStr = pad(Long.toHexString(millis), 11);
		buff.append(timeStr.substring(0, 8));
		buff.append(ISO_DELIMETER);
		buff.append(timeStr.substring(8));
		buff.append(m_isoPaddedStaticGUID);
		// next counter will always be negative with means it will always be
		// 8 hex bytes and not require padding
		buff.append(Integer.toHexString(nextCounter()));
		return buff.toString();
	}

	/**
	 * @return parsed GuidData container, given the raw Padded GUID
	 */
	public GUIDData parsePaddedGUID(String paddedGUID) {
		return GUIDParser.parsePaddedGUID(paddedGUID);
	}

	/**
	 * @return parsed GuidData container, given the raw Non-Padded GUID
	 */
	public GUIDData parseGUID(String nonPaddedGUID) {
		return GUIDParser.parseNonPaddedGUID(nonPaddedGUID);
	}

	/**
	 * @return parsed GuidData container, given the raw ISO GUID
	 */
	public GUIDData parseISOGUID(String isoGUID) {
		return GUIDParser.parseIsoGUID(isoGUID);
	}

	/**
	 * returns the unpadded String with pads...
	 * 
	 * @param unpadded
	 *            the unpadded String
	 * @param padLength
	 *            the length to pad to
	 * @return the padded String
	 */
	private String pad(String unpadded, int padLength) {
		// Special case, worth testing for
		if (unpadded.length() >= padLength) {
			return unpadded; // no padding necessary
		}
		RopeBuffer buff = new RopeBuffer();
		for (int i = unpadded.length(); i < padLength; i++) {
			buff.append("0");
		}
		buff.append(unpadded);
		return buff.toString();
	}

	/**
	 * get the next counter value during the lifetime of this UUID Generator.
	 * This will assure uniqueness in combination with timestamp.
	 * 
	 * @return next counter value
	 */
	private synchronized int nextCounter() {
		int result = --m_counter;
		if (result >= 0) {
			result = m_counter = -1; // keep negative even when it wraps
		}
		return result;
	}

	/**
	 * tester method...
	 */
	public static void main(String[] args) throws IOException {

		/**
		 * tests to perform: (1) nextPaddedGUID should be length 32 (2)
		 * nextISOGUID meets format and 36 digit length (3) nextGUID is dot "."
		 * notated
		 */

		GUID t = new GUID();

		//let's do 100,000 tests for each of the three methods and test time...
		int numTests = 100000;

		long startIsoGUID = System.currentTimeMillis();
		for (int i = 0; i < numTests; i++) {
			t.nextISOGUID();
		}
		System.out.println("nextISOGUID="
				+ (System.currentTimeMillis() - startIsoGUID));

		long startNextGUID = System.currentTimeMillis();
		for (int i = 0; i < numTests; i++) {
			t.nextGUID();
		}
		System.out.println("nextGUID="
				+ (System.currentTimeMillis() - startNextGUID));

		long startNextPaddedGUID = System.currentTimeMillis();
		for (int i = 0; i < numTests; i++) {
			t.nextPaddedGUID();
		}
		System.out.println("nextPaddedGUID="
				+ (System.currentTimeMillis() - startNextPaddedGUID));

		System.out.println("UNP GUID: " + t.nextGUID());
		System.out.println("PAD GUID: " + t.nextPaddedGUID());
		System.out.println("ISO GUID: " + t.nextISOGUID());
		GUID t2 = new GUID();
		//the unique object address should be different in this case...
		System.out.println("t2.UNP GUID: " + t2.nextGUID());

		System.out.println("\n\nParse Padded:="
				+ GUIDParser.parsePaddedGUID(t.nextPaddedGUID()));
		System.out.println("\n\nParse t2 Padded:="
				+ GUIDParser.parsePaddedGUID(t2.nextPaddedGUID()));
		System.out.println("\n\nParse Non-Padded:="
				+ GUIDParser.parseNonPaddedGUID(t.nextGUID()));
		System.out.println("\n\nParse t2 Non-Padded:="
				+ GUIDParser.parseNonPaddedGUID(t2.nextGUID()));
		System.out.println("\n\nParse ISO:="
				+ GUIDParser.parseIsoGUID(t.nextISOGUID()));
		System.out.println("\n\nParse t2 ISO:="
				+ GUIDParser.parseIsoGUID(t2.nextISOGUID()));
	}
}