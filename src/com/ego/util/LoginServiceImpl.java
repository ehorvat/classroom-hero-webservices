package com.ego.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;

import com.webservice.model.League;
import com.webservice.model.PasswordManager;
import com.webservice.model.TeacherUser;
import com.webservice.model.User;


public class LoginServiceImpl implements LoginInterface{
	
	private int type; 
	
	private User user;
	
	private String username;
	
	private String password;
	
	private String serial;
	
	private Connection conn;
	
	private PreparedStatement preparedStatement = null;
	
	private ResultSet rs;
	
	private static final int TEACHER_ROLE = 1;
	
	private static final int STUDENT_ROLE = 2;
	
	private static final int ADMIN_ROLE = 3;
	
	/////////////////
	//
	// ctor
	//
	/////////////////
	
	public LoginServiceImpl(String username, String password) throws NamingException, SQLException, ServletException, NoSuchAlgorithmException, InvalidKeySpecException, PasswordMisMatch{
		this.username = username;
		this.password = password;
		conn = DBConnection.dbc();
		auth();
	}
	
	///////////////////////////////////
	//
	// ctor for serial authentication
	//
	//////////////////////////////////
	
	public LoginServiceImpl(String serial) throws NamingException, SQLException, ServletException, NoSuchAlgorithmException, InvalidKeySpecException, PasswordMisMatch{
		conn = DBConnection.dbc();
		authWithStamp(serial);
	}

	public void auth() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, PasswordMisMatch {
		preparedStatement = conn.prepareStatement(Queries.VERIFY_EMAIL);
		preparedStatement.setString(1, username);
		rs = preparedStatement.executeQuery();
		if(rs.next()){
			//User found. Verify password.		
			preparedStatement = conn.prepareStatement(Queries.VERIFY_PASSWORD);
			preparedStatement.setInt(1, rs.getInt(2));
			rs = preparedStatement.executeQuery();
				
			if(rs.next()){
				
				String hashedPass = rs.getString(2);
				PasswordManager decoder = new PasswordManager();
				boolean success = decoder.validatePassword(password, hashedPass);
				if(!success){
					throw new PasswordMisMatch("Invalid Credentials");
				}else{
					int role = rs.getInt(3);
					
					if(role == 1){
						
						//Teacher is logging in. Retrieve teacher-specific information
						int uid = rs.getInt(1);
						getTeacherData(uid);
						
					}else if(role ==3){
						//Admin  is logging in. Retrieve admin-specific information
						int uid = rs.getInt(1);
						getAdminData(uid);
					}
					
				}
				
			}	
		}else{
			throw new PasswordMisMatch("Invalid Credentials");
		}
	}
	
	// ////////////////////////
	// //
	// Authenticate Stamp //
	// //
	// ////////////////////////
	public void authWithStamp(String serial) throws SQLException, PasswordMisMatch, NoSuchAlgorithmException {
		
		
		preparedStatement = conn.prepareStatement(Queries.AUTH_WITH_STAMP);
		preparedStatement.setString(1, serial);
		ResultSet rs = preparedStatement.executeQuery();

		if (rs.next()) {
			
			//Determine if admin or teacher by looking at role field
			int role = rs.getInt(6);
			
			if(role == 1){
				//instantiate a new teacher
				getTeacherData(rs.getInt(1));
			}else if(role == 3){
				//instantiate a new admin
				getAdminData(rs.getInt(1));
			}
			
			/*teacher = new TeacherUser();
			teacher.setUid(rs.getInt(2));
			teacher.setIsActivated(rs.getInt(5));
			teacher.setSerial(rs.getString(6));
			teacher.setCid(rs.getInt(7));
			teacher.setFname(rs.getString(8));*/
		} else {
			throw new PasswordMisMatch("Invalid Credentials");
		}
		preparedStatement.close();
		
	}



	@Override
	public void getTeacherData(int uid) throws SQLException, NoSuchAlgorithmException {
		int cid = getClassId(uid);
		
		preparedStatement = conn.prepareStatement(Queries.GET_TEACHER_DATA);
		preparedStatement.setInt(1, uid);
		rs = preparedStatement.executeQuery();
		if(rs.next()){
			System.out.println("stamp: " + rs.getString(6));
			//Instantiate user of type teacher
			user = new TeacherUser(uid, rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getBoolean(9), cid, rs.getInt(10));
			
			//Retrieve this teacher's class data
			preparedStatement = conn.prepareStatement(Queries.GET_CLASS_DATA);
			preparedStatement.setInt(1, uid);
			rs = preparedStatement.executeQuery();
			if(rs.next()){
				((TeacherUser) user).setClassLvl(rs.getInt(1));
				((TeacherUser) user).setClassProgress(rs.getInt(2));
				((TeacherUser) user).setClassNextLvl(rs.getInt(3));
			}
			
			
		}
	}

	@Override
	public void getAdminData(int uid) throws SQLException {
		preparedStatement = conn.prepareStatement(Queries.GET_ADMIN_DATA);
		preparedStatement.setInt(1, uid);
		rs = preparedStatement.executeQuery();
		if(rs.next()){
			
			//Instantiate user of type admin (league)
			user = new League(uid, rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getInt(7), rs.getBoolean(8));
			
		}
	}
	
	private int getClassId(int uid) throws SQLException {
		
		int cid = 0;
		System.out.println("in get class id" + uid);
		//Get class id using user id
		preparedStatement = conn.prepareStatement(Queries.GET_CLASS);
		preparedStatement.setInt(1, uid);
		rs = preparedStatement.executeQuery();
		if(rs.next()){
			//Teacher has a class. Get class ID from result set
			
			cid = rs.getInt(1);
			System.out.println("class id: " + cid);
		}
		
		return cid;
	}

	@Override
	public User getUser() {
		System.out.println("getting user");
		return this.user;
	}
	
	public int getType(){
		return this.type;
	}
	
	public void setType(int type){
		this.type = type;
	}	
}
