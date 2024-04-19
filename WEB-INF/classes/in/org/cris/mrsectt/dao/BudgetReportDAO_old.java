package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import java.util.ArrayList;

public class BudgetReportDAO_old {
	String strSQL = "";
	String condSQL = "";
	String refNo = "";
	String inDate = "";
	String refNo2 = "";
	String inDate2 = "";
	DBConnection con = new DBConnection();

public ArrayList<CommonBean> getData(String REFNOFROM, String REFNOTO, String INDATE_DRPDWN, String INDATE_DRPDWN2, String LETDATEFROM, String LETDATETO,
String REFNAME, String STATECODE, String SUBJECTCODE, String SUBJECT, String INCDATEFROM, String INCDATETO, String VIPSTATUS, String MRREMARK, String REMARK, String roleID) {
	
		if (INDATE_DRPDWN.trim().length() > 0) {
			String[] arrRefNo = INDATE_DRPDWN.split("   ");
			refNo = arrRefNo[0];
			inDate = arrRefNo[1];
		}
		if (INDATE_DRPDWN2.trim().length() > 0) {
			String[] arrRefNo = INDATE_DRPDWN2.split("   ");
			refNo2 = arrRefNo[0];
			inDate2 = arrRefNo[1];
		}
//condSQL += (refNo.length() > 0 && refNo2.length() > 0) ? " AND A.REFNO BETWEEN UPPER('"+refNo+"') AND UPPER('"+refNo2+"')" : "";
		if (REFNOFROM.trim().length() > 0 && REFNOTO.trim().length() > 0) {
			String[] arrRefNoFrom = REFNOFROM.split("/");
			String[] arrRefNoTo = REFNOTO.split("/");
			
			String refClassFrom = arrRefNoFrom[0];
			String refCountFrom = arrRefNoFrom[1];
			String refCountTo = arrRefNoTo[1];

			condSQL += " AND UPPER(A.REFCLASS) = UPPER('"+refClassFrom+"') AND (A.REFCOUNT BETWEEN '"+refCountFrom+"' AND '"+refCountTo+"')";
		}

			condSQL += (inDate.length() > 0 && inDate2.length() > 0) ? " AND TO_DATE(TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+inDate+"','DD/MM/YYYY') AND TO_DATE('"+inDate2+"','DD/MM/YYYY')" : "";
			
			condSQL += (LETDATEFROM.length() > 0 && LETDATETO.length() < 1) ?" AND TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') = '"+LETDATEFROM+"'": (LETDATEFROM.length() > 0 && LETDATETO.length() > 0)?" AND TO_DATE(TO_CHAR(A.LETTERDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+LETDATEFROM+"','DD/MM/YYYY') AND TO_DATE('"+LETDATETO+"','DD/MM/YYYY')": "";
			
			condSQL += (INCDATEFROM.length() > 0 && INCDATETO.length() < 1) ?" AND TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') = '"+INCDATEFROM+"'": (INCDATEFROM.length() > 0 && INCDATETO.length() > 0)?" AND TO_DATE(TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+INCDATEFROM+"','DD/MM/YYYY') AND TO_DATE('"+INCDATETO+"','DD/MM/YYYY')" : "";
			
			condSQL += (REFNAME.length() > 0 ? " AND UPPER(REFERENCENAME) LIKE ('%"+REFNAME.toUpperCase()+"%')" : "");
			
			condSQL += (STATECODE.length() > 0 ? " AND UPPER(A.STATECODE) = '"+STATECODE.toUpperCase() + "'" : "");
			
			condSQL += (SUBJECTCODE.length() > 0 ? " AND B.SUBJECTCODE = '"+SUBJECTCODE+"'" : "");
			
			condSQL += (SUBJECT.length() > 0 ? " AND UPPER(B.SUBJECT) LIKE ('%"+SUBJECT.toUpperCase()+"%')" : "");
					
			condSQL += (VIPSTATUS.length() > 0 ? " AND A.VIPSTATUS LIKE('%"+VIPSTATUS+"%')" : "");

			condSQL += (MRREMARK.length() > 0 ? " AND B.MRREMARK LIKE('%"+MRREMARK+"%')" : "");

			condSQL += (REMARK.length() > 0 ? " AND B.REMARK LIKE('%"+REMARK+"%')" : "");

			strSQL = " SELECT A.REFNO,B.BUDGETSEQUENCE,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY'),TO_CHAR(A.LETTERDATE,'DD/MM/YYYY')," +
					 " A.STATECODE,A.REFERENCENAME,A.VIPSTATUS,B.SUBJECTCODE, B.SUBJECT,B.MRREMARK,B.REMARK," +
					 " NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID),'0') ISATTACHMENT, A.REFID" +
					 " FROM TRNREFERENCE A, TRNBUDGET B WHERE A.REFID = B.REFID AND A.ROLEID = '" + roleID + "' AND A.ISBUDGET = 'Y' "+condSQL+" ";
		return (new CommonDAO()).getSQLResult(strSQL, 13);
	}



	public ArrayList<CommonBean> getPeonReportData(String ROLEID,String TENUREID,String BOOKDATEFROM, String BOOKDATETO, String CLASS, String isConf) {
		String tmpCond = "";
		tmpCond += isConf.equalsIgnoreCase("0")? " AND B.ISCONF = '0'": " AND B.ISCONF IN ('0','1')";
		tmpCond += CLASS.length() > 0? " AND B.REFCLASS = '"+CLASS+"'": "";
		
		strSQL = " WITH TAB1 AS (SELECT C.ROLENAME ROLENAME,B.REFNO REFNO FROM "
				+ " TRNMARKING A,TRNREFERENCE B,MSTROLE C WHERE A.MARKINGSEQUENCE	='1'"
				+ " AND A.REFID=B.REFID AND A.MARKINGTO=C.ROLEID"
				+ " AND TO_DATE(TO_CHAR(A.MARKINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+BOOKDATEFROM+"','DD/MM/YYYY') AND TO_DATE('"+BOOKDATETO+"','DD/MM/YYYY')"
				+ " AND B.TENUREID='"+TENUREID+"' AND B.ROLEID = '"+ROLEID+"' "+tmpCond+" ORDER BY 1)"
				+ " SELECT ROWNUM,A.ROLENAME,A.REFNO,HEIGHT FROM TAB1 A,"
				+ " (SELECT ROLENAME,CEIL(count(*)/3)*10 HEIGHT"
				+ " FROM TAB1 GROUP BY  ROLENAME) B"
				+ " WHERE A.ROLENAME=B.ROLENAME" + " ORDER BY 2,3";
		return (new CommonDAO()).getSQLResult(strSQL, 4);
	}

	public ArrayList<CommonBean> getPeonReportData(String ROLEID,String TENUREID,String BOOKDATEFROM, String BOOKDATETO, String refnoFrom, String refnoTo, String isConf) {
			String tmpCond = "";
			tmpCond += isConf.equalsIgnoreCase("0")? " AND B.ISCONF = '0'": " AND B.ISCONF IN ('0','1')";
			if (refnoFrom.trim().length() > 0 && refnoTo.trim().length() > 0) {
				String[] arrRefNoFrom = refnoFrom.split("/");
				String[] arrRefNoTo = refnoTo.split("/");
				String refClassFrom = arrRefNoFrom[0];
				String refCountFrom = arrRefNoFrom[1];
				String refCountTo = arrRefNoTo[1];
				tmpCond += " AND UPPER(REFCLASS) = UPPER('"+refClassFrom+"') AND REFCOUNT BETWEEN '"+refCountFrom+"' AND '"+refCountTo+"' ";
			}
			strSQL = " WITH TAB1 AS (SELECT C.ROLENAME ROLENAME,B.REFNO REFNO FROM "
					+ " TRNMARKING A,TRNREFERENCE B,MSTROLE C WHERE A.MARKINGSEQUENCE	='1'"
					+ " AND A.REFID=B.REFID AND A.MARKINGTO=C.ROLEID"
					+ " AND TO_DATE(TO_CHAR(A.MARKINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+BOOKDATEFROM+"','DD/MM/YYYY') AND TO_DATE('"+BOOKDATETO+"','DD/MM/YYYY')"
					+ " AND B.TENUREID='"+TENUREID+"' AND B.ROLEID = '"+ROLEID+"' "+tmpCond+" ORDER BY 1)"
					+ " SELECT ROWNUM,A.ROLENAME,A.REFNO,HEIGHT FROM TAB1 A,"
					+ " (SELECT ROLENAME,CEIL(count(*)/3)*10 HEIGHT"
					+ " FROM TAB1 GROUP BY  ROLENAME) B"
					+ " WHERE A.ROLENAME=B.ROLENAME" + " ORDER BY 2,3";
			return (new CommonDAO()).getSQLResult(strSQL, 4);
		}
	
	public ArrayList<CommonBean> getPeonReportData_IssueD(String ROLEID,String TENUREID,String BOOKDATEFROM,String BOOKDATETO,String CLASS, String isConf) {
		String tmpCond = "";
		tmpCond += isConf.equalsIgnoreCase("0")? " AND B.ISCONF = '0'": " AND B.ISCONF IN ('0','1')";
		tmpCond += CLASS.length() > 0? " AND B.REFCLASS = '"+CLASS+"'": "";
		
		strSQL = " SELECT B.REFNO REFNO,B.REFID FROM TRNREFERENCE B " +
				 " WHERE TO_DATE(TO_CHAR(B.ACKDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+BOOKDATEFROM+"','DD/MM/YYYY') AND TO_DATE('"+BOOKDATETO+"','DD/MM/YYYY')" +
				 " AND B.TENUREID='"+TENUREID+"' AND B.ROLEID = '"+ROLEID+"' "+tmpCond+" ORDER BY 2";
		return (new CommonDAO()).getSQLResult(strSQL, 2);
	}

	public ArrayList<CommonBean> getOutDak(String ROLEID,String TENUREID,String FBOOKDATE,String TBOOKDATE,String CLASS, String isConf) {
		String tmpCond = "";
		tmpCond += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
		tmpCond += CLASS.length() > 0? " AND REFCLASS = '"+CLASS+"'": " AND REFCLASS IN (SELECT DISTINCT REFCLASS FROM MSTCLASS WHERE TENUREID = '"+TENUREID+"' AND INOUT='O')";
		
		strSQL = " SELECT DISTINCT REFNO, REFERENCENAME, REFID, REFCLASS FROM TRNREFERENCE" +
				 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+FBOOKDATE+"','DD/MM/YYYY') AND TO_DATE('"+TBOOKDATE+"','DD/MM/YYYY')" +
				 " AND TENUREID='"+TENUREID+"' AND ROLEID = '"+ROLEID+"' "+tmpCond+" ORDER BY REFCLASS, REFID";
		return (new CommonDAO()).getSQLResult(strSQL, 4);
	}
}

