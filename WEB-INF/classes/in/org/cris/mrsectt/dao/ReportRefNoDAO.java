package in.org.cris.mrsectt.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.TrnReference;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.util.StringFormat;

public class ReportRefNoDAO {
	
	String strSQL = "";
	static Logger log = LogManager.getLogger(ReportRefNoDAO.class);

	/*public ArrayList<CommonBean> getMainBlock(String referenceNo, String incomingDate) {
			String refNo = "";
			String inDate = "";
			if (incomingDate.trim().length() > 0) {
				String[] arrRefNo = incomingDate.split("   ");
				refNo = arrRefNo[0];
				inDate = arrRefNo[1];
			}
			strSQL = " SELECT A.REFCLASS, A.REFNO, (SELECT X.REFNO FROM TRNREFERENCE X WHERE X.REFID = A.LINKREFID) LINKREFID," +
					 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = A.REFCATEGORY) REFCATEGORY," +
					 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '3' AND CODE <> '00' AND CODE = A.LANGUAGE ) LANGUAGE," +
					 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE = A.URGENCY) URGENCY," +
					 " TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY'), TO_CHAR(A.LETTERDATE,'DD/MM/YYYY')," +
					 " DECODE(A.ISBUDGET, 'Y', 'Budget', 'Non-Budget') ISBUDGET," +
					 " A.REFERENCENAME, A.VIPSTATUS, (SELECT STATENAME FROM MSTSTATE WHERE STATECODE = A.STATECODE) STATECODE," +
					 " TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY'), TO_CHAR(B.TARGETDATE,'DD/MM/YYYY'), A.SUBJECT, " +
					 " (SELECT SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTCODE = A.SUBJECTCODE AND SUBJECTTYPE = 'R') SUBJECTCODE," +
					 " ( SELECT MSTROLE.ROLENAME FROM MSTROLE WHERE MSTROLE.ROLEID = B.MARKINGTO) MARKINGTO," +
					 " A.REMARKS," +
					 " ( SELECT MSTROLE.ROLENAME FROM MSTROLE WHERE MSTROLE.ROLEID = A.SIGNEDBY) SIGNEDBY," +
					 " ( SELECT MSTROLE.ROLENAME FROM MSTROLE WHERE MSTROLE.ROLEID = A.ACKBY) ACKBY," + 
					 " TO_CHAR(A.SIGNEDON,'DD/MM/YYYY'), TO_CHAR(A.ACKDATE,'DD/MM/YYYY')," +
					 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '9' AND CODE<>'00' AND CODE = A.RECIEVEDBY) RECIEVEDBY, " +
					 " TARGETDAYS" +
					 " FROM TRNREFERENCE A, TRNMARKING B"+
					 " WHERE A.REFID = B.REFID AND B.MARKINGSEQUENCE = 1 AND UPPER(A.REFNO) = UPPER('"+refNo+"')" +
					 " AND TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') = '"+inDate+"' ORDER BY A.REFID";
			return (new CommonDAO()).getSQLResult(strSQL,24);
			}*/
	
	//soumen function
	public ArrayList<CommonBean> getMainBlock(String referenceNo, String incomingDate) {
		String refNo = "";
		String inDate = "";
		if (incomingDate.trim().length() > 0) {
			String[] arrRefNo = incomingDate.split("   ");
			refNo = arrRefNo[0];
			inDate = arrRefNo[1];
		}
		/*strSQL = " SELECT A.REFCLASS, A.REFNO, (SELECT X.REFNO FROM TRNREFERENCE X WHERE X.REFID = A.LINKREFID) LINKREFID," +
				 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = A.REFCATEGORY) REFCATEGORY," +
				 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '3' AND CODE <> '00' AND CODE = A.LANGUAGE ) LANGUAGE," +
				 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE = A.URGENCY) URGENCY," +
				 " TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY'), TO_CHAR(A.LETTERDATE,'DD/MM/YYYY')," +
				 " DECODE(A.ISBUDGET, 'Y', 'Budget', 'Non-Budget') ISBUDGET," +
				 " A.REFERENCENAME, A.VIPSTATUS, (SELECT STATENAME FROM MSTSTATE WHERE STATECODE = A.STATECODE) STATECODE," +
				 " TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY'), TO_CHAR(B.TARGETDATE,'DD/MM/YYYY'), A.SUBJECT, " +
				 " (SELECT SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTCODE = A.SUBJECTCODE AND SUBJECTTYPE = 'R') SUBJECTCODE," +
				 " ( SELECT MSTROLE.ROLENAME FROM MSTROLE WHERE MSTROLE.ROLEID = B.MARKINGTO) MARKINGTO," +
				 " A.REMARKS," +
				 " ( SELECT MSTROLE.ROLENAME FROM MSTROLE WHERE MSTROLE.ROLEID = A.SIGNEDBY) SIGNEDBY," +
				 " ( SELECT MSTROLE.ROLENAME FROM MSTROLE WHERE MSTROLE.ROLEID = A.ACKBY) ACKBY," + 
				 " TO_CHAR(A.SIGNEDON,'DD/MM/YYYY'), TO_CHAR(A.ACKDATE,'DD/MM/YYYY')," +
				 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '9' AND CODE<>'00' AND CODE = A.RECIEVEDBY) RECIEVEDBY, " +
				 " TARGETDAYS" +
				 " FROM TRNREFERENCE A, TRNMARKING B"+
				 " WHERE A.REFID = B.REFID AND B.MARKINGSEQUENCE = 1 AND UPPER(A.REFNO) = UPPER('"+refNo+"')" +
				 " AND TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') = '"+inDate+"' ORDER BY A.REFID";
		//return (new CommonDAO()).getSQLResult(strSQL,24);*/
		
		strSQL = " SELECT A.REFCLASS, A.REFNO, (SELECT X.REFNO FROM TRNREFERENCE X WHERE X.REFID = A.LINKREFID) LINKREFID," +
				 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = A.REFCATEGORY) REFCATEGORY," +
				 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '3' AND CODE <> '00' AND CODE = A.LANGUAGE ) LANGUAGE," +
				 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE = A.URGENCY) URGENCY," +
				 " TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY'), TO_CHAR(A.LETTERDATE,'DD/MM/YYYY')," +
				 " DECODE(A.ISBUDGET, 'Y', 'Budget', 'Non-Budget') ISBUDGET," +
				 " A.REFERENCENAME, A.VIPSTATUS, (SELECT STATENAME FROM MSTSTATE WHERE STATECODE = A.STATECODE) STATECODE," +
				 " TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY'), TO_CHAR(B.TARGETDATE,'DD/MM/YYYY'), A.SUBJECT, " +
				 " (SELECT SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTCODE = A.SUBJECTCODE AND SUBJECTTYPE = 'R') SUBJECTCODE," +
				 " ( SELECT MSTROLE.ROLENAME FROM MSTROLE WHERE MSTROLE.ROLEID = B.MARKINGTO) MARKINGTO," +
				 " A.REMARKS," +
				 " ( SELECT MSTROLE.ROLENAME FROM MSTROLE WHERE MSTROLE.ROLEID = A.SIGNEDBY) SIGNEDBY," +
				 " ( SELECT MSTROLE.ROLENAME FROM MSTROLE WHERE MSTROLE.ROLEID = A.ACKBY) ACKBY," + 
				 " TO_CHAR(A.SIGNEDON,'DD/MM/YYYY'), TO_CHAR(A.ACKDATE,'DD/MM/YYYY')," +
				 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '9' AND CODE<>'00' AND CODE = A.RECIEVEDBY) RECIEVEDBY, " +
				 " TARGETDAYS, EOFFICEREFNO " +
				 " FROM TRNREFERENCE A, TRNMARKING B"+
				 " WHERE A.REFID = B.REFID AND B.MARKINGSEQUENCE = 1 AND UPPER(A.REFNO) = UPPER(?)" +
				 " AND TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') = ? ORDER BY A.REFID";
		
		DBConnection con = new DBConnection();
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		try {
			con.openConnection();
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ps.setString(1, refNo);
			ps.setString(2, inDate);
			arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 25,con);
			//System.out.println("getMainBlock executed");
			
	       } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
	      finally{
			con.closeConnection();
	     }
return arr;

}


	/*public ArrayList<CommonBean> getReminderBlock(String referenceNo, String incomingDate) {
		String refNo = "";
		String inDate = "";
		if (incomingDate.trim().length() > 0) {
			String[] arrRefNo = incomingDate.split("   ");
			refNo = arrRefNo[0];
			inDate = arrRefNo[1];
		}
		strSQL = " SELECT (SELECT SNAME FROM MSTGEC WHERE CODETYPE = 5 AND CODE = A.REMINDERTYPE) REMINDERTYPE," +
				 " TO_CHAR(A.REMINDERDATE,'DD/MM/YYYY') REMINDERDATE, A.REMINDERREMARK," +
				 " ( SELECT MSTROLE.ROLENAME FROM MSTROLE WHERE MSTROLE.ROLEID = A.SIGNEDBY) SIGNEDBY," +
				 " TO_CHAR(A.SIGNEDON,'DD/MM/YYYY') SIGNEDON," +
				 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = REMINDERTO) REMINDERTO" +
				 " FROM TRNREMINDER A, TRNREFERENCE B" +
				 " WHERE A.REFID = B.REFID AND UPPER(B.REFNO) = UPPER('"+refNo+"')" +
				 " AND TO_CHAR(B.INCOMINGDATE,'DD/MM/YYYY') = '"+inDate+"' ORDER BY A.REFID ";
		return (new CommonDAO()).getSQLResult(strSQL,6);
	}
*/
	
	//soumen function
	public ArrayList<CommonBean> getReminderBlock(String referenceNo, String incomingDate) {
		String refNo = "";
		String inDate = "";
		if (incomingDate.trim().length() > 0) {
			String[] arrRefNo = incomingDate.split("   ");
			refNo = arrRefNo[0];
			inDate = arrRefNo[1];
		}
		/*strSQL = " SELECT (SELECT SNAME FROM MSTGEC WHERE CODETYPE = 5 AND CODE = A.REMINDERTYPE) REMINDERTYPE," +
				 " TO_CHAR(A.REMINDERDATE,'DD/MM/YYYY') REMINDERDATE, A.REMINDERREMARK," +
				 " ( SELECT MSTROLE.ROLENAME FROM MSTROLE WHERE MSTROLE.ROLEID = A.SIGNEDBY) SIGNEDBY," +
				 " TO_CHAR(A.SIGNEDON,'DD/MM/YYYY') SIGNEDON," +
				 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = REMINDERTO) REMINDERTO" +
				 " FROM TRNREMINDER A, TRNREFERENCE B" +
				 " WHERE A.REFID = B.REFID AND UPPER(B.REFNO) = UPPER('"+refNo+"')" +
				 " AND TO_CHAR(B.INCOMINGDATE,'DD/MM/YYYY') = '"+inDate+"' ORDER BY A.REFID ";
		return (new CommonDAO()).getSQLResult(strSQL,6);*/
		
		
		strSQL = " SELECT (SELECT SNAME FROM MSTGEC WHERE CODETYPE = 5 AND CODE = A.REMINDERTYPE) REMINDERTYPE," +
				 " TO_CHAR(A.REMINDERDATE,'DD/MM/YYYY') REMINDERDATE, A.REMINDERREMARK," +
				 " ( SELECT MSTROLE.ROLENAME FROM MSTROLE WHERE MSTROLE.ROLEID = A.SIGNEDBY) SIGNEDBY," +
				 " TO_CHAR(A.SIGNEDON,'DD/MM/YYYY') SIGNEDON," +
				 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = REMINDERTO) REMINDERTO" +
				 " FROM TRNREMINDER A, TRNREFERENCE B" +
				 " WHERE A.REFID = B.REFID AND UPPER(B.REFNO) = UPPER(?)" +
				 " AND TO_CHAR(B.INCOMINGDATE,'DD/MM/YYYY') = ? ORDER BY A.REFID ";
		
		DBConnection con = new DBConnection();
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
	
		try {
			con.openConnection();
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ps.setString(1, refNo);
			ps.setString(2, inDate);	
			arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 6,con);
			//System.out.println("getReminderBlock executed");
			
	       } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
	      finally{
			con.closeConnection();
	     }
      return arr;
	}

	/*public ArrayList<CommonBean> getFinalStatusBlock(String referenceNo, String incomingDate,String isReply) {
		String refNo = "";
		String inDate = "";
		if (incomingDate.trim().length() > 0) {
			String[] arrRefNo = incomingDate.split("   ");
			refNo = arrRefNo[0];
			inDate = arrRefNo[1];
		}
		
		String Qreply = "";
		
		if(isReply.equalsIgnoreCase("1")){
				Qreply=isReply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=TRNREFERENCE.FMID)";
			}
		else{
			
			Qreply="''";
		}
		strSQL = " SELECT REGISTRATIONNO, TO_CHAR(REGISTRATIONDATE, 'DD/MM/YYYY') REGISTRATIONDATE, FILENO," +
				 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNREFERENCE.IMARKINGTO) IMARKINGTO, " +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = TRNREFERENCE.FILESTATUS1) FILESTATUS1," +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = TRNREFERENCE.FILESTATUS2) FILESTATUS2," +
				 " STATUSREMARK," +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00' AND CODE = TRNREFERENCE.REPLYTYPE) REPLYTYPE, REASON, " +
				 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNREFERENCE.DMARKINGTO) DMARKINGTO," +
				 " TO_CHAR(DMARKINGDATE, 'DD/MM/YYYY') DMARKINGDATE,"+Qreply+" REPLY" +
				 " FROM TRNREFERENCE WHERE UPPER(REFNO) = UPPER('"+refNo+"') AND TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') = '"+inDate+"' ";
		return (new CommonDAO()).getSQLResult(strSQL,12);
	}*/
	
	//soumen function
	public ArrayList<CommonBean> getFinalStatusBlock(String referenceNo, String incomingDate,String isReply) {
		String refNo = "";
		String inDate = "";
		if (incomingDate.trim().length() > 0) {
			String[] arrRefNo = incomingDate.split("   ");
			refNo = arrRefNo[0];
			inDate = arrRefNo[1];
		}
		
		String Qreply = "";
		
		if(isReply.equalsIgnoreCase("1")){
				Qreply=isReply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=TRNREFERENCE.FMID)";
			}
		else{
			
			Qreply="''";
		}
		/*strSQL = " SELECT REGISTRATIONNO, TO_CHAR(REGISTRATIONDATE, 'DD/MM/YYYY') REGISTRATIONDATE, FILENO," +
				 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNREFERENCE.IMARKINGTO) IMARKINGTO, " +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = TRNREFERENCE.FILESTATUS1) FILESTATUS1," +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = TRNREFERENCE.FILESTATUS2) FILESTATUS2," +
				 " STATUSREMARK," +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00' AND CODE = TRNREFERENCE.REPLYTYPE) REPLYTYPE, REASON, " +
				 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNREFERENCE.DMARKINGTO) DMARKINGTO," +
				 " TO_CHAR(DMARKINGDATE, 'DD/MM/YYYY') DMARKINGDATE,"+Qreply+" REPLY" +
				 " FROM TRNREFERENCE WHERE UPPER(REFNO) = UPPER('"+refNo+"') AND TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') = '"+inDate+"' ";
		return (new CommonDAO()).getSQLResult(strSQL,12);*/
		
		strSQL = " SELECT REGISTRATIONNO, TO_CHAR(REGISTRATIONDATE, 'DD/MM/YYYY') REGISTRATIONDATE, FILENO," +
				 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNREFERENCE.IMARKINGTO) IMARKINGTO, " +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = TRNREFERENCE.FILESTATUS1) FILESTATUS1," +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = TRNREFERENCE.FILESTATUS2) FILESTATUS2," +
				 " STATUSREMARK," +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00' AND CODE = TRNREFERENCE.REPLYTYPE) REPLYTYPE, REASON, " +
				 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = TRNREFERENCE.DMARKINGTO) DMARKINGTO," +
				 " TO_CHAR(DMARKINGDATE, 'DD/MM/YYYY') DMARKINGDATE,"+Qreply+" REPLY" +
				 " FROM TRNREFERENCE WHERE UPPER(REFNO) = UPPER(?) AND TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') = ? ";
		
		DBConnection con = new DBConnection();
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		try {
			con.openConnection();
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ps.setString(1, refNo);
			ps.setString(2, inDate);	
			arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 12,con);
			//System.out.println("getFinalStatusBlock executed");
			
	       } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      }
	      finally{
			con.closeConnection();
	     }
 return arr;
		
		
	}

	public ArrayList<TrnReference> getIncomingDate(String ROLEID, String REFNO, String isConf) {
		
		ArrayList<TrnReference> arrList = new ArrayList<TrnReference>();
		DBConnection con = new DBConnection();
		String strSQL = " SELECT REFNO, TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') INDATE," +
						" TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE"+
						" FROM TRNREFERENCE WHERE ROLEID = '" + ROLEID + "'  ";
		String tempSQL = "";
		tempSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
		
		if (REFNO.trim().length() > 0) {
			String[] arrRefNoFrom = REFNO.split("/");
			String refClassFrom = arrRefNoFrom[0];
			String refCountFrom = arrRefNoFrom[1];
			tempSQL += " AND UPPER(REFCLASS) = UPPER('"+refClassFrom+"') AND REFCOUNT = '"+refCountFrom+"' ";
		}
		strSQL += tempSQL;
		strSQL += " ORDER BY INDATE DESC";
		log.debug(strSQL);
		try {
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				TrnReference bn = new TrnReference();
				bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
				bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
				arrList.add(bn);
			}
			rs.close();
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {
			con.closeConnection();
		}
		return arrList;
	}
}