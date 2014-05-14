package factory.simplefactory.food;

public abstract class Bread {
	private String _desc = "Bread";

	public Bread (String desc) {
		_desc = desc;
	}
	public String getDesc () {
		return _desc;
	}
}
