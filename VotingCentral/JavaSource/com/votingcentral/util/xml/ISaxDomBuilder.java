package com.votingcentral.util.xml;

import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;

public interface ISaxDomBuilder extends ContentHandler {
	Node getNode();
}