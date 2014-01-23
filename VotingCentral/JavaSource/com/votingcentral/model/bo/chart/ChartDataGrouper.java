/*
 * Created on Jul 22, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.votingcentral.model.bo.chart;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Gandhari
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ChartDataGrouper {

	public static String KEY_SEPARATOR = ":";

	private Map groupedData = new TreeMap();

	public void groupData(String key1, String key2, String key3, long count) {
		
		String groupKey = key1 + KEY_SEPARATOR + key2 + KEY_SEPARATOR + key3;

		Long total = (Long) groupedData.get(groupKey);
		if (total == null) {
			groupedData.put(groupKey, new Long(count));
		} else {
			long sub = total.longValue() + count;
			groupedData.put(groupKey, new Long(sub));
		}
	}

	/**
	 * @return Returns the groupedData.
	 */
	public Map getGroupedData() {
		return groupedData;
	}
}