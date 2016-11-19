/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

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
    
    
    void encrypt(String pass,String salt) throws Exception
    {
        if(salt==null)
        {
            // Call genSalt()
            this.genSalt();
        }
        else
        {
            // Apply the passed salt to the passed password, then encrypt
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            key = keyGenerator.generateKey();
            cipher = Cipher.getInstance("AES");
            String saltedString = pass + salt;
                
            // Encryption
            byte[] plainTextByte = saltedString.getBytes();
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedByte = cipher.doFinal(plainTextByte);
            Base64.Encoder encoder = Base64.getEncoder();
            String encryptedText = encoder.encodeToString(encryptedByte);
            
            // String array with encrypted password and salt
            String passSaltInfo[] = {encryptedText, salt};
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
        // Generates a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
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
