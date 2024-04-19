package in.org.cris.mrsectt.Beans;

import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import in.org.cris.mrsectt.dao.CommonDAO;
import in.org.cris.mrsectt.dao.TrnReferenceDAO;
import in.org.cris.mrsectt.dbConnection.DBConnection;

public class MstClass_old {
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
		

		
			String strSql = "SELECT (REFCOUNTER + 1) REFCOUNTER FROM MSTCLASS WHERE REFCLASS = '"+refClass+"'" +
			" AND TENUREID = '"+TenureId+"'" +
			" AND ROLEID = '"+roleId+"' " +
			" AND YEAR = '"+year+"' ";

			newRefCount = CommonDAO.getStringParam(strSql, dbCon);
			
			
			
			// now update the counter to next counter
			String QueryUpdate = " UPDATE MSTCLASS SET REFCOUNTER = (REFCOUNTER + 1) WHERE REFCLASS = '"+refClass+"'" +
						" AND TENUREID = '"+TenureId+"'" +
						" AND ROLEID = '"+roleId+"' " +
						" AND YEAR = '"+year+"'";
			
			log.debug(QueryUpdate);
			dbCon.update(QueryUpdate);
			
			
		
		return newRefCount;
		
	}
	public String getCLASSDESCRIPTION() {
		return CLASSDESCRIPTION;
	}
	public void setCLASSDESCRIPTION(String classdescription) {
		CLASSDESCRIPTION = classdescription;
	}
	
	
}
