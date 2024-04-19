package in.org.cris.mrsectt.controller;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogOffController extends HttpServlet implements Servlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LogOffController() {
		super();
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0,
	 * HttpServletResponse arg1)
	 */
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(arg0, arg1);
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0,
	 * HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		
		//System.out.println("LogOffController.doPost()");

		HttpSession session = req.getSession(false);
		if (session != null) {

//			MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
//			String loginid = sessionBean.getLOGINID();

			/*Enumeration<Object> sessionEnum =  session.getAttributeNames();
			while (sessionEnum.hasMoreElements()) {
				Object object = (Object) sessionEnum.nextElement();
				session.removeAttribute(object);
			}*/
			
			
			session.setAttribute("MstLogin", null);
			session.removeAttribute("MstLogin");

			session.invalidate();
			//new LoginDAO().recordLogoff(loginid, session.getId());
		}
		if (req.getAttribute("sessionmsg") == null) {
			req.setAttribute("sessionmsg", "Logged off successfully");
		}

		// req.getRequestDispatcher("/Login.jsp").forward(req,res);
		res.sendRedirect("Login.jsp");

	}

}