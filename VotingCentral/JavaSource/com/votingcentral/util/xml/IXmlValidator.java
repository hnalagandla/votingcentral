package com.votingcentral.util.xml;

import java.io.Reader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;

/**
 * 
 */
public interface IXmlValidator {
    // throws exception in case of error
    void validate(Node node) throws XXMLException;

    void validate(Reader xml) throws XXMLException;

    void validate(Reader xml, EntityResolver er) throws XXMLException;

    String getValidatorCacheKey(); // to refer later

    void recompileValidator(Reader schemaXml) throws XXMLException;

    void recompileValidator(Reader schemaXml, EntityResolver er) throws XXMLException;

    DocumentBuilderFactory getDocumentBuilderFactory() throws XXMLException;

    SAXParserFactory getSAXParserFactory() throws XXMLException;


}
