package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.dao.PreferredListDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PreferredListController
 */
public class PreferredListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PreferredListController() {
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
		String msg="";
		String [] MARKINGTO = request.getParameterValues("SEL_MARKINGTO") != null ? request.getParameterValues("SEL_MARKINGTO") :  new String [0];
		String [] SIGNEDBY = request.getParameterValues("SEL_SIGNEDBY") != null ? request.getParameterValues("SEL_SIGNEDBY") :  new String [0];
		String [] REFSUBJECTCODE = request.getParameterValues("SEL_REFSUBJECTCODE") != null ? request.getParameterValues("SEL_REFSUBJECTCODE") :  new String [0];	
		String [] FILESUBJECTCODE = request.getParameterValues("SEL_FILESUBJECTCODE") != null ? request.getParameterValues("SEL_FILESUBJECTCODE") :  new String [0];
		String [] IMARKINGTO = request.getParameterValues("SEL_INTMARKING") != null ? request.getParameterValues("SEL_INTMARKING") :  new String [0];
		String [] BRANCH = request.getParameterValues("SEL_BRANCH") != null ? request.getParameterValues("SEL_BRANCH") :  new String [0];
		String [] ACKBY = request.getParameterValues("SEL_ACKBY") != null ? request.getParameterValues("SEL_ACKBY") :  new String [0];
		
		//System.out.println("PreferredListController.doPost()::: MARKINGTO :::"+MARKINGTO.length);
		//System.out.println("PreferredListController.doPost()::: IMARKINGTO :::"+IMARKINGTO.length);
		//System.out.println("PreferredListController.doPost()::: SIGNEDBY :::"+SIGNEDBY.length);
		//System.out.println("PreferredListController.doPost()::: REFSUBJECTCODE :::"+REFSUBJECTCODE.length);
		//System.out.println("PreferredListController.doPost()::: FILESUBJECTCODE :::"+FILESUBJECTCODE.length);
		//System.out.println("PreferredListController.doPost()::: BRANCH :::"+BRANCH.length);
		String groupid = request.getParameter("txtGroupId") != null ? request.getParameter("txtGroupId") : "";
		String formAction = request.getParameter("formAction");
		if(formAction.equalsIgnoreCase("SAVE")){
		msg = new PreferredListDAO().saveList(groupid, MARKINGTO, SIGNEDBY, REFSUBJECTCODE, FILESUBJECTCODE,IMARKINGTO,BRANCH,ACKBY);
		}
		
		request.setAttribute("msg", msg);
		
//			request.setAttribute("pcdoBallastHopper", pcdoBallastHopper);
		request.getRequestDispatcher("/PreferredList.jsp").forward(
				request, response);
		 
	}

}
