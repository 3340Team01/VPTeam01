/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db_test;
import DB.User;
import DB.Db;
import DB.Util;
/**
 *
 * @author eli
 *  this test can be used to test encrytion and registration
 */
public class Db_test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception 
    {
        /** TEST REGISTRATION**/
		/**
		
        String first="emilo";
        String last="juares";
        String email="thisisnew@gmail.com";
        String pos="art";
        String salt=null;
        String pass="m";
        
        User u=new User(email,first,last,pass,salt,pos);
        Db d=Db.theDatabase();
      
        
        boolean success=d.register(u);
        
        System.out.println(success);
        **/
		
   /**TEST ENCRYPTION**/     
   /**Util util=new Util();
        
        String passwordo="hamtaro";
        String salty=null;
        
        String passSalt[] = new String[2];
        
      
           passSalt=util.encrypt(passwordo, salty);
           
           System.out.println(passSalt[0]);
           System.out.println(passSalt[1]);
     
      **/  
    
    }
    
}
