package xbmc.tools.examples;

import java.io.FileNotFoundException;
import javax.xml.stream.XMLStreamException;
import xbmc.tools.EncryptText;
import xbmc.tools.ReadUserXML;

/**
 *
 * @author jensb
 */
public class xmlTest {

    public static void main(String[] args)
            throws FileNotFoundException, XMLStreamException {
        String userHome = System.getProperty("user.home");
        ReadUserXML reader = new ReadUserXML(userHome + "/xbmcEmail.xml");
        reader.parseXML();
        byte iv[] = new byte[] {1, 4, 3, 4, 10, 125, 64, 105, 13, 17, 10, 1, 7, 13, 0, 12};
        EncryptText encrypter = new EncryptText("baamx2maeaacxbmc", iv);
        String username = encrypter.decryptString(reader.getUsername());
        String password = encrypter.decryptString(reader.getPassword());
        System.out.println(username);
        System.out.println(password);
    }
}
