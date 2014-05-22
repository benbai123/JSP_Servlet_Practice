package multiton.vitamin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** VitaminBox
 * 
 * Only one box for a kind of Vitamin.
 * 
 * @author benbai123
 *
 */
public class VitaminBox {
	private static Map<String, VitaminBox> _boxes = new HashMap<String, VitaminBox>();
	private List<Vitamin> _vits = new ArrayList<Vitamin>();
	
	public static void add (Vitamin vit) {
		getVitaminBox(vit.getName()) // get VitaminBox by Vitamin name
			.getVitamins(). // get Vitamin list
			add(vit); // add Vitamin
	}
	public Vitamin getVitamin () {
		if (_vits.size() > 0) {
			return _vits.get(0);
		}
		return null;
	}
	public int getVitaminAmount () {
		return _vits.size();
	}
	public static Set<Map.Entry<String, VitaminBox>> getEntries () {
		return _boxes.entrySet();
	}
	private List<Vitamin> getVitamins () {
		return _vits;
	}
	private static VitaminBox getVitaminBox (String name) {
		// try to get box
		VitaminBox box = _boxes.get(name);
		if (box == null) {
			synchronized (_boxes) {
				box = _boxes.get(name);
				// create new if not exists
				if (box == null) {
					box = new VitaminBox();
					_boxes.put(name, box);
				}
			}
		}
		return box;
	}
}
