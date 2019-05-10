package com.evertrue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class CompanyLocationReader {

    public static void main(String[] args) {

        String csvFile = "C:\\Users\\Samuel Zion\\eclipse-workspace\\evertrue\\src\\com\\evertrue\\company_locations.csv";
        String line = "";
        String cvsSplitBy = ",";

        Connection conn = null;
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

        	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost/evertrue?" +
            	                                   "user=root&password=admin"); 
            
            String query = "INSERT INTO company_locations ("
            	    + " company_id, state ) VALUES ("
            	    + "?, ?)";

            int i = 0;
            
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] company = line.split(cvsSplitBy);

                //System.out.println("Person [firstName=" + people[0] + ", lastName=" + people[1] + "]");
                try {

            
                	//i++;
                    PreparedStatement st = conn.prepareStatement(query);
                    st.setInt(1, Integer.valueOf(company[0]));
                    st.setString(2, company[1]);

                    // execute the preparedstatement insert
                    st.executeUpdate();
                    st.close();

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
		}
    }

}