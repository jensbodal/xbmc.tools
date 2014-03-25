/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xbmc.tools;

import java.io.File;
import java.io.FileInputStream;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author jensb
 */
public class WriteUserXML {
    private final String USERNAME = "username";
    private final String PASSWORD = "password";
   
    XMLInputFactory factory;
    FileInputStream stream;
    private File xmlFile;
    XMLStreamReader reader;
    XMLEventReader eventReader;
    private String username;
    private String password;

    public WriteUserXML(String filename) {
        setXmlFile(new File(filename));
    }
    
    /**
     * @return the xmlFile
     */
    public File getXmlFile() {
        return xmlFile;
    }

    /**
     * @param xmlFile the xmlFile to set
     */
    private void setXmlFile(File xmlFile) {
        this.xmlFile = xmlFile;
    }
}
