package xbmc.tools;

import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author jensb
 */
public class PortsTest {

    Emailer emailer = new Emailer();
    private final String testDomain = "portquiz.net";

    @Test
    public void testPort25() {
        boolean expectedResult = true;
        boolean result = false;
        result = emailer.testPort(testDomain, 25, 1500);
        assertEquals("Your ISP is likely blocking sending mail on outgoing "
                + "port 25", expectedResult, result);
    }

    @Test(timeout = 1000)
    public void testPort587() {
        boolean expectedResult = true;
        boolean result = emailer.testPort(testDomain, 587);
        assertEquals(expectedResult, result);
    }

    @Test(timeout = 1000)
    public void testPort81() {
        boolean expectedResult = true;
        boolean result = emailer.testPort(testDomain, 81);
        assertEquals(expectedResult, result);
    }

    @Test(timeout = 1000)
    public void gmailSMTP() {
        boolean expectedResult = true;
        boolean result = emailer.testPort("smtp.gmail.com", 587);
        assertEquals(expectedResult, result);
    }

    @Test(timeout = 1000)
    public void gmail25() {
        boolean expectedResult = true;
        boolean result = emailer.testPort("smtp.gmail.com", 25);
        assertEquals(expectedResult, result);
    }
}
