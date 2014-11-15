package com.synapp.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;
import org.json.JSONObject;

import com.ego.util.DBConnect;
import com.ego.util.InvalidStamp;
import com.webservice.model.RegistrationImpl;


@Path("/register")
public class RegisterResource {
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

	
	@POST
	@Path("user")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String registerStudent(InputStream incomingData){
		
		StringBuilder builder = new StringBuilder();
		
		JSONObject response = new JSONObject();
		
		int uid;
		String serial;

		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				builder.append(line);
			}
			System.out.println(builder.toString());
			// Extract data from JSON
			JSONObject mainObject = new JSONObject(builder.toString());
			JSONObject regObj =  mainObject.getJSONObject("registration");
			
			serial = regObj.getString("serial");
			uid = regObj.getInt("uid");
			
			new RegistrationImpl(serial,  uid);
			
			response.put("success", "true");
			response.put("serial", serial);		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidStamp e) {
			// TODO Auto-generated catch block
			try {
				response.put("success", "false");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		System.out.println(response.toString());
		return response.toString();
	}
}
