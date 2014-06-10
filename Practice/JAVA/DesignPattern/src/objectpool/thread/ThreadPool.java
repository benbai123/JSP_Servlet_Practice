package objectpool.thread;

import java.util.ArrayList;
import java.util.List;

/** ThreadPool, create and handle WorkingThread to do tasks.
 * 
 * @author benbai123
 *
 */
public class ThreadPool {
	private static List<WorkingThread> _idlePool = new ArrayList<WorkingThread>();
	private static int _cnt = 1;
	public static void execute (final Runnable task) {
		final WorkingThread thread = getOrCreateThread ();
		// wrap original task (Decorator Pattern)
		Runnable wrappedTask = new Runnable () {
			public void run () {
				// do original task
				task.run();
				// restore working thread to idle pool
				restoreThread(thread);
			}
		};
		thread.setRunnable(wrappedTask);
	}
	private static synchronized  WorkingThread getOrCreateThread () {
		WorkingThread thread = null;
		// get thread from idle pool if any
		if (_idlePool.size() > 0) {
			thread = _idlePool.remove(0);
			System.out.println("Reuse thread " + thread.getName());
			return thread;
		}
		// create new WorkingThread
		thread = new WorkingThread();
		thread.setName("WorkingThread - " + _cnt);
		_cnt++;
		System.out.println("Create new thread " + thread.getName());
		thread.start();
		return thread;
	}
	private static synchronized void restoreThread (WorkingThread thread) {
		// restore WorkingThread to idle pool to reuse
		_idlePool.add(thread);
	}
}
