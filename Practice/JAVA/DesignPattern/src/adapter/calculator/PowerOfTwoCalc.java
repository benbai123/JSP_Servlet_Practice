package adapter.calculator;

/** PowerOfTwoCalc, probably wrote by others, provide the functions but didn't implement DesiredCalculator
 * 
 * @author benbai123
 *
 */
public class PowerOfTwoCalc {
	private Double _result;

	public PowerOfTwoCalc (Double n) {
		calc(n);
	}
	public Double getValue() {
		return _result;
	}

	private void calc (Double n) {
		_result = Math.pow(2.0, n.doubleValue());
	}
}
