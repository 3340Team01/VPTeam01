/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo1;

/**
 *
 * @author E
 */
public class User 
{

    public User(String Lastname, String email, String Password, String Career, String firstname) 
    {
        Utilities u=new Utilities();
        
        this.Lastname = Lastname;
        this.email = email;
        this.Password = u.hash(Password);
        this.Career = Career;
        this.firstname = firstname;
    }
    
    String Lastname;
    String email;
    String Password;
    String Career;
    String firstname;

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
