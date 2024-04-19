package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstLogin;
import in.org.cris.mrsectt.dao.InDate_RefClassDAO;
import in.org.cris.mrsectt.dao.InDate_RefClassRolewiseDAO;
import in.org.cris.mrsectt.dao.ReceivedFrom_StatsDAO;
import in.org.cris.mrsectt.util.StringFormat;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class StaticReportsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    int rNumber = 0;     
    public StaticReportsController() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
		
		String reportNumber = request.getParameter("reportNumber")!=null ? request.getParameter("reportNumber") : "";
		if(reportNumber!="") rNumber = Integer.parseInt(request.getParameter("reportNumber"));
		String searchfor = request.getParameter("searchfor")!=null ? request.getParameter("searchfor") : "";
		String diarynofrom = request.getParameter("diarynofrom")!=null ? request.getParameter("diarynofrom") : "";
		String diarynoto = request.getParameter("diarynoto")!=null ? request.getParameter("diarynoto") : "";
		String receivedfrom = request.getParameter("receivedfrom")!=null ? request.getParameter("receivedfrom") : "";
		String datefrom = request.getParameter("datefrom")!=null ? request.getParameter("datefrom") : "";
		String dateto = request.getParameter("dateto")!=null ? request.getParameter("dateto") : "";
		String refclass = request.getParameter("refclass")!=null ? request.getParameter("refclass") : "";
		String subjectcode = request.getParameter("subjectcode")!=null ? request.getParameter("subjectcode") : "";
		String markto = request.getParameter("markto")!=null ? request.getParameter("markto") : "";
		String tag = request.getParameter("tag")!=null ? request.getParameter("tag") : "";
		String refNo = request.getParameter("refNo")!=null ? request.getParameter("refNo") : "";
		String status = request.getParameter("status")!=null ? request.getParameter("status") : "";
		String statecode = request.getParameter("statecode")!=null ? request.getParameter("statecode") : "";
		String[] csubject={""};
		if(request.getParameterValues("csubject")!=null){			
			 csubject = request.getParameterValues("csubject") ;
		}
		try {
			if (rNumber == 1) {
				request.setAttribute("datefrom", datefrom);
				request.setAttribute("dateto", dateto);
				request.setAttribute("refclass", refclass);
				
				InDate_RefClassRolewiseDAO rdao = new InDate_RefClassRolewiseDAO();
				
				ArrayList<ArrayList<String>> arrCB = rdao.getTotal(datefrom,dateto,refclass,sessionBean.getISCONF(),sessionBean.getLOGINASROLEID(),csubject);
				request.setAttribute("arrCB", arrCB);
			
				getServletContext().getRequestDispatcher("InDate_RefClassRolewise.jsp").forward(request, response);
			}
			if (rNumber == 2) {
				request.setAttribute("datefrom", datefrom);
				request.setAttribute("dateto", dateto);
				request.setAttribute("refclass", refclass);
				
				InDate_RefClassDAO rdao = new InDate_RefClassDAO();
				
				ArrayList<CommonBean> pendWithDt = rdao.getPendWithDt(datefrom,dateto,refclass,sessionBean.getISCONF(),csubject);
				request.setAttribute("pendWithDt", pendWithDt);
				
				ArrayList<CommonBean> underProcess = rdao.getUnderProcess(datefrom,dateto,refclass,sessionBean.getISCONF(),csubject);
				request.setAttribute("underProcess", underProcess);
				
				ArrayList<CommonBean> additionalInfo = rdao.getAdditionalInfo(datefrom,dateto,refclass,sessionBean.getISCONF(),csubject);
				request.setAttribute("additionalInfo", additionalInfo);

				ArrayList<CommonBean> total = rdao.getTotal(datefrom,dateto,refclass,sessionBean.getISCONF(),csubject);
				request.setAttribute("total", total);
			
				getServletContext().getRequestDispatcher("InDate_RefClass.jsp").forward(request, response);
			}
			if (rNumber == 3) {
				request.setAttribute("datefrom", datefrom);
				request.setAttribute("dateto", dateto);
				request.setAttribute("refclass", refclass);
				
				ReceivedFrom_StatsDAO rdao = new ReceivedFrom_StatsDAO();
				
				ArrayList<ArrayList<String>> arrCB3 = rdao.getTotal(datefrom,dateto,refclass,sessionBean.getISCONF(), status, statecode,csubject);
				request.setAttribute("arrCB3", arrCB3);
				
				getServletContext().getRequestDispatcher("ReceivedFrom_Stats.jsp").forward(request, response);
			}
			if (rNumber == 4) {
				request.setAttribute("datefrom", datefrom);
				request.setAttribute("dateto", dateto);
				request.setAttribute("refclass", refclass);
				
				InDate_RefClassRolewiseDAO rdao = new InDate_RefClassRolewiseDAO();
				
				ArrayList<ArrayList<String>> arrCB = rdao.getTotal_TargDateWise(datefrom,dateto,refclass,sessionBean.getISCONF(),sessionBean.getLOGINASROLEID(),csubject);
				request.setAttribute("arrCB", arrCB);
				
				getServletContext().getRequestDispatcher("TargDate_RefClassRolewise.jsp").forward(request, response);
			}
			if (rNumber == 5) {
				request.setAttribute("datefrom", datefrom);
				request.setAttribute("dateto", dateto);
				request.setAttribute("refclass", refclass);
				
				InDate_RefClassRolewiseDAO rdao = new InDate_RefClassRolewiseDAO();
				
				ArrayList<ArrayList<String>> arrCB = rdao.getTotal_RplyTypeWise(datefrom,dateto,refclass,sessionBean.getISCONF(),sessionBean.getLOGINASROLEID(),csubject);
				request.setAttribute("arrCB", arrCB);
				
				getServletContext().getRequestDispatcher("TargDate_RplyTypeRolewise.jsp").forward(request, response);
			}
			
		}
		catch (Exception e) {e.printStackTrace();}	
		finally {
		}
	}
}