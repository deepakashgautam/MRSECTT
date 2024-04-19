package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstLogin;
import in.org.cris.mrsectt.dao.ReportFileNoDAO;
import in.org.cris.mrsectt.dao.SummaryReportDAO;
import in.org.cris.mrsectt.util.StringFormat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class GeneralReports_FileMovController_uncrypted extends HttpServlet {
	private static final long serialVersionUID = 1L;
    int rNumber = 0;    
    
    public GeneralReports_FileMovController_uncrypted() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String reportNumber = request.getParameter("reportNumber")!=null ? request.getParameter("reportNumber") : "";
		if(reportNumber!="") rNumber = Integer.parseInt(request.getParameter("reportNumber"));
		String regDate = request.getParameter("INDATE_DRPDWN")!=null ? request.getParameter("INDATE_DRPDWN") : "";
		String referenceId = StringFormat.nullString(request.getParameter("referenceId"));
		//System.out.println("rNumber : "+ rNumber);
		//System.out.println("referenceId : "+ referenceId);
		String conf = request.getParameter("conf")!=null ? request.getParameter("conf") : "";
		String reply = request.getParameter("reply")!=null ? request.getParameter("reply") : "";
		String mfinal = request.getParameter("mfinal")!=null ? request.getParameter("mfinal") : "";
		
		String btnClick = StringFormat.nullString(request.getParameter("btnClick"));
		String serverpath = request.getSession().getAttribute("filepath") != null ? request.getSession().getAttribute("filepath").toString(): "/opt/";
		//System.out.println("serverpath :" + serverpath);
		
		try {
			if (rNumber == 1) {
				HttpSession session = request.getSession();
				MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
				
				request.setAttribute("regDate", regDate);
				ReportFileNoDAO rdao = new ReportFileNoDAO();
				
				ArrayList<CommonBean> TrnFileHdr = rdao.getTrnFileHdr(regDate,sessionBean.getLOGINASROLEID(),sessionBean.getISCONF(),sessionBean.getISREPLY());
				request.setAttribute("TrnFileHdr", TrnFileHdr);

				ArrayList<CommonBean> TrnFileRef = rdao.getTrnFileRef(regDate,sessionBean.getLOGINASROLEID());
				request.setAttribute("TrnFileRef", TrnFileRef);

				ArrayList<CommonBean> TrnFileMarking = rdao.getTrnFileMarking(regDate,sessionBean.getLOGINASROLEID());
				request.setAttribute("TrnFileMarking", TrnFileMarking);

				ArrayList<CommonBean> TrnFileIntMarking = rdao.getTrnFileIntMarking(regDate,sessionBean.getLOGINASROLEID());
				request.setAttribute("TrnFileIntMarking", TrnFileIntMarking);

				if(TrnFileHdr.size()<=0)
				{
					String var="No Record found with specific parameters...";
					request.setAttribute("detailarr", TrnFileHdr);
					getServletContext().getRequestDispatcher("ReportFileNoOutput.jsp?var="+var+"").forward(request, response);
				} else{
					request.setAttribute("detailarr", TrnFileHdr);
					getServletContext().getRequestDispatcher("ReportFileNoOutput.jsp").forward(request, response);
				}}

			if (rNumber == 2) {
				String regnofrom = StringFormat.nullString(request.getParameter("regnofrom"));
				String regnoto = StringFormat.nullString(request.getParameter("regnoto"));
				String datefrom = StringFormat.nullString(request.getParameter("datefrom"));
				String dateto = StringFormat.nullString(request.getParameter("dateto"));
				String rplyType = StringFormat.nullString(request.getParameter("REPLYTYPE"));
				
				HttpSession session = request.getSession();
				MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
				
				request.setAttribute("regnofrom", regnofrom);
				request.setAttribute("regnoto", regnoto);
				request.setAttribute("datefrom", datefrom);
				request.setAttribute("dateto", dateto);
				request.setAttribute("rplyType", rplyType);
				ReportFileNoDAO rdao = new ReportFileNoDAO();
				
				
				ArrayList<CommonBean> beanSerialNoFromTo = rdao.getReportSerialNoFromTo(regnofrom, regnoto, datefrom, dateto, rplyType, sessionBean.getLOGINASROLEID(), sessionBean.getISCONF(),sessionBean.getISREPLY(),conf,reply);
				if(beanSerialNoFromTo.size()<=0)
				{
					String var="No Record found with specific parameters...";
					getServletContext().getRequestDispatcher("SerialNoFromTo.jsp?var="+var+"").forward(request, response);
				} else{
					request.setAttribute("beanSerialNoFromTo", beanSerialNoFromTo);
					getServletContext().getRequestDispatcher("SerialNoFromTo.jsp").forward(request, response);
				}}

			if (rNumber == 3) {
				//System.out.println("suneel");
				String dateFrom = StringFormat.nullString(request.getParameter("datefrom"));
				String dateTo = StringFormat.nullString(request.getParameter("dateto"));
				String fileType = StringFormat.nullString(request.getParameter("fileType"));
				String iMarkingTo = StringFormat.nullString(request.getParameter("markto"));
				//System.out.println(iMarkingTo);
				String fStatus1 = StringFormat.nullString(request.getParameter("FILESTATUS1"));
				//System.out.println(fStatus1);
				String fStatus2 = StringFormat.nullString(request.getParameter("FILESTATUS2"));
				//System.out.println(fStatus2);
				String rplyType = StringFormat.nullString(request.getParameter("REPLYTYPE"));
				String markingDateFrom = StringFormat.nullString(request.getParameter("markingdatefrom"));
				String markingDateTo = StringFormat.nullString(request.getParameter("markingdateto"));
				HttpSession session = request.getSession();
				MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
				
				request.setAttribute("datefrom", dateFrom);
				request.setAttribute("dateto", dateTo);
				request.setAttribute("fileType", fileType);
				request.setAttribute("iMarkingTo", iMarkingTo);
				request.setAttribute("fStatus1", fStatus1);
				request.setAttribute("fStatus2", fStatus2);
				request.setAttribute("rplyType", rplyType);
				request.setAttribute("markingdatefrom", markingDateFrom);
				request.setAttribute("markingdateto", markingDateTo);
				ReportFileNoDAO rdao = new ReportFileNoDAO();
				
				ArrayList<CommonBean> beanSerialNoFromTo = rdao.getReportSerialNoFromTo_2(dateFrom, dateTo, rplyType, fileType, iMarkingTo, fStatus1, fStatus2, sessionBean.getLOGINASROLEID(), sessionBean.getISCONF(), sessionBean.getISREPLY(),conf,mfinal,reply,markingDateFrom, markingDateTo);
				if(beanSerialNoFromTo.size()<=0)
				{
					String var="No Record found with specific parameters...";
					getServletContext().getRequestDispatcher("SerialNoFromTo_2.jsp?var="+var+"").forward(request, response);
				} else{
					request.setAttribute("beanSerialNoFromTo", beanSerialNoFromTo);
					getServletContext().getRequestDispatcher("SerialNoFromTo_2.jsp").forward(request, response);
				}}
//			For Peon Book FM
			if (rNumber == 4) {
				String datefrom = StringFormat.nullString(request.getParameter("datefrom"));
				String dateto = StringFormat.nullString(request.getParameter("dateto"));
				String markinguser = StringFormat.nullString(request.getParameter("MARKINGUSER"));
				HttpSession session = request.getSession();
				MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
				
				request.setAttribute("datefrom", datefrom);
				request.setAttribute("dateto", dateto);
				request.setAttribute("MARKINGUSER", markinguser);
				ReportFileNoDAO rdao = new ReportFileNoDAO();
				
				ArrayList<CommonBean> bnPeonBook = rdao.getReportPeonBook(datefrom, dateto, sessionBean.getLOGINASROLEID(),markinguser);
				ArrayList<CommonBean> bnPeonBookTotal = rdao.getReportPeonBookTotal(datefrom, dateto, sessionBean.getLOGINASROLEID(),markinguser);
				if(bnPeonBook.size()<=0)
				{
					String var="No Record found with specific parameters...";
					getServletContext().getRequestDispatcher("PeonBookFM.jsp?var="+var+"").forward(request, response);
				} else{
					request.setAttribute("bnPeonBook", bnPeonBook);
					request.setAttribute("bnPeonBookTotal", bnPeonBookTotal);
					getServletContext().getRequestDispatcher("PeonBookFM.jsp").forward(request, response);
				}
			}
			if (rNumber == 5) {
				HttpSession session = request.getSession();
				MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
				
				request.setAttribute("referenceId", referenceId);
				ReportFileNoDAO rdao = new ReportFileNoDAO();
				
				String fmID = rdao.getFMId(referenceId);
				
				ArrayList<CommonBean> TrnFileHdr = rdao.getTrnFileHdrPopup(fmID,sessionBean.getLOGINASROLEID());
				request.setAttribute("TrnFileHdr", TrnFileHdr);

				ArrayList<CommonBean> TrnFileRef = rdao.getTrnFileRef(fmID);
				request.setAttribute("TrnFileRef", TrnFileRef);

				ArrayList<CommonBean> TrnFileMarking = rdao.getTrnFileMarking(fmID);
				request.setAttribute("TrnFileMarking", TrnFileMarking);

				ArrayList<CommonBean> TrnFileIntMarking = rdao.getTrnFileIntMarking(fmID);
				request.setAttribute("TrnFileIntMarking", TrnFileIntMarking);

				if(TrnFileHdr.size()<=0)
				{
					String var="No Record found with specific parameters...";
					request.setAttribute("detailarr", TrnFileHdr);
					getServletContext().getRequestDispatcher("ReportFileNoOutput.jsp?var="+var+"").forward(request, response);
				} else{
					request.setAttribute("detailarr", TrnFileHdr);
					getServletContext().getRequestDispatcher("ReportFileNoOutput.jsp").forward(request, response);
				}}
// For pending approval files
			if (rNumber == 6) {
				//System.out.println("suneel");
				String dateFrom = StringFormat.nullString(request.getParameter("datefrom"));
				String dateTo = StringFormat.nullString(request.getParameter("dateto"));
				String markingDateFrom = StringFormat.nullString(request.getParameter("markingdatefrom"));
				String markingDateTo = StringFormat.nullString(request.getParameter("markingdateto"));
				//String fileType = StringFormat.nullString(request.getParameter("fileType"));
				String fileType = "A";
//				String iMarkingTo = StringFormat.nullString(request.getParameter("markto"));
//				String fStatus1 = StringFormat.nullString(request.getParameter("FILESTATUS1"));
//				String fStatus2 = StringFormat.nullString(request.getParameter("FILESTATUS2"));
				
				String iMarkingTo = "46";
				//System.out.println(iMarkingTo);
				String fStatus1 = "1";
				//System.out.println(fStatus1);
				String fStatus2 = "3";
				//System.out.println(fStatus2);
				String rplyType = StringFormat.nullString(request.getParameter("REPLYTYPE"));
				
				HttpSession session = request.getSession();
				MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
				
				request.setAttribute("datefrom", dateFrom);
				request.setAttribute("dateto", dateTo);
				request.setAttribute("fileType", fileType);
				request.setAttribute("iMarkingTo", iMarkingTo);
				request.setAttribute("fStatus1", fStatus1);
				request.setAttribute("fStatus2", fStatus2);
				request.setAttribute("rplyType", rplyType);
				request.setAttribute("markingdatefrom", markingDateFrom);
				request.setAttribute("markingdateto", markingDateTo);
				ReportFileNoDAO rdao = new ReportFileNoDAO();
				
				ArrayList<CommonBean> beanSerialNoFromTo = rdao.getReportSerialNoFromTo_2(dateFrom, dateTo, rplyType, fileType, iMarkingTo, fStatus1, fStatus2, sessionBean.getLOGINASROLEID(), sessionBean.getISCONF(), sessionBean.getISREPLY(),conf,mfinal,reply,markingDateFrom, markingDateTo);
				if(beanSerialNoFromTo.size()<=0)
				{
					String var="No Record found with specific parameters...";
					getServletContext().getRequestDispatcher("FileReport_Pending_Approval.jsp?var="+var+"").forward(request, response);
				} else{
					request.setAttribute("beanSerialNoFromTo", beanSerialNoFromTo);
					getServletContext().getRequestDispatcher("FileReport_Pending_Approval.jsp").forward(request, response);
				}}
			
			/*if (rNumber == 6) {
				
				String RepPath = "";
				
				RepPath = new ReportFileNoDAO().generateSummaryReport(serverpath);
			
			File target = new File(RepPath);
			OutputStream output = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition:","attachment;filename= MRSECTT_FilesSummary" + ".xls;");
			response.setHeader("Cache-Control", "must-revalidate");
			BufferedInputStream fin = new BufferedInputStream(new FileInputStream(RepPath));
			int ch = 0;
			while ((ch = fin.read()) != -1) {
				output.write(ch);
			}
			output.flush();
			output.close();
			fin.close();
			target.delete();
				
				
			}*/
		}
		catch (Exception e) {
				e.printStackTrace();
		}	
		finally {
		}
	}
}