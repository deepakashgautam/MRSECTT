package in.org.cris.mrsectt.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.TrnFileHdr;
import in.org.cris.mrsectt.Beans.TrnFileIntMarking;
import in.org.cris.mrsectt.Beans.TrnReference;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.util.StringFormat;

public class ModifyHindi_GDAO {
	static Logger log = LogManager.getLogger(ReportRefNoDAO.class);
	
	public ArrayList<TrnFileHdr> getSearchSub(String fdate, String ROLEID) {
		DBConnection dbCon = new DBConnection();
		String condVar = "";
		String refNo = "";
		String inDate = "";
		
		ArrayList<TrnFileHdr> arr = new ArrayList<TrnFileHdr>();
		if (fdate.trim().length() > 0) {
			String[] arrRefNo = fdate.split("   ");
			refNo = arrRefNo[0];
			inDate = arrRefNo[1];
		}
		try {
condVar += inDate.length()>0?"AND TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+ROLEID+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') = '"+inDate+"'":"";
			
			String strSQL =	 " SELECT DECODE(A.DESTINATIONMARKING,'"+ROLEID+"',A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
							 " TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+ROLEID+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
							 " A.FILENO," +
							 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
							 " A.SUBJECT," +
							 " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=A.RECEIVEDFROM) RECEIVEDFROM," +
							 " A.FILESTATUS1," +
							 " A.FILESTATUS2," +
							 " A.REMARKS, A.REPLYTYPE, A.REASON, " +
							 " B.MARKINGTO, TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY') MARKINGDATE, B.MARKINGREMARK, B.MARKINGSEQUENCE" +
							 " FROM TRNFILEHDR A, TRNFILEMARKING B"+
							 " WHERE A.FMID = B.FMID(+) AND DECODE(A.DESTINATIONMARKING,'"+ROLEID+"',A.REGISTRATIONNODES,A.REGISTRATIONNOORG) = '"+refNo+"' "+condVar+"";
//							 " ORDER BY A.REFID ";
			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				TrnFileHdr bn = new TrnFileHdr();
				bn.setREGISTRATIONNO(StringFormat.nullString(rs.getString("REGISTRATIONNO")));
				bn.setREGISTRATIONDATE(StringFormat.nullString(rs.getString("REGISTRATIONDATE")));
				bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
				bn.setDEPARTMENTCODE(StringFormat.nullString(rs.getString("DEPARTMENTCODE")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setRECEIVEDFROM(StringFormat.nullString(rs.getString("RECEIVEDFROM")));
				bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
				bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
				bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
				bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
				bn.setREASON(StringFormat.nullString(rs.getString("REASON")));
				bn.setDMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
				bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
				bn.setDMARKINGREMARKS(StringFormat.nullString(rs.getString("MARKINGREMARK")));
				bn.setDMARKINGSEQUENCE(StringFormat.nullString(rs.getString("MARKINGSEQUENCE")));
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

	public ArrayList<TrnFileIntMarking> getFileIntMarking(String fdate, String ROLEID) {
		DBConnection dbCon = new DBConnection();
		String condVar = "";
		String refNo = "";
		String inDate = "";
		String fmID = "";
		
		ArrayList<TrnFileIntMarking> IntmarkingBeanList = new ArrayList<TrnFileIntMarking>();
		if (fdate.trim().length() > 0) {
			String[] arrRefNo = fdate.split("   ");
			refNo = arrRefNo[0];
			inDate = arrRefNo[1];
		}
		try {
			condVar += inDate.length()>0?"AND TO_CHAR(DECODE(DESTINATIONMARKING,'"+ROLEID+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') = '"+inDate+"'":"";
			String strSQL =	 " SELECT FMID FROM TRNFILEHDR"+
							 " WHERE DECODE(DESTINATIONMARKING,'"+ROLEID+"',REGISTRATIONNODES,REGISTRATIONNOORG) = '"+refNo+"' "+condVar+"";
			dbCon.openConnection();
			//log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			if(rs.next()){	fmID = rs.getString("FMID"); }
			
			strSQL = " SELECT FMID,MARKINGSEQUENCE,FILECOUNTER,MARKINGTO,TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') MARKINGDATE,LOGINID,TO_CHAR(CHANGEDATE, 'DD/MM/YYYY') CHANGEDATE,	 "
				+ " MARKINGFROM,ACTION,ACTIONDATE,MARKINGREMARK FROM TRNFILEINTMARKING WHERE FMID = '"
				+ fmID + "'" + "  ORDER BY MARKINGSEQUENCE ";

			ResultSet rsMarking2 = dbCon.select(strSQL);
			while (rsMarking2.next()) {
				TrnFileIntMarking intmarkingBean = new TrnFileIntMarking();
				intmarkingBean.setMARKINGSEQUENCE(StringFormat.nullString(rsMarking2.getString("MARKINGSEQUENCE")));
				intmarkingBean.setFILECOUNTER(StringFormat.nullString(rsMarking2.getString("FILECOUNTER")));
				intmarkingBean.setMARKINGTO(StringFormat.nullString(rsMarking2.getString("MARKINGTO")));
				intmarkingBean.setMARKINGDATE(StringFormat.nullString(rsMarking2.getString("MARKINGDATE")));
				intmarkingBean.setLOGINID(StringFormat.nullString(rsMarking2.getString("LOGINID")));
				intmarkingBean.setCHANGEDATE(StringFormat.nullString(rsMarking2.getString("CHANGEDATE")));
				intmarkingBean.setMARKINGFROM(StringFormat.nullString(rsMarking2.getString("MARKINGFROM")));
				intmarkingBean.setACTION(StringFormat.nullString(rsMarking2.getString("ACTION")));
				intmarkingBean.setACTIONDATE(StringFormat.nullString(rsMarking2.getString("ACTIONDATE")));
				intmarkingBean.setMARKINGREMARK(StringFormat.nullString(rsMarking2.getString("MARKINGREMARK")));
				IntmarkingBeanList.add(intmarkingBean);
			}
			rsMarking2.close();
		} catch (Exception e) {
			e.printStackTrace();
			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return IntmarkingBeanList;
	}

	public String updateDataFM2(String fileStatus1, String fileStatus2, String remarks, String[] imarkingToArr, String[] imarkingDateArr,String[] ireturnDateArr,
								String[] markingSeq, String incomingDate, String roleID, String loginID, String DMARKINGTO, String DMARKINGDATE, String DREMARKS, String DMARKINGSEQUENCE, String REPLYTYPE, String REASON, String subject) {

		DBConnection dbCon = new DBConnection();
		String result = "";
		String refNo = "";
		String inDate = "";
		if (incomingDate.trim().length() > 0) {
			String[] arrRefNo = incomingDate.split("   ");
			refNo = arrRefNo[0];
			inDate = arrRefNo[1];
		}
		try {
			String str = "SELECT FMID, FILENO FROM TRNFILEHDR WHERE DECODE(DESTINATIONMARKING,'"+roleID+"',REGISTRATIONNODES,REGISTRATIONNOORG) = UPPER('"+refNo+"') AND TO_CHAR(DECODE(DESTINATIONMARKING,'"+roleID+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') =  '"+inDate+"'";
			ArrayList<CommonBean> bnn = (new CommonDAO()).getSQLResult(str, 2);
			String fmID = bnn.get(0).getField1();
			String fileNo = bnn.get(0).getField2();
			dbCon.openConnection();
			
			String strSQL = " UPDATE TRNFILEHDR SET FILESTATUS1 = '"+fileStatus1+"', FILESTATUS2 = '"+fileStatus2+"', " +
							" REMARKS = '"+remarks+"', REPLYTYPE = '"+REPLYTYPE+"', REASON = '"+REASON+"' WHERE FMID =  '"+fmID+"' ";
			log.debug(strSQL);
			dbCon.update(strSQL);

			String strSQLTrnFMarking = "SELECT COUNT(*) FROM TRNFILEMARKING WHERE FMID = '"+fmID+"' AND MARKINGSEQUENCE = '"+DMARKINGSEQUENCE+"' AND MARKINGTYPE = 'D'";
			log.debug(strSQLTrnFMarking);
			ResultSet rsint = dbCon.select(strSQLTrnFMarking);
			int rec = 0;
			if (rsint.next()) {
				rec = rsint.getInt(1);
			}
			if (rec == 1) {
				String markingQuery = " UPDATE TRNFILEMARKING SET MARKINGTO = '"+DMARKINGTO+"', MARKINGDATE = TO_DATE('"+DMARKINGDATE+"','DD/MM/YYYY'), " +
				  					  " MARKINGREMARK = '"+DREMARKS+"' WHERE FMID = '"+fmID+"' AND MARKINGSEQUENCE = '"+DMARKINGSEQUENCE+"' AND MARKINGTYPE = 'D'";
				log.debug(markingQuery);
				dbCon.update(markingQuery);
			}
			if (rec == 0) {
				String markingQuery = " INSERT INTO TRNFILEMARKING (FMID, MARKINGSEQUENCE, MARKINGTYPE, MARKINGFROM, MARKINGTO," +
									  " MARKINGDATE, MARKINGREMARK, SUBJECT, ACTION, ACTIONDATE) " +
									  " VALUES ('"+fmID+"'," +
									  " (SELECT (NVL(MAX(MARKINGSEQUENCE),0)+1) FROM TRNFILEMARKING WHERE FMID = '"+fmID+"')," +
									  " 'D'," +
									  " '"+roleID+"'," +
									  " '"+DMARKINGTO+"'," +
									  " TO_DATE('"+DMARKINGDATE+"','DD/MM/YYYY')," +
									  " '"+DREMARKS+"', " +
									  " '"+subject+"', 'FOW', SYSDATE) " ;
				log.debug(markingQuery);
				dbCon.insert(markingQuery);
			}
			
			for (int i = 1; i < imarkingToArr.length; i++) {
				String rsintMarkingBean = " SELECT COUNT(*) FROM TRNFILEINTMARKING WHERE FMID='"+fmID+"' AND MARKINGSEQUENCE = '"+markingSeq[i]+ "'";
				log.debug(rsintMarkingBean);
				ResultSet rs = dbCon.select(rsintMarkingBean);
				int Rec = 0;
				if (rs.next()) {
					Rec = rs.getInt(1);
				}
				if (Rec == 1) {
					String intmarkingQuery = " UPDATE TRNFILEINTMARKING SET " +
											 " MARKINGTO = '"+imarkingToArr[i]+"', " +
											 " MARKINGDATE = TO_DATE('"+imarkingDateArr[i]+"','DD/MM/YYYY'), " +
											 " CHANGEDATE = TO_DATE('"+ireturnDateArr[i]+"','DD/MM/YYYY') " +
											 " WHERE FMID =  '"+fmID+"' AND MARKINGSEQUENCE = '"+markingSeq[i]+"' ";
					log.debug(intmarkingQuery);
					dbCon.update(intmarkingQuery);
				}
				if (Rec == 0) {
					String intmarkingQuery = " INSERT INTO TRNFILEINTMARKING (FMID,MARKINGSEQUENCE,MARKINGTO,MARKINGDATE,LOGINID," +
							"CHANGEDATE,MARKINGFROM,ACTION,ACTIONDATE) " +
							" VALUES ('"+fmID+"', (SELECT (NVL(MAX(MARKINGSEQUENCE),0)+1) FROM TRNFILEINTMARKING WHERE FMID = '"+fmID+"'), " +
							" '"+imarkingToArr[i]+"', " +
							" TO_DATE('"+imarkingDateArr[i]+"','DD/MM/YYYY'), '"+loginID+"', TO_DATE('"+ireturnDateArr[i]+"','DD/MM/YYYY'), '"+roleID+"', " +
							" 'FOW', SYSDATE) " ;
					log.debug(intmarkingQuery);
					dbCon.insert(intmarkingQuery);
				}
			}
			UpdateReference(dbCon, fmID, refNo, fileNo, fileStatus1, fileStatus2, REPLYTYPE,REASON, inDate, remarks);
			
			result = "Record Saved Successfully...";
			dbCon.commit();
		} catch (Exception e) {
			e.printStackTrace();
			result = "Saved operation failuare...";
			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return result;
	}
	
	public void UpdateReference(DBConnection con, String fmid, String registrationno, String fileno, String filestatus1,
			String filestatus2, String replytype, String reason, String registrationdate,String remarks) {
			/*
			* String strSQL =
			* "UPDATE TRNREFERENCE A SET A.FMID = '"+fmid+"',A.FILECOUNTER = '"
			* +filecounter+"'," +
			* "A.REGISTRATIONNO = '"+registrationno+"',A.FILENO = '"
			* +fileno+"',A.IMARKNGTO = '"
			* +imarkngto+"',A.FILESTATUS1 = '"+filestatus1+"'," +
			* "A.FILESTATUS2 = '"
			* +filestatus2+"',A.REPLYTYPE = '"+replytype+"',A.REASON = '"
			* +reason+"',A.DMARKINGTO = '"+dmarkingto+"'," +
			* "A.SUBJECTCODE = '"+subjectcode
			* +"',A.SUBJECT = '"+subject+"',A.REGISTRATIONDATE = '"
			* +registrationdate+"'" + "  WHERE A.REFID = '"+refid+"'";
			*/
			String strSQL = " UPDATE TRNREFERENCE A SET A.FMID = '"+ fmid+ "', A.REGISTRATIONNO = '"+ registrationno+ "'," +
							" A.FILENO = '"+fileno+"', " +
							" A.IMARKINGTO = (SELECT O.MARKINGTO FROM TRNFILEINTMARKING O WHERE O.FMID='"+ fmid+ "' AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID='"+ fmid+ "'))," +
							" A.FILESTATUS1 = '"+ filestatus1+ "', A.FILESTATUS2 = '"+ filestatus2+ "', A.REPLYTYPE = '"+ replytype+ "'," +
							" A.REASON = '"+reason+"', A.DMARKINGTO = (SELECT O.MARKINGTO FROM TRNFILEMARKING O WHERE O.FMID='"+ fmid+"' AND O.MARKINGSEQUENCE = 1)," +
							" A.DMARKINGDATE = (SELECT O.MARKINGDATE FROM TRNFILEMARKING O WHERE O.FMID='"+fmid+"' AND O.MARKINGSEQUENCE = 1)," +
							" A.REGISTRATIONDATE = TO_DATE('"+ registrationdate+ "','DD/MM/YYYY'), A.STATUSREMARK = '"+ remarks+ "', " +
							" A.IMARKINGDATE = (SELECT TO_DATE(O.MARKINGDATE,'DD/MM/YYYY') FROM TRNFILEINTMARKING O WHERE O.FMID='"+ fmid+ "' AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID='"+ fmid+ "'))," +
							" A.ICHANGEDATE = (SELECT TO_DATE(O.CHANGEDATE,'DD/MM/YYYY') FROM TRNFILEINTMARKING O WHERE O.FMID='"+fmid+"' AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID='"+fmid+"')) " +
							" WHERE A.REFID IN (SELECT O.REFID FROM TRNFILEREF O WHERE O.FMID='"+fmid+"')" +
							" AND NVL(A.FILESTATUS1,'0') NOT IN ('2','3','4','5','8')";
			log.debug(strSQL);
			try {
				con.update(strSQL);
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbCon.closeConnection();
		}
		return result;
	}
	
	/*public ArrayList<TrnReference> getIncomingDateFM(String ROLEID, String REFNO, String isConf) {
		
		ArrayList<TrnReference> arrList = new ArrayList<TrnReference>();
		DBConnection con = new DBConnection();
		String strSQL = " SELECT DECODE(DESTINATIONMARKING,'"+ROLEID+"',REGISTRATIONNODES,REGISTRATIONNOORG) REGISTRATIONNO," +
						" TO_CHAR(DECODE(DESTINATIONMARKING,'"+ROLEID+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE,"+
						" TO_DATE(TO_CHAR(DECODE(DESTINATIONMARKING,'"+ROLEID+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') REGDATE, REFERENCETYPE"+
						" FROM TRNFILEHDR WHERE 1=1 ";
					//	" FROM TRNFILEHDR WHERE DECODE(DESTINATIONMARKING,'"+ROLEID+"',ROLEIDDES,ROLEIDORG) = '"+ROLEID+"' ";
		String tempSQL = "";
		if (REFNO.trim().length() > 0) {
			tempSQL += " AND DECODE(DESTINATIONMARKING,'"+ROLEID+"',FILECOUNTERDES,FILECOUNTERORG) = "+REFNO+"";
		}
		tempSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
		tempSQL += isConf.equalsIgnoreCase("0")? " AND REFERENCETYPE NOT IN ('C')": "";

		strSQL += tempSQL;
		strSQL += " ORDER BY REGDATE DESC";
		log.debug(strSQL);
		try {
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				TrnReference bn = new TrnReference();
				bn.setREFNO(StringFormat.nullString(rs.getString("REGISTRATIONNO")));
				bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("REGISTRATIONDATE")));
				bn.setREFERENCETYPE(StringFormat.nullString(rs.getString("REFERENCETYPE")));
				arrList.add(bn);
			}
			rs.close();
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {
			con.closeConnection();
		}
		return arrList;
	}*/
	
	//rishabh function
public ArrayList<TrnReference> getIncomingDateFM(String ROLEID, String REFNO, String isConf) {
		
		ArrayList<TrnReference> arrList = new ArrayList<TrnReference>();
		PreparedStatement ps = null;
		ArrayList<String> params = new ArrayList<String>();
		params.add("");
		DBConnection con = new DBConnection();
		/*String strSQL = " SELECT DECODE(DESTINATIONMARKING,'"+ROLEID+"',REGISTRATIONNODES,REGISTRATIONNOORG) REGISTRATIONNO," +
						" TO_CHAR(DECODE(DESTINATIONMARKING,'"+ROLEID+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE,"+
						" TO_DATE(TO_CHAR(DECODE(DESTINATIONMARKING,'"+ROLEID+"',REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') REGDATE, REFERENCETYPE"+
						" FROM TRNFILEHDR WHERE 1=1 ";
		*/
		String strSQL = " SELECT DECODE(DESTINATIONMARKING,?,REGISTRATIONNODES,REGISTRATIONNOORG) REGISTRATIONNO," +
				" TO_CHAR(DECODE(DESTINATIONMARKING,?,REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE,"+
				" TO_DATE(TO_CHAR(DECODE(DESTINATIONMARKING,?,REGISTRATIONDATEDES,REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') REGDATE, REFERENCETYPE"+
				" FROM TRNFILEHDR WHERE 1=1 ";
		params.add(ROLEID);
		params.add(ROLEID);
		params.add(ROLEID);
					//	" FROM TRNFILEHDR WHERE DECODE(DESTINATIONMARKING,'"+ROLEID+"',ROLEIDDES,ROLEIDORG) = '"+ROLEID+"' ";
		String tempSQL = "";
		if (REFNO.trim().length() > 0) {
			tempSQL += " AND DECODE(DESTINATIONMARKING,?,FILECOUNTERDES,FILECOUNTERORG) = ?";
			params.add(ROLEID);
			params.add(REFNO);
		}
		tempSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
		tempSQL += isConf.equalsIgnoreCase("0")? " AND REFERENCETYPE NOT IN ('C')": "";

		strSQL += tempSQL;
		strSQL += " ORDER BY REGDATE DESC";
		log.debug(strSQL);
		try {
			con.openConnection();
			ps = con.setPrepStatement(strSQL);
			for(int i=1;i<params.size();i++){
				ps.setString(i, params.get(i));
			}
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TrnReference bn = new TrnReference();
				bn.setREFNO(StringFormat.nullString(rs.getString("REGISTRATIONNO")));
				bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("REGISTRATIONDATE")));
				bn.setREFERENCETYPE(StringFormat.nullString(rs.getString("REFERENCETYPE")));
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