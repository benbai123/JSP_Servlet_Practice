package flyweight.fibonacci;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Calculate the sum of a range of Fibonacci sequence
 * 
 * Reference:
 * 		https://en.wikipedia.org/wiki/Fibonacci_number
 * 
 * @author benbai123
 *
 */
public class FibRangedSum {
	/** shared Fibonacci sequence */
	private static List<Integer> _fibsequence = new ArrayList<Integer>();
	/** shared calculated result */
	private static Map<String, FibRangedSum> _results = new HashMap<String, FibRangedSum>();

	/** calculated sum */
	private int _result = 0;
	// private constructor
	private FibRangedSum (int start, int end) {
		if (_fibsequence.size() < end) {
			// add Fibonacci numbers into Fibonacci sequence if needed 
			System.out.println("build sequence " + start + "-" + end);
			synchronized (_fibsequence) {
				buildtList(end);
			}
		}
		// calculate result
		for (int i = start-1; i < end; i++) {
			_result += _fibsequence.get(i);
		}
	}
	// get instance by range, Multiton pattern
	public static FibRangedSum getInstance (int start, int end) {
		String key = start + "-" + end;
		FibRangedSum instance = _results.get(key);
		System.out.println("get instance " + key);
		if (instance == null) {
			System.out.println("create new instance " + key);
			instance = new FibRangedSum(start, end);
			_results.put(key, instance);
		}
		return instance;
	}
	public int getResult () {
		return _result;
	}
	// build Fibonacci sequence by size,
	// not Multiton pattern but still Flyweight pattern
	private void buildtList (int size) {
		int _currentSize = _fibsequence.size();
		for (int i = _currentSize; i < size; i++) {
			if (i > 1) {
				_fibsequence.add(_fibsequence.get(i-2) + _fibsequence.get(i-1));
			} else {
				_fibsequence.add(1);
			}
		}
		System.out.println("sequence after build:");
		for (Integer d : _fibsequence) {
			System.out.print(d + ", ");
		}
		System.out.println();
	}
}
