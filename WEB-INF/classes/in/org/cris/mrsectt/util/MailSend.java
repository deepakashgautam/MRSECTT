package in.org.cris.mrsectt.util;


import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletContext;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.*;
import java.io.*;


public class MailSend {
	static Logger log = LogManager.getLogger(MailSend.class);
	
	
	private static final String SMTP_HOST_NAME = "smtpout.railnet.gov.in";
	private static final String SMTP_AUTH_USER = "mrcell@rb.railnet.gov.in";
	private static final String SMTP_AUTH_PWD = "mrcell0987";
	
/*	private static final String SMTP_HOST_NAME = "172.16.1.206";
	private static final String SMTP_AUTH_USER = "suneel sambharia";
	private static final String SMTP_AUTH_PWD = "suneelsambharia";
*/	
	private static String emailMsgTxt="";
	//= "Online Order Confirmation Message.Also include the Tracking Number.";
	private static String type="";
	private static String emailSubjectTxt = "";
	private static String emailAttachment = "";
		//"Order Confirmation Subject";
	private static String emailFromAddress = "";
		//"gupta.rahul@cris.org.in";
	private static String[] emailList =null;
	private static String[] ccemailList =null;
	
	
	public String SendMail(String[] EmailList, String[] CcEmailList, String EmailSubjectTxt, String EmailMsgTxt, String[] Attachment, String EmailFromAddress, ServletContext context)throws Exception{
		
		log.debug("email:-"+EmailList[0]);
		
		//	 Add List of Email address to who email needs to be sent to
		emailList=EmailList;
		ccemailList=CcEmailList;
		emailSubjectTxt=EmailSubjectTxt;
		emailMsgTxt=EmailMsgTxt;
		//	emailAttachment=Attachment;
		emailFromAddress=EmailFromAddress;
		//	MailSend smtpMailSender = new MailSend();
		
		log.debug("mail1.SendMail() :: before Posting mail");
		//	smtpMailSender.postMail( emailList, ccemailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
		postMail( emailList, ccemailList, emailSubjectTxt, emailMsgTxt, Attachment, emailFromAddress, context);
		log.debug("Sucessfully Sent mail to All Users");
		return "Sucessfully Sent";
	}
	
	public static void main(String args[]) throws Exception	{
		//MailSend smtpMailSender = new MailSend();
		//smtpMailSender.postMail( emailList,ccemailList, emailSubjectTxt, emailMsgTxt,emailFromAddress);
		log.debug("Sucessfully Sent mail to All Users");
	
	}
	
	public void postMail( String recipients[ ],String ccrecipients[ ], String subject, String message, String[] Attachment, String from, ServletContext context) 
		throws MessagingException 	{
		
		boolean debug = false;
	
		//Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME );
		props.put("mail.smtp.auth", "true");
	
		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getInstance(props, auth);
		log.debug("Debug setting for this Session. " + session.getDebug());
		session.setDebug(debug);
	
		// create a message
		MimeMessage msg = new MimeMessage(session);
		
		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);
		String Body="";
		log.debug("to="+recipients.length);
		log.debug("cc="+ccrecipients.length);
		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		
				
		//for (int i = 0; i < recipients.length; i++)	{
		if(recipients.length>0){
			for (int i = 0; i < recipients.length; i++)	{
				addressTo[i] = new InternetAddress(recipients[i]);
			}
			
			//addressTo[i] = new InternetAddress(recipients[i]);
			msg.setRecipients(Message.RecipientType.TO, addressTo);
			message=message;
			Body=message;
		
			//Setting the Subject and Content Type
			msg.setSubject(subject);
			msg.setContent(Body, "text/html");
		
			// create and fill the first message part
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setContent(Body, "text/html");
			
			//mbp1.attachFile(file)
			
			BodyPart imgpart1 = new MimeBodyPart();
			BodyPart imgpart2 = new MimeBodyPart();
			BodyPart imgpart5 = new MimeBodyPart();

			FileDataSource imgds1 = new FileDataSource(context.getRealPath("images/letterHeader.jpg"));
			FileDataSource imgds2 = new FileDataSource(context.getRealPath("images/left_spacer.jpg"));
			FileDataSource imgds5 = new FileDataSource(context.getRealPath("images/letterHead_05.gif"));

			imgpart1.setDataHandler(new DataHandler(imgds1));
			imgpart2.setDataHandler(new DataHandler(imgds2));
			imgpart5.setDataHandler(new DataHandler(imgds5));
			
			imgpart1.setHeader("Content-ID", "<letterHeader>");
			imgpart2.setHeader("Content-ID", "<left_spacer>");
			imgpart5.setHeader("Content-ID", "<letterHead_05>");
			
			MimeBodyPart[] mbp2 = new MimeBodyPart[Attachment.length];

// create the Multipart and add its parts to it
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(imgpart1);
			mp.addBodyPart(imgpart2);
			mp.addBodyPart(imgpart5);
			
//attach the file to the message
			for( int i=0; i<Attachment.length;i++){
				mbp2[i]=new MimeBodyPart();
				FileDataSource fds = new FileDataSource(Attachment[i]+File.separator);
				mbp2[i].setDataHandler(new DataHandler(fds));
				mbp2[i].setFileName(fds.getName());
				mp.addBodyPart(mbp2[i]);
			}
			
// add the Multipart to the message
			msg.setContent(mp);
			Transport.send(msg);
		}
		
// for CC 
		
		InternetAddress[] addressCc = new InternetAddress[ccrecipients.length];
		//for (int i = 0; i < ccrecipients.length; i++) {
		if(ccrecipients.length>0){
			for (int i = 0; i < recipients.length; i++)	{
				addressCc[i] = new InternetAddress(ccrecipients[i]);
			}
			
			//addressCc[i] = new InternetAddress(ccrecipients[i]);
			msg.setRecipients(Message.RecipientType.CC, addressCc);
			message=message;
			Body=message;
		
			//Setting the Subject and Content Type
			msg.setSubject(subject);
			msg.setContent(Body, "text/html");
		
			// create and fill the first message part
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(Body);
			//mbp1.attachFile(file)
			
			MimeBodyPart[] mbp2 = new MimeBodyPart[Attachment.length];

			// create the Multipart and add its parts to it
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			
			for( int i=0; i<Attachment.length;i++){
				mbp2[i]=new MimeBodyPart();
				//attach the file to the message
				FileDataSource fds = new FileDataSource(Attachment[i]+File.separator);
				mbp2[i].setDataHandler(new DataHandler(fds));
				mbp2[i].setFileName(fds.getName());
				mp.addBodyPart(mbp2[i]);
			}
			
			// add the Multipart to the message
			msg.setContent(mp);
			Transport.send(msg);
		}
	}
	
	
	/*
	SimpleAuthenticator is used to do simple authentication
	when the SMTP server requires it.
	*/
	private class SMTPAuthenticator extends javax.mail.Authenticator	{
	
		public PasswordAuthentication getPasswordAuthentication(){
			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			
			return new PasswordAuthentication(username, password);
		}
	}

}

