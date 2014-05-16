package factory.simplefactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import factory.simplefactory.food.Bread;

/** Test for Simple Factory
 * 
 * Simple Factory:
 * 		Is not a GoF Design Pattern, simply returns one of many different classes that
 * 		inherit from the same parent class or implement the same interface
 * 		so the program (client) Can rely on the superclass/interface, do not need to
 * 		know the concrete subclass
 * 
 * @author benbai123
 *
 */
public class TestMain {
	public static void main (String args[]) {
		// today
		Calendar cal = Calendar.getInstance();
		// all bread
		List<Bread> breadList = new ArrayList<Bread>();

		for (int i = 0; i < 7; i++) {
			// get bread
			breadList.add(BreadFactory.produceBread(cal));
			// increase date
			cal.add(Calendar.DATE, 1);
		}
		// show all bread
		System.out.println("Bread for next 7 days:");
		for (Bread b : breadList) {
			System.out.println(b.getDesc());
		}
	}
}
