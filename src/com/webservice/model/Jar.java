package com.webservice.model;

import java.io.Serializable;

public class Jar implements Serializable{
	
	String jarName;
	int jarTotal;
	int jarProgress;
	int jid;

	//ctor
	public Jar(){
		
	}
	
	public Jar(String jarName, int jarCost, int jarProgress, int jid){
		this.jarName = jarName;
		this.jarTotal = jarCost;
		this.jarProgress = jarProgress;
		this.jid = jid;
	}
	
	//Getters

	public String getJarName() {
		return jarName;
	}

	public int getJarTotal() {
		return jarTotal;
	}

	public int getJarProgress() {
		return jarProgress;
	}
	
	public int getJid() {
		return jid;
	}


	//Setters
	
	public void setJarName(String jarName) {
		this.jarName = jarName;
	}

	public void setJarTotal(int jarCost) {
		this.jarTotal = jarCost;
	}

	public void setJarProgress(int jarProgress) {
		this.jarProgress = jarProgress;
	}
	
	public void setJid(int jid) {
		this.jid = jid;
	}

}
