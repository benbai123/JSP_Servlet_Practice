package test;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

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
}