package proxy.remoteproxy.geo;

import java.io.InputStreamReader;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/** Interface for Google Geo Services
 * 
 * the real Geo Service hosted by Google remotely
 * 
 * @author benbai123
 *
 */
public class GeoServiceProxy {
	public static String getAddressByLatLng (double[] latLng) {
		try {
			String link = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
					+ latLng[0]+","+latLng[1]
					+"&sensor=true&language=ja";
			String result = getResponse(link).toString();
			JsonParser parser = new JsonParser();
			JsonObject o = parser.parse(result).getAsJsonObject();
	
			return o.getAsJsonArray("results")
						.get(0).getAsJsonObject()
						.get("formatted_address").getAsString();
		} catch (Exception e) {
			return "Service not avaiable!";
		}
	}
	public static double[] getLatLngByAddress (String address) {
		try {
			String link = "http://maps.googleapis.com/maps/api/geocode/json?address="
					+java.net.URLEncoder.encode("59 ウォール街 マンハッタン ニューヨーク 10005 アメリカ合衆国", "UTF-8")+"&sensor=true&language=en";
			String result = getResponse(link).toString();
			JsonParser parser = new JsonParser();
			JsonObject o = parser.parse(result).getAsJsonObject();
	
			JsonObject location = o.getAsJsonArray("results")
				.get(0).getAsJsonObject()
				.get("geometry").getAsJsonObject()
				.get("location").getAsJsonObject();
			double[] latLng = {location.get("lat").getAsDouble(),
								location.get("lng").getAsDouble()
							};
			return latLng;
		} catch (Exception e) {
			double[] err = {-999, -999};
			return err;
		}
	}
	private static StringBuilder getResponse(String path){
		try {
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
}
