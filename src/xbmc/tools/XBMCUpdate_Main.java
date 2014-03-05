/*
 * XBMCUpdate_Main.java
 */ 

package xbmc.tools;

/**
 *
 * @author Jens Bodal
 */

public class XBMCUpdate_Main {
    
    public static void main(String[] args) {
        if (args.length == 4) {
            String username = args[0];
            String password = args[1];
            String host = args[2];
            String port = args[3];
            XBMCUpdate update = new XBMCUpdate(username, password, host, port);
            System.out.println(update.getURL());
            update.sendUpdateRequest();
        }
    }
}