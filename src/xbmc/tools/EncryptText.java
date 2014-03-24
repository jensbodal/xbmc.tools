package xbmc.tools;

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
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    }
    
    public EncryptText(byte[] iv) {
        try {
            SECRET = SECRET_TEXT.getBytes(ENCODING);
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(ENCODING, e);
        }
        key = new SecretKeySpec(SECRET, ALGORITHM);
        initializationVector = iv;
        ivspec = new IvParameterSpec(initializationVector);
        
        try {
            encryptCipher = Cipher.getInstance(CIPHERTYPE);
            decryptCipher = Cipher.getInstance(CIPHERTYPE);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalArgumentException(e);
        }
        
        try {
            encryptCipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
            decryptCipher.init(Cipher.DECRYPT_MODE, key, ivspec);
        }
        catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        }
        catch (InvalidAlgorithmParameterException e) {
            StringBuilder error = new StringBuilder();
            error.append("iv: {");
            for (byte b:initializationVector) {
                error.append("\"");
                error.append(b);
                error.append("\",");
            }
            error.replace(error.length()-1, error.length(), "");
            error.append("}");
            throw new IllegalArgumentException(error.toString(), e);
        }
    }
    
    
    public String encryptString(String string) {
        byte[] inputBytes;
        byte[] encryptedData = null;
        try {
            inputBytes = string.getBytes(ENCODING);
            encryptedData = encryptCipher.doFinal(inputBytes);
        }
        catch (UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalArgumentException(e);
        }
        return String.valueOf(new BigInteger(encryptedData));
    }

    public String decryptString(String string) {
        byte[] decryptedData;
        BigInteger value = new BigInteger(string);
        try {
            decryptedData = decryptCipher.doFinal(value.toByteArray());
        }
        catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalArgumentException(e);
        }
        return new String(decryptedData);        
    }
}
