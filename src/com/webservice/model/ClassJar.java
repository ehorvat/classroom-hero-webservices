package com.webservice.model;

import java.io.Serializable;

public class ClassJar implements Serializable{
	
	String jarName;
	int jarTotal;
	int jarProgress;
	int jid;
	
	//ctor
	public ClassJar(){
		
	}
	
	public ClassJar(String jarName, int jarTotal, int jarProgress, int jid){
		this.jarName = jarName;
		this.jarTotal = jarTotal;
		this.jarProgress = jarProgress;
		this.jid = jid;
	}
	
	//Getters

	public int getJid() {
		return jid;
	}

	public void setJid(int jid) {
		this.jid = jid;
	}

	public String getJarName() {
		return jarName;
	}

	public int getJarTotal() {
		return jarTotal;
	}

	public int getJarProgress() {
		return jarProgress;
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
	
	

}
