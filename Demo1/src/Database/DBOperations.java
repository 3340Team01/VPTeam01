/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import static Database.DB.JDBC_DRIVER;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Eli
 */
public class DBOperations extends DB 
{
 
    protected void checkUser()
    {
        
    }
    
    public void registerUser( User info)
    {
         try
       {
           Class.forName(JDBC_DRIVER); // Load the driver
           conn = DriverManager.getConnection(DB_URL + DBname,USER,PASS);
           
         String first=info.firstname;
         String last=info.Lastname;
         String email=info.email;
         String password=info.Password;
         
         Statement stmt = conn.createStatement();
         String sql="INSERT INTO Users VALUES ("+email+","+first+","+last+","+","+password+")";
      
         stmt.executeUpdate(sql);
         System.out.println("Inserted records into the table Users");
       }
       catch (SQLException e) 
       {
           
           System.out.println("There is an error: "+e.getMessage());
           
       }
       catch (ClassNotFoundException e)
       {
           System.out.println("Please ensure you have mysql connector jar linked to the project.");
       }
  
         
    }
    
    
  
}
