package bridge.calculator.newer;

/** DesiredFactorialCalculator, implements DesiredCalculator
 * 
 * @author benbai123
 *
 */
public class DesiredFactorialCalculator implements DesiredCalculator {
	private Long _source;
	private Long _result;

	public void setData (Number source) {
		if (source == null) {
			source = 0;
		}
		_source = source.longValue();
	}
	public Number getValue () {
		if (_result == null) {
			calc();
		}
		return _result;
	}

	private void calc () {
		_result = 1L;
		for (int i = 2; i <= _source; i++) {
			_result = _result * i;
		}
	}
}