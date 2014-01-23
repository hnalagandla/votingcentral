package com.votingcentral.util.xml;

import org.w3c.dom.Node;
import org.xml.sax.*;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.SAXParser;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Iterator;

/**
 * Date: Apr 22, 2004
 * Time: 10:13:17 PM
 */
public class TraxTransformer implements IXmlTransformer {
    String key;
    Templates templates = null;

    public boolean isValid() {
        return templates != null;
    }

    public TraxTransformer(String key) {
        this.key = key;
    }

    public Node transform(Reader xml) throws XXMLException {
        return transform(xml, (EntityResolver) null);
    }

    public Node transform(Reader xml, EntityResolver er) throws XXMLException {
        return transform(xml, er, (Map) null);
    }

    public Node transform(Node input) throws XXMLException {
        return transform(input, (Map) null);
    }

    // DOM -> DOM transformation
    public Node transform(Node input, Map parameters) throws XXMLException {
        return transform(input, parameters, null);
    }

    public void transform(Source src, Result res) throws XXMLException {
        transform(src, res, null);
    }

    public Transformer getTransformer() throws XXMLException {
        try {
            return templates.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new XXMLException("Transformer configuration exception creating new tfmr "
                    + " ! message : " + e.getMessage(), e);
        }
    }

    public void transform(Source src, Result res, Map parameters) throws XXMLException {
        XslErrorListener xel = new XslErrorListener();
        try {
            Transformer tfmr = templates.newTransformer();
            tfmr.setErrorListener(xel);
            if (parameters != null) {
                Iterator iter = parameters.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String key = (String) entry.getKey();
                    Object value = (Object) entry.getValue();
                    tfmr.setParameter(key, value);
                }
            }
            tfmr.transform(src, res);
            if (xel.getError() != null)
                throw xel.getError();
        } catch (TransformerConfigurationException e) {
            if (xel.getError() != null)
                throw xel.getError();
            else
                throw new XXMLException("Transformer configuration exception executing xsl trans of "
                        + " ! message : " + e.getMessage(), e);
        } catch (TransformerException e) {
            if (xel.getError() != null)
                throw xel.getError();
            else
                throw new XXMLException("Transformer exception executing xsl trans of "
                        + " ! message : " + e.getMessage(), e);
        }

    }

    // DOM -> DOM transformation
    public Node transform(Node input, Map parameters, Node outputnode) throws XXMLException {
        Result res;
        if (outputnode != null)
            res = new DOMResult(outputnode);
        else
            res = new DOMResult();
        Source src = new DOMSource(input);
        transform(src, res, parameters);
        return ((DOMResult) res).getNode();
    }

    // String reader -> DOM transformation
    public Node transform(Reader xml, EntityResolver er, Map parameters) throws XXMLException {
        return transform(xml, er, parameters, (Node) null);
    }

    // String reader -> DOM transformation
    public Node transform(Reader xml, EntityResolver er, Map parameters, Node outputnode) throws XXMLException {

        SAXParser sxp = XmlUtil.getSaxParser(true);
        Source src = null;
        try {
            if (er != null)
                sxp.getXMLReader().setEntityResolver(er);
            src = new SAXSource(sxp.getXMLReader(), new InputSource(xml));
        } catch (SAXException e) {
            throw new XXMLException("SAXException executing xsl trans of "
                    + " ! message : " + e.getMessage(), e);
        }
        Result res;
        if (outputnode != null)
            res = new DOMResult(outputnode);
        else
            res = new DOMResult();
        transform(src, res, parameters);
        return ((DOMResult) res).getNode();
    }

    // SAX Source -> DOM transformation
    public Node transform(SAXSource src, Map parameters) throws XXMLException {
        return transform(src, parameters, null);
    }


    public Node transform(SAXSource src, Map parameters, Node outputnode) throws XXMLException {
        Result res;
        if (outputnode != null)
            res = new DOMResult(outputnode);
        else
            res = new DOMResult();
        transform(src, res);
        return ((DOMResult) res).getNode();
    }

    // String reader -> String Writer
    public void transform(Reader xml, Writer wr) throws XXMLException {
        transform(xml, null, wr);
    }

    // String reader -> String Writer
    public void transform(Reader xml, EntityResolver er, Writer wr) throws XXMLException {
        transform(xml, er, null, wr);
    }

    // String reader -> String Writer
    public void transform(Reader xml, EntityResolver er, Map parameters, Writer wr) throws XXMLException {
        SAXParser sxp = XmlUtil.getSaxParser(true);
        Source src = null;
        try {
            if (er != null)
                sxp.getXMLReader().setEntityResolver(er);
            src = new SAXSource(sxp.getXMLReader(), new InputSource(xml));
        } catch (SAXException e) {
            throw new XXMLException("SAXException executing xsl trans of "
                    + " ! message : " + e.getMessage(), e);
        }
        Result res;
        if (wr != null)
            res = new StreamResult(wr);
        else
            res = new StreamResult();
        transform(src, res, parameters);
    }

    public void transform(Node nd, EntityResolver er, Map parameters, Writer wr) throws XXMLException {
        SAXParser sxp = XmlUtil.getSaxParser(true);
        Source src = null;
        try {
            if (er != null)
                sxp.getXMLReader().setEntityResolver(er);
            src = new DOMSource(nd);
        } catch (SAXException e) {
            throw new XXMLException("SAXException executing xsl trans of "
                    + " ! message : " + e.getMessage(), e);
        }
        Result res;
        if (wr != null)
            res = new StreamResult(wr);
        else
            res = new StreamResult();
        transform(src, res, parameters);
    }

    // String reader -> SAX handler transformation
    public void transform(Reader xml, EntityResolver er, Map parameters, ContentHandler ch) throws XXMLException {
        SAXParser sxp = XmlUtil.getSaxParser(true);
        Source src = null;
        try {
            if (er != null)
                sxp.getXMLReader().setEntityResolver(er);
            src = new SAXSource(sxp.getXMLReader(), new InputSource(xml));
        } catch (SAXException e) {
            throw new XXMLException("SAXException executing xsl trans of "
                    + " ! message : " + e.getMessage(), e);
        }
        Result res;
        if (ch != null)
            res = new SAXResult(ch);
        else
            res = new SAXResult();
        transform(src, res, parameters);
    }

    public String getKeyForXsl() // for later referring
    {
        return key;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void recompileXsl(Reader xsl) throws XXMLException {
        recompileXsl(xsl, null);
    }

    private class XslErrorListener implements ErrorListener {
        XXMLException e = null;

        public XXMLException getError() {
            return e;
        }

        public void error(TransformerException exception) throws TransformerException {
            if (e == null)
                e = new XXMLException("Error in compilation of xsl! " + exception.getMessage(),
                        exception);
        }

        public void fatalError(TransformerException exception) throws TransformerException {
            if (e == null)
                e = new XXMLException("Error in compilation of xsl! " + exception.getMessage(),
                        exception);
        }

        public void warning(TransformerException exception) throws TransformerException {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    public synchronized void recompileXsl(Reader xsl, URIResolver er) throws XXMLException {
        TransformerFactory factory =
                TransformerFactory.newInstance();
        if (er != null)
            factory.setURIResolver(er);
        XslErrorListener xel = new XslErrorListener();
        factory.setErrorListener(xel);
        Templates templs;

        try {
            templs = factory.newTemplates
                    (new StreamSource(xsl));
            if (xel.getError() != null)
                throw xel.getError();
        } catch (TransformerConfigurationException e) {
            if (xel.getError() != null)
                throw xel.getError();
            else
                throw new XXMLException("Transformer configuration exception compiling xsl of "
                        + " ! message : " + e.getMessage(), e);
        }
        templates = templs;
    }

}
