package test.lucene.wrapper;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.*;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Version;
/**
 * Wrap Query so we can create various type of Query instance and
 * do something like createBooleanQuery.addQuery.addQuery...
 *
 */
public class WrappedQuery {
	// the wrapped Query
	private Query _query;
	// Create a Query by QueryParser
	public WrappedQuery createQuery (WrappedIndexWriter wiw, String field, String content)
		throws ParseException {
		_query = new QueryParser(Version.LUCENE_35, field, wiw.getAnalyzer())
			.parse(content);
		return this;
	}

	/**
	 * Create a BooleanQuery
	 * @return WrappedQuery Self instance
	 */
	public WrappedQuery createBooleanQuery () {
		_query = new BooleanQuery();
		return this;
	}

	/**
	 * Create a TermQuery
	 * @param term The term to query
	 * @return WrappedQuery Self instance
	 */
	public WrappedQuery createTermQuery (Term term) {
		_query = new TermQuery(term);
		return this;
	}

	/**
	 * Create a TermRangeQuery
	 * @param field The field to query
	 * @param lower The lower bound
	 * @param upper The upper bound
	 * @param includeLower Grater Equal (true) or Greater Then (false)
	 * @param includeUpper Less Equal (true) or Less Then (false)
	 * @return WrappedQuery Self instance
	 */
	public WrappedQuery createTermRangeQuery (String field, String lower,
			String upper, boolean includeLower, boolean includeUpper) {
		_query = new TermRangeQuery(field, lower, upper,
									includeLower, includeUpper);
		return this;
	}

	/**
	 * Create a WildcardQuery
	 * @param term The term to query
	 * @return WrappedQuery Self instance
	 */
	public WrappedQuery createWildcardQuery (Term term) {
		_query = new WildcardQuery(term);
		return this;
	}

	/**
	 * Add query into a BooleanQuery
	 * @param term The term to add
	 * @param occur MUST, MUST_NOT or SHOULD
	 * @return WrappedQuery Self instance
	 */
	public WrappedQuery addQuery (Term term, BooleanClause.Occur occur) {
		if (_query instanceof BooleanQuery)
			((BooleanQuery) _query).add(new TermQuery(term), occur);
		else
			throw new UnsupportedOperationException("addQuery only works with BooleanQuery");
		return this;
	}

	/**
	 * Get the wrapped Query
	 * @return Query The wrapped query
	 */
	public Query getQuery () {
		return _query;
	}
}