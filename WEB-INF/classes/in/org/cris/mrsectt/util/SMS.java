package in.org.cris.mrsectt.util;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class SMS
{
	public static void send(String phone, String msg) throws IOException {
		
		//Don't send any Message for local System which are running microsoft windows
		if(System.getProperty("os.name").toString().startsWith("Windows")){
			return;
		}

		/*if (uid == null || 0 == uid.length())
			throw new IllegalArgumentException("User ID should be present.");
		else
			uid = URLEncoder.encode(uid, "UTF-8");

		if (pwd == null || 0 == pwd.length())
			throw new IllegalArgumentException("Password should be present.");
		else
			pwd = URLEncoder.encode(pwd, "UTF-8");
*/
		if (phone == null || 0 == phone.length())
			throw new IllegalArgumentException("At least one phone number should be present.");

		if (msg == null || 0 == msg.length())
			throw new IllegalArgumentException("SMS message should be present.");
		else
			msg = URLEncoder.encode(msg, "UTF-8");

		Vector<Long> numbers = new Vector<Long>();
		String pharr[];
		if (phone.indexOf(';') >= 0) {
			pharr = phone.split(";");
			for (String t : pharr) {
				try
				{
					numbers.add(Long.valueOf(t));
				}
				catch (NumberFormatException ex)
				{
					throw new IllegalArgumentException("Give proper phone numbers.");
				}
			}
		} else {
			try
			{
				numbers.add(Long.valueOf(phone));
			}
			catch (NumberFormatException ex)
			{
				throw new IllegalArgumentException("Give proper phone numbers.");
			}
		}

		if (0 == numbers.size())
			throw new IllegalArgumentException("At least one proper phone number should be present to send SMS.");

		/*==================================================================*/

		// Login
		
		/*URL u = new URL("http://bulksms.mysmsmantra.com:8080/WebSMS/SMSAPI.jsp?username=demouser&password=343239045" +
				"&sendername=DM&mobileno=" + num + "&message=" + msg);
		HttpURLConnection uc = (HttpURLConnection) u.openConnection();
		uc.setDoOutput(true);
		uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5");
		uc.setRequestProperty("Content-Length", String.valueOf(content.length()));
		uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		uc.setRequestProperty("Accept", "*//*");
		uc.setRequestMethod("POST");
		uc.setInstanceFollowRedirects(false); // very important line :)
		//System.out.println("SMS.send()"+uc.getOutputStream());
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(uc.getOutputStream()), true);
		pw.print(content);
		pw.flush();
		pw.close();
		BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		while ( (temp = br.readLine()) != null ) {}*/
		

		// Send SMS to each of the phone numbers
		URL u = null; HttpURLConnection uc = null;PrintWriter pw=null;
		for (long num : numbers)
		{
			
			
			
			String temp = "";
			String content = "username=TMSAdmin&password=criscris&sendername=TMSADM&mobileno=91"+num+"&message="+msg;
			System.setProperty("http.proxyHost", "172.16.1.61");
			System.setProperty("http.proxyPort", "8080");
			//content = "HiddenAction=instantsms&login=&pass=&custid=undefined&MobNo=" + num + "&textArea=" + msg;
			//content = "custid=undefined&HiddenAction=instantsms&Action=custfrom50000&login=&pass=&MobNo=" + num + "&textArea=" + msg + "&qlogin1=Gmail+Id&qpass1=******&gincheck=on&ylogin1=Yahoo+Id&ypass1=******&yincheck=on";
			u = new URL("http://bulksms.mysmsmantra.com:8080/WebSMS/SMSAPI.jsp?"+content);
			//u = new URL("http://wwwa.way2sms.com/FirstServletsms?custid=");
			uc = (HttpURLConnection) u.openConnection();
			
			uc.setDoOutput(true);
			uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5");
			uc.setRequestProperty("Content-Length", String.valueOf(content.getBytes().length));
			uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			uc.setRequestProperty("Accept", "*/*");
			//uc.setRequestProperty("Cookie", cookie);
			uc.setRequestMethod("POST");
			uc.setInstanceFollowRedirects(false);
			pw = new PrintWriter(new OutputStreamWriter(uc.getOutputStream()), true);
			pw.print(content);
			pw.flush();
			pw.close();
			BufferedReader br  = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			while ( (temp = br.readLine()) != null ) {String cookie = uc.getHeaderField("Set-Cookie");}
			br.close();
			u = null;
			uc = null;
		}

		// Logout
		/*u = new URL("http://bulksms.mysmsmantra.com:8080/WebSMS/SMSAPI.jsp?"+content);
		uc = (HttpURLConnection) u.openConnection();
		uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5");
		uc.setRequestProperty("Accept", "");
		uc.setRequestProperty("Cookie", cookie);
		uc.setRequestMethod("GET");
		uc.setInstanceFollowRedirects(false);
		br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		while ( (temp = br.readLine()) != null ) {}
		br.close();
		u = null;
		uc = null;*/
	}
	
	public static void main(String[] args) throws IOException {
		send("9310861705","Hi this is test massage from MRSECTT Application");
	}
}