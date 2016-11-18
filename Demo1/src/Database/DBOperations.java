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
 * @author Eli function protoyping
 */
public class DBOperations
{
 
    protected void login()
    {
        
    }
    
    /* @author Eli */
    public void register( User info)
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
    
    public void newXml(/*xml file or appropriate strings like "prefix", "number", "hours". or an object with this info*/)
    {
        /* 
            Extract info from object, array, xml file or get info from strings passed.
            try to open connection to db.
            if success, check table COURSES for the existence of the current xml attempting to be inserted
            if duplicate return false
            ELSE    INSERT new ROW INTO TABLE COURSES with info like "prefix", "number", "hours".
            close connection, return TRUE
        
        */
    }
    public void addCourse(/* A unique USER credetial like email or maybe A object of class USER with all the info and a OBJECT containg COURSE info or the xml file itself */)
    {
        /*
            Open connection to DB
            SELECT from USER TABLE the ROW which has the CREDENTIAL of the USER passed above.
            SELECT from the RESULT SET the "courses" column.
            LOOP through the "coures" for that user and make sure no instance of the NEW course that is attempting to be added.
            IF no instance, search for the new course attempting to be added within the COURSES table.
            IF NOT FOUND, return false
            IF FOUND, create a foreign key/link from the USER in question to the appropriate course ROW in the COURSES TABLE
            ON success return TRUE
            close connection, exit
        
       
        */
                
    }
    
    public void edCourse(/*OBJECT containing xml info like PREFIX, COURSE NUMBER, something unique*/)
    {
          /*
            Our COURSES table only keeps track of vital details of a course. LIke prefix, number, hours, type of class...lecture or lab
            EXTRACT the info from the object, WHERE "INFO" is PREFIX, COURSES NUMBER, HOURS...
            OPEN DB CONNECTION
            TRY
            SELECT ROW form TABLE WHERE PREFIX is object.PREFIX
            IF FAIL->reurn false, COURSE is not in DATABASE
            ELSE INSERT into SELECTED ROW the updated info
            CLOSE DB CONNECITON
            
        
        */
                  
               
    }
    public void rmCourse(/* OBJECT contaiing USER credentials, COURSE to be removed. WHERE COURSE CAN be a string wtth course prefix or maybe a OBJECT itself*/)
    {
        /* 
            EXTRACT USER credentials
            Open DB connection
            TRY SELECT FROM USER "user crednetial"
            FROM selected USER row, remove the above listed course from the COURSES COLUMN of the USER row
            This will probably be done via "remove foreign key", seperate the link from the COURSES column of the specified USER and the specific course WITHIN THE COURSES TABLE.
            Sever the link.
            RETURN TRUE FOR SUCCESS, FALSE FOR FAILURE
        
        */
    }
    
    public void addRem(/*object/container with details concerning the reminder to be added, USER OBJ */)
    {
            /*
                TRY OPEN connection to DB
                TRY INSERT INTO "REMINDER" TABLE, NEW ROW, contating detials from reminder object passed.
                CHECK that the "reminder" to be added doesnt exist.
                IF DOES NOT EXIST-> insert into table new row containing details from reminder object
                
                extract crednetials from USER OBJ
                SELECT FROM USER TABLE, row THAT CONTAINS USER crednetials
                
                FROM that row, go to COLUMN "reminder". 
                Check that there is no link to the new reminder to be added.
                return false if there is.
                else
                Create a link from "reminder" column to the correct row in REMINDER TABLE containg the USERS reminder item.
                return true
        
                * make sure the "reminder name" in the REMINDER TABLE CLOUMN "reminer_name" is unique. Extract from USER obj the email, extract from REMINDER obj the reminder name.
                  combinde like <email@>_<reminder_name>
        
                 
                
                
            */
    }
    
   
    
 
    
    public void rmRem(/*object/container with details concerning the reminder to be removed */)
    {
        /* same as addRem, just the delte version, prety much*/
    }
    
    
  
}
