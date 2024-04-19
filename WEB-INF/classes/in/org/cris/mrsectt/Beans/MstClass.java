package in.org.cris.mrsectt.Beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import in.org.cris.mrsectt.dao.CommonDAO;
import in.org.cris.mrsectt.dao.TrnReferenceDAO;
import in.org.cris.mrsectt.dbConnection.DBConnection;

public class MstClass {
	static Logger log = LogManager.getLogger(TrnReferenceDAO.class);
	

	 private String YEAR = ""; 
	 private String ROLEID = ""; 
	 private String TENUREID = ""; 
	 private String REFCLASS = ""; 
	 private String INOUT = "";
	 private String CLASSDESCRIPTION = "";
	 
	 //non database field
	 private String COUNTER = "";
	 
	 
	public String getYEAR() {
		return YEAR;
	}
	public void setYEAR(String year) {
		YEAR = year;
	}
	public String getROLEID() {
		return ROLEID;
	}
	public void setROLEID(String roleid) {
		ROLEID = roleid;
	}
	public String getTENUREID() {
		return TENUREID;
	}
	public void setTENUREID(String tenureid) {
		TENUREID = tenureid;
	}
	public String getREFCLASS() {
		return REFCLASS;
	}
	public void setREFCLASS(String refclass) {
		REFCLASS = refclass;
	}
	public String getINOUT() {
		return INOUT;
	}
	public void setINOUT(String inout) {
		INOUT = inout;
	}
	public String getCOUNTER() {
		return COUNTER;
	}
	public void setCOUNTER(String counter) {
		COUNTER = counter;
	} 

	
	public static String getRefCountSequence(String roleId, String TenureId, String refClass, int year, DBConnection dbCon) throws SQLException {
		String newRefCount = "";
		
			PreparedStatement ps = null;
			PreparedStatement ps1 = null;
		
			String strSql = "SELECT (REFCOUNTER + 1) REFCOUNTER FROM MSTCLASS WHERE REFCLASS = ?" +
			" AND TENUREID = ?" +
			" AND ROLEID = ? " +
			" AND YEAR = ? ";
			
			String QueryUpdate = " UPDATE MSTCLASS SET REFCOUNTER = (REFCOUNTER + 1) WHERE REFCLASS = ?" +
					" AND TENUREID = ?" +
					" AND ROLEID = ? " +
					" AND YEAR = ?";

			//newRefCount = CommonDAO.getStringParam(strSql, dbCon);
			
			try{
				ps = dbCon.setPrepStatement(strSql);
				ps.setString(1, refClass);
				ps.setString(2, TenureId);
				ps.setString(3, roleId);
				ps.setInt(4, year);
				
				//log.debug(strSql);
				//log.debug(QueryUpdate);
				
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					newRefCount = CommonDAO.handleNull(rs.getString(1));
				}
				
				ps1 = dbCon.setPrepStatement(QueryUpdate);
				ps1.setString(1, refClass);
				ps1.setString(2, TenureId);
				ps1.setString(3, roleId);
				ps1.setInt(4, year);
				ps1.executeUpdate();
				rs.close();
				
			} catch (Exception e){
				log.fatal(e,e);
			} finally{
				ps.close();
				ps1.close();
			}
			
			// now update the counter to next counter

			//log.debug(QueryUpdate);
			//dbCon.update(QueryUpdate);

		
		return newRefCount;
		
	}
	public String getCLASSDESCRIPTION() {
		return CLASSDESCRIPTION;
	}
	public void setCLASSDESCRIPTION(String classdescription) {
		CLASSDESCRIPTION = classdescription;
	}
	
	
}
