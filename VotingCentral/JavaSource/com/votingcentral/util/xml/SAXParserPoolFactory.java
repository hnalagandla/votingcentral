package com.votingcentral.util.xml;

import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.pool.BasePoolableObjectFactory;

public class SAXParserPoolFactory extends BasePoolableObjectFactory {

    // To prepare SAX Parser
    public Object makeObject() throws Exception {

        SAXParserFactory factory = null;

        // create parser factory
        try {
            factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(false);
        } catch (Exception e) {
            System.err.println("error: Unable to instantiate SAXParserFactory");
            throw e;
        }

        return factory.newSAXParser();
    }
}
