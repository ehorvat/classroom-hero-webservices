package com.synapp.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
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

@Path("/update")
public class UpdateResource {
	
	

	/*public Connection dbc() throws NamingException, SQLException, ServletException{
		DBConnect conn;
        try {
            conn = new DBConnect("postgres", null);
          
            return conn.connect();
        }

        catch (Exception e) {
            throw new ServletException(e);
        }
    }

	@POST
	@Path("students")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateDB(InputStream incomingData) {
		
		
		 Connection connection=null;
		 try {
			connection = dbc();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
		StringBuilder builder = new StringBuilder();
		WebHelper helper = new WebHelper();
		JSONObject mainObject;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				builder.append(line);
			}
			System.out.println(builder.toString());
			mainObject = new JSONObject(builder.toString());

			JSONObject updates = mainObject.getJSONObject("Updates");

			if(!updates.getJSONArray("Students").isNull(0)){
				JSONArray students = updates.getJSONArray("Students");

				for (int i = 0; i < students.length(); i++) {
					JSONObject studentObj = students.getJSONObject(i);
					StudentUser student = new StudentUser();
					student.setUid(studentObj.getInt("uid"));
					student.setStamp(studentObj.getString("serial"));
					student.setIsActivated(studentObj.getInt("isactivated"));
					student.setLastUpdate(studentObj.getString("timestamp"));
					student.setSpent(studentObj.getInt("pointsspent"));
					student.setEarned(studentObj.getInt("pointsearned"));
					helper.updateStudentRow(student,connection);
				}
			}
			
			if(!updates.getJSONArray("Points").isNull(0)){
				JSONArray earnedPoints = updates.getJSONArray("Points");
				for (int i = 0; i < earnedPoints.length(); i++) {
					JSONObject pointObj = earnedPoints.getJSONObject(i);
					EarnedPoint point = new EarnedPoint(pointObj.getInt("uid"),pointObj.getInt("cid"), pointObj.getString("timestamp"));
					helper.updatePointRow(point,connection);
				}
			}
			
			if(!updates.getJSONArray("Transactions").isNull(0)){
				JSONArray items = updates.getJSONArray("Transactions");
				for (int i = 0; i < items.length(); i++) {
					JSONObject itemObj = items.getJSONObject(i);
					SoldItem item = new SoldItem(itemObj.getInt("uid"),
							itemObj.getInt("iid"), itemObj.getString("timestamp"));
					helper.updateItemRow(item,connection);
				}
				
			}
		
			if(!updates.getJSONArray("Completed").isNull(0)){
				JSONArray achievements = updates.getJSONArray("Completed");
				for(int i = 0; i<achievements.length();i++){
					JSONObject achievementObj = achievements.getJSONObject(i);
					TeacherAchievement achievement = new TeacherAchievement(achievementObj.getInt("aid"), 1);
					helper.updateAchievementRow(achievement, achievementObj.getInt("uid"),connection);
				}
			}
			
			if(!updates.isNull("Jar")){
				int jid = updates.getJSONObject("Jar").getInt("jid");
				int newTotal = updates.getJSONObject("Jar").getInt("newProgress");
				helper.updateJar(jid, newTotal, connection);
			}
			
			
			if(!updates.isNull("profileUpdates")){
				JSONObject obj = updates.getJSONObject("profileUpdates");
	
				helper.updateProfile(obj.getInt("classLvl"), obj.getInt("classProgress"), obj.getInt("nextLvl"), obj.getInt("coins"), obj.getInt("tid"), connection);
			}
			

			connection.close();
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
	}
	
	
	
	
	@POST
	@Path("addCategory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCategories(InputStream incomingData) {
		
		String resp = null;
		 Connection connection=null;
		 try {
			connection = dbc();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	
		 JSONObject respCategoryId = new JSONObject();
		 
		 StringBuilder builder = new StringBuilder();
			WebHelper helper = new WebHelper();
			JSONObject mainObject;
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						incomingData));
				String line = null;
				while ((line = in.readLine()) != null) {
					builder.append(line);
				}
				System.out.println(builder.toString());
				mainObject = new JSONObject(builder.toString());
				
				String newCategory = mainObject.getString("categoryName");
				int cid = mainObject.getInt("cid");
				
				int newCategoryId = helper.addCategory(cid, newCategory, connection);

				connection.close();
				respCategoryId.put("newCategoryId", newCategoryId);
				resp = respCategoryId.toString();		
				

			}catch (IOException e) {
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
			
			
		
		
		
		
	}
	
	
	@POST
	@Path("editCategory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void editCategory(InputStream incomingData) {
		

		 Connection connection=null;
		 try {
			connection = dbc();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	
		 
		 
		 StringBuilder builder = new StringBuilder();
			WebHelper helper = new WebHelper();
			JSONObject mainObject;
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						incomingData));
				String line = null;
				while ((line = in.readLine()) != null) {
					builder.append(line);
				}
				System.out.println(builder.toString());
				mainObject = new JSONObject(builder.toString());
				
				String newCategory = mainObject.getString("editedCategory");
				int cid = mainObject.getInt("categoryId");
				
				helper.editCategory(cid, newCategory, connection);
				
				connection.close();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
	
	
	
	@POST
	@Path("deleteCategory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteCategory(InputStream incomingData) {
		

		

   	
		 Connection connection=null;
		 
		 StringBuilder builder = new StringBuilder();
			WebHelper helper = new WebHelper();
			JSONObject mainObject;
			try {
				
				connection = dbc();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(
						incomingData));
				String line = null;
				while ((line = in.readLine()) != null) {
					builder.append(line);
				}
				System.out.println(builder.toString());
				mainObject = new JSONObject(builder.toString());
				
				int categoryId = mainObject.getInt("deleteCategoryId");
				
				helper.deleteCategory(categoryId, connection);
				
				connection.close();
			}catch (IOException e) {
				
				e.printStackTrace();
			} catch (JSONException e) {
				
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
	
	
	
	
	
	
	
	
	
	
	
	@POST
	@Path("addItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateItems(InputStream incomingData) {
		
		String resp = null;
		 Connection connection=null;
		 try {
			connection = dbc();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	
		 JSONObject respCategoryId = new JSONObject();
		 
		 StringBuilder builder = new StringBuilder();
			WebHelper helper = new WebHelper();
			JSONObject mainObject;
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						incomingData));
				String line = null;
				while ((line = in.readLine()) != null) {
					builder.append(line);
				}
				System.out.println(builder.toString());
				mainObject = new JSONObject(builder.toString());
				
				String newItemName = mainObject.getString("itemName");
				int newItemCost = mainObject.getInt("itemCost");
				int cid = mainObject.getInt("cid");
				
				int newItemId = helper.addItem(cid, newItemName, newItemCost, connection);

				connection.close();
				respCategoryId.put("newItemId", newItemId);
				resp = respCategoryId.toString();		
				

			}catch (IOException e) {
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
			
			
		
		
		
		
	}
	
	
	@POST
	@Path("editItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void editItem(InputStream incomingData) {
		

		 Connection connection=null;
		 try {
			connection = dbc();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	
		 
		 
		 StringBuilder builder = new StringBuilder();
			WebHelper helper = new WebHelper();
			JSONObject mainObject;
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						incomingData));
				String line = null;
				while ((line = in.readLine()) != null) {
					builder.append(line);
				}
				System.out.println(builder.toString());
				mainObject = new JSONObject(builder.toString());
				
				String editedItemName = mainObject.getString("editedItemName");
				int editedItemCost = mainObject.getInt("editedItemCost");
				int iid = mainObject.getInt("itemId");
				
				helper.editItem(iid, editedItemName, editedItemCost, connection);
				
				connection.close();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
	
	
	
	@POST
	@Path("deleteItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteItem(InputStream incomingData) {
		

		

   	
		 Connection connection=null;
		 
		 StringBuilder builder = new StringBuilder();
			WebHelper helper = new WebHelper();
			JSONObject mainObject;
			try {
				
				connection = dbc();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(
						incomingData));
				String line = null;
				while ((line = in.readLine()) != null) {
					builder.append(line);
				}
				System.out.println(builder.toString());
				mainObject = new JSONObject(builder.toString());
				
				int iid = mainObject.getInt("deleteItemId");
				
				helper.deleteItem(iid, connection);
				
				connection.close();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
	
	
	
	
	
	
	@POST
	@Path("unregStamp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void unregStamp(InputStream incomingData) {
		

		

   	
		 Connection connection=null;
		 
		 StringBuilder builder = new StringBuilder();
			WebHelper helper = new WebHelper();
			JSONObject mainObject;
			try {
				
				connection = dbc();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(
						incomingData));
				String line = null;
				while ((line = in.readLine()) != null) {
					builder.append(line);
				}
				System.out.println(builder.toString());
				mainObject = new JSONObject(builder.toString());
				
				int sid = mainObject.getInt("idToUnreg");
				
				helper.unregister(sid, connection);
				
				connection.close();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@POST
	@Path("onresume")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String onResumeUpdate(InputStream incomingData) {
		
		 Connection connection=null;
		 try {
			connection = dbc();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		
		StringBuilder builder = new StringBuilder();
		WebHelper helper = new WebHelper();
		JSONObject mainObject;
		String resp = null;
		
		//Parent Array
    	JSONObject parent;
    	
    	//Teacher container
    	JSONObject teacherResp = null;
    	
    	
    	//Student container
    	JSONObject unregStudent = null;
    	JSONArray unregStudentArray = null;
    	
    	JSONObject student = null;
    	JSONArray studentsArray = null;
    	
    	
    	//Category container
    	JSONObject category = null;
    	JSONArray categoryArray = null;
    	
    	
    	//Item container
    	JSONObject item = null;
    	JSONArray itemArray = null;
    	
    	//ChartData container
    	JSONObject data = null;
    	JSONArray dataArray = null;
    	
    	//Original Timestamps container
    	JSONObject originalTstamps = null;
    	JSONArray tstampsArray = null;
    	
    	JSONObject err = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				builder.append(line);
			}
			System.out.println("ON RESUME" + builder.toString());
			mainObject = new JSONObject(builder.toString());
			int cid = mainObject.getInt("credentials");
			
			
			tstampsArray = mainObject.getJSONArray("timestamps");
			ArrayList<TimeStamp> original = new ArrayList<TimeStamp>();
			for(int i=0;i<tstampsArray.length();i++){
				TimeStamp tstamp = new TimeStamp(tstampsArray.getJSONObject(i).getInt("uid"), tstampsArray.getJSONObject(i).getString("tstamp"));
				original.add(tstamp);
			}
			
			parent = new JSONObject();
			
			
			studentsArray = new JSONArray();
			ArrayList<StudentUser> regStudents = helper.getUpdatedStudents(original, connection);
			for(int i = 0; i < regStudents.size(); i++){
				student = new JSONObject();
				student.put("cid", regStudents.get(i).getCid());
				student.put("name", regStudents.get(i).getFname());
				student.put("uid", regStudents.get(i).getUid());
				student.put("currentscore", regStudents.get(i).getCurrentCoins());
				student.put("totalcoins", regStudents.get(i).getTotalCoins());
				student.put("lvl", regStudents.get(i).getLvl());
				student.put("progress", regStudents.get(i).getProgress());
				student.put("lvlupamount", regStudents.get(i).getLvlUpAmount());
				student.put("serial", regStudents.get(i).getStamp());
				student.put("isactivated", regStudents.get(i).getIsActivated());
				student.put("timestamp", regStudents.get(i).getLastUpdate());
				student.put("pointsspent", 0);
				student.put("pointsearned", 0);
				student.put("gainedxp", 0);
				student.put("gainedlvls", 0);
				studentsArray.put(student);
			}
			
			categoryArray = new JSONArray();
			ArrayList<Category> categories = helper.getTeacherCategories(cid, connection);
			for (int i = 0; i < categories.size(); i++) {
				category = new JSONObject();
				category.put("name", categories.get(i).getName().trim());
				category.put("categoryId", categories.get(i).getId());
				categoryArray.put(category);
			}
		
			
			itemArray = new JSONArray();
			ArrayList<Item> items = helper.getTeacherItems(cid, connection);
			for (int i = 0; i < items.size(); i++) {
				item = new JSONObject();
				item.put("name", items.get(i).getItemName());
				item.put("cost", items.get(i).getItemCost());
				item.put("iid", items.get(i).getIid());
				itemArray.put(item);
			}
			
			dataArray = new JSONArray();
			ArrayList<TeacherTotal> chartData = helper.getTeacherTotals(connection);
			
			for(int i = 0; i < chartData.size();i++){
				data = new JSONObject();
				data.put("uid", chartData.get(i).getUid());
				data.put("teacher_name", chartData.get(i).getFname());
				data.put("count", chartData.get(i).getCount());
				dataArray.put(data);
			}
		
			connection.close();
			parent.put("Students", studentsArray);
			parent.put("Categories",categoryArray);
			parent.put("Items",itemArray);
			parent.put("TeacherTotals", dataArray);
			
			System.out.println(parent.toString());
			resp = parent.toString();
			
			
	
			
			
			
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

	}
	
	
	
	
	
	
	@GET
	@Path("getTeacherTotals")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getTeacherTotals(InputStream incomingData) {
		

	

   	
		 Connection connection=null;
		 String response=null;
		 StringBuilder builder = new StringBuilder();
			WebHelper helper = new WebHelper();
			JSONObject mainObject;
			try {
				
				connection = dbc();
				
		
				
				ArrayList<TeacherTotal> totals = helper.getTeacherTotals(connection);
				JSONObject parent = new JSONObject();
				JSONArray arr = new JSONArray();
				JSONObject child = null;
				for(int i = 0; i<totals.size(); i++){
					child = new JSONObject();
					TeacherTotal total = totals.get(i);
					child.put("uid", total.getUid());
					child.put("count", total.getCount());
					child.put("fname", total.getFname());
					arr.put(child);
				}
				parent.put("TeacherTotals", arr);
				response = parent.toString();
				
				connection.close();
				
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return response;	
	}*/
	
	
	
	
	
	
	
}
