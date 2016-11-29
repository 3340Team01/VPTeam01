/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author E
 */
public class User 
{

    String last;
    String email;
    String pass;
    String first;
    String hash;
    String salt;
    List courses;
    List reminders;
    
    
    
    public User(String Lastname, String firstname, String password, String email ) 
    {
       
         Util u=new Util();
         
        this.last = Lastname;
        this.email = email;
        this.pass = u.encrypt(password);
        this.first = firstname;
    }
    
    public void getUser(/*email*/)
    {   
        
        /*to be used by the program to create a obejct containing user info expect for password*/
    }
    
    public String getFirstname() {
        return first;
    }

    public void setFirstname(String firstname) {
        this.first = firstname;
    }

    public String getLastname() {
        return last ;
    }

    public void setLastname(String Lastname) {
        this.last = Lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return pass;
    }

    public void setPassword(String Password) {
        this.pass = Password;
    }
    
}
