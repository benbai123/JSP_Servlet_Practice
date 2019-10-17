package basic.thread.wrapper;

import basic.thread.utils.ThreadUtils;

public class ThrottleRunnable implements Runnable {
	// whether is waiting to run
	private boolean _isWaiting = false;
	// target time to run realRunner
	private long _timeToRun;
	// specified delay time to wait
	private long _delay;
	// Runnable that has the real task to run
	private Runnable _realRunner;
	public static ThrottleRunnable wrap (Runnable realRunner, long delay) {
		ThrottleRunnable tr = new ThrottleRunnable();
		tr._realRunner = realRunner;
		tr._delay = delay;
		return tr;
	}
	@Override
	public void run() {
		System.out.println(ThreadUtils.getTime()+" ThrottleRunnable run");
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
			Thread.sleep(_timeToRun - now);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// clear waiting status before run
			_isWaiting = false;
			// do the real task
			_realRunner.run();
		}
	}
}
