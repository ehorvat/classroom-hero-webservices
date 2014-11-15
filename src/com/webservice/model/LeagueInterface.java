package com.webservice.model;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public interface LeagueInterface {


	
	///////////////////////////////////////////////////////////
	//
	// Admin (League) specific method to retrieve all teachers
	// in admin's league
	// 
	///////////////////////////////////////////////////////////

	JSONArray getTeacherStudentPairs() throws SQLException, JSONException;
	
	ArrayList<StudentUser> getStudent(int cid) throws SQLException;
	
	JSONObject getSchoolJar() throws SQLException, JSONException;

	////////////////////////////////////////////////////////
	
	
}
