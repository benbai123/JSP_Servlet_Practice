package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	// The current url object, used to build relative path
	private URL url;
	// the maximum amount of result 0 or smaller denotes no limitation
	private int resultLimit = 0;
	// whether the crawler is stopped
	private boolean stopped = false;

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
				if (urlToCrawl.size() == 0) {
					break;
				}
				String strUrl = urlToCrawl.remove(0);
				url = new java.net.URL(strUrl);
				// get the content of first url
				String content = getResponse(strUrl, url).toString();
				// put the url/content to result map
				if (content != null) {
					result.put(strUrl, content);
					// stop
					if (resultLimit > 0 && result.size() >= resultLimit)
						break;
					// get all url that have not be crawled 
					urlToCrawl.addAll(getSubUrls(content, url));
				}

				// take a rest, do not crawl too fast
				try {
					Thread.sleep(delayBetweenCrawl);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
			stopped = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * get all html links in the given content
	 * @param content The content that crawled by current url
	 * @param url the current url
	 * @return List, all html links in the given content
	 */
	private List<String> getSubUrls (String content, URL url) {
		List<String> subUrls = new ArrayList<String>();
		String lowerCaseContent = content.toLowerCase();
		int index = 0;
		// has anchor tag
		while ((index = lowerCaseContent.indexOf("<a", index)) != -1) {
			// take a rest, do not check too fast
			try {
				Thread.sleep(delayBetweenCheck);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			// has href="..."
			if ((index = lowerCaseContent.indexOf("href", index)) == -1) 
				break;
			if ((index = lowerCaseContent.indexOf("=", index)) == -1) 
				break;

			// get next part of url
			index++;
			String remaining = content.substring(index);
			StringTokenizer st = new StringTokenizer(remaining, "\t\n\r\"<>#");
			String strLink = st.nextToken();
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
			// check to see if this URL has already been 
			// searched or is going to be searched
			if (!result.containsKey(strLink) 
				&& !urlToCrawl.contains(strLink)
				&& !badUrls.contains(strLink)) {

				URL urlLink;
				try {
					// absolute link
					if (strLink.startsWith("http"))
						urlLink = new URL(strLink);
					else // relative link
						urlLink = new URL(url, strLink);
					strLink = urlLink.toString();
					System.out.println(strLink);
				} catch (MalformedURLException e) {
					System.out.println("ERROR: bad URL " + strLink);
					if (!badUrls.contains(strLink)) {
						badUrls.add(strLink);
					}
					continue;
				}
	
				// only look at http links
				if (urlLink.getProtocol().compareTo("http") != 0) {
					System.out.println("Not http");
					if (!badUrls.contains(strLink)) {
						badUrls.add(strLink);
					}
					continue;
				}
	
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
					}
				} catch (IOException e) {
			    	System.out.println("ERROR: couldn't open URL " + strLink);
			    	if (!badUrls.contains(strLink)) {
						badUrls.add(strLink);
					}
					continue;
				}
			} else
				System.out.println(" is bad url");
			// add to bad urls if not added
			if (!result.containsKey(strLink) 
				&& !urlToCrawl.contains(strLink)
				&& !badUrls.contains(strLink)) {
				badUrls.add(strLink);
			}
		}
		return subUrls;
	}

	public static StringBuilder getResponse(String path, URL url){
		try {
			java.net.HttpURLConnection uc = (java.net.HttpURLConnection) url.openConnection();
			uc.setRequestProperty("User-agent", "Mozilla/10.0");

			uc.setRequestProperty("Accept-Charset", "UTF-8"); // encoding
			uc.setReadTimeout(30000);// timeout limit
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
	public Map getResult () {
		return result;
	}
	/**
	 * Whether the crawler is stopped
	 * @return boolean
	 */
	public boolean isStopped () {
		return stopped;
	}
	public static void main (String[] args) {
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
