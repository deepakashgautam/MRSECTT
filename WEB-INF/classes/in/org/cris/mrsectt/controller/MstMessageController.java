package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.MstRole;
import in.org.cris.mrsectt.dao.MstRoleDAO;
import in.org.cris.mrsectt.util.StringFormat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MstRoleController
 */
public class MstMessageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MstMessageController() {
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
			String ROLEID = StringFormat.nullString(req.getParameter("ROLEID"));
			String ROLENAME = StringFormat.nullString(req.getParameter("ROLENAME1"));
			String btnclick = StringFormat.nullString(req.getParameter("btnclick"));
			MstRole bn = new MstRole();
			MstRoleDAO rdao = new MstRoleDAO();
			
			bn.setROLEID(ROLEID);
			bn.setROLENAME(ROLENAME);
			
			if("GO".equalsIgnoreCase(btnclick)){
				
				MstRole mstRoleBean = rdao.getRoleData(ROLEID);
				 
				req.setAttribute("mstRoleBean", mstRoleBean);
				req.getRequestDispatcher("MstRole.jsp").forward(req, res);
				 
			}
			
			
			if("N".equalsIgnoreCase(btnclick))
			{
				String isDataSaved = rdao.saveRoleID(bn);
				if("T".equalsIgnoreCase(isDataSaved))
				{
					isDataSaved = "Save Operation Successfull...";
				}
				else if("F".equalsIgnoreCase(isDataSaved))
				{
					isDataSaved = "Save Operation failuare. Role ID alredy exist...";
				}
//				req.setAttribute("isDataSaved", isDataSaved);
				req.getRequestDispatcher("/MstRole.jsp?isDataSaved="+isDataSaved).forward(req, res);
			}
			if("S".equalsIgnoreCase(btnclick))
			{
				String isDataSaved = rdao.saveRoleID(bn);
				if("T".equalsIgnoreCase(isDataSaved))
				{
					isDataSaved = "Save Operation Successfull...";
				}
				else if("F".equalsIgnoreCase(isDataSaved))
				{
					isDataSaved = "Save Operation failuare. Role ID alredy exist...";
				}
//				req.setAttribute("isDataSaved", isDataSaved);
				req.setAttribute("ROLEID", ROLEID);
				req.setAttribute("ROLENAME", ROLENAME);
				res.sendRedirect("MstRole.jsp?isDataSaved="+isDataSaved+"&ROLEID="+ROLEID+"&ROLENAME="+ROLENAME);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}