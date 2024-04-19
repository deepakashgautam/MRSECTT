package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstClass;
import in.org.cris.mrsectt.Beans.TrnFileHdr;
import in.org.cris.mrsectt.Beans.TrnFileIntMarking;
import in.org.cris.mrsectt.Beans.TrnFileMarking;
import in.org.cris.mrsectt.Beans.TrnFileRef;
import in.org.cris.mrsectt.Beans.TrnFileReply;
import in.org.cris.mrsectt.Beans.TrnMarking;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.util.StringFormat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class TrnFileManagementDAO_uncypted {
	static Logger log = LogManager.getLogger(TrnFileManagementDAO_uncypted.class);

	public ArrayList<String> getCounterStatusNew(String tenureid) {

		DBConnection dbCon = new DBConnection();
		ArrayList<String> arr = new ArrayList<String>();
		PreparedStatement ps = null;
		try {

			String strSQL = "  SELECT A.ROLEID,A.ROLENAME,B.FILECOUNTER FROM MSTROLE A,MSTFILECOUNTER B"
					+ " WHERE B.TENUREID=? AND A.ROLEID=B.ROLEID ORDER BY 1 ";

			dbCon.openConnection();
			ps = dbCon.setPrepStatement(strSQL);
			ps.setString(1, tenureid);
			log.debug(strSQL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				arr.add("&nbsp;&nbsp;" + rs.getString(2) + "&nbsp;&nbsp;");
				arr.add("&nbsp;&nbsp;" + rs.getString(3) + "&nbsp;&nbsp;");
			}

		} catch (Exception e) {
			e.printStackTrace();

			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return arr;
	}

	public ArrayList<TrnFileHdr> getSearchData(String filecounterfrom,
			String filecounterto, String registrationdatefrom,
			String registrationdateto, String filnosearch,
			String subjectSearch, String COMMONSEARCHVALUE, String roleId, String isConf) {

		ArrayList<TrnFileHdr> arrList = new ArrayList<TrnFileHdr>();
		DBConnection con = new DBConnection();
		PreparedStatement ps = null;
		ArrayList<String> params = new ArrayList<String>();
		params.add("");
		params.add(roleId);
		params.add(roleId);
		params.add(roleId);
		params.add(roleId);
		params.add(roleId);
		
		
		String strSQL = " SELECT DISTINCT A.FMID,NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.FMID and STATUSFLAG='1' and TYPE='F'),'0') ISATTACHMENT,DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO,"
				+ "(SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=DECODE(A.DESTINATIONMARKING,?,A.FILESUBJECTCODEDES,A.FILESUBJECTCODEORG)) "
				+ "  FILESUBJECTCODE,A.SUBJECT,"
				+ "TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE,"
				+ "A.FILENO,(SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE,"
				+ "(SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING,"
				+ "(SELECT (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=O.MARKINGTO) FROM TRNFILEINTMARKING O WHERE O.FMID=A.FMID "
				+ " AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID)) IMARKINGTO,"
				+ " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1,"
				+ "(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2,"
				+ "NOL,"
				+ "(SELECT (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=O.MARKINGTO) FROM TRNFILEMARKING O WHERE O.FMID=A.FMID "
				+ " AND O.MARKINGSEQUENCE = 1 AND O.MARKINGTYPE = 'D') DMARKINGTO,"
				+ "(SELECT TO_CHAR(O.MARKINGDATE,'DD/MM/YYYY') FROM TRNFILEMARKING O WHERE O.FMID=A.FMID "
				+ " AND O.MARKINGSEQUENCE = 1 AND O.MARKINGTYPE = 'D') DMARKINGDATE, REFERENCETYPE "
				+ " FROM TRNFILEHDR A  WHERE 1=1 AND DECODE(A.DESTINATIONMARKING,?,A.ROLEIDDES,A.ROLEIDORG) =? AND ";
		String tempSQL = "";

		if (filecounterfrom.trim().length() > 0
				&& filecounterto.trim().length() > 0) {
			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
			tempSQL += " DECODE(A.DESTINATIONMARKING,?,A.FILECOUNTERDES,A.FILECOUNTERORG) BETWEEN ? AND ? ";
			params.add(roleId);
			params.add(filecounterfrom);
			params.add(filecounterto);

		}

		if (registrationdatefrom.trim().length() > 0
				&& registrationdateto.trim().length() > 0) {
			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
			tempSQL += " TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?','DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')";
			params.add(roleId);
			params.add(registrationdatefrom);
			params.add(registrationdateto);

		}

		if (filnosearch.trim().length() > 0) {
			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
			tempSQL += " UPPER(A.FILENO) LIKE UPPER(?)";
			params.add("%"+filnosearch+"%");

		}

		if (subjectSearch.trim().length() > 0) {
			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
			tempSQL += " UPPER(A.SUBJECT) LIKE UPPER(?)";
			params.add("%"+subjectSearch+"%");
		}

/*		if (COMMONSEARCHVALUE.trim().length() > 0) {
			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
			tempSQL += " UPPER(A.RECEIVEDFROM) LIKE (SELECT ROLEID FROM MSTROLE WHERE ROLENAME = UPPER('"+COMMONSEARCHVALUE+"'))";
		}
*/		tempSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
		strSQL += tempSQL;
		strSQL += " ORDER BY A.FMID DESC";
		try {
			log.debug(strSQL);
			con.openConnection();
			
			ps = con.setPrepStatement(strSQL);
			for(int i=1;i<params.size();i++){
				ps.setString(i, params.get(i));
			}
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TrnFileHdr bn = new TrnFileHdr();
				bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
				bn.setREGISTRATIONNO(StringFormat.nullString(rs
						.getString("REGISTRATIONNO")));
				bn.setISATTACHMENT(StringFormat.nullString(rs
						.getString("ISATTACHMENT")));
				bn.setFILESUBJECTCODE(StringFormat.nullString(rs
						.getString("FILESUBJECTCODE")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setREGISTRATIONDATE(StringFormat.nullString(rs
						.getString("REGISTRATIONDATE")));
				bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
				bn.setDEPARTMENTCODE(StringFormat.nullString(rs
						.getString("DEPARTMENTCODE")));
				bn.setDESTINATIONMARKING(StringFormat.nullString(rs
						.getString("DESTINATIONMARKING")));
				bn.setIMARKINGTO(StringFormat.nullString(rs
						.getString("IMARKINGTO")));
				bn.setFILESTATUS1(StringFormat.nullString(rs
						.getString("FILESTATUS1")));
				bn.setFILESTATUS2(StringFormat.nullString(rs
						.getString("FILESTATUS2")));
				bn.setNOL(StringFormat.nullString(rs
						.getString("NOL")));
				bn.setDMARKINGTO(StringFormat.nullString(rs
						.getString("DMARKINGTO")));
				bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("DMARKINGDATE")));
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
	
	
	public ArrayList<TrnFileHdr> getSearchSub2(String sString,String fdate,String tdate,String sphrase, String roleId, String isConf,String isReply, String advtext) {
//		String result = "";
		//System.out.println("isConf : " + isConf);
		ArrayList<String> parameters = new ArrayList<String>();
		parameters.add("");
		PreparedStatement ps = null;
		String[] sArray=sString.split(" ");
		String condSub="";
		String condFileNo="";
		String conddmark="";
		String conddremarks="";
		String condfilestatus1="";
		String condfilestatus2="";
		String condimark="";
		String condDate="";
		String orCond="";
		String regNoDes="";
		String regDate="";
		String recvdFrom="";
		String condConf="";
		String Freply = "";	
		String EOfficeCond = "";
		String SentToCond = "";
		if(isReply.equalsIgnoreCase("1")){
			Freply=isReply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=A.FMID)";
		}
		else{
			Freply="''";
		}
		
		String Qreply="";
		Qreply = isReply.equalsIgnoreCase("0")? "'0'": " NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.FMID AND M.TYPE='F'),'0')";
		DBConnection dbCon = new DBConnection();
		ArrayList<TrnFileHdr> arr = new ArrayList<TrnFileHdr>();
		 //System.out.println("advtext : "+advtext);
		try {

			if(sString.length() > 4 && sString.substring(0, 5).equalsIgnoreCase("USER ")){
				parameters.add(roleId);
				parameters.add(roleId);
				parameters.add(roleId);
				parameters.add(roleId);
				String loginId = sString.substring(5);
				condSub += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				condSub += " AND UPPER(A.LOGINID) LIKE UPPER(?)";
				parameters.add(loginId);
				condSub += " AND TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND  TO_DATE(?,'DD/MM/YYYY')";
				parameters.add(roleId);
				parameters.add(fdate);
				parameters.add(tdate);
				String strSQL = " SELECT DISTINCT A.FMID, "+Qreply+" ISATTACHMENT," +
					" DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONNODES,A.EOFFICENO,A.REGISTRATIONNOORG) REGISTRATIONNO," +
					" TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
					" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Confidential' END REFERENCETYPE," +
					" A.FILENO,(SELECT LISTAGG((SELECT REFNO||'   '||TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') FROM TRNREFERENCE WHERE REFID=P.REFID), ',') WITHIN GROUP (ORDER BY REFID) FROM TRNFILEREF P WHERE P.FMID=A.FMID) PUCLIST," +
					" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
					" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
					" A.SUBJECT, A.EOFFICENO," +
					" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
					" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
					" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
					" SENTTO," +
					" REMARKS," +
					" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
					" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY') DMARKINGDATE," +
					" B.MARKINGREMARK," +
					" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
					" REASON,(SELECT LISTAGG((SELECT ROLENAME FROM MSTROLE WHERE ROLEID=I.MARKINGTO)||CHR(9)||CHR(9)||TO_CHAR(I.MARKINGDATE,'DD/MM/YYYY')||CHR(9)||CHR(9)||" +
					" TO_CHAR(I.CHANGEDATE,'DD/MM/YYYY'), '<BR>') WITHIN GROUP (ORDER BY I.MARKINGSEQUENCE) FROM TRNFILEINTMARKING I WHERE I.FMID=A.FMID) " +
					" MARKINGLIST," +
						"1 CNT,"+Freply+" REPLY " +
					" FROM TRNFILEHDR A, TRNFILEMARKING B" +
					" WHERE 1=1 AND A.FMID = B.FMID(+)  " +
					" AND  DECODE(A.DESTINATIONMARKING,?,A.ROLEIDDES,A.ROLEIDORG) = ?" +
					" "+condSub+"" +
					" ORDER BY FMID,REGISTRATIONDATE DESC";
				dbCon.openConnection();
				log.debug(strSQL);
				ps = dbCon.setPrepStatement(strSQL);
				for(int i=1; i<parameters.size(); i++){
					ps.setString(i, parameters.get(i)); 	
					
				}
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					TrnFileHdr bn = new TrnFileHdr();
					
					bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
					bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
					bn.setREGISTRATIONNO(StringFormat.nullString(rs.getString("REGISTRATIONNO")));
					bn.setREGISTRATIONDATE(StringFormat.nullString(rs.getString("REGISTRATIONDATE")));
					bn.setREFERENCETYPE(StringFormat.nullString(rs.getString("REFERENCETYPE")));
					bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
					bn.setPUCLIST(StringFormat.nullString(rs.getString("PUCLIST")));
					bn.setEOFFICEFILENO(StringFormat.nullString(rs.getString("EOFFICENO")));
					bn.setDEPARTMENTCODE(StringFormat.nullString(rs.getString("DEPARTMENTCODE")));
					bn.setDESTINATIONMARKING(StringFormat.nullString(rs.getString("DESTINATIONMARKING")));
					bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
					bn.setRECEIVEDFROM(StringFormat.nullString(rs.getString("RECEIVEDFROM")));
					bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
					bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
					bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
					bn.setSENTTO(StringFormat.nullString(rs.getString("SENTTO")));
					bn.setDMARKINGTO(StringFormat.nullString(rs.getString("MARKTODOWN")));
					bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("DMARKINGDATE")));
					bn.setMARKINGREMARK(StringFormat.nullString(rs.getString("MARKINGREMARK")));
					bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
					bn.setREASON(StringFormat.nullString(rs.getString("REASON")));
					bn.setIMARKINGTO(StringFormat.nullString(rs.getString("MARKINGLIST")));
					//bn.setIMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
					//bn.setCHANGEDATE(StringFormat.nullString(rs.getString("CHANGEDATE")));
					bn.setCOUNT(StringFormat.nullString(rs.getString("CNT")));
					bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
					//bn.setMARKINGSEQUENCE(StringFormat.nullString(rs.getString("MARKINGSEQUENCE")));
					arr.add(bn);
				}
			}else{
			
			if(advtext.equalsIgnoreCase("0")){
				//System.out.println("-------------------- 1 ");
				parameters.add(roleId);
				parameters.add(roleId);
				parameters.add(roleId);
				parameters.add(roleId);
				 condSub="";
				 condConf += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
						condSub += " UPPER(FILENO) LIKE UPPER(?)";
						parameters.add("%"+sString+"%");
						condSub += " AND TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND  TO_DATE(?,'DD/MM/YYYY')";
						parameters.add(roleId);
						parameters.add(fdate);
						parameters.add(tdate);
						String strSQL = " SELECT DISTINCT A.FMID, "+Qreply+" ISATTACHMENT," +
						" DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
						" TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
						" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Confidential' END REFERENCETYPE," +
						" A.FILENO,(SELECT LISTAGG((SELECT REFNO||'   '||TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') FROM TRNREFERENCE WHERE REFID=P.REFID), ',') WITHIN GROUP (ORDER BY REFID) FROM TRNFILEREF P WHERE P.FMID=A.FMID) PUCLIST," +
						" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
						" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
						" A.SUBJECT, A.EOFFICENO," +
						" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
						" SENTTO," +
						" REMARKS," +
						" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
						" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY') DMARKINGDATE," +
						" B.MARKINGREMARK," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
						" REASON,(SELECT LISTAGG((SELECT ROLENAME FROM MSTROLE WHERE ROLEID=I.MARKINGTO)||CHR(9)||CHR(9)||TO_CHAR(I.MARKINGDATE,'DD/MM/YYYY')||CHR(9)||CHR(9)||" +
					" TO_CHAR(I.CHANGEDATE,'DD/MM/YYYY'), '<BR>') WITHIN GROUP (ORDER BY I.MARKINGSEQUENCE) FROM TRNFILEINTMARKING I WHERE I.FMID=A.FMID) " +
					" MARKINGLIST," +
							"1 CNT,"+Freply+" REPLY " +
						" FROM TRNFILEHDR A, TRNFILEMARKING B" +
						" WHERE 1=1 AND A.FMID = B.FMID(+)  " +
						" AND DECODE(A.DESTINATIONMARKING,?,A.ROLEIDDES,A.ROLEIDORG) = ?" +
						" AND ("+condSub+") "+condConf+"" +
						" ORDER BY FMID,REGISTRATIONDATE DESC";
					dbCon.openConnection();
					log.debug(strSQL);
					ps = dbCon.setPrepStatement(strSQL);
					for(int i=1; i<parameters.size(); i++){
						ps.setString(i, parameters.get(i)); 	
						
					}
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						TrnFileHdr bn = new TrnFileHdr();
						
						bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
						bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
						bn.setREGISTRATIONNO(StringFormat.nullString(rs.getString("REGISTRATIONNO")));
						bn.setREGISTRATIONDATE(StringFormat.nullString(rs.getString("REGISTRATIONDATE")));
						bn.setREFERENCETYPE(StringFormat.nullString(rs.getString("REFERENCETYPE")));
						bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
						bn.setPUCLIST(StringFormat.nullString(rs.getString("PUCLIST")));
						bn.setEOFFICEFILENO(StringFormat.nullString(rs.getString("EOFFICENO")));
						bn.setDEPARTMENTCODE(StringFormat.nullString(rs.getString("DEPARTMENTCODE")));
						bn.setDESTINATIONMARKING(StringFormat.nullString(rs.getString("DESTINATIONMARKING")));
						bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
						bn.setRECEIVEDFROM(StringFormat.nullString(rs.getString("RECEIVEDFROM")));
						bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
						bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
						bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
						bn.setSENTTO(StringFormat.nullString(rs.getString("SENTTO")));
						bn.setDMARKINGTO(StringFormat.nullString(rs.getString("MARKTODOWN")));
						bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("DMARKINGDATE")));
						bn.setMARKINGREMARK(StringFormat.nullString(rs.getString("MARKINGREMARK")));
						bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
						bn.setREASON(StringFormat.nullString(rs.getString("REASON")));
						bn.setIMARKINGTO(StringFormat.nullString(rs.getString("MARKINGLIST")));
						//bn.setIMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
						//bn.setCHANGEDATE(StringFormat.nullString(rs.getString("CHANGEDATE")));
						bn.setCOUNT(StringFormat.nullString(rs.getString("CNT")));
						bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
						//bn.setMARKINGSEQUENCE(StringFormat.nullString(rs.getString("MARKINGSEQUENCE")));
						arr.add(bn);
					}
			}else if(advtext.equalsIgnoreCase("1")){
				//System.out.println("-------------------- 2 ");
				parameters.add(roleId);
				parameters.add(roleId);
				parameters.add(roleId);
				parameters.add(roleId);
				 condSub="";
				 condConf += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
					condSub += " UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT Q.MARKINGTO FROM TRNFILEINTMARKING Q WHERE Q.FMID=A.FMID" +
								" AND Q.MARKINGSEQUENCE=(SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID) ))) = UPPER(?)";
					parameters.add(sString);
					condSub += " AND ((TO_DATE(TO_CHAR((SELECT Q.MARKINGDATE FROM TRNFILEINTMARKING Q WHERE Q.FMID=A.FMID" +
								" AND Q.MARKINGSEQUENCE=(SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID) ),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND  TO_DATE(?,'DD/MM/YYYY')) OR" +
								" (TO_DATE(TO_CHAR((SELECT Q.CHANGEDATE FROM TRNFILEINTMARKING Q WHERE Q.FMID=A.FMID" +
								" AND Q.MARKINGSEQUENCE=(SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID) ),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND  TO_DATE(?,'DD/MM/YYYY')))";
					parameters.add(fdate);
					parameters.add(tdate);
					parameters.add(fdate);
					parameters.add(tdate);
		String strSQL = " SELECT DISTINCT A.FMID, "+Qreply+" ISATTACHMENT," +
					" DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
					" TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
					" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Confidential' END REFERENCETYPE," +
					" A.FILENO,(SELECT LISTAGG((SELECT REFNO||'   '||TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') FROM TRNREFERENCE WHERE REFID=P.REFID), ',') WITHIN GROUP (ORDER BY REFID) FROM TRNFILEREF P WHERE P.FMID=A.FMID) PUCLIST," +
					" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
					" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
					" A.SUBJECT, A.EOFFICENO," +
					" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
					" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
					" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
					" SENTTO,"+
					" REMARKS," +
					" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
					" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY') DMARKINGDATE," +
					" B.MARKINGREMARK," +
					" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
					" REASON,(SELECT LISTAGG((SELECT ROLENAME FROM MSTROLE WHERE ROLEID=I.MARKINGTO)||CHR(9)||CHR(9)||TO_CHAR(I.MARKINGDATE,'DD/MM/YYYY')||CHR(9)||CHR(9)||" +
					" TO_CHAR(I.CHANGEDATE,'DD/MM/YYYY'), '<BR>') WITHIN GROUP (ORDER BY I.MARKINGSEQUENCE) FROM TRNFILEINTMARKING I WHERE I.FMID=A.FMID) " +
					" MARKINGLIST," +
						"1 CNT,"+Freply+" REPLY " +
					" FROM TRNFILEHDR A, TRNFILEMARKING B" +
					" WHERE 1=1 AND A.FMID = B.FMID(+) " +
					" AND DECODE(A.DESTINATIONMARKING,?,A.ROLEIDDES,A.ROLEIDORG) =?" +
					" AND ("+condSub+") "+condConf+"" +
					"ORDER BY FMID,REGISTRATIONDATE DESC";
				dbCon.openConnection();
				log.debug(strSQL);
				ps = dbCon.setPrepStatement(strSQL);
				for(int i=1; i<parameters.size(); i++){
					ps.setString(i, parameters.get(i)); 	
					
				}
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					TrnFileHdr bn = new TrnFileHdr();
					
					bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
					bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
					bn.setREGISTRATIONNO(StringFormat.nullString(rs.getString("REGISTRATIONNO")));
					bn.setREGISTRATIONDATE(StringFormat.nullString(rs.getString("REGISTRATIONDATE")));
					bn.setREFERENCETYPE(StringFormat.nullString(rs.getString("REFERENCETYPE")));
					bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
					bn.setPUCLIST(StringFormat.nullString(rs.getString("PUCLIST")));
					bn.setEOFFICEFILENO(StringFormat.nullString(rs.getString("EOFFICENO")));
					bn.setDEPARTMENTCODE(StringFormat.nullString(rs.getString("DEPARTMENTCODE")));
					bn.setDESTINATIONMARKING(StringFormat.nullString(rs.getString("DESTINATIONMARKING")));
					bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
					bn.setRECEIVEDFROM(StringFormat.nullString(rs.getString("RECEIVEDFROM")));
					bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
					bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
					bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
					bn.setSENTTO(StringFormat.nullString(rs.getString("SENTTO")));
					bn.setDMARKINGTO(StringFormat.nullString(rs.getString("MARKTODOWN")));
					bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("DMARKINGDATE")));
					bn.setMARKINGREMARK(StringFormat.nullString(rs.getString("MARKINGREMARK")));
					bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
					bn.setREASON(StringFormat.nullString(rs.getString("REASON")));
					bn.setIMARKINGTO(StringFormat.nullString(rs.getString("MARKINGLIST")));
					//bn.setIMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
					//bn.setCHANGEDATE(StringFormat.nullString(rs.getString("CHANGEDATE")));
					bn.setCOUNT(StringFormat.nullString(rs.getString("CNT")));
					bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
					//bn.setMARKINGSEQUENCE(StringFormat.nullString(rs.getString("MARKINGSEQUENCE")));
					arr.add(bn);
				}
		}else if(advtext.equalsIgnoreCase("2")){
			//System.out.println("-------------------- 3 ");
			parameters.add(roleId);
			parameters.add(roleId);
			parameters.add(roleId);
			parameters.add(roleId);
			 condSub="";
			 	condConf += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				condSub += " UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNFILEMARKING M WHERE M.FMID=A.FMID" +
						" AND M.MARKINGSEQUENCE=1  AND M.MARKINGTYPE = 'D'))) = UPPER(?)";
				parameters.add(sString);
				condSub += " AND ((TO_DATE(TO_CHAR((SELECT M.MARKINGDATE FROM TRNFILEMARKING M WHERE M.FMID=A.FMID" +
						" AND M.MARKINGSEQUENCE=1  AND M.MARKINGTYPE = 'D'),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND  TO_DATE(?,'DD/MM/YYYY')) OR" +
						" (TO_DATE(TO_CHAR((SELECT M.ACTIONDATE FROM TRNFILEMARKING M WHERE M.FMID=A.FMID" +
						" AND M.MARKINGSEQUENCE=1  AND M.MARKINGTYPE = 'D'),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND  TO_DATE(?,'DD/MM/YYYY')))";
				parameters.add(fdate);
				parameters.add(tdate);
				parameters.add(fdate);
				parameters.add(tdate);
				String strSQL = " SELECT DISTINCT A.FMID, "+Qreply+" ISATTACHMENT," +
				" DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
				" TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
				" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Confidential' END REFERENCETYPE," +
				" A.FILENO,(SELECT LISTAGG((SELECT REFNO||'   '||TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') FROM TRNREFERENCE WHERE REFID=P.REFID), ',') WITHIN GROUP (ORDER BY REFID) FROM TRNFILEREF P WHERE P.FMID=A.FMID) PUCLIST," +
				" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
				" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
				" A.SUBJECT, A.EOFFICENO," +
				" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
				" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
				" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
				" SENTTO,"+
				" REMARKS," +
				" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
				" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY') DMARKINGDATE," +
				" B.MARKINGREMARK," +
				" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
				" REASON,(SELECT LISTAGG((SELECT ROLENAME FROM MSTROLE WHERE ROLEID=I.MARKINGTO)||CHR(9)||CHR(9)||TO_CHAR(I.MARKINGDATE,'DD/MM/YYYY')||CHR(9)||CHR(9)||" +
					" TO_CHAR(I.CHANGEDATE,'DD/MM/YYYY'), '<BR>') WITHIN GROUP (ORDER BY I.MARKINGSEQUENCE) FROM TRNFILEINTMARKING I WHERE I.FMID=A.FMID) " +
					" MARKINGLIST," +
					"1 CNT,"+Freply+" REPLY " +
				" FROM TRNFILEHDR A, TRNFILEMARKING B" +
				" WHERE 1=1 AND A.FMID = B.FMID(+)  " +
				" AND DECODE(A.DESTINATIONMARKING,?,A.ROLEIDDES,A.ROLEIDORG) = ?" +
				" AND ("+condSub+") "+condConf+"" +
				"ORDER BY FMID,REGISTRATIONDATE DESC";
			dbCon.openConnection();
			log.debug(strSQL);
			ps = dbCon.setPrepStatement(strSQL);
			for(int i=1; i<parameters.size(); i++){
				ps.setString(i, parameters.get(i)); 	
				
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TrnFileHdr bn = new TrnFileHdr();
				
				bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
				bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
				bn.setREGISTRATIONNO(StringFormat.nullString(rs.getString("REGISTRATIONNO")));
				bn.setREGISTRATIONDATE(StringFormat.nullString(rs.getString("REGISTRATIONDATE")));
				bn.setREFERENCETYPE(StringFormat.nullString(rs.getString("REFERENCETYPE")));
				bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
				bn.setPUCLIST(StringFormat.nullString(rs.getString("PUCLIST")));
				bn.setEOFFICEFILENO(StringFormat.nullString(rs.getString("EOFFICENO")));
				bn.setDEPARTMENTCODE(StringFormat.nullString(rs.getString("DEPARTMENTCODE")));
				bn.setDESTINATIONMARKING(StringFormat.nullString(rs.getString("DESTINATIONMARKING")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setRECEIVEDFROM(StringFormat.nullString(rs.getString("RECEIVEDFROM")));
				bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
				bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
				bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
				bn.setSENTTO(StringFormat.nullString(rs.getString("SENTTO")));
				bn.setDMARKINGTO(StringFormat.nullString(rs.getString("MARKTODOWN")));
				bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("DMARKINGDATE")));
				bn.setMARKINGREMARK(StringFormat.nullString(rs.getString("MARKINGREMARK")));
				bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
				bn.setREASON(StringFormat.nullString(rs.getString("REASON")));
				bn.setIMARKINGTO(StringFormat.nullString(rs.getString("MARKINGLIST")));
				//bn.setIMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
				//bn.setCHANGEDATE(StringFormat.nullString(rs.getString("CHANGEDATE")));
				bn.setCOUNT(StringFormat.nullString(rs.getString("CNT")));
				bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
				//bn.setMARKINGSEQUENCE(StringFormat.nullString(rs.getString("MARKINGSEQUENCE")));
				arr.add(bn);
			}
	}else{
			parameters.add(roleId);
			parameters.add(roleId);
			parameters.add(roleId);
			parameters.add(roleId);
			if(sphrase.equalsIgnoreCase("0")){
				//System.out.println("------------------");
				
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condSub = condSub+" UPPER(A.SUBJECT) LIKE UPPER(?) "+orCond+" ";
				parameters.add("%"+sArray[i]+"%");
				}
			}
			
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condFileNo = condFileNo+"UPPER(A.FILENO) LIKE UPPER(?) "+orCond+" ";
				parameters.add("%"+sArray[i]+"%");
				}
			}
			
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					EOfficeCond = EOfficeCond+"UPPER(A.EOFFICENO) LIKE UPPER(?) "+orCond+" ";
					parameters.add("%"+sArray[i]+"%");
				}
			}
			
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					SentToCond = SentToCond+"UPPER(A.SENTTO) LIKE UPPER(?) "+orCond+" ";
					parameters.add("%"+sArray[i]+"%");
				}
			}
			
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condfilestatus1 = condfilestatus1+"UPPER((SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1)) LIKE UPPER(?) "+orCond+" ";
					parameters.add("%"+sArray[i]+"%");
				}
			}
			
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condfilestatus2 = condfilestatus2+"UPPER((SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2)) LIKE UPPER(?) "+orCond+" ";
					parameters.add("%"+sArray[i]+"%");
				}
			}
			
			
			
			
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				conddmark = conddmark+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNFILEMARKING M WHERE M.FMID=A.FMID" +
						" AND M.MARKINGSEQUENCE=1  AND M.MARKINGTYPE = 'D'))) LIKE UPPER(?) "+orCond+" ";
				parameters.add("%"+sArray[i]+"%");
				}
			}
			
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				conddremarks = conddremarks+"UPPER((SELECT M.MARKINGREMARK FROM TRNFILEMARKING M WHERE M.FMID=A.FMID" +
						" AND M.MARKINGSEQUENCE=1  AND M.MARKINGTYPE = 'D')) LIKE UPPER(?) "+orCond+" ";
				parameters.add("%"+sArray[i]+"%");
				}
			}
			
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condimark = condimark+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT Q.MARKINGTO FROM TRNFILEINTMARKING Q WHERE Q.FMID=A.FMID" +
				" AND Q.MARKINGSEQUENCE=(SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID) ))) LIKE UPPER(?) "+orCond+" ";
				parameters.add("%"+sArray[i]+"%");
				}
			}

			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					regNoDes = regNoDes+" DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONNODES,A.REGISTRATIONNOORG) LIKE UPPER(?) "+orCond+" ";
					parameters.add(roleId);
					parameters.add("%"+sArray[i]+"%");
				}
			}

			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					regDate = regDate+" TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') = ? "+orCond+" ";
					parameters.add(roleId);
					parameters.add(sArray[i]);
				}
			}

			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length-1)?"OR":"";
					recvdFrom = recvdFrom+" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) = ? "+orCond+"";
					parameters.add(sArray[i]);
				}
			}
			
			
			if(fdate.length()>0 && tdate.length()>0){
				condDate = "AND TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND  TO_DATE(?,'DD/MM/YYYY')";
				parameters.add(roleId);
				parameters.add(fdate);
				parameters.add(tdate);
				condConf += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			}
			}else{
				//System.out.println("h kjdhkjdhkjsdfhkjfgfjkh skjfhg-------------------------------");
				condSub = condSub+"UPPER(A.SUBJECT) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
												
				condFileNo = condFileNo+"UPPER(A.FILENO) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
				
				EOfficeCond = EOfficeCond+"UPPER(A.EOFFICENO) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
				
				SentToCond = SentToCond+"UPPER(A.SENTTO) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
							
				condfilestatus1 = condfilestatus1+"UPPER((SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1)) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
								
				condfilestatus2 = condfilestatus2+"UPPER((SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2)) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
							
				conddmark = conddmark+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNFILEMARKING M WHERE M.FMID=A.FMID" +
				" AND M.MARKINGSEQUENCE=1  AND M.MARKINGTYPE = 'D'))) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
				
				conddremarks = conddremarks+"UPPER((SELECT M.MARKINGREMARK FROM TRNFILEMARKING M WHERE M.FMID=A.FMID" +
						" AND M.MARKINGSEQUENCE=1  AND M.MARKINGTYPE = 'D')) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
				
				condimark = condimark+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT Q.MARKINGTO FROM TRNFILEINTMARKING Q WHERE Q.FMID=A.FMID" +
				" AND Q.MARKINGSEQUENCE=(SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID) ))) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");

				regNoDes = regNoDes+"DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONNODES,A.REGISTRATIONNOORG) LIKE UPPER(?) OR ";
				parameters.add(roleId);
				parameters.add("%"+sString+"%");
				
				regDate = regDate+"TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') = ? OR ";
				parameters.add(roleId);
				parameters.add(sString);

				recvdFrom = recvdFrom+"(SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) = ? ";
				parameters.add(sString);
				
				
				if(fdate.length()>0 && tdate.length()>0){
					condDate = "AND TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND  TO_DATE(?,'DD/MM/YYYY')";
					parameters.add(roleId);
					parameters.add(fdate);
					parameters.add(tdate);
					condConf += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0' ": " AND ISCONF IN ('0','1') ";
				}
			}

/*			String strSQL = " SELECT DISTINCT A.FMID,A.REGISTRATIONNODES REGISTRATIONNO,"
				+ "(SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.FILESUBJECTCODEDES) "
				+ "  FILESUBJECTCODE,A.SUBJECT,"
				+ "TO_CHAR(A.REGISTRATIONDATEDES,'DD/MM/YYYY') REGISTRATIONDATE,"
				+ "A.FILENO,(SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE,"
				+ "(SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING,"
				+ "(SELECT (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=O.MARKINGTO) FROM TRNFILEINTMARKING O WHERE O.FMID=A.FMID "
				+ " AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID)) IMARKINGTO,"
				+ " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1,"
				+ "(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2,"
				+ "NOL,"
				+ "(SELECT (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=O.MARKINGTO) FROM TRNFILEMARKING O WHERE O.FMID=A.FMID "
				+ " AND O.MARKINGSEQUENCE = 1 AND O.MARKINGTYPE = 'D') DMARKINGTO,"
				+ "(SELECT TO_CHAR(O.MARKINGDATE,'DD/MM/YYYY') FROM TRNFILEMARKING O WHERE O.FMID=A.FMID "
				+ " AND O.MARKINGSEQUENCE = 1 AND O.MARKINGTYPE = 'D') DMARKINGDATE "
				+ " FROM TRNFILEHDR A  WHERE ("+condSub+" "+condFileNo+" "+condfilestatus1+" "+condfilestatus2+" "+conddmark+" "+condimark+") "+condDate+" " +
					" ORDER BY A.REGISTRATIONDATEDES DESC";
*/
			
		String strSQL = " SELECT DISTINCT A.FMID, "+Qreply+" ISATTACHMENT," +
						" DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
						" TO_CHAR(DECODE(A.DESTINATIONMARKING,?,A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
						" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Confidential' END REFERENCETYPE," +
						" A.FILENO,(SELECT LISTAGG((SELECT REFNO||'   '||TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') FROM TRNREFERENCE WHERE REFID=P.REFID), ',') WITHIN GROUP (ORDER BY REFID) FROM TRNFILEREF P WHERE P.FMID=A.FMID) PUCLIST," +
						" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
						" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
						" A.SUBJECT,A.EOFFICENO," +
						" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
						" SENTTO,"+
						" REMARKS," +
						" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
						" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY') DMARKINGDATE," +
						" B.MARKINGREMARK," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
						" REASON,(SELECT LISTAGG((SELECT ROLENAME FROM MSTROLE WHERE ROLEID=I.MARKINGTO)||CHR(9)||CHR(9)||TO_CHAR(I.MARKINGDATE,'DD/MM/YYYY')||CHR(9)||CHR(9)||" +
					" TO_CHAR(I.CHANGEDATE,'DD/MM/YYYY'), '<BR>') WITHIN GROUP (ORDER BY I.MARKINGSEQUENCE) FROM TRNFILEINTMARKING I WHERE I.FMID=A.FMID) " +
					" MARKINGLIST," +
							"1 CNT,"+Freply+" REPLY " +
						" FROM TRNFILEHDR A, TRNFILEMARKING B" +
						" WHERE 1=1 AND A.FMID = B.FMID(+) " +
						" AND DECODE(A.DESTINATIONMARKING,?,A.ROLEIDDES,A.ROLEIDORG) = ?" +
						" AND ("+condSub+" "+condFileNo+" "+EOfficeCond + " " +SentToCond+ " "+condfilestatus1+" "+condfilestatus2+" "+conddmark+" "+conddremarks+" "+condimark+" "+regNoDes+" "+regDate+" "+recvdFrom+") "+condDate+" "+condConf+" " +
						"ORDER BY FMID,REGISTRATIONDATE DESC";
			dbCon.openConnection();
			ps = dbCon.setPrepStatement(strSQL);
			for(int i=1; i<parameters.size(); i++){
				ps.setString(i, parameters.get(i));
				//System.out.println("paramas :"+parameters.get(i));
				
			}
			log.debug(strSQL);
			//System.out.println("sunnnnnnnnnn : "+ strSQL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				TrnFileHdr bn = new TrnFileHdr();
				
				bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
				bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
				bn.setREGISTRATIONNO(StringFormat.nullString(rs.getString("REGISTRATIONNO")));
				bn.setREGISTRATIONDATE(StringFormat.nullString(rs.getString("REGISTRATIONDATE")));
				bn.setREFERENCETYPE(StringFormat.nullString(rs.getString("REFERENCETYPE")));
				bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
				bn.setPUCLIST(StringFormat.nullString(rs.getString("PUCLIST")));
				bn.setEOFFICEFILENO(StringFormat.nullString(rs.getString("EOFFICENO")));
				bn.setDEPARTMENTCODE(StringFormat.nullString(rs.getString("DEPARTMENTCODE")));
				bn.setDESTINATIONMARKING(StringFormat.nullString(rs.getString("DESTINATIONMARKING")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setRECEIVEDFROM(StringFormat.nullString(rs.getString("RECEIVEDFROM")));
				bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
				bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
				bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
				bn.setSENTTO(StringFormat.nullString(rs.getString("SENTTO")));
				bn.setDMARKINGTO(StringFormat.nullString(rs.getString("MARKTODOWN")));
				bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("DMARKINGDATE")));
				bn.setMARKINGREMARK(StringFormat.nullString(rs.getString("MARKINGREMARK")));
				bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
				bn.setREASON(StringFormat.nullString(rs.getString("REASON")));
				bn.setIMARKINGTO(StringFormat.nullString(rs.getString("MARKINGLIST")));
				//bn.setIMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
				//bn.setCHANGEDATE(StringFormat.nullString(rs.getString("CHANGEDATE")));
				bn.setCOUNT(StringFormat.nullString(rs.getString("CNT")));
				bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
				//bn.setMARKINGSEQUENCE(StringFormat.nullString(rs.getString("MARKINGSEQUENCE")));
				arr.add(bn);
			}
			}
			}
//	MR/1460/2012
		} catch (Exception e) {
			e.printStackTrace();
//			result = "0~" + e.getMessage();
			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return arr;
	}
	
	public ArrayList<TrnFileHdr> getSearchSubReply(String sString,String fdate,String tdate,String sphrase, String roleId, String isConf,String isReply, String advtext) {
//		String result = "";
		//System.out.println("isConf : " + isConf);
		String[] sArray=sString.split(" ");
		String condSub="";
		String condFileNo="";
		String conddmark="";
		String condfilestatus1="";
		String condfilestatus2="";
		String condimark="";
		String condDate="";
		String orCond="";
		String regNoDes="";
		String regDate="";
		String recvdFrom="";
		String condConf="";
		String Freply = "";	
		if(isReply.equalsIgnoreCase("1")){
			Freply=isReply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=A.FMID)";
		}
		else{
			Freply="''";
		}
		
		String Qreply="";
		Qreply = isReply.equalsIgnoreCase("0")? "'0'": " NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.FMID AND M.TYPE='F'),'0')";
		DBConnection dbCon = new DBConnection();
		ArrayList<TrnFileHdr> arr = new ArrayList<TrnFileHdr>();
		 //System.out.println("advtext : "+advtext);
		try {
			
			if(sString.length() > 4 && sString.substring(0, 5).equalsIgnoreCase("USER ")){
				String loginId = sString.substring(5);
				condSub += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				condSub += " AND UPPER(A.LOGINID) LIKE UPPER('"+loginId+"')";
				condSub += " AND TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
					String strSQL = " SELECT DISTINCT A.FMID, "+Qreply+" ISATTACHMENT," +
					" DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
					" TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
					" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Confidential' END REFERENCETYPE," +
					" A.FILENO,(SELECT LISTAGG((SELECT REFNO||'   '||TO_CHAR(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') FROM TRNREFERENCE WHERE REFID=P.REFID), ',') WITHIN GROUP (ORDER BY REFID) FROM TRNFILEREF P WHERE P.FMID=A.FMID) PUCLIST," +
					" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
					" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
					" A.SUBJECT," +
					" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
					" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
					" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
					" REMARKS," +
					" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
					" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY') DMARKINGDATE," +
					" B.MARKINGREMARK," +
					" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
					" REASON,(SELECT LISTAGG((SELECT ROLENAME FROM MSTROLE WHERE ROLEID=I.MARKINGTO)||CHR(9)||CHR(9)||TO_CHAR(I.MARKINGDATE,'DD/MM/YYYY')||CHR(9)||CHR(9)||" +
					" TO_CHAR(I.CHANGEDATE,'DD/MM/YYYY'), '<BR>') WITHIN GROUP (ORDER BY I.MARKINGSEQUENCE) FROM TRNFILEINTMARKING I WHERE I.FMID=A.FMID) " +
					" MARKINGLIST,1 CNT,"+Freply+" REPLY " +
					" FROM TRNFILEHDR A, TRNFILEMARKING B" +
					" WHERE 1=1 AND A.FMID = B.FMID(+)  AND DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.ROLEIDDES,A.ROLEIDORG) = '"+roleId+"'" +
					" "+condSub+"" +
					
					" ORDER BY FMID,REGISTRATIONDATE DESC";
				dbCon.openConnection();
				log.debug(strSQL);
				ResultSet rs = dbCon.select(strSQL);
				while (rs.next()) {
					TrnFileHdr bn = new TrnFileHdr();
					
					bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
					bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
					bn.setREGISTRATIONNO(StringFormat.nullString(rs.getString("REGISTRATIONNO")));
					bn.setREGISTRATIONDATE(StringFormat.nullString(rs.getString("REGISTRATIONDATE")));
					bn.setREFERENCETYPE(StringFormat.nullString(rs.getString("REFERENCETYPE")));
					bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
					bn.setPUCLIST(StringFormat.nullString(rs.getString("PUCLIST")));
					bn.setDEPARTMENTCODE(StringFormat.nullString(rs.getString("DEPARTMENTCODE")));
					bn.setDESTINATIONMARKING(StringFormat.nullString(rs.getString("DESTINATIONMARKING")));
					bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
					bn.setRECEIVEDFROM(StringFormat.nullString(rs.getString("RECEIVEDFROM")));
					bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
					bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
					bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
					bn.setDMARKINGTO(StringFormat.nullString(rs.getString("MARKTODOWN")));
					bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("DMARKINGDATE")));
					bn.setMARKINGREMARK(StringFormat.nullString(rs.getString("MARKINGREMARK")));
					bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
					bn.setREASON(StringFormat.nullString(rs.getString("REASON")));
					bn.setIMARKINGTO(StringFormat.nullString(rs.getString("MARKINGLIST")));
					
					bn.setCOUNT(StringFormat.nullString(rs.getString("CNT")));
					bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
					//bn.setMARKINGSEQUENCE(StringFormat.nullString(rs.getString("MARKINGSEQUENCE")));
					arr.add(bn);
				}
			}else{
			
			if(advtext.equalsIgnoreCase("0")){
				//System.out.println("-------------------- 1 ");
				 condSub="";
				 condConf += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
						condSub += " UPPER(FILENO) LIKE UPPER('%"+sString+"%')";
						condSub += " AND TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
						String strSQL = " SELECT DISTINCT A.FMID, "+Qreply+" ISATTACHMENT," +
						" DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
						" TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
						" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Confidential' END REFERENCETYPE," +
						" A.FILENO,(SELECT LISTAGG((SELECT REFNO||'   '||TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') FROM TRNREFERENCE WHERE REFID=P.REFID), ',') WITHIN GROUP (ORDER BY REFID) FROM TRNFILEREF P WHERE P.FMID=A.FMID) PUCLIST," +
						" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
						" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
						" A.SUBJECT," +
						" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
						" REMARKS," +
						" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
						" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY') DMARKINGDATE," +
						" B.MARKINGREMARK," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
						" REASON,(SELECT LISTAGG((SELECT ROLENAME FROM MSTROLE WHERE ROLEID=I.MARKINGTO)||CHR(9)||CHR(9)||TO_CHAR(I.MARKINGDATE,'DD/MM/YYYY')||CHR(9)||CHR(9)||" +
						" TO_CHAR(I.CHANGEDATE,'DD/MM/YYYY'), '<BR>') WITHIN GROUP (ORDER BY I.MARKINGSEQUENCE) FROM TRNFILEINTMARKING I WHERE I.FMID=A.FMID) " +
						" MARKINGLIST," +
							"1 CNT,"+Freply+" REPLY " +
						" FROM TRNFILEHDR A, TRNFILEMARKING B" +
						" WHERE 1=1 AND A.FMID = B.FMID(+) AND DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.ROLEIDDES,A.ROLEIDORG) = '"+roleId+"'" +
						" AND ("+condSub+") "+condConf+"" +
						
						" ORDER BY FMID,REGISTRATIONDATE DESC";
					dbCon.openConnection();
					log.debug(strSQL);
					ResultSet rs = dbCon.select(strSQL);
					while (rs.next()) {
						TrnFileHdr bn = new TrnFileHdr();
						
						bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
						bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
						bn.setREGISTRATIONNO(StringFormat.nullString(rs.getString("REGISTRATIONNO")));
						bn.setREGISTRATIONDATE(StringFormat.nullString(rs.getString("REGISTRATIONDATE")));
						bn.setREFERENCETYPE(StringFormat.nullString(rs.getString("REFERENCETYPE")));
						bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
						bn.setPUCLIST(StringFormat.nullString(rs.getString("PUCLIST")));
						bn.setDEPARTMENTCODE(StringFormat.nullString(rs.getString("DEPARTMENTCODE")));
						bn.setDESTINATIONMARKING(StringFormat.nullString(rs.getString("DESTINATIONMARKING")));
						bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
						bn.setRECEIVEDFROM(StringFormat.nullString(rs.getString("RECEIVEDFROM")));
						bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
						bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
						bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
						bn.setDMARKINGTO(StringFormat.nullString(rs.getString("MARKTODOWN")));
						bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("DMARKINGDATE")));
						bn.setMARKINGREMARK(StringFormat.nullString(rs.getString("MARKINGREMARK")));
						bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
						bn.setREASON(StringFormat.nullString(rs.getString("REASON")));
						bn.setIMARKINGTO(StringFormat.nullString(rs.getString("MARKINGLIST")));
						
						bn.setCOUNT(StringFormat.nullString(rs.getString("CNT")));
						bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
						//bn.setMARKINGSEQUENCE(StringFormat.nullString(rs.getString("MARKINGSEQUENCE")));
						arr.add(bn);
					}
			}else if(advtext.equalsIgnoreCase("1")){
				//System.out.println("-------------------- 2 ");
				 condSub="";
				 condConf += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
					condSub += " UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT Q.MARKINGTO FROM TRNFILEINTMARKING Q WHERE Q.FMID=A.FMID" +
								" AND Q.MARKINGSEQUENCE=(SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID) ))) = UPPER('"+sString+"')";
					condSub += " AND ((TO_DATE(TO_CHAR((SELECT Q.MARKINGDATE FROM TRNFILEINTMARKING Q WHERE Q.FMID=A.FMID" +
								" AND Q.MARKINGSEQUENCE=(SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID) ),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')) OR" +
								" (TO_DATE(TO_CHAR((SELECT Q.CHANGEDATE FROM TRNFILEINTMARKING Q WHERE Q.FMID=A.FMID" +
								" AND Q.MARKINGSEQUENCE=(SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID) ),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')))";
		String strSQL = " SELECT DISTINCT A.FMID, "+Qreply+" ISATTACHMENT," +
					" DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
					" TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
					" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Confidential' END REFERENCETYPE," +
					" A.FILENO,(SELECT LISTAGG((SELECT REFNO||'   '||TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') FROM TRNREFERENCE WHERE REFID=P.REFID), ',') WITHIN GROUP (ORDER BY REFID) FROM TRNFILEREF P WHERE P.FMID=A.FMID) PUCLIST," +
					" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
					" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
					" A.SUBJECT," +
					" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
					" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
					" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
					" REMARKS," +
					" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
					" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY') DMARKINGDATE," +
					" B.MARKINGREMARK," +
					" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
					" REASON,(SELECT LISTAGG((SELECT ROLENAME FROM MSTROLE WHERE ROLEID=I.MARKINGTO)||CHR(9)||CHR(9)||TO_CHAR(I.MARKINGDATE,'DD/MM/YYYY')||CHR(9)||CHR(9)||" +
					" TO_CHAR(I.CHANGEDATE,'DD/MM/YYYY'), '<BR>') WITHIN GROUP (ORDER BY I.MARKINGSEQUENCE) FROM TRNFILEINTMARKING I WHERE I.FMID=A.FMID) " +
					" MARKINGLIST," +
						"1 CNT,"+Freply+" REPLY " +
					" FROM TRNFILEHDR A, TRNFILEMARKING B" +
					" WHERE 1=1 AND A.FMID = B.FMID(+) AND DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.ROLEIDDES,A.ROLEIDORG) = '"+roleId+"'" +
					" AND ("+condSub+") "+condConf+"" +
					
					"ORDER BY FMID,REGISTRATIONDATE DESC";
				dbCon.openConnection();
				log.debug(strSQL);
				ResultSet rs = dbCon.select(strSQL);
				while (rs.next()) {
					TrnFileHdr bn = new TrnFileHdr();
					
					bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
					bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
					bn.setREGISTRATIONNO(StringFormat.nullString(rs.getString("REGISTRATIONNO")));
					bn.setREGISTRATIONDATE(StringFormat.nullString(rs.getString("REGISTRATIONDATE")));
					bn.setREFERENCETYPE(StringFormat.nullString(rs.getString("REFERENCETYPE")));
					bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
					bn.setPUCLIST(StringFormat.nullString(rs.getString("PUCLIST")));
					bn.setDEPARTMENTCODE(StringFormat.nullString(rs.getString("DEPARTMENTCODE")));
					bn.setDESTINATIONMARKING(StringFormat.nullString(rs.getString("DESTINATIONMARKING")));
					bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
					bn.setRECEIVEDFROM(StringFormat.nullString(rs.getString("RECEIVEDFROM")));
					bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
					bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
					bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
					bn.setDMARKINGTO(StringFormat.nullString(rs.getString("MARKTODOWN")));
					bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("DMARKINGDATE")));
					bn.setMARKINGREMARK(StringFormat.nullString(rs.getString("MARKINGREMARK")));
					bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
					bn.setREASON(StringFormat.nullString(rs.getString("REASON")));
					bn.setIMARKINGTO(StringFormat.nullString(rs.getString("MARKINGLIST")));
					
					bn.setCOUNT(StringFormat.nullString(rs.getString("CNT")));
					bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
					//bn.setMARKINGSEQUENCE(StringFormat.nullString(rs.getString("MARKINGSEQUENCE")));
					arr.add(bn);
				}
		}else if(advtext.equalsIgnoreCase("2")){
			//System.out.println("-------------------- 3 ");
			 condSub="";
			 	condConf += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				condSub += " UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNFILEMARKING M WHERE M.FMID=A.FMID" +
						" AND M.MARKINGSEQUENCE=1  AND M.MARKINGTYPE = 'D'))) = UPPER('"+sString+"')";
				condSub += " AND ((TO_DATE(TO_CHAR((SELECT M.MARKINGDATE FROM TRNFILEMARKING M WHERE M.FMID=A.FMID" +
						" AND M.MARKINGSEQUENCE=1  AND M.MARKINGTYPE = 'D'),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')) OR" +
						" (TO_DATE(TO_CHAR((SELECT M.ACTIONDATE FROM TRNFILEMARKING M WHERE M.FMID=A.FMID" +
						" AND M.MARKINGSEQUENCE=1  AND M.MARKINGTYPE = 'D'),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')))";
				String strSQL = " SELECT DISTINCT A.FMID, "+Qreply+" ISATTACHMENT," +
				" DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
				" TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
				" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Confidential' END REFERENCETYPE," +
				" A.FILENO,(SELECT LISTAGG((SELECT REFNO||'   '||TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') FROM TRNREFERENCE WHERE REFID=P.REFID), ',') WITHIN GROUP (ORDER BY REFID) FROM TRNFILEREF P WHERE P.FMID=A.FMID) PUCLIST," +
				" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
				" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
				" A.SUBJECT," +
				" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
				" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
				" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
				" REMARKS," +
				" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
				" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY') DMARKINGDATE," +
				" B.MARKINGREMARK," +
				" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
				" REASON,(SELECT LISTAGG((SELECT ROLENAME FROM MSTROLE WHERE ROLEID=I.MARKINGTO)||CHR(9)||CHR(9)||TO_CHAR(I.MARKINGDATE,'DD/MM/YYYY')||CHR(9)||CHR(9)||" +
					" TO_CHAR(I.CHANGEDATE,'DD/MM/YYYY'), '<BR>') WITHIN GROUP (ORDER BY I.MARKINGSEQUENCE) FROM TRNFILEINTMARKING I WHERE I.FMID=A.FMID) " +
					" MARKINGLIST," +
					"1 CNT,"+Freply+" REPLY " +
				" FROM TRNFILEHDR A, TRNFILEMARKING B" +
				" WHERE 1=1 AND A.FMID = B.FMID(+) AND DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.ROLEIDDES,A.ROLEIDORG) = '"+roleId+"'" +
				" AND ("+condSub+") "+condConf+"" +
				
				"ORDER BY FMID,REGISTRATIONDATE DESC";
			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				TrnFileHdr bn = new TrnFileHdr();
				
				bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
				bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
				bn.setREGISTRATIONNO(StringFormat.nullString(rs.getString("REGISTRATIONNO")));
				bn.setREGISTRATIONDATE(StringFormat.nullString(rs.getString("REGISTRATIONDATE")));
				bn.setREFERENCETYPE(StringFormat.nullString(rs.getString("REFERENCETYPE")));
				bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
				bn.setPUCLIST(StringFormat.nullString(rs.getString("PUCLIST")));
				bn.setDEPARTMENTCODE(StringFormat.nullString(rs.getString("DEPARTMENTCODE")));
				bn.setDESTINATIONMARKING(StringFormat.nullString(rs.getString("DESTINATIONMARKING")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setRECEIVEDFROM(StringFormat.nullString(rs.getString("RECEIVEDFROM")));
				bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
				bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
				bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
				bn.setDMARKINGTO(StringFormat.nullString(rs.getString("MARKTODOWN")));
				bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("DMARKINGDATE")));
				bn.setMARKINGREMARK(StringFormat.nullString(rs.getString("MARKINGREMARK")));
				bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
				bn.setREASON(StringFormat.nullString(rs.getString("REASON")));
				bn.setIMARKINGTO(StringFormat.nullString(rs.getString("MARKINGLIST")));
				
				bn.setCOUNT(StringFormat.nullString(rs.getString("CNT")));
				bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
				//bn.setMARKINGSEQUENCE(StringFormat.nullString(rs.getString("MARKINGSEQUENCE")));
				arr.add(bn);
			}
	}else{
			
			
			if(sphrase.equalsIgnoreCase("0")){
				//System.out.println("------------------");
				
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condSub = condSub+" UPPER(A.SUBJECT) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}
			
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condFileNo = condFileNo+"UPPER(A.FILENO) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}
			
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condfilestatus1 = condfilestatus1+"UPPER((SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1)) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}
			
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condfilestatus2 = condfilestatus2+"UPPER((SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2)) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}
			
			
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				conddmark = conddmark+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNFILEMARKING M WHERE M.FMID=A.FMID" +
						" AND M.MARKINGSEQUENCE=1  AND M.MARKINGTYPE = 'D'))) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}
			
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condimark = condimark+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT Q.MARKINGTO FROM TRNFILEINTMARKING Q WHERE Q.FMID=A.FMID" +
				" AND Q.MARKINGSEQUENCE=(SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID) ))) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}

			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					regNoDes = regNoDes+" DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONNODES,A.REGISTRATIONNOORG) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}

			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					regDate = regDate+" TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') = '"+sArray[i]+"' "+orCond+" ";
				}
			}

			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length-1)?"OR":"";
					recvdFrom = recvdFrom+" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) = '"+sArray[i]+"' "+orCond+"";
				}
			}
			
			
			if(fdate.length()>0 && tdate.length()>0){
				condDate = "AND TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
				condConf += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			}
			}else{
				//System.out.println("h kjdhkjdhkjsdfhkjfgfjkh skjfhg-------------------------------");
				condSub = condSub+"UPPER(A.SUBJECT) LIKE UPPER('%"+sString+"%') OR ";
												
				condFileNo = condFileNo+"UPPER(A.FILENO) LIKE UPPER('%"+sString+"%') OR ";
							
				condfilestatus1 = condfilestatus1+"UPPER((SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1)) LIKE UPPER('%"+sString+"%') OR ";
								
				condfilestatus2 = condfilestatus2+"UPPER((SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2)) LIKE UPPER('%"+sString+"%') OR ";
							
				conddmark = conddmark+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNFILEMARKING M WHERE M.FMID=A.FMID" +
				" AND M.MARKINGSEQUENCE=1  AND M.MARKINGTYPE = 'D'))) LIKE UPPER('%"+sString+"%') OR ";
				
				condimark = condimark+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT Q.MARKINGTO FROM TRNFILEINTMARKING Q WHERE Q.FMID=A.FMID" +
				" AND Q.MARKINGSEQUENCE=(SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID) ))) LIKE UPPER('%"+sString+"%') OR ";

				regNoDes = regNoDes+"DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONNODES,A.REGISTRATIONNOORG) LIKE UPPER('%"+sString+"%') OR ";
				
				regDate = regDate+"TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') = '"+sString+"' OR ";

				recvdFrom = recvdFrom+"(SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) = '"+sString+"' ";
				
				
				if(fdate.length()>0 && tdate.length()>0){
					condDate = "AND TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
					condConf += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0' ": " AND ISCONF IN ('0','1') ";
				}
			}

/*			String strSQL = " SELECT DISTINCT A.FMID,A.REGISTRATIONNODES REGISTRATIONNO,"
				+ "(SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.FILESUBJECTCODEDES) "
				+ "  FILESUBJECTCODE,A.SUBJECT,"
				+ "TO_CHAR(A.REGISTRATIONDATEDES,'DD/MM/YYYY') REGISTRATIONDATE,"
				+ "A.FILENO,(SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE,"
				+ "(SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING,"
				+ "(SELECT (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=O.MARKINGTO) FROM TRNFILEINTMARKING O WHERE O.FMID=A.FMID "
				+ " AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=A.FMID)) IMARKINGTO,"
				+ " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1,"
				+ "(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2,"
				+ "NOL,"
				+ "(SELECT (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=O.MARKINGTO) FROM TRNFILEMARKING O WHERE O.FMID=A.FMID "
				+ " AND O.MARKINGSEQUENCE = 1 AND O.MARKINGTYPE = 'D') DMARKINGTO,"
				+ "(SELECT TO_CHAR(O.MARKINGDATE,'DD/MM/YYYY') FROM TRNFILEMARKING O WHERE O.FMID=A.FMID "
				+ " AND O.MARKINGSEQUENCE = 1 AND O.MARKINGTYPE = 'D') DMARKINGDATE "
				+ " FROM TRNFILEHDR A  WHERE ("+condSub+" "+condFileNo+" "+condfilestatus1+" "+condfilestatus2+" "+conddmark+" "+condimark+") "+condDate+" " +
					" ORDER BY A.REGISTRATIONDATEDES DESC";
*/
			
		String strSQL = " SELECT DISTINCT A.FMID, "+Qreply+" ISATTACHMENT," +
						" DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONNODES,A.REGISTRATIONNOORG) REGISTRATIONNO," +
						" TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY') REGISTRATIONDATE," +
						" CASE WHEN REFERENCETYPE = 'D' THEN 'Draft' WHEN REFERENCETYPE = 'A' THEN 'Approval' WHEN REFERENCETYPE = 'P' THEN 'Position' WHEN REFERENCETYPE = 'C' THEN 'Confidential' END REFERENCETYPE," +
						" A.FILENO,(SELECT LISTAGG((SELECT REFNO||'   '||TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') FROM TRNREFERENCE WHERE REFID=P.REFID), ',') WITHIN GROUP (ORDER BY REFID) FROM TRNFILEREF P WHERE P.FMID=A.FMID) PUCLIST," +
						" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DEPARTMENTCODE) DEPARTMENTCODE," +
						" (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=A.DESTINATIONMARKING) DESTINATIONMARKING," +
						" A.SUBJECT," +
						" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=RECEIVEDFROM) RECEIVEDFROM," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE=A.FILESTATUS1) FILESTATUS1," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE=A.FILESTATUS2) FILESTATUS2," +
						" REMARKS," +
						" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = B.MARKINGTO) MARKTODOWN," +
						" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY') DMARKINGDATE," +
						" B.MARKINGREMARK," +
						" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE=REPLYTYPE) REPLYTYPE," +
						" REASON,(SELECT LISTAGG((SELECT ROLENAME FROM MSTROLE WHERE ROLEID=I.MARKINGTO)||CHR(9)||CHR(9)||TO_CHAR(I.MARKINGDATE,'DD/MM/YYYY')||CHR(9)||CHR(9)||" +
					" TO_CHAR(I.CHANGEDATE,'DD/MM/YYYY'), '<BR>') WITHIN GROUP (ORDER BY I.MARKINGSEQUENCE) FROM TRNFILEINTMARKING I WHERE I.FMID=A.FMID) " +
					" MARKINGLIST," +
							"1 CNT,"+Freply+" REPLY " +
						" FROM TRNFILEHDR A, TRNFILEMARKING B" +
						" WHERE 1=1 AND A.FMID = B.FMID(+) AND DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.ROLEIDDES,A.ROLEIDORG) = '"+roleId+"'" +
						" AND ("+condSub+" "+condFileNo+" "+condfilestatus1+" "+condfilestatus2+" "+conddmark+" "+condimark+" "+regNoDes+" "+regDate+" "+recvdFrom+") "+condDate+" "+condConf+" " +
						
						"ORDER BY FMID,REGISTRATIONDATE DESC";
			dbCon.openConnection();
			log.debug(strSQL);
			//System.out.println("sunnnnnnnnnn : "+ strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				TrnFileHdr bn = new TrnFileHdr();
				
				bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
				bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
				bn.setREGISTRATIONNO(StringFormat.nullString(rs.getString("REGISTRATIONNO")));
				bn.setREGISTRATIONDATE(StringFormat.nullString(rs.getString("REGISTRATIONDATE")));
				bn.setREFERENCETYPE(StringFormat.nullString(rs.getString("REFERENCETYPE")));
				bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
				bn.setPUCLIST(StringFormat.nullString(rs.getString("PUCLIST")));
				bn.setDEPARTMENTCODE(StringFormat.nullString(rs.getString("DEPARTMENTCODE")));
				bn.setDESTINATIONMARKING(StringFormat.nullString(rs.getString("DESTINATIONMARKING")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setRECEIVEDFROM(StringFormat.nullString(rs.getString("RECEIVEDFROM")));
				bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
				bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
				bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
				bn.setDMARKINGTO(StringFormat.nullString(rs.getString("MARKTODOWN")));
				bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("DMARKINGDATE")));
				bn.setMARKINGREMARK(StringFormat.nullString(rs.getString("MARKINGREMARK")));
				bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
				bn.setREASON(StringFormat.nullString(rs.getString("REASON")));
				bn.setIMARKINGTO(StringFormat.nullString(rs.getString("MARKINGLIST")));
				
				bn.setCOUNT(StringFormat.nullString(rs.getString("CNT")));
				bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
				//bn.setMARKINGSEQUENCE(StringFormat.nullString(rs.getString("MARKINGSEQUENCE")));
				arr.add(bn);
			}
			}
			}
//	MR/1460/2012
		} catch (Exception e) {
			e.printStackTrace();
//			result = "0~" + e.getMessage();
			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return arr;
	}
/*	
	public ArrayList<TrnFileIntMarking> getIntMark(String fmId, String fdate, String tdate, String roleId) {
		DBConnection dbCon = new DBConnection();
		ArrayList<TrnFileIntMarking> arr = new ArrayList<TrnFileIntMarking>();
		try {
			String strSQL = " SELECT DISTINCT (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=B.MARKINGTO) MARKINGTO," +
							" TO_CHAR(B.MARKINGDATE,'DD/MM/YYYY') MARKINGDATE,"+
							" TO_CHAR(B.CHANGEDATE,'DD/MM/YYYY') CHANGEDATE," +
							" MARKINGSEQUENCE"+
							" FROM TRNFILEHDR A, TRNFILEINTMARKING B"+
							" WHERE 1=1 AND A.FMID = B.FMID AND A.FMID = "+fmId+""+
							" AND TO_DATE(TO_CHAR(DECODE(A.DESTINATIONMARKING,'"+roleId+"',A.REGISTRATIONDATEDES,A.REGISTRATIONDATEORG),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND TO_DATE('"+tdate+"','DD/MM/YYYY')"+
							" ORDER BY MARKINGSEQUENCE ";
			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				TrnFileIntMarking bn = new TrnFileIntMarking();
				bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
				bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
				bn.setCHANGEDATE(StringFormat.nullString(rs.getString("CHANGEDATE")));
				arr.add(bn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return arr;
	}
*/	
	public String delReference(String fmid, String refid) {

		String isDataSaved = "0~Can not delete.";
		DBConnection con = new DBConnection();
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		try {
			con.openConnection();
			String strSQL = "";

			strSQL = " DELETE FROM TRNFILEREF WHERE FMID = ? AND REFID = ?";
			log.debug(strSQL);
			
			ps = con.setPrepStatement(strSQL);
			ps.setString(1, fmid);
			ps.setString(2, refid);
			ps.executeUpdate();
			
			strSQL = " UPDATE TRNREFERENCE SET FILENO='',IMARKINGTO='',FILESTATUS1='',FILESTATUS2='',REPLYTYPE='',"
				+ "REASON='',DMARKINGTO='',DMARKINGDATE='',REGISTRATIONDATE=''"
				+ " WHERE REFID = ?";
			
			ps = con.setPrepStatement(strSQL);
			ps.setString(1, refid);
			ps.executeUpdate();

			
/*			strSQL = " UPDATE TRNREFERENCE SET FILENO='',IMARKINGTO='',FILESTATUS1='',FILESTATUS2='',REPLYTYPE='',"
					+ "REASON='',DMARKINGTO='',DMARKINGDATE='',SUBJECTCODE='',SUBJECT='',REGISTRATIONDATE=''"
					+ " WHERE REFID = '" + refid + "'";
*/
			log.debug(strSQL);
			//con.update(strSQL);

			isDataSaved = "1~Deleted Successfully.";
		} catch (Exception e) {
			con.rollback();
			isDataSaved = "0~Can not delete.";
			log.fatal(e, e);
		} finally {
			con.closeConnection();
		}
		return isDataSaved;

	}

	public int RefNoValidate(String refnumber, String refId) {

		int i = 0;
		DBConnection con = new DBConnection();
		PreparedStatement ps = null;
		try {
			con.openConnection();
			String strSQL = " SELECT A.REFNO,A.REFID FROM TRNREFERENCE A WHERE UPPER(A.REFNO||' ( '||TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY')||' ) ') = UPPER(?) " + "AND A.REFID = ?";
			log.debug(strSQL);
			
			ps = con.setPrepStatement(strSQL);
			ps.setString(1, refnumber);
			ps.setString(2, refId);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				i = 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			con.closeConnection();
		}

		return i;

	}

	public TrnFileHdr getFileData(String fmId, String roleId, String isReply) {
		TrnFileHdr fileBean = null;
		String Qreply = "";
		PreparedStatement ps = null;
		if(isReply.equalsIgnoreCase("1")){
			Qreply=isReply.equalsIgnoreCase("0")? "''": "REPLY";
		}
		else{
		
		Qreply="''";
		}
		DBConnection con = new DBConnection();
		try {

			// Getting the Header Table Details
			String strSQL = " SELECT FMID,TO_CHAR(REGISTRATIONDATEORG,'DD/MM/YYYY') REGISTRATIONDATEORG,REFERENCETYPE,FILENO,DEPARTMENTCODE,FILESTATUS1,FILESTATUS2,SENTTO,NOL,DESTINATIONMARKING,"
					+ "RECEIVEDFROM,REMARKS,LINKFILENO1,LINKFILENO2,LINKFILENO3,LINKFILENO4,EOFFICENO,FILECOUNTERORG,FILECOUNTERDES,ROLEIDORG,LOGINID,"
					+ "CHANGEDATE,TO_CHAR(REGISTRATIONDATEDES,'DD/MM/YYYY') REGISTRATIONDATEDES,FILESUBJECTCODEORG,FILESUBJECTCODEDES,ROLEIDDES,"
					+ "TENUREID,REPLYTYPE,REASON,SUBJECT,REGISTRATIONNODES,REGISTRATIONNOORG,PROPOSEDPATH	 FROM TRNFILEHDR WHERE FMID = ? ORDER BY FMID ";

			log.debug(strSQL);
			con.openConnection();
			ps = con.setPrepStatement(strSQL);
			ps.setString(1, fmId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				fileBean = new TrnFileHdr();

				fileBean.setFMID(StringFormat.nullString(rs.getString("FMID")));
				// fileBean.setTENUREID(StringFormat.nullString(rs.getString(
				// "TENUREID")));
				fileBean.setREGISTRATIONDATEORG(StringFormat.nullString(rs
						.getString("REGISTRATIONDATEORG")));
				// fileBean.setROLEID(StringFormat.nullString(rs.getString(
				// "ROLEID")));
				fileBean.setREFERENCETYPE(StringFormat.nullString(rs
						.getString("REFERENCETYPE")));
				fileBean.setFILENO(StringFormat.nullString(rs
						.getString("FILENO")));
				fileBean.setDEPARTMENTCODE(StringFormat.nullString(rs
						.getString("DEPARTMENTCODE")));
				fileBean.setEOFFICEFILENO(StringFormat.nullString(rs
						.getString("EOFFICENO")));
				fileBean.setFILESTATUS1(StringFormat.nullString(rs
						.getString("FILESTATUS1")));
				fileBean.setFILESTATUS2(StringFormat.nullString(rs
						.getString("FILESTATUS2")));
				
				fileBean.setSENTTO(StringFormat.nullString(rs
						.getString("SENTTO")));
				fileBean.setNOL(StringFormat.nullString(rs
						.getString("NOL")));
				fileBean.setDESTINATIONMARKING(StringFormat.nullString(rs
						.getString("DESTINATIONMARKING")));
				fileBean.setRECEIVEDFROM(StringFormat.nullString(rs
						.getString("RECEIVEDFROM")));
				fileBean.setREMARKS(StringFormat.nullString(rs
						.getString("REMARKS")));
				fileBean.setLINKFILENO1(StringFormat.nullString(rs
						.getString("LINKFILENO1")));
				fileBean.setLINKFILENO2(StringFormat.nullString(rs
						.getString("LINKFILENO2")));
				fileBean.setLINKFILENO3(StringFormat.nullString(rs
						.getString("LINKFILENO3")));
				fileBean.setLINKFILENO4(StringFormat.nullString(rs
						.getString("LINKFILENO4")));
				fileBean.setFILECOUNTERORG(StringFormat.nullString(rs
						.getString("FILECOUNTERORG")));
				fileBean.setFILECOUNTERDES(StringFormat.nullString(rs
						.getString("FILECOUNTERDES")));
				fileBean.setROLEIDORG(StringFormat.nullString(rs
						.getString("ROLEIDORG")));
				fileBean.setLOGINID(StringFormat.nullString(rs
						.getString("LOGINID")));
				fileBean.setCHANGEDATE(StringFormat.nullString(rs
						.getString("CHANGEDATE")));
				fileBean.setREGISTRATIONDATEDES(StringFormat.nullString(rs
						.getString("REGISTRATIONDATEDES")));
				fileBean.setFILESUBJECTCODEORG(StringFormat.nullString(rs
						.getString("FILESUBJECTCODEORG")));
				fileBean.setFILESUBJECTCODEDES(StringFormat.nullString(rs
						.getString("FILESUBJECTCODEDES")));
				fileBean.setROLEIDDES(StringFormat.nullString(rs
						.getString("ROLEIDDES")));
				fileBean.setTENUREID(StringFormat.nullString(rs
						.getString("TENUREID")));
				fileBean.setREPLYTYPE(StringFormat.nullString(rs
						.getString("REPLYTYPE")));
				fileBean.setREASON(StringFormat.nullString(rs
						.getString("REASON")));
				fileBean.setSUBJECT(StringFormat.nullString(rs
						.getString("SUBJECT")));
				fileBean.setREGISTRATIONNODES(StringFormat.nullString(rs
						.getString("REGISTRATIONNODES")));
				fileBean.setREGISTRATIONNOORG(StringFormat.nullString(rs
						.getString("REGISTRATIONNOORG")));
				fileBean.setPROPOSEDPATH(StringFormat.nullString(rs
						.getString("PROPOSEDPATH")));

				if (roleId.equalsIgnoreCase(fileBean.getDESTINATIONMARKING())) {
					fileBean.setREGISTRATIONDATE(fileBean
							.getREGISTRATIONDATEDES());
					fileBean.setREGISTRATIONNO(fileBean.getREGISTRATIONNODES());
					fileBean.setFILESUBJECTCODE(fileBean
							.getFILESUBJECTCODEDES());
					fileBean.setFILECOUNTER(fileBean.getFILECOUNTERDES());
				} else {
					fileBean.setREGISTRATIONDATE(fileBean
							.getREGISTRATIONDATEORG());
					fileBean.setREGISTRATIONNO(fileBean.getREGISTRATIONNOORG());
					fileBean.setFILESUBJECTCODE(fileBean
							.getFILESUBJECTCODEORG());
					fileBean.setFILECOUNTER(fileBean.getFILECOUNTERORG());
				}

			}
			rs.close();
			
			String strSQLReply = " SELECT FMID,"+Qreply+" REPLY FROM TRNFILEREPLY WHERE FMID = ? ORDER BY FMID ";

		log.debug(strSQLReply);
		con.openConnection();
		
		ps = con.setPrepStatement(strSQLReply);
		ps.setString(1, fmId);
		
		ResultSet rsReply = ps.executeQuery();
		while (rsReply.next()) {
			fileBean.setREPLY(StringFormat.nullString(rsReply.getString("REPLY")));
		}
		rsReply.close();
			// Getting the Detail Marking Table Details
			String strSQLMarking = " SELECT A.FMID,A.REFID,"
					+ "(SELECT M.REFNO FROM TRNREFERENCE M WHERE M.REFID=A.REFID) REFNO,"
					+ "(SELECT TO_CHAR(M.INCOMINGDATE,'DD/MM/YYYY') FROM TRNREFERENCE M WHERE M.REFID=A.REFID) INCOMINGDATE,"
					+ "(SELECT N.REFERENCENAME FROM TRNREFERENCE N WHERE N.REFID=A.REFID) REFERENCENAME,"
					+ "(SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=(SELECT DISTINCT O.SUBJECTCODE FROM TRNREFERENCE O WHERE O.REFID=A.REFID)) SUBJECTCODE,"
					+ "(SELECT DISTINCT P.SUBJECT FROM TRNREFERENCE P WHERE P.REFID=A.REFID) SUBJECT,"
					//new code added for compliance
					+ "(SELECT DISTINCT Z.COMPLIANCE FROM TRNREFERENCE Z WHERE Z.REFID=A.REFID) COMPLIANCE,"
					//end f new code
					+ "(SELECT DISTINCT Z.COMPLIANCEREMARKS FROM TRNREFERENCE Z WHERE Z.REFID=A.REFID) COMPLIANCEREMARKS,"
					+ "(SELECT (SELECT E.ROLENAME FROM MSTROLE E WHERE E.ROLEID=O.MARKINGTO)||','||(SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = O.ACTION)||','||O.ACTIONDATE FROM TRNMARKING O WHERE O.REFID=A.REFID "
					+ " AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNMARKING P WHERE P.REFID=A.REFID)) STATUS," +
					" (SELECT S.VIPSTATUS FROM TRNREFERENCE S WHERE S.REFID=A.REFID) VIPSTATUS," +
					" (SELECT R.STATENAME FROM MSTSTATE R WHERE R.STATECODE=(SELECT T.STATECODE FROM TRNREFERENCE T WHERE T.REFID=A.REFID)) STATENAME"
					+ "  FROM TRNFILEREF A WHERE A.FMID = ?";

			log.debug(strSQLMarking);
			ps = con.setPrepStatement(strSQLMarking);
			ps.setString(1, fmId);
			ResultSet rsMarking0 = ps.executeQuery();

			ArrayList<TrnFileRef> refBeanList = new ArrayList<TrnFileRef>();
			while (rsMarking0.next()) {
				TrnFileRef refBean = new TrnFileRef();

				// markingBean.setREFID(StringFormat.nullString(rsMarking.
				// getString("REFID")));
				refBean.setREFID(StringFormat.nullString(rsMarking0
						.getString("REFID")));
				refBean.setFMID(StringFormat.nullString(rsMarking0
						.getString("FMID")));
				refBean.setREFNO(StringFormat.nullString(rsMarking0
						.getString("REFNO")));
				refBean.setINCOMINGDATE(StringFormat.nullString(rsMarking0
						.getString("INCOMINGDATE")));
				refBean.setREFERENCENAME(StringFormat.nullString(rsMarking0
						.getString("REFERENCENAME")));
				refBean.setSUBJECTCODE(StringFormat.nullString(rsMarking0
						.getString("SUBJECTCODE")));
				refBean.setSUBJECT(StringFormat.nullString(rsMarking0
						.getString("SUBJECT")));
				refBean.setSTATUS(StringFormat.nullString(rsMarking0
						.getString("STATUS")));
				refBean.setVIPSTATUS(StringFormat.nullString(rsMarking0
						.getString("VIPSTATUS")));
				refBean.setSTATENAME(StringFormat.nullString(rsMarking0
						.getString("STATENAME")));
				
				
				refBean.setCompliance(StringFormat.nullString(rsMarking0
						.getString("COMPLIANCE")));
				refBean.setComplianceremarks(StringFormat.nullString(rsMarking0
						.getString("COMPLIANCEREMARKS")));
				
				

				refBeanList.add(refBean);

			}
			rsMarking0.close();

			// now add the marking arraylist to the trnreference bean
			fileBean.setRefBeanList(refBeanList);

			// Getting the Detail Marking Table Details
			strSQLMarking = " SELECT FMID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO, TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') MARKINGDATE, MARKINGREMARK, "
					+ " SUBJECTCODE, SUBJECT,MARKINGTYPE "
					+ " FROM TRNFILEMARKING WHERE FMID =?  AND MARKINGSEQUENCE =1 "
					+ " AND MARKINGTYPE = 'U' ORDER BY MARKINGSEQUENCE ";

			log.debug(strSQLMarking);
			ps = con.setPrepStatement(strSQLMarking);
			ps.setString(1, fmId);
			ResultSet rsMarking = ps.executeQuery();
			//ResultSet rsMarking = con.select(strSQLMarking);

			ArrayList<TrnFileMarking> markingBeanUList = new ArrayList<TrnFileMarking>();
			while (rsMarking.next()) {
				TrnFileMarking markingBean = new TrnFileMarking();

				// markingBean.setREFID(StringFormat.nullString(rsMarking.
				// getString("REFID")));
				markingBean.setMARKINGSEQUENCE(StringFormat
						.nullString(rsMarking.getString("MARKINGSEQUENCE")));
				markingBean.setMARKINGFROM(StringFormat.nullString(rsMarking
						.getString("MARKINGFROM")));
				markingBean.setMARKINGTO(StringFormat.nullString(rsMarking
						.getString("MARKINGTO")));
				markingBean.setMARKINGDATE(StringFormat.nullString(rsMarking
						.getString("MARKINGDATE")));
				markingBean.setMARKINGREMARK(StringFormat.nullString(rsMarking
						.getString("MARKINGREMARK")));
				markingBean.setSUBJECTCODE(StringFormat.nullString(rsMarking
						.getString("SUBJECTCODE")));
				markingBean.setSUBJECT(StringFormat.nullString(rsMarking
						.getString("SUBJECT")));
				markingBean.setMARKINGTYPE(StringFormat.nullString(rsMarking
						.getString("MARKINGTYPE")));

				markingBeanUList.add(markingBean);

			}
			rsMarking.close();

			// now add the marking arraylist to the trnreference bean
			fileBean.setMarkingBeanUList(markingBeanUList);

			// Getting the Detail Marking Table Details
			strSQLMarking = " SELECT FMID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO, TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') MARKINGDATE, MARKINGREMARK, "
					+ " SUBJECTCODE, SUBJECT,MARKINGTYPE "
					+ " FROM TRNFILEMARKING WHERE FMID =?  AND MARKINGSEQUENCE =1 "
					+ " AND MARKINGTYPE = 'D' ORDER BY MARKINGSEQUENCE ";

			log.debug(strSQLMarking);
			ps = con.setPrepStatement(strSQLMarking);
			ps.setString(1, fmId);
			ResultSet rsMarking1 = ps.executeQuery();
			//ResultSet rsMarking1 = con.select(strSQLMarking);

			ArrayList<TrnFileMarking> markingBeanDList = new ArrayList<TrnFileMarking>();
			while (rsMarking1.next()) {
				TrnFileMarking markingBean = new TrnFileMarking();

				// markingBean.setREFID(StringFormat.nullString(rsMarking.
				// getString("REFID")));
				markingBean.setMARKINGSEQUENCE(StringFormat
						.nullString(rsMarking1.getString("MARKINGSEQUENCE")));
				markingBean.setMARKINGFROM(StringFormat.nullString(rsMarking1
						.getString("MARKINGFROM")));
				markingBean.setMARKINGTO(StringFormat.nullString(rsMarking1
						.getString("MARKINGTO")));
				markingBean.setMARKINGDATE(StringFormat.nullString(rsMarking1
						.getString("MARKINGDATE")));
				markingBean.setMARKINGREMARK(StringFormat.nullString(rsMarking1
						.getString("MARKINGREMARK")));
				markingBean.setSUBJECTCODE(StringFormat.nullString(rsMarking1
						.getString("SUBJECTCODE")));
				markingBean.setSUBJECT(StringFormat.nullString(rsMarking1
						.getString("SUBJECT")));
				markingBean.setMARKINGTYPE(StringFormat.nullString(rsMarking1
						.getString("MARKINGTYPE")));

				markingBeanDList.add(markingBean);

			}
			rsMarking1.close();

			// now add the marking arraylist to the trnreference bean
			fileBean.setMarkingBeanDList(markingBeanDList);

			// Getting the Detail Internal Marking Table Details
			strSQLMarking = " SELECT FMID,MARKINGSEQUENCE,FILECOUNTER,MARKINGTO,TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') MARKINGDATE,LOGINID,TO_CHAR(CHANGEDATE, 'DD/MM/YYYY') CHANGEDATE,	 "
					+ " MARKINGFROM,ACTION,ACTIONDATE,MARKINGREMARK FROM TRNFILEINTMARKING WHERE FMID = ?  ORDER BY MARKINGSEQUENCE ";

			log.debug(strSQLMarking);
			ps = con.setPrepStatement(strSQLMarking);
			ps.setString(1, fmId);
			ResultSet rsMarking2 = ps.executeQuery();
			//ResultSet rsMarking2 = con.select(strSQLMarking);

			ArrayList<TrnFileIntMarking> IntmarkingBeanList = new ArrayList<TrnFileIntMarking>();
			while (rsMarking2.next()) {
				TrnFileIntMarking intmarkingBean = new TrnFileIntMarking();

				// markingBean.setREFID(StringFormat.nullString(rsMarking.
				// getString("REFID")));
				intmarkingBean.setMARKINGSEQUENCE(StringFormat
						.nullString(rsMarking2.getString("MARKINGSEQUENCE")));
				intmarkingBean.setFILECOUNTER(StringFormat
						.nullString(rsMarking2.getString("FILECOUNTER")));
				intmarkingBean.setMARKINGTO(StringFormat.nullString(rsMarking2
						.getString("MARKINGTO")));
				intmarkingBean.setMARKINGDATE(StringFormat
						.nullString(rsMarking2.getString("MARKINGDATE")));
				intmarkingBean.setLOGINID(StringFormat.nullString(rsMarking2
						.getString("LOGINID")));
				intmarkingBean.setCHANGEDATE(StringFormat.nullString(rsMarking2
						.getString("CHANGEDATE")));
				intmarkingBean.setMARKINGFROM(StringFormat
						.nullString(rsMarking2.getString("MARKINGFROM")));
				intmarkingBean.setACTION(StringFormat.nullString(rsMarking2
						.getString("ACTION")));
				intmarkingBean.setACTIONDATE(StringFormat.nullString(rsMarking2
						.getString("ACTIONDATE")));
				intmarkingBean.setMARKINGREMARK(StringFormat
						.nullString(rsMarking2.getString("MARKINGREMARK")));

				IntmarkingBeanList.add(intmarkingBean);

			}
			rsMarking2.close();

			// now add the marking arraylist to the trnreference bean
			fileBean.setIntmarkingBeanList(IntmarkingBeanList);

		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {

			con.closeConnection();
		}

		return fileBean;

	}
	
	public ArrayList<TrnFileIntMarking> getIntMarkingData(String roleId,String type) {
		ArrayList<TrnFileIntMarking> markingBeanList = new ArrayList<TrnFileIntMarking>();
		PreparedStatement ps = null;
		DBConnection con = new DBConnection();
		try {
			String strSQLMarking = "";
			if(type.equalsIgnoreCase("P")){
			 strSQLMarking =" SELECT A.FMID,(SELECT M.FILENO FROM TRNFILEHDR M WHERE M.FMID=A.FMID) FILENO, MARKINGSEQUENCE,"
				+ " GETROLENAME(MARKINGFROM) MARKINGFROM,"
				+ " MARKINGTO, TO_CHAR(MARKINGDATE, 'DD/MM/YYYY HH24:MI') MARKINGDATE,  MARKINGREMARK," +
				"(SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=(SELECT M.FILESUBJECTCODEDES FROM TRNFILEHDR M WHERE M.FMID=A.FMID)) SUBJECTCODE," +
				"(SELECT M.SUBJECT FROM TRNFILEHDR M WHERE M.FMID=A.FMID) SUBJECT, ACTION,"
				+ " TO_CHAR(ACTIONDATE, 'DD/MM/YYYY HH24:MI') ACTIONDATE,TO_CHAR(CHANGEDATE, 'DD/MM/YYYY HH24:MI') CHANGEDATE FROM TRNFILEINTMARKING A  WHERE A.MARKINGTO= ? AND A.CHANGEDATE IS NULL"
				//+ "AND A.MARKINGTO <> (SELECT X.DESTINATIONMARKING FROM TRNFILEHDR X WHERE X.FMID= A.FMID)" 
				+"";
			}else if(type.equalsIgnoreCase("R")){
				strSQLMarking =" SELECT A.FMID,(SELECT M.FILENO FROM TRNFILEHDR M WHERE M.FMID=A.FMID) FILENO, MARKINGSEQUENCE,"
					+ " GETROLENAME(MARKINGFROM) MARKINGFROM,"
					+ " MARKINGTO, TO_CHAR(MARKINGDATE, 'DD/MM/YYYY HH24:MI') MARKINGDATE,  MARKINGREMARK," +
					"(SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=(SELECT M.FILESUBJECTCODEDES FROM TRNFILEHDR M WHERE M.FMID=A.FMID)) SUBJECTCODE," +
					"(SELECT M.SUBJECT FROM TRNFILEHDR M WHERE M.FMID=A.FMID) SUBJECT, ACTION,"
					+ " TO_CHAR(ACTIONDATE, 'DD/MM/YYYY HH24:MI') ACTIONDATE,TO_CHAR(CHANGEDATE, 'DD/MM/YYYY HH24:MI') CHANGEDATE FROM TRNFILEINTMARKING A  WHERE A.MARKINGTO= ? AND A.CHANGEDATE IS NOT NULL"
					//+ "AND A.MARKINGTO <> (SELECT X.DESTINATIONMARKING FROM TRNFILEHDR X WHERE X.FMID= A.FMID)" 
					+"";
			}else{
				strSQLMarking =" SELECT A.FMID,(SELECT M.FILENO FROM TRNFILEHDR M WHERE M.FMID=A.FMID) FILENO, MARKINGSEQUENCE,"
					+ " GETROLENAME(MARKINGFROM) MARKINGFROM,"
					+ " MARKINGTO, TO_CHAR(MARKINGDATE, 'DD/MM/YYYY HH24:MI') MARKINGDATE,  MARKINGREMARK," +
					"(SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=(SELECT M.FILESUBJECTCODEDES FROM TRNFILEHDR M WHERE M.FMID=A.FMID)) SUBJECTCODE," +
					"(SELECT M.SUBJECT FROM TRNFILEHDR M WHERE M.FMID=A.FMID) SUBJECT, ACTION,"
					+ " TO_CHAR(ACTIONDATE, 'DD/MM/YYYY HH24:MI') ACTIONDATE,TO_CHAR(CHANGEDATE, 'DD/MM/YYYY HH24:MI') CHANGEDATE FROM TRNFILEINTMARKING A  WHERE A.MARKINGTO= ?";
			}
			log.debug(strSQLMarking);
			con.openConnection();
			ps = con.setPrepStatement(strSQLMarking);
			ps.setString(1, roleId);
			ResultSet rsMarking = ps.executeQuery();
			while (rsMarking.next()) {
				TrnFileIntMarking markingBean = new TrnFileIntMarking();

				markingBean.setFMID(StringFormat.nullString(rsMarking
						.getString("FMID")));
				markingBean.setFILENO(StringFormat.nullString(rsMarking
						.getString("FILENO")));
				markingBean.setMARKINGSEQUENCE(StringFormat
						.nullString(rsMarking.getString("MARKINGSEQUENCE")));
				markingBean.setMARKINGFROM(StringFormat.nullString(rsMarking
						.getString("MARKINGFROM")));
				markingBean.setMARKINGTO(StringFormat.nullString(rsMarking
						.getString("MARKINGTO")));
				markingBean.setMARKINGDATE(StringFormat.nullString(rsMarking
						.getString("MARKINGDATE")));
				markingBean.setMARKINGREMARK(StringFormat.nullString(rsMarking
						.getString("MARKINGREMARK")));
				markingBean.setSUBJECTCODE(StringFormat.nullString(rsMarking
						.getString("SUBJECTCODE")));
				markingBean.setSUBJECT(StringFormat.nullString(rsMarking
						.getString("SUBJECT")));
				markingBean.setACTION(StringFormat.nullString(rsMarking
						.getString("ACTION")));
				markingBean.setACTIONDATE(StringFormat.nullString(rsMarking
						.getString("ACTIONDATE")));
				markingBean.setCHANGEDATE(StringFormat.nullString(rsMarking
						.getString("CHANGEDATE")));
				

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

	public ArrayList<TrnFileMarking> getInboxData(String roleId) {
		ArrayList<TrnFileMarking> markingBeanList = new ArrayList<TrnFileMarking>();
		PreparedStatement ps = null;
		DBConnection con = new DBConnection();
		try {

			String strSQLMarking = "  SELECT A.FMID,(SELECT M.FILENO FROM TRNFILEHDR M WHERE M.FMID=A.FMID) FILENO, MARKINGSEQUENCE," +
								   " GETROLENAME(MARKINGFROM) MARKINGFROM, MARKINGTO, TO_CHAR(MARKINGDATE, 'DD/MM/YYYY HH24:MI') MARKINGDATE," +
								   " MARKINGREMARK, SUBJECTCODE, SUBJECT, ACTION, TO_CHAR(ACTIONDATE, 'DD/MM/YYYY HH24:MI') ACTIONDATE, MARKINGTYPE" +
								   " FROM TRNFILEMARKING A  WHERE A.MARKINGTO= ? AND A.ACTION ='FOW'" +
								   " AND A.MARKINGSEQUENCE = DECODE(A.MARKINGTYPE,'U',(SELECT MAX(X.MARKINGSEQUENCE) FROM TRNFILEMARKING X WHERE X.FMID= A.FMID AND X.MARKINGTYPE= 'U' )," +
								   " (SELECT MAX(X.MARKINGSEQUENCE) FROM TRNFILEMARKING X WHERE X.FMID= A.FMID  AND X.MARKINGTYPE= 'D' ))";
			log.debug(strSQLMarking);
			con.openConnection();
			ps = con.setPrepStatement(strSQLMarking);
			ps.setString(1, roleId);
			ResultSet rsMarking = ps.executeQuery();
			while (rsMarking.next()) {
				TrnFileMarking markingBean = new TrnFileMarking();

				markingBean.setFMID(StringFormat.nullString(rsMarking
						.getString("FMID")));
				markingBean.setFILENO(StringFormat.nullString(rsMarking
						.getString("FILENO")));
				markingBean.setMARKINGSEQUENCE(StringFormat
						.nullString(rsMarking.getString("MARKINGSEQUENCE")));
				markingBean.setMARKINGFROM(StringFormat.nullString(rsMarking
						.getString("MARKINGFROM")));
				markingBean.setMARKINGTO(StringFormat.nullString(rsMarking
						.getString("MARKINGTO")));
				markingBean.setMARKINGDATE(StringFormat.nullString(rsMarking
						.getString("MARKINGDATE")));
				markingBean.setMARKINGREMARK(StringFormat.nullString(rsMarking
						.getString("MARKINGREMARK")));
				markingBean.setSUBJECTCODE(StringFormat.nullString(rsMarking
						.getString("SUBJECTCODE")));
				markingBean.setSUBJECT(StringFormat.nullString(rsMarking
						.getString("SUBJECT")));
				markingBean.setACTION(StringFormat.nullString(rsMarking
						.getString("ACTION")));
				markingBean.setACTIONDATE(StringFormat.nullString(rsMarking
						.getString("ACTIONDATE")));
				markingBean.setMARKINGTYPE(StringFormat.nullString(rsMarking
						.getString("MARKINGTYPE")));

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

	public ArrayList<TrnFileMarking> getOutBoxData(String roleId) {
		ArrayList<TrnFileMarking> markingOutBeanList = new ArrayList<TrnFileMarking>();
		PreparedStatement ps = null;
		DBConnection con = new DBConnection();
		try {

			String strSQLMarking = " SELECT A.FMID,(SELECT M.FILENO FROM TRNFILEHDR M WHERE M.FMID=A.FMID) FILENO, MARKINGSEQUENCE,"
					+ " GETROLENAME(MARKINGFROM) MARKINGFROM,"
					+ " MARKINGTO, TO_CHAR(MARKINGDATE, 'DD/MM/YYYY HH24:MI') MARKINGDATE,  MARKINGREMARK, SUBJECTCODE, SUBJECT, ACTION,"
					+ " TO_CHAR(ACTIONDATE, 'DD/MM/YYYY HH24:MI') ACTIONDATE,MARKINGTYPE FROM TRNFILEMARKING A  WHERE A.MARKINGTO= ? "
					+ " AND A.ACTION ='RCD' AND A.MARKINGSEQUENCE = DECODE(A.MARKINGTYPE,'U',(SELECT MAX(X.MARKINGSEQUENCE) FROM TRNFILEMARKING X WHERE X.FMID= A.FMID AND X.MARKINGTYPE= 'U' )," +
					"(SELECT MAX(X.MARKINGSEQUENCE) FROM TRNFILEMARKING X WHERE X.FMID= A.FMID  AND X.MARKINGTYPE= 'D' )) "
					+ "AND A.MARKINGTO <> (SELECT X.DESTINATIONMARKING FROM TRNFILEHDR X WHERE X.FMID= A.FMID)";

			log.debug(strSQLMarking);
			con.openConnection();
			ps = con.setPrepStatement(strSQLMarking);
			ps.setString(1, roleId);
			ResultSet rsMarking = ps.executeQuery();
			while (rsMarking.next()) {
				TrnFileMarking markingBean = new TrnFileMarking();

				markingBean.setFMID(StringFormat.nullString(rsMarking
						.getString("FMID")));
				markingBean.setFILENO(StringFormat.nullString(rsMarking
						.getString("FILENO")));
				markingBean.setMARKINGSEQUENCE(StringFormat
						.nullString(rsMarking.getString("MARKINGSEQUENCE")));
				markingBean.setMARKINGFROM(StringFormat.nullString(rsMarking
						.getString("MARKINGFROM")));
				markingBean.setMARKINGTO(StringFormat.nullString(rsMarking
						.getString("MARKINGTO")));
				markingBean.setMARKINGDATE(StringFormat.nullString(rsMarking
						.getString("MARKINGDATE")));
				markingBean.setMARKINGREMARK(StringFormat.nullString(rsMarking
						.getString("MARKINGREMARK")));
				markingBean.setSUBJECTCODE(StringFormat.nullString(rsMarking
						.getString("SUBJECTCODE")));
				markingBean.setSUBJECT(StringFormat.nullString(rsMarking
						.getString("SUBJECT")));
				markingBean.setACTION(StringFormat.nullString(rsMarking
						.getString("ACTION")));
				markingBean.setACTIONDATE(StringFormat.nullString(rsMarking
						.getString("ACTIONDATE")));
				markingBean.setMARKINGTYPE(StringFormat.nullString(rsMarking
						.getString("MARKINGTYPE")));
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

	public ArrayList<TrnMarking> setFileOutBoxAction(String fmId,
			String outBoxMarkingFrom, String outBoxMarkTo,
			String outboxMarkingRemark, String subjectCode, String subject,
			String action, String markingtype) {

		ArrayList<TrnMarking> markingBeanList = new ArrayList<TrnMarking>();
		DBConnection dbcon = new DBConnection();
		try {
			dbcon.openConnection();
			String outBoxMarkingSequence = getNextMarkingSequence(dbcon,
					 fmId, markingtype);
			String markingQuery = " INSERT INTO TRNFILEMARKING (FMID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO, MARKINGDATE, MARKINGREMARK, "
					+ " SUBJECTCODE, SUBJECT, ACTION, ACTIONDATE,MARKINGTYPE) "
					+ " VALUES (" + " '"
					+ fmId
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
					+ " '"
					+ subjectCode
					+ "',"
					+ " '"
					+ subject
					+ "', "
					+ " '"
					+ action + "', " + " SYSDATE,'" + markingtype + "'" + ")";

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

	public String setFileInboxAction(String fmId, String action, String tenureId,
			String loginAsroleid, String loginAsrolename, String markingType) {

		String updatestatus = "Save operation Successful";

		DBConnection dbcon = new DBConnection();
		try {
			String strSQLMarking = " INSERT INTO TRNFILEMARKING (FMID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO, MARKINGDATE, MARKINGREMARK,"
					+ " SUBJECTCODE, SUBJECT, ACTION, ACTIONDATE,MARKINGTYPE)"
					+ " SELECT FMID, MARKINGSEQUENCE +1, MARKINGFROM, MARKINGTO, MARKINGDATE, MARKINGREMARK,"
					+ " SUBJECTCODE, SUBJECT, '"
					+ action
					+ "', SYSDATE,MARKINGTYPE"
					+ " FROM TRNFILEMARKING A WHERE FMID = '"
					+ fmId
					+ "' "
					+ " AND A. MARKINGSEQUENCE = (SELECT MAX(B.MARKINGSEQUENCE) "
					+ " FROM TRNFILEMARKING B WHERE B.FMID = A.FMID AND B.MARKINGTYPE='"+markingType+"')  AND A.MARKINGTYPE='"+markingType+"' ";

			log.debug(strSQLMarking);
			dbcon.openConnection();
			dbcon.update(strSQLMarking);

			String rsDesQ = " SELECT * FROM TRNFILEHDR WHERE FMID='" + fmId
					+ "' " + "AND DESTINATIONMARKING = '" + loginAsroleid + "'";

			ResultSet rsDes = dbcon.select(rsDesQ);
			String fcdes = "";
			String regNodes = "";

			if (rsDes.next()) {
				// update trnfilehdr for tenureid === destination marking
				fcdes = getNextFileCountPst(dbcon, tenureId, loginAsroleid);
				regNodes = loginAsrolename + "/" + fcdes + "/"
						+ Calendar.getInstance().get(Calendar.YEAR);

				String markingQuery = " UPDATE TRNFILEHDR SET "
						+ " FILECOUNTERDES = '" + fcdes + "', "
						+ " REGISTRATIONNODES = '" + regNodes + "', "
						+ " REGISTRATIONDATEDES = SYSDATE, " + " ROLEIDDES	= '"
						+ loginAsroleid + "' " + " WHERE FMID =  '" + fmId
						+ "'";

				log.debug(markingQuery);
				dbcon.update(markingQuery);
				
				UpdateNextFileCount(dbcon, tenureId,loginAsroleid);
				updatestatus = "Registarion No. of File Generated is "+regNodes;

			}

		} catch (SQLException e) {
			log.fatal(e, e);
			dbcon.rollback();
		} finally {

			dbcon.closeConnection();
		}
		return updatestatus;
	}

	public ArrayList<CommonBean> getSearchFileInboxOutBox(String fileNo,
			String subjectCode, String subject, String markingFrom,
			String markingTo, String remarks, String markingDateFrom,
			String markingDateTo, String roleId) {

		ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
		DBConnection con = new DBConnection();
		String condSQL = "";
		String strSQL = " SELECT DISTINCT A.FMID, A.FILENO, A.SUBJECT,"
				+ "(SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=DECODE(A.DESTINATIONMARKING,'"
				+ roleId
				+ "',A.FILESUBJECTCODEDES,A.FILESUBJECTCODEORG)) SUBJECTCODE,"
				+ "DECODE(B.ACTION,'RCD',(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.MARKINGFROM),NULL)  MARKINGFROM,"
				+ "DECODE(B.ACTION,'FOW',(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.MARKINGTO),NULL) MARKINGTO,"
				+ " DECODE(B.ACTION,'RCD',TO_CHAR(B.MARKINGDATE, 'DD/MM/YYYY HH24:MI'),NULL) MARKINGDATE,  B.MARKINGREMARK,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = B.ACTION) ACTION,"
				+ " DECODE(B.ACTION,'FOW',TO_CHAR(B.ACTIONDATE, 'DD/MM/YYYY HH24:MI'),NULL) ACTIONDATE FROM TRNFILEHDR A, TRNFILEMARKING B WHERE A.FMID = B.FMID "
				+ " AND DECODE(B.ACTION,'RCD',B.MARKINGTO,B.MARKINGFROM)='"
				+ roleId + "' ";

		condSQL += fileNo.trim().length() > 0 ? " AND UPPER(A.FILENO) LIKE UPPER('%"
				+ fileNo + "%')"
				: "";

		condSQL += subjectCode.trim().length() > 0 ? " AND DECODE(A.DESTINATIONMARKING,'"
				+ roleId
				+ "',A.FILESUBJECTCODEDES,A.FILESUBJECTCODEORG) = '"
				+ subjectCode + "'"
				: "";

		condSQL += subject.trim().length() > 0 ? " AND UPPER(A.SUBJECT) LIKE UPPER('%"
				+ subject + "%')"
				: "";

		condSQL += markingFrom.trim().length() > 0 ? " AND B.MARKINGFROM = '"
				+ markingFrom + "'" : "";

		condSQL += markingTo.trim().length() > 0 ? " AND B.MARKINGTO = '"
				+ markingTo + "'" : "";

		condSQL += remarks.trim().length() > 0 ? " AND UPPER(A.REMARKS) LIKE UPPER('%"
				+ remarks + "%')"
				: "";

		condSQL += markingDateFrom.trim().length() > 0
				&& markingDateTo.trim().length() > 0 ? " AND B.ACTIONDATE  BETWEEN TO_DATE('"
				+ markingDateFrom
				+ "','DD/MM/YYYY') AND TO_DATE('"
				+ markingDateTo + "','DD/MM/YYYY') "
				: "";

		strSQL += condSQL;
		strSQL += " ORDER BY A.FMID";

		try {
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				CommonBean bn = new CommonBean();
				bn.setField1(StringFormat.nullString(rs.getString("FMID")));
				bn.setField2(StringFormat.nullString(rs.getString("FILENO")));
				bn.setField3(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setField4(StringFormat.nullString(rs
						.getString("SUBJECTCODE")));
				bn.setField5(StringFormat.nullString(rs
						.getString("MARKINGFROM")));
				bn
						.setField6(StringFormat.nullString(rs
								.getString("MARKINGTO")));
				bn.setField7(StringFormat.nullString(rs
						.getString("MARKINGDATE")));
				bn.setField8(StringFormat.nullString(rs
						.getString("MARKINGREMARK")));
				bn.setField9(StringFormat.nullString(rs.getString("ACTION")));
				bn.setField10(StringFormat.nullString(rs
						.getString("ACTIONDATE")));

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
	
	
	public ArrayList<CommonBean> getSearchFileIntInboxOutBox(String fileNo,
			String subjectCode, String subject, String markingFrom,
			String markingTo, String remarks, String markingDateFrom,
			String markingDateTo, String roleId) {

		ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
		DBConnection con = new DBConnection();
		String condSQL = "";
		String strSQL = " SELECT DISTINCT A.FMID, A.FILENO, A.SUBJECT,"
				+ "(SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.FILESUBJECTCODEDES AND Q.SUBJECTTYPE = 'F') SUBJECTCODE,"
				+ "(SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.MARKINGFROM) MARKINGFROM," +
				" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = B.MARKINGTO) MARKINGTO,"
				+ " TO_CHAR(B.MARKINGDATE, 'DD/MM/YYYY HH24:MI') MARKINGDATE,  B.MARKINGREMARK,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = B.ACTION) ACTION,"
				+ " TO_CHAR(B.ACTIONDATE, 'DD/MM/YYYY HH24:MI') ACTIONDATE,TO_CHAR(B.CHANGEDATE, 'DD/MM/YYYY HH24:MI') CHANGEDATE FROM TRNFILEHDR A, TRNFILEINTMARKING B WHERE A.FMID = B.FMID "
				+ " AND B.MARKINGTO='"
				+ roleId + "' ";

		condSQL += fileNo.trim().length() > 0 ? " AND UPPER(A.FILENO) LIKE UPPER('%"
				+ fileNo + "%')"
				: "";

		condSQL += subjectCode.trim().length() > 0 ? " AND A.FILESUBJECTCODEDES = '"
				+ subjectCode + "'"
				: "";

		condSQL += subject.trim().length() > 0 ? " AND UPPER(A.SUBJECT) LIKE UPPER('%"
				+ subject + "%')"
				: "";

		condSQL += markingFrom.trim().length() > 0 ? " AND B.MARKINGFROM = '"
				+ markingFrom + "'" : "";

		condSQL += markingTo.trim().length() > 0 ? " AND B.MARKINGTO = '"
				+ markingTo + "'" : "";

		condSQL += remarks.trim().length() > 0 ? " AND UPPER(A.REMARKS) LIKE UPPER('%"
				+ remarks + "%')"
				: "";

		condSQL += markingDateFrom.trim().length() > 0
				&& markingDateTo.trim().length() > 0 ? " AND B.ACTIONDATE  BETWEEN TO_DATE('"
				+ markingDateFrom
				+ "','DD/MM/YYYY') AND TO_DATE('"
				+ markingDateTo + "','DD/MM/YYYY') "
				: "";

		strSQL += condSQL;
		strSQL += " ORDER BY A.FMID";

		try {
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				CommonBean bn = new CommonBean();
				bn.setField1(StringFormat.nullString(rs.getString("FMID")));
				bn.setField2(StringFormat.nullString(rs.getString("FILENO")));
				bn.setField3(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setField4(StringFormat.nullString(rs
						.getString("SUBJECTCODE")));
				bn.setField5(StringFormat.nullString(rs
						.getString("MARKINGFROM")));
				bn
						.setField6(StringFormat.nullString(rs
								.getString("MARKINGTO")));
				bn.setField7(StringFormat.nullString(rs
						.getString("MARKINGDATE")));
				bn.setField8(StringFormat.nullString(rs
						.getString("MARKINGREMARK")));
				bn.setField9(StringFormat.nullString(rs.getString("ACTION")));
				bn.setField10(StringFormat.nullString(rs
						.getString("ACTIONDATE")));
				bn.setField11(StringFormat.nullString(rs
						.getString("CHANGEDATE")));

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
	
	

	public String saveReference(TrnFileHdr trnfileBean,TrnFileReply trnfileReply,
			ArrayList<TrnFileRef> trnRefBeanList,
			ArrayList<TrnFileIntMarking> trnintMarkingBeanList,
			ArrayList<TrnFileMarking> trnMarkingBeanUList,
			ArrayList<TrnFileMarking> trnMarkingBeanDList) {
		String outMessage = "";
		PreparedStatement ps = null;
		//ArrayList<String> params = new ArrayList<String>();
		//params.add("");
		DBConnection dbcon = new DBConnection();
		try {
			dbcon.openConnection();

			//System.out.println(" trnRefBeanList : " + trnRefBeanList.size());
			// inserting into marking detail table

			// variable for holding generated ref no and marking
			String refNoAndMarking = "";

			String sqlStr = "SELECT COUNT(*) FROM TRNFILEHDR  WHERE FMID = ? ";
			log.debug("sqlStr=" + sqlStr);
			
			ps = dbcon.setPrepStatement(sqlStr);
			ps.setString(1, trnfileBean.getFMID());

			ResultSet rs = ps.executeQuery();
			int rec0 = 0;
			if (rs.next()) {
				rec0 = rs.getInt(1);
			}
			if (rec0 == 1) {
				// if refid is not null then update query
				String cond;
				/*paramters for query*/
				ArrayList<String> params = new ArrayList<String>();
				params.add("");
				params.add(trnfileBean.getREFERENCETYPE());
						 params.add(trnfileBean.getFILENO());
						 params.add(trnfileBean.getDEPARTMENTCODE());
						 params.add(trnfileBean.getFILESTATUS1());
						 params.add(trnfileBean.getFILESTATUS2());
						 params.add(trnfileBean.getSENTTO());
						 params.add(trnfileBean.getNOL());
						 params.add(trnfileBean.getRECEIVEDFROM());
						 params.add(trnfileBean.getREMARKS());
						 params.add(trnfileBean.getLINKFILENO1());
						 params.add(trnfileBean.getLINKFILENO2());
						 params.add(trnfileBean.getLINKFILENO3());
						 params.add(trnfileBean.getLINKFILENO4());
						 params.add(trnfileBean.getEOFFICEFILENO());
				
				if(trnfileBean.getLOGINASROLEID().equalsIgnoreCase(trnfileBean.getDESTINATIONMARKING())){
					cond = " FILESUBJECTCODEDES = ?, ";
					params.add(trnfileBean.getFILESUBJECTCODEDES());
				}else{
					cond = " FILESUBJECTCODEORG = ?, ";
					params.add(trnfileBean.getFILESUBJECTCODEORG());					
				}
				
				params.add(trnfileBean.getREPLYTYPE());
						 params.add(trnfileBean.getREASON());
						 params.add(trnfileBean.getSUBJECT());
						 params.add(trnfileBean.getPROPOSEDPATH());
						 params.add(trnfileBean.getFMID());
				// update the header table
				String QueryUpdate = " UPDATE TRNFILEHDR SET "
						+ " REFERENCETYPE = ?, "
						+ " FILENO = ?, "
						+ " DEPARTMENTCODE = ?, "
						+ " FILESTATUS1 = ?, "
						+ " FILESTATUS2 = ?, "
						+ " SENTTO = ?, "
						+ " NOL = ?, "
						+ " RECEIVEDFROM = ?, "
						+ " REMARKS = ?, "
						+ " LINKFILENO1 = ?, "
						+ " LINKFILENO2 = ?, "
						+ " LINKFILENO3 = ?, "
						+ " LINKFILENO4 = ?, "
						+ " EOFFICENO =?, "
						+   cond+" "
						+ " REPLYTYPE = ?, "
						+ " REASON = ?, "
						+ " SUBJECT = ?, "
						+ " PROPOSEDPATH = ?, "
						+ " CHANGEDATE = SYSDATE "
						+ " WHERE FMID = ? ";

				log.debug(QueryUpdate);
				
				ps = dbcon.setPrepStatement(QueryUpdate);
				
				for(int i=1; i<params.size();i++){
					ps.setString(i,params.get(i));
				}
				
				ps.executeUpdate();
				//dbcon.update(QueryUpdate);
				
				// update the header table
				

			}
			if (rec0 == 0) {
				trnfileBean.setFMID(CommonDAO.getNextIdFile(dbcon));
				if (trnfileBean.getLOGINASROLEID().equalsIgnoreCase(
						trnfileBean.getDESTINATIONMARKING())) {
					trnfileBean.setFILECOUNTERDES(getNextFileCountPst(dbcon, trnfileBean
									.getTENUREID(), trnfileBean
									.getLOGINASROLEID()));
					trnfileBean.setFILECOUNTER(trnfileBean.getFILECOUNTERDES());
					trnfileBean.setROLEIDDES(trnfileBean.getLOGINASROLEID());
				} else {
					trnfileBean.setFILECOUNTERORG(getNextFileCountPst(dbcon, trnfileBean
									.getTENUREID(), trnfileBean
									.getLOGINASROLEID()));
					trnfileBean.setFILECOUNTER(trnfileBean.getFILECOUNTERDES());
					trnfileBean.setROLEIDORG(trnfileBean.getLOGINASROLEID());
				}
				log.info("Generated Refcount is  ************* "
						+ trnfileBean.getFILECOUNTERDES());
				log.info("Generated Refcount is  ************* "
						+ trnfileBean.getFILECOUNTERORG());

				// trnfileBean.setREFNO(trnfileBean.getREFNO()+"/"+
				// trnfileBean.getREFCOUNT() + "/"+
				// Calendar.getInstance().get(Calendar.YEAR));
				if (trnfileBean.getLOGINASROLEID().equalsIgnoreCase(
						trnfileBean.getDESTINATIONMARKING())) {
					trnfileBean.setREGISTRATIONNODES(trnfileBean
							.getLOGINASROLENAME()
							+ "/"
							+ trnfileBean.getFILECOUNTERDES()
							+ "/"
							+ Calendar.getInstance().get(Calendar.YEAR));
					trnfileBean.setREGISTRATIONNO(trnfileBean
							.getREGISTRATIONNODES());
				} else {
					trnfileBean.setREGISTRATIONNOORG(trnfileBean
							.getLOGINASROLENAME()
							+ "/"
							+ trnfileBean.getFILECOUNTERORG()
							+ "/"
							+ Calendar.getInstance().get(Calendar.YEAR));
					trnfileBean.setREGISTRATIONNO(trnfileBean
							.getREGISTRATIONNOORG());
				}

				// Insert into Header Table
				String fileQuery = " INSERT INTO TRNFILEHDR(FMID,REGISTRATIONDATEORG,REFERENCETYPE,FILENO,DEPARTMENTCODE,FILESTATUS1,"
						+ "FILESTATUS2,SENTTO,NOL,DESTINATIONMARKING,RECEIVEDFROM,REMARKS,LINKFILENO1,LINKFILENO2,LINKFILENO3,LINKFILENO4,EOFFICENO,FILECOUNTERORG,"
						+ "FILECOUNTERDES,ROLEIDORG,LOGINID,CHANGEDATE,REGISTRATIONDATEDES,FILESUBJECTCODEORG,FILESUBJECTCODEDES,ROLEIDDES,TENUREID,"
						+ "REPLYTYPE,REASON,SUBJECT,REGISTRATIONNODES,REGISTRATIONNOORG,PROPOSEDPATH,ISCONF)"
						+ " VALUES (?,"
						+ " TO_DATE(?, 'DD/MM/YYYY'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
						+ " SYSDATE,"
						+ " TO_DATE(?, 'DD/MM/YYYY'),?,?,?,?,?,?,?,?,?,?,?)";
				ps = dbcon.setPrepStatement(fileQuery);
				ps.setString(1,trnfileBean.getFMID());
				 ps.setString(2,trnfileBean.getREGISTRATIONDATEORG());
				 ps.setString(3,trnfileBean.getREFERENCETYPE());
				 ps.setString(4,trnfileBean.getFILENO());
				 ps.setString(5,trnfileBean.getDEPARTMENTCODE());
				 ps.setString(6,trnfileBean.getFILESTATUS1());
				 ps.setString(7,trnfileBean.getFILESTATUS2());
				 ps.setString(8,trnfileBean.getSENTTO());
				 ps.setString(9,trnfileBean.getNOL());
				 ps.setString(10,trnfileBean.getDESTINATIONMARKING());
				 ps.setString(11,trnfileBean.getRECEIVEDFROM());
				 ps.setString(12,trnfileBean.getREMARKS());
				 ps.setString(13,trnfileBean.getLINKFILENO1());
				 ps.setString(14,trnfileBean.getLINKFILENO2());
				 ps.setString(15,trnfileBean.getLINKFILENO3());
				 ps.setString(16,trnfileBean.getLINKFILENO4());
				 ps.setString(17,trnfileBean.getEOFFICEFILENO());
				 ps.setString(18,trnfileBean.getFILECOUNTERORG());
				 ps.setString(19,trnfileBean.getFILECOUNTERDES());
				 ps.setString(20,trnfileBean.getROLEIDORG());
				 ps.setString(21,trnfileBean.getLOGINID());
				 ps.setString(22,trnfileBean.getREGISTRATIONDATEDES());
				 ps.setString(23,trnfileBean.getFILESUBJECTCODEORG());
				 ps.setString(24,trnfileBean.getFILESUBJECTCODEDES());
				 ps.setString(25,trnfileBean.getROLEIDDES());
				 ps.setString(26,trnfileBean.getTENUREID());
				 ps.setString(27,trnfileBean.getREPLYTYPE());
				 ps.setString(28,trnfileBean.getREASON());
				 ps.setString(29,trnfileBean.getSUBJECT());
				 ps.setString(30,trnfileBean.getREGISTRATIONNODES());
				 ps.setString(31,trnfileBean.getREGISTRATIONNOORG());
				 ps.setString(32,trnfileBean.getPROPOSEDPATH());
				 ps.setString(33,trnfileBean.getISCONF());

				log.debug(fileQuery);
				ps.executeUpdate();
				//dbcon.insert(fileQuery);
				

				
				UpdateNextFileCount(dbcon,trnfileBean.getTENUREID(), trnfileBean.getLOGINASROLEID());
			}
			rs.close();
			
	//for reply data	
			
			
		if(trnfileReply.getREPLY().length()>0){	
		String sqlReplyStr = "SELECT COUNT(*) FROM TRNFILEREPLY  WHERE FMID = '"
				+ trnfileBean.getFMID() + "' ";
		log.debug("sqlReplyStr=" + sqlReplyStr);

		ResultSet rsr = dbcon.select(sqlReplyStr);
		int recr = 0;
		if (rsr.next()) {
			recr = rsr.getInt(1);
		}
		if (recr == 1) {
			
			String QueryReplyUpdate = " UPDATE TRNFILEREPLY SET "
				+ " REPLY = ?  WHERE FMID =  ?";
			
		ps = dbcon.setPrepStatement(QueryReplyUpdate);
		ps.setString(1, trnfileReply.getREPLY());
		ps.setString(2, trnfileBean.getFMID());

		log.debug(QueryReplyUpdate);
		ps.executeUpdate();
		//dbcon.update(QueryReplyUpdate);
			
		}
		if (recr == 0) {
			String fileReplyQuery = " INSERT INTO TRNFILEREPLY(FMID,REPLY)"
				+ " VALUES (?,?)";
			
			ps = dbcon.setPrepStatement(fileReplyQuery);
			ps.setString(1, trnfileBean.getFMID());
			ps.setString(2, trnfileReply.getREPLY());
			

		log.debug(fileReplyQuery);
		ps.executeUpdate();
		//dbcon.insert(fileReplyQuery);	
			
		}
		}
		
		
		//reply data ends 
		
		
			String refsQuery = "DELETE FROM TRNFILEREF WHERE FMID=? ";

			ps = dbcon.setPrepStatement(refsQuery);
			ps.setString(1, trnfileBean.getFMID());
			log.debug(refsQuery);
			ps.executeUpdate();
			//dbcon.insert(refsQuery);

		
		
			for (int i = 0; i < trnRefBeanList.size(); i++) {

				TrnFileRef trnrefBean = trnRefBeanList.get(i);
				/*
				 * //insert into trnmarking String refsQuery =
				 * " SELECT COUNT(*) FROM TRNFILEREF WHERE FMID='"+
				 * trnfileBean.getFMID()+ "' AND REFID = '"+
				 * trnrefBean.getREFID()+ "'"; log.debug("dasdasda"+refsQuery);
				 * ResultSet rsref = dbcon.select(refsQuery); int rec =0;
				 * if(rsref.next()) { rec=rsref.getInt(1); } if(rec==1){
				 * //insert into trnmarking String refQuery =
				 * " UPDATE TRNFILEREF SET " +
				 * //" MARKINGSEQUENCE = '"+trnMarkingBean
				 * .getMARKINGSEQUENCE()+"', " +
				 * " REFID = '"+trnrefBean.getREFID()+"' " + " WHERE FMID =  '"
				 * + trnfileBean.getFMID()+ "'";
				 * 
				 * log.debug(refQuery); dbcon.update(refQuery);
				 * 
				 * } if(rec==0){ String refQuery =
				 * " INSERT INTO TRNFILEREF (FMID,REFID) " + " VALUES (" + " '"+
				 * trnfileBean.getFMID()+ "'," + " '"+ trnrefBean.getREFID()+
				 * "')";
				 * 
				 * log.debug(refQuery); dbcon.insert(refQuery); } rsref.close();
				 */

				String refQuery = " INSERT INTO TRNFILEREF (FMID,REFID) "
						+ " VALUES (?,?)";
				
				ps = dbcon.setPrepStatement(refQuery);
				ps.setString(1, trnfileBean.getFMID());
				ps.setString(2, trnrefBean.getREFID());

				log.debug(refQuery);
				ps.executeUpdate();
				//dbcon.insert(refQuery);
				
				//new update of compliance status in TRNREFRENCE table
				String refcomplainceQuery = " UPDATE TRNREFERENCE SET EOFFICENO=?,COMPLIANCE=?,COMPLIANCEREMARKS=?  WHERE REFID=? ";
					
				
				ps = dbcon.setPrepStatement(refcomplainceQuery);
				ps.setString(1,trnfileBean.getEOFFICEFILENO());
				ps.setString(2,trnrefBean.getCompliance());
				ps.setString(3,trnrefBean.getComplianceremarks());
				ps.setString(4,trnrefBean.getREFID());
				log.debug(refcomplainceQuery);
				ps.executeUpdate();
				//dbcon.insert(refcomplainceQuery);

			}

			for (int i = 0; i < trnintMarkingBeanList.size(); i++) {

				TrnFileIntMarking trnintMarkingBean = trnintMarkingBeanList
						.get(i);

				String rsintMarkingBean = " SELECT COUNT(*) FROM TRNFILEINTMARKING WHERE FMID=? "
						+ "AND MARKINGSEQUENCE = ? ";
				
				ps = dbcon.setPrepStatement(rsintMarkingBean);
				ps.setString(1, trnfileBean.getFMID());
				ps.setString(2, trnintMarkingBean.getMARKINGSEQUENCE());

				ResultSet rsint = ps.executeQuery();
				int rec = 0;
				if (rsint.next()) {
					rec = rsint.getInt(1);
				}
				if (rec == 1) {
					// insert into trnmarking
					String cdate="";
					ArrayList<String> params1 = new ArrayList<String>();
					params1.add("");
					params1.add(trnintMarkingBean.getMARKINGTO());
					
					
					if(trnintMarkingBean.getCHANGEDATE().length()>0){
						cdate=" CHANGEDATE = TO_DATE(? ||TO_CHAR(SYSDATE,'HH24:MI'),'DD/MM/YYYY HH24:MI'), ";
						params1.add(trnintMarkingBean.getCHANGEDATE());
					}
					
					params1.add(trnintMarkingBean.getMARKINGREMARK());
					params1.add(trnintMarkingBean.getMARKINGDATE());
					params1.add(trnfileBean.getFMID());
					params1.add(trnintMarkingBean.getMARKINGSEQUENCE());
					
					String intmarkingQuery = " UPDATE TRNFILEINTMARKING SET "
							+
							" MARKINGTO = ?, "
							+ cdate
							+ " MARKINGREMARK = ? "
							+", MARKINGDATE = TO_DATE(?,'DD/MM/YYYY') " 
							+" WHERE FMID =  ?"
							+ " AND MARKINGSEQUENCE = ?";
					
					ps = dbcon.setPrepStatement(intmarkingQuery);
					for(int j =1 ; j<params1.size();j++) {
						ps.setString(j, params1.get(j));
					}
					

					log.debug(intmarkingQuery);
					ps.executeUpdate();
					//dbcon.update(intmarkingQuery);

				}
				if (rec == 0) {

					// insert into trnmarking
					trnintMarkingBean
							.setMARKINGSEQUENCE(getNextIntMarkingSequence(
									dbcon,  trnfileBean.getFMID()));
					String intmarkingQuery = " INSERT INTO TRNFILEINTMARKING (FMID,MARKINGSEQUENCE,FILECOUNTER,MARKINGTO,MARKINGDATE,LOGINID,"
							+ "CHANGEDATE,MARKINGFROM,ACTION,ACTIONDATE,MARKINGREMARK) "
							+ " VALUES (?,?,?,"
							+ " ?,"
							+ " SYSDATE,"
							+ " ?,"
							+ "to_date( ?,'dd/mm/yyyy'),"
							+ " ?,"
							+ " 'FOW',SYSDATE,? )";
					
					ps = dbcon.setPrepStatement(intmarkingQuery);
					ps.setString(1,trnfileBean.getFMID());
					 ps.setString(2,trnintMarkingBean.getMARKINGSEQUENCE());
					 ps.setString(3,trnfileBean.getFILECOUNTER());
					 ps.setString(4,trnintMarkingBean.getMARKINGTO());
					 ps.setString(5,trnintMarkingBean.getLOGINID());
					 ps.setString(6,trnintMarkingBean.getCHANGEDATE());
					 ps.setString(7,trnintMarkingBean.getMARKINGFROM());
					 ps.setString(8,trnintMarkingBean.getMARKINGREMARK());

					log.debug(intmarkingQuery);
					ps.executeUpdate();
					//dbcon.insert(intmarkingQuery);
				}
			}

			for (int i = 0; i < trnMarkingBeanUList.size(); i++) {

				TrnFileMarking trnMarkingUBean = trnMarkingBeanUList.get(i);

				String rsmarkingUQuery = " SELECT COUNT(*) FROM TRNFILEMARKING WHERE FMID=? "
						+ "AND MARKINGSEQUENCE = ? AND MARKINGTYPE = 'U'";
				int rec = 0;
				
				ps = dbcon.setPrepStatement(rsmarkingUQuery);
				ps.setString(1, trnfileBean.getFMID());
				ps.setString(2, trnMarkingUBean.getMARKINGSEQUENCE());
				
				ResultSet rsumrk = ps.executeQuery();
				if (rsumrk.next()) {
					rec = rsumrk.getInt(1);
				}
				if (rec == 1) {
					// insert into trnmarking
					String markingUQuery = " UPDATE TRNFILEMARKING SET "
							+ " MARKINGTO = ?, "
							+ " MARKINGREMARK = ?, "
							+ " SUBJECTCODE = ?, "
							+ " SUBJECT = ?  WHERE FMID =  ? AND MARKINGSEQUENCE = ? AND MARKINGTYPE = 'U'";
					
					ps = dbcon.setPrepStatement(markingUQuery);
					 ps.setString(1,trnMarkingUBean.getMARKINGTO());
					 ps.setString(2,trnMarkingUBean.getMARKINGREMARK() );
					 ps.setString(3,trnMarkingUBean.getSUBJECTCODE());
					 ps.setString(4,trnMarkingUBean.getSUBJECT());
					 ps.setString(5,trnfileBean.getFMID());
					 ps.setString(6,trnMarkingUBean.getMARKINGSEQUENCE());

					log.debug(markingUQuery);
					ps.executeUpdate();
					//dbcon.update(markingUQuery);
				}
				if (rec == 0) {
					// insert into trnmarking
					trnMarkingUBean.setMARKINGSEQUENCE(getNextMarkingSequence(
							dbcon, trnfileBean.getFMID(), "U"));
					String markingUQuery = " INSERT INTO TRNFILEMARKING (FMID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO, MARKINGDATE, MARKINGREMARK, "
							+ " SUBJECTCODE, SUBJECT, ACTION, ACTIONDATE,MARKINGTYPE) "
							+ " VALUES (?,?,?,?,"
							+ " SYSDATE, ?, ?,?, 'FOW', SYSDATE,'U')";
					
					ps = dbcon.setPrepStatement(markingUQuery);
					 ps.setString(1,trnfileBean.getFMID());
					 ps.setString(2,trnMarkingUBean.getMARKINGSEQUENCE());
					 ps.setString(3,trnMarkingUBean.getMARKINGFROM());
					 ps.setString(4,trnMarkingUBean.getMARKINGTO());
					 ps.setString(5,trnMarkingUBean.getMARKINGREMARK());
					 ps.setString(6,trnMarkingUBean.getSUBJECTCODE());
					 ps.setString(7,trnMarkingUBean.getSUBJECT());
					 
					log.debug(markingUQuery);
					ps.executeUpdate();
					//dbcon.insert(markingUQuery);
				}
			}
			if(trnMarkingBeanDList.size()==0){
				
				//System.out.println("in delete structure");
			String markingUQuery = " delete TRNFILEMARKING where FMID= ?";
			
			ps = dbcon.setPrepStatement(markingUQuery);
			ps.setString(1, trnfileBean.getFMID());
			log.debug(markingUQuery);
			ps.executeUpdate();
			//dbcon.delete(markingUQuery);
				
			}	
			for (int i = 0; i < trnMarkingBeanDList.size(); i++) {

				TrnFileMarking trnMarkingDBean = trnMarkingBeanDList.get(i);
				String rsmarkingDQuery = " SELECT COUNT(*) FROM TRNFILEMARKING WHERE FMID=? AND MARKINGSEQUENCE = ? AND MARKINGTYPE = 'D'";
				int rec = 0;
				ps = dbcon.setPrepStatement(rsmarkingDQuery);
				ps.setString(1, trnfileBean.getFMID());
				ps.setString(2, trnMarkingDBean.getMARKINGSEQUENCE());
				ResultSet rsdmrk = ps.executeQuery();
				if (rsdmrk.next()) {
					rec = rsdmrk.getInt(1);
				}
				if (rec == 1) {
					
					//System.out.println("inside 1========"+trnMarkingDBean.getMARKINGTO());
					
					
					String markingUQuery = " UPDATE TRNFILEMARKING SET " +
							" MARKINGTO = ?, " +
							" MARKINGDATE = TO_DATE(?,'DD/MM/YYYY'), " +
							" MARKINGREMARK = ?, " +
							" SUBJECTCODE = ?, "	+
							" SUBJECT = ? " +
							" WHERE FMID = ?" +
							" AND MARKINGSEQUENCE = ? AND MARKINGTYPE = 'D'";
					
					ps = dbcon.setPrepStatement(markingUQuery);
					ps.setString(1,trnMarkingDBean.getMARKINGTO());
					ps.setString(2,trnMarkingDBean.getMARKINGDATE());
					ps.setString(3,trnMarkingDBean.getMARKINGREMARK());
					ps.setString(4,trnMarkingDBean.getSUBJECTCODE() );
					ps.setString(5,trnMarkingDBean.getSUBJECT());
					ps.setString(6,trnfileBean.getFMID());
					ps.setString(7,trnMarkingDBean.getMARKINGSEQUENCE());
					
					log.debug(markingUQuery);
					ps.executeUpdate();
					//dbcon.update(markingUQuery);
				}
				if (rec == 0) {
					// insert into trnmarking
					trnMarkingDBean.setMARKINGSEQUENCE(getNextMarkingSequence(
							dbcon, trnfileBean.getFMID(), "D"));
					String markingUQuery = " INSERT INTO TRNFILEMARKING (FMID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO, MARKINGDATE, MARKINGREMARK, "
							+ " SUBJECTCODE, SUBJECT, ACTION, ACTIONDATE,MARKINGTYPE) "
							+ " VALUES (?,?,?,?,"
							+ " SYSDATE,"
							+ " ?,?,?, 'FOW', SYSDATE,'D' )";
					
					ps = dbcon.setPrepStatement(markingUQuery);
					 ps.setString(1,trnfileBean.getFMID());
					 ps.setString(2,trnMarkingDBean.getMARKINGSEQUENCE());
					 ps.setString(3,trnMarkingDBean.getMARKINGFROM());
					 ps.setString(4,trnMarkingDBean.getMARKINGTO());
					 ps.setString(5,trnMarkingDBean.getMARKINGREMARK());
					 ps.setString(6,trnMarkingDBean.getSUBJECTCODE());
					 ps.setString(7,trnMarkingDBean.getSUBJECT());

					log.debug(markingUQuery);
					ps.executeUpdate();
					//dbcon.insert(markingUQuery);
				}
			}
			// set the variable to flash output message
			refNoAndMarking +=   trnfileBean.getREGISTRATIONNO();

			

			UpdateReference(dbcon, trnfileBean.getFMID(), trnfileBean
					.getFILECOUNTER(), trnfileBean.getREGISTRATIONNO(),
					trnfileBean.getFILENO(), trnfileBean.getFILESTATUS1(),
					trnfileBean.getFILESTATUS2(), trnfileBean.getREPLYTYPE(),
					trnfileBean.getREASON(),
					//trnfileBean.getFILESUBJECTCODE(),
					//trnfileBean.getSUBJECT(),
					trnfileBean.getREGISTRATIONDATE(),
					trnfileBean.getREMARKS(),trnfileBean.getEOFFICEFILENO());
			// reset refid to blank so that new refid is generated for next
			// iteration of the detail tables for markign
			trnfileBean.setFMID("");
			trnfileBean.setFILECOUNTERORG("");
			trnfileBean.setFILECOUNTERDES("");

			outMessage = "GRNSave operation successful.  " + refNoAndMarking;

		} catch (SQLException sql) {
			dbcon.rollback();
			log.fatal(sql, sql);
			outMessage = "REDSave operation failure for FILE Management . "
					+ trnfileBean.getFMID() + ". ERROR--> " + sql.getMessage();
			// (new CommonDAO()).generateMessage("TESTLOGIN",outMessage);
		} finally {
			dbcon.closeConnection();
		}

		return outMessage;
	}

	public void UpdateReference(DBConnection con, String fmid, String filecounter, String registrationno, String fileno, String filestatus1,
								String filestatus2, String replytype, String reason,String registrationdate,
								String remarks,String eOfficeFileNo) {


		String strSQL = " UPDATE TRNREFERENCE A SET A.FMID = ?,A.FILECOUNTER =?, A.REGISTRATIONNO = ?," +
				" A.FILENO = ?," +
				" A.IMARKINGTO = (SELECT O.MARKINGTO FROM TRNFILEINTMARKING O WHERE O.FMID=? AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=?))," +
				" A.FILESTATUS1 = ?,A.FILESTATUS2 = ?,A.REPLYTYPE = ?," +
				" A.REASON = ?, A.DMARKINGTO = (SELECT O.MARKINGTO FROM TRNFILEMARKING O WHERE O.FMID=?" +
				" AND O.MARKINGSEQUENCE = 1), A.DMARKINGDATE = (SELECT O.MARKINGDATE FROM TRNFILEMARKING O WHERE O.FMID=?" +
				" AND O.MARKINGSEQUENCE = 1)," +
				" A.REGISTRATIONDATE = TO_DATE(?,'DD/MM/YYYY'), A.STATUSREMARK = ?, "	+
				" A.IMARKINGDATE = (SELECT TO_DATE(O.MARKINGDATE,'DD/MM/YYYY') FROM TRNFILEINTMARKING O WHERE O.FMID=? AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=?))," +
				" A.ICHANGEDATE = (SELECT TO_DATE(O.CHANGEDATE,'DD/MM/YYYY') FROM TRNFILEINTMARKING O WHERE O.FMID=? AND O.MARKINGSEQUENCE = (SELECT MAX(P.MARKINGSEQUENCE) FROM TRNFILEINTMARKING P WHERE P.FMID=?)) " +
				" WHERE A.REFID IN (SELECT O.REFID FROM TRNFILEREF O WHERE O.FMID=?)" +
				" AND NVL(A.FILESTATUS1,'0') NOT IN ('2','3','4','5','8','11','9','16')";
		

		
		log.debug(strSQL);
		try {
			
			PreparedStatement ps2 = con.setPrepStatement(strSQL);
			 ps2.setString(1,fmid);
			 ps2.setString(2,filecounter);
			 ps2.setString(3,registrationno);
			 ps2.setString(4,fileno);
			 ps2.setString(5,fmid);
			 ps2.setString(6,fmid);
			 ps2.setString(7,filestatus1);
			 ps2.setString(8,filestatus2);
			 ps2.setString(9,replytype);
			 ps2.setString(10,reason);
			 ps2.setString(11,fmid);
			 ps2.setString(12,fmid);
			 ps2.setString(13,registrationdate);
			 ps2.setString(14,remarks);
			 ps2.setString(15,fmid);
			 ps2.setString(16,fmid);
			 ps2.setString(17,fmid);
			 ps2.setString(18,fmid);
			 ps2.setString(19,fmid);
			 
			ps2.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getNextFileCount(DBConnection con, String colName,
			String tableName, String tenureId, String roleId) {
		String strSQL = "(SELECT NVL(MAX(A." + colName + "), 0)+ 1 FROM "
				+ tableName + " A WHERE A.TENUREID = '" + tenureId
				+ "' AND A.ROLEID = '" + roleId + "')";
		log.debug(strSQL);
		return getStringParam(strSQL, con);
	}
	
	public String getNextFileCountPst(DBConnection con, String tenureId, String roleId) {
		String param = "";
		
		try{
			String strSQL = "(SELECT NVL(MAX(A.FILECOUNTER), 0)+ 1 FROM MSTFILECOUNTER A WHERE A.TENUREID = ? AND A.ROLEID = ?)";
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ps.setString(1, tenureId);
			ps.setString(2, roleId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				param = handleNull(rs.getString(1));
			}
		}
		catch(Exception e){
			log.fatal(e, e);
		}
		return param;
		
	}

	public void UpdateNextFileCount(DBConnection con, String colName,
			String tableName, String tenureId, String roleId) {
		String strSQL = "UPDATE " + tableName + " A SET A." + colName + " = A."
				+ colName + " +1  WHERE A.TENUREID = '" + tenureId
				+ "' AND A.ROLEID = '" + roleId + "'";
		log.debug(strSQL);
		try {
			con.update(strSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void UpdateNextFileCount(DBConnection con, String tenureId, String roleId) {
		try{
			String strSQL = "UPDATE MSTFILECOUNTER A SET A.FILECOUNTER = A.FILECOUNTER +1  WHERE A.TENUREID =? AND A.ROLEID =?";
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ps.setString(1, tenureId);
			ps.setString(2, roleId);
			ps.executeUpdate();

		}
		catch(Exception e){
			log.fatal(e, e);
		}
		
	}

	public String getNextMarkingSequence(DBConnection con, String colName,
			String tableName, String fmId, String markingType) {
		String strSQL = "(SELECT NVL(MAX(A." + colName + "), 0)+ 1 FROM "
				+ tableName + " A WHERE FMID = '" + fmId
				+ "' AND MARKINGTYPE = '" + markingType + "')";
		log.debug(strSQL);
		return getStringParam(strSQL, con);
	}
	
	public String getNextMarkingSequence(DBConnection con, String fmId, String markingType) {
		String param = "";
		
		try{
			String strSQL = "(SELECT NVL(MAX(A.MARKINGSEQUENCE), 0)+ 1 FROM TRNFILEMARKING A WHERE FMID = ? AND MARKINGTYPE = ?)";
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ps.setString(1, fmId);
			ps.setString(2, markingType);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				param = handleNull(rs.getString(1));
			}
		}
		catch(Exception e){
			log.fatal(e, e);
		}
		return param;
		
	}

	public String getNextIntMarkingSequence(DBConnection con, String colName,
			String tableName, String fmId) {
		String strSQL = "(SELECT NVL(MAX(A." + colName + "), 0)+ 1 FROM "
				+ tableName + " A WHERE FMID = '" + fmId + "')";
		log.debug(strSQL);
		return getStringParam(strSQL, con);
	}
	
	public String getNextIntMarkingSequence(DBConnection con,  String fmId) {
		String param = "";
		
		try{
			String strSQL = "(SELECT NVL(MAX(MARKINGSEQUENCE), 0)+ 1 FROM TRNFILEINTMARKING A WHERE FMID =?)";
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ps.setString(1, fmId);			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				param = handleNull(rs.getString(1));
			}
		}
		catch(Exception e){
			log.fatal(e, e);
		}
		return param;
		
		
		
	}

	public String getStringParam(String strSQL, DBConnection con) {
		String param = "";
		try {
			ResultSet rs = con.select(strSQL);
			if (rs.next()) {
				param = handleNull(rs.getString(1));
			}
			rs.close();
		} catch (Exception e) {
			log.fatal(e, e);
		}
		return param;
	}
	
	

	public String handleNull(String str) {
		return str != null ? CommonDAO.numberFormet(str) : "";
	}

	public void clearForm() {

	}


	public ArrayList<CommonBean> getTreeviewD(String fmId) {
		String strSQL = "";
		PreparedStatement ps = null;
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		DBConnection dbcon = new DBConnection();
		try {
			dbcon.openConnection();

			strSQL = " SELECT 'd1.add('||DECODE(X.CHILD, NULL, (Y.CHILD-1), X.CHILD)||','"
					+ " ||DECODE(X.PARENT, NULL, (Y.PARENT-1), X.PARENT)||','||''''"
					+ " ||'<span class=\"treespan\" name=\"treespan\" style=\"color:''+col['||2||']+'';\">'"
					+ " ||DECODE(X.MARKINGTO, NULL, (Y.MARKINGFROM), X.MARKINGTO) || '   - '"
					+ " ||NVL(Y.ACTION, 'Pending') ||' on '"
					+ " ||TO_CHAR(DECODE(Y.ACTIONDATE, NULL, (X.ACTIONDATE), Y.ACTIONDATE),'DD/MM/YYYY HH24:MI:SS') "
					+ " ||''||'</span>' ||''''||','||''''||rownum||''''||');' TREEVIEW "
					+ " FROM "
					+ " 	(SELECT MARKINGSEQUENCE CHILD, (MARKINGSEQUENCE - 1) PARENT,  GETROLENAME(MARKINGFROM) MARKINGFROM,"
					+ "	GETROLENAME(MARKINGTO) MARKINGTO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = ACTION) ACTION , ACTIONDATE"
					+ "	FROM TRNFILEMARKING A WHERE FMID=? AND MARKINGTYPE='D' ) X FULL JOIN"
					+ "	(SELECT MARKINGSEQUENCE CHILD, (MARKINGSEQUENCE - 1) PARENT,  GETROLENAME(MARKINGFROM) MARKINGFROM,"
					+ "	GETROLENAME(MARKINGTO) MARKINGTO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = ACTION) ACTION , ACTIONDATE"
					+ "	FROM TRNFILEMARKING A WHERE FMID=? AND MARKINGTYPE='D' )  Y ON X.CHILD = Y.PARENT";

			log.debug("TREE  - " + strSQL);
			ps = dbcon.setPrepStatement(strSQL);
			ps.setString(1, fmId);
			ps.setString(2, fmId);
			arr = (new CommonDAO().getSQLResultPreparedStmt(ps, 1, dbcon));
		} catch (Exception e) {
			log.fatal(e, e);
		} finally {
			dbcon.closeConnection();
		}
		return arr;
	}

	public ArrayList<CommonBean> getTreeviewU(String fmId) {
		String strSQL = "";
		PreparedStatement ps = null;
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		DBConnection dbcon = new DBConnection();
		try {

			dbcon.openConnection();
			strSQL = " SELECT 'd.add('||DECODE(X.CHILD, NULL, (Y.CHILD-1), X.CHILD)||','"
					+ " ||DECODE(X.PARENT, NULL, (Y.PARENT-1), X.PARENT)||','||''''"
					+ " ||'<span class=\"treespan\" name=\"treespan\" style=\"color:''+col['||2||']+'';\">'"
					+ " ||DECODE(X.MARKINGTO, NULL, (Y.MARKINGFROM), X.MARKINGTO) || '   - '"
					+ " ||NVL(Y.ACTION, 'Pending') ||' on '"
					+ " ||TO_CHAR(DECODE(Y.ACTIONDATE, NULL, (X.ACTIONDATE), Y.ACTIONDATE),'DD/MM/YYYY HH24:MI:SS') "
					+ " ||''||'</span>' ||''''||','||''''||rownum||''''||');' TREEVIEW "
					+ " FROM "
					+ " 	(SELECT MARKINGSEQUENCE CHILD, (MARKINGSEQUENCE - 1) PARENT,  GETROLENAME(MARKINGFROM) MARKINGFROM,"
					+ "	GETROLENAME(MARKINGTO) MARKINGTO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = ACTION) ACTION , ACTIONDATE"
					+ "	FROM TRNFILEMARKING A WHERE FMID=?  AND MARKINGTYPE='U' ) X FULL JOIN"
					+ "	(SELECT MARKINGSEQUENCE CHILD, (MARKINGSEQUENCE - 1) PARENT,  GETROLENAME(MARKINGFROM) MARKINGFROM,"
					+ "	GETROLENAME(MARKINGTO) MARKINGTO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = ACTION) ACTION , ACTIONDATE"
					+ "	FROM TRNFILEMARKING A WHERE FMID=?  AND MARKINGTYPE='U' )  Y ON X.CHILD = Y.PARENT";
			log.debug("TREE  - " + strSQL);
			
			ps = dbcon.setPrepStatement(strSQL);
			ps.setString(1, fmId);
			ps.setString(2, fmId);
			arr = (new CommonDAO().getSQLResultPreparedStmt(ps, 1, dbcon));
			
			
		} catch (Exception e) {
			log.fatal(e, e);
		} finally {
			dbcon.closeConnection();
		}
		return arr;
	}

	public ArrayList<CommonBean> getTreeviewInt(String fmId) {
		String strSQL = "";
		PreparedStatement ps = null;
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		DBConnection dbcon = new DBConnection();
		try {
            dbcon.openConnection();

			strSQL = " SELECT 'd2.add('||DECODE(X.CHILD, NULL, (Y.CHILD-1), X.CHILD)||','"
					+ " ||DECODE(X.PARENT, NULL, (Y.PARENT-1), X.PARENT)||','||''''"
					+ " ||'<span class=\"treespan\" name=\"treespan\" style=\"color:''+col['||2||']+'';\">'"
					+ " ||DECODE(X.MARKINGTO, NULL, (Y.MARKINGFROM), X.MARKINGTO) || '   - '"
					+ " ||'Marked'||' on '"
					+ " ||TO_CHAR(DECODE(Y.MARKINGDATE, NULL, (X.MARKINGDATE), Y.MARKINGDATE),'DD/MM/YYYY HH24:MI:SS') "
					+ " ||' - Returned'||' on '"
					+ " ||TO_CHAR(DECODE(Y.ACTIONDATE, NULL, (X.ACTIONDATE), Y.ACTIONDATE),'DD/MM/YYYY HH24:MI:SS') "
					+ " ||''||'</span>' ||''''||','||''''||rownum||''''||');' TREEVIEW "
					+ " FROM "
					+ " 	(SELECT MARKINGSEQUENCE CHILD, (MARKINGSEQUENCE - 1) PARENT,  GETROLENAME(MARKINGFROM) MARKINGFROM,"
					+ "	GETROLENAME(MARKINGTO) MARKINGTO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = ACTION) ACTION ,MARKINGDATE, CHANGEDATE ACTIONDATE"
					+ "	FROM TRNFILEINTMARKING A WHERE FMID=? ) X FULL JOIN"
					+ "	(SELECT MARKINGSEQUENCE CHILD, (MARKINGSEQUENCE - 1) PARENT,  GETROLENAME(MARKINGFROM) MARKINGFROM,"
					+ "	GETROLENAME(MARKINGTO) MARKINGTO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = ACTION) ACTION ,MARKINGDATE, CHANGEDATE ACTIONDATE"
					+ "	FROM TRNFILEINTMARKING A WHERE FMID=? )  Y ON X.CHILD = Y.PARENT";
			log.debug("TREE  - " + strSQL);
			
			ps = dbcon.setPrepStatement(strSQL);
			ps.setString(1, fmId);
			ps.setString(2, fmId);
			arr = (new CommonDAO().getSQLResultPreparedStmt(ps, 1, dbcon));
		} catch (Exception e) {
			log.fatal(e, e);
		} finally {
		}
		return arr;
	}

	public String saveFractureImage(String refID, String subject, String name) {
		String filepath = "";
		String strSQL = "";
		String nextimageid = "";
		DBConnection dbcon = new DBConnection();
		PreparedStatement ps = null;
		try {
			dbcon.openConnection();
			log.debug("saveFractureImage");
			strSQL = "SELECT (NVL(MAX(ATTACHMENTID),0)+1) FROM TRNATTACHMENT WHERE REFID =?";
			log.debug(strSQL);
			
			ps = dbcon.setPrepStatement(strSQL);
			ps.setString(1, refID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nextimageid = rs.getString(1);
			}
			strSQL = " INSERT INTO TRNATTACHMENT (REFID, ATTACHMENTID, ORIGINALFILENAME, PATH)"
					+ " VALUES (?,'1', ?,?)";
			log.debug(strSQL);
			
			ps = dbcon.setPrepStatement(strSQL);
			ps.setString(1, refID);
			ps.setString(2, name);
			ps.setString(3, subject);
			ps.executeUpdate();
		} catch (Exception e) {
			dbcon.rollback();
			e.printStackTrace();
		} finally {
			dbcon.commit();
			dbcon.closeConnection();
		}
		filepath = refID + "_" + nextimageid
				+ name.substring(name.lastIndexOf("."));
		return filepath;
	}

	public String deleteFractureImage(String refID, String attachmentID) {
		String isDataSaved = "0~Can not delete.";
		DBConnection con = new DBConnection();
		PreparedStatement ps = null;
		try {
			con.openConnection();
			String strSQL = "";
			strSQL = " DELETE FROM TRNATTACHMENT WHERE REFID = ? AND ATTACHMENTID =?";
			
			ps = con.setPrepStatement(strSQL);
			ps.setString(1, refID);
			ps.setString(2, attachmentID);
			log.debug(strSQL);
			ps.executeUpdate();
			isDataSaved = "1~Deleted Successfully.";
		} catch (Exception e) {
			con.rollback();
			isDataSaved = "0~Can not delete.";
			log.fatal(e, e);
		} finally {
			con.closeConnection();
		}
		return isDataSaved;
	}
	
	public ArrayList<MstClass> getFileCounterStatus(String tenureid, String roleId) {
		//String result = "";
		DBConnection dbCon = new DBConnection();
		ArrayList<MstClass> arr = new ArrayList<MstClass>();
		PreparedStatement ps = null;
		try {
			String strSQL = " SELECT FILECOUNTER FROM MSTFILECOUNTER WHERE TENUREID =? AND YEAR = TO_CHAR(SYSDATE,'YYYY')" +
							" AND ROLEID = ?";
			dbCon.openConnection();
			
			ps = dbCon.setPrepStatement(strSQL);
			ps.setString(1, tenureid);
			ps.setString(2, roleId);
			
			log.debug(strSQL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				MstClass bean = new MstClass();
				bean.setCOUNTER(rs.getString("FILECOUNTER"));
				arr.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return arr;
	}
	
	public void deleteDMarking(String fMID) {
		// TODO Auto-generated method stub
		if(!fMID.equalsIgnoreCase("")){
			DBConnection dbCon = new DBConnection();
			PreparedStatement ps = null;
			try {
				String strSQL = " DELETE FROM TRNFILEMARKING WHERE FMID = ?";
				dbCon.openConnection();
				
				ps = dbCon.setPrepStatement(strSQL);
				ps.setString(1, fMID);
				log.debug(strSQL);
				ps.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
				dbCon.rollback();
			} finally {
				dbCon.closeConnection();
			}	
		}
	}
}