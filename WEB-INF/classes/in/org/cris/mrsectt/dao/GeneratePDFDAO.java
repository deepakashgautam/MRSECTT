package in.org.cris.mrsectt.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.dbConnection.DBConnectionRWF;

public class GeneratePDFDAO {

	static Logger log = LogManager.getLogger(GeneratePDFDAO.class);
	String rName;
	int rNumber, temp;
	CommonBean bn;
	String filePath = "";
	
	public String generatePDF(String refid, String refName, ServletContext context){
		
		Map parameters = new HashMap();
		String Directory = "";
		try
		{
			//getConnection();
			JasperReport jasperReport = null;
			
			Directory="/opt/MRSECTT/ACK";
			//System.out.println("directory::"+Directory);
			filePath = Directory + File.separator +"AckLetter_"+refName+".pdf";
				
			rName = "report_AckLetter";						
			jasperReport = getCompiledReport(context);
	
			parameters.put("REFID", refid);	
			generatePDFOutput(parameters, jasperReport, filePath);	

		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Inspection Register Servlet: " + e.getMessage());
		} finally {
			disConnect();
		}
		return filePath;
	}

	private void generatePDFOutput(Map parameters, JasperReport jasperReport, String FilePath) throws JRException, NamingException, SQLException, IOException	{
		try {
			//System.out.println("jasperReport in generatePDFOutput=" + jasperReport);
			byte[] bytes = null;
			bytes = JasperRunManager.runReportToPdf(jasperReport, parameters, con);
			
			FileOutputStream fileOut = new FileOutputStream(FilePath);
			fileOut.write(bytes, 0, bytes.length);
			fileOut.flush();
			fileOut.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
/*	private void generatePDFOutput(HttpServletResponse resp, Map parameters, JasperReport jasperReport) throws JRException, NamingException, SQLException, IOException
	{
		try
		{
			//System.out.println("jasperReport in generatePDFOutput=" + jasperReport);
			byte[] bytes = null;
			bytes = JasperRunManager.runReportToPdf(jasperReport, parameters, con);
			resp.setContentType("application/pdf");
			resp.setContentLength(bytes.length);
			ServletOutputStream ouputStream = resp.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
*/
	private JasperReport getCompiledReport(ServletContext context) throws JRException
	{
		System.setProperty("jasper.reports.compile.class.path", context.getRealPath("/WEB-INF/lib/jasperreports-3.0.0.jar") + System.getProperty("path.separator") + context.getRealPath("/WEB-INF/classes/"));
		System.setProperty("jasper.reports.compile.temp", context.getRealPath("/JasperReports/"));
		File reportFile = new File(context.getRealPath("/JasperReports/" + rName + ".jasper"));
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
		if (!reportFile.exists())
		{
			net.sf.jasperreports.engine.JasperCompileManager.compileReportToFile(context.getRealPath("/JasperReports/" + rName + ".xml"));
		}
		return jasperReport;
	}
	
	public Connection con = null;
	
	public void getConnection()
	{
		try
		{
			DBConnectionRWF dbcon = new DBConnectionRWF();
			con = dbcon.getConnection();
			//System.out.println("Connection" + con);
			con.setAutoCommit(false);
		} catch (SQLException se)
		{
			//System.out.println("SQLExecption caught" + se.getMessage());
			se.printStackTrace();
		} catch (Exception e)
		{
			//System.out.println("Execption caught" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String handleNull(String str) {
		return str != null ? numberFormet(str) : "";
	}
	
	public static String numberFormet(String var) {
		try {
			if (var.indexOf(".") != -1) {
				double ret = (new Double(var)).doubleValue();
				String x = "" + ret;
				int j = x.indexOf(".");
				String y = x.substring(j + 1);
				if (y.equals("0"))
					y = "";
				String z = x.substring(0, j);
				String returnvalue;
				if (y.length() == 0) {
					returnvalue = "" + z;
				} else {
					returnvalue = z + "." + y;
				}
				return returnvalue;
			}
			return var;
		} catch (NumberFormatException nfe) {
			return var;
		}
	}
	
	public void disConnect()
	{
		if (con != null)
		{
			try
			{
				con.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
}