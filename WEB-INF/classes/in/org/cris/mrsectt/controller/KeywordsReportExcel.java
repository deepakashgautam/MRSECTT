package in.org.cris.mrsectt.controller;

import in.org.cris.mrsectt.Beans.MastersReportBean;
import in.org.cris.mrsectt.dao.KeywordReportDAO;
import in.org.cris.mrsectt.dao.MastersReportDAO;
import in.org.cris.mrsectt.dao.MastersReportsExcelDAO;

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

/**
 * Servlet implementation class KeywordsReportExcel
 */
public class KeywordsReportExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KeywordsReportExcel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String serverpath = request.getSession().getAttribute("filepath") != null ? request.getSession().getAttribute("filepath").toString(): "";
		//System.out.println("serverpath :" + serverpath);
		
		
		String INDATECONDITION = request.getParameter("INDATECONDITION") != null ? request.getParameter("INDATECONDITION") : "";
		String INDATEFROM = request.getParameter("INDATEFROM") != null ? request.getParameter("INDATEFROM") : "";
		String INDATETO = request.getParameter("INDATETO") != null ? request.getParameter("INDATETO") : "";

		String LETDATECONDITION = request.getParameter("LETDATECONDITION") != null ? request.getParameter("LETDATECONDITION") : "";
		String LETDATEFROM = request.getParameter("LETDATEFROM") != null ? request.getParameter("LETDATEFROM") : "";
		String LETDATETO = request.getParameter("LETDATETO") != null ? request.getParameter("LETDATETO") : "";

		String RECEIVEDFROM = request.getParameter("RECEIVEDFROM") != null ? request.getParameter("RECEIVEDFROM") : "";
		String STATUS = request.getParameter("STATUS") != null ? request.getParameter("STATUS") : "";
		String STATE = request.getParameter("STATE") != null ? request.getParameter("STATE") : "";
		String SUBJECT = request.getParameter("SUBJECT") != null ? request.getParameter("SUBJECT") : "";
		String ACKNBY = request.getParameter("ACKNBY") != null ? request.getParameter("ACKNBY") : "";

		String ACKNDATECONDITION = request.getParameter("ACKNDATECONDITION") != null ? request.getParameter("ACKNDATECONDITION") : "";
		String ACKNDATEFROM = request.getParameter("ACKNDATEFROM") != null ? request.getParameter("ACKNDATEFROM") : "";
		String ACKNDATETO = request.getParameter("ACKNDATETO") != null ? request.getParameter("ACKNDATETO") : "";

		String MARKINGDATECONDITION = request.getParameter("MARKINGDATECONDITION") != null ? request.getParameter("MARKINGDATECONDITION") : "";
		String MARKINGDATEFROM = request.getParameter("MARKINGDATEFROM") != null ? request.getParameter("MARKINGDATEFROM") : "";
		String MARKINGDATETO = request.getParameter("MARKINGDATETO") != null ? request.getParameter("MARKINGDATETO") : "";

		String TARGETDATECONDITION = request.getParameter("TARGETDATECONDITION") != null ? request.getParameter("TARGETDATECONDITION") : "";
		String TARGETDATEFROM = request.getParameter("TARGETDATEFROM") != null ? request.getParameter("TARGETDATEFROM") : "";
		String TARGETDATETO = request.getParameter("TARGETDATETO") != null ? request.getParameter("TARGETDATETO") : "";

		String REMARKS = request.getParameter("REMARKS") != null ? request.getParameter("REMARKS") : "";
		String SIGNEDBY = request.getParameter("SIGNEDBY") != null ? request.getParameter("SIGNEDBY") : "";

		String SIGNEDONCONDITION = request.getParameter("SIGNEDONCONDITION") != null ? request.getParameter("SIGNEDONCONDITION") : "";
		String SIGNEDONFROM = request.getParameter("SIGNEDONFROM") != null ? request.getParameter("SIGNEDONFROM") : "";
		String SIGNEDONTO = request.getParameter("SIGNEDONTO") != null ? request.getParameter("SIGNEDONTO") : "";

		String TAG = request.getParameter("TAG") != null ? request.getParameter("TAG") : "";
		String RLY = request.getParameter("RLY") != null ? request.getParameter("RLY") : "";
		String PLACE = request.getParameter("PLACE") != null ? request.getParameter("PLACE") : "";

		String PLCRLYCONDITION = request.getParameter("SIGNEDONCONDITION") != null ? request.getParameter("SIGNEDONCONDITION") : "";
		String PLCRLYFROM = request.getParameter("SIGNEDONFROM") != null ? request.getParameter("SIGNEDONFROM") : "";
		String PLCRLYTO = request.getParameter("SIGNEDONTO") != null ? request.getParameter("SIGNEDONTO") : "";
		// new code (suneel)

		//String [] ROLEID = request.getParameterValues("SEL_ROLEID") != null ? request.getParameterValues("SEL_ROLEID") :  new String [0];
		String [] REFCLASS = request.getParameterValues("SEL_REFCLASS") != null ? request.getParameterValues("SEL_REFCLASS") :  new String [0];
		String [] REFCATEGORY = request.getParameterValues("SEL_REFCATEGORY") != null ? request.getParameterValues("SEL_REFCATEGORY") :  new String [0];
		String [] SUBJECTTYPE = request.getParameterValues("SEL_SUBJECTTYPE") != null ? request.getParameterValues("SEL_SUBJECTTYPE") :  new String [0];	
		String [] MARKTO = request.getParameterValues("SEL_MARKTO") != null ? request.getParameterValues("SEL_MARKTO") :  new String [0];			
		//String [] STATE = request.getParameterValues("SEL_STATE") != null ? request.getParameterValues("SEL_STATE") :  new String [0];
		//String [] seltabOrderBy = request.getParameterValues("sel_tabOrderBy") != null ? request.getParameterValues("sel_tabOrderBy") :  new String [0];
		String [] selcolval = request.getParameterValues("sel_tabcols") != null ? request.getParameterValues("sel_tabcols") :  new String [0];
		String [] VIPTYPE = request.getParameterValues("SEL_VIPTYPE") != null ? request.getParameterValues("SEL_VIPTYPE") :  new String [0];
		String [] VIPPARTY = request.getParameterValues("SEL_VIPPARTY") != null ? request.getParameterValues("SEL_VIPPARTY") :  new String [0];
		String subjectcode=request.getParameter("SUBJECTTYPE")!=null?request.getParameter("SUBJECTTYPE"):"";
		String keyword1=request.getParameter("keyword1")!=null?request.getParameter("keyword1"):"";
		String keyword3=request.getParameter("keyword3")!=null?request.getParameter("keyword3"):"";
		
		
		String sql=new KeywordReportDAO().getReportSQL(INDATECONDITION, INDATEFROM, INDATETO, LETDATECONDITION, LETDATEFROM, LETDATETO, RECEIVEDFROM, REFCLASS, VIPTYPE, VIPPARTY,STATUS,subjectcode,keyword1,keyword3);
		 
		//System.out.println("My Report for report id : "+reportid+" :: " + strSQL);
		ArrayList<MastersReportBean> mstarr = new ArrayList<MastersReportBean>();
		mstarr.add(new MastersReportDAO().getReportArray(sql, "Keyword Report",	"L",	"200~100~100~100~200~75~200~100~100" ));
		request.setAttribute("mstarr", mstarr);
		
		String RepPath = "";
		{
			RepPath = new MastersReportsExcelDAO().generateReportWithFormat(serverpath, mstarr, "L",	"200~100~100~100~200~75~200~100~100");
		}
		File target = new File(RepPath);
		OutputStream output = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition:","attachment;filename= MRSECTT_KeywordReport.xls;");
		response.setHeader("Cache-Control", "must-revalidate");
		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(RepPath));
		int ch = 0;
		while ((ch = fin.read()) != -1) {
			output.write(ch);
		}
		output.flush();
		output.close();
		fin.close();
		//target.delete();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
