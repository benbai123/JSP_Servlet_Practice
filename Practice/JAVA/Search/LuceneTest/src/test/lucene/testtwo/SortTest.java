package test.lucene.testtwo;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SortField;

import test.lucene.utils.DateUtils;
import test.lucene.wrapper.WrappedDocument;
import test.lucene.wrapper.WrappedIndexWriter;
import test.lucene.wrapper.WrappedQuery;
import test.lucene.wrapper.WrappedSearcher;
import test.lucene.wrapper.WrappedSort;

public class SortTest {
	public static void main(String[] args) {
		try{
			// Create WrappedIndexWriter
			WrappedIndexWriter wiw = new WrappedIndexWriter();
			long oneDay = 1000L*60*60*24;
			long currentTime = System.currentTimeMillis();
	
			// create dates
			Date dateOne = new Date(currentTime - (oneDay*30)); // 30 days ago
			Date dateTwo = new Date(currentTime - (oneDay*20)); // 20 days ago
			Date dateThree = new Date(currentTime - (oneDay*10)); // 10 days ago
			Date dateFour = new Date(currentTime); // just now
	
			// convert dates to strings
			String sdOne = DateUtils.getStringMillis(dateOne);
			String sdTwo = DateUtils.getStringMillis(dateTwo);
			String sdThree = DateUtils.getStringMillis(dateThree);
			String sdFour = DateUtils.getStringMillis(dateFour);

			Locale.setDefault(Locale.US);
			TimeZone.setDefault(TimeZone.getTimeZone("EST"));

			// Create IndexWriter
			wiw.createIndexWriter();
			// add Documents and close
			// based on the Javadoc, the sort field (time)
			// should be NOT_ANALYZED
			wiw.addDocument(new WrappedDocument("title", "Test Title One")
								.addField("content", "Test Content One")
								.addField("time", sdOne, true, false)
								.getDocument())
				.addDocument(new WrappedDocument("title", "Test Title Two")
								.addField("content", "Test Content Two")
								.addField("time", sdTwo, true, false)
								.getDocument())
				.addDocument(new WrappedDocument("title", "Test Title Three")
								.addField("content", "Test Content Three")
								.addField("time", sdThree, true, false)
								.getDocument())
				.addDocument(new WrappedDocument("title", "Test Title Four")
								.addField("content", "Test Content Four")
								.addField("time", sdFour, true, false)
								.getDocument())
				.close();
		
			// create WrappedSearcher, initiate searcher
			WrappedSearcher ws = new WrappedSearcher().initSearcher(wiw);
	
			// do search by range query of date
			// not include upper bound, include lower bound,
			// result: dateOne and dateTwo
			// Sort by time descending
			searchAndDisplay(wiw, ws,
				new WrappedQuery()
					.createTermRangeQuery("time", sdOne, sdThree.toString(), true, false),
				"time", true);

			// do search by range query of date
			// include upper bound and lower bound
			// results: from dateTwo to dateFour
			// Sort by time ascending
			searchAndDisplay(wiw, ws,
				new WrappedQuery()
					.createTermRangeQuery("time", sdTwo.toString(), sdFour.toString(), true, true),
				"time", false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void searchAndDisplay (WrappedIndexWriter wiw, WrappedSearcher ws, WrappedQuery wq,
											String sortField, boolean reverse)
		throws IOException, ParseException, java.text.ParseException {
		ScoreDoc[] results = ws.doSearch( wq, 10,
						new WrappedSort(new SortField(sortField, SortField.STRING, reverse)));
	
		// display results
		System.out.println(results.length + " results.");
		for(int i=0; i < results.length; i++) {
			int docId = results[i].doc;
			Document doc = ws.getIndexSearcher().doc(docId);
			System.out.println((i + 1) + "\ttitle: " + doc.get("title")
								+ "\n\tcontent: " + doc.get("content")
								+ "\n\tcontent: " + DateUtils.stringToDate(doc.get("time"))
								+ "\n");
		}
	}
}