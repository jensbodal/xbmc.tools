package xbmc.tools.examples;

import java.io.FileNotFoundException;
import javax.xml.stream.XMLStreamException;
import xbmc.tools.EncryptText;
import xbmc.tools.ReadUsernameXML;

/**
 *
 * @author jensb
 */
public class xmlTest {

    public static void main(String[] args)
            throws FileNotFoundException, XMLStreamException {
        String userHome = System.getProperty("user.home");
        ReadUsernameXML reader = new ReadUsernameXML(userHome + "/xbmcEmail.xml");
        reader.parseXML();
        EncryptText encrypter = new EncryptText();
        String username = encrypter.decryptString(reader.getUsername());
        String password = encrypter.decryptString(reader.getPassword());
        System.out.println(username);
        System.out.println(password);
    }
}
