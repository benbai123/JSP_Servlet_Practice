package decorator.siren;

import java.awt.Toolkit;

/** Beep decorator, also implements Siren interface,
 * wrap another siren and make a sound after the wrapped siren did alarm
 * 
 * @author benbai123
 *
 */
public class BeepDecorator implements Siren {
	private Siren _wrapped;
	public BeepDecorator (Siren wrapped) {
		_wrapped = wrapped;
	}
	public void alarm () {
		_wrapped.alarm();
		Toolkit.getDefaultToolkit().beep();
	}
}
