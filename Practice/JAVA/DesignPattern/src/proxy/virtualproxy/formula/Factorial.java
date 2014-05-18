package proxy.virtualproxy.formula;

/** Factorial
 * Implement FormulaCalc, can calculate Factorial of an Integer.
 * 
 * @author benbai123
 *
 */
public class Factorial implements FormulaCalc {
	private long _result;

	public Factorial (int n) {
		calc(n);
	}
	public Number getResult() {
		return _result;
	}

	private void calc (int n) {
		System.out.println("Calculate the factorial of " + n);
		_result = 1;
		for (int i = 2; i <= n; i++) {
			_result = _result * i;
		}
	}
}
