package factory.abstractfactory.food;

public abstract class Food {
	private String _desc = "Food";

	public Food (String desc) {
		_desc = desc;
	}
	public String getDesc () {
		return _desc;
	}
}
