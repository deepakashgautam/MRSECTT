package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.TrnReference;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.util.StringFormat;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class ModifyAckDAO {
	static Logger log = LogManager.getLogger(ReportRefNoDAO.class);
	
	public ArrayList<TrnReference> getSearchSub(String fdate) {
		DBConnection dbCon = new DBConnection();
		String condVar = "";
		String refNo = "";
		String inDate = "";
		ArrayList<TrnReference> arr = new ArrayList<TrnReference>();
		if (fdate.trim().length() > 0) {
			String[] arrRefNo = fdate.split("   ");
			refNo = arrRefNo[0];
			inDate = arrRefNo[1];
		}
		try {
			condVar += inDate.length()>0?"AND A.INCOMINGDATE = TO_DATE('"+inDate+"','DD/MM/YYYY')":"";
			
			String strSQL =	 " SELECT REFERENCENAME, VIPSTATUS, STATECODE, SUBJECT," +
							 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," +
							 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," +
							 " STATUSREMARK," +
							 " ACKBY," +
							 " TO_CHAR(ACKDATE,'DD/MM/YYYY') ACKDATE," +
							 " MARKINGTO," +
							 " TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY') MARKINGDATE, TO_CHAR(TARGETDATE,'DD/MM/YYYY') TARGETDATE" +
							 " FROM TRNREFERENCE A, TRNMARKING B"+
							 " WHERE A.REFID = B.REFID AND B.MARKINGSEQUENCE = 1 AND UPPER(A.REFNO) = UPPER('"+refNo+"') "+condVar+""+
							 " ORDER BY A.REFID ";
			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				TrnReference bn = new TrnReference();
				bn.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
				bn.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
				bn.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
				bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
				bn.setSTATUSREMARK(StringFormat.nullString(rs.getString("STATUSREMARK")));
				bn.setACKBY(StringFormat.nullString(rs.getString("ACKBY")));
				bn.setACKDATE(StringFormat.nullString(rs.getString("ACKDATE")));
				bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
				bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
				bn.setTARGETDATE(StringFormat.nullString(rs.getString("TARGETDATE")));
				arr.add(bn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//result = "0~" + e.getMessage();
			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return arr;
	}

	public String updateData(String ackBy, String ackDate, String markingTo, String markinOn, String fdate) {
		DBConnection dbCon = new DBConnection();
	//	String condVar = "";
		String result = "";
		String refNo = "";
		String inDate = "";
		if (fdate.trim().length() > 0) {
			String[] arrRefNo = fdate.split("   ");
			refNo = arrRefNo[0];
			inDate = arrRefNo[1];
		}
		try {
	//		condVar += inDate.length()>0?"AND INCOMINGDATE = TO_DATE('"+inDate+"','DD/MM/YYYY')":"";
			dbCon.openConnection();
			
			String str = "SELECT REFID FROM TRNREFERENCE WHERE UPPER(REFNO) = UPPER('"+refNo+"') AND INCOMINGDATE = TO_DATE('"+inDate+"','DD/MM/YYYY')";
			ArrayList<CommonBean> bnn = (new CommonDAO()).getSQLResult(str, 1);
			String refID = bnn.get(0).getField1();
		//	System.out.println("refID : "+ refID);

			str = "SELECT TARGETDAYS FROM TRNMARKING WHERE REFID = '"+refID+"'";
			ArrayList<CommonBean> bnnn = (new CommonDAO()).getSQLResult(str, 1);
			String targetDays = bnnn.get(0).getField1();
			//System.out.println("targetDays : "+ targetDays);
			
			String strSQL =	" UPDATE TRNREFERENCE SET ACKBY = '"+ackBy+"'," +
							" ACKDATE = TO_DATE('"+ackDate+"','DD/MM/YYYY')" +
							" WHERE REFID = '"+refID+"'";
			dbCon.update(strSQL);
			log.debug(strSQL);
			dbCon.commit();
			dbCon.closeConnection();
			dbCon.openConnection();
			strSQL =	" UPDATE TRNMARKING SET MARKINGTO = '"+markingTo+"'," +
						" MARKINGDATE = TO_DATE('"+markinOn+"','DD/MM/YYYY')," +
						" TARGETDATE = TO_DATE('"+markinOn+"','DD/MM/YYYY')+"+targetDays+"" +
						" WHERE REFID = '"+refID+"'";
			dbCon.update(strSQL);
			log.debug(strSQL);
			result = "Record Saved Successfully...";
			dbCon.commit();
		} catch (Exception e) {
			e.printStackTrace();
			result = "Save operation failuare...";
			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return result;
	}

	public String chkRoleName(String roleName) {
		DBConnection dbCon = new DBConnection();
		String result = "";
		try {
			String str = "SELECT ROLEID FROM MSTROLE WHERE UPPER(ROLENAME) = UPPER('"+roleName+"')";
			log.debug(str);
			dbCon.openConnection();
			ResultSet rs = dbCon.select(str);
			if(rs.next())
			{
				result = rs.getString("ROLEID");
			}
			else{
				//System.out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbCon.closeConnection();
		}
		return result;
	}
}