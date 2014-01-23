package com.votingcentral.util.xml;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleLruCacheNoSync {

	public SimpleLruCacheNoSync(int maxsize) {
		map = new LinkedHashMap(1024, 0.75F, true);
		capacity = maxsize;
	}

	public void setCacheSize(int size) {
		if (size >= 0)
			capacity = size;
	}

	public void put(Object key, Object value) {
		if (value == null) {
			map.remove(key);
			return;
		}
		Object oldval = map.put(key, value);
		for (; map.size() > capacity;) {
			Iterator iter = map.entrySet().iterator();
			Map.Entry e = (Map.Entry) iter.next();
			iter.remove();
		}
	}

	public Object get(Object key) {
		return map.get(key);
	}

	LinkedHashMap map;
	int capacity;
}
