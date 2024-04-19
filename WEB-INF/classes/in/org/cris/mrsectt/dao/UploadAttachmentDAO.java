package in.org.cris.mrsectt.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;

public class UploadAttachmentDAO {
	String strSQL = "";
	String condSQL = "";
	DBConnection con = new DBConnection();

/*public ArrayList<CommonBean> getData(String diarynofrom, String diarynoto, String datefrom, String dateto, String roleId, String isConf) {
	
		if (diarynofrom.trim().length() > 0 && diarynoto.trim().length() > 0) {
			String[] arrRefNoFrom = diarynofrom.split("/");
			String[] arrRefNoTo = diarynoto.split("/");
			
			String refClassFrom = arrRefNoFrom[0];
			String refCountFrom = arrRefNoFrom[1];
			String refCountTo = arrRefNoTo[1];
			condSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			
			strSQL = " SELECT REFID, REFNO, TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE, REFERENCENAME, SUBJECT" +
					 " FROM TRNREFERENCE WHERE ROLEID = '"+roleId+"' AND UPPER(REFCLASS) = UPPER('"+refClassFrom+"')" +
					 " AND REFCOUNT BETWEEN '"+refCountFrom+"' AND '"+refCountTo+"' "+condSQL+"" +
					 " AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
					 " ORDER BY REFID DESC";
		}
			return (new CommonDAO()).getSQLResult(strSQL, 5);
		}*/
	
	//soumen fun
	public ArrayList<CommonBean> getData(String diarynofrom, String diarynoto, String datefrom, String dateto, String roleId, String isConf) {
		
		  ArrayList<CommonBean> arr = new ArrayList<CommonBean>();
		 
		  if (diarynofrom.trim().length() > 0 && diarynoto.trim().length() > 0) {
				String[] arrRefNoFrom = diarynofrom.split("/");
				String[] arrRefNoTo = diarynoto.split("/");
				
				String refClassFrom = arrRefNoFrom[0];
				String refCountFrom = arrRefNoFrom[1];
				String refCountTo = arrRefNoTo[1];
			
				condSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
				
				/*strSQL = " SELECT REFID, REFNO, TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE, REFERENCENAME, SUBJECT" +
						 " FROM TRNREFERENCE WHERE ROLEID = '"+roleId+"' AND UPPER(REFCLASS) = UPPER('"+refClassFrom+"')" +
						 " AND REFCOUNT BETWEEN '"+refCountFrom+"' AND '"+refCountTo+"' "+condSQL+"" +
						 " AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE('"+datefrom+"','DD/MM/YYYY') AND TO_DATE('"+dateto+"','DD/MM/YYYY')" +
						 " ORDER BY REFID DESC";*/
				
				strSQL = " SELECT REFID, REFNO, TO_CHAR(INCOMINGDATE,'DD/MM/YYYY') INCOMINGDATE, REFERENCENAME, SUBJECT" +
						 " FROM TRNREFERENCE WHERE ROLEID = ? AND UPPER(REFCLASS) = UPPER(?)" +
						 " AND REFCOUNT BETWEEN ? AND ? "+condSQL+"" +
						 " AND TO_DATE(TO_CHAR(INCOMINGDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
						 " ORDER BY REFID DESC";
				
				try {
					  con.openConnection();
					  PreparedStatement ps = con.setPrepStatement(strSQL);
					  ps.setString(1, roleId);
					  ps.setString(2, refClassFrom);
					  ps.setString(3, refCountFrom);
					  ps.setString(4, refCountTo);
					  ps.setString(5, datefrom);
					  ps.setString(6, dateto);

					  arr = (new CommonDAO()).getSQLResultPreparedStmt(ps, 5,con);
					  //System.out.println("getData executed");
					
			       } catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			      }
			      finally{
					con.closeConnection();
			     }
				
				
			   }
				//return (new CommonDAO()).getSQLResult(strSQL, 5);
		  
		      return arr;
		      
			}
	}