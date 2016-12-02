/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

//import static Database.DB.JDBC_DRIVER;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
//import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.util.List;


 /*
 * @author Eli function protoyping
 */
public class Db 
{
    
    private static Db databaseSingleton = new Db();
    
    // JDBC driver name and database URL
    protected static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    protected static final String DB_URL = "jdbc:mysql://localhost/";

    //  Database credentials
    protected static final String USER = "root";

    protected static final String PASS = "root";

    protected static final String DB_NAME = "Vaqpack";

    //Fields required for queries
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
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
           dbCheck(); // Check to see if the database exists
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
   private void dbCheck(){
       Connection connectionTest = null;
       try 
       {
           connectionTest = DriverManager.getConnection(DB_URL,USER,PASS);
           // Bool variable that will change depending if the database is there. We assume the database does not exist.
           Boolean exist = false;
           rs = connectionTest.getMetaData().getCatalogs();
           if(!rs.first()) //Check if database is empty.
           {
               System.out.println("NO DB!!!!!!!!!!!!!!!!!");
               dbInit();
               return; //Exit the function since we have just created the database.No need for iteration of the rest of the rows.
           }
           rs.beforeFirst(); //Moves the cursor to the front of this ResultSet object, just before the first row to prepare for iteration.
           while(rs.next()) 
           {
               if(rs.getString(1).equalsIgnoreCase(DB_NAME))

               {
                   exist = true;
                   break;
               }
                   
           }
           if(!exist)
               dbInit();
       }
       catch (SQLException e)
       {
           System.out.println("Error could not access the database.");
       }
       finally {
           closeConnection(connectionTest);
       }
   }
   /**
    * @author Juan Delgado
    * initializes the Database for the VaqPaq project.
    * @param connect 
    */
   
   private void dbInit(){
       Connection connect  = null;
       try {
           connect = DriverManager.getConnection(DB_URL,USER,PASS);
           String sql = "CREATE DATABASE "+DB_NAME;
           stmt = connect.createStatement();
           int holder = stmt.executeUpdate(sql);
           dbInitTables();
           
       }
       catch (SQLException e)
       {
           System.out.println("ERROR Could not access the database");
       }
       finally {
           closeConnection(connect);
       }
   
   }
   /**
    * @authore Juan Delgado
    * Takes a connection object and closes it.
    * @param cn 
    */
   private void closeConnection(Connection cn){
       try {
           cn.close();
           cn = null;
       }
       catch (SQLException e) {
           e.printStackTrace();
       }
   }
   /**
    * @author Juan Delgado
    * Closes either a statement or prepared statement.
    * @param st 
    */
   private void closeStatement(Statement st){
       try {
           st.close();
           st = null;
       }
       catch (SQLException e){
           e.printStackTrace();
       }
   }
 /* @author eli */ 
 private void dbInitTables()
   {
       try 
       {
           
           
           String sql=null;
           Connection conn = DriverManager.getConnection(DB_URL + DB_NAME,USER,PASS);
           sql = "CREATE TABLE Users ( id int NOT NULL AUTO_INCREMENT, email varchar(20), first varchar(10), last varchar(15),"
                   + "password varchar(20), salt varchar(256), pos varchar(56), permission int(1), pic LONGBLOB, PRIMARY KEY (id))";
           
           pstmt=conn.prepareStatement(sql);
           pstmt.executeUpdate();
           
           sql="CREATE TABLE Courses (prefix varchar(4), courseNumber varchar(4), name varchar(30), course_xml LONGBLOB,"
                   +" abet_xml LONGBLOB, outcomes_xml LONGBLOB"
                   +",PRIMARY KEY (courseNumber) )";
           
           pstmt=conn.prepareStatement(sql);
           pstmt.executeUpdate();
           
           sql="CREATE TABLE Style (name varchar(56), category varchar(56),"+
                   "PRIMARY KEY (name) )";
          pstmt=conn.prepareStatement(sql);
           pstmt.executeUpdate();
           
           sql="CREATE TABLE Reminders (reminder_id int, reminderName varchar(20), message varchar(256), DATE date, TIME time, PRIMARY KEY (reminderName),  "
                   + " FOREIGN KEY (reminder_id) REFERENCES Users(id ))";
           
           pstmt=conn.prepareStatement(sql);
           pstmt.executeUpdate();
           
           sql="CREATE TABLE User_Courses( user_id int, course_prefix varchar(4), course_number varchar(4), course_name varchar(255), grade varchar(1), active int(2), hours FLOAT"
                   + ", FOREIGN KEY (user_id) REFERENCES Users(id))";
           pstmt=conn.prepareStatement(sql);
           pstmt.executeUpdate();
           /*
           sql="CREATE TABLE Xml ( name varchar(56), cat(56) "+" PRIMARY KEY name, FOREIGN KEY name)";
           
           pstmt.executeUpdate(sql); */
           
           pstmt.close();
           
       } catch (SQLException e) {
           System.out.println("Error creating table: "+e.getMessage());       
       }
   } 

    
    /*@author eli */     
    public User login(String email, String pass)
    {
      String sql;
      String dbPass=null;
      String dbSalt=null;
      String dbFirst=null;
      String dbLast=null;
      String dbEmail=null;
      String dbPos=null;
      List<String> courses=null;
      
      User u = new User();

      sql="SELECT * FROM Users WHERE email= ?";

      try
      {   conn = DriverManager.getConnection(DB_URL + DB_NAME,USER,PASS);
          pstmt=conn.prepareStatement(sql);
          pstmt.setString(1, email);
          rs=pstmt.executeQuery();
          
         if(rs.first())
         {
             dbPass=rs.getString("password");
             dbSalt=rs.getString("salt");
             dbFirst=rs.getString("first");
             dbLast=rs.getString("last");
             dbEmail=rs.getString("email");
             dbPos=rs.getString("pos");
         }
      }
      catch(SQLException e)
      {
      
      }
      System.out.println(dbFirst);
      System.out.println(dbSalt+"<--this is SALT");
      pass=pass+dbSalt;
      dbPass+=dbSalt;
      System.out.println(pass+"<--this is USER+SALT");
      System.out.println(dbPass+"<--this is DBPASSWORD+salt");
      
      if((pass).equals(dbPass))
         {
             u.setFirst(dbFirst);
             u.setLast(dbLast);
             u.setEmail(dbEmail);
             u.setPos(dbPos);
             //u.setCourses();
         }
      
      return u;
        
    }

    /* @author Eli */
    public boolean register(User u) 
    {
       String sql;
        int rowCount=0;
        try 
        {
            sql="SELECT email FROM Users WHERE email = ? ";
            conn=DriverManager.getConnection(DB_URL+DB_NAME, USER, PASS);
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, u.getEmail());
            rs=pstmt.executeQuery();
            
            if(!rs.first())
            {
                sql="INSERT INTO Users (email, first, last, password, salt, pos) VALUES (?,?,?,?,?,?)";
                
                pstmt=conn.prepareStatement(sql);
                pstmt.setString(1, u.getEmail());
                pstmt.setString(2, u.getFirst());
                pstmt.setString(3, u.getLast());
                pstmt.setString(4, u.getPass());
                pstmt.setString(5, u.getSalt());
                pstmt.setString(6, u.getPos());
                
                pstmt.execute();
                rowCount=pstmt.getUpdateCount();
            }
        } 
        catch (SQLException e) 
        {

            System.out.println("There is an error: " + e.getMessage());

        } 
        if(rowCount==1)
        return true; 
        else 
        return false;
    }   

    /**
     * @author Juan Delgado
     * Create a new entry in the courses table with the specified prefix, number,
     * and name. It will also store the xml files that have been generated. Note that the
     * xml files must exist already in order for this function to work.
     * @param prefix
     * @param courseNumber
     * @param courseName 
     */
    public void newXml(String prefix, String courseNumber, String courseName) {
        String courseXMLPath = DirectoryStructure.getVACPAC_XML() + prefix + "-" + courseNumber + ".xml";
        String abetXMLPath = DirectoryStructure.getVACPAC_XML() + prefix + "-" + courseNumber + "-abet.xml";
        String outcomesXMLPath = DirectoryStructure.getVACPAC_XML() + prefix + "-" + courseNumber + "-outcomes.xml";
        String sql = "INSERT INTO Courses(prefix, course_number, name, course_xml, abet_xml, outcomes_xml)"
                + "VALUES(?, ?, ?, ?, ?, ?)";
        try{
            conn = DriverManager.getConnection(DB_URL + DB_NAME,USER,PASS); //Connect to the database.
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, prefix);
            pstmt.setString(2, courseNumber);
            pstmt.setString(3, courseName);
            pstmt.setBinaryStream(4, new FileInputStream( new File(courseXMLPath)));
            pstmt.setBinaryStream(5, new FileInputStream( new File(abetXMLPath)));
            pstmt.setBinaryStream(6, new FileInputStream( new File(outcomesXMLPath)));
            pstmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
            closeStatement(pstmt);
        }
    }
    /**
     * @author Juan Delgado
     * Automatically populates the xml files contained in the database.
     */
    public void populateXMLFiles()
    {
        String sql = "SELECT * FROM Courses";
        File xmlFile;
        InputStream is;
        FileOutputStream fs;
        byte[] buffer; //Buffer to write the file.
        try{
            conn = DriverManager.getConnection(DB_URL + DB_NAME,USER,PASS); //Connect to the database
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                //Read the course XML file
                xmlFile = new File(DirectoryStructure.getVACPAC_XML() + rs.getString(1) + "-" + rs.getString(2) + ".xml");
                xmlFile.createNewFile(); // Create the file if it does not exist;
                fs = new FileOutputStream(xmlFile);
                is = rs.getBinaryStream(4);
                buffer = rs.getBytes(4); //Get size of the file in bytes
                while(is.read(buffer) > 0){
                    fs.write(buffer);
                }
                fs.close();
                is.close();
                
                //Read the abet XML file
                xmlFile = new File(DirectoryStructure.getVACPAC_XML() + rs.getString(1) + "-" + rs.getString(2) + "-abet.xml");
                xmlFile.createNewFile(); // Create the file if it does not exist;
                fs = new FileOutputStream(xmlFile);
                is = rs.getBinaryStream(5);
                buffer = rs.getBytes(5); //Get size of the file in bytes
                while(is.read(buffer) > 0){
                    fs.write(buffer);
                }
                fs.close();
                is.close();
                
                //Read the outcome xml file
                xmlFile = new File(DirectoryStructure.getVACPAC_XML() + rs.getString(1) + "-" + rs.getString(2) + "-outcomes.xml");
                xmlFile.createNewFile(); // Create the file if it does not exist;
                fs = new FileOutputStream(xmlFile);
                is = rs.getBinaryStream(6);
                buffer = rs.getBytes(6); //Get size of the file in bytes
                while(is.read(buffer) > 0){
                    fs.write(buffer);
                }
                fs.close();
                is.close();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
            closeStatement(stmt);
        }
    }
    /**
     * @author Juan Delgado
     * Function to retrieve the css files from the database.
     */
    public void populateCSSFiles(){
        String sql = "SELECT * FROM Style";
        File xmlFile;
        InputStream is;
        FileOutputStream fs;
        byte[] buffer = new byte[1096];
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