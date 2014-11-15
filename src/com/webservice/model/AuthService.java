package com.webservice.model;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.webservice.model.TeacherUser;


public class AuthService {
	int type;

	PreparedStatement preparedStatement = null;
	ResultSet rs;
	TeacherUser teacher;
	

	
	//////////////////////////
	//						//
	// Authenticate Teacher	//
	//						//
	//////////////////////////
	public boolean auth(String email, String password, int type, Connection conn) throws SQLException{
		boolean isValid = false;
		if(type == 1){
			
			String lookUp = "Select \"hash\", \"uid\", \"email\", \"tid\", \"isActivated\", \"serial\", \"cid\", \"fname\" from \"User\", \"Teacher_User\" NATURAL JOIN \"Class\" where \"User\".\"uid\" = \"tid\" AND \"email\"=?";
			try {
				preparedStatement = conn.prepareStatement(lookUp);
				preparedStatement.setString(1, email);
				rs = preparedStatement.executeQuery();
				
				if(rs.next()){
					teacher = new TeacherUser();
					teacher.setUid(rs.getInt(2));
					teacher.setIsActivated(rs.getInt(5));
					teacher.setSerial(rs.getString(6));
					teacher.setCid(rs.getInt(7));
					teacher.setFname(rs.getString(8));
					String hashedpw = rs.getString(1);
					System.out.println("Querying cid" + teacher.getCid());
					PasswordManager decoder = new PasswordManager();
					boolean valid = decoder.validatePassword(password, hashedpw);
					if(valid){
						isValid = true;
						return isValid;
					}else{
						System.out.println("Error occured in validation");
					}
				}else{
					System.out.println("Error when querying");
					isValid = false;
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			}
		}
		return isValid;
	}
	
	
	
	
	//////////////////////////
	//						//
	// Authenticate Stamp	//
	//						//
	//////////////////////////
	public boolean authWithStamp(String serial, Connection conn) throws SQLException {
		System.out.println("inside auth with stamp");
		boolean isValid = false;
			
			String lookUp = "Select \"hash\", \"uid\", \"email\", \"tid\", \"isActivated\", \"serial\", \"cid\", \"fname\" from \"User\", \"Teacher_User\" NATURAL JOIN \"Class\" where \"User\".\"uid\" = \"tid\" AND \"serial\"=?";
			try {
				preparedStatement = conn.prepareStatement(lookUp);
				preparedStatement.setString(1, serial);
				ResultSet rs = preparedStatement.executeQuery();
				
				if(rs.next()){
					System.out.println("found row");
					isValid = true;
					teacher = new TeacherUser();
					teacher.setUid(rs.getInt(2));
					teacher.setIsActivated(rs.getInt(5));
					teacher.setSerial(rs.getString(6));
					teacher.setCid(rs.getInt(7));
					teacher.setFname(rs.getString(8));
				}else{
					System.out.println("false");
					isValid = false;
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				if (preparedStatement != null) {
					preparedStatement.close();
				}
			}
		return isValid;
	}
	
	
	
	
	
	
	
	
	//////////////////////////
	//						//
	// Init class memebers	//
	//						//
	//////////////////////////
	/*public TeacherUser initUserClass() throws SQLException{
		
		String findDetails = "Select \"uid\", \"fname\", \"lname\", \"role\", \"tid\", \"email\" from \"User\", \"Teacher_User\" where \"uid\"=? AND \"uid\" = \"tid\"";
		preparedStatement = conn.prepareStatement(findDetails);
		preparedStatement.setInt(1, teacher.getUid());
		rs = preparedStatement.executeQuery();
		
		if(rs.next()){
			teacher.setFname(rs.getString(2));
			teacher.setLname(rs.getString(3));
			teacher.setUserType(rs.getInt(4));
			teacher.setEmail(rs.getString(5));
			teacher.setIsLoggedIn(true);
			System.out.println("Initialized " + teacher.getFname() + "'s user details");
		
		}
		return teacher;
	}*/
	
	//////////////////////////
	//						//
	// Init session vars	//
	//						//
	//////////////////////////
	public void initSessionVars(TeacherUser teacher, HttpSession session) {
		session.setAttribute("uid", teacher.getUid());
		session.setAttribute("fname", teacher.getFname());
		session.setAttribute("lname", teacher.getLname());
		session.setAttribute("role", teacher.getUserType());
		session.setAttribute("email", teacher.getEmail());
		session.setAttribute("isLoggedIn", teacher.getIsLoggedIn());
	}
	
	
	//////////////////////////////
	//			 				//
	//Remeber me implementation //
	//(work in progress)		//
	//							//
	//////////////////////////////
	/* public String setCookies(HttpServletResponse response){
		// Generate this key And save In database And Set as cookies
		UUID generateId = UUID.randomUUID();
		String userToken = generateId.toString();
		Cookie cookie = new Cookie ("userToken", userToken);
		//Set the required cookies age
		cookie.setMaxAge(365 * 24 * 60 * 60);
		//Then add the cookies
		response.addCookie(cookie);
		return userToken;

	}
	public void updateRememberMeId(String userToken, int uid) throws SQLException{
		String teacherSql = "UPDATE \"User\" set \"rememberMeId\"=? where \"uid\"=?";
		preparedStatement = conn.prepareStatement(teacherSql);
		preparedStatement.setString(1, userToken);
		preparedStatement.setInt(2, uid);
		preparedStatement.execute();
		if (conn != null) {
			connection.disconnect();
		}
	}
	*/
	public TeacherUser getTeacher(){
		return this.teacher;
	}


}
