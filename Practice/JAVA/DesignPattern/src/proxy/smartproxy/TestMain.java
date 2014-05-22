package proxy.smartproxy;

import proxy.smartproxy.operator.OperatorProxy;

/** Test for Smart Proxy Pattern
 * 
 * Smart Proxy:
 * 		A smart proxy interposes additional actions when an object is accessed. Typical uses include: 
 * 			Counting the number of references to the real object so that it can be freed automatically when
 * 			there are no more references (aka smart pointer),
 * 			Loading a persistent object into memory when it's first referenced,
 * 			Checking that the real object is locked before it is accessed to ensure that no other object can change it.
 * 
 * Reference: http://sourcemaking.com/design_patterns/proxy
 * 
 * This sample implements "Counting the number of references" in opposite way,
 * block a thread when there are too much objects.
 * 
 * @author benbai123
 *
 */
public class TestMain {
	public static void main (String args[]) {
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable () {
				public void run () {
					new OperatorProxy().doOperation();
				}
			}).start();
		}
	}
}
