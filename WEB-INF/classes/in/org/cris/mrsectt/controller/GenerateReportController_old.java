package in.org.cris.mrsectt.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.org.cris.mrsectt.Beans.MastersReportBean;
import in.org.cris.mrsectt.Beans.MstLogin;
import in.org.cris.mrsectt.dao.CustomReportDAO;
import in.org.cris.mrsectt.dao.MastersReportDAO;
import in.org.cris.mrsectt.dao.MastersReportPDFDAO;
import in.org.cris.mrsectt.dao.MastersReportsExcelDAO;
import in.org.cris.mrsectt.util.StringFormat;
public class GenerateReportController_old extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public GenerateReportController_old() {
		super();
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("reportid : " +request.getParameter("reportid"));
		HttpSession session = request.getSession();
		MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
		String strSQL = "";

		String serverpath = request.getSession().getAttribute("filepath") != null ? request.getSession().getAttribute("filepath").toString(): "";
//System.out.println("serverpath :" + serverpath);
		String generated = request.getParameter("generate") != null ? request.getParameter("generate") : "N";
//System.out.println("generated :" + generated);
		String reportid = request.getParameter("reportid") != null ? request.getParameter("reportid") : "";
//System.out.println("reportid :" + reportid);
		String repExportType = request.getParameter("repExportType") != null ? request.getParameter("repExportType"): "";
		String repType = request.getParameter("repType") != null ? request.getParameter("repType") : "1";
		String save = request.getParameter("save") != null ? request.getParameter("save") : "";
//System.out.println("save :" + save);
//System.out.println("repType :" + repType);
		String repheader = request.getParameter("repheader") != null ? request.getParameter("repheader") : "";
		String layout = request.getParameter("layout") != null ? request.getParameter("layout") : "P";
		String colwidth = request.getParameter("colwidth") != null ? request.getParameter("colwidth") : "";

		if ("N".equalsIgnoreCase(generated)) {
			String selcolparam = request.getParameter("selcolparam") != null ? request.getParameter("selcolparam"): "";
			String REFCLASS[] = request.getParameterValues("SEL_REFCLASS") != null ? request.getParameterValues("SEL_REFCLASS"): new String[0];
			String VIPTYPE[] = request.getParameterValues("SEL_VIPTYPE") != null ? request.getParameterValues("SEL_VIPTYPE"): new String[0];
			String COMPLIANCE[] = request.getParameterValues("SEL_COMPLIANCEREMARKS") != null ? request.getParameterValues("SEL_COMPLIANCEREMARKS"): new String[0];
			String[] REFCATEGORY = request.getParameterValues("SEL_REFCATEGORY") != null ? request.getParameterValues("SEL_REFCATEGORY"): new String[0];
			String[] SUBJECTTYPE = request.getParameterValues("SEL_SUBJECTTYPE") != null ? request.getParameterValues("SEL_SUBJECTTYPE"): new String[0];
			String[] MARKTO = request.getParameterValues("SEL_MARKTO") != null ? request.getParameterValues("SEL_MARKTO"): new String[0];
//			String[] selcolval = request.getParameterValues("sel_tabcols") != null ? request.getParameterValues("sel_tabcols"): new String[0];
			String[] seltabOrderBy = request.getParameterValues("sel_tabOrderBy") != null ? request.getParameterValues("sel_tabOrderBy"): new String[0];
 
			String ROLEID = sessionBean.getLOGINASROLEID() != null ? sessionBean.getLOGINASROLEID(): "";
			
			String REFCONDITION = StringFormat.nullString(request.getParameter("REFCONDITION"));
			String REFNOFROM = StringFormat.nullString(request.getParameter("REFNOFROM"));
			String REFNOTO = StringFormat.nullString(request.getParameter("REFNOTO"));
			
			String BUDGETYEAR = StringFormat.nullString(request.getParameter("BUDGETYEAR"));

			String INDATECONDITION = StringFormat.nullString(request.getParameter("INDATECONDITION"));
			String INDATEFROM = StringFormat.nullString(request.getParameter("INDATEFROM"));
			String INDATETO = StringFormat.nullString(request.getParameter("INDATETO"));
			
			String LETDATECONDITION = StringFormat.nullString(request.getParameter("LETDATECONDITION"));
			String LETDATEFROM = StringFormat.nullString(request.getParameter("LETDATEFROM"));
			String LETDATETO = StringFormat.nullString(request.getParameter("LETDATETO"));
			
			String RECEIVEDFROM = StringFormat.nullString(request.getParameter("RECEIVEDFROM"));
			String STATUS = StringFormat.nullString(request.getParameter("STATUS")).toUpperCase();
			String STATE = StringFormat.nullString(request.getParameter("STATE")).toUpperCase();
			String SUBJECT = StringFormat.nullString(request.getParameter("SUBJECT"));
			String ACKNBY = StringFormat.nullString(request.getParameter("ACKNBY"));
			
			String ACKNDATECONDITION = StringFormat.nullString(request.getParameter("ACKNDATECONDITION"));
			String ACKNDATEFROM = StringFormat.nullString(request.getParameter("ACKNDATEFROM"));
			String ACKNDATETO = StringFormat.nullString(request.getParameter("ACKNDATETO"));
			
			String MARKINGDATECONDITION = StringFormat.nullString(request.getParameter("MARKINGDATECONDITION"));
			String MARKINGDATEFROM = StringFormat.nullString(request.getParameter("MARKINGDATEFROM"));
			String MARKINGDATETO = StringFormat.nullString(request.getParameter("MARKINGDATETO"));
			
			String TARGETDATECONDITION = StringFormat.nullString(request.getParameter("TARGETDATECONDITION"));
			String TARGETDATEFROM = StringFormat.nullString(request.getParameter("TARGETDATEFROM"));
			String TARGETDATETO = StringFormat.nullString(request.getParameter("TARGETDATETO"));
			
			String REMARKS = StringFormat.nullString(request.getParameter("REMARKS"));
			String SIGNEDBY = StringFormat.nullString(request.getParameter("SIGNEDBY"));

			String SIGNEDONCONDITION = StringFormat.nullString(request.getParameter("SIGNEDONCONDITION"));
			String SIGNEDONFROM = StringFormat.nullString(request.getParameter("SIGNEDONFROM"));
			String SIGNEDONTO = StringFormat.nullString(request.getParameter("SIGNEDONTO"));
			
			String TAG = StringFormat.nullString(request.getParameter("TAG"));
			String RLY = StringFormat.nullString(request.getParameter("RLY"));
			String PLACE = StringFormat.nullString(request.getParameter("PLACE"));

			String PLCRLYCONDITION = StringFormat.nullString(request.getParameter("SIGNEDONCONDITION"));
			String PLCRLYFROM = StringFormat.nullString(request.getParameter("SIGNEDONFROM"));
			String PLCRLYTO = StringFormat.nullString(request.getParameter("SIGNEDONTO"));
			
			String FinalSelection = "";
			String condREFCLASS = "";
			for (int i = 0; i < REFCLASS.length; i++) {
				condREFCLASS += ",'" + REFCLASS[i] + "'";
			}
			if (REFCLASS.length == 1)
				condREFCLASS = " AND TRNREFERENCE.REFCLASS = "+condREFCLASS.substring(1)+" ";
			else if (REFCLASS.length > 0)
				condREFCLASS = " AND TRNREFERENCE.REFCLASS IN ("+condREFCLASS.substring(1)+") ";

			String condVIPTYPE= "";
			for (int i = 0; i < VIPTYPE.length; i++) {
				condVIPTYPE += ",'" + VIPTYPE[i] + "'";
			}
			if (VIPTYPE.length == 1)
				condVIPTYPE = " AND TRNREFERENCE.ADDVIPTYPE = "+condVIPTYPE.substring(1)+" ";
			else if (VIPTYPE.length > 0)
				condVIPTYPE = " AND TRNREFERENCE.ADDVIPTYPE IN ("+condVIPTYPE.substring(1)+") ";
			
			String condCOMPLIANCE= "";
			for (int i = 0; i < COMPLIANCE.length; i++) {
				condCOMPLIANCE += ",'" + COMPLIANCE[i] + "'";
			}
			if (COMPLIANCE.length == 1)
				condCOMPLIANCE = " AND TRNREFERENCE.COMPLIANCE = "+condCOMPLIANCE.substring(1)+" ";
			else if (COMPLIANCE.length > 0)
				condCOMPLIANCE = " AND TRNREFERENCE.COMPLIANCE IN ("+condCOMPLIANCE.substring(1)+") ";
			
			
			String condREFCATEGORY = "";
			for (int i = 0; i < REFCATEGORY.length; i++) {
				condREFCATEGORY += ",'" + REFCATEGORY[i] + "'";
			}
			if (REFCATEGORY.length == 1)
				condREFCATEGORY = " AND TRNREFERENCE.REFCATEGORY = "+condREFCATEGORY.substring(1)+" ";
			else if (REFCATEGORY.length > 0)
				condREFCATEGORY = " AND TRNREFERENCE.REFCATEGORY IN ("+condREFCATEGORY.substring(1)+") ";

			String condSUBJECTTYPE = "";
			for (int i = 0; i < SUBJECTTYPE.length; i++) {
				condSUBJECTTYPE += " OR TRNREFERENCE.SUBJECTCODE = '" + SUBJECTTYPE[i] + "' ";
			}
			if (SUBJECTTYPE.length > 0)
				condSUBJECTTYPE = " AND (" + condSUBJECTTYPE.substring(3) + ") ";
			
			String condMARKTO = "";
			for (int i = 0; i < MARKTO.length; i++) {
				condMARKTO += " OR TRNMARKING.MARKINGTO =  " + MARKTO[i] + " ";
			}
			if (MARKTO.length > 0)
				condMARKTO = " AND (" + condMARKTO.substring(3) + ") ";

			ROLEID = ROLEID.length() > 0 ? " AND TRNREFERENCE.ROLEID = "+ROLEID+" " : "";
			REFCONDITION = REFNOFROM.length() > 0 ? "=".equalsIgnoreCase(REFCONDITION) ? " AND TRNREFERENCE.REFNO = '"+REFNOFROM+"'" : REFNOTO.length() > 0 ? " AND (TRNREFERENCE.REFNO BETWEEN '"+REFNOFROM+"' AND '"+REFNOTO+"')":" ":"";
			BUDGETYEAR = BUDGETYEAR.length() > 0 ? " AND TRNREFERENCE.ISBUDGET = '"+BUDGETYEAR+"' " : "";
			INDATECONDITION = INDATEFROM.length() > 0 ? "=".equalsIgnoreCase(INDATECONDITION) ? " AND TO_CHAR(TRNREFERENCE.INCOMINGDATE,'DD/MM/YYYY') = '"+INDATEFROM+"'" : INDATETO.length() > 0 ? " AND TO_DATE(TO_CHAR(TRNREFERENCE.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+INDATEFROM+"','DD/MM/YYYY') AND TO_DATE('"+INDATETO+"','DD/MM/YYYY')":" ":"";
			LETDATECONDITION = LETDATEFROM.length() > 0 ? "=".equalsIgnoreCase(LETDATECONDITION) ? " AND TO_CHAR(TRNREFERENCE.LETTERDATE,'DD/MM/YYYY') = '"+LETDATEFROM+"'" : LETDATETO.length() > 0 ? " AND TO_DATE(TO_CHAR(TRNREFERENCE.LETTERDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+LETDATEFROM+"','DD/MM/YYYY') AND TO_DATE('"+LETDATETO+"','DD/MM/YYYY')":" ":"";
			RECEIVEDFROM = RECEIVEDFROM.length() > 0 ? " AND UPPER(TRNREFERENCE.REFERENCENAME) LIKE UPPER('%"+RECEIVEDFROM+"%')" : "";
			STATUS = STATUS.length() > 0 ? " AND UPPER(TRNREFERENCE.VIPSTATUS) LIKE UPPER('%"+STATUS+"%')" : "";
			STATE = STATE.length() > 0 ? " AND TRNREFERENCE.STATECODE= '"+STATE+"'" : "";
			SUBJECT = SUBJECT.length() > 0 ? " AND UPPER(TRNREFERENCE.SUBJECT) LIKE UPPER('%"+SUBJECT+"%')" : "";
			ACKNBY = ACKNBY.length() > 0 ? " AND TRNREFERENCE.ACKBY LIKE '%"+ACKNBY+"%'" : "";
			ACKNDATECONDITION = ACKNDATEFROM.length() > 0 ? "=".equalsIgnoreCase(ACKNDATECONDITION) ? " AND TO_CHAR(TRNREFERENCE.ACKDATE,'DD/MM/YYYY') = '"+ACKNDATEFROM+"'" : ACKNDATETO.length() > 0 ? " AND TO_DATE(TO_CHAR(TRNREFERENCE.ACKDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+ACKNDATEFROM+"','DD/MM/YYYY') AND TO_DATE('"+ACKNDATETO+"','DD/MM/YYYY')":" ":"";
			MARKINGDATECONDITION = MARKINGDATEFROM.length() > 0 ? "=".equalsIgnoreCase(MARKINGDATECONDITION) ? " AND TO_CHAR(TRNMARKING.MARKINGDATE,'DD/MM/YYYY') = '"+MARKINGDATEFROM+"'" : MARKINGDATETO.length() > 0 ? " AND TO_DATE(TO_CHAR(TRNMARKING.MARKINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+MARKINGDATEFROM+"','DD/MM/YYYY') AND TO_DATE('"+MARKINGDATETO+"','DD/MM/YYYY')":" ":"";
			TARGETDATECONDITION = TARGETDATEFROM.length() > 0 ? "=".equalsIgnoreCase(TARGETDATECONDITION) ? " AND TO_CHAR(TRNMARKING.TARGETDATE,'DD/MM/YYYY') = '"+TARGETDATEFROM+"'" : TARGETDATETO.length() > 0 ? " AND TO_DATE(TO_CHAR(TRNMARKING.TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+TARGETDATEFROM+"','DD/MM/YYYY') AND TO_DATE('"+TARGETDATETO+"','DD/MM/YYYY')":" ":"";
			REMARKS = REMARKS.length() > 0 ? " AND TRNREFERENCE.REMARKS LIKE '%"+REMARKS+"%'" : "";
			SIGNEDBY = SIGNEDBY.length() > 0 ? " AND TRNREFERENCE.SIGNEDBY = "+SIGNEDBY+" " : "";
			SIGNEDONCONDITION = SIGNEDONFROM.length() > 0 ? "=".equalsIgnoreCase(SIGNEDONCONDITION) ? " AND TO_CHAR(TRNREFERENCE.SIGNEDON,'DD/MM/YYYY') = '"+SIGNEDONFROM+"'" : SIGNEDONTO.length() > 0 ? " AND TO_DATE(TO_CHAR(TRNREFERENCE.SIGNEDON,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+SIGNEDONFROM+"','DD/MM/YYYY') AND TO_DATE('"+SIGNEDONTO+"','DD/MM/YYYY')":" ":"";
			PLCRLYCONDITION = PLCRLYFROM.length() > 0 ? "=".equalsIgnoreCase(PLCRLYCONDITION) ? " AND TO_CHAR(TRNREFERENCE.SIGNEDON,'DD/MM/YYYY') = '"+PLCRLYFROM+"'" : PLCRLYTO.length() > 0 ? " AND TO_DATE(TO_CHAR(TRNREFERENCE.SIGNEDON,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+PLCRLYFROM+"','DD/MM/YYYY') AND TO_DATE('"+PLCRLYTO+"','DD/MM/YYYY')":" ":"";
			TAG = TAG.length() > 0 ? " AND (UPPER(TRNREFERENCE.TAG1) LIKE UPPER('%"+TAG+"%') OR UPPER(TRNREFERENCE.TAG2) LIKE UPPER('%"+TAG+"%') OR UPPER(TRNREFERENCE.TAG3) LIKE UPPER('%"+TAG+"%') OR UPPER(TRNREFERENCE.TAG4) LIKE UPPER('%"+TAG+"%'))" : "";
			RLY = RLY.length() > 0 ? " AND TRNREFERENCE.ODSRAILWAY = '"+RLY+"'" : "";
			PLACE = PLACE.length() > 0 ? " AND TRNREFERENCE.ODSPLACE = '"+PLACE+"'" : "";
			
			//Ankit Code
			String refwithcurrentSql ="(select substr(max(to_char(movedate,'yyyymmddhh24miss') ||'/' ||toid ||'-'|| toname),instr(max(to_char(movedate,'yyyymmddhh24miss') ||'/' ||toid ||'-'|| toname),'-')+1) || ' - ' ||max(to_char(movedate,'dd/mm/yyyy hh24:mi') ) from "+ 
                    " TRNREFMOVEMENT A WHERE EOFFICERECIEPTNO =TRNREFERENCE.EOFFICERECEIPTNO)";
			
			String filewithcurrentSql ="(SELECT  max(movedate ||'/' ||toid ||'-'|| toname) "
					+ " FROM TRNFILEMOVEMENT A WHERE EOFFICEFILENO=(select max(b.fileno) from trnrefmovement b "+
					" where b.EOFFICEREFNO =TRNREFERENCE.EOFFICEREFNO and  b.fileno is not null))";

			String refmovementSql ="(select  listagg(  fromname || ' - ' || toname ||' on '|| movedate  , '; ' ) " +
					" within group ( order by movedate) "+
					" from trnrefmovement where EOFFICERECIEPTNO=TRNREFERENCE.EOFFICERECEIPTNO)";
			
			
			//if the limit exceeds to 4000 (oracle limit) the querry will have to be changed to the following querry
			//rounak code //24/07/2017
			/*String refmovementSql = "(select rtrim(xmlagg(xmlelement(e,(fromname || ' - ' || toname ||' on '|| movedate ),';').extract('//text()') order by movedate).GetClobVal(),',')  "
					+ "from trnrefmovement where EOFFICERECIEPTNO=TRNREFERENCE.EOFFICERECEIPTNO)";*/

			String filemovementSql ="(select  listagg( fromid || ' ( ' || fromname || ' ) - ' || toid || ' ( ' || toname ||' ) on '|| movedate  , '; ') "
					+ " within group ( order by movedate) "+
					" from trnfilemovement where eofficefileno=(select max(b.fileno) from trnrefmovement b,trnreference c "+
					" where c.EOFFICEREFNO=b.EOFFICEREFNO and c.eofficerefno=TRNREFERENCE.EOFFICEREFNO and b.fileno is not null))";
			//=====
			
			
			 HashMap<String,String> hm=new HashMap<String,String>();  
			 hm.put("1", "TRNREFERENCE.LINKREFID");
			 hm.put("2", "TRNREFERENCE.REFNO");
			 hm.put("3", "TO_CHAR(TRNREFERENCE.INCOMINGDATE,'DD-MM-YYYY')");
			 hm.put("4", "TO_CHAR(TRNREFERENCE.LETTERDATE,'DD-MM-YYYY')");
			 hm.put("5",  "TRNREFERENCE.REFERENCENAME");
			 hm.put("6", "TRNREFERENCE.VIPSTATUS");
			 hm.put("7", "TRNREFERENCE.STATECODE");
			 hm.put("8", "TRNREFERENCE.VIPPARTY");
			 hm.put("9", "(SELECT a.SUBJECTNAME FROM MSTSUBJECT a WHERE a.SUBJECTCODE=TRNREFERENCE.SUBJECTCODE)");
			 hm.put("10", "TRNREFERENCE.SUBJECT");
			 hm.put("11", "GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = TRNREFERENCE.REFID AND MARKINGSEQUENCE = 1)) ");
			 hm.put("12", "TO_CHAR(TRNMARKING.MARKINGDATE,'DD-MM-YYYY')");
			 hm.put("13", "TO_CHAR(TRNMARKING.TARGETDATE,'DD-MM-YYYY')");
			 hm.put("14", "TRNREFERENCE.REMARKS");
			 hm.put("15", "(SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNREFERENCE.SIGNEDBY)");
			 hm.put("16", "TO_CHAR(TRNREFERENCE.SIGNEDON,'DD-MM-YYYY')");
			 hm.put("17", "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNREFERENCE.ACKBY) ");
			 hm.put("18", "TO_CHAR(TRNREFERENCE.ACKDATE,'DD-MM-YYYY')");
			 hm.put("19", "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNREFERENCE.IMARKINGTO)");
			 hm.put("20", "TRNREFERENCE.REGISTRATIONNO");
			 hm.put("21", "TO_CHAR(TRNREFERENCE.REGISTRATIONDATE,'DD-MM-YYYY')");
			 hm.put("22", "TRNREFERENCE.FILENO");
			 hm.put("23", "(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = TRNREFERENCE.FILESTATUS1)");
			 hm.put("24", "(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = TRNREFERENCE.FILESTATUS2) ");
			 hm.put("25", "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNREFERENCE.DMARKINGTO)");
			 hm.put("26", "TO_CHAR(TRNREFERENCE.DMARKINGDATE,'DD-MM-YYYY')");
			 hm.put("27", "TRNREFERENCE.REPLYTYPE");
			 hm.put("28", "TRNREFERENCE.ISBUDGET");
			 hm.put("29", "TRNREFERENCE.ADDVIPTYPE");
			 hm.put("30", "NVL(TRNREFERENCE.K1,TRNREFERENCE.KEYWORD1)");
			 hm.put("31", "NVL(TRNREFERENCE.K2,TRNREFERENCE.KEYWORD2)");
			 hm.put("32", "NVL(TRNREFERENCE.K3,TRNREFERENCE.KEYWORD3)");
			 hm.put("33", "TRNREFERENCE.MOBILENO");
			 hm.put("34", "TRNREFERENCE.VIPCONSTITUENCY");
			 
			 hm.put("35", " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '10' AND CODE<>'00' AND CODE = TRNREFERENCE.COMPLIANCE)");
			 hm.put("36", "TRNREFERENCE.COMPLIANCEREMARKS");
			 hm.put("37", "TO_CHAR(TRNREFERENCE.COMPLIANCEDATE,'DD-MM-YYYY')");
			 hm.put("38", "TRNREFERENCE.TOURREMARKS ");
			 
			 //Ankit Code
			 hm.put("39", refwithcurrentSql);
			 hm.put("40", filewithcurrentSql);
			 hm.put("41", refmovementSql);
			 hm.put("42", filemovementSql);
			//======
			 
			 //Rounak Code
			 hm.put("43", "TRNREFERENCE.EOFFICEREFNO");
			 //|| ' - '|| TRNREFERENCE.EOFFICERECEIPTNO 
			 //=====

			String[] newselcolparam = selcolparam.split("~");
			for (int i = 0; i < newselcolparam.length; i++) {
				if (newselcolparam[i].length() > 0) {
					//System.out.println(newselcolparam[i]);
					String[] tempcol = newselcolparam[i].split("[/^]");
					tempcol[0]=hm.get(tempcol[0]);
				
					//System.out.println(tempcol.length);
					if (tempcol.length > 1)
						FinalSelection = FinalSelection.concat(", "
								+ tempcol[0] + " \"" + tempcol[1] + "\"");
				}
			}
			//System.out.println("condCOMPLIANCE"+condCOMPLIANCE);
			
			FinalSelection = "SELECT DISTINCT " +  FinalSelection.substring(1)+ " ,trnreference.refid, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = TRNREFERENCE.REFID AND M.TYPE='R'),'0') ISATTACHMENT ";
			String conditions = condREFCLASS + condVIPTYPE+ condCOMPLIANCE+condREFCATEGORY + condSUBJECTTYPE + condMARKTO + ROLEID + REFCONDITION + BUDGETYEAR + INDATECONDITION + LETDATECONDITION + RECEIVEDFROM + STATUS + STATE + SUBJECT + ACKNBY + ACKNDATECONDITION + MARKINGDATECONDITION + TARGETDATECONDITION + REMARKS + SIGNEDBY + SIGNEDONCONDITION + PLCRLYCONDITION + TAG + RLY + PLACE ; 
			
			String orderByClause=" TRNREFERENCE.REFID";
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
			conditions+=orderByClause;
			//System.out.println("WHERE Clause	: " + conditions);
			strSQL = new CustomReportDAO().generateSQL(FinalSelection,conditions, repType);
			//System.out.println("strSQL	: " + strSQL);
			if ("Y".equalsIgnoreCase(save)) {
				new CustomReportDAO().updateReportSQL(reportid, strSQL, repType);
			}
		}
		else {
			String[] result = new CustomReportDAO().getReportSQL(reportid);
			strSQL = result[1];
			repheader = result[2];
			layout = result[3];
			colwidth = result[4];
		}

		//System.out.println("My Report for report id : "+reportid+" :: " + strSQL);
		ArrayList<MastersReportBean> mstarr = new ArrayList<MastersReportBean>();
		mstarr.add(new MastersReportDAO().getReportArray(strSQL, repheader,	layout, colwidth));
		request.setAttribute("mstarr", mstarr);
		if (repExportType.equals("E")) {
			String RepPath = "";
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
			request.getRequestDispatcher("ReportHTMLOutput.jsp?reportid=" + reportid).forward(
					request, response);
		}
	}
}