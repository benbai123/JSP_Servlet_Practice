package bridge.calculator.older;

/** DesiredPowerOfTwoCalculator, implements DesiredCalculator.
 *  
 * @author benbai123
 *
 */
public class DesiredPowerOfTwoCalculator implements DesiredCalculator {
	private Double _source;
	private Double _result;

	public void setSource (Number source) {
		if (source == null) {
			source = 0;
		}
		_source = source.doubleValue();
	}
	public Number getResult() {
		if (_result == null) {
			calc();
		}
		return _result;
	}

	private void calc () {
		_result = Math.pow(2.0, _source.doubleValue());
	}
}
