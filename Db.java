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
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


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
   Db() 
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
 private void dbInitTables()
   {
       try 
       {
           
           
           String sql=null;
           Connection conn = DriverManager.getConnection(DB_URL + DB_NAME,USER,PASS);
           sql = "CREATE TABLE Users ( id int NOT NULL AUTO_INCREMENT, email VARCHAR(255), first varchar(255), last varchar(255),"
                   + "password nvarchar(255), salt nvarchar(255), pos varchar(255), permission int(1), pic LONGBLOB, PRIMARY KEY (id))";
           
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
           
           sql="CREATE TABLE Reminders (reminder_id int, reminderName varchar(256), message varchar(256), StartTime timestamp,"
                   + " EndTime timestamp, PRIMARY KEY (reminderName), FOREIGN KEY (reminder_id) REFERENCES Users(id))";
           
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
    public User login(User u)
    {
      String sql;
      String dbPass=null;
      String dbSalt=null;
      String dbFirst=null;
      String dbLast=null;
      String dbEmail=null;
      String dbPos=null;
      List<String> courses=null;
      
      Util util=new Util();
      String [] hashpass=new String[2];

      sql="SELECT * FROM Users WHERE email= ?";

      try
      {   conn = DriverManager.getConnection(DB_URL + DB_NAME,USER,PASS);
          pstmt=conn.prepareStatement(sql);
          pstmt.setString(1, u.getEmail());
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
         else
             return u;
      }
      catch(SQLException e)
      {
      
      }
      
        try 
        {
            hashpass=util.encrypt(u.getPass(), dbSalt);
            if((hashpass[0].equals(dbPass)))
            {
                u.setEmail(dbEmail);
                u.setFirst(dbFirst);
                u.setLast(dbLast);
                u.setPos(dbPos);
            }
            
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        return u;

    }

    /* @author Eli */
    public boolean register(User u) 
    {
        String sql;
        int rowCount=0;
        Util util=new Util();
        String[] passhash=new String[2];
        
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
                
                
                try {
                    passhash=util.encrypt(u.getPass(), u.getSalt());
                    System.out.println("passwordlogin__>"+passhash[0]+"  salt__"+passhash[1]);
                   
                } catch (Exception ex) {
                    Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                pstmt=conn.prepareStatement(sql);
                pstmt.setString(1, u.getEmail());
                pstmt.setString(2, u.getFirst());
                pstmt.setString(3, u.getLast());
                pstmt.setString(4, passhash[0]);
                pstmt.setString(5, passhash[1]);
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
    /**
     * @author Juan Delgado will add the specified course to the user_courses tables that corresponds to the user id.
     * @param user
     * @param courseToAdd 
     */
    public void addCourse(User user, Course courseToAdd) {
        String sql = "INSERT INTO User_Courses(user_id, course_prefix";
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
    
    /**
     * @author Josue Rodriguez
     * 
     * Inserts a new reminder in the reminder table, with the given user credentials,
     * it will check for a reminder in the same time frame, if there exists one already there,
     * it will reject.
     * 
     * @param start must be "yyyy-mm-dd hh:mm:ss" leading zeros may be omitted
     * @param end must be "yyyy-mm-dd hh:mm:ss" leading zeros may be omitted, example "2016-02-01 07:30:00" or 
     * "2016-2-1 7:30:00, SECONDS NEED TO BE ADDED"
     * @param message
     * @param u User object
     * @return r will return an object of Reminder if it was created correctly, otherwise null.
     */
    public Reminder addRem(String start, String end, String message, User u) {
        int id;
        String sql;
        String name;
 
        Timestamp startTime = Timestamp.valueOf(start);
        Timestamp endTime = Timestamp.valueOf(end);
        name = UUID.randomUUID().toString();
        
        Reminder r = new Reminder(startTime, endTime, message, name, "");
                
        sql = "SELECT * FROM Users WHERE email = ? ";
        
        try{
            conn = DriverManager.getConnection(DB_URL + DB_NAME,USER,PASS); //Connect to the database.
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, u.getEmail());
            rs = pstmt.executeQuery();
            
            if(rs.first()){
                id = rs.getInt(1);
                rs = pstmt.executeQuery();

                sql = "INSERT INTO Reminders () SELECT * FROM (SELECT ?,?,?,?,?) AS tmp WHERE NOT EXISTS (" 
                    + "SELECT reminder_id,StartTime,EndTime FROM Reminders WHERE reminder_id = ? " 
                    + "AND ((StartTime = ?) OR (StartTime < ? AND EndTime > ?))"
                    + "OR ((EndTime = ?) OR (StartTime < ? AND EndTime > ?))" 
                    + "OR (StartTime > ? AND EndTime < ?)) LIMIT 1";
                
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                pstmt.setString(2, name);
                pstmt.setString(3, message);
                pstmt.setTimestamp(4, startTime);
                pstmt.setTimestamp(5, endTime);
                pstmt.setInt(6, id);
                pstmt.setTimestamp(7, startTime);
                pstmt.setTimestamp(8, startTime);
                pstmt.setTimestamp(9, startTime);
                pstmt.setTimestamp(10, endTime);
                pstmt.setTimestamp(11, endTime);
                pstmt.setTimestamp(12, endTime);
                pstmt.setTimestamp(13, startTime);
                pstmt.setTimestamp(14, endTime);
                int i = pstmt.executeUpdate();
                if(i == 1){
                    System.out.println("Reminder added");
                }
                else{
                    System.out.println("Time frame for reminder already occupied");
                r = null;
                }
            }
        }
        
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
            closeStatement(pstmt);

        }
        return r;
    }
    
    /**
     * @author Josue Rodriguez
     * Remove the reminder from the user in the database
     * @param r Reminder to remove
     */
    public void rmRem(Reminder r) {
        String sql;
        
        sql = "DELETE FROM Reminders WHERE reminderName = ?";
        
        try{
            conn = DriverManager.getConnection(DB_URL + DB_NAME,USER,PASS); //Connect to the database.
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, r.getReminderName());
            int i = pstmt.executeUpdate();

            if(i ==1){
                System.out.println("Reminder deleted");
            }
            else{
                System.out.println("Reminder could not be deleted");
            }
         }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
            closeStatement(pstmt);
        }
    }
}