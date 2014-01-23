/*
 * Created on Jul 27, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.chart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.votingcentral.env.EnvProps;
import com.votingcentral.util.UnSyncStringBuffer;

/**
 * @author Gandhari
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AgeInterpreter {
	public static String AGE_RANGES = EnvProps
			.getProperty("age.ranges.for.charts");

	private static AgeInterpreter age = null;

	private static List ageRangeObjs = null;

	private AgeInterpreter() {

	}

	public static AgeInterpreter getInstance() {
		if (age == null) {
			age = new AgeInterpreter();
			ageRangeObjs = getRangeObjsInternal();
		}
		return age;
	}

	private static List getRangeObjsInternal() {
		List local = new ArrayList();
		String[] ranges = StringUtils.split(AGE_RANGES, ",");
		int len = ranges.length;
		for (int i = 0; i < len; i++) {
			String[] ages = StringUtils.split(ranges[i], "-");
			AgeRange a = age.new AgeRange();
			a.setLow(Integer.parseInt(ages[0]));
			a.setHigh(Integer.parseInt(ages[1]));
			local.add(a);
		}
		return local;
	}

	public class AgeRange {

		private int low;

		private int high;

		private String rangeKey;

		/**
		 * @return Returns the high.
		 */
		public int getHigh() {
			return high;
		}

		/**
		 * @param high
		 *            The high to set.
		 */
		public void setHigh(int high) {
			this.high = high;
		}

		/**
		 * @return Returns the low.
		 */
		public int getLow() {
			return low;
		}

		/**
		 * @param low
		 *            The low to set.
		 */
		public void setLow(int low) {
			this.low = low;
		}

		public String getRangeKey() {
			if (rangeKey == null || rangeKey.length() == 0) {
				UnSyncStringBuffer buff = new UnSyncStringBuffer();
				buff.append("Age").append(low).append("-").append(high);
				rangeKey = buff.toString();
			}
			return rangeKey;
		}
	}

	public String getAgeRangeKeyForYear(int yob) {
		Calendar today = Calendar.getInstance();
		int currAge = today.get(Calendar.YEAR) - yob;
		for (Iterator itr = ageRangeObjs.iterator(); itr.hasNext();) {
			AgeRange range = (AgeRange) itr.next();
			if (inRange(range, currAge)) {
				return range.getRangeKey();
			}
		}
		return "INVALID";
	}

	private boolean inRange(AgeRange range, int currAge) {
		return currAge >= range.getLow() && currAge <= range.getHigh();
	}

	/**
	 * @return Returns the ageRangeObjs.
	 */
	public List getAgeRangeObjs() {
		return ageRangeObjs;
	}
}