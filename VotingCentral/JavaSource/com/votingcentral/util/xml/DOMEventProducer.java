package com.votingcentral.util.xml;

import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;

// not yet namespace friendly

public class DOMEventProducer implements ISaxEventProducer {


    private ContentHandler _handler = null;

    private Node _node = null;

    /**
     * Creates a new DOMEventProducer
     */
    public DOMEventProducer() {
        super();
    } //-- DOMEventProducer

    /**
     * Creates a new DOMEventProducer for the given Node
     *
     * @param node the node to create the DOMEventProducer for.
     */
    public DOMEventProducer(Node node) {
        super();
        this._node = node;
    } //-- DOMEventProducer


    /**
     * Sets the DocumentHandler to use when firing events
     */
    public void setDocumentHandler(ContentHandler handler) {
        this._handler = handler;
    } //-- setDocumentHandler

    /**
     * Sets the node which is to be converted into SAX events
     *
     * @param node the node which is to be converted into SAX events
     */
    public void setNode(Node node) {
        this._node = node;
    } //-- setNode

    /**
     * Starts producing the events for the Node which is to be
     * converted into SAX events
     */
    public void start()
            throws org.xml.sax.SAXException {
        if ((_node == null) || (_handler == null)) return;

        process(_node, _handler);

    } //-- start

    /**
     * Walks the given DOM Document and converts it into it's corresponding
     * SAX events
     *
     * @param document, the Node to process into SAX events
     * @param handler   the DocumentHandler to send events to
     */
    public static void process(Document document, ContentHandler handler)

            throws org.xml.sax.SAXException {

        if (document == null) return;

        if (handler == null) return;


        handler.startDocument();

        processChildren(document, handler);

        handler.endDocument();


    } //-- process(Document, DocumentHandler)


    /**
     * Breaks down the given node into it's corresponding SAX events
     *
     * @param node,   the Node to process into SAX events
     * @param handler the DocumentHandler to send events to
     */

    public static void process(Node node, ContentHandler handler)

            throws org.xml.sax.SAXException {


        if ((node == null) || (handler == null)) return;


        switch (node.getNodeType()) {

            case Node.DOCUMENT_NODE:

                process((Document) node, handler);

                break;

            case Node.DOCUMENT_FRAGMENT_NODE:

                processChildren(node, handler);

                break;

            case Node.ELEMENT_NODE:

                process((Element) node, handler);

                break;

            case Node.CDATA_SECTION_NODE:

            case Node.TEXT_NODE:

                process((Text) node, handler);

                break;

            case Node.PROCESSING_INSTRUCTION_NODE:

                process((ProcessingInstruction) node, handler);

                break;

            case Node.COMMENT_NODE:


            default:

                break;

        }


    } //-- process(Node, DocumentHandler)



    //-------------------/

    //- Private Methods -/

    //-------------------/



    /**
     * Breaks down the given node into it's corresponding SAX events
     *
     * @param handler the DocumentHandler to send events to
     */

    private static void process(Element element, ContentHandler handler)

            throws org.xml.sax.SAXException {


        String name = element.getNodeName();
        AttributesImpl atts = new AttributesImpl();
        NamedNodeMap domattrs = element.getAttributes();
        for (int i = 0; i < domattrs.getLength(); i++) {
            Attr attr = (Attr) domattrs.item(i);
            atts.addAttribute("", "", attr.getName(), "CDATA", attr.getValue());
        }

        handler.startElement("", "", name, atts);

        processChildren(element, handler);

        handler.endElement("", "", name);

    } //-- process(Element, DocumentHandler);


    /**
     * Breaks down the given node into it's corresponding SAX events
     *
     * @param handler the DocumentHandler to send events to
     */

    private static void process(Text text, ContentHandler handler)

            throws org.xml.sax.SAXException {

        String data = text.getData();

        if ((data != null) && (data.length() > 0)) {

            char[] chars = data.toCharArray();

            handler.characters(chars, 0, chars.length);

        }

    } //-- process(Text, DocumentHandler)


    /**
     * Breaks down the given ProcessingInstruction into it's corresponding
     * <p/>
     * SAX event
     *
     * @param pi      the processing instruction to process
     * @param handler the DocumentHandler to send events to
     */

    private static void process

            (ProcessingInstruction pi, ContentHandler handler)

            throws org.xml.sax.SAXException {

        handler.processingInstruction(pi.getTarget(), pi.getData());


    } //-- process(ProcessingInstruction, DocumentHandler);


    /**
     * Processes the children of the given Node
     *
     * @param node    the Node to process the children of
     * @param handler the DocumentHandler to send events to
     */

    private static void processChildren

            (Node node, ContentHandler handler)

            throws org.xml.sax.SAXException {

        Node child = node.getFirstChild();

        while (child != null) {

            process(child, handler);

            child = child.getNextSibling();

        }


    } //-- processChildren


} //-- DOMEventProducer

