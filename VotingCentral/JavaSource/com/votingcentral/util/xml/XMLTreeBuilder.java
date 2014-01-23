package com.votingcentral.util.xml;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;



public class XMLTreeBuilder extends DefaultHandler implements LexicalHandler {

    // Fields for SAX Parsing
    private XMLNode currentNode = null;
    private XMLNode rootNode = null;
    private String data = null;
    private boolean expectData = false;
    private boolean encounteredCDATA = false;
    private static Log log = LogFactory.getLog(XMLTreeBuilder.class);

    // Singleton instance of SAXParserPool
    static private final ObjectPool pool
            = new StackObjectPool(new SAXParserPoolFactory());

    /**
     * This method builds the Tree of XMLNodes for given xml and returns the
     * root node of the prepared tree
     */
    public static XMLNode buildXMLTree(String xml) throws XXMLException {

        XMLTreeBuilder _builder = new XMLTreeBuilder();
        SAXParser saxParser = null;

        try {
            saxParser = (SAXParser) pool.borrowObject();
        } catch (Exception e) {
            log.error("Failed to acquire saxParser", e);
            throw new XXMLException("Failed to acquire saxParser", e);
        }
        try {
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setProperty("http://xml.org/sax/properties/lexical-handler", _builder);
            saxParser.parse(new InputSource(new StringReader(xml)), _builder);
        } catch (Exception e) {
            log.error("Failed to parse xml", e);
            throw new XXMLException("Failed to parse xml", e);
        } finally {
            if (saxParser != null) {
                try {
                    pool.returnObject(saxParser);
                } catch (Exception e) {
                    log.error("Failed to release saxParser", e);
                }
            }
        }
        return _builder.getRootNode();
    }

    /**
     * Maily to be used for Pool monitoring
     * Returns the number of active objects in SaxParser pool
     */
    public static int getActivePoolSize() {
        return pool.getNumActive();
    }

    /**
     * Maily to be used for Pool monitoring
     * Returns the number of passive objects in SaxParser pool
     */
    public static int getIdlePoolSize() {
        return pool.getNumIdle();
    }

    public XMLNode getRootNode() {
        return rootNode;
    }

    // Methods to support SAX Parsing

    //
    // ContentHandler methods
    //

    /**
     * Start document.
     */
    public void startDocument() throws SAXException {
        rootNode = new XMLNode(XMLNode.ROOT, null);
        currentNode = rootNode;
    }

    /**
     * Start element.
     */
    public void startElement(String uri, String local, String name,
                             Attributes attrs) throws SAXException {

        // Make the New Node and add it to the current Parent
        XMLNode node = new XMLNode(name, null);
        try {
            currentNode.addChild(node);
        } catch (XXMLException e) {
            throw new SAXException(e);
        }
        currentNode = node;

        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                currentNode.addAttribute(attrs.getQName(i), attrs.getValue(i));
            }
        }
        expectData = true;
    }

    /**
     * Characters.
     */
    public void characters(char ch[], int start, int length)
            throws SAXException {

        if (data == null) data = "";
        data = data.concat(((new String(ch, start, length)).trim()));
    }

    /**
     * End element.
     */
    public void endElement(String uri, String local, String name) throws SAXException {

        // Check if you should have data
        if (expectData) {
            try {
                currentNode.setValue(data, encounteredCDATA);
            } catch (XXMLException e) {
                throw new SAXException(e);
            }
            data = null;
            encounteredCDATA = false;
        }
        if (currentNode != null) currentNode = currentNode.getParent();
        expectData = false;
    }

    // Methods From LexicalHandler Interface
    public void startDTD(String s, String s1, String s2) throws SAXException {
    }

    public void endDTD() throws SAXException {
    }

    public void startEntity(String s) throws SAXException {
    }

    public void endEntity(String s) throws SAXException {
    }

    public void startCDATA() throws SAXException {
        encounteredCDATA = true;
    }

    public void endCDATA() throws SAXException {
    }

    public void comment(char[] chars, int i, int i1) throws SAXException {
    }

}
