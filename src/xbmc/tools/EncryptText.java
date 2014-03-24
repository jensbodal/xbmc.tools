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
            {1, 4, 3, 4, 10, 125, 64, 105, 13, 17, 10, 1, 7, 13, 0, 12});
    }
    
    public EncryptText(byte[] iv) throws RuntimeException {
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
        catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            //throw new RuntimeException(e);
            throw new IllegalArgumentException(e);
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
