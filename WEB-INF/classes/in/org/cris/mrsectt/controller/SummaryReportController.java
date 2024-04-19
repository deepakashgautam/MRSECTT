package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.dao.SummaryReportDAO;
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
 * Servlet implementation class SummaryReportController
 */
public class SummaryReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SummaryReportController() {
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
		
		String btnClick = StringFormat.nullString(request.getParameter("btnClick"));
		String serverpath = request.getSession().getAttribute("filepath") != null ? request.getSession().getAttribute("filepath").toString(): "/opt/";
		//System.out.println("serverpath :" + serverpath);
		//String[] Refclass = request.getParameterValues("REFCLASS") != null ? request.getParameterValues("REFCLASS"): new String[0];
		String State = (String) (request.getParameter("State") != null ? request.getParameter("State"): new String[0]);
		
		 String cond = "and statecode='"+State+"'";
		 
		 if ("StateSummary".equalsIgnoreCase(btnClick)){
				
				String RepPath = "";
				
					RepPath = new SummaryReportDAO().generateSummaryReportStateWise(serverpath,cond);
				
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
		 if ("CompleteSummary".equalsIgnoreCase(btnClick)){
				
				String RepPath = "";
				
					RepPath = new SummaryReportDAO().generateSummaryReportAllState(serverpath);
				
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
		 if ("SubjectSummary".equalsIgnoreCase(btnClick)){
			 
			 String RepPath = "";
				
				RepPath = new SummaryReportDAO().generateSubjectWiseSummaryReport1(serverpath,cond);
			
			File target = new File(RepPath);
			OutputStream output = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition:","attachment;filename= MRSECTT_SubjectWiseSummary" + ".xls;");
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
			 
			
			 /*String RepPath = "";
				
				RepPath = new SummaryReportDAO().generateSubjectWiseSummaryReport(serverpath,cond);
			
			File target = new File(RepPath);
			OutputStream output = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition:","attachment;filename= MRSECTT_SubjectWiseSummary" + ".xls;");
			response.setHeader("Cache-Control", "must-revalidate");
			BufferedInputStream fin = new BufferedInputStream(new FileInputStream(RepPath));
			int ch = 0;
			while ((ch = fin.read()) != -1) {
				output.write(ch);
			}
			output.flush();
			output.close();
			fin.close();
			target.delete();*/
			}
		 
		
	}

}
