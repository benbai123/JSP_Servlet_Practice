package composite.calculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** BetterDesiredCalculator, implements DesiredCalculator,
 * consists of other BetterDesiredCalculator or basic DesiredCalculator
 * get inner DesiredCalculator to calculate rather than calculate result itself.
 * 
 * @author benbai123
 *
 */
public class BetterDesiredCalculator implements DesiredCalculator {
	private Map<String, DesiredCalculator> _calcs = new HashMap<String, DesiredCalculator>();

	private Number _source;
	private List<String> _mode;
	public void setSource(Number source) {
		_source = source;
	}

	public Number getResult() {
		DesiredCalculator calc = null;
		for (String s : _mode) {
			if (calc == null) {
				calc = _calcs.get(s);
			} else {
				calc = ((BetterDesiredCalculator)calc).getCalculator(s);
			}
		}
		
		calc.setSource(_source);
		return calc.getResult();
	}

	public void addCalculator (String mode, DesiredCalculator calc) {
		_calcs.put(mode, calc);
	}
	public DesiredCalculator getCalculator (String s) {
		return _calcs.get(s);
	}
	public void setMode (List<String> mode) {
		_mode = mode;
	}
}
