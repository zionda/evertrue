package com.evertrue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class CSVReader {

	public static void main(String[] args) {

		String csvFile = "C:\\Users\\Samuel Zion\\eclipse-workspace\\evertrue\\src\\com\\evertrue\\code-challenge-data.csv";
		String line = "";
		String cvsSplitBy = ",";

		Connection conn = null;
		PreparedStatement st = null;

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection("jdbc:mysql://localhost/evertrue?" +
					"user=root&password=admin"); 

			String query = "INSERT INTO people ("
					+ " first_name,"
					+ " last_name,"
					+ " age,"
					+ " state ) VALUES ("
					+ "?, ?, ?, ?)";

			int i = 0;
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] people = line.split(cvsSplitBy);

				//System.out.println("Person [firstName=" + people[0] + ", lastName=" + people[1] + "]");
				try {

					if (!"first_name".equals(people[0]) && !"last_name".contentEquals(people[1])) {

						i++;
						if (i % 250 == 0) {
							System.out.println("Records processed: " + i);
						}
						st = conn.prepareStatement(query);
						//st.setInt(1, i);
						st.setString(1, people[0]);
						st.setString(2, people[1]);
						st.setString(3, people[2]);
						st.setString(4, people[3]);

						// execute the preparedstatement insert
						st.executeUpdate();
						st.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try{
				if(st!=null)
					st.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
			
		}
	}

}