package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class LoginCrawler extends SimpleCrawler {
	public static final String DISALLOW = "Disallow:";

	// the url list to crawl
	private List<String> urlToCrawl = new ArrayList<String>();
	// the url list to skip
	private List<String> badUrls = new ArrayList<String>();
	
	// cookies
	private List<String> _cookies = new ArrayList<String>();
	// The current url object, used to build relative path
	private URL url;
	// the maximum amount of result 0 or smaller denotes no limitation
	private int resultLimit = 0;

	// the delay between crawl
	private long delayBetweenCrawl = 5000;
	// the delay between check url
	private long delayBetweenCheck = 1000;
	// whether crawl in root domain only
	private boolean excludeOtherDomain = true;

	/**
	 * Crawl the given url after get the cookie
	 * @param rootUrl
	 * @param limit
	 * @param cookieRetrieveUrl
	 * @throws IOException 
	 */
	public void Crawl (String rootUrl, int limit, String cookieRetrieveUrl) throws IOException {
		Crawl(rootUrl, limit, cookieRetrieveUrl, null, null);
	}
	/**
	 * Crawl the given url after get the cookie and post to login
	 * @param rootUrl
	 * @param limit
	 * @param cookieRetrieveUrl
	 * @param loginPostUrl
	 * @param postParams
	 * @throws IOException
	 */
	public void Crawl (String rootUrl, int limit, String cookieRetrieveUrl, String loginUrl, String postParams) throws IOException {
		synchronized (this) {
			if (isStopped()) {
				setStopped(false);
				if (cookieRetrieveUrl != null && !cookieRetrieveUrl.isEmpty())
					retrieveCookie(cookieRetrieveUrl);
				if (!(loginUrl == null || loginUrl.isEmpty()
					|| postParams == null || postParams.isEmpty()))
					postToLogin(loginUrl, postParams);
				this.urlToCrawl.add(rootUrl);
				this.resultLimit = limit;
				
				new Thread(this).start();
			}
		}
	}
	public void run () {
		try {
			while (true) {
				if (urlToCrawl.size() != 0) {
					String strUrl = urlToCrawl.remove(0);
					url = new java.net.URL(strUrl);
					// get the content of first url
					String content = getResponse(url).toString();
					// put the url/content to result map
					if (content != null) {
						String nohtml = trimExtraSpaces(removeScript(new String(content)).replaceAll("\\<.*?>",""));
						
						getResult().put(strUrl, nohtml);
						// stop
						if (resultLimit > 0 && getResult().size() >= resultLimit)
							break;
						// get all url that have not be crawled 
						addSubUrls(content, url);
					}
					sleep(delayBetweenCrawl);
				}
			}
			synchronized (this) {
				setStopped(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets whether exclude the link that at different domain with root url
	 * @param exclude boolean to exclude other domain, default to true
	 */
	public void setExcludeOtherDomain (boolean exclude) {
		excludeOtherDomain = exclude;
	}
	
	// override
	/*package*/ boolean tryAddUrl (URL urlLink, String strLink) {
		boolean success = false;
		// test and store the url
		try {
			// try opening the URL
			URLConnection urlLinkConnection 
				= urlLink.openConnection();
			setRequestProperties(urlLinkConnection, null);
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
	/*package*/ void setRequestProperties (URLConnection uc, Map properties) {
		if (properties != null) {
			for (Object key : properties.keySet()) {
				uc.setRequestProperty((String)key, (String)properties.get(key));
			}
		}
		setCookies(uc);
	}

	/**
	 * set cookies to connection if any
	 * @param uc
	 */
	private void setCookies (URLConnection uc) {
		for (String cookie : _cookies) {
	  		System.out.println(" cookie pt 3 :: " + cookie);
	  		uc.addRequestProperty("Cookie", cookie.split(";", 2)[0]);
	  	}
	}
	/**
	 * get cookies from given url
	 * @param cookieRetrieveUrl
	 * @throws IOException
	 */
	private void retrieveCookie (String cookieRetrieveUrl) throws IOException {
		int status;
		URL url = new URL(cookieRetrieveUrl);
	  	HttpURLConnection uc = getConnection(url);
		uc.connect();// connect
		status = uc.getResponseCode();
		if (status == java.net.HttpURLConnection.HTTP_OK) {
            if (uc.getHeaderFields().get("Set-Cookie") != null) {
            	_cookies.addAll(uc.getHeaderFields().get("Set-Cookie"));
            }
		}
	}
	/**
	 * login with given url and params
	 * @param postUrl
	 * @param params
	 * @throws IOException
	 */
	private void postToLogin (String postUrl, String params) throws IOException {
		int status;
		URL url = new URL(postUrl);
	  	HttpURLConnection uc = getConnection(url);

	  	uc.setDoOutput(true); // Triggers POST.
		
		OutputStream output = null;
		try {
		     output = uc.getOutputStream();
		     output.write(params.getBytes("UTF-8"));
		} finally {
		     if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
		}
		uc.connect();// connect
		status = uc.getResponseCode();
		if (status == java.net.HttpURLConnection.HTTP_OK) {
            
		}
	}
	/**
	 * remove javascript in content
	 * @param content
	 * @return
	 */
	private String removeScript (String content) {
		int index = 0, i  = 0, j = 0;
		StringBuilder sb = new StringBuilder("");
		while ((i = content.indexOf("<script", i)) != -1) {
			if ((j = content.indexOf("</script", i)) != -1) {
				sb.append(content.substring(index, i));
				index = i = j+9;
			} else
				break;
		}
		sb.append(content.substring(index, content.length()));
		return sb.toString();
	}
	/**
	 * remove extra blank in content
	 * @param content
	 * @return
	 */
	private String trimExtraSpaces (String content) {
		// no tab, make sure all newline is \n
		content = content.replaceAll("\t", " ")
					.replaceAll("\r", "\n");
		while (true) {
			if (content.contains("  "))
				content = content.replaceAll("  ", " ");
			else if (content.contains("\n\n"))
				content = content.replaceAll("\n\n", "\n");
			else if (content.contains(" \n"))
				content = content.replaceAll(" \n", "\n");
			else if (content.contains("\n "))
				content = content.replaceAll("\n ", "\n");
			else
				break;
		}
		return content.replaceAll("\n", "\r\n");
	}
	public static void main (String[] args) throws Exception {
		LoginCrawler crawler = new LoginCrawler();
		String firstCookieUrl = "http://FIRST_COOKIE_URL";
		String postLoginUrl = "http://POST_LOGIN_URL";
		String strUrl = "http://URL_TO_CRAWL";
		String userName = "someone";
		String password = "123";
		String params = "wpName="+userName+"&wpPassword="+password;

		List<String> escapeWords = new ArrayList<String>();

		escapeWords.add("logout");
		escapeWords.add("User_talk:");
		escapeWords.add("User:");
		escapeWords.add("Special:");
		escapeWords.add("action=edit");
		escapeWords.add("action=delete");

		crawler.setEscapeWords(escapeWords);
		crawler.setStopped(true);
		// crawl this url
		crawler.Crawl(strUrl, 1, firstCookieUrl, postLoginUrl, params);
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
			System.out.println(result.get(key));
		}
	}
}
