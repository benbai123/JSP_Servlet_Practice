package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class SimpleCrawler implements Runnable {
	public static final String DISALLOW = "Disallow:";

	// the result, <url, content>
	private Map<String, String> result = new HashMap<String, String>();
	// the url list to crawl
	private List<String> urlToCrawl = new ArrayList<String>();
	// the url list to skip
	private List<String> badUrls = new ArrayList<String>();
	// the 'words' that should be skipped (even do not test it)
	private List<String> _escapeWords = new ArrayList<String>();
	// The current url object, used to build relative path
	private URL url;
	// the maximum amount of result 0 or smaller denotes no limitation
	private int resultLimit = 0;
	// whether the crawler is stopped
	private boolean _stopped = false;

	// the delay between crawl
	private long delayBetweenCrawl = 5000;
	// the delay between check url
	private long delayBetweenCheck = 1000;

	/**
	 * Crawl the given url,
	 * @param rootUrl The Url to Crawl
	 * @param limit The maximum size of result, 0 or negative denotes no limitation
	 */
	public void Crawl (String rootUrl, int limit) {
		this.urlToCrawl.add(rootUrl);
		this.resultLimit = limit;
		
		new Thread(this).start();
	}
	public void run () {
		try {
			while (true) {
				// no more link or set stopped, stop
				if (urlToCrawl.size() == 0 || isStopped()) {
					break;
				}
				String strUrl = urlToCrawl.remove(0);
				url = new java.net.URL(strUrl);
				// get the content of first url
				String content = getResponse(url).toString();
				// put the url/content to result map
				if (content != null) {
					result.put(strUrl, content);
					// limit size, stop
					if (resultLimit > 0 && result.size() >= resultLimit)
						break;
					// add all url that have not be crawled in content 
					addSubUrls(content, url);
				}

				// take a rest, do not crawl too fast
				sleep(delayBetweenCrawl);
			}
			setStopped(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * get all html links in the given content
	 * @param content The content that crawled by current url
	 * @param url the current url
	 */
	/*package*/ void addSubUrls (String content, URL url) {
		boolean success;
		String lowerCaseContent = content.toLowerCase();
		int index = 0;
		// has anchor tag
		while ((index = lowerCaseContent.indexOf("<a", index)) != -1) {
			// take a rest, do not check too fast
			sleep(delayBetweenCheck);

			// has href="..."
			if ((index = lowerCaseContent.indexOf("href", index)) == -1
				|| (index = lowerCaseContent.indexOf("=", index)) == -1) 
				break;

			// get next part of url
			index++;
			String strLink = getNextLink(content, index);
			if (strLink == null) {
				if (!badUrls.contains(strLink))
					badUrls.add(strLink);
				continue;
			}
			// check to see if this URL has already been 
			// searched or is going to be searched
			if (!(getResult().containsKey(strLink) 
				|| urlToCrawl.contains(strLink)
				|| badUrls.contains(strLink))) {

				URL urlLink = createURL(url, strLink);
				if (urlLink == null) {
					if (!badUrls.contains(strLink))
						badUrls.add(strLink);
					continue;
				}
				// update strLink, it may changed
				strLink = urlLink.toString();
				success = tryAddUrl(urlLink, strLink);
				if (!success) badUrls.add(strLink);
				if (urlToCrawl.size() > resultLimit)
					break;
			} else
				System.out.println(" is bad url");
		}
	}
	/**
	 * set a list of escape word, if a url contains escape word,
	 * it will not be crawled 
	 * @param escapeWords list of escape word
	 */
	public void setEscapeWords (List<String> escapeWords) {
		_escapeWords = escapeWords;
	}
	/**
	 * sleep the given millis
	 * @param millis
	 * @return
	 */
	/*package*/ boolean sleep (long millis) {
		boolean success = true;
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			success = false;
		}
		return success;
	}
	/**
	 * get a link from the content with given index
	 * @param content the content string
	 * @param index the index where start to get next link
	 * @return String the next url link
	 */
	/*package*/ String getNextLink (String content, int index) {
		String remaining = content.substring(index);
		StringTokenizer st = new StringTokenizer(remaining, "\t\n\r\"<>#");
		String strLink = st.nextToken();
		// escpae this url if it contains escape words
		if (hasEscapeWords(strLink))
			return null;
		// shift to the first http if exists
		if (!strLink.startsWith("http") && strLink.contains("http")) {
			strLink = strLink.substring(strLink.indexOf("http"));
		}
		// cut the tail after htm or html
		if ((!strLink.endsWith("html") && strLink.contains("html"))
			|| (!strLink.endsWith("htm") && strLink.contains("htm"))) {
			boolean hasHtml = false;
			if (strLink.contains("html")) {
				strLink = strLink.substring(0, strLink.lastIndexOf("html") + 4);
				hasHtml = true;
			} else
				strLink = strLink.substring(0, strLink.lastIndexOf("htm") + 3);
			System.out.println(hasHtml + " modified tail " + strLink);
		}
		return strLink;
	}
	/**
	 * create a url from the parent url and link string
	 * @param url the parent url
	 * @param strLink link string
	 * @return URL the created url
	 */
	/*package*/ URL createURL (URL url, String strLink) {
		URL urlLink;
		try {
			// absolute link
			if (strLink.startsWith("http"))
				urlLink = new URL(strLink);
			else // relative link
				urlLink = new URL(url, strLink);
			strLink = urlLink.toString();
		} catch (MalformedURLException e) {
			System.out.println("ERROR: bad URL " + strLink);
			return null;
		}

		// only look at http links
		if (urlLink.getProtocol().compareTo("http") != 0) {
			System.out.println("Not http");
			return null;
		}
		return urlLink;
	}
	/**
	 * check whether a link string contains escape word	
	 * @param urlLink the link string
	 * @return boolean, true if the given link string contains escape word
	 */
	/*package*/ boolean hasEscapeWords (String urlLink) {
		if (_escapeWords != null) {
			for (String s : _escapeWords) {
				if (urlLink.toLowerCase().contains(s.toLowerCase()))
					return true;
			}
		}
		return false;
	}
	/**
	 * try add a link string to crawl list
	 * @param urlLink the url to check
	 * @param strLink the link string to add
	 * @return boolean, true if add success
	 */
	/*package*/ boolean tryAddUrl (URL urlLink, String strLink) {
		boolean success = false;
		// test and store the url
		try {
			// try opening the URL
			URLConnection urlLinkConnection 
				= urlLink.openConnection();

			urlLinkConnection.setAllowUserInteraction(false);
			InputStream linkStream = urlLink.openStream();
			String strType
				= urlLinkConnection.guessContentTypeFromStream(linkStream);
			String strTypeTwo = urlLinkConnection.getContentType();
			linkStream.close();

			// is text/html
			if ((strTypeTwo != null && strTypeTwo.contains("text/html"))
				|| (strType != null && strType.contains("text/html"))) {
				// add new url to list
				urlToCrawl.add(strLink);
				success = true;
			}
		} catch (IOException e) {
	    	System.out.println("ERROR: couldn't open URL " + strLink);
		}
		return success;
	}

	/**
	 * get response content from given url
	 * @param url the url to get response
	 * @return StringBuilder the response content
	 */
	/*package*/ StringBuilder getResponse(URL url){
		try {
			HttpURLConnection uc = getConnection(url);
			uc.connect();// connect
			int status = uc.getResponseCode();

			switch (status) {
				case java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT://504 timeout
					break;
				case java.net.HttpURLConnection.HTTP_FORBIDDEN://403 forbidden
					break;
				case java.net.HttpURLConnection.HTTP_INTERNAL_ERROR://500 server error
					break;
				case java.net.HttpURLConnection.HTTP_NOT_FOUND://404 not exist
					break;
				case java.net.HttpURLConnection.HTTP_OK: // ok
					InputStreamReader reader = new InputStreamReader(uc.getInputStream(), "UTF-8");

					int ch;
					StringBuilder sb = new StringBuilder("");
					while((ch = reader.read())!= -1){
						sb.append((char)ch);
					}
					return sb;
			}

		} catch (java.net.MalformedURLException e) { // invalid address format
			e.printStackTrace();
		} catch (java.io.IOException e) { // connection broken
			e.printStackTrace();
		}
		return new StringBuilder("");
	}
	/**
	 * get HttpURLConnection from given url
	 * @param url the url to get HttpURLConnection
	 * @return HttpURLConnection
	 * @throws IOException
	 */
	/*package*/ HttpURLConnection getConnection (URL url) throws IOException {
		HttpURLConnection uc = (java.net.HttpURLConnection) url.openConnection();

		Map prop = new HashMap();
		prop.put("User-agent", "Mozilla/5.0");
		prop.put("Accept-Charset", "UTF-8");
		setRequestProperties(uc, prop);

		uc.setReadTimeout(30000);// timeout limit
		return uc;
	}
	/**
	 * set properties to a URLConnection
	 * @param uc the URLConnection to set properties to
	 * @param properties the properties to set
	 */
	/*package*/ void setRequestProperties (URLConnection uc, Map properties) {
		if (properties != null) {
			for (Object key : properties.keySet()) {
				uc.setRequestProperty((String)key, (String)properties.get(key));
			}
		}
	}
	/**
	 * get crawling result
	 * @return
	 */
	public Map getResult () {
		return result;
	}
	/**
	 * Whether the crawler is stopped
	 * @return boolean
	 */
	public boolean isStopped () {
		return _stopped;
	}
	public void setStopped (boolean stopped) {
		_stopped = stopped;
	}
	public static void main (String[] args) throws Exception {
		SimpleCrawler crawler = new SimpleCrawler();
		// crawl this url
		crawler.Crawl("http://java.sun.com", 2);
		// wait until crawler stopped
		while (!crawler.isStopped()) {
			try{
				Thread.sleep(10000);
			} catch (Exception e) {
				continue;
			}
		}
		// show the results
		Map result = crawler.getResult();
		System.out.println(result.size());
		for (Object key : result.keySet()) {
			System.out.println(key.toString());
			if (result.get(key).toString().length() > 50)
				System.out.println(result.get(key).toString().substring(0, 50));
			else
				System.out.println(result.get(key));
		}
	}
}
