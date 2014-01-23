package com.votingcentral.util.xml.junit;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.votingcentral.util.xml.XmlUtil;
import com.votingcentral.util.xml.IXmlTransformer;
import com.votingcentral.util.xml.IXmlTransformerFactory;

import javax.xml.transform.URIResolver;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import org.w3c.dom.Node;

/**
 * Created by IntelliJ IDEA.
 * Author: Vijay Eranti
 * Date: Apr 23, 2004
 * Time: 9:53:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class XmlTransformerUnitTest extends TestCase {
    static {
        System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
    }
    public void testTransormRequest() {
        String testxsl = "<?xml version=\"1.0\"?><xsl:stylesheet version = '1.0' \n" +
                "     xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>\n" +
                "<xsl:template match=\"/\"> \n" +
                "     <h1> \n" +
                "          <xsl:value-of select=\"//title\"/> \n" +
                "     </h1> \n" +
                "     <h2> \n" +
                "          <xsl:value-of select=\"//author\"/> \n" +
                "     </h2> \n" +
                "</xsl:template>\n" +
                "</xsl:stylesheet> ";
        String testxml = "<?xml version=\"1.0\"?><source>\n" +
                "\n" +
                "<title>XSL</title> \n" +
                "<author>John Smith</author> \n" +
                "\n" +
                "</source>";
        try {
            IXmlTransformerFactory xtf = XmlUtil.getXmlTransformerFactory();
            IXmlTransformer xt = xtf.getCompiledXsl(new StringReader(testxsl));
            StringWriter res = new StringWriter();
            xt.transform(new StringReader(testxml), res);
            System.out.println("Result : " + res.toString());
            assertEquals(res.toString(), "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    //System.getProperty("line.separator") +
                    "<h1>XSL</h1><h2>John Smith</h2>");

        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception");
        }
    }

    public void testTransormRequest2() {
        String testxsl = "<xsl:stylesheet\n" +
                "  version=\"1.0\"\n" +
                "    xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n" +
                "      xmlns:date=\"http://www.jclark.com/xt/java/java.util.Date\">\n" +
                "\n" +
                "      <xsl:template match=\"/\">\n" +
                "        <html>\n" +
                "            <xsl:if test=\"function-available('date:to-string') and function-available('date:new')\">\n" +
                "                  <p><xsl:value-of select=\"date:to-string(date:new())\"/></p>\n" +
                "                      </xsl:if>\n" +
                "                        </html>\n" +
                "                        </xsl:template>\n" +
                "\n" +
                "                        </xsl:stylesheet>";
        String testxml = "<?xml version=\"1.0\"?><source>\n" +
                "\n" +
                "<title>XSL</title> \n" +
                "<author>John Smith</author> \n" +
                "\n" +
                "</source>";
        try {
            IXmlTransformerFactory xtf = XmlUtil.getXmlTransformerFactory();
            IXmlTransformer xt = xtf.getCompiledXsl(new StringReader(testxsl));
            StringWriter res = new StringWriter();
            xt.transform(new StringReader(testxml), res);
            System.out.println("Result : " + res.toString());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception");
        }
    }
        public void testTransormRequest22() {
        String testxsl = "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
                "        <xsl:output method=\"xml\" indent=\"no\"/>\n" +
                "    <xsl:variable name=\"trigger_id\"><xsl:value-of \n" +
                "\t\t\tselect=\"Notify/REQUEST_INFO/FeedID\"/>_<xsl:value-of \n" +
                "\t\t\tselect=\"Notify/FeedData/AlertId\"/>_<xsl:value-of \n" +
                "\t\t\tselect=\"Notify/REQUEST_INFO/LocalHostIP\"/>_<xsl:value-of \n" +
                "            select=\"Notify/REQUEST_INFO/Timestamp\"/>_<xsl:value-of \n" +
                "            select=\"Notify/REQUEST_INFO/RandomNumber\"/></xsl:variable>\n" +
                "        <xsl:template match=\"/\">\n" +
                "                <EVENT>\n" +
                "                        <MATCHING_SECTION>\n" +
                "                                <EVENT>\n" +
                "                                        <MATCHING_DATA>\n" +
                "                                        <topic_name><xsl:value-of select=\"Notify/FeedData/AlertId\"/></topic_name>\n" +
                "                                        </MATCHING_DATA>\n" +
                "                                </EVENT>\n" +
                "                        </MATCHING_SECTION>\n" +
                "                        <CONTENT_SECTION>\n" +
                "                                <content><xsl:copy-of select=\"Notify/DisplayContent\"/></content>\n" +
                "                        </CONTENT_SECTION>\n" +
                "                        <CONTROL_DATA>\n" +
                "                                <topic_name><xsl:value-of select=\"Notify/FeedData/AlertId\"/></topic_name>\n" +
                "                                <event_id><xsl:value-of select=\"$trigger_id\"/></event_id>\n" +
                "                                <saq_tran_key><xsl:value-of select=\"$trigger_id\"/></saq_tran_key>\n" +
                "                        </CONTROL_DATA>\n" +
                "                </EVENT>\n" +
                "        </xsl:template>\n" +
                "</xsl:stylesheet>\n" +
                " ";
        String testxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Notify><FeedData><AlertId>\n" +
                "\t\t\t\n" +
                "\t\t\t\ta28c8bba-c9e8-4703-9e2f-fdaad8db4376 </AlertId><AlertValidationToken>\n" +
                "\t\t\t\tF09B7C4DE1D81C35F129DDC0C4A01A151BB496CE745E2CE89EA2FE8597DB371</AlertValidationToken>\n" +
                "\t\t\t</FeedData><DisplayContent><BasicContent><Summary>\n" +
                "\t\t\t\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t\tString</Summary><ContentText>\n" +
                "\t\t\t\t\tString</ContentText><FullStory>\n" +
                "\t\t\t\t\tString</FullStory><ImageURL>\n" +
                "\t\t\t\t\thttp://www.altova.com</ImageURL><ImageClickThruURL>\n" +
                "\t\t\t\t\thttp://www.altova.com</ImageClickThruURL><EmailSubject>\n" +
                "\t\t\t\t\thttp://www.altova.com</EmailSubject>\n" +
                "\t\t\t\t</BasicContent>\n" +
                "\t\t\t</DisplayContent>\n" +
                "\t\t<REQUEST_INFO><RandomNumber>4213763139608510464</RandomNumber><RemoteAddress>127.0.0.1</RemoteAddress><LocalHostIP>192.168.2.73</LocalHostIP><EnvID>DEV</EnvID><FeedDefID>a28c9824-c9e8-4703-9e2f-fdaad8db4376</FeedDefID><Timestamp>1096589921335</Timestamp><FeedID>EDITORIAL</FeedID><LocalHostName>CPQ21871143968</LocalHostName></REQUEST_INFO></Notify>";
        try {
            IXmlTransformerFactory xtf = XmlUtil.getXmlTransformerFactory();
            IXmlTransformer xt = xtf.getCompiledXsl(new StringReader(testxsl));
            StringWriter res = new StringWriter();
            xt.transform(new StringReader(testxml), res);
            System.out.println("Result : " + res.toString());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception");
        }
    }
    class SimpleResolver extends HashMap implements URIResolver {
        public Source resolve(String href, String base) throws TransformerException {
            String ret = (String) get(href);
            if (ret == null)
                throw new TransformerException("Invalid transformer include !");
            return new StreamSource(new StringReader(ret));
        }
    }

    public void testTransormRequestWithInclude() {
        String testxslinclude1 = "<?xml version=\"1.0\"?><xsl:stylesheet version = '1.0' \n" +
                "     xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>\n" +
                "<xsl:variable name=\"id2\">Stylesheet 1(id2.xsl)</xsl:variable> \n" +
                "<xsl:variable name=\"t\">Variable t from id2.xsl</xsl:variable> \n" +
                "</xsl:stylesheet> ";
        String testxslinclude2 = "<?xml version=\"1.0\"?><xsl:stylesheet version = '1.0' \n" +
                "     xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>\n" +
                "<xsl:variable name=\"id3\">Stylesheet 2(id3.xsl)</xsl:variable> \n" +
                "<xsl:variable name=\"t\">Variable t from id3.xsl</xsl:variable> \n" +
                "</xsl:stylesheet> ";
        String mainxsl = "<?xml version=\"1.0\"?><xsl:stylesheet version = '1.0' \n" +
                "     xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>\n" +
                "\n" +
                "<xsl:import href=\"id3.xsl\"/> \n" +
                "<xsl:include href=\"id2.xsl\"/> \n" +
                "<xsl:template match=\"/\"> \n" +
                "     <P> \n" +
                "          <xsl:value-of select=\"$id2\"/> \n" +
                "     </P> \n" +
                "     <P> \n" +
                "          <xsl:value-of select=\"$id3\"/> \n" +
                "     </P> \n" +
                "     <P> \n" +
                "          <xsl:value-of select=\"$t\"/> \n" +
                "     </P> \n" +
                "</xsl:template>\n" +
                "\n" +
                "\n" +
                "</xsl:stylesheet> ";
        SimpleResolver sr = new SimpleResolver();
        sr.put("id3.xsl", testxslinclude1);
        sr.put("id2.xsl", testxslinclude2);
        String testxml = "<?xml version=\"1.0\"?><source>\n" +
                "\n" +
                "<title>XSL</title> \n" +
                "<author>John Smith</author> \n" +
                "\n" +
                "</source>";
        try {
            IXmlTransformerFactory xtf = XmlUtil.getXmlTransformerFactory();
            IXmlTransformer xt = xtf.getCompiledXsl(new StringReader(mainxsl), sr);
            StringWriter res = new StringWriter();
            xt.transform(new StringReader(testxml), res);
            Node nd = xt.transform(new StringReader(testxml));
            nd.getFirstChild().appendChild(nd.getOwnerDocument().createElement("tst"));
            System.out.println("Result : " + res.toString());
            assertEquals(res.toString(), "<?xml version=\"1.0\" encoding=\"UTF-8\"?><P>Stylesheet 1(id2.xsl)</P><P>Stylesheet 2(id3.xsl)</P><P>Variable t from id3.xsl</P>");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception");
        }
    }

    public static void main(String[] args) {
        new junit.textui.TestRunner().doRun(new TestSuite(XmlTransformerUnitTest.class));
    }
}
