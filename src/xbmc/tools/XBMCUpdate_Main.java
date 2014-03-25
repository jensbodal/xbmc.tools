/*
 * XBMCUpdate_Main.java
 */
package xbmc.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Jens Bodal
 */
public class XBMCUpdate_Main {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "-update":
                    if (args.length == 5) {
                        updateXBMC(args[1], args[2], args[3], args[4]);
                    }
                    else {
                        printHelp();
                    }
                    break;
                case "-utor":
                    if (args.length == 5) {
                        updateFromUtorrent(args[1], args[2], args[3], args[4]);
                    }
                    else {
                        System.out.println("Invalid number of arguments: " + args.length);
                    }
                    break;
                case "-encrypt":
                    if (args.length == 3) {
                        encryptText(args[1], args[2]);
                    }
                    break;
                case "-decrypt":
                    if (args.length == 3) {
                        decryptText(args[1], args[2]);
                    }
                    break;
                default:
                    System.out.println("Command must begin with -update or -utor");
                    break;
            }
        }
    }

    private static void updateXBMC(String username, String password, String host, String port) {
        XBMCUpdate update = new XBMCUpdate(username, password, host, port);
        System.out.printf("Updating: %s %n", update.getURL());
        update.sendUpdateRequest();
    }

    private static void printHelp() {
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

    private static void updateFromUtorrent(
            String logFilePath, String label, String title, String secret) {
        XBMCUpdate updateXios
                = new XBMCUpdate("xbmc", "xbmc", "10.0.0.151", "8080");
        XBMCUpdate updateJens
                = new XBMCUpdate("xbmc", "xbmc", "127.0.0.1", "8082");
        updateXios.sendUpdateRequest();
        updateJens.sendUpdateRequest();

        DownloadLog log = new DownloadLog(label, title);
        String toEmail = null;
        String password = null;
        String userHome = System.getProperty("user.home");
        ReadUserXML reader;
        try {
            reader = new ReadUserXML(userHome + "/xbmcEmail.xml");
            reader.parseXML();
            EncryptText encrypter = new EncryptText(secret);
            toEmail = encrypter.decryptString(reader.getUsername());
            password = encrypter.decryptString(reader.getPassword());

        }
        catch (FileNotFoundException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
        Emailer emailer = new Emailer();
        emailer.setCredentials(toEmail, password);
        String fromEmail = "utorrent@gmail.com";
        emailer.setToAddress(toEmail);
        emailer.setFromAddress(fromEmail);
        emailer.setMessageSubject("New Torrent Downloaded");
        emailer.setMessageBody(log.toString());
        emailer.sendEmail("true", "smtp.gmail.com", "587", false);

        File logFile = new File(logFilePath);
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

    private static void encryptText(String text, String secret) {
        EncryptText encrypter = new EncryptText(secret);
        System.out.println(encrypter.encryptString(text));
    }

    private static void decryptText(String text, String secret) {
        EncryptText encrypter = new EncryptText(secret);
        System.out.println(encrypter.decryptString(text));
    }
}
