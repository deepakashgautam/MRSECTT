package in.org.cris.mrsectt.Beans;

import java.util.ArrayList;

public class MstTenure {
	
	private String TENUREID = "";
	private String ROLEID = "";
	private String YEAR = "";
	private String NAME = "";
	private String ISACTIVE = "";
	private String TENURESTARTDATE = "";
	private String TENUREENDDATE = "";
	private String TENUREOFFICENAME = "";
	private String TENUREOFFICEADDRESS = "";
	
	private ArrayList arr = new ArrayList();
	
	public String getTENUREID() {
		return TENUREID;
	}
	public void setTENUREID(String tenureid) {
		TENUREID = tenureid;
	}
	public String getROLEID() {
		return ROLEID;
	}
	public void setROLEID(String roleid) {
		ROLEID = roleid;
	}
	public String getYEAR() {
		return YEAR;
	}
	public void setYEAR(String year) {
		YEAR = year;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String name) {
		NAME = name;
	}
	
	public String getISACTIVE() {
		return ISACTIVE;
	}
	public void setISACTIVE(String isactive) {
		ISACTIVE = isactive;
	}
	public String getTENURESTARTDATE() {
		return TENURESTARTDATE;
	}
	public void setTENURESTARTDATE(String tenurestartdate) {
		TENURESTARTDATE = tenurestartdate;
	}
	public String getTENUREENDDATE() {
		return TENUREENDDATE;
	}
	public void setTENUREENDDATE(String tenureenddate) {
		TENUREENDDATE = tenureenddate;
	}
	public String getTENUREOFFICENAME() {
		return TENUREOFFICENAME;
	}
	public void setTENUREOFFICENAME(String tenureofficename) {
		TENUREOFFICENAME = tenureofficename;
	}
	public String getTENUREOFFICEADDRESS() {
		return TENUREOFFICEADDRESS;
	}
	public void setTENUREOFFICEADDRESS(String tenureofficeaddress) {
		TENUREOFFICEADDRESS = tenureofficeaddress;
	}
	public ArrayList getArr() {
		return arr;
	}
	public void setArr(ArrayList arr) {
		this.arr = arr;
	}
}
