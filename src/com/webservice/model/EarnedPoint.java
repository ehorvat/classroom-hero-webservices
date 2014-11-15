package com.webservice.model;

public class EarnedPoint {

	int sid;
	int category;
	String tstamp;

	public EarnedPoint() {
	}

	public EarnedPoint(int sid, int category, String tstamp) {
		this.sid = sid;
		this.category = category;
		this.tstamp = tstamp;
	}

	public int getSid() {
		return sid;
	}

	public String getTstamp() {
		return tstamp;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setTstamp(String tstamp) {
		this.tstamp = tstamp;
	}

}
