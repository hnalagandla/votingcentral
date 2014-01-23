package com.votingcentral.util.xml.junit;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.jaxen.SimpleNamespaceContext;
import com.votingcentral.util.xml.XmlUtil;
import com.votingcentral.util.xml.XXMLException;

import java.io.StringReader;
import java.util.List;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * Author: Vijay Eranti
 * Date: May 4, 2004
 * Time: 9:04:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class XPathApiUnitTest extends TestCase {
	
	public void testSimpleXPath() {
		String simpleXml =
			"<AAA> \n"
				+ "          <BBB/> \n"
				+ "          <CCC/> \n"
				+ "          <BBB/> \n"
				+ "          <DDD> \n"
				+ "               <BBB/> \n"
				+ "          </DDD> \n"
				+ "          <CCC> \n"
				+ "               <DDD> \n"
				+ "                    <BBB/> \n"
				+ "                    <BBB/> \n"
				+ "               </DDD> \n"
				+ "          </CCC> \n"
				+ "     </AAA> ";
		try {
			Node nd =
				XmlUtil.getXmlParserFactory().parseXml(
					new StringReader(simpleXml));
			Node selnd =
				(Node) XmlUtil.getXPathApi().selectSingleNode(
					((Document) nd).getDocumentElement(),
					"//BBB");
			assertEquals(selnd.getNodeName(), "BBB");
			selnd =
				(Node) XmlUtil.getXPathApi().selectSingleNode(
					((Document) nd).getDocumentElement(),
					"CCC[2]");
			assertEquals(selnd.getNodeName(), "CCC");
			selnd =
				(Node) XmlUtil.getXPathApi().selectSingleNode(selnd, "DDD/BBB");
			assertEquals(selnd.getNodeName(), "BBB");
			for (int ii = 0; ii < 10; ii++) {
				List lst = XmlUtil.getXPathApi().selectNodes(nd, "//BBB");
				assertEquals(lst.size(), 5);
				for (Iterator i = lst.iterator(); i.hasNext();) {
					Object o = i.next();
					assertTrue(o instanceof Element);
					Element e = (Element) o;
					assertEquals(e.getNodeName(), "BBB");
					assertEquals(e.getNodeValue(), null);
				}
			}

			String str =
				XmlUtil.getXPathApi().selectString(nd, "string(count(//BBB))");
			assertEquals(str, "5");

			int ncnt =
				XmlUtil
					.getXPathApi()
					.selectNumber(nd, "count(//BBB)")
					.intValue();
			assertEquals(ncnt, 5);

			boolean b =
				XmlUtil.getXPathApi().selectBoolean(nd, "count(//BBB) = 5");
			assertEquals(b, true);

			b = XmlUtil.getXPathApi().selectBoolean(nd, "count(//BBB) = 6");
			assertEquals(b, false);

		} catch (XXMLException e) {
			fail("unexpected exception");
		}

	}
	public void testSimpleXPathWithNamespace() {
		String simpleXml =
			"<AAA xmlns=\"http://foo\"> \n"
				+ "          <BBB/> \n"
				+ "          <CCC/> \n"
				+ "          <BBB/> \n"
				+ "          <DDD> \n"
				+ "               <BBB/> \n"
				+ "          </DDD> \n"
				+ "          <CCC> \n"
				+ "               <DDD> \n"
				+ "                    <BBB/> \n"
				+ "                    <BBB/> \n"
				+ "               </DDD> \n"
				+ "          </CCC> \n"
				+ "     </AAA> ";
		try {
			SimpleNamespaceContext snc = new SimpleNamespaceContext();
			snc.addNamespace("m", "http://foo");
			Node nd =
				XmlUtil.getXmlParserFactory().parseXml(
					new StringReader(simpleXml));
			Node selnd =
				(Node) XmlUtil.getXPathApi().selectSingleNode(
					((Document) nd).getDocumentElement(),
					"//m:BBB",
					snc,
					null,
					null);
			assertEquals(selnd.getNodeName(), "BBB");
			selnd =
				(Node) XmlUtil.getXPathApi().selectSingleNode(
					((Document) nd).getDocumentElement(),
					"m:CCC[2]",
					snc,
					null,
					null);
			assertEquals(selnd.getNodeName(), "CCC");
			selnd =
				(Node) XmlUtil.getXPathApi().selectSingleNode(
					selnd,
					"m:DDD/m:BBB",
					snc,
					null,
					null);
			assertEquals(selnd.getNodeName(), "BBB");
			for (int ii = 0; ii < 10; ii++) {
				List lst =
					XmlUtil.getXPathApi().selectNodes(
						nd,
						"//m:BBB",
						snc,
						null,
						null);
				assertEquals(lst.size(), 5);
				for (Iterator i = lst.iterator(); i.hasNext();) {
					Object o = i.next();
					assertTrue(o instanceof Element);
					Element e = (Element) o;
					assertEquals(e.getNodeName(), "BBB");
					assertEquals(e.getNodeValue(), null);
				}
			}
			/*
			            String str = XmlUtil.getXPathApi().selectString(nd, "count(//m:BBB)", snc, null, null);
			            assertEquals(str, "5");
			
			            int ncnt = XmlUtil.getXPathApi().selectNumber(nd, "count(//m:BBB)", snc, null, null).intValue();
			            assertEquals(ncnt, 5);
			
			            boolean b = XmlUtil.getXPathApi().selectBoolean(nd, "count(//m:BBB) = 5", snc, null, null);
			            assertEquals(b, true);
			
			            b = XmlUtil.getXPathApi().selectBoolean(nd, "count(//m:BBB) = 6", snc, null, null);
			            assertEquals(b, false);
			*/
		} catch (XXMLException e) {
			e.printStackTrace();
			fail("unexpected exception");

		}

	}
	public static void main(String[] args) {
		new junit.textui.TestRunner().doRun(
			new TestSuite(XPathApiUnitTest.class));
	}

}
