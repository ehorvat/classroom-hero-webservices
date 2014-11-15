package com.synapp.resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.tomcat.util.scan.Jar;
import org.json.JSONException;
import org.json.JSONObject;

import com.ego.util.DBConnect;
import com.webservice.model.Category;
import com.webservice.model.ChartData;
import com.webservice.model.EarnedPoint;
import com.webservice.model.Item;
import com.webservice.model.SoldItem;
import com.webservice.model.StudentUser;
import com.webservice.model.TeacherAchievement;
import com.webservice.model.TeacherTotal;
import com.webservice.model.TimeStamp;
import com.webservice.model.ClassJar;

public class WebHelper {

	/*PreparedStatement preparedStatement = null;

	int progress, lvl, lvlUpAmount, lvlsGained, currentCoins;


	public ArrayList<StudentUser> getUpdatedStudents(
			ArrayList<TimeStamp> original, Connection conn) {
		String findDetails = "Select DISTINCT cid, uid, 'fname', 'lname', \"currentCoins\", \"totalCoins\", \"lvl\", \"progress\", \"lvlUpAmount\", \"Student_User\".sid, \"isActivated\", stamp, last_update"
				+ " from \"User\""
				+ " INNER JOIN \"Student_User\" ON sid = \"User\".uid "
				+ " NATURAL JOIN \"Student_Class_Match\"";

		ArrayList<StudentUser> students = null;
		try {
			ResultSet rs;

			preparedStatement = conn.prepareStatement(findDetails);

			// preparedStatement.setInt(1, cid);
			rs = preparedStatement.executeQuery();
			/*
			 * rs.absolute(-1); count = rs.getRow();
			 * System.out.println("Appending Data... with row count: " + count);
			 * 
			 * rs.absolute(0);
			 

			students = new ArrayList<StudentUser>();

			for (int i = 0; i < original.size(); i++) {
				while (rs.next()) {
		
					if (rs.getString(13) != null) {
						if (compareTstamps(rs.getString(13), original.get(i)
								.getTstamp())) {
							StudentUser student = new StudentUser();
							student.setCid(rs.getInt(1));
							student.setUid(rs.getInt(2));
							student.setFname(rs.getString(3).trim() + " "
									+ rs.getString(4).trim());
							student.setCurrentCoins(rs.getInt(5));
							student.setTotalCoins(rs.getInt(6));
							student.setLvl(rs.getInt(7));
							student.setProgress(rs.getInt(8));
							student.setLvlUpAmount(rs.getInt(9));
							student.setIsActivated(rs.getInt(11));
							if (rs.getString(12) != null) {
								student.setStamp(rs.getString(12).trim());
							}

							student.setLastUpdate(rs.getString(13).trim());
							students.add(student);


						} else {
							System.out
									.println("Tstamp matches or centralized is null (student not registered). Don't Update");
						}
					}

				}

			}
			System.out.println(students.toString());
			preparedStatement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return students;
	}

	public ArrayList<StudentUser> getAllStudents(Connection conn) {
		String findDetails = "Select DISTINCT cid, uid, \"fname\", \"lname\", \"currentCoins\", \"totalCoins\", \"lvl\", \"progress\", \"lvlUpAmount\", \"Student_User\".sid, \"isActivated\", stamp, last_update"
				+ " from \"User\""
				+ " INNER JOIN \"Student_User\" ON sid = \"User\".uid "
				+ " NATURAL JOIN \"Student_Class_Match\"";

		ArrayList<StudentUser> students = null;
		int count = 0;
		try {
			ResultSet rs;

			PreparedStatement preparedStatement = conn.prepareStatement(
					findDetails, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			// preparedStatement.setInt(1, cid);
			rs = preparedStatement.executeQuery();
			/*
			 * rs.absolute(-1); count = rs.getRow();
			 * System.out.println("Appending Data... with row count: " + count);
			 * 
			 * rs.absolute(0);
			 

			students = new ArrayList<StudentUser>();

			while (rs.next()) {

				StudentUser student = new StudentUser();
				student.setCid(rs.getInt(1));
				student.setUid(rs.getInt(2));
				student.setFname(rs.getString(3).trim() + " "
						+ rs.getString(4).trim());
				student.setCurrentCoins(rs.getInt(5));
				student.setTotalCoins(rs.getInt(6));
				student.setLvl(rs.getInt(7));
				student.setProgress(rs.getInt(8));
				student.setLvlUpAmount(rs.getInt(9));
				student.setIsActivated(rs.getInt(11));
				if (rs.getString(12) == null) {
					student.setStamp("");
				} else {
					student.setStamp(rs.getString(12).trim());
				}
				if (rs.getString(13) == null) {
					student.setLastUpdate("");
				} else {
					student.setLastUpdate(rs.getString(13).trim());
				}

				students.add(student);
			

			}
			System.out.println(students.toString());
			preparedStatement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return students;

	}

	public ArrayList<StudentUser> getUnregStudentSet(int cid, Connection conn) {
		String findDetails = "Select DISTINCT cid, uid, \"fname\", \"lname\", \"currentCoins\", \"lvl\", \"progress\", \"lvlUpAmount\", \"Student_User\".sid, \"isActivated\", stamp"
				+ " from \"User\""
				+ " INNER JOIN \"Student_User\" ON sid = \"User\".uid "
				+ " NATURAL JOIN \"Student_Class_Match\""
				+ " where stamp IS NULL AND cid=?";

		int count = 0;
		ArrayList<StudentUser> students = null;

		try {
			ResultSet rs;
			PreparedStatement preparedStatement = conn.prepareStatement(
					findDetails, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			System.out.println("Retrieving students in class id: " + cid);

			preparedStatement.setInt(1, cid);
			rs = preparedStatement.executeQuery();
			rs.absolute(-1);
			count = rs.getRow();
			System.out.println("Appending Data... with row count: " + count);

			rs.absolute(0);

			students = new ArrayList<StudentUser>();
			while (rs.next()) {
				StudentUser student = new StudentUser();
				student.setUid(rs.getInt(2));
				student.setFname(rs.getString(3).trim() + " "
						+ rs.getString(4).trim());
				student.setCurrentCoins(rs.getInt(5));
				student.setLvl(rs.getInt(6));
				student.setProgress(rs.getInt(7));
				student.setLvlUpAmount(rs.getInt(8));
				student.setIsActivated(rs.getInt(10));
				student.setStamp(rs.getString(11));

				students.add(student);
				System.out.println("Adding.. " + student.fname);
			}
			System.out.println(students.toString());
			preparedStatement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return students;
	}

	public boolean registerStudent(int uid, String serial, Connection conn) {

		boolean success = false;
		String activateSql = "Update \"Student_User\" set \"isActivated\"=1, \"totalCoins\"=\"totalCoins\"+1, \"currentCoins\"=\"currentCoins\"+1, \"progress\"=\"progress\"+1 where \"sid\"=?";
		String stamp_student_match = "INSERT INTO \"Student_Stamp\" (\"sid\", \"serial\") VALUES(?,?)";
		String checkSerial = "Select \"serial\" From \"Student_Stamp\" where \"serial\"=?";
		try {
			preparedStatement = conn.prepareStatement(checkSerial);
			preparedStatement.setString(1, serial);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				success = false;
			} else {
				preparedStatement = conn.prepareStatement(stamp_student_match);
				preparedStatement.setInt(1, uid);
				preparedStatement.setString(2, serial);
				preparedStatement.execute();
				System.out.println("Adding " + uid + " and " + serial);

				preparedStatement = conn.prepareStatement(activateSql);
				preparedStatement.setInt(1, uid);
				preparedStatement.execute();
				preparedStatement.close();
				success = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return success;

	}

	public StudentUser addPoint(String serial, int categoryId, Connection conn) {

		String find_student = "Select sid From \"Student_Stamp\" where serial=?";

		String increment_score = "Update \"Student_User\" set \"currentCoins\"=\"currentCoins\"+1, \"totalCoins\"=\"totalCoins\"+1, \"progress\"=\"progress\"+1 where sid=?";

		String track_point = "Insert Into \"Earned_Points\" (sid, \"earnTime\", \"category_code\") VALUES(?,?,?)";

		String student_details = "Select uid, serial, \"fname\", \"lname\", \"currentCoins\", \"lvl\", \"progress\", \"lvlUpAmount\" "
				+ "From \"User\" "
				+ "INNER JOIN \"Student_User\" ON sid = uid "
				+ "INNER JOIN \"Student_Stamp\" ON \"Student_Stamp\".sid = uid "
				+ "Where uid=?";
		int sid;
		ResultSet rs;
		StudentUser student = null;
		try {
			// Get student ID associated with serial
			preparedStatement = conn.prepareStatement(find_student);
			preparedStatement.setString(1, serial);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				sid = rs.getInt(1);
				// Update student score
				preparedStatement = conn.prepareStatement(increment_score);
				preparedStatement.setInt(1, sid);
				preparedStatement.execute();
				System.out.println("Adding points to user with ID: " + sid);

				preparedStatement = conn.prepareStatement(track_point);
				preparedStatement.setInt(1, sid);

				// Timestamp for earned point
				java.sql.Timestamp earnTime = new java.sql.Timestamp(
						new java.util.Date().getTime());
				preparedStatement.setTimestamp(2, earnTime);
				preparedStatement.setInt(3, categoryId);
				System.out.println(categoryId);
				preparedStatement.execute();

				// Get student details to display in application
				preparedStatement = conn.prepareStatement(student_details);
				preparedStatement.setInt(1, sid);
				rs = preparedStatement.executeQuery();
				student = new StudentUser();
				rs.next();
				student.setFname(rs.getString(3).trim());
				student.setLname(rs.getString(4).trim());
				student.setCurrentCoins(rs.getInt(5));
				student.setLvl(rs.getInt(6));
				student.setProgress(rs.getInt(7));
				student.setLvlUpAmount(rs.getInt(8));
				System.out.println(student.fname.trim() + " has "
						+ rs.getInt(5) + " points!");
			}

			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return student;

	}

	public ArrayList<Category> getTeacherCategories(int cid, Connection conn) {
		
		String getCategories = "Select code, \"Category\" From \"CategoryCodes\" where \"cid\"=?";

		ArrayList<Category> categories = null;
		ResultSet rs;

		try {
			categories = new ArrayList<Category>();

			preparedStatement = conn.prepareStatement(getCategories);
			preparedStatement.setInt(1, cid);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Category category = new Category(rs.getInt(1), rs.getString(2));
				categories.add(category);
				System.out.println("Adding.. " + rs.getString(2));
			}

			preparedStatement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return categories;
	}

	public ArrayList<Item> getTeacherItems(int cid, Connection conn) {
		String getAllItems = "Select \"iid\", \"cost\", \"name\" From \"Item\" where cid=?";
		ArrayList<Item> items = null;
		try {
			preparedStatement = conn.prepareStatement(getAllItems);
			preparedStatement.setInt(1, (Integer) cid);

			ResultSet rs = preparedStatement.executeQuery();
			items = new ArrayList<Item>();
			while (rs.next()) {

				Item item = new Item(rs.getInt(1), rs.getString(3).trim(),
						Integer.parseInt(rs.getString(2).trim()));
				items.add(item);
				System.out.println("Adding.. " + rs.getString(3));
			}
			preparedStatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(items.toString());
		return items;
	}

	public String itemSale(String serial, int iid, Connection conn) {

		String getItemInfo = "Select cost from \"Item\" where iid=?";

		String getStudentInfo = "Select uid, \"fname\", \"lname\", \"currentCoins\",\"serial\""
				+ " FROM \"User\""
				+ " INNER JOIN \"Student_User\" ON sid=uid"
				+ " INNER JOIN \"Student_Stamp\" ON \"Student_Stamp\".sid=uid"
				+ " where \"serial\"=?";

		String updateCoins = "Update \"Student_User\" set \"currentCoins\"=? where sid=?";
		ResultSet rs;
		int cost = 0;
		int uid = 0;
		int currentAmount = 0;
		int previousAmount = 0;
		String name = null;
		JSONObject results = null;
		try {
			preparedStatement = conn.prepareStatement(getItemInfo);
			preparedStatement.setInt(1, iid);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				cost = rs.getInt(1);
			}

			preparedStatement = conn.prepareStatement(getStudentInfo);
			preparedStatement.setString(1, serial);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				uid = rs.getInt(1);
				name = rs.getString(2).trim() + " " + rs.getString(3).trim();
				previousAmount = rs.getInt(4);
				currentAmount = previousAmount - cost;
				if (currentAmount > 0) {
					preparedStatement = conn.prepareStatement(updateCoins);
					preparedStatement.setInt(1, currentAmount);
					preparedStatement.setInt(2, uid);
					preparedStatement.execute();

					results = new JSONObject();
					results.put("previousAmount", previousAmount);
					results.put("currentAmount", currentAmount);
					results.put("name", name);
				} else {
					results = new JSONObject();
					results.put("message", "Not enough coins!");
				}
			} else {
				results = new JSONObject();
				results.put("message", "Stamp is not registered!");
			}

			preparedStatement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return results.toString();
	}

	private void lvlup() {

		lvl += 1;
		lvlsGained += 1;
		progress = progress - lvlUpAmount;
		lvlUpAmount += 2;

	}

	public void updateStudentRow(StudentUser student, Connection conn) throws SQLException {
		// TODO Auto-generated method stub

		StudentUser DBstudent = getDBStudent(student.getUid(), conn);

		lvlUpAmount = DBstudent.getLvlUpAmount();
		lvl = DBstudent.getLvl();
		progress = DBstudent.getProgress();
		currentCoins = DBstudent.getCurrentCoins();

		currentCoins += student.getEarned() - student.getSpent();
		progress += student.getEarned();

		
		while (progress >= lvlUpAmount) {
			System.out.println("LVLING UP");
			lvlup();
		}

		String updateRow = "Update \"Student_User\" set \"totalCoins\"=\"totalCoins\"+ "
				+ student.getEarned()
				+ ", \"currentCoins\"="
				+ currentCoins
				+ ", \"lvl\"="
				+ lvl
				+ ", \"progress\"= "
				+ progress
				+ ", \"lvlUpAmount\"=?, \"isActivated\"=?, \"stamp\"=?, \"last_update\"=? where sid=?";
		preparedStatement = conn.prepareStatement(updateRow);
		preparedStatement.setInt(1, lvlUpAmount);

		preparedStatement.setInt(2, student.getIsActivated());
		preparedStatement.setString(3, student.getStamp());
		preparedStatement.setString(4, student.getLastUpdate());
		preparedStatement.setInt(5, student.getUid());
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void updatePointRow(EarnedPoint point, Connection conn) throws SQLException {

		String insertPoint = "INSERT INTO \"Earned_Points\" (\"sid\", \"category_code\",  \"earnTime\") VALUES(?,?,?)";
		preparedStatement = conn.prepareStatement(insertPoint);
		preparedStatement.setInt(1, point.getSid());
		preparedStatement.setInt(2, point.getCategory());
		preparedStatement.setString(3, point.getTstamp());

		preparedStatement.execute();
		preparedStatement.close();

	}

	public ArrayList<ChartData> getCategoriesAllTime(int cid, Connection conn)
			throws SQLException {
		String allTime = "Select category_code, count(*), \"Category\" From \"Earned_Points\""
				+ " INNER JOIN \"Student_Class_Match\" ON \"Earned_Points\".sid = \"Student_Class_Match\".sid"
				+ " INNER JOIN \"CategoryCodes\" ON category_code = code"
				+ " Where \"Student_Class_Match\".cid=?"
				+ " Group By category_code, \"Category\"";

		ArrayList<ChartData> data = new ArrayList<ChartData>();

		preparedStatement = conn.prepareStatement(allTime);
		preparedStatement.setInt(1, cid);
		ResultSet rs = preparedStatement.executeQuery();
		while (rs.next()) {
			ChartData results = new ChartData(rs.getInt(1), rs.getInt(2),
					rs.getString(3));
			data.add(results);
		}
		System.out.println(data.toString());
		return data;

	}

	public ArrayList<TeacherTotal> getTeacherTotals(Connection conn) throws SQLException {
		String allTime = "Select uid, fname, count(*) From \"User\""
				+ " INNER JOIN \"Class\" ON \"Class\".tid = \"User\".uid"
				+ " INNER JOIN \"CategoryCodes\" ON \"CategoryCodes\".cid = \"Class\".cid"
				+ " INNER JOIN \"Earned_Points\" ON \"CategoryCodes\".code = \"Earned_Points\".category_code"
				+ " Group By uid";

		String getAllTeachers = "Select uid, fname, tid From \"User\" INNER JOIN \"Teacher_User\" ON uid = tid";

		ArrayList<TeacherTotal> data = new ArrayList<TeacherTotal>();

		
		//Get Teachers with points and 0 points
		PreparedStatement preparedStatement = conn.prepareStatement(allTime,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = preparedStatement.executeQuery();

		rs.absolute(-1);
		int count = rs.getRow();
		System.out.println("Appending Data... with row count: " + count);
		int uids[] = null;
		if (count != 0) {
			rs.absolute(0);
			uids = new int[count];
			int i = 0;
			while (rs.next()) {
				uids[i] = rs.getInt(1);
				System.out.println("Adding... " + rs.getInt(3));
				TeacherTotal results = new TeacherTotal(rs.getInt(1),
						rs.getString(2), rs.getInt(3));
				data.add(results);
				i++;
			}
		}

		preparedStatement = conn.prepareStatement(getAllTeachers);

		ResultSet rs2 = preparedStatement.executeQuery();
		if (uids == null) {
			while (rs2.next()) {
				TeacherTotal results = new TeacherTotal(rs2.getInt(1),
						rs2.getString(2), 0);
				data.add(results);

			}
		} else {
			boolean add;
			while (rs2.next()) {
				add = false;
				for (int k = 0; k < uids.length; k++) {
					if (uids[k] == rs2.getInt(1)) {
						add = false;
						break;
					} else {
						add = true;
					}
				}

				if (add) {
					TeacherTotal results = new TeacherTotal(rs2.getInt(1),
							rs2.getString(2), 0);
					data.add(results);
				}

			}
		}
		return data;

	}

	public void updateItemRow(SoldItem item, Connection conn) throws SQLException {

		String insertItem = "INSERT INTO \"Sold_Items\" (\"uid\", \"iid\",  \"tstamp\") VALUES(?,?,?)";
		preparedStatement = conn.prepareStatement(insertItem);
		preparedStatement.setInt(1, item.getSid());
		preparedStatement.setInt(2, item.getIid());
		preparedStatement.setString(3, item.getTstamp());

		preparedStatement.execute();
		preparedStatement.close();

	}

	public boolean compareTstamps(String centralized, String sqllite) {
		boolean send = false;
		if (sqllite.equals(centralized.trim())) {
			send = false;
		} else {
			send = true;
		}
		return send;
	}

	private StudentUser getDBStudent(int uid, Connection conn) throws SQLException {

		String query = "Select progress, \"lvlUpAmount\", lvl, \"currentCoins\" From \"Student_User\" where sid=?";

		preparedStatement = conn.prepareStatement(query);
		preparedStatement.setInt(1, uid);

		ResultSet rs = preparedStatement.executeQuery();
		StudentUser student = new StudentUser();

		while (rs.next()) {

			student.setProgress(rs.getInt(1));
			student.setLvlUpAmount(rs.getInt(2));
			student.setLvl(rs.getInt(3));
			student.setCurrentCoins(rs.getInt(4));

		}

		return student;
	}


	public boolean registerTeacher(int uid, String serial, Connection conn) {

		boolean success = false;
		String activateSql = "Update \"Teacher_User\" set \"isActivated\"=1, \"serial\"=? where \"tid\"=?";

		// String checkSerial =
		// "Select \"serial\" From \"Student_Stamp\" where \"serial\"=?";
		try {
			// preparedStatement = conn.prepareStatement(checkSerial);
			// preparedStatement.setString(1, serial);
			// ResultSet rs = preparedStatement.executeQuery();
			// if(rs.next()){
			// success = false;
			// }else{
			// preparedStatement = conn.prepareStatement(stamp_student_match);
			// preparedStatement.setInt(1, uid);
			// preparedStatement.setString(2, serial);
			// preparedStatement.execute();
			// System.out.println("Adding " + uid + " and " + serial);

			preparedStatement = conn.prepareStatement(activateSql);
			preparedStatement.setString(1, serial);
			preparedStatement.setInt(2, uid);
			preparedStatement.execute();
			preparedStatement.close();
			success = true;
			// }

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;
	}

	public ArrayList<TeacherAchievement> getTeacherAchievements(int uid, Connection conn) {

		String achievementsSql = "Select aid, completed From \"Achievement_Progress\" WHERE \"uid\"=? AND \"completed\"=0";
		ArrayList<TeacherAchievement> achievements = new ArrayList<TeacherAchievement>();
		try {
			preparedStatement = conn.prepareStatement(achievementsSql);
			preparedStatement.setInt(1, uid);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				TeacherAchievement achievement = new TeacherAchievement(rs.getInt(1), rs.getInt(2));
				achievements.add(achievement);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return achievements;
	}

	public void updateAchievementRow(TeacherAchievement achievement, int uid, Connection conn) throws SQLException {
		String updateRow = "Update \"Achievement_Progress\" set \"completed\"=1 where uid=? AND aid=?";
		preparedStatement = conn.prepareStatement(updateRow);
		preparedStatement.setInt(1, uid);
		preparedStatement.setInt(2, achievement.getAid());
		preparedStatement.execute();
		preparedStatement.close();
	}



	public int addCategory(int cid, String newCategory, Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		int newCategoryId = 0;
		String category = "Insert into \"CategoryCodes\" (\"Category\", cid) VALUES(?,?) RETURNING code";
		
			preparedStatement = connection.prepareStatement(category);
			preparedStatement.setString(1, newCategory);
			preparedStatement.setInt(2, cid);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				newCategoryId = rs.getInt(1);
				
			}
			preparedStatement.close();
			return newCategoryId;
		
	}

	public void editCategory(int categoryId, String newCategory, Connection connection) throws SQLException {
		
		
		String editCategorySql = "UPDATE \"CategoryCodes\" set \"Category\"=? where code=?";
		
			preparedStatement = connection.prepareStatement(editCategorySql);
			preparedStatement.setString(1, newCategory);
			preparedStatement.setInt(2, categoryId);
			
			preparedStatement.execute();
			preparedStatement.close();
		
	
		
	}

	public void deleteCategory(int categoryId, Connection connection) throws SQLException {
		
		String deleteCategorySql = "DELETE FROM \"CategoryCodes\" where code=?";
		
			preparedStatement = connection.prepareStatement(deleteCategorySql);
			preparedStatement.setInt(1, categoryId);
			
			preparedStatement.execute();
			preparedStatement.close();
	
	
	}

	public int addItem(int cid, String newItemName, int newItemCost,Connection connection) throws SQLException {
		String addItemSql = "INSERT INTO \"Item\" (\"cid\", \"cost\", \"name\", \"storeType\") " +
				"VALUES	(?,?,?,?) RETURNING \"iid\"";
		int newItemId = 0;
		preparedStatement = connection.prepareStatement(addItemSql);
		preparedStatement.setInt(1, cid);
		preparedStatement.setInt(2, newItemCost);
		preparedStatement.setString(3, newItemName);
		preparedStatement.setInt(4, 1);
		ResultSet rs = preparedStatement.executeQuery();
		if (rs.next()) {
			newItemId = rs.getInt(1);
				
		}
		preparedStatement.close();
		return newItemId;
			
		
	}

	public void editItem(int iid, String editedItemName, int editedItemCost,Connection connection) throws SQLException {
		
		String updateItemSql = "UPDATE \"Item\" set name=?, cost=? where iid=?";
		
			preparedStatement = connection.prepareStatement(updateItemSql);
			preparedStatement.setString(1, editedItemName);
			preparedStatement.setInt(2, editedItemCost);
			preparedStatement.setInt(3, iid);
			
			preparedStatement.execute();
	
		
	}

	public void deleteItem(int iid, Connection connection) throws SQLException {
		
		
		String deleteItemSql = "DELETE FROM \"Item\" where \"iid\"=?";
		
			preparedStatement = connection.prepareStatement(deleteItemSql);
			preparedStatement.setInt(1, iid);			
			preparedStatement.execute();
			preparedStatement.close();
			
	
		
	}

	public void unregister(int sid, Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		String updateItemSql = "UPDATE \"Student_User\" set \"stamp\"='', \"isActivated\"=0 where sid=?";
		
		preparedStatement = connection.prepareStatement(updateItemSql);
		preparedStatement.setString(1, null);
		preparedStatement.setInt(2, sid);
		
		preparedStatement.execute();
	}

	public ClassJar getClassJar(int cid, Connection connection) throws SQLException {
		
		
		String getCustomCategories = "Select \"jid\", \"total\", \"progress\", \"name\" From \"Class_Jar\" where cid=?";
		ClassJar jar = null;

		
			preparedStatement = connection.prepareStatement(getCustomCategories);
			preparedStatement.setInt(1, cid);
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()){
				System.out.println("GEWD");
				jar = new ClassJar();
				jar.setJid(rs.getInt(1));
				jar.setJarTotal(rs.getInt(2));
				jar.setJarProgress(rs.getInt(3));
				jar.setJarName(rs.getString(4));
			}
			
			preparedStatement.close();

		
		
		return jar;
	}

	public void updateJar(int jid, int newProgress, Connection conn) throws SQLException {
		
		String updateRow = "Update \"Class_Jar\" set progress=? where jid=?";
		preparedStatement = conn.prepareStatement(updateRow);
		preparedStatement.setInt(1, newProgress);
		preparedStatement.setInt(2, jid);
		preparedStatement.execute();
		preparedStatement.close();
		
	}

	public int[] getProfileInfo(int tid, Connection conn) throws SQLException {
		  
		String details = "Select coins, \"classLvl\", \"classProgress\", \"nextLvl\" from \"Teacher_User\" JOIN \"Class\" ON \"Teacher_User\".tid=\"Class\".tid where \"Teacher_User\".tid=?";
		preparedStatement = conn.prepareStatement(details);
		int [] values = new int[4];
		preparedStatement.setInt(1, tid);
		ResultSet rs = preparedStatement.executeQuery();

		if (rs.next()) {
			values[0] = rs.getInt(1);
			values[1] = rs.getInt(2);
			values[2] = rs.getInt(3);
			values[3] = rs.getInt(4);
		}	
		return values;
	}

	public void updateProfile(int classLvl, int classProgress, int nextLvl, int coins, int tid, Connection connection) throws SQLException {
		
		String updateClass = "UPDATE \"Class\" set \"classLvl\"=?, \"classProgress\"=?, \"nextLvl\"=? where tid=?";
		preparedStatement = connection.prepareStatement(updateClass);
		preparedStatement.setInt(1, classLvl);
		preparedStatement.setInt(2, classProgress);
		preparedStatement.setInt(3, nextLvl);
		preparedStatement.setInt(4, tid);
		preparedStatement.execute();
		
		String updateTeacher = "UPDATE \"Teacher_User\" set \"coins\"=? where tid=?";
		preparedStatement = connection.prepareStatement(updateTeacher);
		preparedStatement.setInt(1, coins);
		preparedStatement.setInt(2, tid);
		preparedStatement.execute();	

	}*/

}
