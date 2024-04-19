package in.org.cris.mrsectt.util;




import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class SendMail {
	static Logger log = LogManager.getLogger(SendMail.class);
	static final String username = "edrishti";
	static final String password = "edrishti1234";
	final String fromuserid = "edrishti@cris.org.in";
	//final static String cc_addresses="OSDMR <hanishyadav@gmail.com>, EDPGMR <edpgmr@rb.railnet.gov.in>, PSMR <psmr@rb.railnet.gov.in>";
	final static String cc_addresses="OSDMR <devmgs@yahoo.co.in>, PSMR <devmgs@gmail.com> ";
	static String MachineInfo = "";
	static Transport transport;
	static Session session;
	static Properties  props = new Properties();
	static String smtpHost = "172.16.1.206";
	static String smtpPort = "25";
	static {
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
		try {
			/*
			 * Enumeration<NetworkInterface> ni = NetworkInterface
			 * .getNetworkInterfaces();
			 * 
			 * while (ni.hasMoreElements()) { for (InterfaceAddress ifad :
			 * ni.nextElement() .getInterfaceAddresses()) { if
			 * (!(ifad.toString().startsWith("/127.0.0.1") || ifad
			 * .toString().startsWith("/0:0:0:0:0:0:0:1"))) MachineInfo +=
			 * "<br> IP: " + ifad.toString(); }
			 * 
			 * }
			 */
			for (NetworkInterface ni : Collections.list(NetworkInterface
					.getNetworkInterfaces())) {
				for (InetAddress address : Collections.list(ni
						.getInetAddresses())) {
					if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
						MachineInfo += "<br> IP: " + address;
					}
				}
			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	final static String disclaimerMessage = 
			//"<hr> <b><u>Sender Info:</u></b> <br> "
			//+ MachineInfo
			 "<br /><hr><br /><b>IMPORTANT:</b> Please do not reply to this message or mail address. For any queries or information, contact <a href='http://cris.org.in/eDrishti'>e-Drishti</a> Helpdesk at +91-1123379934."
			+ "<br />"
			+ "<B>DISCLAIMER:</B> This communication is confidential and privileged and is directed to and for"
			+ " the use of the addressee only."
			+ " The recipient if not the addressee should not use this message if erroneously received,"
			+ " and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized."
			+ " The recipient acknowledges that <a href='http://cris.org.in'>Centre for Railway Information Systems (CRIS)</a> may be unable to exercise control or ensure or guarantee"
			+ " the integrity of the text of the email message and the text is not warranted as to completeness"
			+ " and accuracy. Before opening and accessing the attachment, if any, please check and scan for virus.";



	public SendMail() {
		Thread.currentThread().setContextClassLoader( getClass().getClassLoader() );
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "false");
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
		mc.addMailcap("message/rfc822;; x-java-content- handler=com.sun.mail.handlers.message_rfc822"); 
	}

	/*
	 * This method sends mail
	 */
	
	public static void send(String toemailid,String cc, String subject, String messageText,
			int messageFormat, String[] filepaths)
			{

		if (session == null) {
			session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});
		}
		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(
					"e-Drishti <edrishti@cris.org.in>"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toemailid));
			
			if(cc!=null && cc.length()>0)
				message.setRecipients(Message.RecipientType.CC,
						InternetAddress.parse(cc));
			
			
			message.setSubject(subject);

			// create and fill the first message part
			MimeBodyPart mbp1 = new MimeBodyPart();

			if (messageFormat == 0)
				mbp1.setText(messageText);
			else
				mbp1.setText(messageText, "utf-8", "html");

			// create the Multipart and add its parts to it
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);

			if (filepaths != null && filepaths.length > 0) {
				// create the second message part
				
				for(String fpath : filepaths){
					MimeBodyPart mbp2 = new MimeBodyPart();
					// attach the file to the message
					FileDataSource fds = new FileDataSource(fpath);
					mbp2.setDataHandler(new DataHandler(fds));
					mbp2.setFileName(fds.getName());
					mp.addBodyPart(mbp2);
				}
				
				
				
				
			}

			MimeBodyPart mbp3 = new MimeBodyPart();

			mbp3.setText(disclaimerMessage, "utf-8", "html");
			mp.addBodyPart(mbp3);

			// add the Multipart to the message
			message.setContent(mp);

			// set the Date: header
			message.setSentDate(new Date());

			if (transport == null) {

				transport = session.getTransport("smtp");

			}
			if (!transport.isConnected()) {

				transport.connect(username, password);
			}

			transport.sendMessage(message, message.getAllRecipients());

			
			log.info("email sent to " + toemailid);

		} catch (MessagingException e) {
			log.fatal(e, e);
			throw new RuntimeException(e);
		}
		
		
	
			}

	public static void send(String toemailid, String subject, String messageText,
			int messageFormat, String[] filepaths) {
		
		send( toemailid,null,  subject,  messageText,
				 messageFormat, filepaths);
	}
	
	public static void sendwithDefaultCC(String toemailid, String subject, String messageText,
			int messageFormat, String[] filepaths) {
		
		send( toemailid,cc_addresses,  subject,  messageText,
				 messageFormat, filepaths);
	}
	/*public static void main(String [] args){
		
		send("devmgs@gmail.com,rohitjaincse@gmail.com", "Test Subject", "test content", 1, new String [] {"C:\\Users\\5338\\Desktop\\MRSECTT_test11.xlsx","C:\\Users\\5338\\Desktop\\settings.xml"});
	}*/
}
