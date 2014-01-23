package com.votingcentral.util.xml;

import javax.xml.transform.URIResolver;
import java.io.Reader;

/**
 * 
 * Date: Apr 29, 2004
 * Time: 9:06:56 AM
 * use XMLUtil.getXQueryProcessorFactory().... to get this
 */
public interface IXQueryProcessorFactory {
    // get compiled xquery and also store it in cache in internal key
    IXQueryProcessor getCompiledXQuery(Reader xquery) throws XXMLException;

    // get compiled xquery and also store it in cache in internal key
    IXQueryProcessor getCompiledXQuery(Reader xquery, URIResolver er) throws XXMLException;

    // get compiled xquery and also store it in cache with keyToCache as cachekey
    IXQueryProcessor getCompiledXQuery(Reader xquery, String keyToCache) throws XXMLException;

    // get compiled xquery and also store it in cache with keyToCache as cachekey
    IXQueryProcessor getCompiledXQuery(Reader xquery, URIResolver er, String keyToCache) throws XXMLException;


    IXQueryProcessor getCompiledXQueryFromCache(String cachekey) throws XXMLException;

}
