package com.votingcentral.util.xml;

import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXSource;

import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;

/**
 * 
 * To change this template use File | Settings | File Templates.
 */
public interface IXQueryProcessor {

    String getKeyForXQuery(); // for later referring

    void recompileXQuery(Reader xquery) throws XXMLException;

    void recompileXQuery(Reader xquery, URIResolver er) throws XXMLException;

    // variety of utility methods

    // DOM/SAX/Reader --> DOM
    public Node process(Reader xml) throws XXMLException;

    public Node process(Reader xml, EntityResolver er) throws XXMLException;

    public Node process(Node input) throws XXMLException;

    public Node process(Node input, Map parameters) throws XXMLException;

    public Node process(Node input, Map parameters, Node outputnode) throws XXMLException;

    public Node process(Reader xml, EntityResolver er, Map parameters) throws XXMLException;

    public Node process(Reader xml, EntityResolver er, Map parameters, Node outputnode) throws XXMLException;

    public Node process(SAXSource src, Map parameters) throws XXMLException;

    public Node process(SAXSource src, Map parameters, Node outputnode) throws XXMLException;

    // Source --> Result
    public void process(Source src, Result res) throws XXMLException;

    public void process(Source src, Result res, Map parameters) throws XXMLException;


    // Reader --> Writer
    public void process(Reader xml, Writer wr) throws XXMLException;

    public void process(Reader xml, EntityResolver er, Writer wr) throws XXMLException;

    public void process(Reader xml, EntityResolver er, Map parameters, Writer wr) throws XXMLException;

    // Reader -> Content Handler of sax
    public void process(Reader xml, EntityResolver er, Map parameters, ContentHandler ch) throws XXMLException;


}
