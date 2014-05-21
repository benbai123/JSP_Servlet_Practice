package proxy.protectionproxy;

import proxy.protectionproxy.mouse.ProxyMouseController;

/** Test for Protection Proxy Pattern
 * 
 * Protection Proxy Pattern
 * 		The protective proxy acts as an authorisation layer to verify if the actual user has
 * 		access to appropriate content. An example can be thought about the proxy server which
 * 		provides restrictive internet access in office. Only the websites and contents which
 * 		are valid will be allowed and the remaining ones will be blocked.
 * 
 * Ref: http://idiotechie.com/gang-of-four-proxy-design-pattern/
 * 
 * @author benbai123
 *
 */
public class TestMain {
	public static void main (String args[]) throws InterruptedException {
		// create ProxyMouseController with role "Admin"
		ProxyMouseController adminController = new ProxyMouseController("Admin");
		// create ProxyMouseController with role "Guest"
		ProxyMouseController guestController = new ProxyMouseController("Guest");
		// Control mouse by admin
		adminController.moveTo(500, 500);
		Thread.sleep(1000);
		adminController.moveTo(550, 550);
		Thread.sleep(1000);
		// Control mouse by guest (will not work)
		guestController.moveTo(250, 250);
		Thread.sleep(1000);
		guestController.moveTo(300, 300);
	}
}
