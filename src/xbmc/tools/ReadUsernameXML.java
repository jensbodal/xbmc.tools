package xbmc.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author jensb
 */
public class ReadUsernameXML {

    private final String USERNAME = "username";
    private final String PASSWORD = "password";
   
    XMLInputFactory factory;
    FileInputStream stream;
    private File xmlFile;
    XMLStreamReader reader;
    XMLEventReader eventReader;
    private String username;
    private String password;

    public ReadUsernameXML(String filename) {
        setXmlFile(new File(filename));
    }

    public void parseXML() throws FileNotFoundException, XMLStreamException {
        factory = XMLInputFactory.newInstance();
        /**
         * return all characters as single event http://stackoverflow.com/questions/11880742/
         */
        factory.setProperty(XMLInputFactory.IS_COALESCING, true);
        stream = new FileInputStream(getXmlFile());
        reader = factory.createXMLStreamReader(stream);
        eventReader = factory.createXMLEventReader(stream);

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            XMLEvent nextEvent = eventReader.peek();

            if (event.isStartElement()) {
                String startElement = event.asStartElement().getName().toString();
                switch (startElement) {
                    case USERNAME:
                        setUsername(nextEvent.asCharacters().toString());
                        break;
                    case PASSWORD:
                        setPassword(nextEvent.asCharacters().toString());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    private void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    private void setPassword(String password) {
        this.password = password;
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
