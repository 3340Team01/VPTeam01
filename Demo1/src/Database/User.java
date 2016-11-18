/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

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
    
    
     
     
    public User(String Lastname, String firstname, String Password, String email ) 
    {
       
         Utilities u=new Utilities();
         
        this.Lastname = Lastname;
        this.email = email;
        this.Password =Password;
        this.firstname = firstname;
    }
    
    public void getUser(/*email*/)
    {   
        
        /*to be used by the program to create a obejct containing user info expect for password*/
    }
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String Lastname) {
        this.Lastname = Lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getCareer() {
        return Career;
    }

    public void setCareer(String Career) {
        this.Career = Career;
    }
    
    
    
}
