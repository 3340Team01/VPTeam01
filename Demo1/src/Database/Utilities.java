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
public class Utilities 
{
    /**
    *
    * @author Michelle
    */
    public static byte[] genSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        
        return salt;
    }
    
    public static byte[] hash(String password, byte[] salt){        
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 1000, 256);
            SecretKey key = skf.generateSecret(spec);
            byte[] hash = key.getEncoded();
            
            return hash;
        } catch(NoSuchAlgorithmException | InvalidKeySpecException e){
            throw new RuntimeException(e);
        }
    }
}
