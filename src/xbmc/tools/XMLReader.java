package xbmc.tools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author jensb
 */
public class XMLReader {
    
    XMLInputFactory factory;
    FileInputStream stream;
    File xmlFile;
    XMLStreamReader reader;
    XMLEventReader eventReader;
    
        
    public XMLReader() throws FileNotFoundException, XMLStreamException {
        initXMLReader();
    }
    
    private void initXMLReader() throws FileNotFoundException, XMLStreamException {
        
            factory  = XMLInputFactory.newInstance();
            factory.setProperty(XMLInputFactory.IS_COALESCING, true);
            
            xmlFile = new File("sample.xml");
            stream = new FileInputStream(xmlFile);
            reader = factory.createXMLStreamReader(stream);
            eventReader = factory.createXMLEventReader(stream);
            
            while(eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                XMLEvent nextEvent = eventReader.peek();
                
                if (event.isStartElement()) {
                    System.out.print("Name: " + event.asStartElement().getName());
                    if (nextEvent.isCharacters()) {
                        if (nextEvent.asCharacters().toString().trim().length() > 0) {
                            System.out.print(" Value: ");
                            System.out.println(eventReader.peek().asCharacters().toString());
                        }
                        else {
                            System.out.println();
                        }
                    }
                }
            }
    }
}
