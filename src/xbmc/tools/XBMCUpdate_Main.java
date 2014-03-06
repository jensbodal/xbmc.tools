/*
 * XBMCUpdate_Main.java
 */
package xbmc.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Jens Bodal
 */
public class XBMCUpdate_Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("-update")) {
                if (args.length == 6) {
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
                    invalidParams.append("-update ");
                    invalidParams.append("\"username\" ");
                    invalidParams.append("\"password\" ");
                    invalidParams.append("\"host\" ");
                    invalidParams.append("\"port\"\n");
                    invalidParams.append("\te.g. java -jar XBMC_Tools.jar ");
                    invalidParams.append("-update ");
                    invalidParams.append("\"xbmc\" ");
                    invalidParams.append("\"xbmc\" ");
                    invalidParams.append("\"10.0.0.151\" ");
                    invalidParams.append("\"8080\" ");
                    System.out.println(invalidParams.toString());
                }
            }
            if (args[0].equals("-utor")) {
                XBMCUpdate updateXios = new XBMCUpdate("xbmc", "xbmc", "10.0.0.151", "8080");
                XBMCUpdate updateJens = new XBMCUpdate("xbmc", "xbmc", "10.0.0.220", "8082");
                updateXios.sendUpdateRequest();
                updateJens.sendUpdateRequest();
                String label = args[2];
                String title = args[3];
                DownloadLog log = new DownloadLog(label, title);
                File logFile = new File(args[1]);
                if (logFile.exists()) {
                    try (FileWriter writer = new FileWriter(logFile, true)) {
                        writer.write(log.toString());
                        writer.write("\n");
                        System.out.println("Added to log: \n\t" + log.toString());
                        
                    }
                    catch (IOException e) {
                        System.out.println(e);
                    }
                }
                else {
                    System.out.printf("Directory %s does not exist", logFile.getAbsolutePath());
                }

                

            }
        }
    }
}
