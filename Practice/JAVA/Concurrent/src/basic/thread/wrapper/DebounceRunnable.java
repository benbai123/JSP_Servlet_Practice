package basic.thread.wrapper;

import basic.thread.utils.ThreadUtils;

public class DebounceRunnable implements Runnable {
	/** whether is waiting to run or finish */
	private boolean _isWaiting;
	/** target time to run realRunner or finish */
	private long _timeToRun;
	/** specified delay time to wait */
	private long _delay;
	/** Runnable that has the real task to run */
	private Runnable _realRunner;
	/** whether run immediate
	 * ( on the leading instead of the trailing edge of the wait interval) */
	private boolean _immediate;

	public static DebounceRunnable wrap (Runnable realRunner) {
		return wrap(realRunner, 500, false);
	}
	public static DebounceRunnable wrap (Runnable realRunner, long delay) {
		return wrap(realRunner, delay, false);
	}
	public static DebounceRunnable wrap (Runnable realRunner, long delay,
			boolean immediate) {
		DebounceRunnable debounceRunner = new DebounceRunnable();
		debounceRunner._realRunner = realRunner;
		debounceRunner._delay = delay;
		debounceRunner._immediate = immediate;
		return debounceRunner;
	}
	
	@Override
	public void run() {
		// current time
		System.out.println(ThreadUtils.getTime()+" DebouncRunnable"+(_immediate? " immediate" : "")+" run");
		long now;
		synchronized (this) {
			now = System.currentTimeMillis();
			// update time to run each time
			_timeToRun = now + _delay;
			// another thread is waiting, skip
			if (_isWaiting)
				return;
			// set waiting status
			_isWaiting = true;
			/* run at the beginning
			 * cannot be called again if not wait until the _delay milliseconds
			 * after last call
			 */
			if (_immediate) {
				// do the real task
				_realRunner.run();
			}
		}
		try {
			// wait until target time
			while (now < _timeToRun) {
				Thread.sleep(_timeToRun - now);
				now = System.currentTimeMillis();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// clear waiting status
			_isWaiting = false;
			/* do the real task
			 * run at the end
			 * cannot be called again if not wait until the _delay milliseconds
			 * after last call
			 */
			if (!_immediate) {
				_realRunner.run();
			}
		}
	}
}
