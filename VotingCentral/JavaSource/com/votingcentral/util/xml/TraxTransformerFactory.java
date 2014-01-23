package com.votingcentral.util.xml;

import java.io.Reader;

import javax.xml.transform.URIResolver;

/**
 * 
 */
class TraxTransformerFactory implements com.votingcentral.util.xml.IXmlTransformerFactory {
    private int nextKey = 0;
    private SimpleLruCache scache = null;

    public TraxTransformerFactory(int cachesize) {
        scache = new SimpleLruCache(cachesize);
    }

    public void setCacheSize(int cachesize) {
        scache.setCacheSize(cachesize);
    }

    // get compiled xsl and also store it in cache in internal key
    public IXmlTransformer getCompiledXsl(Reader xsl) throws XXMLException {
        return getCompiledXsl(xsl, (URIResolver) null);
    }

    // get compiled xsl and also store it in cache in internal key
    public IXmlTransformer getCompiledXsl(Reader xsl, URIResolver er) throws XXMLException {
        return getCompiledXsl(xsl, er, generateCacheKey());
    }

    private synchronized String generateCacheKey() {
        nextKey++;
        return "Internal" + +System.currentTimeMillis() + "_" + nextKey;
    }

    // get compiled xsl and also store it in cache with keyToCache as cachekey
    public IXmlTransformer getCompiledXsl(Reader xsl, String keyToCache) throws XXMLException {
        return getCompiledXsl(xsl, null, keyToCache);
    }

    // get compiled xsl and also store it in cache with keyToCache as cachekey
    public IXmlTransformer getCompiledXsl(Reader xsl, URIResolver er, String keyToCache) throws XXMLException {
        TraxTransformer tt = (TraxTransformer) scache.get(keyToCache);
        if (tt == null) {
            tt = new TraxTransformer(keyToCache);
            tt.recompileXsl(xsl, er);
            scache.put(keyToCache, tt);
        } else
            tt.recompileXsl(xsl, er);
        return tt;
    }

    public IXmlTransformer getCompiledXslFromCache(String cachekey) throws XXMLException {
        TraxTransformer val = (TraxTransformer) scache.get(cachekey);
        if (val != null && val.isValid())
            return val;
        else
            return null;
    }

}
