package adapter;

import adapter.calculator.*;

/** Adapter Pattern:
 * 		The adapter design pattern is used when you want two different classes with
 * 		incompatible interfaces to work together. Interfaces may be incompatible but
 * 		the inner functionality should suit the need. The Adapter pattern allows
 * 		otherwise incompatible classes to work together by converting the interface of
 * 		one class into an interface expected by the clients.
 * 
 * Reference:
 * 		http://en.wikipedia.org/wiki/Adapter_pattern
 * 
 * @author benbai123
 *
 */
public class TestMain {
	public static void main (String args[]) {
		// show result of Factorial Calculator and PowerOfTwo Calculator
		System.out.println(getResult(new DesiredFactorialCalculator(), 5));
		System.out.println(getResult(new DesiredPowerOfTwoCalculator(), 5));
	}
	private static Number getResult (DesiredCalculator calc, Number source) {
		calc.setSource(source);
		return calc.getResult();
	}
}
