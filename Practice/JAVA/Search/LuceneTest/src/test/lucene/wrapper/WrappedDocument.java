package test.lucene.wrapper;

import java.io.IOException;

import org.apache.lucene.document.*;

/**
 * Wrap the Document so we can do get document in more convenient way:
 * 
 * DocumentWrapper.createDoc(...).addField(...)
 * .addField.......getDocument()
 *
 */
public class WrappedDocument {
	// The wrapped Document
	private Document _doc;

	public WrappedDocument () {
		
	}
	public WrappedDocument (String name, String value,
			boolean store, boolean analyzed)
			throws IllegalStateException, IOException {
		createDoc(name, value, store, analyzed);
	}
	/**
	 * create document and add field.
	 * @param name Field name
	 * @param value Field value
	 * @param store Store value or not
	 * @param analyzed analyze value or not
	 * @return DocumentWrapper Self instance
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public WrappedDocument createDoc(String name, String value,
		boolean store, boolean analyzed)
			throws IOException, IllegalStateException {
		if (_doc != null)
			throw new IllegalStateException ("Document already created!");
		_doc = new Document();
		return addField(name, value, store, analyzed);
	}
	/**
	 * Add field
	 * @param name Field name
	 * @param value Field value
	 * @param store Store value or not
	 * @param analyzed Analyze value or not
	 * @return DocumentWrapper Self instance
	 * @throws IOException
	 */
	public WrappedDocument addField(String name, String value,
			boolean store, boolean analyzed) throws IOException {
		_doc.add(new Field(name,
				value,
				store? Field.Store.YES : Field.Store.NO,
				analyzed? Field.Index.ANALYZED : Field.Index.NOT_ANALYZED));
		return this;
	}
	/**
	 * Get the document
	 * @return The document wrapped by this wrapper.
	 */
	public Document getDocument () {
		return _doc;
	}
}