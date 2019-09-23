package concurrent.ext;

import java.util.UUID;
import java.util.concurrent.Callable;

/** Wrap the real Callable to cache result and add extra methods
 * 
 * also implements Runnable since ExecutorService#execute only allow Runnable
 * 
 * @author benbai123
 *
 * @param <T>
 */
public class CallableWrapper<T> implements Runnable, Callable<T> {
	/** generate ID by self for convenience,
	 * for better performance or flexibility you can
	 * also provide a setter or constructor to pass id in
	 */
	private String _id = UUID.randomUUID().toString();
	/** Result returned from real Callable */
	private T _result;
	/** Exception during call method of real Callable */
	private Exception _error = null;
	/** the real Callable to call */
	private Callable<T> _realCallableToCall;
	/** whether the real Callable is called */
	private boolean _isCalled = false;

	public CallableWrapper (Callable<T> realCallableToCall) {
		_realCallableToCall = realCallableToCall;
	}
	/** sad, ExecutorService#execute only for Runnable
	 * 
	 */
	@Override
	public void run () {
		call();
	}
	/** wrap origin Callable call
	 * 
	 */
	@Override
	public T call () {
		_isCalled = true;
		try {
			_result = _realCallableToCall.call();
		} catch (Exception e) {
			_error = e;
		}
		return _result;
	}
	public String getId () {
		return _id;
	}
	/** get the result generated by original Callable
	 * 
	 * @return
	 */
	public T getResult () {
		if (!_isCalled) {
			throw new RuntimeException("You should call me at least once! my id is "+_id);
		}
		return _result;
	}
	/** Get the exception thrown by Callback#call
	 * 
	 * @return
	 */
	public Exception getError () {
		return _error;
	}
}
