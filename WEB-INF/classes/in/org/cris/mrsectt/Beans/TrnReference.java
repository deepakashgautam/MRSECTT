package in.org.cris.mrsectt.Beans;

import java.util.ArrayList;
import in.org.cris.mrsectt.Beans.TrnMarking;

public class TrnReference {
	
	@Override
	public String toString() {
		return "TrnReference [REFID=" + REFID + ", TENUREID=" + TENUREID + ", REFNO=" + REFNO + ", ROLEID=" + ROLEID
				+ ", REFCLASS=" + REFCLASS + ", REFCOUNT=" + REFCOUNT + ", INCOMINGDATE=" + INCOMINGDATE
				+ ", REFERENCENAME=" + REFERENCENAME + ", LETTERDATE=" + LETTERDATE + ", VIPSTATUS=" + VIPSTATUS
				+ ", STATECODE=" + STATECODE + ", ACKDATE=" + ACKDATE + ", ACKBY=" + ACKBY + ", REFCATEGORY="
				+ REFCATEGORY + ", URGENCY=" + URGENCY + ", LINKREFID=" + LINKREFID + ", NOTECREATOR=" + NOTECREATOR
				+ ", SECURITYGRADING=" + SECURITYGRADING + ", SIGNEDBY=" + SIGNEDBY + ", SIGNEDON=" + SIGNEDON
				+ ", REMARKS=" + REMARKS + ", ODSPLACE=" + ODSPLACE + ", ODSRAILWAY=" + ODSRAILWAY + ", ODSDATE="
				+ ODSDATE + ", LOGINID=" + LOGINID + ", CHANGEDATE=" + CHANGEDATE + ", ISBUDGET=" + ISBUDGET
				+ ", LANGUAGE=" + LANGUAGE + ", FMID=" + FMID + ", FILECOUNTER=" + FILECOUNTER + ", REGISTRATIONNO="
				+ REGISTRATIONNO + ", FILENO=" + FILENO + ", IMARKINGTO=" + IMARKINGTO + ", IMARKINGON=" + IMARKINGON
				+ ", FILESTATUS1=" + FILESTATUS1 + ", FILESTATUS2=" + FILESTATUS2 + ", REPLYTYPE=" + REPLYTYPE
				+ ", REASON=" + REASON + ", DMARKINGTO=" + DMARKINGTO + ", DMARKINGDATE=" + DMARKINGDATE
				+ ", REGISTRATIONDATE=" + REGISTRATIONDATE + ", SUBJECTCODE=" + SUBJECTCODE + ", SUBJECT=" + SUBJECT
				+ ", STATUSREMARK=" + STATUSREMARK + ", RECIEVEDBY=" + RECIEVEDBY + ", TAG1=" + TAG1 + ", TAG2=" + TAG2
				+ ", TAG3=" + TAG3 + ", TAG4=" + TAG4 + ", TARGETDATE=" + TARGETDATE + ", ISCONF=" + ISCONF
				+ ", TXT_NOTE=" + TXT_NOTE + ", SIGNEDBY_YS=" + SIGNEDBY_YS + ", uploadImage=" + uploadImage
				+ ", VIPPARTY=" + VIPPARTY + ", ADDVIPTYPE=" + ADDVIPTYPE + ", ADDVIPID=" + ADDVIPID
				+ ", VIPSTATUSDESC=" + VIPSTATUSDESC + ", EOFFICEREFNO=" + EOFFICEREFNO + ", EOFFICEMSG=" + EOFFICEMSG
				+ ", EOFFICERECEIPTNO=" + EOFFICERECEIPTNO + ", keywords1=" + keywords1 + ", keywords2=" + keywords2
				+ ", keywords3=" + keywords3 + ", k1=" + k1 + ", k2=" + k2 + ", k3=" + k3 + ", btnClick=" + btnClick
				+ ", LOGINASROLENAME=" + LOGINASROLENAME + ", LINKREFNO=" + LINKREFNO + ", MARKINGTO=" + MARKINGTO
				+ ", MARKINGDATE=" + MARKINGDATE + ", ACTION=" + ACTION + ", REFERENCETYPE=" + REFERENCETYPE
				+ ", ISATTACHMENT=" + ISATTACHMENT + ", ISATTACHMENTFILE=" + ISATTACHMENTFILE + ", REPLY=" + REPLY
				+ ", TOURREMARKS=" + TOURREMARKS + ", COMPLIANCE=" + COMPLIANCE + ", COMPLIANCEREMARKS="
				+ COMPLIANCEREMARKS + ", COMPLIANCEDATE=" + COMPLIANCEDATE + ", EOFFICEFILENO=" + EOFFICEFILENO
				+ ", EOFFIEFLAG=" + EOFFIEFLAG + ", URL=" + URL + ", MARKINGBEAN=" + MARKINGBEAN + ", markingBeanList="
				+ markingBeanList + ", budgetBeanList=" + budgetBeanList + ", attachmentBeanList=" + attachmentBeanList
				+ ", ySlipBeanList=" + ySlipBeanList + ", ADDRESSENGLISH=" + ADDRESSENGLISH + ", SALUTATION="
				+ SALUTATION + "]";
	}
	private String REFID = "";
	private String refidencr ="";
	public String getRefidencr() {
		return refidencr;
	}
	public void setRefidencr(String refidencr) {
		this.refidencr = refidencr;
	}
	private String TENUREID = "";
	private String REFNO = "";
	private String ROLEID = "";
	private String REFCLASS = "";
	private String REFCOUNT = "";
	private String INCOMINGDATE = "";
	private String REFERENCENAME = "";
	private String LETTERDATE = "";
	private String VIPSTATUS = "";
	private String STATECODE = "";
	private String ACKDATE = "";
	private String ACKBY = "";
	private String REFCATEGORY = "";
	private String URGENCY = "";
	private String LINKREFID = "";
	private String NOTECREATOR = "";
	private String SECURITYGRADING = "";
	private String SIGNEDBY = "";
	private String SIGNEDON = "";
	private String REMARKS = "";
	private String ODSPLACE = "";
	private String ODSRAILWAY = "";
	private String ODSDATE = "";
	private String LOGINID = "";
	private String CHANGEDATE = "";
	private String ISBUDGET = "";
	private String LANGUAGE = "";
	private String FMID	 = "";
	private String fmidenc="";
	public String getFmidenc() {
		return fmidenc;
	}
	public void setFmidenc(String fmidenc) {
		this.fmidenc = fmidenc;
	}
	private String FILECOUNTER	 = "";    
	private String REGISTRATIONNO	 = "";            
	private String FILENO	 = "";   
	private String IMARKINGTO	 = "";        
	private String IMARKINGON	 = "";
	private String FILESTATUS1	 = "";        
	private String FILESTATUS2	 = "";
	private String REPLYTYPE	 = "";        
	private String REASON	 = "";        
	private String DMARKINGTO	 = "";        
	private String DMARKINGDATE	 = "";        
	private String REGISTRATIONDATE	 = "";   
	private String SUBJECTCODE	 = "";
	private String SUBJECT	 = "";
	private String STATUSREMARK	 = "";
	private String RECIEVEDBY	 = "";
	private String TAG1	 = "";
	private String TAG2	 = "";
	private String TAG3	 = "";
	private String TAG4	 = "";
	private String TARGETDATE	 = "";
	private String ISCONF = "";
	private String TXT_NOTE = "";
	private String SIGNEDBY_YS = "";
	private String uploadImage = "";
	private String VIPPARTY="";
	private String ADDVIPTYPE="";
	private String ADDVIPID="";
	private String VIPSTATUSDESC="";
	private String EOFFICEREFNO =""; 
	private String EOFFICEMSG =""; 
	
	private String EOFFICERECEIPTNO="";
	
	
	private String keywords1="";
	private String keywords2="";
	private String keywords3="";
	private String k1="";
	
	private String k2="";
	private String k3="";
	
	//Non-database field
	private String btnClick = "";
	private String LOGINASROLENAME = "";
	private String LINKREFNO = "";
	private String MARKINGTO = "";
	private String MARKINGDATE = "";
	private String ACTION = "";
	private String REFERENCETYPE = "";
	private String ISATTACHMENT = "";
	private String ISATTACHMENTFILE = "";
	private String REPLY = "";
	private String TOURREMARKS="";
	private String COMPLIANCE = "";
	private String COMPLIANCEREMARKS = "";
	private String COMPLIANCEDATE="";
	private String EOFFICEFILENO="";
	private String EOFFIEFLAG="";
	private String URL="";
	private String LETTERNO="";
	
	
	public String getLETTERNO() {
		return LETTERNO;
	}
	public void setLETTERNO(String lETTERNO) {
		LETTERNO = lETTERNO;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getEOFFICERECEIPTNO() {
		return EOFFICERECEIPTNO;
	}
	public void setEOFFICERECEIPTNO(String eOFFICERECEIPTNO) {
		EOFFICERECEIPTNO = eOFFICERECEIPTNO;
	}
	public String getEOFFICEFILENO() {
		return EOFFICEFILENO;
	}
	public void setEOFFICEFILENO(String eOFFICEFILENO) {
		EOFFICEFILENO = eOFFICEFILENO;
	}
	public String getCOMPLIANCE() {
		return COMPLIANCE;
	}
	public void setCOMPLIANCE(String cOMPLIANCE) {
		COMPLIANCE = cOMPLIANCE;
	}
	public String getCOMPLIANCEREMARKS() {
		return COMPLIANCEREMARKS;
	}
	public void setCOMPLIANCEREMARKS(String cOMPLIANCEREMARKS) {
		COMPLIANCEREMARKS = cOMPLIANCEREMARKS;
	}
	public String getCOMPLIANCEDATE() {
		return COMPLIANCEDATE;
	}
	public void setCOMPLIANCEDATE(String cOMPLIANCEDATE) {
		COMPLIANCEDATE = cOMPLIANCEDATE;
	}
	public String getEOFFICEMSG() {
		return EOFFICEMSG;
	}
	public void setEOFFICEMSG(String eOFFICEMSG) {
		EOFFICEMSG = eOFFICEMSG;
	}
	public String getEOFFICEREFNO() {
		return EOFFICEREFNO;
	}
	public void setEOFFICEREFNO(String eOFFICEREFNO) {
		EOFFICEREFNO = eOFFICEREFNO;
	}
	
	public String getTOURREMARKS() {
		return TOURREMARKS;
	}
	public void setTOURREMARKS(String tOURREMARKS) {
		TOURREMARKS = tOURREMARKS;
	}
	private TrnMarking MARKINGBEAN = new TrnMarking();
	
	private ArrayList<TrnMarking> markingBeanList =  new ArrayList<TrnMarking>(); 
	private ArrayList<TrnBudget> budgetBeanList =  new ArrayList<TrnBudget>();
	private ArrayList<TrnAttachment> attachmentBeanList =  new ArrayList<TrnAttachment>();
	private ArrayList<TrnYellowSlip> ySlipBeanList =  new ArrayList<TrnYellowSlip>();
	
	public String getEOFFIEFLAG() {
		return EOFFIEFLAG;
	}
	public void setEOFFIEFLAG(String eOFFIEFLAG) {
		EOFFIEFLAG = eOFFIEFLAG;
	}
	
	private String ADDRESSENGLISH ="";
	private String SALUTATION="";
	
	public String getVIPSTATUSDESC() {
		return VIPSTATUSDESC;
	}
	public void setVIPSTATUSDESC(String vIPSTATUSDESC) {
		VIPSTATUSDESC = vIPSTATUSDESC;
	}
	public String getADDRESSENGLISH() {
		return ADDRESSENGLISH;
	}
	public void setADDRESSENGLISH(String aDDRESSENGLISH) {
		ADDRESSENGLISH = aDDRESSENGLISH;
	}
	public String getSALUTATION() {
		return SALUTATION;
	}
	public void setSALUTATION(String sALUTATION) {
		SALUTATION = sALUTATION;
	}
	public String getVIPPARTY() {
		return VIPPARTY;
	}
	public void setVIPPARTY(String vipparty) {
		VIPPARTY = vipparty;
	}
	public String getISATTACHMENTFILE() {
		return ISATTACHMENTFILE;
	}
	public void setISATTACHMENTFILE(String isattachmentfile) {
		ISATTACHMENTFILE = isattachmentfile;
	}
	public String getREPLY() {
		return REPLY;
	}
	public void setREPLY(String reply) {
		REPLY = reply;
	}
	public String getISATTACHMENT() {
		return ISATTACHMENT;
	}
	public void setISATTACHMENT(String isattachment) {
		ISATTACHMENT = isattachment;
	}
	public String getUploadImage() {
		return uploadImage;
	}
	public void setUploadImage(String uploadImage) {
		this.uploadImage = uploadImage;
	}
	public ArrayList<TrnYellowSlip> getYSlipBeanList() {
		return ySlipBeanList;
	}
	
	public String getADDVIPTYPE() {
		return ADDVIPTYPE;
	}
	public void setADDVIPTYPE(String addviptype) {
		ADDVIPTYPE = addviptype;
	}
	public String getADDVIPID() {
		return ADDVIPID;
	}
	public void setADDVIPID(String addvipid) {
		ADDVIPID = addvipid;
	}
	public void setYSlipBeanList(ArrayList<TrnYellowSlip> slipBeanList) {
		ySlipBeanList = slipBeanList;
	}
	public String getREFERENCETYPE() {
		return REFERENCETYPE;
	}
	public void setREFERENCETYPE(String referencetype) {
		REFERENCETYPE = referencetype;
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
	public String getACTION() {
		return ACTION;
	}
	public void setACTION(String action) {
		ACTION = action;
	}
	public String getREFID() {
		return REFID;
	}
	public void setREFID(String refid) {
		REFID = refid;
	}
	public String getTENUREID() {
		return TENUREID;
	}
	public void setTENUREID(String tenureid) {
		TENUREID = tenureid;
	}
	public String getREFNO() {
		return REFNO;
	}
	public void setREFNO(String refno) {
		REFNO = refno;
	}
	public String getROLEID() {
		return ROLEID;
	}
	public void setROLEID(String roleid) {
		ROLEID = roleid;
	}
	public String getREFCLASS() {
		return REFCLASS;
	}
	public void setREFCLASS(String refclass) {
		REFCLASS = refclass;
	}
	public String getREFCOUNT() {
		return REFCOUNT;
	}
	public void setREFCOUNT(String refcount) {
		REFCOUNT = refcount;
	}
	public String getINCOMINGDATE() {
		return INCOMINGDATE;
	}
	public void setINCOMINGDATE(String incomingdate) {
		INCOMINGDATE = incomingdate;
	}
	public String getREFERENCENAME() {
		return REFERENCENAME;
	}
	public void setREFERENCENAME(String referencename) {
		REFERENCENAME = referencename;
	}
	public String getLETTERDATE() {
		return LETTERDATE;
	}
	public void setLETTERDATE(String letterdate) {
		LETTERDATE = letterdate;
	}
	public String getVIPSTATUS() {
		return VIPSTATUS;
	}
	public void setVIPSTATUS(String vipstatus) {
		VIPSTATUS = vipstatus;
	}
	
	public String getSTATECODE() {
		return STATECODE;
	}
	public void setSTATECODE(String statecode) {
		STATECODE = statecode;
	}
	public String getACKDATE() {
		return ACKDATE;
	}
	public void setACKDATE(String ackdate) {
		ACKDATE = ackdate;
	}
	public String getACKBY() {
		return ACKBY;
	}
	public void setACKBY(String ackby) {
		ACKBY = ackby;
	}
	public String getREFCATEGORY() {
		return REFCATEGORY;
	}
	public void setREFCATEGORY(String refcategory) {
		REFCATEGORY = refcategory;
	}
	public String getURGENCY() {
		return URGENCY;
	}
	public void setURGENCY(String urgency) {
		URGENCY = urgency;
	}
	public String getLINKREFID() {
		return LINKREFID;
	}
	public void setLINKREFID(String linkrefid) {
		LINKREFID = linkrefid;
	}
	public String getNOTECREATOR() {
		return NOTECREATOR;
	}
	public void setNOTECREATOR(String notecreator) {
		NOTECREATOR = notecreator;
	}
	public String getSECURITYGRADING() {
		return SECURITYGRADING;
	}
	public void setSECURITYGRADING(String securitygrading) {
		SECURITYGRADING = securitygrading;
	}
	public String getSIGNEDBY() {
		return SIGNEDBY;
	}
	public void setSIGNEDBY(String signedby) {
		SIGNEDBY = signedby;
	}
	public String getSIGNEDON() {
		return SIGNEDON;
	}
	public void setSIGNEDON(String signedon) {
		SIGNEDON = signedon;
	}
	public String getREMARKS() {
		return REMARKS;
	}
	public void setREMARKS(String remarks) {
		REMARKS = remarks;
	}
	public String getODSPLACE() {
		return ODSPLACE;
	}
	public void setODSPLACE(String odsplace) {
		ODSPLACE = odsplace;
	}
	public String getODSRAILWAY() {
		return ODSRAILWAY;
	}
	public void setODSRAILWAY(String odsrailway) {
		ODSRAILWAY = odsrailway;
	}
	public String getODSDATE() {
		return ODSDATE;
	}
	public void setODSDATE(String odsdate) {
		ODSDATE = odsdate;
	}
	public String getLOGINID() {
		return LOGINID;
	}
	public void setLOGINID(String loginid) {
		LOGINID = loginid;
	}
	public String getCHANGEDATE() {
		return CHANGEDATE;
	}
	public void setCHANGEDATE(String changedate) {
		CHANGEDATE = changedate;
	}
	public String getLOGINASROLENAME() {
		return LOGINASROLENAME;
	}
	public void setLOGINASROLENAME(String loginasrolename) {
		LOGINASROLENAME = loginasrolename;
	}
	public String getBtnClick() {
		return btnClick;
	}
	public void setBtnClick(String btnClick) {
		this.btnClick = btnClick;
	}
	public String getSUBJECT() {
		return SUBJECT;
	}
	public void setSUBJECT(String subject) {
		SUBJECT = subject;
	}
	public ArrayList<TrnMarking> getMarkingBeanList() {
		return markingBeanList;
	}
	public void setMarkingBeanList(ArrayList<TrnMarking> markingBeanList) {
		this.markingBeanList = markingBeanList;
	}
	public String getISBUDGET() {
		return ISBUDGET;
	}
	public void setISBUDGET(String isbudget) {
		ISBUDGET = isbudget;
	}
	public ArrayList<TrnBudget> getBudgetBeanList() {
		return budgetBeanList;
	}
	public void setBudgetBeanList(ArrayList<TrnBudget> budgetBeanList) {
		this.budgetBeanList = budgetBeanList;
	}
	public String getLANGUAGE() {
		return LANGUAGE;
	}
	public void setLANGUAGE(String language) {
		LANGUAGE = language;
	}
	public ArrayList<TrnAttachment> getAttachmentBeanList() {
		return attachmentBeanList;
	}
	public void setAttachmentBeanList(ArrayList<TrnAttachment> attachmentBeanList) {
		this.attachmentBeanList = attachmentBeanList;
	}
	public String getSUBJECTCODE() {
		return SUBJECTCODE;
	}
	public void setSUBJECTCODE(String subjectcode) {
		SUBJECTCODE = subjectcode;
	}
	public String getFMID() {
		return FMID;
	}
	public void setFMID(String fmid) {
		FMID = fmid;
	}
	public String getFILECOUNTER() {
		return FILECOUNTER;
	}
	public void setFILECOUNTER(String filecounter) {
		FILECOUNTER = filecounter;
	}
	public String getREGISTRATIONNO() {
		return REGISTRATIONNO;
	}
	public void setREGISTRATIONNO(String registrationno) {
		REGISTRATIONNO = registrationno;
	}
	public String getFILENO() {
		return FILENO;
	}
	public void setFILENO(String fileno) {
		FILENO = fileno;
	}
	
	
	
	public String getIMARKINGTO() {
		return IMARKINGTO;
	}
	public void setIMARKINGTO(String imarkingto) {
		IMARKINGTO = imarkingto;
	}
	public String getIMARKINGON() {
		return IMARKINGON;
	}
	public void setIMARKINGON(String imarkingon) {
		IMARKINGON = imarkingon;
	}
	public String getFILESTATUS1() {
		return FILESTATUS1;
	}
	public void setFILESTATUS1(String filestatus1) {
		FILESTATUS1 = filestatus1;
	}
	public String getFILESTATUS2() {
		return FILESTATUS2;
	}
	public void setFILESTATUS2(String filestatus2) {
		FILESTATUS2 = filestatus2;
	}
	public String getREPLYTYPE() {
		return REPLYTYPE;
	}
	public void setREPLYTYPE(String replytype) {
		REPLYTYPE = replytype;
	}
	public String getREASON() {
		return REASON;
	}
	public void setREASON(String reason) {
		REASON = reason;
	}
	public String getDMARKINGTO() {
		return DMARKINGTO;
	}
	public void setDMARKINGTO(String dmarkingto) {
		DMARKINGTO = dmarkingto;
	}
	public String getDMARKINGDATE() {
		return DMARKINGDATE;
	}
	public void setDMARKINGDATE(String dmarkingdate) {
		DMARKINGDATE = dmarkingdate;
	}
	public String getREGISTRATIONDATE() {
		return REGISTRATIONDATE;
	}
	public void setREGISTRATIONDATE(String registrationdate) {
		REGISTRATIONDATE = registrationdate;
	}
	public String getSTATUSREMARK() {
		return STATUSREMARK;
	}
	public void setSTATUSREMARK(String statusremark) {
		STATUSREMARK = statusremark;
	}
	public String getLINKREFNO() {
		return LINKREFNO;
	}
	public void setLINKREFNO(String linkrefno) {
		LINKREFNO = linkrefno;
	}
	public TrnMarking getMARKINGBEAN() {
		return MARKINGBEAN;
	}
	public void setMARKINGBEAN(TrnMarking markingbean) {
		MARKINGBEAN = markingbean;
	}
	public String getRECIEVEDBY() {
		return RECIEVEDBY;
	}
	public void setRECIEVEDBY(String recievedby) {
		RECIEVEDBY = recievedby;
	}
	public String getTAG1() {
		return TAG1;
	}
	public void setTAG1(String tag1) {
		TAG1 = tag1;
	}
	public String getTAG2() {
		return TAG2;
	}
	public void setTAG2(String tag2) {
		TAG2 = tag2;
	}
	public String getTAG3() {
		return TAG3;
	}
	public void setTAG3(String tag3) {
		TAG3 = tag3;
	}
	public String getTAG4() {
		return TAG4;
	}
	public void setTAG4(String tag4) {
		TAG4 = tag4;
	}
	public String getTARGETDATE() {
		return TARGETDATE;
	}
	public void setTARGETDATE(String targetdate) {
		TARGETDATE = targetdate;
	}
	public String getISCONF() {
		return ISCONF;
	}
	public void setISCONF(String isconf) {
		ISCONF = isconf;
	}
	public String getTXT_NOTE() {
		return TXT_NOTE;
	}
	public void setTXT_NOTE(String txt_note) {
		TXT_NOTE = txt_note;
	}
	public String getSIGNEDBY_YS() {
		return SIGNEDBY_YS;
	}
	public void setSIGNEDBY_YS(String signedby_ys) {
		SIGNEDBY_YS = signedby_ys;
	}
	public String getKeywords1() {
		return keywords1;
	}
	public void setKeywords1(String keywords1) {
		this.keywords1 = keywords1;
	}
	public String getKeywords2() {
		return keywords2;
	}
	public void setKeywords2(String keywords2) {
		this.keywords2 = keywords2;
	}
	public String getKeywords3() {
		return keywords3;
	}
	public void setKeywords3(String keywords3) {
		this.keywords3 = keywords3;
	}
	public String getK1() {
		return k1;
	}
	public void setK1(String k1) {
		this.k1 = k1;
	}
	public String getK2() {
		return k2;
	}
	public void setK2(String k2) {
		this.k2 = k2;
	}
	public String getK3() {
		return k3;
	}
	public void setK3(String k3) {
		this.k3 = k3;
	}
		
}
