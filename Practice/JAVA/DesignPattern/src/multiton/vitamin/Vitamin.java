package multiton.vitamin;

/** Vitamin
 * 
 * @author benbai123
 *
 */
public class Vitamin {
	private String _name;
	public Vitamin (String name) {
		_name = name;
	}
	public String getName () {
		return _name;
	}
}
