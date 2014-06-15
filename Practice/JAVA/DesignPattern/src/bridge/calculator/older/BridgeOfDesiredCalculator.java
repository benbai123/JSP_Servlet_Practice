package bridge.calculator.older;

/** Bridge class used to access all DesiredCalculator
 * 
 * @author benbai123
 *
 */
public class BridgeOfDesiredCalculator {
	private DesiredCalculator _calc;
	public BridgeOfDesiredCalculator (DesiredCalculator calc) {
		_calc = calc;
	}
	public void setSource (Number source) {
		_calc.setSource(source);
	}
	public Number getResult () {
		return _calc.getResult();
	}
}
