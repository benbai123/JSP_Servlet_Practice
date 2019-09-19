package basic.thread;

import basic.thread.utils.ThreadUtils;

public class MultiThreadDebounceThrottle {
	public static void main (String[] args) throws InterruptedException {
		Runnable debounceRunnable = getDebounceRunnable(900L);
		Runnable throttleRunnable = getThrottleRunnable(500L);
		// assume there are many threads will want to run this runnable
		int[] lo_delayArray = new int[] {100, 100, 100, 100, 100, 100, 1000, 100, 100, 100, 100, 100, 100, 1000};
		// the runnable will only run if not called again before delay
		for (int delay : lo_delayArray) {
			new Thread(debounceRunnable).start();
			Thread.sleep(delay);
		}
		Thread.sleep(2000);
		// the runnable will run every 500ms+
		for (int delay : lo_delayArray) {
			new Thread(throttleRunnable).start();
			Thread.sleep(delay);
		}
	}

	private static Runnable getDebounceRunnable(long delay) {
		Runnable runner = new Runnable () {
			@Override
			public void run() {
				System.out.println("debounceRunner run ");
			}
		};
		Runnable debounceRunner = ThreadUtils.debounce(runner, delay);
		return debounceRunner;
	}
	private static Runnable getThrottleRunnable(long delay) {
		Runnable runner = new Runnable () {
			@Override
			public void run() {
				System.out.println("throttleRunner run ");
			}
		};
		Runnable throttleRunner = ThreadUtils.throttle(runner, delay);
		return throttleRunner;
	}
}
