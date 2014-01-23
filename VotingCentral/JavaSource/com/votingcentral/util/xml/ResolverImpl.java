package com.votingcentral.util.xml;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.apache.velocity.exception.ResourceNotFoundException;

import javax.xml.transform.URIResolver;
import javax.xml.transform.TransformerException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.util.HashMap;
import java.io.StringReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

/**
 * ResolverImpl implements EntityResolver, URIResolver Interfaces 
 * 
 * Options | File Templates.
 */
public class ResolverImpl implements EntityResolver, URIResolver,
		VelocityResolver {

	private HashMap dataStore = new HashMap();

	/**
	 * Allow the application to resolve external entities.
	 * 
	 * <p>
	 * The Parser will call this method before opening any external entity
	 * except the top-level document entity (including the external DTD subset,
	 * external entities referenced within the DTD, and external entities
	 * referenced within the document element): the application may request that
	 * the parser resolve the entity itself, that it use an alternative URI, or
	 * that it use an entirely different input source.
	 * </p>
	 * 
	 * <p>
	 * Application writers can use this method to redirect external system
	 * identifiers to secure and/or local URIs, to look up public identifiers in
	 * a catalogue, or to read an entity from a database or other input source
	 * (including, for example, a dialog box).
	 * </p>
	 * 
	 * <p>
	 * If the system identifier is a URL, the SAX parser must resolve it fully
	 * before reporting it to the application.
	 * </p>
	 * 
	 * @param publicId
	 *            The public identifier of the external entity being referenced,
	 *            or null if none was supplied.
	 * @param systemId
	 *            The system identifier of the external entity being referenced.
	 * @return An InputSource object describing the new input source, or null to
	 *         request that the parser open a regular URI connection to the
	 *         system identifier.
	 * @exception org.xml.sax.SAXException
	 *                Any SAX exception, possibly wrapping another exception.
	 * @exception java.io.IOException
	 *                A Java-specific IO exception, possibly the result of
	 *                creating a new InputStream or Reader for the InputSource.
	 * @see org.xml.sax.InputSource
	 */
	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
		String entity = (String) dataStore.get(systemId);
		if (entity != null)
			return new InputSource(new StringReader(entity));
		throw new IOException("Unable to resolve entity for publicID:"
				+ publicId + ";systemId:" + systemId);
	}

	/**
	 * Called by the processor when it encounters an xsl:include, xsl:import, or
	 * document() function.
	 * 
	 * @param href
	 *            An href attribute, which may be relative or absolute.
	 * @param base
	 *            The base URI in effect when the href attribute was
	 *            encountered.
	 * 
	 * @return A Source object, or null if the href cannot be resolved, and the
	 *         processor should try to resolve the URI itself.
	 * 
	 * @throws TransformerException
	 *             if an error occurs when trying to resolve the URI.
	 */
	public Source resolve(String href, String base) throws TransformerException {
		String entity = (String) dataStore.get(href);
		if (entity != null)
			return new StreamSource(new StringReader(entity));
		throw new TransformerException("Unable to resolve entity for hRef:"
				+ href + ";base:" + base);
	}

	public void addEntity(String systemID, String schema) {
		dataStore.put(systemID, schema);
	}

	public String getEntity(String systemID) {
		return (String) dataStore.get(systemID);
	}

	public int size() {
		return dataStore.size();
	}

	// Methods for VelocityResolver

	public long getLastModified(String name) throws ResourceNotFoundException {
		return System.currentTimeMillis();
	}

	public InputStream getResource(String name)
			throws ResourceNotFoundException {
		String val = (String) dataStore.get(name);
		if (val != null)
			return new ByteArrayInputStream(val.getBytes());
		return null;
	}

}