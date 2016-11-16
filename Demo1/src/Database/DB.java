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
import java.sql.ResultSet;
//import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;

/**
 *
 * @author Eli (function interfacing)
 */
public class DB 
{
    // JDBC driver name and database URL
   protected static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   protected static final String DB_URL = "jdbc:mysql://localhost/";
   
    //  Database credentials
   protected static final String USER = "root";
   protected static final String PASS = "teamblack";
   protected static final String DBname="vaqpack";

   
   //Fields required for queries
   protected Connection conn;
   private Statement stmt;
   private ResultSet result;
   private PreparedStatement pstmt;
   
   /**
    * @author Juan Delgado
    * Constructor.
    * This constructor will automatically connect to the database and check the
    * server and check if the database exist. If not it will create it.
   */
   public DB() 
   {
       try
       {
           Class.forName(JDBC_DRIVER); // Load the driver
           dbCheck(DriverManager.getConnection(DB_URL,USER,PASS));
           conn = DriverManager.getConnection(DB_URL + DBname,USER,PASS);
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
   
   /**
    * @author Juan Delgado
    * This function will check if the database exist. If not it will call another
    * function to create it.
    * @param Connection 
    */
   private void dbCheck(Connection connectionTest)
   {
       try 
       {
           // Bool variable that will change depending if the database is there. We assume the database does not exist.
           Boolean exist = false;
           result = connectionTest.getMetaData().getCatalogs();
           if(!result.first()) //Check if database is empty.
           {
               System.out.println("no dfb");
               dbInit(connectionTest);
               return; //Exit the function since we have just created the database.No need for iteration of the rest of the rows.
           }
           result.beforeFirst(); //Moves the cursor to the front of this ResultSet object, just before the first row to prepare for iteration.
           while(result.next()) 
           {
               if(result.getString(1).equalsIgnoreCase(DBname))

               {
                   exist = true;
                   break;
               }
                   
           }
           if(!exist)
               dbInit(connectionTest);
       }
       catch (SQLException e)
       {
           System.out.println("Error could not access the database.");
       }
   }
   
   private void dbInit(Connection connect)
   {
       try 
       {
           String sql = "CREATE DATABASE "+DBname;
           stmt = connect.createStatement();
           int holder = stmt.executeUpdate(sql);
           dbInitTable();
           
       }
       catch (SQLException e)
       {
           System.out.println("ERROR Could not access the database");
       }
   
   }
   
   /**
    * @author Josue Rodriguez
    * This function creates an object of statement, and then creates tables.
    */
   private void dbInitTable()
   {
       try {
           
           //Creates an object of statement
            String DB_URL = "jdbc:mysql://localhost/"+DBname;
           conn = DriverManager.getConnection(DB_URL, USER, PASS);
           Statement stat = conn.createStatement();
           
           String stat0 = "CREATE TABLE Users (email varchar(20), first varchar(10), last varchar(15),"
                   + "password varchar(20), primary key (email))";
           
           //Excutes statement
           stat.executeUpdate(stat0);
           
           System.out.println("Table created succesfully");        
           
           stat.closeOnCompletion();
           
       } catch (SQLException e) {
           System.out.println("Error creating table: "+e.getMessage());       
       }
   }
      
}

