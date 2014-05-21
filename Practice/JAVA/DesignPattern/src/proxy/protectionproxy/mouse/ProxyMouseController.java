package proxy.protectionproxy.mouse;

/** Proxy Mouse Controller
 * 
 * Implements MouseController, check user role first then move
 * mouse cursor by RealMouseController
 * 
 * @author benbai123
 *
 */
public class ProxyMouseController implements MouseController {
	private String _role;
	private RealMouseController _rmc;
	public ProxyMouseController (String role) {
		if (role == null) {
			role = "";
		}
		_role = role;
	}

	public void moveTo(int x, int y) {
		if (canControl()) {
			if (_rmc == null) {
				_rmc = new RealMouseController();
			}
			_rmc.moveTo(x, y);
		} else {
			System.err.println("Only Admin can control mouse!");
		}
	}

	private boolean canControl () {
		return "Admin".equals(_role);
	}
}
