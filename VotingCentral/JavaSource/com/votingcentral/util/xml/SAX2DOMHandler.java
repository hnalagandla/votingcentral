package com.votingcentral.util.xml;

import java.util.Stack;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.*;
import com.votingcentral.util.xml.ISaxDomBuilder;

/**
 * 
 */
class SAX2DOMHandler extends DefaultHandler implements ISaxDomBuilder {
    //TODO: note this doesnt handle mixed content. need to copy stuff from xalan on this.
    private Node _document;
    private Node _parent;
    private StringBuffer _buffer;
    private Stack _parents = new Stack();

    public SAX2DOMHandler(Node node) {
        _document = node;
        _buffer = new StringBuffer();
    }

    public Node getNode() {
        return _document;
    }

    public void startElement(String uri, String localName,
                             String name, Attributes attributes)
            throws SAXException {
        Node parent = _parents.size() > 0 ? (Node) _parents.peek() : _document;
        Document document;
        if (parent instanceof Document)
            document = (Document) parent;
        else
            document = parent.getOwnerDocument();
        Element element = document.createElement(name);
        int length = attributes.getLength();
        for (int i = 0; i < length; i++) // todo: handle namespaces properly
            element.setAttribute(attributes.getQName(i), attributes.getValue(i));
        parent.appendChild(element);
        _buffer.setLength(0);
        _parents.push(element);
    }

    public void characters(char[] chars, int offset, int length) {
        _buffer.append(chars, offset, length);
    }

    public void endElement(String uri, String localName, String name)
            throws SAXException {
        Node parent = _parents.size() > 0 ? (Node) _parents.peek() : _document;
        if (_buffer.length() != 0) {
            Text text = parent.getOwnerDocument().createTextNode(_buffer.toString());
            parent.appendChild(text);
        }
        _parents.pop();
        _buffer.setLength(0);
    }

}
