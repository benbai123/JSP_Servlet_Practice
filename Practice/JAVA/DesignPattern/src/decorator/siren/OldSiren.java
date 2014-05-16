package decorator.siren;

/** OldSiren, output message to console to do alarm
 * 
 * @author benbai123
 *
 */
public class OldSiren implements Siren {
	public void alarm () {
		System.err.println("Something goes wrong!");
	}
}
