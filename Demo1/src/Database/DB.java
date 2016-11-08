/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author E
 */
public class DB 
{
    // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "irasaico_blue_heaven";

    //  Database credentials
   static final String USER = "irasaico_black";
   static final String PASS = "teamblack";
   
   Connection conn = null;
   Statement stmt = null;
    
    public boolean checkDBExist()
    {
        boolean database=false;
        
        return database;
        
    }
    
    public void createDB()
    {
        try
        {
            //Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //Execute a query
            System.out.println("Creating database...");
            stmt = conn.createStatement();
      
             String sql = "CREATE DATABASE USERS";
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        }
        catch(SQLException | ClassNotFoundException se)
        {
            
        }
        finally
        {
            //finally block used to close resources
            try{
                if(stmt!=null)
                stmt.close();
                }
            catch(SQLException se2)
            {
                
            }
              // nothing can be done
            try
            {
             if(conn!=null)
                conn.close();
            }
            catch(SQLException se)
            {
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
   
    }
    
    
    
}
