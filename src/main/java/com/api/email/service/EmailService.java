package com.api.email.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.api.email.entities.Email;

@Service
public class EmailService {
	
	@Value("${smtp.host}")
	private String host;
	
	@Value("${smtp.port}")
	private String port;
	
	@Value("${smtp.ssl.enable}")
	private boolean sslEnable;
	
	@Value("${smtp.auth}")
	private boolean smtpAuth;
	
	@Value("${password.authenticator.emailId}")
	private String authenticatedEmail;
	
	@Value("${password.authenticator.password}")
	private String authenticatedPassword;
	
	public boolean sendEmail(Email email) {
	
		boolean emailSent = false;
	    // Get system properties
	    Properties properties = System.getProperties();
	
	    // Setup mail server
	    properties.put("mail.smtp.host", host);
	    properties.put("mail.smtp.port", port);
	    properties.put("mail.smtp.ssl.enable", sslEnable);
	    properties.put("mail.smtp.auth", smtpAuth);
	
	    // Get the default Session object.
	    Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(authenticatedEmail, authenticatedPassword);
			}
	    	
	    });
	    
	    try {
	        // Create a default MimeMessage object.
	        MimeMessage message = new MimeMessage(session);
	
	        // Set From: header field of the header.
	        message.setFrom(new InternetAddress(authenticatedEmail));
	
	        // Set To: header field of the header.
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
	
	        // Set Subject: header field
	        message.setSubject(email.getSubject());
	
	        // Now set the actual message
	        message.setText(email.getContent());
	
	        // Send message
	        Transport.send(message);
	        emailSent = true;
	     } catch (MessagingException mex) {
	        mex.printStackTrace();
	     }
	    return emailSent;
	}
}
