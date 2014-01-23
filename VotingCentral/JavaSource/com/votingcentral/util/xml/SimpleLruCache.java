package com.votingcentral.util.xml;


/**
 * 
 */
public class SimpleLruCache {
    SimpleLruCacheNoSync cache;

    public SimpleLruCache(int maxsize) {
        cache = new SimpleLruCacheNoSync(maxsize);
    }

    public synchronized void setCacheSize(int size) {
        cache.setCacheSize(size);
    }

    public synchronized void put(Object key, Object value) {
        cache.put(key, value);
    }

    public synchronized Object get(Object key) {
        return cache.get(key);
    }
}
