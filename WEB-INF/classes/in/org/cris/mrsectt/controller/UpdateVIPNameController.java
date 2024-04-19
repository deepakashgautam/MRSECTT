package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.UpdateVIPName;
import in.org.cris.mrsectt.dao.UpdateVIPNameDAO;
import in.org.cris.mrsectt.util.StringFormat;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MstRoleController
 */
public class UpdateVIPNameController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateVIPNameController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doPost(arg0, arg1);
		req.getRequestDispatcher("/UpdateVIPName.jsp").forward(req, res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			HttpSession session = req.getSession();
			UpdateVIPNameDAO rdao = new UpdateVIPNameDAO();
			
			String btnclick = StringFormat.nullString(req.getParameter("btnclick"));
			String REFERENCENAME = StringFormat.nullString(req.getParameter("REFERENCENAME"));
			
			String[] arrOLDNAME = req.getParameterValues("OLDNAME");
			String[] arrOLDSTATUS = req.getParameterValues("OLDSTATUS");
			String[] arrOLDPARTY = req.getParameterValues("OLDPARTY");
			String[] arrOLDSTATE = req.getParameterValues("OLDSTATE");
			String[] arrNEWNAME = req.getParameterValues("NEWNAME");
			String[] arrNEWSTATUS = req.getParameterValues("NEWSTATUS");
			String[] arrNEWPARTY = req.getParameterValues("NEWPARTY");
			String[] arrNEWSTATE = req.getParameterValues("NEWSTATE");
			
			String[] arrVIPID = req.getParameterValues("VIPID");
			String[] arrVIPADDRESS = req.getParameterValues("VIPADDRESS");
			
			ArrayList<UpdateVIPName> bn = new ArrayList<UpdateVIPName>();

			if("GO".equalsIgnoreCase(btnclick))
			{
				bn = rdao.getData(REFERENCENAME);
			//	session.setAttribute("msg", isDataSaved);
				session.setAttribute("bn", bn);
				res.sendRedirect("UpdateVIPName.jsp");
			}
			if ("C".equalsIgnoreCase(btnclick)) {
				res.sendRedirect("UpdateVIPName.jsp");
			}
			if("S".equalsIgnoreCase(btnclick))
			{
				String isDataSaved = rdao.saveSubject(arrOLDNAME, arrOLDSTATUS, arrOLDSTATE, arrNEWNAME, arrNEWSTATUS, arrNEWSTATE,arrOLDPARTY,arrNEWPARTY,arrVIPID,arrVIPADDRESS);
				session.setAttribute("msg", isDataSaved);
				res.sendRedirect("UpdateVIPName.jsp");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}