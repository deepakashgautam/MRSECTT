package in.org.cris.mrsectt.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.org.cris.mrsectt.dao.MenuAdministrationDAO;

/**
 * Servlet implementation class MenuGenerationController
 */
public class MenuGenerationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MenuGenerationController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String parentid=request.getParameter("txtParentMenuid");
		String nodalflag=request.getParameter("cmbNodalFlag");
		String menutext=request.getParameter("txtMenuText");
		String url=request.getParameter("txtURL");
		String groupid="RFMS";
		
		MenuAdministrationDAO dao=new MenuAdministrationDAO();
		dao.insertMenu(parentid,nodalflag,menutext,url,groupid); 
		
		

		request.getRequestDispatcher("/MenuGeneration.jsp").forward(request,
				response);
	}

}
