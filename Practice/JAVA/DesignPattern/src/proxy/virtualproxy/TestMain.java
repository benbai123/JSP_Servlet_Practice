package proxy.virtualproxy;

import proxy.virtualproxy.formula.ProxyFactorial;

/** Test for Virtual Proxy (Lazy Initialization)
 * 
 * Virtual Proxy:
 * 		A Virtual Proxy creates expensive objects on demand.
 * 
 * 		Lazy initialization is the tactic of delaying the creation of an
 * 		object, the calculation of a value, or some other expensive
 * 		process until the first time it is needed.
 * 
 * @author benbai123
 *
 */
public class TestMain {
	public static void main (String args[]) {
		// first time ask 25!, calculation required
		System.out.println(new ProxyFactorial(25).getResult());
		// first time ask 10!, calculation required
		System.out.println(new ProxyFactorial(10).getResult());
		// second time, do not need to calculate
		System.out.println(new ProxyFactorial(10).getResult());
		System.out.println(new ProxyFactorial(25).getResult());
	}
}
