package in.org.cris.mrsectt.util;



import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.TrnReminder;
import in.org.cris.mrsectt.dao.CommonDAO;
import in.org.cris.mrsectt.dao.ReminderAutoDAO;
import in.org.cris.mrsectt.dbConnection.DBConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;



public class EmailJob   implements  Job {
	static Logger log = LogManager.getLogger(EmailJob.class);
//	String emailTo = "mrcell@rb.railnet.gov.in";
	String emailTo = "mrcellrb@gmail.com";
	public static final Map<Integer, String> statusSymbol;
    static
    {
    	statusSymbol = new HashMap<Integer, String>();
    	statusSymbol.put(-1, "&#916;");
    	statusSymbol.put(1, "&#10004;");
    	statusSymbol.put(0, "&#8709;");
    }
    
	//
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
				//log.warn("Trying to Execute  Job : "+QuartzInit.count++);
				
				
				/*
				for(WebAppBean host: new WebCheckerDAO().getHosts()){
					
					List<HostStatus>  status = new WebCheckerDAO().getHostStatusForNDaysOfHost(1, host.getHost());
					String message="<table rules='all' style='border-color: #666;'>"
							+ "  	<thead>"
							+ "  		<tr  style='background: #eee;'>"
							+ "  			<th>Host</th>"
							+ "  			<th>TimeStamp</th>"
							+ "  			<th><span >Login Available</span></th>"
							+ "  			<th><span >Login Success</span></th>"
							+ "  			<th><span >Logout Success</span></th>"
							+ "  			<th>Remarks</th>"
							+ "  		</tr>"
							+ "  	</thead>"
							+ "  	<tbody>";
					
					for(HostStatus hs : status){
						message+="<tr>"
								+ "	  				<td>"+hs.getHost()+"</td>"
								+ "	  				<td>"+hs.getSTATUSTS()+"</td>"
								+ "	  				<td align='center'>"+statusSymbol.get(hs.getLoginAvailable())+"</td>"
								+ "	  				<td align='center'>"+statusSymbol.get(hs.getLoginSuccess())+"</td>"
								+ "	  				<td align='center'>"+statusSymbol.get(hs.getLogoutSucces())+"</td>"
								+ "	  				<td>"+hs.getRemarks()+"</td>"
								+ "	  			</tr>";
								
					}
					
					
					message+="</tbody>  </table>  ";
					
					
				/*	message+=" <br>  <img src='cid:1.png' /> = Success";
					message+=" <br>  <img src='cid:0.png' /> = Failed";
					message+=" <br>  <img src='cid:-1.png' /> = Warning";
				
					
					
					
				} */
				
		
		
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
		 DBConnection con = new DBConnection();
		 con.openConnection();
	 		String dept =	" SELECT DISTINCT A.MARKINGTO,GETROLENAME(A.MARKINGTO) ROLENAME,(select emailid from mstrole where roleid=A.MARKINGTO)"+
	 						" FROM TRNMARKING A,TRNREFERENCE B"+
	 						" WHERE TO_CHAR(A.TARGETDATE+1,'DD/MM/YYYY')= TO_CHAR(sysdate,'DD/MM/YYYY')"+
							"  AND A.REFID=B.REFID AND B.TENUREID>='12' and b.refclass in ('A','M') "+
							" AND A.MARKINGSEQUENCE=1 AND B.FILENO IS NULL AND A.MARKINGTO IS NOT NULL ORDER BY ROLENAME";
		ArrayList<CommonBean> deptNameList = (new CommonDAO()).getSQLResult(dept, 3);
		//System.out.println("dept:::" + dept);
		String olddept="";
		int mailcount=0;
		for (int i = 0; i < deptNameList.size(); i++) {
			
			CommonBean cb = (CommonBean) deptNameList.get(i);
			emailTo="".equals(cb.getField3())?"mrcell@rb.railnet.gov.in":cb.getField3();
			//emailTo="".equals(cb.getField3())?"mrcell@rb.railnet.gov.in":"mrcell@rb.railnet.gov.in";
					//  if(!cb.getField1().equalsIgnoreCase(olddept)){
			
				ArrayList<TrnReminder> dataArr = new ArrayList<TrnReminder>();
				dataArr =  (new ReminderAutoDAO()).getReminderReportDataEMAIL(cb.getField1());
				for (int j = 0; j < dataArr.size(); j++) {
					  TrnReminder rb = (TrnReminder) dataArr.get(j);
					  String msgText = "<BODY BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>";
					  msgText+="<div id='reportData' align='left' style='vertical-align: top;'>"+
					  			"<table width='100%'  align='center' >"+
					  			"<tr align='center'><td colspan='5'><FONT size='4'><b></b></FONT></td></tr>"+
								"<tr align='center'><td colspan='5'><FONT size='2'></FONT></td></tr>"+
								"<tr align='center'><td colspan='5' style='height: 5px;' align='right'><br /><br />&nbsp;<font><b><u>" +
								"</u></b></font></td></tr>"+
								"<tr><td colspan='5'><p style='text-align: justify; font-size: 15px; font: Tahoma;'>"+
								"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>"+
								"<p style='text-align: justify; font-size: 15px; font: Tahoma;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
								"You are requested to kindly adhere to the specified target date and furnish the consolidated current status of the case immediately for" +
								" kind perusal of Hon’ble MR.</p></td></tr>"+
								"<tr><td colspan='5'><br /><br /><table align='center' width='100%'><tr><td colspan='3'> <b><u></u></b></td>"+
								"<td colspan='2' align='right'><b></b><br /></td></tr></table></td></tr>"+
								"<tr><td><table align='center'>	<tr><td nowrap='nowrap' style='text-align: left;'>" +
								"<u>Details of the Pending Case</u></td>	</tr></table></td></tr></table>" +
								"<br><table width='100%'  align='center' style='page-break-after: always;display: table-header-group;'>" +
								"<thead  style='display: table-header-group;'>" +
								"<tr><td class='bototmDashedth'><b><nobr></nobr>&nbsp;&nbsp;</b></td>" +
								"<td class='bototmDashedth'><b><nobr></nobr></b></td>" +
								"<td class='bototmDashedth'><b><nobr></nobr>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></td>" +
								"<td class='bototmDashedth'><b><nobr>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</nobr>&nbsp;&nbsp;</b></td>" +
								"<td class='bototmDashedth'><b><nobr></nobr></b></td></tr>	</thead><br>" +
								"<tbody id='content'>" ;
					  msgText+="<tr class='trodd'>" +
					  		"<td class='bototmDashed'></td>" +
					  		"<td class='bototmDashed'>&nbsp;Reference No.: <b>"+rb.getREFNO()+"</b><br><br>" +
					  		"<b>&nbsp;Marking Date: &nbsp;</b><b>"+rb.getMARKINGDATE()+"</b><br><br><b>&nbsp;Received from: &nbsp;</b></td>" +
					  		"<td class='bototmDashed'><b>"+rb.getREFERENCENAME()+","+rb.getVIPSTATUS()+"," +
					  		""+rb.getSTATECODE()+"</b><br><br><b>&nbsp;Subject:&nbsp;</b><br/><b>"+rb.getSUBJECT() +"</b><br><br><b>&nbsp;Target Date: &nbsp;</b></td>" +
					  		"<td><b>"+rb.getTARGETDATE()+"</b>&nbsp;&nbsp;<br><br>" +
					  		"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; MR Coordination Cell, Railway Board";
					  msgText+="</tbody></table></div>";
					  SendMail sm=new SendMail();
					  mailcount++;
					  try{
						  String query =	"insert into trnreminderemail(REFID,REMINDERSEQUENCE,REMINDERFROM,REMINDERTO,REMINDERDATE,REMINDERREMARK," +
						  		"REMINDERACTION,REMINDERACTIONDATE,LOGINID,CHANGEDATE,TARGETDAYS,TARGETDATE,REMINDERTYPE,SIGNEDBY,SIGNEDON,MAILDATE) " +
						  		"values('"+rb.getREFID()+"',1,'"+rb.getREMINDERFROM()+"','"+rb.getREMINDERTO()+"',sysdate,''," +
						  		" '','','cris',sysdate,'"+rb.getTARGETDAYS()+"','"+rb.getTARGETDATE()+"','','973',sysdate,sysdate) ";
						//System.out.println("query:::" + query);
						con.insert(query);
						sm.send(emailTo, "Reminder for MR Cell Reference "+rb.getREFNO()+"-"+rb.getSUBJECT(), msgText,1, new String [] {});	
						  if(mailcount==9)
						  {
						  mailcount=0;
						  SendMail.transport.close();
						  Thread.sleep(70000);
						  }
					  }
					  catch(Exception e)
					  {
						  e.printStackTrace();
					  }
				}
				
			
			olddept= cb.getField1();
			
		//	}
			
	}
			
		con.closeConnection();		
		 
			}
}
