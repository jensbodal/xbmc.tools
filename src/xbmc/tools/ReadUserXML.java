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
public class ReadUserXML {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private XMLInputFactory factory;
    private FileInputStream inputStream;
    private File xmlFile;
    private XMLStreamReader reader;
    private XMLEventReader eventReader;
    private String username;
    private String password;

    public ReadUserXML(String filename) {
        setXmlFile(new File(filename));
    }

    public void parseXML() throws FileNotFoundException, XMLStreamException {
        factory = XMLInputFactory.newInstance();
        /**
         * return all characters as single event http://stackoverflow.com/questions/11880742/
         */
        factory.setProperty(XMLInputFactory.IS_COALESCING, true);
        inputStream = new FileInputStream(getXmlFile());
        reader = factory.createXMLStreamReader(inputStream);
        eventReader = factory.createXMLEventReader(inputStream);

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            XMLEvent nextEvent = eventReader.peek();

            if (event.isStartElement()) {
                String startElement = event.asStartElement().getName().toString();
                switch (startElement) {
                    case ReadUserXML.USERNAME:
                        setUsername(nextEvent.asCharacters().toString());
                        break;
                    case ReadUserXML.PASSWORD:
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
