package com.tracking.utils;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class PasswordUtil {

	private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";
	
    public static String encrypt(String value) throws Exception
    {
        String encryptedValue64 = Base64.getEncoder().encodeToString(value.getBytes());
        return encryptedValue64;
               
    }
    
    public static String decrypt(String value) throws Exception
    {

    	byte [] decryptedValue64 =  Base64.getDecoder().decode(value);
        String decryptedValue = new String(decryptedValue64,"utf-8");
        return decryptedValue;
                
    }
    
    
    public static void main(String[] args) throws Exception {
    	String password = "tariq";
    	
    	String encrypted = encrypt(password);
    	System.out.println(encrypted);
    	
    	String decrypted = decrypt(encrypted);
    	System.out.println(decrypted);
    	
    }
    
}
