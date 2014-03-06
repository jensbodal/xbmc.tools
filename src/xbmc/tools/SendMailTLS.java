/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xbmc.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMailTLS {
 
	public static void main(String[] args) {
 
		
		String password_temp = null;
                String username_temp = null;
                String userHome = System.getProperty("user.home");

                StringBuilder gmailPass = new StringBuilder();
                gmailPass.append(userHome);
                gmailPass.append("/gmailpassword.txt");
                try (BufferedReader passReader = new BufferedReader(
                        new FileReader(gmailPass.toString()))) {
                    password_temp = passReader.readLine();
                }
                catch (IOException e) {
                    System.out.println(e);
                }
                
                StringBuilder gmailUser = new StringBuilder();
                gmailUser.append(userHome);
                gmailUser.append("/gmailusername.txt");
                try (BufferedReader userReader = new BufferedReader(
                        new FileReader(gmailUser.toString()))) {
                    username_temp = userReader.readLine();
                }
                catch (IOException e) {
                    System.out.println(e);
                }

                
                final String username = username_temp;
                final String password = password_temp;
                
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
				InternetAddress.parse(username + "@gmail.com"));
			message.setSubject("New Torrent Downloaded");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}

