package in.org.cris.mrsectt.dao;

import in.org.cris.mrsectt.Beans.CommonBean;
import in.org.cris.mrsectt.dbConnection.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UploadAttachmentFileDAO {
	String strSQL = "";
	String condSQL = "";
	DBConnection con = new DBConnection();

public ArrayList<CommonBean> getData(String filenofrom, String filenoto, String datefrom, String dateto, String roleId, String isConf) {
	
		   ArrayList<CommonBean> arrList = new ArrayList<CommonBean>();
		   
			
			
			String fileCountFrom = filenofrom;
			String fileCountTo = filenoto;
			//condSQL += isConf.equalsIgnoreCase("0")? " AND ISCONF = '0'": " AND ISCONF IN ('0','1')";
			
			strSQL = " SELECT FMID, FILENO, TO_CHAR(REGISTRATIONDATEDES,'DD/MM/YYYY') REGISTRATIONDATEDES, " +
					" (SELECT ROLENAME FROM MSTROLE WHERE ROLEID=TRNFILEHDR.RECEIVEDFROM) RECEIVEDFROM, SUBJECT" +
					 " FROM TRNFILEHDR WHERE ROLEIDDES = ? " +
					 " AND FILECOUNTERDES BETWEEN ? AND ? "+condSQL+"" +
					 " AND TO_DATE(TO_CHAR(REGISTRATIONDATEDES,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" +
					 " ORDER BY FMID DESC";
			
			try {
				
				//	log.debug(strSQL);
					con.openConnection();
					PreparedStatement ps = null;
					ps = con.setPrepStatement(strSQL);
					ps.setString(1, roleId);
					ps.setString(2, fileCountFrom);
					ps.setString(3, fileCountTo);
					ps.setString(4, datefrom);
					ps.setString(5, dateto);
					ResultSet rs =  ps.executeQuery();
					while (rs.next()) {
						CommonBean bn =  new CommonBean();
						bn.setField1(CommonDAO.handleNull(rs.getString(1)));
						bn.setField2(CommonDAO.handleNull(rs.getString(2)));
						bn.setField3(CommonDAO.handleNull(rs.getString(3)));
						bn.setField4(CommonDAO.handleNull(rs.getString(4)));
						bn.setField5(CommonDAO.handleNull(rs.getString(5)));
						arrList.add(bn);
					}
					rs.close();
					
					
				} catch(SQLException e)		{
					//log.fatal(e,e);
				}finally{
					
					con.closeConnection();
				}
				
				return arrList;
			//return (new CommonDAO()).getSQLResult(strSQL, 5);
		}
	}