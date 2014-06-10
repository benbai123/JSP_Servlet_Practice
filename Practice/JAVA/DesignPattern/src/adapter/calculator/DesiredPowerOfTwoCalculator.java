package adapter.calculator;

/** DesiredPowerOfTwoCalculator, implements DesiredCalculator and use PowerOfTwoCalc
 * to perform the real operation.
 * 
 * @author benbai123
 *
 */
public class DesiredPowerOfTwoCalculator implements DesiredCalculator {
	private PowerOfTwoCalc _calc;

	public void setSource(Number source) {
		_calc = new PowerOfTwoCalc(source.doubleValue());
	}
	public Number getResult() {
		return _calc.getValue();
	}
	
}
