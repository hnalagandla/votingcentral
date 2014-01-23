package com.votingcentral.util.xml;

import java.io.Reader;
import java.io.Writer;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.helpers.DefaultHandler;




public interface IXmlParser {
    Document createNewDocument() throws XXMLException;

    Document parseXml(Reader data) throws XXMLException;

    Document parseXml(Reader data, EntityResolver er) throws XXMLException;

    Document parseXml(Reader data, IXmlValidator schema) throws XXMLException;

    Document parseXml(Reader data, EntityResolver er, IXmlValidator schema) throws XXMLException;

    void parseXmlUsingSax(Reader data, DefaultHandler ch) throws XXMLException;;

    void parseXmlUsingSax(Reader data, EntityResolver er, DefaultHandler ch) throws XXMLException;

    void parseXmlUsingSax(Reader data, IXmlValidator schema, DefaultHandler ch) throws XXMLException;

    void parseXmlUsingSax(Reader data, EntityResolver er, IXmlValidator schema, DefaultHandler ch)
            throws XXMLException;

    void xmlToStream(Node node, Writer writer) throws XXMLException;

    void xmlToStream(Node node, Writer writer, boolean indent) throws XXMLException;
    String xmlToString(Node node) throws XXMLException;

    String xmlToStringWithIndent(Node node) throws XXMLException;

    ISaxDomBuilder getDomBuilderForSaxEvents(Node node);

    ISaxEventProducer getSaxEventProducerForDom(Node node);

    Node cloneNodeWithoutNamespaces(Node node) throws XXMLException;
}
