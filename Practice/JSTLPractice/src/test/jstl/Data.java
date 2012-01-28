package test.jstl;

import java.util.*;

public class Data {
	private List<Product> _productList;

	public Data() {
	}
	public Data (List<Product> productList) {
		_productList = productList;
	}
	public void setProductList (List<Product> productList) {
		_productList = productList;
	}
	public List<Product> getProductList() {
		return _productList;
	}
	public int getProductCount () {
		return _productList == null? 0 : _productList.size();
	}
}
