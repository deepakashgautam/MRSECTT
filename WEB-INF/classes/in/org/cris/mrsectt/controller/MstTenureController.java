package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstTenure;
import in.org.cris.mrsectt.dao.MstTenureDAO;
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
public class MstTenureController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MstTenureController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doPost(arg0, arg1);
		req.getRequestDispatcher("/MstTenure.jsp").forward(req, res);
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
			CommonBean sbean = new CommonBean();
			
			String TENUREID = StringFormat.nullString(req.getParameter("TENUREID"));
			String ROLEID = StringFormat.nullString(req.getParameter("ROLEID"));
			String YEAR = StringFormat.nullString(req.getParameter("YEAR"));
			String NAME = StringFormat.nullString(req.getParameter("NAME"));
			String TENUREOFFICENAME = StringFormat.nullString(req.getParameter("TENUREOFFICENAME"));
			String ISACTIVE = StringFormat.nullString(req.getParameter("ISACTIVE"));
			String TENURESTARTDATE = StringFormat.nullString(req.getParameter("TENURESTARTDATE"));
			String TENUREENDDATE = StringFormat.nullString(req.getParameter("TENUREENDDATE"));			
			String TENUREOFFICEADDRESS = StringFormat.nullString(req.getParameter("TENUREOFFICEADDRESS"));
			
			String sTENUREID = StringFormat.nullString(req.getParameter("sTENUREID"));
			String sNAME = StringFormat.nullString(req.getParameter("sNAME"));
			String sYEAR = StringFormat.nullString(req.getParameter("sYEAR"));
			
			sbean.setField1(sTENUREID);
			sbean.setField2(sNAME);
			sbean.setField3(sYEAR);
			
			String[] arrREFCLASS = req.getParameterValues("REFCLASS");
			String[] arrINOUT = req.getParameterValues("INOUT");
			String[] arrCLASSDESCRIPTION = req.getParameterValues("CLASSDESCRIPTION");
			String[] arrISNEW = req.getParameterValues("ISNEW");
			
			MstTenure bn = new MstTenure();
			
			MstTenureDAO rdao = new MstTenureDAO();
			bn.setTENUREID(TENUREID);
			bn.setROLEID(ROLEID);
			bn.setYEAR(YEAR);
			bn.setNAME(NAME);
			bn.setTENUREOFFICENAME(TENUREOFFICENAME);
			bn.setISACTIVE(ISACTIVE);
			bn.setTENURESTARTDATE(TENURESTARTDATE);
			bn.setTENUREENDDATE(TENUREENDDATE);
			bn.setTENUREOFFICEADDRESS(TENUREOFFICEADDRESS);
			
			if ("C".equalsIgnoreCase(btnclk)) {
				
				//add sbean to session for search parameters
				session.removeAttribute("sbean");
				session.removeAttribute("mstTenureBean");
				//
				res.sendRedirect("MstTenure.jsp");
				
			}
			if("GO".equalsIgnoreCase(btnclk)){
				MstTenure mstTenureBean = rdao.getTenureData(TENUREID);
				
				session.setAttribute("sbean", sbean);
				req.setAttribute("mstTenureBean", mstTenureBean);
				
				req.getRequestDispatcher("MstTenure.jsp").forward(req, res);
			}
			if("S".equalsIgnoreCase(btnclk))
			{
				String isDataSaved = rdao.saveTenureID(bn, arrREFCLASS, arrINOUT, arrCLASSDESCRIPTION, arrISNEW);
				session.setAttribute("sbean", sbean);
				session.setAttribute("msg", isDataSaved);
				res.sendRedirect("MstTenure.jsp");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}