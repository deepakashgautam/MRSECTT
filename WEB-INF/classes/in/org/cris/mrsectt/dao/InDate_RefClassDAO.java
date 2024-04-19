package in.org.cris.mrsectt.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;

public class InDate_RefClassDAO {
	
	String strSQL = "";
	static Logger log = LogManager.getLogger(ReportRefNoDAO.class);
	String tmpCond = "";
	/*public ArrayList<CommonBean> getPendWithDt(String datefrom, String dateto, String refclass, String isConf,String[] subjectcode) {
		
			tmpCond += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			
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
			strSQL = " select TO_CHAR(TO_DATE(dates,'DD/MM/YYYY'),'DD/MM/YYYY') DATES, NVL(TO_CHAR(TO_DATE(dates,'DD/MM/YYYY'),'DAY'),'TOTAL') DAYS, sum("+refclass+") "+refclass+", TO_DATE(dates,'DD/MM/YYYY') DATES1 from (" +
					 " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES, NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" FROM " +
					 " (SELECT TO_DATE('"+datefrom+"','DD/MM/YYYY')+ROWNUM -1 DATES FROM (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)) WHERE" +
					 " ROWNUM <= TO_DATE('"+dateto+"','DD/MM/YYYY')+1 - TO_DATE('"+datefrom+"','DD/MM/YYYY')) A," +
					 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1,ISCONF, SUBJECTCODE from trnreference)  pivot (count(REFID) for refclass in ('"+refclass+"'))" +
					 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					 " AND NVL(FILESTATUS1,'0') = 0 "+tmpCond+") B" +
					 " WHERE A.DATES = B.INCOMINGDATE(+)" +
					 " ) X group by ROLLUP (dates) ORDER BY DATES1 ";

			strSQL = " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES,NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" " +
					 " FROM ( SELECT TO_DATE('"+datefrom+"','DD/MM/YYYY')+ROWNUM -1 DATES FROM" +
					 " (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2))" +
					 " WHERE ROWNUM <= TO_DATE('"+dateto+"','DD/MM/YYYY')+1 - TO_DATE('"+datefrom+"','DD/MM/YYYY')) A," +
					 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1 from trnreference) pivot (count(REFID) for refclass in ('"+refclass+"'))" +
					 " WHERE INCOMINGDATE BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') AND FILESTATUS1 IS NULL) B" +
					 " WHERE A.DATES = B.INCOMINGDATE(+) ORDER BY A.DATES ";
			return (new CommonDAO()).getSQLResult(strSQL,3);
			}*/
//soumen func
	public ArrayList<CommonBean> getPendWithDt(String datefrom, String dateto, String refclass, String isConf,String[] subjectcode) {
		
		tmpCond += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
		
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
		/*strSQL = " select TO_CHAR(TO_DATE(dates,'DD/MM/YYYY'),'DD/MM/YYYY') DATES, NVL(TO_CHAR(TO_DATE(dates,'DD/MM/YYYY'),'DAY'),'TOTAL') DAYS, sum("+refclass+") "+refclass+", TO_DATE(dates,'DD/MM/YYYY') DATES1 from (" +
				 " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES, NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" FROM " +
				 " (SELECT TO_DATE('"+datefrom+"','DD/MM/YYYY')+ROWNUM -1 DATES FROM (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)) WHERE" +
				 " ROWNUM <= TO_DATE('"+dateto+"','DD/MM/YYYY')+1 - TO_DATE('"+datefrom+"','DD/MM/YYYY')) A," +
				 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1,ISCONF, SUBJECTCODE from trnreference)  pivot (count(REFID) for refclass in ('"+refclass+"'))" +
				 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				 " AND NVL(FILESTATUS1,'0') = 0 "+tmpCond+") B" +
				 " WHERE A.DATES = B.INCOMINGDATE(+)" +
				 " ) X group by ROLLUP (dates) ORDER BY DATES1 ";*/

/*			strSQL = " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES,NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" " +
				 " FROM ( SELECT TO_DATE('"+datefrom+"','DD/MM/YYYY')+ROWNUM -1 DATES FROM" +
				 " (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2))" +
				 " WHERE ROWNUM <= TO_DATE('"+dateto+"','DD/MM/YYYY')+1 - TO_DATE('"+datefrom+"','DD/MM/YYYY')) A," +
				 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1 from trnreference) pivot (count(REFID) for refclass in ('"+refclass+"'))" +
				 " WHERE INCOMINGDATE BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') AND FILESTATUS1 IS NULL) B" +
				 " WHERE A.DATES = B.INCOMINGDATE(+) ORDER BY A.DATES ";
*/			//return (new CommonDAO()).getSQLResult(strSQL,3);
		
		//// Using Prepared Statement///
		
		strSQL = " select TO_CHAR(TO_DATE(dates,'DD/MM/YYYY'),'DD/MM/YYYY') DATES, NVL(TO_CHAR(TO_DATE(dates,'DD/MM/YYYY'),'DAY'),'TOTAL') DAYS, sum("+refclass+") "+refclass+", TO_DATE(dates,'DD/MM/YYYY') DATES1 from (" +
				 " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES, NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" FROM " +
				 " (SELECT TO_DATE(?,'DD/MM/YYYY')+ROWNUM -1 DATES FROM (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)) WHERE" +
				 " ROWNUM <= TO_DATE(?,'DD/MM/YYYY')+1 - TO_DATE(?,'DD/MM/YYYY')) A," +
				 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1,ISCONF, SUBJECTCODE from trnreference)  pivot (count(REFID) for refclass in ('"+refclass+"'))" +
				 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
				 " AND NVL(FILESTATUS1,'0') = 0 "+tmpCond+") B" +
				 " WHERE A.DATES = B.INCOMINGDATE(+)" +
				 " ) X group by ROLLUP (dates) ORDER BY DATES1 ";
		
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		DBConnection con = new DBConnection();
		try {
					con.openConnection();
					PreparedStatement ps = con.setPrepStatement(strSQL);
					
					//ps.setString(1,refclass);
					ps.setString(1,datefrom);
					ps.setString(2,dateto);
					ps.setString(3,datefrom);
					ps.setString(4,datefrom);
					ps.setString(5,dateto);
					
					arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 3,con);
					//System.out.println("getPendWithDt executed");
					
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
			finally{
					con.closeConnection();
			}
		return arr;
			

}
	/*public ArrayList<CommonBean> getUnderProcess(String datefrom, String dateto, String refclass, String isConf,String[] subjectcode) {
		
		tmpCond += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
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

			strSQL = " select TO_DATE(dates,'DD/MM/YYYY') DATES, NVL(TO_CHAR(TO_DATE(dates,'DD/MM/YYYY'),'DAY'),'TOTAL') DAYS, sum("+refclass+") "+refclass+" from (" +
					 " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES, NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" FROM " +
					 " (SELECT TO_DATE('"+datefrom+"','DD/MM/YYYY')+ROWNUM -1 DATES FROM (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)) WHERE" +
					 " ROWNUM <= TO_DATE('"+dateto+"','DD/MM/YYYY')+1 - TO_DATE('"+datefrom+"','DD/MM/YYYY')) A," +
					 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1,ISCONF, SUBJECTCODE from trnreference)  pivot (count(REFID) for refclass in ('"+refclass+"'))" +
					 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					 " AND NVL(FILESTATUS1,'0') = 1 "+tmpCond+") B" +
					 " WHERE A.DATES = B.INCOMINGDATE(+)" +
					 " ) X group by ROLLUP (dates) ORDER BY DATES ";

			strSQL = " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES,NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" " +
					 " FROM ( SELECT TO_DATE('"+datefrom+"','DD/MM/YYYY')+ROWNUM -1 DATES FROM" +
					 " (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2))" +
					 " WHERE ROWNUM <= TO_DATE('"+dateto+"','DD/MM/YYYY')+1 - TO_DATE('"+datefrom+"','DD/MM/YYYY')) A," +
					 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1 from trnreference) pivot (count(REFID) for refclass in ('"+refclass+"'))" +
					 " WHERE INCOMINGDATE BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') AND FILESTATUS1 = 1) B" +
					 " WHERE A.DATES = B.INCOMINGDATE(+) ORDER BY A.DATES ";
		return (new CommonDAO()).getSQLResult(strSQL,3);
	}*/
	
	//soumen func
public ArrayList<CommonBean> getUnderProcess(String datefrom, String dateto, String refclass, String isConf,String[] subjectcode) {
		
		tmpCond += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
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

			/*strSQL = " select TO_DATE(dates,'DD/MM/YYYY') DATES, NVL(TO_CHAR(TO_DATE(dates,'DD/MM/YYYY'),'DAY'),'TOTAL') DAYS, sum("+refclass+") "+refclass+" from (" +
					 " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES, NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" FROM " +
					 " (SELECT TO_DATE('"+datefrom+"','DD/MM/YYYY')+ROWNUM -1 DATES FROM (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)) WHERE" +
					 " ROWNUM <= TO_DATE('"+dateto+"','DD/MM/YYYY')+1 - TO_DATE('"+datefrom+"','DD/MM/YYYY')) A," +
					 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1,ISCONF, SUBJECTCODE from trnreference)  pivot (count(REFID) for refclass in ('"+refclass+"'))" +
					 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					 " AND NVL(FILESTATUS1,'0') = 1 "+tmpCond+") B" +
					 " WHERE A.DATES = B.INCOMINGDATE(+)" +
					 " ) X group by ROLLUP (dates) ORDER BY DATES ";*/

/*			strSQL = " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES,NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" " +
					 " FROM ( SELECT TO_DATE('"+datefrom+"','DD/MM/YYYY')+ROWNUM -1 DATES FROM" +
					 " (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2))" +
					 " WHERE ROWNUM <= TO_DATE('"+dateto+"','DD/MM/YYYY')+1 - TO_DATE('"+datefrom+"','DD/MM/YYYY')) A," +
					 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1 from trnreference) pivot (count(REFID) for refclass in ('"+refclass+"'))" +
					 " WHERE INCOMINGDATE BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') AND FILESTATUS1 = 1) B" +
					 " WHERE A.DATES = B.INCOMINGDATE(+) ORDER BY A.DATES ";
*/		//return (new CommonDAO()).getSQLResult(strSQL,3);
			
			
			strSQL = " select TO_DATE(dates,'DD/MM/YYYY') DATES, NVL(TO_CHAR(TO_DATE(dates,'DD/MM/YYYY'),'DAY'),'TOTAL') DAYS, sum("+refclass+") "+refclass+" from (" +
					 " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES, NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" FROM " +
					 " (SELECT TO_DATE(?,'DD/MM/YYYY')+ROWNUM -1 DATES FROM (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)) WHERE" +
					 " ROWNUM <= TO_DATE(?,'DD/MM/YYYY')+1 - TO_DATE(?,'DD/MM/YYYY')) A," +
					 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1,ISCONF, SUBJECTCODE from trnreference)  pivot (count(REFID) for refclass in ('"+refclass+"'))" +
					 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
					 " AND NVL(FILESTATUS1,'0') = 1 "+tmpCond+") B" +
					 " WHERE A.DATES = B.INCOMINGDATE(+)" +
					 " ) X group by ROLLUP (dates) ORDER BY DATES ";
			
			ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
			DBConnection con = new DBConnection();
			try {
						con.openConnection();
						PreparedStatement ps = con.setPrepStatement(strSQL);
						//ps.setString(1,refclass);
						ps.setString(1,datefrom);
						ps.setString(2,dateto);
						ps.setString(3,datefrom);
						ps.setString(4,datefrom);
						ps.setString(5,dateto);
						
						arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 3,con);
						//System.out.println("getUnderProcess executed");
						
				} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
				finally{
						con.closeConnection();
				}
			return arr;
				
	}


	/*public ArrayList<CommonBean> getAdditionalInfo(String datefrom, String dateto, String refclass, String isConf,String[] subjectcode) {
		
		tmpCond += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
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
		
			strSQL = " select TO_DATE(dates,'DD/MM/YYYY') DATES, NVL(TO_CHAR(TO_DATE(dates,'DD/MM/YYYY'),'DAY'),'TOTAL') DAYS, sum("+refclass+") "+refclass+" from (" +
					 " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES, NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" FROM " +
					 " (SELECT TO_DATE('"+datefrom+"','DD/MM/YYYY')+ROWNUM -1 DATES FROM (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)) WHERE" +
					 " ROWNUM <= TO_DATE('"+dateto+"','DD/MM/YYYY')+1 - TO_DATE('"+datefrom+"','DD/MM/YYYY')) A," +
					 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1,ISCONF, SUBJECTCODE from trnreference)  pivot (count(REFID) for refclass in ('"+refclass+"'))" +
					 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					 " AND (NVL(FILESTATUS1,'0') = 10 OR NVL(FILESTATUS1,'0') = 12 OR NVL(FILESTATUS1,'0') = 6 OR NVL(FILESTATUS1,'0') = 7) "+tmpCond+") B WHERE A.DATES = B.INCOMINGDATE(+)" +
					 " ) X group by ROLLUP (dates) ORDER BY DATES ";
		return (new CommonDAO()).getSQLResult(strSQL,3);
	}*/
//soumen func
public ArrayList<CommonBean> getAdditionalInfo(String datefrom, String dateto, String refclass, String isConf,String[] subjectcode) {
	
	tmpCond += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
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
	
		/*strSQL = " select TO_DATE(dates,'DD/MM/YYYY') DATES, NVL(TO_CHAR(TO_DATE(dates,'DD/MM/YYYY'),'DAY'),'TOTAL') DAYS, sum("+refclass+") "+refclass+" from (" +
				 " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES, NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" FROM " +
				 " (SELECT TO_DATE('"+datefrom+"','DD/MM/YYYY')+ROWNUM -1 DATES FROM (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)) WHERE" +
				 " ROWNUM <= TO_DATE('"+dateto+"','DD/MM/YYYY')+1 - TO_DATE('"+datefrom+"','DD/MM/YYYY')) A," +
				 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1,ISCONF, SUBJECTCODE from trnreference)  pivot (count(REFID) for refclass in ('"+refclass+"'))" +
				 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
				 " AND (NVL(FILESTATUS1,'0') = 10 OR NVL(FILESTATUS1,'0') = 12 OR NVL(FILESTATUS1,'0') = 6 OR NVL(FILESTATUS1,'0') = 7) "+tmpCond+") B WHERE A.DATES = B.INCOMINGDATE(+)" +
				 " ) X group by ROLLUP (dates) ORDER BY DATES ";
	return (new CommonDAO()).getSQLResult(strSQL,3);*/
	
	strSQL = " select TO_DATE(dates,'DD/MM/YYYY') DATES, NVL(TO_CHAR(TO_DATE(dates,'DD/MM/YYYY'),'DAY'),'TOTAL') DAYS, sum("+refclass+") "+refclass+" from (" +
			 " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES, NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" FROM " +
			 " (SELECT TO_DATE(?,'DD/MM/YYYY')+ROWNUM -1 DATES FROM (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)) WHERE" +
			 " ROWNUM <= TO_DATE(?,'DD/MM/YYYY')+1 - TO_DATE(?,'DD/MM/YYYY')) A," +
			 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1,ISCONF, SUBJECTCODE from trnreference)  pivot (count(REFID) for refclass in ('"+refclass+"'))" +
			 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
			 " AND (NVL(FILESTATUS1,'0') = 10 OR NVL(FILESTATUS1,'0') = 12 OR NVL(FILESTATUS1,'0') = 6 OR NVL(FILESTATUS1,'0') = 7) "+tmpCond+") B WHERE A.DATES = B.INCOMINGDATE(+)" +
			 " ) X group by ROLLUP (dates) ORDER BY DATES ";
	
	ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
	DBConnection con = new DBConnection();
	try {
				con.openConnection();
				PreparedStatement ps = con.setPrepStatement(strSQL);
				ps.setString(1,datefrom);
				ps.setString(2,dateto);
				ps.setString(3,datefrom);
				ps.setString(4,datefrom);
				ps.setString(5,dateto);
				
				arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 3,con);
				//System.out.println("getAdditionalInfo executed");
				
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		finally{
				con.closeConnection();
		}
	return arr;
		
	
}


/*public ArrayList<CommonBean> getTotal(String datefrom, String dateto, String refclass, String isConf,String[] subjectcode) {
		
		tmpCond += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
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
		
			strSQL = " select TO_DATE(dates,'DD/MM/YYYY') DATES, NVL(TO_CHAR(TO_DATE(dates,'DD/MM/YYYY'),'DAY'),'TOTAL') DAYS, sum("+refclass+") "+refclass+" from (" +
					 " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES, NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" FROM " +
					 " (SELECT TO_DATE('"+datefrom+"','DD/MM/YYYY')+ROWNUM -1 DATES FROM (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)) WHERE" +
					 " ROWNUM <= TO_DATE('"+dateto+"','DD/MM/YYYY')+1 - TO_DATE('"+datefrom+"','DD/MM/YYYY')) A," +
					 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1,ISCONF, SUBJECTCODE from trnreference)  pivot (count(REFID) for refclass in ('"+refclass+"'))" +
					 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') "+tmpCond+") B" +
					 " WHERE A.DATES = B.INCOMINGDATE(+)" +
					 " ) X group by ROLLUP (dates) ORDER BY DATES ";

			strSQL = " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES,NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" " +
					 " FROM ( SELECT TO_DATE('"+datefrom+"','DD/MM/YYYY')+ROWNUM -1 DATES FROM" +
					 " (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2))" +
					 " WHERE ROWNUM <= TO_DATE('"+dateto+"','DD/MM/YYYY')+1 - TO_DATE('"+datefrom+"','DD/MM/YYYY')) A," +
					 " (select * from (select INCOMINGDATE, refclass, REFID from trnreference) pivot (count(REFID) for refclass in ('"+refclass+"'))" +
					 " WHERE INCOMINGDATE BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')) B" +
					 " WHERE A.DATES = B.INCOMINGDATE(+) ORDER BY A.DATES ";
		return (new CommonDAO()).getSQLResult(strSQL,3);
	}*/


///soumen func
public ArrayList<CommonBean> getTotal(String datefrom, String dateto, String refclass, String isConf,String[] subjectcode) {
	
	tmpCond += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
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
	
		/*strSQL = " select TO_DATE(dates,'DD/MM/YYYY') DATES, NVL(TO_CHAR(TO_DATE(dates,'DD/MM/YYYY'),'DAY'),'TOTAL') DAYS, sum("+refclass+") "+refclass+" from (" +
				 " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES, NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" FROM " +
				 " (SELECT TO_DATE('"+datefrom+"','DD/MM/YYYY')+ROWNUM -1 DATES FROM (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)) WHERE" +
				 " ROWNUM <= TO_DATE('"+dateto+"','DD/MM/YYYY')+1 - TO_DATE('"+datefrom+"','DD/MM/YYYY')) A," +
				 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1,ISCONF, SUBJECTCODE from trnreference)  pivot (count(REFID) for refclass in ('"+refclass+"'))" +
				 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') "+tmpCond+") B" +
				 " WHERE A.DATES = B.INCOMINGDATE(+)" +
				 " ) X group by ROLLUP (dates) ORDER BY DATES ";*/

/*			strSQL = " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES,NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" " +
				 " FROM ( SELECT TO_DATE('"+datefrom+"','DD/MM/YYYY')+ROWNUM -1 DATES FROM" +
				 " (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2))" +
				 " WHERE ROWNUM <= TO_DATE('"+dateto+"','DD/MM/YYYY')+1 - TO_DATE('"+datefrom+"','DD/MM/YYYY')) A," +
				 " (select * from (select INCOMINGDATE, refclass, REFID from trnreference) pivot (count(REFID) for refclass in ('"+refclass+"'))" +
				 " WHERE INCOMINGDATE BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')) B" +
				 " WHERE A.DATES = B.INCOMINGDATE(+) ORDER BY A.DATES ";
*/		//return (new CommonDAO()).getSQLResult(strSQL,3);
		
		strSQL = " select TO_DATE(dates,'DD/MM/YYYY') DATES, NVL(TO_CHAR(TO_DATE(dates,'DD/MM/YYYY'),'DAY'),'TOTAL') DAYS, sum("+refclass+") "+refclass+" from (" +
				 " SELECT TO_CHAR(DATES,'DD/MM/YYYY') DATES, NVL(TO_CHAR(\"'"+refclass+"'\"),'0') "+refclass+" FROM " +
				 " (SELECT TO_DATE(?,'DD/MM/YYYY')+ROWNUM -1 DATES FROM (SELECT 1 FROM Dual GROUP BY CUBE (2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2)) WHERE" +
				 " ROWNUM <= TO_DATE(?,'DD/MM/YYYY')+1 - TO_DATE(?,'DD/MM/YYYY')) A," +
				 " (select * from (select INCOMINGDATE, refclass, REFID, FILESTATUS1,ISCONF, SUBJECTCODE from trnreference)  pivot (count(REFID) for refclass in ('"+refclass+"'))" +
				 " WHERE TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+tmpCond+") B" +
				 " WHERE A.DATES = B.INCOMINGDATE(+)" +
				 " ) X group by ROLLUP (dates) ORDER BY DATES ";
		
		
		ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		DBConnection con = new DBConnection();
		try {
					con.openConnection();
					PreparedStatement ps = con.setPrepStatement(strSQL);
					ps.setString(1,datefrom);
					ps.setString(2,dateto);
					ps.setString(3,datefrom);
					ps.setString(4,datefrom);
					ps.setString(5,dateto);
					
					arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 3,con);
					//System.out.println("getTotal executed");
					
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




/*	public ArrayList<CommonBean> RequestFrom(String receivedfrom, String datefrom, String dateto, String refclass,
			String subjectcode, String markto) {
		String condSQL = "";

			condSQL = condSQL + (receivedfrom.length() >0 ? " AND UPPER(REFERENCENAME) LIKE ('%"+receivedfrom.toUpperCase()+"%')" : "");		
			condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND INCOMINGDATE BETWEEN " +
					  " TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");
			condSQL = condSQL + (refclass.length() >0 ? " AND REFCLASS = '"+refclass+"'" : "");	
			condSQL = condSQL + (subjectcode.length() >0 ? " AND SUBJECTCODE = '"+subjectcode+"'" : "");		
			//condSQL = condSQL + (refclass.length() >0 ? " AND REFCLASS = '"+markto+"'" : "");		
			
			strSQL = " SELECT REFNO,REFCLASS,TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),REFERENCENAME,TO_CHAR(LETTERDATE,'DD/MM/YYYY')," +
					 " VIPSTATUS,STATECODE,TO_CHAR(ACKDATE,'DD/MM/YYYY'),(SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = ACKBY)," +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = REFCATEGORY)," +
					 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=URGENCY)," +
					 " (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = SIGNEDBY)," +
					 " TO_CHAR(SIGNEDON,'DD/MM/YYYY'),REMARKS,LANGUAGE FROM TRNREFERENCE "+
					 " WHERE 1=1 "+condSQL+" ORDER BY REFID";
			
			//System.out.println("strSQL:"+strSQL);
			return (new CommonDAO()).getSQLResult(strSQL,15);
			}
	
	public ArrayList<CommonBean> SearchFor(String searchfor, String receivedfrom, String datefrom, String dateto, String refclass,
			String subjectcode, String markto) {
		String condSQL = "";

			condSQL = condSQL + (receivedfrom.length() >0 ? " AND UPPER(REFERENCENAME) LIKE ('%"+receivedfrom.toUpperCase()+"%')" : "");		
			condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND INCOMINGDATE BETWEEN " +
					  " TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");
			condSQL = condSQL + (refclass.length() >0 ? " AND REFCLASS = '"+refclass+"'" : "");	
			condSQL = condSQL + (subjectcode.length() >0 ? " AND SUBJECTCODE = '"+subjectcode+"'" : "");		
			condSQL = condSQL + (searchfor.length() >0 ? " AND (UPPER(SUBJECT) LIKE '%"+searchfor.toUpperCase()+"%' OR UPPER(REMARKS) LIKE '%"+searchfor.toUpperCase()+"%') " : "");		
			
			strSQL = " SELECT REFNO,REFCLASS,TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),REFERENCENAME,TO_CHAR(LETTERDATE,'DD/MM/YYYY')," +
					 " VIPSTATUS,STATECODE,TO_CHAR(ACKDATE,'DD/MM/YYYY'),(SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = ACKBY)," +
					 " (SELECT FNAME FROM MSTGEC WHERE CODETYPE = '0' AND CODE<>'00' AND CODE = REFCATEGORY)," +
					 " (SELECT SNAME FROM MSTGEC WHERE CODETYPE = '1' AND CODE<>'00' AND CODE=URGENCY)," +
					 " (SELECT  A.ROLENAME FROM MSTROLE A WHERE A.ROLEID = SIGNEDBY)," +
					 " TO_CHAR(SIGNEDON,'DD/MM/YYYY'),REMARKS,LANGUAGE FROM TRNREFERENCE "+
					 " WHERE 1=1 "+condSQL+" ORDER BY REFID";
			
			//System.out.println("strSQL:"+strSQL);
			return (new CommonDAO()).getSQLResult(strSQL,15);
			}
	
	public ArrayList<CommonBean> FormattedReport(String searchfor, String receivedfrom, String datefrom, String dateto, String refclass,
			String subjectcode, String markto) {
		String condSQL = "";

			condSQL = condSQL + (receivedfrom.length() >0 ? " AND UPPER(REFERENCENAME) LIKE ('%"+receivedfrom.toUpperCase()+"%')" : "");		
			condSQL = condSQL + ((datefrom.length() > 0 && dateto.length() > 0) ? " AND INCOMINGDATE BETWEEN " +
					  " TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY') ": "");
			condSQL = condSQL + (refclass.length() >0 ? " AND REFCLASS = '"+refclass+"'" : "");	
			condSQL = condSQL + (subjectcode.length() >0 ? " AND SUBJECTCODE = '"+subjectcode+"'" : "");		
			condSQL = condSQL + (searchfor.length() >0 ? " AND (UPPER(SUBJECT) LIKE '%"+searchfor.toUpperCase()+"%' OR UPPER(REMARKS) LIKE '%"+searchfor.toUpperCase()+"%') " : "");		
			
			strSQL = " SELECT REFERENCENAME, VIPSTATUS, (SELECT X.STATENAME FROM MSTSTATE X WHERE X.STATECODE = A.STATECODE) STATE, "+
					 " SUBJECT, REMARKS, REFNO, TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'), 'Dtd:'||TO_CHAR(LETTERDATE,'DD/MM') LETTERDATE, " +
					 " 'Ack:'||(SELECT P.ROLENAME FROM MSTROLE P WHERE P.ROLEID= A.ACKBY) ACKBY, TO_CHAR(ACKDATE,'DD/MM'), " +
					 " (SELECT P.ROLENAME FROM MSTROLE P WHERE P.ROLEID = (SELECT Y.MARKINGTO FROM TRNMARKING Y WHERE Y.REFID = A.REFID AND Y.MARKINGSEQUENCE = '1')) MARKTO, " +
					 " (SELECT TO_CHAR(Y.MARKINGDATE,'DD/MM/YYYY') FROM TRNMARKING Y WHERE Y.REFID = A.REFID AND Y.MARKINGSEQUENCE = '1') MARKDATE, " +
					 " FILENO, REGISTRATIONNO, IMARKINGTO,DECODE((FILESTATUS1 || FILESTATUS2), '',  STATUSREMARK," +
					 " ((SELECT SNAME FROM MSTGEC WHERE CODETYPE = '6' AND CODE <> '00' AND CODE = FILESTATUS1) ||' ,' " +
					 " ||(SELECT SNAME  FROM MSTGEC WHERE CODETYPE = '7' AND CODE <> '00' AND CODE = FILESTATUS2))), TO_CHAR(REGISTRATIONDATE,'DD/MM')," +
					 " (SELECT TO_CHAR(TARGETDATE,'DD/MM') FROM TRNMARKING X WHERE X.REFID = A.REFID AND X.MARKINGSEQUENCE = '1') " +
					 " FROM TRNREFERENCE A WHERE 1=1 "+condSQL+" ORDER BY REFID ";
			
			//System.out.println("strSQL:"+strSQL);
			return (new CommonDAO()).getSQLResult(strSQL,18);
			}
*/