package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MstClass;
import in.org.cris.mrsectt.Beans.RecieptDetails;
import in.org.cris.mrsectt.Beans.RecieptDetailsList;
import in.org.cris.mrsectt.Beans.TrnBudget;
import in.org.cris.mrsectt.Beans.TrnMarking;
import in.org.cris.mrsectt.Beans.TrnReference;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.util.StringFormat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import test.WSResponse;

import com.oreilly.servlet.Base64Encoder;

import in.org.cris.mrsectt.util.MailSend;

public class TrnReferenceDAO_old {
	static Logger log = LogManager.getLogger(TrnReferenceDAO_old.class);

	public ArrayList<MstClass> getCounterStatus(String tenureid) {
		//String result = "";
		DBConnection dbCon = new DBConnection();
		ArrayList<MstClass> arr = new ArrayList<MstClass>();
		try {

			String strSQL = " SELECT B.REFCLASS REFCLASS, B.TENUREID, "
					+ " REFCOUNTER COUNTER, INOUT,  " 
					+ " NVL(B.CLASSDESCRIPTION, ' ') CLASSDESCRIPTION "
					+ " FROM MSTCLASS B WHERE B.TENUREID = '" + tenureid + "' " 
					+ " AND YEAR = TO_CHAR(SYSDATE,'YYYY')   ORDER BY 1 ";

			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				MstClass bean = new MstClass();

				bean.setREFCLASS(rs.getString("REFCLASS"));
				bean.setCOUNTER(rs.getString("COUNTER"));
				bean.setINOUT(rs.getString("INOUT"));
				bean.setCLASSDESCRIPTION(rs.getString("CLASSDESCRIPTION"));
				arr.add(bean);
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

	public ArrayList<TrnReference> getSearchSub(String sString,String fdate,String tdate,String sphrase, String roleID, String isConf,String isReply, String advSearch, String refClass) {
	//System.out.println("In Search Sub");
	ArrayList<TrnReference> arr = new ArrayList<TrnReference>();
	String Qreply = "";	
	if(isReply.equalsIgnoreCase("1")){
		Qreply=isReply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=A.FMID)";
	}
	else{
		Qreply="''";
	}
	if(sString.length() > 4 && advSearch.equalsIgnoreCase("")) {
		if(sString.substring(0, 5).equalsIgnoreCase("USER ")){
			String loginId = sString.substring(5);
			String condSub="";
			DBConnection dbCon = new DBConnection();
			try {
					condSub += " AND UPPER(LOGINID) LIKE UPPER('"+loginId+"')";
					condSub += " AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
					condSub += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
					condSub += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
					String strSQL = " SELECT A.REFID, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID AND M.TYPE='R'),'0') ISATTACHMENT," +
									" A.REFNO,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE,A.REFERENCENAME,A.VIPSTATUS,A.STATECODE," +
									" NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT," +
									" A.REMARKS,A.TOURREMARKS,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '10' AND CODE<>'00' AND CODE = A.COMPLIANCE) COMPLIANCE,A.COMPLIANCEREMARKS,TO_CHAR(A.COMPLIANCEDATE,'DD/MM/YYYY') COMPLIANCEDATE,A.EOFFICEREFNO,(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO," +
									" (SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE," +
									" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.IMARKINGTO) FMMARKTO," +
									" TO_CHAR(A.REGISTRATIONDATE,'DD/MM/YYYY') FMMARKON," +
									" A.FILENO," +
									" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," +
									" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," +
									" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) RETURNTO," +
									" TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY') RETURNON, ISBUDGET," +
									" (SELECT ' Sd/- '||ROLENAME FROM MSTROLE WHERE ROLEID = A.SIGNEDBY) SIGNEDBY, TO_CHAR(A.SIGNEDON,'DD/MM') SIGNEDON, A.URGENCY,"+Qreply+" REPLY,A.VIPPARTY" +
									" FROM TRNREFERENCE A WHERE A.ROLEID = "+roleID+" "+condSub+" " +
									" ORDER BY A.REFCLASS, A.INCOMINGDATE, A.REFNO";
				dbCon.openConnection();
				log.debug(strSQL);
				ResultSet rs = dbCon.select(strSQL);
				while (rs.next()) {
					TrnReference bn = new TrnReference();
					bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
					bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
					bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
					bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
					bn.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
					bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
					bn.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
					bn.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
					bn.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
					bn.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));
					bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
					bn.setTOURREMARKS(StringFormat.nullString(rs.getString("TOURREMARKS")));
					
					bn.setCOMPLIANCE(StringFormat.nullString(rs.getString("COMPLIANCE")));
					bn.setCOMPLIANCEREMARKS(StringFormat.nullString(rs.getString("COMPLIANCEREMARKS")));
					bn.setCOMPLIANCEDATE(StringFormat.nullString(rs.getString("COMPLIANCEDATE")));
					
					bn.setEOFFICEREFNO(StringFormat.nullString(rs.getString("EOFFICEREFNO")));
					
					bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
					bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
					bn.setIMARKINGTO(StringFormat.nullString(rs.getString("FMMARKTO")));
					bn.setIMARKINGON(StringFormat.nullString(rs.getString("FMMARKON")));
					bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
					bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
					bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
					bn.setDMARKINGTO(StringFormat.nullString(rs.getString("RETURNTO")));
					bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("RETURNON")));
					bn.setISBUDGET(StringFormat.nullString(rs.getString("ISBUDGET")));
					bn.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
					bn.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
					bn.setURGENCY(StringFormat.nullString(rs.getString("URGENCY")));
					bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
					bn.setVIPPARTY(StringFormat.nullString(rs.getString("VIPPARTY")));
					arr.add(bn);
				}
			} catch (Exception e) {
				e.printStackTrace();
				//result = "0~" + e.getMessage();
				dbCon.rollback();
			} finally {
				dbCon.closeConnection();
			}
		}else {
			String[] sArray=sString.split(" ");
			String condSub="";
			String condVip="";
			String condVipparty="";
			String condstate="";
			String condRef="";
			String condRem="";
			String condDate="";
			String orCond="";
			String condRefCls="";
			String condTOURRem="";
			String compliance="";
			String condEofficeRefNo="";
			String complianceRemarks="";
			DBConnection dbCon = new DBConnection();
	//		ArrayList<TrnReference> arr = new ArrayList<TrnReference>();
			try {
				if(sphrase.equalsIgnoreCase("0")){
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condSub = condSub+"UPPER(SUBJECT) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condVip = condVip+"UPPER(VIPSTATUS) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}
				}
				
				//code added by rohit
				
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condVipparty = condVipparty+"UPPER(VIPPARTY) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}
				}
				
				//end of code added by rohit
				
		//	REFNO & INCOMING DATE
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
						orCond = (i<sArray.length)?"OR":"";
						condVip = condVip+"UPPER(REFNO) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
						orCond = (i<sArray.length)?"OR":"";
						condVip = condVip+"TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') = '"+sArray[i]+"' "+orCond+" ";
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
						orCond = (i<sArray.length)?"OR":"";
						condRef = condRef+"UPPER(REFERENCENAME) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condstate = condstate+"UPPER(STATECODE) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condstate = condstate+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1))) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length-1)?"OR":"";
					condRem = condRem+"UPPER(REMARKS) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}
				}
				//code added by Rounak
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length-1)?"OR":"";
					condTOURRem = condTOURRem+"UPPER(TOURREMARKS) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}	
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condEofficeRefNo = condEofficeRefNo+"UPPER(EOFFICEREFNO) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length-1)?"OR":"";
				//	compliance = compliance+"UPPER(COMPLIANCE) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				//	compliance=(select code from mstgec where codetype='10' and sname like '%Not Feasible%')
					compliance = compliance+"compliance=(select code from mstgec where codetype='10' and sname like '%"+sArray[i]+"%') "+orCond+" ";
					}	
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length-1)?"OR":"";
					complianceRemarks = complianceRemarks+"UPPER(COMPLIANCEREMARKS) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}	
				}
				
				
				if(fdate.length()>0 && tdate.length()>0){
					condDate = "AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
					condDate += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				}
				condRefCls += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
				}else{
					condSub = condSub+"UPPER(SUBJECT) LIKE UPPER('%"+sString+"%') OR ";
					condVip = condVip+"UPPER(VIPSTATUS) LIKE UPPER('%"+sString+"%') OR ";
					condVip = condVip+"UPPER(VIPPARTY) LIKE UPPER('%"+sString+"%') OR "; //code added by rohit
					condVip = condVip+"UPPER(REFNO) LIKE UPPER('%"+sString+"%') OR ";
					condVip = condVip+"TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') = '"+sString+"' OR ";
					condRef = condRef+"UPPER(REFERENCENAME) LIKE UPPER('%"+sString+"%') OR ";
					condstate = condstate+"UPPER(STATECODE) LIKE UPPER('%"+sString+"%') OR ";
					condstate = condstate+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1))) LIKE UPPER('%"+sString+"%') OR ";
					condRem = condRem+"UPPER(REMARKS) LIKE UPPER('%"+sString+"%') OR ";
					condTOURRem = condTOURRem+"UPPER(TOURREMARKS) LIKE UPPER('%"+sString+"%')  OR";
					compliance = compliance+"UPPER(COMPLIANCE) LIKE UPPER('%"+sString+"%') OR ";
					condEofficeRefNo = condEofficeRefNo+"UPPER(EOFFICEREFNO) LIKE UPPER('%"+sString+"%') OR ";
					
					complianceRemarks = complianceRemarks+"UPPER(COMPLIANCEREMARKS) LIKE UPPER('%"+sString+"%')  ";
					if(fdate.length()>0 && tdate.length()>0){
						condDate = "AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
						condDate += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
					}
					condRefCls += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
				}
				String strSQL = " SELECT A.REFID, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID  AND M.TYPE='R'),'0') ISATTACHMENT," +
								" A.REFNO,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE,A.REFERENCENAME,A.VIPSTATUS,A.STATECODE," +
								" NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT," +
								" A.REMARKS,A.TOURREMARKS,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '10' AND CODE<>'00' AND CODE = A.COMPLIANCE) COMPLIANCE,A.COMPLIANCEREMARKS,TO_CHAR(A.COMPLIANCEDATE,'DD/MM/YYYY') COMPLIANCEDATE,A.EOFFICEREFNO,(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO," +
								" (SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE," +
								" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.IMARKINGTO) FMMARKTO," +
								" TO_CHAR(A.REGISTRATIONDATE,'DD/MM/YYYY') FMMARKON," +
								" A.FILENO," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," +
								" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) RETURNTO," +
								" TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY') RETURNON, ISBUDGET," +
								" (SELECT ' Sd/- '||ROLENAME FROM MSTROLE WHERE ROLEID = A.SIGNEDBY) SIGNEDBY, TO_CHAR(A.SIGNEDON,'DD/MM') SIGNEDON, A.URGENCY,"+Qreply+" REPLY,A.VIPPARTY" +
								" FROM TRNREFERENCE A WHERE A.ROLEID = "+roleID+" AND ("+condSub+" "+condVip+" "+condVipparty+" "+condRef+" "+condstate+" "+ condRem +" "+ condTOURRem +" "+ compliance+" " + condEofficeRefNo+" "+complianceRemarks+") "+condDate+" "+condRefCls+"" +
								" ORDER BY A.REFCLASS, A.INCOMINGDATE, A.REFNO";
				dbCon.openConnection();
				log.debug(strSQL);
				ResultSet rs = dbCon.select(strSQL);
				while (rs.next()) {
					TrnReference bn = new TrnReference();
					bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
					bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
					bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
					bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
					bn.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
					bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
					bn.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
					bn.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
					bn.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
					bn.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));
					bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
					bn.setTOURREMARKS(StringFormat.nullString(rs.getString("TOURREMARKS")));
					
					bn.setCOMPLIANCE(StringFormat.nullString(rs.getString("COMPLIANCE")));
					bn.setCOMPLIANCEREMARKS(StringFormat.nullString(rs.getString("COMPLIANCEREMARKS")));
					bn.setCOMPLIANCEDATE(StringFormat.nullString(rs.getString("COMPLIANCEDATE")));
					
					bn.setEOFFICEREFNO(StringFormat.nullString(rs.getString("EOFFICEREFNO")));
					
					bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
					bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
					bn.setIMARKINGTO(StringFormat.nullString(rs.getString("FMMARKTO")));
					bn.setIMARKINGON(StringFormat.nullString(rs.getString("FMMARKON")));
					bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
					bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
					bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
					bn.setDMARKINGTO(StringFormat.nullString(rs.getString("RETURNTO")));
					bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("RETURNON")));
					bn.setISBUDGET(StringFormat.nullString(rs.getString("ISBUDGET")));
					bn.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
					bn.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
					bn.setURGENCY(StringFormat.nullString(rs.getString("URGENCY")));
					bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
					bn.setVIPPARTY(StringFormat.nullString(rs.getString("VIPPARTY")));
					arr.add(bn);
				}
			} catch (Exception e) {
				e.printStackTrace();
				//result = "0~" + e.getMessage();
				dbCon.rollback();
			} finally {
				dbCon.closeConnection();
			}
		}
	}else if(advSearch.equalsIgnoreCase("0")){
		
		String condSub="";
		DBConnection dbCon = new DBConnection();
		try {
				condSub += " AND UPPER(REFERENCENAME) LIKE UPPER('%"+sString+"%')";
				condSub += " AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
				condSub += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				condSub += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
				String strSQL = " SELECT A.REFID, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID  AND M.TYPE='R'),'0') ISATTACHMENT," +
								" A.REFNO,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE,A.REFERENCENAME,A.VIPSTATUS,A.STATECODE," +
								" NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT," +
								" A.REMARKS,A.TOURREMARKS,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '10' AND CODE<>'00' AND CODE = A.COMPLIANCE) COMPLIANCE,A.COMPLIANCEREMARKS,TO_CHAR(A.COMPLIANCEDATE,'DD/MM/YYYY') COMPLIANCEDATE,A.EOFFICEREFNO,(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO," +
								" (SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE," +
								" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.IMARKINGTO) FMMARKTO," +
								" TO_CHAR(A.REGISTRATIONDATE,'DD/MM/YYYY') FMMARKON," +
								" A.FILENO," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," +
								" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) RETURNTO," +
								" TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY') RETURNON, ISBUDGET," +
								" (SELECT ' Sd/- '||ROLENAME FROM MSTROLE WHERE ROLEID = A.SIGNEDBY) SIGNEDBY, TO_CHAR(A.SIGNEDON,'DD/MM') SIGNEDON, A.URGENCY,"+Qreply+" REPLY,A.VIPPARTY" +
								" FROM TRNREFERENCE A WHERE A.ROLEID = "+roleID+" "+condSub+" " +
								" ORDER BY A.REFCLASS, A.INCOMINGDATE, A.REFNO";
			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				TrnReference bn = new TrnReference();
				bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
				bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
				bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
				bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
				bn.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
				bn.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
				bn.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
				bn.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));
				bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
				bn.setTOURREMARKS(StringFormat.nullString(rs.getString("TOURREMARKS")));
				
				bn.setCOMPLIANCE(StringFormat.nullString(rs.getString("COMPLIANCE")));
				bn.setCOMPLIANCEREMARKS(StringFormat.nullString(rs.getString("COMPLIANCEREMARKS")));
				bn.setCOMPLIANCEDATE(StringFormat.nullString(rs.getString("COMPLIANCEDATE")));
				
				bn.setEOFFICEREFNO(StringFormat.nullString(rs.getString("EOFFICEREFNO")));
				
				bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
				bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
				bn.setIMARKINGTO(StringFormat.nullString(rs.getString("FMMARKTO")));
				bn.setIMARKINGON(StringFormat.nullString(rs.getString("FMMARKON")));
				bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
				bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
				bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
				bn.setDMARKINGTO(StringFormat.nullString(rs.getString("RETURNTO")));
				bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("RETURNON")));
				bn.setISBUDGET(StringFormat.nullString(rs.getString("ISBUDGET")));
				bn.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
				bn.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
				bn.setURGENCY(StringFormat.nullString(rs.getString("URGENCY")));
				bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
				bn.setVIPPARTY(StringFormat.nullString(rs.getString("VIPPARTY")));
				arr.add(bn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//result = "0~" + e.getMessage();
			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
	}else if(advSearch.equalsIgnoreCase("1")){

		String condSub="";
		DBConnection dbCon = new DBConnection();
		try {
				condSub += " AND UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1))) = UPPER('"+sString+"')";
				condSub += " AND TO_DATE((SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
				condSub += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				condSub += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
				String strSQL = " SELECT A.REFID, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID AND M.TYPE='R'),'0') ISATTACHMENT," +
								" A.REFNO,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE,A.REFERENCENAME,A.VIPSTATUS,A.STATECODE," +
								" NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT," +
								" A.REMARKS,A.TOURREMARKS,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '10' AND CODE<>'00' AND CODE = A.COMPLIANCE) COMPLIANCE,A.COMPLIANCEREMARKS,TO_CHAR(A.COMPLIANCEDATE,'DD/MM/YYYY') COMPLIANCEDATE,A.EOFFICEREFNO,(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO," +
								" (SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE," +
								" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.IMARKINGTO) FMMARKTO," +
								" TO_CHAR(A.REGISTRATIONDATE,'DD/MM/YYYY') FMMARKON," +
								" A.FILENO," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," +
								" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) RETURNTO," +
								" TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY') RETURNON, ISBUDGET," +
								" (SELECT ' Sd/- '||ROLENAME FROM MSTROLE WHERE ROLEID = A.SIGNEDBY) SIGNEDBY, TO_CHAR(A.SIGNEDON,'DD/MM') SIGNEDON, A.URGENCY,"+Qreply+" REPLY,A.VIPPARTY" +
								" FROM TRNREFERENCE A WHERE A.ROLEID = "+roleID+" "+condSub+" " +
								" ORDER BY A.REFCLASS, A.INCOMINGDATE, A.REFNO";
			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				TrnReference bn = new TrnReference();
				bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
				bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
				bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
				bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
				bn.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
				bn.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
				bn.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
				bn.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));
				bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
				bn.setTOURREMARKS(StringFormat.nullString(rs.getString("TOURREMARKS")));
				
				bn.setCOMPLIANCE(StringFormat.nullString(rs.getString("COMPLIANCE")));
				bn.setCOMPLIANCEREMARKS(StringFormat.nullString(rs.getString("COMPLIANCEREMARKS")));
				bn.setCOMPLIANCEDATE(StringFormat.nullString(rs.getString("COMPLIANCEDATE")));
				
				bn.setEOFFICEREFNO(StringFormat.nullString(rs.getString("EOFFICEREFNO")));
				
				bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
				bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
				bn.setIMARKINGTO(StringFormat.nullString(rs.getString("FMMARKTO")));
				bn.setIMARKINGON(StringFormat.nullString(rs.getString("FMMARKON")));
				bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
				bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
				bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
				bn.setDMARKINGTO(StringFormat.nullString(rs.getString("RETURNTO")));
				bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("RETURNON")));
				bn.setISBUDGET(StringFormat.nullString(rs.getString("ISBUDGET")));
				bn.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
				bn.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
				bn.setURGENCY(StringFormat.nullString(rs.getString("URGENCY")));
				bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
				bn.setVIPPARTY(StringFormat.nullString(rs.getString("VIPPARTY")));
				arr.add(bn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//result = "0~" + e.getMessage();
			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
	}else if(advSearch.equalsIgnoreCase("2")){
		
		String condSub="";
		DBConnection dbCon = new DBConnection();
		try {
				condSub += " AND (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) = UPPER('"+sString+"')";
				condSub += " AND TO_DATE(TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
				condSub += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				condSub += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
				String strSQL = " SELECT A.REFID, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID AND M.TYPE='R'),'0') ISATTACHMENT," +
								" A.REFNO,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE,A.REFERENCENAME,A.VIPSTATUS,A.STATECODE," +
								" NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT," +
								" A.REMARKS,A.TOURREMARKS,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '10' AND CODE<>'00' AND CODE = A.COMPLIANCE) COMPLIANCE,A.COMPLIANCEREMARKS,TO_CHAR(A.COMPLIANCEDATE,'DD/MM/YYYY') COMPLIANCEDATE,A.EOFFICEREFNO,(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO," +
								" (SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE," +
								" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.IMARKINGTO) FMMARKTO," +
								" TO_CHAR(A.REGISTRATIONDATE,'DD/MM/YYYY') FMMARKON," +
								" A.FILENO," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," +
								" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) RETURNTO," +
								" TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY') RETURNON, ISBUDGET," +
								" (SELECT ' Sd/- '||ROLENAME FROM MSTROLE WHERE ROLEID = A.SIGNEDBY) SIGNEDBY, TO_CHAR(A.SIGNEDON,'DD/MM') SIGNEDON, A.URGENCY,"+Qreply+" REPLY,A.VIPPARTY" +
								" FROM TRNREFERENCE A WHERE A.ROLEID = "+roleID+" "+condSub+" " +
								" ORDER BY A.REFCLASS, A.INCOMINGDATE, A.REFNO";
			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				TrnReference bn = new TrnReference();
				bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
				bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
				bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
				bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
				bn.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
				bn.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
				bn.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
				bn.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));
				bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
				bn.setTOURREMARKS(StringFormat.nullString(rs.getString("TOURREMARKS")));
				
				bn.setCOMPLIANCE(StringFormat.nullString(rs.getString("COMPLIANCE")));
				bn.setCOMPLIANCEREMARKS(StringFormat.nullString(rs.getString("COMPLIANCEREMARKS")));
				bn.setCOMPLIANCEDATE(StringFormat.nullString(rs.getString("COMPLIANCEDATE")));
				
				bn.setEOFFICEREFNO(StringFormat.nullString(rs.getString("EOFFICEREFNO")));
				
				bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
				bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
				bn.setIMARKINGTO(StringFormat.nullString(rs.getString("FMMARKTO")));
				bn.setIMARKINGON(StringFormat.nullString(rs.getString("FMMARKON")));
				bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
				bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
				bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
				bn.setDMARKINGTO(StringFormat.nullString(rs.getString("RETURNTO")));
				bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("RETURNON")));
				bn.setISBUDGET(StringFormat.nullString(rs.getString("ISBUDGET")));
				bn.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
				bn.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
				bn.setURGENCY(StringFormat.nullString(rs.getString("URGENCY")));
				bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
				bn.setVIPPARTY(StringFormat.nullString(rs.getString("VIPPARTY")));
				arr.add(bn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//result = "0~" + e.getMessage();
			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
	}else {
		//String result = "";
		String[] sArray=sString.split(" ");
		String condSub="";
		String condVip="";
		String condVipparty="";
		String condstate="";
		String condRef="";
		String condRem="";
		String condDate="";
		String orCond="";
		String condRefCls="";
		String condTOURRem="";
		String compliance="";
		String condEofficeRefNo="";
		String complianceRemarks="";
		String complianceDate="";
		DBConnection dbCon = new DBConnection();
	//	ArrayList<TrnReference> arr = new ArrayList<TrnReference>();
		try {
			if(sphrase.equalsIgnoreCase("0")){
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condSub = condSub+"UPPER(SUBJECT) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condVip = condVip+"UPPER(VIPSTATUS) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}
			
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condVipparty = condVip+"UPPER(VIPPARTY) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condRef = condRef+"UPPER(REFERENCENAME) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condstate = condstate+"UPPER(STATECODE) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condstate = condstate+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1))) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length-1)?"OR":"";
				condRem = condRem+"UPPER(REMARKS) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}
			//Code added by Rounak
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length-1)?"OR":"";
				condTOURRem = condTOURRem+"UPPER(TOURREMARKS) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condEofficeRefNo = condEofficeRefNo+"UPPER(EOFFICEREFNO) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length-1)?"OR":"";
			//	compliance = compliance+"UPPER(COMPLIANCE) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				compliance = compliance+"compliance=(select code from mstgec where codetype='10' and sname like '%"+sArray[i]+"%') "+orCond+" ";
				}	
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length-1)?"OR":"";
				complianceRemarks = complianceRemarks+"UPPER(COMPLIANCEREMARKS) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				}	
			}
			
			if(fdate.length()>0 && tdate.length()>0){
				condDate = "AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
				condDate += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			}
			condRefCls += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
			}else{
				condSub = condSub+"UPPER(SUBJECT) LIKE UPPER('%"+sString+"%') OR ";
				condVip = condVip+"UPPER(VIPSTATUS) LIKE UPPER('%"+sString+"%') OR ";
				condVip = condVip+"UPPER(VIPPARTY) LIKE UPPER('%"+sString+"%') OR ";
				condRef = condRef+"UPPER(REFERENCENAME) LIKE UPPER('%"+sString+"%') OR ";
				condstate = condstate+"UPPER(STATECODE) LIKE UPPER('%"+sString+"%') OR ";
				condstate = condstate+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1))) LIKE UPPER('%"+sString+"%') OR ";
				condRem = condRem+"UPPER(REMARKS) LIKE UPPER('%"+sString+"%') OR ";
				condTOURRem = condTOURRem+"UPPER(TOURREMARKS) LIKE UPPER('%"+sString+"%') OR ";
				compliance = compliance+"UPPER(COMPLIANCE) LIKE UPPER('%"+sString+"%') OR ";
				condEofficeRefNo = condEofficeRefNo+"UPPER(EOFFICEREFNO) LIKE UPPER('%"+sString+"%') OR ";
				complianceRemarks = complianceRemarks+"UPPER(COMPLIANCEREMARKS) LIKE UPPER('%"+sString+"%')  ";
				if(fdate.length()>0 && tdate.length()>0){
					condDate = "AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
					condDate += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				}
				condRefCls += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
			}
			String strSQL = " SELECT A.REFID, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID AND M.TYPE='R'),'0') ISATTACHMENT," +
							" A.REFNO,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE,A.REFERENCENAME,A.VIPSTATUS,A.STATECODE," +
							" NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT," +
							" A.REMARKS,A.TOURREMARKS,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '10' AND CODE<>'00' AND CODE = A.COMPLIANCE) COMPLIANCE,A.COMPLIANCEREMARKS,TO_CHAR(A.COMPLIANCEDATE,'DD/MM/YYYY') COMPLIANCEDATE,A.EOFFICEREFNO,(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO," +
							" (SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE," +
							" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.IMARKINGTO) FMMARKTO," +
							" TO_CHAR(A.REGISTRATIONDATE,'DD/MM/YYYY') FMMARKON," +
							" A.FILENO," +
							" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," +
							" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," +
							" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) RETURNTO," +
							" TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY') RETURNON, ISBUDGET," +
							" (SELECT ' Sd/- '||ROLENAME FROM MSTROLE WHERE ROLEID = A.SIGNEDBY) SIGNEDBY, TO_CHAR(A.SIGNEDON,'DD/MM') SIGNEDON, A.URGENCY,"+Qreply+" REPLY,A.VIPPARTY" +
							" FROM TRNREFERENCE A WHERE A.ROLEID = "+roleID+" AND ("+condSub+" "+condVip+" "+condVipparty+" "+condRef+" "+condstate+" "+condRem+" "+condTOURRem+" "+compliance+" "+condEofficeRefNo+" "+complianceRemarks+") "+condDate+" "+condRefCls+"" +
							" ORDER BY A.REFCLASS, A.INCOMINGDATE, A.REFNO";
			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				TrnReference bn = new TrnReference();
				bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
				bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
				bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
				bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
				bn.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
				bn.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
				bn.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
				bn.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));
				bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
				bn.setTOURREMARKS(StringFormat.nullString(rs.getString("TOURREMARKS")));
				
				bn.setCOMPLIANCE(StringFormat.nullString(rs.getString("COMPLIANCE")));
				bn.setCOMPLIANCEREMARKS(StringFormat.nullString(rs.getString("COMPLIANCEREMARKS")));
				bn.setCOMPLIANCEDATE(StringFormat.nullString(rs.getString("COMPLIANCEDATE")));
				
				bn.setEOFFICEREFNO(StringFormat.nullString(rs.getString("EOFFICEREFNO")));
				
				bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
				bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
				bn.setIMARKINGTO(StringFormat.nullString(rs.getString("FMMARKTO")));
				bn.setIMARKINGON(StringFormat.nullString(rs.getString("FMMARKON")));
				bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
				bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
				bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
				bn.setDMARKINGTO(StringFormat.nullString(rs.getString("RETURNTO")));
				bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("RETURNON")));
				bn.setISBUDGET(StringFormat.nullString(rs.getString("ISBUDGET")));
				bn.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
				bn.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
				bn.setURGENCY(StringFormat.nullString(rs.getString("URGENCY")));
				bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
				bn.setVIPPARTY(StringFormat.nullString(rs.getString("VIPPARTY")));
				arr.add(bn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//result = "0~" + e.getMessage();
			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
	}
		return arr;
	}
	
	
	public ArrayList<TrnReference> getSearchSubRefReply(String sString,String fdate,String tdate,String sphrase, String roleID, String isConf,String isReply, String advSearch, String refClass) {
		
		ArrayList<TrnReference> arr = new ArrayList<TrnReference>();
		String Qreply = "";	
		if(isReply.equalsIgnoreCase("1")){
			Qreply=isReply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=A.FMID)";
		}
		else{
			Qreply="''";
		}
		
		String Areply="";
		Areply = isReply.equalsIgnoreCase("0")? "'0'": " NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.FMID AND M.TYPE='F'),'0')";
		
		
		
		if(sString.length() > 4 && advSearch.equalsIgnoreCase("")) {
			if(sString.substring(0, 5).equalsIgnoreCase("USER ")){
				String loginId = sString.substring(5);
				String condSub="";
				DBConnection dbCon = new DBConnection();
				try {
						condSub += " AND UPPER(LOGINID) LIKE UPPER('"+loginId+"')";
						condSub += " AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
						condSub += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
						condSub += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
						String strSQL = " SELECT A.REFID, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID AND M.TYPE='R'),'0') ISATTACHMENT, "+Areply+" ISATTACHMENTFILE," +
										" A.REFNO,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE,A.REFERENCENAME,A.VIPSTATUS,A.STATECODE," +
										" NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT," +
										" A.REMARKS,(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO," +
										" (SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE," +
										" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.IMARKINGTO) FMMARKTO," +
										" TO_CHAR(A.REGISTRATIONDATE,'DD/MM/YYYY') FMMARKON," +
										" A.FILENO," +
										" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," +
										" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," +
										" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) RETURNTO," +
										" TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY') RETURNON,REPLYTYPE, ISBUDGET," +
										" (SELECT ' Sd/- '||ROLENAME FROM MSTROLE WHERE ROLEID = A.SIGNEDBY) SIGNEDBY, TO_CHAR(A.SIGNEDON,'DD/MM') SIGNEDON, A.URGENCY,"+Qreply+" REPLY,A.FMID" +
										" FROM TRNREFERENCE A WHERE A.ROLEID = "+roleID+" "+condSub+" " +
										" ORDER BY A.REFCLASS, A.INCOMINGDATE";
					dbCon.openConnection();
					log.debug(strSQL);
					ResultSet rs = dbCon.select(strSQL);
					while (rs.next()) {
						TrnReference bn = new TrnReference();
						bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
						bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
						bn.setISATTACHMENTFILE(StringFormat.nullString(rs.getString("ISATTACHMENTFILE")));
						bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
						bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
						bn.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
						bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
						bn.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
						bn.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
						bn.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
						bn.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));
						bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
						bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
						bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
						bn.setIMARKINGTO(StringFormat.nullString(rs.getString("FMMARKTO")));
						bn.setIMARKINGON(StringFormat.nullString(rs.getString("FMMARKON")));
						bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
						bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
						bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
						bn.setDMARKINGTO(StringFormat.nullString(rs.getString("RETURNTO")));
						bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("RETURNON")));
						bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
						bn.setISBUDGET(StringFormat.nullString(rs.getString("ISBUDGET")));
						bn.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
						bn.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
						bn.setURGENCY(StringFormat.nullString(rs.getString("URGENCY")));
						bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
						bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
						arr.add(bn);
					}
				} catch (Exception e) {
					e.printStackTrace();
					//result = "0~" + e.getMessage();
					dbCon.rollback();
				} finally {
					dbCon.closeConnection();
				}
			}else {
				String[] sArray=sString.split(" ");
				String condSub="";
				String condVip="";
				String condstate="";
				String condRef="";
				String condRem="";
				String condDate="";
				String orCond="";
				String condRefCls="";
				DBConnection dbCon = new DBConnection();
		//		ArrayList<TrnReference> arr = new ArrayList<TrnReference>();
				try {
					if(sphrase.equalsIgnoreCase("0")){
					for(int i=0;i<sArray.length;i++){
						if(!sArray[i].trim().equalsIgnoreCase("")){
						orCond = (i<sArray.length)?"OR":"";
						condSub = condSub+"UPPER(SUBJECT) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
						}
					}
					for(int i=0;i<sArray.length;i++){
						if(!sArray[i].trim().equalsIgnoreCase("")){
						orCond = (i<sArray.length)?"OR":"";
						condVip = condVip+"UPPER(VIPSTATUS) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
						}
					}
			//	REFNO & INCOMING DATE
					for(int i=0;i<sArray.length;i++){
						if(!sArray[i].trim().equalsIgnoreCase("")){
							orCond = (i<sArray.length)?"OR":"";
							condVip = condVip+"UPPER(REFNO) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
						}
					}
					for(int i=0;i<sArray.length;i++){
						if(!sArray[i].trim().equalsIgnoreCase("")){
							orCond = (i<sArray.length)?"OR":"";
							condVip = condVip+"TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') = '"+sArray[i]+"' "+orCond+" ";
						}
					}
					for(int i=0;i<sArray.length;i++){
						if(!sArray[i].trim().equalsIgnoreCase("")){
							orCond = (i<sArray.length)?"OR":"";
							condRef = condRef+"UPPER(REFERENCENAME) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
						}
					}
					for(int i=0;i<sArray.length;i++){
						if(!sArray[i].trim().equalsIgnoreCase("")){
						orCond = (i<sArray.length)?"OR":"";
						condstate = condstate+"UPPER(STATECODE) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
						}
					}
					for(int i=0;i<sArray.length;i++){
						if(!sArray[i].trim().equalsIgnoreCase("")){
						orCond = (i<sArray.length)?"OR":"";
						condstate = condstate+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1))) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
						}
					}
					for(int i=0;i<sArray.length;i++){
						if(!sArray[i].trim().equalsIgnoreCase("")){
						orCond = (i<sArray.length-1)?"OR":"";
						condRem = condRem+"UPPER(REMARKS) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
						}
					}
					if(fdate.length()>0 && tdate.length()>0){
						condDate = "AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
						condDate += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
					}
					condRefCls += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
					}else{
						condSub = condSub+"UPPER(SUBJECT) LIKE UPPER('%"+sString+"%') OR ";
						condVip = condVip+"UPPER(VIPSTATUS) LIKE UPPER('%"+sString+"%') OR ";
						condVip = condVip+"UPPER(REFNO) LIKE UPPER('%"+sString+"%') OR ";
						condVip = condVip+"TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') = '"+sString+"' OR ";
						condRef = condRef+"UPPER(REFERENCENAME) LIKE UPPER('%"+sString+"%') OR ";
						condstate = condstate+"UPPER(STATECODE) LIKE UPPER('%"+sString+"%') OR ";
						condstate = condstate+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1))) LIKE UPPER('%"+sString+"%') OR ";
						condRem = condRem+"UPPER(REMARKS) LIKE UPPER('%"+sString+"%')  ";
						if(fdate.length()>0 && tdate.length()>0){
							condDate = "AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
							condDate += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
						}
						condRefCls += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
					}
					String strSQL = " SELECT A.REFID, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID  AND M.TYPE='R'),'0') ISATTACHMENT,"+Areply+" ISATTACHMENTFILE," +
									" A.REFNO,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE,A.REFERENCENAME,A.VIPSTATUS,A.STATECODE," +
									" NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT," +
									" A.REMARKS,(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO," +
									" (SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE," +
									" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.IMARKINGTO) FMMARKTO," +
									" TO_CHAR(A.REGISTRATIONDATE,'DD/MM/YYYY') FMMARKON," +
									" A.FILENO," +
									" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," +
									" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," +
									" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) RETURNTO," +
									" TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY') RETURNON,REPLYTYPE, ISBUDGET," +
									" (SELECT ' Sd/- '||ROLENAME FROM MSTROLE WHERE ROLEID = A.SIGNEDBY) SIGNEDBY, TO_CHAR(A.SIGNEDON,'DD/MM') SIGNEDON, A.URGENCY,"+Qreply+" REPLY,A.FMID" +
									" FROM TRNREFERENCE A WHERE A.ROLEID = "+roleID+" AND ("+condSub+" "+condVip+" "+condRef+" "+condstate+" "+condRem+") "+condDate+" "+condRefCls+"" +
									" ORDER BY A.REFCLASS, A.INCOMINGDATE";
					dbCon.openConnection();
					log.debug(strSQL);
					ResultSet rs = dbCon.select(strSQL);
					while (rs.next()) {
						TrnReference bn = new TrnReference();
						bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
						bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
						bn.setISATTACHMENTFILE(StringFormat.nullString(rs.getString("ISATTACHMENTFILE")));
						bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
						bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
						bn.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
						bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
						bn.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
						bn.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
						bn.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
						bn.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));
						bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
						bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
						bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
						bn.setIMARKINGTO(StringFormat.nullString(rs.getString("FMMARKTO")));
						bn.setIMARKINGON(StringFormat.nullString(rs.getString("FMMARKON")));
						bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
						bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
						bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
						bn.setDMARKINGTO(StringFormat.nullString(rs.getString("RETURNTO")));
						bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("RETURNON")));
						bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
						bn.setISBUDGET(StringFormat.nullString(rs.getString("ISBUDGET")));
						bn.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
						bn.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
						bn.setURGENCY(StringFormat.nullString(rs.getString("URGENCY")));
						bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
						bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
						arr.add(bn);
					}
				} catch (Exception e) {
					e.printStackTrace();
					//result = "0~" + e.getMessage();
					dbCon.rollback();
				} finally {
					dbCon.closeConnection();
				}
			}
		}else if(advSearch.equalsIgnoreCase("0")){
			
			String condSub="";
			DBConnection dbCon = new DBConnection();
			try {
					condSub += " AND UPPER(REFERENCENAME) LIKE UPPER('%"+sString+"%')";
					condSub += " AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
					condSub += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
					condSub += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
					String strSQL = " SELECT A.REFID, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID  AND M.TYPE='R'),'0') ISATTACHMENT,"+Areply+" ISATTACHMENTFILE," +
									" A.REFNO,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE,A.REFERENCENAME,A.VIPSTATUS,A.STATECODE," +
									" NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT," +
									" A.REMARKS,(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO," +
									" (SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE," +
									" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.IMARKINGTO) FMMARKTO," +
									" TO_CHAR(A.REGISTRATIONDATE,'DD/MM/YYYY') FMMARKON," +
									" A.FILENO," +
									" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," +
									" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," +
									" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) RETURNTO," +
									" TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY') RETURNON,REPLYTYPE, ISBUDGET," +
									" (SELECT ' Sd/- '||ROLENAME FROM MSTROLE WHERE ROLEID = A.SIGNEDBY) SIGNEDBY, TO_CHAR(A.SIGNEDON,'DD/MM') SIGNEDON, A.URGENCY,"+Qreply+" REPLY,A.FMID" +
									" FROM TRNREFERENCE A WHERE A.ROLEID = "+roleID+" "+condSub+" " +
									" ORDER BY A.REFCLASS, A.INCOMINGDATE";
				dbCon.openConnection();
				log.debug(strSQL);
				ResultSet rs = dbCon.select(strSQL);
				while (rs.next()) {
					TrnReference bn = new TrnReference();
					bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
					bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
					bn.setISATTACHMENTFILE(StringFormat.nullString(rs.getString("ISATTACHMENTFILE")));
					bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
					bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
					bn.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
					bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
					bn.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
					bn.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
					bn.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
					bn.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));
					bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
					bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
					bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
					bn.setIMARKINGTO(StringFormat.nullString(rs.getString("FMMARKTO")));
					bn.setIMARKINGON(StringFormat.nullString(rs.getString("FMMARKON")));
					bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
					bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
					bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
					bn.setDMARKINGTO(StringFormat.nullString(rs.getString("RETURNTO")));
					bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("RETURNON")));
					bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
					bn.setISBUDGET(StringFormat.nullString(rs.getString("ISBUDGET")));
					bn.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
					bn.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
					bn.setURGENCY(StringFormat.nullString(rs.getString("URGENCY")));
					bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
					bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
					arr.add(bn);
				}
			} catch (Exception e) {
				e.printStackTrace();
				//result = "0~" + e.getMessage();
				dbCon.rollback();
			} finally {
				dbCon.closeConnection();
			}
		}else if(advSearch.equalsIgnoreCase("1")){

			String condSub="";
			DBConnection dbCon = new DBConnection();
			try {
					condSub += " AND UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1))) = UPPER('"+sString+"')";
					condSub += " AND TO_DATE((SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
					condSub += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
					condSub += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
					String strSQL = " SELECT A.REFID, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID AND M.TYPE='R'),'0') ISATTACHMENT,"+Areply+" ISATTACHMENTFILE," +
									" A.REFNO,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE,A.REFERENCENAME,A.VIPSTATUS,A.STATECODE," +
									" NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT," +
									" A.REMARKS,(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO," +
									" (SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE," +
									" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.IMARKINGTO) FMMARKTO," +
									" TO_CHAR(A.REGISTRATIONDATE,'DD/MM/YYYY') FMMARKON," +
									" A.FILENO," +
									" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," +
									" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," +
									" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) RETURNTO," +
									" TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY') RETURNON,REPLYTYPE, ISBUDGET," +
									" (SELECT ' Sd/- '||ROLENAME FROM MSTROLE WHERE ROLEID = A.SIGNEDBY) SIGNEDBY, TO_CHAR(A.SIGNEDON,'DD/MM') SIGNEDON, A.URGENCY,"+Qreply+" REPLY,A.FMID" +
									" FROM TRNREFERENCE A WHERE A.ROLEID = "+roleID+" "+condSub+" " +
									" ORDER BY A.REFCLASS, A.INCOMINGDATE";
				dbCon.openConnection();
				log.debug(strSQL);
				ResultSet rs = dbCon.select(strSQL);
				while (rs.next()) {
					TrnReference bn = new TrnReference();
					bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
					bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
					bn.setISATTACHMENTFILE(StringFormat.nullString(rs.getString("ISATTACHMENTFILE")));
					bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
					bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
					bn.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
					bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
					bn.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
					bn.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
					bn.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
					bn.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));
					bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
					bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
					bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
					bn.setIMARKINGTO(StringFormat.nullString(rs.getString("FMMARKTO")));
					bn.setIMARKINGON(StringFormat.nullString(rs.getString("FMMARKON")));
					bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
					bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
					bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
					bn.setDMARKINGTO(StringFormat.nullString(rs.getString("RETURNTO")));
					bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("RETURNON")));
					bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
					bn.setISBUDGET(StringFormat.nullString(rs.getString("ISBUDGET")));
					bn.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
					bn.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
					bn.setURGENCY(StringFormat.nullString(rs.getString("URGENCY")));
					bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
					bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
					arr.add(bn);
				}
			} catch (Exception e) {
				e.printStackTrace();
				//result = "0~" + e.getMessage();
				dbCon.rollback();
			} finally {
				dbCon.closeConnection();
			}
		}else if(advSearch.equalsIgnoreCase("2")){
			
			String condSub="";
			DBConnection dbCon = new DBConnection();
			try {
					condSub += " AND (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) = UPPER('"+sString+"')";
					condSub += " AND TO_DATE(TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
					condSub += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
					condSub += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
					String strSQL = " SELECT A.REFID, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID AND M.TYPE='R'),'0') ISATTACHMENT,"+Areply+" ISATTACHMENTFILE," +
									" A.REFNO,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE,A.REFERENCENAME,A.VIPSTATUS,A.STATECODE," +
									" NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT," +
									" A.REMARKS,(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO," +
									" (SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE," +
									" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.IMARKINGTO) FMMARKTO," +
									" TO_CHAR(A.REGISTRATIONDATE,'DD/MM/YYYY') FMMARKON," +
									" A.FILENO," +
									" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," +
									" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," +
									" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) RETURNTO," +
									" TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY') RETURNON,REPLYTYPE, ISBUDGET," +
									" (SELECT ' Sd/- '||ROLENAME FROM MSTROLE WHERE ROLEID = A.SIGNEDBY) SIGNEDBY, TO_CHAR(A.SIGNEDON,'DD/MM') SIGNEDON, A.URGENCY,"+Qreply+" REPLY,A.FMID" +
									" FROM TRNREFERENCE A WHERE A.ROLEID = "+roleID+" "+condSub+" " +
									" ORDER BY A.REFCLASS, A.INCOMINGDATE";
				dbCon.openConnection();
				log.debug(strSQL);
				ResultSet rs = dbCon.select(strSQL);
				while (rs.next()) {
					TrnReference bn = new TrnReference();
					bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
					bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
					bn.setISATTACHMENTFILE(StringFormat.nullString(rs.getString("ISATTACHMENTFILE")));
					bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
					bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
					bn.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
					bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
					bn.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
					bn.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
					bn.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
					bn.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));
					bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
					bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
					bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
					bn.setIMARKINGTO(StringFormat.nullString(rs.getString("FMMARKTO")));
					bn.setIMARKINGON(StringFormat.nullString(rs.getString("FMMARKON")));
					bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
					bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
					bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
					bn.setDMARKINGTO(StringFormat.nullString(rs.getString("RETURNTO")));
					bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("RETURNON")));
					bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
					bn.setISBUDGET(StringFormat.nullString(rs.getString("ISBUDGET")));
					bn.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
					bn.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
					bn.setURGENCY(StringFormat.nullString(rs.getString("URGENCY")));
					bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
					bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
					arr.add(bn);
				}
			} catch (Exception e) {
				e.printStackTrace();
				//result = "0~" + e.getMessage();
				dbCon.rollback();
			} finally {
				dbCon.closeConnection();
			}
		}else {
			//String result = "";
			String[] sArray=sString.split(" ");
			String condSub="";
			String condVip="";
			String condstate="";
			String condRef="";
			String condRem="";
			String condDate="";
			String orCond="";
			String condRefCls="";
			DBConnection dbCon = new DBConnection();
		//	ArrayList<TrnReference> arr = new ArrayList<TrnReference>();
			try {
				if(sphrase.equalsIgnoreCase("0")){
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condSub = condSub+"UPPER(SUBJECT) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condVip = condVip+"UPPER(VIPSTATUS) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
						orCond = (i<sArray.length)?"OR":"";
						condRef = condRef+"UPPER(REFERENCENAME) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condstate = condstate+"UPPER(STATECODE) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condstate = condstate+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1))) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length-1)?"OR":"";
					condRem = condRem+"UPPER(REMARKS) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
					}
				}
				if(fdate.length()>0 && tdate.length()>0){
					condDate = "AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
					condDate += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				}
				condRefCls += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
				}else{
					condSub = condSub+"UPPER(SUBJECT) LIKE UPPER('%"+sString+"%') OR ";
					condVip = condVip+"UPPER(VIPSTATUS) LIKE UPPER('%"+sString+"%') OR ";
					condRef = condRef+"UPPER(REFERENCENAME) LIKE UPPER('%"+sString+"%') OR ";
					condstate = condstate+"UPPER(STATECODE) LIKE UPPER('%"+sString+"%') OR ";
					condstate = condstate+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1))) LIKE UPPER('%"+sString+"%') OR ";
					condRem = condRem+"UPPER(REMARKS) LIKE UPPER('%"+sString+"%')  ";
					if(fdate.length()>0 && tdate.length()>0){
						condDate = "AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
						condDate += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
					}
					condRefCls += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
				}
				String strSQL = " SELECT A.REFID, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID AND M.TYPE='R'),'0') ISATTACHMENT,"+Areply+" ISATTACHMENTFILE," +
								" A.REFNO,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE,A.REFERENCENAME,A.VIPSTATUS,A.STATECODE," +
								" NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT," +
								" A.REMARKS,(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO," +
								" (SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE," +
								" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.IMARKINGTO) FMMARKTO," +
								" TO_CHAR(A.REGISTRATIONDATE,'DD/MM/YYYY') FMMARKON," +
								" A.FILENO," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," +
								" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," +
								" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) RETURNTO," +
								" TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY') RETURNON,REPLYTYPE, ISBUDGET," +
								" (SELECT ' Sd/- '||ROLENAME FROM MSTROLE WHERE ROLEID = A.SIGNEDBY) SIGNEDBY, TO_CHAR(A.SIGNEDON,'DD/MM') SIGNEDON, A.URGENCY,"+Qreply+" REPLY,A.FMID" +
								" FROM TRNREFERENCE A WHERE A.ROLEID = "+roleID+" AND ("+condSub+" "+condVip+" "+condRef+" "+condstate+" "+condRem+") "+condDate+" "+condRefCls+"" +
								" ORDER BY A.REFCLASS, A.INCOMINGDATE";
				dbCon.openConnection();
				log.debug(strSQL);
				ResultSet rs = dbCon.select(strSQL);
				while (rs.next()) {
					TrnReference bn = new TrnReference();
					bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
					bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
					bn.setISATTACHMENTFILE(StringFormat.nullString(rs.getString("ISATTACHMENTFILE")));
					bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
					bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
					bn.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
					bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
					bn.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
					bn.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
					bn.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
					bn.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));
					bn.setREMARKS(StringFormat.nullString(rs.getString("REMARKS")));
					bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
					bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
					bn.setIMARKINGTO(StringFormat.nullString(rs.getString("FMMARKTO")));
					bn.setIMARKINGON(StringFormat.nullString(rs.getString("FMMARKON")));
					bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
					bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
					bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
					bn.setDMARKINGTO(StringFormat.nullString(rs.getString("RETURNTO")));
					bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("RETURNON")));
					bn.setREPLYTYPE(StringFormat.nullString(rs.getString("REPLYTYPE")));
					bn.setISBUDGET(StringFormat.nullString(rs.getString("ISBUDGET")));
					bn.setSIGNEDBY(StringFormat.nullString(rs.getString("SIGNEDBY")));
					bn.setSIGNEDON(StringFormat.nullString(rs.getString("SIGNEDON")));
					bn.setURGENCY(StringFormat.nullString(rs.getString("URGENCY")));
					bn.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
					bn.setFMID(StringFormat.nullString(rs.getString("FMID")));
					arr.add(bn);
				}
			} catch (Exception e) {
				e.printStackTrace();
				//result = "0~" + e.getMessage();
				dbCon.rollback();
			} finally {
				dbCon.closeConnection();
			}
		}
			return arr;
		}
		
	
	public ArrayList<TrnReference> getRefDetail(String refname, String vipstatus) {
		//String result = "";
		DBConnection dbCon = new DBConnection();
		ArrayList<TrnReference> arr = new ArrayList<TrnReference>();
		 
		try {

			String strSQL = " SELECT  A.REFNO,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE, "
					+ " TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE,A.REFERENCENAME,A.VIPSTATUS,"
					+ "NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT,"
					+ " to_char(A.INCOMINGDATE,'YYYYMMDD') ORDERDATE,A.STATECODE," +
					"(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO," +
					"(SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE," +
					"(SELECT F.FNAME FROM MSTGEC F WHERE F.CODETYPE = 4 AND F.CODE =(SELECT M.ACTION FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) ACTION FROM TRNREFERENCE A"
					+ " WHERE A.REFERENCENAME='"
					+ refname
					+ "' AND A.VIPSTATUS='"
					+ vipstatus + "'  ORDER BY  ORDERDATE DESC";

			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				TrnReference bn = new TrnReference();
				
				bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));

				bn.setINCOMINGDATE(StringFormat.nullString(rs
						.getString("INCOMINGDATE")));
				bn.setLETTERDATE(StringFormat.nullString(rs
						.getString("LETTERDATE")));
				bn.setREFERENCENAME(StringFormat.nullString(rs
						.getString("REFERENCENAME")));

				bn.setVIPSTATUS(StringFormat.nullString(rs
						.getString("VIPSTATUS")));
				
				bn.setSUBJECTCODE(StringFormat.nullString(rs
						.getString("SUBJECTCODE")));
				bn.setSTATECODE(StringFormat.nullString(rs
						.getString("STATECODE")));
				bn.setMARKINGTO(StringFormat.nullString(rs
						.getString("MARKINGTO")));
				bn.setMARKINGDATE(StringFormat.nullString(rs
						.getString("MARKINGDATE")));
				bn.setACTION(StringFormat.nullString(rs
						.getString("ACTION")));
				
				

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

	public ArrayList<String> getCounterStatusNew(String tenureid) {

		DBConnection dbCon = new DBConnection();
		ArrayList<String> arr = new ArrayList<String>();
		try {

			String strSQL = "  SELECT B.REFCLASS REFCLASS, B.TENUREID, "
					+ " REFCOUNTER COUNTER, "
					+ " NVL(B.CLASSDESCRIPTION, ' ') CLASSDESCRIPTION "
					+ " FROM MSTCLASS B WHERE B.TENUREID = '" + tenureid+ "'"
					+ " AND YEAR = TO_CHAR(SYSDATE,'YYYY') ORDER BY 1 ";

			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
//				arr.add("<font color=\"grey\" size=2px> " +rs.getString(1) +"("+rs.getString(4)+")" +"<font>" + " :  <font color=\"green\" size=2px > " + rs.getString(3)+"<font>");
				arr.add("<font color=\"blue\" size=2px> " +rs.getString(1)+"<font>" + " :  <font color=\"green\" size=2px > " + rs.getString(3)+"<font>");
				//arr.add(rs.getString(3));
			}

		} catch (Exception e) {
			e.printStackTrace();

			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return arr;
	}

	public String getClassCounter(String tenureid, String refclass) {
		String result = "";
		DBConnection dbCon = new DBConnection();
		try {

			/*
			 * String strSQL =
			 * " SELECT NVL(MAX(REFCOUNT),0) COUNTER FROM TRNREFERENCE WHERE " +
			 * " TENUREID ='"+ tenureid+ "'  AND REFCLASS='" + refclass +
			 * "'    ";
			 */
			String strSQL = " SELECT (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=A.ROLEID)||'/'||REFCLASS||'/'||REFCOUNTER||'/'||YEAR "
					+ " FROM MSTCLASS A WHERE TENUREID ='"
					+ tenureid
					+ "'  AND UPPER(REFCLASS)=UPPER('"
					+ refclass
					+ "') AND YEAR = TO_CHAR(SYSDATE, 'YYYY')";
			log.debug(strSQL);
			dbCon.openConnection();
			ResultSet rs = dbCon.select(strSQL);

			if (rs.next()) {
				result = rs.getString(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "0~" + e.getMessage();
			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return result;
	}

	public String saveBudgetData(ArrayList<TrnBudget> trnBudgetBeanList, String LOGINID) {
		String result = "";
		if(trnBudgetBeanList.size()>0) {
		DBConnection con = new DBConnection();
		try {
			con.openConnection();
			// update the parent table to set the budget flag to true
			String updateQueryRef = " UPDATE TRNREFERENCE SET ISBUDGET = 'Y' WHERE REFID = '"+trnBudgetBeanList.get(0).getREFID()+"'";
			log.debug(updateQueryRef);
			con.update(updateQueryRef);

			// skip first row for hidden row
			if (trnBudgetBeanList.get(0).getREFID() != null && trnBudgetBeanList.get(0).getSUBJECT() != null) {
				// String GeneratedBUDGETSEQUENCE = "";
				String budgetDeleteQRY = "DELETE FROM TRNBUDGET WHERE REFID = '"+trnBudgetBeanList.get(0).getREFID()+"'";
				log.debug(budgetDeleteQRY);
				con.delete(budgetDeleteQRY);
			}
			for (int i = 0; i < trnBudgetBeanList.size(); i++) {
				TrnBudget trnBudgetBean = trnBudgetBeanList.get(i);

				trnBudgetBean.setBUDGETSEQUENCE(getNextBudgetSequence(con, "BUDGETSEQUENCE", "TRNBUDGET", trnBudgetBean.getREFID()));
				log.info("Generated Budget Sequence is  ************* " + trnBudgetBean.getBUDGETSEQUENCE());

				String budgetQuery = " INSERT INTO TRNBUDGET (REFID, BUDGETSEQUENCE, SUBJECTCODE, SUBJECT, MRREMARK, REMARK, LOGINID, CHANGEDATE) " +
									 " VALUES ('"+ trnBudgetBean.getREFID()+ "'," +
									 " '"+trnBudgetBean.getBUDGETSEQUENCE()+"'," +
									 " '"+trnBudgetBean.getSUBJECTCODE()+"'," +
									 " '"+trnBudgetBean.getSUBJECT()+"'," +
									 " '"+trnBudgetBean.getMRREMARK()+"'," +
									 " '"+trnBudgetBean.getREMARK()+"'," +
									 " '"+LOGINID+"'," +
									 " SYSDATE)";
				log.debug(budgetQuery);
				con.insert(budgetQuery);
			}
			result = "1~Data Saved";
		} catch (Exception e) {
			e.printStackTrace();
			result = "0~" + e.getMessage();
			con.rollback();
		} finally {
			con.closeConnection();
		}
		}
		return result;
	}

	public String deleteBudgetRow(String refId, String budgetSequence) {
		String result = "";
		DBConnection con = new DBConnection();
		try {
			con.openConnection();

			String budgetQuery = " 	DELETE FROM TRNBUDGET WHERE REFID = '"
					+ refId + "' AND BUDGETSEQUENCE = '" + budgetSequence + "'";

			log.debug(budgetQuery);
			con.delete(budgetQuery);

			result = "1~Data Saved";

		} catch (SQLException e) {
			e.printStackTrace();
			result = "0~" + e.getMessage();
			con.rollback();
		} finally {
			con.closeConnection();
		}
		return result;

	}

	public String saveFileStatus(String REFID, String FILENO,
			String REGISTRATIONDATE, String IMARKINGTO, String FILESTATUS1,
			String FILESTATUS2, String STATUSREMARK, String REPLYTYPE,
			String REASON, String DMARKINGTO, String DMARKINGDATE,
			String LOGINID) {
		String result = "";
		DBConnection con = new DBConnection();
		try {
			con.openConnection();

			String statusQuery = " UPDATE TRNREFERENCE SET "
					+ " FILENO = UPPER('"
					+ FILENO
					+ "'), "
					+ " REGISTRATIONDATE = TO_DATE('"
					+ REGISTRATIONDATE
					+ "','DD/MM/YYYY'), "
					+ " IMARKINGTO = '"
					+ IMARKINGTO
					+ "',"
					+ " FILESTATUS1 = '"
					+ FILESTATUS1
					+ "', "
					+ " FILESTATUS2 = '"
					+ FILESTATUS2
					+ "', "
					+ " STATUSREMARK = '"
					+ STATUSREMARK
					+ "', "
					+ " REPLYTYPE = '"
					+ REPLYTYPE
					+ "', "
					+ " REASON = '"
					+ REASON
					+ "', "
					+ " DMARKINGTO = '"
					+ DMARKINGTO
					+ "', "
					+ " DMARKINGDATE = TO_DATE('"
					+ DMARKINGDATE
					+ "','DD/MM/YYYY HH24:MI'), "
					/*+ " LOGINID = '"
					+ LOGINID
					+ "',"*/
					+ " CHANGEDATE = SYSDATE "
					+ " WHERE REFID = '"
					+ REFID + "' ";

			log.debug(statusQuery);
			con.update(statusQuery);

			result = "1~Data Saved";

		} catch (Exception e) {
			con.rollback();
			result = "0~" + e.getMessage();
			log.fatal(e, e);

		} finally {
			con.closeConnection();
		}

		return result;

	}

public ArrayList<TrnReference> getSearchData(String roleId,String refNofrom, String refNoTo, String incomingDateFrom,String incomingDateTo, String referenceNameSearch,
			String subjectSearch, String REMARKSEARCH,
			String commonSearchValue, String flag, String isConf)
{
		ArrayList<TrnReference> arrList = new ArrayList<TrnReference>();
		DBConnection con = new DBConnection();
		String strSQL = "";
		String tempSQL = "";
		String[] arrRefNoFrom = null;
		String[] arrRefNoTo = null;
		String refClassFrom = null;
		String inout = "";
		ResultSet rs = null;
		if (refNofrom.trim().length() > 0 && refNoTo.trim().length() > 0)
		{
			arrRefNoFrom = refNofrom.split("/");
			arrRefNoTo = refNoTo.split("/");
			refClassFrom = arrRefNoFrom[0];
			String refCountFrom = arrRefNoFrom[1];
			String refCountTo = arrRefNoTo[1];
			tempSQL += " AND UPPER(A.REFCLASS) = UPPER('"+refClassFrom+"') AND (A.REFCOUNT BETWEEN '"+refCountFrom+"' AND '"+refCountTo+"')";
			
			String sql = "SELECT INOUT FROM MSTCLASS WHERE REFCLASS = UPPER('"+refClassFrom+"')";
			con.openConnection();
			try {
				rs = con.select(sql);
				if(rs.next()){ inout = StringFormat.nullString(rs.getString("INOUT")); }
			}
			catch (SQLException e){
				e.printStackTrace();
			}
			con.closeConnection();
		}
		if(inout.equalsIgnoreCase("O"))
		{
			strSQL = " SELECT  A.REFID, A.REFNO, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID and STATUSFLAG='1' and TYPE='R'),'0') ISATTACHMENT," +
					 " TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,"
					+ "TO_CHAR(LETTERDATE,'DD/MM/YYYY') LETTERDATE,NVL(REFERENCENAME,'-') REFERENCENAME,VIPSTATUS,STATECODE,"
					+ "NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT,"
					+ "(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO,"
					+ "(SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE,"
					+ " REMARKS STATUSREMARK, RECIEVEDBY, TAG1, TAG2, TAG3, TAG4,  "
					+ " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.IMARKINGTO) FMMARKTO,"
					+ " TO_CHAR(A.REGISTRATIONDATE,'DD/MM/YYYY') FMMARKON," 
					+ " A.FILENO," 
					+ " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," 
					+ " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," 
					+ " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) RETURNTO," 
					+ " TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY') RETURNON, DECODE(ISBUDGET,'Y', '-BUDGET','') ISBUDGET, A.URGENCY" 
					+ " FROM TRNREFERENCE A WHERE A.ROLEID = '" + roleId + "'  ";
		}else
		{
			strSQL = " SELECT  A.REFID, A.REFNO, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID and STATUSFLAG='1' and TYPE='R'),'0') ISATTACHMENT," +
					 " TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,"
					+ "TO_CHAR(LETTERDATE,'DD/MM/YYYY') LETTERDATE,NVL(REFERENCENAME,'-') REFERENCENAME,VIPSTATUS,STATECODE,"
					+ "NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT,"
					+ "(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO,"
					+ "(SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE,"
					+ " REMARKS STATUSREMARK, RECIEVEDBY, TAG1, TAG2, TAG3, TAG4,  "
					+ " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.IMARKINGTO) FMMARKTO,"
					+ " TO_CHAR(A.REGISTRATIONDATE,'DD/MM/YYYY') FMMARKON," 
					+ " A.FILENO," 
					+ " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," 
					+ " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," 
					+ " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) RETURNTO," 
					+ " TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY') RETURNON, DECODE(ISBUDGET,'Y', '-BUDGET','') ISBUDGET, A.URGENCY " 
					+ " FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID AND B.MARKINGSEQUENCE=1 AND A.ROLEID = '" + roleId + "'  ";
		}
		tempSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
		
		if (incomingDateFrom.trim().length() > 0 && incomingDateTo.trim().length() > 0) {
			tempSQL += " AND TO_DATE(TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+incomingDateFrom+"','DD/MM/YYYY') AND TO_DATE('"+incomingDateTo+"','DD/MM/YYYY')";
		}
		
		if (referenceNameSearch.trim().length() > 0) {
			tempSQL += " AND UPPER(A.REFERENCENAME) LIKE UPPER('%"+referenceNameSearch+"%')";
		}
		
		if (subjectSearch.trim().length() > 0) {
			tempSQL += " AND UPPER(A.SUBJECT) LIKE UPPER('%"+subjectSearch+"%')";
		}
		
		if (REMARKSEARCH.trim().length() > 0) {
			tempSQL += " AND UPPER(A.REMARKS) LIKE UPPER('%"+REMARKSEARCH+"%')";
		}
		
		if (commonSearchValue.trim().length() > 0) {
			tempSQL += " AND B.MARKINGTO = (SELECT ROLEID FROM MSTROLE WHERE ROLENAME = UPPER('"+commonSearchValue+"'))";
		}
		strSQL += tempSQL;
		strSQL += " ORDER BY A.REFID DESC";
		log.debug(strSQL);
		
		try {
			con.openConnection();
			rs = con.select(strSQL);
			while (rs.next()) {
				TrnReference bn = new TrnReference();
				bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
				bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
				bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
				bn.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
				bn.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
				bn.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
				bn.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
				bn.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));
				bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
				bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
				bn.setSTATUSREMARK(StringFormat.nullString(rs.getString("STATUSREMARK")));
				bn.setRECIEVEDBY(StringFormat.nullString(rs.getString("RECIEVEDBY")));
				bn.setTAG1(StringFormat.nullString(rs.getString("TAG1")));
				bn.setTAG2(StringFormat.nullString(rs.getString("TAG2")));
				bn.setTAG3(StringFormat.nullString(rs.getString("TAG3")));
				bn.setTAG4(StringFormat.nullString(rs.getString("TAG4")));
				
				bn.setIMARKINGTO(StringFormat.nullString(rs.getString("FMMARKTO")));
				bn.setIMARKINGON(StringFormat.nullString(rs.getString("FMMARKON")));
				bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
				bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
				bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
				bn.setDMARKINGTO(StringFormat.nullString(rs.getString("RETURNTO")));
				bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("RETURNON")));
				bn.setISBUDGET(StringFormat.nullString(rs.getString("ISBUDGET")));
				bn.setURGENCY(StringFormat.nullString(rs.getString("URGENCY")));
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
	
	public ArrayList<TrnReference> getSearchDataOnRefNo(String roleId, String refNofrom, String refNoTo, String isConf) {
		
		ArrayList<TrnReference> arrList = new ArrayList<TrnReference>();
		DBConnection con = new DBConnection();
		
		String strSQL = " SELECT  A.REFID, NVL((SELECT DISTINCT '1' FROM TRNATTACHMENT M WHERE M.REFID = A.REFID),'0') ISATTACHMENT," +
						" A.REFNO,TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,"
			+ "TO_CHAR(LETTERDATE,'DD/MM/YYYY') LETTERDATE,NVL(REFERENCENAME,'-') REFERENCENAME,VIPSTATUS,STATECODE,"
			+ "NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT," +
			" (SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO,"
			+ "(SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE," +
			" REMARKS STATUSREMARK, RECIEVEDBY, TAG1, TAG2, TAG3, TAG4," +
			" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.IMARKINGTO) FMMARKTO," +
			" TO_CHAR(A.REGISTRATIONDATE,'DD/MM/YYYY') FMMARKON," +
			" A.FILENO," +
			" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = A.FILESTATUS1) FILESTATUS1," +
			" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = A.FILESTATUS2) FILESTATUS2," +
			" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) RETURNTO," +
			" TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY') RETURNON, DECODE(ISBUDGET,'Y', '-BUDGET','') ISBUDGET, LOGINID,A.VIPPARTY" +
			" FROM TRNREFERENCE A WHERE A.ROLEID = '" + roleId + "' ";
		String tempSQL = "";
		tempSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
		
		if (refNofrom.trim().length() > 0 && refNoTo.trim().length() > 0) {
			String[] arrRefNoFrom = refNofrom.split("/");
			String[] arrRefNoTo = refNoTo.split("/");
			String refClassFrom = arrRefNoFrom[0];
			String refCountFrom = arrRefNoFrom[1];
			String refCountTo = arrRefNoTo[1];
			tempSQL += " AND  UPPER(A.REFCLASS) = UPPER('"+refClassFrom+"') " +
				// " AND (TO_CHAR(REFERENCEDATE, 'YYYY') BETWEEN '"+
				// refYearFrom+"' AND '"+refYearTo+"') " +
				" AND (A.REFCOUNT BETWEEN '" + refCountFrom + "' AND '"	+ refCountTo + "')";
		}
		strSQL += tempSQL;
		strSQL += " ORDER BY A.REFID DESC";
		
		log.debug(strSQL);
		try {
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				TrnReference bn = new TrnReference();
				bn.setREFID(StringFormat.nullString(rs.getString("REFID")));
				bn.setISATTACHMENT(StringFormat.nullString(rs.getString("ISATTACHMENT")));
				bn.setREFNO(StringFormat.nullString(rs.getString("REFNO")));
				bn.setSUBJECT(StringFormat.nullString(rs.getString("SUBJECT")));
				bn.setINCOMINGDATE(StringFormat.nullString(rs.getString("INCOMINGDATE")));
				bn.setLETTERDATE(StringFormat.nullString(rs.getString("LETTERDATE")));
				bn.setREFERENCENAME(StringFormat.nullString(rs.getString("REFERENCENAME")));
				bn.setVIPSTATUS(StringFormat.nullString(rs.getString("VIPSTATUS")));
				bn.setSTATECODE(StringFormat.nullString(rs.getString("STATECODE")));
				bn.setSUBJECTCODE(StringFormat.nullString(rs.getString("SUBJECTCODE")));
				bn.setMARKINGTO(StringFormat.nullString(rs.getString("MARKINGTO")));
				bn.setMARKINGDATE(StringFormat.nullString(rs.getString("MARKINGDATE")));
				bn.setSTATUSREMARK(StringFormat.nullString(rs.getString("STATUSREMARK")));
				bn.setRECIEVEDBY(StringFormat.nullString(rs.getString("RECIEVEDBY")));
				bn.setTAG1(StringFormat.nullString(rs.getString("TAG1")));
				bn.setTAG2(StringFormat.nullString(rs.getString("TAG2")));
				bn.setTAG3(StringFormat.nullString(rs.getString("TAG3")));
				bn.setTAG4(StringFormat.nullString(rs.getString("TAG4")));
				
				bn.setIMARKINGTO(StringFormat.nullString(rs.getString("FMMARKTO")));
				bn.setIMARKINGON(StringFormat.nullString(rs.getString("FMMARKON")));
				bn.setFILENO(StringFormat.nullString(rs.getString("FILENO")));
				bn.setFILESTATUS1(StringFormat.nullString(rs.getString("FILESTATUS1")));
				bn.setFILESTATUS2(StringFormat.nullString(rs.getString("FILESTATUS2")));
				bn.setDMARKINGTO(StringFormat.nullString(rs.getString("RETURNTO")));
				bn.setDMARKINGDATE(StringFormat.nullString(rs.getString("RETURNON")));
				bn.setISBUDGET(StringFormat.nullString(rs.getString("ISBUDGET")));
				bn.setLOGINID(StringFormat.nullString(rs.getString("LOGINID")));
				bn.setVIPPARTY(StringFormat.nullString(rs.getString("VIPPARTY")));
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

	public ArrayList<TrnReference> getSearchDataNew(String roleId,
			String refNofrom, String refNoTo, String incomingDateFrom,
			String incomingDateTo, String referenceNameSearch,
			String subjectSearch, String commonSearch, String commonSearchValue) {

		ArrayList<TrnReference> arrList = new ArrayList<TrnReference>();
		DBConnection dbCon = new DBConnection();

		String strSQL = " SELECT REFID, TENUREID, REFNO, 'Anoop' SUBJECT, ROLEID, REFCLASS, REFCOUNT, TO_CHAR(INCOMINGDATE, 'DD/MM/YYYY') INCOMINGDATE, REFERENCENAME,"
				+ " TO_CHAR(LETTERDATE, 'DD/MM/YYYY') LETTERDATE, ISBUDGET, VIPSTATUS, STATECODE, TO_CHAR(ACKDATE, 'DD/MM/YYYY') ACKDATE, ACKBY, REFCATEGORY, LANGUAGE, URGENCY,"
				+ " LINKREFID, NOTECREATOR, SECURITYGRADING, SIGNEDBY, TO_CHAR(SIGNEDON, 'DD/MM/YYYY') SIGNEDON, REMARKS, ODSPLACE, ODSRAILWAY,"
				+ " TO_CHAR(ODSDATE, 'DD/MM/YYYY') ODSDATE, UPPER(LOGINID), CHANGEDATE "
				+ " FROM TRNREFERENCE A WHERE A.ROLEID = '" + roleId + "' AND ";
		String tempSQL = "";

		if (refNofrom.trim().length() > 0 && refNoTo.trim().length() > 0) {

			tempSQL += (tempSQL.length() > 0) ? " AND " : "";

			// code to handle REFNO Search
			String[] arrRefNoFrom = refNofrom.split("/");
			String[] arrRefNoTo = refNoTo.split("/");

			String refClassFrom = arrRefNoFrom[0];
			String refCountFrom = arrRefNoFrom[1];
			String refYearFrom = arrRefNoFrom[2];

			// String refClassTo = arrRefNoTo[0];
			String refCountTo = arrRefNoTo[1];
			String refYearTo = arrRefNoTo[2];

			// tempSQL += " A.REFNO BETWEEN '"+refNofrom+"' AND '"+refNoTo+"'";
			tempSQL += " UPPER(A.REFCLASS) = UPPER('" + refClassFrom + "') "
					+ " AND (TO_CHAR(REFERENCEDATE, 'YYYY') BETWEEN '"
					+ refYearFrom + "' AND '" + refYearTo + "') "
					+ " AND (A.REFCOUNT BETWEEN '" + refCountFrom + "' AND '"
					+ refCountTo + "')";

		}

		if (incomingDateFrom.trim().length() > 0
				&& incomingDateTo.trim().length() > 0) {
			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
			tempSQL += " A.INCOMINGDATE BETWEEN TO_DATE('" + incomingDateFrom
					+ "', 'DD/MM/YYYY') AND TO_DATE('" + incomingDateTo
					+ "', 'DD/MM/YYYY')";

		}

		if (referenceNameSearch.trim().length() > 0) {
			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
			tempSQL += " UPPER(A.REFERENCENAME) LIKE UPPER('%"
					+ referenceNameSearch + "%')";

		}

		if (subjectSearch.trim().length() > 0) {
			tempSQL += (tempSQL.length() > 0) ? " AND " : "";
			tempSQL += " UPPER(A.SUBJECT) LIKE UPPER('%" + subjectSearch
					+ "%')";
		}
		
		if (commonSearch.trim().length() > 0) {
			// tempSQL += (tempSQL.length() > 0) ? " AND " : "";
			tempSQL += " AND A.ISBUDGET = UPPER('"+commonSearch+"')";
		}

		if (commonSearchValue.trim().length() > 0) {
			// tempSQL += (tempSQL.length() > 0) ? " AND " : "";
			tempSQL += " AND A.DMARKINGTO = '"+commonSearchValue+"'";
		}
		strSQL += tempSQL;
		strSQL += " ORDER BY A.REFID DESC";

		try {
			log.debug(strSQL);
			dbCon.openConnection();
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				TrnReference refBean = new TrnReference();

				refBean
						.setREFID(StringFormat
								.nullString(rs.getString("REFID")));
				refBean
						.setREFNO(StringFormat
								.nullString(rs.getString("REFNO")));
				// refBean.setTENUREID(StringFormat.nullString(rs.getString(
				// "TENUREID")));
				refBean.setSUBJECT(StringFormat.nullString(rs
						.getString("SUBJECT")));
				//refBean.setROLEID(StringFormat.nullString(rs.getString("ROLEID"
				// )));
				refBean.setREFCLASS(StringFormat.nullString(rs
						.getString("REFCLASS")));
				refBean.setREFCOUNT(StringFormat.nullString(rs
						.getString("REFCOUNT")));
				refBean.setINCOMINGDATE(StringFormat.nullString(rs
						.getString("INCOMINGDATE")));
				refBean.setREFERENCENAME(StringFormat.nullString(rs
						.getString("REFERENCENAME")));
				refBean.setLETTERDATE(StringFormat.nullString(rs
						.getString("LETTERDATE")));
				refBean.setISBUDGET(StringFormat.nullString(rs
						.getString("ISBUDGET")));
				refBean.setVIPSTATUS(StringFormat.nullString(rs
						.getString("VIPSTATUS")));
				refBean.setSTATECODE(StringFormat.nullString(rs
						.getString("STATECODE")));
				refBean.setACKDATE(StringFormat.nullString(rs
						.getString("ACKDATE")));
				refBean
						.setACKBY(StringFormat
								.nullString(rs.getString("ACKBY")));
				refBean.setREFCATEGORY(StringFormat.nullString(rs
						.getString("REFCATEGORY")));
				refBean.setLANGUAGE(StringFormat.nullString(rs
						.getString("LANGUAGE")));
				refBean.setURGENCY(StringFormat.nullString(rs
						.getString("URGENCY")));
				refBean.setLINKREFID(StringFormat.nullString(rs
						.getString("LINKREFID")));
				refBean.setNOTECREATOR(StringFormat.nullString(rs
						.getString("NOTECREATOR")));
				refBean.setSECURITYGRADING(StringFormat.nullString(rs
						.getString("SECURITYGRADING")));
				refBean.setSIGNEDBY(StringFormat.nullString(rs
						.getString("SIGNEDBY")));
				refBean.setSIGNEDON(StringFormat.nullString(rs
						.getString("SIGNEDON")));
				refBean.setREMARKS(StringFormat.nullString(rs
						.getString("REMARKS")));
				refBean.setODSPLACE(StringFormat.nullString(rs
						.getString("ODSPLACE")));
				refBean.setODSRAILWAY(StringFormat.nullString(rs
						.getString("ODSRAILWAY")));
				refBean.setODSDATE(StringFormat.nullString(rs
						.getString("ODSDATE")));
				// refBean.setLOGINID(StringFormat.nullString(rs.getString(
				// "LOGINID")));
				// refBean.setCHANGEDATE(StringFormat.nullString(rs.getString(
				// "CHANGEDATE")));

				// getting the marking table details
				refBean
						.setMarkingBeanList(getMarkingSearch(refBean.getREFID()));

				arrList.add(refBean);
			}
			rs.close();

		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {

			dbCon.closeConnection();
		}

		return arrList;

	}

	public ArrayList<TrnMarking> getMarkingSearch(String refId) {
		ArrayList<TrnMarking> markingBeanList = new ArrayList<TrnMarking>();
		DBConnection dbCon = new DBConnection();

		// Getting the Detail Marking Table Details
		/*
		 * String strSQLMarking =
		 * " SELECT REFID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO, TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') MARKINGDATE, MARKINGREMARK, "
		 * +
		 * " TARGETDAYS, TO_CHAR(TARGETDATE, 'DD/MM/YYYY') TARGETDATE, SUBJECTCODE, SUBJECT "
		 * + " FROM TRNMARKING WHERE REFID = '" + refId + "'" // +
		 * " FROM TRNMARKING WHERE REFID IN (1, 2) " +
		 * " AND MARKINGSEQUENCE =1 ORDER BY MARKINGSEQUENCE ";
		 */

		String strSQLMarking = " SELECT REFID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO, TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') MARKINGDATE, MARKINGREMARK, "
				+ " TARGETDAYS, TO_CHAR(TARGETDATE, 'DD/MM/YYYY') TARGETDATE "
				+ " FROM TRNMARKING WHERE REFID = '" + refId + "'"
				// + " FROM TRNMARKING WHERE REFID IN (1, 2) "
				+ " AND MARKINGSEQUENCE =1 ORDER BY MARKINGSEQUENCE ";

		log.debug(strSQLMarking);
		dbCon.openConnection();
		try {
			ResultSet rsMarking = dbCon.select(strSQLMarking);

			while (rsMarking.next()) {
				TrnMarking markingBean = new TrnMarking();

				markingBean.setREFID(StringFormat.nullString(rsMarking
						.getString("REFID")));
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
				markingBean.setTARGETDAYS(StringFormat.nullString(rsMarking
						.getString("TARGETDAYS")));
				markingBean.setTARGETDATE(StringFormat.nullString(rsMarking
						.getString("TARGETDATE")));
				// markingBean.setSUBJECTCODE(StringFormat.nullString(rsMarking.
				// getString("SUBJECTCODE")));
				// markingBean.setSUBJECT(StringFormat.nullString(rsMarking.
				// getString("SUBJECT")));

				markingBeanList.add(markingBean);

			}
			rsMarking.close();

		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {

			dbCon.closeConnection();
		}

		return markingBeanList;
	}

	public ArrayList<CommonBean> getRefNameSearch(String refName) {
		ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
		DBConnection con = new DBConnection();

		String strSQL = " SELECT VIPNAME, VIPSTATUS, STATECODE  FROM MSTVIP ";

		try {
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				CommonBean bn = new CommonBean();
				bn.setField1(StringFormat.nullString(rs.getString("VIPNAME")));
				bn
						.setField2(StringFormat.nullString(rs
								.getString("VIPSTATUS")));
				bn
						.setField3(StringFormat.nullString(rs
								.getString("STATECODE")));

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

	public TrnReference getRefData(String refId,String isReply) {
		TrnReference refBean = null;
		String Qreply = "";
		
		if(isReply.equalsIgnoreCase("1")){
				Qreply=isReply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=A.FMID)";
			}
		else{
			
			Qreply="''";
		}
		DBConnection con = new DBConnection();
		try {

			// Getting the Header Table Details
			String strSQL = " SELECT REFID, TENUREID, REFNO, ROLEID, REFCLASS, REFCOUNT, TO_CHAR(INCOMINGDATE, 'DD/MM/YYYY') INCOMINGDATE, REFERENCENAME,ADDRESSENGLISH, SALUTATION,VIPSTATUSDESC,"
					+ " TO_CHAR(LETTERDATE, 'DD/MM/YYYY') LETTERDATE, ISBUDGET, VIPSTATUS, STATECODE, TO_CHAR(ACKDATE, 'DD/MM/YYYY') ACKDATE, ACKBY, REFCATEGORY, LANGUAGE, URGENCY,"
					+ " LINKREFID, (SELECT X.REFNO FROM TRNREFERENCE X WHERE X.REFID = A.LINKREFID) LINKREFNO, NOTECREATOR, SECURITYGRADING, SIGNEDBY, TO_CHAR(SIGNEDON, 'DD/MM/YYYY') SIGNEDON, REMARKS,TOURREMARKS,COMPLIANCE,COMPLIANCEREMARKS,TO_CHAR(COMPLIANCEDATE,'DD/MM/YYYY') COMPLIANCEDATE, ODSPLACE, ODSRAILWAY,"
					+ " TO_CHAR(ODSDATE, 'DD/MM/YYYY') ODSDATE, LOGINID, CHANGEDATE, FMID,"+Qreply+" REPLY, "
					+ " FILECOUNTER, REGISTRATIONNO, FILENO,EOFFICENO, IMARKINGTO IMARKINGTO, FILESTATUS1, FILESTATUS2, REPLYTYPE, REASON, "
					+ " DMARKINGTO DMARKINGTO, TO_CHAR(DMARKINGDATE, 'DD/MM/YYYY') DMARKINGDATE, TO_CHAR(REGISTRATIONDATE, 'DD/MM/YYYY') REGISTRATIONDATE, SUBJECTCODE, SUBJECT, STATUSREMARK, RECIEVEDBY, TAG1, TAG2, TAG3, TAG4, TO_CHAR(IMARKINGDATE,'DD/MM/YYYY') IMARKINGDATE," +
							" TO_CHAR(ICHANGEDATE,'DD/MM/YYYY') ICHANGEDATE,VIPPARTY,ADDVIPTYPE,ADDVIPID,EOFFICEREFNO,EOFFICERECEIPTNO," +
							"KEYWORD1,KEYWORD2,KEYWORD3,K1,K2,K3"
					+ " FROM TRNREFERENCE A WHERE REFID = '"
					+ refId
					+ "'"
					+ " ORDER BY REFNO ";

			log.debug("Reference ** :-- " + strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while (rs.next()) {
				refBean = new TrnReference();

				refBean
						.setREFID(StringFormat
								.nullString(rs.getString("REFID")));
				 refBean.setTENUREID(StringFormat.nullString(rs.getString("TENUREID")));
				refBean
						.setREFNO(StringFormat
								.nullString(rs.getString("REFNO")));
				//refBean.setROLEID(StringFormat.nullString(rs.getString("ROLEID"
				// )));
				refBean.setREFCLASS(StringFormat.nullString(rs
						.getString("REFCLASS")));
				refBean.setREFCOUNT(StringFormat.nullString(rs
						.getString("REFCOUNT")));
				refBean.setINCOMINGDATE(StringFormat.nullString(rs
						.getString("INCOMINGDATE")));
				refBean.setREFERENCENAME(StringFormat.nullString(rs
						.getString("REFERENCENAME")));
				refBean.setADDRESSENGLISH(StringFormat.nullString(rs
						.getString("ADDRESSENGLISH")));
				refBean.setSALUTATION(StringFormat.nullString(rs
						.getString("SALUTATION")));
				refBean.setVIPSTATUSDESC(StringFormat.nullString(rs
						.getString("VIPSTATUSDESC")));
				refBean.setLETTERDATE(StringFormat.nullString(rs
						.getString("LETTERDATE")));
				
				refBean.setISBUDGET(StringFormat.nullString(rs
						.getString("ISBUDGET")));
				refBean.setVIPSTATUS(StringFormat.nullString(rs
						.getString("VIPSTATUS")));
				refBean.setSTATECODE(StringFormat.nullString(rs
						.getString("STATECODE")));
				refBean.setACKDATE(StringFormat.nullString(rs
						.getString("ACKDATE")));
				refBean
						.setACKBY(StringFormat
								.nullString(rs.getString("ACKBY")));
				refBean.setREFCATEGORY(StringFormat.nullString(rs
						.getString("REFCATEGORY")));
				refBean.setLANGUAGE(StringFormat.nullString(rs
						.getString("LANGUAGE")));
				refBean.setURGENCY(StringFormat.nullString(rs
						.getString("URGENCY")));
				refBean.setLINKREFID(StringFormat.nullString(rs
						.getString("LINKREFID")));
				refBean.setLINKREFNO(StringFormat.nullString(rs
						.getString("LINKREFNO")));
				refBean.setNOTECREATOR(StringFormat.nullString(rs
						.getString("NOTECREATOR")));
				refBean.setSECURITYGRADING(StringFormat.nullString(rs
						.getString("SECURITYGRADING")));
				refBean.setSIGNEDBY(StringFormat.nullString(rs
						.getString("SIGNEDBY")));
				refBean.setSIGNEDON(StringFormat.nullString(rs
						.getString("SIGNEDON")));
				refBean.setREMARKS(StringFormat.nullString(rs
						.getString("REMARKS")));
				refBean.setTOURREMARKS(StringFormat.nullString(rs
						.getString("TOURREMARKS")));
				refBean.setCOMPLIANCE(StringFormat.nullString(rs
						.getString("COMPLIANCE")));
				refBean.setCOMPLIANCEREMARKS(StringFormat.nullString(rs
						.getString("COMPLIANCEREMARKS")));
				refBean.setCOMPLIANCEDATE(StringFormat.nullString(rs
						.getString("COMPLIANCEDATE")));
				
				refBean.setODSPLACE(StringFormat.nullString(rs
						.getString("ODSPLACE")));
				refBean.setODSRAILWAY(StringFormat.nullString(rs
						.getString("ODSRAILWAY")));
				refBean.setODSDATE(StringFormat.nullString(rs
						.getString("ODSDATE")));
				// refBean.setLOGINID(StringFormat.nullString(rs.getString(
				// "LOGINID")));
				// refBean.setCHANGEDATE(StringFormat.nullString(rs.getString(
				// "CHANGEDATE")));
				refBean.setFMID(StringFormat.nullString(rs.getString("FMID")));
				refBean.setREPLY(StringFormat.nullString(rs.getString("REPLY")));
				//System.out.println("Reply ::" +refBean.getREPLY());
				refBean.setFILECOUNTER(StringFormat.nullString(rs
						.getString("FILECOUNTER")));
				refBean.setREGISTRATIONNO(StringFormat.nullString(rs
						.getString("REGISTRATIONNO")));
				refBean.setFILENO(StringFormat.nullString(rs
						.getString("FILENO")));
				refBean.setEOFFICEFILENO(StringFormat.nullString(rs
						.getString("EOFFICENO")));
				refBean.setEOFFICERECEIPTNO(StringFormat.nullString(rs
						.getString("EOFFICERECEIPTNO")));
				refBean.setIMARKINGTO(StringFormat.nullString(rs
						.getString("IMARKINGTO")));
				refBean.setFILESTATUS1(StringFormat.nullString(rs
						.getString("FILESTATUS1")));
				refBean.setFILESTATUS2(StringFormat.nullString(rs
						.getString("FILESTATUS2")));
				refBean.setREPLYTYPE(StringFormat.nullString(rs
						.getString("REPLYTYPE")));
				refBean.setREASON(StringFormat.nullString(rs
						.getString("REASON")));
				refBean.setDMARKINGTO(StringFormat.nullString(rs
						.getString("DMARKINGTO")));
				refBean.setDMARKINGDATE(StringFormat.nullString(rs
						.getString("DMARKINGDATE")));
				refBean.setREGISTRATIONDATE(StringFormat.nullString(rs
						.getString("REGISTRATIONDATE")));
				refBean.setSUBJECTCODE(StringFormat.nullString(rs
						.getString("SUBJECTCODE")));
				refBean.setSUBJECT(StringFormat.nullString(rs
						.getString("SUBJECT")));
				refBean.setSTATUSREMARK(StringFormat.nullString(rs
						.getString("STATUSREMARK")));
				refBean.setEOFFICEREFNO(StringFormat.nullString(rs
						.getString("EOFFICEREFNO")));
				refBean.setRECIEVEDBY(StringFormat.nullString(rs.getString("RECIEVEDBY")));
				refBean.setTAG1(StringFormat.nullString(rs.getString("TAG1")));
				refBean.setTAG2(StringFormat.nullString(rs.getString("TAG2")));
				refBean.setTAG3(StringFormat.nullString(rs.getString("TAG3")));
				refBean.setTAG4(StringFormat.nullString(rs.getString("TAG4")));
				refBean.setIMARKINGON(StringFormat.nullString(rs.getString("IMARKINGDATE")));
				refBean.setCHANGEDATE(StringFormat.nullString(rs.getString("ICHANGEDATE")));
				refBean.setLOGINID(StringFormat.nullString(rs.getString("LOGINID")));
				refBean.setVIPPARTY(StringFormat.nullString(rs.getString("VIPPARTY")));
				refBean.setADDVIPTYPE(StringFormat.nullString(rs.getString("ADDVIPTYPE")));
				refBean.setADDVIPID(StringFormat.nullString(rs.getString("ADDVIPID")));
				
				refBean.setKeywords1(StringFormat.nullString(rs.getString("KEYWORD1")));
				refBean.setKeywords2(StringFormat.nullString(rs.getString("KEYWORD2")));
				refBean.setKeywords3(StringFormat.nullString(rs.getString("KEYWORD3")));
				refBean.setK1(StringFormat.nullString(rs.getString("K1")));
				refBean.setK2(StringFormat.nullString(rs.getString("K2")));
				refBean.setK3(StringFormat.nullString(rs.getString("K3")));
				
				
			}
			rs.close();

			// Getting the Detail Marking Table Details
			/*
			 * String strSQLMarking =
			 * " SELECT REFID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO, TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') MARKINGDATE, MARKINGREMARK, "
			 * // + //
			 * " TARGETDAYS, TO_CHAR(TARGETDATE, 'DD/MM/YYYY') TARGETDATE, SUBJECTCODE, SUBJECT "
			 * + " TARGETDAYS, TO_CHAR(TARGETDATE, 'DD/MM/YYYY') TARGETDATE " +
			 * " FROM TRNMARKING WHERE REFID = '" + refId + "'" +
			 * " AND MARKINGSEQUENCE =1 ORDER BY MARKINGSEQUENCE ";
			 */
			String strSQLMarking = " SELECT REFID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO,  TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') MARKINGDATE, MARKINGREMARK,"
					+ " TARGETDAYS, TO_CHAR(TARGETDATE, 'DD/MM/YYYY') TARGETDATE, ACTION "
					+ " FROM TRNMARKING WHERE REFID = '"
					+ refId
					+ "' "
					+ " AND ( (MARKINGSEQUENCE = 3 AND ACTION = 'RET') OR (MARKINGSEQUENCE = 5) OR (MARKINGSEQUENCE = 1)) ORDER BY MARKINGSEQUENCE DESC";

			log.debug("Marking ** :-- " + strSQLMarking);
			ResultSet rsMarking = con.select(strSQLMarking);

			ArrayList<TrnMarking> markingBeanList = new ArrayList<TrnMarking>();
			// while (rsMarking.next()) {
			if (rsMarking.next()) {
				TrnMarking markingBean = new TrnMarking();

				markingBean.setREFID(StringFormat.nullString(rsMarking
						.getString("REFID")));
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
				markingBean.setTARGETDAYS(StringFormat.nullString(rsMarking
						.getString("TARGETDAYS")));
				markingBean.setTARGETDATE(StringFormat.nullString(rsMarking
						.getString("TARGETDATE")));
				// markingBean.setSUBJECTCODE(StringFormat.nullString(rsMarking.
				// getString("SUBJECTCODE")));
				// markingBean.setSUBJECT(StringFormat.nullString(rsMarking.
				// getString("SUBJECT")));

				markingBeanList.add(markingBean);

			}
			rsMarking.close();

			// now add the marking arraylist to the trnreference bean
			refBean.setMarkingBeanList(markingBeanList);

			// Getting the Detail Budget Table Details
			String strSQLBudget = " SELECT REFID, BUDGETSEQUENCE, SUBJECTCODE, SUBJECT, MRREMARK, REMARK "
					+ " FROM TRNBUDGET WHERE REFID = '"
					+ refId
					+ "' ORDER BY BUDGETSEQUENCE ";

			log.debug("Budget ** :-- " + strSQLBudget);
			ResultSet rsBudget = con.select(strSQLBudget);

			ArrayList<TrnBudget> budgetBeanList = new ArrayList<TrnBudget>();
			while (rsBudget.next()) {
				TrnBudget budgetBean = new TrnBudget();

				// markingBean.setREFID(StringFormat.nullString(rsMarking.
				// getString("REFID")));
				budgetBean.setREFID(StringFormat.nullString(rsBudget
						.getString("REFID")));
				budgetBean.setBUDGETSEQUENCE(StringFormat.nullString(rsBudget
						.getString("BUDGETSEQUENCE")));
				budgetBean.setSUBJECTCODE(StringFormat.nullString(rsBudget
						.getString("SUBJECTCODE")));
				budgetBean.setSUBJECT(StringFormat.nullString(rsBudget
						.getString("SUBJECT")));
				budgetBean.setMRREMARK(StringFormat.nullString(rsBudget
						.getString("MRREMARK")));
				budgetBean.setREMARK(StringFormat.nullString(rsBudget
						.getString("REMARK")));

				budgetBeanList.add(budgetBean);

			}
			rsBudget.close();
			// now add the budget arraylist to the trnreference bean
			refBean.setBudgetBeanList(budgetBeanList);
			
			// Getting the data from yellow slip Table
			String strSQLyellowSlip = " SELECT SLIPTXT, SIGNEDBY FROM TRNYELLOWSLIP WHERE REFID = '"+refId+"'";
			log.debug(strSQLyellowSlip);
			ResultSet rsYellowSlip = con.select(strSQLyellowSlip);
			if(rsYellowSlip.next()) {
				refBean.setTXT_NOTE(StringFormat.nullString(rsYellowSlip.getString("SLIPTXT")));
				refBean.setSIGNEDBY_YS(StringFormat.nullString(rsYellowSlip.getString("SIGNEDBY")));
			}
			rsYellowSlip.close();
		} catch (SQLException e) {
			log.fatal(e, e);
		} finally {
			con.closeConnection();
		}
		return refBean;
	}
	/*
	 * public ArrayList<TrnReminder> getOutBoxReminderData(String tenureId) {
	 * ArrayList<TrnReminder> reminderOutBeanList = new
	 * ArrayList<TrnReminder>();
	 * 
	 * DBConnection con = new DBConnection(); try {
	 * 
	 * String strSQLreminder =
	 * " SELECT A.REFID, (SELECT REFNO FROM TRNREFERENCE WHERE REFID = A.REFID) REFNO, REMINDERSEQUENCE,"
	 * + " REMINDERFROM, " +
	 * " (SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT B.ROLEID FROM MSTTENURE B WHERE B.TENUREID = REMINDERFROM) )  REMINDERFROMNAME, "
	 * +
	 * " REMINDERTO, TO_CHAR(REMINDERDATE, 'DD/MM/YYYY HH24:MI') REMINDERDATE,  REMINDERREMARK, TARGETDAYS,"
	 * + " TO_CHAR(TARGETDATE, 'DD/MM/YYYY') TARGETDATE, " +
	 * " (SELECT SUBJECTCODE FROM TRNREFERENCE WHERE REFID = A.REFID) SUBJECTCODE, "
	 * + " (SELECT SUBJECT FROM TRNREFERENCE WHERE REFID = A.REFID) SUBJECT," +
	 * " REMINDERACTION, TO_CHAR(REMINDERACTIONDATE, 'DD/MM/YYYY HH24:MI') REMINDERACTIONDATE"
	 * + " FROM TRNREMINDER A  WHERE A.REMINDERTO= '" + tenureId +
	 * "' AND A.REMINDERACTION ='RCD'";// + //
	 * " AND A.REMINDERSEQUENCE = ( SELECT MAX(X.REMINDERSEQUENCE) FROM TRNREMINDER X  WHERE X.REFID= A.REFID)"
	 * // ;
	 * 
	 * log.debug(strSQLreminder); con.openConnection(); ResultSet rsreminder =
	 * con.select(strSQLreminder); while (rsreminder.next()) { TrnReminder
	 * reminderBean = new TrnReminder();
	 * 
	 * reminderBean.setREFID(StringFormat.nullString(rsreminder
	 * .getString("REFID")));
	 * reminderBean.setREFNO(StringFormat.nullString(rsreminder
	 * .getString("REFNO"))); reminderBean.setREMINDERSEQUENCE(StringFormat
	 * .nullString(rsreminder.getString("REMINDERSEQUENCE")));
	 * reminderBean.setREMINDERFROM(StringFormat.nullString(rsreminder
	 * .getString("REMINDERFROM")));
	 * reminderBean.setREMINDERFROMNAME(StringFormat
	 * .nullString(rsreminder.getString("REMINDERFROMNAME")));
	 * reminderBean.setREMINDERTO(StringFormat.nullString(rsreminder
	 * .getString("REMINDERTO")));
	 * reminderBean.setREMINDERDATE(StringFormat.nullString(rsreminder
	 * .getString("REMINDERDATE"))); reminderBean.setREMINDERREMARK(StringFormat
	 * .nullString(rsreminder.getString("REMINDERREMARK")));
	 * reminderBean.setTARGETDAYS(StringFormat.nullString(rsreminder
	 * .getString("TARGETDAYS")));
	 * reminderBean.setTARGETDATE(StringFormat.nullString(rsreminder
	 * .getString("TARGETDATE")));
	 * reminderBean.setSUBJECTCODE(StringFormat.nullString(rsreminder
	 * .getString("SUBJECTCODE")));
	 * reminderBean.setSUBJECT(StringFormat.nullString(rsreminder
	 * .getString("SUBJECT"))); reminderBean.setREMINDERACTION(StringFormat
	 * .nullString(rsreminder.getString("REMINDERACTION"))); reminderBean
	 * .setREMINDERACTIONDATE(StringFormat .nullString(rsreminder
	 * .getString("REMINDERACTIONDATE")));
	 * 
	 * reminderOutBeanList.add(reminderBean);
	 * 
	 * } rsreminder.close();
	 * 
	 * } catch (SQLException e) { log.fatal(e, e); } finally {
	 * 
	 * con.closeConnection(); }
	 * 
	 * return reminderOutBeanList;
	 * 
	 * }
	 */

	/*
	 * public ArrayList<TrnReminder> getInboxReminderData(String tenureId) {
	 * ArrayList<TrnReminder> reminderBeanList = new ArrayList<TrnReminder>();
	 * 
	 * DBConnection con = new DBConnection(); try {
	 * 
	 * String strSQLREMINDER =
	 * " SELECT A.REFID, (SELECT REFNO FROM TRNREFERENCE WHERE REFID = A.REFID) REFNO, REMINDERSEQUENCE,"
	 * +
	 * " GETROLENAME(REMINDERFROM) REMINDERFROM, GETROLENAME(REMINDERTO) REMINDERTO, TO_CHAR(REMINDERDATE, 'DD/MM/YYYY HH24:MI') REMINDERDATE,"
	 * +
	 * " REMINDERREMARK, TARGETDAYS, TO_CHAR(TARGETDATE, 'DD/MM/YYYY') TARGETDATE, "
	 * +
	 * " (SELECT SUBJECTCODE FROM TRNREFERENCE WHERE REFID = A.REFID) SUBJECTCODE, "
	 * + " (SELECT SUBJECT FROM TRNREFERENCE WHERE REFID = A.REFID) SUBJECT," +
	 * " REMINDERACTION, " +
	 * " TO_CHAR(REMINDERACTIONDATE, 'DD/MM/YYYY HH24:MI') REMINDERACTIONDATE" +
	 * " FROM TRNREMINDER A  WHERE A.REMINDERTO= '" + tenureId +
	 * "' AND A.REMINDERACTION ='FOW' AND A.REMINDERACTION <> 'RCD' ";// + //
	 * " AND A.REMINDERSEQUENCE = ( SELECT MAX(X.REMINDERSEQUENCE) FROM TRNREMINDER X  WHERE X.REFID= A.REFID)"
	 * // ;
	 * 
	 * log.debug(strSQLREMINDER); con.openConnection(); ResultSet rsREMINDER =
	 * con.select(strSQLREMINDER); while (rsREMINDER.next()) { TrnReminder
	 * reminderBean = new TrnReminder();
	 * 
	 * reminderBean.setREFID(StringFormat.nullString(rsREMINDER
	 * .getString("REFID")));
	 * reminderBean.setREFNO(StringFormat.nullString(rsREMINDER
	 * .getString("REFNO"))); reminderBean.setREMINDERSEQUENCE(StringFormat
	 * .nullString(rsREMINDER.getString("REMINDERSEQUENCE")));
	 * reminderBean.setREMINDERFROM(StringFormat.nullString(rsREMINDER
	 * .getString("REMINDERFROM")));
	 * reminderBean.setREMINDERTO(StringFormat.nullString(rsREMINDER
	 * .getString("REMINDERTO")));
	 * reminderBean.setREMINDERDATE(StringFormat.nullString(rsREMINDER
	 * .getString("REMINDERDATE"))); reminderBean.setREMINDERREMARK(StringFormat
	 * .nullString(rsREMINDER.getString("REMINDERREMARK")));
	 * reminderBean.setTARGETDAYS(StringFormat.nullString(rsREMINDER
	 * .getString("TARGETDAYS")));
	 * reminderBean.setTARGETDATE(StringFormat.nullString(rsREMINDER
	 * .getString("TARGETDATE")));
	 * reminderBean.setSUBJECTCODE(StringFormat.nullString(rsREMINDER
	 * .getString("SUBJECTCODE")));
	 * reminderBean.setSUBJECT(StringFormat.nullString(rsREMINDER
	 * .getString("SUBJECT"))); reminderBean.setREMINDERACTION(StringFormat
	 * .nullString(rsREMINDER.getString("REMINDERACTION"))); reminderBean
	 * .setREMINDERACTIONDATE(StringFormat .nullString(rsREMINDER
	 * .getString("REMINDERACTIONDATE")));
	 * 
	 * reminderBeanList.add(reminderBean);
	 * 
	 * } rsREMINDER.close();
	 * 
	 * } catch (SQLException e) { log.fatal(e, e); } finally {
	 * 
	 * con.closeConnection(); }
	 * 
	 * return reminderBeanList;
	 * 
	 * }
	 */

	public ArrayList<TrnMarking> getReturnBoxData(String roleId) {
		ArrayList<TrnMarking> markingOutBeanList = new ArrayList<TrnMarking>();

		DBConnection con = new DBConnection();
		try {

			String strSQLMarking = " SELECT A.REFID, (SELECT REFNO FROM TRNREFERENCE WHERE REFID = A.REFID) REFNO, MARKINGSEQUENCE, "
					+ " MARKINGFROM, "
					+ " (SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = MARKINGFROM) MARKINGFROMNAME, "
					+ " MARKINGTO, "
					+ " (SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = MARKINGTO) MARKINGTONAME, "
					+ " TO_CHAR(MARKINGDATE, 'DD/MM/YYYY HH24:MI') MARKINGDATE,  MARKINGREMARK, TARGETDAYS,"
					+ " TO_CHAR(TARGETDATE, 'DD/MM/YYYY') TARGETDATE,"
					// + " SUBJECTCODE, SUBJECT, "
					+ " (SELECT SUBJECTCODE FROM TRNREFERENCE WHERE REFID = A.REFID) SUBJECTCODE, (SELECT SUBJECT FROM TRNREFERENCE WHERE REFID = A.REFID) SUBJECT, "
					+ " ACTION, TO_CHAR(ACTIONDATE, 'DD/MM/YYYY HH24:MI') ACTIONDATE "
					+ " FROM TRNMARKING A  WHERE A.MARKINGTO= '"
					+ roleId
					+ "' AND A.ACTION ='RET' AND 3 = ( SELECT MAX(X.MARKINGSEQUENCE) FROM TRNMARKING X  WHERE X.REFID = A.REFID)";

			log.debug(strSQLMarking);
			con.openConnection();
			ResultSet rsMarking = con.select(strSQLMarking);
			while (rsMarking.next()) {
				TrnMarking markingBean = new TrnMarking();

				markingBean.setREFID(StringFormat.nullString(rsMarking
						.getString("REFID")));
				markingBean.setREFNO(StringFormat.nullString(rsMarking
						.getString("REFNO")));
				markingBean.setMARKINGSEQUENCE(StringFormat
						.nullString(rsMarking.getString("MARKINGSEQUENCE")));
				markingBean.setMARKINGFROM(StringFormat.nullString(rsMarking
						.getString("MARKINGFROM")));
				markingBean.setMARKINGFROMNAME(StringFormat
						.nullString(rsMarking.getString("MARKINGFROMNAME")));
				markingBean.setMARKINGTO(StringFormat.nullString(rsMarking
						.getString("MARKINGTO")));
				markingBean.setMARKINGTONAME(StringFormat.nullString(rsMarking
						.getString("MARKINGTONAME")));
				markingBean.setMARKINGDATE(StringFormat.nullString(rsMarking
						.getString("MARKINGDATE")));
				markingBean.setMARKINGREMARK(StringFormat.nullString(rsMarking
						.getString("MARKINGREMARK")));
				markingBean.setTARGETDAYS(StringFormat.nullString(rsMarking
						.getString("TARGETDAYS")));
				markingBean.setTARGETDATE(StringFormat.nullString(rsMarking
						.getString("TARGETDATE")));
				markingBean.setSUBJECTCODE(StringFormat.nullString(rsMarking
						.getString("SUBJECTCODE")));
				markingBean.setSUBJECT(StringFormat.nullString(rsMarking
						.getString("SUBJECT")));
				markingBean.setACTION(StringFormat.nullString(rsMarking
						.getString("ACTION")));
				markingBean.setACTIONDATE(StringFormat.nullString(rsMarking
						.getString("ACTIONDATE")));

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

	public ArrayList<TrnMarking> setReturnBoxAction(String refId,
			String returnMarkFrom, String MarkingFrom, String MarkTo,
			String MarkingRemark, String targetDate, String subjectCode,
			String subject, String action) {

		ArrayList<TrnMarking> markingBeanList = new ArrayList<TrnMarking>();

		DBConnection dbcon = new DBConnection();
		try {

			dbcon.openConnection();

			String outBoxMarkingSequence = getNextMarkingSequence(dbcon,
					"MARKINGSEQUENCE", "TRNMARKING", refId);

			// two entries are saved in table one for receiving and one for
			// forwarding

			String markingQueryOne = " INSERT INTO TRNMARKING (REFID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO, MARKINGDATE, MARKINGREMARK, "
					// +
					// " TARGETDAYS, TARGETDATE, SUBJECTCODE, SUBJECT, ACTION, ACTIONDATE) "
					// + " VALUES (" + " '"
					+ " TARGETDAYS, TARGETDATE, ACTION, ACTIONDATE) "
					+ " VALUES (" + " '"
					+ refId
					+ "',"
					+ " '"
					+ outBoxMarkingSequence
					+ "',"
					+ " '"
					+ returnMarkFrom
					+ "',"
					+ " '"
					+ MarkingFrom
					+ "',"
					+ " SYSDATE, "
					+ " '"
					+ MarkingRemark
					+ "',"
					+ " '15',"
					+ " TO_DATE('"
					+ targetDate
					+ "','DD/MM/YYYY'),"
					+ "'RCD', " + " SYSDATE" + ")";
			log.debug(markingQueryOne);
			dbcon.insert(markingQueryOne);

			outBoxMarkingSequence = getNextMarkingSequence(dbcon,
					"MARKINGSEQUENCE", "TRNMARKING", refId);

			String markingQueryTwo = " INSERT INTO TRNMARKING (REFID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO, MARKINGDATE, MARKINGREMARK, "
					// +
					// " TARGETDAYS, TARGETDATE, SUBJECTCODE, SUBJECT, ACTION, ACTIONDATE) "
					// + " VALUES (" + " '"
					+ " TARGETDAYS, TARGETDATE, ACTION, ACTIONDATE) "
					+ " VALUES (" + " '"
					+ refId
					+ "',"
					+ " '"
					+ outBoxMarkingSequence
					+ "',"
					+ " '"
					+ MarkingFrom
					+ "',"
					+ " '"
					+ MarkTo
					+ "',"
					+ " SYSDATE, "
					+ " '"
					+ MarkingRemark
					+ "',"
					+ " '15',"
					+ " TO_DATE('"
					+ targetDate + "','DD/MM/YYYY'),"
					// + " '"+ subjectCode + "',"
					// + " '" + subject + "', "
					+ " '" + action + "', " + " SYSDATE" + ")";

			log.debug(markingQueryTwo);
			dbcon.insert(markingQueryTwo);

		} catch (Exception e) {
			log.fatal(e, e);
			dbcon.rollback();

		} finally {

			dbcon.closeConnection();
		}

		return markingBeanList;

	}

	public String saveReference(TrnReference trnRefBean, ArrayList<TrnMarking> trnMarkingBeanList, ArrayList<TrnBudget> trnBudgetBeanList) {
		String outMessage = "";
		String refId = "";
		DBConnection dbcon = new DBConnection();
		try {
			dbcon.openConnection();

			// inserting into marking detail table

			// variable for holding generated ref no and marking
			String refNoAndMarking = "";
			if(trnRefBean.getISBUDGET().equalsIgnoreCase("Y")){
				saveBudgetData(trnBudgetBeanList, trnRefBean.getLOGINID());				
			}
			
			for (int i = 0; i < trnMarkingBeanList.size(); i++) {

				TrnMarking trnMarkingBean = trnMarkingBeanList.get(i);
					
			
				//System.out.println("trnMarkingBean----------------------"+ trnMarkingBean.getEOFFICEREFNO());
				//System.out.println("trnMarkingBean----------------------"+ trnMarkingBean.getSUBJECT());
				//System.out.println("trnMarkingBean----------------------"+ trnMarkingBean.getSUBJECTCODE());
				// update OR insert for each row of the table
				if (trnRefBean.getREFID().trim().length() <= 0) {
					trnRefBean.setREFID(CommonDAO.getNextId(dbcon, "REFID",	"TRNREFERENCE"));
					refId = trnRefBean.getREFID();
					// trnRefBean.setREFCOUNT(getNextRefCount(dbcon, "REFCOUNT",
					// "TRNREFERENCE", trnRefBean.getTENUREID(),
					// trnRefBean.getREFCLASS()));
					trnRefBean.setREFCOUNT(MstClass.getRefCountSequence(
							trnRefBean.getROLEID(), trnRefBean.getTENUREID(),
							trnRefBean.getREFCLASS(), Calendar.getInstance()
									.get(Calendar.YEAR), dbcon));
					log.info("Generated Refcount is  ************* "
							+ trnRefBean.getREFCOUNT());

					// trnRefBean.setREFNO(trnRefBean.getREFNO()+"/"+
					// trnRefBean.getREFCOUNT() + "/"+
					// Calendar.getInstance().get(Calendar.YEAR));
					trnRefBean.setREFNO(trnRefBean.getLOGINASROLENAME() + "/"
							+ trnRefBean.getREFCLASS() + "/"
							+ trnRefBean.getREFCOUNT() + "/"
							+ Calendar.getInstance().get(Calendar.YEAR));

					// code for linking link ref id
					if (i == 0) {
						trnRefBean.setLINKREFID(trnRefBean.getREFID());
					}

					// Insert into Header Table
					
					String k1=trnMarkingBean.getKeywords2()==null || trnMarkingBean.getKeywords2().length()==0 ||  trnMarkingBean.getKeywords1().compareTo(trnMarkingBean.getKeywords2())<0?trnMarkingBean.getKeywords1():trnMarkingBean.getKeywords2();
					String k2=trnMarkingBean.getKeywords2()==null || trnMarkingBean.getKeywords2().length()==0 || trnMarkingBean.getKeywords1().compareTo(trnMarkingBean.getKeywords2())<0?trnMarkingBean.getKeywords2():trnMarkingBean.getKeywords1();
					
						
					
					//System.out.println("---------------------"+trnMarkingBean.getEOFFICERECEIPTNO());
					
					
					String refQuery = " INSERT INTO TRNREFERENCE ( REFID, TENUREID,  REFNO, ROLEID, REFCLASS, REFCOUNT, INCOMINGDATE, REFERENCENAME,ADDRESSENGLISH,SALUTATION,VIPSTATUSDESC,"
							+ " LETTERDATE, ISBUDGET, VIPSTATUS, STATECODE, ACKDATE, ACKBY, REFCATEGORY, LANGUAGE, URGENCY, LINKREFID, NOTECREATOR, SECURITYGRADING,"
							+ " SIGNEDBY, SIGNEDON, REMARKS,TOURREMARKS,COMPLIANCE,COMPLIANCEREMARKS,COMPLIANCEDATE, ODSPLACE, ODSRAILWAY, ODSDATE, LOGINID, CHANGEDATE, REFERENCEDATE, FMID, "
							+ " FILECOUNTER, REGISTRATIONNO, FILENO,EOFFICENO, IMARKINGTO, FILESTATUS1, FILESTATUS2, REPLYTYPE, REASON, "
							+ " DMARKINGTO, DMARKINGDATE, REGISTRATIONDATE, SUBJECTCODE, SUBJECT, STATUSREMARK, RECIEVEDBY, TAG1, TAG2, TAG3, TAG4,IMARKINGDATE,ICHANGEDATE,ISCONF,VIPPARTY,ADDVIPTYPE,ADDVIPID,EOFFICEREFNO,EOFFICERECEIPTNO" +
									",KEYWORD1,KEYWORD2,KEYWORD3,K1,K2,K3)"
							+ " VALUES (" + " '"
							+ trnRefBean.getREFID()
							+ "', "
							+ " '"
							+ trnRefBean.getTENUREID()
							+ "', "
							+ " '"
							+ trnRefBean.getREFNO()
							+ "', "
							+ " '"
							+ trnRefBean.getROLEID()
							+ "', "
							+ " '"
							+ trnRefBean.getREFCLASS()
							+ "', "
							+ " '"
							+ trnRefBean.getREFCOUNT()
							+ "', "
							+ " TO_DATE('"
							+ trnRefBean.getINCOMINGDATE()
							+ "', 'DD/MM/YYYY'), "
							+ " '"
							+ trnRefBean.getREFERENCENAME()
							+ "', "
							+ " '"
							+ trnRefBean.getADDRESSENGLISH()
							+ "', "
							+ " '"
							+ trnRefBean.getSALUTATION()
							+ "', "
							+ " '"
							+ trnRefBean.getVIPSTATUSDESC()
							+ "', "
							+ " TO_DATE('"
							+ trnRefBean.getLETTERDATE()
							+ "', 'DD/MM/YYYY'), "
							+ " '"
							+ trnRefBean.getISBUDGET()
							+ "', "
							+ " '"
							+ trnRefBean.getVIPSTATUS()
							+ "', "
							+ " '"
							+ trnRefBean.getSTATECODE()
							+ "', "
							+ " TO_DATE('"
							+ trnRefBean.getACKDATE()
							+ "', 'DD/MM/YYYY'), "
							+ " '"
							+ trnRefBean.getACKBY()
							+ "', "
							+ " '"
							+ trnRefBean.getREFCATEGORY()
							+ "', "
							+ " '"
							+ trnRefBean.getLANGUAGE()
							+ "', "
							+ " '"
							+ trnRefBean.getURGENCY()
							+ "', "
							+ " '"
							+ trnRefBean.getLINKREFID()
							+ "', "
							+ " '"
							+ trnRefBean.getNOTECREATOR()
							+ "', "
							+ " '"
							+ trnRefBean.getSECURITYGRADING()
							+ "', "
							+ " '"
							+ trnRefBean.getSIGNEDBY()
							+ "', "
							+ " TO_DATE('"
							+ trnRefBean.getSIGNEDON()
							+ "', 'DD/MM/YYYY'), "
							+ " '"
							+ trnRefBean.getREMARKS()
							+ "', "
							+ " '"
							+ trnRefBean.getTOURREMARKS()
							+ "', "
								+ " '"
							+ trnRefBean.getCOMPLIANCE()
							+ "', "
								+ " '"
							+ trnRefBean.getCOMPLIANCEREMARKS()
							+ "', "
							+ " TO_DATE('"
							+ trnRefBean.getCOMPLIANCEDATE()
							+ "', 'DD/MM/YYYY'), "
							+ " '"
							+ trnRefBean.getODSPLACE()
							+ "', "
							+ " '"
							+ trnRefBean.getODSRAILWAY()
							+ "', "
							+ " TO_DATE('"
							+ trnRefBean.getODSDATE()
							+ "', 'DD/MM/YYYY'), "
							+ " '"
							+ trnRefBean.getLOGINID()
							+ "', "
							+ " SYSDATE, "
							+ " SYSDATE, "
							+ " '"
							+ trnRefBean.getFMID()
							+ "', "
							+ " '"
							+ trnRefBean.getFILECOUNTER()
							+ "', "
							+ " '"
							+ trnRefBean.getREGISTRATIONNO()
							+ "', "
							+ " '"
							+ trnRefBean.getFILENO()
							+ "', "
							
							+ " '"
							+ trnRefBean.getEOFFICEFILENO()
							+ "', "
							
							
							+ " '"
							+ trnRefBean.getIMARKINGTO()
							+ "', "
							+ " '"
							+ trnRefBean.getFILESTATUS1()
							+ "', "
							+ " '"
							+ trnRefBean.getFILESTATUS2()
							+ "', "
							+ " '"
							+ trnRefBean.getREPLYTYPE()
							+ "', "
							+ " '"
							+ trnRefBean.getREASON()
							+ "', "
							+ " '"
							+ trnRefBean.getDMARKINGTO()
							+ "', "
							+ " TO_DATE('"
							+ trnRefBean.getDMARKINGDATE()
							+ "', 'DD/MM/YYYY'), "
							+ " TO_DATE('"
							+ trnRefBean.getREGISTRATIONDATE()
							+ "', 'DD/MM/YYYY'), "
							+ " '" + trnMarkingBean.getSUBJECTCODE() + "', "
							+ " '" + trnMarkingBean.getSUBJECT() + "', "
							+ " '" + trnRefBean.getSTATUSREMARK() + "', " 
							+ " '" + trnRefBean.getRECIEVEDBY() + "', " 
							+ " '" + trnRefBean.getTAG1() + "', " 
							+ " '" + trnRefBean.getTAG2() + "', " 
							+ " '" + trnRefBean.getTAG3() + "', " 
							+ " '" + trnRefBean.getTAG4() + "', " 
							+ " '" + trnRefBean.getIMARKINGON() + "', " 
							+ " to_date('" + trnRefBean.getCHANGEDATE() + "','dd/mm/yyyy'), " 
							+ " '" + trnRefBean.getISCONF() + "','"+trnRefBean.getVIPPARTY()+"','"+trnRefBean.getADDVIPTYPE()+"','"+trnRefBean.getADDVIPID()+"', " 
							+ "" +
							"'"+trnMarkingBean.getEOFFICEREFNO()+"','" + trnMarkingBean.getEOFFICERECEIPTNO() +"','"+ k1 +"','"+ k2 +"','"+ trnMarkingBean.getKeywords3() +"', "+
							"'" + k1 +"','"+ k2 +"','"+ trnMarkingBean.getKeywords3() +"' "+
									")";

					log.debug(refQuery);
					dbcon.insert(refQuery);
					
					//constituency update 
					if(trnRefBean.getADDVIPID().length()!=0)
					{
					String constituencyQuerry = "update trnreference a set VIPCONSTITUENCY = (select VIPCONSTITUENCY from mstvip where vipid = "+trnRefBean.getADDVIPID()+" ) where a.refid = '"+trnRefBean.getREFID()+"'";
					log.debug(constituencyQuerry);
					dbcon.update(constituencyQuerry);
					}
					// insert into Trnmarking
					trnMarkingBean.setMARKINGSEQUENCE(getNextMarkingSequence(
							dbcon, "MARKINGSEQUENCE", "TRNMARKING", trnRefBean
									.getREFID()));
					String markingQuery = " INSERT INTO TRNMARKING (REFID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO, MARKINGDATE, MARKINGREMARK, "
							// +
							// " TARGETDAYS, TARGETDATE, SUBJECTCODE, SUBJECT, ACTION, ACTIONDATE) "
							// + " VALUES (" + " '"
							+ " TARGETDAYS, TARGETDATE, ACTION, ACTIONDATE) "
							+ " VALUES (" + " '"
							+ trnRefBean.getREFID()
							+ "',"
							+ " '"
							+ trnMarkingBean.getMARKINGSEQUENCE()
							+ "',"
							+ " '"
							+ trnMarkingBean.getMARKINGFROM()
							+ "',"
							+ " '"
							+ trnMarkingBean.getMARKINGTO()
							+ "',"
							+ " SYSDATE,"
							+ " '"
							+ trnRefBean.getREMARKS()
							+ "',"
							+ " '"
							+ trnMarkingBean.getTARGETDAYS()
							+ "',"
							+ " TO_DATE('"
							+ trnMarkingBean.getTARGETDATE()
							+ "','DD/MM/YYYY'),"
							// + " '"+ trnMarkingBean.getSUBJECTCODE() + "',"
							// + " '"+ trnMarkingBean.getSUBJECT() + "',"
							+ " 'FOW'," + " SYSDATE" + ")";

					log.debug(markingQuery);
					dbcon.insert(markingQuery);
					
					String strSQL = "SELECT ROLENAME FROM MSTROLE WHERE ROLEID = '"
							+ trnMarkingBean.getMARKINGTO() + "'";
					log.debug(strSQL);
					String markingName = getStringParam(strSQL, dbcon);

					// set the variable to flash output message
					refNoAndMarking += "<BR>"
							+ StringFormat.leftPad(i + 1 + "", 2, ' ')
							+ ".  &nbsp; &nbsp; " + trnRefBean.getREFNO()
							+ " - " + markingName+ ".  &nbsp; &nbsp; /-"+trnRefBean.getREFID() + "/-";

					// reset refid to blank so that new refid is generated for
					// next
					// iteration of the detail tables for markign
				
					
					
					
					//ATTACHMENT SAVING CODE FROM EOFFICE
				/*	if(trnRefBean.getURL()!=null){
						
						String urlQuerry = "insert into trnattachment values ('"+trnRefBean.getREFID()+"','1','"+trnRefBean.getURL()+"','"+trnRefBean.getURL()+"','1','R','1')";
						log.debug(urlQuerry);
						dbcon.insert(urlQuerry);
					}*/
					
					
					trnRefBean.setREFID("");
					trnRefBean.setREFCOUNT("");
					trnRefBean.setREFNO("");

				
				
				
				} else { // if refid is not null then update query

					// update the header table
					
					String k1=trnMarkingBean.getKeywords2()==null || trnMarkingBean.getKeywords2().length()==0 || trnMarkingBean.getKeywords1().compareTo(trnMarkingBean.getKeywords2())<0?trnMarkingBean.getKeywords1():trnMarkingBean.getKeywords2();
					String k2=trnMarkingBean.getKeywords2()==null || trnMarkingBean.getKeywords2().length()==0 || trnMarkingBean.getKeywords1().compareTo(trnMarkingBean.getKeywords2())<0?trnMarkingBean.getKeywords2():trnMarkingBean.getKeywords1();
					
					
					
					String QueryUpdate = " UPDATE TRNREFERENCE SET " +
					// " REFNO = '"+trnRefBean.getREFNO()+"', " +
							// " ROLEID = '"+trnRefBean.getROLEID()+"', " +
							// " REFCLASS = '"+trnRefBean.getREFCLASS()+"', " +
							// " REFCOUNT = '"+trnRefBean.getREFCOUNT()+"', " +
							" INCOMINGDATE = TO_DATE('"
							+ trnRefBean.getINCOMINGDATE()
							+ "','DD/MM/YYYY'), " + " REFERENCENAME = '"
							+ trnRefBean.getREFERENCENAME() + "', "
							
							+ " ADDRESSENGLISH = '"
							+ trnRefBean.getADDRESSENGLISH() + "', "
							+ " SALUTATION = '"
							+ trnRefBean.getSALUTATION() + "', "
							+ " VIPSTATUSDESC = '"
							+ trnRefBean.getVIPSTATUSDESC() + "', "
							/*+ " EOFFICEREFNO = '"
							+ trnMarkingBean.getEOFFICEREFNO() + "', "
								+ " EOFFICERECEIPTNO = '"
							+ trnMarkingBean.getEOFFICERECEIPTNO() + "', "
							*///Not needed update only insert //code by Rounak 18-07-17
							+ " LETTERDATE = TO_DATE('"
							+ trnRefBean.getLETTERDATE() + "','DD/MM/YYYY'), "
							+ " ISBUDGET = '" + trnRefBean.getISBUDGET()
							+ "', " + " VIPSTATUS = '"
							+ trnRefBean.getVIPSTATUS() + "', "
							+ " STATECODE = '" + trnRefBean.getSTATECODE()
							+ "',VIPPARTY = '" + trnRefBean.getVIPPARTY()
							+ "',ADDVIPID = '" + trnRefBean.getADDVIPID()
							+ "',ADDVIPTYPE = '" + trnRefBean.getADDVIPTYPE()+"', ACKDATE = TO_DATE('"
							+ trnRefBean.getACKDATE() + "','DD/MM/YYYY'), "
							+ " ACKBY = '" + trnRefBean.getACKBY() + "', "
							+ " REFCATEGORY = '" + trnRefBean.getREFCATEGORY()
							+ "', " + " LANGUAGE = '"
							+ trnRefBean.getLANGUAGE() + "', " + " URGENCY = '"
							+ trnRefBean.getURGENCY() + "', "
							+ " LINKREFID = '" + trnRefBean.getLINKREFID()
							+ "', " + " NOTECREATOR = '"
							+ trnRefBean.getNOTECREATOR() + "', "
							+ " SECURITYGRADING = '"
							+ trnRefBean.getSECURITYGRADING() + "', "
							+ " SIGNEDBY = '" + trnRefBean.getSIGNEDBY()
							+ "', " + " SIGNEDON = TO_DATE('"
							+ trnRefBean.getSIGNEDON() + "','DD/MM/YYYY'), "
							+ " REMARKS = '" + trnRefBean.getREMARKS() + "', "
							+ " TOURREMARKS = '" + trnRefBean.getTOURREMARKS() + "', "
							
							
							
							+ " COMPLIANCE = '" + trnRefBean.getCOMPLIANCE() + "', "
							+ " COMPLIANCEREMARKS = '" + trnRefBean.getCOMPLIANCEREMARKS() + "', "
							+ " COMPLIANCEDATE = TO_DATE('"
							+ trnRefBean.getCOMPLIANCEDATE() + "','DD/MM/YYYY'), "
							
							
							+ " ODSPLACE = '" + trnRefBean.getODSPLACE()+ "', " 
							+ " ODSRAILWAY = '"	+ trnRefBean.getODSRAILWAY() + "', "
							+ " ODSDATE = TO_DATE('" + trnRefBean.getODSDATE()	+ "','DD/MM/YYYY'), " 
						/*	+ " LOGINID = '"	+ trnRefBean.getLOGINID() + "', " */
							+ " CHANGEDATE = SYSDATE, " 
							+ " SUBJECTCODE = '" + trnMarkingBean.getSUBJECTCODE() + "', "
							+ " SUBJECT = '" + trnMarkingBean.getSUBJECT() + "', "
							+ " RECIEVEDBY = '" + trnRefBean.getRECIEVEDBY() + "', "
							+ " TAG1 = '" + trnRefBean.getTAG1() + "', "
							+ " TAG2 = '" + trnRefBean.getTAG2() + "', "
							+ " TAG3 = '" + trnRefBean.getTAG3() + "', "
							+ " TAG4 = '" + trnRefBean.getTAG4() + "', "
							+ " REGISTRATIONNO = '" + trnRefBean.getREGISTRATIONNO() + "', "
							+ " FILENO = '" + trnRefBean.getFILENO() + "', "
							+ " EOFFICENO = '" + trnRefBean.getEOFFICEFILENO() + "', "
							+ " IMARKINGTO = '" + trnRefBean.getIMARKINGTO() + "', "
							+ " FILESTATUS1 = '" + trnRefBean.getFILESTATUS1() + "', "
							+ " FILESTATUS2 = '" + trnRefBean.getFILESTATUS2() + "', "
							+ " REPLYTYPE = '" + trnRefBean.getREPLYTYPE() + "', "
							+ " REASON = '" + trnRefBean.getREASON() + "', "
							+ " DMARKINGTO = '" + trnRefBean.getDMARKINGTO() + "', "
							+ " DMARKINGDATE = to_date('" + trnRefBean.getDMARKINGDATE() + "','dd/mm/yyyy'), "
							+ " REGISTRATIONDATE = to_date('" + trnRefBean.getREGISTRATIONDATE() + "','dd/mm/yyyy'), "
							+ " KEYWORD1 = '" + k1 + "', "
							+ " KEYWORD2 = '" + k2 + "', "
							+ " KEYWORD3 = '" + trnRefBean.getKeywords3() + "', "
							+ " K1 = '" + k1 + "', "
							+ " K2 = '" + k2 + "', "
							+ " K3 = '" + trnMarkingBean.getKeywords3() + "' "							
							+ " WHERE REFID =  '"	+ trnRefBean.getREFID() + "'";
					log.debug(QueryUpdate);
					dbcon.update(QueryUpdate);

					// update the marking table
					String QueryUpdateMarking = " UPDATE TRNMARKING SET "
							+ " MARKINGFROM = '"
							+ trnMarkingBean.getMARKINGFROM()
							+ "', "
							+ " MARKINGTO = '"
							+ trnMarkingBean.getMARKINGTO()
							+ "', "
							+ " MARKINGDATE = TO_DATE('"
							+ trnMarkingBean.getMARKINGDATE()
							+ "','DD/MM/YYYY'), "
							+ " MARKINGREMARK = '"
							+ trnMarkingBean.getMARKINGREMARK()
							+ "', "
							+ " TARGETDAYS = '"
							+ trnMarkingBean.getTARGETDAYS()
							+ "', "
							+ " TARGETDATE = TO_DATE('"
							+ trnMarkingBean.getTARGETDATE()
							+ "','DD/MM/YYYY') "
							// + " SUBJECTCODE = '"+
							// trnMarkingBean.getSUBJECTCODE() + "', " +
							// " SUBJECT = '" + trnMarkingBean.getSUBJECT() +
							// "' "
							+ " WHERE REFID =  '"
							+ trnRefBean.getREFID()
							+ "' "
							+ " AND MARKINGSEQUENCE = '"
							+ trnMarkingBean.getMARKINGSEQUENCE() + "'";
					log.debug(QueryUpdateMarking);
					dbcon.update(QueryUpdateMarking);
					
					refNoAndMarking += "/-"+ trnRefBean.getREFID();
				}
				
		if(trnRefBean.getTXT_NOTE().length() > 0 && trnRefBean.getSIGNEDBY_YS().length() > 0) {		
				String strSQLSelect = "SELECT COUNT(REFID) FROM TRNYELLOWSLIP WHERE REFID = '"+trnRefBean.getREFID()+"'";
				log.debug(strSQLSelect);
				ResultSet rs = dbcon.select(strSQLSelect);
				int count = 0;
				if(rs.next()){
					count = rs.getInt(1);
				}
				if(count > 0){
					// UPDATE into YELLOWSLIP
					String strSqlYS = " UPDATE TRNYELLOWSLIP SET SLIPTXT = '"+trnRefBean.getTXT_NOTE()+"'," +
									  " SIGNEDBY = '"+trnRefBean.getSIGNEDBY_YS()+"'," +
									  " SIGNEDON = SYSDATE," +
									  " MARKTO = '"+trnMarkingBean.getMARKINGTO()+"'" +
									  " WHERE REFID = '"+trnRefBean.getREFID()+"'";
					log.debug(strSqlYS);
					dbcon.update(strSqlYS);
				}else {
					// insert into YELLOWSLIP
					String strSqlYS = " INSERT INTO TRNYELLOWSLIP (REFID, ROLEID, SLIPTXT, SIGNEDBY, SIGNEDON, MARKTO,ISOPENED)" +
									  " VALUES('"+trnRefBean.getREFID()+"'," +
									  " '"+trnRefBean.getROLEID()+"',"	+
									  " '"+trnRefBean.getTXT_NOTE()+"'," +
									  " '"+trnRefBean.getSIGNEDBY_YS()+"'," +
									  " SYSDATE," +
									  " '"+trnMarkingBean.getMARKINGTO()+"',"	+
									  " 'N')";
					log.debug(strSqlYS);
					dbcon.insert(strSqlYS);
				}
		}
			}
			outMessage = "GRNSave operation successful." + refNoAndMarking;
			//System.out.println(outMessage);
		} catch (SQLException sql) {
			dbcon.rollback();
			log.fatal(sql, sql);
			outMessage = "REDSave operation failure for . "
					+ trnRefBean.getREFNO() + ". ERROR--> " + sql.getMessage();
			// (new CommonDAO()).generateMessage("TESTLOGIN",outMessage);
		} finally {
			dbcon.closeConnection();
		}

		return outMessage;
	}
	public String saveReferenceOutWard(TrnReference trnRefBean) {
		String outMessage = "";
		
		DBConnection dbcon = new DBConnection();
		try {
			dbcon.openConnection();
			
			// variable for holding generated ref no and marking
			String refNoAndMarking = "";
			
				// update OR insert for each row of the table
				if (trnRefBean.getREFID().trim().length() <= 0) {
					trnRefBean.setREFID(CommonDAO.getNextId(dbcon, "REFID",
					"TRNREFERENCE"));
					// trnRefBean.setREFCOUNT(getNextRefCount(dbcon, "REFCOUNT",
					// "TRNREFERENCE", trnRefBean.getTENUREID(),
					// trnRefBean.getREFCLASS()));
					trnRefBean.setREFCOUNT(MstClass.getRefCountSequence(
							trnRefBean.getROLEID(), trnRefBean.getTENUREID(),
							trnRefBean.getREFCLASS(), Calendar.getInstance()
							.get(Calendar.YEAR), dbcon));
					log.info("Generated Refcount is  ************* "
							+ trnRefBean.getREFCOUNT());
					
					// trnRefBean.setREFNO(trnRefBean.getREFNO()+"/"+
					// trnRefBean.getREFCOUNT() + "/"+
					// Calendar.getInstance().get(Calendar.YEAR));
					trnRefBean.setREFNO(trnRefBean.getLOGINASROLENAME() + "/"
							+ trnRefBean.getREFCLASS() + "/"
							+ trnRefBean.getREFCOUNT() + "/"
							+ Calendar.getInstance().get(Calendar.YEAR));
					
					// code for linking link ref id
						trnRefBean.setLINKREFID(trnRefBean.getREFID());
					
					// Insert into Header Table
						
						String k1=trnRefBean.getKeywords2()==null || trnRefBean.getKeywords2().length()==0 || trnRefBean.getKeywords1().compareTo(trnRefBean.getKeywords2())<0?trnRefBean.getKeywords1():trnRefBean.getKeywords2();
						String k2=trnRefBean.getKeywords2()==null || trnRefBean.getKeywords2().length()==0 || trnRefBean.getKeywords1().compareTo(trnRefBean.getKeywords2())<0?trnRefBean.getKeywords2():trnRefBean.getKeywords1();
						
						
					String refQuery = " INSERT INTO TRNREFERENCE ( REFID, TENUREID,  REFNO, ROLEID, REFCLASS, REFCOUNT, INCOMINGDATE, REFERENCENAME,ADDRESSENGLISH,SALUTATION,VIPSTATUSDESC,"
						+ " LETTERDATE, ISBUDGET, VIPSTATUS, STATECODE, ACKDATE, ACKBY, REFCATEGORY, LANGUAGE, URGENCY, LINKREFID, NOTECREATOR, SECURITYGRADING,"
						+ " SIGNEDBY, SIGNEDON, REMARKS,TOURREMARKS,COMPLIANCE,COMPLIANCEREMARKS,COMPLIANCEDATE,ODSPLACE, ODSRAILWAY, ODSDATE, LOGINID, CHANGEDATE, REFERENCEDATE, FMID, "
						+ " FILECOUNTER, REGISTRATIONNO, FILENO, IMARKINGTO, FILESTATUS1, FILESTATUS2, REPLYTYPE, REASON, EOFFICEREFNO,"
						+ " DMARKINGTO, DMARKINGDATE, REGISTRATIONDATE, SUBJECTCODE, SUBJECT, STATUSREMARK, RECIEVEDBY, TAG1, TAG2, TAG3, TAG4,ISCONF," +
								"KEYWORD1,KEYWORD2,KEYWORD3,K1,K2,K3)"
						+ " VALUES (" + " '"
						+ trnRefBean.getREFID()
						+ "', "
						+ " '"
						+ trnRefBean.getTENUREID()
						+ "', "
						+ " '"
						+ trnRefBean.getREFNO()
						+ "', "
						+ " '"
						+ trnRefBean.getROLEID()
						+ "', "
						+ " '"
						+ trnRefBean.getREFCLASS()
						+ "', "
						+ " '"
						+ trnRefBean.getREFCOUNT()
						+ "', "
						+ " TO_DATE('"
						+ trnRefBean.getINCOMINGDATE()
						+ "', 'DD/MM/YYYY'), "
						+ " '"
						+ trnRefBean.getREFERENCENAME()
						+ "', "
						+ " '"
						+ trnRefBean.getADDRESSENGLISH()
						+ "', "
						+ " '"
						+ trnRefBean.getSALUTATION()
						+ "', "
							+ " '"
						+ trnRefBean.getVIPSTATUSDESC()
						+ "', "
						
						+ " TO_DATE('"
						+ trnRefBean.getLETTERDATE()
						+ "', 'DD/MM/YYYY'), "
						+ " '"
						+ trnRefBean.getISBUDGET()
						+ "', "
						+ " '"
						+ trnRefBean.getVIPSTATUS()
						+ "', "
						+ " '"
						+ trnRefBean.getSTATECODE()
						+ "', "
						+ " TO_DATE('"
						+ trnRefBean.getACKDATE()
						+ "', 'DD/MM/YYYY'), "
						+ " '"
						+ trnRefBean.getACKBY()
						+ "', "
						+ " '"
						+ trnRefBean.getREFCATEGORY()
						+ "', "
						+ " '"
						+ trnRefBean.getLANGUAGE()
						+ "', "
						+ " '"
						+ trnRefBean.getURGENCY()
						+ "', "
						+ " '"
						+ trnRefBean.getLINKREFID()
						+ "', "
						+ " '"
						+ trnRefBean.getNOTECREATOR()
						+ "', "
						+ " '"
						+ trnRefBean.getSECURITYGRADING()
						+ "', "
						+ " '"
						+ trnRefBean.getSIGNEDBY()
						+ "', "
						+ " TO_DATE('"
						+ trnRefBean.getSIGNEDON()
						+ "', 'DD/MM/YYYY'), "
						+ " '"
						+ trnRefBean.getREMARKS()
						+ "', "
						+ " '"
						+ trnRefBean.getTOURREMARKS()
						+ "', "
						+ " '"
						+ trnRefBean.getCOMPLIANCE()
						+ "', "
							+ " '"
						+ trnRefBean.getCOMPLIANCEREMARKS()
						+ "', "
							+ " '"
						+ trnRefBean.getCOMPLIANCEDATE()
						+ "', "
						
						+ " '"
						+ trnRefBean.getODSPLACE()
						+ "', "
						+ " '"
						+ trnRefBean.getODSRAILWAY()
						+ "', "
						+ " TO_DATE('"
						+ trnRefBean.getODSDATE()
						+ "', 'DD/MM/YYYY'), "
						+ " '"
						+ trnRefBean.getLOGINID()
						+ "', "
						+ " SYSDATE, "
						+ " SYSDATE, "
						+ " '"
						+ trnRefBean.getFMID()
						+ "', "
						+ " '"
						+ trnRefBean.getFILECOUNTER()
						+ "', "
						+ " '"
						+ trnRefBean.getREGISTRATIONNO()
						+ "', "
						+ " '"
						+ trnRefBean.getFILENO()
						+ "', "
						+ " '"
						+ trnRefBean.getIMARKINGTO()
						+ "', "
						+ " '"
						+ trnRefBean.getFILESTATUS1()
						+ "', "
						+ " '"
						+ trnRefBean.getFILESTATUS2()
						+ "', "
						+ " '"
						+ trnRefBean.getREPLYTYPE()
						+ "', "
						+ " '"
						+ trnRefBean.getREASON()
						+ "', "
						+ " '"
						+ trnRefBean.getEOFFICEREFNO()
						+ "', "
						+ " '"
						+ trnRefBean.getDMARKINGTO()
						+ "', "
						+ " TO_DATE('"
						+ trnRefBean.getDMARKINGDATE()
						+ "', 'DD/MM/YYYY'), "
						+ " TO_DATE('"
						+ trnRefBean.getREGISTRATIONDATE()
						+ "', 'DD/MM/YYYY'), "
						+ " '"+ trnRefBean.getSUBJECTCODE() + "', "
						+ " '"
						+ trnRefBean.getSUBJECT()+ "', "
						+ " '" + trnRefBean.getSTATUSREMARK() + "', " 
						+ " '" + trnRefBean.getRECIEVEDBY() + "', " 
						+ " '" + trnRefBean.getTAG1() + "', " 
						+ " '" + trnRefBean.getTAG2() + "', " 
						+ " '" + trnRefBean.getTAG3() + "', " 
						+ " '" + trnRefBean.getTAG4() + "', " 
						+ " '" + trnRefBean.getISCONF() + "', " 
						+ "" 
						+ " '" + k1 + "', "
						+ " '" + k2 + "', "
						+ " '" + trnRefBean.getKeywords3() + "', "
						+ " '" + k1 + "', "
						+ " '" + k2 + "', "
						+ " '" + trnRefBean.getKeywords3() + "' "
							+	")";
					
					log.debug(refQuery);
					dbcon.insert(refQuery);
					
					String markingName = "";
					
					// set the variable to flash output message
					refNoAndMarking += "<BR>"
						+ StringFormat.leftPad(1 + "", 2, ' ')
						+ ".  &nbsp; &nbsp; " + trnRefBean.getREFNO()
						+ " - " + markingName+ ".  &nbsp; &nbsp; /-"+trnRefBean.getREFID() + "/-";
					
					// reset refid to blank so that new refid is generated for
					// next
					// iteration of the detail tables for markign
					trnRefBean.setREFID("");
					trnRefBean.setREFCOUNT("");
					trnRefBean.setREFNO("");
					
				} else { // if refid is not null then update query
					
					// update the header table
					
					String k1=trnRefBean.getKeywords2()==null || trnRefBean.getKeywords2().length()==0 || trnRefBean.getKeywords1().compareTo(trnRefBean.getKeywords2())<0?trnRefBean.getKeywords1():trnRefBean.getKeywords2();
					String k2=trnRefBean.getKeywords2()==null || trnRefBean.getKeywords2().length()==0 || trnRefBean.getKeywords1().compareTo(trnRefBean.getKeywords2())<0?trnRefBean.getKeywords2():trnRefBean.getKeywords1();
					
					String QueryUpdate = " UPDATE TRNREFERENCE SET " +
					// " REFNO = '"+trnRefBean.getREFNO()+"', " +
					// " ROLEID = '"+trnRefBean.getROLEID()+"', " +
					// " REFCLASS = '"+trnRefBean.getREFCLASS()+"', " +
					// " REFCOUNT = '"+trnRefBean.getREFCOUNT()+"', " +
					" INCOMINGDATE = TO_DATE('"
					+ trnRefBean.getINCOMINGDATE()
					+ "','DD/MM/YYYY'), " + " REFERENCENAME = '"
					+ trnRefBean.getREFERENCENAME() + "', "
					
					+ " ADDRESSENGLISH = '"
							+ trnRefBean.getADDRESSENGLISH() + "', "
							+ " SALUTATION = '"
							+ trnRefBean.getSALUTATION() + "', "
							+ " VIPSTATUSDESC = '"
							+ trnRefBean.getVIPSTATUSDESC() + "', "
							+ " EOFFICEREFNO = '"
							+ trnRefBean.getEOFFICEREFNO() + "', "
							
					+ " LETTERDATE = TO_DATE('"
					+ trnRefBean.getLETTERDATE() + "','DD/MM/YYYY'), "
					+ " ISBUDGET = '" + trnRefBean.getISBUDGET()
					+ "', " + " VIPSTATUS = '"
					+ trnRefBean.getVIPSTATUS() + "', "
					+ " STATECODE = '" + trnRefBean.getSTATECODE()
					+ "', " + " ACKDATE = TO_DATE('"
					+ trnRefBean.getACKDATE() + "','DD/MM/YYYY'), "
					+ " ACKBY = '" + trnRefBean.getACKBY() + "', "
					+ " REFCATEGORY = '" + trnRefBean.getREFCATEGORY()
					+ "', " + " LANGUAGE = '"
					+ trnRefBean.getLANGUAGE() + "', " + " URGENCY = '"
					+ trnRefBean.getURGENCY() + "', "
					+ " LINKREFID = '" + trnRefBean.getLINKREFID()
					+ "', " + " NOTECREATOR = '"
					+ trnRefBean.getNOTECREATOR() + "', "
					+ " SECURITYGRADING = '"
					+ trnRefBean.getSECURITYGRADING() + "', "
					+ " SIGNEDBY = '" + trnRefBean.getSIGNEDBY()
					+ "', " + " SIGNEDON = TO_DATE('"
					+ trnRefBean.getSIGNEDON() + "','DD/MM/YYYY'), "
					+ " REMARKS = '" + trnRefBean.getREMARKS() + "', "
					+ " TOURREMARKS = '" + trnRefBean.getTOURREMARKS() + "', "
					
					+ " COMPLIANCE = '" + trnRefBean.getCOMPLIANCE() + "', "
							+ " COMPLIANCEREMARKS = '" + trnRefBean.getCOMPLIANCEREMARKS() + "', "
							+ " COMPLIANCEDATE = '" + trnRefBean.getCOMPLIANCEDATE() + "', "
					
					+ " ODSPLACE = '" + trnRefBean.getODSPLACE()
					+ "', " + " ODSRAILWAY = '"
					+ trnRefBean.getODSRAILWAY() + "', "
					+ " ODSDATE = TO_DATE('" + trnRefBean.getODSDATE()
					+ "','DD/MM/YYYY'), " 
					/*
					+ " LOGINID = '"
					+ trnRefBean.getLOGINID() + "', "
					*/
					+ " CHANGEDATE = SYSDATE, " + " SUBJECTCODE = '"+ trnRefBean.getSUBJECTCODE() + "', "
					+ " SUBJECT = '" + trnRefBean.getSUBJECT()+ "', " 
					+ " RECIEVEDBY = '" + trnRefBean.getRECIEVEDBY() + "', "
					+ " TAG1 = '" + trnRefBean.getTAG1() + "', "
					+ " TAG2 = '" + trnRefBean.getTAG2() + "', "
					+ " TAG3 = '" + trnRefBean.getTAG3() + "', "
					+ " TAG4 = '" + trnRefBean.getTAG4() + "', "
					+ " KEYWORD1 = '" + k1 + "', "
					+ " KEYWORD2 = '" + k2 + "', "
					+ " KEYWORD3 = '" + trnRefBean.getKeywords3() + "', "
					+ " K1 = '" + k1 + "', "
					+ " K2 = '" + k2 + "', "
					+ " K3 = '" + trnRefBean.getKeywords3() + "' "	
					+ " WHERE REFID =  '"
					+ trnRefBean.getREFID() + "'";
					
					log.debug(QueryUpdate);
					dbcon.update(QueryUpdate);
					
					refNoAndMarking += "/-"+ trnRefBean.getREFID() + "/-";
				}
			
			outMessage = "GRNSave operation successful." + refNoAndMarking;
			
		} catch (SQLException sql) {
			dbcon.rollback();
			log.fatal(sql, sql);
			outMessage = "REDSave operation failure for . "
				+ trnRefBean.getREFNO() + ". ERROR--> " + sql.getMessage();
			// (new CommonDAO()).generateMessage("TESTLOGIN",outMessage);
		} finally {
			dbcon.closeConnection();
		}
		
		return outMessage;
	}

	public String saveBudget(ArrayList<TrnBudget> trnBudgetBeanList) {
		String outMessage = "";

		DBConnection dbcon = new DBConnection();
		try {
			dbcon.openConnection();

			// inserting into marking detail table

			// variable for holding generated ref no and marking
			String refNoAndMarking = "";

			for (int i = 0; i < trnBudgetBeanList.size(); i++) {
				TrnBudget trnBudgetBean = trnBudgetBeanList.get(i);

				// delete the previous recode from budget table
				if (i == 0) {
					// delete query
					String budgetDeleteQRY = "DELETE FROM TRNBUDGET WHERE REFID = '"
							+ trnBudgetBean.getREFID() + "'";
					log.debug(budgetDeleteQRY);
					dbcon.delete(budgetDeleteQRY);
				}

				// log.info("budget seq : "+ trnBudgetBean.getBUDGETSEQUENCE());
				// update OR insert for each row of the table
				if (trnBudgetBean.getBUDGETSEQUENCE().trim().length() <= 0) {

					trnBudgetBean.setBUDGETSEQUENCE(getNextBudgetSequence(
							dbcon, "BUDGETSEQUENCE", "TRNBUDGET", trnBudgetBean
									.getREFID()));
					log.info("Generated Budget Sequence is  ************* "
							+ trnBudgetBean.getBUDGETSEQUENCE());
				}
				String budgetQuery = " INSERT INTO TRNBUDGET (REFID,BUDGETSEQUENCE,SUBJECTCODE,SUBJECT,MRREMARK,REMARK,LOGINID,CHANGEDATE) "
						+ " VALUES (" + " '"
						+ trnBudgetBean.getREFID()
						+ "',"
						+ " '"
						+ trnBudgetBean.getBUDGETSEQUENCE()
						+ "',"
						+ " '"+ trnBudgetBean.getSUBJECTCODE() + "',"
						+ " '"
						+ trnBudgetBean.getSUBJECT()
						+ "',"
						+ " '"
						+ trnBudgetBean.getMRREMARK()
						+ "',"
						+ " '"
						+ trnBudgetBean.getREMARK()
						+ "',"
						+ " '"
						+ trnBudgetBean.getLOGINID() + "'," + " SYSDATE" + ")";

				log.debug(budgetQuery);
				dbcon.insert(budgetQuery);
			}

			outMessage = "GRNSave operation successful." + refNoAndMarking;
		} catch (SQLException sql) {
			dbcon.rollback();
			log.fatal(sql, sql);
			// outMessage = "REDSave operation failure for . " +
			// trnBudgetBean.getREFNO() + ". ERROR--> " + sql.getMessage();
			// (new CommonDAO()).generateMessage("TESTLOGIN",outMessage);
		} finally {
			dbcon.closeConnection();
		}

		return outMessage;
	}

	public String getNextRefCount(DBConnection con, String colName,
			String tableName, String tenureId, String refClass) {
		String strSQL = "(SELECT NVL(MAX(A." + colName + "), 0)+ 1 FROM "
				+ tableName + " A WHERE A.TENUREID = '" + tenureId
				+ "' AND A.REFCLASS = '" + refClass + "')";
		log.debug(strSQL);
		return getStringParam(strSQL, con);
	}

	public String getNextBudgetSequence(DBConnection con, String colName,
			String tableName, String refID) {
		String strSQL = "(SELECT NVL(MAX(A." + colName + "), 0)+ 1 FROM "
				+ tableName + " A WHERE A.REFID = '" + refID + "')";
		log.debug(strSQL);
		return getStringParam(strSQL, con);
	}

	public String getNextMarkingSequence(DBConnection con, String colName,
			String tableName, String refId) {
		String strSQL = "(SELECT NVL(MAX(A." + colName + "), 0)+ 1 FROM "
				+ tableName + " A WHERE REFID = '" + refId + "')";
		log.debug(strSQL);
		return getStringParam(strSQL, con);
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

	/*
	 * public ArrayList<CommonBean> getTreeview(String refId) { String strSQL =
	 * ""; try {
	 * 
	 * d.add(31047,-1,'<span class=\"treespan\" name=\"treespan\"
	 * style=\"color:'+col[2]+';\">SSE/PW/MTJ(S)</span>','2~031047');
	 * d.add(31103,31047,'<span class=\"treespan\" name=\"treespan\"
	 * style=\"color:'+col[1]+';\">JE/PW/BAAD</span>','1~031103');
	 * d.add(31104,31047,'<span class=\"treespan\" name=\"treespan\"
	 * style=\"color:'+col[1]+';\">JE/PW/BFP</span>','1~031104');
	 * d.add(31112,31047,'<span class=\"treespan\" name=\"treespan\"
	 * style=\"color:'+col[1]+';\">JE/PW/MTJ YD</span>','1~031112');
	 * d.add(31232,31047,'<span class=\"treespan\" name=\"treespan\"
	 * style=\"color:'+col[1]+';\">JE/PW/BAD-YARD</span>','1~031232');
	 * 
	 * strSQL =
	 * " SELECT 'd.add('||(ROWNUM )||','||-1||','||''''||'<span class=\"treespan\" name=\"treespan\" style=\"color:''+col['||2||']+'';\">'||(SELECT B.ROLENAME FROM MSTROLE B WHERE B.ROLEID = (SELECT C.ROLEID FROM MSTTENURE C WHERE C.TENUREID = A.MARKINGFROM))||' ( On - '||TO_CHAR(MARKINGDATE,'DD/MM/YYYY HH24:MI:SS')||')'||'</span>' ||''''||','||''''||MARKINGSEQUENCE||''''||');' TREEVIEW, MARKINGSEQUENCE FROM TRNMARKING A WHERE A.REFID = '"
	 * +refId+"' AND A.MARKINGSEQUENCE ='1' " + " UNION " +
	 * " SELECT 'd.add('||(ROWNUM+1)||','||(ROWNUM)||','||''''||'<span class=\"treespan\" name=\"treespan\" style=\"color:''+col['||2||']+'';\">'||(SELECT B.ROLENAME FROM MSTROLE B WHERE B.ROLEID = (SELECT C.ROLEID FROM MSTTENURE C WHERE C.TENUREID = A.MARKINGTO))||' ( On - '||TO_CHAR(MARKINGDATE,'DD/MM/YYYY HH24:MI:SS')||')'||'</span>' ||''''||','||''''||MARKINGSEQUENCE||''''||');' TREEVIEW , MARKINGSEQUENCE FROM TRNMARKING A WHERE A.REFID = '"
	 * +refId+"'  AND ACTION='FOW' " + " ORDER BY MARKINGSEQUENCE " ;
	 * 
	 * log.debug("TREE  - " + strSQL); } catch (Exception e) { log.fatal(e, e);
	 * } finally { } return (new CommonDAO()).getSQLResult(strSQL, 1); }
	 */

	public ArrayList<CommonBean> getTreeview(String refId) {
		String strSQL = "";
		try {

			/*
			 * strSQL =
			 * " SELECT 'd.add('||MARKINGSEQUENCE||','||(-1)||','||''''||'<span class=\"treespan\" name=\"treespan\" style=\"color:''+col['||2||']+'';\">'||(SELECT B.ROLENAME FROM MSTROLE B WHERE B.ROLEID = (SELECT C.ROLEID FROM MSTTENURE C WHERE C.TENUREID = A.MARKINGFROM))||'   - Forwared on  '||TO_CHAR(MARKINGDATE,'DD/MM/YYYY HH24:MI:SS')||')'||'</span>' ||''''||','||''''||MARKINGSEQUENCE||''''||');' TREEVIEW, A.MARKINGSEQUENCE-1, markingfrom, markingto , ACTION FROM TRNMARKING A WHERE REFID='"
			 * +refId+"' AND MARKINGSEQUENCE=1" + " UNION " +
			 * " SELECT 'd.add('||(rownum+1)||','||(case when action='RCD' then (rownum-1) else rownum end )||','||''''||'<span class=\"treespan\" name=\"treespan\" style=\"color:''+col['||2||']+'';\">'"
			 * +
			 * " || (SELECT B.ROLENAME FROM MSTROLE B WHERE B.ROLEID = (SELECT C.ROLEID FROM MSTTENURE C WHERE C.TENUREID = A.MARKINGTO))"
			 * +
			 * " || '   - '|| (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '4' AND CODE = DECODE(ACTION, 'FOW', 'RCD', 'RCD', 'FOW')) ||' on '||TO_CHAR(MARKINGDATE,'DD/MM/YYYY HH24:MI:SS')||''||'</span>' ||''''||','||''''||MARKINGSEQUENCE||''''||');' TREEVIEW,A.MARKINGSEQUENCE,"
			 * +
			 * " markingfrom,markingto, ACTION FROM TRNMARKING A WHERE REFID='"
			 * +refId+"' ORDER BY 2";
			 */
			strSQL = " SELECT 'd.add('||DECODE(X.CHILD, NULL, (Y.CHILD-1), X.CHILD)||','"
					+ " ||DECODE(X.PARENT, NULL, (Y.PARENT-1), X.PARENT)||','||''''"
					+ " ||'<span class=\"treespan\" name=\"treespan\" style=\"color:''+col['||2||']+'';\">'"
					+ " ||DECODE(X.MARKINGTO, NULL, (Y.MARKINGFROM), X.MARKINGTO) || '   - '"
					+ " ||NVL(Y.ACTION, 'Pending') ||' on '"
					+ " ||TO_CHAR(DECODE(Y.ACTIONDATE, NULL, (X.ACTIONDATE), Y.ACTIONDATE),'DD/MM/YYYY HH24:MI:SS') "
					+ " ||''||'</span>' ||''''||','||''''||rownum||''''||');' TREEVIEW "
					+ " FROM "
					+ " 	(SELECT MARKINGSEQUENCE CHILD, (MARKINGSEQUENCE - 1) PARENT,  (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = MARKINGFROM) MARKINGFROM,"
					+ " 	(SELECT ROLENAME FROM MSTROLE WHERE ROLEID = MARKINGTO) MARKINGTO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = ACTION) ACTION , ACTIONDATE"
					+ "	FROM TRNMARKING A WHERE REFID='"
					+ refId
					+ "' ) X FULL JOIN"
					+ "	(SELECT MARKINGSEQUENCE CHILD, (MARKINGSEQUENCE - 1) PARENT, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = MARKINGFROM) MARKINGFROM,"
					+ "	(SELECT ROLENAME FROM MSTROLE WHERE ROLEID = MARKINGTO) MARKINGTO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = ACTION) ACTION , ACTIONDATE"
					+ "	FROM TRNMARKING A WHERE REFID='"
					+ refId
					+ "' )  Y ON X.CHILD = Y.PARENT";

			log.debug("MarkingTree ** :- " + strSQL);
		} catch (Exception e) {
			log.fatal(e, e);
		} finally {
		}
		return (new CommonDAO()).getSQLResult(strSQL, 1);
	}

	public ArrayList<CommonBean> getTreeviewReminder(String refId) {
		String strSQL = "";
		try {

			/*
			 * strSQL =
			 * " SELECT 'd1.add('||MARKINGSEQUENCE||','||(-1)||','||''''||'<span class=\"treespan\" name=\"treespan\" style=\"color:''+col['||2||']+'';\">'||(SELECT B.ROLENAME FROM MSTROLE B WHERE B.ROLEID = (SELECT C.ROLEID FROM MSTTENURE C WHERE C.TENUREID = A.MARKINGFROM))||'   - Forwared on  '||TO_CHAR(MARKINGDATE,'DD/MM/YYYY HH24:MI:SS')||')'||'</span>' ||''''||','||''''||MARKINGSEQUENCE||''''||');' TREEVIEW, A.MARKINGSEQUENCE-1, markingfrom, markingto , ACTION FROM TRNMARKING A WHERE REFID='"
			 * +refId+"' AND MARKINGSEQUENCE=1" + " UNION " +
			 * " SELECT 'd1.add('||(rownum+1)||','||(case when action='RCD' then (rownum-1) else rownum end )||','||''''||'<span class=\"treespan\" name=\"treespan\" style=\"color:''+col['||2||']+'';\">'"
			 * +
			 * " || (SELECT B.ROLENAME FROM MSTROLE B WHERE B.ROLEID = (SELECT C.ROLEID FROM MSTTENURE C WHERE C.TENUREID = A.MARKINGTO))"
			 * +
			 * " || '   - '|| (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '4' AND CODE = DECODE(ACTION, 'FOW', 'RCD', 'RCD', 'FOW')) ||' on '||TO_CHAR(MARKINGDATE,'DD/MM/YYYY HH24:MI:SS')||''||'</span>' ||''''||','||''''||MARKINGSEQUENCE||''''||');' TREEVIEW,A.MARKINGSEQUENCE,"
			 * +
			 * " markingfrom,markingto, ACTION FROM TRNMARKING A WHERE REFID='"
			 * +refId+"' ORDER BY 2";
			 */
			strSQL = " SELECT 'd1.add('||DECODE(X.CHILD, NULL, (Y.CHILD-1), X.CHILD)||',' ||DECODE(X.PARENT, NULL, (Y.PARENT-1), X.PARENT)||"
					+ "','||'''' ||'<span class=\"treespan\" name=\"treespan\" style=\"color:''+col['||2||']+'';\">' || "
					+ " DECODE(X.REMINDERTO, NULL, (Y.REMINDERFROM), X.REMINDERTO) || '   - ' ||NVL(Y.REMINDERACTION, 'Pending') ||' on ' ||"
					+ " TO_CHAR(DECODE(Y.REMINDERACTIONDATE, NULL, (X.REMINDERACTIONDATE), Y.REMINDERACTIONDATE),'DD/MM/YYYY HH24:MI:SS')  ||''||'</span>' ||''''||','||''''||rownum||''''||');' TREEVIEW"
					+ " FROM "
					+ "     (SELECT REMINDERSEQUENCE CHILD, (REMINDERSEQUENCE - 1) PARENT, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = REMINDERFROM) REMINDERFROM, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = REMINDERTO) REMINDERTO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = REMINDERACTION) REMINDERACTION , REMINDERACTIONDATE    FROM TRNREMINDER A WHERE REFID='"
					+ refId
					+ "' ) X"
					+ "     FULL JOIN "
					+ "     (SELECT REMINDERSEQUENCE CHILD, (REMINDERSEQUENCE - 1) PARENT,  (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = REMINDERFROM) REMINDERFROM, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = REMINDERTO) REMINDERTO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = REMINDERACTION) REMINDERACTION , REMINDERACTIONDATE    FROM TRNREMINDER A WHERE REFID='"
					+ refId + "' )  Y" + " ON X.CHILD = Y.PARENT";

			log.debug("ReminderTree ** :- " + strSQL);
		} catch (Exception e) {
			log.fatal(e, e);
		} finally {
		}
		return (new CommonDAO()).getSQLResult(strSQL, 1);
	}

	public String saveAttachment(String refID, String name,String type) {
		
		String filepath = "";
		String strSQL = "";
		String nextimageid = "";
		DBConnection dbcon = new DBConnection();
		try {
			dbcon.openConnection();
			log.debug("saveFractureImage");
			strSQL = "SELECT (NVL(MAX(ATTACHMENTID),0)+1) FROM TRNATTACHMENT WHERE REFID ='"+refID+"' AND TYPE ='"+type+"'";
			log.debug(strSQL);
			ResultSet rs = dbcon.select(strSQL);
			if (rs.next()) {
				nextimageid = rs.getString(1);
			}
			filepath = refID +"_"+ nextimageid + name.substring(name.lastIndexOf("."));
			strSQL = " INSERT INTO TRNATTACHMENT (REFID, ATTACHMENTID, ORIGINALFILENAME, NEWFILENAME, STATUSFLAG,TYPE)"
					+ " VALUES ('"+refID+"', '"+nextimageid+"', '"+name+"', '"+filepath+"', '1','"+type+"')";
			log.debug(strSQL);
			dbcon.insert(strSQL);
		} catch (Exception e) {
			dbcon.rollback();
			e.printStackTrace();
		} finally {
			dbcon.commit();
			dbcon.closeConnection();
		}
		return filepath;
	}

	public String deleteRefImage(String refID, String attachmentID,String type) {
		String isDataSaved = "0~Can not delete.";
		DBConnection con = new DBConnection();
		try {
			con.openConnection();
			String strSQL = "";
//			strSQL = " DELETE FROM TRNATTACHMENT WHERE REFID = '"+refID+"' AND ATTACHMENTID = '"+attachmentID+ "'";
			strSQL = " UPDATE TRNATTACHMENT SET STATUSFLAG = '0' WHERE REFID = '"+refID+"' AND ATTACHMENTID = '"+attachmentID+"' AND TYPE='"+type+"'";
			log.debug(strSQL);
			con.update(strSQL);
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

	public ArrayList<CommonBean> getReminderData(String refid) {
		String strSQL = " SELECT REFID, TO_CHAR(REMINDERDATE,'DD/MM/YYYY') REMINDERDATE, REMINDERREMARK, REMINDERACTION, "
				+ " TO_CHAR(REMINDERACTIONDATE,'DD/MM/YYYY') REMINDERACTIONDATE, TO_CHAR(CHANGEDATE,'DD/MM/YYYY') CHANGEDATE, "
				+ " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = 5 AND CODE = REMINDERTYPE) REMINDERTYPE," +
				  " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=SIGNEDBY) SIGNEDBY, TO_CHAR(SIGNEDON,'DD/MM/YYYY') SIGNEDON, WM_CONCAT(REMINDERTO) AS SENTTO FROM ( "
				+ " SELECT REFID, REMINDERDATE, REMINDERREMARK, REMINDERACTION, REMINDERACTIONDATE, CHANGEDATE, "
				+ " REMINDERTYPE, SIGNEDBY, SIGNEDON, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = REMINDERTO) REMINDERTO "
				+ " FROM TRNREMINDER WHERE REFID='"
				+ refid
				+ "' ORDER BY REFID ) "
				+ " GROUP BY REFID, REMINDERDATE, REMINDERREMARK, REMINDERACTION, REMINDERACTIONDATE, "
				+ " CHANGEDATE, REMINDERTYPE, SIGNEDBY, SIGNEDON ";
		log.debug("Remider ** :-- " + strSQL);
		return (new CommonDAO()).getSQLResult(strSQL, 10);
	}

	public String saveReminder(String refId, String reminderFrom,
			String reminderType, String signedBy, String signedOn,
			String reminderRemark, String roleId, String loginid) {

		int updcount = 0;
		String strSQL = "";
		// String SUBJECT = "";
		// String SUBJECTCODE = "";
		String REMINDERTO = "";
		String TARGETDAYS = "";
		String TARGETDATE = "";
		DBConnection dbcon = new DBConnection();
		try {
			dbcon.openConnection();
			// strSQL =
			// " SELECT MARKINGTO, SUBJECTCODE, SUBJECT, TARGETDAYS, TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID IN "
			strSQL = " SELECT MARKINGTO, TARGETDAYS, TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID IN "
					+ " (SELECT REFID FROM TRNREFERENCE WHERE LINKREFID IN (SELECT LINKREFID FROM TRNREFERENCE WHERE REFID = '"
					+ refId
					+ "')) "
					+ " AND MARKINGFROM = '"
					+ roleId
					+ "' AND MARKINGSEQUENCE = (SELECT MIN(MARKINGSEQUENCE) FROM TRNMARKING WHERE REFID IN "
					+ " (SELECT REFID FROM TRNREFERENCE WHERE LINKREFID IN  (SELECT LINKREFID FROM TRNREFERENCE WHERE REFID = '"
					+ refId
					+ "')) "
					+ " AND MARKINGFROM = '"
					+ roleId
					+ "'AND ACTION NOT IN ('NOR', 'RET')) AND ACTION IN ('RCD', 'FOW') "
					+ " ORDER BY REFID, MARKINGSEQUENCE";
			log.debug(strSQL);
			ResultSet rs = dbcon.select(strSQL);
			while (rs.next()) {
				REMINDERTO = handleNull(rs.getString(1));
				// SUBJECT = handleNull(rs.getString(3));
				// SUBJECTCODE = handleNull(rs.getString(2));
				TARGETDAYS = handleNull(rs.getString(2));
				TARGETDATE = handleNull(rs.getString(3));

				strSQL = " INSERT INTO TRNREMINDER (REFID, REMINDERSEQUENCE, REMINDERFROM, REMINDERTO, REMINDERDATE, REMINDERREMARK, "
						// +
						// " REMINDERACTION, REMINDERACTIONDATE, LOGINID, CHANGEDATE, TARGETDAYS, TARGETDATE, SUBJECTCODE, SUBJECT, "
						+ " REMINDERACTION, REMINDERACTIONDATE, LOGINID, CHANGEDATE, TARGETDAYS, TARGETDATE, "
						+ " REMINDERTYPE, SIGNEDBY, SIGNEDON) VALUES ('"
						+ refId
						+ "', (SELECT (NVL(MAX(REMINDERSEQUENCE),0)+1) FROM "
						+ " TRNREMINDER WHERE REFID ='"
						+ refId
						+ "'), '"
						+ reminderFrom
						+ "', '"
						+ REMINDERTO
						+ "', SYSDATE, '"
						+ reminderRemark
						+ "',"
						+ " 'FOW', SYSDATE, '"
						+ loginid
						+ "',SYSDATE, '"
						+ TARGETDAYS
						+ "',TO_DATE('"
						+ TARGETDATE
						+ "','DD/MM/YYYY'), "
						// + "'"+ SUBJECTCODE+ "', "
						// + " '"+ SUBJECT + "', "
						+ "'"
						+ reminderType
						+ "', '"
						+ signedBy
						+ "', TO_DATE('" + signedOn + "','DD/MM/YYYY'))";
				log.debug(strSQL);
				Statement stmt = dbcon.getConnection().createStatement();
				updcount = stmt.executeUpdate(strSQL);
			}
			rs.close();

		} catch (Exception e) {
			log.fatal(e, e);
			dbcon.rollback();
		} finally {
			dbcon.closeConnection();
		}
		return updcount + "";
	}

	public ArrayList<CommonBean> getAttachFiles(String refid) {
		String strSQL = "SELECT REFID, ATTACHMENTID, ORIGINALFILENAME, NEWFILENAME,EOFFICEFLAG FROM TRNATTACHMENT WHERE REFID='"+refid+"' AND STATUSFLAG = '1' ORDER BY ATTACHMENTID";
	//	log.debug(strSQL);
		return (new CommonDAO()).getSQLResult(strSQL, 5);
	}
	
	public String sendMail(TrnReference trnRefBean, ServletContext context, String MAILID) {
		String outMessage = "";
		GeneratePDFDAO genPDF = new GeneratePDFDAO();
		DBConnection dbcon = new DBConnection();
		try {
			dbcon.openConnection();
			String refNoAndMarking = "";
			
//	Generate PDF & save it to local folder
			String filePath = genPDF.generatePDF(trnRefBean.getREFID(), trnRefBean.getREFERENCENAME(), context);
			
// get VIP's mail id
			String sysDateSQL = "SELECT TO_CHAR(SYSDATE,'DD/MON/YYYY') CURDATE FROM DUAL";
			log.debug(sysDateSQL);
			String sysDate = getStringParam(sysDateSQL, dbcon);
			String mailId = "";
			if( MAILID.length()>3 ){
				mailId = MAILID;
			}else {
				String mailIdQuery = " SELECT EMAILID FROM MSTVIP WHERE VIPNAME = '"+trnRefBean.getREFERENCENAME()+"'" +
									 " AND STATECODE = '"+trnRefBean.getSTATECODE()+"'" +
									 " AND VIPSTATUS = '"+trnRefBean.getVIPSTATUS()+"'";
				log.debug(mailIdQuery);
				mailId = getStringParam(mailIdQuery, dbcon);
			}
			
			String stateName = CommonDAO.getStateName(trnRefBean.getSTATECODE());

// Format letter for e-mail body
			String msgText = "<BODY BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>" +
							 "<TABLE WIDTH=\"21%\" BORDER=1 CELLPADDING=0 CELLSPACING=0><TR><TD VALIGN=\"TOP\" ALIGN=\"CENTER\">" +
							 "<TABLE WIDTH=\"20%\" BORDER=0 CELLPADDING=0 CELLSPACING=0 style=\"font-size: 14; font-family: TAHOMA\">" +
							 "<TR><TD ALIGN=\"CENTER\" COLSPAN=4><IMG SRC=\"cid:letterHeader\" WIDTH=726 HEIGHT=165></TD></TR>" +
							 "<TR><TD ALIGN=\"CENTER\" COLSPAN=4><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD ALIGN=\"CENTER\" COLSPAN=4><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD align=\"LEFT\" width=\"623\">"+trnRefBean.getREFNO()+"</TD><TD ALIGN=\"RIGHT\" > "+sysDate+"</TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD align=\"LEFT\" width=\"623\"></TD><TD ALIGN=\"RIGHT\" ></TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD align=\"LEFT\" width=\"623\"></TD><TD ALIGN=\"RIGHT\" ></TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD align=\"LEFT\" width=\"623\"></TD><TD ALIGN=\"RIGHT\" ></TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"LEFT\">Dear "+trnRefBean.getREFERENCENAME()+"</TD></TR>" +
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"LEFT\"></TD></TR>" +
							 
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"LEFT\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;I am in receipt of your letter dated "+trnRefBean.getLETTERDATE()+" regarding "+trnRefBean.getSUBJECT()+"</TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"LEFT\"></TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"LEFT\"></TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"LEFT\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;I am having the matter looked into.</TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"LEFT\"></TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"LEFT\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;With regards,</TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"LEFT\"></TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"right\">Yours sincerely,</TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"right\"></TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"right\"></TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"right\"></TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"right\"></TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +

							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"right\">( Mukul Roy )</TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"right\"></TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +

							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"LEFT\">"+trnRefBean.getREFERENCENAME()+"</TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"LEFT\">"+trnRefBean.getVIPSTATUS()+"</TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD width=\"10\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD><TD COLSPAN=2 align=\"LEFT\">"+stateName+"</TD><TD width=\"29\"><IMG SRC=\"cid:left_spacer\" WIDTH=35 HEIGHT=20></TD></TR>" +
							 "<TR><TD COLSPAN=4><IMG SRC=\"cid:letterHead_05\"></TD></TR>" +
							 "<TR><TD COLSPAN=4><IMG SRC=\"cid:letterHead_05\"></TD></TR>" +
//							 "<TR><TD COLSPAN=4><IMG SRC=\"cid:letterHead_05\"></TD></TR>" +
							 "</TD></TR></TABLE></TABLE>" +
							 "</BODY>";
			//System.out.println("--- "+ msgText);
			String [] attachment={filePath};
				
			outMessage = mailSend(attachment, mailId, msgText, trnRefBean.getACKBY(), trnRefBean.getACKDATE(), trnRefBean.getSUBJECT(), context);
		}
		catch (Exception e) {
			dbcon.rollback();
			log.fatal(e, e);
		} finally {
			dbcon.closeConnection();
		}
		return outMessage;
	}
	
	public String  mailSend(String[] Attachment,String email, String mailBody, String acknBy, String acknDate, String Subject, ServletContext context){
		String retValue = "";
		//String [] mailList={"sunilktripathi@gmail.com"};
		String [] mailList=email.split(",");
		//String [] ccmailList=null;
		String [] ccmailList={};
		//ccmailList=stcc.split(",");
		//String mailsubject="Engineering Control Morning Position - "+rlycode+" - "+posdate;
		String mailsubject=Subject;
		String mailbody=mailBody;
		try{
			(new MailSend()).SendMail(mailList, ccmailList, mailsubject, mailbody, Attachment, "mrcell@rb.railnet.gov.in", context);
//			(new MailSend()).SendMail(mailList, ccmailList, mailsubject, mailbody, Attachment, "suneelsambharia@cris.org.in", context);
			//System.out.println("mail id "+ email);
			retValue="GRNEmail Sent to - "+email+"";
		}catch(Exception e)	{
			e.printStackTrace();
			retValue="REDMail sending failed to - "+email+"";
	   	}finally{
	   	}
	   	return retValue;
	}

	@SuppressWarnings({ "null", "null" })
	public TrnReference getEofficeData(String eOFFICEREFNO) {
		// TODO Auto-generated method stub

		TrnReference refBean = null;
		

		RecieptDetails recieptdetails = null;
		String  proxyHost="172.16.1.61";
		  //String  proxyHost="";
		  String proxyPort="8080";
		// String proxyPort="";
		System.setProperty("http.proxyHost",proxyHost);
		System.setProperty("http.proxyPort", proxyPort);
		System.setProperty("https.proxyHost", proxyHost);
		System.setProperty("https.proxyPort", proxyPort);
		  try {
			  String encoding = Base64Encoder.encode ("eoffice:eoffice");

			  String urlParameters  = "computernumber=" +eOFFICEREFNO;
			  byte[] postData = urlParameters.getBytes();
			  int postDataLength = postData.length;
			URL url = new URL("http://eofficerb.ggndc.rcil.gov.in/eFileServices/rest/xmldataset/efile/receiptdetails");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty  ("Authorization", "Basic " + encoding);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
			
			conn.setUseCaches(false);
	
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				   wr.write( postData );
			
				   
				   
			/*OutputStream os = conn.getOutputStream();
			os.write(postData.getBytes());
			os.flush();
			os.close();
			 	*/
				   recieptdetails = new RecieptDetails();
			if (conn.getResponseCode() != 200) {
				refBean = new TrnReference();
				recieptdetails.setErrorCode(String.valueOf(conn.getResponseCode()));
				recieptdetails.setErrorMessage(conn.getResponseMessage());
				 refBean.setEOFFICEMSG(recieptdetails.getErrorCode() + ":- " + recieptdetails.getErrorMessage() +" Data Not Fetched");
				
			}
			else{
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			String xmlString="";
			//System.out.println("response: "+ conn.getResponseCode() +"Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				//System.out.println(output);
				xmlString+=output;
			}
			
			try {
			JAXBContext jaxbContext = JAXBContext.newInstance(WSResponse.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			WSResponse wsresponse = (WSResponse) jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
			//System.out.println(wsresponse);
			
			
			
		/*	jaxbContext = JAXBContext.newInstance(RecieptDetails.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			 recieptdetails = (RecieptDetails) jaxbUnmarshaller.unmarshal(new StringReader(wsresponse.toString()));*/
			
			JAXBContext jaxbContext1 = JAXBContext.newInstance(RecieptDetailsList.class);
			Unmarshaller jaxbUnmarshaller1 = jaxbContext1.createUnmarshaller();
			RecieptDetailsList rdl = (RecieptDetailsList) jaxbUnmarshaller1.unmarshal(new StringReader(wsresponse.toString()));
			
			refBean = new TrnReference();
		if(!(rdl.getList().size()==0))
		{
				
				recieptdetails = rdl.getList().get(0);
		
			
				DBConnection dbcon = new DBConnection();
				
				
				
				dbcon.openConnection();
				
				String strCheckForEofficeRefNo = "select count(*) from trnreference where eofficerefno = '"+recieptdetails.getComputernumber()+"'";
				String u = getStringParam(strCheckForEofficeRefNo, dbcon);
				
			if(Integer.parseInt(u) == 0){
				
				String strType = "select code from MSTGECMAPPING where EOFFICECODE = '"+StringFormat.nullString(recieptdetails.getCorredpondenceTypeId().replaceAll("\\s+",""))+"' and codetype = '0'";
				
				String strLang = "select code from MSTGECMAPPING where EOFFICECODE = '"+StringFormat.nullString(recieptdetails.getLanguageId().replaceAll("\\s+",""))+"' and codetype = '3'";
				String strurgency = "select code from MSTGECMAPPING where EOFFICECODE = '"+StringFormat.nullString(recieptdetails.getClassifiedId().replaceAll("\\s+",""))+"' and codetype = '1'";
				String strrecievedBy = "select code from MSTGECMAPPING where EOFFICECODE = '"+StringFormat.nullString(recieptdetails.getDeliveryModeId().replaceAll("\\s+",""))+"' and codetype = '9'";
				//String strSubjectCode = "select code from MSTSUBJECTMAPPING where EOFFICESUBJECTCODE = '"+StringFormat.nullString(recieptdetails.getSubjectCategoryId().replaceAll("\\s+",""))+"'";
				//String strRole = "select roleid from MSTROLEMAPPING where EOFFICEROLEID = "+StringFormat.nullString(recieptdetails.getPostDetailId().replaceAll("\\s+",""));
				log.debug(strType);
				log.debug(strLang);
				//log.debug(strSubjectCode);
				log.debug(strurgency);
				//log.debug(strRole);
				log.debug(strrecievedBy);
				String type = getStringParam(strType, dbcon);
				String Lang = getStringParam(strLang, dbcon);
				String recievedBy = getStringParam(strrecievedBy, dbcon);
				String urgency = getStringParam(strurgency, dbcon);
			
				
				//System.out.println(type + "=====" + Lang);
				
				
				
				
				refBean.setURGENCY(urgency);
			
				
				SimpleDateFormat sdfmt1 = new SimpleDateFormat("yyyy-mm-dd");
				SimpleDateFormat sdfmt2= new SimpleDateFormat("dd/mm/yyyy");
				String strOutput ="";
				if(!(recieptdetails.getLetterDate().length()==0)){
				//String forwardDate ="";
				try {
					java.util.Date dDate;
					dDate = sdfmt1.parse( recieptdetails.getLetterDate() );
					strOutput = sdfmt2.format( dDate );
					
					//dDate = sdfmt1.parse( recieptdetails.getSentTo() );
					//forwardDate = sdfmt2.format( dDate );
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				String strSubjectCode = "select subjectcode from MSTSUBJECTMAPPING where EOFFICESUBJECTCODE = '"+StringFormat.nullString(recieptdetails.getSubjectCategoryId().replaceAll("\\s+",""))+"'";
				String SubjectCode = getStringParam(strSubjectCode, dbcon);
				refBean.setSUBJECT(StringFormat.nullString(recieptdetails.getSubject().replaceAll("'", "")));
				refBean.setLETTERDATE(StringFormat.nullString(strOutput));
				refBean.setLANGUAGE(StringFormat.nullString(Lang));
				refBean.setREFCATEGORY(StringFormat.nullString(type));
				refBean.setRECIEVEDBY(recievedBy);
				refBean.setEOFFIEFLAG("eofficedata");
				refBean.setSUBJECTCODE(SubjectCode);
				refBean.setEOFFICERECEIPTNO(recieptdetails.getReceiptNumber());
				refBean.setEOFFICEREFNO(recieptdetails.getComputernumber());
				refBean.setREFERENCENAME(StringFormat.nullString(recieptdetails.getSenderName().replaceAll("'", "")));
				refBean.setADDRESSENGLISH(StringFormat.nullString(recieptdetails.getAddress().replaceAll("'", "")));
				//refBean.setURL(StringFormat.nullString("http://www.google.com"));
				
				//System.out.println("recieptdetails.getComputernumber()"+recieptdetails.getComputernumber());
				ArrayList<TrnMarking> markingBeanList = new ArrayList<TrnMarking>();
					for(int i=0;i<rdl.getList().size();i++){
						
						RecieptDetails rd = rdl.getList().get(i);
						TrnMarking markingBean = new TrnMarking();
						//refBean.setEOFFICEREFNO(StringFormat.nullString(recieptdetails.getComputernumber()));
						String strSubjectCode1 = "select subjectcode from MSTSUBJECTMAPPING where EOFFICESUBJECTCODE = '"+StringFormat.nullString(rd.getSubjectCategoryId().replaceAll("\\s+",""))+"'";
						String strRole = "select roleid from MSTROLEMAPPING where EOFFICEROLEID = "+StringFormat.nullString(rd.getPostDetailId().replaceAll("\\s+",""));
						
						
						
						SimpleDateFormat sdfmt1_N = new SimpleDateFormat("yyyy-mm-dd");
						SimpleDateFormat sdfmt2_N= new SimpleDateFormat("dd/mm/yyyy");
						
						String forwardDate_N ="";
						if(!(rd.getSentOn().length()==0)){
						//if(!rd.getSentOn().equalsIgnoreCase("")){
						try {
							java.util.Date dDate;
							
							dDate = sdfmt1_N.parse( rd.getSentOn() );
							forwardDate_N = sdfmt2_N.format( dDate );
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
						log.debug(strSubjectCode1);
						log.debug(strRole);
						String SubjectCode1 = getStringParam(strSubjectCode1, dbcon);
						String role = getStringParam(strRole, dbcon);
						markingBean.setEOFFICEREFNO(rd.getComputernumber());
						markingBean.setEOFFICERECEIPTNO(rd.getReceiptNumber());
						markingBean.setMARKINGDATE(forwardDate_N);
						markingBean.setSUBJECT(StringFormat.nullString(rd.getSubject().replaceAll("'", "")));
						markingBean.setSUBJECTCODE(SubjectCode1);
						markingBean.setMARKINGTO(role);
						//System.out.println("rd.getComputernumber()"+rd.getComputernumber());
						markingBeanList.add(markingBean);
						
					}
					refBean.setMarkingBeanList(markingBeanList);
					refBean.setEOFFICEMSG("Data Received successfully from E-Office");
			}else{
				
				refBean.setEOFFICEMSG("E-OFFICE Reference Number already registered");
			}
			dbcon.closeConnection();
		}
		else {
			refBean.setEOFFICEMSG("Blank XML Received from E-OFFICE");
		}
			} catch (JAXBException e) {
					e.printStackTrace();
				  }
			conn.disconnect();

		 } 
			
		  }catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
	
	 return refBean;
	}
}