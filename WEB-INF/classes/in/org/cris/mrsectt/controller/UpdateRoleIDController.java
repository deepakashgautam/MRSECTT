package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.dao.UpdateRoleIDDAO;
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
public class UpdateRoleIDController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateRoleIDController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doPost(arg0, arg1);
		req.getRequestDispatcher("/UpdateRoleID.jsp").forward(req, res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			HttpSession session = req.getSession();
			UpdateRoleIDDAO rdao = new UpdateRoleIDDAO();
			
			String btnclick = StringFormat.nullString(req.getParameter("btnclick"));
			String[] arrOROLEID = req.getParameterValues("OROLEID");
			String[] arrOROLENAME = req.getParameterValues("OROLENAME");
			String[] arrNROLEID = req.getParameterValues("NROLEID");
			String[] arrNROLENAME = req.getParameterValues("NROLENAME");

			if ("C".equalsIgnoreCase(btnclick)) {
				res.sendRedirect("UpdateRoleID.jsp");
			}
			if("S".equalsIgnoreCase(btnclick))
			{
				String isDataSaved = rdao.saveSubject(arrOROLEID, arrOROLENAME, arrNROLEID, arrNROLENAME);
				session.setAttribute("msg", isDataSaved);
				res.sendRedirect("UpdateRoleID.jsp");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}