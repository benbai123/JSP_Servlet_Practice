package factory.abstractfactory.foodfactory;

import java.util.Calendar;

import factory.abstractfactory.food.*;

/** A bread factory that produce WonderfulBread at 3rd day of a week and
 * produce CommonBread in other days
 * 
 * @author benbai123
 *
 */
public class BreadFactory extends FoodFactory {
	public Food produceFood (Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}

		// 3rd day of week
		if (cal.get(Calendar.DAY_OF_WEEK) == 3) {
			return new WonderfulBread();
		}
		// other days
		return new CommonBread();
	}
}
