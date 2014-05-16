package builder.donburi;

/** Donburi, consists of several ingredients and served over base (a kind of rice)
 * 
 * @author benbai123
 *
 */
public class Donburi {
	private String _base;
	private String _ingredients;
	
	public Donburi (String base, String ingredients) {
		_base = base;
		_ingredients = ingredients;
	}
	public void showDesc () {
		System.out.println ("This Donburi consisting of " + _ingredients
						+ " and served over " + _base);
	}
}
