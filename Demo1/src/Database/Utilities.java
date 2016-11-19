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
    
    void encrypt(String pass, String salt) throws Exception
    {
        if(salt == null)
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
    }
    
    void genSalt()
    {
        // Generates a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
    }
}
