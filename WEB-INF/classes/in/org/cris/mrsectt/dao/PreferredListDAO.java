package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.GenBean;
import in.org.cris.mrsectt.Beans.MstRole;
import in.org.cris.mrsectt.dbConnection.DBConnection;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

public class PreferredListDAO {
	static Logger log = LogManager.getLogger(PreferredListDAO.class);
	

	/*public ArrayList<GenBean> getReports(String loginid) {
		String strSQL = "";
		strSQL = " SELECT REPID,REPHEADER,TO_CHAR(CHANGEDATE,'DD/MM/YYYY    HH24:MI'), '' SQL, '' PARAMS,NVL(REPTYPE,1) FROM MSTREPORTS WHERE LOGINID='"+loginid+"' AND REPTYPE = '2' ORDER BY CHANGEDATE DESC";
		//System.out.println("CustomReportDAO.getReports() :--" + strSQL);
		ArrayList<GenBean> arr = new CommonDAO().getCodeDesc(strSQL, 6);
		DBConnection con = new DBConnection();
		try {
				con.openConnection();
				PreparedStatement ps = con.setPrepStatement("SELECT SQL,PARAMS FROM  MSTREPORTS  WHERE REPID=?");
				for(int i=0;i<arr.size();i++){
					
					ps.setString(1, arr.get(i).getField()[0]);
					ResultSet rs =  ps.executeQuery();
					
					if(rs.next())
					{
						
						InputStream is = null;
						Blob blobObj = null;
						
						
						
						if (rs.getBlob(1) != null) {
							blobObj = rs.getBlob(1);
							byte[] byteStr = blobObj.getBytes(1, (int) blobObj.length());
							arr.get(i).getField()[3] = (new String(byteStr));
						}
						if (rs.getBlob(2) != null) {
							blobObj = rs.getBlob(2);
							byte[] byteStr = blobObj.getBytes(1, (int) blobObj.length());
							arr.get(i).getField()[4] = (new String(byteStr));
						}
						
					}
				}
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return arr;
	}*/
	//soumen code
	public ArrayList<GenBean> getReports(String loginid) {
		String strSQL = "";
		/*strSQL = " SELECT REPID,REPHEADER,TO_CHAR(CHANGEDATE,'DD/MM/YYYY    HH24:MI'), '' SQL, '' PARAMS,NVL(REPTYPE,1) FROM MSTREPORTS WHERE LOGINID='"+loginid+"' AND REPTYPE = '2' ORDER BY CHANGEDATE DESC";
		//System.out.println("CustomReportDAO.getReports() :--" + strSQL);
		ArrayList<GenBean> arr = new CommonDAO().getCodeDesc(strSQL, 6);*/
		  
		DBConnection con = new DBConnection(); 
		strSQL = " SELECT REPID,REPHEADER,TO_CHAR(CHANGEDATE,'DD/MM/YYYY    HH24:MI'), '' SQL, '' PARAMS,NVL(REPTYPE,1) FROM MSTREPORTS WHERE LOGINID=? AND REPTYPE = '2' ORDER BY CHANGEDATE DESC";	
		PreparedStatement ps0 = con.setPrepStatement(strSQL);
		try {
			ps0.setString(1, loginid);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<GenBean> arr = new CommonDAO().getCodeDescPrepStmnt(ps0, 6);
		//System.out.println("getReports::1:: executed");
		
		try {
				con.openConnection();
				
				PreparedStatement ps = con.setPrepStatement("SELECT SQL,PARAMS FROM  MSTREPORTS  WHERE REPID=?");
				for(int i=0;i<arr.size();i++){
					
					ps.setString(1, arr.get(i).getField()[0]);
					ResultSet rs =  ps.executeQuery();
					
					if(rs.next())
					{
						
						InputStream is = null;
						Blob blobObj = null;
						
						
						
						if (rs.getBlob(1) != null) {
							blobObj = rs.getBlob(1);
							byte[] byteStr = blobObj.getBytes(1, (int) blobObj.length());
							arr.get(i).getField()[3] = (new String(byteStr));
						}
						if (rs.getBlob(2) != null) {
							blobObj = rs.getBlob(2);
							byte[] byteStr = blobObj.getBytes(1, (int) blobObj.length());
							arr.get(i).getField()[4] = (new String(byteStr));
						}
						
					}
					
				}
				
				//System.out.println("getReports::2:: executed");
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		return arr;
	}
	
	private String[] getUniqueList(String[] sList) {
		
				
		Set<String> set = new HashSet<String>(Arrays.asList(sList));
		String[] usList = (String[])(set.toArray(new String[set.size()]));
		
		
		return usList;
	}
	
	/*public String saveList(String roleid, String [] vmarkingto,String [] vsignedby,String [] vrefsubjectcode,String [] vfilesubjectcode,String [] vimarkingto,String [] vbranch) {
		String outMessage = "";
		String strSQL = "";

		DBConnection dbcon = new DBConnection();
		try {
			dbcon.openConnection();
			
			strSQL = " DELETE FROM  MSTPREFERREDLIST WHERE ROLEID='"+roleid+"'";
			//System.out.println("CustomReportDAO.saveReport() 1:--" + strSQL);
			dbcon.delete(strSQL);
			String [] markingto = getUniqueList(vmarkingto);		
			for (int i = 0; i < markingto.length; i++) {
			strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
					" VALUES ('"+markingto[i]+"','1','"+roleid+"',SYSDATE)"; //param[0]
			//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
			dbcon.insert(strSQL);
			}
			String [] signedby = getUniqueList(vsignedby);
			for (int i = 0; i < signedby.length; i++) {
				strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+signedby[i]+"','2','"+roleid+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);
			}
			String [] refsubjectcode = getUniqueList(vrefsubjectcode);
			for (int i = 0; i < refsubjectcode.length; i++) {
				strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+refsubjectcode[i]+"','3','"+roleid+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);
			}
			String [] filesubjectcode = getUniqueList(vfilesubjectcode);
			for (int i = 0; i < filesubjectcode.length; i++) {
				strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+filesubjectcode[i]+"','4','"+roleid+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);
			}
			
			String [] imarkingto = getUniqueList(vimarkingto);
			for (int i = 0; i < imarkingto.length; i++) {
				strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+imarkingto[i]+"','5','"+roleid+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);
			}
			
			String [] branch = getUniqueList(vbranch);
			for (int i = 0; i < branch.length; i++) {
				strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+branch[i]+"','6','"+roleid+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);
			}
			
			

			outMessage = "GRNSave operation successful.";

		} catch (SQLException sql) {
			dbcon.rollback();
			log.fatal(sql, sql);
			outMessage = "REDSave operation failure ERROR--> " + sql.getMessage();
			// (new CommonDAO()).generateMessage("TESTLOGIN",outMessage);
		} finally {
			dbcon.closeConnection();
		}

		return outMessage;
	}*/
	//soumen code
	public String saveList(String roleid, String [] vmarkingto,String [] vsignedby,String [] vrefsubjectcode,String [] vfilesubjectcode,String [] vimarkingto,String [] vbranch,String [] vsackby) {
		String outMessage = "";
		String strSQL = "";

		DBConnection dbcon = new DBConnection();
		PreparedStatement ps =null;
		int row=0,count=0;
		
		
			
			try{
			dbcon.openConnection();
			
			   /*strSQL = " DELETE FROM  MSTPREFERREDLIST WHERE ROLEID='"+roleid+"'";
			   //System.out.println("CustomReportDAO.saveReport() 1:--" + strSQL);
			   dbcon.delete(strSQL);*/
			
			   strSQL = " DELETE FROM  MSTPREFERREDLIST WHERE ROLEID=?";
			   ps=dbcon.setPrepStatement(strSQL);
			   ps.setString(1, roleid);
			   row=ps.executeUpdate();
			  // System.out.println("saveList::TOTAL ROWS DELETED:"+ row);
			}catch (SQLException sql) {
				
				dbcon.rollback();
				log.fatal(sql, sql);
				outMessage = "REDSave operation failure ERROR--> " + sql.getMessage();
				
			} finally {
				dbcon.closeConnection();
			}
			
			
			try{
			
			   dbcon.openConnection();
			   String [] markingto = getUniqueList(vmarkingto);
			   count=0;
			   strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES (?,'1',?,SYSDATE)"; //param[0]
			
			   ps=dbcon.setPrepStatement(strSQL);
			   //System.out.print("Total length of markingto "+markingto.length);
			   for (int i = 0; i < markingto.length; i++) {
			     	
			  /* strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
					" VALUES ('"+markingto[i]+"','1','"+roleid+"',SYSDATE)"; //param[0]
			   //System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
			   dbcon.insert(strSQL);*/
			
			   
			   ps.setString(1, markingto[i]);
			   ps.setString(2, roleid);
			   row=ps.executeUpdate();
			   count+=row;
			
			  }
			
			 // System.out.println("saveList::markingto::TOTAL ROWS INSERTED:"+ count);
			}catch (SQLException sql) {
				
				dbcon.rollback();
				log.fatal(sql, sql);
				outMessage = "REDSave operation failure ERROR--> " + sql.getMessage();
				
			} finally {
				dbcon.closeConnection();
			}
			  
			
			try{
				
				dbcon.openConnection();
		        count=0;
			    String [] signedby = getUniqueList(vsignedby);
			
			    strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES (?,'2',?,SYSDATE)"; //param[0]
				
				ps=dbcon.setPrepStatement(strSQL);
			    //System.out.print("Total length of signedby "+signedby.length);
			    for (int i = 0; i < signedby.length; i++) 
			    {
				/*strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+signedby[i]+"','2','"+roleid+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);*/
				
				
				ps.setString(1, signedby[i]);
				ps.setString(2, roleid);
				row=ps.executeUpdate();
				count+=row;
				
			    }
			   // System.out.println("saveList::signedby::TOTAL ROWS INSERTED:"+ count);
			
			}catch (SQLException sql) {
				
				dbcon.rollback();
				log.fatal(sql, sql);
				outMessage = "REDSave operation failure ERROR--> " + sql.getMessage();
				
			} finally {
				dbcon.closeConnection();
			}
			
			try{
				
				dbcon.openConnection();
		        count=0;
			    String [] ackby = getUniqueList(vsackby);
			
			    strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES (?,'7',?,SYSDATE)"; //param[0]
				
				ps=dbcon.setPrepStatement(strSQL);
			    //System.out.print("Total length of signedby "+signedby.length);
			    for (int i = 0; i < ackby.length; i++) 
			    {
				/*strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+signedby[i]+"','2','"+roleid+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);*/
				
				
				ps.setString(1, ackby[i]);
				ps.setString(2, roleid);
				row=ps.executeUpdate();
				count+=row;
				
			    }
			   // System.out.println("saveList::signedby::TOTAL ROWS INSERTED:"+ count);
			
			}catch (SQLException sql) {
				
				dbcon.rollback();
				log.fatal(sql, sql);
				outMessage = "REDSave operation failure ERROR--> " + sql.getMessage();
				
			} finally {
				dbcon.closeConnection();
			}
			
			try{
				dbcon.openConnection();
			    count=0;
			    String [] refsubjectcode = getUniqueList(vrefsubjectcode);
			    strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES (?,'3',?,SYSDATE)";
				
				  ps=dbcon.setPrepStatement(strSQL);
			    //System.out.print("Total length of refsubjectcode "+refsubjectcode.length);
			    for (int i = 0; i < refsubjectcode.length; i++) 
			    {
				  /*strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+refsubjectcode[i]+"','3','"+roleid+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);*/
				
				  
				  ps.setString(1, refsubjectcode[i]);
				  ps.setString(2, roleid);
				  row=ps.executeUpdate();
				  count+=row;
			   }
			   //System.out.println("saveList::refsubjectcode::TOTAL ROWS INSERTED:"+ count);
			
			}catch (SQLException sql) {
				
				dbcon.rollback();
				log.fatal(sql, sql);
				outMessage = "REDSave operation failure ERROR--> " + sql.getMessage();
				
			} finally {
				dbcon.closeConnection();
			}
			
			
	  try{
		    dbcon.openConnection();
			count=0;
			String [] filesubjectcode = getUniqueList(vfilesubjectcode);
			
			strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
					" VALUES (?,'4',?,SYSDATE)"; //param[0]
			ps=dbcon.setPrepStatement(strSQL);
			//System.out.print("Total length of filesubjectcode "+filesubjectcode.length);
			for (int i = 0; i < filesubjectcode.length; i++) {
				/*strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+filesubjectcode[i]+"','4','"+roleid+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);*/
				
				
				ps.setString(1, filesubjectcode[i]);
				ps.setString(2, roleid);
				row=ps.executeUpdate();
				count+=row;
			}
			//System.out.println("saveList::filesubjectcode::TOTAL ROWS INSERTED:"+ count);
		}catch (SQLException sql) {
				
				dbcon.rollback();
				log.fatal(sql, sql);
				outMessage = "REDSave operation failure ERROR--> " + sql.getMessage();
				
		} finally {
				dbcon.closeConnection();
		}
			
	   try{
		    dbcon.openConnection();
			count=0;
			String [] imarkingto = getUniqueList(vimarkingto);
			
			strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
					" VALUES (?,'5',?,SYSDATE)"; //param[0]
			
			ps=dbcon.setPrepStatement(strSQL);
			//System.out.print("Total length of imarkingto "+imarkingto.length);
			for (int i = 0; i < imarkingto.length; i++) {
				/*strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+imarkingto[i]+"','5','"+roleid+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);*/
				
				
				ps.setString(1, imarkingto[i]);
				ps.setString(2, roleid);
				row=ps.executeUpdate();
				count+=row;
				
			}
			//System.out.println("saveList::imarkingto::TOTAL ROWS INSERTED:"+ count);
		 }catch (SQLException sql) {
				
				dbcon.rollback();
				log.fatal(sql, sql);
				outMessage = "REDSave operation failure ERROR--> " + sql.getMessage();
				
		} finally {
				dbcon.closeConnection();
		}
			
			
		try{
			dbcon.openConnection();
			count=0;
			String [] branch = getUniqueList(vbranch);
			//System.out.print("Total length of branch "+branch.length);
			strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
					" VALUES (?,'6',?,SYSDATE)"; //param[0]
			
			ps=dbcon.setPrepStatement(strSQL);
			for (int i = 0; i < branch.length; i++) {
				/*strSQL = " INSERT INTO  MSTPREFERREDLIST(PREFERREDID,PREFERREDTYPE,ROLEID,CHANGEDATE)" +
						" VALUES ('"+branch[i]+"','6','"+roleid+"',SYSDATE)"; //param[0]
				//System.out.println("CustomReportDAO.saveReport() 2:--" + strSQL);
				dbcon.insert(strSQL);*/
				
				
				ps.setString(1, branch[i]);
				ps.setString(2, roleid);
				row=ps.executeUpdate();
				count+=row;
				
				
		    }
			//System.out.println("saveList::branch::TOTAL ROWS INSERTED:"+ count);
		}catch (SQLException sql) {
				
				dbcon.rollback();
				log.fatal(sql, sql);
				outMessage = "REDSave operation failure ERROR--> " + sql.getMessage();
				
		} finally {
				dbcon.closeConnection();
		}
			
			
			

		outMessage = "GRNSave operation successful.";

		

		return outMessage;
	}
	
	/*public ArrayList<MstRole> populateDeptList(String deptcode) {

		DBConnection dbCon = new DBConnection();
		ArrayList<MstRole> arr = new ArrayList<MstRole>();
		try {

			String strSQL = " SELECT A.ROLEID, A.ROLENAME "+ 
				" FROM MSTROLE A WHERE " +
				" A.DEPTCODE=DECODE('"+deptcode+"','',A.DEPTCODE,'"+deptcode+"') ORDER BY 2";

			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				MstRole mr = new MstRole();
				mr.setROLEID(rs.getString(1));
				mr.setROLENAME(rs.getString(2));
				arr.add(mr);
			}

		} catch (Exception e) {
			e.printStackTrace();

			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return arr;
	}*/
	//souemn code
	public ArrayList<MstRole> populateDeptList(String deptcode) {

		DBConnection dbcon = new DBConnection();
		ArrayList<MstRole> arr = new ArrayList<MstRole>();
		PreparedStatement ps =null;
		
		try {

			/*String strSQL = " SELECT A.ROLEID, A.ROLENAME "+ 
				" FROM MSTROLE A WHERE " +
				" A.DEPTCODE=DECODE('"+deptcode+"','',A.DEPTCODE,'"+deptcode+"') ORDER BY 2";
			
			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);*/
			
			dbcon.openConnection();
			String strSQL = " SELECT A.ROLEID, A.ROLENAME "+ 
					" FROM MSTROLE A WHERE " +
					" A.DEPTCODE=DECODE(?,'',A.DEPTCODE,?) ORDER BY 2";
			
			ps=dbcon.setPrepStatement(strSQL);
			ps.setString(1, deptcode);
			ps.setString(2, deptcode);
			ResultSet rs =ps.executeQuery();
			while (rs.next()) {
				MstRole mr = new MstRole();
				mr.setROLEID(rs.getString(1));
				mr.setROLENAME(rs.getString(2));
				arr.add(mr);
			}
			rs.close();
			//System.out.println(" populateDeptList executed");

		} catch (Exception e) {
			e.printStackTrace();

			dbcon.rollback();
		} finally {
			dbcon.closeConnection();
		}
		return arr;
	}
	
	
/*public ArrayList<MstRole> populateAuthList(String deptcode,String authlevel) {

		DBConnection dbCon = new DBConnection();
		ArrayList<MstRole> arr = new ArrayList<MstRole>();
		try {

			String strSQL = " SELECT A.ROLEID, A.ROLENAME "+ 
				" FROM MSTROLE A WHERE " +
				" A.DEPTCODE=DECODE('"+deptcode+"','',A.DEPTCODE,'"+deptcode+"') AND" +
				" A.AUTHLEVEL=DECODE('"+authlevel+"','',A.AUTHLEVEL,'"+authlevel+"') ORDER BY 2";

			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);
			while (rs.next()) {
				MstRole mr = new MstRole();
				mr.setROLEID(rs.getString(1));
				mr.setROLENAME(rs.getString(2));
				arr.add(mr);
			}

		} catch (Exception e) {
			e.printStackTrace();

			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return arr;
	}*/
	//soumen code
	public ArrayList<MstRole> populateAuthList(String deptcode,String authlevel) {

		DBConnection dbCon = new DBConnection();
		ArrayList<MstRole> arr = new ArrayList<MstRole>();
		PreparedStatement ps =null;
		
		try {

			/*String strSQL = " SELECT A.ROLEID, A.ROLENAME "+ 
				" FROM MSTROLE A WHERE " +
				" A.DEPTCODE=DECODE('"+deptcode+"','',A.DEPTCODE,'"+deptcode+"') AND" +
				" A.AUTHLEVEL=DECODE('"+authlevel+"','',A.AUTHLEVEL,'"+authlevel+"') ORDER BY 2";

			dbCon.openConnection();
			log.debug(strSQL);
			ResultSet rs = dbCon.select(strSQL);*/
			
			dbCon.openConnection();
			String strSQL = " SELECT A.ROLEID, A.ROLENAME "+ 
					" FROM MSTROLE A WHERE " +
					" A.DEPTCODE=DECODE(?,'',A.DEPTCODE,?) AND" +
					" A.AUTHLEVEL=DECODE(?,'',A.AUTHLEVEL,?) ORDER BY 2";

			ps=dbCon.setPrepStatement(strSQL);
			ps.setString(1, deptcode);
			ps.setString(2, deptcode);
			ps.setString(3, authlevel);
			ps.setString(4, authlevel);
			
			ResultSet rs =ps.executeQuery();
			while (rs.next()) {
				MstRole mr = new MstRole();
				mr.setROLEID(rs.getString(1));
				mr.setROLENAME(rs.getString(2));
				arr.add(mr);
			}
			
			rs.close();
			//System.out.println(" populateAuthList executed");

		} catch (Exception e) {
			e.printStackTrace();

			dbCon.rollback();
		} finally {
			dbCon.closeConnection();
		}
		return arr;
	}
	
	
	public String handleNull(String str)
	{
		return str != null ? str : "";
	}
	
	
}
