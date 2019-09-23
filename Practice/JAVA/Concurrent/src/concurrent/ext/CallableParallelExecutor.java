package concurrent.ext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * Execute multiple Callable in parallel and
 * get their Results/Errors in order
 * 
 * @author benbai123
 *
 * @param <T>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CallableParallelExecutor<T> {
	/** the Callables should be called */
	List<Callable<T>> _tasks;
	/** Wrappers, wrap Callable to provide more methods */
	List<CallableWrapper<T>> _wrappedTasks;
	/** Maximum concurrent Threads */
	private int _poolSize = 5;
	
	/**
	 * Run all Callable in Parallel
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public List<CallableWrapper<T>> execute (List<Callable<T>> tasks) throws InterruptedException {
		setTasks(tasks);
		// nothing to execute, return
		if (!hasTasksToRun()) return _wrappedTasks;
		// execute
		ExecutorService taskExecutor = Executors.newFixedThreadPool(_poolSize);
		for (CallableWrapper t : _wrappedTasks) {
			taskExecutor.execute(t);
		}
		taskExecutor.shutdown();
		taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.MICROSECONDS);
		return _wrappedTasks;
	}
	/**
	 * Set Maximum concurrent Threads
	 * 
	 * @param poolSize
	 * @return
	 */
	public CallableParallelExecutor setPoolSize (int poolSize) {
		_poolSize = poolSize;
		return this;
	}
	
	/**
	 * Set Callables
	 * 
	 * @param tasks
	 * @return
	 */
	private CallableParallelExecutor setTasks (List<Callable<T>> tasks) {
		// store real Callables
		_tasks = tasks;
		/* Wrap them with CallableWrapper
		 * so can cache their Result or Exception in order
		 */
		_wrappedTasks = new ArrayList<CallableWrapper<T>>();
		for (Callable c : tasks) {
			CallableWrapper t = new CallableWrapper(c);
			_wrappedTasks.add(t);
		}
		return this;
	}
	private boolean hasTasksToRun() {
		return _wrappedTasks != null && !_wrappedTasks.isEmpty();
	}
}
