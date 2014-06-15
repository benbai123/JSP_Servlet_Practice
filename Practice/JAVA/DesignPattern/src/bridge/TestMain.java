package bridge;

import bridge.calculator.older.BridgeOfDesiredCalculator;
import bridge.calculator.older.DesiredFactorialCalculator;
import bridge.calculator.older.DesiredPowerOfTwoCalculator;

/** Bridge Pattern:
 * 		The bridge pattern is a design pattern used in software engineering which is meant to
 * 		"decouple an abstraction from its implementation so that the two can vary independently".
 * 		The bridge uses encapsulation, aggregation, and can use inheritance to separate
 * 		responsibilities into different classes.
 * 		When a class varies often, the features of object-oriented programming become very useful
 * 		because changes to a program's code can be made easily with minimal prior knowledge about the program.
 * 		The bridge pattern is useful when both the class as well as what it does vary often. The class itself
 * 		can be thought of as the implementation and what the class can do as the abstraction.
 * 		The bridge pattern can also be thought of as two layers of abstraction.
 * 
 * Reference:
 * 		http://en.wikipedia.org/wiki/Bridge_pattern
 * 
 * @author benbai123
 *
 */
public class TestMain {
	public static void main (String args[]) {
		// at the beginning,
		// there are an interface DesiredCalculator
		// and several (2 here) concrete classes implement that interface
		// however you always access them via a Bridge class BridgeOfDesiredCalculator
		BridgeOfDesiredCalculator fracBridge = new BridgeOfDesiredCalculator(new DesiredFactorialCalculator());
		BridgeOfDesiredCalculator powBridge = new BridgeOfDesiredCalculator(new DesiredPowerOfTwoCalculator());
		fracBridge.setSource(5);
		powBridge.setSource(5);
		// show result of Factorial Calculator and PowerOfTwo Calculator
		System.out.println("5! = " + fracBridge.getResult());
		System.out.println("2^5 = " + powBridge.getResult());

		// after several days,
		// the API of DesiredCalculator is changed
		// setSource -> setData, getResult -> getValue
		// all the concrete classes implement the new API
		// but you just need to change the content within Bridge class,
		// all other parts of your program can stay unchanged.
		//
		// NOTE:
		// For convenience they are separated to 2 different packages,
		// but they should be exactly the same classes with different content in different time.
		bridge.calculator.newer.BridgeOfDesiredCalculator newFracBridge =
				new bridge.calculator.newer.BridgeOfDesiredCalculator(new bridge.calculator.newer.DesiredFactorialCalculator());
		bridge.calculator.newer.BridgeOfDesiredCalculator newPowBridge =
				new bridge.calculator.newer.BridgeOfDesiredCalculator(new bridge.calculator.newer.DesiredPowerOfTwoCalculator());
		newFracBridge.setSource(5);
		newPowBridge.setSource(5);
		// show result of Factorial Calculator and PowerOfTwo Calculator
		System.out.println("5! = " + newFracBridge.getResult());
		System.out.println("2^5 = " + newPowBridge.getResult());
	}
}
