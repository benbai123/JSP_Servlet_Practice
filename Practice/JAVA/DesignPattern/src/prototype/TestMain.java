package prototype;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import prototype.membership.Membership;

/** Prototype Pattern
 * 
 * The prototype pattern is a creational design pattern in software development. 
 * It is used when the type of objects to create is determined by a prototypical instance,
 * which is cloned to produce new objects. This pattern is used to:
 * 		* avoid subclasses of an object creator in the client application, like the abstract factory pattern does.
 * 		* avoid the inherent cost of creating a new object in the standard way (e.g., using the 'new' keyword) when
 * 		it is prohibitively expensive for a given application.
 * 
 * Reference:
 * 
 * 		http://en.wikipedia.org/wiki/Prototype_pattern
 * 
 * @author benbai123
 *
 */
/** Assume the memberships of a gym, there are some default packages you can choose,
 * you can also discuss with sales to obtain a custom package better for you
 * 
 * @author benbai123
 *
 */
public class TestMain {
	private static Map<String, Membership> basicMemberships = new LinkedHashMap<String, Membership>();

	public static void main (String args[]) {
		initBasicMemberships();
		Membership customizedBronze = customize(basicMemberships.get("Bronze"), 10, "Can use x-bike and eat pizza for free.");
		Membership customizedSilver = customize(basicMemberships.get("Silver"), -4, "Pay 5 years at once for 20% off.");
		System.out.println("There are " + basicMemberships.size() + " basic memberships:");
		for (String s : basicMemberships.keySet()) {
			System.out.println(s + " membership,");
			displayMembership(basicMemberships.get(s));
		}
		System.out.println("and some customized Memberships:");
		System.out.println("Customized Bronze membership,");
		displayMembership(customizedBronze);
		System.out.println("Customized Silver membership,");
		displayMembership(customizedSilver);
	}

	private static void initBasicMemberships () {
		List<String> content = new ArrayList<String>();

		// assume need to query content of basic memberships
		// from database via network,
		// so store them to prevent query each time
		content.add("Can use dumbbell and barbell.");
		basicMemberships.put("Bronze", new Membership(10, content));

		content.add("Can use swimming pool and x-bike.");
		basicMemberships.put("Silver", new Membership(20, content));

		content.add("Can eat pizza and drink beer for free.");
		basicMemberships.put("Gold", new Membership(30, content));
	}
	private static Membership customize(Membership membership, int monthlyFeeToAdd, String content) {
		// clone and customize membership
		Membership customizedMembership = membership.clone();

		customizedMembership.setMonthlyFee(membership.getMonthlyFee() + monthlyFeeToAdd);
		customizedMembership.getContent().add(content);
		return customizedMembership;
	}
	private static void displayMembership (Membership m) {
		System.out.println("Monthly Fee: " + m.getMonthlyFee());
		System.out.println("Content:");
		for (String cnt : m.getContent()) {
			System.out.println(cnt);
		}
		System.out.println();
	}
}
