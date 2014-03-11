/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xbmc.tools;

import javax.mail.Authenticator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jensb
 */
public class EmailerTest {
    String toEmail = "java@mailinator.com";
    String fromEmail = "billgates@live.com";
    String messageSubject = "Amazing opportunity!!!!";
    String messageBody = "Do you want money?";
    Emailer emailer;
    
    public EmailerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        emailer = new Emailer();
        emailer.setToAddress(toEmail);
        emailer.setFromAddress(fromEmail);
        emailer.setMessageSubject(messageSubject);
        emailer.setMessageContext(messageBody);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testFail() {
        System.out.println("Testing failure to send email on port 251");
        emailer.sendEmail("true", "251", false);
        String expectedResult = "FAIL";
        String result = emailer.getStatus();
        assertEquals(expectedResult, result);
    }
    
    @Test
    public void testSuccess() {
        System.out.println("Testing failure to send email on port 25");
        emailer.sendEmail("true", "25", false);
        String expectedResult = "SUCCESS";
        String result = emailer.getStatus();
        assertEquals(expectedResult, result);
    }
    
    @Test
    public void testToAddress() {
        emailer.setToAddress("bill@mailinator.com");
        String result = emailer.getToAddress();
        assertNotNull(result);
        String resultFail = null;
        try {
            emailer.setToAddress("dave2gmail.com");
        }
        catch (IllegalArgumentException e) {
            resultFail = emailer.getToAddress();
        }
        assertNull(resultFail);
    }
    


}
