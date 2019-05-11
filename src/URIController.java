package com.evertrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class URIController {

	public static void main(String[] args) {
		/*
		 * DZion 05/10/2019
		 * This class will simulate application within servlet container,
		 * reading the method redirection with necessary parameter for the
		 * CareerInfo implementation. Base URL will be 
		 * /com/dzion/evertrue/{method}/{parameter}
		 */

		if (args[0] == null) {
			System.out.println("URI required.");
			return;
		}

		String[] uriComponents = args[0].split("/");
		//System.out.println(uriComponents[4]);
		String uriComponent = uriComponents[4];
		int parameter;
		String JsonOutput = "";
		switch(uriComponent) 
		{ 
		case "careerinfo": 
			try {
				parameter = Integer.valueOf(uriComponents[5]);
			} catch (Exception e) {
				System.out.println("Invalid parameter");
				break;
			}

			JsonOutput = careerInfo(parameter);
			break; 
		case "companyrentention": 
			JsonOutput = companyRetention(1); 
			break; 
		case "mosttenured": 
			//mostTenured();
			JsonOutput = companyRetention(2);
			break; 
		default: 
			System.out.println("Invalid URI");
			return;
		}
		
		System.out.println(JsonOutput);
	}

	public static String careerInfo(int id) {
		//System.out.println("Start careerInfo with person ID = " + id);
		String output = null;
		
		String query =   "SELECT pos.position, "
				+ "co.company, "
				+ "loc.state, "
				+ "hist.start_date, "
				+ "hist.end_date "
				+ "FROM people INNER JOIN career_history AS hist "
				+ "ON hist.people_id = people.id "
				+ "INNER JOIN company_locations AS loc "
				+ "ON hist.company_location_id = loc.id "
				+ "INNER JOIN companies AS co "
				+ "ON loc.company_id = co.id "
				+ "INNER JOIN positions AS pos "
				+ "ON hist.position_id = pos.id "
				+ "WHERE people.id = " + id + " "
				+ "ORDER "
				+ "BY hist.start_date DESC";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection("jdbc:mysql://localhost/evertrue?" +
					"user=root&password=admin");

			stmt = conn.createStatement();

			rs = stmt.executeQuery(query);

			ArrayList<CareerInfo> ciList = new ArrayList<>();
			while(rs.next()){
				//String position = rs.getString("position");
				//System.out.println(position);
				CareerInfo ci = new CareerInfo(rs.getString("position"), rs.getString("company"), rs.getString("state"), rs.getDate("start_date"), rs.getDate("end_date"));
				ciList.add(ci);
			}

			//Gson gson = new Gson();
			Gson gson = new GsonBuilder().setDateFormat("MM/dd/yyy").create();
			output = gson.toJson(ciList);
			//System.out.println(output);
			
			//Clean-up environment
			rs.close();
			stmt.close();
			conn.close();

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		
		return output;
	}

	public static String companyRetention(int type) {
		/*
		 * type = 1 is just company detail
		 * type = 2 is company and most tenured detail
		 */
		
		String output = null;

		// Base/outer cursor to get all companies with average retention.
		// Definition of retention is end_date - start_date
		String query =   "SELECT co.id, " + 
				"           co.company, " + 
				"           ROUND(AVG(DATEDIFF(hist.end_date, hist.start_date)/365.25), 2) AS retention_time " + 
				"      FROM career_history AS hist " + 
				"           INNER JOIN company_locations AS loc " + 
				"           ON hist.company_location_id = loc.id " + 
				"           RIGHT JOIN companies AS co " + 
				"           ON loc.company_id = co.id " + 
				" 	 GROUP " + 
				"        BY co.id, " + 
				"           co.company " + 
				"	 ORDER " + 
				"        BY 3 DESC, 2";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection("jdbc:mysql://localhost/evertrue?" +
					"user=root&password=admin");

			stmt = conn.createStatement();

			rs = stmt.executeQuery(query);

			// Create both array lists
			ArrayList<CompanyRetention> crList = new ArrayList<>();
			ArrayList<MostTenured> mtList = new ArrayList<>();
			
			while(rs.next()){
				// Populate appropriate list
				if (type == 1) {
					CompanyRetention cr = new CompanyRetention(rs.getString("company"), rs.getFloat("retention_time"));
					crList.add(cr);
				} else if (type == 2) {
					MostTenured mt = new MostTenured(rs.getString("company"), rs.getFloat("retention_time"));
					
					// Call helper function to return tenured individuals
					mt.setTenuredPeople(getListOfTenuredPeople(rs.getInt("id"), rs.getFloat("retention_time")));
					mtList.add(mt);
					
				}
			}

			// Create Gson object with appropriate date format
			Gson gson = new GsonBuilder().setDateFormat("MM/dd/yyy").create();
			if (type == 1) {
				output = gson.toJson(crList);
				//System.out.println(output);
			} else if (type == 2) {
				System.out.println("type=2?");
				output = gson.toJson(mtList);
			}
			
			//Clean-up environment
			rs.close();
			stmt.close();
			conn.close();

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} finally{
			try{
				if(stmt != null)
					stmt.close();
			}catch(SQLException e){
				// Do nothing
			}
			try{
				if(conn != null)
					conn.close();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		
		// Return JSON string formatted by Gson class utility
		return output;

	}

	public static ArrayList<TenuredPeople> getListOfTenuredPeople(int companyId, float averageRetentionTime){
		// Helper method to return all people with an individual tenure greater than the company average
		// A person with multiple tenures might appear twice, but the combined tenure time doesn't count.
		// Can't have it both ways, so I choose the company;s tenure perspective.
		String query = "SELECT people.id, " +
		          "            CONCAT(people.first_name, \" \", people.last_name) AS full_name, " +
		          "            people.age, " +
		          "            ROUND(DATEDIFF(hist.end_date, hist.start_date)/365.25, 2) AS retention_time " +
		          "       FROM career_history AS hist " +
		          "            INNER JOIN company_locations AS loc " +
		          "            ON hist.company_location_id = loc.id " +
		          "            INNER JOIN companies AS co " +
		          "            ON loc.company_id = co.id " +
		          "            INNER JOIN people " +
		          "            ON hist.people_id = people.id " +
			      "      WHERE co.id = ? " +
		          "        AND ROUND(DATEDIFF(hist.end_date, hist.start_date)/365.25, 2) > ? " +
		          "      ORDER " +
		          "         BY 4 DESC, people.last_name";        

		ArrayList<TenuredPeople> tpList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection("jdbc:mysql://localhost/evertrue?" +
					"user=root&password=admin");

			stmt = conn.prepareStatement(query);
			stmt.setInt(1, companyId);
			stmt.setFloat(2, averageRetentionTime);
			
			System.out.println(query);
			rs = stmt.executeQuery();
			
			// Iterate through people and add them to the return ListArray
			while (rs.next()) {
				TenuredPeople tp = new TenuredPeople(rs.getString("full_name"), rs.getInt("age"), rs.getFloat("retention_time"));
				tpList.add(tp);
			}
		
			// Clean-up environment
			rs.close();
			stmt.close();
			conn.close();

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			// Finally block used to close resources
			try{
				if (rs != null){
					rs.close();
				}
			} catch (SQLException ex){
				// Do nothing
			}
			try{
				if(stmt != null)
					stmt.close();
			}catch(SQLException ex2){
				// Do nothing
			}
			try{
				if(conn != null)
					conn.close();
			}catch(SQLException ex){
				ex.printStackTrace();
			}
		}
		
		// Return the list of people
		return tpList;
	}
}