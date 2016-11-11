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
import java.sql.DatabaseMetaData;

/**
 *
 * @author E
 */
public class DB 
{
    // JDBC driver name and database URL
   private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   private static final String DB_URL = "jdbc:mysql://localhost/";
   
    //  Database credentials
   private static final String USER = "root";
   private static final String PASS = "Skyl@r5106";
   
   //Fields required for queries
   private Connection conn;
   private Statement stmt;
   private ResultSet result;
   
   /**
    * @author Juan Delgado
    * Constructor.
    * This constructor will automatically connect to the database and check the
    * server and check if the database exist. If not it will create it.
   */
   public DB() {
       try {
           Class.forName(JDBC_DRIVER); // Load the driver
           dbCheck(DriverManager.getConnection(DB_URL,USER,PASS));
           conn = DriverManager.getConnection(DB_URL + "VaqPaqTestDB",USER,PASS);
       }
       catch (SQLException e) {
           System.out.println("ERROR Could not connect to the database.");
           
       }
       catch (ClassNotFoundException e){
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
       try {
           // Bool variable that will change depending if the database is there. We assume the database does not exist.
           Boolean isThere = false;
           result = connectionTest.getMetaData().getCatalogs();
           if(!result.first()) //Check if database is empty.
           {
               dbInit(connectionTest);
               return; //Exit the function since we have just created the database.No need for iteration of the rest of the rows.
           }
           result.beforeFirst(); // Move the cursor before the first to prepare for iteration.
           while(result.next()) {
               if(result.getString(1).equalsIgnoreCase("VaqPaqTestDB"))
                   isThere = true;
                   
           }
           if(!isThere)
               dbInit(connectionTest);
       }
       catch (SQLException e){
           System.out.println("Error could not access the database.");
       }
   }
   
   private void dbInit(Connection connect)
   {
       try {
           String sql = "CREATE DATABaSE VaqPaqTestDB";
           stmt = connect.createStatement();
           int holder = stmt.executeUpdate(sql);
       }
       catch (SQLException e){
           System.out.println("ERROR Could not access the database");
       }
   
   }
   
   private void dbInitTable()
   {
   
   }
   public static void main(String[] args){
       DB p = new DB();
   }
   
}
