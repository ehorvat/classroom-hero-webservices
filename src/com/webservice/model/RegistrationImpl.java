package com.webservice.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;

import com.ego.util.DBConnection;
import com.ego.util.InvalidStamp;
import com.ego.util.Queries;

public class RegistrationImpl {

	private String serial;

	private int uid;
	
	PreparedStatement p;
	
	Connection c;
	
	ResultSet rs;
	
	public RegistrationImpl(String serial, int uid) throws NamingException, SQLException, ServletException, InvalidStamp{
		this.serial = serial;
	
		this.uid = uid;
		
		c = DBConnection.dbc();
		
		register();
	}

	private void register() throws SQLException, InvalidStamp {
		
		if(validateStamp()){
			//Stamp doesn't already exist. Link stamp with user
			
			p = c.prepareStatement(Queries.USER_STAMP_LINK);
			p.setString(1, serial);
			p.setInt(2, uid);
			p.execute();
			
			
		}else{
			throw new InvalidStamp("Stamp already registered");
		}
		
		
	}

	private boolean validateStamp() throws SQLException {
		
		boolean success = false;
		
		p = c.prepareStatement(Queries.CHECK_STAMP);
		p.setString(1, serial);
		rs = p.executeQuery();
		
		if(rs.next()){
			//Stamp already registered
			success = false;
		}else{
			//good to register
			success = true;
		}
		
		return success;
	}
	
}
