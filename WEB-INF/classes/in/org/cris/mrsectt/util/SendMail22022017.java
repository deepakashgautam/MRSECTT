package in.org.cris.mrsectt.util;



import java.io.IOException;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
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

public class SendMail22022017 {
	static Logger log = LogManager.getLogger(SendMail.class);
	//final String username = "mrcell@rb.railnet.gov.in";
	final String username = "rohitjain@cris.org.in";
	final String password = "rickyj";
	//final String password = "MRllec#0987";
//	final String fromuserid = "MR Cell <surya.m.ojha@gmail.com>";
	final String fromuserid = "MR Cell <rohitjain@cris.org.in>";
	//final String fromuserid = "MR Cell <mrcell@rb.railnet.gov.in>";
	static String MachineInfo="";
	static Transport transport;
	static {
				try {
					Enumeration<NetworkInterface> ni  = NetworkInterface.getNetworkInterfaces();
					
					while(ni.hasMoreElements()){
						for(InterfaceAddress ifad : ni.nextElement().getInterfaceAddresses()){
							if( !
									(ifad.toString().startsWith("/127.0.0.1")
									||
									ifad.toString().startsWith("/0:0:0:0:0:0:0:1"))
							  )
								MachineInfo+="<br> IP: "+ ifad.toString();
						}
						
					}
					
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	final static String disclaimerMessage= "<hr> <B>DISCLAIMER:</B> This is a system generated e-mail. Please don't reply to this email."
			+ "This communication is confidential and privileged and is directed to and for" +
	" the use of the addressee only." +
	" The recipient if not the addressee should not use this message if erroneously received," +
	" and access and use of this e-mail in any manner by anyone other than the addressee is unauthorized." +
	" The recipient acknowledges that <a href='http://cris.org.in'>CRIS</a> may be unable to exercise control or ensure or guarantee" +
	" the integrity of the text of the email message and the text is not warranted as to completeness" +
	" and accuracy. Before opening and accessing the attachment, if any, please check and scan for virus.";
	
	
	Properties props = new Properties();
	//String smtpHost = "smtpout.railnet.gov.in";
	String smtpHost = "172.16.1.206";
	//String smtpHost = "mail.gov.in";
	String smtpPort = "25";

	public SendMail22022017() {
		
		/*props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", smtpHost);
		props.put("mail.smtps.auth", "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		props.put("mail.smtp.port", smtpPort);
		//props.put("mail.smtp.starttls.enable", "true");
		
		
		//props.put("mail.smtp.socketFactory.fallback", "false");
*/	
		
		
		//System.out.println("SSLEmail Start one");
		// props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.auth", "false");
		//props.put("mail.smtp.ssl.trust", "*");
		
		
	}

	/*
	 * This method sends mail
	 */
	
	public static void main(String []args)
	{
		new SendMail().send("rohitjaincse@gmail.com", "test attachment subject", "my mail",1,
				null);
	}
	
	public void send(String toemailid, String subject, String messageText,int messageFormat,
			String[] filepaths) {

		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);
	       

		try {

			MimeMessage message = new MimeMessage(session);
			//message.setFrom(new InternetAddress("MRCoordinationCell<surya.m.ojha@gmail.com>"));
			message.setFrom(new InternetAddress("Rohit <rohitjain@cris.org.in>"));
			//message.setFrom(new InternetAddress("MRCoordinationCell<mrcell@rb.railnet.gov.in>"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toemailid));
			message.setSubject(subject);

			// create and fill the first message part
			MimeBodyPart mbp1 = new MimeBodyPart();
			
			if(messageFormat==0)
				mbp1.setText(messageText);
			else
				mbp1.setText(messageText,"utf-8", "html");
			
			// create the Multipart and add its parts to it
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);

			if (filepaths!=null && filepaths.length > 0) {
				// create the second message part
				MimeBodyPart mbp2 = new MimeBodyPart();
				// attach the file to the message
				FileDataSource fds = new FileDataSource("filepath");
				mbp2.setDataHandler(new DataHandler(fds));
				mbp2.setFileName(fds.getName());
				mp.addBodyPart(mbp2);
			}

			/*MimeBodyPart imagePart = new MimeBodyPart();
			try {
				imagePart.attachFile("C:\\Users\\Devendra\\Documents\\InfraDb\\0.png");
				imagePart.setContentID("<0.png>");
				imagePart.setDisposition(MimeBodyPart.INLINE);
				mp.addBodyPart(imagePart);
				
				imagePart = new MimeBodyPart();
				imagePart.attachFile("C:\\Users\\Devendra\\Documents\\InfraDb\\1.png");
				imagePart.setContentID("<1.png>");
				imagePart.setDisposition(MimeBodyPart.INLINE);
				mp.addBodyPart(imagePart);
				
				imagePart = new MimeBodyPart();
				imagePart.attachFile("C:\\Users\\Devendra\\Documents\\InfraDb\\-1.png");
				imagePart.setContentID("<-1.png>");
				imagePart.setDisposition(MimeBodyPart.INLINE);
				mp.addBodyPart(imagePart);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
			MimeBodyPart mbp3 = new MimeBodyPart();
			
			mbp3.setText(disclaimerMessage,"utf-8", "html");
			mp.addBodyPart(mbp3);
			
			// add the Multipart to the message
			message.setContent(mp);

			// set the Date: header
			message.setSentDate(new Date());

			//Transport.send(message);
			
			
				transport = session.getTransport();
				transport.connect(smtpHost, 25, username,password);
			transport.sendMessage(message, message.getAllRecipients());
			log.info("sent email to "+ toemailid);
			transport.close();

		} catch (MessagingException e) {
			log.fatal(e,e);
			throw new RuntimeException(e);
		}
	}
}
