package com.votingcentral.util.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 */
class XmlParser implements IXmlParser {
    private static class TLDCache {
        DocumentBuilder db;
        DocumentBuilder dbNoNamespaceAware;
        SAXParser saxParser;
        SAXParser saxParserNoNamespaceAware;
    }

    private class EmptyResolver implements EntityResolver {
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            throw new SAXException("Unable to resolve " + publicId + " : " + systemId);
        }
    }

    static ThreadLocal tlData = new ThreadLocal();

    private static SAXParser createSaxParser(boolean namespaceaware) throws XXMLException {
        SAXParserFactory dbf = null;
        dbf = SAXParserFactory.newInstance();
        dbf.setValidating(false);
        dbf.setNamespaceAware(namespaceaware);
        try {
            return dbf.newSAXParser();
        } catch (ParserConfigurationException e) {
            throw new com.votingcentral.util.xml.XXMLException("Parser configuration exception while creating xml parser", e);
        } catch (SAXException e) {
            throw new com.votingcentral.util.xml.XXMLException("SAX Parser Exception while creating xml parser", e);
        }

    }

    private static DocumentBuilder createDomParser(boolean namespaceaware) throws XXMLException {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(namespaceaware);
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db;
        } catch (ParserConfigurationException e) {
            throw new XXMLException("Parser configuration exception while creating dom parser", e);
        } catch (Exception e) {
            throw new XXMLException("Exception while creating dom parser", e);
        }
    }

    public static SAXParser getSaxParser(boolean namespaceaware) throws XXMLException {
        TLDCache tldc = (TLDCache) tlData.get();
        if (tldc == null) {
            tldc = new TLDCache();
            tlData.set(tldc);
        }
        if (!namespaceaware) {
            if (tldc.saxParser == null)
                tldc.saxParser = createSaxParser(namespaceaware);
            return tldc.saxParser;
        } else {
            if (tldc.saxParserNoNamespaceAware == null)
                tldc.saxParserNoNamespaceAware = createSaxParser(namespaceaware);
            return tldc.saxParserNoNamespaceAware;
        }
    }

    public static DocumentBuilder getDomParser(boolean namespaceaware) throws XXMLException {
        TLDCache tldc = (TLDCache) tlData.get();
        if (tldc == null) {
            tldc = new TLDCache();
            tlData.set(tldc);
        }
        if (!namespaceaware) {
            if (tldc.db == null)
                tldc.db = createDomParser(namespaceaware);
            return tldc.db;
        } else {
            if (tldc.dbNoNamespaceAware == null)
                tldc.dbNoNamespaceAware = createDomParser(namespaceaware);
            return tldc.dbNoNamespaceAware;
        }
    }

    public Document createNewDocument() throws XXMLException {
        return getDomParser(true).newDocument();
    }

    public Document parseXml(Reader data) throws XXMLException {
        return parseXml(data, (EntityResolver) null);
    }

    public Document parseXml(Reader data, EntityResolver er) throws XXMLException {
        return parseXml(data, er, null);
    }

    public Document parseXml(Reader data, IXmlValidator schema) throws XXMLException {
        return parseXml(data, null, schema);
    }

    public Document parseXml(Reader data, EntityResolver er, IXmlValidator schema) throws XXMLException {
        try {
            DocumentBuilder db = null;
            if (schema == null)
                db = getDomParser(true);
            else {
                DocumentBuilderFactory dbf = null;
                if (schema == null)
                    dbf = DocumentBuilderFactory.newInstance();
                else
                    dbf = schema.getDocumentBuilderFactory();
                dbf.setNamespaceAware(true);
                db = dbf.newDocumentBuilder();
            }
            if (er != null)
                db.setEntityResolver(er);
            else
                db.setEntityResolver(null);
            Document doc = db.parse(new InputSource(data));
            return doc;
        } catch (IOException e) {
            throw new XXMLException("IO Exception while parsing xml", e);
        } catch (ParserConfigurationException e) {
            throw new XXMLException("Parser configuration exception while parsing xml", e);
        } catch (SAXException e) {
            throw new XXMLException("SAX Parser Exception while parsing xml", e);
        } catch (Exception e) {
            throw new XXMLException("Exception while sax parsing xml", e);
        }
    }

    public void parseXmlUsingSax(Reader reader, DefaultHandler contentHandler) throws XXMLException {
        parseXmlUsingSax(reader, (EntityResolver) null, contentHandler);
    }

    public void parseXmlUsingSax(Reader reader, EntityResolver entityResolver, DefaultHandler contentHandler) throws XXMLException {
        parseXmlUsingSax(reader, entityResolver, null, contentHandler);
    }

    public void parseXmlUsingSax(Reader reader, IXmlValidator iXmlValidator, DefaultHandler contentHandler) throws XXMLException {
        parseXmlUsingSax(reader, null, iXmlValidator, contentHandler);
    }

    public void parseXmlUsingSax(Reader data, EntityResolver er, IXmlValidator schema, DefaultHandler contentHandler) throws XXMLException {
        try {
            SAXParser db = null;
            if (schema == null) {
                db = getSaxParser(true);
                if (db.getXMLReader().getEntityResolver() != er) {
                    if (er != null)
                        db.getXMLReader().setEntityResolver(er);
                    else
                        db = createSaxParser(true);
                }
            } else {
                SAXParserFactory dbf = null;
                if (schema == null)
                    dbf = SAXParserFactory.newInstance();
                else {
                    dbf = schema.getSAXParserFactory();
                    dbf.setValidating(true);
                }
                dbf.setNamespaceAware(true);
                db = dbf.newSAXParser();
                if (er != null)
                    db.getXMLReader().setEntityResolver(er);
            }

            db.parse(new InputSource(data), contentHandler);
        } catch (ParserConfigurationException e) {
            throw new XXMLException("Parser configuration exception while parsing xml", e);
        } catch (SAXException e) {
            throw new XXMLException("SAX Parser Exception while parsing xml", e);
        } catch (Exception e) {
            throw new XXMLException("Exception while sax parsing xml", e);
        }

    }

    private static ThreadLocal tFactoryTLD = new ThreadLocal();
    private TransformerFactory getTransformerFactory () {
        TransformerFactory tf = (TransformerFactory) tFactoryTLD.get();
        if (tf == null)
            tFactoryTLD.set(tf = TransformerFactory.newInstance());
        return tf;
    }

    public void xmlToStream(Node node, Writer writer) throws XXMLException {
        xmlToStream(node, writer, false);
    }
    public void xmlToStream(Node node, Writer writer, boolean indent) throws XXMLException {
        try {

            Transformer transformer =
                    getTransformerFactory().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,  indent ? "yes" : "no");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,  "yes");
            transformer.setOutputProperty(OutputKeys.METHOD,  "xml");

            DOMSource origDocSource = new DOMSource(node);
            StreamResult origResult = new StreamResult(writer);
            transformer.transform(origDocSource, origResult);

        } catch (TransformerConfigurationException e) {
            throw new XXMLException("Transformer Configuration exception while serializing node ", e);
        } catch (TransformerException e) {
            throw new XXMLException("Transformer exception while serializing node ", e);
        }

    }

    public String xmlToString(Node node) throws XXMLException {
        StringWriter sw = new StringWriter(4096);
        xmlToStream(node, sw, false);
        return sw.toString();
    }
    public String xmlToStringWithIndent(Node node) throws XXMLException {
        StringWriter sw = new StringWriter(4096);
        xmlToStream(node, sw, true);
        return sw.toString();
    }

    public ISaxDomBuilder getDomBuilderForSaxEvents(Node node) {
        return new SAX2DOMHandler(node);
    }

    public ISaxEventProducer getSaxEventProducerForDom(Node node) {
        return new DOMEventProducer(node);
    }

    class NamespaceStripper implements ContentHandler {
        Node target;
        ISaxDomBuilder dombuilder;
        NamespaceStripper(Document newdoc) {
            target = newdoc;
            dombuilder = getDomBuilderForSaxEvents(target);
        }
        public void endDocument() throws SAXException {
            dombuilder.endDocument();
        }

        public void startDocument() throws SAXException {
            dombuilder.startDocument();
        }

        public void characters(char ch[], int start, int length) throws SAXException {
            dombuilder.characters(ch, start, length);
        }

        public void ignorableWhitespace(char ch[], int start, int length) throws SAXException {
            dombuilder.ignorableWhitespace(ch, start, length);
        }

        public void endPrefixMapping(String prefix) throws SAXException {
        }

        public void skippedEntity(String name) throws SAXException {
            dombuilder.skippedEntity(name);
        }

        public void setDocumentLocator(Locator locator) {
            dombuilder.setDocumentLocator(locator);
        }

        public void processingInstruction(String target, String data) throws SAXException {
            dombuilder.processingInstruction(target, data);
        }

        public void startPrefixMapping(String prefix, String uri) throws SAXException {

        }

        private String getLocalName(String localName, String qName) {
            String lName = localName;
            if (lName.equals("")) {
                int colon= qName.indexOf(':');
                if (colon  >= 0)
                    lName = qName.substring(colon+1);
                else
                    lName = qName;
            }
            return lName;
        }
        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            String lName = getLocalName(localName, qName);
            dombuilder.endElement("", "", lName);
        }

        public void startElement(String namespaceURI, String localName, String qName, Attributes satts) throws SAXException {
            AttributesImpl atts = new AttributesImpl();
            for (int i = 0; i < satts.getLength(); i++) {
                if (satts.getQName(i).startsWith("xmlns:") || satts.getQName(i).equals("xmlns"))
                    continue;
                String lName = getLocalName(satts.getLocalName(i), satts.getQName(i));
                atts.addAttribute("", "", lName, "CDATA", satts.getValue(i));
            }
            String lName = getLocalName(localName, qName);
            dombuilder.startElement("", "", lName, atts);
        }

    }
    public Node  cloneNodeWithoutNamespaces(Node node) throws XXMLException {
        ISaxEventProducer sep = getSaxEventProducerForDom(node);
        Document doc = createNewDocument();
        sep.setDocumentHandler(new NamespaceStripper(doc));
        try {
            sep.start();
        } catch (SAXException e) {
             new XXMLException("SAX exception while stripping ns node ", e);
        }
        if (node instanceof Document)
            return doc;
        else
            return doc.getDocumentElement();
    }
}
