package basic.thread;

/** Simple demo for java Thread
 * 
 * run the main method, you should see the child Threads (one and two)
 * output some msgs when the Main Thread is running
 * 
 * @author benbai123
 *
 */
public class MultiThread {
	public static void main (String[] args) throws InterruptedException {
		System.out.println("Main Thread: start Thread one");
		startNewThread("Thread one", 1000, 6);
		// add some delay
		Thread.sleep(300);
		
		System.out.println("Main Thread: start Thread two");
		startNewThread("Thread two", 1500, 3);
		Thread.sleep(300);
		
		System.out.println("Main Thread: start Thread three");
		startNewThread("Thread three", 2000, 2);
		
		startNewThreadWithImpl("Thread four", 2500, 2);
	}

	private static void startNewThread(String msg, long interval, int totalRuns) {
		Runnable runner = new Runner(msg, interval, totalRuns);
		// create a thread with the created Runner
		Thread th = new Thread(runner);
		th.start();
	}
	
	private static void startNewThreadWithImpl(String msg, long interval, int totalRuns) {
		// can also new and implement Runnable directly
		new Thread(new Runnable () {
			@Override
			public void run() {
				int cnt = 1;
				while (cnt <= totalRuns) {
					System.out.println(msg + " run " + cnt + " time(s)");
					try {
						Thread.sleep(interval);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cnt++;
				}
			}
		}).start();
	}

	/** Runnable is an interface,
	 * 
	 * has a method "public void run ()" that
	 * should be implemented
	 * 
	 * @author benbai123
	 *
	 */
	private static class Runner implements Runnable {
		// msg to print
		String _msg = "";
		// delay between each run
		long _interval;
		// total runs
		int _totalRuns;
		
		// current run
		int _cnt = 1;
		
		public Runner (String msg, long interval, int totalRuns) {
			_msg = msg;
			_interval = interval;
			_totalRuns = totalRuns;
		}
		public void run () {
			while (_cnt <= _totalRuns) {
				// print msg and current run
				System.out.println(_msg + " run " + _cnt + " time(s)");
				// take a break
				try {
					Thread.sleep(_interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// step forward
				_cnt++;
			}
		}
	}
}
