package com.ego.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;

import com.ego.util.DBConnect;

public class DBConnection {
	
	public static DBConnect connection;
	
	public static Connection dbc() throws NamingException, SQLException,ServletException {
		try {
			connection = new DBConnect("postgres", null);

			return connection.connect();
		}

		catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
