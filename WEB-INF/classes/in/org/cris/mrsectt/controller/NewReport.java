package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.dao.MonthlyReportDAO;
import in.org.cris.mrsectt.dao.ReportMArkToWise;
import in.org.cris.mrsectt.util.StringFormat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewReport
 */

public class NewReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String serverpath = request.getSession().getAttribute("filepath") != null ? request.getSession().getAttribute("filepath").toString(): "/opt/";
		String criteria = request.getParameter("REP")!= null ? request.getParameter("REP"): "";
		
			String RepPath = "";
			{
				
				RepPath = new ReportMArkToWise().generateConsolidatedReportWithFormat(serverpath,criteria);
			}
			File target = new File(RepPath);
			OutputStream output = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition:","attachment;filename= MRSECTT_Consolidated" + ".xls;");
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
