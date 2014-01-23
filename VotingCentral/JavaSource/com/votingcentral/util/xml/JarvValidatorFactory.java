package com.votingcentral.util.xml;

import org.xml.sax.EntityResolver;

import java.io.Reader;

/**
 * Date: Mar 28, 2004
 * Time: 2:15:14 PM
 */
class JarvValidatorFactory implements IXmlValidatorFactory {
    public JarvValidatorFactory(int cachesize) {
        scache = new SimpleLruCache(cachesize);
    }

    public void setCacheSize(int cachesize) {
        scache.setCacheSize(cachesize);
    }

    public IXmlValidator compileValidator(Reader schemadata, int schemaType) throws XXMLException {
        return compileValidator(schemadata, null, schemaType);
    }

    // default to w3c schema
    public IXmlValidator compileValidator(Reader schemadata) throws XXMLException {
        return compileValidator(schemadata, W3C_SCHEMA_VALIDATOR);
    }

    // default to w3c schema
    public IXmlValidator compileValidator(Reader schemadata, EntityResolver er) throws XXMLException {
        return compileValidator(schemadata, er, W3C_SCHEMA_VALIDATOR);
    }

    public IXmlValidator compileValidator(Reader schemadata, EntityResolver er, int schemaType) throws XXMLException {
        return compileValidator(schemadata, er, generateCacheKey(schemaType), schemaType);
    }

    private int nextKey = 0;
    private SimpleLruCache scache = null;

    private synchronized String generateCacheKey(int schemaType) {
        nextKey++;
        return "Internal" + schemaType + "_" + System.currentTimeMillis() + "_" + nextKey;
    }

    public IXmlValidator compileValidator(Reader schemadata, EntityResolver er, String cacheKey) throws XXMLException {
        return compileValidator(schemadata, er, cacheKey, W3C_SCHEMA_VALIDATOR);
    }

    // get compiled schema/dtd and store as well in given cache key
    public IXmlValidator compileValidator(Reader schemadata, EntityResolver er, String cacheKey, int schemaType) throws XXMLException {
        JarvValidator val = (JarvValidator) scache.get(cacheKey);
        if (val == null) {
            val = new JarvValidator(cacheKey, schemaType);
            val.recompileValidator(schemadata, er);
            scache.put(cacheKey, val);
        } else
            val.recompileValidator(schemadata, er);
        return val;
    }

    public IXmlValidator getValidator(String cacheKey) throws XXMLException {
        JarvValidator val = (JarvValidator) scache.get(cacheKey);
        if (val != null && val.isValid())
            return val;
        else
            return null;
    }
}
