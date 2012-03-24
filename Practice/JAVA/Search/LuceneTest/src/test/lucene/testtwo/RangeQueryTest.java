package test.lucene.testtwo;

import java.io.IOException;
import java.util.*;

import org.apache.lucene.document.*;
import org.apache.lucene.queryParser.*;
import org.apache.lucene.search.*;

import test.lucene.utils.*;
import test.lucene.wrapper.*;

public class RangeQueryTest {
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
			wiw.addDocument(new WrappedDocument("title", "Test Title One")
								.addField("content", "Test Content One")
								.addField("time", sdOne)
								.getDocument())
				.addDocument(new WrappedDocument("title", "Test Title Two")
								.addField("content", "Test Content Two")
								.addField("time", sdTwo)
								.getDocument())
				.addDocument(new WrappedDocument("title", "Test Title Three")
								.addField("content", "Test Content Three")
								.addField("time", sdThree)
								.getDocument())
				.addDocument(new WrappedDocument("title", "Test Title Four")
								.addField("content", "Test Content Four")
								.addField("time", sdFour)
								.getDocument())
				.close();
		
			// create WrappedSearcher, initiate searcher
			WrappedSearcher ws = new WrappedSearcher().initSearcher(wiw);
	
			// do search by range query of date
			// not include upper bound, not include lower bound,
			// result: only dateTwo
			searchAndDisplay(wiw, ws, new WrappedQuery()
					.createTermRangeQuery("time", sdOne,
							sdThree.toString(), false, false));

			// do search by range query of date
			// include upper bound and lower bound
			// results: from dateTwo to dateFour
			searchAndDisplay(wiw, ws, new WrappedQuery()
					.createTermRangeQuery("time", sdTwo.toString(),
						sdFour.toString(), true, true));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void searchAndDisplay (WrappedIndexWriter wiw, WrappedSearcher ws, WrappedQuery wq)
		throws IOException, ParseException, java.text.ParseException {
		ScoreDoc[] results =
			ws.doSearch( wq, 10);
	
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