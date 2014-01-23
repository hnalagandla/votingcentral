package com.votingcentral.util.xml;

import javax.xml.transform.URIResolver;
import java.io.Reader;

/**
 * Date: Apr 29, 2004
 * Time: 9:13:55 AM
 */
public class SaxonXQueryProcessorFactory implements IXQueryProcessorFactory {
    private int nextKey = 0;
    private SimpleLruCache scache = null;

    public SaxonXQueryProcessorFactory(int cachesize) {
        scache = new SimpleLruCache(cachesize);
    }

    public void setCacheSize(int cachesize) {
        scache.setCacheSize(cachesize);
    }

    // get compiled xquery and also store it in cache in internal key
    public IXQueryProcessor getCompiledXQuery(Reader xquery) throws XXMLException {
        return getCompiledXQuery(xquery, (URIResolver) null);
    }

    // get compiled xquery and also store it in cache in internal key
    public IXQueryProcessor getCompiledXQuery(Reader xquery, URIResolver er) throws XXMLException {
        return getCompiledXQuery(xquery, er, generateCacheKey());
    }

    private synchronized String generateCacheKey() {
        nextKey++;
        return "Internal" + +System.currentTimeMillis() + "_" + nextKey;
    }

    // get compiled xquery and also store it in cache with keyToCache as cachekey
    public IXQueryProcessor getCompiledXQuery(Reader xquery, String keyToCache) throws XXMLException {
        return getCompiledXQuery(xquery, null, keyToCache);
    }

    // get compiled xquery and also store it in cache with keyToCache as cachekey
    public IXQueryProcessor getCompiledXQuery(Reader xquery, URIResolver er, String keyToCache) throws XXMLException {
        SaxonXQueryProcessor tt = (SaxonXQueryProcessor) scache.get(keyToCache);
        if (tt == null) {
            tt = new SaxonXQueryProcessor(keyToCache);
            tt.recompileXQuery(xquery, er);
            scache.put(keyToCache, tt);
        } else
            tt.recompileXQuery(xquery, er);
        return tt;
    }

    public IXQueryProcessor getCompiledXQueryFromCache(String cachekey) throws XXMLException {
        SaxonXQueryProcessor val = (SaxonXQueryProcessor) scache.get(cachekey);
        if (val != null && val.isValid())
            return val;
        else
            return null;
    }

}
