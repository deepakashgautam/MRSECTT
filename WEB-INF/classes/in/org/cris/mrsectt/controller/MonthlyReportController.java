package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.dao.MonthlyReportDAO;
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
 * Servlet implementation class MonthlyReportController
 */
public class MonthlyReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonthlyReportController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		
		String btnClick = StringFormat.nullString(request.getParameter("btnClick"));
		String serverpath = request.getSession().getAttribute("filepath") != null ? request.getSession().getAttribute("filepath").toString(): "/opt/";
		//System.out.println("serverpath :" + serverpath);
		String[] Refclass = request.getParameterValues("REFCLASS") != null ? request.getParameterValues("REFCLASS"): new String[0];
		String[] Year = request.getParameterValues("YEAR") != null ? request.getParameterValues("YEAR"): new String[0];
		String[] MARKTO = request.getParameterValues("SEL_MARKTO") != null ? request.getParameterValues("SEL_MARKTO"): new String[0];
		String criteria = request.getParameter("REP")!= null ? request.getParameter("REP"): "";
		
		String edrishti = request.getParameter("edrishti")!= null ? request.getParameter("edrishti"): "";
		//System.out.println("11111111111111==========================11111111111111111");
		//System.out.println("1111111111111111111111111111111"+criteria);
		
		if(edrishti.equalsIgnoreCase("1")){
			 MARKTO = new  MonthlyReportDAO().getMarkToListForeDrishti();
			
			
		}
		String condMARKTO = "";
		//System.out.println("MARKTO.length-------------------------"+MARKTO.length);
		for (int i = 0; i < MARKTO.length; i++) {
			if(i==MARKTO.length-1)
			{
				condMARKTO += "'" + MARKTO[i] + "'";	
			}
			else{
				condMARKTO += "'" + MARKTO[i] + "',";
			}
			//System.out.println("-------------------------condMARKTO"+condMARKTO);
		}
		//System.out.println("-------------------------condMARKTO"+condMARKTO);
		String condMARKTOFinal = " and b.MARKINGTO in  (" + condMARKTO + " )";
		
		String year = " ";
		for (int i = 0; i < Year.length; i++) {
			if(i==Year.length-1)
			{
				year += "'" + Year[i] + "'";	
			}
			else{
				year += "'" + Year[i] + "',";
			}
			/*if(Year[i]==""){
				year = "'2014','2015','2016','2017'";
				
			}*/
		}
		String condYearFinal = " and to_char(incomingdate,'yyyy') in   (" + year + " )";
		//System.out.println(Refclass[0]+""+Refclass[1]);
		//System.out.println(Year[0]+""+Year[1]);
		//System.out.println(MARKTO[0]+""+MARKTO[1]);
		
		
		
		if ("Go".equalsIgnoreCase(btnClick)){
		String RepPath = "";
		{
			
			RepPath = new MonthlyReportDAO().generateConsolidatedReportWithFormat(serverpath,Refclass,condMARKTOFinal,condYearFinal,year,criteria);
		}
		File target = new File(RepPath);
		OutputStream output = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition:","attachment;filename= MRSECTT_officerWiseSummary" + ".xls;");
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
		if ("CaseWise".equalsIgnoreCase(btnClick)){
			
			String RepPath = "";
			
				RepPath = new MonthlyReportDAO().generateConsolidatedReportCaseWise(serverpath,condMARKTOFinal,criteria);
			
			File target = new File(RepPath);
			OutputStream output = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition:","attachment;filename= MRSECTT_ClassWiseSummary" + ".xls;");
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
		
		if ("YearWise".equalsIgnoreCase(btnClick)){
		
		String RepPath = "";
		
			RepPath = new MonthlyReportDAO().generateConsolidatedReportYearWise(serverpath,condMARKTOFinal,criteria);
		
		File target = new File(RepPath);
		OutputStream output = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition:","attachment;filename= MRSECTT_YearWiseSummary" + ".xls;");
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
		
		if ("StatisticSummary".equalsIgnoreCase(btnClick)){
			
			String RepPath = "";
			
				RepPath = new MonthlyReportDAO().generateStatisticalSummary(serverpath,criteria);
			
			File target = new File(RepPath);
			OutputStream output = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition:","attachment;filename= MRSECTT_StatisticSummary" + ".xls;");
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
	
	

}
