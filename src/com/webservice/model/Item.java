package com.webservice.model;

public class Item {

	String item_name = new String();

	int item_cost;
	int iid;

	public Item(int iid, String item_name, int item_cost) {
		this.iid = iid;
		this.item_name = item_name;
		
		this.item_cost = item_cost;
	}

	public int getIid() {
		return iid;
	}
	
	public String getItemName() {
		return item_name;
	}


	public int getItemCost() {
		return item_cost;
	}

	public void setItemName(String item_name) {
		this.item_name = item_name;
	}


	public void setItemCost(int item_cost) {
		this.item_cost = item_cost;
	}

	public void setIid(int iid) {
		this.iid = iid;
	}


}
