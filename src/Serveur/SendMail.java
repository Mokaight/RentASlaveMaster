package Serveur;

import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendMail {
Session session;
	SendMail () {
	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", "localhost");
		//props.put("mail.transport.protocol","smtp");
		//props.put("mail.smtp.host","localhost");
		session = Session.getInstance(properties);
	}
	/**
	 * <b> Permet d'envoyer un e-mail avec le packet sendmail.jar</b>
	 * 
	 * @param envoie,alias,recipient,subject,text
	 * 		
	 * */
	public void sendMail(String envoie, String alias, String recipient, String subject, String text) throws UnsupportedEncodingException {
		MimeMessage message = new MimeMessage(session);
		InternetAddress from = null, to = null;
		from = new InternetAddress(envoie, alias);
		
		try {
			message.setFrom(from);
		} catch (MessagingException me) {}
		
		try {
			to = new InternetAddress(recipient);
		} catch  (AddressException e) {System.err.println("Adresse destination incorrecte");}
		
		try {
			message.setRecipient(Message.RecipientType.TO, to);
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
		} catch (MessagingException me) {System.err.println(me);}
	}
}