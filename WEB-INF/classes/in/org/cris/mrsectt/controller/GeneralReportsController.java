package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstLogin;
import in.org.cris.mrsectt.dao.ReportRefNoDAO;
import in.org.cris.mrsectt.dao.StaticReportsDAO;
import in.org.cris.mrsectt.util.StringFormat;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GeneralReportsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    int rNumber = 0;    
    public GeneralReportsController() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("===8888888====step 0");
		HttpSession session = request.getSession();
		//System.out.println("=======step 0");
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
		String inDate = request.getParameter("INDATE_DRPDWN")!=null ? request.getParameter("INDATE_DRPDWN") : "";
		String ISCONF = StringFormat.nullString(request.getParameter("ISCONF"));
		String roleId = StringFormat.nullString(request.getParameter("ROLEID"));
		String replyType = StringFormat.nullString(request.getParameter("REPLYTYPE"));
		String reply = StringFormat.nullString(request.getParameter("reply"));
		request.setAttribute("ISCONF", ISCONF);
		try {
			if (rNumber == 1) {
				request.setAttribute("diarynofrom", diarynofrom);
				request.setAttribute("diarynoto", diarynoto);
				request.setAttribute("receivedfrom", receivedfrom);
				request.setAttribute("datefrom", datefrom);
				request.setAttribute("dateto", dateto);
//				request.setAttribute("refclass", refclass);
				request.setAttribute("subjectcode", subjectcode);
				request.setAttribute("markto", markto);
				request.setAttribute("tag", tag);
				request.setAttribute("replyType", replyType);
				StaticReportsDAO sdao = new StaticReportsDAO();
				ArrayList<CommonBean> detailarr = sdao.diaryFromTo(diarynofrom,diarynoto,receivedfrom,datefrom,dateto,subjectcode,markto,tag,replyType,ISCONF,roleId,sessionBean.getISREPLY(),reply);
				if(detailarr.size()<=0)
				{
					String var="No Record found with specific parameters...";
					request.setAttribute("detailarr", detailarr);
					getServletContext().getRequestDispatcher("DiaryReport.jsp?var="+var+"").forward(request, response);
				} else{
				request.setAttribute("detailarr", detailarr);
				getServletContext().getRequestDispatcher("DiaryReport.jsp").forward(request, response);
				}
			}
			if (rNumber == 2) {
				request.setAttribute("receivedfrom", receivedfrom);
				request.setAttribute("datefrom", datefrom);
				request.setAttribute("dateto", dateto);
				request.setAttribute("refclass", refclass);
				request.setAttribute("subjectcode", subjectcode);
				request.setAttribute("markto", markto);
				request.setAttribute("tag", tag);
				request.setAttribute("replyType", replyType);
				StaticReportsDAO sdao = new StaticReportsDAO();
				ArrayList<CommonBean> detailarr = sdao.RequestFrom(receivedfrom,datefrom,dateto,refclass,subjectcode,markto,tag,replyType,ISCONF,roleId,sessionBean.getISREPLY(),reply);
				if(detailarr.size()<=0)
				{
					String var="No Record found with specific parameters...";
					request.setAttribute("detailarr", detailarr);
					getServletContext().getRequestDispatcher("DiaryReport.jsp?var="+var+"").forward(request, response);
				} else{
				request.setAttribute("detailarr", detailarr);
				getServletContext().getRequestDispatcher("DiaryReport.jsp").forward(request, response);
			}}
			if (rNumber == 3) {
				request.setAttribute("searchfor", searchfor);
				request.setAttribute("receivedfrom", receivedfrom);
				request.setAttribute("datefrom", datefrom);
				request.setAttribute("dateto", dateto);
				request.setAttribute("refclass", refclass);
				request.setAttribute("subjectcode", subjectcode);
				request.setAttribute("markto", markto);
				request.setAttribute("tag", tag);
				request.setAttribute("replyType", replyType);
				StaticReportsDAO sdao = new StaticReportsDAO();
				ArrayList<CommonBean> detailarr = sdao.SearchFor(searchfor, receivedfrom,datefrom,dateto,refclass, subjectcode, markto,tag,replyType,ISCONF,roleId,sessionBean.getISREPLY(),reply);
				if(detailarr.size()<=0)
				{
					String var="No Record found with specific parameters...";
					request.setAttribute("detailarr", detailarr);
					getServletContext().getRequestDispatcher("DiaryReport.jsp?var="+var+"").forward(request, response);
				} else{
				request.setAttribute("detailarr", detailarr);
				getServletContext().getRequestDispatcher("DiaryReport.jsp").forward(request, response);
			}}
			if (rNumber == 4) {
				request.setAttribute("searchfor", searchfor);
				request.setAttribute("receivedfrom", receivedfrom);
				request.setAttribute("datefrom", datefrom);
				request.setAttribute("dateto", dateto);
				request.setAttribute("refclass", refclass);
				request.setAttribute("subjectcode", subjectcode);
				request.setAttribute("markto", markto);
				request.setAttribute("tag", tag);
				StaticReportsDAO sdao = new StaticReportsDAO();
				ArrayList<CommonBean> detailarr = sdao.FormattedReport(searchfor, receivedfrom,datefrom,dateto,refclass, subjectcode, markto, tag,roleId);
				if(detailarr.size()<=0)
				{
					String var="No Record found with specific parameters...";
					request.setAttribute("detailarr", detailarr);
					getServletContext().getRequestDispatcher("DiaryReport.jsp?var="+var+"").forward(request, response);
				} else{
				request.setAttribute("detailarr", detailarr);
				getServletContext().getRequestDispatcher("FormattedReport.jsp").forward(request, response);
			}}
			if (rNumber == 5) {
				request.setAttribute("refNo", refNo);
				request.setAttribute("inDate", inDate);
				ReportRefNoDAO rdao = new ReportRefNoDAO();
				
				ArrayList<CommonBean> mainArr = rdao.getMainBlock(refNo,inDate);
				request.setAttribute("mainArr", mainArr);

				ArrayList<CommonBean> reminderArr = rdao.getReminderBlock(refNo,inDate);
				request.setAttribute("reminderArr", reminderArr);

				ArrayList<CommonBean> finalStatusArr = rdao.getFinalStatusBlock(refNo,inDate,sessionBean.getISREPLY());
				request.setAttribute("finalStatusArr", finalStatusArr);
//				StaticReportsDAO sdao = new StaticReportsDAO();
//				ArrayList<CommonBean> detailarr = sdao.FormattedReport(searchfor, receivedfrom,datefrom,dateto,refclass, subjectcode, markto, tag);
				if(mainArr.size()<=0)
				{
					String var="No Record found with specific parameters...";
					request.setAttribute("detailarr", mainArr);
					getServletContext().getRequestDispatcher("ReportRefNoOutput.jsp?var="+var+"").forward(request, response);
				} else{
					request.setAttribute("detailarr", mainArr);
					getServletContext().getRequestDispatcher("ReportRefNoOutput.jsp").forward(request, response);
				}}
			else if (rNumber==6)
			{
				
				// System.out.println("step 0");
				String fileName="RFMSACKENGLISH_"+new Date().getTime()+".doc";
				
				StaticReportsDAO sdao = new StaticReportsDAO();
				String retFileName =	sdao.exportToDOCEnglish(request,diarynofrom,diarynoto,fileName);
				
//				System.out.println("inside Export data Servlet");
				
				//String wordDir =request.getRealPath("/ReportFiles");
				 //System.out.println("*****************ReportFiles path1 :"+retFileName);
				 
				 
				 int length=0;
				 File f = new File(retFileName);
					
					ServletOutputStream op = response.getOutputStream();
	
					response.setContentType("application/msword");
					// else
					// resp.setContentType("");
					response.setContentLength((int) f.length());
					response.setHeader("Content-Disposition", "attachment; filename="+retFileName);
	
					byte[] bbuf = new byte[1024];
					DataInputStream in = new DataInputStream(new FileInputStream(f));
	
					while ((in != null) && ((length = in.read(bbuf)) != -1)) {
						op.write(bbuf, 0, length);
					}
	
					in.close();
					op.flush();
					op.close();
	
				 /*
				 
				 
				 
				      
				      ServletOutputStream stream = null;
				      BufferedInputStream buf = null;
				      
				      stream = response.getOutputStream();
					     
					     File doc = new File(retFileName);
					   //System.out.println("step 0");
					   
					   //String contentType = getServletContext().getMimeType(retFileName);
					   //response.reset();
					   response.setContentType("application/msword");	
					  // ServletContext context = request.getSession().getServletContext();
					   //response.setContentType(context.getMimeType(retFileName));
					      //System.out.println("step 1");
					     response.setHeader("Content-disposition","attachment; filename=test.doc" );
					    // System.out.println("step 2");
					     response.setContentLength( (int) doc.length() );
					     //System.out.println("step 3"+doc.length());
					     FileInputStream input = new FileInputStream(doc);
					    // System.out.println("step 4");
					     buf = new BufferedInputStream(input);
					     int readBytes = 0;
					   //  System.out.println("step 5");
					     while((readBytes = buf.read()) != -1)
					        stream.write(readBytes);
					     
					     stream.flush();
					     stream.close();
					     input.close();
					     
					     */
					     //System.out.println("step 5");
				/*request.setAttribute("diarynofrom", diarynofrom);
				request.setAttribute("diarynoto", diarynoto);
				
				StaticReportsDAO sdao = new StaticReportsDAO();
				ArrayList<CommonBean> detailarr = sdao.acknowledgementfromto(diarynofrom,diarynoto);
				if(detailarr.size()<=0)
				{
					String var="No Record found with specific parameters...";
					request.setAttribute("detailarr", detailarr);
					getServletContext().getRequestDispatcher("Acknowledgement.jsp?var="+var+"").forward(request, response);
				} else{
				request.setAttribute("detailarr", detailarr);
				getServletContext().getRequestDispatcher("Acknowledgement.jsp").forward(request, response);
				}*/
			}//System.out.println("end");
		}
		catch (Exception e) {
			//System.out.println("en11d");
				e.printStackTrace();
		}	
		finally {
			//System.out.println("en22d");
		}
	}
}