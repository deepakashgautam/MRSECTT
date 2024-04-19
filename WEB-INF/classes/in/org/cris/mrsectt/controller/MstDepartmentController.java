package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstDepartment;
import in.org.cris.mrsectt.dao.MstDepartmentDAO;
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
public class MstDepartmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MstDepartmentController() {
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
			CommonBean sbean = new CommonBean();
			
			String DEPTCODE = StringFormat.nullString(req.getParameter("DEPTCODE"));
			String DEPTNAME = StringFormat.nullString(req.getParameter("DEPTNAME"));
			String btnclick = StringFormat.nullString(req.getParameter("btnclick"));
			
			//////get and set search parameters 
			String sDEPTCODE = StringFormat.nullString(req.getParameter("sDEPTCODE"));
			String sDEPTNAME = StringFormat.nullString(req.getParameter("sDEPTNAME"));
			sbean.setField1(sDEPTCODE);
			sbean.setField2(sDEPTNAME);
			//////
			
			
			MstDepartment bn = new MstDepartment();
			MstDepartmentDAO rdao = new MstDepartmentDAO();
			bn.setDEPTCODE(DEPTCODE);
			bn.setDEPTNAME(DEPTNAME);
			if ("C".equalsIgnoreCase(btnclick)) {
				
				//add sbean to session for search parameters
				session.removeAttribute("sbean");
				//
				res.sendRedirect("MstDepartment.jsp");
				
			}
			if("GO".equalsIgnoreCase(btnclick)){
				
				MstDepartment mstDepartmentBean = rdao.getDepartmentData(DEPTCODE);
			
				//add sbean to session for search parameters
				session.setAttribute("sbean", sbean);
				//	
				req.setAttribute("mstDepartmentBean", mstDepartmentBean);
				req.setAttribute("btnclick", btnclick);
				
				req.getRequestDispatcher("MstDepartment.jsp").forward(req, res);
			}
			if("S".equalsIgnoreCase(btnclick))
			{
				String isDataSaved = rdao.saveDepartmentID(bn);
			
				//add sbean to session for search parameters
				session.setAttribute("sbean", sbean);
				//
				session.setAttribute("msg", isDataSaved);
				res.sendRedirect("MstDepartment.jsp");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}