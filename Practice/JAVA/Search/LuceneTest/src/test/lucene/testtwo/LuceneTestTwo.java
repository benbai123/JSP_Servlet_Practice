package test.lucene.testtwo;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;

import test.lucene.wrapper.*;

public class LuceneTestTwo {
	public static void main(String[] args)
	throws IOException, ParseException {
		// Create IndexWriter
		WrappedIndexWriter wiw = new WrappedIndexWriter();
		wiw.createIndexWriter();

		// add Documents and close
		wiw.addDocument(new WrappedDocument("title", "Test Title One", true, true)
							.addField("content", "Test Content One", true, true)
							.getDocument())
			.addDocument(new WrappedDocument("title", "Test Title Two", true, true)
							.addField("content", "Test Content Two", true, false)
							.getDocument())
			.addDocument(new WrappedDocument("title", "Test Title Three", true, true)
							.addField("content", "Test Content Three", true, true)
							.getDocument())
			.addDocument(new WrappedDocument("title", "Test Title Four", true, true)
							.addField("content", "Test Content Four", false, true)
							.getDocument())
			.close();

		// creat query, initiate searcher and do search
		WrappedSearcher ws = new WrappedSearcher();
		ScoreDoc[] results =
			ws.initSearcher(wiw)
				.doSearch( new WrappedQuery()
								.createQuery(wiw, "content", "Test Content Three"),
							10);

		// display results, have to remove it
		System.out.println(results.length + " results.");
		for(int i=0; i < results.length; i++) {
			int docId = results[i].doc;
			Document doc = ws.getIndexSearcher().doc(docId);
			System.out.println((i + 1) + "\ttitle: " + doc.get("title")
								+ "\n\tcontent: " + doc.get("content"));
		}
	}
}