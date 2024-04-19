package in.org.cris.mrsectt.dao;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.rtf.RtfWriter2;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;

public class StaticReportsDAO {
	
	String strSQL = "";
	static Logger log = LogManager.getLogger(StaticReportsDAO.class);

	/*public ArrayList<CommonBean> diaryFromTo(String diarynofrom, String diarynoto,
											String receivedfrom, String datefrom, String dateto, String subjectcode, String markto, String tag, String replyType, String isConf, String roleId,String isReply,String reply) {
		String condSQL = "";
//		String strSQL = " SELECT REFNO, TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE FROM TRNREFERENCE WHERE ROLEID = '" + ROLEID + "'  ";
		//String tempSQL = "";
		String Qreply = "";
		if(isReply.equalsIgnoreCase("1")){
			Qreply=reply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=X.FMID)";
		}
		else{
			Qreply="''";
		}
		
		if (diarynofrom.trim().length() > 0 && diarynoto.trim().length() > 0) {
			String[] arrRefNoFrom = diarynofrom.split("/");
			String[] arrRefNoTo = diarynoto.split("/");
			String refClassFrom = arrRefNoFrom[0];
			String refCountFrom = arrRefNoFrom[1];
			String refCountTo = arrRefNoTo[1];
			condSQL += " AND UPPER(REFCLASS) = UPPER('"+refClassFrom+"') AND REFCOUNT BETWEEN '"+refCountFrom+"' AND '"+refCountTo+"' ";
		}
			condSQL = condSQL + (receivedfrom.length() >0 ? " AND UPPER(X.REFERENCENAME) LIKE ('%"+receivedfrom.toUpperCase()+"%')" : "");		
			condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
					  " TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");
			condSQL = condSQL + (subjectcode.length() >0 ? " AND X.SUBJECTCODE = '"+subjectcode+"'" : "");
			condSQL = condSQL + (replyType.length() >0 ? " AND X.REPLYTYPE = '"+replyType+"'" : "");			
			condSQL = condSQL + (markto.length() >0 ? " AND TRNMARKING.MARKINGTO = '"+markto+"'" : "");		
			condSQL = condSQL + (tag.length() >0 ? " AND (UPPER(X.TAG1) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG2) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG3) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG4) LIKE UPPER('%"+tag+"%'))" : "");		
			condSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			strSQL = " SELECT X.REFNO,X.REFCLASS,TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,X.REFERENCENAME,TO_CHAR(X.LETTERDATE,'DD/MM/YYYY') LETTERDATE," +
					 " X.VIPSTATUS,(SELECT STATENAME FROM MSTSTATE WHERE STATECODE = X.STATECODE) STATECODE,TO_CHAR(X.ACKDATE,'DD/MM/YYYY') ACKDATE," +
					 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.ACKBY)," +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = X.REFCATEGORY)," +
					 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=X.URGENCY)," +
					 " (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.SIGNEDBY)," +
					 " TO_CHAR(X.SIGNEDON,'DD/MM/YYYY') SIGNEDON, X.REMARKS, X.LANGUAGE, " +
					 "	(SELECT SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTCODE = X.SUBJECTCODE AND SUBJECTTYPE = 'R') SUBJECTCODE, X.SUBJECT, " +
					 " GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))  MARKEDTO," +
					 " (SELECT TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1)  MARKINGDATE," +
					 " (SELECT TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1) TARGETDATE," +
					 " X.FILENO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = X.FILESTATUS1) FILESTATUS1, " +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = X.FILESTATUS2) FILESTATUS2, " +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00' AND CODE = X.REPLYTYPE) REPLYTYPE," +
					 " X.REGISTRATIONNO, TO_CHAR(X.REGISTRATIONDATE,'DD/MM/YYYY')," +
					 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.DMARKINGTO) DMARKINGTO," +
					 " TO_CHAR(X.DMARKINGDATE,'DD/MM/YYYY')," +
					 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.IMARKINGTO) IMARKINGTO, X.ISBUDGET,"+Qreply+" REPLY " +
					 " FROM TRNREFERENCE X, TRNMARKING "+
					 " WHERE X.ROLEID = '"+roleId+"' AND X.REFID = TRNMARKING.REFID "+condSQL+" ORDER BY X.REFID";
			
			return (new CommonDAO()).getSQLResult(strSQL,31);
			}*/
	//soumen function
	public ArrayList<CommonBean> diaryFromTo(String diarynofrom, String diarynoto,
			String receivedfrom, String datefrom, String dateto, String subjectcode, String markto, String tag, String replyType, String isConf, String roleId,String isReply,String reply) {
String condSQL = "";
//String strSQL = " SELECT REFNO, TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE FROM TRNREFERENCE WHERE ROLEID = '" + ROLEID + "'  ";
//String tempSQL = "";
String Qreply = "";
if(isReply.equalsIgnoreCase("1")){
Qreply=reply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=X.FMID)";
}
else{
Qreply="''";
}

/*if (diarynofrom.trim().length() > 0 && diarynoto.trim().length() > 0) {
String[] arrRefNoFrom = diarynofrom.split("/");
String[] arrRefNoTo = diarynoto.split("/");
String refClassFrom = arrRefNoFrom[0];
String refCountFrom = arrRefNoFrom[1];
String refCountTo = arrRefNoTo[1];
condSQL += " AND UPPER(REFCLASS) = UPPER('"+refClassFrom+"') AND REFCOUNT BETWEEN '"+refCountFrom+"' AND '"+refCountTo+"' ";
}
condSQL = condSQL + (receivedfrom.length() >0 ? " AND UPPER(X.REFERENCENAME) LIKE ('%"+receivedfrom.toUpperCase()+"%')" : "");		
condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
" TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");
condSQL = condSQL + (subjectcode.length() >0 ? " AND X.SUBJECTCODE = '"+subjectcode+"'" : "");
condSQL = condSQL + (replyType.length() >0 ? " AND X.REPLYTYPE = '"+replyType+"'" : "");			
condSQL = condSQL + (markto.length() >0 ? " AND TRNMARKING.MARKINGTO = '"+markto+"'" : "");		
condSQL = condSQL + (tag.length() >0 ? " AND (UPPER(X.TAG1) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG2) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG3) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG4) LIKE UPPER('%"+tag+"%'))" : "");		
condSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
strSQL = " SELECT X.REFNO,X.REFCLASS,TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,X.REFERENCENAME,TO_CHAR(X.LETTERDATE,'DD/MM/YYYY') LETTERDATE," +
" X.VIPSTATUS,(SELECT STATENAME FROM MSTSTATE WHERE STATECODE = X.STATECODE) STATECODE,TO_CHAR(X.ACKDATE,'DD/MM/YYYY') ACKDATE," +
" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.ACKBY)," +
" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = X.REFCATEGORY)," +
" (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=X.URGENCY)," +
" (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.SIGNEDBY)," +
" TO_CHAR(X.SIGNEDON,'DD/MM/YYYY') SIGNEDON, X.REMARKS, X.LANGUAGE, " +
"	(SELECT SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTCODE = X.SUBJECTCODE AND SUBJECTTYPE = 'R') SUBJECTCODE, X.SUBJECT, " +
" GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))  MARKEDTO," +
" (SELECT TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1)  MARKINGDATE," +
" (SELECT TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1) TARGETDATE," +
" X.FILENO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = X.FILESTATUS1) FILESTATUS1, " +
" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = X.FILESTATUS2) FILESTATUS2, " +
" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00' AND CODE = X.REPLYTYPE) REPLYTYPE," +
" X.REGISTRATIONNO, TO_CHAR(X.REGISTRATIONDATE,'DD/MM/YYYY')," +
" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.DMARKINGTO) DMARKINGTO," +
" TO_CHAR(X.DMARKINGDATE,'DD/MM/YYYY')," +
" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.IMARKINGTO) IMARKINGTO, X.ISBUDGET,"+Qreply+" REPLY " +
" FROM TRNREFERENCE X, TRNMARKING "+
" WHERE X.ROLEID = '"+roleId+"' AND X.REFID = TRNMARKING.REFID "+condSQL+" ORDER BY X.REFID";

//return (new CommonDAO()).getSQLResult(strSQL,31);*/

strSQL = " SELECT X.REFNO,X.REFCLASS,TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,X.REFERENCENAME,TO_CHAR(X.LETTERDATE,'DD/MM/YYYY') LETTERDATE," +
" X.VIPSTATUS,(SELECT STATENAME FROM MSTSTATE WHERE STATECODE = X.STATECODE) STATECODE,TO_CHAR(X.ACKDATE,'DD/MM/YYYY') ACKDATE," +
" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.ACKBY)," +
" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = X.REFCATEGORY)," +
" (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=X.URGENCY)," +
" (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.SIGNEDBY)," +
" TO_CHAR(X.SIGNEDON,'DD/MM/YYYY') SIGNEDON, X.REMARKS, X.LANGUAGE, " +
"	(SELECT SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTCODE = X.SUBJECTCODE AND SUBJECTTYPE = 'R') SUBJECTCODE, X.SUBJECT, " +
" GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))  MARKEDTO," +
" (SELECT TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1)  MARKINGDATE," +
" (SELECT TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1) TARGETDATE," +
" X.FILENO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = X.FILESTATUS1) FILESTATUS1, " +
" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = X.FILESTATUS2) FILESTATUS2, " +
" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00' AND CODE = X.REPLYTYPE) REPLYTYPE," +
" X.REGISTRATIONNO, TO_CHAR(X.REGISTRATIONDATE,'DD/MM/YYYY')," +
" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.DMARKINGTO) DMARKINGTO," +
" TO_CHAR(X.DMARKINGDATE,'DD/MM/YYYY')," +
" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.IMARKINGTO) IMARKINGTO, X.ISBUDGET,"+Qreply+" REPLY, X.LETTERNO LETTERNO, X.EOFFICEREFNO EOFFICEREFNO " +
" FROM TRNREFERENCE X, TRNMARKING "+
" WHERE X.ROLEID = ? AND X.REFID = TRNMARKING.REFID ";


DBConnection con = new DBConnection();
ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
ArrayList<String> params = new ArrayList<String>();

params.add("");
params.add(roleId);


if (diarynofrom.trim().length() > 0 && diarynoto.trim().length() > 0) {
String[] arrRefNoFrom = diarynofrom.split("/");
String[] arrRefNoTo = diarynoto.split("/");
String refClassFrom = arrRefNoFrom[0];
String refCountFrom = arrRefNoFrom[1];
String refCountTo = arrRefNoTo[1];
strSQL += " AND UPPER(REFCLASS) = UPPER(?) AND REFCOUNT BETWEEN ? AND ? ";
params.add(refClassFrom);
params.add(refCountFrom);
params.add(refCountTo);
}

//condSQL = condSQL + (receivedfrom.length() >0 ? " AND UPPER(X.REFERENCENAME) LIKE ('%"+receivedfrom.toUpperCase()+"%')" : "");		
if(receivedfrom.length() >0)
{
strSQL= strSQL+" AND UPPER(X.REFERENCENAME) LIKE (?)";
params.add("%"+receivedfrom.toUpperCase()+"%");

}

/*condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
" TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");*/

if(datefrom.length() > 0 && dateto.length() > 0)
{
strSQL= strSQL+" AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
" TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') ";
params.add(datefrom);
params.add(dateto);
}
//condSQL = condSQL + (subjectcode.length() >0 ? " AND X.SUBJECTCODE = '"+subjectcode+"'" : "");	
if(subjectcode.length() >0)
{
strSQL= strSQL+" AND X.SUBJECTCODE = ?";	
params.add(subjectcode);
}		
//condSQL = condSQL + (replyType.length() >0 ? " AND X.REPLYTYPE = '"+replyType+"'" : "");			
if(replyType.length() >0)
{
strSQL= strSQL+" AND X.REPLYTYPE = ?";	
params.add(replyType);
}
//condSQL = condSQL + (markto.length() >0 ? " AND TRNMARKING.MARKINGTO = '"+markto+"'" : "");		
if(markto.length() >0)
{
strSQL= strSQL+" AND TRNMARKING.MARKINGTO = ?";	
params.add(markto);
}
//condSQL = condSQL + (tag.length() >0 ? " AND (UPPER(X.TAG1) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG2) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG3) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG4) LIKE UPPER('%"+tag+"%'))" : "");		
if(tag.length() >0)
{
strSQL= strSQL+" AND (UPPER(X.TAG1) LIKE UPPER(?) "
+ " OR UPPER(X.TAG2) LIKE UPPER(?) "
+ " OR UPPER(X.TAG3) LIKE UPPER(?) "
+ " OR UPPER(X.TAG4) LIKE UPPER(?) ";	
params.add("%"+tag+"%");
params.add("%"+tag+"%");
params.add("%"+tag+"%");
params.add("%"+tag+"%");

}
//condSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
if(isConf.equalsIgnoreCase("0"))
{
strSQL= strSQL+" AND ISCONF = '0'";	
}else
{
strSQL= strSQL+" AND ISCONF IN ('0','1')";
}
strSQL=strSQL+" ORDER BY X.REFID";

try {
con.openConnection();
PreparedStatement ps = con.setPrepStatement(strSQL);
for(int k=1;k<params.size();k++){
ps.setString(k, params.get(k));
//System.out.println("k :"+k+" params "+params.get(k));

}
arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 33,con);
//System.out.println("diaryFromTo executed");

} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
finally{
con.closeConnection();
}
return arr;

}
	/*public ArrayList<CommonBean> RequestFrom(String receivedfrom, String datefrom, String dateto, String refclass,
			String subjectcode, String markto, String tag, String replyType, String isConf, String roleId,String isReply,String reply) {
		String Qreply = "";	
		if(isReply.equalsIgnoreCase("1")){
			Qreply=reply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=X.FMID)";
		}
		else{
			Qreply="''";
		}
			String condSQL = "";
			condSQL = condSQL + (receivedfrom.length() >0 ? " AND UPPER(X.REFERENCENAME) LIKE ('%"+receivedfrom.toUpperCase()+"%')" : "");		
			condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
					  " TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");
			condSQL = condSQL + (refclass.length() >0 ? " AND X.REFCLASS = '"+refclass+"'" : "");	
			condSQL = condSQL + (subjectcode.length() >0 ? " AND X.SUBJECTCODE = '"+subjectcode+"'" : "");
			condSQL = condSQL + (replyType.length() >0 ? " AND X.REPLYTYPE = '"+replyType+"'" : "");
			condSQL = condSQL + (markto.length() >0 ? " AND TRNMARKING.MARKINGTO = '"+markto+"'" : "");
			condSQL = condSQL + (tag.length() >0 ? " AND (UPPER(X.TAG1) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG2) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG3) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG4) LIKE UPPER('%"+tag+"%'))" : "");
			condSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			//condSQL = condSQL + (refclass.length() >0 ? " AND REFCLASS = '"+markto+"'" : "");		
			strSQL = " SELECT X.REFNO,X.REFCLASS,TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,X.REFERENCENAME,TO_CHAR(X.LETTERDATE,'DD/MM/YYYY') LETTERDATE," +
					 " X.VIPSTATUS,(SELECT STATENAME FROM MSTSTATE WHERE STATECODE = X.STATECODE) STATECODE,TO_CHAR(X.ACKDATE,'DD/MM/YYYY') ACKDATE," +
					 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.ACKBY)," +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = X.REFCATEGORY)," +
					 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=X.URGENCY)," +
					 " (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.SIGNEDBY)," +
					 " TO_CHAR(X.SIGNEDON,'DD/MM/YYYY') SIGNEDON, X.REMARKS, X.LANGUAGE, " +
					 " (SELECT SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTCODE = X.SUBJECTCODE AND SUBJECTTYPE = 'R') SUBJECTCODE, X.SUBJECT, " +
					 " GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))  MARKEDTO," +
					 " (SELECT TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1)  MARKINGDATE," +
					 " (SELECT TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1) TARGETDATE," +
					 " X.FILENO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = X.FILESTATUS1) FILESTATUS1, " +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = X.FILESTATUS2) FILESTATUS2, " +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00' AND CODE = X.REPLYTYPE) REPLYTYPE," +
					 " X.REGISTRATIONNO, TO_CHAR(X.REGISTRATIONDATE,'DD/MM/YYYY')," +
					 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.DMARKINGTO) DMARKINGTO, TO_CHAR(X.DMARKINGDATE,'DD/MM/YYYY')," +
					 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.IMARKINGTO) IMARKINGTO, X.ISBUDGET,"+Qreply+" REPLY " +
					 " FROM TRNREFERENCE X, TRNMARKING "+
					 " WHERE X.REFID = TRNMARKING.REFID AND X.ROLEID = '"+roleId+"' "+condSQL+" ORDER BY X.REFID";
			
			//System.out.println("strSQL:"+strSQL);
			return (new CommonDAO()).getSQLResult(strSQL,31);
			}*/

	
	
	//soumen function
	public ArrayList<CommonBean> RequestFrom(String receivedfrom, String datefrom, String dateto, String refclass,
			String subjectcode, String markto, String tag, String replyType, String isConf, String roleId,String isReply,String reply) {
		String Qreply = "";	
		if(isReply.equalsIgnoreCase("1")){
			Qreply=reply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=X.FMID)";
		}
		else{
			Qreply="''";
		}
			/*String condSQL = "";
			condSQL = condSQL + (receivedfrom.length() >0 ? " AND UPPER(X.REFERENCENAME) LIKE ('%"+receivedfrom.toUpperCase()+"%')" : "");		
			condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
					  " TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");
			condSQL = condSQL + (refclass.length() >0 ? " AND X.REFCLASS = '"+refclass+"'" : "");	
			condSQL = condSQL + (subjectcode.length() >0 ? " AND X.SUBJECTCODE = '"+subjectcode+"'" : "");
			condSQL = condSQL + (replyType.length() >0 ? " AND X.REPLYTYPE = '"+replyType+"'" : "");
			condSQL = condSQL + (markto.length() >0 ? " AND TRNMARKING.MARKINGTO = '"+markto+"'" : "");
			condSQL = condSQL + (tag.length() >0 ? " AND (UPPER(X.TAG1) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG2) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG3) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG4) LIKE UPPER('%"+tag+"%'))" : "");
			condSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			//condSQL = condSQL + (refclass.length() >0 ? " AND REFCLASS = '"+markto+"'" : "");		
			strSQL = " SELECT X.REFNO,X.REFCLASS,TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,X.REFERENCENAME,TO_CHAR(X.LETTERDATE,'DD/MM/YYYY') LETTERDATE," +
					 " X.VIPSTATUS,(SELECT STATENAME FROM MSTSTATE WHERE STATECODE = X.STATECODE) STATECODE,TO_CHAR(X.ACKDATE,'DD/MM/YYYY') ACKDATE," +
					 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.ACKBY)," +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = X.REFCATEGORY)," +
					 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=X.URGENCY)," +
					 " (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.SIGNEDBY)," +
					 " TO_CHAR(X.SIGNEDON,'DD/MM/YYYY') SIGNEDON, X.REMARKS, X.LANGUAGE, " +
					 " (SELECT SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTCODE = X.SUBJECTCODE AND SUBJECTTYPE = 'R') SUBJECTCODE, X.SUBJECT, " +
					 " GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))  MARKEDTO," +
					 " (SELECT TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1)  MARKINGDATE," +
					 " (SELECT TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1) TARGETDATE," +
					 " X.FILENO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = X.FILESTATUS1) FILESTATUS1, " +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = X.FILESTATUS2) FILESTATUS2, " +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00' AND CODE = X.REPLYTYPE) REPLYTYPE," +
					 " X.REGISTRATIONNO, TO_CHAR(X.REGISTRATIONDATE,'DD/MM/YYYY')," +
					 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.DMARKINGTO) DMARKINGTO, TO_CHAR(X.DMARKINGDATE,'DD/MM/YYYY')," +
					 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.IMARKINGTO) IMARKINGTO, X.ISBUDGET,"+Qreply+" REPLY " +
					 " FROM TRNREFERENCE X, TRNMARKING "+
					 " WHERE X.REFID = TRNMARKING.REFID AND X.ROLEID = '"+roleId+"' "+condSQL+" ORDER BY X.REFID";
			
			//System.out.println("strSQL:"+strSQL);
			//return (new CommonDAO()).getSQLResult(strSQL,31);*/
			
	
			strSQL = " SELECT X.REFNO,X.REFCLASS,TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,X.REFERENCENAME,TO_CHAR(X.LETTERDATE,'DD/MM/YYYY') LETTERDATE," +
					 " X.VIPSTATUS,(SELECT STATENAME FROM MSTSTATE WHERE STATECODE = X.STATECODE) STATECODE,TO_CHAR(X.ACKDATE,'DD/MM/YYYY') ACKDATE," +
					 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.ACKBY)," +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = X.REFCATEGORY)," +
					 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=X.URGENCY)," +
					 " (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.SIGNEDBY)," +
					 " TO_CHAR(X.SIGNEDON,'DD/MM/YYYY') SIGNEDON, X.REMARKS, X.LANGUAGE, " +
					 " (SELECT SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTCODE = X.SUBJECTCODE AND SUBJECTTYPE = 'R') SUBJECTCODE, X.SUBJECT, " +
					 " GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))  MARKEDTO," +
					 " (SELECT TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1)  MARKINGDATE," +
					 " (SELECT TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1) TARGETDATE," +
					 " X.FILENO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = X.FILESTATUS1) FILESTATUS1, " +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = X.FILESTATUS2) FILESTATUS2, " +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00' AND CODE = X.REPLYTYPE) REPLYTYPE," +
					 " X.REGISTRATIONNO, TO_CHAR(X.REGISTRATIONDATE,'DD/MM/YYYY')," +
					 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.DMARKINGTO) DMARKINGTO, TO_CHAR(X.DMARKINGDATE,'DD/MM/YYYY')," +
					 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.IMARKINGTO) IMARKINGTO, X.ISBUDGET,"+Qreply+" REPLY, X.LETTERNO LETTERNO, X.EOFFICEREFNO EOFFICEREFNO " +
					 " FROM TRNREFERENCE X, TRNMARKING "+
					 " WHERE X.REFID = TRNMARKING.REFID AND X.ROLEID = ?";
			
			DBConnection con = new DBConnection();
			ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
			ArrayList<String> params = new ArrayList<String>();
			
			params.add("");
		    params.add(roleId);
		    
		    //condSQL = condSQL + (receivedfrom.length() >0 ? " AND UPPER(X.REFERENCENAME) LIKE ('%"+receivedfrom.toUpperCase()+"%')" : "");		
		    if(receivedfrom.length() >0)
			{
				 strSQL= strSQL+" AND UPPER(X.REFERENCENAME) LIKE (?)";
				 params.add("%"+receivedfrom.toUpperCase()+"%");
	 
			}
		    /*condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
					  " TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");*/
		    if(datefrom.length() > 0 && dateto.length() > 0)
			{
				strSQL= strSQL+" AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
						  " TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') ";
				params.add(datefrom);
				params.add(dateto);
			}
		    //condSQL = condSQL + (refclass.length() >0 ? " AND X.REFCLASS = '"+refclass+"'" : "");	
		    if(refclass.length() >0)
			{
				strSQL= strSQL+" AND X.REFCLASS = ?";	
				params.add(refclass);
			}		
		    //condSQL = condSQL + (subjectcode.length() >0 ? " AND X.SUBJECTCODE = '"+subjectcode+"'" : "");
			if(subjectcode.length() >0)
			{
				strSQL= strSQL+" AND X.SUBJECTCODE = ?";	
				params.add(subjectcode);
			}		
			//condSQL = condSQL + (replyType.length() >0 ? " AND X.REPLYTYPE = '"+replyType+"'" : "");
			if(replyType.length() >0)
			{
				strSQL= strSQL+" AND X.REPLYTYPE = ?";	
				params.add(replyType);
			}
			//condSQL = condSQL + (markto.length() >0 ? " AND TRNMARKING.MARKINGTO = '"+markto+"'" : "");
			if(markto.length() >0)
			{
				strSQL= strSQL+" AND TRNMARKING.MARKINGTO = ?";	
				params.add(markto);
			}
			//condSQL = condSQL + (tag.length() >0 ? " AND (UPPER(X.TAG1) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG2) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG3) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG4) LIKE UPPER('%"+tag+"%'))" : "");
			if(tag.length() >0)
			{
				strSQL= strSQL+" AND (UPPER(X.TAG1) LIKE UPPER(?) "
						+ " OR UPPER(X.TAG2) LIKE UPPER(?) "
						+ " OR UPPER(X.TAG3) LIKE UPPER(?) "
						+ " OR UPPER(X.TAG4) LIKE UPPER(?) ";	
				params.add("%"+tag+"%");
				params.add("%"+tag+"%");
				params.add("%"+tag+"%");
				params.add("%"+tag+"%");
				
			}
			//condSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			if(isConf.equalsIgnoreCase("0"))
			{
				strSQL= strSQL+" AND ISCONF = '0'";	
			}else
			{
				strSQL= strSQL+" AND ISCONF IN ('0','1')";
			}
			strSQL=strSQL+" ORDER BY X.REFID";
			
			try {
				con.openConnection();
				PreparedStatement ps = con.setPrepStatement(strSQL);
				for(int k=1;k<params.size();k++){
		    		ps.setString(k, params.get(k));
		    		//System.out.println("k :"+k+" params "+params.get(k));
		    		
		    	}
				arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 33,con);
				//System.out.println("RequestFrom executed");
				
		       } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		      }
		      finally{
				con.closeConnection();
		     }
	return arr;
			
	
	}
	/*public ArrayList<CommonBean> SearchFor(String searchfor, String receivedfrom, String datefrom, String dateto, String refclass,
											String subjectcode, String markto, String tag, String replyType, String isConf, String roleId,String isReply,String reply) {
		String Qreply = "";	
		if(isReply.equalsIgnoreCase("1")){
			Qreply=reply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=X.FMID)";
		}
		else{
			Qreply="''";
		}
			String condSQL = "";
			condSQL = condSQL + (receivedfrom.length() >0 ? " AND UPPER(X.REFERENCENAME) LIKE ('%"+receivedfrom.toUpperCase()+"%')" : "");		
			condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
					  " TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");
			condSQL = condSQL + (refclass.length() >0 ? " AND X.REFCLASS = '"+refclass+"'" : "");	
			condSQL = condSQL + (subjectcode.length() >0 ? " AND X.SUBJECTCODE = '"+subjectcode+"'" : "");	
			condSQL = condSQL + (replyType.length() >0 ? " AND X.REPLYTYPE = '"+replyType+"'" : "");	
			condSQL = condSQL + (markto.length() >0 ? " AND TRNMARKING.MARKINGTO = '"+markto+"'" : "");
			condSQL = condSQL + (searchfor.length() >0 ? " AND (UPPER(X.SUBJECT) LIKE '%"+searchfor.toUpperCase()+"%' OR UPPER(X.REMARKS) LIKE '%"+searchfor.toUpperCase()+"%' OR UPPER(X.REFERENCENAME) LIKE '%"+searchfor.toUpperCase()+"%' OR UPPER(X.VIPSTATUS) LIKE '%"+searchfor.toUpperCase()+"%') " : "");
			condSQL = condSQL + (tag.length() >0 ? " AND (UPPER(X.TAG1) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG2) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG3) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG4) LIKE UPPER('%"+tag+"%'))" : "");
			condSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			strSQL = " SELECT REFNO,REFCLASS,TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'), REFERENCENAME, TO_CHAR(LETTERDATE,'DD/MM/YYYY')," +
					 " VIPSTATUS,STATECODE,TO_CHAR(ACKDATE,'DD/MM/YYYY'),(SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = ACKBY), " +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = REFCATEGORY), " +
					 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=URGENCY), " +
					 " (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = SIGNEDBY), " +
					 " TO_CHAR(SIGNEDON,'DD/MM/YYYY'),REMARKS,LANGUAGE FROM TRNREFERENCE "+
					 " WHERE 1=1 "+condSQL+" ORDER BY REFID";
			strSQL = " SELECT X.REFNO,X.REFCLASS,TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,X.REFERENCENAME,TO_CHAR(X.LETTERDATE,'DD/MM/YYYY') LETTERDATE," +
					 " X.VIPSTATUS,(SELECT STATENAME FROM MSTSTATE WHERE STATECODE = X.STATECODE) STATECODE, TO_CHAR(X.ACKDATE,'DD/MM/YYYY') ACKDATE," +
					 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.ACKBY)," +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = X.REFCATEGORY)," +
					 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=X.URGENCY)," +
					 " (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.SIGNEDBY)," +
					 " TO_CHAR(X.SIGNEDON,'DD/MM/YYYY') SIGNEDON, X.REMARKS, X.LANGUAGE," +
					 " (SELECT SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTCODE = X.SUBJECTCODE AND SUBJECTTYPE = 'R') SUBJECTCODE, X.SUBJECT, " +
					 " GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))  MARKEDTO," +
					 " (SELECT TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1)  MARKINGDATE," +
					 " (SELECT TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1) TARGETDATE," +
					 " X.FILENO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = X.FILESTATUS1) FILESTATUS1, " +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = X.FILESTATUS2) FILESTATUS2, " +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00' AND CODE = X.REPLYTYPE) REPLYTYPE," +
					 " X.REGISTRATIONNO, TO_CHAR(X.REGISTRATIONDATE,'DD/MM/YYYY')," +
					 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.DMARKINGTO) DMARKINGTO, TO_CHAR(X.DMARKINGDATE,'DD/MM/YYYY')," +
					 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.IMARKINGTO) IMARKINGTO, X.ISBUDGET,"+Qreply+" REPLY " +
					 " FROM TRNREFERENCE X, TRNMARKING "+
					 " WHERE X.ROLEID = '"+roleId+"' AND X.REFID = TRNMARKING.REFID "+condSQL+" ORDER BY X.REFID";
			return (new CommonDAO()).getSQLResult(strSQL,31);
			}
	*/
	
	
	///soumen function 
	public ArrayList<CommonBean> SearchFor(String searchfor, String receivedfrom, String datefrom, String dateto, String refclass,
			String subjectcode, String markto, String tag, String replyType, String isConf, String roleId,String isReply,String reply) {
String Qreply = "";	
if(isReply.equalsIgnoreCase("1")){
Qreply=reply.equalsIgnoreCase("0")? "''": "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=X.FMID)";
}
else{
Qreply="''";
}
/*String condSQL = "";
condSQL = condSQL + (receivedfrom.length() >0 ? " AND UPPER(X.REFERENCENAME) LIKE ('%"+receivedfrom.toUpperCase()+"%')" : "");		
condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
" TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");
condSQL = condSQL + (refclass.length() >0 ? " AND X.REFCLASS = '"+refclass+"'" : "");	
condSQL = condSQL + (subjectcode.length() >0 ? " AND X.SUBJECTCODE = '"+subjectcode+"'" : "");	
condSQL = condSQL + (replyType.length() >0 ? " AND X.REPLYTYPE = '"+replyType+"'" : "");	
condSQL = condSQL + (markto.length() >0 ? " AND TRNMARKING.MARKINGTO = '"+markto+"'" : "");
condSQL = condSQL + (searchfor.length() >0 ? " AND (UPPER(X.SUBJECT) LIKE '%"+searchfor.toUpperCase()+"%' OR UPPER(X.REMARKS) LIKE '%"+searchfor.toUpperCase()+"%' OR UPPER(X.REFERENCENAME) LIKE '%"+searchfor.toUpperCase()+"%' OR UPPER(X.VIPSTATUS) LIKE '%"+searchfor.toUpperCase()+"%') " : "");
condSQL = condSQL + (tag.length() >0 ? " AND (UPPER(X.TAG1) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG2) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG3) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG4) LIKE UPPER('%"+tag+"%'))" : "");
condSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";*/
/*strSQL = " SELECT REFNO,REFCLASS,TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'), REFERENCENAME, TO_CHAR(LETTERDATE,'DD/MM/YYYY')," +
" VIPSTATUS,STATECODE,TO_CHAR(ACKDATE,'DD/MM/YYYY'),(SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = ACKBY), " +
" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = REFCATEGORY), " +
" (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=URGENCY), " +
" (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = SIGNEDBY), " +
" TO_CHAR(SIGNEDON,'DD/MM/YYYY'),REMARKS,LANGUAGE FROM TRNREFERENCE "+
" WHERE 1=1 "+condSQL+" ORDER BY REFID";********/
/*strSQL = " SELECT X.REFNO,X.REFCLASS,TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,X.REFERENCENAME,TO_CHAR(X.LETTERDATE,'DD/MM/YYYY') LETTERDATE," +
" X.VIPSTATUS,(SELECT STATENAME FROM MSTSTATE WHERE STATECODE = X.STATECODE) STATECODE, TO_CHAR(X.ACKDATE,'DD/MM/YYYY') ACKDATE," +
" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.ACKBY)," +
" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = X.REFCATEGORY)," +
" (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=X.URGENCY)," +
" (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.SIGNEDBY)," +
" TO_CHAR(X.SIGNEDON,'DD/MM/YYYY') SIGNEDON, X.REMARKS, X.LANGUAGE," +
" (SELECT SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTCODE = X.SUBJECTCODE AND SUBJECTTYPE = 'R') SUBJECTCODE, X.SUBJECT, " +
" GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))  MARKEDTO," +
" (SELECT TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1)  MARKINGDATE," +
" (SELECT TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1) TARGETDATE," +
" X.FILENO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = X.FILESTATUS1) FILESTATUS1, " +
" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = X.FILESTATUS2) FILESTATUS2, " +
" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00' AND CODE = X.REPLYTYPE) REPLYTYPE," +
" X.REGISTRATIONNO, TO_CHAR(X.REGISTRATIONDATE,'DD/MM/YYYY')," +
" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.DMARKINGTO) DMARKINGTO, TO_CHAR(X.DMARKINGDATE,'DD/MM/YYYY')," +
" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.IMARKINGTO) IMARKINGTO, X.ISBUDGET,"+Qreply+" REPLY " +
" FROM TRNREFERENCE X, TRNMARKING "+
" WHERE X.ROLEID = '"+roleId+"' AND X.REFID = TRNMARKING.REFID "+condSQL+" ORDER BY X.REFID";
//return (new CommonDAO()).getSQLResult(strSQL,31);*/

strSQL = " SELECT X.REFNO,X.REFCLASS,TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,X.REFERENCENAME,TO_CHAR(X.LETTERDATE,'DD/MM/YYYY') LETTERDATE," +
" X.VIPSTATUS,(SELECT STATENAME FROM MSTSTATE WHERE STATECODE = X.STATECODE) STATECODE, TO_CHAR(X.ACKDATE,'DD/MM/YYYY') ACKDATE," +
" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.ACKBY)," +
" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = X.REFCATEGORY)," +
" (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=X.URGENCY)," +
" (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.SIGNEDBY)," +
" TO_CHAR(X.SIGNEDON,'DD/MM/YYYY') SIGNEDON, X.REMARKS, X.LANGUAGE," +
" (SELECT SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTCODE = X.SUBJECTCODE AND SUBJECTTYPE = 'R') SUBJECTCODE, X.SUBJECT, " +
" GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))  MARKEDTO," +
" (SELECT TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1)  MARKINGDATE," +
" (SELECT TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1) TARGETDATE," +
" X.FILENO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = X.FILESTATUS1) FILESTATUS1, " +
" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = X.FILESTATUS2) FILESTATUS2, " +
" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00' AND CODE = X.REPLYTYPE) REPLYTYPE," +
" X.REGISTRATIONNO, TO_CHAR(X.REGISTRATIONDATE,'DD/MM/YYYY')," +
" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.DMARKINGTO) DMARKINGTO, TO_CHAR(X.DMARKINGDATE,'DD/MM/YYYY')," +
" (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.IMARKINGTO) IMARKINGTO, X.ISBUDGET,"+Qreply+" REPLY, X.LETTERNO LETTERNO, X.EOFFICEREFNO EOFFICEREFNO " +
" FROM TRNREFERENCE X, TRNMARKING "+
" WHERE X.ROLEID = ? AND X.REFID = TRNMARKING.REFID ";


DBConnection con = new DBConnection();
ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
ArrayList<String> params = new ArrayList<String>();

params.add("");
params.add(roleId);

//condSQL = condSQL + (receivedfrom.length() >0 ? " AND UPPER(X.REFERENCENAME) LIKE ('%"+receivedfrom.toUpperCase()+"%')" : "");		
if(receivedfrom.length() >0)
{
strSQL= strSQL+" AND UPPER(X.REFERENCENAME) LIKE (?)";
params.add("%"+receivedfrom.toUpperCase()+"%");

}

/*condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
" TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");*/

if(datefrom.length() > 0 && dateto.length() > 0)
{
strSQL= strSQL+" AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
" TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') ";
params.add(datefrom);
params.add(dateto);
}

//condSQL = condSQL + (refclass.length() >0 ? " AND X.REFCLASS = '"+refclass+"'" : "");	
if(refclass.length() >0)
{
strSQL= strSQL+" AND X.REFCLASS = ?";	
params.add(refclass);
}		

//condSQL = condSQL + (subjectcode.length() >0 ? " AND X.SUBJECTCODE = '"+subjectcode+"'" : "");	
if(subjectcode.length() >0)
{
strSQL= strSQL+" AND X.SUBJECTCODE = ?";	
params.add(subjectcode);
}		
//condSQL = condSQL + (replyType.length() >0 ? " AND X.REPLYTYPE = '"+replyType+"'" : "");	
if(replyType.length() >0)
{
strSQL= strSQL+" AND X.REPLYTYPE = ?";	
params.add(replyType);
}
//condSQL = condSQL + (markto.length() >0 ? " AND TRNMARKING.MARKINGTO = '"+markto+"'" : "");
if(markto.length() >0)
{
strSQL= strSQL+" AND TRNMARKING.MARKINGTO = ?";	
params.add(markto);
}
//condSQL = condSQL + (searchfor.length() >0 ? " AND (UPPER(X.SUBJECT) LIKE '%"+searchfor.toUpperCase()+"%' OR UPPER(X.REMARKS) LIKE '%"+searchfor.toUpperCase()+"%' OR UPPER(X.REFERENCENAME) LIKE '%"+searchfor.toUpperCase()+"%' OR UPPER(X.VIPSTATUS) LIKE '%"+searchfor.toUpperCase()+"%') " : "");
if(searchfor.length() >0)
{
strSQL= strSQL+" AND (UPPER(X.SUBJECT) LIKE ? "
+ " OR UPPER(X.REMARKS) LIKE ? "
+ " OR UPPER(X.REFERENCENAME) LIKE ? "
+ " OR UPPER(X.VIPSTATUS) LIKE ?) ";	
params.add("%"+searchfor.toUpperCase()+"%");
params.add("%"+searchfor.toUpperCase()+"%");
params.add("%"+searchfor.toUpperCase()+"%");
params.add("%"+searchfor.toUpperCase()+"%");

}			
//condSQL = condSQL + (tag.length() >0 ? " AND (UPPER(X.TAG1) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG2) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG3) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG4) LIKE UPPER('%"+tag+"%'))" : "");
if(tag.length() >0)
{
strSQL= strSQL+" AND (UPPER(X.TAG1) LIKE UPPER(?) "
+ " OR UPPER(X.TAG2) LIKE UPPER(?) "
+ " OR UPPER(X.TAG3) LIKE UPPER(?) "
+ " OR UPPER(X.TAG4) LIKE UPPER(?) ";	
params.add("%"+tag+"%");
params.add("%"+tag+"%");
params.add("%"+tag+"%");
params.add("%"+tag+"%");

}
//condSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
if(isConf.equalsIgnoreCase("0"))
{
strSQL= strSQL+" AND ISCONF = '0'";	
}else
{
strSQL= strSQL+" AND ISCONF IN ('0','1')";
}
strSQL=strSQL+" ORDER BY X.REFID";

try {
con.openConnection();
PreparedStatement ps = con.setPrepStatement(strSQL);
for(int k=1;k<params.size();k++){
ps.setString(k, params.get(k));
//System.out.println("k :"+k+" params "+params.get(k));

}
arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 33,con);
//System.out.println("SearchFor executed");

} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
finally{
con.closeConnection();
}
return arr;

}
	
	/*public ArrayList<CommonBean> FormattedReport(String searchfor, String receivedfrom, String datefrom, String dateto, String refclass,
			String subjectcode, String markto, String tag, String roleId) {
			String condSQL = "";
			condSQL = condSQL + (receivedfrom.length() >0 ? " AND UPPER(REFERENCENAME) LIKE ('%"+receivedfrom.toUpperCase()+"%')" : "");		
			condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
					  " TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");
			condSQL = condSQL + (refclass.length() >0 ? " AND REFCLASS = '"+refclass+"'" : "");	
			condSQL = condSQL + (subjectcode.length() >0 ? " AND SUBJECTCODE = '"+subjectcode+"'" : "");		
			condSQL = condSQL + (searchfor.length() >0 ? " AND (UPPER(SUBJECT) LIKE '%"+searchfor.toUpperCase()+"%' OR UPPER(REMARKS) LIKE '%"+searchfor.toUpperCase()+"%') " : "");
			condSQL = condSQL + (tag.length() >0 ? " AND (UPPER(X.TAG1) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG2) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG3) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG4) LIKE UPPER('%"+tag+"%'))" : "");
			strSQL = " SELECT REFERENCENAME, VIPSTATUS, (SELECT X.STATENAME FROM MSTSTATE X WHERE X.STATECODE = A.STATECODE) STATE, "+
					 " SUBJECT, REMARKS, REFNO, TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'), 'Dtd:'||TO_CHAR(LETTERDATE,'DD/MM') LETTERDATE, " +
					 " 'Ack:'||(SELECT P.ROLENAME FROM MSTROLE P WHERE P.ROLEID= A.ACKBY) ACKBY, TO_CHAR(ACKDATE,'DD/MM'), " +
					 " (SELECT P.ROLENAME FROM MSTROLE P WHERE P.ROLEID = (SELECT Y.MARKINGTO FROM TRNMARKING Y WHERE Y.REFID = A.REFID AND Y.MARKINGSEQUENCE = '1')) MARKTO, " +
					 " (SELECT TO_CHAR(Y.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING Y WHERE Y.REFID = A.REFID AND Y.MARKINGSEQUENCE = '1') MARKDATE, " +
					 " FILENO, REGISTRATIONNO, IMARKINGTO,DECODE((FILESTATUS1 || FILESTATUS2), '',  STATUSREMARK," +
					 " ((SELECT SNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE <> '00' AND CODE = FILESTATUS1) ||' ,' " +
					 " ||(SELECT SNAME  FROM MSTGEC WHERE CODETYPE = '7' AND CODE <> '00' AND CODE = FILESTATUS2))), TO_CHAR(REGISTRATIONDATE,'DD/MM')," +
					 " (SELECT TO_CHAR(TARGETDATE,'DD/MM') FROM TRNMARKING X WHERE X.REFID = A.REFID AND X.MARKINGSEQUENCE = '1') " +
					 " FROM TRNREFERENCE A WHERE A.ROLEID = '"+roleId+"' "+condSQL+" ORDER BY REFID ";
			return (new CommonDAO()).getSQLResult(strSQL,18);
			}*/
	
	
	//soumen function
	public ArrayList<CommonBean> FormattedReport(String searchfor, String receivedfrom, String datefrom, String dateto, String refclass,
			String subjectcode, String markto, String tag, String roleId) {
			/*String condSQL = "";
			condSQL = condSQL + (receivedfrom.length() >0 ? " AND UPPER(REFERENCENAME) LIKE ('%"+receivedfrom.toUpperCase()+"%')" : "");		
			condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
					  " TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");
			condSQL = condSQL + (refclass.length() >0 ? " AND REFCLASS = '"+refclass+"'" : "");	
			condSQL = condSQL + (subjectcode.length() >0 ? " AND SUBJECTCODE = '"+subjectcode+"'" : "");		
			condSQL = condSQL + (searchfor.length() >0 ? " AND (UPPER(SUBJECT) LIKE '%"+searchfor.toUpperCase()+"%' OR UPPER(REMARKS) LIKE '%"+searchfor.toUpperCase()+"%') " : "");
			condSQL = condSQL + (tag.length() >0 ? " AND (UPPER(X.TAG1) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG2) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG3) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG4) LIKE UPPER('%"+tag+"%'))" : "");
			strSQL = " SELECT REFERENCENAME, VIPSTATUS, (SELECT X.STATENAME FROM MSTSTATE X WHERE X.STATECODE = A.STATECODE) STATE, "+
					 " SUBJECT, REMARKS, REFNO, TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'), 'Dtd:'||TO_CHAR(LETTERDATE,'DD/MM') LETTERDATE, " +
					 " 'Ack:'||(SELECT P.ROLENAME FROM MSTROLE P WHERE P.ROLEID= A.ACKBY) ACKBY, TO_CHAR(ACKDATE,'DD/MM'), " +
					 " (SELECT P.ROLENAME FROM MSTROLE P WHERE P.ROLEID = (SELECT Y.MARKINGTO FROM TRNMARKING Y WHERE Y.REFID = A.REFID AND Y.MARKINGSEQUENCE = '1')) MARKTO, " +
					 " (SELECT TO_CHAR(Y.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING Y WHERE Y.REFID = A.REFID AND Y.MARKINGSEQUENCE = '1') MARKDATE, " +
					 " FILENO, REGISTRATIONNO, IMARKINGTO,DECODE((FILESTATUS1 || FILESTATUS2), '',  STATUSREMARK," +
					 " ((SELECT SNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE <> '00' AND CODE = FILESTATUS1) ||' ,' " +
					 " ||(SELECT SNAME  FROM MSTGEC WHERE CODETYPE = '7' AND CODE <> '00' AND CODE = FILESTATUS2))), TO_CHAR(REGISTRATIONDATE,'DD/MM')," +
					 " (SELECT TO_CHAR(TARGETDATE,'DD/MM') FROM TRNMARKING X WHERE X.REFID = A.REFID AND X.MARKINGSEQUENCE = '1') " +
					 " FROM TRNREFERENCE A WHERE A.ROLEID = '"+roleId+"' "+condSQL+" ORDER BY REFID ";
			//return (new CommonDAO()).getSQLResult(strSQL,18);*/
			
			
			strSQL = " SELECT REFERENCENAME, VIPSTATUS, (SELECT X.STATENAME FROM MSTSTATE X WHERE X.STATECODE = A.STATECODE) STATE, "+
					 " SUBJECT, REMARKS, REFNO, TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'), 'Dtd:'||TO_CHAR(LETTERDATE,'DD/MM') LETTERDATE, " +
					 " 'Ack:'||(SELECT P.ROLENAME FROM MSTROLE P WHERE P.ROLEID= A.ACKBY) ACKBY, TO_CHAR(ACKDATE,'DD/MM'), " +
					 " (SELECT P.ROLENAME FROM MSTROLE P WHERE P.ROLEID = (SELECT Y.MARKINGTO FROM TRNMARKING Y WHERE Y.REFID = A.REFID AND Y.MARKINGSEQUENCE = '1')) MARKTO, " +
					 " (SELECT TO_CHAR(Y.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING Y WHERE Y.REFID = A.REFID AND Y.MARKINGSEQUENCE = '1') MARKDATE, " +
					 " FILENO, REGISTRATIONNO, IMARKINGTO,DECODE((FILESTATUS1 || FILESTATUS2), '',  STATUSREMARK," +
					 " ((SELECT SNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE <> '00' AND CODE = FILESTATUS1) ||' ,' " +
					 " ||(SELECT SNAME  FROM MSTGEC WHERE CODETYPE = '7' AND CODE <> '00' AND CODE = FILESTATUS2))), TO_CHAR(REGISTRATIONDATE,'DD/MM')," +
					 " (SELECT TO_CHAR(TARGETDATE,'DD/MM') FROM TRNMARKING X WHERE X.REFID = A.REFID AND X.MARKINGSEQUENCE = '1') " +
					 " FROM TRNREFERENCE A WHERE A.ROLEID = ? ";
			
			DBConnection con = new DBConnection();
			ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
			ArrayList<String> params = new ArrayList<String>();
			
			params.add("");
		    params.add(roleId);
		    
		    //condSQL = condSQL + (receivedfrom.length() >0 ? " AND UPPER(REFERENCENAME) LIKE ('%"+receivedfrom.toUpperCase()+"%')" : "");		
			if(receivedfrom.length() >0)
			{
				 strSQL= strSQL+" AND UPPER(REFERENCENAME) LIKE (?)";
				 params.add("%"+receivedfrom.toUpperCase()+"%");
	 
			}
			
			/*condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
					  " TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");*/
		    if(datefrom.length() > 0 && dateto.length() > 0)
			{
				strSQL= strSQL+" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
						  " TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') ";
				params.add(datefrom);
				params.add(dateto);
			}
			
		    //condSQL = condSQL + (refclass.length() >0 ? " AND REFCLASS = '"+refclass+"'" : "");
			if(refclass.length() >0)
			{
				strSQL= strSQL+" AND REFCLASS = ?";	
				params.add(refclass);
			}		
		    
			//condSQL = condSQL + (subjectcode.length() >0 ? " AND SUBJECTCODE = '"+subjectcode+"'" : "");		
			if(subjectcode.length() >0)
			{
				strSQL= strSQL+" AND SUBJECTCODE = ?";	
				params.add(subjectcode);
			}		
			//condSQL = condSQL + (searchfor.length() >0 ? " AND (UPPER(SUBJECT) LIKE '%"+searchfor.toUpperCase()+"%' OR UPPER(REMARKS) LIKE '%"+searchfor.toUpperCase()+"%') " : "");
			if(searchfor.length() >0)
			{
				strSQL= strSQL+" AND (UPPER(SUBJECT) LIKE ? "
						+ " OR UPPER(REMARKS) LIKE ? ";
				params.add("%"+searchfor.toUpperCase()+"%");
				params.add("%"+searchfor.toUpperCase()+"%");
					
			}
		    //condSQL = condSQL + (tag.length() >0 ? " AND (UPPER(X.TAG1) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG2) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG3) LIKE UPPER('%"+tag+"%') OR UPPER(X.TAG4) LIKE UPPER('%"+tag+"%'))" : "");
			if(tag.length() >0)
			{
				strSQL= strSQL+" AND (UPPER(X.TAG1) LIKE UPPER(?) "
						+ " OR UPPER(X.TAG2) LIKE UPPER(?) "
						+ " OR UPPER(X.TAG3) LIKE UPPER(?) "
						+ " OR UPPER(X.TAG4) LIKE UPPER(?) ";	
				params.add("%"+tag+"%");
				params.add("%"+tag+"%");
				params.add("%"+tag+"%");
				params.add("%"+tag+"%");
				
			}
			
            strSQL=strSQL+" ORDER BY REFID ";
			
			try {
				con.openConnection();
				PreparedStatement ps = con.setPrepStatement(strSQL);
				for(int k=1;k<params.size();k++){
		    		ps.setString(k, params.get(k));
		    		//System.out.println("k :"+k+" params "+params.get(k));
		    		
		    	}
				arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 18,con);
				log.debug("FormattedReport executed");
				
		       } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		      }
		      finally{
				con.closeConnection();
		     }
	return arr;
			
	}
	
public String exportToDOCEnglish(HttpServletRequest request, String diarynofrom, String diarynoto,String fileName) {
	
	String dir=("/opt/MRSECTT/RFMSACKDOCS/");
	new File(dir).mkdirs();
	String strPath = dir+fileName;
	
	try{
		
		Document document=new Document(PageSize.A4,54,36,36,36);
	
		ArrayList<CommonBean> detailarr = acknowledgementfromto(diarynofrom,diarynoto);
		
		RtfWriter2.getInstance(document, new FileOutputStream(strPath));
		
		document.open();

		FontFactory.register(request.getRealPath("/Fonts/arial.ttf"));
		FontFactory.register(request.getRealPath("/Fonts/mangal.ttf"));
		Font fontLabel=FontFactory.getFont("Arial", 10,Font.BOLD,new Color(0, 0, 0));
		Font fontText=FontFactory.getFont("Arial", 10,Font.NORMAL,new Color(0, 0, 0));
		
		
		String serverDate = CommonDAO.getSysDate("dd/MM/yyyy");
		
		for(int i=0;i<detailarr.size();i++)
		{
		CommonBean bean =(CommonBean) detailarr.get(i);
		String[] sal1 = {"",""};
		
		String salute ="";
		//System.out.println("=======Address======="+bean.getField34());
		if(bean.getField33().contains(" ")){
			sal1 = bean.getField33().split(" ");
			}
		
		String otherCond = (!(bean.getField31().equals(bean.getField32())))?"and others ":"";
		
		document.add(new Phrase("\n\n\n\n\n\n\n\n\n\n"+bean.getField1()+"    		                                                                   " +serverDate,fontText));
		document.add(new Phrase("\n\n\n\n\n\n "+bean.getField33()+" "+bean.getField4()+" ji,",fontLabel));
		Paragraph p2=new Paragraph(new Phrase("\n\n\n\n I am in receipt of your letter dated "+bean.getField5() +" regarding "+ bean.getField17()+" "+otherCond+".",fontText));
		p2.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p2);
		document.add(new Phrase("\n\n\n            I am having the matter looked into.",fontText));
		document.add(new Phrase("\n\n            With regards,",fontText));
		document.add(new Phrase("\n\n                                                                                                Yours sincerely,",fontText));
		document.add(new Phrase("\n\n\n\n                                                                                      ( Suresh Prabhu )",fontLabel));
		
		document.add(new Phrase("\n\n\n "+sal1[1] + " "+bean.getField4(),fontLabel));
		document.add(new Phrase("\n  "+bean.getField35(),fontText));
		document.add(new Phrase("\n  "+bean.getField34(),fontText));
		document.newPage();
		}
	
		document.close();
		
	}catch(Exception e){
		log.warn(e.getMessage());
		e.printStackTrace();
	}
	finally{
		
	}

	return strPath;
	
}

/*public ArrayList<CommonBean> acknowledgementfromto(String diarynofrom, String diarynoto)
{
	String condSQL = "";
//	String strSQL = " SELECT REFNO, TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE FROM TRNREFERENCE WHERE ROLEID = '" + ROLEID + "'  ";
	//String tempSQL = "";

	
	if (diarynofrom.trim().length() > 0 && diarynoto.trim().length() > 0) {
		String[] arrRefNoFrom = diarynofrom.split("/");
		String[] arrRefNoTo = diarynoto.split("/");
		String refClassFrom = arrRefNoFrom[0];
		String refCountFrom = arrRefNoFrom[1];
		String refCountTo = arrRefNoTo[1];
		String refYear=arrRefNoFrom[2];
		condSQL += " AND UPPER(REFCLASS) = UPPER('"+refClassFrom+"') AND REFCOUNT BETWEEN '"+refCountFrom+"' AND '"+refCountTo+"' AND EXTRACT(YEAR from INCOMINGDATE) ='"+refYear+"'";
	}
		
		strSQL = " SELECT X.REFNO,X.REFCLASS,TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,X.REFERENCENAME,TO_CHAR(X.LETTERDATE,'DD/MM/YYYY') LETTERDATE," +
				 " X.VIPSTATUS,(SELECT STATENAME FROM MSTSTATE WHERE STATECODE = X.STATECODE) STATECODE,TO_CHAR(X.ACKDATE,'DD/MM/YYYY') ACKDATE," +
				 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.ACKBY)," +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = X.REFCATEGORY)," +
				 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=X.URGENCY)," +
				 " (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.SIGNEDBY)," +
				 " TO_CHAR(X.SIGNEDON,'DD/MM/YYYY') SIGNEDON, X.REMARKS, X.LANGUAGE, " +
				 "	(SELECT SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTCODE = X.SUBJECTCODE AND SUBJECTTYPE = 'R') SUBJECTCODE, X.SUBJECT, " +
				 " GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))  MARKEDTO," +
				 " (SELECT TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1)  MARKINGDATE," +
				 " (SELECT TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1) TARGETDATE," +
				 " X.FILENO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = X.FILESTATUS1) FILESTATUS1, " +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = X.FILESTATUS2) FILESTATUS2, " +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00' AND CODE = X.REPLYTYPE) REPLYTYPE," +
				 " X.REGISTRATIONNO, TO_CHAR(X.REGISTRATIONDATE,'DD/MM/YYYY')," +
				 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.DMARKINGTO) DMARKINGTO," +
				 " TO_CHAR(X.DMARKINGDATE,'DD/MM/YYYY')," +
				 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.IMARKINGTO) IMARKINGTO, X.ISBUDGET," +
				 "X.REFID,X.LINKREFID,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '11' AND CODE<>'00' AND CODE = X.SALUTATION) SALUTATION , X.ADDRESSENGLISH, X.VIPSTATUSDESC" +
				 " FROM TRNREFERENCE X, TRNMARKING "+
				 " WHERE  X.REFID = TRNMARKING.REFID "+condSQL+" ORDER BY X.REFID";
		
		//System.out.println(strSQL);
		return (new CommonDAO()).getSQLResult(strSQL,35);
}*/


//soumen function
public ArrayList<CommonBean> acknowledgementfromto(String diarynofrom, String diarynoto)
{
	/*String condSQL = "";
//	String strSQL = " SELECT REFNO, TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE FROM TRNREFERENCE WHERE ROLEID = '" + ROLEID + "'  ";
	//String tempSQL = "";

	
	if (diarynofrom.trim().length() > 0 && diarynoto.trim().length() > 0) {
		String[] arrRefNoFrom = diarynofrom.split("/");
		String[] arrRefNoTo = diarynoto.split("/");
		String refClassFrom = arrRefNoFrom[0];
		String refCountFrom = arrRefNoFrom[1];
		String refCountTo = arrRefNoTo[1];
		String refYear=arrRefNoFrom[2];
		condSQL += " AND UPPER(REFCLASS) = UPPER('"+refClassFrom+"') AND REFCOUNT BETWEEN '"+refCountFrom+"' AND '"+refCountTo+"' AND EXTRACT(YEAR from INCOMINGDATE) ='"+refYear+"'";
	}
		
		strSQL = " SELECT X.REFNO,X.REFCLASS,TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,X.REFERENCENAME,TO_CHAR(X.LETTERDATE,'DD/MM/YYYY') LETTERDATE," +
				 " X.VIPSTATUS,(SELECT STATENAME FROM MSTSTATE WHERE STATECODE = X.STATECODE) STATECODE,TO_CHAR(X.ACKDATE,'DD/MM/YYYY') ACKDATE," +
				 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.ACKBY)," +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = X.REFCATEGORY)," +
				 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=X.URGENCY)," +
				 " (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.SIGNEDBY)," +
				 " TO_CHAR(X.SIGNEDON,'DD/MM/YYYY') SIGNEDON, X.REMARKS, X.LANGUAGE, " +
				 "	(SELECT SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTCODE = X.SUBJECTCODE AND SUBJECTTYPE = 'R') SUBJECTCODE, X.SUBJECT, " +
				 " GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))  MARKEDTO," +
				 " (SELECT TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1)  MARKINGDATE," +
				 " (SELECT TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1) TARGETDATE," +
				 " X.FILENO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = X.FILESTATUS1) FILESTATUS1, " +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = X.FILESTATUS2) FILESTATUS2, " +
				 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00' AND CODE = X.REPLYTYPE) REPLYTYPE," +
				 " X.REGISTRATIONNO, TO_CHAR(X.REGISTRATIONDATE,'DD/MM/YYYY')," +
				 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.DMARKINGTO) DMARKINGTO," +
				 " TO_CHAR(X.DMARKINGDATE,'DD/MM/YYYY')," +
				 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.IMARKINGTO) IMARKINGTO, X.ISBUDGET," +
				 "X.REFID,X.LINKREFID,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '11' AND CODE<>'00' AND CODE = X.SALUTATION) SALUTATION , X.ADDRESSENGLISH, X.VIPSTATUSDESC" +
				 " FROM TRNREFERENCE X, TRNMARKING "+
				 " WHERE  X.REFID = TRNMARKING.REFID "+condSQL+" ORDER BY X.REFID";
		
		//System.out.println(strSQL);
		//return (new CommonDAO()).getSQLResult(strSQL,35);*/


	strSQL = " SELECT X.REFNO,X.REFCLASS,TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE,X.REFERENCENAME,TO_CHAR(X.LETTERDATE,'DD/MM/YYYY') LETTERDATE," +
			 " X.VIPSTATUS,(SELECT STATENAME FROM MSTSTATE WHERE STATECODE = X.STATECODE) STATECODE,TO_CHAR(X.ACKDATE,'DD/MM/YYYY') ACKDATE," +
			 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.ACKBY)," +
			 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = X.REFCATEGORY)," +
			 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=X.URGENCY)," +
			 " (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.SIGNEDBY)," +
			 " TO_CHAR(X.SIGNEDON,'DD/MM/YYYY') SIGNEDON, X.REMARKS, X.LANGUAGE, " +
			 "	(SELECT SUBJECTNAME FROM MSTSUBJECT WHERE SUBJECTCODE = X.SUBJECTCODE AND SUBJECTTYPE = 'R') SUBJECTCODE, X.SUBJECT, " +
			 " GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))  MARKEDTO," +
			 " (SELECT TO_CHAR(MARKINGDATE, 'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1)  MARKINGDATE," +
			 " (SELECT TO_CHAR(TARGETDATE,'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1) TARGETDATE," +
			 " X.FILENO, (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE<>'00' AND CODE = X.FILESTATUS1) FILESTATUS1, " +
			 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '7' AND CODE<>'00' AND CODE = X.FILESTATUS2) FILESTATUS2, " +
			 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '8' AND CODE<>'00' AND CODE = X.REPLYTYPE) REPLYTYPE," +
			 " X.REGISTRATIONNO, TO_CHAR(X.REGISTRATIONDATE,'DD/MM/YYYY')," +
			 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.DMARKINGTO) DMARKINGTO," +
			 " TO_CHAR(X.DMARKINGDATE,'DD/MM/YYYY')," +
			 " (SELECT A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = X.IMARKINGTO) IMARKINGTO, X.ISBUDGET," +
			 "X.REFID,X.LINKREFID,(SELECT FNAME FROM MSTGEC WHERE CODETYPE = '11' AND CODE<>'00' AND CODE = X.SALUTATION) SALUTATION , X.ADDRESSENGLISH, X.VIPSTATUSDESC" +
			 " FROM TRNREFERENCE X, TRNMARKING "+
			 " WHERE  X.REFID = TRNMARKING.REFID ";
	
	DBConnection con = new DBConnection();
	ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
	ArrayList<String> params = new ArrayList<String>();
	
	params.add("");

	if (diarynofrom.trim().length() > 0 && diarynoto.trim().length() > 0) {
		String[] arrRefNoFrom = diarynofrom.split("/");
		String[] arrRefNoTo = diarynoto.split("/");
		String refClassFrom = arrRefNoFrom[0];
		String refCountFrom = arrRefNoFrom[1];
		String refCountTo = arrRefNoTo[1];
		String refYear=arrRefNoFrom[2];
		strSQL += " AND UPPER(REFCLASS) = UPPER(?) AND REFCOUNT BETWEEN ? AND ? AND EXTRACT(YEAR from INCOMINGDATE) =?";
		params.add(1,refClassFrom);
		params.add(2,refCountFrom);
		params.add(3,refCountTo);
		params.add(4,refYear);
	
	}
	
	strSQL +=" ORDER BY X.REFID";
	
	try {
		con.openConnection();
		PreparedStatement ps = con.setPrepStatement(strSQL);
		for(int k=1;k<params.size();k++){
    		ps.setString(k, params.get(k));
    		//System.out.println("k :"+k+" params "+params.get(k));
    		
    	}
		arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 35,con);
		//System.out.println("acknowledgementfromto executed");
		
       } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
      }
      finally{
		con.closeConnection();
     }
    return arr;

   }
}
