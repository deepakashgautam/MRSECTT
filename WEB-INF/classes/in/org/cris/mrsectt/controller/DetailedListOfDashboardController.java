package in.org.cris.mrsectt.controller;

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

import in.org.cris.mrsectt.dao.DashboardSubjectWiseDAO;

/**
 * Servlet implementation class DetailedListOfDashboardController
 */
@WebServlet("/DetailedListOfDashboardController")
public class DetailedListOfDashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DetailedListOfDashboardController() {
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
//		String subjectCode1 = request.getParameter("subjectCode") != null ? request.getParameter("subjectCode"): new String[0];
//		String loginID1 = (String) (request.getParameter("loginID") != null ? request.getParameter("loginID"): new String[0]);
//		String status1 = (String) (request.getParameter("status") != null ? request.getParameter("status"): new String[0]);
		String subjectCode = request.getParameter("subjectCode")!=null?request.getParameter("subjectCode"):"";
		String loginID = request.getParameter("loginID")!=null?request.getParameter("loginID"):"";
		//String loginName = request.getParameter("loginName")!=null?request.getParameter("loginName"):"";
		String status = request.getParameter("status")!=null?request.getParameter("status"):"";
		//System.out.println(" getReportData getReportData+++++++++++++++++++++"+status);
		 String typeOfUser = request.getParameter("typeOfUser");

		 if(typeOfUser.equalsIgnoreCase("stateCoordinator")){

		
		 //String cond = "and statecode='"+State+"'";
		String RepPath = "";
		
		RepPath = new DashboardSubjectWiseDAO().generateDashboardSubjectWiseDetailState(serverpath,subjectCode,loginID,status);
	
	    File target = new File(RepPath);
	    OutputStream output = response.getOutputStream();
	    response.setContentType("application/vnd.ms-excel");
	    response.setHeader("Content-Disposition:","attachment;filename= Detail List SubjectWise" + ".xls;");
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
		 
		 if(typeOfUser.equalsIgnoreCase("hod")){
			 
			 String RepPath = "";
			 RepPath = new DashboardSubjectWiseDAO().generateDashboardSubjectWiseDetailHOD(serverpath,subjectCode,loginID,status);
				
			    File target = new File(RepPath);
			    OutputStream output = response.getOutputStream();
			    response.setContentType("application/vnd.ms-excel");
			    response.setHeader("Content-Disposition:","attachment;filename= Detail List SubjectWise" + ".xls;");
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
		 
		 
		 if(typeOfUser.equalsIgnoreCase("subHOD")){
			 
			 String RepPath = "";
			 RepPath = new DashboardSubjectWiseDAO().generateDashboardSubjectWiseDetailHODSub(serverpath,subjectCode,loginID,status);
				
			    File target = new File(RepPath);
			    OutputStream output = response.getOutputStream();
			    response.setContentType("application/vnd.ms-excel");
			    response.setHeader("Content-Disposition:","attachment;filename= Detail List SubjectWise" + ".xls;");
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
		 	 
		 
 if(typeOfUser.equalsIgnoreCase("subRailway")){
			 
			 String RepPath = "";
			 RepPath = new DashboardSubjectWiseDAO().generateDashboardSubjectWiseDetailSubRailway(serverpath,subjectCode,loginID,status);
				
			    File target = new File(RepPath);
			    OutputStream output = response.getOutputStream();
			    response.setContentType("application/vnd.ms-excel");
			    response.setHeader("Content-Disposition:","attachment;filename= Detail List SubjectWise" + ".xls;");
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
