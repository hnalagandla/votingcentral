package com.votingcentral.util.xml;

import java.io.Reader;

import org.xml.sax.EntityResolver;

/**
 * 
 * * use XmlUtil.getXmlValidatorFactory().... to get an instance
 */
public interface IXmlValidatorFactory {
    public final int W3C_SCHEMA_VALIDATOR = 1;
    public final int DTD_VALIDATOR = 2;
    public final int RELAX_NG_VALIDATOR = 3;
    public final int TREX_VALIDATOR = 4;
    public final int RELAX_CORE_VALIDATOR = 5;
    public final int RELAX_NAMESPACE_VALIDATOR = 6;


    // default to w3c schema
    public IXmlValidator compileValidator(Reader schemadata)
            throws XXMLException;

    // default to w3c schema
    public IXmlValidator compileValidator(Reader schemadata, EntityResolver er)
            throws XXMLException;

    public IXmlValidator compileValidator(Reader schemadata, int schemaType)
            throws XXMLException;

    public IXmlValidator compileValidator(Reader schemadata, EntityResolver er, int schemaType)
            throws XXMLException;

    public IXmlValidator compileValidator(Reader schemadata, EntityResolver er, String cacheKey)
            throws XXMLException;

    // get compiled schema/dtd and store as well in given cache key
    public IXmlValidator compileValidator(Reader schemadata, EntityResolver er, String cacheKey, int schemaType)
            throws XXMLException;

    IXmlValidator getValidator(String cacheKey)
            throws XXMLException;

}
