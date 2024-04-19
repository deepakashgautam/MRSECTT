package in.org.cris.mrsectt.Beans;

public class TrnFileRef {
	
	
	private String FMID	 = "";
	private String REFID  = "";
	
	//non database field
	private String REFNO  = "";
	private String INCOMINGDATE  = "";
	private String REFERENCENAME  = "";
	private String SUBJECTCODE  = "";
	private String SUBJECT  = "";
	private String STATUS  = "";
	private String VIPSTATUS  = "";
	private String STATENAME  = "";
	
	//new code added for compliance
	
	private String compliance="";
	private String complianceremarks="";
	
	
	
	public String getComplianceremarks() {
		return complianceremarks;
	}
	public void setComplianceremarks(String complianceremarks) {
		this.complianceremarks = complianceremarks;
	}
	
	public String getCompliance() {
		return compliance;
	}
	public void setCompliance(String compliance) {
		this.compliance = compliance;
	}
	
	//end of new code
	
	
	public String getVIPSTATUS() {
		return VIPSTATUS;
	}
	public String getINCOMINGDATE() {
		return INCOMINGDATE;
	}
	public void setINCOMINGDATE(String incomingdate) {
		INCOMINGDATE = incomingdate;
	}
	public void setVIPSTATUS(String vipstatus) {
		VIPSTATUS = vipstatus;
	}
	public String getSTATENAME() {
		return STATENAME;
	}
	public void setSTATENAME(String statename) {
		STATENAME = statename;
	}
	public String getFMID() {
		return FMID;
	}
	public String getREFNO() {
		return REFNO;
	}
	public void setREFNO(String refno) {
		REFNO = refno;
	}
	public String getREFERENCENAME() {
		return REFERENCENAME;
	}
	public void setREFERENCENAME(String referencename) {
		REFERENCENAME = referencename;
	}
	public String getSUBJECTCODE() {
		return SUBJECTCODE;
	}
	public void setSUBJECTCODE(String subjectcode) {
		SUBJECTCODE = subjectcode;
	}
	public String getSUBJECT() {
		return SUBJECT;
	}
	public void setSUBJECT(String subject) {
		SUBJECT = subject;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String status) {
		STATUS = status;
	}
	public void setFMID(String fmid) {
		FMID = fmid;
	}
	public String getREFID() {
		return REFID;
	}
	public void setREFID(String refid) {
		REFID = refid;
	}
	
	
	

}
