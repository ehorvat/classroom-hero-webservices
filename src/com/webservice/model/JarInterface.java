package com.webservice.model;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

public interface JarInterface {
	void createJar(HttpSession session) throws SQLException;
	void deleteJar(HttpSession session) throws SQLException;
	void changeJar(HttpSession session) throws SQLException;
}
