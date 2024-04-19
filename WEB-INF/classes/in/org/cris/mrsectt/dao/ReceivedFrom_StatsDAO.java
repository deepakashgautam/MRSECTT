package in.org.cris.mrsectt.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;

public class ReceivedFrom_StatsDAO {
	
	String strSQL = "";
	static Logger log = LogManager.getLogger(ReportRefNoDAO.class);
	
	ArrayList<ArrayList<String>> arrCB = new ArrayList<ArrayList<String>>(); 
	ArrayList<String> referenceName = new ArrayList<String>();
	ArrayList<String> vipStatus = new ArrayList<String>();
	ArrayList<String> roleName = new ArrayList<String>();
	ArrayList<String> PendWithDt = new ArrayList<String>();
	ArrayList<String> UnderProcess = new ArrayList<String>();
	ArrayList<String> AdditionalInfo = new ArrayList<String>();
	ArrayList<String> totalCase = new ArrayList<String>();
	ArrayList<String> SubjectCode = new ArrayList<String>();
	ArrayList<String> TotalSub = new ArrayList<String>();
	CommonBean bean = new CommonBean();
	DBConnection con = new DBConnection();

	/*public ArrayList<ArrayList<String>> getTotal(String datefrom, String dateto, String refclass, String isConf, String status, String state,String[] subjectcode) {
		
		try {
			String tmpCond = isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			tmpCond += status.length()>0? " AND UPPER(VIPSTATUS) LIKE UPPER('%"+status+"%')": "";
			tmpCond += state.length()>0? " AND STATECODE = '"+state+"'": "";
			
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
			
			strSQL = " SELECT DISTINCT UPPER(REFERENCENAME) REFERENCENAME, REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE VIPSTATUS FROM TRNREFERENCE " +
					 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					 " AND REFCLASS = '"+refclass+"' "+tmpCond+" ORDER BY REFERENCENAME ";
			//System.out.println(": "+ strSQL);
//			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			while(rs.next())
			{
				referenceName.add(rs.getString("REFERENCENAME"));
				vipStatus.add(rs.getString("VIPSTATUS"));
			}
			arrCB.add(referenceName);
			
			for(int i=0;i<referenceName.size();i++)
			{
				strSQL = " SELECT COUNT(REFID) TOTAL FROM TRNREFERENCE WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
						 " AND NVL(FILESTATUS1,'0') = 0 AND REFCLASS = '"+refclass+"' AND UPPER(REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE) = UPPER('"+vipStatus.get(i)+"') "+tmpCond+"";
				//System.out.println("strSQL : "+ strSQL);
//				log.debug(strSQL);
				ResultSet rs_PendWithDt = con.select(strSQL);
				if(rs_PendWithDt.next())
				{
					PendWithDt.add(rs_PendWithDt.getString("TOTAL"));
				}
			}
			arrCB.add(PendWithDt);

			
			for(int i=0;i<referenceName.size();i++)
			{
				strSQL = " SELECT COUNT(REFID) TOTAL FROM TRNREFERENCE WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				 		 " AND NVL(FILESTATUS1,'0') = 1 AND REFCLASS = '"+refclass+"' AND UPPER(REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE) = UPPER('"+vipStatus.get(i)+"') "+tmpCond+"";
	//			log.debug(strSQL);
				ResultSet rs_UnderProcess = con.select(strSQL);
				if(rs_UnderProcess.next())
				{
					UnderProcess.add(rs_UnderProcess.getString("TOTAL"));
				}
			}
			arrCB.add(UnderProcess);

			
			for(int i=0;i<referenceName.size();i++)
			{
				strSQL = " SELECT COUNT(REFID) TOTAL FROM TRNREFERENCE WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
		 		 		 " AND (NVL(FILESTATUS1,'0') = 10 OR NVL(FILESTATUS1,'0') = 12 OR NVL(FILESTATUS1,'0') = 6 OR NVL(FILESTATUS1,'0') = 7)" +
		 		 		 " AND REFCLASS = '"+refclass+"' AND UPPER(REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE) = UPPER('"+vipStatus.get(i)+"') "+tmpCond+"";
	//			log.debug(strSQL);
				ResultSet rs_AdditionalInfo = con.select(strSQL);
				if(rs_AdditionalInfo.next())
				{
					AdditionalInfo.add(rs_AdditionalInfo.getString("TOTAL"));
				}
			}
			arrCB.add(AdditionalInfo);

			
			
			for(int i=0;i<referenceName.size();i++)
			{
				strSQL = " SELECT COUNT(REFID) TOTAL FROM TRNREFERENCE WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
		 		 		 " AND REFCLASS = '"+refclass+"' AND UPPER(REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE) = UPPER('"+vipStatus.get(i)+"') "+tmpCond+"";
	//			log.debug(strSQL);
				ResultSet rs_totalCase = con.select(strSQL);
				if(rs_totalCase.next())
				{
					totalCase.add(rs_totalCase.getString("TOTAL"));
				}
			}
			arrCB.add(totalCase);
			
			for(int i=0;i<referenceName.size();i++)
			{
			   strSQL = "SELECT LISTAGG(TOTAL||'-'||SUBJECTCODE, ', ') WITHIN GROUP (ORDER BY TOTAL DESC, SUBJECTCODE) AS employees" +
						" FROM (SELECT COUNT(REFID) TOTAL, SUBJECTCODE FROM TRNREFERENCE" +
						" WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
						" AND REFCLASS = '"+refclass+"' AND UPPER(REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE) = UPPER('"+vipStatus.get(i)+"') "+tmpCond+" GROUP BY SUBJECTCODE)";
				ResultSet rs_SubjectCode = con.select(strSQL);
				if(rs_SubjectCode.next())
				{
					SubjectCode.add(rs_SubjectCode.getString("employees"));
				}
			}
			arrCB.add(SubjectCode);
			arrCB.add(vipStatus);
			con.closeConnection();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return arrCB;
	}*/
	
	//soumen func
public ArrayList<ArrayList<String>> getTotal(String datefrom, String dateto, String refclass, String isConf, String status, String state,String[] subjectcode) {
		
		try {
			
			int count=0;
			PreparedStatement ps=null;
			String tmpCond = isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			tmpCond += status.length()>0? " AND UPPER(VIPSTATUS) LIKE UPPER('%"+status+"%')": "";
			tmpCond += state.length()>0? " AND STATECODE = '"+state+"'": "";
			
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
			
			
			/*strSQL = " SELECT DISTINCT UPPER(REFERENCENAME) REFERENCENAME, REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE VIPSTATUS FROM TRNREFERENCE " +
					 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					 " AND REFCLASS = '"+refclass+"' "+tmpCond+" ORDER BY REFERENCENAME ";
			//System.out.println(": "+ strSQL);
//			log.debug(strSQL);
			con.openConnection();
			ResultSet rs = con.select(strSQL);*/
			
			strSQL = " SELECT DISTINCT UPPER(REFERENCENAME) REFERENCENAME, REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE VIPSTATUS FROM TRNREFERENCE " +
					 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
					 " AND REFCLASS = ? "+tmpCond+" ORDER BY REFERENCENAME ";
		
			con.openConnection();
			ps = con.setPrepStatement(strSQL);
			ps.setString(1,datefrom);
			ps.setString(2,dateto);
			ps.setString(3,refclass);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				referenceName.add(rs.getString("REFERENCENAME"));
				vipStatus.add(rs.getString("VIPSTATUS"));
			}
			arrCB.add(referenceName);
			rs.close();
			//System.out.println("getTotal::1:: executed");
			
			count=0;
			for(int i=0;i<referenceName.size();i++)
			{
				/*strSQL = " SELECT COUNT(REFID) TOTAL FROM TRNREFERENCE WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
						 " AND NVL(FILESTATUS1,'0') = 0 AND REFCLASS = '"+refclass+"' AND UPPER(REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE) = UPPER('"+vipStatus.get(i)+"') "+tmpCond+"";
				//System.out.println("strSQL : "+ strSQL);
//				log.debug(strSQL);
				ResultSet rs_PendWithDt = con.select(strSQL);*/
				
				strSQL = " SELECT COUNT(REFID) TOTAL FROM TRNREFERENCE WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
						 " AND NVL(FILESTATUS1,'0') = 0 AND REFCLASS = ? AND UPPER(REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE) = UPPER(?) "+tmpCond+"";
				
				ps = con.setPrepStatement(strSQL);
				ps.setString(1,datefrom);
				ps.setString(2,dateto);
				ps.setString(3,refclass);
				ps.setString(4, vipStatus.get(i));
				ResultSet rs_PendWithDt=ps.executeQuery();
						
				if(rs_PendWithDt.next())
				{
					PendWithDt.add(rs_PendWithDt.getString("TOTAL"));
					count++;
				}
				
				
			}
			arrCB.add(PendWithDt);
			//System.out.println("getTotal::PendWithDt executed "+count);
			
			count=0;
			for(int i=0;i<referenceName.size();i++)
			{
				/*strSQL = " SELECT COUNT(REFID) TOTAL FROM TRNREFERENCE WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				 		 " AND NVL(FILESTATUS1,'0') = 1 AND REFCLASS = '"+refclass+"' AND UPPER(REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE) = UPPER('"+vipStatus.get(i)+"') "+tmpCond+"";
	//			log.debug(strSQL);
				ResultSet rs_UnderProcess = con.select(strSQL);*/
				
				strSQL = " SELECT COUNT(REFID) TOTAL FROM TRNREFERENCE WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
				 		 " AND NVL(FILESTATUS1,'0') = 1 AND REFCLASS = ? AND UPPER(REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE) = UPPER(?) "+tmpCond+"";

				ps = con.setPrepStatement(strSQL);
				ps.setString(1,datefrom);
				ps.setString(2,dateto);
				ps.setString(3,refclass);
				ps.setString(4,vipStatus.get(i));
				ResultSet rs_UnderProcess =ps.executeQuery();
					
				if(rs_UnderProcess.next())
				{
					UnderProcess.add(rs_UnderProcess.getString("TOTAL"));
					count++;
				}
				
				
			}
			arrCB.add(UnderProcess);
			//System.out.println("getTotal::UnderProcess executed "+count);
			
			count=0;
			for(int i=0;i<referenceName.size();i++)
			{
				/*strSQL = " SELECT COUNT(REFID) TOTAL FROM TRNREFERENCE WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
		 		 		 " AND (NVL(FILESTATUS1,'0') = 10 OR NVL(FILESTATUS1,'0') = 12 OR NVL(FILESTATUS1,'0') = 6 OR NVL(FILESTATUS1,'0') = 7)" +
		 		 		 " AND REFCLASS = '"+refclass+"' AND UPPER(REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE) = UPPER('"+vipStatus.get(i)+"') "+tmpCond+"";
	//			log.debug(strSQL);
				ResultSet rs_AdditionalInfo = con.select(strSQL);*/

				
				strSQL = " SELECT COUNT(REFID) TOTAL FROM TRNREFERENCE WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
		 		 		 " AND (NVL(FILESTATUS1,'0') = 10 OR NVL(FILESTATUS1,'0') = 12 OR NVL(FILESTATUS1,'0') = 6 OR NVL(FILESTATUS1,'0') = 7)" +
		 		 		 " AND REFCLASS = ? AND UPPER(REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE) = UPPER(?) "+tmpCond+"";

				ps = con.setPrepStatement(strSQL);
				ps.setString(1,datefrom);
				ps.setString(2,dateto);
				ps.setString(3,refclass);
				ps.setString(4,vipStatus.get(i));
				ResultSet rs_AdditionalInfo =ps.executeQuery();
					
				if(rs_AdditionalInfo.next())
				{
					AdditionalInfo.add(rs_AdditionalInfo.getString("TOTAL"));
					count++;
				}
				
			}
			arrCB.add(AdditionalInfo);
			//System.out.println("getTotal::AdditionalInfo executed "+count);
			
			count=0;
			for(int i=0;i<referenceName.size();i++)
			{
				/*strSQL = " SELECT COUNT(REFID) TOTAL FROM TRNREFERENCE WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
		 		 		 " AND REFCLASS = '"+refclass+"' AND UPPER(REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE) = UPPER('"+vipStatus.get(i)+"') "+tmpCond+"";
	//			log.debug(strSQL);
				ResultSet rs_totalCase = con.select(strSQL);*/
				
				strSQL = " SELECT COUNT(REFID) TOTAL FROM TRNREFERENCE WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
		 		 		 " AND REFCLASS = ? AND UPPER(REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE) = UPPER(?) "+tmpCond+"";
	
				ps = con.setPrepStatement(strSQL);
				ps.setString(1,datefrom);
				ps.setString(2,dateto);
				ps.setString(3,refclass);
				ps.setString(4,vipStatus.get(i));
				ResultSet rs_totalCase =ps.executeQuery();
			
				if(rs_totalCase.next())
				{
					totalCase.add(rs_totalCase.getString("TOTAL"));
					count++;
				}
				
			}
			arrCB.add(totalCase);
			//System.out.println("getTotal::totalCase executed "+count);
			
			count=0;
			for(int i=0;i<referenceName.size();i++)
			{
				/*strSQL = "SELECT LISTAGG(TOTAL||'-'||SUBJECTCODE, ', ') WITHIN GROUP (ORDER BY TOTAL DESC, SUBJECTCODE) AS employees" +
						" FROM (SELECT COUNT(REFID) TOTAL, SUBJECTCODE FROM TRNREFERENCE" +
						" WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
						" AND REFCLASS = '"+refclass+"' AND UPPER(REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE) = UPPER('"+vipStatus.get(i)+"') "+tmpCond+" GROUP BY SUBJECTCODE)";
				ResultSet rs_SubjectCode = con.select(strSQL);*/
				
				strSQL = "SELECT LISTAGG(TOTAL||'-'||SUBJECTCODE, ', ') WITHIN GROUP (ORDER BY TOTAL DESC, SUBJECTCODE) AS employees" +
						" FROM (SELECT COUNT(REFID) TOTAL, SUBJECTCODE FROM TRNREFERENCE" +
						" WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
						" AND REFCLASS = ? AND UPPER(REFERENCENAME||'-'||VIPSTATUS||'-'||STATECODE) = UPPER(?) "+tmpCond+" GROUP BY SUBJECTCODE)";
		
				ps = con.setPrepStatement(strSQL);
				ps.setString(1,datefrom);
				ps.setString(2,dateto);
				ps.setString(3,refclass);
				ps.setString(4,vipStatus.get(i));
				ResultSet rs_SubjectCode =ps.executeQuery();
			
				if(rs_SubjectCode.next())
				{
					SubjectCode.add(rs_SubjectCode.getString("employees"));
					count++;
				}
				
			}
			arrCB.add(SubjectCode);
			arrCB.add(vipStatus);
			//System.out.println("getTotal::SubjectCode executed "+count);
			
			con.closeConnection();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return arrCB;
	}
}