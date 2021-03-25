package com.bookspot.service;
import java.io.File;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	//this is responsible to send email..
	public static boolean sendEmail(String message, String subject, String to) {
	    boolean flag = false;
		String from = "bookSpot121@gmail.com";
		//Variable for gmail
		String host="smtp.gmail.com";
		//get the system properties
		Properties properties = System.getProperties();
		System.out.println("PROPERTIES "+properties);
		//setting important information to properties object
		//host set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable","true");
		properties.put("mail.smtp.auth","true");
		//Step 1: to get the session object..
		Session session=Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {				
				return new PasswordAuthentication("bookSpot121@gmail.com", "Reading@12345");
			}
		});
		session.setDebug(true);
		//Step 2 : compose the message [text,multi media]
		MimeMessage m = new MimeMessage(session);
		try {
		//from email
		m.setFrom(from);
		//adding recipient to message
		m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		//adding subject to message
		m.setSubject(subject);
		//adding text to message
		m.setText(message);
		//send 
		//Step 3 : send the message using Transport class
		Transport.send(m);
		flag = true;
		System.out.println("Sent success...................");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}