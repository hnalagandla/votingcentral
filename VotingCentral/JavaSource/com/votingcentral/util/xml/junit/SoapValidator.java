package com.votingcentral.util.xml.junit;

import org.jaxen.SimpleNamespaceContext;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.votingcentral.util.xml.XmlUtil;
import com.votingcentral.util.xml.IXmlValidator;

import java.io.*;

/**
 * 
 */
public class SoapValidator {

    public static String readStringFromInputStream(InputStream is) throws Exception
    {
        try {
            StringBuffer result = new StringBuffer();
            int ch;
            while ((ch=is.read()) != -1)
                result.append((char) ch);
            return result.toString();
        } catch (Exception e) {throw e;}
    }
    public static String loadFileData(String fname) throws Exception
    {
        FileInputStream fs = new FileInputStream(fname);
        String ret = readStringFromInputStream(fs);
        fs.close();
        return ret;
    }

    public static void main(String args[]) {
        SimpleNamespaceContext mNameSpaceContext = new SimpleNamespaceContext();
        mNameSpaceContext.addNamespace("s", "http://alertsapi.aol.com/AlertsSubscription/1.0/AlertsSubscriptionAPI.xsd");
        mNameSpaceContext.addNamespace("f", "http://alertsapi.aol.com/AlertsFeed/1.0/AlertFeedAPI.xsd");
        mNameSpaceContext.addNamespace("m", "http://alertsapi.aol.com/AlertsDefinition/1.0/AlertsDefinitionAPI.xsd");
        mNameSpaceContext.addNamespace("soap", "http://schemas.xmlsoap.org/soap/envelope/");

        if (args.length < 2) {
            System.out.println("Usage : java com.aol.organizer.notify.xmlutil.junit.SoapValidator xmlfile xsdfile");
            System.exit(1);
        }

        try {
            String inputxml = loadFileData(args[0]);
            String xsdfile = loadFileData(args[1]);
            final String schemadirectory = new File(args[1]).getParent();

            Node nd = XmlUtil.getXmlParserFactory().parseXml(new StringReader(inputxml));
            String xpath = System.getProperty("xpath");
            if (xpath == null)
                xpath = "/soap:Envelope/soap:Body/*";
            Node body = (Node) XmlUtil.getXPathApi().selectSingleNode(nd,
                                "/soap:Envelope/soap:Body/*", mNameSpaceContext, null, null);
            IXmlValidator validator = XmlUtil.getXmlValidatorFactory().compileValidator(new StringReader(xsdfile),
                    new EntityResolver () {
                        public InputSource resolveEntity (String publicId,   String systemId)
                            throws SAXException, IOException {
                            try {
                                    String res = (String) loadFileData(schemadirectory + "/" + new File(systemId).getName());
                                    return new InputSource(new StringReader(res));
                            } catch (Exception e) {
                                e.printStackTrace();
                                throw new SAXException("Unable to resolve " + publicId + " : " + systemId);
                            }
                        }
                    });
            validator.validate(body);
            System.out.println("DOCUMENT IS VALID");
        } catch (Exception e) {
                        System.out.println("DOCUMENT IS INVALID!\n\n" + e.getMessage());
                                    e.printStackTrace();
                                            }


            }
}

