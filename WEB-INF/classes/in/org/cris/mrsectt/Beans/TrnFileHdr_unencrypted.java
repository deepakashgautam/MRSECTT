package in.org.cris.mrsectt.Beans;

import in.org.cris.mrsectt.util.StringFormat;

import java.util.ArrayList;

public class TrnFileHdr_unencrypted {

	private String FMID	 = "";
	private String REGISTRATIONDATE = "";	
	private String REGISTRATIONDATEORG = "";	
	private String REFERENCETYPE = "";	
	private String FILENO	 = "";
	private String DEPARTMENTCODE = "";	
	private String FILESTATUS1 = "";	
	private String FILESTATUS2	 = "";
	private String NOL = "";
	private String DESTINATIONMARKING = "";	
	private String RECEIVEDFROM	 = "";
	private String REMARKS	 = "";
	private String LINKFILENO1	 = "";
	private String LINKFILENO2 = "";	
	private String LINKFILENO3	 = "";
	private String LINKFILENO4	 = "";
	private String FILECOUNTERORG	 = "";
	private String FILECOUNTERDES	 = "";
	private String FILECOUNTER	 = "";
	private String ROLEIDORG = "";	
	private String LOGINID = "";	
	private String CHANGEDATE = "";	
	private String REGISTRATIONDATEDES = "";	
	private String FILESUBJECTCODEORG = "";	
	private String FILESUBJECTCODEDES = "";	
	private String FILESUBJECTCODE = "";	
	private String ROLEIDDES = "";
	private String TENUREID = "";
	private String REPLYTYPE = "";
	private String REASON = "";
	private String SUBJECT	 = "";
	private String REGISTRATIONNODES = "";
	private String REGISTRATIONNOORG = "";
	private String REGISTRATIONNO = "";
	private String PROPOSEDPATH = "";
	private String MARKINGREMARK = "";
	private String MARKINGTO = "";
	private String MARKINGDATE = "";
	private String ISCONF = "";
	private String SENTTO="";
	
	
	public String getSENTTO() {
		return SENTTO;
	}
	public void setSENTTO(String sENTTO) {
		SENTTO = sENTTO;
	}
	//Non-database field
	private String btnClick = "";
	private String LOGINASROLEID = "";
	private String LOGINASROLENAME = "";
	private ArrayList<TrnFileMarking> markingBeanUList =  new ArrayList<TrnFileMarking>(); 
	private ArrayList<TrnFileMarking> markingBeanDList =  new ArrayList<TrnFileMarking>(); 
	private ArrayList<TrnFileIntMarking> IntmarkingBeanList =  new ArrayList<TrnFileIntMarking>(); 
	private ArrayList<TrnFileRef> refBeanList =  new ArrayList<TrnFileRef>(); 
	
	
	private String FILECOUNTERFROM = "";
	private String FILECOUNTERTO = "";
	private String REGISTRATIONDATEFROM = "";
	private String REGISTRATIONDATETO = "";
	private String FILENOSEARCH = "";
	private String SUBJECTSEARCH = "";
	private String COMMONSEARCH = "";
	
	private String IMARKINGTO = "";
	private String IMARKINGDATE = "";
	private String DMARKINGTO = "";
	private String DMARKINGDATE = "";
	private String DMARKINGREMARKS = "";
	private String DMARKINGSEQUENCE = "";
	
	private String COUNT = "";
	private String PUCLIST = "";
	private String REPLY = "";
	private String ISATTACHMENT = "";
	private String EOFFICEFILENO="";
	
	
	public String getEOFFICEFILENO() {
		return EOFFICEFILENO;
	}
	public void setEOFFICEFILENO(String eOFFICEFILENO) {
		EOFFICEFILENO = eOFFICEFILENO;
	}
	public String getISATTACHMENT() {
		return ISATTACHMENT;
	}
	public void setISATTACHMENT(String isattachment) {
		ISATTACHMENT = isattachment;
	}
	public String getREPLY() {
		return REPLY;
	}
	public void setREPLY(String reply) {
		REPLY = reply;
	}
	public String getPUCLIST() {
		return PUCLIST;
	}
	public void setPUCLIST(String puclist) {
		PUCLIST = puclist;
	}
	public String getISCONF() {
		return ISCONF;
	}
	public void setISCONF(String isconf) {
		ISCONF = isconf;
	}
	public String getCOUNT() {
		return COUNT;
	}
	public void setCOUNT(String count) {
		COUNT = count;
	}
	public String getDMARKINGSEQUENCE() {
		return DMARKINGSEQUENCE;
	}
	public void setDMARKINGSEQUENCE(String dmarkingsequence) {
		DMARKINGSEQUENCE = dmarkingsequence;
	}
	public String getMARKINGREMARK() {
		return MARKINGREMARK;
	}
	public void setMARKINGREMARK(String markingremark) {
		MARKINGREMARK = markingremark;
	}
	public String getIMARKINGTO() {
		return IMARKINGTO;
	}
	public void setIMARKINGTO(String imarkingto) {
		IMARKINGTO = imarkingto;
	}
	public String getIMARKINGDATE() {
		return IMARKINGDATE;
	}
	public void setIMARKINGDATE(String imarkingdate) {
		IMARKINGDATE = imarkingdate;
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
	public String getPROPOSEDPATH() {
		return PROPOSEDPATH;
	}
	public void setPROPOSEDPATH(String proposedpath) {
		PROPOSEDPATH = proposedpath;
	}
	public String getFILECOUNTERFROM() {
		return FILECOUNTERFROM;
	}
	public void setFILECOUNTERFROM(String filecounterfrom) {
		FILECOUNTERFROM = filecounterfrom;
	}
	public String getFILECOUNTERTO() {
		return FILECOUNTERTO;
	}
	public void setFILECOUNTERTO(String filecounterto) {
		FILECOUNTERTO = filecounterto;
	}
	public String getREGISTRATIONDATEFROM() {
		return REGISTRATIONDATEFROM;
	}
	public void setREGISTRATIONDATEFROM(String registrationdatefrom) {
		REGISTRATIONDATEFROM = registrationdatefrom;
	}
	public String getREGISTRATIONDATETO() {
		return REGISTRATIONDATETO;
	}
	public void setREGISTRATIONDATETO(String registrationdateto) {
		REGISTRATIONDATETO = registrationdateto;
	}
	public String getFILENOSEARCH() {
		return FILENOSEARCH;
	}
	public void setFILENOSEARCH(String filenosearch) {
		FILENOSEARCH = filenosearch;
	}
	public String getSUBJECTSEARCH() {
		return SUBJECTSEARCH;
	}
	public void setSUBJECTSEARCH(String subjectsearch) {
		SUBJECTSEARCH = subjectsearch;
	}
	public String getCOMMONSEARCH() {
		return COMMONSEARCH;
	}
	public void setCOMMONSEARCH(String commonsearch) {
		COMMONSEARCH = commonsearch;
	}
	public String getREGISTRATIONNODES() {
		return REGISTRATIONNODES;
	}
	public void setREGISTRATIONNODES(String registrationnodes) {
		REGISTRATIONNODES = registrationnodes;
	}
	public String getREGISTRATIONNOORG() {
		return REGISTRATIONNOORG;
	}
	public void setREGISTRATIONNOORG(String registrationnoorg) {
		REGISTRATIONNOORG = registrationnoorg;
	}
	public String getREGISTRATIONNO() {
		return REGISTRATIONNO;
	}
	public void setREGISTRATIONNO(String registrationno) {
		REGISTRATIONNO = registrationno;
	}
	public String getREGISTRATIONDATE() {
		return REGISTRATIONDATE;
	}
	public void setREGISTRATIONDATE(String registrationdate) {
		REGISTRATIONDATE = registrationdate;
	}
	public String getFILECOUNTER() {
		return FILECOUNTER;
	}
	public void setFILECOUNTER(String filecounter) {
		FILECOUNTER = filecounter;
	}
	public String getFILESUBJECTCODE() {
		return FILESUBJECTCODE;
	}
	public void setFILESUBJECTCODE(String filesubjectcode) {
		FILESUBJECTCODE = filesubjectcode;
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
	public String getBtnClick() {
		return btnClick;
	}
	public void setBtnClick(String btnClick) {
		this.btnClick = btnClick;
	}
	public String getTENUREID() {
		return TENUREID;
	}
	public void setTENUREID(String tenureid) {
		TENUREID = tenureid;
	}
	public String getLOGINASROLEID() {
		return LOGINASROLEID;
	}
	public void setLOGINASROLEID(String loginasroleid) {
		LOGINASROLEID = loginasroleid;
	}
	public String getLOGINASROLENAME() {
		return LOGINASROLENAME;
	}
	public void setLOGINASROLENAME(String loginasrolename) {
		LOGINASROLENAME = loginasrolename;
	}
	public String getSUBJECT() {
		return SUBJECT;
	}
	public void setSUBJECT(String subject) {
		SUBJECT = subject;
	}
	
	public String getFMID() {
		return FMID;
	}
	public void setFMID(String fmid) {
		FMID = fmid;
	}
	public String getREGISTRATIONDATEORG() {
		return REGISTRATIONDATEORG;
	}
	public void setREGISTRATIONDATEORG(String registrationdateorg) {
		REGISTRATIONDATEORG = registrationdateorg;
	}
	public String getREFERENCETYPE() {
		return REFERENCETYPE;
	}
	public void setREFERENCETYPE(String referencetype) {
		REFERENCETYPE = referencetype;
	}
	public String getFILENO() {
		return FILENO;
	}
	public void setFILENO(String fileno) {
		FILENO = fileno;
	}
	public String getDEPARTMENTCODE() {
		return DEPARTMENTCODE;
	}
	public void setDEPARTMENTCODE(String departmentcode) {
		DEPARTMENTCODE = departmentcode;
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
	public String getNOL() {
		return NOL;
	}
	public void setNOL(String nol) {
		NOL = nol;
	}
	public void setFILESTATUS2(String filestatus2) {
		FILESTATUS2 = filestatus2;
	}
	public String getDESTINATIONMARKING() {
		return DESTINATIONMARKING;
	}
	public void setDESTINATIONMARKING(String destinationmarking) {
		DESTINATIONMARKING = destinationmarking;
	}
	public String getRECEIVEDFROM() {
		return RECEIVEDFROM;
	}
	public void setRECEIVEDFROM(String receivedfrom) {
		RECEIVEDFROM = receivedfrom;
	}
	public String getREMARKS() {
		return REMARKS;
	}
	public void setREMARKS(String remarks) {
		REMARKS = remarks;
	}
	public String getLINKFILENO1() {
		return LINKFILENO1;
	}
	public void setLINKFILENO1(String linkfileno1) {
		LINKFILENO1 = linkfileno1;
	}
	public String getLINKFILENO2() {
		return LINKFILENO2;
	}
	public void setLINKFILENO2(String linkfileno2) {
		LINKFILENO2 = linkfileno2;
	}
	public String getLINKFILENO3() {
		return LINKFILENO3;
	}
	public void setLINKFILENO3(String linkfileno3) {
		LINKFILENO3 = linkfileno3;
	}
	public String getLINKFILENO4() {
		return LINKFILENO4;
	}
	public void setLINKFILENO4(String linkfileno4) {
		LINKFILENO4 = linkfileno4;
	}
	public String getFILECOUNTERORG() {
		return FILECOUNTERORG;
	}
	public void setFILECOUNTERORG(String filecounterorg) {
		FILECOUNTERORG = filecounterorg;
	}
	public String getFILECOUNTERDES() {
		return FILECOUNTERDES;
	}
	public void setFILECOUNTERDES(String filecounterdes) {
		FILECOUNTERDES = filecounterdes;
	}
	public String getROLEIDORG() {
		return ROLEIDORG;
	}
	public void setROLEIDORG(String roleidorg) {
		ROLEIDORG = roleidorg;
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
	public String getREGISTRATIONDATEDES() {
		return REGISTRATIONDATEDES;
	}
	public void setREGISTRATIONDATEDES(String registrationdatedes) {
		REGISTRATIONDATEDES = registrationdatedes;
	}
	public String getFILESUBJECTCODEORG() {
		return FILESUBJECTCODEORG;
	}
	public void setFILESUBJECTCODEORG(String filesubjectcodeorg) {
		FILESUBJECTCODEORG = filesubjectcodeorg;
	}
	public String getFILESUBJECTCODEDES() {
		return FILESUBJECTCODEDES;
	}
	public void setFILESUBJECTCODEDES(String filesubjectcodedes) {
		FILESUBJECTCODEDES = filesubjectcodedes;
	}
	public String getROLEIDDES() {
		return ROLEIDDES;
	}
	public void setROLEIDDES(String roleiddes) {
		ROLEIDDES = roleiddes;
	}
	public ArrayList<TrnFileMarking> getMarkingBeanUList() {
		return markingBeanUList;
	}
	public void setMarkingBeanUList(ArrayList<TrnFileMarking> markingBeanUList) {
		this.markingBeanUList = markingBeanUList;
	}
	public ArrayList<TrnFileMarking> getMarkingBeanDList() {
		return markingBeanDList;
	}
	public void setMarkingBeanDList(ArrayList<TrnFileMarking> markingBeanDList) {
		this.markingBeanDList = markingBeanDList;
	}
	public ArrayList<TrnFileIntMarking> getIntmarkingBeanList() {
		return IntmarkingBeanList;
	}
	public void setIntmarkingBeanList(
			ArrayList<TrnFileIntMarking> intmarkingBeanList) {
		IntmarkingBeanList = intmarkingBeanList;
	}
	public ArrayList<TrnFileRef> getRefBeanList() {
		return refBeanList;
	}
	public void setRefBeanList(ArrayList<TrnFileRef> refBeanList) {
		this.refBeanList = refBeanList;
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
	public String getDMARKINGREMARKS() {
		return DMARKINGREMARKS;
	}
	public void setDMARKINGREMARKS(String dmarkingremarks) {
		DMARKINGREMARKS = dmarkingremarks;
	}
	
}
