package composite;

import java.util.ArrayList;
import java.util.List;

import composite.calculator.BetterDesiredCalculator;
import composite.calculator.DesiredFactorialCalculator;
import composite.calculator.DesiredPowerOfTwoCalculator;

/** Composite Pattern:
 * 		the composite pattern is a partitioning design pattern. The composite pattern describes that
 * 		a group of objects are to be treated in the same way as a single instance of an object. The
 * 		intent of a composite is to "compose" objects into tree structures to represent part-whole hierarchies.
 * 		Implementing the composite pattern lets clients treat individual objects and compositions uniformly.
 * 
 * Reference:
 * 		http://en.wikipedia.org/wiki/Composite_pattern
 * 
 * @author benbai123
 *
 */
public class TestMain {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main (String args[]) {
		// a BetterDesiredCalculator is a DesiredCalculator
		// you can build it with several different kind of DesiredCalculator
		// and change its mode to calculate different things as needed
		// 
		BetterDesiredCalculator calc = new BetterDesiredCalculator();
		BetterDesiredCalculator examCalc = new BetterDesiredCalculator();
		BetterDesiredCalculator scienceCalc = new BetterDesiredCalculator();

		
		examCalc.addCalculator("fac", new DesiredFactorialCalculator());
		scienceCalc.addCalculator("pow", new DesiredPowerOfTwoCalculator());
		calc.addCalculator("exam", examCalc);
		calc.addCalculator("science", scienceCalc);
		calc.setSource(5);

		// set mode and calculate
		// probably triggered by user hitting different keys
		List mode = new ArrayList();
		mode.add("exam");
		mode.add("fac");
		calc.setMode(mode);
		System.out.println("5! = " + calc.getResult());

		mode.clear();
		mode.add("science");
		mode.add("pow");
		calc.setMode(mode);
		System.out.println("2 ^ 5 = " + calc.getResult());
	}
}
