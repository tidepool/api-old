package com.tidepool.api.email;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpleemail.AWSJavaMailTransport;
import com.tidepool.api.model.Account;
import com.tidepool.api.model.Invite;

@Repository
public class EmailController {

	private String accessKey;
	private Properties props;
	private PropertiesCredentials credentials = null;
	
	@Autowired
	public VelocityEngine velocityEngine;
	
	@Value("${tidepool.invite.url}") 
	private String inviteUrl;
	
	public EmailController() {
		
		/*
		 * Important: Be sure to fill in your AWS access credentials in the
		 * AwsCredentials.properties file before you try to run this sample.
		 * http://aws.amazon.com/security-credentials
		 */		
		
		try {
			credentials = new PropertiesCredentials(EmailController.class.getResourceAsStream("AwsCredentials.properties"));
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		
	   /*
		* Get JavaMail Properties and Setup Session
		*/
		props = new Properties();

	   /*
	    * Setup JavaMail to use the Amazon Simple Email Service by
	    * specifying the "aws" protocol.
		*/
		props.setProperty("mail.transport.protocol", "aws");

		/*
		 * Setting mail.aws.user and mail.aws.password are optional. Setting
		 * these will allow you to send mail using the static transport send()
		 * convince method.  It will also allow you to call connect() with no
		 * parameters. Otherwise, a user name and password must be specified
		 * in connect.
		 */
		props.setProperty("mail.aws.user", credentials.getAWSAccessKeyId());
		props.setProperty("mail.aws.password", credentials.getAWSSecretKey());
	}
	
	
	public void sendEmail(String to, String from, String subject, String message) throws IOException {

		Session session = Session.getInstance(props);

		try {
			// Create a new Message
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(subject);
			msg.setText(message);
			msg.saveChanges();

			// Reuse one Transport object for sending all your messages
			// for better performance
			Transport t = new AWSJavaMailTransport(session, null);
			
			t.connect();
			t.sendMessage(msg, null);

			// Close your transport when you're completely done sending
			// all your messages
			t.close();

		} catch (AddressException e) {
			e.printStackTrace();
			System.out.println("Caught an AddressException, which means one or more of your "
					+ "addresses are improperly formatted.");
		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("Caught a MessagingException, which means that there was a "
					+ "problem sending your message to Amazon's E-mail Service check the "
					+ "stack trace for more information.");
		}
	}
	
	
	public void sendMultipartEmail(String to, String from, String subject, String messageText) throws IOException {

		Session session = Session.getInstance(props);

		try {
			// Create a new Message
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setSubject(subject);
			
			MimeMultipart mp = new MimeMultipart();
            BodyPart part = new MimeBodyPart();
            part.setContent(messageText, "text/html");
            mp.addBodyPart(part);
            msg.setContent(mp);

			// Reuse one Transport object for sending all your messages
			// for better performance
			Transport t = new AWSJavaMailTransport(session, null);
			
			t.connect();
			t.sendMessage(msg, null);

			// Close your transport when you're completely done sending
			// all your messages
			t.close();

		} catch (AddressException e) {
			e.printStackTrace();
			System.out.println("Caught an AddressException, which means one or more of your "
					+ "addresses are improperly formatted.");
		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("Caught a MessagingException, which means that there was a "
					+ "problem sending your message to Amazon's E-mail Service check the "
					+ "stack trace for more information.");
		}
	}
	
	
	public void sendTestEmail(String name) {
		Map model = new HashMap();
        model.put("testString", name);	
		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "com/tidepool/api/email/test.vm", model);		
		try {
			sendMultipartEmail("josephshoop@gmail.com", "admin@tidepool.co", "test", text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void sendForgotPasswordEmail(Account account) {
		Map model = new HashMap();
		model.put("account", account);
		model.put("inviteURL", inviteUrl);
        
		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "com/tidepool/api/email/lost_password.vm", model);		
		try {
			sendMultipartEmail(account.getEmail(), "admin@tidepool.co", "Password reset", text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	public void sendInvitationEmail(Account inviteeAccount, Account inviter, Invite invite) {
		Map model = new HashMap();
        model.put("inviteeAccount", inviteeAccount);
        model.put("inviterAccount", inviter);
        model.put("invite", invite);
        model.put("inviteURL", inviteUrl);
		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "com/tidepool/api/email/invitation.vm", model);		
		try {
			sendMultipartEmail(inviteeAccount.getEmail(), "admin@tidepool.co", "Invitation", text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}