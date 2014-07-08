package flyweight;

import flyweight.fibonacci.FibRangedSum;

/** Test for Flyweight
 * 
 * Flyweight:
 * 		flyweight is a software design pattern.
 * 		A flyweight is an object that minimizes memory use by sharing as
 * 		much data as possible with other similar objects; it is a way to
 * 		use objects in large numbers when a simple repeated representation
 * 		would use an unacceptable amount of memory. Often some parts of the
 * 		object state can be shared, and it is common practice to hold them in
 * 		external data structures and pass them to the flyweight objects
 * 		temporarily when they are used.
 * 
 * Reference:
 * 		http://en.wikipedia.org/wiki/Flyweight_pattern
 * 
 * @author benbai123
 *
 */
public class TestMain {
	public static void main (String args[]) {
		System.out.println("Calculate sum from Fib(1) to Fib(3)");
		System.out.println("Result: " + FibRangedSum.getInstance(1, 3).getResult() + "\n\n");
		System.out.println("Calculate sum from Fib(2) to Fib(3)");
		System.out.println("Result: " + FibRangedSum.getInstance(2, 3).getResult() + "\n\n");
		System.out.println("Calculate sum from Fib(2) to Fib(5)");
		System.out.println("Result: " + FibRangedSum.getInstance(2, 5).getResult() + "\n\n");
		System.out.println("Calculate sum from Fib(1) to Fib(3)");
		System.out.println("Result: " + FibRangedSum.getInstance(1, 3).getResult() + "\n\n");
		System.out.println("Calculate sum from Fib(2) to Fib(3)");
		System.out.println("Result: " + FibRangedSum.getInstance(2, 3).getResult() + "\n\n");
	}
}
