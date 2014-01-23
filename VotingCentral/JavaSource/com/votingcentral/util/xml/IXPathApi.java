package com.votingcentral.util.xml;

import java.util.List;

import org.jaxen.FunctionContext;
import org.jaxen.NamespaceContext;
import org.jaxen.VariableContext;

// Referenced classes of package com.aol.organizer.notify.xmlutil:
//            XXMLException

/**
 * 
 * * use XmlUtil.getXPathPathApi().... to get an instance
 *  Why not use jaxen - this api does caching of xpressions
 */
public interface IXPathApi {
    /**
     * Evaluate this XPath against a given context.
     * <p/>
     * <p/>
     * The context of evaluation my be a <i>document</i>,
     * an <i>element</i>, or a set of <i>elements</i>.
     * </p>
     * <p/>
     * <p/>
     * If the expression evaluates to a single primitive
     * (String, Number or Boolean) type, it is returned
     * directly.  Otherwise, the returned value is a
     * List (a <code>node-set</code>, in the terms of the
     * specification) of values.
     * </p>
     * <p/>
     * <p/>
     * When using this method, one must be careful to
     * test the class of the returned objects, and of
     * each of the composite members if a <code>List</code>
     * is returned.  If the returned members are XML entities,
     * they will be the actual <code>Document</code>,
     * <code>Element</code> or <code>Attribute</code> objects
     * as defined by the concrete XML object-model implementation,
     * directly from the context document.  This <b>does not
     * return <i>copies</i> of anything</b>, but merely returns
     * references to entities within the source document.
     * </p>
     *
     * @param node The node, nodeset or Context object for evaluation. This value can be null.
     * @return The result of evaluating the XPath expression
     *         against the supplied context.
     */

    public abstract Object evaluate(Object node, String xpath)
            throws XXMLException;

    /**
     * Select all nodes that are selectable by this XPath
     * expression. If multiple nodes match, multiple nodes
     * will be returned.
     * <p/>
     * <p/>
     * <b>NOTE:</b> In most cases, nodes will be returned
     * in document-order, as defined by the XML Canonicalization
     * specification.  The exception occurs when using XPath
     * expressions involving the <code>union</code> operator
     * (denoted with the pipe '|' character).
     * </p>
     *
     * @param node The node, nodeset or Context object for evaluation. This value can be null.
     * @return The <code>node-set</code> of all items selected
     *         by this XPath expression.
     * @see #selectSingleNode
     */

    public abstract List selectNodes(Object node, String xpath)
            throws XXMLException;

    /**
     * Select only the first node that is selectable by this XPath
     * expression.  If multiple nodes match, only one node will be
     * returned.
     * <p/>
     * <b>NOTE:</b> In most cases, the selected node will be the first
     * selectable node in document-order, as defined by the XML Canonicalization
     * specification.  The exception occurs when using XPath
     * expressions involving the <code>union</code> operator
     * (denoted with the pipe '|' character).
     * </p>
     *
     * @param node The node, nodeset or Context object for evaluation. This value can be null.
     * @return The <code>node-set</code> of all items selected
     *         by this XPath expression.
     * @see #selectNodes
     */

    public abstract Object selectSingleNode(Object node, String xpath)
            throws XXMLException;

    /**
     * Retrieve a boolean-value interpretation of this XPath
     * expression when evaluated against a given context.
     * <p/>
     * <p/>
     * The boolean-value of the expression is determined per
     * the <code>boolean(..)</code> core function as defined
     * in the XPath specification.  This means that an expression
     * that selects zero nodes will return <code>false</code>,
     * while an expression that selects one-or-more nodes will
     * return <code>true</code>.
     * </p>
     *
     * @param node The node, nodeset or Context object for evaluation. This value can be null.
     * @return The boolean-value interpretation of this expression.
     */

    public abstract boolean selectBoolean(Object node, String xpath)
            throws XXMLException;

    /**
     * Retrieve a boolean-value interpretation of this XPath
     * expression when evaluated against a given context.
     * <p/>
     * <p/>
     * The boolean-value of the expression is determined per
     * the <code>boolean(..)</code> core function as defined
     * in the XPath specification.  This means that an expression
     * that selects zero nodes will return <code>false</code>,
     * while an expression that selects one-or-more nodes will
     * return <code>true</code>.
     * </p>
     *
     * @param node The node, nodeset or Context object for evaluation. This value can be null.
     * @return The boolean-value interpretation of this expression.
     */
    public abstract boolean[] selectBooleans(Object node, String xpath)
            throws XXMLException;

    /**
     * Retrieve a string-value interpretation of this XPath
     * expression when evaluated against a given context.
     * <p/>
     * <p/>
     * The string-value of the expression is determined per
     * the <code>string(..)</code> core function as defined
     * in the XPath specification.  This means that an expression
     * that selects more than one nodes will return the string value
     * of the first node in the node set..
     * </p>
     *
     * @param  node object for evaluation. This value can be null
     * @return The string-value interpretation of this expression.
     */
    public abstract String selectString(Object node, String xpath)
            throws XXMLException;

    /**
     * Retrieve a string-value interpretation of this XPath
     * expression when evaluated against a given context.
     * <p/>
     * <p/>
     * The string-value of the expression is determined per
     * the <code>string(..)</code> core function as defined
     * in the XPath specification.  This means that an expression
     * that selects more than one nodes will return the string value
     * of the first node in the node set..
     * </p>
     *
     * @param node object for evaluation. This value can be null
     * @return The string-value interpretation of this expression.
     */
    public abstract String[] selectStrings(Object node, String xpath)
            throws XXMLException;

    /**
     * Retrieve a number-value interpretation of this XPath
     * expression when evaluated against a given context.
     * <p/>
     * <p/>
     * The number-value of the expression is determined per
     * the <code>number(..)</code> core function as defined
     * in the XPath specification. This means that if this
     * expression selects multiple nodes, the number-value
     * of the first node is returned.
     * </p>
     *
     * @param node The node, nodeset or Context object for evaluation. This value can be null.
     * @return The number-value interpretation of this expression.
     */
    public abstract Number selectNumber(Object node, String xpath)
            throws XXMLException;

    /**
     * Retrieve a number-value interpretation of this XPath
     * expression when evaluated against a given context.
     * <p/>
     * <p/>
     * The number-value of the expression is determined per
     * the <code>number(..)</code> core function as defined
     * in the XPath specification. This means that if this
     * expression selects multiple nodes, the number-value
     * of the first node is returned.
     * </p>
     *
     * @param node The node, nodeset or Context object for evaluation. This value can be null.
     * @return The number-value interpretation of this expression.
     */
    public abstract Number[] selectNumbers(Object node, String xpath)
            throws XXMLException;
















    public abstract List selectNodes(Object node, String xpath, NamespaceContext namespacecontext, FunctionContext functioncontext, VariableContext variablecontext)
            throws XXMLException;


    public abstract Object selectSingleNode(Object node, String xpath, NamespaceContext namespacecontext, FunctionContext functioncontext, VariableContext variablecontext)
            throws XXMLException;


    public abstract boolean selectBoolean(Object node, String xpath, NamespaceContext namespacecontext, FunctionContext functioncontext, VariableContext variablecontext)
            throws XXMLException;

    public abstract boolean[] selectBooleans(Object node, String xpath, NamespaceContext namespacecontext, FunctionContext functioncontext, VariableContext variablecontext)
            throws XXMLException;

    public abstract String selectString(Object node, String xpath, NamespaceContext namespacecontext, FunctionContext functioncontext, VariableContext variablecontext)
            throws XXMLException;

    public abstract String[] selectStrings(Object node, String xpath, NamespaceContext namespacecontext, FunctionContext functioncontext, VariableContext variablecontext)
            throws XXMLException;

    public abstract Number selectNumber(Object node, String xpath, NamespaceContext namespacecontext, FunctionContext functioncontext, VariableContext variablecontext)
            throws XXMLException;

    public abstract Number[] selectNumbers(Object node, String xpath, NamespaceContext namespacecontext, FunctionContext functioncontext, VariableContext variablecontext)
            throws XXMLException;
    /**
     * Evaluate this XPath against a given context.
     * <p/>
     * <p/>
     * The context of evaluation my be a <i>document</i>,
     * an <i>element</i>, or a set of <i>elements</i>.
     * </p>
     * <p/>
     * <p/>
     * If the expression evaluates to a single primitive
     * (String, Number or Boolean) type, it is returned
     * directly.  Otherwise, the returned value is a
     * List (a <code>node-set</code>, in the terms of the
     * specification) of values.
     * </p>
     * <p/>
     * <p/>
     * When using this method, one must be careful to
     * test the class of the returned objects, and of
     * each of the composite members if a <code>List</code>
     * is returned.  If the returned members are XML entities,
     * they will be the actual <code>Document</code>,
     * <code>Element</code> or <code>Attribute</code> objects
     * as defined by the concrete XML object-model implementation,
     * directly from the context document.  This <b>does not
     * return <i>copies</i> of anything</b>, but merely returns
     * references to entities within the source document.
     * </p>
     *
     * @param node The node, nodeset or Context object for evaluation. This value can be null.
     * @return The result of evaluating the XPath expression
     *         against the supplied context.
     */
    public abstract Object evaluate(Object node, String xpath, NamespaceContext namespacecontext, FunctionContext functioncontext, VariableContext variablecontext)
            throws XXMLException;
}
