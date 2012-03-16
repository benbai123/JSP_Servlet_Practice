package test.lucene;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

public class DocumentWrapper {
	private Document _doc;

	public DocumentWrapper createDoc(String name, String value,
		boolean store, boolean analyzed)
			throws IOException, IllegalStateException {
		if (_doc != null)
			throw new IllegalStateException ("Document already created!");
		_doc = new Document();
		return addField(name, value, store, analyzed);
	}
	public DocumentWrapper addField(String name, String value,
			boolean store, boolean analyzed) throws IOException {
		_doc.add(new Field(name,
				value,
				store? Field.Store.YES : Field.Store.NO,
				analyzed? Field.Index.ANALYZED : Field.Index.NOT_ANALYZED));
		return this;
	}
	public Document getDocument () {
		return _doc;
	}
}
