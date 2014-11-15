package com.webservice.model;

public class League extends User {
	int leagueId;

	String leagueCode;

	String leagueName;
	
	String email;

	boolean hasStamps;
	
	private boolean privacy;

	//////////////////
	//
	// ctor 
	// 
	//////////////////

	public League() {

	}
	
	public League(int uid) {
		super(uid);
	}

	
	public League(String leagueName, boolean privacy) {
		
		this.leagueName = leagueName;
		this.privacy = privacy;
	}
	
	public League(String leagueName, int leagueId, boolean hasStamps) {
		
		this.leagueId = leagueId;
		this.leagueName = leagueName;
		this.hasStamps = hasStamps;
	}

	public League(String leagueCode, String leagueName, boolean hasStamps, String email, String fname, String lname, String pass, String confPass, int userType) {
		
		super(fname, lname, pass, confPass, userType);
		
		this.leagueCode = leagueCode;
		this.leagueName = leagueName;
		this.hasStamps = hasStamps;
		this.email = email;
	}
	
	public League(int uid, String fname, String lname, String email, int leagueId, boolean hasStamps, String leagueCode){
		super(uid, fname, lname, email);
		
		this.leagueId = leagueId;
		this.hasStamps = hasStamps;
		this.leagueCode = leagueCode;
	}
	
	public League(int uid, String fname, String lname, String email, int isActivated, String stamp, int leagueId, boolean hasStamps){
		super(uid, fname, lname, email);
		
		this.leagueId = leagueId;
		this.hasStamps = hasStamps;
		this.leagueCode = leagueCode;
	}
	
	//////////////////
	//
	// Getters
	// 
	//////////////////
	
	public int getLeagueId(){
		return leagueId;
	}

	public String getLeagueCode() {
		return leagueCode;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public boolean getHasStamps() {
		return hasStamps;
	}
	
	public String getEmail(){
		return email;
	}
	
	
	//////////////////
	//
	// Setters
	// 
	//////////////////
	
	public void setLeagueId(int leagueId){
		this.leagueId = leagueId;
	}

	public void setLeagueCode(String guildCode) {
		this.leagueCode = guildCode;
	}

	public void setLeagueName(String guildName) {
		this.leagueName = guildName;
	}

	public void setHasStamps(boolean hasStamps) {
		this.hasStamps = hasStamps;
	}
	
	public void setEmail(String email){
		this.email = email;
	}

}
