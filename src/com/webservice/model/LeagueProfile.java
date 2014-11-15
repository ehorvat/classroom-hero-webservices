package com.webservice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.NamingException;
import javax.servlet.ServletException;





import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ego.util.DBConnection;
import com.ego.util.Queries;

public class LeagueProfile extends Profile implements AdministrativeInterface,LeagueInterface {

	private League league;

	private Connection conn;

	private PreparedStatement preparedStatement = null;

	
	public LeagueProfile() throws NamingException, SQLException, ServletException {
		conn = DBConnection.dbc();
	}
	
	public LeagueProfile(League league) throws NamingException, SQLException,
			ServletException {
		this.league = league;
		conn = DBConnection.dbc();
	}

	@Override
	public JSONArray getTeacherStudentPairs()
			throws SQLException, JSONException {

		ArrayList<StudentUser> students = null;	
		
		JSONObject classes = new JSONObject();
		
		JSONArray parentArr = new JSONArray();
		
		//Instantiate a JSON array for each grade level. These will be the children of our parent JSON array that will go to the android
		JSONArray fourthData = new JSONArray();
		
		JSONArray fifthData = new JSONArray();
		
		JSONArray sixthData = new JSONArray();
		
		//Instantiate JSON arrays that will hold an array of teachers
		JSONObject fourthTeacher = null;
		
		JSONObject fifthTeacher = null;
		
		JSONObject sixthTeacher = null;
		
		//Instantiate an empty JSON array that will be the container for an array of students for all teachers
		JSONArray studentsArr = null;

		// Variable to hold teacher's class data
		int classData[] = new int[2];

		int cid;

		int grade;

		preparedStatement = conn.prepareStatement(Queries.GET_LEAGUE_TEACHERS);
		preparedStatement.setInt(1, league.getLeagueId());
		ResultSet rs = preparedStatement.executeQuery();

		// Read all teachers and corresponding students into arraylist
		while (rs.next()) {
			
			
			studentsArr = new JSONArray();
			
			// Instantiate a new teacher object per database row
			TeacherUser teacher = new TeacherUser(rs.getInt(1),
					rs.getString(2), rs.getString(3), rs.getString(5),
					rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getInt(9),
					rs.getBoolean(10));
			

			// For each teacher, retrieve the classID
			classData = getClass(teacher.getUid());

			
			cid = classData[0];
			
			grade = classData[1];
			
			
			teacher.setGrade(grade);
			
			// Get students in current teacher's class
			students = getStudent(cid);
			
			//Store students into JSONArray
			studentsArr.put(students);
			
			String fullname = teacher.getFname().trim() + " " + teacher.getLname().trim();
			
			//Perform switch case to figure out which grade to populate with teachers and students
			switch(grade){
	
			case 4:
				//Put under 4th grade section
				
				//Create JSONObject (teacher) containing array of json objects(the students)
				fourthTeacher = new JSONObject();
				fourthTeacher.put(fullname, studentsArr);
				
				//Add new teacher to fourth grade section
				fourthData.put(fourthTeacher);
	
						
				break;
			case 5:
				//Put under 5th grade section
				
				fifthTeacher = new JSONObject();
				fifthTeacher.put(fullname, studentsArr);
				
				fifthData.put(fifthTeacher);
	
			
				
				break;
			case 6:
				//Put under 6th grade section
				
				sixthTeacher = new JSONObject();
				sixthTeacher.put(fullname, studentsArr);
				
				sixthData.put(sixthTeacher);
		
				
				
				break;
			}

		}
		
		//Add new teacher/student pairs to parent object(classes obj)
		classes.put("fourth",fourthData);
		classes.put("fifth",fifthData);
		classes.put("sixth",sixthData);
		
		parentArr.put(classes);
		
		return parentArr;
	
	}

	@Override
	public ArrayList<StudentUser> getStudent(int cid) throws SQLException {
		
		// Arraylist to hold student users
		ArrayList<StudentUser> students = new ArrayList<StudentUser>();

		// Get students by class ID
		PreparedStatement getStudents = conn.prepareStatement(Queries.GET_STUDENTS);
		getStudents.setInt(1, cid);
		ResultSet results = getStudents.executeQuery();

		while (results.next()) {
			// For each DB row create a new student and store in students
			// arraylist
			StudentUser student = new StudentUser(results.getString(3),results.getString(4), results.getInt(6), results.getInt(1), results.getInt(5));
			students.add(student);
		}

		return students;
	}

	@Override
	public JSONArray getStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getItems() throws SQLException, JSONException {

		JSONArray itemArray = new JSONArray();
		
		JSONObject item = null;

		preparedStatement = conn.prepareStatement(Queries.GET_ADMIN_ITEMS);
		preparedStatement.setInt(1, league.getUid());
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			
			item = new JSONObject();
			item.put("cost", rs.getInt(1));
			item.put("name", rs.getString(2));
			
			itemArray.put(item);
		}
		preparedStatement.close();

		return itemArray;
	}

	@Override
	public JSONArray getCategories() throws SQLException, JSONException {

		JSONArray categories = new JSONArray();
		
		JSONObject category = null;

		preparedStatement = conn.prepareStatement(Queries.GET_ADMIN_CATEGORIES);
		preparedStatement.setInt(1, league.getUid());
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			category = new JSONObject();
			category.put("name", rs.getString(1).trim());
			categories.put(category);
		}
		preparedStatement.close();

		return categories;
	}

	@Override
	public JSONArray initProfile() throws SQLException, JSONException {
		
		//JSON object to hold items
		JSONObject itemObj = new JSONObject();
		
		//JSON object to hold categories
		JSONObject categoryObj = new JSONObject();
		
		//JSON array to hold both items and categories
		JSONArray entities = new JSONArray();

		JSONArray items = getItems();

		JSONArray categories = getCategories();
		
		//JSON object to hold school jar
		JSONObject jarObj = getSchoolJar();

		itemObj.put("items", items);
		
		categoryObj.put("categories", categories);
		
		entities.put(itemObj);
		entities.put(categoryObj);
		entities.put(jarObj);

		return entities;
	}

	private int[] getClass(int uid) throws SQLException {
		
		int info[] = new int[2];

		// Get class id using user id
		PreparedStatement getCid = conn.prepareStatement(Queries.GET_CLASS);
		getCid.setInt(1, uid);

		ResultSet result = getCid.executeQuery();

		if (result.next()) {
			System.out.println("ResultSet for class: " + result.getInt(1) + " " + result.getInt(2));
			// Teacher has a class. Get class ID from result set
			info[0] = result.getInt(1);
			info[1] = result.getInt(2);

		}
		
		return info;
	}

	@Override
	public JSONObject getSchoolJar() throws SQLException, JSONException {

		JSONObject jar = new JSONObject();

		preparedStatement = conn.prepareStatement(Queries.GET_ADMIN_JAR);
		preparedStatement.setInt(1, league.getUid());
		ResultSet rs = preparedStatement.executeQuery();

		if (rs.next()) {
			jar.put("jid", rs.getInt(1));
			jar.put("total",rs.getInt(2));
			jar.put("progress",rs.getInt(3));
			jar.put("name",rs.getString(4));
			
		}

		preparedStatement.close();

		return jar;

	}
	
	public League getLeague(){
		return this.league;
	}
	
	public void buildArrays(TeacherUser teacher, StudentUser student) {	
		
		
		
	}

}
