package xbmc.tools.examples;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 *
 * @author jensb
 */
public class TestLogging {
    private static final Logger LOGGER = LogManager.getLogger("GLOBAL");
    
    public static void main(String[] args) {
        
            LOGGER.debug("debug");
            LOGGER.error("erroring");
            LOGGER.info("INFO: HI");
            LOGGER.fatal("tracing");
            test();
            
        }
    public static void test() {
        LOGGER.debug("What do I say?");
        try {
            throw new RuntimeException("OMG RUNTIME ERROR");
        }
        catch (RuntimeException e) {
            LOGGER.fatal(e, e);
        }
        
    }
}
