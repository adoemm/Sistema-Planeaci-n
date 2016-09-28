/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspread.core.util.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Emmanuel
 */
public class EDP {

    private static final String UNICODE_FORMAT = "UTF8";
    //public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    //public static final String ENCRYPTION_SCHEME = "DESEDE_ENCRYPTION_SCHEME";
    public static final String ENCRYPTION_SCHEME = "DESede";

    /**
     * Method To Encrypt The String
     */
    public static String encrypt(HttpSession session, String unencryptedString) {
        String encryptedString = null;
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_SCHEME);
            cipher.init(Cipher.ENCRYPT_MODE, (SecretKey) session.getAttribute("encryptedKey"));

            encryptedString = new String(Base64.encodeBase64(cipher.doFinal(unencryptedString.getBytes(UNICODE_FORMAT))), "UTF-8");
            //System.out.println("unencryptedString: "+unencryptedString +" = "+encryptedString);
            //String decryptedText = new String(Base64.decodeBase64(encryptedString), "UTF8");
            // System.out.println("decryptedText: "+decryptedText);           
            //decrypt(session,encryptedString);
            return encryptedString;

//            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
//            byte[] encryptedText = cipher.doFinal(plainText);
//            //BASE64Encoder base64encoder = new BASE64Encoder();
//            //encryptedString = base64encoder.encode(encryptedText);
//            //encryptedString = Base64.encodeBase64(encryptedText);  
//            //encryptedString = new String(Base64.decodeBase64(encryptedText));
//            byte[] encodedBytes = Base64.encodeBase64(unencryptedString.getBytes(UNICODE_FORMAT));
//            // Get encoded string
//            encryptedString = new String(encodedBytes);
//            String(utf8Bytes, "UTF8")
        } catch (Exception ex) {
            Logger.getLogger(EDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encryptedString;
    }
    
    public static String encrypt(String key, String unencryptedString) {
        String encryptedString = null;
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_SCHEME);
            cipher.init(Cipher.ENCRYPT_MODE, encryptedKey(key));

            encryptedString = new String(Base64.encodeBase64(cipher.doFinal(unencryptedString.getBytes(UNICODE_FORMAT))), "UTF-8");
            //System.out.println("unencryptedString: "+unencryptedString +" = "+encryptedString);
            //String decryptedText = new String(Base64.decodeBase64(encryptedString), "UTF8");
            // System.out.println("decryptedText: "+decryptedText);           
            //decrypt(session,encryptedString);
            return encryptedString;

//            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
//            byte[] encryptedText = cipher.doFinal(plainText);
//            //BASE64Encoder base64encoder = new BASE64Encoder();
//            //encryptedString = base64encoder.encode(encryptedText);
//            //encryptedString = Base64.encodeBase64(encryptedText);  
//            //encryptedString = new String(Base64.decodeBase64(encryptedText));
//            byte[] encodedBytes = Base64.encodeBase64(unencryptedString.getBytes(UNICODE_FORMAT));
//            // Get encoded string
//            encryptedString = new String(encodedBytes);
//            String(utf8Bytes, "UTF8")
        } catch (Exception ex) {
            Logger.getLogger(EDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encryptedString;
    }

    public static SecretKey encryptedKey(String encryptionKey) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        byte[] keyAsBytes = encryptionKey.getBytes(UNICODE_FORMAT);
        KeySpec keySpec = new DESedeKeySpec(keyAsBytes);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ENCRYPTION_SCHEME);
        SecretKey key = secretKeyFactory.generateSecret(keySpec);
        return key;
    }

    /**
     * Method To Decrypt An Ecrypted String
     */
    public static String decrypt(HttpSession session, String encryptedString) {
        String decryptedText = null;
        try {
            //Base64 base64decoder = new Base64();
            Cipher cipher = Cipher.getInstance(ENCRYPTION_SCHEME);
            cipher.init(Cipher.DECRYPT_MODE, (SecretKey) session.getAttribute("encryptedKey"));

            decryptedText = new String(cipher.doFinal(Base64.decodeBase64(encryptedString)), "UTF8");
            //System.out.println("decryptedText: " + decryptedText);

//            //BASE64Decoder base64decoder = new BASE64Decoder();
//            //byte[] encryptedText = base64decoder.decodeBuffer(encryptedString);
//            decryptedText = new String(Base64.decodeBase64(encryptedString));
//            //byte[] plainText = cipher.doFinal(encryptedText);
//            //decryptedText = bytes2String(plainText);
        } catch (Exception ex) {
            Logger.getLogger(EDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return decryptedText;
    }
    
    public static String decrypt(String key, String encryptedString) {
        String decryptedText = null;
        try {
            //Base64 base64decoder = new Base64();
            Cipher cipher = Cipher.getInstance(ENCRYPTION_SCHEME);
            cipher.init(Cipher.DECRYPT_MODE, encryptedKey(key));

            decryptedText = new String(cipher.doFinal(Base64.decodeBase64(encryptedString)), "UTF8");
            //System.out.println("decryptedText: " + decryptedText);

//            //BASE64Decoder base64decoder = new BASE64Decoder();
//            //byte[] encryptedText = base64decoder.decodeBuffer(encryptedString);
//            decryptedText = new String(Base64.decodeBase64(encryptedString));
//            //byte[] plainText = cipher.doFinal(encryptedText);
//            //decryptedText = bytes2String(plainText);
        } catch (Exception ex) {
            Logger.getLogger(EDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return decryptedText;
    }

    /**
     * Returns String From An Array Of Bytes
     */
    private static String bytes2String(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            stringBuffer.append((char) bytes[i]);
        }
        return stringBuffer.toString();
    }

    /**
     * Testing The DESede Encryption And Decryption Technique
     */
    public static void main(String args[]) throws Exception {
//        DESedeEncryption myEncryptor = new DESedeEncryption("fieldSecure123456789asdfg");
//
//        //String stringToEncrypt = "Hola Mundo";
//        String stringToEncrypt = "sApellidoPaterno";
//
//        String encrypted = myEncryptor.encrypt(stringToEncrypt);
//        String decrypted = myEncryptor.decrypt(encrypted);
//
//        System.out.println("String To Encrypt: " + stringToEncrypt);
//        System.out.println("Encrypted Value :" + encrypted);
//        System.out.println("Decrypted Value :" + decrypted);
    }
}
