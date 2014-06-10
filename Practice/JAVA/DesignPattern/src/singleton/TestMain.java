package singleton;

import singleton.config.AppConfig;

/** Single Pattern
 * 		Singleton pattern is a design pattern that restricts the instantiation of
 * 		a class to one object. This is useful when exactly one object is needed to
 * 		coordinate actions across the system. The concept is sometimes generalized
 * 		to systems that operate more efficiently when only one object exists, or
 * 		that restrict the instantiation to a certain number of objects. The term
 * 		comes from the mathematical concept of a singleton.
 * 
 * References:
 * 		http://en.wikipedia.org/wiki/Singleton_pattern
 * 		http://stackoverflow.com/questions/519520/difference-between-static-class-and-singleton-pattern
 * 		http://stackoverflow.com/questions/2765060/why-use-a-singleton-instead-of-static-methods
 * 
 * @author benbai123
 *
 */
public class TestMain {
	public static void main (String args[]) {
		AppConfig config = AppConfig.getConfig();
		System.out.println(config.getLocale());
		System.out.println(config.getEncoding());
	}
}
