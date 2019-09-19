package basic.thread.utils;

public class ThreadUtils {
	/** Make a runnable become debounce
	 * 
	 * usage: to reduce the real processing for some task
	 * 
	 * example: the stock price sometimes probably changes 1000 times in 1 second,
	 * 	but you just want redraw the candlestick of k-line chart after last change+"delay ms"
	 * 
	 * @param realRunner Runnable that has something real to do
	 * @param delay milliseconds that realRunner should wait since last call
	 * @return
	 */
	public static Runnable debounce (Runnable realRunner, long delay) {
		Runnable debounceRunner = new Runnable() {
			// whether is waiting to run
			private boolean _isWaiting = false;
			// target time to run realRunner
			private long _timeToRun;
			// specified delay time to wait
			private long _delay = delay;
			// Runnable that has the real task to run
			private Runnable _realRunner = realRunner;
			@Override
			public void run() {
				// current time
				long now;
				synchronized (this) {
					now = System.currentTimeMillis();
					// update time to run each time
					_timeToRun = now+_delay;
					// another thread is waiting, skip
					if (_isWaiting) return;
					// set waiting status
					_isWaiting = true;
				}
				try {
					// wait until target time
					while (now < _timeToRun) {
						Thread.sleep(_timeToRun-now);
						now = System.currentTimeMillis();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					// clear waiting status before run
					_isWaiting = false;
					// do the real task
					_realRunner.run();
				}
			}};
		return debounceRunner;
	}
	/** Make a runnable become throttle
	 * 
	 * usage: to smoothly reduce running times of some task
	 * 
	 * example: assume the price of a stock often updated 1000 times per second
	 * but you want to redraw the candlestick of k-line at most once per 300ms
	 * 
	 * @param realRunner
	 * @param delay
	 * @return
	 */
	public static Runnable throttle (Runnable realRunner, long delay) {
		Runnable throttleRunner = new Runnable() {
			// whether is waiting to run
			private boolean _isWaiting = false;
			// target time to run realRunner
			private long _timeToRun;
			// specified delay time to wait
			private long _delay = delay;
			// Runnable that has the real task to run
			private Runnable _realRunner = realRunner;
			@Override
			public void run() {
				// current time
				long now;
				synchronized (this) {
					// another thread is waiting, skip
					if (_isWaiting) return;
					now = System.currentTimeMillis();
					// update time to run
					// do not update it each time since
					// you do not want to postpone it unlimited
					_timeToRun = now+_delay;
					// set waiting status
					_isWaiting = true;
				}
				try {
					Thread.sleep(_timeToRun-now);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					// clear waiting status before run
					_isWaiting = false;
					// do the real task
					_realRunner.run();
				}
			}};
		return throttleRunner;
	}
}
