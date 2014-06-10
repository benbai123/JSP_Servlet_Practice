package objectpool;

import objectpool.thread.ThreadPool;

/** Object Pool Pattern:
 * 		The object pool pattern is a software creational design pattern that uses
 * 		a set of initialized objects kept ready to use ¡V a "pool" ¡V rather than
 * 		allocating and destroying them on demand. A client of the pool will request
 * 		an object from the pool and perform operations on the returned object. When
 * 		the client has finished, it returns the object to the pool rather than
 * 		destroying it; this can be done manually or automatically.
 * 
 * Reference:
 * 		http://en.wikipedia.org/wiki/Object_pool_pattern
 * 
 * @author benbai123
 *
 */
public class TestMain {
	public static void main (String args[]) throws InterruptedException {
		// execute two tasks, will create two new WorkingThreads.
		ThreadPool.execute(getTask("Task A"));
		ThreadPool.execute(getTask("Task B"));
		// wait the 2 jobs done.
		Thread.sleep(3000);
		// execute three tasks, will reuse 2 old threads and create 1 new thread.
		ThreadPool.execute(getTask("Task C"));
		ThreadPool.execute(getTask("Task D"));
		ThreadPool.execute(getTask("Task E"));
	}
	// function for create a task
	private static Runnable getTask (final String task) {
		return new Runnable () { // create a task (Runnable object)
			public void run () { // the run function
				int i = 3;
				while (i > 0) {
					try {
						// output task 3 times
						System.out.println(task);
						Thread.sleep(100);
						i--;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}
		};
	}
}
