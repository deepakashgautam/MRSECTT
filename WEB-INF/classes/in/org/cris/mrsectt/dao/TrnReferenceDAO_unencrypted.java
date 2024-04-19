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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.print.attribute.standard.MediaSize.NA;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import test.WSResponse;

import com.oreilly.servlet.Base64Encoder;

import in.org.cris.mrsectt.util.MailSend;

public class TrnReferenceDAO_unencrypted {
	static Logger log = LogManager.getLogger(TrnReferenceDAO_unencrypted.class);

	public ArrayList<MstClass> getCounterStatus(String tenureid) {
		//String result = "";
		DBConnection dbCon = new DBConnection();
		PreparedStatement ps = null;
		ArrayList<MstClass> arr = new ArrayList<MstClass>();
		try {

			String strSQL = " SELECT B.REFCLASS REFCLASS, B.TENUREID, "
					+ " REFCOUNTER COUNTER, INOUT,  " 
					+ " NVL(B.CLASSDESCRIPTION, ' ') CLASSDESCRIPTION "
					+ " FROM MSTCLASS B WHERE B.TENUREID = ? " 
					+ " AND YEAR = TO_CHAR(SYSDATE,'YYYY')   ORDER BY 1 ";

			dbCon.openConnection();
			
			ps = dbCon.setPrepStatement(strSQL);
			ps.setString(1, tenureid);
			
			log.debug(strSQL);
			ResultSet rs = ps.executeQuery();
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
		
	PreparedStatement pst = null;
		
	ArrayList<TrnReference> arr = new ArrayList<TrnReference>();
	ArrayList<String> params = new ArrayList<String>();
	ArrayList<String> parameters = new ArrayList<String>();
	parameters.add("");
	parameters.add(roleID);
	params.add("");
	params.add(roleID);
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
					condSub += " AND UPPER(LOGINID) LIKE UPPER(?)";
					params.add(loginId);
					condSub += " AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND  TO_DATE(?,'DD/MM/YYYY')";
					params.add(fdate);
					params.add(tdate);
					condSub += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
					condSub += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER(?)":"";
					if(refClass.length()>0){
						params.add(refClass);
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
									" FROM TRNREFERENCE A WHERE A.ROLEID = ? "+condSub+" " +
									" ORDER BY A.REFCLASS, A.INCOMINGDATE, A.REFNO";
				dbCon.openConnection();
				PreparedStatement ps = null;
				ps = dbCon.setPrepStatement(strSQL);
				for(int i=1;i<params.size();i++){
					ps.setString(i, params.get(i));
				}
				log.debug(strSQL);
				ResultSet rs = ps.executeQuery();
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
			//System.out.println("ok1---------------------------");
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
					condSub = condSub+"UPPER(SUBJECT) LIKE UPPER(?) "+orCond+" ";
					parameters.add("%"+sArray[i]+"%");
					//System.out.println("param subject :"+"%"+sArray[i]+"%");
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condVip = condVip+"UPPER(VIPSTATUS) LIKE UPPER(?) "+orCond+" ";
					parameters.add("%"+sArray[i]+"%");
					}
				}
				
				//code added by rohit
				
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condVipparty = condVipparty+"UPPER(VIPPARTY) LIKE UPPER() "+orCond+" ";
					parameters.add("%"+sArray[i]+"%");
					}
				}
				
				//end of code added by rohit
				
		//	REFNO & INCOMING DATE
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
						orCond = (i<sArray.length)?"OR":"";
						condVip = condVip+"UPPER(REFNO) LIKE UPPER(?) "+orCond+" ";
						parameters.add("%"+sArray[i]+"%");
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
						orCond = (i<sArray.length)?"OR":"";
						condVip = condVip+"TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') = ? "+orCond+" ";
						parameters.add(sArray[i]);
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
						orCond = (i<sArray.length)?"OR":"";
						condRef = condRef+"UPPER(REFERENCENAME) LIKE UPPER(?) "+orCond+" ";
						parameters.add("%"+sArray[i]+"%");
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condstate = condstate+"UPPER(STATECODE) LIKE UPPER(?) "+orCond+" ";
					parameters.add("%"+sArray[i]+"%");
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condstate = condstate+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1))) LIKE UPPER(?) "+orCond+" ";
					parameters.add("%"+sArray[i]+"%");
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length-1)?"OR":"";
					condRem = condRem+"UPPER(REMARKS) LIKE UPPER(?) "+orCond+" ";
					parameters.add("%"+sArray[i]+"%");
					}
				}
				//code added by Rounak
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length-1)?"OR":"";
					condTOURRem = condTOURRem+"UPPER(TOURREMARKS) LIKE UPPER(?) "+orCond+" ";
					parameters.add("%"+sArray[i]+"%");
					}	
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condEofficeRefNo = condEofficeRefNo+"UPPER(EOFFICEREFNO) LIKE UPPER(?) "+orCond+" ";
					parameters.add("%"+sArray[i]+"%");
					}
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length-1)?"OR":"";
				//	compliance = compliance+"UPPER(COMPLIANCE) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				//	compliance=(select code from mstgec where codetype='10' and sname like '%Not Feasible%')
					compliance = compliance+"compliance=(select code from mstgec where codetype='10' and sname like ?) "+orCond+" ";
					parameters.add("%"+sArray[i]+"%");
					}	
				}
				for(int i=0;i<sArray.length;i++){
					if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length-1)?"OR":"";
					complianceRemarks = complianceRemarks+"UPPER(COMPLIANCEREMARKS) LIKE UPPER(?) "+orCond+" ";
					parameters.add("%"+sArray[i]+"%");
					}	
				}
				
				
				if(fdate.length()>0 && tdate.length()>0){
					condDate = "AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+fdate+"','DD/MM/YYYY') AND  TO_DATE('"+tdate+"','DD/MM/YYYY')";
					parameters.add(fdate);
					parameters.add(tdate);
					condDate += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				}
				condRefCls += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER('"+refClass+"')":"";
				if(refClass.length()>0){
					parameters.add(refClass);
				}
				}else{
					condSub = condSub+"UPPER(SUBJECT) LIKE UPPER(?) OR ";
					parameters.add("%"+sString+"%");
					//System.out.println("param subject :"+"%"+sString+"%");
					condVip = condVip+"UPPER(VIPSTATUS) LIKE UPPER(?) OR ";
					parameters.add("%"+sString+"%");
					condVip = condVip+"UPPER(VIPPARTY) LIKE UPPER(?) OR "; //code added by rohit
					parameters.add("%"+sString+"%");
					condVip = condVip+"UPPER(REFNO) LIKE UPPER(?) OR ";
					parameters.add("%"+sString+"%");
					condVip = condVip+"TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') = ? OR ";
					parameters.add(sString);
					condRef = condRef+"UPPER(REFERENCENAME) LIKE UPPER(?) OR ";
					parameters.add("%"+sString+"%");
					condstate = condstate+"UPPER(STATECODE) LIKE UPPER(?) OR ";
					parameters.add("%"+sString+"%");
					condstate = condstate+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1))) LIKE UPPER(?) OR ";
					parameters.add("%"+sString+"%");
					condRem = condRem+"UPPER(REMARKS) LIKE UPPER(?) OR ";
					parameters.add("%"+sString+"%");
					condTOURRem = condTOURRem+"UPPER(TOURREMARKS) LIKE UPPER(?)  OR";
					parameters.add("%"+sString+"%");
					compliance = compliance+"UPPER(COMPLIANCE) LIKE UPPER(?) OR ";
					parameters.add("%"+sString+"%");
					condEofficeRefNo = condEofficeRefNo+"UPPER(EOFFICEREFNO) LIKE UPPER(?) OR ";
					parameters.add("%"+sString+"%");
					
					complianceRemarks = complianceRemarks+"UPPER(COMPLIANCEREMARKS) LIKE UPPER(?)  ";
					parameters.add("%"+sString+"%");
					if(fdate.length()>0 && tdate.length()>0){
						condDate = "AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND  TO_DATE(?,'DD/MM/YYYY')";
						parameters.add(fdate);
						parameters.add(tdate);
						condDate += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
					}
					condRefCls += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER(?)":"";
					if(refClass.length()>0){
						parameters.add(refClass);
					}
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
								" FROM TRNREFERENCE A WHERE A.ROLEID = ? AND ("+condSub+" "+condVip+" "+condVipparty+" "+condRef+" "+condstate+" "+ condRem +" "+ condTOURRem +" "+ compliance+" " + condEofficeRefNo+" "+complianceRemarks+") "+condDate+" "+condRefCls+"" +
								" ORDER BY A.REFCLASS, A.INCOMINGDATE, A.REFNO";
				dbCon.openConnection();
				pst = dbCon.setPrepStatement(strSQL);
				//System.out.println("size of params :"+ parameters.size());
				//System.out.println("str search :"+strSQL);
				for(int i=1; i<parameters.size(); i++){
					pst.setString(i, parameters.get(i));
					//System.out.println("parameter at "+i +" :" + parameters.get(i));
					
				}
				ResultSet rs = pst.executeQuery();
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
		//System.out.println("ok2---------------------------");
		DBConnection dbCon = new DBConnection();
		try {
				condSub += " AND UPPER(REFERENCENAME) LIKE UPPER(?)";
				parameters.add("%"+sString+"%");
				condSub += " AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND  TO_DATE(?,'DD/MM/YYYY')";
				parameters.add(fdate);
				parameters.add(tdate);
				condSub += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				condSub += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER(?)":"";
				if(refClass.length()>0){
					parameters.add(refClass);
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
								" FROM TRNREFERENCE A WHERE A.ROLEID = ? "+condSub+" " +
								" ORDER BY A.REFCLASS, A.INCOMINGDATE, A.REFNO";
			dbCon.openConnection();
			log.debug(strSQL);
			pst = dbCon.setPrepStatement(strSQL);
			for(int i=1; i<parameters.size(); i++){
				pst.setString(i, parameters.get(i)); 	
				
			}
			ResultSet rs = pst.executeQuery();
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
		//System.out.println("ok3---------------------------");
		DBConnection dbCon = new DBConnection();
		try {
				condSub += " AND UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1))) = UPPER(?)";
				parameters.add(sString);
				condSub += " AND TO_DATE((SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND  TO_DATE(?,'DD/MM/YYYY')";
				parameters.add(fdate);
				parameters.add(tdate);
				condSub += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				condSub += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER(?)":"";
				if(refClass.length()>0){
					parameters.add(refClass);
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
								" FROM TRNREFERENCE A WHERE A.ROLEID = ? "+condSub+" " +
								" ORDER BY A.REFCLASS, A.INCOMINGDATE, A.REFNO";
			dbCon.openConnection();
			pst = dbCon.setPrepStatement(strSQL);
			for(int i=1; i<parameters.size(); i++){
				pst.setString(i, parameters.get(i)); 	
				
			}
			ResultSet rs = pst.executeQuery();
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
		//System.out.println("ok4---------------------------");
		DBConnection dbCon = new DBConnection();
		try {
				condSub += " AND (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = A.DMARKINGTO) = UPPER(?)";
				parameters.add(sString);
				condSub += " AND TO_DATE(TO_CHAR(A.DMARKINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND  TO_DATE(?,'DD/MM/YYYY')";
				parameters.add(fdate);
				parameters.add(tdate);
				condSub += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				condSub += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER(?)":"";
				if(refClass.length()>0){
					parameters.add(refClass);
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
								" FROM TRNREFERENCE A WHERE A.ROLEID =? "+condSub+" " +
								" ORDER BY A.REFCLASS, A.INCOMINGDATE, A.REFNO";
			dbCon.openConnection();
			pst = dbCon.setPrepStatement(strSQL);
			for(int i=1; i<parameters.size(); i++){
				pst.setString(i, parameters.get(i)); 	
				
			}
			ResultSet rs = pst.executeQuery();
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
		//System.out.println("ok5---------------------------");
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
				condSub = condSub+"UPPER(SUBJECT) LIKE UPPER(?) "+orCond+" ";
				parameters.add("%"+sArray[i]+"%");
				}
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condVip = condVip+"UPPER(VIPSTATUS) LIKE UPPER(?) "+orCond+" ";
				parameters.add("%"+sArray[i]+"%");
				}
			}
			
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condVipparty = condVip+"UPPER(VIPPARTY) LIKE UPPER(?) "+orCond+" ";
				parameters.add("%"+sArray[i]+"%");
				}
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
					orCond = (i<sArray.length)?"OR":"";
					condRef = condRef+"UPPER(REFERENCENAME) LIKE UPPER(?) "+orCond+" ";
					parameters.add("%"+sArray[i]+"%");
				}
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condstate = condstate+"UPPER(STATECODE) LIKE UPPER(?) "+orCond+" ";
				parameters.add("%"+sArray[i]+"%");
				}
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condstate = condstate+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1))) LIKE UPPER(?) "+orCond+" ";
				parameters.add("%"+sArray[i]+"%");
				}
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length-1)?"OR":"";
				condRem = condRem+"UPPER(REMARKS) LIKE UPPER(?) "+orCond+" ";
				parameters.add("%"+sArray[i]+"%");
				}
			}
			//Code added by Rounak
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length-1)?"OR":"";
				condTOURRem = condTOURRem+"UPPER(TOURREMARKS) LIKE UPPER(?) "+orCond+" ";
				parameters.add("%"+sArray[i]+"%");
				}
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length)?"OR":"";
				condEofficeRefNo = condEofficeRefNo+"UPPER(EOFFICEREFNO) LIKE UPPER(?) "+orCond+" ";
				parameters.add(sArray[i]);
				}
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length-1)?"OR":"";
			//	compliance = compliance+"UPPER(COMPLIANCE) LIKE UPPER('%"+sArray[i]+"%') "+orCond+" ";
				compliance = compliance+"compliance=(select code from mstgec where codetype='10' and sname like ? ) "+orCond+" ";
				parameters.add("%"+sArray[i]+"%");
				}	
			}
			for(int i=0;i<sArray.length;i++){
				if(!sArray[i].trim().equalsIgnoreCase("")){
				orCond = (i<sArray.length-1)?"OR":"";
				complianceRemarks = complianceRemarks+"UPPER(COMPLIANCEREMARKS) LIKE UPPER(?) "+orCond+" ";
				parameters.add("%"+sArray[i]+"%");
				}	
			}
			
			if(fdate.length()>0 && tdate.length()>0){
				condDate = "AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND  TO_DATE(?,'DD/MM/YYYY')";
				parameters.add(fdate);
				parameters.add(tdate);
				condDate += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			}
			condRefCls += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER(?)":"";
			if(refClass.length()>0){
				parameters.add(refClass);
			}
			}else{
				condSub = condSub+"UPPER(SUBJECT) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
				condVip = condVip+"UPPER(VIPSTATUS) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
				condVip = condVip+"UPPER(VIPPARTY) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
				condRef = condRef+"UPPER(REFERENCENAME) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
				condstate = condstate+"UPPER(STATECODE) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
				condstate = condstate+"UPPER((SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1))) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
				condRem = condRem+"UPPER(REMARKS) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
				condTOURRem = condTOURRem+"UPPER(TOURREMARKS) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
				compliance = compliance+"UPPER(COMPLIANCE) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
				condEofficeRefNo = condEofficeRefNo+"UPPER(EOFFICEREFNO) LIKE UPPER(?) OR ";
				parameters.add("%"+sString+"%");
				complianceRemarks = complianceRemarks+"UPPER(COMPLIANCEREMARKS) LIKE UPPER(?)  ";
				parameters.add("%"+sString+"%");
				if(fdate.length()>0 && tdate.length()>0){
					condDate = "AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND  TO_DATE(?,'DD/MM/YYYY')";
					parameters.add(fdate);
					parameters.add(tdate);
					condDate += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				}
				condRefCls += refClass.length()>0?" AND UPPER(REFCLASS) LIKE UPPER(?)":"";
				if(refClass.length()>0){
					parameters.add(refClass);
				}
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
							" FROM TRNREFERENCE A WHERE A.ROLEID = ?  AND ("+condSub+" "+condVip+" "+condVipparty+" "+condRef+" "+condstate+" "+condRem+" "+condTOURRem+" "+compliance+" "+condEofficeRefNo+" "+complianceRemarks+") "+condDate+" "+condRefCls+"" +
							" ORDER BY A.REFCLASS, A.INCOMINGDATE, A.REFNO";
			
			//System.out.println("strSQL "+ strSQL);

			dbCon.openConnection();
			pst = dbCon.setPrepStatement(strSQL);
			for(int i=1; i<parameters.size(); i++){
				pst.setString(i, parameters.get(i)); 
				//System.out.println("paramters at "+i+" :"+parameters.get(i));
				
			}
			log.debug(strSQL);
			ResultSet rs = pst.executeQuery();
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
		PreparedStatement ps = null;
		ArrayList<TrnReference> arr = new ArrayList<TrnReference>();
		 
		try {

			String strSQL = " SELECT  A.REFNO,TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE, "
					+ " TO_CHAR(A.LETTERDATE,'DD/MM/YYYY') LETTERDATE,A.REFERENCENAME,A.VIPSTATUS,"
					+ "NVL((SELECT Q.SUBJECTNAME FROM MSTSUBJECT Q WHERE Q.SUBJECTCODE=A.SUBJECTCODE AND Q.SUBJECTTYPE = 'R'),' ') SUBJECTCODE,NVL(A.SUBJECT,' ') SUBJECT,"
					+ " to_char(A.INCOMINGDATE,'YYYYMMDD') ORDERDATE,A.STATECODE," +
					"(SELECT A.ROLENAME FROM MSTROLE A WHERE ROLEID = (SELECT M.MARKINGTO FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) MARKINGTO," +
					"(SELECT TO_CHAR(M.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1) MARKINGDATE," +
					"(SELECT F.FNAME FROM MSTGEC F WHERE F.CODETYPE = 4 AND F.CODE =(SELECT M.ACTION FROM TRNMARKING M WHERE M.REFID=A.REFID AND M.MARKINGSEQUENCE=1)) ACTION FROM TRNREFERENCE A"
					+ " WHERE A.REFERENCENAME=? AND A.VIPSTATUS=?  ORDER BY  ORDERDATE DESC";

			dbCon.openConnection();
			
			ps = dbCon.setPrepStatement(strSQL);
			ps.setString(1, refname);
			ps.setString(2, vipstatus);
			
			log.debug(strSQL);
			ResultSet rs = ps.executeQuery();
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
		PreparedStatement ps = null;
		ArrayList<String> arr = new ArrayList<String>();
		try {

			String strSQL = "  SELECT B.REFCLASS REFCLASS, B.TENUREID, "
					+ " REFCOUNTER COUNTER, "
					+ " NVL(B.CLASSDESCRIPTION, ' ') CLASSDESCRIPTION "
					+ " FROM MSTCLASS B WHERE B.TENUREID = ? "
					+ " AND YEAR = TO_CHAR(SYSDATE,'YYYY') ORDER BY 1 ";

			dbCon.openConnection();
			
			ps = dbCon.setPrepStatement(strSQL);
			ps.setString(1, tenureid);
			log.debug(strSQL);
			ResultSet rs = ps.executeQuery();
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
		PreparedStatement ps = null;
	
		try {

			/*
			 * String strSQL =
			 * " SELECT NVL(MAX(REFCOUNT),0) COUNTER FROM TRNREFERENCE WHERE " +
			 * " TENUREID ='"+ tenureid+ "'  AND REFCLASS='" + refclass +
			 * "'    ";
			 */
			String strSQL = " SELECT (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=A.ROLEID)||'/'||REFCLASS||'/'||REFCOUNTER||'/'||YEAR "
					+ " FROM MSTCLASS A WHERE TENUREID =?  AND UPPER(REFCLASS)=UPPER(?) AND YEAR = TO_CHAR(SYSDATE, 'YYYY')";
			

			dbCon.openConnection();
			
			ps = dbCon.setPrepStatement(strSQL);
			ps.setString(1, tenureid);
			ps.setString(2, refclass);
			log.debug(strSQL);
			ResultSet rs = ps.executeQuery();

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
		PreparedStatement ps = null;
		try {
			con.openConnection();
			// update the parent table to set the budget flag to true
			String updateQueryRef = " UPDATE TRNREFERENCE SET ISBUDGET = 'Y' WHERE REFID = ? ";
			
			ps = con.setPrepStatement(updateQueryRef);
			ps.setString(1, trnBudgetBeanList.get(0).getREFID());
			log.debug(updateQueryRef);
			ps.executeUpdate();

			// skip first row for hidden row
			if (trnBudgetBeanList.get(0).getREFID() != null && trnBudgetBeanList.get(0).getSUBJECT() != null) {
				// String GeneratedBUDGETSEQUENCE = "";
				String budgetDeleteQRY = "DELETE FROM TRNBUDGET WHERE REFID = ? ";
				
				ps = con.setPrepStatement(budgetDeleteQRY);
				ps.setString(1, trnBudgetBeanList.get(0).getREFID());
				log.debug(budgetDeleteQRY);
				ps.executeUpdate();
			}
			for (int i = 0; i < trnBudgetBeanList.size(); i++) {
				TrnBudget trnBudgetBean = trnBudgetBeanList.get(i);

				trnBudgetBean.setBUDGETSEQUENCE(getNextBudgetSequence(con,  trnBudgetBean.getREFID()));
				log.info("Generated Budget Sequence is  ************* " + trnBudgetBean.getBUDGETSEQUENCE());

				String budgetQuery = " INSERT INTO TRNBUDGET (REFID, BUDGETSEQUENCE, SUBJECTCODE, SUBJECT, MRREMARK, REMARK, LOGINID, CHANGEDATE) " +
									 " VALUES (?,?,?,?,?,?',?, SYSDATE)";
				
				ps = con.setPrepStatement(budgetQuery);
				ps.setString(1, trnBudgetBean.getREFID());
				ps.setString(2, trnBudgetBean.getBUDGETSEQUENCE());
				ps.setString(3, trnBudgetBean.getSUBJECTCODE());
				ps.setString(4, trnBudgetBean.getSUBJECT());
				ps.setString(5, trnBudgetBean.getMRREMARK());
				ps.setString(6, trnBudgetBean.getREMARK());
				ps.setString(7, LOGINID);
				
				log.debug(budgetQuery);
				ps.executeUpdate();
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
		PreparedStatement ps = null;
		try {
			con.openConnection();

			String budgetQuery = " 	DELETE FROM TRNBUDGET WHERE REFID = ? AND BUDGETSEQUENCE = ?";
			
			ps = con.setPrepStatement(budgetQuery);
			ps.setString(1, refId);
			ps.setString(2, budgetSequence);

			log.debug(budgetQuery);
			ps.executeUpdate();

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
		PreparedStatement ps = null;
		ArrayList<String> params = new ArrayList<String>();
		params.add("");
		String strSQL = "";
		String tempSQL = "";
		String[] arrRefNoFrom = null;
		String[] arrRefNoTo = null;
		String refClassFrom = null;
		String inout = "";
		ResultSet rs = null;
		
		params.add(roleId);
		
		if (refNofrom.trim().length() > 0 && refNoTo.trim().length() > 0)
		{
			arrRefNoFrom = refNofrom.split("/");
			arrRefNoTo = refNoTo.split("/");
			refClassFrom = arrRefNoFrom[0];
			String refCountFrom = arrRefNoFrom[1];
			String refCountTo = arrRefNoTo[1];
			tempSQL += " AND UPPER(A.REFCLASS) = UPPER(?) AND (A.REFCOUNT BETWEEN ? AND ?)";
			params.add(refClassFrom);
			params.add(refCountFrom);
			params.add(refCountTo);
			
			String sql = "SELECT INOUT FROM MSTCLASS WHERE REFCLASS = UPPER(?)";
			con.openConnection();
			try {
				PreparedStatement pst=con.setPrepStatement(sql);
				pst.setString(1, refClassFrom);
				rs = pst.executeQuery();
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
					+ " FROM TRNREFERENCE A WHERE A.ROLEID = ?  ";
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
					+ " FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID AND B.MARKINGSEQUENCE=1 AND A.ROLEID = ?  ";
		}
		tempSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
		
		if (incomingDateFrom.trim().length() > 0 && incomingDateTo.trim().length() > 0) {
			tempSQL += " AND TO_DATE(TO_CHAR(A.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')";
			params.add(incomingDateFrom);
			params.add(incomingDateTo);
		}
		
		if (referenceNameSearch.trim().length() > 0) {
			tempSQL += " AND UPPER(A.REFERENCENAME) LIKE UPPER(?)";
			params.add("%"+referenceNameSearch+"%");
		}
		
		if (subjectSearch.trim().length() > 0) {
			tempSQL += " AND UPPER(A.SUBJECT) LIKE UPPER(?)";
			params.add("%"+subjectSearch+"%");
		}
		
		if (REMARKSEARCH.trim().length() > 0) {
			tempSQL += " AND UPPER(A.REMARKS) LIKE UPPER(?)";
			params.add("%"+REMARKSEARCH+"%");
		}
		
		if (commonSearchValue.trim().length() > 0) {
			tempSQL += " AND B.MARKINGTO = (SELECT ROLEID FROM MSTROLE WHERE ROLENAME = UPPER(?))";
			params.add("%"+commonSearchValue+"%");
		}
		strSQL += tempSQL;
		strSQL += " ORDER BY A.REFID DESC";
		log.debug(strSQL);
		
		try {
			con.openConnection();
			ps = con.setPrepStatement(strSQL);
			for(int i=1;i<params.size();i++){
				ps.setString(i, params.get(i));
			}
			
			rs = ps.executeQuery();
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
		PreparedStatement ps = null;
		ArrayList<String> params = new ArrayList<String>();
		params.add("");
		
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
			" FROM TRNREFERENCE A WHERE A.ROLEID = ? ";
		params.add(roleId);
		String tempSQL = "";
		tempSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
		
		if (refNofrom.trim().length() > 0 && refNoTo.trim().length() > 0) {
			String[] arrRefNoFrom = refNofrom.split("/");
			String[] arrRefNoTo = refNoTo.split("/");
			String refClassFrom = arrRefNoFrom[0];
			String refCountFrom = arrRefNoFrom[1];
			String refCountTo = arrRefNoTo[1];
			tempSQL += " AND  UPPER(A.REFCLASS) = UPPER(?) " +
				// " AND (TO_CHAR(REFERENCEDATE, 'YYYY') BETWEEN '"+
				// refYearFrom+"' AND '"+refYearTo+"') " +
				" AND (A.REFCOUNT BETWEEN ? AND ? )";
			params.add(refClassFrom);
			params.add(refCountFrom);
			params.add(refCountTo);
		}
		strSQL += tempSQL;
		strSQL += " ORDER BY A.REFID DESC";
		
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
		PreparedStatement ps = null;

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
				+ " FROM TRNMARKING WHERE REFID = ?"
				// + " FROM TRNMARKING WHERE REFID IN (1, 2) "
				+ " AND MARKINGSEQUENCE =1 ORDER BY MARKINGSEQUENCE ";

		log.debug(strSQLMarking);
		dbCon.openConnection();
		try {
			ps = dbCon.setPrepStatement(strSQLMarking);
			ps.setString(1, refId);
			ResultSet rsMarking = ps.executeQuery();

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
		PreparedStatement ps = null;
		
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
					+ " FROM TRNREFERENCE A WHERE REFID = ? ORDER BY REFNO ";
			

			log.debug("Reference ** :-- " + strSQL);
			con.openConnection();
			
			ps = con.setPrepStatement(strSQL);
			ps.setString(1, refId);
			
			ResultSet rs = ps.executeQuery();
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
					+ " FROM TRNMARKING WHERE REFID = ?  AND ( (MARKINGSEQUENCE = 3 AND ACTION = 'RET') OR (MARKINGSEQUENCE = 5) OR (MARKINGSEQUENCE = 1)) ORDER BY MARKINGSEQUENCE DESC";

			log.debug("Marking ** :-- " + strSQLMarking);
			
			ps = con.setPrepStatement(strSQLMarking);
			ps.setString(1, refId);
			ResultSet rsMarking = ps.executeQuery();

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
					+ " FROM TRNBUDGET WHERE REFID = ?  ORDER BY BUDGETSEQUENCE ";

			log.debug("Budget ** :-- " + strSQLBudget);
			
			ps = con.setPrepStatement(strSQLBudget);
			ps.setString(1, refId);
			ResultSet rsBudget = ps.executeQuery();

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
			String strSQLyellowSlip = " SELECT SLIPTXT, SIGNEDBY FROM TRNYELLOWSLIP WHERE REFID = ?";
			log.debug(strSQLyellowSlip);
			
			ps = con.setPrepStatement(strSQLyellowSlip);
			ps.setString(1, refId);
			ResultSet rsYellowSlip = ps.executeQuery();
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
		PreparedStatement ps = null;
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
					+ " FROM TRNMARKING A  WHERE A.MARKINGTO= ?  AND A.ACTION ='RET' AND 3 = ( SELECT MAX(X.MARKINGSEQUENCE) FROM TRNMARKING X  WHERE X.REFID = A.REFID)";

			log.debug(strSQLMarking);
			con.openConnection();
			ps=con.setPrepStatement(strSQLMarking);
			ps.setString(1, roleId);
			ResultSet rsMarking = ps.executeQuery();
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

			String outBoxMarkingSequence = getNextMarkingSequence(dbcon, refId);

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

			outBoxMarkingSequence = getNextMarkingSequence(dbcon, refId);

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
		PreparedStatement pst = null;
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
		                            + " VALUES (?, ?, ?, ?, ?, ?, "
									+ " TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, "
									+ " TO_DATE(?, 'DD/MM/YYYY'),?,?,?, "
									+ " TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?,?,?,?, ?,?, "
									+ " TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, "
									+ " TO_DATE(?, 'DD/MM/YYYY'),?, ?, "
									+ " TO_DATE(?, 'DD/MM/YYYY'), ?, "
									+ " SYSDATE, "
									+ " SYSDATE,?, ?, ?, ?,?,?, ?, ?,?,?,?, "
									+ " TO_DATE(?, 'DD/MM/YYYY'), "
									+ " TO_DATE(?, 'DD/MM/YYYY'), ?,?, ?, ?, ?, ?, ?,?,?, " 
									+ " to_date(?,'dd/mm/yyyy'),?,?,?,?, ?,?,?,?,?, ?,?,? )";
					
					pst = dbcon.setPrepStatement(refQuery);
					 pst.setString(1,trnRefBean.getREFID());
					 pst.setString(2,trnRefBean.getTENUREID());
					 pst.setString(3,trnRefBean.getREFNO());
					  pst.setString(4,trnRefBean.getROLEID());
					  pst.setString(5,trnRefBean.getREFCLASS());
					  pst.setString(6,trnRefBean.getREFCOUNT());
					  pst.setString(7,trnRefBean.getINCOMINGDATE());
					  pst.setString(8,trnRefBean.getREFERENCENAME());
					 pst.setString(9, trnRefBean.getADDRESSENGLISH());
					  pst.setString(10,trnRefBean.getSALUTATION());
					  pst.setString(11,trnRefBean.getVIPSTATUSDESC());
					  pst.setString(12,trnRefBean.getLETTERDATE());
					  pst.setString(13,trnRefBean.getISBUDGET());
					  pst.setString(14,trnRefBean.getVIPSTATUS());
					  pst.setString(15,trnRefBean.getSTATECODE());
					   pst.setString(16,trnRefBean.getACKDATE());
					  pst.setString(17, trnRefBean.getACKBY());
					   pst.setString(18,trnRefBean.getREFCATEGORY());
					  							  pst.setString(19,trnRefBean.getLANGUAGE());
												 pst.setString(20, trnRefBean.getURGENCY());
												  pst.setString(21,trnRefBean.getLINKREFID());
												  pst.setString(22,trnRefBean.getNOTECREATOR());
												  pst.setString(23,trnRefBean.getSECURITYGRADING());
												  pst.setString(24,trnRefBean.getSIGNEDBY());
												  pst.setString(25,trnRefBean.getSIGNEDON());
												  pst.setString(26,trnRefBean.getREMARKS());
												  pst.setString(27,trnRefBean.getTOURREMARKS());
												  pst.setString(28,trnRefBean.getCOMPLIANCE());
												  pst.setString(29,trnRefBean.getCOMPLIANCEREMARKS());
												  pst.setString(30,trnRefBean.getCOMPLIANCEDATE());
												  pst.setString(31,trnRefBean.getODSPLACE());
												 pst.setString(32, trnRefBean.getODSRAILWAY());
												  pst.setString(33,trnRefBean.getODSDATE());
												 pst.setString(34, trnRefBean.getLOGINID());
												 pst.setString(35, trnRefBean.getFMID());
												 pst.setString(36, trnRefBean.getFILECOUNTER());
												 pst.setString(37, trnRefBean.getREGISTRATIONNO());
												  pst.setString(38,trnRefBean.getFILENO());
												 pst.setString(39, trnRefBean.getEOFFICEFILENO());
												  pst.setString(40,trnRefBean.getIMARKINGTO());
												  pst.setString(41,trnRefBean.getFILESTATUS1());
												  pst.setString(42,trnRefBean.getFILESTATUS2());
												 pst.setString(43, trnRefBean.getREPLYTYPE());
												  pst.setString(44,trnRefBean.getREASON());
												  pst.setString(45,trnRefBean.getDMARKINGTO());
												  pst.setString(46,trnRefBean.getDMARKINGDATE());
												 pst.setString(47,trnRefBean.getREGISTRATIONDATE());
												  pst.setString(48,trnMarkingBean.getSUBJECTCODE());
												 pst.setString(49, trnMarkingBean.getSUBJECT());
												 pst.setString(50, trnRefBean.getSTATUSREMARK());
												  pst.setString(51,trnRefBean.getRECIEVEDBY());
												  pst.setString(52,trnRefBean.getTAG1()); 
												 pst.setString(53, trnRefBean.getTAG2());
												 pst.setString(54,trnRefBean.getTAG3()); 
												 pst.setString(55,trnRefBean.getTAG4()); 
												 pst.setString(56,trnRefBean.getIMARKINGON());
												 pst.setString(57,trnRefBean.getCHANGEDATE());
												 pst.setString(58,trnRefBean.getISCONF());
												 pst.setString(59,trnRefBean.getVIPPARTY());
												 pst.setString(60,trnRefBean.getADDVIPTYPE());
												 pst.setString(61,trnRefBean.getADDVIPID());
												 pst.setString(62,trnMarkingBean.getEOFFICEREFNO());
												 pst.setString(63,trnMarkingBean.getEOFFICERECEIPTNO()); 
												 pst.setString(64,k1);
												 pst.setString(65,k2); 
												 pst.setString(66,trnMarkingBean.getKeywords3());
												 pst.setString(67,k1);
												 pst.setString(68,k2);
												 pst.setString(69,trnMarkingBean.getKeywords3());
												 
					pst.executeUpdate();

					log.debug(refQuery);
					//dbcon.insert(refQuery);
					
					//constituency update 
					if(trnRefBean.getADDVIPID().length()!=0)
					{
					String constituencyQuerry = "update trnreference a set VIPCONSTITUENCY = (select VIPCONSTITUENCY from mstvip where vipid = ? ) where a.refid = ?";
					pst = dbcon.setPrepStatement(constituencyQuerry);
					pst.setString(1, trnRefBean.getADDVIPID());
					pst.setString(2, trnRefBean.getREFID());
					
					pst.executeUpdate();
					log.debug(constituencyQuerry);
					//dbcon.update(constituencyQuerry);
					}
					// insert into Trnmarking
					trnMarkingBean.setMARKINGSEQUENCE(getNextMarkingSequence(
							dbcon,  trnRefBean
									.getREFID()));
					String markingQuery = " INSERT INTO TRNMARKING (REFID, MARKINGSEQUENCE, MARKINGFROM, MARKINGTO, MARKINGDATE, MARKINGREMARK, "
							// +
							// " TARGETDAYS, TARGETDATE, SUBJECTCODE, SUBJECT, ACTION, ACTIONDATE) "
							// + " VALUES (" + " '"
							+ " TARGETDAYS, TARGETDATE, ACTION, ACTIONDATE) "
							+ " VALUES (?,?,?,?,"
							+ " SYSDATE,?,?,"
							+ " TO_DATE(?,'DD/MM/YYYY'),"
							+ " 'FOW'," + " SYSDATE" + ")";
					pst = dbcon.setPrepStatement(markingQuery);
					pst.setString(1,trnRefBean.getREFID());
					pst.setString(2,trnMarkingBean.getMARKINGSEQUENCE());
					pst.setString(3,trnMarkingBean.getMARKINGFROM());
					pst.setString(4,trnMarkingBean.getMARKINGTO());
					pst.setString(5,trnRefBean.getREMARKS());
					pst.setString(6,trnMarkingBean.getTARGETDAYS());
					pst.setString(7,trnMarkingBean.getTARGETDATE());
					
					pst.executeUpdate();
					log.debug(markingQuery);
					//dbcon.insert(markingQuery);
					
					String strSQL = "SELECT ROLENAME FROM MSTROLE WHERE ROLEID = ? ";
					log.debug(strSQL);
					pst = dbcon.setPrepStatement(strSQL);
					pst.setString(1, trnMarkingBean.getMARKINGTO());
					String markingName = getStringParamPrepared(pst, dbcon);

					// set the variable to flash output message
					refNoAndMarking += "<BR>"
							+ StringFormat.leftPad(i + 1 + "", 2, ' ')
							+ ".     " + trnRefBean.getREFNO()
							+ " - " + markingName+ ".     /-"+trnRefBean.getREFID() + "/-";

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

							" INCOMINGDATE = TO_DATE(?,'DD/MM/YYYY'), "
							+ " REFERENCENAME = ?, "
							
							+ " ADDRESSENGLISH = ?, "
							+ " SALUTATION = ?, "
							+ " VIPSTATUSDESC = ?, "
							+ " LETTERDATE = TO_DATE(?,'DD/MM/YYYY'), "
							+ " ISBUDGET = ?, "
							+ " VIPSTATUS = ?, "
							+ " STATECODE = ?, VIPPARTY = ?,ADDVIPID = ?,ADDVIPTYPE = ?, ACKDATE = TO_DATE(?,'DD/MM/YYYY'), "
							+ " ACKBY = ?, "
							+ " REFCATEGORY = ?, "
							+ " LANGUAGE = ?, "
							+ " URGENCY = ?, "
							+ " LINKREFID = ?, "
							+ " NOTECREATOR = ?, "
							+ " SECURITYGRADING = ?, "
							+ " SIGNEDBY = ?, "
							+ " SIGNEDON = TO_DATE(?,'DD/MM/YYYY'), "
							+ " REMARKS = ?, "
							+ " TOURREMARKS = ?, "
							
							
							
							+ " COMPLIANCE = ?, "
							+ " COMPLIANCEREMARKS = ?, "
							+ " COMPLIANCEDATE = TO_DATE(?,'DD/MM/YYYY'), "
							
							
							+ " ODSPLACE = ?, " 
							+ " ODSRAILWAY = ?, "
							+ " ODSDATE = TO_DATE(?,'DD/MM/YYYY'), " 

							+ " CHANGEDATE = SYSDATE, " 
							+ " SUBJECTCODE = ?, "
							+ " SUBJECT = ?, "
							+ " RECIEVEDBY = ?, "
							+ " TAG1 = ?, "
							+ " TAG2 = ?, "
							+ " TAG3 = ?, "
							+ " TAG4 = ?, "
							+ " REGISTRATIONNO = ?, "
							+ " FILENO = ?, "
							+ " EOFFICENO = ?, "
							+ " IMARKINGTO = ?, "
							+ " FILESTATUS1 = ?, "
							+ " FILESTATUS2 = ?, "
							+ " REPLYTYPE = ?, "
							+ " REASON = ?, "
							+ " DMARKINGTO = ?, "
							+ " DMARKINGDATE = to_date(?,'dd/mm/yyyy'), "
							+ " REGISTRATIONDATE = to_date(?,'dd/mm/yyyy'), "
							+ " KEYWORD1 = ?, "
							+ " KEYWORD2 = ?, "
							+ " KEYWORD3 = ?, "
							+ " K1 = ?, "
							+ " K2 = ?, "
							+ " K3 = ? "							
							+ " WHERE REFID =  ?";
					pst = dbcon.setPrepStatement(QueryUpdate);
					
					pst.setString(1,trnRefBean.getINCOMINGDATE());
					pst.setString(2, trnRefBean.getREFERENCENAME()); 
					 pst.setString(3,trnRefBean.getADDRESSENGLISH());
					 pst.setString(4,trnRefBean.getSALUTATION());
					 pst.setString(5,trnRefBean.getVIPSTATUSDESC()); 
					 pst.setString(6,trnRefBean.getLETTERDATE());
					 pst.setString(7,trnRefBean.getISBUDGET());
					 pst.setString(8,trnRefBean.getVIPSTATUS());
					 pst.setString(9,trnRefBean.getSTATECODE());
					 pst.setString(10,trnRefBean.getVIPPARTY());
					pst.setString(11,trnRefBean.getADDVIPID());
					pst.setString(12,trnRefBean.getADDVIPTYPE());
					pst.setString(13,trnRefBean.getACKDATE());
					pst.setString(14,trnRefBean.getACKBY()); 
					pst.setString(15,trnRefBean.getREFCATEGORY());
					pst.setString(16,trnRefBean.getLANGUAGE());
					pst.setString(17,trnRefBean.getURGENCY());
					pst.setString(18,trnRefBean.getLINKREFID());
					 pst.setString(19,trnRefBean.getNOTECREATOR());
					 pst.setString(20,trnRefBean.getSECURITYGRADING());
					pst.setString(21,trnRefBean.getSIGNEDBY());
					 pst.setString(22,trnRefBean.getSIGNEDON());
					pst.setString(23,trnRefBean.getREMARKS()); 
					pst.setString(24,trnRefBean.getTOURREMARKS());
					pst.setString(25,trnRefBean.getCOMPLIANCE()); 
					pst.setString(26,trnRefBean.getCOMPLIANCEREMARKS());
					pst.setString(27,trnRefBean.getCOMPLIANCEDATE());
					pst.setString(28,trnRefBean.getODSPLACE());
					pst.setString(29,trnRefBean.getODSRAILWAY());
					pst.setString(30,trnRefBean.getODSDATE());
					pst.setString(31,trnMarkingBean.getSUBJECTCODE());
					pst.setString(32,trnMarkingBean.getSUBJECT());
					pst.setString(33,trnRefBean.getRECIEVEDBY());
					pst.setString(34,trnRefBean.getTAG1());
					pst.setString(35,trnRefBean.getTAG2());
					pst.setString(36,trnRefBean.getTAG3());
					pst.setString(37,trnRefBean.getTAG4());
					pst.setString(38,trnRefBean.getREGISTRATIONNO());
					pst.setString(39,trnRefBean.getFILENO());
					pst.setString(40,trnRefBean.getEOFFICEFILENO());
					pst.setString(41,trnRefBean.getIMARKINGTO());
					pst.setString(42,trnRefBean.getFILESTATUS1());
					pst.setString(43,trnRefBean.getFILESTATUS2());
					pst.setString(44,trnRefBean.getREPLYTYPE());
					pst.setString(45,trnRefBean.getREASON());
					pst.setString(46,trnRefBean.getDMARKINGTO());
					pst.setString(47,trnRefBean.getDMARKINGDATE());
					pst.setString(48,trnRefBean.getREGISTRATIONDATE());
					pst.setString(49,k1);
					pst.setString(50,k2);
					pst.setString(51,trnRefBean.getKeywords3());
					pst.setString(52,k1);
					pst.setString(53,k2); 
					pst.setString(54,trnMarkingBean.getKeywords3());
					pst.setString(55,trnRefBean.getREFID());
					
					
					log.debug(QueryUpdate);
					pst.executeUpdate();
					//dbcon.update(QueryUpdate);

					// update the marking table
					String QueryUpdateMarking = " UPDATE TRNMARKING SET "
							+ " MARKINGFROM = ?, "
							+ " MARKINGTO = ?, "
							+ " MARKINGDATE = TO_DATE(?,'DD/MM/YYYY'), "
							+ " MARKINGREMARK = ?, "
							+ " TARGETDAYS = ?, "
							+ " TARGETDATE = TO_DATE(?,'DD/MM/YYYY') "
							
							+ " WHERE REFID =  ?  AND MARKINGSEQUENCE = ?";
					pst=dbcon.setPrepStatement(QueryUpdateMarking);
					
					pst.setString(1,trnMarkingBean.getMARKINGFROM());
					 pst.setString(2,trnMarkingBean.getMARKINGTO());
					 pst.setString(3,trnMarkingBean.getMARKINGDATE());
					 pst.setString(4,trnMarkingBean.getMARKINGREMARK());
					 pst.setString(5,trnMarkingBean.getTARGETDAYS());
					 pst.setString(6,trnMarkingBean.getTARGETDATE());
					 pst.setString(7,trnRefBean.getREFID());
					 pst.setString(8,trnMarkingBean.getMARKINGSEQUENCE());
					 
					log.debug(QueryUpdateMarking);
					pst.executeUpdate();
					//dbcon.update(QueryUpdateMarking);
					
					refNoAndMarking += "/-"+ trnRefBean.getREFID();
				}
				
		if(trnRefBean.getTXT_NOTE().length() > 0 && trnRefBean.getSIGNEDBY_YS().length() > 0) {		
				String strSQLSelect = "SELECT COUNT(REFID) FROM TRNYELLOWSLIP WHERE REFID = ?";
				pst=dbcon.setPrepStatement(strSQLSelect);
				pst.setString(1, trnRefBean.getREFID());
				
				log.debug(strSQLSelect);
				ResultSet rs = pst.executeQuery();
				int count = 0;
				if(rs.next()){
					count = rs.getInt(1);
				}
				if(count > 0){
					// UPDATE into YELLOWSLIP
					String strSqlYS = " UPDATE TRNYELLOWSLIP SET SLIPTXT = ?," +
									  " SIGNEDBY = ?," +
									  " SIGNEDON = SYSDATE," +
									  " MARKTO =?  WHERE REFID = ?";
					pst = dbcon.setPrepStatement(strSqlYS);
					
					pst.setString(1, trnRefBean.getTXT_NOTE());
					pst.setString(2, trnRefBean.getSIGNEDBY_YS());
					pst.setString(3, trnMarkingBean.getMARKINGTO());
					pst.setString(4, trnRefBean.getREFID());
					
					log.debug(strSqlYS);
					pst.executeUpdate();
					//dbcon.update(strSqlYS);
				}else {
					// insert into YELLOWSLIP
					String strSqlYS = " INSERT INTO TRNYELLOWSLIP (REFID, ROLEID, SLIPTXT, SIGNEDBY, SIGNEDON, MARKTO,ISOPENED)" +
									  " VALUES(?,?,?,?, SYSDATE,?, 'N')";
					pst = dbcon.setPrepStatement(strSqlYS);
					
					pst.setString(1, trnRefBean.getREFID());
					pst.setString(2, trnRefBean.getROLEID());
					pst.setString(3, trnRefBean.getTXT_NOTE());
					pst.setString(4, trnRefBean.getSIGNEDBY_YS());
					pst.setString(5, trnMarkingBean.getMARKINGTO());
				
					
					log.debug(strSqlYS);
					pst.executeUpdate();
					//dbcon.insert(strSqlYS);
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
		
		PreparedStatement pst = null;
		DBConnection dbcon = new DBConnection();
		try {
			dbcon.openConnection();
			
			// variable for holding generated ref no and marking
			String refNoAndMarking = "";
			
				// update OR insert for each row of the table
				if (trnRefBean.getREFID().trim().length() <= 0) {
					trnRefBean.setREFID(CommonDAO.getNextIdRef(dbcon));
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
								+ " VALUES (?, ?,?, ?, ?,?, "
								+ " TO_DATE(?, 'DD/MM/YYYY'),?, ?, ?, ?, "
								
								+ " TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, "
								+ " TO_DATE(?, 'DD/MM/YYYY'), ?,?, ?,?, ?,?, ?,?, "
								+ " TO_DATE(?, 'DD/MM/YYYY'),?,?, ?, ?,?, ?, ?, "
								+ " TO_DATE(?, 'DD/MM/YYYY'),?, "
								+ " SYSDATE, "
								+ " SYSDATE, ?, ?,?, ?, ?, ?, ?, ?,?, ?, ?, "
								+ " TO_DATE(?, 'DD/MM/YYYY'), "
								+ " TO_DATE(?, 'DD/MM/YYYY'),?,?,?, ?,?,?, ?, ?, ?, ?,?,?,?,?, ? )";
					
					pst = dbcon.setPrepStatement(refQuery);
					
					pst.setString(1,trnRefBean.getREFID());
					 pst.setString(2,trnRefBean.getTENUREID());
					 pst.setString(3,trnRefBean.getREFNO());
					 pst.setString(4,trnRefBean.getROLEID());
					 pst.setString(5,trnRefBean.getREFCLASS());
					 pst.setString(6,trnRefBean.getREFCOUNT());
					 pst.setString(7,trnRefBean.getINCOMINGDATE());
					 pst.setString(8,trnRefBean.getREFERENCENAME());
					 pst.setString(9,trnRefBean.getADDRESSENGLISH());
					 pst.setString(10,trnRefBean.getSALUTATION());
					 pst.setString(11,trnRefBean.getVIPSTATUSDESC());
					 pst.setString(12,trnRefBean.getLETTERDATE());
					 pst.setString(13,trnRefBean.getISBUDGET());
					 pst.setString(14,trnRefBean.getVIPSTATUS());
					 pst.setString(15,trnRefBean.getSTATECODE());
					 pst.setString(16,trnRefBean.getACKDATE());
					 pst.setString(17,trnRefBean.getACKBY());
					 pst.setString(18,trnRefBean.getREFCATEGORY());
					 pst.setString(19,trnRefBean.getLANGUAGE());
					 pst.setString(20,trnRefBean.getURGENCY());
					 pst.setString(21,trnRefBean.getLINKREFID());
					 pst.setString(22,trnRefBean.getNOTECREATOR());
					 pst.setString(23,trnRefBean.getSECURITYGRADING());
					 pst.setString(24,trnRefBean.getSIGNEDBY());
					 pst.setString(25,trnRefBean.getSIGNEDON());
					 pst.setString(26,trnRefBean.getREMARKS());
					 pst.setString(27,trnRefBean.getTOURREMARKS());
					 pst.setString(28,trnRefBean.getCOMPLIANCE());
					 pst.setString(29,trnRefBean.getCOMPLIANCEREMARKS());
					 pst.setString(30,trnRefBean.getCOMPLIANCEDATE());
					 pst.setString(31,trnRefBean.getODSPLACE());
					 pst.setString(32,trnRefBean.getODSRAILWAY());
					 pst.setString(33,trnRefBean.getODSDATE());
					 pst.setString(34,trnRefBean.getLOGINID());
					 pst.setString(35,trnRefBean.getFMID());
					 pst.setString(36,trnRefBean.getFILECOUNTER());
					 pst.setString(37,trnRefBean.getREGISTRATIONNO());
					 pst.setString(38,trnRefBean.getFILENO());
					 pst.setString(39,trnRefBean.getIMARKINGTO());
					 pst.setString(40,trnRefBean.getFILESTATUS1());
					 pst.setString(41,trnRefBean.getFILESTATUS2());
					 pst.setString(42,trnRefBean.getREPLYTYPE());
					 pst.setString(43,trnRefBean.getREASON());
					 pst.setString(44,trnRefBean.getEOFFICEREFNO());
					 pst.setString(45,trnRefBean.getDMARKINGTO());
					 pst.setString(46,trnRefBean.getDMARKINGDATE());
					 pst.setString(47,trnRefBean.getREGISTRATIONDATE());
					 pst.setString(48,trnRefBean.getSUBJECTCODE());
					 pst.setString(49,trnRefBean.getSUBJECT());
					 pst.setString(50,trnRefBean.getSTATUSREMARK() );
					 pst.setString(51,trnRefBean.getRECIEVEDBY());
					 pst.setString(52,trnRefBean.getTAG1());
					 pst.setString(53,trnRefBean.getTAG2());
					 pst.setString(54,trnRefBean.getTAG3());
					 pst.setString(55,trnRefBean.getTAG4());
					 pst.setString(56,trnRefBean.getISCONF());
					 pst.setString(57,k1);
					 pst.setString(58,k2);
					 pst.setString(59,trnRefBean.getKeywords3());
					 pst.setString(60,k1);
					 pst.setString(61,k2);
					 pst.setString(62,trnRefBean.getKeywords3());
						
					
					log.debug(refQuery);
					pst.executeUpdate();
					//dbcon.insert(refQuery);
					
					String markingName = "";
					
					// set the variable to flash output message
					refNoAndMarking += "<BR>"
						+ StringFormat.leftPad(1 + "", 2, ' ')
						+ ".     " + trnRefBean.getREFNO()
						+ " - " + markingName+ ".     /-"+trnRefBean.getREFID() + "/-";
					
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
					" INCOMINGDATE = TO_DATE(?,'DD/MM/YYYY'), "
					+ " REFERENCENAME = ?, "
					
					+ " ADDRESSENGLISH = ?, "
							+ " SALUTATION = ?, "
							+ " VIPSTATUSDESC = ?, "
							+ " EOFFICEREFNO = ?, "
							
					+ " LETTERDATE = TO_DATE(?,'DD/MM/YYYY'), "
					+ " ISBUDGET = ?, "
					+ " VIPSTATUS = ?, "
					+ " STATECODE = ?, "
					+ " ACKDATE = TO_DATE(?,'DD/MM/YYYY'), "
					+ " ACKBY = ?, "
					+ " REFCATEGORY = ?, "
					+ " LANGUAGE = ?, "
					+ " URGENCY = ?, "
					+ " LINKREFID = ?, "
					+ " NOTECREATOR = ?, "
					+ " SECURITYGRADING = ?, "
					+ " SIGNEDBY = ?, "
					+ " SIGNEDON = TO_DATE(?,'DD/MM/YYYY'), "
					+ " REMARKS = ?, "
					+ " TOURREMARKS = ?, "
					
					+ " COMPLIANCE = ?, "
							+ " COMPLIANCEREMARKS = ?, "
							+ " COMPLIANCEDATE = ?, "
					
					+ " ODSPLACE = ?, "
					+ " ODSRAILWAY = ?, "
					+ " ODSDATE = TO_DATE(?,'DD/MM/YYYY'), " 

					+ " CHANGEDATE = SYSDATE, "
					+ " SUBJECTCODE = ?, "
					+ " SUBJECT = ?, " 
					+ " RECIEVEDBY = ?, "
					+ " TAG1 = ?, "
					+ " TAG2 = ?, "
					+ " TAG3 = ?, "
					+ " TAG4 = ?, "
					+ " KEYWORD1 = ?, "
					+ " KEYWORD2 = ?, "
					+ " KEYWORD3 = ?, "
					+ " K1 = ?, "
					+ " K2 = ?, "
					+ " K3 = ?  WHERE REFID = ? ";
					
					pst = dbcon.setPrepStatement(QueryUpdate);
					
					 pst.setString(1,trnRefBean.getINCOMINGDATE());
					 pst.setString(2,trnRefBean.getREFERENCENAME());
					 pst.setString(3,trnRefBean.getADDRESSENGLISH());
					 pst.setString(4,trnRefBean.getSALUTATION());
					 pst.setString(5,trnRefBean.getVIPSTATUSDESC());
					 pst.setString(6,trnRefBean.getEOFFICEREFNO());
					 pst.setString(7,trnRefBean.getLETTERDATE());
					 pst.setString(8,trnRefBean.getISBUDGET());
					 pst.setString(9,trnRefBean.getVIPSTATUS());
					 pst.setString(10,trnRefBean.getSTATECODE());
					 pst.setString(11,trnRefBean.getACKDATE());
					 pst.setString(12,trnRefBean.getACKBY());
					 pst.setString(13,trnRefBean.getREFCATEGORY());
					 pst.setString(14,trnRefBean.getLANGUAGE());
					 pst.setString(15,trnRefBean.getURGENCY());
					 pst.setString(16,trnRefBean.getLINKREFID());
					 pst.setString(17,trnRefBean.getNOTECREATOR());
					 pst.setString(18,trnRefBean.getSECURITYGRADING());
					 pst.setString(19,trnRefBean.getSIGNEDBY());
					 pst.setString(20,trnRefBean.getSIGNEDON());
					 pst.setString(21,trnRefBean.getREMARKS());
					 pst.setString(22,trnRefBean.getTOURREMARKS() );
					 pst.setString(23,trnRefBean.getCOMPLIANCE());
					 pst.setString(24,trnRefBean.getCOMPLIANCEREMARKS());
					 pst.setString(25,trnRefBean.getCOMPLIANCEDATE());
					 pst.setString(26,trnRefBean.getODSPLACE());
					 pst.setString(27,trnRefBean.getODSRAILWAY());
					 pst.setString(28,trnRefBean.getODSDATE());
					 pst.setString(29,trnRefBean.getSUBJECTCODE());
					 pst.setString(30,trnRefBean.getSUBJECT());
					 pst.setString(31,trnRefBean.getRECIEVEDBY());
					 pst.setString(32,trnRefBean.getTAG1());
					 pst.setString(33,trnRefBean.getTAG2());
					 pst.setString(34,trnRefBean.getTAG3());
					 pst.setString(35,trnRefBean.getTAG4());
					 pst.setString(36,k1);
					 pst.setString(37,k2);
					 pst.setString(38,trnRefBean.getKeywords3());
					 pst.setString(39,k1);
					 pst.setString(40,k2);
					 pst.setString(41,trnRefBean.getKeywords3());
					 pst.setString(42,trnRefBean.getREFID());
					
					log.debug(QueryUpdate);
					pst.executeQuery();
					//dbcon.update(QueryUpdate);
					
					refNoAndMarking += "/-"+ trnRefBean.getREFID() + "/-";
				}
			
			outMessage = "GRNSave operation successful. " + refNoAndMarking;
			
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
		PreparedStatement ps = null;
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
							
					ps = dbcon.setPrepStatement(budgetDeleteQRY);
					ps.setString(1,trnBudgetBean.getREFID());
					
					log.debug(budgetDeleteQRY);
					ps.executeUpdate();
				}

				// log.info("budget seq : "+ trnBudgetBean.getBUDGETSEQUENCE());
				// update OR insert for each row of the table
				if (trnBudgetBean.getBUDGETSEQUENCE().trim().length() <= 0) {

					trnBudgetBean.setBUDGETSEQUENCE(getNextBudgetSequence(
							dbcon,  trnBudgetBean
									.getREFID()));
					log.info("Generated Budget Sequence is  ************* "
							+ trnBudgetBean.getBUDGETSEQUENCE());
				}
				String budgetQuery = " INSERT INTO TRNBUDGET (REFID,BUDGETSEQUENCE,SUBJECTCODE,SUBJECT,MRREMARK,REMARK,LOGINID,CHANGEDATE) "
						+ " VALUES (?,"
						+ " ?,"
						+ " ?,"
						+ " ?,"
						+ " ?,"
						+ " ?,"
						+ " ?," + " SYSDATE" + ")";
						
				ps = dbcon.setPrepStatement(budgetQuery);
				ps.setString(1,trnBudgetBean.getREFID());
				 ps.setString(2,trnBudgetBean.getBUDGETSEQUENCE());
				 ps.setString(3,trnBudgetBean.getSUBJECTCODE());
				 ps.setString(4,trnBudgetBean.getSUBJECT());
				 ps.setString(5,trnBudgetBean.getMRREMARK());
				 ps.setString(6,trnBudgetBean.getREMARK());
				 ps.setString(7,trnBudgetBean.getLOGINID());

				log.debug(budgetQuery);
				ps.executeUpdate();
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
	
	public String getNextBudgetSequence(DBConnection con,  String refID) {
		
		String param = "";
		
		try{
			String strSQL = "(SELECT NVL(MAX(A.BUDGETSEQUENCE), 0)+ 1 FROM TRNBUDGET A WHERE A.REFID = ?)";
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ps.setString(1, refID);
			
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


	public String getNextMarkingSequence(DBConnection con, String colName,
			String tableName, String refId) {
		String strSQL = "(SELECT NVL(MAX(A." + colName + "), 0)+ 1 FROM "
				+ tableName + " A WHERE REFID = '" + refId + "')";
		log.debug(strSQL);
		return getStringParam(strSQL, con);
	}
	

	public String getNextMarkingSequence(DBConnection con, String refId) {
		String param = "";
		
		try{
			String strSQL = "(SELECT NVL(MAX(A.MARKINGSEQUENCE), 0)+ 1 FROM TRNMARKING A WHERE REFID = ?)";
			PreparedStatement ps = con.setPrepStatement(strSQL);
			ps.setString(1, refId);
			
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
	public String getStringParamPrepared(PreparedStatement pst, DBConnection con) {
		String param = "";
		try {
			ResultSet rs = pst.executeQuery();
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
					+ " 	(SELECT MARKINGSEQUENCE CHILD, (MARKINGSEQUENCE - 1) PARENT,  (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = MARKINGFROM) MARKINGFROM,"
					+ " 	(SELECT ROLENAME FROM MSTROLE WHERE ROLEID = MARKINGTO) MARKINGTO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = ACTION) ACTION , ACTIONDATE"
					+ "	FROM TRNMARKING A WHERE REFID=? ) X FULL JOIN"
					+ "	(SELECT MARKINGSEQUENCE CHILD, (MARKINGSEQUENCE - 1) PARENT, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = MARKINGFROM) MARKINGFROM,"
					+ "	(SELECT ROLENAME FROM MSTROLE WHERE ROLEID = MARKINGTO) MARKINGTO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = ACTION) ACTION , ACTIONDATE"
					+ "	FROM TRNMARKING A WHERE REFID=? )  Y ON X.CHILD = Y.PARENT";

			log.debug("MarkingTree ** :- " + strSQL);
			ps = dbcon.setPrepStatement(strSQL);
			ps.setString(1, refId);
			ps.setString(2, refId);
			arr = (new CommonDAO().getSQLResultPreparedStmt(ps, 1, dbcon));
		} catch (Exception e) {
			log.fatal(e, e);
		} finally {
			dbcon.closeConnection();
		}
		return arr;
	}

	public ArrayList<CommonBean> getTreeviewReminder(String refId) {
		String strSQL = "";
		PreparedStatement ps = null;
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		DBConnection dbcon = new DBConnection();
		try {
            dbcon.openConnection();
			strSQL = " SELECT 'd1.add('||DECODE(X.CHILD, NULL, (Y.CHILD-1), X.CHILD)||',' ||DECODE(X.PARENT, NULL, (Y.PARENT-1), X.PARENT)||"
					+ "','||'''' ||'<span class=\"treespan\" name=\"treespan\" style=\"color:''+col['||2||']+'';\">' || "
					+ " DECODE(X.REMINDERTO, NULL, (Y.REMINDERFROM), X.REMINDERTO) || '   - ' ||NVL(Y.REMINDERACTION, 'Pending') ||' on ' ||"
					+ " TO_CHAR(DECODE(Y.REMINDERACTIONDATE, NULL, (X.REMINDERACTIONDATE), Y.REMINDERACTIONDATE),'DD/MM/YYYY HH24:MI:SS')  ||''||'</span>' ||''''||','||''''||rownum||''''||');' TREEVIEW"
					+ " FROM "
					+ "     (SELECT REMINDERSEQUENCE CHILD, (REMINDERSEQUENCE - 1) PARENT, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = REMINDERFROM) REMINDERFROM, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = REMINDERTO) REMINDERTO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = REMINDERACTION) REMINDERACTION , REMINDERACTIONDATE    FROM TRNREMINDER A WHERE REFID=? ) X"
					+ "     FULL JOIN "
					+ "     (SELECT REMINDERSEQUENCE CHILD, (REMINDERSEQUENCE - 1) PARENT,  (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = REMINDERFROM) REMINDERFROM, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = REMINDERTO) REMINDERTO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = 4 AND CODE = REMINDERACTION) REMINDERACTION , REMINDERACTIONDATE    FROM TRNREMINDER A WHERE REFID=? )  Y" + " ON X.CHILD = Y.PARENT";

			log.debug("ReminderTree ** :- " + strSQL);
			ps = dbcon.setPrepStatement(strSQL);
			ps.setString(1, refId);
			ps.setString(2, refId);
			arr = (new CommonDAO().getSQLResultPreparedStmt(ps, 1, dbcon));
		} catch (Exception e) {
			log.fatal(e, e);
		} finally {
			dbcon.closeConnection();
		}
		return arr;
	}

	public String saveAttachment(String refID, String name,String type) {
		
		String filepath = "";
		String strSQL = "";
		String nextimageid = "";
		String alllinkrefID = "";
		String strSQL2="";
		DBConnection dbcon = new DBConnection();
		
		try {
			dbcon.openConnection();
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			log.debug("saveFractureImage");
			strSQL = "SELECT (NVL(MAX(ATTACHMENTID),0)+1) FROM TRNATTACHMENT WHERE REFID =? AND TYPE =?";
			ps = dbcon.setPrepStatement(strSQL);
			ps.setString(1, refID);
			ps.setString(2, type);
			log.debug(strSQL);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nextimageid = rs.getString(1);
			}
			filepath = refID +"_"+ nextimageid + name.substring(name.lastIndexOf("."));
			
			////insert into multiple references
			
			strSQL="SELECT REFID, REFNO FROM TRNREFERENCE WHERE LINKREFID in (select LINKREFID FROM TRNREFERENCE WHERE REFID =?)";
			ps = dbcon.setPrepStatement(strSQL);
			ps.setString(1, refID);
			//log.debug(strSQL);
			rs = ps.executeQuery();
			while(rs.next()){
				alllinkrefID = handleNull(rs.getString(1));
				strSQL2 = " INSERT INTO TRNATTACHMENT (REFID, ATTACHMENTID, ORIGINALFILENAME, NEWFILENAME, STATUSFLAG,TYPE)"
						+ " VALUES (?, ?, ?, ?, '1',?)";
				//System.out.println("Strsql :"+strSQL2);
				ps2 = dbcon.setPrepStatement(strSQL2);
				ps2.setString(1,alllinkrefID);
				ps2.setString(2,nextimageid);
				ps2.setString(3,name);
				ps2.setString(4,filepath);
				ps2.setString(5,type);
				
				//log.debug(strSQL2);
				ps2.executeUpdate();
				/*Statement stmt = dbcon.getConnection().createStatement();
				stmt.executeUpdate(strSQL2);*/
				//dbcon.insert(strSQL2);
			}
			if(type.equalsIgnoreCase("F")){
				String strSQL3 = " INSERT INTO TRNATTACHMENT (REFID, ATTACHMENTID, ORIGINALFILENAME, NEWFILENAME, STATUSFLAG,TYPE)"
						+ " VALUES (?, ?, ?, ?, '1',?)";
				//log.debug(strSQL3);
				ps2 = dbcon.setPrepStatement(strSQL3);
				ps2.setString(1,refID);
				ps2.setString(2,nextimageid);
				ps2.setString(3,name);
				ps2.setString(4,filepath);
				ps2.setString(5,type);
				
				ps2.executeUpdate();
			}
			rs.close();

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
		PreparedStatement ps = null;
		try {
			con.openConnection();
			String strSQL = "";
//			strSQL = " DELETE FROM TRNATTACHMENT WHERE REFID = '"+refID+"' AND ATTACHMENTID = '"+attachmentID+ "'";
			strSQL = " UPDATE TRNATTACHMENT SET STATUSFLAG = '0' WHERE REFID =? AND ATTACHMENTID = ? AND TYPE=?";
			
			ps = con.setPrepStatement(strSQL);
			ps.setString(1, refID);
			ps.setString(2, attachmentID);
			ps.setString(3, type);
			
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

	public ArrayList<CommonBean> getReminderData(String refid) {
		DBConnection dbcon = new DBConnection();
		ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
		PreparedStatement ps = null;
		String strSQL = " SELECT REFID, TO_CHAR(REMINDERDATE,'DD/MM/YYYY') REMINDERDATE, REMINDERREMARK, REMINDERACTION, "
				+ " TO_CHAR(REMINDERACTIONDATE,'DD/MM/YYYY') REMINDERACTIONDATE, TO_CHAR(CHANGEDATE,'DD/MM/YYYY') CHANGEDATE, "
				+ " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = 5 AND CODE = REMINDERTYPE) REMINDERTYPE," +
				  " (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=SIGNEDBY) SIGNEDBY, TO_CHAR(SIGNEDON,'DD/MM/YYYY') SIGNEDON, WM_CONCAT(REMINDERTO) AS SENTTO FROM ( "
				+ " SELECT REFID, REMINDERDATE, REMINDERREMARK, REMINDERACTION, REMINDERACTIONDATE, CHANGEDATE, "
				+ " REMINDERTYPE, SIGNEDBY, SIGNEDON, (SELECT ROLENAME FROM MSTROLE WHERE ROLEID = REMINDERTO) REMINDERTO "
				+ " FROM TRNREMINDER WHERE REFID=? ORDER BY REFID ) "
				+ " GROUP BY REFID, REMINDERDATE, REMINDERREMARK, REMINDERACTION, REMINDERACTIONDATE, "
				+ " CHANGEDATE, REMINDERTYPE, SIGNEDBY, SIGNEDON ";
		try{
			dbcon.openConnection();
			ps = dbcon.setPrepStatement(strSQL);
			ps.setString(1, refid);
			arrList = (new CommonDAO()).getSQLResultPreparedStmt(ps,10,dbcon);
			
		}
		catch (Exception e){
			
		}
		finally{
			dbcon.closeConnection();
		}
		log.debug("Remider ** :-- " + strSQL);
		//return (new CommonDAO()).getSQLResult(strSQL, 10);
		return arrList;
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
		PreparedStatement ps = null;
		try {
			dbcon.openConnection();
			// strSQL =
			// " SELECT MARKINGTO, SUBJECTCODE, SUBJECT, TARGETDAYS, TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID IN "
			strSQL = " SELECT MARKINGTO, TARGETDAYS, TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID IN "
					+ " (SELECT REFID FROM TRNREFERENCE WHERE LINKREFID IN (SELECT LINKREFID FROM TRNREFERENCE WHERE REFID = ?)) "
					+ " AND MARKINGFROM = ? AND MARKINGSEQUENCE = (SELECT MIN(MARKINGSEQUENCE) FROM TRNMARKING WHERE REFID IN "
					+ " (SELECT REFID FROM TRNREFERENCE WHERE LINKREFID IN  (SELECT LINKREFID FROM TRNREFERENCE WHERE REFID = ?)) "
					+ " AND MARKINGFROM = ? AND ACTION NOT IN ('NOR', 'RET')) AND ACTION IN ('RCD', 'FOW') "
					+ " ORDER BY REFID, MARKINGSEQUENCE";
			
			ps = dbcon.setPrepStatement(strSQL);
			ps.setString(1, refId);
			ps.setString(2, roleId);
			ps.setString(3, refId);
			ps.setString(4, roleId);
			
			log.debug(strSQL);
			ResultSet rs = ps.executeQuery();
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
						+ " REMINDERTYPE, SIGNEDBY, SIGNEDON) VALUES (?, (SELECT (NVL(MAX(REMINDERSEQUENCE),0)+1) FROM "
						+ " TRNREMINDER WHERE REFID =?),?, ?, SYSDATE, ?,"
						+ " 'FOW', SYSDATE, ?,SYSDATE, ?,TO_DATE(?,'DD/MM/YYYY'), ?, ?, TO_DATE(?,'DD/MM/YYYY'))";
				
				ps = dbcon.setPrepStatement(strSQL);
				ps.setString(1,refId);
				 ps.setString(2,refId);
				 ps.setString(3,reminderFrom);
				 ps.setString(4,REMINDERTO);
				 ps.setString(5,reminderRemark);
				 ps.setString(6,loginid);
				 ps.setString(7,TARGETDAYS);
				 ps.setString(8,TARGETDATE);
				 ps.setString(9,reminderType);
				 ps.setString(10,signedBy);
				 ps.setString(11,signedOn);
				
				
				log.debug(strSQL);
				//Statement stmt = dbcon.getConnection().createStatement();
				updcount = ps.executeUpdate();
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
		DBConnection dbcon = new DBConnection();
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		PreparedStatement ps = null;
		
		String strSQL = "SELECT REFID, ATTACHMENTID, ORIGINALFILENAME, NEWFILENAME,EOFFICEFLAG FROM TRNATTACHMENT WHERE REFID=? AND STATUSFLAG = '1' ORDER BY ATTACHMENTID";
	//	log.debug(strSQL);
		try{
			dbcon.openConnection();
			ps = dbcon.setPrepStatement(strSQL);
			ps.setString(1, refid);
			log.debug(strSQL);
			arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 5, dbcon);
			
		}catch(Exception e){
			log.fatal(e,e);
		} finally{
			
			dbcon.closeConnection();
		}
		
		return arr;
	}
	
	public String sendMail(TrnReference trnRefBean, ServletContext context, String MAILID) {
		String outMessage = "";
		GeneratePDFDAO genPDF = new GeneratePDFDAO();
		DBConnection dbcon = new DBConnection();
		PreparedStatement ps = null;
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
				String mailIdQuery = " SELECT EMAILID FROM MSTVIP WHERE VIPNAME = ?" +
									 " AND STATECODE =?" +
									 " AND VIPSTATUS =?";
				ps = dbcon.setPrepStatement(mailIdQuery);
				ps.setString(1, trnRefBean.getREFERENCENAME());
				ps.setString(2,trnRefBean.getSTATECODE());
				ps.setString(3,trnRefBean.getVIPSTATUS());
				
				log.debug(mailIdQuery);
				mailId = getStringParamPrepared(ps, dbcon);
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
		
		PreparedStatement ps = null;
		

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
				
				String strCheckForEofficeRefNo = "select count(*) from trnreference where eofficerefno = ?";
				
				ps = dbcon.setPrepStatement(strCheckForEofficeRefNo);
				ps.setString(1, recieptdetails.getComputernumber());
				
				String u = getStringParamPrepared(ps, dbcon);
				
			if(Integer.parseInt(u) == 0){
				
				String inStrType = StringFormat.nullString(recieptdetails.getCorredpondenceTypeId().replaceAll("\\s+",""));
				String strType = "select code from MSTGECMAPPING where EOFFICECODE = ? and codetype = '0'";
				String inStrLang = StringFormat.nullString(recieptdetails.getLanguageId().replaceAll("\\s+",""));
				String strLang = "select code from MSTGECMAPPING where EOFFICECODE = ? and codetype = '3'";
				String inStrurgency = StringFormat.nullString(recieptdetails.getClassifiedId().replaceAll("\\s+",""));
				String strurgency = "select code from MSTGECMAPPING where EOFFICECODE = ? and codetype = '1'";
				String inStrrecievedBy = StringFormat.nullString(recieptdetails.getDeliveryModeId().replaceAll("\\s+",""));
				String strrecievedBy = "select code from MSTGECMAPPING where EOFFICECODE = ? and codetype = '9'";
				//String strSubjectCode = "select code from MSTSUBJECTMAPPING where EOFFICESUBJECTCODE = '"+StringFormat.nullString(recieptdetails.getSubjectCategoryId().replaceAll("\\s+",""))+"'";
				//String strRole = "select roleid from MSTROLEMAPPING where EOFFICEROLEID = "+StringFormat.nullString(recieptdetails.getPostDetailId().replaceAll("\\s+",""));
				PreparedStatement psStrType= dbcon.setPrepStatement(strType);
				PreparedStatement psStrurgency=dbcon.setPrepStatement(strurgency);
				PreparedStatement psStrLang=dbcon.setPrepStatement(strLang);
				PreparedStatement psStrrecievedBy=dbcon.setPrepStatement(strrecievedBy);
				
				psStrType.setString(1, inStrType);
				psStrurgency.setString(1, inStrurgency);
				psStrLang.setString(1, inStrLang);
				psStrrecievedBy.setString(1, inStrrecievedBy);
				
				
				log.debug(strType);
				log.debug(strLang);
				//log.debug(strSubjectCode);
				log.debug(strurgency);
				//log.debug(strRole);
				log.debug(strrecievedBy);
				String type = getStringParamPrepared(psStrType, dbcon);
				String urgency = getStringParamPrepared(psStrurgency, dbcon);
				String Lang = getStringParamPrepared(psStrLang, dbcon);
				String recievedBy = getStringParamPrepared(psStrrecievedBy, dbcon);
			
				
				//System.out.println(type + "=====" + Lang);
				
				
				
				
				refBean.setURGENCY(urgency);
			
				
				SimpleDateFormat sdfmt1 = new SimpleDateFormat("yyyy-mm-dd");
				SimpleDateFormat sdfmt2= new SimpleDateFormat("dd/mm/yyyy");
				String strOutput ="";
				if(!(recieptdetails.getLetterDate().length()==0)){
				//String forwardDate ="";
				
					java.util.Date dDate;
					dDate = sdfmt1.parse( recieptdetails.getLetterDate() );
					strOutput = sdfmt2.format( dDate );
				}
				
				String inStrSubjectCode = StringFormat.nullString(recieptdetails.getSubjectCategoryId().replaceAll("\\s+",""));
				String strSubjectCode = "select subjectcode from MSTSUBJECTMAPPING where EOFFICESUBJECTCODE = ?";
				PreparedStatement psStrSubjectCode = dbcon.setPrepStatement(strSubjectCode);
				psStrSubjectCode.setString(1, inStrSubjectCode);
				
				String SubjectCode = getStringParamPrepared(psStrSubjectCode, dbcon);
				
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
						
						String inStrSubjectCode1 = StringFormat.nullString(rd.getSubjectCategoryId().replaceAll("\\s+",""));
						String strSubjectCode1 = "select subjectcode from MSTSUBJECTMAPPING where EOFFICESUBJECTCODE = ?";
						String inStrRole = StringFormat.nullString(rd.getPostDetailId().replaceAll("\\s+",""));
						String strRole = "select roleid from MSTROLEMAPPING where EOFFICEROLEID = ? ";
						
						PreparedStatement psStrSubjectCode1	= dbcon.setPrepStatement(strSubjectCode1);
						PreparedStatement psStrRole = dbcon.setPrepStatement(strRole);
						
						psStrSubjectCode1.setString(1, inStrSubjectCode1);
						psStrRole.setString(1, inStrRole);
						
						
						
						SimpleDateFormat sdfmt1_N = new SimpleDateFormat("yyyy-mm-dd");
						SimpleDateFormat sdfmt2_N= new SimpleDateFormat("dd/mm/yyyy");
						
						String forwardDate_N ="";
						if(!(rd.getSentOn().length()==0)){
						//if(!rd.getSentOn().equalsIgnoreCase("")){
						
							java.util.Date dDate;
							
							dDate = sdfmt1_N.parse( rd.getSentOn() );
							forwardDate_N = sdfmt2_N.format( dDate );
						}
						log.debug(strSubjectCode1);
						log.debug(strRole);
						String SubjectCode1 = getStringParamPrepared(psStrSubjectCode1, dbcon);
						String role = getStringParamPrepared(psStrRole, dbcon);
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
			} catch (Exception  e) {
					e.printStackTrace();
				  }
			conn.disconnect();

		 } 
			
		  }catch (Exception e) {

			e.printStackTrace();

		  } 
	//System.out.println("refbean"+refBean);
	 return refBean;
	}
}