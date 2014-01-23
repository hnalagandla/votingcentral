package com.votingcentral.util.xml;

import org.w3c.dom.Node;
import org.xml.sax.*;
import org.iso_relax.verifier.*;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.Reader;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 
 * Date: Mar 28, 2004
 * Time: 2:32:34 PM
 */
class JarvValidator implements IXmlValidator {
	private int schemaType;
	private String cacheKey;

	public JarvValidator(String cachekey, int schemaType)
		throws XXMLException {
		this.schemaType = schemaType;
		this.cacheKey = cacheKey;
		if (getSchemaStringFromType(schemaType) == null)
			throw new XXMLException(
				"Invalid schemaType : "
					+ schemaType
					+ " should be one of 1,2,3,4,5,6");
	}

	private static String getSchemaStringFromType(int schemaType) {
		switch (schemaType) {
			case IXmlValidatorFactory.W3C_SCHEMA_VALIDATOR :
				return "http://www.w3.org/2001/XMLSchema";
			case IXmlValidatorFactory.DTD_VALIDATOR :
				return "http://www.w3.org/XML/1998/namespace";
			case IXmlValidatorFactory.RELAX_NG_VALIDATOR :
				return "http://relaxng.org/ns/structure/0.9";
			case IXmlValidatorFactory.TREX_VALIDATOR :
				return "http://www.thaiopensource.com/trex";
			case IXmlValidatorFactory.RELAX_CORE_VALIDATOR :
				return "http://www.xml.gr.jp/xmlns/relaxCore";
			case IXmlValidatorFactory.RELAX_NAMESPACE_VALIDATOR :
				return "http://www.xml.gr.jp/xmlns/relaxNamespace";
			default :
				return null;
		}
	}

	public boolean isValid() {
		return schema != null;
	}
	public static String getSaxExceptionInfo(SAXParseException exception) {
		return exception.getMessage()
			+ (exception.getLineNumber() != -1
				? " Line: " + exception.getLineNumber()
				: "")
			+ (exception.getColumnNumber() != -1
				? " Column : " + exception.getColumnNumber()
				: "")
			+ (exception.getPublicId() != null
				? " Publicid: " + exception.getPublicId()
				: "")
			+ (exception.getSystemId() != null
				? " Systemid : " + exception.getSystemId()
				: "");
	}
	class JarvErrorHandler implements ErrorHandler {
		XXMLException e = null;

		public XXMLException getError() {
			return e;
		}

		public void error(SAXParseException exception) throws SAXException {
			if (e == null)
				e =
					new XXMLException(
						"Error in validation of node ! message "
							+ JarvValidator.getSaxExceptionInfo(exception),
						exception);
		}

		public void fatalError(SAXParseException exception)
			throws SAXException {
			e =
				new XXMLException(
					"Error in validation of node ! "
						+ JarvValidator.getSaxExceptionInfo(exception),
					exception);
		}

		public void warning(SAXParseException exception) throws SAXException {
			//TODO:
		}

	}

	// throws exception in case of error
	public void validate(Node node) throws XXMLException {

		Schema schm = schema;
		if (schm == null) {
			throw new XXMLException("compiled schema is null !");
		}
		try {
			Verifier verifier = schm.newVerifier();
			JarvErrorHandler hndlr = new JarvErrorHandler();
			verifier.setErrorHandler(hndlr);
			boolean result = verifier.verify(node);
			if (result == true)
				return;
			XXMLException e = hndlr.getError();
			if (e == null)
				throw new XXMLException("Unknown error occured during validation");
			else
				throw e;
		} catch (VerifierConfigurationException e) {
			throw new XXMLException(
				"VerifierConfigurationException for validating dom node "
					+ getSchemaStringFromType(this.schemaType)
					+ " ! message : "
					+ e.getMessage(),
				e);
		} catch (SAXParseException e) {
			throw new XXMLException(
				"SAXException while validating node against schema of type "
					+ getSchemaStringFromType(this.schemaType)
					+ " ! message : "
					+ JarvValidator.getSaxExceptionInfo(e),
				e);
		} catch (SAXException e) {
			throw new XXMLException(
				"SAXException while validating node against schema of type "
					+ getSchemaStringFromType(this.schemaType)
					+ " ! message : "
					+ e.getMessage(),
				e);
		} catch (Exception e) {
			throw new XXMLException(
				"Exception while validating node against schema of type "
					+ getSchemaStringFromType(this.schemaType)
					+ " ! message : "
					+ e.getMessage(),
				e);

		}
	}

	public void validate(Reader xml) throws XXMLException {
		validate(xml, null);
	}

	public void validate(Reader xml, EntityResolver er) throws XXMLException {
		Schema schm = schema;
		if (schm == null) {
			throw new XXMLException("compiled schema is null !");
		}
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// Parse the input
			SAXParser saxParser = factory.newSAXParser();
			XMLReader reader = saxParser.getXMLReader();
			Verifier verifier = schm.newVerifier();
			if (er != null)
				saxParser.getXMLReader().setEntityResolver(er);
			saxParser.getXMLReader().setFeature(
				"http://xml.org/sax/features/namespaces",
				true);
			JarvErrorHandler hndlr = new JarvErrorHandler();
			verifier.setErrorHandler(hndlr);
			VerifierHandler handler = verifier.getVerifierHandler();
			reader.setContentHandler(handler);
			reader.parse(new InputSource(xml));

			if (handler.isValid());
			else {
				XXMLException e = hndlr.getError();
				if (e == null)
					throw new XXMLException("Unknown error occured during validation");
				else
					throw e;
			}
		} catch (ParserConfigurationException e) {
			throw new XXMLException(
				"ParserConfigurationException while validation of data against schema of type "
					+ getSchemaStringFromType(this.schemaType)
					+ " ! message : "
					+ e.getMessage(),
				e);
		} catch (IOException e) {
			throw new XXMLException(
				"IO exception while reading data for validation against schema of type "
					+ getSchemaStringFromType(this.schemaType)
					+ " ! message : "
					+ e.getMessage(),
				e);
		} catch (VerifierConfigurationException e) {
			throw new XXMLException(
				"VerifierConfigurationException for validating data "
					+ getSchemaStringFromType(this.schemaType)
					+ " ! message : "
					+ e.getMessage(),
				e);
		} catch (SAXParseException e) {
			throw new XXMLException(
				"SAXException while validating data against schema of type "
					+ getSchemaStringFromType(this.schemaType)
					+ " ! message : "
					+ JarvValidator.getSaxExceptionInfo(e),
				e);

		} catch (SAXException e) {
			throw new XXMLException(
				"SAXException while validating data against schema of type "
					+ getSchemaStringFromType(this.schemaType)
					+ " ! message : "
					+ e.getMessage(),
				e);
		}

	}

	public String getValidatorCacheKey() // to refer later
	{
		return this.cacheKey;
	}

	public void recompileValidator(Reader schemaXml) throws XXMLException {
		recompileValidator(schemaXml, null);
	}

	public synchronized void recompileValidator(
		Reader schemaXml,
		EntityResolver er)
		throws XXMLException {
		// create a VerifierFactory
		try {
			// TODO: think about caching these verifierfactories on per thread local data
			VerifierFactory factory = null;
			InputSource is = new InputSource(schemaXml);
			if (this.schemaType != IXmlValidatorFactory.DTD_VALIDATOR) {
				factory =
					VerifierFactory.newInstance(
						getSchemaStringFromType(this.schemaType));

			} else {
				is.setSystemId("abc.dtd");
				factory =
					(VerifierFactory) Class
						.forName("com.sun.msv.verifier.jarv.TheFactoryImpl")
						.newInstance();
			}
			if (er != null) {
				factory.setEntityResolver(er);
			}
			Schema schema = factory.compileSchema(is);
			this.schema = schema;
		} catch (VerifierConfigurationException e) {
			throw new XXMLException(
				"VerifierConfigurationException for getting verifierfactory for "
					+ getSchemaStringFromType(this.schemaType)
					+ " ! message : "
					+ e.getMessage(),
				e);
		} catch (SAXParseException e) {
			throw new XXMLException(
				"Error in compiling schema ! "
					+ getSchemaStringFromType(this.schemaType)
					+ JarvValidator.getSaxExceptionInfo(e),
				e);
		} catch (SAXException e) {
			throw new XXMLException(
				"SAXException while compiling schema of type "
					+ getSchemaStringFromType(this.schemaType)
					+ " ! message : "
					+ e.getMessage(),
				e);
		} catch (IOException e) {
			throw new XXMLException(
				"IO exception while compiling schema of type "
					+ getSchemaStringFromType(this.schemaType)
					+ " ! message : "
					+ e.getMessage(),
				e);
		} catch (IllegalAccessException e) {
			throw new XXMLException(
				"IllegalAccessException while compiling schema of type "
					+ getSchemaStringFromType(this.schemaType)
					+ " ! message : "
					+ e.getMessage(),
				e);
		} catch (ClassNotFoundException e) {
			throw new XXMLException(
				"ClassNotFound exception compiling schema of type "
					+ getSchemaStringFromType(this.schemaType)
					+ " ! message : "
					+ e.getMessage(),
				e);
		} catch (InstantiationException e) {
			throw new XXMLException(
				"Instantiation exception compiling schema of type "
					+ getSchemaStringFromType(this.schemaType)
					+ " ! message : "
					+ e.getMessage(),
				e);
		}
	}

	public DocumentBuilderFactory getDocumentBuilderFactory()
		throws XXMLException {
		if (schema == null)
			throw new XXMLException("Validator schema is null !");
		return new org.iso_relax.jaxp.ValidatingDocumentBuilderFactory(schema);
	}

	public SAXParserFactory getSAXParserFactory() throws XXMLException {
		if (schema == null)
			throw new XXMLException("Validator schema is null !");
		return new org.iso_relax.jaxp.ValidatingSAXParserFactory(schema);
	}

	private volatile Schema schema;

}
