package factory.simplefactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import factory.simplefactory.food.Bread;

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
