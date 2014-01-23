package com.votingcentral.util.xml;

import javax.xml.transform.URIResolver;
import java.io.Reader;

/**
 * 
 */
public interface IXmlTransformerFactory {
    // get compiled xsl and also store it in cache in internal key
    IXmlTransformer getCompiledXsl(Reader xsl) throws XXMLException;

    // get compiled xsl and also store it in cache in internal key
    IXmlTransformer getCompiledXsl(Reader xsl, URIResolver er) throws XXMLException;

    // get compiled xsl and also store it in cache with keyToCache as cachekey
    IXmlTransformer getCompiledXsl(Reader xsl, String keyToCache) throws XXMLException;

    // get compiled xsl and also store it in cache with keyToCache as cachekey
    IXmlTransformer getCompiledXsl(Reader xsl, URIResolver er, String keyToCache) throws XXMLException;


    IXmlTransformer getCompiledXslFromCache(String cachekey) throws XXMLException;
}
