package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstLogin;
import in.org.cris.mrsectt.Beans.TrnFileHdr;
import in.org.cris.mrsectt.Beans.TrnReminder;
import in.org.cris.mrsectt.dao.ReminderAutoDAO;
import in.org.cris.mrsectt.util.StringFormat;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PreferredListController
 */
public class ReminderAutoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReminderAutoController() {
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
		

		//System.out.println("im in controller");
		HttpSession session = request.getSession();
		MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
		CommonBean sbean = new CommonBean();
		String msg="";
		String btnClick = StringFormat.nullString(request.getParameter("btnClick"));
		
		
		String REMDATE=request.getParameter("REMDATE")!=null ? request.getParameter("REMDATE") : "";
	    String CLASS=request.getParameter("CLASS")!=null ? request.getParameter("CLASS") : "";
	    
		//System.out.println("ReminderAutoController.doPost()::: REMDATE :::"+REMDATE);
		//System.out.println("ReminderAutoController.doPost()::: CLASS :::"+CLASS);
		
		
		ReminderAutoDAO remDAO = new ReminderAutoDAO();
		

		if(btnClick.equalsIgnoreCase("SAVE")){
		//msg = new ReminderAutoDAO().saveList(groupid, MARKINGTO, SIGNEDBY, REFSUBJECTCODE, FILESUBJECTCODE,IMARKINGTO,BRANCH);
		}
		
		request.setAttribute("msg", msg);
		
//			request.setAttribute("pcdoBallastHopper", pcdoBallastHopper);
		request.getRequestDispatcher("/ReminderAuto.jsp").forward(
				request, response);
		 
	}

}
