/*
 * Created on Aug 27, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
*
* This class has the same API as StringBuffer.  The main benefit this
* class provides is that it delays appends for append(String) and append(Object)
* till the last possible moment.  That moment is forced when an operation occurs
* that requires the response to act as if those previous appends have been applied.
*
* For example if you need to do a repeated set of appends(String | Object) and
* you want to do the minimum amount of memory allocations, then use this class.
* It also makes it nice for cases where it is not possible to presize the StringBuffer
* before its use; for example using a StringBuffer in a visitor pattern such as in
* our generateXml() in the XmlBuilder.
*
* Note that if the intention is to do many append(char), append(char[]),
* append(char[], int, int) or insert(...) operations force delayed appends to acutally
* occur and will cut down on the efficiency of this implmentation over the
* java.lang.StringBuffer implementation.
*
* The following methods will force the currently deferred String, Object and other
* type appends to occur:
*   append(char), append(char[]), append(char[], int, int)
*   All the inserts
*   charAt, delete, getChars, reverse, setCharAt, substring, toString
* Note that in the case where the above methods would fail due to some sort of
* incorrect argument exception, the force of pushing deferred data into the internal
* StringBuffer has already occured (we do not optimize failure cases)>
*
* This class is not threadsafe.  We are using an UnsynchronizedStringBuffer
* as the backing store.  There is a synchronized wrapper version of this class
* available, see
* {@link SynchronizedRopeBuffer SynchronizedRopeBuffer}.
* @see java.lang.StringBuffer
*
*/
public class RopeBuffer {
	private static final int INITIAL_LIST_SIZE = 32;
	private int m_initialBufferSize = INITIAL_LIST_SIZE;
	private List m_appendList = null;
	private int m_stringSize = 0; // size of deferred appends, excludes chars in buffer
	private UnSyncStringBuffer m_buffer = null;

	public RopeBuffer() {
		this(INITIAL_LIST_SIZE);
	}
	protected RopeBuffer(int deferredListSize) {
		m_appendList = new ArrayList(deferredListSize);
	}

	protected RopeBuffer(int deferredListSize, int initBufSize) {
		m_appendList = new ArrayList(deferredListSize);
		m_initialBufferSize = initBufSize;
		m_buffer = new UnSyncStringBuffer(m_initialBufferSize);
	}

	public RopeBuffer(String str) {
		this(INITIAL_LIST_SIZE);
		// StringBuffer will throw NPE when null passed to constructor
		// so mimic that behavior in RopeBuffer
		if (str == null) {
			throw new NullPointerException();
		}
		append(str);
	}
	public RopeBuffer(StringBuffer sb) {
		this(INITIAL_LIST_SIZE);
		// There is no constructor in StringBuffer
		// that takes a StringBuffer argument, but we choose to match
		// the behavior when a null is passed to the String constructor
		if (sb == null) {
			throw new NullPointerException();
		}
		append(sb);
	}
	public RopeBuffer(RopeBuffer rb) {
		this(INITIAL_LIST_SIZE);
		// Behave the same as when null is passed to constructor that
		// takes a StringBuffer
		if (rb == null) {
			throw new NullPointerException();
		}
		append(rb);
	}

	/**
	 * Resets the contents of this RopeBuffer to be empty.  Any existing
	 * backing storage is preserved for reuse.  CAUTION: Calling this method will
	 * not release storage for reclaimation by the garbage collector.
	 */
	public void clear() {
		m_appendList.clear();
		m_stringSize = 0;
		if (m_buffer != null) {
			m_buffer.clear();
		}
	}
	/**
	 * Appends the string representation of the <code>boolean</code>
	 * argument to the string buffer.
	 * <p>
	 * The argument is converted to a string as if by the method
	 * <code>String.valueOf</code>, and the characters of that
	 * string are then appended to this string buffer.
	 *
	 * @param   b   a <code>boolean</code>.
	 * @return  a reference to this <code>RopeBuffer</code>.
	 * @see     java.lang.String#valueOf(boolean)
	 * @see     RopeBuffer#append(java.lang.String)
	 */
	public RopeBuffer append(boolean b) {
		return append(String.valueOf(b));
	}
	public RopeBuffer append(char c) {
		applyOutstandingChanges(1); // we know we need one more char
		getStringBuffer().append(c);
		return this;
	}
	public RopeBuffer append(char[] str) {
		// StringBuffer will throw a null pointer exception if you pass
		// a null char[] so mimic that behavior
		if (str == null) {
			throw new NullPointerException();
		}
		applyOutstandingChanges(str.length);
		getStringBuffer().append(str);
		return this;
	}
	public RopeBuffer append(char[] str, int offset, int len) {
		// StringBuffer will throw a null pointer exception if you pass
		// a null char[] so mimic that behavior
		if (str == null) {
			throw new NullPointerException();
		}
		applyOutstandingChanges(len);
		getStringBuffer().append(str, offset, len);
		return this;
	}
	/**
	 * Appends the string representation of the <code>double</code>
	 * argument to this string buffer.
	 * <p>
	 * The argument is converted to a string as if by the method
	 * <code>String.valueOf</code>, and the characters of that
	 * string are then appended to this string buffer.
	 *
	 * @param   d   a <code>double</code>.
	 * @return  a reference to this <code>RopeBuffer</code> object.
	 * @see     java.lang.String#valueOf(double)
	 * @see     RopeBuffer#append(java.lang.String)
	 */
	public RopeBuffer append(double d) {
		return append(String.valueOf(d));
	}
	/**
	 * Appends the string representation of the <code>float</code>
	 * argument to this string buffer.
	 * <p>
	 * The argument is converted to a string as if by the method
	 * <code>String.valueOf</code>, and the characters of that
	 * string are then appended to this string buffer.
	 *
	 * @param   f   a <code>float</code>.
	 * @return  a reference to this <code>RopeBuffer</code> object.
	 * @see     java.lang.String#valueOf(float)
	 * @see     RopeBuffer#append(java.lang.String)
	 */
	public RopeBuffer append(float f) {
		return append(String.valueOf(f));
	}
	/**
	 * Appends the string representation of the <code>int</code>
	 * argument to this string buffer.
	 * <p>
	 * The argument is converted to a string as if by the method
	 * <code>String.valueOf</code>, and the characters of that
	 * string are then appended to this string buffer.
	 *
	 * @param   i   an <code>int</code>.
	 * @return  a reference to this <code>StringBuffer</code> object.
	 * @see     java.lang.String#valueOf(int)
	 * @see     RopeBuffer#append(java.lang.String)
	 */
	public RopeBuffer append(int i) {
		return append(String.valueOf(i));
	}
	/**
	 * Appends the string representation of the <code>long</code>
	 * argument to this string buffer.
	 * <p>
	 * The argument is converted to a string as if by the method
	 * <code>String.valueOf</code>, and the characters of that
	 * string are then appended to this string buffer.
	 *
	 * @param   l   a <code>long</code>.
	 * @return  a reference to this <code>StringBuffer</code> object.
	 * @see     java.lang.String#valueOf(long)
	 * @see     RopeBuffer#append(java.lang.String)
	 */
	public RopeBuffer append(long l) {
		return append(String.valueOf(l));
	}
	/**
	 * Append the logical contents of another RopeBuffer to this
	 * one.
	 */
	public RopeBuffer append(RopeBuffer rb) {
		if (rb == null) {
			return append((Object) null);
		}
		if (rb.m_buffer != null) {
			m_appendList.add(rb.m_buffer.toString());
		}

		// Add all the deferred appends from the argument buffer to us
		m_appendList.addAll(rb.m_appendList);
		m_stringSize += rb.length();
		return this;
	}
	/**
	 * We need to get the String value immediately at the time this call is
	 * made.  This is the behavior that StringBuffer provides.  Note that we
	 * still delay the actual resulting string append since we do an
	 * append(String) with the String value.  Note also, that the behavior
	 * of String.valueOf(o) will test for null and return the characters "null"
	 * if the input argument is null.
	 */
	public RopeBuffer append(Object o) {
		return append(String.valueOf(o));
	}
	public RopeBuffer append(String str) {
		// Use String.valueOf() to supply value for null input
		if (str == null) {
			str = String.valueOf(str);
		}
		int len = str.length();
		m_appendList.add(str);
		m_stringSize += len;
		return this;
	}
	public int capacity() {
		// This method is usually called as a prelude to ensureCapacity.  It really has 
		// no meaning when we defer creation of the buffer, so we return something that always
		// looks big.
		return Integer.MAX_VALUE;
	}
	/**
	 * The specified character of the sequence currently represented by
	 * the rope buffer, as indicated by the <code>index</code> argument,
	 * is returned. The first character of a string buffer is at index
	 * <code>0</code>, the next at index <code>1</code>, and so on, for
	 * array indexing.
	 * <p>
	 * The index argument must be greater than or equal to
	 * <code>0</code>, and less than the length of this rope buffer.
	 *
	 * @param      index   the index of the desired character.
	 * @return     the character at the specified index of this rope buffer.
	 * @exception  IndexOutOfBoundsException  if <code>index</code> is
	 *             negative or greater than or equal to <code>length()</code>.
	 * @see       #length()
	 */
	public char charAt(final int index) {
		// Optimize charAt() to work without applying outstanding changes
		// First check that index is in bounds.
		if ((index < 0) || (index >= length())) {
			throw new StringIndexOutOfBoundsException(index);
		}

		// Check, if index is in the buffer do this the easy way
		int offset = deferredStart();
		if (index < offset) {
			return getStringBuffer().charAt(index);
		}

		// OK, we need to find the deferred String that has the 
		// desired character.  We know there is one since we
		// already did the bounds check.
		final int limit = m_appendList.size();
		for (int i = 0; i < limit; i++) {
			String deferredString = (String) m_appendList.get(i);
			int newOffset = offset + deferredString.length();
			if (index < newOffset) {
				// Return the desired character
				return deferredString.charAt(index - offset);
			}
			offset = newOffset;
		}

		// If we fall through the loop, then something went terribly wrong
		throw new IllegalStateException("should have found desired character at index: " + index
			+ " RopeBuffer length is " + length());
	}
	/**
	 * Apply any outstanding changes and return the underlying buffer.
	 * This method should be used with caution.  Subsequent appends() to the
	 * RopeBuffer are not reflected in the returned buffer.  The returned buffer
	 * is the live backing storage.  This method is intened to be used in
	 * preformance sensitive areas where mulitple calls to charAt() are not
	 * desireable.
	 * @return the live buffer that represents the current state of this
	 * RopeBuffer.
	 */
	public UnSyncStringBuffer get() {
		applyOutstandingChanges();
		if (m_buffer == null) {
			m_buffer = new UnSyncStringBuffer();
		}
		return m_buffer;
	}
	public RopeBuffer delete(int start, int end) {
		applyOutstandingChanges();
		getStringBuffer().delete(start, end);
		return this;
	}
	public RopeBuffer deleteCharAt(int index) {
		applyOutstandingChanges();
		getStringBuffer().deleteCharAt(index);
		return this;
	}
	public void ensureCapacity(int minimumCapacity) {
		// This method is a noop since we defer buffer allocation util we 
		// really need it anyway.
	}
	/**
	 * Characters are copied from this rope buffer into the
	 * destination character array <code>dst</code>. The first character to
	 * be copied is at index <code>srcBegin</code>; the last character to
	 * be copied is at index <code>srcEnd-1</code>. The total number of
	 * characters to be copied is <code>srcEnd-srcBegin</code>. The
	 * characters are copied into the subarray of <code>dst</code> starting
	 * at index <code>dstBegin</code> and ending at index:
	 * <p><blockquote><pre>
	 * dstbegin + (srcEnd-srcBegin) - 1
	 * </pre></blockquote>
	 *
	 * @param      srcBegin   start copying at this offset in the rope buffer.
	 * @param      srcEnd     stop copying at this offset in the rope buffer.
	 * @param      dst        the array to copy the data into.
	 * @param      dstBegin   offset into <code>dst</code>.
	 * @exception  NullPointerException if <code>dst</code> is
	 *             <code>null</code>.
	 * @exception  IndexOutOfBoundsException  if any of the following is true:
	 *             <ul>
	 *             <li><code>srcBegin</code> is negative
	 *             <li><code>dstBegin</code> is negative
	 *             <li>the <code>srcBegin</code> argument is greater than
	 *             the <code>srcEnd</code> argument.
	 *             <li><code>srcEnd</code> is greater than
	 *             <code>this.length()</code>, the current length of this
	 *             rope buffer.
	 *             <li><code>dstBegin+srcEnd-srcBegin</code> is greater than
	 *             <code>dst.length</code>
	 *             </ul>
	 */
	public void getChars(final int srcBegin, final int srcEnd, final char[] dst, final int dstBegin) {
		if (srcBegin < 0) {
			throw new StringIndexOutOfBoundsException("srcBegin: " + srcBegin);
		}
		if (dstBegin < 0) {
			throw new StringIndexOutOfBoundsException("dstBegin: " + dstBegin);
		}
		if ((srcEnd < 0) || (srcEnd > length())) {
			throw new StringIndexOutOfBoundsException("srcEnd: " + srcEnd);
		}
		if (srcBegin > srcEnd) {
			throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
		}
		if (dst == null) {
			throw new NullPointerException();
		}
		if (((dstBegin + srcEnd) - srcBegin) > dst.length) {
			throw new StringIndexOutOfBoundsException("dstBegin+srcEnd-srcBegin > dst.length");
		}
		int index = srcBegin; // logical index of next character to copy
		int dstIx = dstBegin; // index in destination buffer for next char

		// Copy any desired characters that are in the buffer
		int offset = deferredStart();
		if (index < offset) {
			// m_buffer cannot be null here because deferred start is 
			// greater than zero which means it's at least one so there 
			// are chars in the buffer...ergo, it cannot be null.
			int lclEnd = Math.min(srcEnd, offset); // offset is also the length of m_buffer here
			m_buffer.getChars(index, lclEnd, dst, dstIx);
			dstIx += (lclEnd - index);
			index = lclEnd;
		}

		// Pick out chars from the deferred strings if necessary		
		// Make sure we're positioned to the right entry in the list to start
		final int limit = m_appendList.size();
		for (int curEntIx = 0; (curEntIx < limit) && (index < srcEnd); curEntIx++) {
			String deferredString = (String) m_appendList.get(curEntIx);
			int deferredLength = deferredString.length();
			int newOffset = offset + deferredString.length();
			if (index < newOffset) {
				// Copy the chars we need from the deferred string
				int lclStart = index - offset;
				int lclEnd = Math.min(deferredLength, srcEnd - offset);
				int lclCopied = lclEnd - lclStart;
				deferredString.getChars(lclStart, lclEnd, dst, dstIx);
				index += lclCopied;
				dstIx += lclCopied;
			}
			offset = newOffset;
		}

		// If we haven't copied what we need by now, something's wrong
		if (index != srcEnd) {
			throw new IllegalStateException("Error gettings chars. index: " + index + " srcEnd: " + srcEnd
				+ " srcBegin: " + srcBegin + " length: " + length());
		}
	}
	public RopeBuffer insert(int offset, boolean b) {
		return insert(offset, String.valueOf(b));
	}
	public RopeBuffer insert(int offset, char c) {
		applyOutstandingChanges(1);
		getStringBuffer().insert(offset, c);
		return this;
	}
	public RopeBuffer insert(int offset, char[] str) {
		// StringBuffer will throw a NullPointerException if null char[]
		// is passed, mimic that behavior
		if (str == null) {
			throw new NullPointerException();
		}
		applyOutstandingChanges(str.length);
		getStringBuffer().insert(offset, str);
		return this;
	}
	public RopeBuffer insert(int index, char[] str, int offset, int len) {
		// StringBuffer will throw a NullPointerException if null char[]
		// is passed, mimic that behavior
		if (str == null) {
			throw new NullPointerException();
		}
		applyOutstandingChanges(len);
		getStringBuffer().insert(index, str, offset, len);
		return this;
	}
	public RopeBuffer insert(int offset, double d) {
		return insert(offset, String.valueOf(d));
	}
	public RopeBuffer insert(int offset, float f) {
		return insert(offset, String.valueOf(f));
	}
	public RopeBuffer insert(int offset, int i) {
		return insert(offset, String.valueOf(i));
	}
	public RopeBuffer insert(int offset, long l) {
		return insert(offset, String.valueOf(l));
	}
	public RopeBuffer insert(int offset, Object o) {
		return insert(offset, String.valueOf(o));
	}
	public RopeBuffer insert(int offset, String str) {
		if (str == null) {
			str = String.valueOf(str);
		}
		applyOutstandingChanges(str.length());
		getStringBuffer().insert(offset, str);
		return this;
	}
	public int length() {
		if (m_buffer == null) {
			return m_stringSize;
		}
		return m_buffer.length() + m_stringSize;
	}
	public RopeBuffer replace(int start, int end, String str) {
		// StringBuffer will throw a NullPointerException if null str
		// is passed, mimic that behavior
		if (str == null) {
			throw new NullPointerException();
		}

		// Apply outstanding changes and ensure that resulting buffer
		// has enough space.  If the computed argument is less than 0,
		// it is ignored.
		applyOutstandingChanges(str.length() - (end - start));
		getStringBuffer().replace(start, end, str);
		return this;
	}
	public RopeBuffer reverse() {
		applyOutstandingChanges();
		getStringBuffer().reverse();
		return this;
	}
	public void setCharAt(int index, char ch) {
		applyOutstandingChanges();
		getStringBuffer().setCharAt(index, ch);
	}
	public void setLength(int newLength) {
		// Optimize case of setLength(0), common method of clearing buffer
		if (newLength == 0) {
			clear();
			return;
		}
		applyOutstandingChanges(newLength - length()); // ignored if < 0
		getStringBuffer().setLength(newLength);
	}
	/**
	 * Returns a new <code>String</code> that contains a subsequence of
	 * characters currently contained in this <code>RopeBuffer</code>.The
	 * substring begins at the specified index and extends to the end of the
	 * <code>RopeBuffer</code>.
	 *
	 * @param      start    The beginning index, inclusive.
	 * @return     The new string.
	 * @exception  StringIndexOutOfBoundsException  if <code>start</code> is
	 *             less than zero, or greater than the length of this
	 *             <code>RopeBuffer</code>.
	 */
	public String substring(int start) {
		return substring(start, length());
	}
	/**
	 * Returns a new <code>String</code> that contains a subsequence of
	 * characters currently contained in this <code>RopeBuffer</code>. The
	 * substring begins at the specified <code>start</code> and
	 * extends to the character at index <code>end - 1</code>.
	 *
	 * @param      start    The beginning index, inclusive.
	 * @param      end      The ending index, exclusive.
	 * @return     The new string.
	 * @exception  StringIndexOutOfBoundsException  if <code>start</code>
	 *             or <code>end</code> are negative or greater than
	 *             <code>length()</code>, or <code>start</code> is
	 *             greater than <code>end</code>.
	 * @since      1.2
	 */
	public String substring(int start, int end) {
		if (start < 0) {
			throw new StringIndexOutOfBoundsException(start);
		}
		if (end > length()) {
			throw new StringIndexOutOfBoundsException(end);
		}
		if (start > end) {
			throw new StringIndexOutOfBoundsException(end - start);
		}

		// Get temp char array
		char[] temp = new char[end - start];

		// Get the characters we want in temporary char array
		getChars(start, end, temp, 0);
		return new String(temp);
	}
	public String toString() {
		applyOutstandingChanges();
		return (m_buffer == null) ? "" : m_buffer.toString();
	}
	/*
	 * Returns an array of String from internal collection of strings
	 */
	public String[] toStringArray() {
		int arraySize = m_appendList.size();
		int numberOfStrings = arraySize;
		if (m_buffer != null) { // If m_buffer is not empty, return it as well
			++arraySize;
		}
		String[] stringArray = new String[arraySize];

		// add m_buffer as the first element in the array
		if (m_buffer != null) {
			stringArray[0] = m_buffer.toString();
		}
		for (int i = 0, j = (arraySize - numberOfStrings); i < numberOfStrings; i++, j++) {
			String s = (String) m_appendList.get(i);
			stringArray[j] = s;
		}
		return stringArray;
	}
	//
	// Private
	//
	private UnSyncStringBuffer getStringBuffer() {
		if (m_buffer == null) {
			m_buffer = new UnSyncStringBuffer();
		}
		return m_buffer;
	}
	/**
	 * Return the index, relative to the logical contents of the
	 * RopeBuffer, of the first character held in deferred list.
	 * There may or may not be a character in this position.
	 */
	private int deferredStart() {
		if (m_buffer == null) {
			return 0;
		}
		return m_buffer.length();
	}
	/**
	 * Apply any outstanding changes.
	 */
	private void applyOutstandingChanges() {
		applyOutstandingChanges(0);
	}
	/**
	 * Apply any outstanding changes.  Ensure that the buffer has at least
	 * the specified additional capacity when done.
	 * @param additionalCapacity a non-negative integer specifying the
	 * minimum additional capacity known to be needed in the buffer.
	 */
	private void applyOutstandingChanges(int additionalCapacity) {
		// if nothing to process then simply return
		if (m_stringSize == 0) {
			return;
		}
		int numberOfStrings = m_appendList.size();
		int capacity;

		// ignore negative additioinal capacity
		if (additionalCapacity < 0) {
			additionalCapacity = 0;
		}
		if (m_buffer == null) {
			// Use 1.125 times known size, but ensure that the additional capacity requirement
			// is satisfied.
			capacity = m_stringSize + Math.max(additionalCapacity, m_stringSize >>> 3);
			m_buffer = new UnSyncStringBuffer(capacity);
		} else {
			capacity = m_buffer.length() + m_stringSize;
			// Use 1.125 times known size, but ensure that the additional capacity requirement
			// is satisfied 
			capacity += Math.max(additionalCapacity, capacity >>> 3);
			m_buffer.ensureCapacity(capacity);
		}
		for (int i = 0; i < numberOfStrings; i++) {
			String s = (String) m_appendList.get(i);
			m_buffer.append(s);
		}
		m_appendList.clear();
		m_stringSize = 0;
	}
	public Iterator iterator() {
		return new RopeBufferDefferedListIterator();
	}

	class RopeBufferDefferedListIterator implements Iterator {
		int currentIndex = 0;

		RopeBufferDefferedListIterator() {
			if (m_buffer != null) { // return the string buffer as well
				currentIndex = -1;
			}
		}

		public boolean hasNext() {
			return (m_appendList.size() > currentIndex);
		}
		public Object next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements available");
			}
			if (currentIndex == -1) { // first return the string in buffer
				++currentIndex;
				return m_buffer.toString();
			}
			return m_appendList.get(currentIndex++);
		}
		public void remove() {
			throw new UnsupportedOperationException("Remove not supported");
		}
	}
}
