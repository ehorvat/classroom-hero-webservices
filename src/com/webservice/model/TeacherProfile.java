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
import com.ego.util.PasswordMisMatch;
import com.ego.util.Queries;

/////////////////////////////////
//
// Persistence Layer
//
/////////////////////////////////

public class TeacherProfile extends Profile implements AdministrativeInterface,
		TeacherInterface {

	private TeacherUser teacher;

	private Connection conn;

	private PreparedStatement preparedStatement = null;

	public TeacherProfile() throws NamingException, SQLException,
			ServletException {
		conn = DBConnection.dbc();
	}

	public TeacherProfile(TeacherUser teacher) throws NamingException,
			SQLException, ServletException {
		this.teacher = teacher;
		conn = DBConnection.dbc();
	}

	public JSONArray getStudents() throws SQLException, JSONException {
		
		//JSONArray to hold all students
		JSONArray students = new JSONArray();

		//JSONObject to hold a single student
		JSONObject student = null;
		
		ResultSet rs;

		PreparedStatement preparedStatement = conn.prepareStatement(
				Queries.GET_STUDENTS, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		
		preparedStatement.setInt(1, teacher.getCid());
		rs = preparedStatement.executeQuery();

	

		while (rs.next()) {
			
			student = new JSONObject();
			student.put("cid", rs.getInt(1));
			student.put("uid", rs.getInt(2));
			student.put("name", rs.getString(3).trim() + " " + rs.getString(4).trim());
			student.put("currentCoins", rs.getInt(5));
			student.put("totalCoins", rs.getInt(6));
			student.put("lvl", rs.getInt(7));
			student.put("progress", rs.getInt(8));
			student.put("lvlUpAmount", rs.getInt(9));
			student.put("isActivated", rs.getInt(11));
			if (rs.getString(12) == null) {
				student.put("stamp", "");
			} else {
				student.put("stamp", rs.getString(rs.getString(12).trim()));
			}
			if (rs.getString(13) == null) {
				student.put("lastUpdate", "");
			} else {
				student.put("lastUpdate", rs.getString(13));
			}

			students.put(student);

		}

		preparedStatement.close();

		return students;

	}

	@Override
	public JSONArray getItems() throws SQLException, JSONException {

		JSONArray itemArray = new JSONArray();
		
		JSONObject item = null;

		preparedStatement = conn.prepareStatement(Queries.GET_ITEMS);
		preparedStatement.setInt(1, teacher.getUid());
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			
			item = new JSONObject();
			item.put("id", rs.getInt(1));
			item.put("cost", rs.getInt(2));
			item.put("name", rs.getString(3).trim());
			
			itemArray.put(item);
		}
		preparedStatement.close();

		return itemArray;
	}

	@Override
	public JSONArray getCategories() throws SQLException, JSONException {

		JSONArray categories = new JSONArray();
		
		JSONObject category = null;

		preparedStatement = conn.prepareStatement(Queries.GET_CATEGORIES);
		preparedStatement.setInt(1, teacher.getUid());
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			category = new JSONObject();
			category.put("id", rs.getInt(1));
			category.put("name", rs.getString(2).trim());
			categories.put(category);
		}
		preparedStatement.close();

		return categories;
	}


	@Override
	public JSONObject getClassJar() throws SQLException, JSONException {

		JSONObject jar = new JSONObject();

		preparedStatement = conn.prepareStatement(Queries.GET_CLASS_JAR);
		preparedStatement.setInt(1, teacher.getUid());
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

	@Override
	public JSONArray initProfile() throws SQLException, JSONException {

		//JSON object to hold items
		JSONObject itemObj = new JSONObject();
		
		//JSON object to hold categories
		JSONObject categoryObj = new JSONObject();
		
		//JSON object to hold teacher's students
		JSONObject studentsObj = new JSONObject();
		
		//JSON array to hold both items/categories/students
		JSONArray entities = new JSONArray();

		JSONArray items = getItems();

		JSONArray categories = getCategories();
		
		JSONArray students = getStudents();
		
		//JSON object to hold class jar
		JSONObject jarObj = getClassJar();

		itemObj.put("items", items);
		
		
		categoryObj.put("categories", categories);
		
		
		studentsObj.put("students", students);
		
	
		
		entities.put(itemObj);
		entities.put(categoryObj);
		entities.put(jarObj);
		entities.put(studentsObj);
		
		System.out.println("Before return " + entities.toString());

		return entities;
	}



}
