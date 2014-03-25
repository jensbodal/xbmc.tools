package xbmc.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author jensb
 */
public class WriteUserXML {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String USER = "user";
    private static final String XMLTAG = "1.0\" encoding=\"UTF-8";
    private FileOutputStream outputStream;
    private XMLOutputFactory factory;
    private XMLStreamWriter writer;
    private File xmlFile;

    public WriteUserXML(String filename) {
        setXmlFile(new File(filename));
    }

    public void WriteUserInfo(String username, String password)
            throws FileNotFoundException, XMLStreamException {
        
        outputStream = new FileOutputStream(getXmlFile());
        factory = XMLOutputFactory.newInstance();
        writer = factory.createXMLStreamWriter(outputStream);
        writer.writeStartDocument(XMLTAG);
        writer.writeCharacters("\n");
        writer.writeStartElement(USER);
        writer.writeCharacters("\n\t");
        writer.writeStartElement(USERNAME);
        writer.writeCharacters(username);
        writer.writeEndElement();
        writer.writeCharacters("\n\t");
        writer.writeStartElement(PASSWORD);
        writer.writeCharacters(password);
        writer.writeEndElement();
        writer.writeCharacters("\n");
        writer.writeEndDocument();
        writer.close();
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
