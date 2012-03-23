package test.lucene.wrapper;

import java.io.IOException;

import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.*;
import org.apache.lucene.search.*;
/**
 * Wrap the Searcher to encapsulate the initiate and
 * search process
 *
 */
public class WrappedSearcher {
	/**
	 * The only instance of searcher
	 */
	private static IndexSearcher _searcher;

	/**
	 * Initiate or renew the _searcher
	 * @param wiw Thw wrapped index writer
	 * @return WrappedSearcher Self instance
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	public WrappedSearcher initSearcher (WrappedIndexWriter wiw)
		throws CorruptIndexException, IOException {
		IndexReader ir = IndexReader.open(wiw.getDirectory());
		_searcher = new IndexSearcher(ir);
		return this;
	}
	/**
	 * Get the searcher instance
	 * @return IndexSearcher The instance of searcher
	 */
	public IndexSearcher getIndexSearcher () {
		return _searcher;
	}

	/**
	 * Search and return the results
	 * @param wq WrappedQuery
	 * @param resultsPerPage
	 * @return ScoreDoc[] The results
	 * @throws IOException
	 * @throws ParseException
	 */
	public ScoreDoc[] doSearch (WrappedQuery wq, int resultsPerPage)
		throws IOException, ParseException{

		TopScoreDocCollector collector =
			TopScoreDocCollector.create(resultsPerPage, true);
		_searcher.search(wq.getQuery(), collector);
		ScoreDoc[] results = collector.topDocs().scoreDocs;
	    
		return results;
	}
	public ScoreDoc[] doSearch (WrappedQuery wq, int resultsPerPage,
			String sortField, boolean reverse)
			throws IOException, ParseException{
		Sort sort = new Sort(new SortField(sortField, SortField.STRING, reverse));
		return _searcher.search(wq.getQuery(), resultsPerPage, sort).scoreDocs;
	}
}