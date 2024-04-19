package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.TrnFileHdr;
import in.org.cris.mrsectt.Beans.TrnReminder;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.util.StringFormat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class ReminderInbxoDAO {
	static Logger log = LogManager.getLogger(ReminderInbxoDAO.class);
	
	
public ArrayList<CommonBean> getSearchFileInboxOutBox(String fileNo,String subjectCode, String subject, String markingFrom, String markingTo, String remarks, String markingDateFrom, String markingDateTo, String tenureId){
		
		ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
		DBConnection con =  new DBConnection();
		String condSQL = "";
		String strSQL = " SELECT DISTINCT A.FMID, A.FILENO, A.SUBJECT," +
				"(SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=DECODE(A.DESTINATIONMARKING,'"+tenureId+"',A.FILESUBJECTCODEDES,A.FILESUBJECTCODEORG)) SUBJECTCODE," +
				"DECODE(B.ACTION,'RCD',(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT B.ROLEID FROM MSTTENURE B WHERE B.TENUREID = B.MARKINGFROM) ),NULL)  MARKINGFROM," +
				"DECODE(B.ACTION,'FOW',(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT B.ROLEID FROM MSTTENURE B WHERE B.TENUREID = B.MARKINGTO) ),NULL) MARKINGTO," +
				" DECODE(B.ACTION,'RCD',TO_CHAR(B.MARKINGDATE, 'DD/MM/YYYY HH24:MI'),NULL) MARKINGDATE,  B.MARKINGREMARK,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = B.ACTION) ACTION," +
				" DECODE(B.ACTION,'FOW',TO_CHAR(B.ACTIONDATE, 'DD/MM/YYYY HH24:MI'),NULL) ACTIONDATE FROM TRNFILEHDR A, TRNFILEMARKING B WHERE A.FMID = B.FMID " +
				" AND DECODE(B.ACTION,'RCD',B.MARKINGTO,B.MARKINGFROM)='"+tenureId+"' ";
		
		condSQL += fileNo.trim().length() > 0 ? " AND UPPER(A.FILENO) LIKE UPPER('%"+fileNo+"%')" : "";
		
		condSQL += subjectCode.trim().length() > 0 ? " AND DECODE(A.DESTINATIONMARKING,'"+tenureId+"',A.FILESUBJECTCODEDES,A.FILESUBJECTCODEORG) = '"+subjectCode+"'" : "";
		
		condSQL += subject.trim().length() > 0 ? " AND UPPER(A.SUBJECT) LIKE UPPER('%"+subject+"%')": "";
		
		condSQL += markingFrom.trim().length() > 0 ? " AND B.MARKINGFROM = '"+markingFrom+"'" : "";
		
		condSQL += markingTo.trim().length() > 0 ? " AND B.MARKINGTO = '"+markingTo+"'" : "";
		
		condSQL += remarks.trim().length() > 0 ? " AND UPPER(A.REMARKS) LIKE UPPER('%"+remarks+"%')" : "";
		
		condSQL += markingDateFrom.trim().length() > 0 && markingDateTo.trim().length() > 0? " AND TO_DATE(TO_CHAR(B.ACTIONDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+markingDateFrom+"','DD/MM/YYYY') AND TO_DATE('"+markingDateTo+"','DD/MM/YYYY') " : "";
		
		
		
		strSQL += condSQL;
		strSQL += " ORDER BY A.FMID";
		
		try {
			log.debug(strSQL);
			con.openConnection();
			/*ResultSet rs =  con.select(strSQL);
			while (rs.next()) {
				CommonBean bn =  new CommonBean();
				bn.setField1(StringFormat.nullString(rs.getString("FMID")));
				bn.setField2(StringFormat.nullString(rs.getString("FILENO")));
				bn.setField3(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setField4(StringFormat.nullString(rs.getString("SUBJECTCODE")));
				bn.setField5(StringFormat.nullString(rs.getString("MARKINGFROM")));
				bn.setField6(StringFormat.nullString(rs.getString("MARKINGTO")));
				bn.setField7(StringFormat.nullString(rs.getString("MARKINGDATE")));
				bn.setField8(StringFormat.nullString(rs.getString("MARKINGREMARK")));
				bn.setField9(StringFormat.nullString(rs.getString("ACTION")));
				bn.setField10(StringFormat.nullString(rs.getString("ACTIONDATE")));
				
				arrList.add(bn);
			}
			rs.close();*/
			
		} catch(Exception e)		{
			log.fatal(e,e);
		}finally{
			con.closeConnection();
		}
		return arrList;
		}

	

	
	public int setInboxReminderAction(String refId, String action, String loginId, String reminderType, String signedBy, String signedOn) {
		
		int updatestatus = 0;
		
		DBConnection dbcon = new DBConnection();
		try {
			
			/*String strSQLREMINDER = " INSERT INTO TRNREMINDER (REFID, REMINDERSEQUENCE, REMINDERFROM, REMINDERTO, REMINDERDATE, REMINDERREMARK,"
					+ " TARGETDAYS, TARGETDATE, SUBJECTCODE, SUBJECT, REMINDERACTION, REMINDERACTIONDATE)"
					+ " SELECT REFID, REMINDERSEQUENCE +1, REMINDERFROM, REMINDERTO, REMINDERDATE, REMINDERREMARK,"
					+ " TARGETDAYS, TARGETDATE, SUBJECTCODE, SUBJECT, '" + action + "', SYSDATE" + " FROM TRNREMINDER A WHERE REFID = '"
					+ refId + "' " + " AND A. REMINDERSEQUENCE = (SELECT MAX(B.REMINDERSEQUENCE) "
					+ " FROM TRNREMINDER B WHERE B.REFID = A.REFID)  ";*/
			
			String strSQLREMINDER = " INSERT INTO TRNREMINDER (REFID, REMINDERSEQUENCE, REMINDERFROM, REMINDERTO, REMINDERDATE, REMINDERREMARK, " +
									" REMINDERACTION, REMINDERACTIONDATE, LOGINID, CHANGEDATE, TARGETDAYS, TARGETDATE, SUBJECTCODE, SUBJECT, REMINDERTYPE," +
									" SIGNEDBY, SIGNEDON ) SELECT REFID, REMINDERSEQUENCE +1, REMINDERFROM, REMINDERTO, REMINDERDATE, REMINDERREMARK," +
									" 'RCD', SYSDATE, '"+loginId+"', SYSDATE, TARGETDAYS, TARGETDATE,  SUBJECTCODE, SUBJECT , '"+reminderType+"'," +
									" '"+signedBy+"', '"+signedOn+"' FROM TRNREMINDER A WHERE REFID = '1' " +
									" AND A. REMINDERSEQUENCE = (SELECT MAX(B.REMINDERSEQUENCE)  FROM TRNREMINDER B WHERE B.REFID = A.REFID);)  ";
			
			log.debug(strSQLREMINDER); 
			dbcon.openConnection();
			updatestatus = dbcon.update(strSQLREMINDER);
			
		} catch (SQLException e) {
			log.fatal(e, e);
			dbcon.rollback();
		} finally {
			
			dbcon.closeConnection();
		}
		
		return updatestatus;
		
	}
	
	public ArrayList<TrnReminder> setoutBoxReminderAction(String refId, String outBoxReminderFrom, String outBoxReminderTo, String outboxReminderRemark,
			String targetDate, String subjectCode, String subject, String action) {
		
		ArrayList<TrnReminder> reminderBeanList = new ArrayList<TrnReminder>();
		
		DBConnection dbcon = new DBConnection();
		try {
			
			dbcon.openConnection();
			String outBoxReminderSequence = getNextReminderSequence(dbcon, "REMINDERSEQUENCE", "TRNREMINDER", refId);
			
			String reminderQuery = " INSERT INTO TRNREMINDER (REFID, REMINDERSEQUENCE, REMINDERFROM, REMINDERTO, REMINDERDATE, REMINDERREMARK, "
					+ " TARGETDAYS, TARGETDATE, SUBJECTCODE, SUBJECT, REMINDERACTION, REMINDERACTIONDATE) " + " VALUES (" + " '"
					+ refId
					+ "',"
					+ " '"
					+ outBoxReminderSequence
					+ "',"
					+ " '"
					+ outBoxReminderFrom
					+ "',"
					+ " '"
					+ outBoxReminderTo
					+ "',"
					+ " SYSDATE, "
					+ " '"
					+ outboxReminderRemark
					+ "',"
					+ " '15',"
					+ " TO_DATE('"
					+ targetDate
					+ "','DD/MM/YYYY'),"
					+ " '"
					+ subjectCode + "'," + " '" + subject + "', " + " '" + action + "', " + " SYSDATE" + ")";
			
			log.debug(reminderQuery);
			dbcon.insert(reminderQuery);
			
		} catch (Exception e) {
			log.fatal(e, e);
			dbcon.rollback();
		} finally {
			
			dbcon.closeConnection();
		}
		
		return reminderBeanList;
		
	}
	
	public ArrayList<TrnReminder> getOutBoxReminderData(String tenureId) {
		ArrayList<TrnReminder> reminderOutBeanList =new ArrayList<TrnReminder>();
		
		DBConnection con =  new DBConnection();
		try {
			
			String strSQLreminder = " SELECT A.REFID, (SELECT REFNO FROM TRNREFERENCE WHERE REFID = A.REFID) REFNO, REMINDERSEQUENCE," +
			" REMINDERFROM, " +
			" (SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT B.ROLEID FROM MSTTENURE B WHERE B.TENUREID = REMINDERFROM) )  REMINDERFROMNAME, " +
			" REMINDERTO, TO_CHAR(REMINDERDATE, 'DD/MM/YYYY HH24:MI') REMINDERDATE,  REMINDERREMARK, TARGETDAYS," +
			" TO_CHAR(TARGETDATE, 'DD/MM/YYYY') TARGETDATE, SUBJECTCODE, SUBJECT, REMINDERACTION, TO_CHAR(REMINDERACTIONDATE, 'DD/MM/YYYY HH24:MI') REMINDERACTIONDATE" +
			" FROM TRNREMINDER A  WHERE A.REMINDERTO= '"+tenureId+"' AND A.REMINDERACTION ='RCD'" ;// +
			//" AND A.REMINDERSEQUENCE = ( SELECT MAX(X.REMINDERSEQUENCE) FROM TRNREMINDER X  WHERE X.REFID= A.REFID)";
			
			log.debug(strSQLreminder);
			con.openConnection();
			ResultSet rsreminder = con.select(strSQLreminder);
			while (rsreminder.next()) {
				TrnReminder reminderBean = new TrnReminder();
				
				reminderBean.setREFID(StringFormat.nullString(rsreminder.getString("REFID")));
				reminderBean.setREFNO(StringFormat.nullString(rsreminder.getString("REFNO")));
				reminderBean.setREMINDERSEQUENCE(StringFormat.nullString(rsreminder.getString("REMINDERSEQUENCE")));
				reminderBean.setREMINDERFROM(StringFormat.nullString(rsreminder.getString("REMINDERFROM")));
				reminderBean.setREMINDERFROMNAME(StringFormat.nullString(rsreminder.getString("REMINDERFROMNAME")));
				reminderBean.setREMINDERTO(StringFormat.nullString(rsreminder.getString("REMINDERTO")));
				reminderBean.setREMINDERDATE(StringFormat.nullString(rsreminder.getString("REMINDERDATE")));
				reminderBean.setREMINDERREMARK(StringFormat.nullString(rsreminder.getString("REMINDERREMARK")));
				reminderBean.setTARGETDAYS(StringFormat.nullString(rsreminder.getString("TARGETDAYS")));
				reminderBean.setTARGETDATE(StringFormat.nullString(rsreminder.getString("TARGETDATE")));
				reminderBean.setSUBJECTCODE(StringFormat.nullString(rsreminder.getString("SUBJECTCODE")));
				reminderBean.setSUBJECT(StringFormat.nullString(rsreminder.getString("SUBJECT")));
				reminderBean.setREMINDERACTION(StringFormat.nullString(rsreminder.getString("REMINDERACTION")));
				reminderBean.setREMINDERACTIONDATE(StringFormat.nullString(rsreminder.getString("REMINDERACTIONDATE")));
				
				reminderOutBeanList.add(reminderBean);
				
			}
			rsreminder.close();
			
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {
			
			con.closeConnection();
		}
		
		return reminderOutBeanList;
		
	}
	
	
	public ArrayList<TrnReminder> getInboxReminderData(String tenureId) {
		ArrayList<TrnReminder> reminderBeanList =new ArrayList<TrnReminder>();

		DBConnection con =  new DBConnection();
		try {

			String strSQLREMINDER = " SELECT A.REFID, (SELECT REFNO FROM TRNREFERENCE WHERE REFID = A.REFID) REFNO, REMINDERSEQUENCE," +
					" GETROLENAME(REMINDERFROM) REMINDERFROM, GETROLENAME(REMINDERTO) REMINDERTO, TO_CHAR(REMINDERDATE, 'DD/MM/YYYY HH24:MI') REMINDERDATE," +
					" REMINDERREMARK, TARGETDAYS, TO_CHAR(TARGETDATE, 'DD/MM/YYYY') TARGETDATE, SUBJECTCODE, SUBJECT, REMINDERACTION, " +
					" TO_CHAR(REMINDERACTIONDATE, 'DD/MM/YYYY HH24:MI') REMINDERACTIONDATE" +
					" FROM TRNREMINDER A, TRNREFERENCE B  WHERE A.REFID = B.REFID AND A.REMINDERTO= '"+tenureId+"' AND A.REMINDERACTION ='FOW' AND A.REMINDERACTION <> 'RCD' ";//+
	            //    " AND A.REMINDERSEQUENCE = ( SELECT MAX(X.REMINDERSEQUENCE) FROM TRNREMINDER X  WHERE X.REFID= A.REFID)";

			
			log.debug(strSQLREMINDER);
			con.openConnection();
			ResultSet rsREMINDER = con.select(strSQLREMINDER);
			while (rsREMINDER.next()) {
				TrnReminder reminderBean = new TrnReminder();
				
				reminderBean.setREFID(StringFormat.nullString(rsREMINDER.getString("REFID")));
				reminderBean.setREFNO(StringFormat.nullString(rsREMINDER.getString("REFNO")));
				reminderBean.setREMINDERSEQUENCE(StringFormat.nullString(rsREMINDER.getString("REMINDERSEQUENCE")));
				reminderBean.setREMINDERFROM(StringFormat.nullString(rsREMINDER.getString("REMINDERFROM")));
				reminderBean.setREMINDERTO(StringFormat.nullString(rsREMINDER.getString("REMINDERTO")));
				reminderBean.setREMINDERDATE(StringFormat.nullString(rsREMINDER.getString("REMINDERDATE")));
				reminderBean.setREMINDERREMARK(StringFormat.nullString(rsREMINDER.getString("REMINDERREMARK")));
				reminderBean.setTARGETDAYS(StringFormat.nullString(rsREMINDER.getString("TARGETDAYS")));
				reminderBean.setTARGETDATE(StringFormat.nullString(rsREMINDER.getString("TARGETDATE")));
				reminderBean.setSUBJECTCODE(StringFormat.nullString(rsREMINDER.getString("SUBJECTCODE")));
				reminderBean.setSUBJECT(StringFormat.nullString(rsREMINDER.getString("SUBJECT")));
				reminderBean.setREMINDERACTION(StringFormat.nullString(rsREMINDER.getString("REMINDERACTION")));
				reminderBean.setREMINDERACTIONDATE(StringFormat.nullString(rsREMINDER.getString("REMINDERACTIONDATE")));
				
				reminderBeanList.add(reminderBean);

			}
			rsREMINDER.close();
			
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {

			con.closeConnection();
		}

		return reminderBeanList;

	}
	
	public String getNextReminderSequence(DBConnection con, String colName, String tableName, String refId) {
		String strSQL = "(SELECT NVL(MAX(A." + colName + "), 0)+ 1 FROM " + tableName + " A WHERE REFID = '" + refId + "')";
		log.debug(strSQL);
		return CommonDAO.getStringParam(strSQL, con);
	}
	
	
	
}