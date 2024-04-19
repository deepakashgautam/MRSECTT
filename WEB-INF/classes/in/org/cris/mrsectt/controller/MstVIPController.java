package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstVIP;
import in.org.cris.mrsectt.dao.MstVIPDAO;
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
public class MstVIPController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MstVIPController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doPost(arg0, arg1);
		req.getRequestDispatcher("/MstVIP.jsp").forward(req, res);
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
			MstVIP bn = new MstVIP();
			MstVIPDAO rdao = new MstVIPDAO();
			
			String btnclick = StringFormat.nullString(req.getParameter("btnclick"));
			String VIPID = StringFormat.nullString(req.getParameter("VIPID"));
			String VIPNAME = StringFormat.nullString(req.getParameter("VIPNAME"));
			String STATECODE = StringFormat.nullString(req.getParameter("STATECODE"));
			String VIPSTATUS = StringFormat.nullString(req.getParameter("VIPSTATUS"));
			String VIPCONSTITUENCY = StringFormat.nullString(req.getParameter("VIPCONSTITUENCY"));
			String VIPPARTY = StringFormat.nullString(req.getParameter("PARTYCODE"));
			String VIPADDRESS = StringFormat.nullString(req.getParameter("VIPADDRESS"));
			String sVIPID = StringFormat.nullString(req.getParameter("sVIPID"));
			String sVIPNAME = StringFormat.nullString(req.getParameter("sVIPNAME"));
			String sSTATECODE = StringFormat.nullString(req.getParameter("sSTATECODE"));
			String sVIPSTATUS = StringFormat.nullString(req.getParameter("sVIPSTATUS"));
			String sVIPCONSTITUENCY = StringFormat.nullString(req.getParameter("sVIPCONSTITUENCY"));
			String sVIPPARTY = StringFormat.nullString(req.getParameter("sVIPPARTY"));
			
			String MOBILENO = StringFormat.nullString(req.getParameter("MOBILENO"));
			String CONSTID = StringFormat.nullString(req.getParameter("CONSTID"));
			sbean.setField1(sVIPID);
			sbean.setField2(sVIPNAME);
			sbean.setField3(sSTATECODE);
			sbean.setField3(sVIPSTATUS);
			sbean.setField3(sVIPCONSTITUENCY);
			sbean.setField3(sVIPPARTY);
			
			bn.setVIPID(VIPID);
			bn.setVIPNAME(VIPNAME);
			bn.setSTATECODE(STATECODE);
			bn.setVIPSTATUS(VIPSTATUS);
			bn.setVIPCONSTITUENCY(VIPCONSTITUENCY);
			bn.setVIPPARTY(VIPPARTY);
			bn.setVIPADDRESS(VIPADDRESS);
			bn.setMOBILENO(MOBILENO);
			bn.setCONSTID(CONSTID);
		
			if ("C".equalsIgnoreCase(btnclick)) {
				//add sbean to session for search parameters
				session.removeAttribute("sbean");
				//
				res.sendRedirect("MstVIP.jsp");
			}
			if("GO".equalsIgnoreCase(btnclick)){
				
				MstVIP mstVIPBean = rdao.getVIPData(VIPID);
				
				session.setAttribute("sbean", sbean);
				req.setAttribute("mstVIPBean", mstVIPBean);
				
				req.getRequestDispatcher("MstVIP.jsp").forward(req, res);
			}
			if("S".equalsIgnoreCase(btnclick))
			{
				String isDataSaved = rdao.saveVIPID(bn);
				session.setAttribute("sbean", sbean);
				session.setAttribute("msg", isDataSaved);

				res.sendRedirect("MstVIP.jsp");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}