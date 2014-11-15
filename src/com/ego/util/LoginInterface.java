package com.ego.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import com.webservice.model.User;



public interface LoginInterface {
	
	void auth() throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, PasswordMisMatch;
			
	void getTeacherData(int uid) throws SQLException, NoSuchAlgorithmException;
	
	void getAdminData(int uid) throws SQLException;
	
	User getUser();
	
	void authWithStamp(String serial) throws SQLException, PasswordMisMatch, NoSuchAlgorithmException;
	
	
	
	
}
