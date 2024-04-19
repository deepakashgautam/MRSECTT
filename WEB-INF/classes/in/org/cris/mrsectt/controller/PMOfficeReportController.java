package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.dao.PMOfficeReportDAO;

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
 * Servlet implementation class PMOfficeReportController
 */
@WebServlet("/PMOfficeReportController")
public class PMOfficeReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PMOfficeReportController() {
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
		
		
		String serverpath = request.getSession().getAttribute("filepath") != null ? request.getSession().getAttribute("filepath").toString(): "/opt/";
		//System.out.println("serverpath 1111111:" + serverpath);
		String Date = (String) (request.getParameter("Date") != null ? request.getParameter("Date"): new String[0]);
		
		 //String cond = "and statecode='"+State+"'";
		String RepPath = "";
		
		RepPath = new PMOfficeReportDAO().generatePMofficeReport(serverpath,Date);
	
	    File target = new File(RepPath);
	    OutputStream output = response.getOutputStream();
	    response.setContentType("application/vnd.ms-excel");
	    response.setHeader("Content-Disposition:","attachment;filename= PMOfficeReport" + ".xls;");
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
