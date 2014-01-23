package com.votingcentral.util.xml;

import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import net.sf.saxon.Configuration;
import net.sf.saxon.om.DocumentInfo;
import net.sf.saxon.om.NamePool;
import net.sf.saxon.om.SequenceIterator;
import net.sf.saxon.query.DynamicQueryContext;
import net.sf.saxon.query.QueryProcessor;
import net.sf.saxon.query.QueryResult;
import net.sf.saxon.query.StaticQueryContext;
import net.sf.saxon.query.XQueryExpression;
import net.sf.saxon.xpath.XPathException;

import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Date: Apr 29, 2004
 * Time: 9:15:50 AM
 */
public class SaxonXQueryProcessor implements IXQueryProcessor {
    String key;
    Templates templates = null;

    public boolean isValid() {
        return templates != null;
    }

    public SaxonXQueryProcessor(String key) {
        this.key = key;
    }

    public Node process(Reader xml) throws XXMLException {
        return process(xml, (EntityResolver) null);
    }

    public Node process(Reader xml, EntityResolver er) throws XXMLException {
        return process(xml, er, (Map) null);
    }

    public Node process(Node input) throws XXMLException {
        return process(input, (Map) null);
    }

    // DOM -> DOM processation
    public Node process(Node input, Map parameters) throws XXMLException {
        return process(input, parameters, null);
    }

    public void process(Source src, Result res) throws XXMLException {
        process(src, res, null);
    }

    public void process(Source src, Result res, Map parameters) throws XXMLException {

        DynamicQueryContext dynamicEnv = new DynamicQueryContext();
        DocumentInfo doc = null;


        try {
            doc = xquery.buildDocument(src);
            dynamicEnv.setContextNode(doc);
            // The next line actually executes the query
            SequenceIterator results = exp.iterator(dynamicEnv);
            DocumentInfo resultDoc = QueryResult.wrap(results, NamePool.getDefaultNamePool());
            Properties outputProps = new Properties();
            outputProps.setProperty(OutputKeys.INDENT, "yes");

            QueryResult.serialize(resultDoc,
                    res,
                    outputProps);

        } catch (XPathException err) {
            int line = -1;
            if (err.getLocator() != null) {
                line = err.getLocator().getLineNumber();
            }
            if (line == -1)
                throw new XXMLException("Failed to process : " + err.getMessage(), err);
            else {
                throw new XXMLException("Static error at line " + line + ":" + err.getMessage(), err);
            }
        } catch (Exception e) {
            throw new XXMLException("Error in compile of xquery. message : " + e.getMessage(), e);
        }


    }

    // DOM -> DOM processation
    public Node process(Node input, Map parameters, Node outputnode) throws XXMLException {
        Result res;
        if (outputnode != null)
            res = new DOMResult(outputnode);
        else
            res = new DOMResult();
        Source src = new DOMSource(input);
        process(src, res);
        return ((DOMResult) res).getNode();
    }

    // String reader -> DOM processation
    public Node process(Reader xml, EntityResolver er, Map parameters) throws XXMLException {
        return process(xml, er, parameters, (Node) null);
    }

    // String reader -> DOM processation
    public Node process(Reader xml, EntityResolver er, Map parameters, Node outputnode) throws XXMLException {

        SAXParser sxp = XmlUtil.getSaxParser(true);
        Source src = null;
        try {
            if (er != null)
                sxp.getXMLReader().setEntityResolver(er);
            src = new SAXSource(sxp.getXMLReader(), new InputSource(xml));
        } catch (SAXException e) {
            throw new XXMLException("SAXException executing xquery trans of "
                    + " ! message : " + e.getMessage(), e);
        }
        Result res;
        if (outputnode != null)
            res = new DOMResult(outputnode);
        else
            res = new DOMResult();
        process(src, res, parameters);
        return ((DOMResult) res).getNode();
    }

    // SAX Source -> DOM processation
    public Node process(SAXSource src, Map parameters) throws XXMLException {
        return process(src, parameters, null);
    }


    public Node process(SAXSource src, Map parameters, Node outputnode) throws XXMLException {
        Result res;
        if (outputnode != null)
            res = new DOMResult(outputnode);
        else
            res = new DOMResult();
        process(src, res);
        return ((DOMResult) res).getNode();
    }

    // String reader -> String Writer
    public void process(Reader xml, Writer wr) throws XXMLException {
        process(xml, null, wr);
    }

    // String reader -> String Writer
    public void process(Reader xml, EntityResolver er, Writer wr) throws XXMLException {
        process(xml, er, null, wr);
    }

    // String reader -> String Writer
    public void process(Reader xml, EntityResolver er, Map parameters, Writer wr) throws XXMLException {
        SAXParser sxp = XmlUtil.getSaxParser(true);
        Source src = null;
        try {
            if (er != null)
                sxp.getXMLReader().setEntityResolver(er);
            src = new SAXSource(sxp.getXMLReader(), new InputSource(xml));
        } catch (SAXException e) {
            throw new XXMLException("SAXException executing xquery trans of "
                    + " ! message : " + e.getMessage(), e);
        }
        Result res;
        if (wr != null)
            res = new StreamResult(wr);
        else
            res = new StreamResult();
        process(src, res, parameters);
    }

    // String reader -> SAX handler processation
    public void process(Reader xml, EntityResolver er, Map parameters, ContentHandler ch) throws XXMLException {
        SAXParser sxp = XmlUtil.getSaxParser(true);
        Source src = null;
        try {
            if (er != null)
                sxp.getXMLReader().setEntityResolver(er);
            src = new SAXSource(sxp.getXMLReader(), new InputSource(xml));
        } catch (SAXException e) {
            throw new XXMLException("SAXException executing xquery trans of "
                    + " ! message : " + e.getMessage(), e);
        }
        Result res;
        if (ch != null)
            res = new SAXResult(ch);
        else
            res = new SAXResult();
        process(src, res, parameters);
    }

    public String getKeyForXQuery() // for later referring
    {
        return key;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void recompileXQuery(Reader xquery) throws XXMLException {
        recompileXQuery(xquery, null);
    }

    private class XQueryErrorListener implements ErrorListener {
        XXMLException e = null;

        public XXMLException getError() {
            return e;
        }

        public void error(TransformerException exception) throws TransformerException {
            if (e == null)
                e = new XXMLException("Error in compilation of xquery! " + exception.getMessage(),
                        exception);
        }

        public void fatalError(TransformerException exception) throws TransformerException {
            if (e == null)
                e = new XXMLException("Error in compilation of xquery! " + exception.getMessage(),
                        exception);
        }

        public void warning(TransformerException exception) throws TransformerException {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    XQueryExpression exp = null;
    QueryProcessor xquery = null;

    public synchronized void recompileXQuery(Reader xqueryreader, URIResolver er) throws XXMLException {
        Configuration config = new Configuration();
        config.setHostLanguage(Configuration.XQUERY);
        StaticQueryContext staticEnv = new StaticQueryContext(config);
        if (er != null)
            config.setURIResolver(er);
        QueryProcessor xquery = new QueryProcessor(staticEnv);
        try {

            XQueryExpression exp = xquery.compileQuery(xqueryreader);
            this.exp = exp;
            this.xquery = xquery;
        } catch (XPathException err) {
            int line = -1;
            if (err.getLocator() != null) {
                line = err.getLocator().getLineNumber();
            }
            if (line == -1)
                throw new XXMLException("Failed to compile query: " + err.getMessage(), err);
            else {
                throw new XXMLException("Static error at line " + line + ":" + err.getMessage(), err);
            }

        } catch (Exception e) {
            throw new XXMLException("Error in compile of xquery. message : " + e.getMessage(), e);

        }
    }

}
