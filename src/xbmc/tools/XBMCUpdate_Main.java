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
            StringBuilder invalidParams = new StringBuilder();
            invalidParams.append("Please run this program as follows: \n");
            invalidParams.append("\tjava -jar XBMC_Tools.jar ");
            invalidParams.append("\"username\" ");
            invalidParams.append("\"password\" ");
            invalidParams.append("\"host\" ");
            invalidParams.append("\"port\"\n");
            invalidParams.append("\te.g. java -jar XBMC_Tools.jar ");
            invalidParams.append("\"xbmc\" ");
            invalidParams.append("\"xbmc\" ");
            invalidParams.append("\"10.0.0.151\" ");
            invalidParams.append("\"8080\" ");
            System.out.println(invalidParams.toString());
        }
    }
}