package factory.abstractfactory.foodfactory;

import java.util.Calendar;

import factory.abstractfactory.food.*;

/** A PASTA factory that produce WonderfulPasta at 5th day of a week and
 * produce CommonPasta in other days
 * 
 * @author benbai123
 *
 */
public class PastaFactory extends FoodFactory {
	public Food produceFood (Calendar cal) {
		if (cal == null) {
			cal = Calendar.getInstance();
		}

		// 5th day of week
		if (cal.get(Calendar.DAY_OF_WEEK) == 5) {
			return new WonderfulPasta();
		}
		// other days
		return new CommonPasta();
	}
}