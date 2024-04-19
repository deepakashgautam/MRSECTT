package in.org.cris.mrsectt.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;

public class InDate_RefClassRolewiseDAO {
	
	String strSQL = "";
	static Logger log = LogManager.getLogger(ReportRefNoDAO.class);
	
	ArrayList<ArrayList<String>> arrCB = new ArrayList<ArrayList<String>>(); 
	ArrayList<String> roleID = new ArrayList<String>();
	ArrayList<String> roleName = new ArrayList<String>();
	ArrayList<String> PendWithDt = new ArrayList<String>();
	ArrayList<String> PartiallyPos = new ArrayList<String>();
	ArrayList<String> AdditionalInfo = new ArrayList<String>();
	ArrayList<String> Negative = new ArrayList<String>();
	ArrayList<String> UnderProcess = new ArrayList<String>();
	ArrayList<String> Positive = new ArrayList<String>();
	ArrayList<String> totalCase = new ArrayList<String>();
	ArrayList<String> Subjudice = new ArrayList<String>();
	ArrayList<String> Tentative = new ArrayList<String>();
	ArrayList<String> NotRec = new ArrayList<String>();
	CommonBean bean = new CommonBean();
	DBConnection con = new DBConnection();

	/*public ArrayList<ArrayList<String>> getTotal(String datefrom, String dateto, String refclass, String isConf, String roleId,String[] subjectcode) {
		
		try {
			String tmpCond = isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			//System.out.println("subjectcode length ::" + subjectcode.length);
			if(subjectcode.length>0){
				String scode="";
				for(int i=0;i<subjectcode.length;i++){
					if(!subjectcode[i].equalsIgnoreCase("")){
					scode=scode + "'"+subjectcode[i]+"'"+(i==subjectcode.length-1?"":",");
					}
				}
				if(!scode.equalsIgnoreCase("")){	
					tmpCond = tmpCond + " AND SUBJECTCODE IN ("+scode+")";
				}
			}
			
			strSQL = " select (SELECT ZX.ROLENAME FROM MSTROLE ZX WHERE ZX.ROLEID = A1.ROLENAME) ROLEDESC, ROLENAME, " +
					 " sum(\"'"+refclass+"'\") "+refclass+" from (   select * from (   select BB.MARKINGTO," +
					 " BB.MARKINGTO ROLENAME, AA.REFID, AA.REFCLASS, AA.FILESTATUS1, AA.INCOMINGDATE, AA.ISCONF, AA.SUBJECTCODE" +
					 " from trnreference AA, TRNMARKING BB WHERE AA.REFID = BB.REFID AND BB.MARKINGFROM = 430)" +
					 " pivot (count(REFID) for refclass in ('"+refclass+"'))" +
					 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') "+tmpCond+") A1 " +
					 " WHERE \"'"+refclass+"'\" <> 0 group by ROLENAME ORDER BY ROLEDESC ";
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while(rs.next())
			{
				roleID.add(rs.getString("ROLENAME"));
				roleName.add(rs.getString("ROLEDESC"));
			}
			arrCB.add(roleID);
			arrCB.add(roleName);
			
			for(int i=0;i<roleID.size();i++)
			{
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
						 " AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
						 " AND NVL(FILESTATUS1,'0') = 0 AND REFCLASS = '"+refclass+"'" +
						 " AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond+"";
				//log.debug(strSQL);
				ResultSet rs_PendWithDt = con.select(strSQL);
				if(rs_PendWithDt.next())
				{
					PendWithDt.add(rs_PendWithDt.getString("total"));
				}
			}
			arrCB.add(PendWithDt);

			
			for(int i=0;i<roleID.size();i++)
			{
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND NVL(FILESTATUS1,'0') = 1 AND REFCLASS = '"+refclass+"'" +
				" AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond+"";
//				log.debug(strSQL);
				ResultSet rs_UnderProcess = con.select(strSQL);
				if(rs_UnderProcess.next())
				{
					UnderProcess.add(rs_UnderProcess.getString("total"));
				}
			}
			arrCB.add(UnderProcess);

			
			for(int i=0;i<roleID.size();i++)
			{
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND (NVL(FILESTATUS1,'0') = 10 OR NVL(FILESTATUS1,'0') = 12 OR NVL(FILESTATUS1,'0') = 6 OR NVL(FILESTATUS1,'0') = 7) AND REFCLASS = '"+refclass+"'" +
				" AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond+"";
//				log.debug(strSQL);
				ResultSet rs_AdditionalInfo = con.select(strSQL);
				if(rs_AdditionalInfo.next())
				{
					AdditionalInfo.add(rs_AdditionalInfo.getString("total"));
				}
			}
			arrCB.add(AdditionalInfo);

			
			for(int i=0;i<roleID.size();i++)
			{
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond+"";
//				log.debug(strSQL);
				ResultSet rs_totalCase = con.select(strSQL);
				if(rs_totalCase.next())
				{
					totalCase.add(rs_totalCase.getString("total"));
				}
			}
			con.closeConnection();
			arrCB.add(totalCase);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return arrCB;
	}*/
	
	//soumen func
	public ArrayList<ArrayList<String>> getTotal(String datefrom, String dateto, String refclass, String isConf, String roleId,String[] subjectcode) {
		
		 try {
			    PreparedStatement ps=null;
	            ArrayList<String> params = new ArrayList<String>();
	            ArrayList<String> paramstmpcond = new ArrayList<String>();
	            int count=0;
				params.add("");
				paramstmpcond.add("");
				params.add(refclass);
				params.add(datefrom);
				params.add(dateto);
				
				/*paramstmpcond.add(datefrom);
				paramstmpcond.add(dateto);
				paramstmpcond.add(refclass);*/
				
				String tmpCond = isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				String tmpCond2 = isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				//System.out.println("subjectcode length ::" + subjectcode.length);
				if(subjectcode.length>0){
					String scode="";
					for(int i=0;i<subjectcode.length;i++){
						if(!subjectcode[i].equalsIgnoreCase("")){
						scode=scode + "'"+subjectcode[i]+"'"+(i==subjectcode.length-1?"":",");
						}
					}
					if(!scode.equalsIgnoreCase("")){	
						//tmpCond = tmpCond + " AND SUBJECTCODE IN ("+scode+")";
						tmpCond2 = tmpCond2 + " AND SUBJECTCODE IN ("+scode+")";
						tmpCond = tmpCond + " AND SUBJECTCODE IN (?)";
					
						scode = scode.replaceAll("\'","");
						params.add(scode);
						paramstmpcond.add(scode);
						
						
					}
				}
				
				strSQL = " select (SELECT ZX.ROLENAME FROM MSTROLE ZX WHERE ZX.ROLEID = A1.ROLENAME) ROLEDESC, ROLENAME, " +
						 " sum(\"'"+refclass+"'\") "+refclass+" from (   select * from (   select BB.MARKINGTO," +
						 " BB.MARKINGTO ROLENAME, AA.REFID, AA.REFCLASS, AA.FILESTATUS1, AA.INCOMINGDATE, AA.ISCONF, AA.SUBJECTCODE" +
						 " from trnreference AA, TRNMARKING BB WHERE AA.REFID = BB.REFID AND BB.MARKINGFROM = 430)" +
						 " pivot (count(REFID) for refclass in ('"+refclass+"'))" +
						 //" WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') "+tmpCond2+") A1 " +
						 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+tmpCond2+") A1 " +	
						 " WHERE \"'"+refclass+"'\" <> 0 group by ROLENAME ORDER BY ROLEDESC ";
				
				//log.debug(strSQL);
				con.openConnection();
				//ResultSet rs = con.select(strSQL);
	
		    	ps=con.setPrepStatement(strSQL);
				ps.setString(1, datefrom);
				ps.setString(2, dateto);
				ResultSet rs =ps.executeQuery();
				
				while(rs.next())
				{
					  //System.out.println("hi1");
					roleID.add(rs.getString("ROLENAME"));
					roleName.add(rs.getString("ROLEDESC"));
				}
				rs.close();
				//System.out.println("getTotal::1:: executed");
				
				arrCB.add(roleID);
				arrCB.add(roleName);
				count=0;
				for(int i=0;i<roleID.size();i++)
				{
					/*strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
							 " AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
							 " AND NVL(FILESTATUS1,'0') = 0 AND REFCLASS = '"+refclass+"'" +
							 " AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond2+"";
					log.debug(strSQL);
					ResultSet rs_PendWithDt = con.select(strSQL);*/
					
					strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
							 " AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
							 " AND NVL(FILESTATUS1,'0') = 0 AND REFCLASS = ?" +
							 " AND B.MARKINGTO = ? "+tmpCond2+"";
					ps = con.setPrepStatement(strSQL);
					ps.setString(1, datefrom);
					ps.setString(2, dateto);
					ps.setString(3, refclass);
					ps.setString(4, roleID.get(i));
					
					/*for(int k=1;k<paramstmpcond.size();k++){
			    		ps.setString(4+k, paramstmpcond.get(k));
			    		//System.out.println("k :"+(k)+" params "+paramstmpcond.get(k));
			    		
			    	}*/
					ResultSet rs_PendWithDt =ps.executeQuery();
					
					if(rs_PendWithDt.next())
					{
						PendWithDt.add(rs_PendWithDt.getString("total"));
					}
					rs_PendWithDt.close();
					//count++;
				}
				arrCB.add(PendWithDt);
				//System.out.println("getTotal::2:: executed::"+count);
				
				count=0;
				for(int i=0;i<roleID.size();i++)
				{
					/*strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
					" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					" AND NVL(FILESTATUS1,'0') = 1 AND REFCLASS = '"+refclass+"'" +
					" AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond2+"";
				    log.debug(strSQL);
					ResultSet rs_UnderProcess = con.select(strSQL);*/
					
					strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
							" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
							" AND NVL(FILESTATUS1,'0') = 1 AND REFCLASS = ? " +
							" AND B.MARKINGTO = ? "+tmpCond2+"";
					
					ps = con.setPrepStatement(strSQL);
					ps.setString(1, datefrom);
					ps.setString(2, dateto);
					ps.setString(3, refclass);
					ps.setString(4, roleID.get(i));
					ResultSet rs_UnderProcess =ps.executeQuery();
					
					
					if(rs_UnderProcess.next())
					{
						UnderProcess.add(rs_UnderProcess.getString("total"));
					}
					rs_UnderProcess.close();
					//count++;
				}
				arrCB.add(UnderProcess);
				//System.out.println("getTotal::3:: executed::"+count);
				
				count=0;
				for(int i=0;i<roleID.size();i++)
				{
					/*strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
					" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					" AND (NVL(FILESTATUS1,'0') = 10 OR NVL(FILESTATUS1,'0') = 12 OR NVL(FILESTATUS1,'0') = 6 OR NVL(FILESTATUS1,'0') = 7) AND REFCLASS = '"+refclass+"'" +
					" AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond2+"";
      				log.debug(strSQL);
					ResultSet rs_AdditionalInfo = con.select(strSQL);*/
					
					strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
							" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
							" AND (NVL(FILESTATUS1,'0') = 10 OR NVL(FILESTATUS1,'0') = 12 OR NVL(FILESTATUS1,'0') = 6 OR NVL(FILESTATUS1,'0') = 7) AND REFCLASS = ? " +
							" AND B.MARKINGTO =? "+tmpCond2+"";
					
					ps = con.setPrepStatement(strSQL);
					ps.setString(1, datefrom);
					ps.setString(2, dateto);
					ps.setString(3, refclass);
					ps.setString(4, roleID.get(i));
					ResultSet rs_AdditionalInfo =ps.executeQuery();
					
					if(rs_AdditionalInfo.next())
					{   
						AdditionalInfo.add(rs_AdditionalInfo.getString("total"));
					}
					rs_AdditionalInfo.close();
					//count++;
				}
				arrCB.add(AdditionalInfo);
				//System.out.println("getTotal::4:: executed::"+count);
				
				count=0;
				for(int i=0;i<roleID.size();i++)
				{
					/*strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
					" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond2+"";
					log.debug(strSQL);
					ResultSet rs_totalCase = con.select(strSQL);*/
					
					strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
							" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
							" AND REFCLASS = ? AND B.MARKINGTO = ?"+tmpCond2+"";

					ps = con.setPrepStatement(strSQL);
					ps.setString(1, datefrom);
					ps.setString(2, dateto);
					ps.setString(3, refclass);
					ps.setString(4, roleID.get(i));
					ResultSet rs_totalCase =ps.executeQuery();
					
					
					if(rs_totalCase.next())
					{   
						totalCase.add(rs_totalCase.getString("total"));
					}
					rs_totalCase.close();
					//count++;
					
				}
				
				con.closeConnection();
				arrCB.add(totalCase);
				//System.out.println("getTotal::5:: executed::"+count);
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			return arrCB;
		}		


	
	/*public ArrayList<CommonBean> SearchFor(String datefrom, String dateto, String refclass, String isConf, String roleId,String isReply,String[] subjectcode,int rNumber, String status, String state) {
			String Qreply = "";
			String OrderBy = "";
			if(isReply.equalsIgnoreCase("1")){
			Qreply= "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=X.FMID)";
			}
			else{
			Qreply="''";
			}
			
			String condSQL = "";
			if(rNumber == 1||rNumber == 2||rNumber == 3){
			condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
			" TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");
			}
			else{
			condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND (SELECT TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1) BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					//" AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					" ": "");	
			}
			
			if(rNumber==3){
				condSQL += status.length()>0? " AND UPPER(VIPSTATUS) LIKE UPPER('%"+status+"%')": "";
				condSQL += state.length()>0? " AND STATECODE = '"+state+"'": "";	
			}
			condSQL = condSQL + (refclass.length() >0 ? " AND X.REFCLASS = '"+refclass+"'" : "");	
			condSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			if(subjectcode.length>0){
				String scode="";
				for(int i=0;i<subjectcode.length;i++){
					if(!subjectcode[i].equalsIgnoreCase("")){
					scode=scode + "'"+subjectcode[i]+"'"+(i==subjectcode.length-1?"":",");
					}
				}
				if(!scode.equalsIgnoreCase("")){	
					condSQL = condSQL + " AND SUBJECTCODE IN ("+scode+")";
				}
			}
			
			if(rNumber==1){
				OrderBy = "GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))";
				
			}else if(rNumber==2){
				OrderBy = "X.INCOMINGDATE";
				
			}else if(rNumber==3){
				OrderBy = "X.REFERENCENAME";
			}else if(rNumber==4){
				OrderBy = "GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))";
			}else if(rNumber==5){
				OrderBy = "GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))";
			}else{
				OrderBy = "X.REFERENCENAME";
			}
				
			
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
			" WHERE X.ROLEID = '"+roleId+"' AND X.REFID = TRNMARKING.REFID "+condSQL+" ORDER BY "+OrderBy+"";
			return (new CommonDAO()).getSQLResult(strSQL,31);
}*/
	
//soumen code
	public ArrayList<CommonBean> SearchFor(String datefrom, String dateto, String refclass, String isConf, String roleId,String isReply,String[] subjectcode,int rNumber, String status, String state) {
		
		
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		ArrayList<String> params = new ArrayList<String>();
		
		params.add("");
	    params.add(roleId);
	    
	    String Qreply = "";
		String OrderBy = "";
		
		if(isReply.equalsIgnoreCase("1")){
		Qreply= "(SELECT R.REPLY FROM TRNFILEREPLY R WHERE R.FMID=X.FMID)";
		}
		else{
		Qreply="''";
		}
		
		String condSQL = "";
		
		/*if(rNumber == 1||rNumber == 2||rNumber == 3){
		condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
		" TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");
		}
		else{
		condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND (SELECT TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1) BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				//" AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" ": "");	
		}
		
		if(rNumber==3){
			condSQL += status.length()>0? " AND UPPER(VIPSTATUS) LIKE UPPER('%"+status+"%')": "";
			condSQL += state.length()>0? " AND STATECODE = '"+state+"'": "";	
		}
		condSQL = condSQL + (refclass.length() >0 ? " AND X.REFCLASS = '"+refclass+"'" : "");	*/
		
		// PREP STATEMENT
		
		if(rNumber == 1||rNumber == 2||rNumber == 3)
		{
			if(datefrom.length() > 0 && dateto.length() > 0)
			{	
			   condSQL = condSQL +	" AND TO_DATE(TO_CHAR(X.INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN " +
						" TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') ";
			   
		       params.add(datefrom);
		       params.add(dateto);
			}
		    
		}
		else
		{	
			if(datefrom.length() > 0 && dateto.length() > 0)
			{
				condSQL +=" AND (SELECT TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') "
						+ " FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1) "
						+ " BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') ";
				
				params.add(datefrom);
			    params.add(dateto);
						
			}	
		}
			
		if(rNumber==3)
		{
				if(status.length()>0)
				{
					condSQL +=" AND UPPER(VIPSTATUS) LIKE UPPER(?) ";
					params.add("%"+status+"%");
				}
				
				if(state.length()>0)
				{
					condSQL +=" AND STATECODE =? ";
					params.add(state);
				}
				
		}
				
		if(refclass.length() >0)
		{
			condSQL +=" AND X.REFCLASS =? ";
			params.add(refclass);
		}
		
		
		condSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
		if(subjectcode.length>0){
			String scode="";
			for(int i=0;i<subjectcode.length;i++){
				if(!subjectcode[i].equalsIgnoreCase("")){
				scode=scode + "'"+subjectcode[i]+"'"+(i==subjectcode.length-1?"":",");
				}
			}
			if(!scode.equalsIgnoreCase("")){	
				condSQL = condSQL + " AND SUBJECTCODE IN ("+scode+")";
			}
		}
		
		
		if(rNumber==1){
			OrderBy = "GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))";
			
		}else if(rNumber==2){
			OrderBy = "X.INCOMINGDATE";
			
		}else if(rNumber==3){
			OrderBy = "X.REFERENCENAME";
		}else if(rNumber==4){
			OrderBy = "GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))";
		}else if(rNumber==5){
			OrderBy = "GETROLENAME((SELECT MARKINGTO FROM TRNMARKING WHERE REFID = X.REFID AND MARKINGSEQUENCE = 1))";
		}else{
			OrderBy = "X.REFERENCENAME";
		}
			
		
		/*strSQL = " SELECT REFNO,REFCLASS,TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'), REFERENCENAME, TO_CHAR(LETTERDATE,'DD/MM/YYYY')," +
		" VIPSTATUS,STATECODE,TO_CHAR(ACKDATE,'DD/MM/YYYY'),(SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = ACKBY), " +
		" (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = REFCATEGORY), " +
		" (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=URGENCY), " +
		" (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = SIGNEDBY), " +
		" TO_CHAR(SIGNEDON,'DD/MM/YYYY'),REMARKS,LANGUAGE FROM TRNREFERENCE "+
		" WHERE 1=1 "+condSQL+" ORDER BY REFID";*/
		
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
		" WHERE X.ROLEID = '"+roleId+"' AND X.REFID = TRNMARKING.REFID "+condSQL+" ORDER BY "+OrderBy+"";
		return (new CommonDAO()).getSQLResult(strSQL,31);*/
		
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
				" WHERE X.ROLEID = ? AND X.REFID = TRNMARKING.REFID "+condSQL+" ORDER BY "+OrderBy+"";
			
		
		try 
		{
			con.openConnection();
			PreparedStatement ps = con.setPrepStatement(strSQL);
			for(int k=1;k<params.size();k++)
			{
	    		ps.setString(k, params.get(k));
	    		//System.out.println("k :"+k+" params "+params.get(k));
	    	}
			arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 31,con);
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

	/*public ArrayList<ArrayList<String>> getTotal_TargDateWise(String datefrom, String dateto, String refclass, String isConf, String roleId,String[] subjectcode) {
		
		try {
			String tmpCond = isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			tmpCond += isConf.equalsIgnoreCase("0")? " AND REFERENCETYPE NOT IN ('C')": "";
			
			if(subjectcode.length>0){
				String scode="";
				for(int i=0;i<subjectcode.length;i++){
					if(!subjectcode[i].equalsIgnoreCase("")){
					scode=scode + "'"+subjectcode[i]+"'"+(i==subjectcode.length-1?"":",");
					}
				}
				if(!scode.equalsIgnoreCase("")){	
					tmpCond = tmpCond + " AND SUBJECTCODE IN ("+scode+")";
				}
			}
			
			strSQL = " select (SELECT ZX.ROLENAME FROM MSTROLE ZX WHERE ZX.ROLEID = A1.ROLENAME) ROLEDESC, ROLENAME, " +
					" sum(\"'"+refclass+"'\") "+refclass+" from (   select * from (   select BB.MARKINGTO," +
					" BB.MARKINGTO ROLENAME, AA.REFID, AA.REFCLASS, AA.FILESTATUS1, BB.TARGETDATE, AA.INCOMINGDATE, AA.ISCONF, AA.SUBJECTCODE" +
					" from trnreference AA, TRNMARKING BB WHERE AA.REFID = BB.REFID AND BB.MARKINGFROM = 430 "+tmpCond+")" +
					" pivot (count(REFID) for refclass in ('"+refclass+"'))" +
					" WHERE TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					//" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					") A1 " +
					" WHERE \"'"+refclass+"'\" <> 0 AND ROLENAME IS NOT NULL GROUP BY ROLENAME ORDER BY ROLEDESC ";
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while(rs.next())
			{
				roleID.add(rs.getString("ROLENAME"));
				roleName.add(rs.getString("ROLEDESC"));
			}
			arrCB.add(roleID);
			arrCB.add(roleName);
			
			for(int i=0;i<roleID.size();i++)
			{
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
			//	" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND NVL(FILESTATUS1,'0') = 0 AND REFCLASS = '"+refclass+"'" +
				" AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond+"";
			//	log.debug(strSQL);
				ResultSet rs_PendWithDt = con.select(strSQL);
				if(rs_PendWithDt.next())
				{
					PendWithDt.add(rs_PendWithDt.getString("total"));
				}
			}
			arrCB.add(PendWithDt);
			
			
			for(int i=0;i<roleID.size();i++)
			{
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				//" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND NVL(FILESTATUS1,'0') = 1 AND REFCLASS = '"+refclass+"'" +
				" AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond+"";
			//	log.debug(strSQL);
				ResultSet rs_UnderProcess = con.select(strSQL);
				if(rs_UnderProcess.next())
				{
					UnderProcess.add(rs_UnderProcess.getString("total"));
				}
			}
			arrCB.add(UnderProcess);
			
			
			for(int i=0;i<roleID.size();i++)
			{
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
			//	" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND (NVL(FILESTATUS1,'0') = 10 OR NVL(FILESTATUS1,'0') = 12 OR NVL(FILESTATUS1,'0') = 6 OR NVL(FILESTATUS1,'0') = 7) AND REFCLASS = '"+refclass+"'" +
				" AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond+"";
			//	log.debug(strSQL);
				ResultSet rs_AdditionalInfo = con.select(strSQL);
				if(rs_AdditionalInfo.next())
				{
					AdditionalInfo.add(rs_AdditionalInfo.getString("total"));
				}
			}
			arrCB.add(AdditionalInfo);
			
			
			for(int i=0;i<roleID.size();i++)
			{
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
			//	" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond+"";
			//	log.debug(strSQL);
				ResultSet rs_totalCase = con.select(strSQL);
				if(rs_totalCase.next())
				{
					totalCase.add(rs_totalCase.getString("total"));
				}
			}
			con.closeConnection();
			arrCB.add(totalCase);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return arrCB;
	}*/
	//soumen func
public ArrayList<ArrayList<String>> getTotal_TargDateWise(String datefrom, String dateto, String refclass, String isConf, String roleId,String[] subjectcode) {
		
		try {
			
			PreparedStatement ps =null;
			int count=0;
			
			String tmpCond = isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			//tmpCond += isConf.equalsIgnoreCase("0")? " AND REFERENCETYPE NOT IN ('C')": "";
			
			if(subjectcode.length>0){
				String scode="";
				for(int i=0;i<subjectcode.length;i++){
					if(!subjectcode[i].equalsIgnoreCase("")){
					scode=scode + "'"+subjectcode[i]+"'"+(i==subjectcode.length-1?"":",");
					}
				}
				if(!scode.equalsIgnoreCase("")){	
					tmpCond = tmpCond + " AND SUBJECTCODE IN ("+scode+")";
				}
			}
			
			/*strSQL = " select (SELECT ZX.ROLENAME FROM MSTROLE ZX WHERE ZX.ROLEID = A1.ROLENAME) ROLEDESC, ROLENAME, " +
					" sum(\"'"+refclass+"'\") "+refclass+" from (   select * from (   select BB.MARKINGTO," +
					" BB.MARKINGTO ROLENAME, AA.REFID, AA.REFCLASS, AA.FILESTATUS1, BB.TARGETDATE, AA.INCOMINGDATE, AA.ISCONF, AA.SUBJECTCODE" +
					" from trnreference AA, TRNMARKING BB WHERE AA.REFID = BB.REFID AND BB.MARKINGFROM = 430 "+tmpCond+")" +
					" pivot (count(REFID) for refclass in ('"+refclass+"'))" +
					" WHERE TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					//" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					") A1 " +
					" WHERE \"'"+refclass+"'\" <> 0 AND ROLENAME IS NOT NULL GROUP BY ROLENAME ORDER BY ROLEDESC ";
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);*/
			
			/*strSQL2 = " select (SELECT ZX.ROLENAME FROM MSTROLE ZX WHERE ZX.ROLEID = A1.ROLENAME) ROLEDESC, ROLENAME, " +
					" sum(\"'?'\")  ? "+" from (   select * from (   select BB.MARKINGTO," +
					" BB.MARKINGTO ROLENAME, AA.REFID, AA.REFCLASS, AA.FILESTATUS1, BB.TARGETDATE, AA.INCOMINGDATE, AA.ISCONF, AA.SUBJECTCODE" +
					" from trnreference AA, TRNMARKING BB WHERE AA.REFID = BB.REFID AND BB.MARKINGFROM = 430 "+tmpCond+")" +
					" pivot (count(REFID) for refclass in ('"+refclass+"'))" +
					" WHERE TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
					//" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					") A1 " +
					" WHERE \"'?'\" <> 0 AND ROLENAME IS NOT NULL GROUP BY ROLENAME ORDER BY ROLEDESC ";*/
		
			strSQL = " select (SELECT ZX.ROLENAME FROM MSTROLE ZX WHERE ZX.ROLEID = A1.ROLENAME) ROLEDESC, ROLENAME, " +
					" sum(\"'"+refclass+"'\") "+refclass+" from (   select * from (   select BB.MARKINGTO," +
					" BB.MARKINGTO ROLENAME, AA.REFID, AA.REFCLASS, AA.FILESTATUS1, BB.TARGETDATE, AA.INCOMINGDATE, AA.ISCONF, AA.SUBJECTCODE" +
					" from trnreference AA, TRNMARKING BB WHERE AA.REFID = BB.REFID AND BB.MARKINGFROM = 430 "+tmpCond+")" +
					" pivot (count(REFID) for refclass in ('"+refclass+"'))" +
					" WHERE TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
					//" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					") A1 " +
					" WHERE \"'"+refclass+"'\" <> 0 AND ROLENAME IS NOT NULL GROUP BY ROLENAME ORDER BY ROLEDESC ";
	//System.out.println("strSQL inDate refclass: "+strSQL);
	//System.out.println("strSQL inDate refclass: "+refclass);
	//System.out.println("strSQL inDate tmpCond: "+tmpCond);
			
			con.openConnection();
			ps = con.setPrepStatement(strSQL);
			/*ps.setString(1,refclass);
			ps.setString(2,refclass);
			ps.setString(3,datefrom);*/
			
			ps.setString(1,datefrom);
			ps.setString(2,dateto);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				roleID.add(rs.getString("ROLENAME"));
				roleName.add(rs.getString("ROLEDESC"));
			}
			arrCB.add(roleID);
			arrCB.add(roleName);
			//System.out.println("getTotal_TargDateWise::1:: executed");
			
			count=0;
			for(int i=0;i<roleID.size();i++)
			{
				/*strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
			//	" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND NVL(FILESTATUS1,'0') = 0 AND REFCLASS = '"+refclass+"'" +
				" AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond+"";
			//	log.debug(strSQL);
				ResultSet rs_PendWithDt = con.select(strSQL);*/
				
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
						" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
					//	" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
						" AND NVL(FILESTATUS1,'0') = 0 AND REFCLASS = ?" +
						" AND B.MARKINGTO = ? "+tmpCond+"";
				
				ps=con.setPrepStatement(strSQL);
				ps.setString(1,datefrom);
				ps.setString(2,dateto);
				ps.setString(3,refclass);
				ps.setString(4,roleID.get(i));
				ResultSet rs_PendWithDt =ps.executeQuery();
				
				if(rs_PendWithDt.next())
				{
					PendWithDt.add(rs_PendWithDt.getString("total"));
					count++;
				}
				
				
			}
			arrCB.add(PendWithDt);
			//System.out.println("getTotal_TargDateWise::PendWithDt executed"+count);
			
			count=0;
			for(int i=0;i<roleID.size();i++)
			{
				/*strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				//" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND NVL(FILESTATUS1,'0') = 1 AND REFCLASS = '"+refclass+"'" +
				" AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond+"";
			//	log.debug(strSQL);
				ResultSet rs_UnderProcess = con.select(strSQL);*/
				
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
						" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
						//" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
						" AND NVL(FILESTATUS1,'0') = 1 AND REFCLASS = ? " +
						" AND B.MARKINGTO = ? "+tmpCond+"";
				
				ps=con.setPrepStatement(strSQL);
				ps.setString(1,datefrom);
				ps.setString(2,dateto);
				ps.setString(3,refclass);
				ps.setString(4,roleID.get(i));
				ResultSet rs_UnderProcess =ps.executeQuery();
		
				
				if(rs_UnderProcess.next())
				{
					UnderProcess.add(rs_UnderProcess.getString("total"));
					count++;
				}
			}
			arrCB.add(UnderProcess);
			//System.out.println("getTotal_TargDateWise::UnderProcess executed"+count);
			
			count=0;
			for(int i=0;i<roleID.size();i++)
			{
				/*strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
			//	" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND (NVL(FILESTATUS1,'0') = 10 OR NVL(FILESTATUS1,'0') = 12 OR NVL(FILESTATUS1,'0') = 6 OR NVL(FILESTATUS1,'0') = 7) AND REFCLASS = '"+refclass+"'" +
				" AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond+"";
			//	log.debug(strSQL);
				ResultSet rs_AdditionalInfo = con.select(strSQL);*/
				
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
						" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
					//	" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
						" AND (NVL(FILESTATUS1,'0') = 10 OR NVL(FILESTATUS1,'0') = 12 OR NVL(FILESTATUS1,'0') = 6 OR NVL(FILESTATUS1,'0') = 7) AND REFCLASS = ? " +
						" AND B.MARKINGTO = ? "+tmpCond+"";
				
				ps=con.setPrepStatement(strSQL);
				ps.setString(1,datefrom);
				ps.setString(2,dateto);
				ps.setString(3,refclass);
				ps.setString(4,roleID.get(i));
				ResultSet rs_AdditionalInfo =ps.executeQuery();
		
				if(rs_AdditionalInfo.next())
				{
					AdditionalInfo.add(rs_AdditionalInfo.getString("total"));
					count++;
				}
				
			}
			arrCB.add(AdditionalInfo);
			//System.out.println("getTotal_TargDateWise::AdditionalInfo executed"+count);
			
			count=0;
			for(int i=0;i<roleID.size();i++)
			{
				/*strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
			//	" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond+"";
			//	log.debug(strSQL);
				ResultSet rs_totalCase = con.select(strSQL);*/
				
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
						" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
					//	" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
						" AND REFCLASS = ? AND B.MARKINGTO = ? "+tmpCond+"";
			
				ps=con.setPrepStatement(strSQL);
				ps.setString(1,datefrom);
				ps.setString(2,dateto);
				ps.setString(3,refclass);
				ps.setString(4,roleID.get(i));
				ResultSet rs_totalCase =ps.executeQuery();
		
				if(rs_totalCase.next())
				{
					totalCase.add(rs_totalCase.getString("total"));
					count++;
				}
			}
			con.closeConnection();
			arrCB.add(totalCase);
			//System.out.println("getTotal_TargDateWise::totalCase executed"+count);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return arrCB;
	}


	/*public ArrayList<ArrayList<String>> getTotal_RplyTypeWise(String datefrom, String dateto, String refclass, String isConf, String roleId,String[] subjectcode) {
		
		try {
			String tmpCond = isConf.equalsIgnoreCase("0")? " AND REFERENCETYPE NOT IN ('C')": "";
			if(subjectcode.length>0){
				String scode="";
				for(int i=0;i<subjectcode.length;i++){
					if(!subjectcode[i].equalsIgnoreCase("")){
					scode=scode + "'"+subjectcode[i]+"'"+(i==subjectcode.length-1?"":",");
					}
				}
				if(!scode.equalsIgnoreCase("")){	
					tmpCond = tmpCond + " AND SUBJECTCODE IN ("+scode+")";
				}
			}
			strSQL = " select (SELECT ZX.ROLENAME FROM MSTROLE ZX WHERE ZX.ROLEID = A1.ROLENAME) ROLEDESC, ROLENAME, " +
			" sum(\"'"+refclass+"'\") "+refclass+" from (   select * from (   select BB.MARKINGTO," +
			" BB.MARKINGTO ROLENAME, AA.REFID, AA.REFCLASS, AA.FILESTATUS1, BB.TARGETDATE, AA.INCOMINGDATE, AA.ISCONF, AA.SUBJECTCODE" +
			" from trnreference AA, TRNMARKING BB WHERE AA.REFID = BB.REFID AND BB.MARKINGFROM = 430 "+tmpCond+")" +
			" pivot (count(REFID) for refclass in ('"+refclass+"'))" +
			" WHERE TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//			" AND REPLYTYPE IN ('P','N','PP','T','S')" +
//			" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')
			") A1 " +
			" WHERE \"'"+refclass+"'\" <> 0 AND ROLENAME IS NOT NULL GROUP BY ROLENAME ORDER BY ROLEDESC ";
			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while(rs.next())
			{
				roleID.add(rs.getString("ROLENAME"));
				roleName.add(rs.getString("ROLEDESC"));
			}
			arrCB.add(roleID);
			arrCB.add(roleName);
			
			for(int i=0;i<roleID.size();i++)
			{
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//				" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" AND A.REPLYTYPE = 'PP' "+tmpCond+"";
				//	log.debug(strSQL);
				ResultSet rs_PartiallyPos = con.select(strSQL);
				if(rs_PartiallyPos.next())
				{
					PartiallyPos.add(rs_PartiallyPos.getString("total"));
				}
			}
			arrCB.add(PartiallyPos);
			
			
			for(int i=0;i<roleID.size();i++)
			{
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//				" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" AND A.REPLYTYPE = 'N' "+tmpCond+"";
				//	log.debug(strSQL);
				ResultSet rs_Negative = con.select(strSQL);
				if(rs_Negative.next())
				{
					Negative.add(rs_Negative.getString("total"));
				}
			}
			arrCB.add(Negative);
			
			
			for(int i=0;i<roleID.size();i++)
			{
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//				" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" AND A.REPLYTYPE = 'P' "+tmpCond+"";
				//	log.debug(strSQL);
				ResultSet rs_Positive = con.select(strSQL);
				if(rs_Positive.next())
				{
					Positive.add(rs_Positive.getString("total"));
				}
			}
			arrCB.add(Positive);
			
			
			for(int i=0;i<roleID.size();i++)
			{
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//				" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond+"";
				//	log.debug(strSQL);
				ResultSet rs_totalCase = con.select(strSQL);
				if(rs_totalCase.next())
				{
					totalCase.add(rs_totalCase.getString("total"));
				}
			}
			arrCB.add(totalCase);
			
			
			for(int i=0;i<roleID.size();i++)
			{
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//				" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" AND A.REPLYTYPE = 'S' "+tmpCond+"";
				//	log.debug(strSQL);
				ResultSet rs_Subjudice = con.select(strSQL);
				if(rs_Subjudice.next())
				{
					Subjudice.add(rs_Subjudice.getString("total"));
				}
			}
			arrCB.add(Subjudice);

			
			for(int i=0;i<roleID.size();i++)
			{
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//				" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" AND A.REPLYTYPE = 'T' "+tmpCond+"";
				//	log.debug(strSQL);
				ResultSet rs_Tentative = con.select(strSQL);
				if(rs_Tentative.next())
				{
					Tentative.add(rs_Tentative.getString("total"));
				}
			}
			arrCB.add(Tentative);

			
			for(int i=0;i<roleID.size();i++)
			{
				strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
				" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//				" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" AND A.REPLYTYPE IS NULL "+tmpCond+"";
				//	log.debug(strSQL);
				ResultSet rs_NotRec = con.select(strSQL);
				if(rs_NotRec.next())
				{
					NotRec.add(rs_NotRec.getString("total"));
				}
			}
			arrCB.add(NotRec);
			con.closeConnection();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return arrCB;
	}*/

//soumen func
public ArrayList<ArrayList<String>> getTotal_RplyTypeWise(String datefrom, String dateto, String refclass, String isConf, String roleId,String[] subjectcode) {
	
	try {
		
		PreparedStatement ps=null;
		int count=0;
		
		String tmpCond = isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
		if(subjectcode.length>0){
			String scode="";
			for(int i=0;i<subjectcode.length;i++){
				if(!subjectcode[i].equalsIgnoreCase("")){
				scode=scode + "'"+subjectcode[i]+"'"+(i==subjectcode.length-1?"":",");
				}
			}
			if(!scode.equalsIgnoreCase("")){	
				tmpCond = tmpCond + " AND SUBJECTCODE IN ("+scode+")";
			}
		}
		
		/*strSQL = " select (SELECT ZX.ROLENAME FROM MSTROLE ZX WHERE ZX.ROLEID = A1.ROLENAME) ROLEDESC, ROLENAME, " +
		" sum(\"'"+refclass+"'\") "+refclass+" from (   select * from (   select BB.MARKINGTO," +
		" BB.MARKINGTO ROLENAME, AA.REFID, AA.REFCLASS, AA.FILESTATUS1, BB.TARGETDATE, AA.INCOMINGDATE, AA.ISCONF, AA.SUBJECTCODE" +
		" from trnreference AA, TRNMARKING BB WHERE AA.REFID = BB.REFID AND BB.MARKINGFROM = 430 "+tmpCond+")" +
		" pivot (count(REFID) for refclass in ('"+refclass+"'))" +
		" WHERE TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//		" AND REPLYTYPE IN ('P','N','PP','T','S')" +
//		" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')
		") A1 " +
		" WHERE \"'"+refclass+"'\" <> 0 AND ROLENAME IS NOT NULL GROUP BY ROLENAME ORDER BY ROLEDESC ";
		log.debug(strSQL);
		con.openConnection();
		ResultSet rs = con.select(strSQL);*/
		
		con.openConnection();
		strSQL = " select (SELECT ZX.ROLENAME FROM MSTROLE ZX WHERE ZX.ROLEID = A1.ROLENAME) ROLEDESC, ROLENAME, " +
				" sum(\"'"+refclass+"'\") "+refclass+" from (   select * from (   select BB.MARKINGTO," +
				" BB.MARKINGTO ROLENAME, AA.REFID, AA.REFCLASS, AA.FILESTATUS1, BB.TARGETDATE, AA.INCOMINGDATE, AA.ISCONF, AA.SUBJECTCODE" +
				" from trnreference AA, TRNMARKING BB WHERE AA.REFID = BB.REFID AND BB.MARKINGFROM = 430 "+tmpCond+")" +
				" pivot (count(REFID) for refclass in ('"+refclass+"'))" +
				" WHERE TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
				") A1 " +
				" WHERE \"'"+refclass+"'\" <> 0 AND ROLENAME IS NOT NULL GROUP BY ROLENAME ORDER BY ROLEDESC ";
		//System.out.println("strSQL: "+strSQL);
		ps=con.setPrepStatement(strSQL);
		ps.setString(1,datefrom);
		ps.setString(2,dateto);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next())
		{
			roleID.add(rs.getString("ROLENAME"));
			roleName.add(rs.getString("ROLEDESC"));
		}
		arrCB.add(roleID);
		arrCB.add(roleName);
		//System.out.println("getTotal_RplyTypeWise::1:: executed");
		
		
		count=0;
		for(int i=0;i<roleID.size();i++)
		{
			/*strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
			" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//			" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
			" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" AND A.REPLYTYPE = 'PP' "+tmpCond+"";
			//	log.debug(strSQL);
			ResultSet rs_PartiallyPos = con.select(strSQL);*/
			
			strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
					" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
//					" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					" AND REFCLASS = ? AND B.MARKINGTO = ? AND A.REPLYTYPE = 'PP' "+tmpCond+"";

			ps=con.setPrepStatement(strSQL);
			ps.setString(1,datefrom);
			ps.setString(2,dateto);
			ps.setString(3,refclass);
			ps.setString(4,roleID.get(i));
			ResultSet rs_PartiallyPos = ps.executeQuery();
			
			if(rs_PartiallyPos.next())
			{
				PartiallyPos.add(rs_PartiallyPos.getString("total"));
				count++;
			}
           				

		}
		arrCB.add(PartiallyPos);
		//System.out.println("getTotal_RplyTypeWise::PartiallyPos executed"+count);
		
		count=0;
		for(int i=0;i<roleID.size();i++)
		{
			/*strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
			" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//			" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
			" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" AND A.REPLYTYPE = 'N' "+tmpCond+"";
			//	log.debug(strSQL);
			ResultSet rs_Negative = con.select(strSQL);*/
			
			strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
					" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
//					" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					" AND REFCLASS = ? AND B.MARKINGTO = ? AND A.REPLYTYPE = 'N' "+tmpCond+"";

			ps=con.setPrepStatement(strSQL);
			ps.setString(1,datefrom);
			ps.setString(2,dateto);
			ps.setString(3,refclass);
			ps.setString(4,roleID.get(i));
			ResultSet rs_Negative = ps.executeQuery();
			
			
			if(rs_Negative.next())
			{
				Negative.add(rs_Negative.getString("total"));
				count++;
			}
			
			
			
		}
		arrCB.add(Negative);
		//System.out.println("getTotal_RplyTypeWise::Negative executed"+count);
		
		count=0;
		for(int i=0;i<roleID.size();i++)
		{
			/*strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
			" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//			" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
			" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" AND A.REPLYTYPE = 'P' "+tmpCond+"";
			//	log.debug(strSQL);
			ResultSet rs_Positive = con.select(strSQL);*/
			
			strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
					" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
//					" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					" AND REFCLASS = ? AND B.MARKINGTO = ? AND A.REPLYTYPE = 'P' "+tmpCond+"";
			
			ps=con.setPrepStatement(strSQL);
			ps.setString(1,datefrom);
			ps.setString(2,dateto);
			ps.setString(3,refclass);
			ps.setString(4,roleID.get(i));
			ResultSet rs_Positive = ps.executeQuery();
		
			if(rs_Positive.next())
			{
				Positive.add(rs_Positive.getString("total"));
				count++;
			}
		}
		arrCB.add(Positive);
		//System.out.println("getTotal_RplyTypeWise::Positive executed"+count);
		
		count=0;
		for(int i=0;i<roleID.size();i++)
		{
			/*strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
			" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//			" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
			" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" "+tmpCond+"";
			//	log.debug(strSQL);
			ResultSet rs_totalCase = con.select(strSQL);*/
			
			strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
					" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
//					" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					" AND REFCLASS = ? AND B.MARKINGTO = ? "+tmpCond+"";
				
			ps=con.setPrepStatement(strSQL);
			ps.setString(1,datefrom);
			ps.setString(2,dateto);
			ps.setString(3,refclass);
			ps.setString(4,roleID.get(i));
			ResultSet rs_totalCase = ps.executeQuery();
		
			
			if(rs_totalCase.next())
			{
				totalCase.add(rs_totalCase.getString("total"));
				count++;
			}
		}
		arrCB.add(totalCase);
		//System.out.println("getTotal_RplyTypeWise::totalCase executed"+count);
		
		count=0;
		for(int i=0;i<roleID.size();i++)
		{
			/*strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
			" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//			" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
			" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" AND A.REPLYTYPE = 'S' "+tmpCond+"";
			//	log.debug(strSQL);
			ResultSet rs_Subjudice = con.select(strSQL);*/
			
			strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
					" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
//					" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					" AND REFCLASS = ? AND B.MARKINGTO = ? AND A.REPLYTYPE = 'S' "+tmpCond+"";
				
			ps=con.setPrepStatement(strSQL);
			ps.setString(1,datefrom);
			ps.setString(2,dateto);
			ps.setString(3,refclass);
			ps.setString(4,roleID.get(i));
			ResultSet rs_Subjudice = ps.executeQuery();
		
			if(rs_Subjudice.next())
			{
				Subjudice.add(rs_Subjudice.getString("total"));
				count++;
			}
		}
		arrCB.add(Subjudice);
		//System.out.println("getTotal_RplyTypeWise::Subjudice executed"+count);
		
		count=0;
		for(int i=0;i<roleID.size();i++)
		{
			/*strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
			" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//			" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
			" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" AND A.REPLYTYPE = 'T' "+tmpCond+"";
			//	log.debug(strSQL);
			ResultSet rs_Tentative = con.select(strSQL);*/
			
			strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
					" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
//					" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					" AND REFCLASS = ? AND B.MARKINGTO = ? AND A.REPLYTYPE = 'T' "+tmpCond+"";

			ps=con.setPrepStatement(strSQL);
			ps.setString(1,datefrom);
			ps.setString(2,dateto);
			ps.setString(3,refclass);
			ps.setString(4,roleID.get(i));
			ResultSet rs_Tentative = ps.executeQuery();
		
			if(rs_Tentative.next())
			{
				Tentative.add(rs_Tentative.getString("total"));
				count++;
				
			}
		}
		arrCB.add(Tentative);
		//System.out.println("getTotal_RplyTypeWise::Tentative executed"+count);
		
		count=0;
		for(int i=0;i<roleID.size();i++)
		{
			/*strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
			" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
//			" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
			" AND REFCLASS = '"+refclass+"' AND B.MARKINGTO = "+roleID.get(i)+" AND A.REPLYTYPE IS NULL "+tmpCond+"";
			//	log.debug(strSQL);
			ResultSet rs_NotRec = con.select(strSQL);*/
			
			
			strSQL = " SELECT COUNT(A.REFID) total FROM TRNREFERENCE A, TRNMARKING B WHERE A.REFID = B.REFID " +
					" AND TO_DATE(TO_CHAR(TARGETDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
//					" AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					" AND REFCLASS = ? AND B.MARKINGTO = ? AND A.REPLYTYPE IS NULL "+tmpCond+"";
			
			ps=con.setPrepStatement(strSQL);
			ps.setString(1,datefrom);
			ps.setString(2,dateto);
			ps.setString(3,refclass);
			ps.setString(4,roleID.get(i));
			ResultSet rs_NotRec = ps.executeQuery();

			if(rs_NotRec.next())
			{
				NotRec.add(rs_NotRec.getString("total"));
				count++;
			}
		}
		arrCB.add(NotRec);
		con.closeConnection();
		//System.out.println("getTotal_RplyTypeWise::NotRec executed"+count);
	}
	catch (SQLException e) {
		e.printStackTrace();
	}
	return arrCB;
}
}