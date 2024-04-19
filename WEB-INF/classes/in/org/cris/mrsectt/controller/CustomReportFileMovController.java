package in.org.cris.mrsectt.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.org.cris.mrsectt.dao.CustomReportFileMovDAO;

/**
 * Servlet implementation class CustomReportController
 */
public class CustomReportFileMovController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomReportFileMovController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("IN CustomReportFileMovController");
		String params="";
		String repid="";
		try {
			String loginid = request.getSession().getAttribute("loginid") != null ? request.getSession().getAttribute("loginid").toString(): "";
			
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			params = sb.toString(); // "oldpass=&newpass=&cnfpass=&dateofbirth=14%2F06%2F1959&mobilenumber=9910487302&emailaddress=edceg@rb.railnet.gov.in&hidtask=EP&cmbrlycode=&cmbdivcode=&listbox=4&listbox2=8&reportid=0&repheader=5453"
											// ;//sb.toString();
			//System.out.println("parameters are: " + params);

			repid=new CustomReportFileMovDAO().saveReport(loginid,params);
		} catch (IOException e) {
			// TODO: handle exception
		}
		String newparam =params.split("&reportid=").length>0 ? params.split("&reportid=")[0]:"";
		//System.out.println("newparam : " +newparam);
		request.getRequestDispatcher("GenerateReportFileMovController?save=Y&reportid="+repid+"&"+newparam).forward(request, response);
//		response.sendRedirect("GenerateReportFileMovController?save=Y&reportid="+repid+"&"+newparam);
	}

}
