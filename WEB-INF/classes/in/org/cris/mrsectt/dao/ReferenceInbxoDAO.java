package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.TrnMarking;
import in.org.cris.mrsectt.Beans.TrnReference;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.util.StringFormat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class ReferenceInbxoDAO {
	static Logger log = LogManager.getLogger(ReferenceInbxoDAO.class);
	
	public ArrayList<TrnReference> getSearchData(String tenureId, String refNo) {
		
		ArrayList<TrnReference> arrList = new ArrayList<TrnReference>();
		DBConnection con = new DBConnection();
		
		String strSQL = " SELECT A.REFNO, A.REFNO, A.SUBJECTCODE , A.SUBJECT, MARKINGFROM, MARKINGTO, ACTION, ACTIONDATE FROM TRNREFERENCE A WHERE A.REFID IN ( "
				+ "		SELECT DISTINCT REFID FROM TRNMARKING WHERE  (MARKINGFROM = '"
				+ tenureId
				+ "' OR MARKINGTO = '"
				+ tenureId
				+ "' )) AND ";
		// " AND ";
		String tempSQL = "";
		
		if (refNo.length() > 0) {
			
			// code to handle REFNO Search
			String[] arrRefNo = refNo.split("/");
			
			String refClass = arrRefNo[0];
			String refCount = arrRefNo[1];
			
			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
			tempSQL += " UPPER(A.REFCLASS) = UPPER('" + refClass + "') " + " AND A.REFCOUNT = '" + refCount + "'";
		}
		
		// if( incomingDateFrom.trim().length() > 0 && incomingDateTo.trim().length() > 0){
		// tempSQL += (tempSQL.length() > 0)? " AND ": "";
		// tempSQL +=
		// " A.INCOMINGDATE BETWEEN TO_DATE('"+incomingDateFrom+"', 'DD/MM/YYYY') AND TO_DATE('"+incomingDateTo+"', 'DD/MM/YYYY')";
		//			
		// }
		//		
		// if( referenceNameSearch.trim().length() > 0 ){
		// tempSQL += (tempSQL.length() > 0)? " AND ": "";
		// tempSQL += " UPPER(A.REFERENCENAME) LIKE UPPER('%"+referenceNameSearch+"%')";
		//			
		// }
		//		
		// if(subjectSearch.trim().length()> 0 ){
		// tempSQL += (tempSQL.length() > 0)? " AND ": "";
		// tempSQL += " UPPER(B.SUBJECT) LIKE UPPER('%"+subjectSearch+"%')";
		// }
		strSQL += tempSQL;
		strSQL += " ORDER BY A.REFID";
		
		try {
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				TrnReference bn = new TrnReference();
				bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
				bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				
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
	
	public ArrayList<TrnReference> getReferenceInboxSearchData(String roleId, String refNo, String SUBJECTCODE, String subjectSearch,
			String MARKINGFROM, String MARKINGTO) {
		ArrayList<TrnReference> arrList = new ArrayList<TrnReference>();
		DBConnection con = new DBConnection();
		
		String strSQL = " SELECT A.REFID, A.REFNO, A.SUBJECTCODE , A.SUBJECT , " +
				" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = MARKINGFROM) MARKINGFROM, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = MARKINGTO) MARKINGTO," +
				" MARKINGDATE, MARKINGREMARK,ACTION, ACTIONDATE FROM TRNREFERENCE A, TRNMARKING B " +
				" WHERE A.REFID = B.REFID " +
//				" AND (B.MARKINGFROM = '"+tenureId+"' OR B.MARKINGTO = '"+tenureId+"' ) " +
				" AND DECODE(B.ACTION, 'RCD', B.MARKINGTO, B.MARKINGFROM)='"+roleId+"' " +
				" AND " ;

		String tempSQL = "";
		
		if (refNo.length() > 0) {
			
			// code to handle REFNO Search
			String[] arrRefNo = refNo.split("/");
			
			String refClass = arrRefNo[0];
			String refCount = arrRefNo[1];
			
			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
			tempSQL += " UPPER(A.REFCLASS) = UPPER('" + refClass + "') " + " AND A.REFCOUNT = '" + refCount + "'";
		}
		// if( incomingDateFrom.trim().length() > 0 && incomingDateTo.trim().length() > 0){
		// tempSQL += (tempSQL.length() > 0)? " AND ": "";
		// tempSQL +=
		// " A.INCOMINGDATE BETWEEN TO_DATE('"+incomingDateFrom+"', 'DD/MM/YYYY') AND TO_DATE('"+incomingDateTo+"', 'DD/MM/YYYY')";
		//			
		// }
		//		
		// if( referenceNameSearch.trim().length() > 0 ){
		// tempSQL += (tempSQL.length() > 0)? " AND ": "";
		// tempSQL += " UPPER(A.REFERENCENAME) LIKE UPPER('%"+referenceNameSearch+"%')";
		//			
		// }
		//		
		 if(subjectSearch.trim().length()> 0 ){
		 tempSQL += (tempSQL.length() > 0)? " AND ": "";
		 tempSQL += " UPPER(B.SUBJECT) LIKE UPPER('%"+subjectSearch+"%')";
		 }
		strSQL += tempSQL;
		strSQL += " ORDER BY A.REFID, B.MARKINGSEQUENCE";
		
		try {
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				TrnReference bn = new TrnReference();
				bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
				bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));
				bn.getMARKINGBEAN().setMARKINGFROM(StringFormat.nullString(rs.getString("MARKINGFROM")));
				bn.getMARKINGBEAN().setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
				bn.getMARKINGBEAN().setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
				bn.getMARKINGBEAN().setMARKINGREMARK(StringFormat.nullString(rs.getString("MARKINGREMARK")));
				bn.getMARKINGBEAN().setACTION(StringFormat.nullString(rs.getString("ACTION")));
				bn.getMARKINGBEAN().setACTIONDATE(StringFormat.nullString(rs.getString("ACTIONDATE")));
				
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
	public ArrayList<TrnReference> getReferenceSearchData(String roleId, String refNo, String REFNOTO, String INCOMINGDATEFROM,
			String INCOMINGDATETO, String REFERENCENAMESEARCH,String SUBJECTSEARCH,String COMMONSEARCH,String COMMONSEARCHVALUE) {
		ArrayList<TrnReference> arrList = new ArrayList<TrnReference>();
		DBConnection con = new DBConnection();
		
		String strSQL = " SELECT A.REFNO, A.REFNO, A.SUBJECTCODE , A.SUBJECT, MARKINGFROM, MARKINGTO, ACTION, ACTIONDATE FROM TRNREFERENCE A WHERE A.REFID IN ( "
				+ "		SELECT DISTINCT REFID FROM TRNMARKING WHERE  (MARKINGFROM = '"
				+ roleId
				+ "' OR MARKINGTO = '"
				+ roleId
				+ "' )) AND ";
		// " AND ";
		String tempSQL = "";
		
		if (refNo.length() > 0) {
			
			// code to handle REFNO Search
			String[] arrRefNo = refNo.split("/");
			
			String refClass = arrRefNo[0];
			String refCount = arrRefNo[1];
			
			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
			tempSQL += " UPPER(A.REFCLASS) = UPPER('" + refClass + "') " + " AND A.REFCOUNT = '" + refCount + "'";
		}
		// if( incomingDateFrom.trim().length() > 0 && incomingDateTo.trim().length() > 0){
		// tempSQL += (tempSQL.length() > 0)? " AND ": "";
		// tempSQL +=
		// " A.INCOMINGDATE BETWEEN TO_DATE('"+incomingDateFrom+"', 'DD/MM/YYYY') AND TO_DATE('"+incomingDateTo+"', 'DD/MM/YYYY')";
		//			
		// }
		//		
		// if( referenceNameSearch.trim().length() > 0 ){
		// tempSQL += (tempSQL.length() > 0)? " AND ": "";
		// tempSQL += " UPPER(A.REFERENCENAME) LIKE UPPER('%"+referenceNameSearch+"%')";
		//			
		// }
		//		
		// if(subjectSearch.trim().length()> 0 ){
		// tempSQL += (tempSQL.length() > 0)? " AND ": "";
		// tempSQL += " UPPER(B.SUBJECT) LIKE UPPER('%"+subjectSearch+"%')";
		// }
		strSQL += tempSQL;
		strSQL += " ORDER BY A.REFID";
		
		try {
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				TrnReference bn = new TrnReference();
				bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
				bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				
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
	
	
	
	public int setInboxAction(String refId, String action) {
		
		int updatestatus = 0;
		
		DBConnection dbcon = new DBConnection();
		try {
			
			String strSQLMarking = " INSERT INTO TRNMARKING (REFID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO, MARKINGDATE, MARKINGREMARK,"
					+ " TARGETDAYS, TARGETDATE, ACTION, ACTIONDATE)"
					+ " SELECT REFID, MARKINGSEQUENCE +1, MARKINGFROM, MARKINGTO, MARKINGDATE, MARKINGREMARK,"
					+ " TARGETDAYS, TARGETDATE, '" + action + "', SYSDATE" + " FROM TRNMARKING A WHERE REFID = '"
					+ refId + "' " + " AND A. MARKINGSEQUENCE = (SELECT MAX(B.MARKINGSEQUENCE) "
					+ " FROM TRNMARKING B WHERE B.REFID = A.REFID)  ";
			
			log.debug(strSQLMarking);
			dbcon.openConnection();
			updatestatus = dbcon.update(strSQLMarking);
			
		} catch (SQLException e) {
			log.fatal(e, e);
			dbcon.rollback();
		} finally {
			
			dbcon.closeConnection();
		}
		
		return updatestatus;
		
	}
	
	public ArrayList<TrnMarking> setoutBoxAction(String refId, String outBoxMarkingFrom, String outBoxMarkTo, String outboxMarkingRemark,
			String targetDate, String subjectCode, String subject, String action) {
		
		ArrayList<TrnMarking> markingBeanList = new ArrayList<TrnMarking>();
		
		DBConnection dbcon = new DBConnection();
		try {
			
			dbcon.openConnection();
			String outBoxMarkingSequence = getNextMarkingSequence(dbcon, "MARKINGSEQUENCE", "TRNMARKING", refId);
			
			String markingQuery = " INSERT INTO TRNMARKING (REFID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO, MARKINGDATE, MARKINGREMARK, "
					+ " TARGETDAYS, TARGETDATE, ACTION, ACTIONDATE) " + " VALUES (" + " '"
					+ refId
					+ "',"
					+ " '"
					+ outBoxMarkingSequence
					+ "',"
					+ " '"
					+ outBoxMarkingFrom
					+ "',"
					+ " '"
					+ outBoxMarkTo
					+ "',"
					+ " SYSDATE, "
					+ " '"
					+ outboxMarkingRemark
					+ "',"
					+ " '15',"
					+ " TO_DATE('"
					+ targetDate
					+ "','DD/MM/YYYY'),"
					+ "'" + action + "', " + " SYSDATE" + ")";
			
			log.debug(markingQuery);
			dbcon.insert(markingQuery);
			
		} catch (Exception e) {
			log.fatal(e, e);
			dbcon.rollback();
		} finally {
			
			dbcon.closeConnection();
		}
		
		return markingBeanList;
		
	}
	
	public String getNextMarkingSequence(DBConnection con, String colName, String tableName, String refId) {
		String strSQL = "(SELECT NVL(MAX(A." + colName + "), 0)+ 1 FROM " + tableName + " A WHERE REFID = '" + refId + "')";
		log.debug(strSQL);
		return CommonDAO.getStringParam(strSQL, con);
	}
	
	public ArrayList<TrnMarking> getInboxData(String roleId) {
		ArrayList<TrnMarking> markingBeanList = new ArrayList<TrnMarking>();
		
		DBConnection con = new DBConnection();
		try {
			
			String strSQLMarking = " SELECT A.REFID, (SELECT REFNO FROM TRNREFERENCE WHERE REFID = A.REFID) REFNO, MARKINGSEQUENCE,"
					+ " (SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = MARKINGFROM) MARKINGFROM, MARKINGTO, TO_CHAR(MARKINGDATE, 'DD/MM/YYYY HH24:MI') MARKINGDATE,  MARKINGREMARK, TARGETDAYS,"
					+ " TO_CHAR(TARGETDATE, 'DD/MM/YYYY') TARGETDATE, " 
					+ " (SELECT SUBJECTCODE FROM TRNREFERENCE WHERE REFID = A.REFID) SUBJECTCODE, " 
					+ " (SELECT SUBJECT FROM TRNREFERENCE WHERE REFID = A.REFID) SUBJECT," 
					+ " ACTION, TO_CHAR(ACTIONDATE, 'DD/MM/YYYY HH24:MI') ACTIONDATE"
					+ " FROM TRNMARKING A  WHERE A.MARKINGTO= '"
					+ roleId
					+ "' AND A.ACTION IN('FOW', 'RET') AND A.MARKINGSEQUENCE = ( SELECT MAX(X.MARKINGSEQUENCE) FROM TRNMARKING X  WHERE X.REFID= A.REFID) AND A.MARKINGSEQUENCE <>3 ";
			
			log.debug("Ref InBox ** :- "+strSQLMarking);
			con.openConnection();
			ResultSet rsMarking = con.select(strSQLMarking);
			while (rsMarking.next()) {
				TrnMarking markingBean = new TrnMarking();
				
				markingBean.setREFID(StringFormat.nullString(rsMarking.getString("REFID")));
				markingBean.setREFNO(StringFormat.nullString(rsMarking.getString("REFNO")));
				markingBean.setMARKINGSEQUENCE(StringFormat.nullString(rsMarking.getString("MARKINGSEQUENCE")));
				markingBean.setMARKINGFROM(StringFormat.nullString(rsMarking.getString("MARKINGFROM")));
				markingBean.setMARKINGTO(StringFormat.nullString(rsMarking.getString("MARKINGTO")));
				markingBean.setMARKINGDATE(StringFormat.nullString(rsMarking.getString("MARKINGDATE")));
				markingBean.setMARKINGREMARK(StringFormat.nullString(rsMarking.getString("MARKINGREMARK")));
				markingBean.setTARGETDAYS(StringFormat.nullString(rsMarking.getString("TARGETDAYS")));
				markingBean.setTARGETDATE(StringFormat.nullString(rsMarking.getString("TARGETDATE")));
				markingBean.setSUBJECTCODE(StringFormat.nullString(rsMarking.getString("SUBJECTCODE")));
				markingBean.setSUBJECT(StringFormat.nullString(rsMarking.getString("SUBJECT")));
				markingBean.setACTION(StringFormat.nullString(rsMarking.getString("ACTION")));
				markingBean.setACTIONDATE(StringFormat.nullString(rsMarking.getString("ACTIONDATE")));
				
				markingBeanList.add(markingBean);
				
			}
			rsMarking.close();
			
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {
			
			con.closeConnection();
		}
		
		return markingBeanList;
		
	}
	
	public ArrayList<TrnMarking> getOutBoxData(String roleId) {
		ArrayList<TrnMarking> markingOutBeanList = new ArrayList<TrnMarking>();
		
		DBConnection con = new DBConnection();
		try {
			
			String strSQLMarking = " SELECT A.REFID, (SELECT REFNO FROM TRNREFERENCE WHERE REFID = A.REFID) REFNO, MARKINGSEQUENCE,"
					+ " MARKINGFROM, "
					+ " (SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = MARKINGFROM) MARKINGFROMNAME, "
					+ " MARKINGTO, TO_CHAR(MARKINGDATE, 'DD/MM/YYYY HH24:MI') MARKINGDATE,  MARKINGREMARK, TARGETDAYS,"
					+ " TO_CHAR(TARGETDATE, 'DD/MM/YYYY') TARGETDATE," 
					+ " (SELECT SUBJECTCODE FROM TRNREFERENCE WHERE REFID = A.REFID) SUBJECTCODE, " 
					+ " (SELECT SUBJECT FROM TRNREFERENCE WHERE REFID = A.REFID) SUBJECT," 
					+ " ACTION, TO_CHAR(ACTIONDATE, 'DD/MM/YYYY HH24:MI') ACTIONDATE"
					+ " FROM TRNMARKING A  WHERE A.MARKINGTO= '"
					+ roleId
					+ "' AND A.ACTION ='RCD' AND A.MARKINGSEQUENCE = ( SELECT MAX(X.MARKINGSEQUENCE) FROM TRNMARKING X  WHERE X.REFID= A.REFID)";
			
			log.debug("Ref outBox ** :- "+strSQLMarking);
			con.openConnection();
			ResultSet rsMarking = con.select(strSQLMarking);
			while (rsMarking.next()) {
				TrnMarking markingBean = new TrnMarking();
				
				markingBean.setREFID(StringFormat.nullString(rsMarking.getString("REFID")));
				markingBean.setREFNO(StringFormat.nullString(rsMarking.getString("REFNO")));
				markingBean.setMARKINGSEQUENCE(StringFormat.nullString(rsMarking.getString("MARKINGSEQUENCE")));
				markingBean.setMARKINGFROM(StringFormat.nullString(rsMarking.getString("MARKINGFROM")));
				markingBean.setMARKINGFROMNAME(StringFormat.nullString(rsMarking.getString("MARKINGFROMNAME")));
				markingBean.setMARKINGTO(StringFormat.nullString(rsMarking.getString("MARKINGTO")));
				markingBean.setMARKINGDATE(StringFormat.nullString(rsMarking.getString("MARKINGDATE")));
				markingBean.setMARKINGREMARK(StringFormat.nullString(rsMarking.getString("MARKINGREMARK")));
				markingBean.setTARGETDAYS(StringFormat.nullString(rsMarking.getString("TARGETDAYS")));
				markingBean.setTARGETDATE(StringFormat.nullString(rsMarking.getString("TARGETDATE")));
				markingBean.setSUBJECTCODE(StringFormat.nullString(rsMarking.getString("SUBJECTCODE")));
				markingBean.setSUBJECT(StringFormat.nullString(rsMarking.getString("SUBJECT")));
				markingBean.setACTION(StringFormat.nullString(rsMarking.getString("ACTION")));
				markingBean.setACTIONDATE(StringFormat.nullString(rsMarking.getString("ACTIONDATE")));
				
				markingOutBeanList.add(markingBean);
				
			}
			rsMarking.close();
			
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {
			
			con.closeConnection();
		}
		
		return markingOutBeanList;
		
	}
	
}