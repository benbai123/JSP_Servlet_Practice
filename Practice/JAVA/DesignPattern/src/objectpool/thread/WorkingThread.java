package objectpool.thread;

/** WorkingThread, run specified task.
 * 
 * @author benbai123
 *
 */
public class WorkingThread extends Thread {
	private Runnable _task;
	
	public void run () {
		while (true) {
			// synchronize on self for wait/notify
			synchronized (this) {
				try {
					// run task if any
					if (_task != null) {
						_task.run();
						_task = null;
					}
					// wait until setRunnable is called
					wait();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void setRunnable (Runnable task) {
		// synchronize on self for wait/notify
		synchronized (this) {
			// set task
			_task = task;
			// continue self run function
			notify();
		}
	}
}
