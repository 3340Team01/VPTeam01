/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

//import static Database.DB.JDBC_DRIVER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
//import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;


 /*
 * @author Eli function protoyping
 */
public class Db {
    
    private static Db databaseSingleton = new Db();
    
    // JDBC driver name and database URL
    protected static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    protected static final String DB_URL = "jdbc:mysql://localhost/";

    //  Database credentials
    protected static final String USER = "root";
    protected static final String PASS = "Skyl@r5106";
    protected static final String DBname = "vaqpack";

    //Fields required for queries
    private Connection conn;
    private Statement stmt;
    private ResultSet result;
    private PreparedStatement pstmt;

   /**
    * @author Juan Delgado
    * Constructor.
    * This constructor will automatically connect to the database and check the
    * server and check if the database exist. If not it will create it.
   */
   private Db() 
   {
       try
       {
           Class.forName(JDBC_DRIVER); // Load the driver
           dbCheck(DriverManager.getConnection(DB_URL,USER,PASS)); // Check to see if the database exists
           conn = DriverManager.getConnection(DB_URL + DBname,USER,PASS); // Connection that connects to our database.
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
    * returns the database object. Which is a singleton.
    * @return 
    */
   public static Db theDatabase()
   {
       return databaseSingleton;
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
   /**
    * @author Juan Delgado
    * initializes the Database for the VaqPaq project.
    * @param connect 
    */
   
   private void dbInit(Connection connect)
   {
       try 
       {
           String sql = "CREATE DATABASE "+DBname;
           stmt = connect.createStatement();
           int holder = stmt.executeUpdate(sql);
           dbInitTables();
           
       }
       catch (SQLException e)
       {
           System.out.println("ERROR Could not access the database");
       }
   
   }
      private void dbInitTables()
   {
       try 
       {
           
           //Creates an object of statement
            String DB_URL = "jdbc:mysql://localhost/"+DBname;
           conn = DriverManager.getConnection(DB_URL, USER, PASS);
           Statement con = conn.createStatement();
           
           String sql0 = "CREATE TABLE Users (email varchar(20), first varchar(10), last varchar(15),"
                   + "password varchar(20), primary key (email))";
         
           con.executeUpdate(sql0);
           
           String sql1="CREATE TABLE Course (department varchar(20), prefix varchar(4), number varchar(4), name varchar(30), description varchar(300),"
                   + "credit_hours varchar(10), lecture_hours varchar(10), lab_hours varchar(10), level varchar(30), schedule_type varchar(20),"
                   + "pre requisite varchar(200), co_requisite varchar(100), course_attributes varchar(100), legacy_number varchar(4),"
                   + "cross_listed varchar(20), restrictions varchar(200), primary key (number))";
           con.executeUpdate(sql1);
           
           String sql2="CREATE TABLE Reminders (reminderName varchar(20), message varchar(256), currentTime time, primary key (reminderName))";
           con.executeQuery(sql2);
           
          
           
           con.closeOnCompletion();
           
       } catch (SQLException e) {
           System.out.println("Error creating table: "+e.getMessage());       
       }
   }

    
         
    protected boolean login(String email, String pass)
    {
        String dbMail=null;
        String dbPass=null;
        String dbSalt=null;
        
        Utilities u=new Utilities();
        
        try
        {
              conn = DriverManager.getConnection(DB_URL + DBname,USER,PASS);
              stmt=conn.createStatement();
              String sql= "SELECT email ,pass, FROM Users ";
              ResultSet rs=stmt.executeQuery(sql);
                  dbSalt=rs.getString("salt");
                  dbMail=rs.getString("email");
                  dbPass=rs.getString("pass");
                  
              
        }
        catch(SQLException e)
        {
            e.getMessage();
        }
        
        String encPass=u.encrypt(pass,dbSalt);
        if (Objects.equals(encPass, dbMail))
        {
            return true;
        }
        
        else return false;
    
    }

    /* @author Eli */
    public void register(String firstname, String lastname, String email, String password) {
        try {
            Class.forName(JDBC_DRIVER); // Load the driver
            conn = DriverManager.getConnection(DB_URL + DBname, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO Users VALUES (" + email + "," + firstname + "," + lastname + "," + "," + password + ")";
            stmt.executeUpdate(sql);
            System.out.println("Inserted records into the table Users");
        } catch (SQLException e) {

            System.out.println("There is an error: " + e.getMessage());

        } catch (ClassNotFoundException e) {
            System.out.println("Please ensure you have mysql connector jar linked to the project.");
        }

    }

    public void newXml(/*xml file or appropriate strings like "prefix", "number", "hours". or an object with this info*/) {
        /* 
            Extract info from object, array, xml file or get info from strings passed.
            try to open connection to db.
            if success, check table COURSES for the existence of the current xml attempting to be inserted
            if duplicate return false
            ELSE    INSERT new ROW INTO TABLE COURSES with info like "prefix", "number", "hours".
            close connection, return TRUE
        
         */
    }

    public void addCourse(String department, String prefix, String number, String name, String description, 
            String creditHours, String lectureHours, String labHours, String level, String scheduleType,
            String prerequiste, String corequisite, String courseAttributes) {
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

    public void edCourse(/*OBJECT containing xml info like PREFIX, COURSE NUMBER, something unique*/) {
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

    public void rmCourse(/* OBJECT contaiing USER credentials, COURSE to be removed. WHERE COURSE CAN be a string wtth course prefix or maybe a OBJECT itself*/) {
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

    public void addRem(/*object/container with details concerning the reminder to be added, USER OBJ */) {
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

    public void rmRem(/*object/container with details concerning the reminder to be removed */) {
        /* same as addRem, just the delte version, prety much*/
    }
}
