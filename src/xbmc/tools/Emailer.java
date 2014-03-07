package xbmc.tools;

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
    private boolean setFromAddress = false;

    public Emailer() {

    }

    public void setCredentials() {
        setPassword(LocalFile.getString("/gmailpassword.txt"));
        setUsername(LocalFile.getString("/gmailusername.txt"));
        this.authenticationEnabled = true;
    }

    public Authenticator enableAuthentication() {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
    }

    public Authenticator disableAuthentication() {
        return new Authenticator() {

        };
    }

    public void sendEmail(
            String tls, String port, boolean tryAll) {
        String[] smtp = determineSMTP(getToAddress());
        sendEmail(tls, smtp[0], port, tryAll);
    }

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
            props.put("mail.smtp.auth", "\"" + authenticationEnabled + "\"");
            props.put("mail.smtp.starttls.enable", tls);
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            Authenticator authenticator;
            if (authenticationEnabled) {
                authenticator = enableAuthentication();
            }
            else {
                authenticator = disableAuthentication();
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
                break;
            }
            catch (MessagingException e) {
                System.out.print("Please try another SMTP address: ");
                System.out.println(this.getSmtpHost());
                System.out.println(e);
            }
        }
    }

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
                sortedHostNames[i] = pvhn[i][1].endsWith(".") ? 
                  pvhn[i][1].substring(0, pvhn[i][1].length() - 1) : pvhn[i][1];
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

    private void setUsername(String username) {
        this.username = username;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    private String getToAddress() {
        return this.toAddress;
    }

    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }

    public String getMessageSubject() {
        return this.messageSubject;
    }

    public void setMessageContext(String messageContext) {
        this.messageContext = messageContext;
    }

    private String getMessageContext() {
        return this.messageContext;
    }

    public void setFromAddress(String fromAddress) {
        setFromAddress = true;
        if (!authenticationEnabled) {
            this.fromAddress = fromAddress;
        }

    }

    private String getFromAddress() {
        if (!setFromAddress) {
            return toAddress;
        }
        else {
            return this.fromAddress;
        }
    }

    private void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpHost() {
        return this.smtpHost;
    }

    private void setTLS(String tls) {
        this.tls = tls;
    }

    public String getTLS() {
        return this.tls;
    }

    private void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return this.port;
    }

}
