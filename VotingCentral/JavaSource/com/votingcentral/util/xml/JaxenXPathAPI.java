package com.votingcentral.util.xml;

import java.util.Iterator;
import java.util.List;

import org.jaxen.FunctionContext;
import org.jaxen.NamespaceContext;
import org.jaxen.Navigator;
import org.jaxen.SimpleFunctionContext;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.SimpleVariableContext;
import org.jaxen.VariableContext;
import org.jaxen.dom.DOMXPath;
import org.jaxen.function.BooleanFunction;
import org.jaxen.function.NumberFunction;
import org.jaxen.function.StringFunction;

// Referenced classes of package com.aol.organizer.notify.xmlutil:
//            SimpleLruCacheNoSync, XXMLException, IXPathApi

public class JaxenXPathAPI implements IXPathApi {

	public JaxenXPathAPI() {
	}

	private SimpleLruCacheNoSync getXPathExprTldMap() {
		SimpleLruCacheNoSync m = (SimpleLruCacheNoSync) xpathexprtld.get();
		if (m == null) {
			m = new SimpleLruCacheNoSync(MAXTLDCACHESIZE);
			xpathexprtld.set(m);
		}
		return m;
	}

	DOMXPath getXPathExpr(String xpathstr) throws XXMLException {
		SimpleLruCacheNoSync m = getXPathExprTldMap();
		DOMXPath res = (DOMXPath) m.get(xpathstr);
		if (res == null)
			try {
				res = new DOMXPath(xpathstr);
				m.put(xpathstr, res);
			} catch (Exception e) {
				throw new XXMLException(
					"Exception while compiling xpath " + e.getMessage(),
					e);
			}
		return res;
	}

	public Object evaluate(Object node, String xpathString)
		throws XXMLException {
		try {
			return getXPathExpr(xpathString).evaluate(node);
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}
	}

	public List selectNodes(Object node, String xpathString)
		throws XXMLException {
		try {
			return getXPathExpr(xpathString).selectNodes(node);
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}
	}

	public Object selectSingleNode(Object node, String xpathString)
		throws XXMLException {
		try {
			return getXPathExpr(xpathString).selectSingleNode(node);
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}
	}

	public boolean selectBoolean(Object node, String xpathString)
		throws XXMLException {
		try {
			return getXPathExpr(xpathString).booleanValueOf(node);
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}
	}

	private boolean[] nodesToBoolean(Navigator navigator, List results) {
		if (results == null)
			return new boolean[0];
		boolean res[];
		res = new boolean[results.size()];
		Iterator iterator = results.iterator();
		int cnt = 0;
		while (iterator.hasNext()) {
			Object result = (Object) iterator.next();
			res[cnt++] =
				BooleanFunction.evaluate(result, navigator).booleanValue();
		}
		return res;
	}
	public boolean[] selectBooleans(Object node, String xpathString)
		throws XXMLException {
		try {
			DOMXPath expression = getXPathExpr(xpathString);
			return nodesToBoolean(
				expression.getNavigator(),
				expression.selectNodes(node));
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}
	}

	public String selectString(Object node, String xpathString)
		throws XXMLException {
		try {
			return getXPathExpr(xpathString).stringValueOf(node);
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}
	}
	private String[] nodesToStrings(Navigator navigator, List results) {
		if (results == null)
			return new String[0];
		String res[];
		res = new String[results.size()];
		Iterator iterator = results.iterator();
		int cnt = 0;
		while (iterator.hasNext()) {
			Object result = (Object) iterator.next();
			res[cnt++] = StringFunction.evaluate(result, navigator);
		}
		return res;
	}

	public String[] selectStrings(Object node, String xpathString)
		throws XXMLException {
		try {
			DOMXPath expression = getXPathExpr(xpathString);
			return nodesToStrings(
				expression.getNavigator(),
				expression.selectNodes(node));
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}
	}

	public Number selectNumber(Object node, String xpathString)
		throws XXMLException {
		try {
			return getXPathExpr(xpathString).numberValueOf(node);
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}
	}

	private Number[] nodesToNumbers(Navigator navigator, List results) {
		if (results == null)
			return new Number[0];
		Number res[];
		res = new Number[results.size()];
		Iterator iterator = results.iterator();
		int cnt = 0;
		while (iterator.hasNext()) {
			Object result = (Object) iterator.next();
			res[cnt++] = NumberFunction.evaluate(result, navigator);
		}
		return res;
	}

	public Number[] selectNumbers(Object node, String xpathString)
		throws XXMLException {
		try {
			DOMXPath expression = getXPathExpr(xpathString);
			return nodesToNumbers(
				expression.getNavigator(),
				expression.selectNodes(node));
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}
	}

	private SimpleLruCacheNoSync getXPathExprTldMapSpecial() {
		SimpleLruCacheNoSync m = (SimpleLruCacheNoSync) xpathexprtld.get();
		if (m == null) {
			m = new SimpleLruCacheNoSync(MAXTLDCACHESIZE);
			xpathexprtld.set(m);
		}
		return m;
	}

	public List selectNodes(
		Object node,
		String xpath,
		NamespaceContext namespacecontext,
		FunctionContext functioncontext,
		VariableContext variablecontext)
		throws XXMLException {
		try {
			return getXPathExprSpecial(
				xpath,
				namespacecontext,
				functioncontext,
				variablecontext).selectNodes(
				node);
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}

	}

	public Object selectSingleNode(
		Object node,
		String xpath,
		NamespaceContext namespacecontext,
		FunctionContext functioncontext,
		VariableContext variablecontext)
		throws XXMLException {
		try {
			return getXPathExprSpecial(
				xpath,
				namespacecontext,
				functioncontext,
				variablecontext).selectSingleNode(
				node);
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}

	}

	public boolean selectBoolean(
		Object node,
		String xpath,
		NamespaceContext namespacecontext,
		FunctionContext functioncontext,
		VariableContext variablecontext)
		throws XXMLException {
		try {
			return getXPathExprSpecial(
				xpath,
				namespacecontext,
				functioncontext,
				variablecontext).booleanValueOf(
				node);
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}

	}

	public boolean[] selectBooleans(
		Object node,
		String xpath,
		NamespaceContext namespacecontext,
		FunctionContext functioncontext,
		VariableContext variablecontext)
		throws XXMLException {
		try {
			DOMXPath expression =
				getXPathExprSpecial(
					xpath,
					namespacecontext,
					functioncontext,
					variablecontext);
			return nodesToBoolean(
				expression.getNavigator(),
				expression.selectNodes(node));
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}

	}

	public String selectString(
		Object node,
		String xpath,
		NamespaceContext namespacecontext,
		FunctionContext functioncontext,
		VariableContext variablecontext)
		throws XXMLException {
		try {
			return getXPathExprSpecial(
				xpath,
				namespacecontext,
				functioncontext,
				variablecontext).stringValueOf(
				node);
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}

	}

	public String[] selectStrings(
		Object node,
		String xpath,
		NamespaceContext namespacecontext,
		FunctionContext functioncontext,
		VariableContext variablecontext)
		throws XXMLException {
		try {
			DOMXPath expression =
				getXPathExprSpecial(
					xpath,
					namespacecontext,
					functioncontext,
					variablecontext);
			return nodesToStrings(
				expression.getNavigator(),
				expression.selectNodes(node));
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}

	}

	public Number selectNumber(
		Object node,
		String xpath,
		NamespaceContext namespacecontext,
		FunctionContext functioncontext,
		VariableContext variablecontext)
		throws XXMLException {
		try {
			return getXPathExprSpecial(
				xpath,
				namespacecontext,
				functioncontext,
				variablecontext).numberValueOf(
				node);
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}

	}

	public Number[] selectNumbers(
		Object node,
		String xpath,
		NamespaceContext namespacecontext,
		FunctionContext functioncontext,
		VariableContext variablecontext)
		throws XXMLException {
		try {
			DOMXPath expression =
				getXPathExprSpecial(
					xpath,
					namespacecontext,
					functioncontext,
					variablecontext);
			return nodesToNumbers(
				expression.getNavigator(),
				expression.selectNodes(node));
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}

	}

	DOMXPath getXPathExprSpecial(
		String xpathstr,
		NamespaceContext nc,
		FunctionContext fc,
		VariableContext vc)
		throws XXMLException {
		SimpleLruCacheNoSync m = getXPathExprTldMapSpecial();
		String key = xpathstr;
		if (nc != null)
			key += nc.hashCode();
		if (fc != null)
			key += fc.hashCode();
		if (vc != null)
			key += vc.hashCode();
		DOMXPath res = (DOMXPath) m.get(key);
		if (res == null) {
			try {
				res = new DOMXPath(xpathstr);
				m.put(key, res);
			} catch (Exception e) {
				throw new XXMLException(
					"Exception while compiling xpath " + e.getMessage(),
					e);
			}
		}
		if (nc != null)
			res.setNamespaceContext(nc);
		else
			res.setNamespaceContext(emptync);
		if (fc != null)
			res.setFunctionContext(fc);
		else
			res.setFunctionContext(emptyfc);
		if (vc != null)
			res.setVariableContext(vc);
		else
			res.setVariableContext(emptyvc);
		return res;
	}

	public Object evaluate(
		Object node,
		String xpathString,
		NamespaceContext nc,
		FunctionContext fc,
		VariableContext vc)
		throws XXMLException {
		try {
			return getXPathExprSpecial(xpathString, nc, fc, vc).evaluate(node);
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while evaluating xpath " + e.getMessage(),
				e);
		}
	}

	private static ThreadLocal xpathexprtld = new ThreadLocal();
	private static int MAXTLDCACHESIZE = 512; //TODO: make it tuneable
	static {
		try {
			MAXTLDCACHESIZE =
				Integer.parseInt(
					System.getProperty("XPATH_MAX_TLD_CACHE_SIZE"));
			if (MAXTLDCACHESIZE <= 0)
				MAXTLDCACHESIZE = 0;
		} catch (Exception e) {
		}
	}
	private static ThreadLocal xpathexprtldspecial = new ThreadLocal();
	private static NamespaceContext emptync = new SimpleNamespaceContext();
	private static FunctionContext emptyfc = new SimpleFunctionContext();
	private static VariableContext emptyvc = new SimpleVariableContext();

}
