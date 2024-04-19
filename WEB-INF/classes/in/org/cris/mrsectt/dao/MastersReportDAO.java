package in.org.cris.mrsectt.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.Beans.MastersReportBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;
import in.org.cris.mrsectt.util.Encrypt;


public class MastersReportDAO {
	
	public static void main(String[] args) {
	}
	ArrayList arrh = new ArrayList();
	ArrayList arrDT = new ArrayList();
	ArrayList arr = new ArrayList();
	String header = "";
	String layout="";
	String colwidth="";
	
	ArrayList arrh_temp = new ArrayList();
	ArrayList arrDT_temp = new ArrayList();
	ArrayList arr_temp = new ArrayList();
	
	
	public MastersReportBean getReportArray(String strSQL, String header,String layout,String  colwidth) {
		//System.out.println("strSQL"+strSQL);
		MastersReportBean bn = getReportArray(strSQL,header) ;
		bn.setLayout(layout);
		bn.setColwidth(colwidth);
		return bn;
	}
	
	public MastersReportBean getReportArray(String strSQL, String header) {
		//final String secretKey = "iojd345iuK";
		this.header = header;
		DBConnection con = new DBConnection();
		try {
			arrh.clear();
			arr.clear();
			con.openConnection();
			ResultSet rs = con.select(strSQL);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			//int numberOfColumns_temp = rsmd.getColumnCount();
			for (int i = 1; i <= numberOfColumns; i++) {
				arrh_temp.add(rsmd.getColumnName(i));
				arrDT_temp.add(rsmd.getColumnTypeName(i));
			}
			
			for (int i = 2; i <= numberOfColumns; i++) {
				arrh.add(rsmd.getColumnName(i));
				arrDT.add(rsmd.getColumnTypeName(i));
			}
			while (rs.next()) {
				int colcnt = 1;
				//System.out.println(rs.getString(arrh_temp.get(0).toString()));
				for (int i = 0; i < (numberOfColumns-1); i++) {
					
					//System.out.println(rsmd.getColumnName(i+2));
					if((rs.getString(arrh_temp.get(0).toString()).equalsIgnoreCase("C")) && (rsmd.getColumnName(i+2).equalsIgnoreCase("Remarks 2 (marking)")))
					{
						//System.out.println("99999999999=========="+rs.getString(arrh.get(i).toString()));
					//arr.add(handleNull(AES.decrypt(handleNull(rs.getString(arrh.get(i).toString())), secretKey)));
						arr.add(handleNull(Encrypt.decrypt(handleNull(rs.getString(arrh.get(i).toString())))));
					}
					else{
						arr.add(handleNull(rs.getString(arrh.get(i).toString())));	
					}
				}
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			con.closeConnection();
		}
		MastersReportBean bn = new MastersReportBean(arrh, arr, arrDT, header);
		return bn;
	}
	
	public String handleNull(String str) {
		return str != null ? str : "";
	}
	/**
	 * @return Returns the arr.
	 */
	public ArrayList getArr() {
		return arr;
	}
	
	/**
	 * @param arr
	 *            The arr to set.
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
	 * @param arrh
	 *            The arrh to set.
	 */
	public void setArrh(ArrayList arrh) {
		this.arrh = arrh;
	}
	
	public MastersReportBean getMastersReportBean() {
		MastersReportBean bn = new MastersReportBean(arrh, arr, header);
		return bn;
	}
	
}
