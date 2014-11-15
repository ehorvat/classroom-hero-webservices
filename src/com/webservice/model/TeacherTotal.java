package com.webservice.model;

public class TeacherTotal {
	
	int uid;
	int count;
	String fname;
	
	public TeacherTotal(int uid, String fname, int count){
		this.uid = uid;
		this.fname = fname;
		this.count = count;
	}
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	
	

}
