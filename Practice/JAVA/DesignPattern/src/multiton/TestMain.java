package multiton;

import java.util.Map;
import java.util.Set;

import multiton.vitamin.Vitamin;
import multiton.vitamin.VitaminBox;

/** Test for Multiton Pattern (registry of singletons)
 * 
 * Multiton Pattern:
 * 		The multiton pattern expands on the singleton concept to manage a map of
 * 		named instances as key-value pairs.
 * 		Rather than having a single instance per application
 * 		(e.g. the java.lang.Runtime object in the Java programming language) the
 * 		multiton pattern instead ensures a single instance per key.
 * 
 * Reference: http://en.wikipedia.org/wiki/Multiton_pattern
 * 
 * @author benbai123
 *
 */
public class TestMain {
	public static void main (String[] args) {
		VitaminBox.add(new Vitamin("Vitamin C"));
		VitaminBox.add(new Vitamin("Vitamin C"));
		VitaminBox.add(new Vitamin("Vitamin A"));
		VitaminBox.add(new Vitamin("Vitamin A"));
		VitaminBox.add(new Vitamin("Vitamin B"));

		Set<Map.Entry<String, VitaminBox>> entries = VitaminBox.getEntries();
		System.out.println("There are " + entries.size() + " VitaminBox(es).");
		for (Map.Entry<String, VitaminBox> entry : entries) {
			String name = entry.getKey();
			System.out.println(entry.getValue().getVitaminAmount() + " " + name
								+ " in " + name + " box");
		}
	}
}
