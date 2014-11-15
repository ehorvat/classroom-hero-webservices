package com.webservice.model;

import javax.xml.bind.annotation.XmlRootElement;

public class Category {

	int id;
	String name;

	public Category() {
	
	}
	
	public Category(int id, String name) {
		this.name = name;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
