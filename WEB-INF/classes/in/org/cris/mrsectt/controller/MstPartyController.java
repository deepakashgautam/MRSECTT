package in.org.cris.mrsectt.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstParty;
import in.org.cris.mrsectt.dao.MstPartyDAO;
import in.org.cris.mrsectt.util.StringFormat;

/**
 * Servlet implementation class MstPartyController
 */
public class MstPartyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MstPartyController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/MstParty.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session =   request.getSession();
		CommonBean sbean = new CommonBean();
		MstParty bn = new MstParty();
		MstPartyDAO rdao = new MstPartyDAO();
		
		String btnclick = StringFormat.nullString(request.getParameter("btnclick"));
		String PARTYCODE = StringFormat.nullString(request.getParameter("PARTYCODE"));
		//System.out.println("partycode controller :"+PARTYCODE);
		String PARTYNAME = StringFormat.nullString(request.getParameter("PARTYNAME"));
		
		String sPARTYCODE = StringFormat.nullString(request.getParameter("sPARTYCODE"));
		String sPARTYNAME = StringFormat.nullString(request.getParameter("sPARTYNAME"));
		
		sbean.setField1(sPARTYCODE);
		sbean.setField2(sPARTYNAME);
		
		bn.setPartycode(PARTYCODE);
		bn.setName(PARTYNAME);
		
		if ("C".equalsIgnoreCase(btnclick)) {
			//add sbean to session for search parameters
			session.removeAttribute("sbean");
			//
			response.sendRedirect("MstParty.jsp");
		}
		if("GO".equalsIgnoreCase(btnclick)){
			
			MstParty mstPartyBean = rdao.getVIPParty(PARTYCODE);
			
			session.setAttribute("sbean", sbean);
			request.setAttribute("mstPartyBean", mstPartyBean);
			
			request.getRequestDispatcher("MstParty.jsp").forward(request, response);
		}
		if("S".equalsIgnoreCase(btnclick))
		{
			String isDataSaved = rdao.saveVIPParty(bn);
			session.setAttribute("sbean", sbean);
			session.setAttribute("msg", isDataSaved);

			response.sendRedirect("MstParty.jsp");
		}
	}

}
