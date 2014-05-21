package proxy.protectionproxy.mouse;

import java.awt.AWTException;
import java.awt.Robot;

/** Real Mouse Controller
 * 
 * Implements MouseController, move mouse cursor directly.
 * 
 * @author benbai123
 *
 */
public class RealMouseController implements MouseController {
	private Robot _bot;

	public void moveTo (int x, int y) {
		try {
			if (_bot == null) {
				_bot = new Robot();
			}
			_bot.mouseMove(x, y);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
