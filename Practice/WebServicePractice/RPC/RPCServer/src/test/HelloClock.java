package test;

/** this class provide the HelloClockService,
  * only one service 'sayHello'
  */
public class HelloClock {
	/**
	 * sayHello service of HelloClockService
	 * @param name the name to say hello
	 * @return HelloClockMessage contains hello message and current time
	 */
	public HelloClockMessage sayHello (String name) {
		return new HelloClockMessage(name);
	}
}
