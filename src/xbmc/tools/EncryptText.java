package xbmc.tools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author jensb
 */
public class EncryptText {

    private final String SECRET_TEXT = "baamx2maeaacxbmc";
    private final String CIPHERTYPE = "AES/CBC/PKCS5Padding";
    private final String ALGORITHM = "AES";
    private final String ENCODING = "UTF-8";
    private final byte[] SECRET;
    private final SecretKeySpec key;
    private final byte[] initializationVector;
    private final IvParameterSpec ivspec;
    
    private Cipher encryptCipher;
    private Cipher decryptCipher;
    
    public EncryptText() {
        this(new byte[]
            {1, 4, 3, 4, 10, 125, 64, 105, 13, 17, 10, 1, 7, 13, 0, 12});
    }
    
    public EncryptText(byte[] iv) {
        try {
            SECRET = SECRET_TEXT.getBytes(ENCODING);
        }
        catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException("ENCODING should be UTF-8"); 
        }
        key = new SecretKeySpec(SECRET, ALGORITHM);
        initializationVector = iv;
        ivspec = new IvParameterSpec(initializationVector);
        
        try {
            encryptCipher = Cipher.getInstance(CIPHERTYPE);
            decryptCipher = Cipher.getInstance(CIPHERTYPE);
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("CIPHERTYPE should be AES/CBC/PKCS5Padding");
        }
        catch (NoSuchPaddingException e) {
            System.out.println("Padding in CIPHERTYPE should be PKCS5Padding");
        }
        
        try {
            encryptCipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
            decryptCipher.init(Cipher.DECRYPT_MODE, key, ivspec);
        }
        catch (InvalidKeyException e) {
            System.out.println("key should be new SecretKeySpec(SECRET, ALGORITHM)");
        }
        catch (InvalidAlgorithmParameterException e) {
            System.out.println("InvalidAlgorithmParameterException");
        }
    }
    
    
    public String encryptString(String string) {
        byte[] inputBytes;
        byte[] encryptedData = null;
        try {
            inputBytes = string.getBytes(ENCODING);
            encryptedData = encryptCipher.doFinal(inputBytes);
        }
        catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException("UnsupportedEncodingException");
        }
        catch (IllegalBlockSizeException e) {
            throw new UnsupportedOperationException("IllegalBlockSizeException");
        }
        catch (BadPaddingException e) {
            throw new UnsupportedOperationException("BadPaddingException");
        }
        return String.valueOf(new BigInteger(encryptedData));
    }

    public String decryptString(String string) {
        byte[] decryptedData;
        BigInteger value = new BigInteger(string);
        try {
            decryptedData = decryptCipher.doFinal(value.toByteArray());
        }
        catch (IllegalBlockSizeException e) {
            throw new UnsupportedOperationException("IllegalBlockSizeException");
        }
        catch (BadPaddingException e) {
            throw new UnsupportedOperationException("BadPaddingException");
        }
        return new String(decryptedData);        
    }
}
