package bridge.calculator.newer;

/** Newer DesiredCalculator interface, assume you wish all calculators implement this interface.
 * 
 * The API has been changed
 * 
 * @author benbai123
 *
 */
public interface DesiredCalculator {
	public void setData (Number source);
	public Number getValue ();
}
