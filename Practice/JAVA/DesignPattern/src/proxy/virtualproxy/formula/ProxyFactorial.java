package proxy.virtualproxy.formula;

import java.util.HashMap;
import java.util.Map;

/** ProxyFactorial
 * Implement FormulaCalc, proxy of the Factorial class to delay the construction and
 * calculation of a Factorial object and cache it.
 * 
 * @author benbai123
 *
 */
public class ProxyFactorial implements FormulaCalc {
	private static Map<Integer, Factorial> _cache = new HashMap<Integer, Factorial>();
	private int _n;
	public ProxyFactorial (int n) {
		_n = n;
	}
	public Number getResult() {
		if (!_cache.containsKey(_n)) {
			synchronized (_cache) {
				if (!_cache.containsKey(_n)) {
					_cache.put(_n, new Factorial(_n));
				}
			}
		}
		Factorial fac = _cache.get(_n);
		return fac.getResult();
	}
}
