package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstLogin;
//import in.org.cris.mrsectt.Beans.MstRole;
import in.org.cris.mrsectt.dao.MstLoginDAO;
import in.org.cris.mrsectt.util.StringFormat;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Servlet implementation class MstRoleController
 */
public class MstLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MstLoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doPost(arg0, arg1);
		req.getRequestDispatcher("/MstLogin.jsp").forward(req, res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			HttpSession session = req.getSession();
			
			String btnclk = req.getParameter("btnclick") != null ? req.getParameter("btnclick") : "";
			
			String LOGINID = StringFormat.nullString(req.getParameter("LOGINID"));                                               
			String PASSWORD = StringFormat.nullString(req.getParameter("PASSWORD"));                                             
			String LOGINASROLEID = StringFormat.nullString(req.getParameter("ROLEID"));                                   
			String ISACTIVELOGIN = StringFormat.nullString(req.getParameter("ISACTIVELOGIN"));                                   
			String LASTLOGINDATE = StringFormat.nullString(req.getParameter("LASTLOGINDATE"));                                   
			String STARTDATE = StringFormat.nullString(req.getParameter("STARTDATE"));                                           
			String ENDDATE = StringFormat.nullString(req.getParameter("ENDDATE"));                                               
			String LOGINDESIGNATION = StringFormat.nullString(req.getParameter("LOGINDESIGNATION"));                             
			String LOGINNAME = StringFormat.nullString(req.getParameter("LOGINNAME"));
			String ISCONF = StringFormat.nullString(req.getParameter("ISCONF"));
			String ISREPLY = StringFormat.nullString(req.getParameter("ISREPLY"));
			String THEMECOLOR = StringFormat.nullString(req.getParameter("THEMECOLOR"));
			
			MstLogin bn = new MstLogin();
			MstLoginDAO rdao = new MstLoginDAO();
			CommonBean sbean = new CommonBean();
			
			bn.setLOGINID(LOGINID);                                               
			bn.setPASSWORD(PASSWORD);                                             
			bn.setLOGINASROLEID(LOGINASROLEID);                                   
			bn.setISACTIVELOGIN(ISACTIVELOGIN);                                   
			bn.setLASTLOGINDATE(LASTLOGINDATE);                                   
			bn.setSTARTDATE(STARTDATE);                                           
			bn.setENDDATE(ENDDATE);                                               
			bn.setLOGINDESIGNATION(LOGINDESIGNATION);                             
			bn.setLOGINNAME(LOGINNAME);
			bn.setISCONF(ISCONF);
			bn.setISREPLY(ISREPLY);
			bn.setTHEMECOLOR(THEMECOLOR);
			
		//////get and set search parameters 
			String sLOGINID = StringFormat.nullString(req.getParameter("sLOGINID"));
			String sLOGINNAME = StringFormat.nullString(req.getParameter("sLOGINNAME"));
			String sLOGINDESIGNATION = StringFormat.nullString(req.getParameter("sLOGINDESIGNATION"));
			String sROLEID = StringFormat.nullString(req.getParameter("sROLEID"));
			
			sbean.setField1(sLOGINID);
			sbean.setField2(sLOGINNAME);
			sbean.setField3(sLOGINDESIGNATION);
			sbean.setField4(sROLEID);
			//////
			
			if ("C".equalsIgnoreCase(btnclk)) {
				
				//add sbean to session for search parameters
				session.removeAttribute("sbean");
				session.removeAttribute("mstLoginBean");
				//
				res.sendRedirect("MstLogin.jsp");
			}
			if("GO".equalsIgnoreCase(btnclk)){
				MstLogin mstLoginBean = rdao.getLoginData(LOGINID);
				 
				session.setAttribute("sbean", sbean);
				req.setAttribute("mstLoginBean", mstLoginBean);

				req.getRequestDispatcher("MstLogin.jsp").forward(req, res);
			}

			if("S".equalsIgnoreCase(btnclk))
			{
				String isDataSaved = rdao.saveLoginID(bn);
				//add sbean to session for search parameters
				session.setAttribute("sbean", sbean);
				
				session.setAttribute("msg", isDataSaved);
				res.sendRedirect("MstLogin.jsp");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}