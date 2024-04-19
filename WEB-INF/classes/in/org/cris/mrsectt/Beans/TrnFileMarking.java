package in.org.cris.mrsectt.Beans;

public class TrnFileMarking {

	private String FMID = "";
	private String MARKINGSEQUENCE = "";
	private String MARKINGFROM = "";
	private String MARKINGTO = "";
	private String MARKINGDATE = "";
	private String MARKINGREMARK = "";
	private String SUBJECTCODE = "";
	private String SUBJECT = "";
	private String ACTION = "";
	private String ACTIONDATE = "";
	private String MARKINGTYPE = "";
	private String STATUSCHANGEUSER = "";

	// non database field
	private String FILENO = "";

	public String getFILENO() {
		return FILENO;
	}

	public void setFILENO(String fileno) {
		FILENO = fileno;
	}

	public String getMARKINGTYPE() {
		return MARKINGTYPE;
	}

	public void setMARKINGTYPE(String markingtype) {
		MARKINGTYPE = markingtype;
	}

	public String getFMID() {
		return FMID;
	}

	public void setFMID(String fmid) {
		FMID = fmid;
	}

	public String getMARKINGSEQUENCE() {
		return MARKINGSEQUENCE;
	}

	public void setMARKINGSEQUENCE(String markingsequence) {
		MARKINGSEQUENCE = markingsequence;
	}

	public String getMARKINGFROM() {
		return MARKINGFROM;
	}

	public void setMARKINGFROM(String markingfrom) {
		MARKINGFROM = markingfrom;
	}

	public String getMARKINGTO() {
		return MARKINGTO;
	}

	public void setMARKINGTO(String markingto) {
		MARKINGTO = markingto;
	}

	public String getMARKINGDATE() {
		return MARKINGDATE;
	}

	public void setMARKINGDATE(String markingdate) {
		MARKINGDATE = markingdate;
	}

	public String getMARKINGREMARK() {
		return MARKINGREMARK;
	}

	public void setMARKINGREMARK(String markingremark) {
		MARKINGREMARK = markingremark;
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

	public String getACTION() {
		return ACTION;
	}

	public void setACTION(String action) {
		ACTION = action;
	}

	public String getACTIONDATE() {
		return ACTIONDATE;
	}

	public void setACTIONDATE(String actiondate) {
		ACTIONDATE = actiondate;
	}

	public String getSTATUSCHANGEUSER() {
		return STATUSCHANGEUSER;
	}

	public void setSTATUSCHANGEUSER(String sTATUSCHANGEUSER) {
		STATUSCHANGEUSER = sTATUSCHANGEUSER;
	}

}
