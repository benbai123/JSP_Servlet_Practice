package test.lucene.testtwo;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopFieldDocs;

import test.lucene.wrapper.WrappedDocument;
import test.lucene.wrapper.WrappedIndexWriter;
import test.lucene.wrapper.WrappedQuery;
import test.lucene.wrapper.WrappedSearcher;

public class RangeQueryTest {
	public static void main(String[] args)
		throws IOException, ParseException {
		// Create IndexWriter
		WrappedIndexWriter wiw = new WrappedIndexWriter();
		wiw.createIndexWriter();

		Long timeOne = System.currentTimeMillis() - (1000L*60*60*24*30); // 30 days ago
		Long timeTwo = System.currentTimeMillis() - (1000L*60*60*24*20); // 20 days ago
		Long timeThree = System.currentTimeMillis() - (1000L*60*60*24*10); // 10 days ago
		Long timeFour = System.currentTimeMillis(); // just now

		// add Documents and close
		wiw.addDocument(new WrappedDocument("title", "Test Title One", true, true)
							.addField("content", "Test Content One", true, true)
							.addField("time", timeOne.toString(), true, false)
							.getDocument())
			.addDocument(new WrappedDocument("title", "Test Title Two", true, true)
							.addField("content", "Test Content Two", true, false)
							.addField("time", timeTwo.toString(), true, false)
							.getDocument())
			.addDocument(new WrappedDocument("title", "Test Title Three", true, true)
							.addField("content", "Test Content Three", true, true)
							.addField("time", timeThree.toString(), true, false)
							.getDocument())
			.addDocument(new WrappedDocument("title", "Test Title Four", true, true)
							.addField("content", "Test Content Four", false, true)
							.addField("time", timeFour.toString(), true, false)
							.getDocument())
			.close();
	
		// creat query, initiate searcher and do search
		WrappedSearcher ws = new WrappedSearcher();
		ws.initSearcher(wiw);
		searchAndDisplay(wiw, ws, new WrappedQuery()
				.createTermRangeQuery("time", timeOne.toString(),
						timeThree.toString(), true, false),
						"time", false);
		searchAndDisplay(wiw, ws, new WrappedQuery()
				.createTermRangeQuery("time", timeTwo.toString(),
					timeFour.toString(), false, true),
					"time", false);

		searchAndDisplay(wiw, ws, new WrappedQuery()
				.createTermRangeQuery("time", timeOne.toString(),
						timeThree.toString(), true, false),
						"time", true);
		searchAndDisplay(wiw, ws, new WrappedQuery()
				.createTermRangeQuery("time", timeTwo.toString(),
					timeFour.toString(), false, true),
					"time", true);

	}
	public static void searchAndDisplay (WrappedIndexWriter wiw, WrappedSearcher ws, WrappedQuery wq)
		throws IOException, ParseException {
		ScoreDoc[] results =
			ws.doSearch( wq, 10);
	
		// display results, have to remove it
		System.out.println(results.length + " results.");
		for(int i=0; i < results.length; i++) {
			int docId = results[i].doc;
			Document doc = ws.getIndexSearcher().doc(docId);
			System.out.println((i + 1) + "\ttitle: " + doc.get("title")
								+ "\n\tcontent: " + doc.get("content"));
		}
	}
	public static void searchAndDisplay (WrappedIndexWriter wiw, WrappedSearcher ws, WrappedQuery wq,
			String field, boolean ascending)
			throws IOException, ParseException {
	
		ScoreDoc[] results = ws.doSearch( wq, 10, field, ascending);;
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
