package builder;

import builder.donburi.*;

/** Test for Builder Pattern.
 * 
 * Builder Pattern:
 * 		Separate the construction of a complex object from its representation,
 * 		allowing the same construction process to create various representations.
 * 
 * 		intention of the builder pattern is to find a solution to the telescoping constructor anti-pattern.
 * 		Uses another object, a builder, that receives each initialization parameter step by step and then
 * 		returns the resulting constructed object at once, rather than using numerous constructors, the builder pattern
 *  
 * 		Also can be used for objects that contain flat data (e.g. dom tree, query statement)
 * 		
 * 
 * @author benbai123
 *
 */
public class TestMain {
	public static void main (String args[]) {
		Donburi don = new DonburiBuilder().base("Brown Rice")
						.ingredient("Beef")
						.ingredient("Tonkatsu")
						.ingredient("Fried Chicken")
						.build();
		don.showDesc();
	}
}
