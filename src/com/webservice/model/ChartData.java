package com.webservice.model;

public class ChartData {
	int category_code;
	int count;
	String Category;

	public ChartData() {

	}
	
	public ChartData(int category_code, int count, String Category){
		this.category_code = category_code;
		this.count = count;
		this.Category = Category;
	}

	public int getCategory_code() {
		return category_code;
	}

	public int getCount() {
		return count;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory_code(int category_code) {
		this.category_code = category_code;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setCategory(String category) {
		Category = category;
	}

}
