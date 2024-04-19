package in.org.cris.mrsectt.Beans;

import java.sql.SQLException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import in.org.cris.mrsectt.dao.CommonDAO;
import in.org.cris.mrsectt.dao.TrnReferenceDAO;
import in.org.cris.mrsectt.dbConnection.DBConnection;

public class MstPreferredList {
	static Logger log = LogManager.getLogger(MstPreferredList.class);
	

	 private String PREFERREDID	 = ""; 
	 private String PREFERREDTYPE	 = ""; 
	 private String DEPTCODE = ""; 
	 private String CHANGEDATE = "";
	public String getPREFERREDID() {
		return PREFERREDID;
	}
	public void setPREFERREDID(String preferredid) {
		PREFERREDID = preferredid;
	}
	public String getPREFERREDTYPE() {
		return PREFERREDTYPE;
	}
	public void setPREFERREDTYPE(String preferredtype) {
		PREFERREDTYPE = preferredtype;
	}
	public String getDEPTCODE() {
		return DEPTCODE;
	}
	public void setDEPTCODE(String deptcode) {
		DEPTCODE = deptcode;
	}
	public String getCHANGEDATE() {
		return CHANGEDATE;
	}
	public void setCHANGEDATE(String changedate) {
		CHANGEDATE = changedate;
	} 
	 
	 
	 
}
