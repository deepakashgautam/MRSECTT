package in.org.cris.mrsectt.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.org.cris.mrsectt.Beans.MastersReportBean;
import in.org.cris.mrsectt.dao.CustomReportFileMovDAO;
import in.org.cris.mrsectt.dao.MastersReportDAO;
import in.org.cris.mrsectt.dao.MastersReportPDFDAO;
import in.org.cris.mrsectt.dao.MastersReportsExcelDAO;
import in.org.cris.mrsectt.dbConnection.DBConnection;
//import in.org.cris.mrsectt.dao.MastersReportPDFDAO;
//import in.org.cris.mrsectt.dao.MastersReportsExcelDAO;
import in.org.cris.mrsectt.util.StringFormat;
import in.org.cris.mrsectt.util.Encrypt;

/**
 * Servlet implementation class GenerateReportController
 */
public class GenerateReportFileMovController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenerateReportFileMovController() {
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("in GenerateReportController-----------");
		String serverpath = request.getSession().getAttribute("filepath") != null ? request.getSession().getAttribute("filepath").toString(): "";
		String generated = request.getParameter("generate") != null ? request.getParameter("generate") : "N";
		//System.out.println("generated ---- " + generated);
		String strSQL = "";
		String reportid = request.getParameter("reportid") != null ? request.getParameter("reportid") : "";
		String reportidencr = request.getParameter("reportidencr") != null ? Encrypt.decrypt(request.getParameter("reportidencr")) : "";
		reportid = request.getParameter("reportid") != null ? reportid : reportidencr;
		//System.out.println("reportid ---- " + reportid);
		String repExportType = request.getParameter("repExportType") != null ? request.getParameter("repExportType"): "";
		//System.out.println("repExportType ---- " + repExportType);
		String repType = request.getParameter("repType") != null ? request.getParameter("repType") : "2";
		String repTypeenc = request.getParameter("repTypeenc") != null ? Encrypt.decrypt(request.getParameter("repTypeenc")) : "2";
		repType = request.getParameter("repType") != null ? repType : repTypeenc;
		//System.out.println("repType ---- " + repType);
		String save = request.getParameter("save") != null ? request.getParameter("save") : "";
		//System.out.println("save ---- " + save);
		String repheader = request.getParameter("repheader") != null ? request.getParameter("repheader") : "";
		//System.out.println("repheader ---- " + repheader);
		String layout = request.getParameter("layout") != null ? request.getParameter("layout") : "P";
		//System.out.println("layout ---- " + layout);
		String colwidth = request.getParameter("colwidth") != null ? request.getParameter("colwidth") : "";
		//System.out.println("colwidth ---- " + colwidth);

		if ("N".equalsIgnoreCase(generated)) {

			String [] DEPTCODE = request.getParameterValues("SEL_DEPARTMENTCODE") != null ? request.getParameterValues("SEL_DEPARTMENTCODE") :  new String [0];
			String [] DESTMARKING = request.getParameterValues("SEL_DESTINATIONMARKING") != null ? request.getParameterValues("SEL_DESTINATIONMARKING") :  new String [0];
			String [] SUBJECTCODE = request.getParameterValues("SEL_SUBJECTCODE") != null ? request.getParameterValues("SEL_SUBJECTCODE") :  new String [0];	
			String [] RECEIVEDFROM = request.getParameterValues("SEL_RECEIVEDFROM") != null ? request.getParameterValues("SEL_RECEIVEDFROM") :  new String [0];			
			String [] FILESTATUS1 = request.getParameterValues("SEL_FILESTATUS1") != null ? request.getParameterValues("SEL_FILESTATUS1") :  new String [0];
			String [] FILESTATUS2 = request.getParameterValues("SEL_FILESTATUS2") != null ? request.getParameterValues("SEL_FILESTATUS2") :  new String [0];
			String [] seltabOrderBy = request.getParameterValues("sel_tabOrderBy") != null ? request.getParameterValues("K") :  new String [0];
			
//			String [] selcolval = request.getParameterValues("sel_tabcols") != null ? request.getParameterValues("sel_tabcols") :  new String [0];
			String selcolparam = request.getParameter("selcolparam") != null ? request.getParameter("selcolparam"): "";

			String REGDATECOND = request.getParameter("REGDATECOND") != null ? request.getParameter("REGDATECOND") : "";
			String REGDATEFROM = request.getParameter("REGDATEFROM") != null ? request.getParameter("REGDATEFROM") : "";
			String REGDATETO = request.getParameter("REGDATETO") != null ? request.getParameter("REGDATETO") : "";
			
			String FILETYPE = request.getParameter("FILETYPE") != null ? request.getParameter("FILETYPE") : "";
			String FILENO = request.getParameter("FILENO") != null ? request.getParameter("FILENO") : "";
			String LINKFILENO = request.getParameter("LINKFILENO") != null ? request.getParameter("LINKFILENO") : "";
			String PUC = request.getParameter("PUC") != null ? request.getParameter("PUC") : "";
			//System.out.println("PUC : "+ PUC);
			String SUBJECT = request.getParameter("SUBJECT") != null ? request.getParameter("SUBJECT") : "";
			String PROPOSEDPATH = request.getParameter("PROPOSEDPATH") != null ? request.getParameter("PROPOSEDPATH") : "";
			String MARKTOUP = request.getParameter("MARKTOUP") != null ? request.getParameter("MARKTOUP") : "";
			
			String MARKTOUPCOND = request.getParameter("MARKTOUPCOND") != null ? request.getParameter("MARKTOUPCOND") : "";
			String MARKTOUPFROM = request.getParameter("MARKTOUPFROM") != null ? request.getParameter("MARKTOUPFROM") : "";
			String MARKTOUPTO = request.getParameter("MARKTOUPTO") != null ? request.getParameter("MARKTOUPTO") : "";
			
			String MARKINGREMARKS = request.getParameter("MARKINGREMARKS") != null ? request.getParameter("MARKINGREMARKS") : "";
			String INTMARKING = request.getParameter("INTMARKING") != null ? request.getParameter("INTMARKING") : "";
			
			String INTMARKINGCOND = request.getParameter("INTMARKINGCOND") != null ? request.getParameter("INTMARKINGCOND") : "";
			String INTMARKINGFROM = request.getParameter("INTMARKINGFROM") != null ? request.getParameter("INTMARKINGFROM") : "";
			String INTMARKINGTO = request.getParameter("INTMARKINGTO") != null ? request.getParameter("INTMARKINGTO") : "";
			
			String RETURNONCOND = request.getParameter("RETURNONCOND") != null ? request.getParameter("RETURNONCOND") : "";
			String RETURNONFROM = request.getParameter("RETURNONFROM") != null ? request.getParameter("RETURNONFROM") : "";
			String RETURNONTO = request.getParameter("RETURNONTO") != null ? request.getParameter("RETURNONTO") : "";
			
			String MARKINGREMARKS1 = request.getParameter("MARKINGREMARKS1") != null ? request.getParameter("MARKINGREMARKS1") : "";
			String MARKTODOWN = request.getParameter("MARKTODOWN") != null ? request.getParameter("MARKTODOWN") : "";
			
			String MARKTODOWNCOND = request.getParameter("MARKTODOWNCOND") != null ? request.getParameter("MARKTODOWNCOND") : "";
			String MARKTODOWNFROM = request.getParameter("MARKTODOWNFROM") != null ? request.getParameter("MARKTODOWNFROM") : "";
			String MARKTODOWNTO = request.getParameter("MARKTODOWNTO") != null ? request.getParameter("MARKTODOWNTO") : "";
			
			String MARKINGREMARKS2 = request.getParameter("MARKINGREMARKS2") != null ? request.getParameter("MARKINGREMARKS2") : "";
			String REPLYTYPE = request.getParameter("REPLYTYPE") != null ? request.getParameter("REPLYTYPE") : "";
			String REASON = request.getParameter("REASON") != null ? request.getParameter("REASON") : "";
			String RECEIVEDFROMEXCEPT = request.getParameter("RECEIVEDFROMEXCEPT") != null ? request.getParameter("RECEIVEDFROMEXCEPT") : ""; 
			
			String FinalSelection = "";
			
			String condDEPTCODE = "";
			for (int i = 0; i < DEPTCODE.length; i++) {
				condDEPTCODE += ",'" + DEPTCODE[i]+ "'";
			}
			if (DEPTCODE.length == 1)
				condDEPTCODE = " AND TRNFILEHDR.DEPARTMENTCODE = " + condDEPTCODE.substring(1) + " ";
			else if (DEPTCODE.length > 0)
				condDEPTCODE = " AND TRNFILEHDR.DEPARTMENTCODE IN (" +condDEPTCODE.substring(1)+ ") ";

			String condDESTMARKING = "";
			for (int i = 0; i < DESTMARKING.length; i++) {
				condDESTMARKING += ",'" + DESTMARKING[i] + "'";
			}
			if (DESTMARKING.length == 1)
				condDESTMARKING = " AND TRNFILEHDR.DESTINATIONMARKING = " + condDESTMARKING.substring(1) + " ";
			else if (DESTMARKING.length > 0)
				condDESTMARKING = " AND TRNFILEHDR.DESTINATIONMARKING IN (" + condDESTMARKING.substring(1) + ") ";

			String condSUBJECTCODE = "";
			for (int i = 0; i < SUBJECTCODE.length; i++) {
				condSUBJECTCODE += " OR TRNFILEMARKING.SUBJECTCODE LIKE '%" + SUBJECTCODE[i] + "%' ";
			}
			if (SUBJECTCODE.length > 0)
				condSUBJECTCODE = " AND (" + condSUBJECTCODE.substring(3) + ") ";

			String condRECEIVEDFROM = "";
			for (int i = 0; i < RECEIVEDFROM.length; i++) {
				condRECEIVEDFROM += " OR TRNFILEHDR.RECEIVEDFROM = " + RECEIVEDFROM[i] + " ";
			}
			if (RECEIVEDFROM.length > 0)
				condRECEIVEDFROM = " AND (" + condRECEIVEDFROM.substring(3) + ") ";

			String condFILESTATUS1 = "";
			for (int i = 0; i < FILESTATUS1.length; i++) {
				condFILESTATUS1 += " OR  TRNFILEHDR.FILESTATUS1 = '" + FILESTATUS1[i] + "' ";
			}
			if (FILESTATUS1.length > 0)
				condFILESTATUS1 = " AND (" + condFILESTATUS1.substring(3) + ") ";
			
			String condFILESTATUS2 = "";
			for (int i = 0; i < FILESTATUS2.length; i++) {
				condFILESTATUS2 += " OR  TRNFILEHDR.FILESTATUS2 = '" + FILESTATUS2[i] + "' ";
			}
			if (FILESTATUS2.length > 0)
				condFILESTATUS2 = " AND (" + condFILESTATUS2.substring(3) + ") ";
//	CODE TO GET REFID FOR PUC FILTER
			DBConnection conn = new DBConnection();
			String refId = "";
			try {
				conn.openConnection();
				ResultSet rs = conn.select( " SELECT DISTINCT REFID FROM TRNREFERENCE WHERE REFNO LIKE '"+PUC+"' " );
				if(rs.next()) { refId = rs.getString("REFID");	}
				//System.out.println("refId :  :  "+ refId );
				rs.close();conn.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			REGDATECOND = REGDATEFROM.length() > 0 ? "=".equalsIgnoreCase(REGDATECOND) ? " AND TO_CHAR(TRNFILEHDR.REGISTRATIONDATEDES,'DD/MM/YYYY') = '"+REGDATEFROM+"'" : REGDATETO.length() > 0 ? " AND trunc(TRNFILEHDR.REGISTRATIONDATEDES) BETWEEN TO_DATE('"+REGDATEFROM+"','DD/MM/YYYY') AND TO_DATE('"+REGDATETO+"','DD/MM/YYYY')":" ":"";
			FILETYPE = FILETYPE.length() > 0 ? " AND TRNFILEHDR.REFERENCETYPE = '"+FILETYPE+"' " : "";
			FILENO = FILENO.length() > 0 ? " AND TRNFILEHDR.FILENO LIKE '%"+FILENO+"%'" : "";
			LINKFILENO = LINKFILENO.length() > 0 ? " AND (TRNFILEHDR.LINKFILENO1 LIKE '%"+LINKFILENO+"%' OR TRNFILEHDR.LINKFILENO2 LIKE '%"+LINKFILENO+"%' OR TRNFILEHDR.LINKFILENO3 LIKE '%"+LINKFILENO+"%' OR TRNFILEHDR.LINKFILENO4 LIKE '%"+LINKFILENO+"%') " : "";
			PUC = PUC.length() > 0 ? " AND TRNFILEREF.REFID = '"+refId+"' " : "";
			SUBJECT = SUBJECT.length() > 0 ? " AND TRNFILEHDR.SUBJECT LIKE '%"+SUBJECT+"%' " : "";
			PROPOSEDPATH = PROPOSEDPATH.length() > 0 ? " AND TRNFILEHDR.PROPOSEDPATH LIKE '%"+PROPOSEDPATH+"%' " : "";
			MARKTOUP = MARKTOUP.length() > 0 ? " AND TRNFILEMARKING.MARKINGTO = '"+MARKTOUP+"' " : "";
			MARKTOUPCOND = MARKTOUPFROM.length() > 0 ? "=".equalsIgnoreCase(MARKTOUPCOND) ? " AND TO_CHAR(TRNFILEMARKING.MARKINGDATE,'DD/MM/YYYY') = '"+MARKTOUPFROM+"'" : MARKTOUPTO.length() > 0 ? " AND trunc(TRNFILEMARKING.MARKINGDATE) BETWEEN TO_DATE('"+MARKTOUPFROM+"','DD/MM/YYYY') AND TO_DATE('"+MARKTOUPTO+"','DD/MM/YYYY')":" ":"";
			MARKINGREMARKS = MARKINGREMARKS.length() > 0 ? " AND TRNFILEMARKING.MARKINGREMARK LIKE '%"+MARKINGREMARKS+"%' " : "";
			
			INTMARKING = INTMARKING.length() > 0 ? "AND TRNFILEHDR.FMID in  ( select fmid from TRNFILEINTMARKING where MARKINGTO = "+INTMARKING+ ") ":"";
			
			//INTMARKING = INTMARKING.length() > 0 ? " AND TRNFILEINTMARKING.MARKINGTO = "+INTMARKING+" " : "";
			INTMARKINGCOND = INTMARKINGFROM.length() > 0 ? "=".equalsIgnoreCase(INTMARKINGCOND) ? 
		" AND TRNFILEHDR.FMID in  (select fmid from trnfileintmarking where TO_CHAR(TRNFILEINTMARKING.MARKINGDATE,'DD/MM/YYYY') = '"+INTMARKINGFROM+"')" : 
						INTMARKINGTO.length() > 0 ? 
		" AND TRNFILEHDR.FMID in  (select fmid from trnfileintmarking where trunc(TRNFILEINTMARKING.MARKINGDATE) BETWEEN TO_DATE('"+INTMARKINGFROM+"','DD/MM/YYYY') AND TO_DATE('"+INTMARKINGTO+"','DD/MM/YYYY'))":" ":"";
			
			
			
			RETURNONCOND = RETURNONFROM.length() > 0 ? "=".equalsIgnoreCase(RETURNONCOND) ? " AND TRNFILEINTMARKING.CHANGEDATE = TO_DATE('"+RETURNONFROM+"','DD/MM/YYYY')" : RETURNONTO.length() > 0 ? " AND trunc(TRNFILEINTMARKING.CHANGEDATE) BETWEEN TO_DATE('"+RETURNONFROM+"','DD/MM/YYYY') AND TO_DATE('"+RETURNONTO+"','DD/MM/YYYY')":" ":"";
			MARKINGREMARKS1 = MARKINGREMARKS1.length() > 0 ? " AND TRNFILEHDR.REMARKS LIKE '%"+MARKINGREMARKS1+"%' " : "";
			MARKTODOWN = MARKTODOWN.length() > 0 ? " AND TRNFILEMARKING.MARKINGTO = '"+MARKTODOWN+"' " : "";
			MARKTODOWNCOND = MARKTODOWNFROM.length() > 0 ? "=".equalsIgnoreCase(MARKTODOWNCOND) ? " AND TO_DATE(TO_CHAR(TRNFILEMARKING.MARKINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') = TO_DATE('"+MARKTODOWNFROM+"','DD/MM/YYYY')" : MARKTODOWNTO.length() > 0 ? " AND trunc(TRNFILEMARKING.MARKINGDATE) BETWEEN TO_DATE('"+MARKTODOWNFROM+"','DD/MM/YYYY') AND TO_DATE('"+MARKTODOWNTO+"','DD/MM/YYYY')":" ":"";
			MARKINGREMARKS2 = MARKINGREMARKS2.length() > 0 ? " AND UPPER(TRNFILEMARKING.MARKINGREMARK) LIKE UPPER('%"+MARKINGREMARKS2+"%') " : "";
			REPLYTYPE = REPLYTYPE.length() > 0 ? " AND TRNFILEHDR.REPLYTYPE = '"+REPLYTYPE+"' " : "";
			REASON = REASON.length() > 0 ? " AND TRNFILEHDR.REASON = '"+REASON+"' " : "";
			RECEIVEDFROMEXCEPT = RECEIVEDFROMEXCEPT.length() > 0 ? " AND TRNFILEHDR.RECEIVEDFROM <> '"+RECEIVEDFROMEXCEPT+"' " : "";
			
			 HashMap<String,String> hm=new HashMap<String,String>();  
			 hm.put("1", "TRNFILEHDR.REGISTRATIONNODES");
			 hm.put("2", "TO_CHAR(TRNFILEHDR.REGISTRATIONDATEDES,'DD-MM-YYYY')");
			 hm.put("3", "DECODE(TRNFILEHDR.REferenceTYPE,'A','APPROVAL','D','DRAFT','P','POSITION','C','CONFIDENTIAL','')");
			 hm.put("4", "TRNFILEHDR.FILENO");
			 hm.put("5",  "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEHDR.DEPARTMENTCODE)");
			 hm.put("6", "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEHDR.DESTINATIONMARKING)");
			 hm.put("7", "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEHDR.RECEIVEDFROM) ");
			 hm.put("8", "TRNFILEHDR.SUBJECT");
			 
			 hm.put("9", "substr(getFileCustomReportData(TRNFILEHDR.FMID),2)");
			 hm.put("10", "(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = TRNFILEHDR.FILESTATUS1)");
			 hm.put("11", "(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = TRNFILEHDR.FILESTATUS2) ");
			 hm.put("12", "TRNFILEHDR.REMARKS");
			 hm.put("13", "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNFILEMARKING.MARKINGTO) ");
			 hm.put("14", "TO_CHAR(TRNFILEMARKING.MARKINGDATE,'DD-MM-YYYY')");
			 
			 hm.put("15", "TRNFILEMARKING.MARKINGREMARK");
			 hm.put("16", "TRNFILEHDR.REPLYTYPE");
			 hm.put("17", "TRNFILEHDR.REASON");
			 hm.put("18", "NVL(TO_DATE(TRNFILEMARKING.MARKINGDATE,'DD-MM-YYYY'),TO_DATE(TRNFILEHDR.REGISTRATIONDATEDES,'DD-MM-YYYY'))-TO_DATE(TRNFILEHDR.REGISTRATIONDATEDES,'DD-MM-YYYY')");
			 hm.put("19", "ROUND(AVG(NVL(TO_DATE(TRNFILEMARKING.MARKINGDATE,'DD-MM-YYYY'),TO_DATE(TRNFILEHDR.REGISTRATIONDATEDES,'DD-MM-YYYY'))-TO_DATE(TRNFILEHDR.REGISTRATIONDATEDES,'DD-MM-YYYY'))OVER (ORDER BY TRNFILEHDR.filecounterdes ROWS BETWEEN unbounded PRECEDING AND CURRENT ROW),1)");
			 hm.put("20", "TRNFILEHDR.EOFFICENO");
			 hm.put("21", "TO_DATE(SYSDATE,'DD-MM-YYYY')-TO_DATE(TRNFILEHDR.REGISTRATIONDATEDES,'DD-MM-YYYY')");
			 
			 
			 
			 
			 //System.out.println("selcolparam"+selcolparam);
			 String[] newselcolparam = selcolparam.split("~");
				for (int i = 0; i < newselcolparam.length; i++) {
					
					if (newselcolparam[i].length() > 0) {
						String[] tempcol = newselcolparam[i].split("[/^]");
						//System.out.println("-----------tempcol[0]-----------------"+tempcol[0]);
						tempcol[0]=hm.get(tempcol[0]);
						//System.out.println("-----------tempcol[0]-----------------"+tempcol[0]);
						if (tempcol.length > 1)
							FinalSelection = FinalSelection.concat(", "
									+ tempcol[0] + " \"" + tempcol[1] + "\"");
					}
				}
				
				FinalSelection = "SELECT TRNFILEHDR.REFERENCETYPE \"filetypeflag\", " + FinalSelection.substring(1);
			String conditions = condDEPTCODE + condDESTMARKING + condSUBJECTCODE + condRECEIVEDFROM + condFILESTATUS1 + condFILESTATUS2 +
								REGDATECOND + FILETYPE + FILENO + LINKFILENO + PUC + SUBJECT + PROPOSEDPATH + MARKTOUP + MARKTOUPCOND + MARKINGREMARKS + INTMARKING + 
								INTMARKINGCOND + RETURNONCOND + MARKINGREMARKS1 + MARKTODOWN + MARKTODOWNCOND + MARKINGREMARKS2 + REPLYTYPE + REASON + RECEIVEDFROMEXCEPT; // + ("2".equalsIgnoreCase(repType) ? proposalCond : "");
			
			String orderByClause="";
			for(int i=0;i<seltabOrderBy.length;i++)
			{
				String[] temporderby = seltabOrderBy[i].split("~");
				orderByClause+=","+temporderby[0]+" "+ (temporderby.length>0?temporderby[1]:"ASC")+ " " ;
			}
			if(orderByClause.length()>0)
			{
				orderByClause = " ORDER BY " + orderByClause.substring(1);
			}
			//Append Order By to conditions
			//conditions+=orderByClause;
			conditions+=" ORDER BY  TRNFILEHDR.fmid";
			//conditions+=" ORDER BY  TRNFILEHDR.filecounterdes";
			strSQL = new CustomReportFileMovDAO().generateSQL(FinalSelection,conditions, repType);
			//System.out.println(strSQL);
			if ("Y".equalsIgnoreCase(save)) {
				new CustomReportFileMovDAO().updateReportSQL(reportid, strSQL, repType);
			}
		}
		else {
			String[] result = new CustomReportFileMovDAO().getReportSQL(reportid);
			strSQL = result[1];
			repheader = result[2];
			layout = result[3];
			colwidth = result[4];
		}

		//System.out.println("My Report for report id = " + reportid + " ---> \n " + strSQL);
		ArrayList<MastersReportBean> mstarr = new ArrayList<MastersReportBean>();
		mstarr.add(new MastersReportDAO().getReportArray(strSQL, repheader,	layout, colwidth));

		// System.out.println("save flag is:" + save);

		request.setAttribute("mstarr", mstarr);

		if (repExportType.equals("E")) {
			String RepPath = "";
		//	if("N".equalsIgnoreCase(generated)){
		//	 RepPath = new MastersReportsExcelDAO().generateReport(serverpath,
		//	 mstarr);
		//	 }else
			{
				RepPath = new MastersReportsExcelDAO().generateReportWithFormat(serverpath, mstarr, layout,	colwidth);
			}

			File target = new File(RepPath);
			OutputStream output = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition:","attachment;filename= MRSECTT_" + repheader + ".xls;");
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
		} else if (repExportType.equals("D")) {
			String RepPath = "";
		//	 if("N".equalsIgnoreCase(generated)){
		//	 RepPath = new MastersReportsExcelDAO().generateReport(serverpath, mstarr);
		//	 }else
			{
				RepPath = new MastersReportPDFDAO().generateReportWithFormat(serverpath, mstarr, layout, colwidth);
			}

			File target = new File(RepPath);
			OutputStream output = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition:",
					"attachment;filename= MRSECTT_" + repheader + ".pdf;");
			response.setHeader("Cache-Control", "must-revalidate");
			response.setHeader("Content-Length", String.valueOf(target.length())); 
			BufferedInputStream fin = new BufferedInputStream(
					new FileInputStream(RepPath));
			int ch = 0;
			while ((ch = fin.read()) != -1) {
				output.write(ch);
			}

			output.flush();
			output.close();
			fin.close();
			target.delete();
		} else {
			request.getRequestDispatcher("ReportFileMovHTMLOutput.jsp?reportid=" + reportid).forward(
					request, response);
		}

	}

	public String decodeWord2Symbol(String symbol) {
		String result = "";
		result = symbol.equalsIgnoreCase("GE") ? ">=" : symbol
				.equalsIgnoreCase("GT") ? ">"
				: symbol.equalsIgnoreCase("EQ") ? "=" : symbol
						.equalsIgnoreCase("LT") ? "<" : symbol
						.equalsIgnoreCase("LE") ? "<=" : "";
		return result;
	}

}
