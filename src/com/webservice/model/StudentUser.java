package com.webservice.model;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

public class StudentUser extends User {
	
	int totalCoins;
	int currentCoins;

	int lvl;
	int progress;
	int lvlUpAmount;
	int cid;
	int isActivated;
	String serial;
	String lastUpdate;
	
	
	
	//////////////////
	//				//
	// ctor			//
	//				//
	//////////////////
	public StudentUser(){
		
	}
	
	
	public StudentUser(String fname, String lname, String pass, String confPass, int userType) throws NoSuchAlgorithmException {
		super(fname, lname, pass, confPass, userType);
		
		
	}
	
	public StudentUser(String fname, String lname, int totalCoins, int cid, int currentCoins){
		super(fname, lname);
		this.cid = cid;
		this.totalCoins = totalCoins;
	}

	
	//////////////////////////
	//						//
	// Getters				//
	//						//
	//////////////////////////
	public int getTotalCoins() {
		return totalCoins;
	}
	public int getCurrentCoins() {
		return currentCoins;
	}

	public int getLvl() {
		return lvl;
	}
	public int getProgress() {
		return progress;
	}
	public int getLvlUpAmount() {
		return lvlUpAmount;
	}
	
	public int getCid() {
		return cid;
	}


	public int getIsActivated() {
		return isActivated;
	}


	public String getSerial() {
		return serial;
	}


	public String getLastUpdate() {
		return lastUpdate;
	}
	
	//////////////////////////
	//						//
	// Setters				//
	//						//
	//////////////////////////
	public void setTotalCoins(int totalCoins) {
		this.totalCoins = totalCoins;
	}
	public void setCurrentCoins(int currentCoins) {
		this.currentCoins = currentCoins;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	public void setProgress(int progress) {
		this.progress = progress;
	}
	public void setLvlUpAmount(int lvlUpAmount) {
		this.lvlUpAmount = lvlUpAmount;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}


	public void setIsActivated(int isActivated) {
		this.isActivated = isActivated;
	}


	public void setSerial(String serial) {
		this.serial = serial;
	}


	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	
	
}
