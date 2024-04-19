package in.org.cris.mrsectt.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import in.org.cris.mrsectt.Beans.MstRole;
import in.org.cris.mrsectt.Beans.TrnReminder;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.util.StringFormat;

public class ReminderAutoDAO {
	static Logger log = LogManager.getLogger(ReminderAutoDAO.class);
	

	/*public ArrayList<TrnReminder> getReminderReportData(String ROLEID,String TENUREID,String FREMDATE,String TREMDATE,String CLASS,String DEPT,String STATE,String REQUESTOF, String ISCONF, String SUBJECT, String REMARKS, String vvip) {
		String strSQL = "";
		String tmpCond = "";
		ArrayList<TrnReminder> arr = new ArrayList<TrnReminder>();
		tmpCond += ISCONF.equalsIgnoreCase("0")? " AND B.ISCONF = '0'": " AND B.ISCONF IN ('0','1')";
		tmpCond += CLASS.length() > 0? " AND B.REFCLASS = '"+CLASS+"'": "";
		tmpCond += DEPT.length() > 0? " AND A.MARKINGTO = '"+DEPT+"'": "";
		tmpCond += STATE.length() > 0? " AND B.STATECODE = '"+STATE+"'": "";
		tmpCond += REQUESTOF.length() > 0? " AND REFERENCENAME LIKE '%"+REQUESTOF+"%'": "";
		tmpCond += SUBJECT.length() > 0? " AND B.SUBJECT LIKE '%"+SUBJECT+"%'": "";
		tmpCond += REMARKS.length() > 0? " AND B.REMARKS LIKE '%"+REMARKS+"%'": "";
		tmpCond += vvip.length() > 0? "AND B.ADDVIPTYPE in ('CM','GOV','MIN','PM') AND B.SUBJECTCODE NOT IN ('APT','CMP','PEN','PMT','TFR')":"";

		strSQL = 	" SELECT A.REFID,A.MARKINGSEQUENCE,A.MARKINGFROM,A.MARKINGTO,TO_CHAR(A.MARKINGDATE,'DD/MM/YYYY') MARKINGDATE,A.MARKINGREMARK," +
					" A.TARGETDAYS,TO_CHAR(A.TARGETDATE,'DD/MM/YYYY') TARGETDATE,A.ACTION,A.ACTIONDATE,B.SUBJECT,B.SUBJECTCODE," +
					" B.REFERENCENAME,B.VIPSTATUS,TO_CHAR(B.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,B.STATECODE,B.REFNO," +
					" TO_DATE(TO_CHAR(A.TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') TDATE, TRUNC((SYSDATE - TARGETDATE)) DAYS" +
					" FROM TRNMARKING A,TRNREFERENCE B" +
					" WHERE TO_DATE(TO_CHAR(A.TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+FREMDATE+"','DD/MM/YYYY') AND TO_DATE('"+TREMDATE+"','DD/MM/YYYY')" +
					//" AND TO_DATE(TO_CHAR(B.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+FREMDATE+"','DD/MM/YYYY') AND TO_DATE('"+TREMDATE+"','DD/MM/YYYY')" +
					" AND A.REFID=B.REFID" +
//					" AND B.TENUREID='"+TENUREID+"'" +
					" AND B.TENUREID='"+TENUREID+"'" +
					" AND A.MARKINGSEQUENCE=1" +
					" AND B.FILENO IS NULL" +
					" AND A.MARKINGTO IS NOT NULL" +
					" "+tmpCond+"" +
					" ORDER BY TDATE";
		log.debug(strSQL);
		DBConnection con = new DBConnection();
		try {
				con.openConnection();
				ResultSet rs = con.select(strSQL);
				while(rs.next()){
					TrnReminder rb = new TrnReminder();
					rb.setREFID(StringFormat.nullString(rs.getString("REFID")));
					//rb.setREMINDERSEQUENCE(StringFormat.nullString(rs.getString("REMINDERSEQUENCE")));  
					rb.setREMINDERFROM(StringFormat.nullString(rs.getString("MARKINGFROM")));
					rb.setREMINDERTO(StringFormat.nullString(rs.getString("MARKINGTO")));
					//rb.setREMINDERDATE(StringFormat.nullString(rs.getString("REMINDERDATE")));
					//rb.setREMINDERREMARK(StringFormat.nullString(rs.getString("REMINDERREMARK")));
					//rb.setREMINDERACTION(StringFormat.nullString(rs.getString("REMINDERACTION")));
					//rb.setREMINDERACTIONDATE(StringFormat.nullString(rs.getString("REMINDERACTIONDATE")));
					//rb.setLOGINID (StringFormat.nullString(rs.getString("LOGINID")));
					//rb.setCHANGEDATE(StringFormat.nullString(rs.getString("CHANGEDATE")));
					//rb.setREMINDERTYPE(StringFormat.nullString(rs.getString("REMINDERTYPE")));
					//rb.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));		
					//rb.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));		
					rb.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));		
					rb.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));		
					rb.setTARGETDATE(StringFormat.nullString(rs.getString("TARGETDATE")));		
					rb.setTARGETDAYS(StringFormat.nullString(rs.getString("TARGETDAYS")));
					rb.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
					rb.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
					rb.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
					rb.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
					rb.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
					rb.setTARDAYS(StringFormat.nullString(rs.getString("DAYS")));
					arr.add(rb);
				}
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return arr;
	}*/
	//soumen func
	public ArrayList<TrnReminder> getReminderReportData(String ROLEID,String TENUREID,String FREMDATE,String TREMDATE,String CLASS,String DEPT,String STATE,String REQUESTOF, String ISCONF, String SUBJECT, String REMARKS, String vvip) {
		String strSQL = "";
		ArrayList<TrnReminder> arr = new ArrayList<TrnReminder>();
		
		/*String tmpCond = "";
		tmpCond += ISCONF.equalsIgnoreCase("0")? " AND B.ISCONF = '0'": " AND B.ISCONF IN ('0','1')";
		tmpCond += CLASS.length() > 0? " AND B.REFCLASS = '"+CLASS+"'": "";
		tmpCond += DEPT.length() > 0? " AND A.MARKINGTO = '"+DEPT+"'": "";
		tmpCond += STATE.length() > 0? " AND B.STATECODE = '"+STATE+"'": "";
		tmpCond += REQUESTOF.length() > 0? " AND REFERENCENAME LIKE '%"+REQUESTOF+"%'": "";
		tmpCond += SUBJECT.length() > 0? " AND B.SUBJECT LIKE '%"+SUBJECT+"%'": "";
		tmpCond += REMARKS.length() > 0? " AND B.REMARKS LIKE '%"+REMARKS+"%'": "";
		tmpCond += vvip.length() > 0? "AND B.ADDVIPTYPE in ('CM','GOV','MIN','PM') AND B.SUBJECTCODE NOT IN ('APT','CMP','PEN','PMT','TFR')":"";

		strSQL = 	" SELECT A.REFID,A.MARKINGSEQUENCE,A.MARKINGFROM,A.MARKINGTO,TO_CHAR(A.MARKINGDATE,'DD/MM/YYYY') MARKINGDATE,A.MARKINGREMARK," +
					" A.TARGETDAYS,TO_CHAR(A.TARGETDATE,'DD/MM/YYYY') TARGETDATE,A.ACTION,A.ACTIONDATE,B.SUBJECT,B.SUBJECTCODE," +
					" B.REFERENCENAME,B.VIPSTATUS,TO_CHAR(B.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,B.STATECODE,B.REFNO," +
					" TO_DATE(TO_CHAR(A.TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') TDATE, TRUNC((SYSDATE - TARGETDATE)) DAYS" +
					" FROM TRNMARKING A,TRNREFERENCE B" +
					" WHERE TO_DATE(TO_CHAR(A.TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+FREMDATE+"','DD/MM/YYYY') AND TO_DATE('"+TREMDATE+"','DD/MM/YYYY')" +
					//" AND TO_DATE(TO_CHAR(B.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+FREMDATE+"','DD/MM/YYYY') AND TO_DATE('"+TREMDATE+"','DD/MM/YYYY')" +
					" AND A.REFID=B.REFID" +
//					" AND B.TENUREID='"+TENUREID+"'" +
					" AND B.TENUREID='"+TENUREID+"'" +
					" AND A.MARKINGSEQUENCE=1" +
					" AND B.FILENO IS NULL" +
					" AND A.MARKINGTO IS NOT NULL" +
					" "+tmpCond+"" +
					" ORDER BY TDATE";
		log.debug(strSQL);*/
		
		strSQL = 	" SELECT A.REFID,A.MARKINGSEQUENCE,A.MARKINGFROM,(SELECT C.ROLENAME FROM MSTROLE C WHERE A.MARKINGTO = C.ROLEID ) MARKINGTO,TO_CHAR(A.MARKINGDATE,'DD/MM/YYYY') MARKINGDATE,A.MARKINGREMARK," +
				" A.TARGETDAYS,TO_CHAR(A.TARGETDATE,'DD/MM/YYYY') TARGETDATE,A.ACTION,A.ACTIONDATE,B.SUBJECT,B.SUBJECTCODE," +
				" B.REFERENCENAME,B.VIPSTATUS,TO_CHAR(B.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,B.STATECODE,B.REFNO," +
				" TO_DATE(TO_CHAR(A.TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') TDATE, TRUNC((SYSDATE - TARGETDATE)) DAYS, B.EOFFICEREFNO EOFFICENO," +
				" (select substr(max(to_char(M.movedate,'yyyymmddhh24miss') ||'/' ||M.toid ||'-'|| M.toname),instr(max(to_char(M.movedate,'yyyymmddhh24miss') ||'/' ||M.toid ||'-'|| M.toname),'-')+1) || ' - ' ||max(to_char(M.movedate,'dd/mm/yyyy') ) " +
				" from  TRNREFMOVEMENT M WHERE EOFFICERECIEPTNO =B.EOFFICERECEIPTNO) REFCURRENTLYWITH " +
				" FROM TRNMARKING A,TRNREFERENCE B" +
				" WHERE TO_DATE(TO_CHAR(A.TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
				//" AND TO_DATE(TO_CHAR(B.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+FREMDATE+"','DD/MM/YYYY') AND TO_DATE('"+TREMDATE+"','DD/MM/YYYY')" +
				" AND A.REFID=B.REFID" +
//				" AND B.TENUREID='"+TENUREID+"'" +
				" AND B.TENUREID=?" +
				" AND A.MARKINGSEQUENCE=1" +
				" AND B.FILESTATUS1 IS NULL" +
				" AND A.MARKINGTO IS NOT NULL";
				//" "+tmpCond+"" +
				//" ORDER BY TDATE";
	
		ArrayList<String> params = new ArrayList<String>();
		params.add("");
		params.add(FREMDATE);
		params.add(TREMDATE);
		params.add(TENUREID);
		
		if(ISCONF.equalsIgnoreCase("0"))
		{
			strSQL=strSQL+" AND B.ISCONF = '0'";
		}else
		{
			strSQL=strSQL+ " AND B.ISCONF IN ('0','1')";
		}
		
		
		if(CLASS.length() > 0)
		{
			strSQL=strSQL+" AND B.REFCLASS = ?";
			params.add(CLASS);
			
		}else
		{
			strSQL=strSQL+ "";
		}
		
		if(DEPT.length() > 0)
		{
			strSQL=strSQL+" AND A.MARKINGTO = ?";
			params.add(DEPT);
			
		}else
		{
			strSQL=strSQL+ "";
		}
		
		//tmpCond += STATE.length() > 0? " AND B.STATECODE = '"+STATE+"'": "";
		if(STATE.length() > 0)
		{
			strSQL=strSQL+" AND B.STATECODE = ?";
			params.add(STATE);
			
		}else
		{
			strSQL=strSQL+ "";
		}
		//tmpCond += REQUESTOF.length() > 0? " AND REFERENCENAME LIKE '%"+REQUESTOF+"%'": "";
		if(REQUESTOF.length() > 0)
		{
			strSQL=strSQL+" AND REFERENCENAME LIKE ?";
			params.add("%"+REQUESTOF+"%");
			
		}else
		{
			strSQL=strSQL+ "";
		}
		
		//tmpCond += SUBJECT.length() > 0? " AND B.SUBJECT LIKE '%"+SUBJECT+"%'": "";
		if(SUBJECT.length() > 0)
		{
			strSQL=strSQL+" AND B.SUBJECT LIKE ?";
			params.add("%"+SUBJECT+"%");
			
		}else
		{
			strSQL=strSQL+ "";
		}
		//tmpCond += REMARKS.length() > 0? " AND B.REMARKS LIKE '%"+REMARKS+"%'": "";
		if(REMARKS.length() > 0)
		{
			strSQL=strSQL+" AND B.REMARKS LIKE ?";
			params.add("%"+REMARKS+"%");
			
		}else
		{
			strSQL=strSQL+ "";
		}
		//tmpCond += vvip.length() > 0? "AND B.ADDVIPTYPE in ('CM','GOV','MIN','PM') AND B.SUBJECTCODE NOT IN ('APT','CMP','PEN','PMT','TFR')":"";

		if(vvip.length() > 0)
		{
			strSQL=strSQL+" AND B.ADDVIPTYPE in ('CM','GOV','MIN','PM') AND B.SUBJECTCODE NOT IN ('APT','CMP','PEN','PMT','TFR') ";
		}else
		{
			strSQL=strSQL+ "";
		}
		
		strSQL=strSQL+" ORDER BY TDATE";
		
		DBConnection con = new DBConnection();
		try {
				con.openConnection();
				log.debug("strsql: "+strSQL);
				PreparedStatement ps = con.setPrepStatement(strSQL);
				
				for(int k=1;k<params.size();k++){
		    		ps.setString(k, params.get(k));
		    		//System.out.println("k :"+k+" params "+params.get(k));
		    		
		    	}
				ResultSet rs =ps.executeQuery();

				//ResultSet rs = con.select(strSQL);
				while(rs.next()){
					TrnReminder rb = new TrnReminder();
					rb.setREFID(StringFormat.nullString(rs.getString("REFID")));
					//rb.setREMINDERSEQUENCE(StringFormat.nullString(rs.getString("REMINDERSEQUENCE")));  
					rb.setREMINDERFROM(StringFormat.nullString(rs.getString("MARKINGFROM")));
					rb.setREMINDERTO(StringFormat.nullString(rs.getString("MARKINGTO")));
					rb.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
					rb.setEOFFICEREFNO(StringFormat.nullString(rs.getString("EOFFICENO")));
					rb.setREFCURRENTLYWITH(StringFormat.nullString(rs.getString("REFCURRENTLYWITH")));
					//rb.setREMINDERDATE(StringFormat.nullString(rs.getString("REMINDERDATE")));
					//rb.setREMINDERREMARK(StringFormat.nullString(rs.getString("REMINDERREMARK")));
					//rb.setREMINDERACTION(StringFormat.nullString(rs.getString("REMINDERACTION")));
					//rb.setREMINDERACTIONDATE(StringFormat.nullString(rs.getString("REMINDERACTIONDATE")));
					//rb.setLOGINID (StringFormat.nullString(rs.getString("LOGINID")));
					//rb.setCHANGEDATE(StringFormat.nullString(rs.getString("CHANGEDATE")));
					//rb.setREMINDERTYPE(StringFormat.nullString(rs.getString("REMINDERTYPE")));
					//rb.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));		
					//rb.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));		
					rb.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));		
					rb.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));		
					rb.setTARGETDATE(StringFormat.nullString(rs.getString("TARGETDATE")));		
					rb.setTARGETDAYS(StringFormat.nullString(rs.getString("TARGETDAYS")));
					rb.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
					rb.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
					rb.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
					rb.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
					rb.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
					rb.setTARDAYS(StringFormat.nullString(rs.getString("DAYS")));
					arr.add(rb);
				}
				rs.close();
				//System.out.println("getReminderReportData executed");
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return arr;
	}
	
	
	
	
	public ArrayList<TrnReminder> getReminderReportDataEMAIL(String markto) {
		String strSQL = "";
		String tmpCond = "";
		ArrayList<TrnReminder> arr = new ArrayList<TrnReminder>();
	/*	tmpCond += ISCONF.equalsIgnoreCase("0")? " AND B.ISCONF = '0'": " AND B.ISCONF IN ('0','1')";
		tmpCond += CLASS.length() > 0? " AND B.REFCLASS = '"+CLASS+"'": "";
		tmpCond += DEPT.length() > 0? " AND A.MARKINGTO = '"+DEPT+"'": "";
		tmpCond += STATE.length() > 0? " AND B.STATECODE = '"+STATE+"'": "";
		tmpCond += REQUESTOF.length() > 0? " AND REFERENCENAME LIKE '%"+REQUESTOF+"%'": "";
		tmpCond += SUBJECT.length() > 0? " AND B.SUBJECT LIKE '%"+SUBJECT+"%'": "";
		tmpCond += REMARKS.length() > 0? " AND B.REMARKS LIKE '%"+REMARKS+"%'": "";*/

		strSQL = 	" SELECT A.REFID,A.MARKINGSEQUENCE,A.MARKINGFROM,A.MARKINGTO,TO_CHAR(A.MARKINGDATE,'DD/MM/YYYY') MARKINGDATE,A.MARKINGREMARK," +
					" A.TARGETDAYS,TO_CHAR(A.TARGETDATE,'DD/MM/YYYY') TARGETDATE,A.ACTION,A.ACTIONDATE,B.SUBJECT,B.SUBJECTCODE," +
					" B.REFERENCENAME,B.VIPSTATUS,TO_CHAR(B.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,B.STATECODE,B.REFNO," +
					" TO_DATE(TO_CHAR(A.TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') TDATE, TRUNC((SYSDATE - TARGETDATE)) DAYS" +
					" FROM TRNMARKING A,TRNREFERENCE B" +
					" WHERE" +
					" TO_CHAR(A.TARGETDATE+1,'DD/MM/YYYY')= TO_CHAR(sysdate,'DD/MM/YYYY') "+
					//" TO_DATE(TO_CHAR(A.TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('05/12/2014','DD/MM/YYYY') AND TO_DATE('06/12/2014','DD/MM/YYYY')" +
					//" AND TO_DATE(TO_CHAR(B.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+FREMDATE+"','DD/MM/YYYY') AND TO_DATE('"+TREMDATE+"','DD/MM/YYYY')" +
					" AND B.REFCLASS in ('A','M') " +
					" AND A.REFID=B.REFID" +
//					" AND B.TENUREID='"+TENUREID+"'" +
					" AND B.TENUREID='12'" +
					" AND A.MARKINGSEQUENCE=1" +
					" AND B.FILENO IS NULL" +
					" AND A.MARKINGTO='"+markto+"'" +
					" "+tmpCond+"" +
					" ORDER BY TDATE";
		log.debug(strSQL);
		DBConnection con = new DBConnection();
		try {
				con.openConnection();
				ResultSet rs = con.select(strSQL);
				while(rs.next()){
					TrnReminder rb = new TrnReminder();
					rb.setREFID(StringFormat.nullString(rs.getString("REFID")));
					//rb.setREMINDERSEQUENCE(StringFormat.nullString(rs.getString("REMINDERSEQUENCE")));  
					rb.setREMINDERFROM(StringFormat.nullString(rs.getString("MARKINGFROM")));
					rb.setREMINDERTO(StringFormat.nullString(rs.getString("MARKINGTO")));
					rb.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
					//rb.setREMINDERDATE(StringFormat.nullString(rs.getString("REMINDERDATE")));
					//rb.setREMINDERREMARK(StringFormat.nullString(rs.getString("REMINDERREMARK")));
					//rb.setREMINDERACTION(StringFormat.nullString(rs.getString("REMINDERACTION")));
					//rb.setREMINDERACTIONDATE(StringFormat.nullString(rs.getString("REMINDERACTIONDATE")));
					//rb.setLOGINID (StringFormat.nullString(rs.getString("LOGINID")));
					//rb.setCHANGEDATE(StringFormat.nullString(rs.getString("CHANGEDATE")));
					//rb.setREMINDERTYPE(StringFormat.nullString(rs.getString("REMINDERTYPE")));
					//rb.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));		
					//rb.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));		
					rb.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));		
					rb.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));		
					rb.setTARGETDATE(StringFormat.nullString(rs.getString("TARGETDATE")));		
					rb.setTARGETDAYS(StringFormat.nullString(rs.getString("TARGETDAYS")));
					rb.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
					rb.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
					rb.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
					rb.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
					rb.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
					rb.setTARDAYS(StringFormat.nullString(rs.getString("DAYS")));
					arr.add(rb);
				}
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return arr;
	}

	/*public String dataPatched(String ROLEID, String TENUREID, String LOGINID, String FREMDATE, String TREMDATE, String CLASS, String DEPT, String STATE, String REMARKS,String DREMARKS,String SUBJECT, String SIGNEDBY, String REQOF, String isConf, String reminderType) {
		String REMINDERTO = "";		String REMINDERFROM = "";
		String TARGETDAYS = "";		String TARGETDATE = "";		String result = "Somthing wrong happned";	String tmpCond = "";
		DBConnection con = new DBConnection();			ArrayList<String> arr = new ArrayList<String>();

		tmpCond += isConf.equalsIgnoreCase("0")? " AND B.ISCONF = '0'": " AND B.ISCONF IN ('0','1')";
		tmpCond += CLASS.length() > 0? " AND B.REFCLASS = '"+CLASS+"'": "";
		tmpCond += DEPT.length() > 0? " AND A.MARKINGTO = '"+DEPT+"'": "";
		tmpCond += STATE.length() > 0? " AND B.STATECODE = '"+STATE+"'": "";
		tmpCond += REQOF.length() > 0? " AND REFERENCENAME LIKE '%"+REQOF+"%'": "";
		tmpCond += SUBJECT.length() > 0? " AND B.SUBJECT LIKE '%"+SUBJECT+"%'": "";
		tmpCond += DREMARKS.length() > 0? " AND B.REMARKS LIKE '%"+DREMARKS+"%'": "";

		String strSQL = " SELECT A.REFID FROM TRNMARKING A,TRNREFERENCE B" +
						" WHERE TO_DATE(TO_CHAR(A.TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+FREMDATE+"','DD/MM/YYYY') AND TO_DATE('"+TREMDATE+"','DD/MM/YYYY')" +
						" AND A.REFID=B.REFID AND B.TENUREID='"+TENUREID+"' AND A.MARKINGSEQUENCE=1 "+tmpCond+"" +
						" ORDER BY A.REFID";
		log.debug(strSQL);
		try {
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while(rs.next()){
				arr.add(StringFormat.nullString(rs.getString("REFID")));
			}
			int updcount = 0;
			for(int i=0;i<arr.size();i++){			
				//System.out.println(arr.get(i));
				String	strSQL2 = " SELECT MARKINGTO, MARKINGFROM, TARGETDAYS, TO_CHAR(TARGETDATE,'DD/MM/YYYY') TARGETDATE FROM TRNMARKING WHERE REFID = '"+arr.get(i)+"' ";
				log.debug(strSQL2);
				ResultSet rs2 = con.select(strSQL2);
				if(rs2.next()) {
					REMINDERTO = handleNull(rs2.getString(1));
					REMINDERFROM = handleNull(rs2.getString(2));
					TARGETDAYS = handleNull(rs2.getString(3));
					TARGETDATE = handleNull(rs2.getString(4));
					String strSQL3 = " INSERT INTO TRNREMINDER (REFID, REMINDERSEQUENCE, REMINDERFROM, REMINDERTO, REMINDERDATE, REMINDERREMARK, REMINDERACTION, REMINDERACTIONDATE," +
									 " LOGINID, CHANGEDATE, TARGETDAYS, TARGETDATE, REMINDERTYPE, SIGNEDBY, SIGNEDON)" +
									 " VALUES ('"+arr.get(i)+"',(SELECT (NVL(MAX(REMINDERSEQUENCE),0)+1) FROM TRNREMINDER WHERE REFID ='"+arr.get(i)+"')," +
									 " '"+REMINDERFROM+"', '"+REMINDERTO+"',SYSDATE, '"+REMARKS+"','FOW',SYSDATE, '"+LOGINID+"',SYSDATE, '"+TARGETDAYS+"'," +
									 " TO_DATE('"+TARGETDATE+"','DD/MM/YYYY'), '"+reminderType+"', '"+SIGNEDBY+"', SYSDATE )";
					log.debug(strSQL3);
					updcount = con.update(strSQL3);
					//System.out.println("updcount = :"+ updcount);
				}
				if(updcount>0){ result = "Data Patched Successfuly"; }
				//System.out.println(result);
			}
			con.commit();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			result = "Operation failed";
		} finally {
			con.closeConnection();
		}
		return result;
	}*/
	//soumen func
	public String dataPatched(String ROLEID, String TENUREID, String LOGINID, String FREMDATE, String TREMDATE, String CLASS, String DEPT, String STATE, String REMARKS,String DREMARKS,String SUBJECT, String SIGNEDBY, String REQOF, String isConf, String reminderType) {
		String REMINDERTO = "";		String REMINDERFROM = "";
		String TARGETDAYS = "";		String TARGETDATE = "";		String result = "Somthing wrong happned";	String tmpCond = "";
		DBConnection con = new DBConnection();			
		ArrayList<String> arr = new ArrayList<String>();

		ArrayList<String> params = new ArrayList<String>();
		
		params.add("");
		params.add(FREMDATE);
		params.add(TREMDATE);
		params.add(TENUREID);
		
		/*tmpCond += isConf.equalsIgnoreCase("0")? " AND B.ISCONF = '0'": " AND B.ISCONF IN ('0','1')";
		tmpCond += CLASS.length() > 0? " AND B.REFCLASS = '"+CLASS+"'": "";
		tmpCond += DEPT.length() > 0? " AND A.MARKINGTO = '"+DEPT+"'": "";
		tmpCond += STATE.length() > 0? " AND B.STATECODE = '"+STATE+"'": "";
		tmpCond += REQOF.length() > 0? " AND REFERENCENAME LIKE '%"+REQOF+"%'": "";
		tmpCond += SUBJECT.length() > 0? " AND B.SUBJECT LIKE '%"+SUBJECT+"%'": "";
		tmpCond += DREMARKS.length() > 0? " AND B.REMARKS LIKE '%"+DREMARKS+"%'": "";*/
		
		
		if(CLASS.length() > 0)
		{
			tmpCond +=" AND B.REFCLASS =? ";
			params.add(CLASS);
		}

		if(DEPT.length() > 0)
		{
			tmpCond +=" AND A.MARKINGTO =? ";
			params.add(DEPT);
		}
		
		if(STATE.length() > 0)
		{
			tmpCond +=" AND B.STATECODE =? ";
			params.add(STATE);
		}
		
		if(REQOF.length() > 0)
		{
			tmpCond +=" AND REFERENCENAME LIKE ? ";
			params.add("%"+REQOF+"%");
		}
		if(SUBJECT.length() > 0)
		{
			tmpCond +=" AND B.SUBJECT LIKE ? ";
			params.add("%"+SUBJECT+"%");
		}
		if(DREMARKS.length() > 0)
		{
			tmpCond +=" AND B.REMARKS LIKE ? ";
			params.add("%"+DREMARKS+"%");
		}
		
		
		/*String strSQL = " SELECT A.REFID FROM TRNMARKING A,TRNREFERENCE B" +
						" WHERE TO_DATE(TO_CHAR(A.TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+FREMDATE+"','DD/MM/YYYY') AND TO_DATE('"+TREMDATE+"','DD/MM/YYYY')" +
						" AND A.REFID=B.REFID AND B.TENUREID='"+TENUREID+"' AND A.MARKINGSEQUENCE=1 "+tmpCond+"" +
						" ORDER BY A.REFID";
		log.debug(strSQL);*/
		
		String strSQL = " SELECT A.REFID FROM TRNMARKING A,TRNREFERENCE B" +
				" WHERE TO_DATE(TO_CHAR(A.TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
				" AND A.REFID=B.REFID AND B.TENUREID=?  AND A.MARKINGSEQUENCE=1 "+tmpCond+"" +
				" ORDER BY A.REFID";

		
		
		try {
			 con.openConnection();
			 //ResultSet rs = con.select(strSQL);
			
			 PreparedStatement ps = con.setPrepStatement(strSQL);
			 for(int k=1;k<params.size();k++){
	    		ps.setString(k, params.get(k));
	    		//System.out.println("k :"+k+" params "+params.get(k))	
	    	 }
			 ResultSet rs = ps.executeQuery();
			
			
			while(rs.next()){
				arr.add(StringFormat.nullString(rs.getString("REFID")));
			}
			 
			//System.out.println("dataPatched::1:: executed::"+arr.size());
			
			int updcount = 0;
			String	strSQL2 = " SELECT MARKINGTO, MARKINGFROM, TARGETDAYS, TO_CHAR(TARGETDATE,'DD/MM/YYYY') TARGETDATE FROM TRNMARKING WHERE REFID =? ";
			 PreparedStatement ps1 = con.setPrepStatement(strSQL2);
			 String strSQL3 = " INSERT INTO TRNREMINDER (REFID, REMINDERSEQUENCE, REMINDERFROM, REMINDERTO, REMINDERDATE, REMINDERREMARK, REMINDERACTION, REMINDERACTIONDATE," +
					 " LOGINID, CHANGEDATE, TARGETDAYS, TARGETDATE, REMINDERTYPE, SIGNEDBY, SIGNEDON)" +
					 " VALUES (?,(SELECT (NVL(MAX(REMINDERSEQUENCE),0)+1) FROM TRNREMINDER WHERE REFID =? )," +
					 " ?, ?,SYSDATE, ?,'FOW',SYSDATE, ?,SYSDATE, ?," +
					 " TO_DATE(?,'DD/MM/YYYY'), ?, ?, SYSDATE )";
			 PreparedStatement ps2 = con.setPrepStatement(strSQL3);
			for(int i=0;i<arr.size();i++){			
				//System.out.println(arr.get(i));
				/*String	strSQL2 = " SELECT MARKINGTO, MARKINGFROM, TARGETDAYS, TO_CHAR(TARGETDATE,'DD/MM/YYYY') TARGETDATE FROM TRNMARKING WHERE REFID = '"+arr.get(i)+"' ";
				log.debug(strSQL2);
				ResultSet rs2 = con.select(strSQL2);*/
				
				
				ps1.setString(1, arr.get(i));
				ResultSet rs2 =ps1.executeQuery();
				//System.out.println("dataPatched::2:: executed");
				
				if(rs2.next()) {
					REMINDERTO = handleNull(rs2.getString(1));
					REMINDERFROM = handleNull(rs2.getString(2));
					TARGETDAYS = handleNull(rs2.getString(3));
					TARGETDATE = handleNull(rs2.getString(4));
					
					/*String strSQL3 = " INSERT INTO TRNREMINDER (REFID, REMINDERSEQUENCE, REMINDERFROM, REMINDERTO, REMINDERDATE, REMINDERREMARK, REMINDERACTION, REMINDERACTIONDATE," +
									 " LOGINID, CHANGEDATE, TARGETDAYS, TARGETDATE, REMINDERTYPE, SIGNEDBY, SIGNEDON)" +
									 " VALUES ('"+arr.get(i)+"',(SELECT (NVL(MAX(REMINDERSEQUENCE),0)+1) FROM TRNREMINDER WHERE REFID ='"+arr.get(i)+"')," +
									 " '"+REMINDERFROM+"', '"+REMINDERTO+"',SYSDATE, '"+REMARKS+"','FOW',SYSDATE, '"+LOGINID+"',SYSDATE, '"+TARGETDAYS+"'," +
									 " TO_DATE('"+TARGETDATE+"','DD/MM/YYYY'), '"+reminderType+"', '"+SIGNEDBY+"', SYSDATE )";
					log.debug(strSQL3);
					updcount = con.update(strSQL3);*/
					
					//System.out.println("updcount = :"+ updcount);
					
					
				 
					
					
					ps2 = con.setPrepStatement(strSQL3);
					ps2.setString(1, arr.get(i));
					ps2.setString(2, arr.get(i));
					ps2.setString(3, REMINDERFROM);
					ps2.setString(4, REMINDERTO);
					ps2.setString(5, REMARKS);
					ps2.setString(6, LOGINID);
					ps2.setString(7, TARGETDAYS);
					ps2.setString(8, TARGETDATE);
					ps2.setString(9, reminderType);
					ps2.setString(10, SIGNEDBY);
					
					
					
					updcount=ps2.executeUpdate();
					
					//System.out.println("dataPatched::Inserted Rows:"+updcount) ;
					
				}
				if(updcount>0){ result = "Data Patched Successfuly"; }
				//System.out.println(result);
			}
			con.commit();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			result = "Operation failed";
		} finally {
			con.closeConnection();
		}
		return result;
	}

	/*public String patchSingleReminder(String INDATE_DRPDWN, String REMARKSINGLE, String SIGNEDBYSINGLE, String LOGINID, String reminderType) {
		String REMINDERTO = "";		String REMINDERFROM = "";	String refID = "";
		String TARGETDAYS = "";		String TARGETDATE = "";		String result = "Somthing wrong happned";
		DBConnection con = new DBConnection();			
		String refNo = "";
		String inDate = "";
		
		String[] arrRefNo = INDATE_DRPDWN.split("   ");
		refNo = arrRefNo[0];
		inDate = arrRefNo[1];
		
		String refIdSQL = " SELECT REFID FROM TRNREFERENCE WHERE REFNO = '"+refNo+"' AND TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') = '"+inDate+"' ";
		log.debug(refIdSQL);
		try {
			con.openConnection();
			ResultSet rs = con.select(refIdSQL);
			if(rs.next()){
				refID = StringFormat.nullString(rs.getString("REFID"));
			}
			int updcount = 0;
			String	strSQL2 = " SELECT MARKINGTO, MARKINGFROM, TARGETDAYS, TO_CHAR(TARGETDATE,'DD/MM/YYYY') TARGETDATE FROM TRNMARKING WHERE REFID = '"+refID+"' ";
			log.debug(strSQL2);
			ResultSet rs2 = con.select(strSQL2);
			if(rs2.next()) {
					REMINDERTO = handleNull(rs2.getString(1));
					REMINDERFROM = handleNull(rs2.getString(2));
					TARGETDAYS = handleNull(rs2.getString(3));
					TARGETDATE = handleNull(rs2.getString(4));
					String strSQL3 = " INSERT INTO TRNREMINDER (REFID, REMINDERSEQUENCE, REMINDERFROM, REMINDERTO, REMINDERDATE, REMINDERREMARK, REMINDERACTION, REMINDERACTIONDATE," +
									 " LOGINID, CHANGEDATE, TARGETDAYS, TARGETDATE, REMINDERTYPE, SIGNEDBY, SIGNEDON)" +
									 " VALUES ('"+refID+"',(SELECT (NVL(MAX(REMINDERSEQUENCE),0)+1) FROM TRNREMINDER WHERE REFID ='"+refID+"')," +
									 " '"+REMINDERFROM+"', '"+REMINDERTO+"',SYSDATE, '"+REMARKSINGLE+"','FOW',SYSDATE, '"+LOGINID+"',SYSDATE, '"+TARGETDAYS+"'," +
									 " TO_DATE('"+TARGETDATE+"','DD/MM/YYYY'), '"+reminderType+"', '"+SIGNEDBYSINGLE+"', SYSDATE )";
					log.debug(strSQL3);
					updcount = con.update(strSQL3);
					//System.out.println("updcount = :"+ updcount);
			}
			if(updcount>0){ result = "Data Patched Successfuly"; }
			//System.out.println(result);
			con.commit();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			result = "Operation failed";
		} finally {
			con.closeConnection();
		}
		return result;
	}*/
	//soumen func
	public String patchSingleReminder(String INDATE_DRPDWN, String REMARKSINGLE, String SIGNEDBYSINGLE, String LOGINID, String reminderType) {
		String REMINDERTO = "";		String REMINDERFROM = "";	String refID = "";
		String TARGETDAYS = "";		String TARGETDATE = "";		String result = "Somthing wrong happned";
		DBConnection con = new DBConnection();			
		String refNo = "";
		String inDate = "";
		
		String[] arrRefNo = INDATE_DRPDWN.split("   ");
		refNo = arrRefNo[0];
		inDate = arrRefNo[1];
		
		/*String refIdSQL = " SELECT REFID FROM TRNREFERENCE WHERE REFNO = '"+refNo+"' AND TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') = '"+inDate+"' ";
		log.debug(refIdSQL);*/
	
		String refIdSQL = " SELECT REFID FROM TRNREFERENCE WHERE REFNO = ? AND TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') = ? ";
		
		PreparedStatement ps = null; 
		
		
		try {
			 con.openConnection();
			//ResultSet rs = con.select(refIdSQL);
			 ps=con.setPrepStatement(refIdSQL);
			 ps.setString(1,refNo);
			 ps.setString(2,inDate);
			 ResultSet rs =ps.executeQuery();
			 
			if(rs.next()){
				refID = StringFormat.nullString(rs.getString("REFID"));
			}
			
			//System.out.println("patchSingleReminder::1:: executed");
			int updcount = 0;
			/*String	strSQL2 = " SELECT MARKINGTO, MARKINGFROM, TARGETDAYS, TO_CHAR(TARGETDATE,'DD/MM/YYYY') TARGETDATE FROM TRNMARKING WHERE REFID = '"+refID+"' ";
			log.debug(strSQL2);
			ResultSet rs2 = con.select(strSQL2);*/
			
			String	strSQL2 = " SELECT MARKINGTO, MARKINGFROM, TARGETDAYS, TO_CHAR(TARGETDATE,'DD/MM/YYYY') TARGETDATE FROM TRNMARKING WHERE REFID = ? ";

			 ps=con.setPrepStatement(strSQL2);
			 ps.setString(1,refID);
			 ResultSet rs2 =ps.executeQuery();
			 //System.out.println("patchSingleReminder::2:: executed");
			 
			if(rs2.next()) {
					REMINDERTO = handleNull(rs2.getString(1));
					REMINDERFROM = handleNull(rs2.getString(2));
					TARGETDAYS = handleNull(rs2.getString(3));
					TARGETDATE = handleNull(rs2.getString(4));
					/*String strSQL3 = " INSERT INTO TRNREMINDER (REFID, REMINDERSEQUENCE, REMINDERFROM, REMINDERTO, REMINDERDATE, REMINDERREMARK, REMINDERACTION, REMINDERACTIONDATE," +
									 " LOGINID, CHANGEDATE, TARGETDAYS, TARGETDATE, REMINDERTYPE, SIGNEDBY, SIGNEDON)" +
									 " VALUES ('"+refID+"',(SELECT (NVL(MAX(REMINDERSEQUENCE),0)+1) FROM TRNREMINDER WHERE REFID ='"+refID+"')," +
									 " '"+REMINDERFROM+"', '"+REMINDERTO+"',SYSDATE, '"+REMARKSINGLE+"','FOW',SYSDATE, '"+LOGINID+"',SYSDATE, '"+TARGETDAYS+"'," +
									 " TO_DATE('"+TARGETDATE+"','DD/MM/YYYY'), '"+reminderType+"', '"+SIGNEDBYSINGLE+"', SYSDATE )";
					log.debug(strSQL3);
					updcount = con.update(strSQL3);*/
					//System.out.println("updcount = :"+ updcount);
					
					String strSQL3 = " INSERT INTO TRNREMINDER (REFID, REMINDERSEQUENCE, REMINDERFROM, REMINDERTO, REMINDERDATE, REMINDERREMARK, REMINDERACTION, REMINDERACTIONDATE," +
							 " LOGINID, CHANGEDATE, TARGETDAYS, TARGETDATE, REMINDERTYPE, SIGNEDBY, SIGNEDON)" +
							 " VALUES (?, (SELECT (NVL(MAX(REMINDERSEQUENCE),0)+1) FROM TRNREMINDER WHERE REFID =? )," +
							 " ?, ?,SYSDATE, ?,'FOW',SYSDATE, ?,SYSDATE, ?," +
							 " TO_DATE(?,'DD/MM/YYYY'), ?, ?, SYSDATE )";
					 
					 ps=con.setPrepStatement(strSQL3);
					 ps.setString(1,refID);
					 ps.setString(2,refID);
					 ps.setString(3,REMINDERFROM);
					 ps.setString(4,REMINDERTO);
					 ps.setString(5,REMARKSINGLE);
					 ps.setString(6,LOGINID);
					 ps.setString(7,TARGETDAYS);
					 ps.setString(8,TARGETDATE);
					 ps.setString(9,reminderType);
					 ps.setString(10,SIGNEDBYSINGLE);
					
					 updcount =ps.executeUpdate();
					 //System.out.println("patchSingleReminder::inserted::"+updcount);
					 

			}
			if(updcount>0){ result = "Data Patched Successfuly"; }
			//System.out.println(result);
			con.commit();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
			result = "Operation failed";
		} finally {
			con.closeConnection();
		}
		return result;
	}

	/*public ArrayList<TrnReminder> getSingleReminderReportData(String REFID) {
		
		//System.out.println("suneel       ''''");
		String strSQL = "";
		ArrayList<TrnReminder> arr = new ArrayList<TrnReminder>();
		strSQL = " SELECT A.REFNO, TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE, TO_CHAR(A.REFERENCEDATE,'DD/MM/YYYY') REFERENCEDATE," +
				 " A.ACKBY, TO_CHAR(A.ACKDATE,'DD/MM/YYYY') ACKDATE, A.REFERENCENAME, A.VIPSTATUS," +
				 " (SELECT STATENAME FROM MSTSTATE WHERE STATECODE = A.STATECODE) STATECODE, A.SUBJECT, A.REMARKS, TO_CHAR(B.TARGETDATE,'DD/MM/YYYY') TARGETDATE," +
				 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=B.MARKINGTO) MARKINGTO," +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1, " +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2, " +
				 " A.FMID, A.FILENO, " +
				 " TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = A.SIGNEDBY) SIGNEDBY," +
				 " TO_CHAR(A.SIGNEDON,'DD/MM/YYYY') SIGNEDON" +
				 " FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID=B.REFID AND A.REFID = '"+REFID+"' ";
		log.debug(strSQL);
		DBConnection con = new DBConnection();
		try {
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while(rs.next()){
				TrnReminder rb = new TrnReminder();
				rb.setREFERENCEDATE(StringFormat.nullString(rs.getString("REFERENCEDATE")));  
				rb.setACKBY(StringFormat.nullString(rs.getString("ACKBY")));
				rb.setACKDATE(StringFormat.nullString(rs.getString("ACKDATE")));
				rb.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
				rb.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));		
				rb.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
				rb.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
				rb.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
				rb.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
				rb.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
				rb.setTARGETDATE(StringFormat.nullString(rs.getString("TARGETDATE")));
				rb.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
				rb.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
				rb.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
				rb.setFMID(StringFormat.nullString(rs.getString("FMID")));
				rb.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
				rb.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
				rb.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
				rb.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
				arr.add(rb);
			}
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return arr;
	}*/
	//soumen func
public ArrayList<TrnReminder> getSingleReminderReportData(String REFID) {
		
		//System.out.println("suneel       ''''");
		String strSQL = "";
		ArrayList<TrnReminder> arr = new ArrayList<TrnReminder>();
		/*strSQL = " SELECT A.REFNO, TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE, TO_CHAR(A.REFERENCEDATE,'DD/MM/YYYY') REFERENCEDATE," +
				 " A.ACKBY, TO_CHAR(A.ACKDATE,'DD/MM/YYYY') ACKDATE, A.REFERENCENAME, A.VIPSTATUS," +
				 " (SELECT STATENAME FROM MSTSTATE WHERE STATECODE = A.STATECODE) STATECODE, A.SUBJECT, A.REMARKS, TO_CHAR(B.TARGETDATE,'DD/MM/YYYY') TARGETDATE," +
				 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=B.MARKINGTO) MARKINGTO," +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1, " +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2, " +
				 " A.FMID, A.FILENO, " +
				 " TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = A.SIGNEDBY) SIGNEDBY," +
				 " TO_CHAR(A.SIGNEDON,'DD/MM/YYYY') SIGNEDON" +
				 " FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID=B.REFID AND A.REFID = '"+REFID+"' ";
		log.debug(strSQL);*/
		
		
		strSQL = " SELECT A.REFNO, TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE, TO_CHAR(A.REFERENCEDATE,'DD/MM/YYYY') REFERENCEDATE," +
				 " A.ACKBY, TO_CHAR(A.ACKDATE,'DD/MM/YYYY') ACKDATE, A.REFERENCENAME, A.VIPSTATUS," +
				 " (SELECT STATENAME FROM MSTSTATE WHERE STATECODE = A.STATECODE) STATECODE, A.SUBJECT, A.REMARKS, TO_CHAR(B.TARGETDATE,'DD/MM/YYYY') TARGETDATE," +
				 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=B.MARKINGTO) MARKINGTO," +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1, " +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2, " +
				 " A.FMID, A.FILENO, " +
				 " TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = A.SIGNEDBY) SIGNEDBY," +
				 " TO_CHAR(A.SIGNEDON,'DD/MM/YYYY') SIGNEDON" +
				 " FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID=B.REFID AND A.REFID = ?";
		
		DBConnection con = new DBConnection();
		try {
			con.openConnection();
			//ResultSet rs = con.select(strSQL);
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ps.setString(1,REFID);
			ResultSet rs =ps.executeQuery();
			while(rs.next()){
				TrnReminder rb = new TrnReminder();
				rb.setREFERENCEDATE(StringFormat.nullString(rs.getString("REFERENCEDATE")));  
				rb.setACKBY(StringFormat.nullString(rs.getString("ACKBY")));
				rb.setACKDATE(StringFormat.nullString(rs.getString("ACKDATE")));
				rb.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
				rb.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));		
				rb.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
				rb.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
				rb.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
				rb.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
				rb.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
				rb.setTARGETDATE(StringFormat.nullString(rs.getString("TARGETDATE")));
				rb.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
				rb.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
				rb.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
				rb.setFMID(StringFormat.nullString(rs.getString("FMID")));
				rb.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
				rb.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
				rb.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
				rb.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
				arr.add(rb);
			}
			rs.close();
			//System.out.println("getSingleReminderReportData executed");
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return arr;
	}

	/*public ArrayList<TrnReminder> getReminderData(String REFID) {
		
		//System.out.println("suneel       ''''");
		String strSQL = "";
		ArrayList<TrnReminder> markingArr = new ArrayList<TrnReminder>();
		strSQL = " SELECT (SELECT SNAME FROM MSTGEC WHERE CODETYPE=5 AND CODE = REMINDERTYPE) REMINDERTYPE, TO_CHAR(REMINDERDATE,'DD/MM/YYYY') REMINDERDATE, REMINDERREMARK," +
				 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = SIGNEDBY) SIGNEDBY, TO_CHAR(SIGNEDON,'DD/MM/YYYY') SIGNEDON FROM TRNREMINDER WHERE REFID = '"+REFID+"' ";
		log.debug(strSQL);
		DBConnection con = new DBConnection();
		try {
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while(rs.next()){
				TrnReminder rb = new TrnReminder();
				rb.setREMINDERTYPE(StringFormat.nullString(rs.getString("REMINDERTYPE")));  
				rb.setREMINDERDATE(StringFormat.nullString(rs.getString("REMINDERDATE")));
				rb.setREMINDERREMARK(StringFormat.nullString(rs.getString("REMINDERREMARK")));
				rb.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
				rb.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
				markingArr.add(rb);
			}
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return markingArr;
	}*/
	//soumen func
public ArrayList<TrnReminder> getReminderData(String REFID) {
	
	//System.out.println("suneel       ''''");
	String strSQL = "";
	ArrayList<TrnReminder> markingArr = new ArrayList<TrnReminder>();
	/*strSQL = " SELECT (SELECT SNAME FROM MSTGEC WHERE CODETYPE=5 AND CODE = REMINDERTYPE) REMINDERTYPE, TO_CHAR(REMINDERDATE,'DD/MM/YYYY') REMINDERDATE, REMINDERREMARK," +
			 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = SIGNEDBY) SIGNEDBY, TO_CHAR(SIGNEDON,'DD/MM/YYYY') SIGNEDON FROM TRNREMINDER WHERE REFID = '"+REFID+"' ";
	log.debug(strSQL);*/
	
	strSQL = " SELECT (SELECT SNAME FROM MSTGEC WHERE CODETYPE=5 AND CODE = REMINDERTYPE) REMINDERTYPE, TO_CHAR(REMINDERDATE,'DD/MM/YYYY') REMINDERDATE, REMINDERREMARK," +
			 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = SIGNEDBY) SIGNEDBY, TO_CHAR(SIGNEDON,'DD/MM/YYYY') SIGNEDON FROM TRNREMINDER WHERE REFID = ? ";
	
	DBConnection con = new DBConnection();
	try {
		con.openConnection();
		//ResultSet rs = con.select(strSQL);
		
		PreparedStatement ps = con.setPrepStatement(strSQL);
		ps.setString(1,REFID);
		ResultSet rs =ps.executeQuery();

		while(rs.next()){
			TrnReminder rb = new TrnReminder();
			rb.setREMINDERTYPE(StringFormat.nullString(rs.getString("REMINDERTYPE")));  
			rb.setREMINDERDATE(StringFormat.nullString(rs.getString("REMINDERDATE")));
			rb.setREMINDERREMARK(StringFormat.nullString(rs.getString("REMINDERREMARK")));
			rb.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
			rb.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
			markingArr.add(rb);
		}
		rs.close();
		//System.out.println("getReminderData executed");
	} catch (Exception e) {
		con.rollback();
		e.printStackTrace();
	} finally {
		con.closeConnection();
		
	}
	return markingArr;
}
	private String[] getUniqueList(String[] sList) {
		
				
		Set<String> set = new HashSet<String>(Arrays.asList(sList));
		String[] usList = (String[])(set.toArray(new String[set.size()]));
		
		
		return usList;
	}
	
	public String saveList(String deptcode, String [] vmarkingto,String [] vsignedby,String [] vrefsubjectcode,String [] vfilesubjectcode,String [] vimarkingto,String [] vbranch) {
		String outMessage = "";
		String strSQL = "";

		DBConnection dbcon = new DBConnection();
		try {
			dbcon.openConnection();
			
			strSQL = " DELETE FROM  MSTPREFERREDLIST WHERE ROLEID='"+deptcode+"'";
			//System.out.println("CustomReportDAO.saveReport() 1:--" + strSQL);
			dbcon.delete(strSQL);
			String [] markingto = getUniqueList(vmarkingto);		
			for (int i = 0; i < markingto.length; i++) {
			strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
					" VALUES ('"+markingto[i]+"','1','"+deptcode+"',SYSDATE)"; //param[0]
			//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
			dbcon.insert(strSQL);
			}
			String [] signedby = getUniqueList(vsignedby);
			for (int i = 0; i < signedby.length; i++) {
				strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+signedby[i]+"','2','"+deptcode+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);
			}
			String [] refsubjectcode = getUniqueList(vrefsubjectcode);
			for (int i = 0; i < refsubjectcode.length; i++) {
				strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+refsubjectcode[i]+"','3','"+deptcode+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);
			}
			String [] filesubjectcode = getUniqueList(vfilesubjectcode);
			for (int i = 0; i < filesubjectcode.length; i++) {
				strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+filesubjectcode[i]+"','4','"+deptcode+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);
			}
			
			String [] imarkingto = getUniqueList(vimarkingto);
			for (int i = 0; i < imarkingto.length; i++) {
				strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+imarkingto[i]+"','5','"+deptcode+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);
			}
			
			String [] branch = getUniqueList(vbranch);
			for (int i = 0; i < branch.length; i++) {
				strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+branch[i]+"','6','"+deptcode+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);
			}
			
			

			outMessage = "GRNSave operation successful.";

		} catch (SQLException sql) {
			dbcon.rollback();
			log.fatal(sql, sql);
			outMessage = "REDSave operation failure ERROR--> " + sql.getMessage();
			// (new CommonDAO()).generateMessage("TESTLOGIN",outMessage);
		} finally {
			dbcon.closeConnection();
		}

		return outMessage;
	}
	
	public ArrayList<MstRole> populateDeptList(String deptcode) {

		DBConnection dbCon = new DBConnection();
		ArrayList<MstRole> arr = new ArrayList<MstRole>();
		try {

			String strSQL = " SELECT A.ROLEID, A.ROLENAME "+ 
				" FROM MSTROLE A,MSTTENURE B WHERE B.ISACTIVE = 'Y' AND A.ROLEID=B.ROLEID AND" +
				" A.DEPTCODE=DECODE('"+deptcode+"','',A.DEPTCODE,'"+deptcode+"') ORDER BY 2";

			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				MstRole mr = new MstRole();
				mr.setROLEID(rs.getString(1));
				mr.setROLENAME(rs.getString(2));
				arr.add(mr);
			}

		} catch (Exception e) {
			e.printStackTrace();

			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return arr;
	}
	
public ArrayList<MstRole> populateAuthList(String deptcode,String authlevel) {

		DBConnection dbCon = new DBConnection();
		ArrayList<MstRole> arr = new ArrayList<MstRole>();
		try {

			String strSQL = " SELECT A.ROLEID, A.ROLENAME "+ 
				" FROM MSTROLE A,MSTTENURE B WHERE B.ISACTIVE = 'Y' AND A.ROLEID=B.ROLEID AND" +
				" A.DEPTCODE=DECODE('"+deptcode+"','',A.DEPTCODE,'"+deptcode+"') AND" +
				" A.AUTHLEVEL=DECODE('"+authlevel+"','',A.AUTHLEVEL,'"+authlevel+"') ORDER BY 2";

			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				MstRole mr = new MstRole();
				mr.setROLEID(rs.getString(1));
				mr.setROLENAME(rs.getString(2));
				arr.add(mr);
			}

		} catch (Exception e) {
			e.printStackTrace();

			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return arr;
	}
	
	
	public String handleNull(String str)
	{
		return str != null ? str : "";
	}
	
	
}
