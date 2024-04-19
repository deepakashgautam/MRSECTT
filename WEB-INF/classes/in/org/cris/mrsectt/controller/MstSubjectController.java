package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.dao.MstSubjectDAO;
import in.org.cris.mrsectt.util.StringFormat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MstRoleController
 */
public class MstSubjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MstSubjectController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doPost(arg0, arg1);
		req.getRequestDispatcher("/MstRole.jsp").forward(req, res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			HttpSession session = req.getSession();
			//MstSubject bn = new MstSubject();
			MstSubjectDAO rdao = new MstSubjectDAO();
			CommonBean sbean = new CommonBean();
			
			String btnclick = StringFormat.nullString(req.getParameter("btnclick"));
			String deptCode = StringFormat.nullString(req.getParameter("department"));
			String[] arrRSUBCODE = req.getParameterValues("RSUBCODE");
			String[] arrRSUBNAME = req.getParameterValues("RSUBNAME");
			String[] arrRSUBDEPT = req.getParameterValues("RSUBDEPT");
			String[] arrRISBUDGET = req.getParameterValues("RISBUDGET");			
			String[] arrRISNEW = req.getParameterValues("RISNEW");
			String[] arrRISEDIT = req.getParameterValues("RNE");
			
			String[] arrFSUBCODE = req.getParameterValues("FSUBCODE");
			String[] arrFSUBNAME = req.getParameterValues("FSUBNAME");
			String[] arrFSUBDEPT = req.getParameterValues("FSUBDEPT");
			String[] arrFISBUDGET = req.getParameterValues("FISBUDGET");
			String[] arrFISNEW = req.getParameterValues("FISNEW");
			String[] arrFISEDIT = req.getParameterValues("FNE");
			
//////get and set search parameters 
			String sSUBJECTCODE = StringFormat.nullString(req.getParameter("sSUBJECTCODE"));
			String sSUBJECTNAME = StringFormat.nullString(req.getParameter("sSUBJECTNAME"));
			String sSUBJECTDEPT = StringFormat.nullString(req.getParameter("sSUBJECTDEPT"));
			
			sbean.setField1(sSUBJECTCODE);
			sbean.setField2(sSUBJECTNAME);
			sbean.setField3(sSUBJECTDEPT);
			if ("C".equalsIgnoreCase(btnclick)) {
				
				//add sbean to session for search parameters
				session.removeAttribute("sbean");
				//
				res.sendRedirect("MstSubject.jsp");
			}
			if("D".equalsIgnoreCase(btnclick)){
			//	session.setAttribute("deptCode", deptCode);
				req.setAttribute("deptCode", deptCode);
				req.getRequestDispatcher("MstSubject.jsp").forward(req, res);
			}
			if("S".equalsIgnoreCase(btnclick))
			{
				String isDataSaved = rdao.saveSubject(arrRSUBCODE, arrRSUBNAME, deptCode, arrRISBUDGET, arrRISNEW, arrRISEDIT, arrFSUBCODE, arrFSUBNAME, arrFISBUDGET, arrFISNEW, arrFISEDIT);
				session.setAttribute("msg", isDataSaved);
				res.sendRedirect("MstSubject.jsp");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}