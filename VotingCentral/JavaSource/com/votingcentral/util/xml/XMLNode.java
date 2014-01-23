package com.votingcentral.util.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * XMLNode: Class to represent a single meaningful node in XML. It has
 * name, value, elements and attributes.
 * <p/>
 * value will be null or meaningless if elements exist.
 * <p/>
 * elements and attributes are implemented using LinkedHashMap which helps
 * to get to a particular element directly, and also preserves the order of
 * the elements.
 * <p/>
 * XMLNode currently DOES NOT address namespaces, comments, processing
 * instructions, etc.
 * <p/>
 * Very minimum validation is done. No validation is done to detect loops in a
 * tree
 *
 */

public class XMLNode implements Cloneable {

    protected String _name = null;
    protected String _value = null;
    protected XMLNode _parent = null;

    protected LinkedHashMap attributes = null;
    protected LinkedHashMap elements = null;

    private static int ALLOW_XMLNODE = 0x0000;
    private static int ALLOW_NULL = 0x0001;
    private static int ALLOW_LIST = 0x0002;

    private boolean hasCDATA = false;

    public static final String ROOT = "__DocumentRoot__";

    // Constructor
    public XMLNode(String name, String value) {
        this(name, value, false);
    }

    public XMLNode(String name, String value, boolean cdata) {
        _name = name;
        if ((value != null) && !cdata) {
            _value = XmlUtil.escapeXMLSpecialChars(value);
        } else {
            _value = value;
        }
        hasCDATA = cdata;
        elements = new LinkedHashMap();
        attributes = new LinkedHashMap();
    }

    // Set/Get methods

    public String getName() {
        return _name;
    }

    public String getValue() {
        if ((_value != null) && !hasCDATA) {
            return XmlUtil.unescapeXMLSpecialChars(_value);
        }
        return _value;
    }

    public XMLNode getParent() {
        return _parent;
    }

    public void setName(String val) {
        _name = val;
    }

    public void setValue(String value) throws XXMLException {
        setValue(value, false);
    }

    /**
     * Sets the Value of this node
     *
     * @param value the value to be set
     * @param cdata true if the value is a CDATA
     * @throws XXMLException If attempt is made to set a value for non-leaf element
     */
    public void setValue(String value, boolean cdata) throws XXMLException {
        if (elements.size() > 0) {
            throw new XXMLException(XXMLException.INVALID_OPERATION,
                    "Can not set value for non leaf node.", null);
        }
        if ((value != null) && !cdata) {
            _value = XmlUtil.escapeXMLSpecialChars(value);
        } else {
            _value = value;
        }
        hasCDATA = cdata;
    }

    public void setParent(XMLNode val) {
        _parent = val;
    }

    public void addAttribute(String name, String value) {
        attributes.put(name, value);
    }

    public String getAttribute(String name) {
        return (String) (attributes.get(name));
    }

    /**
     * Checks if this node is a root Node (Not the top node)
     * To get the top node or root element use getRootElement() method
     *
     * @return true if the node is a Root node
     */
    public boolean isRoot() {
        return ROOT.equals(_name);
    }

    /**
     * Checks is this node is a leaf Node
     *
     * @return true is leafNode
     */
    public boolean isLeafNode() {
        if ((elements == null) || elements.isEmpty()) return true;
        return false;
    }

    public boolean hasCDATA() {
        return hasCDATA;
    }

    /**
     * To get the RootElement for this node
     *
     * @return the XMLNode associated with the Root Element (Not the document Root)
     */
    public XMLNode getRootElement() {
        if (!isRoot()) return _parent.getRootElement();
        ArrayList list = new ArrayList(elements.values());
        if (list != null) return (XMLNode) list.get(0);
        return null;
    }

    // Helper Methods

    /**
     * Helper method to check the value
     */
    private void checkNull(String value) throws XXMLException {
        if ((value == null) || (value.trim().length() == 0)) {
            throw(new XXMLException(XXMLException.EMPTY_PATH,
                    "Path must not be NULL or Empty", null));
        }
    }

    /**
     * Helper method to check if value is empty
     */
    private boolean isEmpty(String value) {
        if ((value == null) || (value.trim().length() == 0))
            return true;

        return false;
    }

    /**
     * Helper method to get the object from Elements
     */
    private Object getElement(String value, int flag)
            throws XXMLException {

        Object o = elements.get(value);
        if ((o == null) && ((flag & ALLOW_NULL) != ALLOW_NULL)) {
            throw(new XXMLException(XXMLException.NON_EXISTING_NODE,
                    "No node exists for: " + value, null));
        }
        if (((flag & ALLOW_LIST) != ALLOW_LIST) && (o instanceof LinkedList)) {
            throw(new XXMLException(XXMLException.AMBIGUOUS_PATH,
                    "More than one node exists for: " + value, null));
        }
        return o;
    }

    /**
     * Returns the Children on this Node as a List
     *
     * @return
     */
    public LinkedList getChildren() {
        LinkedList retList = new LinkedList();

        Iterator keysIterator = elements.keySet().iterator();
        while (keysIterator.hasNext()) {
            Object o = elements.get(keysIterator.next());
            if (o instanceof XMLNode) {
                retList.add(o);
            } else if (o instanceof LinkedList) {
                retList.addAll((LinkedList) o);
            }
        }
        return retList;
    }

    /**
     * Method to remove this node from the parent
     */
    public void delete() {
        if (_parent != null) {
            _parent.removeChild(this);
            _parent = null;
        }
    }

    /**
     * Method to replace this node by provided node
     *
     * @param node
     */
    public void replace(XMLNode node) {
        // replace everything except parent
        _name = node._name;
        _value = node._value;
        attributes = node.attributes;
        elements = node.elements;
    }

    /**
     * Method to add a given XMLNode as a child of this node
     * This method is used while constructing the initial XMLNode tree
     * structure
     */

    public void addChild(XMLNode e) throws XXMLException {

        if (!isEmpty(_value)) {
            throw new XXMLException(XXMLException.INVALID_OPERATION,
                    "Can not add child to a leaf node.", null);
        }

        if (e == null) return;
        String eName = e.getName();

        e.setParent(this);
        Object entry = elements.get(eName);
        if (entry == null) {
            elements.put(eName, e);
        } else {
            if (entry instanceof XMLNode) {
                LinkedList list = new LinkedList();
                list.add(entry);
                list.add(e);
                elements.put(eName, list);
            } else if (entry instanceof LinkedList) {
                ((LinkedList) entry).add(e);
            }
        }
    }

    /**
     * Method to remove all children node maching specified Name
     */
    public void removeChild(String nodeName) {
        elements.remove(nodeName);
    }

    /**
     * Method to remove a child by itself. If the node being passed is not child, no action is taken
     *
     * @param node
     */
    public void removeChild(XMLNode node) {
        Object o = elements.get(node._name);
        if (o instanceof XMLNode) {
            if ((XMLNode) o == node) elements.remove(node._name);
            return;
        }
        if (o instanceof LinkedList) {
            LinkedList list = (LinkedList) o;
            list.remove(node);
        }
    }

    /**
     * Method to delete a subtree located at specified path
     */

    public void deleteTree(String value) throws XXMLException {

        LinkedList list = getNodeList(value);

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                ((XMLNode) list.get(i)).delete();
            }
        }
    }

    /**
     * This method removes the specified node only, it makes the children of delted node as children of deleted
     * node's parent.
     *
     * @param value
     * @throws XXMLException if ambigeous nodes are found
     */
    public void removeNode(String value) throws XXMLException {
        XMLNode node = getNode(value);
        LinkedList list = node.getChildren();

        for (int i = 0; i < list.size(); i++) {
            XMLNode tNode = (XMLNode) list.get(i);
            node._parent.addChild(tNode);
        }
        node.delete();
    }

    private LinkedList parseQuery(String query) {
        int idx = query.indexOf("?");
        String nodeName = null;
        String conditions[] = null;

        if (idx != -1) {
            nodeName = query.substring(0, idx);
            query = query.substring(idx + 1);
            conditions = query.split("&");
        } else if (query.indexOf("=") == -1) {
            nodeName = query;
            conditions = null;
        } else {
            conditions = query.split("&");
        }

        LinkedList list = new LinkedList();
        list.add(nodeName);
        list.add(conditions);

        return list;
    }

    /**
     * convenience method to check if a give node mathces to given query
     */
    public boolean isMatch(String query) {
        LinkedList list = parseQuery(query);
        return isMatch((String) list.get(0), (String[]) list.get(1));
    }

    /**
     * This method checks if given node satifies the given conditions
     * Method to process preparsed query
     */
    protected boolean isMatch(String nodeName, String[] conditions) {

        int eq_idx = -1;
        int i = 0;
        String attr_value = null;
        String srch_value = null;

        if ((nodeName != null) && (nodeName.equals(_name) == false)) return false;
        if (conditions == null) return true;

        for (i = 0; i < conditions.length; i++) {
            eq_idx = conditions[i].indexOf('=');
            attr_value = getAttribute(conditions[i].substring(0, eq_idx));
            srch_value = conditions[i].substring(eq_idx + 1);

            if (!(srch_value.equals(attr_value))) {
                break;
            }
        }

        if (i == conditions.length) return true;

        return false;
    }

    /**
     * This method returns the first node in the list matching the give query
     * query should be of format a=1&b=2&c=3
     * currently no format validation on query is done
     */
    public XMLNode getFirstMatchingNode(LinkedList list, String query) {
        XMLNode node = null;

        LinkedList qlist = parseQuery(query);
        String nodeName = (String) qlist.get(0);
        String conditions[] = (String[]) qlist.get(1);

        if (list != null) {
            int i = 0;
            for (i = 0; i < list.size(); i++) {
                node = (XMLNode) list.get(i);
                if (node.isMatch(nodeName, conditions))
                    break;
            }
            if (i == list.size()) node = null;
        }

        return node;
    }

    /**
     * This method returns the all the nodes matching the given query
     * query path should be of format a=1&b=2&c=3
     * currently no format validation on query is done
     */
    public LinkedList getMatchingList(LinkedList list, String query) {

        LinkedList returnList = new LinkedList();
        XMLNode node = null;
        LinkedList qlist = parseQuery(query);
        String nodeName = (String) qlist.get(0);
        String conditions[] = (String[]) qlist.get(1);

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                node = (XMLNode) list.get(i);
                if (node.isMatch(nodeName, conditions)) returnList.add(node);
            }
        }

        return returnList;
    }

    public LinkedList getMatchingChildren(String query) {
        return getMatchingList(getChildren(), query);
    }

    /**
     * Internal private method to return the XMLNode or LinkedList
     * depending upon the flag
     *
     * @param flag  ALLOW_XMLNODE or ALLOW_LIST
     * @param value
     * @return
     * @throws XXMLException
     */

    private Object getNode(int flag, String value) throws XXMLException {

        if (isEmpty(value)) return null;

        int idx = value.indexOf('/');
        String curr_children_path = null;
        String remaining_path = null;

        if (idx == -1) {
            curr_children_path = value;
        } else {
            curr_children_path = value.substring(0, idx);
            remaining_path = value.substring(idx + 1);
        }

        LinkedList list = getMatchingChildren(curr_children_path);

        if ((list == null) || (list.isEmpty())) return null;

        if (list.size() == 1) {
            XMLNode xnode = (XMLNode) list.getFirst();
            if (remaining_path != null) return xnode.getNode(flag, remaining_path);

            if (flag == ALLOW_XMLNODE) return xnode;
            if (flag == ALLOW_LIST) return list;
        }

        if ((flag == ALLOW_XMLNODE) || (remaining_path != null)) {
            throw(new XXMLException(XXMLException.AMBIGUOUS_PATH,
                    "More than one node exists for: " + curr_children_path, null));
        }

        return list;
    }

    /**
     * This method returns the XMLNode located at the given path. The path
     * should have format like a/b/c/d?attr1=v1&attr2=v2
     *
     * @param value
     * @return XMLNode if found otherwise null
     * @throws XXMLException If multiple nodes are found.
     */
    public XMLNode getNode(String value) throws XXMLException {
        return (XMLNode) getNode(ALLOW_XMLNODE, value);
    }

    /**
     * This method returns the List of nodes located at the given path. The path
     * should have format like a/b/c/d?a=1&b=2
     */

    public LinkedList getNodeList(String value) throws XXMLException {
        return (LinkedList) getNode(ALLOW_LIST, value);
    }

    /**
     * This method returns the object located at the given path. The path
     * should have format like a/b/c?a=val/d:attr
     */

    public Object getValue(String value) throws XXMLException {

        checkNull(value);

        XMLNode targetNode = this;
        String currPath = value;
        int lastidx = value.lastIndexOf('/');
        if (lastidx != -1) {
            targetNode = getNode(value.substring(0, lastidx));
            currPath = value.substring(lastidx + 1);
        }

        // If middle node in the path does not exist, return null
        if (targetNode == null) return null;

        int attr_idx = currPath.indexOf(':');
        String nodePath = null;
        String attrName = null;

        if (attr_idx == -1) {
            nodePath = currPath;
        } else {
            nodePath = currPath.substring(0, attr_idx);
            attrName = currPath.substring(attr_idx + 1);
        }
        LinkedList list = targetNode.getNodeList(nodePath);
        if ((list == null) || (list.isEmpty())) return null;
        if (list.size() == 1) {
            if (attr_idx == -1) {
                return ((XMLNode) list.get(0)).getValue();
            }
            return ((XMLNode) list.get(0)).getAttribute(attrName);
        } else {
            if (attr_idx == -1) {
                return list;
            }
            throw(new XXMLException(XXMLException.AMBIGUOUS_PATH,
                    "More than one node exists for: " + nodePath, null));
        }
    }

    public String getValueAsString(String value) throws XXMLException {
        return getValueAsString(value, null);
    }

    /**
     * This method returns the value at a given path as a String. If more than
     * 1 node exist for a given path then the value is concatenated using the
     * specified delimiter (default ;)
     */
    public String getValueAsString(String value, String delim) throws XXMLException {

        Object o = getValue(value);

        if (o != null) {
            if ((delim == null) || (delim.trim().length() == 0)) {
                delim = ";";
            }
            if (o instanceof String)
                return (String) o;
            else if (o instanceof LinkedList) {
                String retVal = "";
                ListIterator li = ((LinkedList) o).listIterator();
                while (li.hasNext()) {
                    retVal += ((XMLNode) li.next()).getValue() + delim;
                }
                return retVal;
            }
        }
        return null;
    }

    /**
     * This method checks if given path exists
     */
    public boolean checkPath(String path) {

        try {
            if (getNodeList(path) != null)
                return true;
        } catch (XXMLException e) {
        }
        return false;
    }

    /**
     * Returns the Path of this node in the current XML Document
     *
     * @return
     */

    public String getPath() {
        if (isRoot()) return "";
        StringBuffer myPath = new StringBuffer(_parent.getPath());
        if (_parent.isRoot() == false) myPath.append("/");
        myPath.append(_name);

        if (attributes.size() > 0) {
            myPath.append("?");
            Iterator keyIterator = attributes.keySet().iterator();

            while (keyIterator.hasNext()) {
                String key = (String) keyIterator.next();
                myPath.append(key).append("=").append((String) attributes.get(key)).append("&");
            }
            myPath.setLength(myPath.length() - 1);
        }
        return myPath.toString();
    }

    /**
     * This method checks if nodes exist in a path, creates the node if it
     * does not exist or it is a part of the list
     * If path contains attributeList like /A/b?id=1&wer=2 a node is searched matching that path
     * IF not found then a node is created with that attribute list
     * If multiple match is found; exception is thrown
     */
    protected XMLNode createPath(String path) throws XXMLException {

        checkNull(path);

        int idx = path.indexOf('/');
        Object entry;

        if (idx == -1) {
            // Check if the path has attributes specified
            int srch_idx = path.indexOf('?');
            if (srch_idx == -1) {
                entry = getElement(path, ALLOW_NULL | ALLOW_LIST);
                if ((entry == null) || (entry instanceof LinkedList)) {
                    XMLNode xnode = new XMLNode(path, null);
                    addChild(xnode);
                    return xnode;
                }
                return (XMLNode) entry;

            } else {

                entry = getElement(path.substring(0, srch_idx), ALLOW_LIST | ALLOW_NULL);
                if (entry == null) {
                    XMLNode xnode = new XMLNode(path.substring(0, srch_idx), null);
                    addChild(xnode);
                    return xnode;
                }

                if (entry instanceof LinkedList) {
                    LinkedList list = getMatchingList((LinkedList) entry,
                            path.substring(srch_idx + 1));
                    if (list != null) {
                        if (list.size() == 1) return (XMLNode) list.getFirst();
                        throw(new XXMLException(XXMLException.AMBIGUOUS_PATH,
                                "More than one node exists for: " + path, null));
                    }
                    // Add the node with attribute list
                    XMLNode xnode = new XMLNode(path.substring(0, srch_idx), null);
                    String[] attrlist = path.substring(srch_idx + 1).split("&");
                    int eq_idx = -1;
                    for (int i = 0; i < attrlist.length; i++) {
                        eq_idx = attrlist[i].indexOf('=');
                        xnode.addAttribute(attrlist[i].substring(0, eq_idx), attrlist[i].substring(eq_idx + 1));
                    }
                    addChild(xnode);
                    return xnode;
                }

                if (((XMLNode) entry).isMatch(path.substring(srch_idx + 1))) {
                    return (XMLNode) entry;
                }

                // Add the node with attribute list
                XMLNode xnode = new XMLNode(path.substring(0, srch_idx), null);
                String[] attrlist = path.substring(srch_idx + 1).split("&");
                int eq_idx = -1;
                for (int i = 0; i < attrlist.length; i++) {
                    eq_idx = attrlist[i].indexOf('=');
                    xnode.addAttribute(attrlist[i].substring(0, eq_idx), attrlist[i].substring(eq_idx + 1));
                }
                addChild(xnode);
                return xnode;
            }
        } else {
            // check if we have attributes specified in currentNodePath
            String currentNodePath = path.substring(0, idx);
            int srch_idx = currentNodePath.indexOf('?');
            String currNodeName = null;
            String attrStr = null;
            if (srch_idx == -1) {
                currNodeName = currentNodePath;
            } else {
                currNodeName = currentNodePath.substring(0, srch_idx);
                attrStr = currentNodePath.substring(srch_idx + 1);
            }

            entry = getElement(currNodeName, ALLOW_NULL | ALLOW_LIST);
            if (entry instanceof LinkedList) {
                if (attrStr == null) {
                    entry = null;
                } else {
                    LinkedList lst = getMatchingList((LinkedList) entry, attrStr);
                    if ((lst == null) || (lst.size() == 0)) {
                        entry = null;
                    } else {
                        if (lst.size() == 1) {
                            XMLNode xnode = (XMLNode) lst.getFirst();
                            return xnode.createPath(path.substring(idx + 1));
                        } else {
                            throw new XXMLException("Ambigeous path: " + currentNodePath);
                        }
                    }
                }
            }
            if (entry == null) {
                XMLNode xnode = new XMLNode(currNodeName, null);
                // If attribute exist add them while creating a node
                if (attrStr != null) {
                    String[] attrlist = attrStr.split("&");
                    int eq_idx = -1;
                    for (int i = 0; i < attrlist.length; i++) {
                        eq_idx = attrlist[i].indexOf('=');
                        xnode.addAttribute(attrlist[i].substring(0, eq_idx), attrlist[i].substring(eq_idx + 1));
                    }
                }
                addChild(xnode);
                return xnode.createPath(path.substring(idx + 1));
            }


            return ((XMLNode) entry).createPath(path.substring(idx + 1));
        }
    }

    /**
     * This method sets the value in the existing node associated with given path,
     * if node does not exist
     * adds all the missing nodes in the path and sets the value of the leaf node
     */
    public void setValue(String path, String value) throws XXMLException {
        XMLNode node = createPath(path);
        node.setValue(value);
    }

    /**
     * This method adds the node to the existing node associated with given path,
     * if node does not exist
     * adds all the missing nodes in the path and adds the given node as a child
     */
    public void addNode(String path, XMLNode node) throws XXMLException {
        XMLNode pnode = createPath(path);
        pnode.addChild(node);
    }

    /**
     * This method adds the node to the existing node associated with given path,
     * if node does not exist
     * adds all the missing nodes in the path and adds the given node as a child
     * This method adds node only if the same node does not exist under the
     * same parent. If same node exists then the node is replaced if replace is set to true
     *
     * @param path    The path of the parent
     * @param node    The node to be inserted
     * @param replace true if this node should replace the existing node
     * @return true if new node is added otherwise returns false
     * @throws XXMLException
     */
    public boolean addUniqNode(String path, XMLNode node, boolean replace) throws XXMLException {
        XMLNode pnode = createPath(path);

        return pnode.addUniqNode(node, replace);
    }

    /**
     * This method adds the given node as a child node only if the same node
     * does not exist as a child node
     *
     * @return true if new node is added otherwise returns false
     */
    public boolean addUniqNode(XMLNode node, boolean replace) throws XXMLException {

        // Check if the node to be merged has any attributes, if yes suply those in
        // path in format ?attr1=val&attr2=val
        String currPath = null;
        if ((node.attributes == null) || (node.attributes.isEmpty())) {
            currPath = node._name;
        } else {
            Iterator attrIter = node.attributes.keySet().iterator();
            for (int i = 0; attrIter.hasNext(); i++) {
                String attr_name = (String) attrIter.next();
                if (i == 0)
                    currPath = node._name + "?";
                else
                    currPath += "&";
                currPath += attr_name + "=" + node.getAttribute(attr_name);
            }
        }

        LinkedList list = getMatchingList(getChildren(), currPath);
        if (list != null) {
            if (list.size() == 1) {
                XMLNode xnode = (XMLNode) list.getFirst();
                if (xnode.equals(node)) return true;
                if (replace) {
                    xnode.replace(node);
                    return true;
                }
            } else {
                for (int i = 0; i < list.size(); i++) {
                    XMLNode xnode = (XMLNode) list.get(i);
                    if (xnode.equals(node)) return true;
                }
            }
        }
        addChild(node);
        return true;
    }

    /**
     * This method merges all of this nodes leaf nodes to the toNode
     *
     * @param path   path of this node with respective to the root of the node
     * @param toNode the destination node
     * @throws XXMLException
     */
    public void mergeLeafNodes(String path, XMLNode toNode) throws XXMLException {

        checkNull(path);
        LinkedList list = getChildren();
        String currPath = null;
        for (int k = 0; k < list.size(); k++) {
            currPath = path;
            XMLNode xmlNode = (XMLNode) list.get(k);

            if (xmlNode.isLeafNode()) {
                toNode.addUniqNode(currPath, xmlNode, true);
            } else {
                // Check if the node to be merged has any attributes, if yes suply those in
                // path in format ?attr1=val&attr2=val
                if ((xmlNode.attributes == null) || (xmlNode.attributes.isEmpty())) {
                    currPath += "/" + xmlNode._name;
                } else {
                    Iterator attrIter = xmlNode.attributes.keySet().iterator();
                    for (int i = 0; attrIter.hasNext(); i++) {
                        String attr_name = (String) attrIter.next();
                        if (i == 0)
                            currPath += "/" + xmlNode._name + "?";
                        else
                            currPath += "&";
                        currPath += attr_name + "=" + xmlNode.getAttribute(attr_name);
                    }
                }
                xmlNode.mergeLeafNodes(currPath, toNode);
            }
        }
    }

    /**
     * This method merges the XML associated with the sourceNode into the XML denoted by this node.
     * The matching elements from sourceNode will overwrite the one present in this node
     *
     * @param fromNode The XMLNode denoting the xml required to be merged in
     * @throws XXMLException
     */
    public void merge(XMLNode fromNode) throws XXMLException {

        XMLNode toNode = this;

        if (toNode.equals(fromNode)) return;

        // The top level names of both the nodes must match
        if (!(_name.equals(fromNode._name))) {
            throw new XXMLException("The name of top level elements must match for merging.");
        }
        if (fromNode.isLeafNode()) {
            throw new XXMLException("The XML required to be merged should atleast be 1 level deep");
        }

        if (isRoot()) {
            fromNode = fromNode.getRootElement();
        }

        fromNode.mergeLeafNodes(fromNode.getName(), toNode);
    }

    public Object clone() {

        XMLNode cNode = null;
        try {
            cNode = (XMLNode) super.clone();
        } catch (CloneNotSupportedException e) {
            // This should not happen since we implement clonable
            return null;
        }

        cNode._parent = null;
        cNode.attributes = (LinkedHashMap) attributes.clone();
        cNode.elements = new LinkedHashMap();

        // Change the parentID in all children
        LinkedList list = getChildren();
        for (int i = 0; i < list.size(); i++) {
            XMLNode childNode = (XMLNode) list.get(i);
            try {
                cNode.addChild((XMLNode) (childNode.clone()));
            } catch (XXMLException e) {
            }
        }

        return cNode;
    }


    /**
     * This method returns the XML String for this node and all it's descendents
     * appendNewLine flag is setto false
     */

    public String getXML() {
        return getXMLInternal(false, "");
    }

    public String getXML(boolean indent) {
        return getXMLInternal(indent, "");
    }

    /**
     * This method returns the XML String for this node and all it's
     * descendents
     */
    /**
     * This method returns the XML String for this node and all it's descendents
     *
     * @param indent append new line after each node XML is this flag is true
     * @return
     */

    protected String getXMLInternal(boolean indent, String indentStr) {

        Iterator i = null;

        if (isRoot()) {
            // return the xml of the child
            i = elements.values().iterator();
            if (i.hasNext()) {
                return ((XMLNode) (i.next())).getXMLInternal(indent, indentStr);
            } else {
                return "";
            }
        }

        StringBuffer retStr = new StringBuffer((indent ? indentStr : "") + "<" + _name);
        String aName = null;
        Object o = null;

        if ((attributes != null) && (attributes.size() != 0)) {
            i = attributes.keySet().iterator();

            while (i.hasNext()) {
                aName = (String) (i.next());
                retStr.append(" " + aName + "=\"" +
                        (String) (attributes.get(aName)) + "\"");
            }
        }
        retStr.append(">");
        if (_value != null) {
            if (hasCDATA) retStr.append("<![CDATA[");
            retStr.append(_value);
            if (hasCDATA) retStr.append("]]>");
        } else if (elements.size() != 0) {

            if (indent) retStr.append("\n");

            i = elements.keySet().iterator();

            while (i.hasNext()) {
                o = elements.get(i.next());

                if (o instanceof XMLNode) {
                    retStr.append(((XMLNode) o).getXMLInternal(indent, indentStr + "  "));
                } else if (o instanceof LinkedList) {
                    ListIterator li = ((LinkedList) o).listIterator();
                    while (li.hasNext()) {
                        retStr.append(((XMLNode) li.next()).getXMLInternal(indent, indentStr + "  "));
                    }
                }
            }
        }

        return retStr.toString() + ((indent && (elements.size() != 0)) ? indentStr : "") + "</" + _name + ">" + (indent ? "\n" : "");
    }

    public String toString() {

        Iterator i = null;

        if (isRoot()) {
            // return the xml of the child
            i = elements.values().iterator();
            if (i.hasNext()) {
                return i.next().toString();
            } else {
                return "";
            }
        }

        StringBuffer retStr = new StringBuffer(_name + "(");
        String aName = null;
        Object o = null;

        if ((attributes != null) && (attributes.size() != 0)) {
            retStr.append("attrs=[");
            i = attributes.keySet().iterator();

            while (i.hasNext()) {
                aName = (String) (i.next());
                retStr.append(" " + aName + "=\"" +
                        (String) (attributes.get(aName)) + "\"");
            }
        }
        retStr.append("] ");
        if (_value != null) {
            if (hasCDATA) retStr.append("<![CDATA[");

            retStr.append("value=" + (hasCDATA ? "<![CDATA[" : "") + _value + (hasCDATA ? "]]>" : ""));
        } else if (elements.size() != 0) {

            retStr.append("Elems={");
            i = elements.keySet().iterator();

            while (i.hasNext()) {
                o = elements.get(i.next());

                if (o instanceof XMLNode) {
                    retStr.append(o.toString());
                } else if (o instanceof LinkedList) {
                    ListIterator li = ((LinkedList) o).listIterator();
                    while (li.hasNext()) {
                        retStr.append(li.next().toString());
                    }
                }
            }
        }

        return retStr.toString() + ")\n";
    }

    /**
     * Returns the Map representation of this XMLNode
     *
     * @return
     */
    public HashMap getMap() {

        HashMap retMap = new HashMap();
        Iterator i = null;

        if (isRoot()) {
            // return the xml of the child
            i = elements.values().iterator();
            if (i.hasNext()) {
                return ((XMLNode) (i.next())).getMap();
            } else {
                return null;
            }
        }

        i = elements.keySet().iterator();
        Object o;

        if (_value != null) {
            retMap.put(getPath(), _value);
        } else if (elements.size() > 0) {
            while (i.hasNext()) {
                o = elements.get(i.next());

                if (o instanceof XMLNode) {
                    retMap.putAll(((XMLNode) o).getMap());
                } else if (o instanceof LinkedList) {
                    ListIterator li = ((LinkedList) o).listIterator();
                    while (li.hasNext()) {
                        retMap.putAll(((XMLNode) li.next()).getMap());
                    }
                }
            }
        }

        return retMap;
    }

    /**
     * This method overwrites the equals method of Object class
     * 2 XMLNodes are same if they have same Name, Value and attributes
     */
    public boolean equals(Object o) {

        if (!(o instanceof XMLNode)) return false;
        XMLNode node = (XMLNode) o;

        if ((_name.equals(node._name)) &&
                (_value == null ? node._value == null : _value.equals(node._value)) &&
                (attributes.equals(node.attributes)) &&
                (elements.equals(node.elements)))

            return true;

        return false;
    }
}
