/*
 * Created on Jul 27, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.chart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;

import com.votingcentral.model.enums.userinfo.SalaryRangeEnum;
import com.votingcentral.util.UnSyncStringBuffer;

/**
 * @author Gandhari
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SalaryInterpreter {

	private static SalaryInterpreter salary = null;

	private static List salaryRangeObjs = null;

	private SalaryInterpreter() {

	}

	public static SalaryInterpreter getInstance() {
		if (salary == null) {
			salary = new SalaryInterpreter();
			salaryRangeObjs = getRangeObjsInternal();
		}
		return salary;
	}

	private static List getRangeObjsInternal() {
		List local = new ArrayList();
		for (ListIterator itr = SalaryRangeEnum
				.getIterator(SalaryRangeEnum.class); itr.hasNext();) {
			SalaryRangeEnum range = (SalaryRangeEnum) itr.next();
			String[] salaryRange = StringUtils.split(range.getName(), "-");
			SalaryRange a = salary.new SalaryRange();
			a.setLow(Integer.parseInt(salaryRange[0]));
			a.setHigh(Integer.parseInt(salaryRange[1]));
			local.add(a);
		}
		return local;
	}

	public class SalaryRange {

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
				buff.append("Salary").append(low).append("-").append(high);
				rangeKey = buff.toString();
			}
			return rangeKey;
		}
	}

	public String getSalaryRangeKeyForSalary(long salary) {
		for (Iterator itr = salaryRangeObjs.iterator(); itr.hasNext();) {
			SalaryRange range = (SalaryRange) itr.next();
			if (inRange(range, salary)) {
				return range.getRangeKey();
			}
		}
		return "INVALID";
	}

	private boolean inRange(SalaryRange range, long salary) {
		return salary >= range.getLow() && salary <= range.getHigh();
	}

	/**
	 * @return Returns the salaryRangeObjs.
	 */
	public static List getSalaryRangeObjs() {
		return salaryRangeObjs;
	}
}