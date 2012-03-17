package test.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.Version;

import java.io.IOException;

public class LuceneTest {
	// The analyzer for tokenizing text, indexing and searching
	public static StandardAnalyzer analyzer =
		new StandardAnalyzer(Version.LUCENE_35);

	public static void main(String[] args)
		throws IOException, ParseException {
		// create the index
		Directory index = new RAMDirectory();
		// create the index writer config
		IndexWriterConfig config =
			new IndexWriterConfig(Version.LUCENE_35, analyzer);
		// create index writer by index and config
		IndexWriter iw = new IndexWriter(index, config);

		// add documents into index writer
		addDocuments(iw);
		iw.close();

		// Search the 'Test Content One' in documents content
		doSearch(index, "content", "Test Content Three");

	}
	private static void addDocuments(IndexWriter iw)
		throws IOException, ParseException {

		iw.addDocument(new DocumentWrapper()
				.createDoc("title", "Test Title One", true, true)
				.addField("content", "Test Content One", true, true)
				.getDocument());
		// content of this one not analyzed,
		// can not be searched
		iw.addDocument(new DocumentWrapper()
				.createDoc("title", "Test Title Two", true, true)
				.addField("content", "Test Content Two", true, false)
				.getDocument());
		iw.addDocument(new DocumentWrapper()
				.createDoc("title", "Test Title Three", true, true)
				.addField("content", "Test Content Three", true, true)
				.getDocument());
		// content of this one not is stored,
		// can be searched but the content is null
		iw.addDocument(new DocumentWrapper()
				.createDoc("title", "Test Title Four", true, true)
				.addField("content", "Test Content Four", false, true)
				.getDocument());
	}
	private static void doSearch (Directory index, String field, String content)
		throws IOException, ParseException{
		// query string
		String querystr = content;

		// query, with default field
		Query q = new QueryParser(Version.LUCENE_35, field, analyzer)
						.parse(querystr);

		// search
		int hitsPerPage = 10;
		IndexReader reader = IndexReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector =
			TopScoreDocCollector.create(hitsPerPage, true);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
	    
		// display results
		System.out.println(hits.length + " results.");
		for(int i=0; i < hits.length; i++) {
			int docId = hits[i].doc;
			Document doc = searcher.doc(docId);
			System.out.println((i + 1) + "\ttitle: " + doc.get("title")
								+ "\n\tcontent: " + doc.get("content"));
		}

		// close searcher 
		searcher.close();
	}
}