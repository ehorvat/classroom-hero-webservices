package com.webservice.model;

public class SoldItem {
	
	int sid;
	int iid;
	String tstamp;
	
	public SoldItem(){}
	
	public SoldItem(int sid, int iid, String tstamp){
		this.sid = sid;
		this.iid = iid;
		this.tstamp = tstamp;
	}
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getIid() {
		return iid;
	}
	public void setIid(int iid) {
		this.iid = iid;
	}
	public String getTstamp() {
		return tstamp;
	}
	public void setTstamp(String tstamp) {
		this.tstamp = tstamp;
	}
}
