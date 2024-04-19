package in.org.cris.mrsectt.Beans;

public class MstRole {
	
	private String ROLEID = "";
	private String ROLENAME = "";
	private String DEPTCODE = "";
	private String AUTHLEVEL = "";
	private String FNAME = "";
	
	
	public String getFNAME() {
		return FNAME;
	}
	public void setFNAME(String fname) {
		FNAME = fname;
	}
	public String getROLEID() {
		return ROLEID;
	}
	public void setROLEID(String roleid) {
		ROLEID = roleid;
	}
	public String getROLENAME() {
		return ROLENAME;
	}
	public void setROLENAME(String rolename) {
		ROLENAME = rolename;
	}
	public String getDEPTCODE() {
		return DEPTCODE;
	}
	public void setDEPTCODE(String deptcode) {
		DEPTCODE = deptcode;
	}
	public String getAUTHLEVEL() {
		return AUTHLEVEL;
	}
	public void setAUTHLEVEL(String authlevel) {
		AUTHLEVEL = authlevel;
	}
}
