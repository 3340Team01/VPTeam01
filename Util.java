/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * @author E
 */
public class Util 
{
    /**
    *
    * @author Michelle
    */
    
    
    public String []  encrypt(String pass, String salty) throws Exception
    {   
        String passSalt[] = new String[2]; 
        String salt=salty;
        
        System.out.println("this is original pass"+pass);
        if(salt == null)
        {
            // Call genSalt()
            salt =this.genSalt();
            System.out.println("this is salt alone-->:  "+salt);
        }
        
        
            // Apply the passed salt to the passed password, then encrypt
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey key = keyGenerator.generateKey();
            Cipher cipher = Cipher.getInstance("AES");
            String saltedString = pass + salt;
                System.out.println("this is pass and salt---->> "+saltedString);
            // Encryption
            byte[] plainTextByte = saltedString.getBytes();
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedByte = cipher.doFinal(plainTextByte);
            Base64.Encoder encoder = Base64.getEncoder();
            String passEncrypt = encoder.encodeToString(encryptedByte);
            System.out.println("this is encrypted passandsalt---->"+passEncrypt);
            // String array with encrypted password and salt
            passSalt[0]=passEncrypt;
            passSalt[1]=salt;
            
        
    
        return passSalt;
    }
    
    String genSalt()
    {
        SecureRandom random = new SecureRandom();
        String salt=null;
        byte[] saltByte = new byte[8];
        random.nextBytes(saltByte);
      
        try 
        {
           salt= new String(saltByte,"UTF-8");
           
           
        } 
        catch (UnsupportedEncodingException ex) 
        {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return salt;
    }
}
