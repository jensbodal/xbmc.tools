package xbmc.tools;

/**
 *
 * @author jensb
 */
public interface Emailable {
   public void setToAddress(String email);
   public void setFromAddress(String email);
   public void setMessageSubject(String subject);
   public void setMessageBody(String messageBody);
}
