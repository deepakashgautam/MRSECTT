package in.org.cris.mrsectt.Beans;

public class MstLogin {

	private String LOGINID = "" ;
	private String PASSWORD = "" ;
	private String PASSWORDENC = "" ;
	private String LOGINASROLEID = "" ;
	private String ISACTIVELOGIN = "" ;
	private String LASTLOGINDATE = "" ;
	private String STARTDATE = "" ;
	private String ENDDATE = "" ;
	private String LOGINDESIGNATION = "" ;
	private String LOGINNAME = "" ;
	private String TENUREOFFICENAME = "" ;
	private String TENUREOFFICEADDRESS = "" ;
	private String ISCONF = "" ;
	private String ISREPLY = "" ;
	
	//non-database field
	private String ISVALIDUSER = "";
	private String THEME = "";
	private String TENUREID = "";
	private String LOGINASROLENAME = "";
	private String TENURESTARTDATE = "";
	private String DEPTCODE = "";
	private String REFCLASS = "";
	private String THEMECOLOR = "";
	
	
	
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	
	
	public String getISREPLY() {
		return ISREPLY;
	}
	public void setISREPLY(String isreply) {
		ISREPLY = isreply;
	}
	public String getTHEMECOLOR() {
		return THEMECOLOR;
	}
	public void setTHEMECOLOR(String themecolor) {
		THEMECOLOR = themecolor;
	}
	public String getREFCLASS() {
		return REFCLASS;
	}
	public void setREFCLASS(String refclass) {
		REFCLASS = refclass;
	}
	public String getTENUREOFFICEADDRESS() {
		return TENUREOFFICEADDRESS;
	}
	public void setTENUREOFFICEADDRESS(String tenureofficeaddress) {
		TENUREOFFICEADDRESS = tenureofficeaddress;
	}
	public String getDEPTCODE() {
		return DEPTCODE;
	}
	public void setDEPTCODE(String deptcode) {
		DEPTCODE = deptcode;
	}
	public String getLOGINID() {
		return LOGINID;
	}
	public void setLOGINID(String loginid) {
		LOGINID = loginid;
	}
	public String getPASSWORDENC() {
		return PASSWORDENC;
	}
	public void setPASSWORDENC(String password) {
		PASSWORDENC = password;
	}
	public String getLOGINASROLEID() {
		return LOGINASROLEID;
	}
	public void setLOGINASROLEID(String loginasroleid) {
		LOGINASROLEID = loginasroleid;
	}
	public String getISACTIVELOGIN() {
		return ISACTIVELOGIN;
	}
	public void setISACTIVELOGIN(String isactivelogin) {
		ISACTIVELOGIN = isactivelogin;
	}
	public String getLASTLOGINDATE() {
		return LASTLOGINDATE;
	}
	public void setLASTLOGINDATE(String lastlogindate) {
		LASTLOGINDATE = lastlogindate;
	}
	public String getSTARTDATE() {
		return STARTDATE;
	}
	public void setSTARTDATE(String startdate) {
		STARTDATE = startdate;
	}
	public String getENDDATE() {
		return ENDDATE;
	}
	public void setENDDATE(String enddate) {
		ENDDATE = enddate;
	}
	public String getLOGINDESIGNATION() {
		return LOGINDESIGNATION;
	}
	public void setLOGINDESIGNATION(String logindesignation) {
		LOGINDESIGNATION = logindesignation;
	}
	public String getLOGINNAME() {
		return LOGINNAME;
	}
	public void setLOGINNAME(String loginname) {
		LOGINNAME = loginname;
	}
	public String getISVALIDUSER() {
		return ISVALIDUSER;
	}
	public void setISVALIDUSER(String isvaliduser) {
		ISVALIDUSER = isvaliduser;
	}
	public String getTHEME() {
		return THEME;
	}
	public void setTHEME(String theme) {
		THEME = theme;
	}
	public String getTENUREID() {
		return TENUREID;
	}
	public void setTENUREID(String tenureid) {
		TENUREID = tenureid;
	}
	public String getLOGINASROLENAME() {
		return LOGINASROLENAME;
	}
	public void setLOGINASROLENAME(String loginasrolename) {
		LOGINASROLENAME = loginasrolename;
	}
	public String getTENUREOFFICENAME() {
		return TENUREOFFICENAME;
	}
	public void setTENUREOFFICENAME(String tenureofficename) {
		TENUREOFFICENAME = tenureofficename;
	}
	public String getTENURESTARTDATE() {
		return TENURESTARTDATE;
	}
	public void setTENURESTARTDATE(String tenurestartdate) {
		TENURESTARTDATE = tenurestartdate;
	}
	public String getISCONF() {
		return ISCONF;
	}
	public void setISCONF(String isconf) {
		ISCONF = isconf;
	}
	
}
