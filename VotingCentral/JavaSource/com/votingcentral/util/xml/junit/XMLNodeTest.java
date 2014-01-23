package com.votingcentral.util.xml.junit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.votingcentral.util.xml.XMLNode;
import com.votingcentral.util.xml.XMLTreeBuilder;
import com.votingcentral.util.xml.XXMLException;

public class XMLNodeTest extends TestCase {

    private XMLNode xmlRoot = null;
    private static String testxml =
            "<test><t1>valt1</t1><t2>valt2.1</t2><t2>valt2.2</t2>\n" +
            "<t3 t3_a=\"aval t3\"><t4>valt4</t4></t3>\n" +
            "<t5 a=\"1\" b=\"1\">valt5.1</t5><t5 a=\"2\">\n" + "valt5.2</t5><t5 a=\"3\">valt5.3</t5>\n" +
            "<t5 a=\"4\">valt5.4</t5><t5 a=\"5\">valt5.5</t5><t5 a=\"6\">valt5.6</t5>\n" +
            "<t5 a=\"7\">valt5.7</t5><t5 a=\"8\" b=\"1\">valt5.8</t5><t5 a=\"9\">valt5.9</t5>\n" +
            "</test>";

    public XMLNodeTest(String value) {
        super(value);
    }

    protected void setUp() throws Exception {
        super.setUp();
        xmlRoot = XMLTreeBuilder.buildXMLTree(testxml);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        xmlRoot = null;
    }

    // Test Case Handlers
    public void test_addChild() throws Exception {
        (xmlRoot.getNode("test/t3")).addChild(new XMLNode("New", "New Value"));
        Assert.assertEquals("New Value", xmlRoot.getValue("test/t3/New"));
    }

    public void test_removeChild() throws Exception {
        (xmlRoot.getNode("test/t3")).removeChild("t4");
        try {
            Assert.assertNull(xmlRoot.getNode("test/t3/t4"));
        } catch (XXMLException e) {
            Assert.assertTrue(true);
        }
    }

    public void test_deleteTree() throws Exception {
        xmlRoot.deleteTree("test/t3");

        try {
            Assert.assertNull(xmlRoot.getNode("test/t3"));
        } catch (XXMLException e) {
            Assert.assertTrue(true);
        }

    }

    public void test_getNode() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            try {
                Assert.assertNotNull(xmlRoot.getNode("test/t1"));
            } catch (XXMLException e) {
                Assert.assertFalse("Failed to get the node", true);
            }
        }
        System.out.println("time taken by getNode: " +
                (System.currentTimeMillis() - start));
    }

    public void test_getMatchingNode() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            try {
                XMLNode node = xmlRoot.getNode("test/t5?a=1&b=1");
                Assert.assertNotNull(node);
                Assert.assertEquals("valt5.1", node.getValue());
            } catch (XXMLException e) {
                Assert.assertFalse("Failed to get the node", true);
            }
        }
        System.out.println("time taken by matching getNode: " +
                (System.currentTimeMillis() - start));

    }

    public void test_getNodeList() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            try {
                LinkedList list = xmlRoot.getNodeList("test/t2");
                Assert.assertNotNull(list);
                Assert.assertEquals("The return size does not match", 2, list.size());
            } catch (XXMLException e) {
                Assert.assertFalse("Failed to get the node list", true);
            }
        }
        System.out.println("time taken by nodelist: " +
                (System.currentTimeMillis() - start));

    }

    public void test_getMatchingList() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            try {
                LinkedList list = xmlRoot.getNodeList("test/t5?b=1");
                Assert.assertNotNull(list);
                Assert.assertEquals("The return size does not match", 2, list.size());
            } catch (XXMLException e) {
                Assert.assertFalse("Failed to get the node list", true);
            }
        }
        System.out.println("time taken by matching nodelist: " +
                (System.currentTimeMillis() - start));

    }

    public void test_getXML() throws Exception {
//        Assert.assertEquals(testxml, xmlRoot.getXML());
        xmlRoot.getXML();
    }

    public void test_getValue() throws Exception {
        Assert.assertEquals("valt1", xmlRoot.getValue("test/t1"));
        Assert.assertEquals("valt4", xmlRoot.getValue("test/t3/t4"));
        Assert.assertEquals("aval t3", xmlRoot.getValue("test/t3:t3_a"));
    }

    public void test_getValueAsString() throws Exception {
        Assert.assertEquals("valt2.1;valt2.2;", xmlRoot.getValueAsString("test/t2", ";"));
    }

    public void test_getPath() throws Exception {
        String xml = "<SubscriptionData><Title>Test XML Title</Title><DeliveryPrefs><Online>1</Online></DeliveryPrefs>" +
                "<Topic name='SF-Giants'><TeamName>San Francisco</TeamName></Topic>" +
                "<Topic name='Oakland-As'><TeamName>Oakland</TeamName></Topic>" +
                "</SubscriptionData>";

        XMLNode xmlRoot = XMLTreeBuilder.buildXMLTree(xml);
        System.out.println("value:" + xmlRoot.getValue("SubscriptionData/Topic?name=SF-Giants/TeamName"));
        xmlRoot.setValue("SubscriptionData/Topic?name=SF-Giants/TeamName", "changed SFO");
        System.out.println("value:" + xmlRoot.getValue("SubscriptionData/Topic?name=SF-Giants/TeamName"));
        XMLNode n = xmlRoot.getNode("SubscriptionData/Topic?name=SF-Giants/TeamName");
        System.out.println("Path: " + n.getPath());
        n = xmlRoot.getNode("SubscriptionData/Title");
        System.out.println("Path: " + n.getPath());
        n = xmlRoot.getNode("SubscriptionData/Topic?name=SF-Giants");
        System.out.println("Path: " + n.getPath());
    }

    public void test_getMap() throws Exception {
        String xml = "<SubscriptionData><Title>Test XML Title</Title><DeliveryPrefs><Online>1</Online></DeliveryPrefs>" +
                "<Topic name='SF-Giants'><TeamName>San Francisco</TeamName></Topic>" +
                "<Topic name='Oakland-As'><TeamName>Oakland</TeamName></Topic>" +
                "</SubscriptionData>";

        XMLNode xmlRoot = XMLTreeBuilder.buildXMLTree(xml);

        HashMap m = xmlRoot.getMap();
        Iterator i = m.keySet().iterator();
        System.out.println("MAP Keys:");
        while (i.hasNext()) {
            String key = (String) i.next();
            System.out.println("Key=" + key);
        }
    }

    public void test_CDATAxml() throws Exception {
        String xml = "<Test><t1> \n<![CDATA[\\nThis is a cdata <A>\n</B>]]></t1><t3>valt3</t3><t2> \n" +
                "<![CDATA[\n" +
                "This is a cdata <A>\n" +
                "</B\n" +
                " >]]></t2></Test>";
        XMLNode xmlRoot = XMLTreeBuilder.buildXMLTree(xml);
        System.out.println(xmlRoot.getXML(true));
    }

    public void test_merge() throws Exception {
        String xml = "<test><t1 id=\"1\">valt1</t1><t1 id=\"2\">" +
                "<t6>valt6</t6><t7>valt7</t7></t1><t2>t2_val1</t2><t2>t2_val2</t2></test>";
        XMLNode xmlRoot = XMLTreeBuilder.buildXMLTree(xml);
        xmlRoot.merge(XMLTreeBuilder.buildXMLTree("<test><t1 id=\"2\"><t6>changed value</t6><t8>val8</t8></t1><t2>t2_val2</t2><t4><t5>val t5</t5></t4></test>"));
        System.out.println(xmlRoot.getXML(true));
        XMLNode node = xmlRoot.getNode("test/t1?id=2/t6");
        System.out.println(node.getXML(true));

        node = xmlRoot.getNode("test/t4");
        System.out.println(node.getXML(true));

        LinkedList list = xmlRoot.getNodeList("test/t1?id=2/t6");
        System.out.println("size:" + list.size());
    }

    public void test_addUniqNode() throws Exception {
        String xml = "<test><t1 id=\"1\">valt1</t1><t1 id=\"2\">" +
                "<t6>valt6</t6><t7>valt7</t7></t1><t2>t2_val1</t2><t2>t2_val2</t2></test>";
        XMLNode xmlRoot = XMLTreeBuilder.buildXMLTree(xml);
        xmlRoot.addUniqNode("test", new XMLNode("t2", "t2_val1"), false);
        System.out.println(xmlRoot.getXML());
        xmlRoot.addUniqNode("test", new XMLNode("t2", "t2_val3"), false);
        System.out.println(xmlRoot.getXML());
    }

    public void test_sharedSchemaXML() throws Exception {

        String xml = "<SharedSchemas>\n" +
                "<Schema Name=\"schedule.xsd\">\n<![CDATA[\n" +
                "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "<xs:element name=\"Schedule\" type=\"ScheduleType\"/>\n" +
                "<xs:complexType name=\"ScheduleType\">\n" +
                "<xs:sequence>\n" +
                "<xs:element name=\"name\" type=\"xs:string\"/>\n" +
                "<xs:element name=\"size\" type=\"xs:integer\"/>\n" +
                "</xs:sequence>\n" +
                "</xs:complexType>\n" +
                "</xs:schema>" +
                "]]></Schema>\n" +
                "<Schema Name=\"rule.xsd\"><![CDATA[\n" +
                "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "<xs:element name=\"Rule\" type=\"RuleType\"/>\n" +
                "<xs:complexType name=\"RuleType\">\n" +
                "<xs:sequence>\n" +
                "<xs:element name=\"id\" type=\"xs:integer\"/>\n" +
                "<xs:element name=\"name\" type=\"xs:string\"/>\n" +
                "</xs:sequence>\n" +
                "</xs:complexType>\n" +
                "</xs:schema>" +
                "]]></Schema>\n" +

                "</SharedSchemas>\n";

        System.out.println("The value: " + xmlRoot.getNode("SharedSchemas/Schema?Name=schedule.xsd").getValue());


        XMLNode xmlRoot = XMLTreeBuilder.buildXMLTree(xml);
        System.out.println(xmlRoot.getXML(true));
    }

    public void test_clone() throws Exception {
        String xml = "<test><T1>vt1</T1><T2><T3 id=\"1\">vt3</T3><T4>vt4</T4></T2></test>";
        XMLNode xmlRoot = XMLTreeBuilder.buildXMLTree(xml);

        XMLNode clonedXmlRoot = (XMLNode)xmlRoot.clone();
        xmlRoot.setValue("test/T1", "Modified in root");

        XMLNode y = xmlRoot.getNode("test/T2/T3?id=1");
        y.addAttribute("id","2");

        clonedXmlRoot.setValue("test/T1", "Modified in clone");
        XMLNode x = clonedXmlRoot.getNode("test/T2/T3?id=1");
        x.addAttribute("id","3");

        clonedXmlRoot.deleteTree("test/T2/T4");

        System.out.println("Originnal XML: " + xmlRoot.getXML());
        System.out.println("Cloned XML: " + clonedXmlRoot.getXML());

        String newXML = "<test><T1>From the merged</T1><T5>valt5</T5><T2><T4>changed from merge</T4></T2></test>";
//        clonedXmlRoot.merge(XMLTreeBuilder.buildXMLTree(newXML));

        clonedXmlRoot.merge(new XMLNode(XMLNode.ROOT, null));
        System.out.println("Originnal XML: " + xmlRoot.getXML());
        System.out.println("Cloned XML: " + clonedXmlRoot.getXML());

    }

    // Test Suite

    public static Test suite() {
        TestSuite suite = new TestSuite();
/*
        suite.addTest(new XMLNodeTest("test_getXML"));
        suite.addTest(new XMLNodeTest("test_getValue"));
        suite.addTest(new XMLNodeTest("test_getValueAsString"));
        suite.addTest(new XMLNodeTest("test_addChild"));
        suite.addTest(new XMLNodeTest("test_removeChild"));
        suite.addTest(new XMLNodeTest("test_deleteTree"));
        suite.addTest(new XMLNodeTest("test_getMatchingList"));
        suite.addTest(new XMLNodeTest("test_getMatchingNode"));
        suite.addTest(new XMLNodeTest("test_getNode"));
        suite.addTest(new XMLNodeTest("test_getNodeList"));
        suite.addTest(new XMLNodeTest("test_getPath"));
        suite.addTest(new XMLNodeTest("test_getMap"));
*/

        suite.addTest(new XMLNodeTest("test_clone"));


        return suite;
    }

    public static void main(String args[]) {
        junit.textui.TestRunner.run(suite());
    }
}
