package in.org.cris.mrsectt.dao;
import in.org.cris.mrsectt.Beans.RecieptDetails;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.oreilly.servlet.Base64Encoder;

import test.WSResponse;
public class NetClientGet {
	public RecieptDetails main2(String q) {
		RecieptDetails recieptdetails = null;
		 String  proxyHost="172.16.1.61";
		String proxyPort="8080";
		System.setProperty("http.proxyHost",proxyHost);
        System.setProperty("http.proxyPort", proxyPort);
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", proxyPort);
		  try {
			  String encoding = Base64Encoder.encode ("eoffice:eoffice");

			  String urlParameters  = "computernumber="+q;
			  byte[] postData = urlParameters.getBytes();
			  int postDataLength = postData.length;
			URL url = new URL("http://eofficerb.ggndc.rcil.gov.in/eFileServices/rest/xmldataset/efile/receiptdetails");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty  ("Authorization", "Basic " + encoding);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
			
			conn.setUseCaches(false);
	
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				   wr.write( postData );
			
			/*OutputStream os = conn.getOutputStream();
			os.write(postData.getBytes());
			os.flush();
			os.close();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}
*/
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			String xmlString="";
			//System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				//System.out.println(output);
				xmlString+=output;
			}
			
			try {
			JAXBContext jaxbContext = JAXBContext.newInstance(WSResponse.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			WSResponse wsresponse = (WSResponse) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
			//System.out.println(wsresponse);
			
			
			jaxbContext = JAXBContext.newInstance(RecieptDetails.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			 recieptdetails = (RecieptDetails) jaxbUnmarshaller.unmarshal(new StringReader(wsresponse.toString()));

			//System.out.println(recieptdetails.getReceiptNumber());
			
			
			
			} catch (JAXBException e) {
					e.printStackTrace();
				  }
			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
return recieptdetails;
		}

}
