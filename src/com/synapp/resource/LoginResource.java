package com.synapp.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.ego.util.DBConnect;
import com.ego.util.DBConnection;
import com.ego.util.LoginServiceImpl;
import com.ego.util.PasswordMisMatch;
import com.webservice.model.AuthService;
import com.webservice.model.Category;
import com.webservice.model.ChartData;
import com.webservice.model.ClassJar;
import com.webservice.model.Item;
import com.webservice.model.Jar;
import com.webservice.model.LeagueProfile;
import com.webservice.model.Profile;
import com.webservice.model.StudentUser;
import com.webservice.model.TeacherAchievement;
import com.webservice.model.TeacherProfile;
import com.webservice.model.TeacherTotal;
import com.webservice.model.TeacherUser;
import com.webservice.model.User;
import com.webservice.model.League;

@Path("/login")
public class LoginResource {
	// The @Context annotation allows us to have certain contextual objects
	// injected into this class.
	// UriInfo object allows us to get URI information (no kidding).
	
	
	
	@Context
	UriInfo uriInfo;

	// Another "injected" object. This allows us to use the information that's
	// part of any incoming request.
	// We could, for example, get header information, or the requestor's
	// address.
	@Context
	Request request;

	// Use data from the client source to create a new Person object, returned
	// in JSON format.
	@POST
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
    public String authUser(InputStream incomingData) {
    	User user = null;
    	
    	String username = null;
    	String password = null;
    	JSONObject mainObject;
    	
    	//Parent Array
    	JSONObject parent = null;
    	
    	//Teacher container
    	JSONObject teacherResp = null;
    	
    	//Admin(League) container
    	JSONObject adminResp = null;   	
    	
    	JSONObject err = null;

    	StringBuilder builder = new StringBuilder();
    	
    	String resp = null;
    	
    	LoginServiceImpl auth = null;
    	
    	JSONArray entities = null;
    	
    	try {
    		BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
        	String line = null;
			while((line = in.readLine()) != null){
				builder.append(line);
			}
			
			
			mainObject = new JSONObject(builder.toString());
			JSONObject credObj = (JSONObject) mainObject.get("credentials");
			username = credObj.getString("username");
			password = credObj.getString("password");
			
			parent = new JSONObject();
			
			auth = new LoginServiceImpl(username, password);
			
			user = auth.getUser();
			System.out.println("Stamp: " + user.getStamp());
			Profile profile;
			if(user instanceof TeacherUser){
				//Teacher is logging in
				profile = new TeacherProfile((TeacherUser) user);
				//Get teacher data and store into a json object
				teacherResp = new JSONObject();
				teacherResp.put("uid", user.getUid());
				teacherResp.put("cid", ((TeacherUser) user).getCid());
				teacherResp.put("isactivated", user.getIsActivated());
				teacherResp.put("fname", user.getFname());
				teacherResp.put("coins", ((TeacherUser) user).getCoins());
				teacherResp.put("classLvl", ((TeacherUser) user).getClassLvl());
				teacherResp.put("classProgress", ((TeacherUser) user).getClassProgress());
				teacherResp.put("nextLvl", ((TeacherUser) user).getClassNextLvl());
				teacherResp.put("role", user.getUserType());
				teacherResp.put("success", "true");
				
				if(user.getStamp() == "" || user.getStamp() == null){
					teacherResp.put("stamp", "");
				}else{
					teacherResp.put("stamp", user.getStamp());
				}
				
				
				//Get teacher items/categories/students into a single JSONArray
				entities =  ((TeacherProfile) profile).initProfile();
				
			
											
				parent.put("login", teacherResp);
				parent.put("entities", entities);
		
				//parent.put("TeacherTotals", dataArray);
				//parent.put("Achievements", achievementArray);
				
				
			}else if(user instanceof League){
				//Admin is logging in
				profile = new LeagueProfile((League) user);
				
				adminResp = new JSONObject();
				adminResp.put("uid", user.getUid());
				adminResp.put("isactivated", user.getIsActivated());
				adminResp.put("stamp", user.getStamp());
				adminResp.put("fname", user.getFname());
				adminResp.put("role", user.getUserType());
				adminResp.put("success", "true");
				
				//Get admin items/categories/jar into a single JSONArray
				entities = ((LeagueProfile) profile).initProfile();
				
				//Get teacher/student pairs into a single JSONArray
				JSONArray teacherStudentPairs = ((LeagueProfile) profile).getTeacherStudentPairs();
			
				parent.put("login", adminResp);
				parent.put("entities", entities);
				parent.put("classes", teacherStudentPairs);
				
								
			}
				
				resp = parent.toString();
				System.out.println(resp);

				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PasswordMisMatch e) {
			err = new JSONObject();	
			try {
				err.put("success", "false");
				parent.put("login", err);
				resp = parent.toString();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			DBConnection.connection.disconnect();
		}
    	System.out.println(resp);
		return resp;
   
    }
    

	/*@POST
	@Path("authStamp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String authorizeWithStamp(InputStream incomingData) {

		User user = null;

		JSONObject mainObject;

		// Parent Array
		JSONObject parent;

		// Teacher container
		JSONObject teacherResp = null;

		JSONObject student = null;
		JSONArray studentsArray = null;

		// Category container
		JSONObject category = null;
		JSONArray categoryArray = null;

		// Item container
		JSONObject item = null;
		JSONArray itemArray = null;

		// ChartData container
		JSONObject data = null;
		JSONArray dataArray = null;

		JSONObject achievement = null;
		JSONArray achievementArray = null;

		JSONObject err = null;

		StringBuilder builder = new StringBuilder();

		AuthService service = new AuthService();
		WebHelper helper = new WebHelper();
		String resp = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				builder.append(line);
			}

			mainObject = new JSONObject(builder.toString());
			String serial = mainObject.getString("serial");
			parent = new JSONObject();

			LoginServiceImpl auth = new LoginServiceImpl(serial);

			user = auth.getUser();

			TeacherProfile teacherProfile = new TeacherProfile(
					(TeacherUser) user);

			if (service.authWithStamp(serial, connection)) {

				teacherResp = new JSONObject();
				TeacherUser teacher = service.getTeacher();
				teacherResp.put("uid", teacher.getUid());
				teacherResp.put("cid", teacher.getCid());
				teacherResp.put("isactivated", teacher.getIsActivated());
				teacherResp.put("serial", teacher.getSerial());
				teacherResp.put("fname", teacher.getFname());
				teacherResp.put("success", "true");

				int[] values = new int[4];
				values = helper.getProfileInfo(teacher.getUid(), connection);
				if (values != null) {
					teacherResp.put("coins", values[0]);
					teacherResp.put("classLvl", values[1]);
					teacherResp.put("classProgress", values[2]);
					teacherResp.put("nextLvl", values[3]);
				}

				studentsArray = new JSONArray();
				ArrayList<StudentUser> regStudents = helper
						.getAllStudents(connection);
				for (int i = 0; i < regStudents.size(); i++) {
					student = new JSONObject();
					student.put("cid", regStudents.get(i).getCid());
					student.put("name", regStudents.get(i).getFname());
					student.put("uid", regStudents.get(i).getUid());
					student.put("currentscore", regStudents.get(i)
							.getCurrentCoins());
					student.put("totalcoins", regStudents.get(i)
							.getTotalCoins());
					student.put("lvl", regStudents.get(i).getLvl());
					student.put("progress", regStudents.get(i).getProgress());
					student.put("lvlupamount", regStudents.get(i)
							.getLvlUpAmount());
					student.put("isactivated", regStudents.get(i)
							.getIsActivated());
					student.put("timestamp", regStudents.get(i).getLastUpdate());
					student.put("pointsspent", 0);
					student.put("pointsearned", 0);

					if (regStudents.get(i).getStamp() == "") {
						student.put("serial", "");
					} else {
						student.put("serial", regStudents.get(i).getStamp());
					}

					studentsArray.put(student);
				}

				categoryArray = new JSONArray();
				ArrayList<Category> categories = helper.getTeacherCategories(
						teacher.getCid(), connection);
				for (int i = 0; i < categories.size(); i++) {
					category = new JSONObject();
					category.put("name", categories.get(i).getName().trim());
					category.put("categoryId", categories.get(i).getId());
					categoryArray.put(category);
				}

				itemArray = new JSONArray();
				ArrayList<Item> items = helper.getTeacherItems(
						teacher.getCid(), connection);
				for (int i = 0; i < items.size(); i++) {
					item = new JSONObject();
					item.put("name", items.get(i).getItemName());
					item.put("cost", items.get(i).getItemCost());
					item.put("iid", items.get(i).getIid());
					itemArray.put(item);
				}

				dataArray = new JSONArray();
				ArrayList<TeacherTotal> chartData = helper
						.getTeacherTotals(connection);

				for (int i = 0; i < chartData.size(); i++) {
					data = new JSONObject();
					data.put("uid", chartData.get(i).getUid());
					data.put("teacher_name", chartData.get(i).getFname());
					data.put("count", chartData.get(i).getCount());
					dataArray.put(data);
				}

				ArrayList<TeacherAchievement> achievements = helper
						.getTeacherAchievements(teacher.getUid(), connection);
				achievementArray = new JSONArray();

				for (int i = 0; i < achievements.size(); i++) {
					achievement = new JSONObject();
					TeacherAchievement ta = achievements.get(i);
					achievement.put("aid", ta.getAid());
					achievement.put("completed", ta.getCompleted());
					achievementArray.put(achievement);
				}

				System.out.println(teacher.getCid());
				ClassJar jar = helper.getClassJar(teacher.getCid(), connection);
				JSONObject jarContainer = new JSONObject();
				jarContainer.put("name", jar.getJarName());
				jarContainer.put("total", jar.getJarTotal());
				jarContainer.put("progress", jar.getJarProgress());
				jarContainer.put("jid", jar.getJid());

				connection.close();
				parent.put("login", teacherResp);
				parent.put("Students", studentsArray);
				parent.put("Categories", categoryArray);
				parent.put("Items", itemArray);
				parent.put("TeacherTotals", dataArray);
				parent.put("Achievements", achievementArray);
				parent.put("ClassJar", jarContainer);

				System.out.println(parent.toString().trim());
				resp = parent.toString();

			} else {
				connection.close();
				err = new JSONObject();

				err.put("success", "false");
				parent.put("login", err);
				resp = parent.toString();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resp;

	}*/
	
	
	
	/*ArrayList<TeacherAchievement> achievements = helper.getTeacherAchievements(teacher.getUid(),connection);
	achievementArray = new JSONArray();
	
	for(int i = 0; i < achievements.size(); i++){
		achievement = new JSONObject();
		TeacherAchievement ta = achievements.get(i);
		achievement.put("aid", ta.getAid());
		achievement.put("completed", ta.getCompleted());
		achievementArray.put(achievement);
	}*/
	

}
