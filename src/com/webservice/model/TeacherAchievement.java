package com.webservice.model;

public class TeacherAchievement {
	
	int aid, completed;
	
	public TeacherAchievement(int aid, int completed){
		this.aid = aid;
		this.completed = completed;
	}

	//Getters
	public int getAid() {
		return aid;
	}

	
	public int getCompleted() {
		return completed;
	}

	public void setCompleted(int completed) {
		this.completed = completed;
	}

	//Setters
	public void setAid(int aid) {
		this.aid = aid;
	}


	
	
	

}
