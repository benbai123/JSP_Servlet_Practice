package proxy.remoteproxy;

import java.awt.AWTException;
import java.io.UnsupportedEncodingException;

import com.google.gson.JsonSyntaxException;

import proxy.remoteproxy.geo.GeoServiceProxy;

/** Test for Remote Proxy Pattern
 * 
 * Remote Proxy Pattern
 * 		A remote proxy can be thought about the stub in the RPC call. The
 * 		remote proxy provides a local representation of the object which is present in
 * 		the different address location. Another example can be providing interface for
 * 		remote resources such as web service or REST resources.
 * 
 * Ref: http://idiotechie.com/gang-of-four-proxy-design-pattern/
 * 
 * @author benbai123
 *
 */
public class TestMain {
	public static void main (String args[]) throws AWTException, InterruptedException, JsonSyntaxException, UnsupportedEncodingException {
		String address = "アメリカ合衆国 〒10005 ニューヨーク ウォール街 59-63";
		double[] latLng = {40.7059659, -74.0087841};
		// get Address by Lat/Lng
		String resultAddress = GeoServiceProxy.getAddressByLatLng(latLng);
		// get Lat/Lng by Address
		double[] resultLatlng = GeoServiceProxy.getLatLngByAddress(address);
		if ("Service not avaiable!".equals(resultAddress)) {
			// error
		} else {
			System.out.println(resultAddress);
		}
		if (resultLatlng[0] == -999) {
			// error
		} else {
			System.out.println("Lat: " + resultLatlng[0] + ", Lng: " + resultLatlng[1]);
		}
	}
}
