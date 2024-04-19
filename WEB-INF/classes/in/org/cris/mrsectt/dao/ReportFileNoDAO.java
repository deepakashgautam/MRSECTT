package in.org.cris.mrsectt.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MastersReportBean;
import in.org.cris.mrsectt.Beans.TrnReference;
import in.org.cris.mrsectt.dbConnection.DBConnection;

//import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class ReportFileNoDAO {
	static Logger log = LogManager.getLogger(ReportFileNoDAO.class);
	String strSQL = "";
	public static final short EXCEL_COLUMN_WIDTH_FACTOR = 256; 
	 public static final int UNIT_OFFSET_LENGTH = 7; 
	 public static final int[] UNIT_OFFSET_MAP = new int[] { 0, 36, 73, 109, 146, 182, 219 }; 
	
//	static Logger log = LogManager.getLogger(ReportRefNoDAO.class);

	/*public ArrayList<CommonBean> getTrnFileHdr(String fdate, String roleId,String isConf,String isReply) {
		
		String regNo = "";
		String regDate = "";
		if (fdate.trim().length() > 0) {
			String[] arrRefNo = fdate.split("   ");
			regNo = arrRefNo[0];
			regDate = arrRefNo[1];
		}
		String subQry = "";
		String Qreply = "";
		if(isConf.equalsIgnoreCase("1")){
			subQry+=isConf.equalsIgnoreCase("0")? "AND REFERENCETYPE NOT IN ('C')": "";
		}else{
			subQry+= "AND REFERENCETYPE NOT IN ('C')";
		}
		if(isReply.equalsIgnoreCase("1")){
				Qreply=isReply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=TRNFILEHDR.FMID)";
			}
		else{
			
			Qreply="''";
		}
		
		strSQL = " select  FMID," +
				 " DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONNODES,REGISTRATIONNOORG) REGISTRATIONNO," +
				 " TO_CHAR(DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATEDES," +
				 " CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval'" +
				 " WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Conf.' END REFERENCETYPE," +
				 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=DEPARTMENTCODE) DEPARTMENTCODE," +
				 " FILENO, "+
				 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=DESTINATIONMARKING) DESTINATIONMARKING, " +
				 " LINKFILENO1, LINKFILENO2, LINKFILENO3, LINKFILENO4," +
				 " SUBJECT," +
				 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
				 " FILESUBJECTCODEDES," +
				 " PROPOSEDPATH," +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=FILESTATUS1) FILESTATUS1," +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=FILESTATUS2) FILESTATUS2," +
				 " REMARKS," +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
				 " REASON, NOL,"+Qreply+" REPLY" +
				 " FROM TRNFILEHDR " +
				 " WHERE DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONNODES,REGISTRATIONNOORG) = UPPER('"+regNo+"')" +
				 " AND TO_CHAR(DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') = '"+regDate+"' " +
				 " "+subQry+" ORDER BY FMID ";
			return (new CommonDAO()).getSQLResult(strSQL,22);
			}*/
	 
	 //rishabh code
	 public ArrayList<CommonBean> getTrnFileHdr(String fdate, String roleId,String isConf,String isReply) {
			
			String regNo = "";
			String regDate = "";
			if (fdate.trim().length() > 0) {
				String[] arrRefNo = fdate.split("   ");
				regNo = arrRefNo[0];
				regDate = arrRefNo[1];
			}
			String subQry = "";
			String Qreply = "";
			if(isConf.equalsIgnoreCase("1")){
				subQry+=isConf.equalsIgnoreCase("0")? "AND REFERENCETYPE NOT IN ('C')": "";
			}else{
				subQry+= "AND REFERENCETYPE NOT IN ('C')";
			}
			if(isReply.equalsIgnoreCase("1")){
					Qreply=isReply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=TRNFILEHDR.FMID)";
				}
			else{
				
				Qreply="''";
			}
			
	/*		strSQL = " select  FMID," +
					 " DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONNODES,REGISTRATIONNOORG) REGISTRATIONNO," +
					 " TO_CHAR(DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATEDES," +
					 " CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval'" +
					 " WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Conf.' END REFERENCETYPE," +
					 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=DEPARTMENTCODE) DEPARTMENTCODE," +
					 " FILENO, "+
					 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=DESTINATIONMARKING) DESTINATIONMARKING, " +
					 " LINKFILENO1, LINKFILENO2, LINKFILENO3, LINKFILENO4," +
					 " SUBJECT," +
					 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
					 " FILESUBJECTCODEDES," +
					 " PROPOSEDPATH," +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=FILESTATUS1) FILESTATUS1," +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=FILESTATUS2) FILESTATUS2," +
					 " REMARKS," +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
					 " REASON, NOL,"+Qreply+" REPLY" +
					 " FROM TRNFILEHDR " +
					 " WHERE DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONNODES,REGISTRATIONNOORG) = UPPER('"+regNo+"')" +
					 " AND TO_CHAR(DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') = '"+regDate+"' " +
					 " "+subQry+" ORDER BY FMID ";

			return (new CommonDAO()).getSQLResult(strSQL,22);*/

			strSQL = " select  FMID," +
					 " DECODE(DESTINATIONMARKING,?,REGISTRATIONNODES,REGISTRATIONNOORG) REGISTRATIONNO," +
					 " TO_CHAR(DECODE(DESTINATIONMARKING,?,REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATEDES," +
					 " CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval'" +
					 " WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Conf.' END REFERENCETYPE," +
					 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=DEPARTMENTCODE) DEPARTMENTCODE," +
					 " FILENO, "+
					 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=DESTINATIONMARKING) DESTINATIONMARKING, " +
					 " LINKFILENO1, LINKFILENO2, LINKFILENO3, LINKFILENO4," +
					 " SUBJECT," +
					 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
					 " FILESUBJECTCODEDES," +
					 " PROPOSEDPATH," +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=FILESTATUS1) FILESTATUS1," +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=FILESTATUS2) FILESTATUS2," +
					 " REMARKS," +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
					 " REASON, NOL,"+Qreply+" REPLY, EOFFICENO" +
					 " FROM TRNFILEHDR " +
					 " WHERE DECODE(DESTINATIONMARKING,?,REGISTRATIONNODES,REGISTRATIONNOORG) = UPPER(?)" +
					 " AND TO_CHAR(DECODE(DESTINATIONMARKING,?,REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') = ? " +
					 " "+subQry+" ORDER BY FMID ";
	       PreparedStatement ps = null;
			ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
			DBConnection dbcon = new DBConnection();
			try {

				dbcon.openConnection();
				ps = dbcon.setPrepStatement(strSQL);
				ps.setString(1, roleId);
				ps.setString(2, roleId);
				
				ps.setString(3, roleId);
				ps.setString(4, regNo);
				ps.setString(5, roleId);
				ps.setString(6, regDate);
				
				arr = (new CommonDAO()).getSQLResultPreparedStmt(ps,23,dbcon);
			} catch (Exception e) {
				//log.fatal(e, e);
			} finally {
				dbcon.closeConnection();
			}
			return arr;
			
				}

	/*public ArrayList<CommonBean> getTrnFileHdrPopup(String fmID, String roleId) {
		
		strSQL = " select  FMID," +
		" DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONNODES,REGISTRATIONNOORG) REGISTRATIONNO," +
		" TO_CHAR(DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATEDES," +
		" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval'" +
		" WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Conf.' END REFERENCETYPE," +
		" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=DEPARTMENTCODE) DEPARTMENTCODE," +
		" FILENO, "+
		" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=DESTINATIONMARKING) DESTINATIONMARKING, " +
		" LINKFILENO1, LINKFILENO2, LINKFILENO3, LINKFILENO4," +
		" SUBJECT," +
		" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
		" FILESUBJECTCODEDES," +
		" PROPOSEDPATH," +
		" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=FILESTATUS1) FILESTATUS1," +
		" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=FILESTATUS2) FILESTATUS2," +
		" REMARKS," +
		" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
		" REASON, NOL" +
		" FROM TRNFILEHDR " +
		" WHERE FMID = "+fmID+" ORDER BY FMID ";
		return (new CommonDAO()).getSQLResult(strSQL,21);
	}*/
	 
	 //soumen func
	 public ArrayList<CommonBean> getTrnFileHdrPopup(String fmID, String roleId) {
			
			/*strSQL = " select  FMID," +
			" DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONNODES,REGISTRATIONNOORG) REGISTRATIONNO," +
			" TO_CHAR(DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATEDES," +
			" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval'" +
			" WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Conf.' END REFERENCETYPE," +
			" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=DEPARTMENTCODE) DEPARTMENTCODE," +
			" FILENO, "+
			" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=DESTINATIONMARKING) DESTINATIONMARKING, " +
			" LINKFILENO1, LINKFILENO2, LINKFILENO3, LINKFILENO4," +
			" SUBJECT," +
			" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
			" FILESUBJECTCODEDES," +
			" PROPOSEDPATH," +
			" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=FILESTATUS1) FILESTATUS1," +
			" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=FILESTATUS2) FILESTATUS2," +
			" REMARKS," +
			" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
			" REASON, NOL" +
			" FROM TRNFILEHDR " +
			" WHERE FMID = "+fmID+" ORDER BY FMID ";
			return (new CommonDAO()).getSQLResult(strSQL,21);*/
			
			strSQL = " select  FMID," +
					" DECODE(DESTINATIONMARKING,?,REGISTRATIONNODES,REGISTRATIONNOORG) REGISTRATIONNO," +
					" TO_CHAR(DECODE(DESTINATIONMARKING,?,REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATEDES," +
					" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval'" +
					" WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Conf.' END REFERENCETYPE," +
					" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=DEPARTMENTCODE) DEPARTMENTCODE," +
					" FILENO, "+
					" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=DESTINATIONMARKING) DESTINATIONMARKING, " +
					" LINKFILENO1, LINKFILENO2, LINKFILENO3, LINKFILENO4," +
					" SUBJECT," +
					" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
					" FILESUBJECTCODEDES," +
					" PROPOSEDPATH," +
					" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=FILESTATUS1) FILESTATUS1," +
					" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=FILESTATUS2) FILESTATUS2," +
					" REMARKS," +
					" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
					" REASON, NOL" +
					" FROM TRNFILEHDR " +
					" WHERE FMID = ? ORDER BY FMID ";
			
			ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
			DBConnection con = new DBConnection();
			try {
						con.openConnection();
						PreparedStatement ps = con.setPrepStatement(strSQL);
						ps.setString(1,roleId);
						ps.setString(2,roleId);
						ps.setString(3,fmID);
						arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 21,con);
						//System.out.println("getTrnFileHdrPopup executed");
						
				} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
				finally{
						con.closeConnection();
				}
			return arr;
					
			
		}


	/*public ArrayList<CommonBean> getTrnFileRef(String fdate, String roleId) {
		
		String regNo = "";
		String regDate = "";
		if (fdate.trim().length() > 0) {
			String[] arrRefNo = fdate.split("   ");
			regNo = arrRefNo[0];
			regDate = arrRefNo[1];
		}
		strSQL = " SELECT A.FMID,A.REFID," +
				 " (SELECT M.REFNO FROM TRNREFERENCE M WHERE M.REFID=A.REFID) REFNO," +
				 " (SELECT N.REFERENCENAME FROM TRNREFERENCE N WHERE N.REFID=A.REFID) REFERENCENAME," +
				 " (SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=(SELECT DISTINCT O.SUBJECTCODE FROM TRNREFERENCE O WHERE O.REFID=A.REFID)) SUBJECTCODE," +
				 " (SELECT DISTINCT P.SUBJECT FROM TRNREFERENCE P WHERE P.REFID=A.REFID) SUBJECT," +
				 " (SELECT (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=O.MARKINGTO)||','||(SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = O.ACTION)||','||O.ACTIONDATE FROM TRNMARKING O WHERE O.REFID=A.REFID AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNMARKING P WHERE P.REFID=A.REFID)) STATUS," +
				 " (SELECT S.VIPSTATUS FROM TRNREFERENCE S WHERE S.REFID=A.REFID) VIPSTATUS," +
				 " (SELECT R.STATENAME FROM MSTSTATE R WHERE R.STATECODE=(SELECT T.STATECODE FROM TRNREFERENCE T WHERE T.REFID=A.REFID)) STATENAME" +
				 " FROM TRNFILEREF A, TRNFILEHDR B" +
				 " WHERE A.FMID = B.FMID AND DECODE(B.DESTINATIONMARKING,'"+roleId+"',B.REGISTRATIONNODES,B.REGISTRATIONNOORG) = UPPER('"+regNo+"')" +
				 " AND TO_CHAR(DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') = '"+regDate+"' ORDER BY FMID ";
		return (new CommonDAO()).getSQLResult(strSQL,9);
	}*/
	//rishabh function
public ArrayList<CommonBean> getTrnFileRef(String fdate, String roleId) {
		
		String regNo = "";
		String regDate = "";
		if (fdate.trim().length() > 0) {
			String[] arrRefNo = fdate.split("   ");
			regNo = arrRefNo[0];
			regDate = arrRefNo[1];
		}
		/*strSQL = " SELECT A.FMID,A.REFID," +
				 " (SELECT M.REFNO FROM TRNREFERENCE M WHERE M.REFID=A.REFID) REFNO," +
				 " (SELECT N.REFERENCENAME FROM TRNREFERENCE N WHERE N.REFID=A.REFID) REFERENCENAME," +
				 " (SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=(SELECT DISTINCT O.SUBJECTCODE FROM TRNREFERENCE O WHERE O.REFID=A.REFID)) SUBJECTCODE," +
				 " (SELECT DISTINCT P.SUBJECT FROM TRNREFERENCE P WHERE P.REFID=A.REFID) SUBJECT," +
				 " (SELECT (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=O.MARKINGTO)||','||(SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = O.ACTION)||','||O.ACTIONDATE FROM TRNMARKING O WHERE O.REFID=A.REFID AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNMARKING P WHERE P.REFID=A.REFID)) STATUS," +
				 " (SELECT S.VIPSTATUS FROM TRNREFERENCE S WHERE S.REFID=A.REFID) VIPSTATUS," +
				 " (SELECT R.STATENAME FROM MSTSTATE R WHERE R.STATECODE=(SELECT T.STATECODE FROM TRNREFERENCE T WHERE T.REFID=A.REFID)) STATENAME" +
				 " FROM TRNFILEREF A, TRNFILEHDR B" +
				 " WHERE A.FMID = B.FMID AND DECODE(B.DESTINATIONMARKING,'"+roleId+"',B.REGISTRATIONNODES,B.REGISTRATIONNOORG) = UPPER('"+regNo+"')" +
				 " AND TO_CHAR(DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') = '"+regDate+"' ORDER BY FMID ";
		return (new CommonDAO()).getSQLResult(strSQL,9);*/
		
		strSQL = " SELECT A.FMID,A.REFID," +
				 " (SELECT M.REFNO FROM TRNREFERENCE M WHERE M.REFID=A.REFID) REFNO," +
				 " (SELECT N.REFERENCENAME FROM TRNREFERENCE N WHERE N.REFID=A.REFID) REFERENCENAME," +
				 " (SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=(SELECT DISTINCT O.SUBJECTCODE FROM TRNREFERENCE O WHERE O.REFID=A.REFID)) SUBJECTCODE," +
				 " (SELECT DISTINCT P.SUBJECT FROM TRNREFERENCE P WHERE P.REFID=A.REFID) SUBJECT," +
				 " (SELECT (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=O.MARKINGTO)||','||(SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = O.ACTION)||','||O.ACTIONDATE FROM TRNMARKING O WHERE O.REFID=A.REFID AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNMARKING P WHERE P.REFID=A.REFID)) STATUS," +
				 " (SELECT S.VIPSTATUS FROM TRNREFERENCE S WHERE S.REFID=A.REFID) VIPSTATUS," +
				 " (SELECT R.STATENAME FROM MSTSTATE R WHERE R.STATECODE=(SELECT T.STATECODE FROM TRNREFERENCE T WHERE T.REFID=A.REFID)) STATENAME" +
				 " FROM TRNFILEREF A, TRNFILEHDR B" +
				 " WHERE A.FMID = B.FMID AND DECODE(B.DESTINATIONMARKING,?,B.REGISTRATIONNODES,B.REGISTRATIONNOORG) = UPPER(?)" +
				 " AND TO_CHAR(DECODE(DESTINATIONMARKING,?,REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') = ? ORDER BY FMID ";
		
	       PreparedStatement ps = null;
			ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
			DBConnection dbcon = new DBConnection();
			try {

				dbcon.openConnection();
				ps = dbcon.setPrepStatement(strSQL);
				ps.setString(1, roleId);
				ps.setString(2, regNo);
				
				ps.setString(3, roleId);
				ps.setString(4, regDate);

				
				arr = (new CommonDAO()).getSQLResultPreparedStmt(ps,9,dbcon);
			} catch (Exception e) {
				//log.fatal(e, e);
			} finally {
				dbcon.closeConnection();
			}
			return arr;
		
	}

	/*public ArrayList<CommonBean> getTrnFileRef(String fmID) {

		strSQL = " SELECT A.FMID,A.REFID," +
		" (SELECT M.REFNO FROM TRNREFERENCE M WHERE M.REFID=A.REFID) REFNO," +
		" (SELECT N.REFERENCENAME FROM TRNREFERENCE N WHERE N.REFID=A.REFID) REFERENCENAME," +
		" (SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=(SELECT DISTINCT O.SUBJECTCODE FROM TRNREFERENCE O WHERE O.REFID=A.REFID)) SUBJECTCODE," +
		" (SELECT DISTINCT P.SUBJECT FROM TRNREFERENCE P WHERE P.REFID=A.REFID) SUBJECT," +
		" (SELECT (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=O.MARKINGTO)||','||(SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = O.ACTION)||','||O.ACTIONDATE FROM TRNMARKING O WHERE O.REFID=A.REFID AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNMARKING P WHERE P.REFID=A.REFID)) STATUS," +
		" (SELECT S.VIPSTATUS FROM TRNREFERENCE S WHERE S.REFID=A.REFID) VIPSTATUS," +
		" (SELECT R.STATENAME FROM MSTSTATE R WHERE R.STATECODE=(SELECT T.STATECODE FROM TRNREFERENCE T WHERE T.REFID=A.REFID)) STATENAME" +
		" FROM TRNFILEREF A, TRNFILEHDR B" +
		" WHERE A.FMID = B.FMID AND A.FMID = "+fmID+" ORDER BY FMID ";
		return (new CommonDAO()).getSQLResult(strSQL,9);
	}*/

//soumen func
public ArrayList<CommonBean> getTrnFileRef(String fmID) {

	strSQL = " SELECT A.FMID,A.REFID," +
	" (SELECT M.REFNO FROM TRNREFERENCE M WHERE M.REFID=A.REFID) REFNO," +
	" (SELECT N.REFERENCENAME FROM TRNREFERENCE N WHERE N.REFID=A.REFID) REFERENCENAME," +
	" (SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=(SELECT DISTINCT O.SUBJECTCODE FROM TRNREFERENCE O WHERE O.REFID=A.REFID)) SUBJECTCODE," +
	" (SELECT DISTINCT P.SUBJECT FROM TRNREFERENCE P WHERE P.REFID=A.REFID) SUBJECT," +
	" (SELECT (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=O.MARKINGTO)||','||(SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = O.ACTION)||','||O.ACTIONDATE FROM TRNMARKING O WHERE O.REFID=A.REFID AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNMARKING P WHERE P.REFID=A.REFID)) STATUS," +
	" (SELECT S.VIPSTATUS FROM TRNREFERENCE S WHERE S.REFID=A.REFID) VIPSTATUS," +
	" (SELECT R.STATENAME FROM MSTSTATE R WHERE R.STATECODE=(SELECT T.STATECODE FROM TRNREFERENCE T WHERE T.REFID=A.REFID)) STATENAME" +
	" FROM TRNFILEREF A, TRNFILEHDR B" +
	" WHERE A.FMID = B.FMID AND A.FMID = ? ORDER BY FMID ";
	//" WHERE A.FMID = B.FMID AND A.FMID = "+fmID+" ORDER BY FMID ";
	//return (new CommonDAO()).getSQLResult(strSQL,9);
	
	ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
	DBConnection con = new DBConnection();
	try {
				con.openConnection();
				PreparedStatement ps = con.setPrepStatement(strSQL);
				ps.setString(1,fmID);
				arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 9,con);
				//System.out.println("getTrnFileRef executed");
				
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		finally{
				con.closeConnection();
		}
	return arr;
			
	
}
	/*public ArrayList<CommonBean> getTrnFileMarking(String fdate, String roleId) {
		
		String regNo = "";
		String regDate = "";
		if (fdate.trim().length() > 0) {
			String[] arrRefNo = fdate.split("   ");
			regNo = arrRefNo[0];
			regDate = arrRefNo[1];
		}
		strSQL = " select A.FMID, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=A.MARKINGTO) MARKINGTO, TO_CHAR(A.MARKINGDATE,'DD/MM/YYYY'), A.MARKINGREMARK" +
		 " from trnfilemarking A, trnfilehdr B" +
		 " where A.FMID=B.FMID" +
		 " AND DECODE(B.DESTINATIONMARKING,'"+roleId+"',B.REGISTRATIONNODES,B.REGISTRATIONNOORG) = UPPER('"+regNo+"')" +
		 " AND TO_CHAR(DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') = '"+regDate+"' "+
		 " AND A.markingtype='D'";
return (new CommonDAO()).getSQLResult(strSQL,4);
	}*/
	
	//rishabh function
public ArrayList<CommonBean> getTrnFileMarking(String fdate, String roleId) {
		
		String regNo = "";
		String regDate = "";
		if (fdate.trim().length() > 0) {
			String[] arrRefNo = fdate.split("   ");
			regNo = arrRefNo[0];
			regDate = arrRefNo[1];
		}
		/*strSQL = " select A.FMID, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=A.MARKINGTO) MARKINGTO, TO_CHAR(A.MARKINGDATE,'DD/MM/YYYY'), A.MARKINGREMARK" +
		 " from trnfilemarking A, trnfilehdr B" +
		 " where A.FMID=B.FMID" +
		 " AND DECODE(B.DESTINATIONMARKING,'"+roleId+"',B.REGISTRATIONNODES,B.REGISTRATIONNOORG) = UPPER('"+regNo+"')" +
		 " AND TO_CHAR(DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') = '"+regDate+"' "+
		 " AND A.markingtype='D'";
return (new CommonDAO()).getSQLResult(strSQL,4);*/
		

		strSQL = " select A.FMID, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=A.MARKINGTO) MARKINGTO, TO_CHAR(A.MARKINGDATE,'DD/MM/YYYY'), A.MARKINGREMARK, B.REFERENCETYPE" +
		 " from trnfilemarking A, trnfilehdr B" +
		 " where A.FMID=B.FMID" +
		 " AND DECODE(B.DESTINATIONMARKING,?,B.REGISTRATIONNODES,B.REGISTRATIONNOORG) = UPPER(?)" +
		 " AND TO_CHAR(DECODE(DESTINATIONMARKING,?,REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') = ? "+
		 " AND A.markingtype='D'";
		PreparedStatement ps = null;
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		
		DBConnection dbcon = new DBConnection();
		try {
		
			dbcon.openConnection();
			ps = dbcon.setPrepStatement(strSQL);
			ps.setString(1, roleId);
			ps.setString(2, regNo);
			
			ps.setString(3, roleId);
			ps.setString(4, regDate);
		
			
			arr = (new CommonDAO()).getSQLResultPreparedStmtRep1(ps,5,dbcon);
		} catch (Exception e) {
			//log.fatal(e, e);
		} finally {
			dbcon.closeConnection();
		}
		return arr;
			

	}

	/*public ArrayList<CommonBean> getTrnFileMarking(String fmID) {
		
		strSQL = " select A.FMID, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=A.MARKINGTO) MARKINGTO, TO_CHAR(A.MARKINGDATE,'DD/MM/YYYY'), A.MARKINGREMARK" +
		" from trnfilemarking A, trnfilehdr B" +
		" where A.FMID=B.FMID" +
		" AND B.FMID = "+fmID+"";
		return (new CommonDAO()).getSQLResult(strSQL,4);
	}*/
public ArrayList<CommonBean> getTrnFileMarking(String fmID) {
	
	strSQL = " select A.FMID, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=A.MARKINGTO) MARKINGTO, TO_CHAR(A.MARKINGDATE,'DD/MM/YYYY'), A.MARKINGREMARK" +
	" from trnfilemarking A, trnfilehdr B" +
	" where A.FMID=B.FMID" +
	" AND B.FMID = ? ";
	//" AND B.FMID = "+fmID+"";
	//return (new CommonDAO()).getSQLResult(strSQL,4);
	
	ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
	DBConnection con = new DBConnection();
	try {
				con.openConnection();
				PreparedStatement ps = con.setPrepStatement(strSQL);
				ps.setString(1,fmID);
				arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 4,con);
				//System.out.println("getTrnFileMarking executed");
				
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		finally{
				con.closeConnection();
		}
	return arr;
			
}
	public ArrayList<CommonBean> getTrnFileIntMarking(String fdate, String roleId) {
		
		String regNo = "";
		String regDate = "";
		if (fdate.trim().length() > 0) {
			String[] arrRefNo = fdate.split("   ");
			regNo = arrRefNo[0];
			regDate = arrRefNo[1];
		}
		strSQL = " select A.FMID, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=A.MARKINGTO) MARKINGTO, TO_CHAR(A.MARKINGDATE,'DD/MM/YYYY'), TO_CHAR(A.CHANGEDATE,'DD/MM/YYYY')" +
				 " from trnfileintmarking A, TRNFILEHDR B" +
				 " where A.FMID=B.FMID" +
				 " AND DECODE(B.DESTINATIONMARKING,'"+roleId+"',B.REGISTRATIONNODES,B.REGISTRATIONNOORG) = UPPER('"+regNo+"')" +
				 " AND TO_CHAR(DECODE(DESTINATIONMARKING,'"+roleId+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') = '"+regDate+"'";
		return (new CommonDAO()).getSQLResult(strSQL,4);
	}

	/*public ArrayList<CommonBean> getTrnFileIntMarking(String fmID) {
		
		strSQL = " select A.FMID, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=A.MARKINGTO) MARKINGTO, TO_CHAR(A.MARKINGDATE,'DD/MM/YYYY'), TO_CHAR(A.CHANGEDATE,'DD/MM/YYYY')" +
		" from trnfileintmarking A, TRNFILEHDR B" +
		" where A.FMID=B.FMID" +
		" AND B.FMID = "+fmID+"";
		return (new CommonDAO()).getSQLResult(strSQL,4);
	}*/
	//soumen func
public ArrayList<CommonBean> getTrnFileIntMarking(String fmID) {
		
		strSQL = " select A.FMID, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=A.MARKINGTO) MARKINGTO, TO_CHAR(A.MARKINGDATE,'DD/MM/YYYY'), TO_CHAR(A.CHANGEDATE,'DD/MM/YYYY')" +
		" from trnfileintmarking A, TRNFILEHDR B" +
		" where A.FMID=B.FMID" +
		" AND B.FMID = ? ";
		/*" AND B.FMID = "+fmID+"";
		return (new CommonDAO()).getSQLResult(strSQL,4);*/
		
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		DBConnection con = new DBConnection();
		try {
					con.openConnection();
					PreparedStatement ps = con.setPrepStatement(strSQL);
					ps.setString(1,fmID);
					arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 4,con);
					//System.out.println("getTrnFileIntMarking executed");
					
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			finally{
					con.closeConnection();
			}
		return arr;
				
	}
	
	
	public ArrayList<String> getIncomingDate(String roleId, String regNo) {
		DBConnection con = new DBConnection();
		ArrayList<String> arr = new ArrayList<String>();
		strSQL = " SELECT TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE FROM TRNFILEHDR A" +
				 " WHERE DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONNODES,A.REGISTRATIONNOORG) = UPPER('"+regNo+"') ";
//		log.debug(strSQL);
		try {
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while(rs.next())
			{
				arr.add(rs.getString("REGISTRATIONDATE"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			con.closeConnection();
		}
		return arr;
	}

	/*public String getFMId(String refId) {
		
		DBConnection con = new DBConnection();
		String fmID = "";
		strSQL = " SELECT FMID FROM TRNFILEREF WHERE REFID = '"+refId+"' ";
		try {
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			if(rs.next())
			{
				fmID = (rs.getString("FMID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fmID;
	}*/
	
	///soumen func
public String getFMId(String refId) {
		
		DBConnection con = new DBConnection();
		String fmID = "";
		//strSQL = " SELECT FMID FROM TRNFILEREF WHERE REFID = '"+refId+"' ";
		strSQL = " SELECT FMID FROM TRNFILEREF WHERE REFID = ? ";
		try {
			con.openConnection();
			//ResultSet rs = con.select(strSQL);
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ps.setString(1,refId);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				fmID = (rs.getString("FMID"));
			}
			//System.out.println("getFMId executed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			con.closeConnection();
		}
		return fmID;
	}
	
//	for Serial No From-To (FM) report. Report No- 2
	/*public ArrayList<CommonBean> getReportSerialNoFromTo(String filecounterfrom, String filecounterto, String registrationdatefrom, String registrationdateto, String rplyType, String roleId, String isConf, String isReply,String conf,String reply) {
		
		String strSQL = " SELECT DISTINCT A.FMID," +
						" DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
						" TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
						" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval'" +
						" WHEN REFERENCETYPE = 'P' THEN 'Position' END REFERENCETYPE," +
						" A.FILENO," +
						" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
						" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
						" A.SUBJECT," +
						" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
						" REMARKS," +
						" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
						" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY')," +
						" B.MARKINGREMARK," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
						" REASON" +
						" FROM TRNFILEHDR A, TRNFILEMARKING B" +
						" WHERE 1=1 AND A.FMID = B.FMID(+)" ;
		String subQry = "";
		String Qreply = "";
		//subQry=isConf.equalsIgnoreCase("0")? " AND REFERENCETYPE NOT IN ('C')": "";
		if(isConf.equalsIgnoreCase("1")){
			subQry+=conf.equalsIgnoreCase("0")? "AND REFERENCETYPE NOT IN ('C')": "";
		}else{
			subQry+= "AND REFERENCETYPE NOT IN ('C')";
		}
		if(isReply.equalsIgnoreCase("1")){
			Qreply=reply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=A.FMID)";
		}
		else{
			Qreply="''";
		}
		
		
		
				String strSQL = " SELECT DISTINCT A.FMID," +
						" DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
						" TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
						" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Conf.' END REFERENCETYPE," +
						" A.FILENO," +
						" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
						" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
						" A.SUBJECT," +
						" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
						" REMARKS," +
						" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
						" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY')," +
						" B.MARKINGREMARK," +
						" REPLYTYPE," +
						" REASON, REFERENCETYPE,"+Qreply+" REPLY" +
						" FROM TRNFILEHDR A, TRNFILEMARKING B" +
						" WHERE 1=1 AND A.FMID = B.FMID(+)" ;
		//				"AND  DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.FILECOUNTERDES,A.FILECOUNTERORG) BETWEEN 1 AND 5" +
		//				"AND  DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG) BETWEEN TO_DATE('01/07/2011','DD/MM/YYYY') AND TO_DATE('25/04/2012','DD/MM/YYYY')" +
		//				"ORDER BY REGISTRATIONNO; ";

					String tempSQL = "";
					if (filecounterfrom.trim().length() > 0	&& filecounterto.trim().length() > 0) {
			//			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
						tempSQL += " AND DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.FILECOUNTERDES,A.FILECOUNTERORG) BETWEEN "+filecounterfrom+" AND "+filecounterto+" ";
					}else if (filecounterfrom.trim().length() > 0	&& filecounterto.trim().length() <= 0) {
						tempSQL += " AND DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.FILECOUNTERDES,A.FILECOUNTERORG) = "+filecounterfrom+" ";
					}
					if (registrationdatefrom.trim().length() > 0 && registrationdateto.trim().length() > 0) {
			//			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
						tempSQL += " AND TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+registrationdatefrom+"','DD/MM/YYYY') AND TO_DATE('"+registrationdateto+"','DD/MM/YYYY')";
					}else if (registrationdatefrom.trim().length() > 0 && registrationdateto.trim().length() <= 0) {
						tempSQL += " AND DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG) = TO_DATE('"+registrationdatefrom+"','DD/MM/YYYY') ";
					}
					tempSQL += rplyType.trim().length()>0? " AND REPLYTYPE = '"+rplyType+"'": "";
					strSQL += tempSQL;
					strSQL += subQry;
					strSQL += " ORDER BY A.FMID";
					return (new CommonDAO()).getSQLResult(strSQL,19);
	}*/
	
	//rishabh code
	public ArrayList<CommonBean> getReportSerialNoFromTo(String filecounterfrom, String filecounterto, String registrationdatefrom, String registrationdateto, String rplyType, String roleId, String isConf, String isReply,String conf,String reply) {
		
				String subQry = "";
				String Qreply = "";
				//subQry=isConf.equalsIgnoreCase("0")? " AND REFERENCETYPE NOT IN ('C')": "";
				if(isConf.equalsIgnoreCase("1")){
					subQry+=conf.equalsIgnoreCase("0")? "AND REFERENCETYPE NOT IN ('C')": "";
				}else{
					subQry+= "AND REFERENCETYPE NOT IN ('C')";
				}
				if(isReply.equalsIgnoreCase("1")){
					Qreply=reply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=A.FMID)";
				}
				else{
					Qreply="''";
				}
				
				PreparedStatement ps = null;
				ArrayList<String> params = new ArrayList<String>();
				params.add("");
				
		/*				String strSQL = " SELECT DISTINCT A.FMID," +
								" DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
								" TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
								" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Conf.' END REFERENCETYPE," +
								" A.FILENO," +
								" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
								" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
								" A.SUBJECT," +
								" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
								" REMARKS," +
								" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
								" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY')," +
								" B.MARKINGREMARK," +
								" REPLYTYPE," +
								" REASON, REFERENCETYPE,"+Qreply+" REPLY" +
								" FROM TRNFILEHDR A, TRNFILEMARKING B" +
								" WHERE 1=1 AND A.FMID = B.FMID(+)" ;
				//				"AND  DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.FILECOUNTERDES,A.FILECOUNTERORG) BETWEEN 1 AND 5" +
				//				"AND  DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG) BETWEEN TO_DATE('01/07/2011','DD/MM/YYYY') AND TO_DATE('25/04/2012','DD/MM/YYYY')" +
				//				"ORDER BY REGISTRATIONNO; ";

							String tempSQL = "";
							if (filecounterfrom.trim().length() > 0	&& filecounterto.trim().length() > 0) {
					//			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
								tempSQL += " AND DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.FILECOUNTERDES,A.FILECOUNTERORG) BETWEEN "+filecounterfrom+" AND "+filecounterto+" ";
							}else if (filecounterfrom.trim().length() > 0	&& filecounterto.trim().length() <= 0) {
								tempSQL += " AND DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.FILECOUNTERDES,A.FILECOUNTERORG) = "+filecounterfrom+" ";
							}
							if (registrationdatefrom.trim().length() > 0 && registrationdateto.trim().length() > 0) {
					//			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
								tempSQL += " AND TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+registrationdatefrom+"','DD/MM/YYYY') AND TO_DATE('"+registrationdateto+"','DD/MM/YYYY')";
							}else if (registrationdatefrom.trim().length() > 0 && registrationdateto.trim().length() <= 0) {
								tempSQL += " AND DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG) = TO_DATE('"+registrationdatefrom+"','DD/MM/YYYY') ";
							}
							tempSQL += rplyType.trim().length()>0? " AND REPLYTYPE = '"+rplyType+"'": "";
							strSQL += tempSQL;
							strSQL += subQry;
							strSQL += " ORDER BY A.FMID";
							return (new CommonDAO()).getSQLResult(strSQL,19);*/
							
							
							String strSQL = " SELECT DISTINCT A.FMID," +
									" DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
									" TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
									" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Conf.' END REFERENCETYPE," +
									" A.FILENO," +
									" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
									" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
									" A.SUBJECT," +
									" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
									" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
									" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
									" REMARKS," +
									" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
									" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY')," +
									" B.MARKINGREMARK," +
									" REPLYTYPE," +
									" REASON, REFERENCETYPE,"+Qreply+" REPLY, EOFFICENO" +
									" FROM TRNFILEHDR A, TRNFILEMARKING B" +
									" WHERE 1=1 AND A.FMID = B.FMID(+)" ;
							params.add(roleId);
							params.add(roleId);
					//				"AND  DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.FILECOUNTERDES,A.FILECOUNTERORG) BETWEEN 1 AND 5" +
					//				"AND  DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG) BETWEEN TO_DATE('01/07/2011','DD/MM/YYYY') AND TO_DATE('25/04/2012','DD/MM/YYYY')" +
					//				"ORDER BY REGISTRATIONNO; ";

								String tempSQL = "";
								if (filecounterfrom.trim().length() > 0	&& filecounterto.trim().length() > 0) {
						//			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
									tempSQL += " AND DECODE(A.DESTINATIONMARKING,?,A.FILECOUNTERDES,A.FILECOUNTERORG) BETWEEN ? AND ? ";
									params.add(roleId);
									params.add(filecounterfrom);
									params.add(filecounterto);
								}else if (filecounterfrom.trim().length() > 0	&& filecounterto.trim().length() <= 0) {
									tempSQL += " AND DECODE(A.DESTINATIONMARKING,?,A.FILECOUNTERDES,A.FILECOUNTERORG) = ? ";
									params.add(roleId);
									params.add(filecounterfrom);
								}
								if (registrationdatefrom.trim().length() > 0 && registrationdateto.trim().length() > 0) {
						//			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
									tempSQL += " AND TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')";
									params.add(roleId);
									params.add(registrationdatefrom);
									params.add(registrationdateto);
								
								}else if (registrationdatefrom.trim().length() > 0 && registrationdateto.trim().length() <= 0) {
									tempSQL += " AND DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG) = TO_DATE(?,'DD/MM/YYYY') ";
									params.add(roleId);
									params.add(registrationdatefrom);
								}
								//tempSQL += rplyType.trim().length()>0? " AND REPLYTYPE = '"+rplyType+"'": "";
								if(rplyType.trim().length()>0){
									tempSQL += " AND REPLYTYPE = ?";
									params.add(rplyType);
								}
								strSQL += tempSQL;
								strSQL += subQry;
								strSQL += " ORDER BY A.FMID";
								
								ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
								DBConnection dbcon = new DBConnection();
								try {
							
									dbcon.openConnection();
									ps = dbcon.setPrepStatement(strSQL);
									for(int i=1;i<params.size();i++){
										ps.setString(i, params.get(i));
									}
									
								
									
									arr = (new CommonDAO()).getSQLResultPreparedStmtRep2(ps,20,dbcon);
								} catch (Exception e) {
									log.error("Error"+e.getMessage());
									//log.fatal(e, e);
								} finally {
									dbcon.closeConnection();
								}
								return arr;

								
								//return (new CommonDAO()).getSQLResult(strSQL,19);
								
								
							
			}
	
	/*public ArrayList<CommonBean> getReportSerialNoFromTo_2(String dateFrom, String dateTo, String rplyType, String fileType, String iMarkingTo, String fStatus1, String fStatus2, String roleId, String isConf, String isReply,String conf,String mfinal,String reply) {
		String subQry = "";
		String Qreply = "";
		//subQry=isConf.equalsIgnoreCase("0")? " AND REFERENCETYPE NOT IN ('C')": "";
		if(isConf.equalsIgnoreCase("1")){
			subQry+=conf.equalsIgnoreCase("0")? "AND REFERENCETYPE NOT IN ('C')": "";
		}else{
			subQry+= "AND REFERENCETYPE NOT IN ('C')";
		}
		if(isReply.equalsIgnoreCase("1")){
			Qreply=reply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=A.FMID)";
		}
		else{
			Qreply="''";
		}
		
				String strSQL = " SELECT DISTINCT A.FMID," +
								" DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
								" TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
								" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Conf.' END FILETYPE," +
								" A.FILENO," +
								" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
								" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
								" A.SUBJECT," +
								" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
								" REMARKS," +
								" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
								" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY')," +
								" B.MARKINGREMARK," +
//								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
								" REPLYTYPE," +
								" REASON," +
//								" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = C.MARKINGTO) IMARKTO," +
								" '' IMARKTO," +
								" TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') REGDATE," +
								" REFERENCETYPE,"+Qreply+" REPLY ," +
								"EOFFICENO " +
								" FROM TRNFILEHDR A, TRNFILEMARKING B, TRNFILEINTMARKING C" +
								" WHERE A.FMID = B.FMID(+) AND A.FMID = C.FMID(+) " +
								" AND A.DESTINATIONMARKING = '"+roleId+"'" +
								" AND TO_DATE(TO_CHAR(A.REGISTRATIONDATEDES,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+dateFrom+"','DD/MM/YYYY') AND TO_DATE('"+dateTo+"','DD/MM/YYYY')" ;
				String tempSQL = "";
				tempSQL += fileType.trim().length() > 0? " AND A.REFERENCETYPE = '"+fileType+"'": "";
				tempSQL += iMarkingTo.trim().length() > 0? " AND C.MARKINGTO = '"+iMarkingTo+"'": "";
				tempSQL += mfinal.equalsIgnoreCase("1")? " AND C.MARKINGSEQUENCE=(SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID)": "";
				tempSQL += fStatus1.trim().length() > 0? " AND A.FILESTATUS1 = '"+fStatus1+"'": "";
				tempSQL += fStatus2.trim().length() > 0? " AND A.FILESTATUS2 = '"+fStatus2+"'": "";
				tempSQL += rplyType.trim().length() > 0? " AND A.REPLYTYPE = '"+rplyType+"'": "";
				
				strSQL += tempSQL;
				strSQL += subQry;
//				strSQL += " ORDER BY IMARKTO, REGDATE";
				strSQL += " ORDER BY A.FMID";
				return (new CommonDAO()).getSQLResult(strSQL,22);
	}
*/
	//rishabh code
	public ArrayList<CommonBean> getReportSerialNoFromTo_2(String dateFrom, String dateTo, String rplyType, String fileType, String iMarkingTo, String fStatus1, String fStatus2, String roleId, String isConf, String isReply,String conf,String mfinal,String reply, String markingDateFrom, String markingDateTo) {
		String subQry = "";
		String Qreply = "";
		//subQry=isConf.equalsIgnoreCase("0")? " AND REFERENCETYPE NOT IN ('C')": "";
		if(isConf.equalsIgnoreCase("1")){
			subQry+=conf.equalsIgnoreCase("0")? "AND REFERENCETYPE NOT IN ('C')": "";
		}else{
			subQry+= "AND REFERENCETYPE NOT IN ('C')";
		}
		if(isReply.equalsIgnoreCase("1")){
			Qreply=reply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=A.FMID)";
		}
		else{
			Qreply="''";
		}
		
		ArrayList<TrnReference> arrList = new ArrayList<TrnReference>();
		PreparedStatement ps = null;
		ArrayList<String> params = new ArrayList<String>();
		params.add("");
		
		/*		String strSQL = " SELECT DISTINCT A.FMID," +
								" DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
								" TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
								" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Conf.' END FILETYPE," +
								" A.FILENO," +
								" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
								" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
								" A.SUBJECT," +
								" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
								" REMARKS," +
								" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
								" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY')," +
								" B.MARKINGREMARK," +
//								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
								" REPLYTYPE," +
								" REASON," +
//								" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = C.MARKINGTO) IMARKTO," +
								" '' IMARKTO," +
								" TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') REGDATE," +
								" REFERENCETYPE,"+Qreply+" REPLY ," +
								"EOFFICENO " +
								" FROM TRNFILEHDR A, TRNFILEMARKING B, TRNFILEINTMARKING C" +
								" WHERE A.FMID = B.FMID(+) AND A.FMID = C.FMID(+) " +
								" AND A.DESTINATIONMARKING = '"+roleId+"'" +
								" AND TO_DATE(TO_CHAR(A.REGISTRATIONDATEDES,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+dateFrom+"','DD/MM/YYYY') AND TO_DATE('"+dateTo+"','DD/MM/YYYY')" ;
				String tempSQL = "";
				tempSQL += fileType.trim().length() > 0? " AND A.REFERENCETYPE = '"+fileType+"'": "";
				tempSQL += iMarkingTo.trim().length() > 0? " AND C.MARKINGTO = '"+iMarkingTo+"'": "";
				tempSQL += mfinal.equalsIgnoreCase("1")? " AND C.MARKINGSEQUENCE=(SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID)": "";
				tempSQL += fStatus1.trim().length() > 0? " AND A.FILESTATUS1 = '"+fStatus1+"'": "";
				tempSQL += fStatus2.trim().length() > 0? " AND A.FILESTATUS2 = '"+fStatus2+"'": "";
				tempSQL += rplyType.trim().length() > 0? " AND A.REPLYTYPE = '"+rplyType+"'": "";
				
				strSQL += tempSQL;
				strSQL += subQry;
//				strSQL += " ORDER BY IMARKTO, REGDATE";
				strSQL += " ORDER BY A.FMID";
				return (new CommonDAO()).getSQLResult(strSQL,22);*/
				
				
				String strSQL = " SELECT DISTINCT A.FMID," +
						" DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
						" TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
						" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Conf.' END FILETYPE," +
						" A.FILENO," +
						" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
						" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
						" A.SUBJECT," +
						" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
						" REMARKS," +
						" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
						" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY')," +
						" B.MARKINGREMARK," +
//						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
						" REPLYTYPE," +
						" REASON," +
//						" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = C.MARKINGTO) IMARKTO," +
						" '' IMARKTO," +
						" TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') REGDATE," +
						" REFERENCETYPE,"+Qreply+" REPLY ," +
						" EOFFICENO,  NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.FMID AND M.TYPE='F'),'0') ISATTACHMENT, " +
						" '<img border=\"0\" alt=\"Image Gallery\" src=\"images/stn.gif\"'||' onclick=popupGallery('||A.FMID||','||A.FMID||')>'  " +
						" FROM TRNFILEHDR A, TRNFILEMARKING B, TRNFILEINTMARKING C" +
						" WHERE A.FMID = B.FMID(+) AND A.FMID = C.FMID(+) " +
						" AND A.DESTINATIONMARKING = ?" +
						" AND TO_DATE(TO_CHAR(A.REGISTRATIONDATEDES,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" ;
				params.add(roleId);
				params.add(roleId);
				params.add(roleId);
				params.add(roleId);
				params.add(dateFrom);
				params.add(dateTo);
				
		String tempSQL = "";
		//tempSQL += fileType.trim().length() > 0? " AND A.REFERENCETYPE = '"+fileType+"'": "";
		if(fileType.trim().length() > 0){
			tempSQL +=" AND A.REFERENCETYPE = ?";
			params.add(fileType);
		}
		//tempSQL += iMarkingTo.trim().length() > 0? " AND C.MARKINGTO = '"+iMarkingTo+"'": "";
		if(iMarkingTo.trim().length() > 0){
			tempSQL +=" AND C.MARKINGTO = ?";
			params.add(iMarkingTo);
		}
		tempSQL += mfinal.equalsIgnoreCase("1")? " AND C.MARKINGSEQUENCE=(SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID)": "";
		//tempSQL += fStatus1.trim().length() > 0? " AND A.FILESTATUS1 = '"+fStatus1+"'": "";
		if(fStatus1.trim().length() > 0){
			tempSQL +=" AND A.FILESTATUS1 = ?";
			params.add(fStatus1);
		}
		//tempSQL += fStatus2.trim().length() > 0? " AND A.FILESTATUS2 = '"+fStatus2+"'": "";
		if(fStatus2.trim().length() > 0){
			tempSQL +=" AND A.FILESTATUS2 = ?";
			params.add(fStatus2);
		}
		if(markingDateFrom.trim().length() > 0 && markingDateTo.trim().length() > 0){
			tempSQL +=" AND trunc(B.MARKINGDATE) BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') ";
			params.add(markingDateFrom);
			params.add(markingDateTo);
		}
		//tempSQL += rplyType.trim().length() > 0? " AND A.REPLYTYPE = '"+rplyType+"'": "";
		if(rplyType.trim().length() > 0){
			tempSQL +=" AND A.REPLYTYPE = ?";
			params.add(rplyType);
		}
		
		strSQL += tempSQL;
		strSQL += subQry;
//		strSQL += " ORDER BY IMARKTO, REGDATE";
		strSQL += " ORDER BY A.FMID";
		
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		DBConnection dbcon = new DBConnection();
		try {
	
			dbcon.openConnection();
			ps = dbcon.setPrepStatement(strSQL);
			for(int i=1;i<params.size();i++){
				ps.setString(i, params.get(i));
			}
			
		
			
			arr = (new CommonDAO()).getSQLResultPreparedStmtRep2(ps,24,dbcon);
		} catch (Exception e) {
			log.error("Error"+e.getMessage());
			//log.fatal(e, e);
		} finally {
			dbcon.closeConnection();
		}
		return arr;

		
		//return (new CommonDAO()).getSQLResult(strSQL,22);
	}
	
	public ArrayList<CommonBean> getReportPeonBook(String datefrom, String dateto, String roleId, String usermarking) {
		ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
		ArrayList<String> params = new ArrayList<String>();
		params.add("");
		params.add(roleId);
		params.add(roleId);
		params.add(roleId);
		params.add(datefrom);
		params.add(dateto);
		String tempCond="";
		tempCond += usermarking.length() > 0? " AND B.STATUSCHANGEUSER = ? ": "";
		if(usermarking.length() > 0){
			params.add(usermarking);
		}
		DBConnection con =  new DBConnection();
		String strSQL = " SELECT DECODE(A.DESTINATIONMARKING, ? ,A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGDATE," +
						" DECODE(A.DESTINATIONMARKING, ? ,A.REGISTRATIONNODES,A.REGISTRATIONNOORG)||'  -  '||TO_CHAR(DECODE(A.DESTINATIONMARKING, ? ,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY')||'  -  '||A.FILENO||'  -  '||A.NOL OUTPUT" +
						" FROM TRNFILEHDR A, TRNFILEMARKING B" +
						" WHERE A.FMID = B.FMId AND B.MARKINGSEQUENCE = 1" +
						" AND A.FILESTATUS1 IN(5,8,16)" +
						" AND TO_DATE(TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+tempCond+" ORDER BY REGDATE";
		//System.out.println("StrSQL "+strSQL);
		try {
			
			//	log.debug(strSQL);
				con.openConnection();
				PreparedStatement ps = null;
				ps = con.setPrepStatement(strSQL);
				/*ps.setString(1, roleId);
				ps.setString(2, roleId);
				ps.setString(3, roleId);
				ps.setString(4, datefrom);
				ps.setString(5, dateto);*/
				for(int i=1;i<params.size();i++){
					ps.setString(i, params.get(i));
					
				}
				ResultSet rs =  ps.executeQuery();
				while (rs.next()) {
					CommonBean bn =  new CommonBean();
					bn.setField1(CommonDAO.handleNull(rs.getString(1)));
					bn.setField2(CommonDAO.handleNull(rs.getString(2)));
					
					arrList.add(bn);
				}
				rs.close();
				
				
			} catch(SQLException e)		{
				//log.fatal(e,e);
			}finally{
				
				con.closeConnection();
			}
			
			return arrList;
		//return (new CommonDAO()).getSQLResult(strSQL,2);
	}

	public ArrayList<CommonBean> getReportPeonBookTotal(String datefrom, String dateto, String roleId, String usermarking) {
		ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
		ArrayList<String> params = new ArrayList<String>();
		params.add("");
		
		params.add(datefrom);
		params.add(dateto);
		String tempCond="";
		tempCond += usermarking.length() > 0? " AND B.STATUSCHANGEUSER = ? ": "";
		if(usermarking.length() > 0){
			params.add(usermarking);
		}
		DBConnection con =  new DBConnection();
		
		String strSQL = " SELECT NVL(SUM(A.NOL),0) TOTAL" +
						" FROM TRNFILEHDR A, TRNFILEMARKING B" +
						" WHERE A.FMID = B.FMID AND B.MARKINGSEQUENCE = 1" +
						" AND A.FILESTATUS1 IN(5,8,16)" + 
						" AND TO_DATE(TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+tempCond;
		
		try {
			
			//	log.debug(strSQL);
				con.openConnection();
				PreparedStatement ps = null;
				ps = con.setPrepStatement(strSQL);
				for(int i=1;i<params.size();i++){
					ps.setString(i, params.get(i));
					
				}
				ResultSet rs =  ps.executeQuery();
				while (rs.next()) {
					CommonBean bn =  new CommonBean();
					bn.setField1(CommonDAO.handleNull(rs.getString(1)));
					
					arrList.add(bn);
				}
				rs.close();
				
				
			} catch(SQLException e)		{
				//log.fatal(e,e);
			}finally{
				
				con.closeConnection();
			}
			
			return arrList;
		//return (new CommonDAO()).getSQLResult(strSQL,1);
	}

	public String generateSummaryReport(String serverpath, String condYEARCODEFINAL, String condFILETYPEFINAL, String condPENDINGFINAL, String condDISPOSEDFINAL) {
		// TODO Auto-generated method stub
		int ran = (int) (1000000 * Math.random());
		String Directory = serverpath;
		//String Directory = "D://";

		String FilePath = Directory + File.separator + "WPMSExcel"+ ran + ".xls";
		try {
			
			
			MastersReportBean bean = null;
			
			String headername = "Case Wise Reports";

			HSSFWorkbook wb = new HSSFWorkbook();

			HSSFCellStyle style_cell = wb.createCellStyle();
			
			style_cell.setBorderBottom(BorderStyle.THIN);
			style_cell.setBottomBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderLeft(BorderStyle.THIN);
			style_cell.setLeftBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderRight(BorderStyle.THIN);
			style_cell.setRightBorderColor(HSSFColor.BLACK.index);
			style_cell.setBorderTop(BorderStyle.THIN);
			style_cell.setTopBorderColor(HSSFColor.BLACK.index);
			style_cell.setAlignment(HorizontalAlignment.CENTER);
			style_cell.setWrapText(true);
			HSSFCellStyle style_cell_num = wb.createCellStyle();
			style_cell_num.setAlignment(HorizontalAlignment.RIGHT);
			style_cell_num.setBorderBottom(BorderStyle.THIN);
			style_cell_num.setBottomBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setBorderLeft(BorderStyle.THIN);
			style_cell_num.setLeftBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setBorderRight(BorderStyle.THIN);
			style_cell_num.setRightBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setBorderTop(BorderStyle.THIN);
			style_cell_num.setTopBorderColor(HSSFColor.BLACK.index);
			style_cell_num.setWrapText(true);
			HSSFCellStyle style_pageHeader = wb.createCellStyle();
			style_pageHeader.setAlignment(HorizontalAlignment.CENTER);

			HSSFCellStyle style_header = wb.createCellStyle();
			style_header
					.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style_header.setAlignment(HorizontalAlignment.CENTER);
			style_header.setBorderBottom(BorderStyle.THIN);
			style_header.setBottomBorderColor(HSSFColor.BLACK.index);
			style_header.setBorderLeft(BorderStyle.THIN);
			style_header.setLeftBorderColor(HSSFColor.BLACK.index);
			style_header.setBorderRight(BorderStyle.THIN);
			style_header.setRightBorderColor(HSSFColor.BLACK.index);
			style_header.setBorderTop(BorderStyle.THIN);
			style_header.setTopBorderColor(HSSFColor.BLACK.index);
			style_header.setWrapText(true);

			// Fonts for PageHeader and CellHeaders
			HSSFFont font_pageHeader = wb.createFont();
			font_pageHeader.setFontName("Arial");
			font_pageHeader.setFontHeightInPoints((short) 10);
			font_pageHeader.setBold(true);
			font_pageHeader.setColor(HSSFColor.BLACK.index);

			HSSFFont font_Header = wb.createFont();
			font_Header.setFontHeightInPoints((short) 10);
			font_Header.setBold(true);
			
			HSSFFont font_cell = wb.createFont();
			font_cell.setFontHeightInPoints((short) 10);
			
			//for (int index = 0; index < Refclass.length; index++) {
				ArrayList<CommonBean> cm =new ArrayList();	
				
				Date date = new Date();
			    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(date);
			    
				
				headername = "Files (Approval + Confidential) ";
				String[] arrh = {"Year","Files Recieved","Files Disposed","Files Pending"};
				
				
				HSSFSheet sheet = wb.createSheet("StateSummary");
				sheet.setColumnWidth(0, 5000);
		        sheet.setColumnWidth(1, 5000);
		        sheet.setColumnWidth(2, 5000);
		        sheet.setColumnWidth(3, 5000);
		       ;
		       
				HSSFPrintSetup ps = sheet.getPrintSetup();
				sheet.setAutobreaks(true);
				sheet.getPrintSetup().setLandscape(true);
				ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
				ps.setFitWidth((short)1);
				ps.setFitHeight((short)0);
				sheet.setHorizontallyCenter(true);
				
				cm = FileReportData(condYEARCODEFINAL,condFILETYPEFINAL,condPENDINGFINAL,condDISPOSEDFINAL);
				
				HSSFHeader header = sheet.getHeader();
				
				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);

				header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic")
						+ HSSFHeader.fontSize((short) 16) + headername);
				
				
				int rnum = 0;				
				
				HSSFRichTextString string;
				
				HSSFRow row = sheet.createRow(rnum++);
				for (int i = 0; i < arrh.length; i++) {
					string = new HSSFRichTextString(arrh[i].toString()
							.replaceAll("&deg;", "�"));
					string.applyFont(font_Header);
					createCell(wb, row, (short) (i), style_header, string);
				

				}
				
				
				//int sno = 1;
				int sumRecieved = 0;
				int sumDisposed = 0;
				int sumPending = 0;
			
				
				
				for (int i = 0; i < cm.size(); i++) {
	
						row = sheet.createRow(rnum++);
						
						string = new HSSFRichTextString(cm.get(i).getField1().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (0),
								style_cell, string);
						string = new HSSFRichTextString(cm.get(i).getField2().toString());
						string.applyFont(font_cell);
						
						createCell(wb, row, (short) (1),
								style_cell, string);
						
						string = new HSSFRichTextString(cm.get(i).getField3().toString());
						string.applyFont(font_cell);
						//sumY1Total =sumY1Total+ (Integer.parseInt(cm.get(i).getField3()));
						//sumY1Dis =sumY1Dis+ (Integer.parseInt(cm.get(i).getField4()));
						createCell(wb, row, (short) (2),
								style_cell, string);
						
						string = new HSSFRichTextString(cm.get(i).getField4().toString());
						string.applyFont(font_cell);
						createCell(wb, row, (short) (3),
								style_cell, string);
						sumPending = sumPending+ Integer.parseInt(cm.get(i).getField3());
						sumDisposed = sumDisposed + Integer.parseInt(cm.get(i).getField3());
						sumRecieved = sumRecieved + Integer.parseInt(cm.get(i).getField2());
						
					
					//sno++;
				}
				
				
				HSSFFooter footer = sheet.getFooter();
				
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				footer.setRight("As on " + df.format(new Date()));
				footer.setCenter("Page " + HSSFFooter.page() + " of "
						+ HSSFFooter.numPages());
				
			

			FileOutputStream fileOut = new FileOutputStream(FilePath);

			wb.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FilePath;
	}
	
	private ArrayList<CommonBean> FileReportData(String condYEARCODEFINAL, String condFILETYPEFINAL, String condPENDINGFINAL, String condDISPOSEDFINAL) {
		// TODO Auto-generated method stub
		
		String strSQL = "select fileyear YEAR,sum(total) RECEIVED,sum(disposed) DISPOSED, sum(pending) PENDING from "
				+ "( "
				+ "SELECT  substr(trim(TRNFILEHDR.REGISTRATIONNODES),length(TRNFILEHDR.REGISTRATIONNODES)-3) fileyear, "
				+ "1 total, DECODE(TRNFILEHDR.FILESTATUS1,"+condDISPOSEDFINAL+"0) disposed, "
				+ "DECODE(TRNFILEHDR.FILESTATUS1,"+condPENDINGFINAL+"0) pending from "
				+ "trnfilehdr "
				+ "where TO_DATE(TO_CHAR(TRNFILEHDR.REGISTRATIONDATEDES,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('19/11/2014','DD/MM/YYYY') AND sysdate AND "+condYEARCODEFINAL
				+ condFILETYPEFINAL 
				+ ") group by fileyear  "
				+ "union all "
				+ "select 'TOTAL' YEAR,sum(tot) RECEIVED,sum(disp) DISPOSED,sum(pend) PENDING from "
				+ "( "
				+ "select fileyear,sum(total) tot,sum(disposed) disp, sum(pending) pend from "
				+ "( "
				+ "SELECT  substr(trim(TRNFILEHDR.REGISTRATIONNODES),length(TRNFILEHDR.REGISTRATIONNODES)-3) fileyear, "
				+ "1 total, DECODE(TRNFILEHDR.FILESTATUS1,"+condDISPOSEDFINAL+"0) disposed, "
				+ "DECODE(TRNFILEHDR.FILESTATUS1,"+condPENDINGFINAL+"0) pending from "
				+ "trnfilehdr "
				+ "where TO_DATE(TO_CHAR(TRNFILEHDR.REGISTRATIONDATEDES,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('19/11/2014','DD/MM/YYYY') AND sysdate AND "+condYEARCODEFINAL
				+ condFILETYPEFINAL 
				+ ") group by fileyear order by fileyear) "
				+ "order by 1";
	//	System.out.println( "strSQL============================"+strSQL);
		return (new CommonDAO()).getSQLResult(strSQL, 4);
	}

	private static void createCell(HSSFWorkbook wb, HSSFRow row, short column,
			HSSFCellStyle style, HSSFRichTextString value) {
		HSSFCell cell = row.createCell(column);
		cell.setCellValue(value);
		cell.setCellStyle(style);
	}
}