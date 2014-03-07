package xbmc.tools.tests;

import xbmc.tools.Emailer;
import xbmc.tools.LocalFile;

/**
 * For this test to work you need to create two local files under your
 * user home directory called gmailusername.txt and gmailpassword.txt which 
 * contain both your username and password.
 * 
 * <b>Note: Your username does not include the domain, ie test@gmail.com has
 * a username of test</b>
 * <br />
 * <br />
 * This will send an example email to example@mailinator.com, anyone can
 * check this inbox at www.mailinator.com
 * 
 * @author jensb
 */
public class GmailTest {
    
    public static void main(String[] args) {
        Emailer emailer = new Emailer();
        String username = LocalFile.getString("/gmailusername.txt");
        String password = LocalFile.getString("/gmailpassword.txt");
        emailer.setCredentials(username, password);
        emailer.setFromAddress(username + "@gmail.com");
        emailer.setToAddress("example@mailinator.com");
        emailer.setMessageSubject("You sent an email!");
        emailer.setMessageContext("This is the body of the email");
        emailer.sendEmail("true", "smtp.gmail.com", "587" , false);
    }
    
}
