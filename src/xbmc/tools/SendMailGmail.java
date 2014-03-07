/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xbmc.tools;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author jensb
 * @deprecated
 */
@Deprecated
public class SendMailGmail {

    private String password;
    private String username;
    private String toAddress;
    private String torrentLog;

    /**
     *
     * @param toAddress
     * @param torrentLog
     */
    public SendMailGmail(String toAddress, String torrentLog) {
        setToAddress(toAddress);
        setTorrentLog(torrentLog);
        setPassword(LocalFile.getString("/gmailpassword.txt"));
        setUsername(LocalFile.getString("/gmailusername.txt"));
    }

    /**
     *
     */
    public void sendEmail() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username + "@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toAddress));
            message.setSubject("New Torrent Downloaded");
            message.setText(torrentLog);

            Transport.send(message);

            System.out.println("Done");

        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void setPassword(String password) {
        this.password = password;
    }
    
    private void setUsername(String username) {
        this.username = username;
    }
    
    private void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
    
    private void setTorrentLog(String torrentLog) {
        this.torrentLog = torrentLog;
    }
    

}

