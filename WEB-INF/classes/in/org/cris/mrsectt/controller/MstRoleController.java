package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstRole;
import in.org.cris.mrsectt.dao.MstRoleDAO;
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
public class MstRoleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MstRoleController() {
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
			CommonBean sbeanROLE = new CommonBean();
			
			String ROLEID = StringFormat.nullString(req.getParameter("ROLEID"));
			String ROLENAME = StringFormat.nullString(req.getParameter("ROLENAME1"));
			String DEPTCODE = StringFormat.nullString(req.getParameter("DEPTCODE"));
			String AUTHLEVEL = StringFormat.nullString(req.getParameter("AUTHLEVEL"));
			String FNAME = StringFormat.nullString(req.getParameter("FNAME1"));
			
			String btnclick = StringFormat.nullString(req.getParameter("btnclick"));
			
			//////get and set search parameters 
			String SEARCHROLEIDFROM = StringFormat.nullString(req.getParameter("SEARCHROLEIDFROM"));
			String SEARCHROLEIDTO = StringFormat.nullString(req.getParameter("SEARCHROLEIDTO"));
			String SEARCHROLENAME = StringFormat.nullString(req.getParameter("SEARCHROLENAME"));
			String SEARCHDEPT = StringFormat.nullString(req.getParameter("SEARCHDEPT"));
			String SEARCHAUTH = StringFormat.nullString(req.getParameter("SEARCHAUTH"));
			
			sbeanROLE.setField1(SEARCHROLEIDFROM);
			sbeanROLE.setField2(SEARCHROLEIDTO);
			sbeanROLE.setField3(SEARCHROLENAME);
			sbeanROLE.setField4(SEARCHDEPT);
			sbeanROLE.setField5(SEARCHAUTH);
			//////
			
			
			MstRole bn = new MstRole();
			MstRoleDAO rdao = new MstRoleDAO();
			bn.setROLEID(ROLEID);
			bn.setROLENAME(ROLENAME);
			bn.setDEPTCODE(DEPTCODE);
			bn.setAUTHLEVEL(AUTHLEVEL);
			bn.setFNAME(FNAME);
			
			if ("C".equalsIgnoreCase(btnclick)) {
				
				//add sbean to session for search parameters
				session.removeAttribute("mstRoleBean");
				session.removeAttribute("sbeanROLE");
				//
				res.sendRedirect("MstRole.jsp");
				
			}
			
			if("GO".equalsIgnoreCase(btnclick)){
				
				MstRole mstRoleBean = rdao.getRoleData(ROLEID);
			
				//add sbean to session for search parameters
				session.setAttribute("sbeanROLE", sbeanROLE);
				//	
				req.setAttribute("mstRoleBean", mstRoleBean);
				req.setAttribute("btnclick", btnclick);
				
				req.getRequestDispatcher("MstRole.jsp").forward(req, res);
			}
			
			if("S".equalsIgnoreCase(btnclick))
			{
				String isDataSaved = rdao.saveRoleID(bn);
			
				//add sbean to session for search parameters
				session.setAttribute("sbeanROLE", sbeanROLE);
				//
				session.setAttribute("msg", isDataSaved);
				res.sendRedirect("MstRole.jsp");
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}