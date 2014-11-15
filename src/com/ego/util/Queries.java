package com.ego.util;


public class Queries {
	
	/////////////////////////////////
	//
	// Registration SQL Queries
	//
	/////////////////////////////////
	
	public static final String GENERAL_INFO_NO_PASS = "INSERT INTO \"User\"(\"fname\", \"lname\", \"role\", \"registerTime\") VALUES(?,?,?,?) RETURNING \"uid\"";
	
	public static final String GENERAL_INFO = "INSERT INTO \"User\"(\"fname\", \"lname\", \"role\", \"registerTime\", \"salt\", \"hash\") VALUES(?,?,?,?,?,?) RETURNING \"uid\"";
	
	public static final String REGISTER_TEACHER = "INSERT INTO \"Teacher_User\"(\"tid\" , \"email\") VALUES(?,?)";
	
	public static final String REGISTER_STUDENT = "INSERT INTO \"Student_User\"(\"sid\") VALUES(?)";
	
	public static final String REGISTER_LEAGUE = "INSERT INTO \"League\"(\"leagueName\", \"email\", \"hasStamps\", \"leagueAdmin\", \"leagueCode\") VALUES(?,?,?,?,?)";
	
	public static final String CREATE_CLASS = "INSERT INTO \"Class\"(\"grade\" , \"tid\", \"schoolName\") VALUES(?,?,?)";
	
	public static final String STUDENT_CLASS_MATCH = "INSERT INTO \"Student_Class_Match\" (\"cid\", \"sid\") VALUES(?,?)";
	
	public static final String PRIVATE_LEAGUE_LOOKUP = "SELECT \"leagueName\", \"leagueId\", \"hasStamps\" FROM \"League\" WHERE \"leagueCode\"=?";
	
	public static final String PUBLIC_LEAGUE_LOOKUP = "SELECT \"leagueName\", \"leagueId\", \"hasStamps\" FROM \"League\" WHERE \"leagueName\"=?";
	
	public static final String TEACHER_LEAUGE_MATCH = "UPDATE \"Teacher_User\" SET \"leagueId\"=? WHERE \"tid\"=?";
	
	public static final String LEAVE_LEAGUE = "Update \"Teacher_User\" set \"leagueId\"=NULL where \"tid\"=?";
	
	public static final String DELETE_USER_DATA = "DELETE FROM \"User\" WHERE \"uid\"=?";
	
	public static final String DELETE_TEACHER_DATA = "DELETE FROM \"Teacher_User\" WHERE \"tid\"=?";
	
	public static final String GET_LEAGUE_STATUS = "SELECT \"leagueId\" FROM \"Teacher_User\" WHERE \"tid\"=?";
	
	public static final String CHECK_STAMP = "Select \"stamp\" from \"User\" where \"stamp\"=?";
	
	public static final String USER_STAMP_LINK = "Update \"User\" set \"stamp\"=?, \"isActivated\"=1 where \"uid\"=?";
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
/////////////////////////////////
//
// Login SQL Queries
//
/////////////////////////////////

public static final String VERIFY_EMAIL = "Select \"email\", \"uid\" from \"User\" where \"email\"=?";

public static final String VERIFY_EMAIL_ADMIN = "Select \"email\", \"leagueAdmin\" from \"League\" where \"email\"=?";

public static final String VERIFY_PASSWORD = "Select \"uid\", \"hash\", \"role\" from \"User\" where \"uid\"=?";

public static final String GET_TEACHER_DATA = "Select \"uid\", \"fname\", \"lname\", \"email\", \"isActivated\", \"stamp\", \"coins\", \"leagueId\", \"hasStamps\", \"role\" from \"User\", \"Teacher_User\" where \"uid\"=? AND \"uid\" = \"tid\"";

public static final String GET_ADMIN_DATA = "Select \"uid\", \"fname\", \"lname\", \"email\", \"isActivated\", \"stamp\", \"leagueId\", \"hasStamps\", \"leagueAdmin\", \"leagueCode\", \"role\" from \"User\", \"League\" where \"uid\"=? AND \"uid\" = \"leagueAdmin\"";
	
public static final String GET_LEAGUE = "Select \"leagueId\" from \"league\" where \"leagueAdmin\"=?";	
	///////////////////////////////////////
	//
	// Teacher and Admin profile queries
	//
	///////////////////////////////////////
	
	public static final String GET_STUDENTS = "Select DISTINCT cid, uid, \"fname\", \"lname\", \"currentCoins\", \"totalCoins\", \"lvl\", \"progress\", \"lvlUpAmount\", \"Student_User\".sid, \"isActivated\", stamp, last_update"
			+ " from \"User\""
			+ " INNER JOIN \"Student_User\" ON sid = \"User\".uid "
			+ " NATURAL JOIN \"Student_Class_Match\" where \"cid\"=?";
	
	public static final String GET_ITEMS = "Select \"iid\",\"cost\", \"name\" From \"Item\" where uid=?";
	
	public static final String GET_CATEGORIES = "Select \"code\",\"Category\" From \"CategoryCodes\" where uid=?";
	
	public static final String GET_CLASS_JAR = "Select \"jid\", \"total\", \"progress\", \"name\" From \"Class_Jar\" where uid=?";
	
	public static final String GET_CLASS = "Select \"cid\", \"grade\" From \"Class\" where \"tid\"=?";
	
	public static final String AUTH_WITH_STAMP = "Select \"uid\", \"email\", \"isActivated\", \"stamp\", \"fname\", \"role\" from \"User\" where \"serial\"=?";
	
	
	///////////////////////////////////////
	//
	// Admin-specific queries
	//
	///////////////////////////////////////
	
	public static final String GET_LEAGUE_TEACHERS = "Select \"uid\", \"fname\", \"lname\", \"tid\", \"email\", \"isActivated\", \"stamp\", \"coins\", \"leagueId\", \"hasStamps\" from \"User\", \"Teacher_User\" where \"leagueId\"=? AND \"tid\"=\"uid\"";
	
	public static final String GET_ADMIN_ITEMS = "Select \"cost\", \"name\" From \"Item\" where \"storeType\"=3 AND uid=?";
	
	public static final String GET_ADMIN_CATEGORIES = "Select \"Category\" From \"CategoryCodes\" where \"categoryType\"=3 AND uid=?";
	
	public static final String GET_ADMIN_JAR = "Select \"jid\",\"total\", \"progress\", \"name\" From \"Class_Jar\" where \"type\"=3 AND uid =?";
	
	public static final String GET_PRIVACY_SETTING = "Select \"private\", \"leagueCode\" from \"League\" where \"leagueAdmin\"=?";
	
	public static final String CHANGE_PRIVACY_SETTINGS = "Update \"League\" set \"private\"=? where \"leagueAdmin\"=?";
	
	
	///////////////////////////////////////
	//
	// Teacher-specific queries
	//
	///////////////////////////////////////

	public static final String GET_CLASS_DATA = "Select \"classLvl\", \"classProgress\", \"nextLvl\" from \"Class\" where \"tid\"=?";
	
	
	
	///////////////////////////////////////
	//
	// CRUD operations for items
	//
	///////////////////////////////////////
	
	public static final String INSERT_ITEM = "INSERT INTO \"Item\" (\"cost\", \"name\", \"storeType\", uid) VALUES (?,?,?,?)";
	
	public static final String DELETE_ITEM = "DELETE FROM \"Item\" where uid=? AND name=?";
	
	public static final String UPDATE_ITEM = "UPDATE \"Item\" set name=?, cost=? where uid=? AND name=?";
	
	
	///////////////////////////////////////
	//
	// CRUD operations for categories
	//
	///////////////////////////////////////

	public static final String INSERT_CATEGORY = "Insert into \"CategoryCodes\" (\"Category\", \"categoryType\", uid) VALUES(?,?,?)";

	public static final String DELETE_CATEGORY = "DELETE FROM \"CategoryCodes\" where uid=? AND \"Category\"=?";

	public static final String UPDATE_CATEGORY = "UPDATE \"CategoryCodes\" set \"Category\"=? where uid=? AND \"Category\"=?";
	
	///////////////////////////////////////
	//
	// CRUD operations for jar
	//
	///////////////////////////////////////

	public static final String INSERT_JAR = "INSERT INTO \"Class_Jar\" (\"total\", \"progress\", \"name\", \"type\", \"uid\") VALUES (?,?,?,?,?)";

	public static final String DELETE_JAR = "DELETE FROM \"Class_Jar\" where uid=?";

	public static final String UPDATE_JAR = "UPDATE \"Class_Jar\" set \"name\"=?, \"total\"=? where uid=?";
	
	
	///////////////////////////////////////
	//
	// CRUD operations for students
	//
	///////////////////////////////////////
	
	public static final String INSERT_STUDENT_USER ="INSERT INTO \"User\"(\"fname\", \"lname\", \"role\", \"registerTime\") VALUES(?,?,?,?) RETURNING \"uid\"";

	public static final String INSERT_STUDENT = "INSERT INTO \"Student_User\"(\"sid\") VALUES(?)";

	public static final String DELETE_STUDENT = "DELETE FROM \"User\" where uid=?";

	public static final String UPDATE_STUDENT = "UPDATE \"User\" set \"fname\"=?, \"lname\"=? where \"uid\"=?";
	
	
	///////////////////////////////////////
	//
	// WebHelperImpl Queries
	//
	///////////////////////////////////////
	
	public static final String GET_LEAGUES = "Select \"leagueName\", \"private\" From \"League\"";
	
	public static final String GET_LEAGUE_DATA = "Select \"uid\", \"fname\", \"lname\", \"leagueName\", \"leagueId\" from \"User\", \"League\" where \"leagueId\"=? AND \"leagueAdmin\"=\"uid\"";
	
}
