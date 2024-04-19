package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.dao.ReportFileNoDAO;
import in.org.cris.mrsectt.util.StringFormat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileSummaryReportController
 */
public class FileSummaryReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileSummaryReportController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//String btnClick = StringFormat.nullString(request.getParameter("btnClick"));
		String serverpath = request.getSession().getAttribute("filepath") != null ? request.getSession().getAttribute("filepath").toString(): "/opt/";
		//System.out.println("serverpath :" + serverpath);
		String[] YEARCODE = request.getParameterValues("SEL_YEARCODE") != null ? request.getParameterValues("SEL_YEARCODE"): new String[0];
		String[] FILETYPE = request.getParameterValues("SEL_FILETYPE") != null ? request.getParameterValues("SEL_FILETYPE"): new String[0];
		String[] PENDING = request.getParameterValues("SEL_FILESTATUS1") != null ? request.getParameterValues("SEL_FILESTATUS1"): new String[0];
		String[] DISPOSED = request.getParameterValues("SEL_DISPOSED") != null ? request.getParameterValues("SEL_DISPOSED"): new String[0];
		
		String in="";
		for (int i = 0; i < DISPOSED.length; i++) {
			
			in=in+", "+DISPOSED[i];
			}
		//System.out.println(in);
		
		String condYEARCODE = "";
		String condFILETYPE="";
		String condPENDING="";
		String condDISPOSED="";
		
		//System.out.println("YEARCODE.length"+YEARCODE.length);
		for (int i = 0; i < YEARCODE.length; i++) {
			if(i==YEARCODE.length-1){
				condYEARCODE += "'" + YEARCODE[i] + "'";	
			}
			else{
				condYEARCODE += "'" + YEARCODE[i] + "',";
			}}
		
		
		
		
		for (int i = 0; i < FILETYPE.length; i++) {
			if(i==FILETYPE.length-1){
				condFILETYPE += "'" + FILETYPE[i] + "'";	
			}
			else{
				condFILETYPE += "'" + FILETYPE[i] + "',";
			}}
		
		for (int i = 0; i < PENDING.length; i++) {
			
				condPENDING += "" + PENDING[i] + ",1,";
		}
		for (int i = 0; i < DISPOSED.length; i++) {
			
			condDISPOSED += "" + DISPOSED[i] + ",1,";
			}
		
		
		String condYEARCODEFINAL="";
		String condFILETYPEFINAL="";
		if(!(YEARCODE.length==0))
		{
			 condYEARCODEFINAL = " TO_CHAR(TRNFILEHDR.REGISTRATIONDATEDES,'YYYY') in  (" + condYEARCODE + " ) ";
		}else{
			 condYEARCODEFINAL="1=1";
		}
		if(!(FILETYPE.length==0))
		{
			 condFILETYPEFINAL = " and TRNFILEHDR.REFERENCETYPE in  (" + condFILETYPE + " )";
		}else 
		{
			 condFILETYPEFINAL = "and 1=1";
		}
			String condPENDINGFINAL = condPENDING;
			String condDISPOSEDFINAL = condDISPOSED	;		
			
			//System.out.println("condPENDINGFINAL"+condPENDINGFINAL);
			//System.out.println("condDISPOSED"+condDISPOSED);
		String RepPath = "";
		
				RepPath = new ReportFileNoDAO().generateSummaryReport(serverpath,condYEARCODEFINAL,condFILETYPEFINAL,condPENDINGFINAL,condDISPOSEDFINAL);
	
					File target = new File(RepPath);
						OutputStream output = response.getOutputStream();
							response.setContentType("application/vnd.ms-excel");
							response.setHeader("Content-Disposition:","attachment;filename= MRSECTT_FilesSummary" + ".xls;");
							response.setHeader("Cache-Control", "must-revalidate");
							BufferedInputStream fin = new BufferedInputStream(new FileInputStream(RepPath));
							int ch = 0;
							while ((ch = fin.read()) != -1) {
								output.write(ch);
							}
							output.flush();
							output.close();
							fin.close();
							target.delete();
		
	}

}
