package factory.factorymethod;

import factory.factorymethod.audience.*;

public class TestMain {
	public static void main (String args[]) {
		new MusicAudience().enjoy();
		new MovieAudience().enjoy();
	}
}
