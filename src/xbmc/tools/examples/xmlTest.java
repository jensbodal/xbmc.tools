package xbmc.tools.examples;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import javax.xml.stream.XMLStreamException;
import xbmc.tools.ReadUsernameXML;

/**
 *
 * @author jensb
 */
public class xmlTest {
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        ReadUsernameXML reader = new ReadUsernameXML("sample.xml"); 
      
        System.out.println(reader.getUsername()+":"+reader.getPassword());
    }
}
