package com.votingcentral.util.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.SAXParser;

import org.w3c.dom.Node;

/**
 * 
 */
public class XmlUtil {
    private static JaxenXPathAPI jaxenxpath = new JaxenXPathAPI();
    private static XmlParser xmlParser = new XmlParser();
    private static JarvValidatorFactory jvf = new JarvValidatorFactory(1024);
    private static TraxTransformerFactory ttf = new TraxTransformerFactory(1024);
    private static SaxonXQueryProcessorFactory sxqpf = new SaxonXQueryProcessorFactory(1024);

    public static IXmlParser getXmlParserFactory() {
        return xmlParser;
    }
    String xmlToString(Node node) throws XXMLException {
        return getXmlParserFactory().xmlToString(node);
    }

    String xmlToStringWithIndent(Node node) throws XXMLException {

        return getXmlParserFactory().xmlToStringWithIndent(node);

    }

    public static IXmlValidatorFactory getXmlValidatorFactory() {
        return jvf;
    }

    public static IXmlTransformerFactory getXmlTransformerFactory() {
        return ttf;
    }

    public static IXQueryProcessorFactory getXQueryProcessorFactory() {
        return sxqpf;
    }

    public static IXPathApi getXPathApi() {
        return jaxenxpath;
    }

    // return a per thread local pool parser
    public static SAXParser getSaxParser() throws XXMLException {
        return getSaxParser(false);
    }

    /**
     * return a SAX parser instance - cached on thread local data
     *
     * @param namespaceaware
     * @return
     * @throws XXMLException
     */

    public static SAXParser getSaxParser(boolean namespaceaware) throws XXMLException {
        return xmlParser.getSaxParser(namespaceaware);
    }

    /**
     * return a DOM parser instance for parsing xml - cached on thread local data
     *
     * @param namespaceaware
     * @return
     * @throws XXMLException
     */
    public static DocumentBuilder getDomParser(boolean namespaceaware) throws XXMLException {
        return xmlParser.getDomParser(namespaceaware);
    }

    // Utility Methods to deal with XML Data
    static public String replaceStr2StrSet(String inpStr, String[] from, String[] to) {
        if (inpStr != null) {
            char[] arr = inpStr.toCharArray();
            StringBuffer res = new StringBuffer();
            int i, k, iStart;
            for (iStart = i = 0; i < arr.length; i++) {
                for (k = 0; k < from.length; k++) {
                    if (inpStr.startsWith(from[k], i)) {
                        res.append(arr, iStart, i - iStart);
                        res.append(to[k]);
                        iStart = i + from[k].length();
                        break;
                    }
                }
            }
            res.append(arr, iStart, i - iStart);
            return res.toString();
        }
        return null;
    }

    static final private String[] _xmlEscapeFrom = {"<", ">", "\"", "&", "\'"};
    static final private String[] _xmlEscapeTo = {"&lt;", "&gt;", "&quot;", "&amp;", "&apos;"};


    static public String escapeXMLSpecialChars(String inpStr) {
        return replaceStr2StrSet(inpStr, _xmlEscapeFrom, _xmlEscapeTo);
    }

    static public String unescapeXMLSpecialChars(String inpStr) {
        return replaceStr2StrSet(inpStr, _xmlEscapeTo, _xmlEscapeFrom);
    }


}
