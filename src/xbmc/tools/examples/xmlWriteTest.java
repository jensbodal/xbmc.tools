/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xbmc.tools.examples;

import java.io.FileNotFoundException;
import javax.xml.stream.XMLStreamException;
import xbmc.tools.ReadUserXML;
import xbmc.tools.WriteUserXML;

/**
 *
 * @author jensb
 */
public class xmlWriteTest {

    public static void main(String[] args)
            throws FileNotFoundException, XMLStreamException {
        String userHome = System.getProperty("user.home");
        String path = (userHome + "/XBMCwrite.xml");
        WriteUserXML writer = new WriteUserXML(path);
        writer.WriteUserInfo("jens", "password");
        
        ReadUserXML reader = new ReadUserXML(path);
        reader.parseXML();
        System.out.printf("Username: %s | Password: %s%n", reader.getUsername(), reader.getPassword());
    }
}
