package test;

import java.io.InputStreamReader;

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
public class IPLookupService {
	public static void main (String[] args) {
		AddressInfo info = ipToLocation("98.76.54.32");
		System.out.println(info.getCountryName());
		System.out.println(info.getCountryCode());
		System.out.println(info.getCity());
		System.out.println(info.getLat());
		System.out.println(info.getLng());
		System.out.println(info.getFormattedAddress());
	}
	/**
	 * return country, city, address by given IP
	 * @param ip
	 * @return
	 */
	public static AddressInfo ipToLocation (String ip) {
		String country;
		AddressInfo info = null;
		double lat;
		double lng;
		StringBuilder sb = getResponse(
				"http://api.hostip.info/get_json.php?ip=" + ip + "&position=true");
		JSONObject jobj = JSONObject.fromObject(sb.toString());
		lat = jobj.getDouble("lat");
		lng = jobj.getDouble("lng");
		info = new AddressInfo(jobj.getString("country_name"), jobj.getString("country_code"),
				jobj.getString("city"), lat, lng,
				GeocodeService.getAddressByLatLng(lat, lng, "ja"));
		return info;
	}
	public static StringBuilder getResponse(String path){
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
class AddressInfo {
	private String _countryName;
	private String _countryCode;
	private String _city;
	private double _lat;
	private double _lng;
	private String _formattedAddress;
	AddressInfo (String countryName, String countryCode,
		String city, double lat, double lng, String formattedAddress) {
		_countryName = countryName;
		_countryCode = countryCode;
		_city = city;
		_lat = lat;
		_lng = lng;
		_formattedAddress = formattedAddress;
	}
	public String getCountryName () {
		return _countryName;
	}
	public String getCountryCode () {
		return _countryCode;
	}
	public String getCity () {
		return _city;
	}
	public double getLat () {
		return _lat;
	}
	public double getLng () {
		return _lng;
	}
	public String getFormattedAddress () {
		return _formattedAddress;
	}
}