package test.jstl;

public class Product {
	private String _id;
	private String _name;
	private int _value;

	public Product () {
	}
	public Product (String id, String name, int value) {
		_id = id;
		_name = name;
		_value = value;
	}
	public void setId(String id) {
		_id = id;
	}
	public void setName(String name) {
		_name = name;
	}
	public void setValue(int value) {
		_value = value;
	}
	public String getId() {
		return _id;
	}
	public String getName() {
		return _name;
	}
	public int getValue() {
		return _value;
	}
}
