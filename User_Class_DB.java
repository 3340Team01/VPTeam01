/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_class_db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author juand
 */
public class User_Class_DB {

    private Connection conn;
    private Statement stmt;
    private String host = "jdbc:mysql://localhost/";
    private String user = "root";
    private String pass = "Skyl@r5106";

    public User_Class_DB() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(host, user, pass);
            checkDB();
            System.out.println("IT Worked");
            conn = DriverManager.getConnection(host + "UserTestDB", user, pass);
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: Appropriate JDBC Driver was loaded incorrectly");

        } catch (SQLException e) {
            //System.out.println("Could not connect to database.");
            e.printStackTrace();
        }
    }

    /**
     * Checks to see if the database exists or not. If it does not exist, then
     * create it.
     *
     * @author Juan Delgado
     */
    private Connection checkDB() {
        try {
            String sql = "CREATE DATABASE IF NOT EXISTS UserTestDB";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            return getConnectionDB();
        } catch (SQLException e) {
            System.out.println("Error: could not process sql request");
        }
        return null;
    }
    
    private Connection getConnectionDB() throws SQLException{
        return conn = DriverManager.getConnection(host + "UserTestDB", user, pass);
        
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        User_Class_DB usr = new User_Class_DB();
    }

}
