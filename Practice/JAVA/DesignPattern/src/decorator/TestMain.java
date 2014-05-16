package decorator;

import decorator.siren.*;

/** Test for Decorator
 * 
 * Decorator:
 * 		a design pattern that allows behavior to be added to an
 * 		individual object, either statically or dynamically, without
 * 		affecting the behavior of other objects from the same class.
 * 
 * @author benbai123
 *
 */
public class TestMain {
	public static void main (String args[]) throws InterruptedException {
		// alarm by an OldSiren
		new OldSiren().alarm();
		Thread.sleep(3000);
		// alarm by an OldSiren that wrapped by a BeepDecorator
		new BeepDecorator(new OldSiren()).alarm();
	}
}
