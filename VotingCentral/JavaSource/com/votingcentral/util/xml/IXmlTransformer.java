package com.votingcentral.util.xml;

import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXSource;

import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;

/**
 * 
 * Author:
 * * use XmlUtil.getXmlTransformerFactory().... to get an instance
 */
public interface IXmlTransformer {

    String getKeyForXsl(); // for later referring

    void recompileXsl(Reader xsl) throws XXMLException;

    void recompileXsl(Reader xsl, URIResolver er) throws XXMLException;

    // variety of utility methods

    // DOM/SAX/Reader --> DOM
    public Node transform(Reader xml) throws XXMLException;

    public Node transform(Reader xml, EntityResolver er) throws XXMLException;

    public Node transform(Node input) throws XXMLException;

    public Node transform(Node input, Map parameters) throws XXMLException;

    public Node transform(Node input, Map parameters, Node outputnode) throws XXMLException;

    public Node transform(Reader xml, EntityResolver er, Map parameters) throws XXMLException;

    public Node transform(Reader xml, EntityResolver er, Map parameters, Node outputnode) throws XXMLException;

    public Node transform(SAXSource src, Map parameters) throws XXMLException;

    public Node transform(SAXSource src, Map parameters, Node outputnode) throws XXMLException;

    // Source --> Result
    public void transform(Source src, Result res) throws XXMLException;

    public void transform(Source src, Result res, Map parameters) throws XXMLException;


    // Reader --> Writer
    public void transform(Reader xml, Writer wr) throws XXMLException;

    public void transform(Reader xml, EntityResolver er, Writer wr) throws XXMLException;

    public void transform(Reader xml, EntityResolver er, Map parameters, Writer wr) throws XXMLException;
    public void transform(Node nd, EntityResolver er, Map parameters, Writer wr) throws XXMLException;

    // Reader -> Content Handler of sax
    public void transform(Reader xml, EntityResolver er, Map parameters, ContentHandler ch) throws XXMLException;

    // returns a new tranformer (dont use return value in multiple threads)
    public Transformer getTransformer() throws XXMLException;


}
