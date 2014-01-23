package com.votingcentral.util.xml;

import org.xml.sax.ContentHandler;


public interface ISaxEventProducer {

    /**
     * Sets the DocumentHandler to send SAX events to
     */
    public void setDocumentHandler(ContentHandler handler);

    /**
     * Signals to start producing events.
     */
    public void start() throws org.xml.sax.SAXException;

}
