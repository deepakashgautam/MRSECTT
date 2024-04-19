package in.org.cris.mrsectt.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.org.cris.mrsectt.dao.MenuAdministrationDAO;

/**
 * Servlet implementation class MenuAdministrationController
 */
public class MenuAdministrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MenuAdministrationController() {
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
		//System.out.println("In Controller");

		String formAction = request.getParameter("formAction");
		String menuid = request.getParameter("menuid");
		String flag = request.getParameter("flag");
		String groupid = request.getParameter("txtGroupId") != null ? request
				.getParameter("txtGroupId") : "";

		MenuAdministrationDAO dao = new MenuAdministrationDAO();
		if (groupid.length() > 0) {
			if (formAction.equalsIgnoreCase("ADD")) {
				dao.addMenu(groupid, menuid, flag);
			} else if (formAction.equalsIgnoreCase("REMOVE")) {
				dao.RemoveMenu(groupid, menuid, flag);
			}
		}
		request.getRequestDispatcher("/MenuAdministration.jsp").forward(
				request, response);
	}

}
