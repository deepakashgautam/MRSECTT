package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.dao.DashboardSubjectWiseDAO;
import in.org.cris.mrsectt.dao.SummaryReportDAO;
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
 * Servlet implementation class StateCoordinatorReportController
 */
@WebServlet("/StateCoordinatorReportController")
public class StateCoordinatorReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StateCoordinatorReportController() {
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
		//System.out.println("serverpath :" + serverpath);
		//String[] Refclass = request.getParameterValues("REFCLASS") != null ? request.getParameterValues("REFCLASS"): new String[0];
		String[] STATE = request.getParameterValues("SEL_STATE") != null ? request.getParameterValues("SEL_STATE"): new String[0];
		String[] VIP = request.getParameterValues("SEL_VIP") != null ? request.getParameterValues("SEL_VIP"): new String[0];
		String loginID = request.getParameter("loginid")!=null?request.getParameter("loginid"):"";
		String condSTATE = "";
	
		for (int i = 0; i < STATE.length; i++) {
			if(i==STATE.length-1)
			{
				condSTATE += "'" + STATE[i] + "'";	
			}
			else{
				condSTATE += "'" + STATE[i] + "',";
			}
			
		}
		
		String condSTATEFinal = " and a.STATEcode in  (" + condSTATE + " )";
		
		String condVIP = " ";
		for (int i = 0; i < VIP.length; i++) {
			if(i==VIP.length-1)
			{
				condVIP += "'" + VIP[i] + "'";	
			}
			else{
				condVIP += "'" + VIP[i] + "',";
			}
		}
		String condVIPFinal = " and a.addviptype in   (" + condVIP + " )";
				
				String RepPath = "";
				
					RepPath = new DashboardSubjectWiseDAO().generateStateCoordinatorDetailReport(serverpath,condSTATEFinal,condVIPFinal,loginID);
				
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

}
