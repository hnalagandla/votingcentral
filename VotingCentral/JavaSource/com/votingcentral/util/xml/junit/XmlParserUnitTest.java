package com.votingcentral.util.xml.junit;

import java.io.StringReader;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

import com.votingcentral.util.xml.ISaxDomBuilder;
import com.votingcentral.util.xml.IXmlParser;
import com.votingcentral.util.xml.XmlUtil;


/**
 * Created by IntelliJ IDEA.
 * User: eranti
 * Date: Feb 27, 2004
 * Time: 4:27:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class XmlParserUnitTest extends TestCase {

    public void testParsingRequest2() {
        String testcase1 = "<?xml version=\"1.0\"?>"
                + "<address xmlns=\"http://foo.mcom.com\"><name type=\"personal\">vijay</name><phone>234234342</phone></address>";

        try {
            IXmlParser pf = XmlUtil.getXmlParserFactory();
            Document doc = pf.parseXml(new StringReader(testcase1));
            Node n = pf.cloneNodeWithoutNamespaces(doc.getDocumentElement());
            assertNotNull(doc);
            System.out.println(pf.xmlToString(n));
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }
    public void testParsingRequest1() {
        String testcase1 = "<?xml version=\"1.0\"?>"
                + "<address><name type=\"personal\">vijay</name><phone>234234342</phone></address>";

        try {
            IXmlParser pf = XmlUtil.getXmlParserFactory();
            Document doc = pf.parseXml(new StringReader(testcase1));
            assertNotNull(doc);
            String parseddoc = pf.xmlToString(doc.getDocumentElement());
            assertNotNull(parseddoc);
            assertEquals(parseddoc,
                    "<address><name type=\"personal\">vijay</name><phone>234234342" +
                    "</phone></address>");
            //System.out.println(parseddoc);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }
    public void testCloneWithNoNamespaces() {
        String testcase1 = "<?xml version=\"1.0\"?>"
                + "<m:address xmlns:m=\"http://foo\"><m:name m:type=\"personal\">vijay</m:name><m:phone>234234342</m:phone></m:address>";

        try {
            IXmlParser pf = XmlUtil.getXmlParserFactory();
            Document doc = pf.parseXml(new StringReader(testcase1));
            assertNotNull(doc);
            Node nd = pf.cloneNodeWithoutNamespaces(doc);
            String parseddoc = pf.xmlToString(nd);
            assertNotNull(parseddoc);
            System.out.println(parseddoc);
            Node nd2 = pf.cloneNodeWithoutNamespaces(doc.getDocumentElement().getFirstChild());
            String parseddoc2 = pf.xmlToStringWithIndent(nd2);
            assertNotNull(parseddoc2);
            System.out.println(parseddoc2);

            assertEquals(parseddoc, "<address><name type=\"personal\">vijay</name><phone>234234342" +
                    "</phone></address>");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception");
        }
    }

    public void testParsingRequestSax1() {
        String testcase1 = "<?xml version=\"1.0\"?>"
                + "<address><name type=\"personal\">vijay</name><phone>234234342</phone></address>";

        try {
            IXmlParser pf = XmlUtil.getXmlParserFactory();
            pf.parseXmlUsingSax(new StringReader(testcase1), new DefaultHandler());
        } catch (Exception e) {
            fail("Unexpected exception");
        }
        String testcase3 = "<?xml version=\"1.0\"?>"
                + "<address><name type=\"personal\">vijay<name><phone>234234342</phone></address>";

        try {
            IXmlParser pf = XmlUtil.getXmlParserFactory();
            pf.parseXmlUsingSax(new StringReader(testcase3), new DefaultHandler());
            fail("Expected to fail");
        } catch (Exception e) {

        }
    }

    public void testSaxEventProducer() {
        String testcase1 = "<?xml version=\"1.0\"?>"
                + "<address><name type=\"personal\">vijay</name><phone>234234342</phone></address>";


        try {
            IXmlParser pf = XmlUtil.getXmlParserFactory();
            Document doc = pf.createNewDocument();
            Node root = doc.createElement("address");
            doc.appendChild(root);

            ISaxDomBuilder dombuilder = pf.getDomBuilderForSaxEvents(root);
            AttributesImpl attrs = new AttributesImpl();
            attrs.addAttribute("", "", "validfor", "CDATA", "usa");
            dombuilder.startElement("", "", "name", (Attributes) attrs);
            dombuilder.characters(new char[]{'v', 'i', 'j', 'a', 'y'}, 0, 5);
            dombuilder.endElement("", "", "name");

            String parseddoc = pf.xmlToString(root);
            assertNotNull(parseddoc);
            assertEquals(parseddoc, "<address><name validfor=\"usa\">vijay</name></address>");
            //System.out.println(parseddoc);
        } catch (Exception e) {
            fail("Unexpected exception");
        }
    }

    public static void main(String[] args) {
        new junit.textui.TestRunner().doRun(new TestSuite(XmlParserUnitTest.class));
    }


}
