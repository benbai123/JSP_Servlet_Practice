package factory.abstractfactory;

import java.util.Calendar;

import factory.abstractfactory.food.*;
import factory.abstractfactory.foodfactory.*;

/** Food Store, sells Bread in odd weeks and sells PASTA in even weeks.
 * 
 * @author benbai123
 *
 */
public class FoodStore {
	public static Food getTodaysFood (Calendar cal) {
		return getTodaysFactory(cal).produceFood(cal);
	}
	private static FoodFactory getTodaysFactory (Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}

		// odd weeks
		if ((cal.get(Calendar.WEEK_OF_YEAR) % 2) == 0) {
			return new BreadFactory();
		}
		// even weeks
		return new PastaFactory();
	}
}
