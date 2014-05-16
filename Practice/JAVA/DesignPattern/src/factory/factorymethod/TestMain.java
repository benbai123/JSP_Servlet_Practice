package factory.factorymethod;

import factory.factorymethod.audience.*;

/** Test for Factory Method
 * 
 * Factory Method:
 * 		Define an interface for creating an object, but let the classes that
 * 		implement the interface decide which class to instantiate, defer
 * 		instantiation to subclasses.
 * 		So subclass can return different class of object to perform different
 * 		action (e.g. play music or play movie in this sample) as needed.
 * 
 * @author benbai123
 *
 */
public class TestMain {
	public static void main (String args[]) {
		new MusicAudience().enjoy();
		new MovieAudience().enjoy();
	}
}
