package xbmc.tools;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import org.apache.commons.net.telnet.TelnetClient;

/**
 *
 * @author jensb
 */
public class Emailer {

    
    private String password;
    private String username;
    private String toAddress;
    private String fromAddress;
    private String messageSubject;
    private String messageContext;
    private String smtpHost;
    private String tls;
    private String port;
    private boolean authenticationEnabled = false;
    private STATUS status = STATUS.FAIL;

    /**
     * Creates an Emailer object, there are no arguments to pass
     */
    public Emailer() {

    }

    /**
     * Grabs the username and password from text files stored in the user's home
     * directory
     */
    public void setCredentials() {
        setPassword(LocalFile.getString("/gmailpassword.txt"));
        setUsername(LocalFile.getString("/gmailusername.txt"));
        this.authenticationEnabled = true;
    }

    /**
     *
     * @param username user email username (do not include domain)
     * @param password user email password
     */
    public void setCredentials(String username, String password) {
        setUsername(username);
        setPassword(password);
        this.authenticationEnabled = true;
    }

    /**
     *
     * @return an authenticator object which sets the username and password
     */
    public Authenticator enableAuthentication() {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getUsername(), getPassword());
            }

            @Override
            public String toString() {
                return "Custom authenticator with user/pass";
            }
        };
    }

    /**
     * <b>NOTE</b> this will likely not work if you don't have a business class
     * ISP. Most ISPs disable sending unauthenticated email on port 25
     *
     * @return authenticator object if no authenticaion desired
     */
    public Authenticator disableAuthentication() {
        return new Authenticator() {
        };
    }

    /**
     * These need to be passed as implicit strings as passing booleans did not
     * work
     *
     * @param tls expects either "true" or "false" whether to use TLS
     * @param port the port to use (e.g. "587")
     * @param tryAll boolean value which specifies that you want to iterate
     * through all MX servers until one works or they all fail. Most would be
     * satisfied with setting to false
     */
    public void sendEmail(
            String tls, String port, boolean tryAll) {
        String[] smtp = determineSMTP(getToAddress());
        sendEmail(tls, smtp[0], port, tryAll);
    }

    /**
     * These need to be passed as implicit strings as passing booleans did not
     * work
     *
     * @param tls expects either "true" or "false" whether to use TLS
     * @param smtpHost e.g. "smtp.google.com"
     * @param port the port to use (e.g. "587")
     * @param tryAll boolean value which specifies that you want to iterate
     * through all MX servers until one works or they all fail. Most would be
     * satisfied with setting to false
     * @throws javax.mail.MessagingException
     */
    public void sendEmail(
            String tls, String smtpHost, String port, boolean tryAll) {
        setTLS(tls);
        setSmtpHost(smtpHost);
        setPort(port);
        String[] hosts;

        if (tryAll) {
            String[] generate_SMTP_list = determineSMTP(getToAddress());
            hosts = generate_SMTP_list;
        }
        else {
            hosts = new String[]{getSmtpHost()};
        }

        for (String host : hosts) {
            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", tls);
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            Authenticator authenticator;
            if (authenticationEnabled) {
                authenticator = enableAuthentication();
                props.put("mail.smtp.auth", "true");
            }
            else {
                authenticator = disableAuthentication();
                props.put("mail.smtp.auth", "false");
            }
            Session session = Session.getInstance(props, authenticator);

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(getFromAddress()));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(getToAddress()));
                message.setSubject(getMessageSubject());
                message.setText(getMessageContext());
                Transport.send(message);
                System.out.println("Sent");
                status = STATUS.SUCCESS;
                break;
            }
            catch (MessagingException e) {
                status = STATUS.FAIL;
                System.out.print("Please try another SMTP address: ");
                System.out.println(this.getSmtpHost());
                System.out.println(e);
            }
        }

    }

    /**
     * This will take an email address then parse the domain to search for SMTP
     * servers
     *
     * @param emailAddress email address to search for smtp servers on
     * @return an array of SMTP servers for the domain
     */
    public String[] determineSMTP(String emailAddress) {
        String domain = parseDomain(emailAddress);
        InitialDirContext context;
        Attributes attributes;
        Attribute attributeMX;
        String[][] pvhn = null;
        String[] sortedHostNames = null;

        try {
            context = new InitialDirContext();
            attributes = context
                    .getAttributes("dns:/" + domain, new String[]{"MX"});
            attributeMX = attributes.get("MX");
            if (attributeMX == null) {
                return (new String[]{domain});
            }
            // split MX RRs into Preference Values(pvhn[0]) and Host Names(pvhn[1])
            pvhn = new String[attributeMX.size()][2];
            for (int i = 0; i < attributeMX.size(); i++) {
                pvhn[i] = ("" + attributeMX.get(i)).split("\\s+");
            }
        }
        catch (NamingException e) {
            System.out.println(e);
        }

        // sort the MX RRs by RR value (lower is preferred)
        Arrays.sort(pvhn, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return (Integer.parseInt(o1[0]) - Integer.parseInt(o2[0]));
            }
        });

        // put sorted host names in an array, get rid of any trailing '.' 
        if (pvhn != null) {
            sortedHostNames = new String[pvhn.length];
            for (int i = 0; i < pvhn.length; i++) {
                sortedHostNames[i] = pvhn[i][1].endsWith(".")
                        ? pvhn[i][1].substring(0, pvhn[i][1].length() - 1) : pvhn[i][1];
            }
        }
        return sortedHostNames;
    }

    private String parseDomain(String emailAddress) {
        int atLocation;
        if (emailAddress.contains("@")) {
            atLocation = emailAddress.indexOf("@");
        }
        else {
            throw new IllegalArgumentException("Not a valid email address");
        }
        return emailAddress.substring(atLocation + 1, emailAddress.length());
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private String getPassword() {
        return this.password;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private String getUsername() {
        return this.username;
    }

    /**
     *
     * @param toAddress email address to send an email to
     */
    public void setToAddress(String toAddress) {
        if (toAddress.length() > 0 && toAddress.contains("@")) {
            this.toAddress = toAddress;
        }
        else {
            this.toAddress = null;
            throw new IllegalArgumentException(
                    "Please check email address format");
        }
    }

    public String getToAddress() {
        return this.toAddress;
    }

    /**
     *
     * @param messageSubject message subject for email
     */
    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    /**
     *
     * @return the message subject for the email
     */
    public String getMessageSubject() {
        return this.messageSubject;
    }

    /**
     *
     * @param messageContext the message body for the email to send
     */
    public void setMessageContext(String messageContext) {
        this.messageContext = messageContext;
    }

    private String getMessageContext() {
        return this.messageContext;
    }

    /**
     *
     * @param fromAddress the address you want to show in the from field
     */
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    private String getFromAddress() {
        return this.fromAddress;
    }

    private void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    /**
     *
     * @return the SMTP host that has been specified
     */
    public String getSmtpHost() {
        return this.smtpHost;
    }

    private void setTLS(String tls) {
        this.tls = tls;
    }

    /**
     *
     * @return whether or not to use TLS, "true" or "false"
     */
    public String getTLS() {
        return this.tls;
    }

    private void setPort(String port) {
        this.port = port;
    }

    /**
     *
     * @return the port specified for the SMTP server, e.g. "587"
     */
    public String getPort() {
        return this.port;
    }

    public String getStatus() {
        return status.getStatus();
    }

    /**
     * 
     * @param domain domain to test port on
     * @param port port to test
     * @param timeout time in milliseconds before timeout
     * @return true if port is open
     */
    public boolean testPort(String domain, int port, int timeout) {
        TelnetClient client = new TelnetClient();
        client.setConnectTimeout(timeout);
        try {
            client.connect(domain, port);
            return true;
        }
        
        catch (IOException e) {
            return false;
        }

    }
    
    /**
     * if no timeoutspecified then this overloaded method will default to 3
     * seconds
     * @param domain domain to test port on
     * @param port port to test
     * @return true if port is open
     */
    public boolean testPort(String domain, int port) {
        return testPort(domain, port, 3000);
    }
    
 
    private enum STATUS {

        SUCCESS("SUCCESS", true),
        FAIL("FAIL", false);

        private String status;
        private boolean sent;

        private STATUS(String status, boolean sent) {
            this.status = status;
            this.sent = sent;
        }

        public String getStatus() {
            return status;
        }

        public boolean Sent() {
            return sent;
        }
    }

}
