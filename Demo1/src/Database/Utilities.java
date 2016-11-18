/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author E
 */
public class Utilities 
{
    /**
    *
    * @author Michelle
    */
    
    
    void encrypt(String pass,String salt)
    {
        if(salt==null)
        {
            //call genSalt()
        }
        else
        {
            //apply the passed salt to the passed password then encrypt.
        }
        
        /*
            accept a string
            call generate salt()
            apply generated salt to string
            use some hash or sercurity function to encrypt the (string + salt)
            create a structure or array
            place within it the newly encrypted string and the salt that wash generated
            reutrnt the array/structure

        */
    }
    
    void genSalt()
    {
        /*
            generates a random salt somehow. Random character generator
        
        */
    }
    /*
    
    public static byte[] hash(String password)
    {        
        byte[] salt=genSalt();
        
        try
        {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 1000, 256);
            SecretKey key = skf.generateSecret(spec);
            byte[] hash = key.getEncoded();
            
            return hash;
            
        } 
        catch(NoSuchAlgorithmException | InvalidKeySpecException e)
            {
                throw new RuntimeException(e);
            }
    }
     
    public static byte[] genSalt()
    {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        
        return salt;
    }
    
    public void checkSalt()
    {
         
            
        
        
    }
    
    */
   
}
