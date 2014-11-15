package com.webservice.model;

public class TimeStamp {
	int sid;
	String tstamp;

	public TimeStamp() {

	}

	public TimeStamp(int sid, String tstamp) {
		this.sid = sid;
		this.tstamp = tstamp;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setTstamp(String tstamp) {
		this.tstamp = tstamp;
	}

	public String getTstamp() {
		return tstamp;
	}

	public int getSid() {
		return sid;
	}

}
