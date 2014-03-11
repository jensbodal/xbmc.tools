package xbmc.tools.examples;

import xbmc.tools.Emailer;

/**
 *
 * @author jensb
 */
public class UnecryptedEmailTest {

    /**
     *
     * @param args
     */
    
    public static void main(String[] args) {
        Emailer emailer = new Emailer();
        String toEmail = "java@mailinator.com";
        String fromEmail = "billgates@live.com";
        emailer.setToAddress(toEmail);
        emailer.setFromAddress(fromEmail);
        emailer.setMessageSubject("Amazing opportunity!!!!");
        emailer.setMessageContext("Do you want money?");
        emailer.sendEmail("true", "25", false);
    }
}
