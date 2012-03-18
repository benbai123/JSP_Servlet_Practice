package test.lucene.wrapper;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.Version;

/**
 * Wrap the index writer with Analyzer/Directory,
 * to make sure we can get the correct one.
 *
 */
public class WrappedIndexWriter {
	// The analyzer for tokenizing text, indexing and searching
	private Analyzer _analyzer;
	// create the index
	private Directory _dir;
	// create the index writer config
	private IndexWriterConfig _config;
	// create index writer by index and config
	private IndexWriter _iw;

	/**
	 * Create the IndexWriter
	 * @throws CorruptIndexException
	 * @throws LockObtainFailedException
	 * @throws IOException
	 */
	public void createIndexWriter ()
		throws CorruptIndexException, LockObtainFailedException, IOException {
		// The analyzer for tokenizing text, indexing and searching
		_analyzer = new StandardAnalyzer(Version.LUCENE_35);
		// create the index
		_dir = new RAMDirectory();
		// create the index writer config
		_config = new IndexWriterConfig(Version.LUCENE_35, _analyzer);
		// create index writer by index and config
		_iw = new IndexWriter(_dir, _config);
	}
	/**
	 * Get the Analyzer of this IndexWriter
	 * @return Analyzer
	 */
	public Analyzer getAnalyzer () {
		return _analyzer;
	}
	/**
	 * Get the Directory of this IndexWriter
	 * @return Directory
	 */
	public Directory getDirectory () {
		return _dir;
	}
	/**
	 * Get the wrapped IndexWriter
	 * @return IndexWriter The wrapped IndexWriter
	 */
	public IndexWriter getIndexWriter() {
		return _iw;
	}
	/**
	 * Add document into IndexWriter
	 * @param doc The document to add
	 * @return WrappedIndexWriter Self instance
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	public WrappedIndexWriter addDocument (Document doc)
		throws CorruptIndexException, IOException {
		_iw.addDocument(doc);
		return this;
	}
	/**
	 * Update a Document in the IndexWriter
	 * @param term The term that indicate the Document to be updated
	 * @param doc The new Document
	 * @return WrappedIndexWriter Self instance
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	public WrappedIndexWriter updateDocument (Term term, Document doc)
		throws CorruptIndexException, IOException {
		_iw.updateDocument(term, doc);
		return this;
	}
	/**
	 * Close the IndexWriter
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 * 
	 */
	public void close() throws CorruptIndexException, IOException {
		_iw.close();
	}
}