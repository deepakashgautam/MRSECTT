package in.org.cris.mrsectt.controller;
import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.dao.ReportIncomingDateDAO;
import in.org.cris.mrsectt.util.StringFormat;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReportIncomingDateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    int rNumber = 0;
    public ReportIncomingDateController() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("-----ReportRefNoController-----");
		String dateFrom = StringFormat.nullString(request.getParameter("dateFrom"));
		String dateTo = StringFormat.nullString(request.getParameter("dateTo"));
		ArrayList<CommonBean> mainArr = new ArrayList<CommonBean>();
		try {
				HttpSession session = request.getSession();
				
				ReportIncomingDateDAO rdao = new ReportIncomingDateDAO();
				
				mainArr = rdao.getData(dateFrom,dateTo);
				session.setAttribute("mainArr", mainArr);
				session.setAttribute("dateFrom", dateFrom);
				session.setAttribute("dateTo", dateTo);
				response.sendRedirect("ReportIncomingDate.jsp");
		}
		catch (Exception e) {
				e.printStackTrace();
		}	
		finally {
		}
	}
}