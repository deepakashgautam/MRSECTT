package in.org.cris.mrsectt.Beans;

/*
 * Created on Sep 14, 2009
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.util.ArrayList;

/**
 * @author Devendra
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MastersReportBean {

	/**
	 * 
	 */
	public MastersReportBean() {
		super();
	}
	
	
	public MastersReportBean(ArrayList arrh, ArrayList arr,String header) {
		super();
		this.arrh = arrh;
		this.arr = arr;
		this.header = header;
	}
	
	public MastersReportBean(ArrayList arrh, ArrayList arr,ArrayList arrDT, String header) {
		super();
		this.arrh = arrh;
		this.arrDT = arrDT;
		this.arr = arr;
		this.header = header;
	}
	
	public MastersReportBean(ArrayList arrh, ArrayList arr,ArrayList arrDT, String header,String layout,String  colwidth) {
		super();
		this.arrh = arrh;
		this.arrDT = arrDT;
		this.arr = arr;
		this.header = header;
		this.layout = layout;
		this.colwidth = colwidth;
		
		
	}
	
	
	ArrayList arrh = new ArrayList();
	ArrayList arr = new ArrayList();
	ArrayList arrDT = new ArrayList();
	String header="";
	String layout="";
	String colwidth="";
	
	public boolean hasData()
	{
		return arrh.size()>0?true:false;
	}
	/**
	 * @return Returns the arr.
	 */
	public ArrayList getArr() {
		return arr;
	}
	/**
	 * @param arr The arr to set.
	 */
	public void setArr(ArrayList arr) {
		this.arr = arr;
	}
	/**
	 * @return Returns the arrh.
	 */
	public ArrayList getArrh() {
		return arrh;
	}
	/**
	 * @param arrh The arrh to set.
	 */
	public void setArrh(ArrayList arrh) {
		this.arrh = arrh;
	}
	/**
	 * @return Returns the header.
	 */
	public String getHeader() {
		return header;
	}
	/**
	 * @param header The header to set.
	 */
	public void setHeader(String header) {
		this.header = header;
	}
	/**
	 * @return Returns the arrDT.
	 */
	public ArrayList getArrDT() {
		return arrDT;
	}
	/**
	 * @param arrDT The arrDT to set.
	 */
	public void setArrDT(ArrayList arrDT) {
		this.arrDT = arrDT;
	}


	public String getLayout() {
		return layout;
	}


	public void setLayout(String layout) {
		this.layout = layout;
	}


	public String getColwidth() {
		return colwidth;
	}


	public void setColwidth(String colwidth) {
		this.colwidth = colwidth;
	}
}
