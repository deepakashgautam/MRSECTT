package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstLogin;
import in.org.cris.mrsectt.Beans.TrnReminder;
import in.org.cris.mrsectt.util.MailSend;

import java.util.ArrayList;

import javax.servlet.ServletContext;

public class ReminderEmailDAO {

	public void sendReminderEmail(ServletContext context)
	{
		 //MstLogin sessionBean = (MstLogin)session.getAttribute("MstLogin");
		 String serverDate="";
		 serverDate = CommonDAO.getSysDate("dd/MM/yyyy"); 
		 /*
		  	String FREMDATE=request.getParameter("FREMDATE")!=null ? request.getParameter("FREMDATE") : "";
		    String TREMDATE=request.getParameter("TREMDATE")!=null ? request.getParameter("TREMDATE") : "";
			String CLASS=request.getParameter("CLASS")!=null ? request.getParameter("CLASS") : "";
			String REQOF=request.getParameter("REQOF")!=null ? request.getParameter("REQOF") : "";
			String DEPT=request.getParameter("DEPT")!=null ? request.getParameter("DEPT") : "";
			String STATE=request.getParameter("STATE")!=null ? request.getParameter("STATE") : "";
			String REMARKS=request.getParameter("REMARKS")!=null ? request.getParameter("REMARKS") : "";
			String DREMARKS=request.getParameter("DREMARKS")!=null ? request.getParameter("DREMARKS") : "";
			String SUBJECT=request.getParameter("SUBJECT")!=null ? request.getParameter("SUBJECT") : "";
			String SIGNEDBY=request.getParameter("SIGNEDBY")!=null ? request.getParameter("SIGNEDBY") : "";
			String ISCONF = sessionBean.getISCONF();
			
		String queryRefClass = "SELECT REFCLASS, INOUT FROM MSTCLASS WHERE TENUREID = '"+sessionBean.getTENUREID()+"' AND INOUT='I'"; 
		ArrayList<CommonBean> refClassList = (new CommonDAO()).getSQLResult(queryRefClass, 2);
		
		String querydept = "SELECT DISTINCT A.DEPTCODE,A.DEPTNAME FROM MSTDEPT A ORDER BY A.DEPTNAME	"; 
	 	ArrayList<CommonBean> deptList = (new CommonDAO()).getSQLResult(querydept, 2);
	 	
	 	String queryState = "SELECT STATECODE, STATENAME FROM MSTSTATE"; 
		ArrayList<CommonBean> stateList = (new CommonDAO()).getSQLResult(queryState, 2);
	 	
	 	String querySignedBy = " SELECT B.PREFERREDID ROLEID,(SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.PREFERREDID ) ROLENAME	"+
	 							" FROM MSTPREFERREDLIST B WHERE PREFERREDTYPE='2' AND ROLEID='"+sessionBean.getLOGINASROLEID()+"' ORDER BY 2"; 
		ArrayList<CommonBean> querySignedByList = (new CommonDAO()).getSQLResult(querySignedBy, 2);
	 
	 	String SignedBy = " SELECT DISTINCT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = '"+SIGNEDBY+"'"; 
		String SignedByName = (new CommonDAO()).getStringParam(SignedBy);
		
			tmpCond += ISCONF.equalsIgnoreCase("0")? " AND B.ISCONF = '0'": " AND B.ISCONF IN ('0','1')";
			tmpCond += CLASS.length() > 0? " AND B.REFCLASS = '"+CLASS+"'": "";
			tmpCond += DEPT.length() > 0? " AND A.MARKINGTO = '"+DEPT+"'": "";
			tmpCond += STATE.length() > 0? " AND B.STATECODE = '"+STATE+"'": "";
			tmpCond += REQOF.length() > 0? " AND REFERENCENAME LIKE '%"+REQOF+"%'": "";
			tmpCond += SUBJECT.length() > 0? " AND B.SUBJECT LIKE '%"+SUBJECT+"%'": "";
			tmpCond += DREMARKS.length() > 0? " AND B.REMARKS LIKE '%"+DREMARKS+"%'": "";
*/
	 		String dept =	" SELECT DISTINCT A.MARKINGTO,GETROLENAME(A.MARKINGTO) ROLENAME"+
	 						" FROM TRNMARKING A,TRNREFERENCE B"+
	 						" WHERE TO_DATE(TO_CHAR(A.TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('05/12/2014','DD/MM/YYYY') AND TO_DATE('06/12/2014','DD/MM/YYYY')"+
							" AND B.REFCLASS='A' AND A.REFID=B.REFID AND B.TENUREID>='12'"+
							" AND A.MARKINGSEQUENCE=1 AND B.FILENO IS NULL AND A.MARKINGTO IS NOT NULL ORDER BY ROLENAME";
		ArrayList<CommonBean> deptNameList = (new CommonDAO()).getSQLResult(dept, 2);
		//System.out.println("dept:::" + dept);
		String olddept="";
		String msgText = "<BODY BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>";
		for (int i = 0; i < deptNameList.size(); i++) {
			
			CommonBean cb = (CommonBean) deptNameList.get(i);
					  if(!cb.getField1().equalsIgnoreCase(olddept)){
						  msgText+="<div id='reportData' align='left' style='vertical-align: top;'>"+
						  			"<table width='100%'  align='center' >"+
						  			"<tr align='center'><td colspan='5'><FONT size='4'><b> OFFICE OF MR </b></FONT></td></tr>"+
									"<tr align='center'><td colspan='5'><FONT size='2'> RAILWAY BOARD </FONT></td></tr>"+
									"<tr align='center'><td colspan='5' style='height: 5px;' align='right'><br /><br />&nbsp;<font><b><u>" +
									"</u></b></font></td></tr>"+
									"<tr><td colspan='5'><p style='text-align: justify; font-size: 15px; font: Tahoma;'>"+
									"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Given below is a list of case(s) sent by this office, which are pending.</p>"+
									"<p style='text-align: justify; font-size: 15px; font: Tahoma;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
									"You are requested to expedite the action and furnish the consolidated current status of all the case(s) immediately for" +
									" the kind perusal of Hon’ble MR.</p></td></tr>"+
									"<tr><td colspan='5'><br /><br /><table align='center' width='100%'><tr><td colspan='3'> <b><u>"+cb.getField2()+"</u></b></td>"+
									"<td colspan='2' align='right'><b>TEST</b><br />"+serverDate+"</td></tr></table></td></tr>"+
									"<tr><td colspan='5'><table align='center'>	<tr><td nowrap='nowrap' style='text-align: left;'>LIST OF " +
									"PENDING CASE( s )</td>	</tr></table></td></tr></table>" +
									"<table width='100%'  align='center' style='page-break-after: always;display: table-header-group;'>" +
									"<thead  style='display: table-header-group;'>" +
									"<tr><td class='bototmDashedth'><b><nobr>SNo.</nobr>&nbsp;&nbsp;</b></td>" +
									"<td class='bototmDashedth'><b><nobr>Reference No.</nobr>&nbsp;&nbsp;</b></td>" +
									"<td class='bototmDashedth'><b><nobr>Reference from" +
									"&amp; content</nobr>&nbsp;&nbsp;</b></td>" +
									"<td class='bototmDashedth'><b><nobr>Target Date</nobr>&nbsp;&nbsp;</b></td>" +
									"<td class='bototmDashedth'><b><nobr>Mark [&#10004;] &amp; fill up the information</nobr></b></td></tr>	</thead>" +
									"<tbody id='content' >" ;
				ArrayList<TrnReminder> dataArr = new ArrayList<TrnReminder>();
				dataArr =  (new ReminderAutoDAO()).getReminderReportDataEMAIL(cb.getField1());
				for (int j = 0; j < dataArr.size(); j++) {
					  TrnReminder rb = (TrnReminder) dataArr.get(j);
					  msgText+="<tr class='trodd' style='page-break-inside:avoid;page-break-after: auto;'>" +
					  		"<td style='vertical-align: top;page-break-inside:avoid;page-break-after: auto;' class='bototmDashed'>"+( j+1)+"</td>" +
					  		"<td style='vertical-align: top;page-break-inside:avoid;page-break-after: auto;' class='bototmDashed'>"+rb.getREFNO()+"" +
					  		"<b>&nbsp;&nbsp;</b><br>"+rb.getINCOMINGDATE()+"<b>&nbsp;&nbsp;</b></td>" +
					  		"<td style='vertical-align: top;page-break-inside:avoid;page-break-after: auto;' class='bototmDashed'>"+rb.getREFERENCENAME()+","+rb.getVIPSTATUS()+"," +
					  		""+rb.getSTATECODE()+"<b>&nbsp;&nbsp;</b><br/>["+rb.getSUBJECT() +"]<b>&nbsp;&nbsp;</b></td>" +
					  		"<td style='vertical-align: top;page-break-inside:avoid;page-break-after: auto;' class='bototmDashed'>"+rb.getTARGETDATE()+"&nbsp;&nbsp;" +
					  		"<br /><br />Reply burst by "+ rb.getTARDAYS() +" day(s)</td>" +
					  		"<td style='vertical-align: top;page-break-inside:avoid;page-break-after: auto;' class='bototmDashed'>" +
					  		"<NOBR>BRANCH:................................ FILE NO.:.............................................</NOBR><br /><NOBR>[&nbsp;&nbsp;]" +
					  		"PUT UP TO ............................ ON .................. [&nbsp;&nbsp;] UNDER PROCESS</NOBR><br /><NOBR>[&nbsp;&nbsp;]" +
					  		" TRANSFERRED TO ........................ ON ............. [&nbsp;&nbsp;] NOT RECEIVED</NOBR><br /><NOBR>[&nbsp;&nbsp;] " +
					  		"REPLY AWAITED FROM RLY. [&nbsp;&nbsp;] REPLY ISSUED BY .......... ON .........</NOBR></td>	</tr>";
					  
					}
				msgText+="</tbody></table></div>";
			
			olddept= cb.getField1();
			}
	}
		String [] attachment={};
		
		String outMessage = mailSend(attachment, "mrcell@rb.railnet.gov.in", msgText, "", "", "Test Email",context);
}
	
	public String  mailSend(String[] Attachment,String email, String mailBody, String acknBy, String acknDate, String Subject, ServletContext context){
		String retValue = "";
		//String [] mailList={"sunilktripathi@gmail.com"};
		String [] mailList=email.split(",");
		//String [] ccmailList=null;
		String [] ccmailList={};
		//ccmailList=stcc.split(",");
		//String mailsubject="Engineering Control Morning Position - "+rlycode+" - "+posdate;
		String mailsubject=Subject;
		String mailbody=mailBody;
		try{
			(new MailSend()).SendMail(mailList, ccmailList, mailsubject, mailbody, Attachment, "mrcell@rb.railnet.gov.in", context);
//			(new MailSend()).SendMail(mailList, ccmailList, mailsubject, mailbody, Attachment, "suneelsambharia@cris.org.in", context);
			//System.out.println("mail id "+ email);
			retValue="GRNEmail Sent to - "+email+"";
		}catch(Exception e)	{
			e.printStackTrace();
			retValue="REDMail sending failed to - "+email+"";
	   	}finally{
	   	}
	   	return retValue;
	}
}
