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
        if (args.length > 0) {
            if (args[0].equals("-u")) {
                if (args.length == 5) {
                    String username = args[1];
                    String password = args[2];
                    String host = args[3];
                    String port = args[4];
                    XBMCUpdate update = new XBMCUpdate(username, password, host, port);
                    System.out.printf("Updating: %s %n", update.getURL());
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

    }
}
