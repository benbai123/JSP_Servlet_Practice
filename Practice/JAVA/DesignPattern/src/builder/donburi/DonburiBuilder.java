package builder.donburi;

import java.util.ArrayList;
import java.util.List;

/** DonburiBuilder, collect base (basically, a kind of rice) and ingredients,
 * and build Donburi with them.
 * 
 * @author benbai123
 *
 */
public class DonburiBuilder {
	String _base;
	List<String> _ingredientList = new ArrayList<String>();
	public DonburiBuilder base (String base) {
		_base = base;
		return this;
	}
	public DonburiBuilder ingredient (String ingredient) {
		_ingredientList.add(ingredient);
		return this;
	}
	public Donburi build () {
		return new Donburi(_base, buildIngredients());
	}

	private String buildIngredients () {
		String ingredients = "";
		if (_ingredientList.size() == 0) {
			ingredients = "nothing";
		} else {
			for (int i = 0; i < _ingredientList.size(); i++) {
				if (i > 0) {
					if (i < (_ingredientList.size()-1)) {
						ingredients += ", ";
					} else {
						ingredients += " and ";
					}
				}
				ingredients += _ingredientList.get(i);
			}
		}
		return ingredients;
	}
}
