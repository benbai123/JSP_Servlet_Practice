package test;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

/**
 * required jar files:
 * json-lib: http://sourceforge.net/projects/json-lib/files/json-lib/
 * commons-beanutils-1.8.3.jar http://commons.apache.org/beanutils/download_beanutils.cgi
 * commons-collections-3.2.1.jar http://commons.apache.org/collections/download_collections.cgi
 * commons-lang-2.5.jar http://commons.apache.org/lang/download_lang.cgi
 * commons-logging-1.1.1.jar http://commons.apache.org/logging/download_logging.cgi
 * ezmorph-1.0.6.jar http://sourceforge.net/projects/ezmorph/files/ezmorph/
 *
 */
public class GeocodeService {
	public static void main (String args[]) {
		String result = null;
		try {
			System.out.println("First request\n\n");
			// request service by Lat, Lng
			StringBuilder sb = getResponse("http://maps.googleapis.com/maps/api/geocode/json?latlng=40.70594140,-74.0088760&sensor=true&language=ja");
			result = sb.toString();
			System.out.println(result+"\n");
			System.out.println("Second request\n\n");
			sb.setLength(0);
			// request service by address
			sb = getResponse("http://maps.googleapis.com/maps/api/geocode/json?address="
					+java.net.URLEncoder.encode("59 ウォール街 マンハッタン ニューヨーク 10005 アメリカ合衆国", "UTF-8")+"&sensor=true&language=en");
			result = sb.toString();
			System.out.println(result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static StringBuilder getResponse(String path){
		try {
			System.out.println(path);
			java.net.URL url = new java.net.URL(path);
			java.net.HttpURLConnection uc = (java.net.HttpURLConnection) url.openConnection();
			uc.setRequestProperty("User-agent", "Mozilla/5.0");

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
		return null;
	}
	/**
	 * return formatted address by given lat/lng
	 * @param lat
	 * @param lng
	 * @param lang
	 * @return
	 */
	public static String getAddressByLatLng (double lat, double lng, String lang) {
		StringBuilder sb = getResponse("http://maps.googleapis.com/maps/api/geocode/json?"
				+ "latlng="+lat+","+lng
				+ "&sensor=true&language="+lang);
		JSONObject jobj = JSONObject.fromObject(sb.toString());
		return jobj.getJSONArray("results").getJSONObject(0).getString("formatted_address");
	}
}