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
        else {
            StringBuilder error = new StringBuilder();
            error.append("Please run this program as follows: \n");
            error.append("java -jar XBMC_Tools.jar ");
            error.append("\"username\" ");
            error.append("\"password\" ");
            error.append("\"host\" ");
            error.append("\"port\"\n");
            error.append("e.g. ");
            error.append("java -jar XBMC_Tools.jar ");
            error.append("\"xbmc\" ");
            error.append("\"xbmc\" ");
            error.append("\"10.0.0.151\" ");
            error.append("\"8080\" ");
            System.out.println(error.toString());
        }
    }
}