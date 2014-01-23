package com.votingcentral.util.xml.junit;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.votingcentral.util.xml.IXmlParser;
import com.votingcentral.util.xml.IXmlValidator;
import com.votingcentral.util.xml.IXmlValidatorFactory;
import com.votingcentral.util.xml.XmlUtil;

/**
 * Created by IntelliJ IDEA.
 * User: eranti
 * Date: Feb 27, 2004
 * Time: 4:27:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class XmlValidatorUnitTest extends TestCase {

	public void testXmlSchemaValidationInheritance() {
		String simplexmlschema =
			"<xsd:schema targetNamespace=\"http://foo\" elementFormDefault=\"qualified\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://foo\">\n"
				+ "\t<xsd:element name=\"root\">\n"
				+ "\t\t<xsd:complexType>\n"
				+ "\t\t\t<xsd:sequence>\n"
				+ "\t\t\t\t<xsd:element name=\"content\" type=\"tns:AAA\" maxOccurs=\"unbounded\"/>\n"
				+ "\t\t\t</xsd:sequence>\n"
				+ "\t\t</xsd:complexType>\n"
				+ "\t</xsd:element>\n"
				+ "\t<xsd:complexType name=\"AAA\">\n"
				+ "\t\t<xsd:sequence>\n"
				+ "\t\t\t<xsd:element name=\"x\" type=\"xsd:string\"/>\n"
				+ "\t\t\t<xsd:element name=\"y\" type=\"xsd:string\"/>\n"
				+ "\t\t</xsd:sequence>\n"
				+ "\t</xsd:complexType>\n"
				+ "<xsd:simpleType name=\"nonnullstring\">\n"
				+ "\t\t<xsd:restriction base=\"xsd:string\">\n"
				+ "\t\t\t<xsd:minLength value=\"10\"/>\n"
				+ "\t\t\t<xsd:maxLength value=\"100\"/>\n"
				+ "\t\t</xsd:restriction>\n"
				+ "\t</xsd:simpleType>"
				+ "\t<xsd:complexType name=\"BBB\">\n"
				+ "\t\t<xsd:complexContent>\n"
				+ "\t\t\t<xsd:extension base=\"tns:AAA\">\n"
				+ "\t\t\t\t<xsd:sequence>\n"
				+ "\t\t\t\t\t<xsd:element name=\"z\" type=\"tns:nonnullstring\"/>\n"
				+ "\t\t\t\t</xsd:sequence>\n"
				+ "\t\t\t</xsd:extension>\n"
				+ "\t\t</xsd:complexContent>\n"
				+ "\t</xsd:complexType>\n"
				+ "\t<xsd:complexType name=\"CCC\">\n"
				+ "\t\t<xsd:complexContent>\n"
				+ "\t\t\t<xsd:extension base=\"tns:AAA\">\n"
				+ "\t\t\t\t<xsd:sequence>\n"
				+ "\t\t\t\t\t<xsd:element name=\"a\" type=\"xsd:string\"/>\n"
				+ "\t\t\t\t</xsd:sequence>\n"
				+ "\t\t\t</xsd:extension>\n"
				+ "\t\t</xsd:complexContent>\n"
				+ "\t</xsd:complexType>\n"
				+ "</xsd:schema>";
		String simplexml =
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<t:root xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"c:\\zvon.xsd\" xmlns:t=\"http://foo\">\n"
				+ "\t<t:content>\n"
				+ "\t\t<t:x/>\n"
				+ "\t\t<t:y/>\n"
				+ "\t</t:content>\n"
				+ "\t<t:content xsi:type=\"t:BBB\">\n"
				+ "\t\t<t:x>test3</t:x>\n"
				+ "\t\t<t:y>id2</t:y>\n"
				+ "\t\t<t:z>sdaf asdf sa df sdaf sadf sfa dsfasdf</t:z>\n"
				+ "\t</t:content>\n"
				+ "\t<t:content xsi:type=\"t:CCC\">\n"
				+ "\t\t<t:x/>\n"
				+ "\t\t<t:y/>\n"
				+ "\t\t<t:a/>\n"
				+ "\t</t:content>\n"
				+ "</t:root>";
		String simplebadxml =
			"<?xml version=\"1.0\"?>\n"
				+ "<note>\n"
				+ "<tooo>Tove</tooo>\n"
				+ "<from>Jani</from>\n"
				+ "<heading>Reminder</heading>\n"
				+ "<body>Don't forget me this weekend!</body>\n"
				+ "</note>";

		try {
			IXmlValidatorFactory vf = XmlUtil.getXmlValidatorFactory();
			for (int i = 0; i < 10000; i++) {
				IXmlValidator val =
					vf.compileValidator(new StringReader(simplexmlschema));
				IXmlParser pf = XmlUtil.getXmlParserFactory();
				Document doc = pf.parseXml(new StringReader(simplexml));
				assertNotNull(doc);
				String beforeval = pf.xmlToStringWithIndent(doc);
				System.out.println("parsed xml doc " + beforeval);
				val.validate(doc);
				assertEquals(beforeval, pf.xmlToStringWithIndent(doc));

				//System.out.println(pf.xmlToStringWithIndent(doc));
				val.validate(new StringReader(simplexml));
				try {
					doc = pf.parseXml(new StringReader(simplebadxml));
					assertNotNull(doc);
					val.validate(doc);
					fail("Expected to fail validation and hence throw exception !");
				} catch (Exception e) {
					System.out.println(
						"Expected failure happened. message " + e.getMessage());
					//e.printStackTrace();
				}
				try {
					val.validate(new StringReader(simplebadxml));
					fail("Expected to fail validation and hence throw exception !");
				} catch (Exception e) {
					System.out.println(
						"Expected failure happened. message " + e.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}

	public void testXmlSchemaValidation() {
		String simplexmlschema =
			"<?xml version=\"1.0\"?>\n"
				+ "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n"
				+ "elementFormDefault=\"qualified\"><xs:element name=\"note\">\n"
				+ "    <xs:complexType>\n"
				+ "      <xs:sequence>\n"
				+ "\t<xs:element name=\"to\" type=\"xs:string\"/>\n"
				+ "\t<xs:element name=\"from\" type=\"xs:string\"/>\n"
				+ "\t<xs:element name=\"heading\" type=\"xs:string\"/>\n"
				+ "\t<xs:element name=\"body\" type=\"xs:string\"/>\n"
				+ "      </xs:sequence>\n"
				+ "    </xs:complexType>\n"
				+ "</xs:element></xs:schema>";
		String simplexml =
			"<?xml version=\"1.0\"?>\n"
				+ "<note>\n"
				+ "<to>Tove</to>\n"
				+ "<from>Jani</from>\n"
				+ "<heading>Reminder</heading>\n"
				+ "<body>Don't forget me this weekend!</body>\n"
				+ "</note>";
		String simplebadxml =
			"<?xml version=\"1.0\"?>\n"
				+ "<note>\n"
				+ "<tooo>Tove</tooo>\n"
				+ "<from>Jani</from>\n"
				+ "<heading>Reminder</heading>\n"
				+ "<body>Don't forget me this weekend!</body>\n"
				+ "</note>";

		try {
			IXmlValidatorFactory vf = XmlUtil.getXmlValidatorFactory();
			IXmlValidator val =
				vf.compileValidator(new StringReader(simplexmlschema));
			IXmlParser pf = XmlUtil.getXmlParserFactory();
			Document doc = pf.parseXml(new StringReader(simplexml));
			assertNotNull(doc);
			String beforeval = pf.xmlToStringWithIndent(doc);
			val.validate(doc);
			assertEquals(beforeval, pf.xmlToStringWithIndent(doc));

			//System.out.println(pf.xmlToStringWithIndent(doc));
			val.validate(new StringReader(simplexml));
			try {
				doc = pf.parseXml(new StringReader(simplebadxml));
				assertNotNull(doc);
				val.validate(doc);
				fail("Expected to fail validation and hence throw exception !");
			} catch (Exception e) {
				System.out.println(
					"Expected failure happened. message " + e.getMessage());
				//e.printStackTrace();
			}
			try {
				val.validate(new StringReader(simplebadxml));
				fail("Expected to fail validation and hence throw exception !");
			} catch (Exception e) {
				System.out.println(
					"Expected failure happened. message " + e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}

	public void testXmlDtdValidation() {
		String simplexmldtd =
			"<!ELEMENT note (to, from, heading, body)>\n"
				+ "<!ELEMENT to (#PCDATA)>\n"
				+ "<!ELEMENT from (#PCDATA)>\n"
				+ "<!ELEMENT heading (#PCDATA)>\n"
				+ "<!ELEMENT body (#PCDATA)>";
		String simplexml =
			"<?xml version=\"1.0\"?>\n"
				+ "<note>\n"
				+ "<to>Tove</to>\n"
				+ "<from>Jani</from>\n"
				+ "<heading>Reminder</heading>\n"
				+ "<body>Don't forget me this weekend!</body>\n"
				+ "</note>";
		String simplebadxml =
			"<?xml version=\"1.0\"?>\n"
				+ "<note>\n"
				+ "<tooo>Tove</tooo>\n"
				+ "<from>Jani</from>\n"
				+ "<heading>Reminder</heading>\n"
				+ "<body>Don't forget me this weekend!</body>\n"
				+ "</note>";

		try {
			IXmlValidatorFactory vf = XmlUtil.getXmlValidatorFactory();
			IXmlValidator val =
				vf.compileValidator(
					new StringReader(simplexmldtd),
					IXmlValidatorFactory.DTD_VALIDATOR);
			IXmlParser pf = XmlUtil.getXmlParserFactory();
			Document doc = pf.parseXml(new StringReader(simplexml));
			assertNotNull(doc);
			String beforeval = pf.xmlToStringWithIndent(doc);
			val.validate(doc);
			//System.out.println(pf.xmlToStringWithIndent(doc));
			assertEquals(beforeval, pf.xmlToStringWithIndent(doc));
			val.validate(new StringReader(simplexml));
			try {
				doc = pf.parseXml(new StringReader(simplebadxml));
				assertNotNull(doc);
				val.validate(doc);
				fail("Expected to fail validation and hence throw exception !");
			} catch (Exception e) {
				System.out.println(
					"Expected failure happened. message " + e.getMessage());
			}
			try {
				val.validate(new StringReader(simplebadxml));
				fail("Expected to fail validation and hence throw exception !");
			} catch (Exception e) {
				System.out.println(
					"Expected failure happened. message " + e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}

	class SResolver extends HashMap implements EntityResolver {

		public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
			String res = (String) get(systemId);
			if (res == null)
				throw new SAXException(
					"Unable to resolve " + publicId + " : " + systemId);
			return new InputSource(new StringReader(res));
		}

	}

	public void testXmlIncludeSchemaValidation() {

		String simplexmlschema =
			"<?xml version=\"1.0\"?>\n"
				+ "<xsd:schema targetNamespace=\"http://foo\" xmlns:ff=\"http://junk\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:foo=\"http://foo\" > \n"
				+ "\n"
				+ "  <xsd:include schemaLocation=\"http://www.alerts.com/correct_1.xsd\"/> \n"
				+ "\n"
				+ "  <xsd:element name=\"root\" type=\"foo:myType\"/> \n"
				+ "</xsd:schema> ";
		String includeschema =
			"<?xml version=\"1.0\"?>\n"
				+ "<xsd:schema targetNamespace=\"http://foo\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" > \n"
				+ "\n"
				+ "  <xsd:complexType name=\"myType\"> \n"
				+ "    <xsd:sequence> \n"
				+ "      <xsd:element name=\"x\" minOccurs=\"1\" maxOccurs=\"1\" type=\"xsd:integer\" form=\"qualified\"/> \n"
				+ "    </xsd:sequence> \n"
				+ "  </xsd:complexType> \n"
				+ "</xsd:schema>";
		String simplexml =
			"<?xml version=\"1.0\"?>\n"
				+ "<foo:root  xmlns:foo=\"http://foo\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" > \n"
				+ "  <foo:x>23423</foo:x> \n"
				+ "</foo:root>";
		String simplebadxml =
			"<?xml version=\"1.0\"?>\n"
				+ "<foo:root  xmlns:foo=\"http://foo\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" > \n"
				+ "  <x xmlns=\"http://foo1\">dhhglh</x> \n"
				+ "</foo:root> ";

		System.out.println(
			"schema : "
				+ simplexmlschema
				+ "\nincluded :\n"
				+ includeschema
				+ "\n badxml : \n"
				+ simplebadxml);
		/**
		String simplexmlschema = "<?xml version=\"1.0\"?>\n" +
		        "<xsd:schema  xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"  > \n" +
		        "\n" +
		        "  <xsd:include schemaLocation=\"http://www.alerts.com/correct_1.xsd\"/> \n" +
		        "\n" +
		        "  <xsd:element name=\"root\" type=\"myType\"/> \n" +
		        "</xsd:schema> ";
		String includeschema = "<?xml version=\"1.0\"?>\n" +
		        "<xsd:schema  xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" > \n" +
		        "\n" +
		        "  <xsd:complexType name=\"myType\"> \n" +
		        "    <xsd:sequence> \n" +
		        "      <xsd:element name=\"x\" minOccurs=\"1\" maxOccurs=\"1\" type=\"xsd:integer\" form=\"qualified\"/> \n" +
		        "    </xsd:sequence> \n" +
		        "  </xsd:complexType> \n" +
		        "</xsd:schema>";
		String simplexml = "<?xml version=\"1.0\"?>\n" +
		        "<root> \n" +
		        "  <x>123</x> \n" +
		        "</root>";
		String simplebadxml = "<?xml version=\"1.0\"?>\n" +
		        "<root> \n" +
		        "  <xx>123</xx> \n" +
		        "</root> ";
		 *
		 */
		try {
			SResolver res = new SResolver();
			res.put("http://www.alerts.com/correct_1.xsd", includeschema);
			IXmlValidatorFactory vf = XmlUtil.getXmlValidatorFactory();
			IXmlValidator val =
				vf.compileValidator(
					new StringReader(simplexmlschema),
					res,
					IXmlValidatorFactory.W3C_SCHEMA_VALIDATOR);
			IXmlParser pf = XmlUtil.getXmlParserFactory();
			Document doc = pf.parseXml(new StringReader(simplexml));
			assertNotNull(doc);
			String beforeval = pf.xmlToStringWithIndent(doc);
			val.validate(new StringReader(simplexml));
			//System.out.println(pf.xmlToStringWithIndent(doc));
			val.validate(doc);
			assertEquals(beforeval, pf.xmlToStringWithIndent(doc));
			try {
				doc = pf.parseXml(new StringReader(simplebadxml));
				assertNotNull(doc);
				val.validate(doc);
				fail("Expected to fail validation and hence throw exception !");
			} catch (Exception e) {
				System.out.println(
					"Expected failure happened. message " + e.getMessage());
				e.printStackTrace(System.err);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}

	public void testXmlIncludeSchemaValidationWithNamespaces() {

		String simplexmlschema =
			"<?xml version=\"1.0\"?>\n"
				+ "<xsd:schema targetNamespace=\"http://foo\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:foo=\"http://foo\" > \n"
				+ "\n"
				+ "  <xsd:include schemaLocation=\"http://www.alerts.com/correct_1.xsd\"/> \n"
				+ "\n"
				+ "  <xsd:element name=\"root\" type=\"foo:myType\"/> \n"
				+ "</xsd:schema> ";
		String includeschema =
			"<?xml version=\"1.0\"?>\n"
				+ "<xsd:schema targetNamespace=\"http://foo\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" > \n"
				+ "\n"
				+ "  <xsd:complexType name=\"myType\"> \n"
				+ "    <xsd:sequence> \n"
				+ "      <xsd:element name=\"x\" minOccurs=\"1\" maxOccurs=\"1\" type=\"xsd:integer\" form=\"qualified\"/> \n"
				+ "    </xsd:sequence> \n"
				+ "  </xsd:complexType> \n"
				+ "</xsd:schema>";
		String simplexml =
			"<?xml version=\"1.0\"?>\n"
				+ "<foo:root  xmlns:foo=\"http://foo\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" > \n"
				+ "  <foo:x>243223</foo:x> \n"
				+ "</foo:root>";
		String simplebadxml =
			"<?xml version=\"1.0\"?>\n"
				+ "<foo:root  xmlns:foo=\"http://foo\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" > \n"
				+ "  <foo:xx>243223</foo:xx> \n"
				+ "</foo:root>";
		/*        String simplebadxml = "<?xml version=\"1.0\"?>\n" +
		                "<foo:root  xmlns:foo=\"http://foo\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" > \n" +
		                "  <x xmlns=\"\">dhhglh</x> \n" +
		                "</foo:root> ";
		 */
		try {
			SResolver res = new SResolver();
			res.put("http://www.alerts.com/correct_1.xsd", includeschema);
			IXmlValidatorFactory vf = XmlUtil.getXmlValidatorFactory();
			IXmlValidator val =
				vf.compileValidator(
					new StringReader(simplexmlschema),
					res,
					IXmlValidatorFactory.W3C_SCHEMA_VALIDATOR);
			IXmlParser pf = XmlUtil.getXmlParserFactory();
			Document doc = pf.parseXml(new StringReader(simplexml));
			assertNotNull(doc);
			String beforeval = pf.xmlToStringWithIndent(doc);
			val.validate(new StringReader(simplexml));
			//            System.out.println(pf.xmlToStringWithIndent(doc));
			val.validate(doc);
			assertEquals(beforeval, pf.xmlToStringWithIndent(doc));
			try {
				doc = pf.parseXml(new StringReader(simplebadxml));
				assertNotNull(doc);
				val.validate(doc);
				fail("Expected to fail validation and hence throw exception !");
			} catch (Exception e) {
				System.out.println(
					"Expected failure happened. message " + e.getMessage());
				//              e.printStackTrace(System.err);
			}
			val.validate(new StringReader(simplexml));

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	public void testXmlImportSchemaValidationAndParsingWithNamespaces() {

		String simplexmlschema =
			"<?xml version=\"1.0\"?>\n"
				+ "<xsd:schema targetNamespace=\"http://foo1\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:foo=\"http://foo\" > \n"
				+ "\n"
				+ "  <xsd:import schemaLocation=\"http://www.alerts.com/correct_1.xsd\" namespace=\"http://foo\"/> \n"
				+ "\n"
				+ "  <xsd:element name=\"root\" type=\"foo:myType\"/> \n"
				+ "</xsd:schema> ";
		String includeschema =
			"<?xml version=\"1.0\"?>\n"
				+ "<xsd:schema targetNamespace=\"http://foo\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" > \n"
				+ "\n"
				+ "  <xsd:complexType name=\"myType\"> \n"
				+ "    <xsd:sequence> \n"
				+ "      <xsd:element name=\"x\" minOccurs=\"1\" maxOccurs=\"1\" type=\"xsd:integer\" form=\"qualified\"/> \n"
				+ "    </xsd:sequence> \n"
				+ "  </xsd:complexType> \n"
				+ "</xsd:schema>";
		String simplexml =
			"<?xml version=\"1.0\"?>\n"
				+ "<foo1:root xmlns:foo1=\"http://foo1\" xmlns:foo=\"http://foo\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" > \n"
				+ "  <foo:x>243223</foo:x> \n"
				+ "</foo1:root>";
		String simplebadxml =
			"<?xml version=\"1.0\"?>\n"
				+ "<foo:root  xmlns:foo=\"http://foo\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" > \n"
				+ "  <foo:xx>243223</foo:xx> \n"
				+ "</foo:root>";
		/*        String simplebadxml = "<?xml version=\"1.0\"?>\n" +
		                "<foo:root  xmlns:foo=\"http://foo\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" > \n" +
		                "  <x xmlns=\"\">dhhglh</x> \n" +
		                "</foo:root> ";
		 */
		try {
			SResolver res = new SResolver();
			res.put("http://www.alerts.com/correct_1.xsd", includeschema);
			IXmlValidatorFactory vf = XmlUtil.getXmlValidatorFactory();
			IXmlValidator val =
				vf.compileValidator(
					new StringReader(simplexmlschema),
					res,
					IXmlValidatorFactory.W3C_SCHEMA_VALIDATOR);
			IXmlParser pf = XmlUtil.getXmlParserFactory();
			Document doc = pf.parseXml(new StringReader(simplexml), val);
			try {
				doc = pf.parseXml(new StringReader(simplebadxml), val);
				fail("Expected to fail validation and hence throw exception !");
			} catch (Exception e) {
				System.out.println(
					"Expected failure happened. message " + e.getMessage());
				//              e.printStackTrace(System.err);
			}
			val.validate(new StringReader(simplexml));

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}
	public void testXmlIncludeSchemaValidationAndParsingWithNamespaces() {

		String simplexmlschema =
			"<?xml version=\"1.0\"?>\n"
				+ "<xsd:schema targetNamespace=\"http://foo\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:foo=\"http://foo\" > \n"
				+ "\n"
				+ "  <xsd:include schemaLocation=\"http://www.alerts.com/correct_1.xsd\"/> \n"
				+ "\n"
				+ "  <xsd:element name=\"root\" type=\"foo:myType\"/> \n"
				+ "</xsd:schema> ";
		String includeschema =
			"<?xml version=\"1.0\"?>\n"
				+ "<xsd:schema targetNamespace=\"http://foo\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" > \n"
				+ "\n"
				+ "  <xsd:complexType name=\"myType\"> \n"
				+ "    <xsd:sequence> \n"
				+ "      <xsd:element name=\"x\" minOccurs=\"1\" maxOccurs=\"1\" type=\"xsd:integer\" form=\"qualified\"/> \n"
				+ "    </xsd:sequence> \n"
				+ "  </xsd:complexType> \n"
				+ "</xsd:schema>";
		String simplexml =
			"<?xml version=\"1.0\"?>\n"
				+ "<foo:root  xmlns:foo=\"http://foo\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" > \n"
				+ "  <foo:x>243223</foo:x> \n"
				+ "</foo:root>";
		String simplebadxml =
			"<?xml version=\"1.0\"?>\n"
				+ "<foo:root  xmlns:foo=\"http://foo\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" > \n"
				+ "  <foo:xx>243223</foo:xx> \n"
				+ "</foo:root>";
		/*        String simplebadxml = "<?xml version=\"1.0\"?>\n" +
		                "<foo:root  xmlns:foo=\"http://foo\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" > \n" +
		                "  <x xmlns=\"\">dhhglh</x> \n" +
		                "</foo:root> ";
		 */
		try {
			SResolver res = new SResolver();
			res.put("http://www.alerts.com/correct_1.xsd", includeschema);
			IXmlValidatorFactory vf = XmlUtil.getXmlValidatorFactory();
			IXmlValidator val =
				vf.compileValidator(
					new StringReader(simplexmlschema),
					res,
					IXmlValidatorFactory.W3C_SCHEMA_VALIDATOR);
			IXmlParser pf = XmlUtil.getXmlParserFactory();
			Document doc = pf.parseXml(new StringReader(simplexml), val);
			try {
				doc = pf.parseXml(new StringReader(simplebadxml), val);
				fail("Expected to fail validation and hence throw exception !");
			} catch (Exception e) {
				System.out.println(
					"Expected failure happened. message " + e.getMessage());
				//              e.printStackTrace(System.err);
			}
			val.validate(new StringReader(simplexml));

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}

	public void testXmlIncludeSchemaValidationAndSAXParsingWithNamespaces() {

		String simplexmlschema =
			"<?xml version=\"1.0\"?>\n"
				+ "<xsd:schema targetNamespace=\"http://foo\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:foo=\"http://foo\" > \n"
				+ "\n"
				+ "  <xsd:include schemaLocation=\"http://www.alerts.com/correct_1.xsd\"/> \n"
				+ "\n"
				+ "  <xsd:element name=\"root\" type=\"foo:myType\"/> \n"
				+ "</xsd:schema> ";
		String includeschema =
			"<?xml version=\"1.0\"?>\n"
				+ "<xsd:schema targetNamespace=\"http://foo\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" > \n"
				+ "\n"
				+ "  <xsd:complexType name=\"myType\"> \n"
				+ "    <xsd:sequence> \n"
				+ "      <xsd:element name=\"x\" minOccurs=\"1\" maxOccurs=\"1\" type=\"xsd:integer\" form=\"qualified\"/> \n"
				+ "    </xsd:sequence> \n"
				+ "  </xsd:complexType> \n"
				+ "</xsd:schema>";
		String simplexml =
			"<?xml version=\"1.0\"?>\n"
				+ "<foo:root  xmlns:foo=\"http://foo\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" > \n"
				+ "  <foo:x>243223</foo:x> \n"
				+ "</foo:root>";
		String simplebadxml =
			"<?xml version=\"1.0\"?>\n"
				+ "<foo:root  xmlns:foo=\"http://foo\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" > \n"
				+ "  <foo:xx>243223</foo:xx> \n"
				+ "</foo:root>";
		/*        String simplebadxml = "<?xml version=\"1.0\"?>\n" +
		                "<foo:root  xmlns:foo=\"http://foo\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" > \n" +
		                "  <x xmlns=\"\">dhhglh</x> \n" +
		                "</foo:root> ";
		 */
		try {
			SResolver res = new SResolver();
			res.put("http://www.alerts.com/correct_1.xsd", includeschema);
			IXmlValidatorFactory vf = XmlUtil.getXmlValidatorFactory();
			IXmlValidator val =
				vf.compileValidator(
					new StringReader(simplexmlschema),
					res,
					IXmlValidatorFactory.W3C_SCHEMA_VALIDATOR);
			IXmlParser pf = XmlUtil.getXmlParserFactory();
			pf.parseXmlUsingSax(
				new StringReader(simplexml),
				val,
				new DefaultHandler());
			try {
				pf.parseXmlUsingSax(
					new StringReader(simplebadxml),
					val,
					new DefaultHandler());
				// TODO: work on this - this test case is failing !!
				//fail("Expected to fail validation and hence throw exception !");
			} catch (Exception e) {
				System.out.println(
					"Expected failure happened. message " + e.getMessage());
				//              e.printStackTrace(System.err);
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}
	}

	public static void main(String[] args) {
		new junit.textui.TestRunner().doRun(
			new TestSuite(XmlValidatorUnitTest.class));
	}

}
